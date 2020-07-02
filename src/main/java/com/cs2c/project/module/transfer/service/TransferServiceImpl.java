package com.cs2c.project.module.transfer.service;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.config.Cs2cConfig;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.project.module.wdiodeConfig.service.IWdiodeConfigService;
import com.cs2c.project.system.user.domain.User;

import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.transfer.domain.Transfer;

import javax.sound.sampled.Port;

/**
 * 传输控制 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-25
 */
@Service
public class TransferServiceImpl implements ITransferService 
{
	@Autowired
	private ICommandInfoService commandInfoService;

	@Autowired
	private IWdiodeConfigService wdiodeConfigService;
	
	private codezip codeZip = new codezip();
	/**
     * 查询传输控制列表
     * 
     * @param transfer 传输控制信息
     * @return 传输控制集合
     */
	@Override
	public List<Transfer> selectTransferList(Transfer transfer)
	{
		return parseStdout(filterStdout(transfer_control("transfer_manager", "get")));
	}

	/**
     * 修改传输控制
	 *
	 * @param ports
	 * @param pids
	 * @param operation
	 * @return
	 */
	@Override
	public AjaxResult changeStatus(String ports, String pids, String operation)
	{
		String result = null;
		if ("pass".equals(operation) || "pause".equals(operation) || "cancel".equals(operation)) {
			result = transfer_control("transfer_manager", "update", "\"" + ports + "\"", operation);
		} else if ("cancel".equals(operation)) {
			//result = transfer_control("transfer_manager", "cancel", "\"" + ports + "\"", "\"" + pids + "\"");
		} else {
			LogUtils.ERROR_LOG.error("修改传输任务的状态指令 " + operation + " 非法， 当前用户为 " + ShiroUtils.getUser().getLoginName());
			return AjaxResult.error(1, "非法操作, 请规范操作，您的操作已经被记录");
		}
		if (isStdoutContainsCustomError(result)) {
			LogUtils.ERROR_LOG.error("更改任务状态失败（pid = " + pids + "; port = " + ports + "; operation = " + operation + "; STDOUT输出内容为 " + result);
			return AjaxResult.error(1, "更改任务状态失败");
		} else {
			return AjaxResult.success();
		}
	}

	/**
     * 删除传输控制对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTransferByIds(String ids)
	{
		return 1;
	}

	// 用于更新状态
	// 用于执行导出任务
	private String transfer_control(String commandKey, String oper, String port, String status) {
		return transfer_control(commandKey, oper, port, status, "");
	}

	// 用于执行取消操作
	private String transfer_control(String commandKey, String oper, String pid) {
		return transfer_control(commandKey, oper, "", "", pid);
	}

	// 执行获取传输列表操作
	private String transfer_control(String commandKey, String oper) {
		return transfer_control(commandKey, oper, "", "", "");
	}

	/**
	 * 执行传输控制相关的命令
	 *
	 * 格式：命令 transfer_control get => 获取当前传输的列表
	 *                           update port [pause | pass ] => 暂停、放行
	 *                           cancel pid => 取消
	 *
	 * @param commandKey 命令键
	 * @param oper 操作类型(get | update | cancel)
	 * @param port 端口号
	 * @param status (pause | pass)目标状态
	 * @param pid 进程号
	 *
	 * @return
	 */
	private String transfer_control(String commandKey, String oper, String port, String status, String pid) {
		CommandInfo commandInfo = commandInfoService.selectCommandInfoByKey(commandKey);
		if (commandInfo != null && !commandInfo.getValue().trim().equals("")) {
			boolean jobBackground = false;
			if (commandInfo.getBackground().trim().equals("yes")) {
				jobBackground = true;
			}

			long jobTimeout = 3000; // 默认3s超时
			if (!jobBackground) {
				if (commandInfo.getTimeout() != 0) {
					jobTimeout = commandInfo.getTimeout();
				}
			}

			StringBuilder command = new StringBuilder(commandInfo.getValue());
			command.append(UserConstants.SPACE_ONE).append(oper)
					.append(UserConstants.SPACE_ONE).append(port)
					.append(UserConstants.SPACE_ONE).append(pid)
					.append(UserConstants.SPACE_ONE).append(status);
			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if ("get".equals(oper) || StringUtils.isEmpty(stderr)) {
					return stdout;
				} else {
					LogUtils.ERROR_LOG.error("执行命令 " + command.toString() + " 时出错\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
					if (!StringUtils.isEmpty(stderr)) {
						return "Error: STDERR中包含错误信息，任务执行失败";
					}
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("执行命令 " + command.toString() + "失败 ", e);
				return "Error: 发生异常，任务执行失败";
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在传输控制的command或command信息错误, 获取command时参数为 " + commandKey);
			return "Error: 数据库异常，任务执行失败";
		}

		return "";
	}

	/**
	 * 检查标准输出是否包含Error自定义输出
	 */
	private ArrayList<String> filterStdout(String string) {
		ArrayList<String> result = new ArrayList<>();
		String str[] = string.split("\n");
		for (String s : str) {
			if (s.trim().startsWith("Error")) {
				LogUtils.ERROR_LOG.error("解析正在传输列表的内容时发现错误，已忽略， 错误内容为 " + s);
			}

			if (s.trim().equals("")) {
				continue;
			}

			result.add(s.trim());
		}

		return result;
	}


	//pid port transferFile filenameDir timeStamp currentStatus preview
	//0   1    2            3           4         5             6
	//transferFile timeStamp pid port details preview statusTransfer startTransfer breakTransfer;
	private ArrayList<Transfer> parseStdout(ArrayList<String> list) {
		ArrayList<Transfer> result = new ArrayList<>();
		int index = 1;
		for (String line : list) {
			String[] lineArray = line.split("JoenarrySep");
			if (lineArray.length != 7) {
				LogUtils.ERROR_LOG.error("实例化传输对象的时候，数据不符合规范， 数据为 " + line);
				continue;
			}

			result.add(new Transfer(index++, lineArray[2], lineArray[4], lineArray[0], lineArray[1], "查看详情", lineArray[6], lineArray[5], "", "", lineArray[3]));
		}

		return  result;
	}

	public AjaxResult download(String  path, String filename) {
		String downloadFilename = encodingFilename(filename);
		String result = transfer_control("transfer_manager", "export", "\"" + path + File.separator + filename + "\"", "\"" + getAbsoluteFile(downloadFilename) + "\"");

		if (!result.trim().equals("") || isStdoutContainsCustomError(result)) {
			LogUtils.ERROR_LOG.warn("文件" + path + File.separator + filename + "获取失败， 状态信息为 " + result);
			return AjaxResult.error("任务已传输完毕，文件不存在！");
		} else {
			return AjaxResult.success(downloadFilename);
		}
	}

	public AjaxResult downloadPreview(String  path, String filename) {
		String c_file_dir = path;
		String oriFilename = filename;

		String fileBasename = getFilename(filename);
		String previewName = "noExists";
		String suffix = getSuffix(filename);
		// 20170918 修改预览子目录问题
		if ("txt".equalsIgnoreCase(suffix) || "xml".equalsIgnoreCase(suffix) || "html".equalsIgnoreCase(suffix)) {
			previewName = c_file_dir.replace("/var/wdiode/c", "/var/wdiode/d") + join("_dw_", fileBasename.split("\\."));

			int index = filename.lastIndexOf(".");
			String tmpName = filename.substring(0, index);
			String joinName = join("_dw_", tmpName.split("\\."));

			filename = c_file_dir + joinName + "." + getSuffix(filename);
		} else if("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)){
			filename = join("_dw_", filename.split("\\.")) + ".csv";
			previewName = c_file_dir.replace("/var/wdiode/c", "/var/wdiode/d") + getFilename(filename);
			filename = c_file_dir.replace("/var/wdiode/c", "/var/wdiode/d") + filename;
		}

		String previewTifName = previewName + ".tif";

		if (oriFilename.contains("/")) {
			oriFilename = oriFilename.replaceAll("/", "00");
		}
		String downloadFilename = encodingFilename(oriFilename);

		String command = "/bin/cp ";
		CommandLine commandLine = CommandLine.parse(command);
		commandLine.addArgument(previewTifName, false);
		commandLine.addArgument(getAbsoluteFile(downloadFilename) + ".tif" , false);

		Map<String, String> output = null;

		try {
			output = ShellUtils.runAndGetOutput(commandLine, 5000, false);

			LogUtils.ERROR_LOG.error("准备下载预览文件命令 = " + commandLine.toString());

			//LogUtils.ERROR_LOG.error("shiuahuidhuihusahdufish ===== " + commandLine.toString() );
			String logs = "";
			if (output != null) {
				logs = "stdout = " + output.get("stdout") + "; stderr = " + output.get("stderr");
				LogUtils.ERROR_LOG.error("执行命令 " + commandLine.toString() + "失败, OUTPUT ERROR = " + logs);
				//
			}

			if (StringUtils.isEmpty(output.get("stderr")) && StringUtils.isEmpty(output.get("stdout"))) {
				return AjaxResult.success(downloadFilename + ".tif");
			} else {
				return AjaxResult.error("任务已传输完毕 或 预览文件异常");
			}
		} catch (IOException e) {
			String logs = "";
			if (output != null) {
				logs = "stdout = " + output.get("stdout") + "; stderr = " + output.get("stderr");
			}
			LogUtils.ERROR_LOG.error("执行命令 " + commandLine.toString() + "失败, OUTPUT ERROR = " + logs, e);
			return AjaxResult.error("Error: 发生异常，下载预览失败");
		}

//		String result = transfer_control("transfer_manager", "export", "\"" + path + File.separator + filename + "\"", "\"" + getAbsoluteFile(downloadFilename) + "\"");
//
//		if (!result.trim().equals("") || isStdoutContainsCustomError(result)) {
//			LogUtils.ERROR_LOG.warn("文件" + path + File.separator + previewName + "获取失败， 状态信息为 " + result);
//			return AjaxResult.error("任务已传输完毕，文件不存在！");
//		} else {
//			return AjaxResult.success(downloadFilename);
//		}


	}



	/**
	 * 检查标准输出是否包含Error自定义输出
	 */
	private boolean isStdoutContainsCustomError(String string) {
		String result[] = string.split("\n");
		for (String s : result) {
			if (s.trim().startsWith("Error"))
				return true;
		}
		return false;
	}

	/**
	 * 编码文件名
	 */
	private String encodingFilename(String filename)
	{
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");
		filename = UUID.randomUUID().toString() + "_" + time.format(new Date()) + "-" + filename;

		return filename;
	}

	/**
	 * 获取下载路径
	 *
	 * @param filename 文件名称
	 */
	public String getAbsoluteFile(String filename)
	{

		String downloadPath = Cs2cConfig.getTransferDownloadPath() + File.separator + filename;
		File desc = new File(downloadPath);
		if (!desc.getParentFile().exists())
		{
			desc.getParentFile().mkdirs();
		}
		return downloadPath;
	}



	private String read(File f) {
		//File f = new File(filename);
		String content = "";
		if (f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
				while ((content = br.readLine()) != null) {
					if (content.trim().length() > 0) {
						break;
					}
				}

				br.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("读取文件内容发生异常: " + f.getAbsolutePath(), e);
				content = null;
			}
		} else {
			content = null;
		}

		return content;
	}

	/*
	 * 读取文件所有内容
	 * 当文件不存在或者发生异常返回null
	 */
	private ArrayList<String> readAll(String filename) {
		File f = new File(filename);


		String content = "";
		ArrayList<String> contents = new ArrayList<String>();
		if (f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
				while ((content = br.readLine()) != null) {
					contents.add(content.trim());
				}

				br.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("读取文件内容发生异常: " + filename, e);
				contents = null;
			}
		} else {
			contents = null;
		}

		return contents;
	}

	private String getSuffix(String s) {
		String suffix = null;

		int i = s.lastIndexOf(".");

		if (i == s.length() - 1 || i == -1) {
			suffix = "";
		} else if (i > 0) {
			suffix = s.substring(i + 1);
		} else if (i == 0) {
			suffix = "";
		}

		return suffix;
	}

	private String getFilename(String s) {
		String filename = "";
		if (s.lastIndexOf(".") == -1 || s.lastIndexOf(".") == 0) {
			filename = s;
		} else {
			filename = s.substring(0, s.lastIndexOf("."));
		}

		return filename;
	}


	private String getWdiodeConfig(String key) {
		return wdiodeConfigService.selectWdiodeConfigByKey(key).getW_value();
	}



	public ArrayList<Transfer> getTransferList() {
		ArrayList<Transfer> result = new ArrayList<>();
		int index = 1;

		String wlogd = getWdiodeConfig("wlogd");
		String security_level = getWdiodeConfig("security_level");
		String t_files = getWdiodeConfig("t_files");
		//String c_file_tmp = getWdiodeConfig("c_file_tmp");
		String c_file_tmp = "/var/wdiode/d";

		if (StringUtils.isEmpty(wlogd) || StringUtils.isEmpty(security_level) || StringUtils.isEmpty(t_files) || StringUtils.isEmpty(c_file_tmp)) {
			LogUtils.ERROR_LOG.error("获取配置项异常，可能为数据库中配置项不正确 或 缺少配置项");
			return result;
		}

		File f_wlogd = new File(wlogd);
		if (!f_wlogd.exists() || !f_wlogd.isDirectory() || !f_wlogd.canRead()) {
			LogUtils.ERROR_LOG.error("获取系统进程列表异常，可能为进程列表文件格式不正确 或 权限不正确");
			return result;
		}

		for (File f : f_wlogd.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(".ctrl"))
					return false;

				return true;
			}
		})) {

			//pid port transferFile filenameDir timeStamp currentStatus preview
			//0   1    2            3           4         5             6
			//transferFile timeStamp pid port details preview statusTransfer startTransfer breakTransfer;
			//result.add(new Transfer(index++, lineArray[2], lineArray[4], lineArray[0], lineArray[1], "查看详情", lineArray[6], lineArray[5], "", "", lineArray[3]));


			Transfer transfer = new Transfer();
			try {
				String headLine = readHeadLine(f);
				if (headLine == null) {
					continue;
				}

				String[] headLineArrays = headLine.split("\\|\\|");

				File transferFile = new File(headLineArrays[1].split("=")[1]);
				String filenameDir = transferFile.getParent().trim() + transferFile.separatorChar;

				transfer.setTransferFile(transferFile.getName().trim());
				transfer.setPort(headLine.split("\\|\\|")[2].split("=")[1]);
				transfer.setPid(headLine.split("\\|\\|")[3].split("=")[1]);
				transfer.setRealDir(filenameDir);

				transfer.setDetails("查看详情");
				transfer.setBreakTransfer("");
				transfer.setStartTransfer("");

				String statusTransfer = null;
				// 获取传输状态
				if (isTFiles(t_files, transferFile.getName())) { // 白名单
					int transferFlag = getTransferFlagWirte("wdiode." + transfer.getPort(), wlogd + transferFile.separatorChar);
					statusTransfer = getCurrentStatus("wdiode." + transfer.getPort() + ".ctrl", wlogd + transferFile.separatorChar, security_level, transferFlag);
				} else { // 非白名单
					int transferFlag = getTransferFlag("wdiode." + transfer.getPort(), wlogd + transferFile.separatorChar);
					statusTransfer = getCurrentStatus("wdiode." + transfer.getPort() + ".ctrl", wlogd + transferFile.separatorChar, security_level, transferFlag);
				}
				transfer.setStatusTransfer(statusTransfer);

				// 获取时间戳
				Object[] timestamp = getTimeStamp(transferFile.getName(), t_files, filenameDir, c_file_tmp);
				if (timestamp != null && timestamp.length == 3) {
					transfer.setTimeStamp(timestamp[0].toString());
				} else {
					LogUtils.ERROR_LOG.error("获取文件 " + transferFile.getAbsolutePath() + " 传输时间失败");
					continue;
				}

				// 获取预览状态
				String preview = getPreviewStatus(filenameDir, transferFile.getName(), t_files, c_file_tmp)[0].toString();
				transfer.setPreview(preview);

			} catch (ArrayIndexOutOfBoundsException e) {
				LogUtils.ERROR_LOG.error("处理系统进程列表异常，列表解析时， 格式不正确 => ", e);
				continue;
			}

			transfer.setId(index++);
			result.add(transfer);
		}

		return result;
	}

	private String getCurrentStatus(String statusTransferFile, String wlogd, String security_level, int transferFlag) {
		String currentStatus = "";
		String ctrlFlag = "";
		if (transferFlag == 1) {
			currentStatus = "transfering";
		} else if (transferFlag == 0) {
			currentStatus = "getStateing";
		} else {
			File f = new File(statusTransferFile);
			if (f.exists()) {
				String c = null;
				if ((c = readHeadLine(f)) != null) {
					ctrlFlag = c.split("=")[1];
				}
			} else {
				ctrlFlag = security_level.trim();
			}

			if (transferFlag == 2 && ctrlFlag.equalsIgnoreCase("auto")) {
				currentStatus = "autoTranslate";
			} else if (transferFlag == 2 && ctrlFlag.equalsIgnoreCase("control")) {
				currentStatus = "controlTranslate";
			} else if (transferFlag == 3 && ctrlFlag.equalsIgnoreCase("auto")) {
				currentStatus = "autoPrepare";
			} else if (transferFlag == 3 && ctrlFlag.equalsIgnoreCase("control")) {
				currentStatus = "controlPrepare";
			}
		}

		return currentStatus;
	}


	/**
	 * transferFlag 代表当前任务传输的状态标识
	 * 0 => 初始值、状态未知
	 * 1 => 已经开始发送
	 * 2 => 正在转换
	 * 3 => 转换完成,等待传输
	 */
	private int getTransferFlag(String logTransferFile, String wlogd) {
		ArrayList<String> contents = null; // 用于保存获取文件的内容
		int transferFlag = 0; // 用于标识当前传输状态

		if ((contents = readAll(wlogd + logTransferFile)) != null) {
			for ( String c : contents) {
				if (c.indexOf("send") >= 0 && c.indexOf("sendfilename") < 0) {
					transferFlag = 1;
					break;
				}
			}
			if (transferFlag == 0) {
				for (String c : contents) {
					if (c.indexOf("starting") >= 0 && c.indexOf("sendfilename") < 0) {
						transferFlag = 2;
						break;
					}
				}
				if (transferFlag == 2) {
					String lastLine = contents.get(contents.size() - 1);
					String lastTwoLine = contents.get(contents.size() - 2);
					if (lastLine.indexOf("security") >= 0 || lastTwoLine.indexOf("security") >= 0) {
						transferFlag = 3;
					}
				}
			}
		}

		return transferFlag;
	}


	/* transferFlag 代表当前任务传输的状态标识
    0 => 初始值、状态未知
    1 => 已经开始发送
    2 => 正在转换
    3 => 转换完成,等待传输
    */
	private int getTransferFlagWirte(String logTransferFile, String wlogd) {
		ArrayList<String> contents = null; // 用于保存获取文件的内容
		int transferFlag = 0; // 用于标识当前传输状态
		if ((contents = readAll(wlogd + logTransferFile)) != null) {
			for (String c : contents) {
				if (c.indexOf("send") >= 0) {
					transferFlag = 1;
					break;
				}
			}

			if (transferFlag == 0) {
				String lastLine = contents.get(contents.size() - 1);
				String lastTwoLine = contents.get(contents.size() - 2);
				if (lastLine.indexOf("security") >= 0 || lastTwoLine.indexOf("security") >= 0) {
					transferFlag = 3;
				}
			}
		}

		return transferFlag;
	}

	// 读取文件首行内容
	private String readHeadLine(File file) {
		//File f = new File(filename);
		String content = "";
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				while ((content = br.readLine()) != null) {
					if (content.trim().length() > 0) {
						break;
					}
				}

				br.close();
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("获取进程列表时，读取进程 " + file.getName() + " 信息时发生异常", e);
				content = null;
			}
		} else {
			content = null;
		}

		return content;
	}

	private Boolean isTFiles(String t_files, String filename) {
		if (StringUtils.isEmpty(t_files) || StringUtils.isEmpty(filename)) {
			return false;
		}

		for (String tt : t_files.trim().split("\\s+")) {
			if (filename.endsWith(tt))
				return true;
		}

		return false;
	}

	// fileLength = -200 代表是白名单文件， 不提供预览功能
	// fileLength > 0 代表预览文件存在, 可以提供预览
	// fileLength = 0 代表转换出错, 预览文件大小为0
	// 返回 {时间戳, fileLength, supportFlag} 或 null(代表该任务已完成)
	private Object[] getTimeStamp(String filename, String t_files, String filenameDir, String c_file_tmp) {
		// 临时代码, 后续合并
		String cFileName = filename;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String c_file_dir = filenameDir;

		/*
		 * 为解决上传文件编码转换失败的情况
		 */
		long fileLength = -1;
		boolean supportFlag = false;
		// 标识是否是白名单文件, true代表是
		boolean flag = false;
		String suffix = null;
		// 获取文件名后缀
		//suffix = getSuffix(filename);
		if (filename.endsWith(".txt") || filename.endsWith(".xml") || filename.endsWith(".html")) {
			//if ("txt".equalsIgnoreCase(suffix) || "xml".equalsIgnoreCase(suffix) || "html".equalsIgnoreCase(suffix)) {
			filename = join("_", filename.split("\\.")) + "_wd.txt.png";
			supportFlag = true;
			//} else if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
		} else if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
			filename = join("_", filename.split("\\.")) + "_wd.csv.png";
			supportFlag = true;
		} else {
			if (isTFiles(t_files, filename)) {
				flag = true;
				supportFlag = true;
			}

			if (!flag) {
				filename = "notSupportedFormat";
			}
		}

		/*
		 * 解决装换为多张图片的情况
		 * 如果为多张图片， 则默认获取第一张的时间
		 */
		if (!flag) {
			File k = new File(c_file_tmp + filename);
			if (!k.exists()) {
				filename = filename + "__wd__0__wd__.png";
			}

			k = new File(c_file_tmp + filename);
			if (k.exists()) {
				fileLength = k.length();
			}

		} else {
			fileLength = -200; // 代表是白名单文件
		}
		LogUtils.ERROR_LOG.error("filenamedir = " + filenameDir + "      cFilename = " + cFileName);

		//File f = new File(c_file_dir + cFileName);
		File f = new File(filenameDir + "/" + cFileName);
		if (f.exists()) {
			return new Object[]{format.format(f.lastModified()), fileLength, supportFlag};
		} else {
			//return new Object[]{"文件名不符合规范", fileLength, supportFlag};
			// 如果原文件不存在, 认为该任务已经传输完成, 不再进行前端显示
			return null;
		}
	}

	private String join(String join, String[] strAry) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strAry.length; i++) {
			if (i == (strAry.length - 1)) {
				sb.append(strAry[i]);
			} else {
				sb.append(strAry[i]).append(join);
			}
		}

		return new String(sb);
	}

	/*
	 * generated 预览已生成
	 * notGenerated 预览未生成
	 * notSupportedFormat 不支持的文件格式
	 * superNotProvide 白名单文件， 不提供预览
	 */
	private Object[] getPreviewStatus(String pDir, String transferFile, String t_files, String c_file_tmp){


		String previewFilename = "";
		String preview = "";
		//String suffix = getSuffix(transferFile);

		//if ("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)) {
		if (transferFile.endsWith(".xls") || transferFile.endsWith(".xlsx")) {
			String filename = join("_dw_", transferFile.split("\\.")) + ".csv";
			previewFilename = getFilename(filename) + ".tif";

			/*if (new File(c_file_tmp + previewFilename).exists()) {
				preview = "generated";
			} else {
				preview = "notGenerated";
			}*/
			// 20170918改解决子目录事宜
			if (new File(pDir.replace("/var/wdiode/c", "/var/wdiode/d") + previewFilename).exists()) {
				preview = "generated";
			} else {
				preview = "notGenerated";
			}
			//} else if (suffix.equalsIgnoreCase("xml") || suffix.equalsIgnoreCase("txt") || suffix.equalsIgnoreCase("html")) {
		} else if (transferFile.endsWith(".xml") || transferFile.endsWith(".txt") || transferFile.endsWith(".html")) {
			previewFilename = getFilename(transferFile) + ".tif";

			/*if (new File(c_file_tmp + previewFilename).exists()) {
				preview = "generated";
			} else {
				preview = "notGenerated";
			}*/
			// 20170918改解决子目录事宜
			if (new File(pDir.replace("/var/wdiode/c", "/var/wdiode/d") + previewFilename).exists()) {
				preview = "generated";
			} else {
				preview = "notGenerated";
			}
		} else {
			boolean flag = true;
//            for (String i : t_files.trim().split("\\s+")) {
//                if (transferFile.endsWith(i)) {
//                    flag = false;
//                    break;
//                }
//            }

			if (isTFiles(t_files, transferFile)) {
				flag = false;
			}

			if (flag) {
				previewFilename = "预览文件不存在";
				preview = "notSupportedFormat";
			} else {
				previewFilename = "不支持预览此类型文件";
				preview = "superNotProvide";
			}
		}

		//previewFilename = c_file_tmp + previewFilename;
		previewFilename = pDir.replace("/var/wdiode/c", "/var/wdiode/d") + previewFilename;


//        if (debug.trim().equalsIgnoreCase("1")) {
//            JoenasLogManager.LOG("preview = " + preview + "  previewFilename = " + previewFilename);
//        }

		return new Object[] {preview, previewFilename};
	}

	@Override
	public AjaxResult genPreview(String dir, String filename) {
		//String command = "/usr/bin/text2image --textJSEPsource --outputbaseJSEPdestination --font=WenQuanYi\\ Zen\\ Hei\\ Medium --fonts_dirJSEP/usr/share/fonts/wqy-zenhei";
		//String command = "/usr/bin/text2image --textJSEPsource --outputbaseJSEPdestination";

		String cmd = commandInfoService.selectCommandInfoByKey("preview_control").getValue();

		if (StringUtils.isEmpty(cmd)) {
			LogUtils.ERROR_LOG.error("获取预览命令时出错， 数据库不存在相应的命令或当前连接失败");
			return AjaxResult.error();
		}


		String c_file_dir = dir;

		String fileBasename = getFilename(filename);
		String previewName = "noExists";
		String suffix = getSuffix(filename);
		// 20170918 修改预览子目录问题
		if ("txt".equalsIgnoreCase(suffix) || "xml".equalsIgnoreCase(suffix) || "html".equalsIgnoreCase(suffix)) {
			previewName = c_file_dir.replace("/var/wdiode/c", "/var/wdiode/d") + join("_dw_", fileBasename.split("\\."));

			int index = filename.lastIndexOf(".");
			String tmpName = filename.substring(0, index);
			String joinName = join("_dw_", tmpName.split("\\."));

			filename = c_file_dir + joinName + "." + getSuffix(filename);
		} else if("xls".equalsIgnoreCase(suffix) || "xlsx".equalsIgnoreCase(suffix)){
			filename = join("_dw_", filename.split("\\.")) + ".csv";
			previewName = c_file_dir.replace("/var/wdiode/c", "/var/wdiode/d") + getFilename(filename);
			filename = c_file_dir.replace("/var/wdiode/c", "/var/wdiode/d") + filename;
		}

		//filename = filename.replace(" ", "\\ ");
		//previewName = previewName.replace(" ", "\\ ");

		// 重新构造生成命令
		String command = cmd.trim() + " gen \"" + filename + "\" \"" + previewName + "\"";
		//command = command.replace("source", filename).replace("destination", previewName).replace("JSEP", "=");

		//LinkedList<String> result = new LinkedList<String>();
		CommandLine commandLine = CommandLine.parse(command);
		//commandLine.addArgument("--font=\'WenQuanYi\\ Zen\\ Hei\\ Medium\'", false);
		//commandLine.addArgument("--fonts_dir=/usr/share/fonts/wqy-zenhei");
		Map<String, String> output = null;

		try {
			output = ShellUtils.runAndGetOutput(commandLine, 5000, false);

			LogUtils.ERROR_LOG.error("生成预览命令 = " + command);

			//LogUtils.ERROR_LOG.error("shiuahuidhuihusahdufish ===== " + commandLine.toString() );
			String logs = "";
			if (output != null) {
				logs = "stdout = " + output.get("stdout") + "; stderr = " + output.get("stderr");
				LogUtils.ERROR_LOG.error("执行命令 " + command + "失败, OUTPUT ERROR = " + logs);
			}


			return AjaxResult.success();
		} catch (IOException e) {
			String logs = "";
			if (output != null) {
				logs = "stdout = " + output.get("stdout") + "; stderr = " + output.get("stderr");
			}
			LogUtils.ERROR_LOG.error("执行命令 " + command + "失败, OUTPUT ERROR = " + logs, e);
			return AjaxResult.error("Error: 发生异常，生成预览失败");
		}

	}

    @Override
    public AjaxResult downloadF() {
        List<Transfer> list = selectTransferList(new Transfer());
        String stdout = "";
        StringBuffer sb = new StringBuffer();
        for(Transfer transfer : list) {
            sb.append( transfer.getTransferFile()+"\n");
            
            sb.append( transfer.getTimeStamp()+"\t");
            sb.append( transfer.getPid()+"\t" + transfer.getPort()+"\t");
            
            
            switch (transfer.getStatusTransfer()) {
                case "superTransfer":
                    sb.append( "正在传输……\t");
                    break;
                case "transfering":
                    sb.append( "正在传输……\n");
                    break;
                case "autoTranslate":
                    sb.append( "正在转换……\n");
                    break;
                case "controlTranslate":
                    sb.append( "正在转换……\n");
                    break;
                case "autoPrepare":
                    sb.append( "准备传输……\n");
                    break;
                case "getStateing":
                    sb.append( "状态获取中……\n");
                    break;
                case "convertFailed":
                    sb.append( "转换失败……\n");
                    break;
                case "notSupportFormat":
                    sb.append( "文件格式不支持……\n");
                    break;
                default:
                    sb.append( "等待放行……\n");
            }

        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "传输控制-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
}
