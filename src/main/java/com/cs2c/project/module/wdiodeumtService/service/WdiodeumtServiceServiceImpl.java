package com.cs2c.project.module.wdiodeumtService.service;

import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.wdiodeumtService.domain.WdiodeumtService;
import com.cs2c.project.module.wdiodeumtService.mapper.WdiodeumtServiceMapper;
import com.cs2c.project.system.config.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 端口注册服务 服务层实现
 *
 * @author Joenas
 * @date 2020-03-09
 */
@Service
public class WdiodeumtServiceServiceImpl implements IWdiodeumtServiceService {
	@Autowired
	private WdiodeumtServiceMapper wdiodeumtServiceMapper;

	@Autowired
	private IConfigService configService;
	
    @Autowired
    private IWdiodeumtServiceMService wdiodeumtServiceMService;

	@Value("${joenas.wdiodeumt_service_config_path}")
	private String wdiodeumtServiceConfigPath;

	@Value("${joenas.wdiode_config_path}")
	private String wdiodeConfigPath;
	
	/**
	 * 查询端口注册服务信息
	 *
	 * @param id 端口注册服务ID
	 * @return 端口注册服务信息
	 */
	@Override
	public WdiodeumtService selectWdiodeumtServiceById(Integer id) {
		return wdiodeumtServiceMapper.selectWdiodeumtServiceById(id);
	}

	/**
	 * 查询端口注册服务列表
	 *
	 * @param wdiodeumtService 端口注册服务信息
	 * @return 端口注册服务集合
	 */
	@Override
	public List<WdiodeumtService> selectWdiodeumtServiceList(WdiodeumtService wdiodeumtService) {
		return wdiodeumtServiceMapper.selectWdiodeumtServiceList(wdiodeumtService);
	}

	/**
	 * 新增端口注册服务
	 *
	 * @param wdiodeumtService 端口注册服务信息
	 * @return 结果
	 */
	@Override
	public int insertWdiodeumtService(WdiodeumtService wdiodeumtService) {

		int i = wdiodeumtServiceMapper.insertWdiodeumtService(wdiodeumtService);
		if (i == 1) {
			int i1 = persistConfig();
			if (i1 == 0) {
				LogUtils.ERROR_LOG.error("持久化修改到配置文件失败,回滚数据库");
				if (0 == wdiodeumtServiceMapper.deleteWdiodeumtServiceById(wdiodeumtService.getId())) {
					LogUtils.ERROR_LOG.error("回滚数据库失败");
					return 0;
				}
				LogUtils.ERROR_LOG.error("回滚数据库成功");
				return 0;
			}
			wdiodeumtServiceMService.start(wdiodeumtServiceMapper.selectWdiodeumtServiceByServiceName
					(wdiodeumtService.getServiceName()).getId()+"");
			return 1;
		} else {
			LogUtils.ERROR_LOG.error("持久化修改到数据库失败");
			return 0;
		}
	}

	/**
	 * 修改端口注册服务
	 *
	 * @param wdiodeumtService 端口注册服务信息
	 * @return 结果
	 */
	@Override
	public int updateWdiodeumtService(WdiodeumtService wdiodeumtService) {
		wdiodeumtServiceMService.stop(wdiodeumtService.getId()+"");
		WdiodeumtService oldW = oldWdiodeumtService(
				wdiodeumtServiceMapper.selectWdiodeumtServiceById(wdiodeumtService.getId()));
		int i = wdiodeumtServiceMapper.updateWdiodeumtService(wdiodeumtService);
		
		if (i == 1) {
			int i1 = persistConfig();
			
			wdiodeumtServiceMService.start(wdiodeumtService.getId()+"");
			if (i1 == 0) {
				LogUtils.ERROR_LOG.error("持久化修改到配置文件失败,回滚数据库");
				if (0 == wdiodeumtServiceMapper.updateWdiodeumtService(oldW)) {
					LogUtils.ERROR_LOG.error("回滚数据库失败");
					return 0;
				}
				LogUtils.ERROR_LOG.error("回滚数据库成功");
				return 0;
			}
			return 1;
		} else {
			LogUtils.ERROR_LOG.error("持久化修改到数据库失败");
			return 0;
		}
	}

	/**
	 * 删除端口注册服务对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteWdiodeumtServiceByIds(String ids) {

		return wdiodeumtServiceMapper.deleteWdiodeumtServiceByIds(Convert.toStrArray(ids));
	}

	@Override
	public int deleteWdiodeumtServiceById(Integer id) {
		WdiodeumtService wdiodeumtService = wdiodeumtServiceMapper.selectWdiodeumtServiceById(id);
		wdiodeumtServiceMService.stop(id + "");
		int i = wdiodeumtServiceMapper.deleteWdiodeumtServiceById(id);
		if (i == 1) {
			int i1 = persistConfig();
			if (i1 == 0) {
				LogUtils.ERROR_LOG.error("持久化修改到配置文件失败,回滚数据库");
				if (0 == wdiodeumtServiceMapper.insertWdiodeumtService(wdiodeumtService)) {
					LogUtils.ERROR_LOG.error("回滚数据库失败");
					return 0;
				}
				LogUtils.ERROR_LOG.error("回滚数据库成功");
				return 0;
			}
			return 1;
		} else {
			LogUtils.ERROR_LOG.error("持久化修改到数据库失败");
			return 0;
		}
	}

	@Override
	public int selectWdiodeumtServiceByServiceName(String serviceName) {
		WdiodeumtService wdiodeumtService = wdiodeumtServiceMapper.selectWdiodeumtServiceByServiceName(serviceName);
		if (wdiodeumtService == null) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int selectWdiodeumtServiceByWid(String wid) {
		List<WdiodeumtService> wdiodeumtServices = wdiodeumtServiceMapper.selectWdiodeumtServiceByWid(wid);
		if (wdiodeumtServices.size() == 0) {
			return 0;
		} else {
			return 1;
		}

	}

	/**
	 * @param port
	 * @return 返回1表示本地端口没有被占用可以使用
	 */
	@Override
	public int checkPort(String port) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(Integer.parseInt(port));
		} catch (IOException e) {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (Exception ignored) {
			}
			return 0;
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("测试远程端口是否可用时，关闭socket出错");

		}
		return 1;
	}

	/**
	 * @param remoteIp
	 * @param remotePort
	 * @return 返回1表示远程端口没有被占用可以使用
	 */
	@Override
	public int checkRemote(String remoteIp, String remotePort) {
		
		try {
			boolean reachable = InetAddress.getByName(remoteIp).isReachable(1500);
			if (!reachable) {
				LogUtils.ERROR_LOG.error("IP地址不可用");
				return 0;
			}
		} catch (IOException e) {
			LogUtils.ERROR_LOG.error("IP地址不可用");
			return 0;
		}
		
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(remoteIp, Integer.parseInt(remotePort)), 1500);

		} catch (IOException e) {
			// e.printStackTrace();
			LogUtils.ERROR_LOG.error("远程端口未被占用");
			return 1;
		}finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (Exception ignored) {LogUtils.ERROR_LOG.error("测试远程端口是否可用时，关闭socket出错");}
		}
		LogUtils.ERROR_LOG.error("连接成功，端口不可用");
		return 0;
	}

	/*
	 * @Override public int fileSync(String password) { try { if
	 * (InetAddress.getByName(ipAddr).isReachable(1500)) { Connection conn = new
	 * Connection(ipAddr); conn.connect(); if
	 * (conn.authenticateWithPassword(userName, password)) { SCPClient scpClient =
	 * conn.createSCPClient(); scpClient.put(wdiodeumtServiceConfigPath, fileFerry);
	 * conn.close(); } else { LogUtils.ERROR_LOG.error("连接远程服务器失败"); return 0; } } }
	 * catch (IOException e) { e.printStackTrace(); } return 0; }
	 */

	// 更新之后先别提交了，等上个版本通过了再提交吧，，如果上个版本有问题就先从GitHub上面再clone一个项目进行修改
	@Override
	public AjaxResult fileSync() {
		String fileName = wdiodeumtServiceConfigPath.substring(wdiodeumtServiceConfigPath.lastIndexOf("/"));
//		String filePath = configService.selectConfigByKey("sys.file.directory") + "c/" + fileName + ".txt";
		String filePath = getWdiodeConf("c_file_dir") + "/" + fileName + ".txt";
		if (StringUtils.isEmpty(configService.selectConfigByKey("sys.file.directory"))) {
			return AjaxResult.error("数据库 [sys.file.directory] 缺失！");
		}
        File src = new File(wdiodeumtServiceConfigPath);
        File dest = new File(filePath);

        if (!dest.exists()) {
            try {
                dest.createNewFile();
            } catch (IOException e) {
                LogUtils.ERROR_LOG.error("创建文件失败");
                return AjaxResult.error("创建此配置文件失败");
            }
        }
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(src).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("文件同步失败！");
        } finally {
            try {
                if (inputChannel != null) {
                    inputChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputChannel != null) {
                    outputChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //src.delete();
        }
        return AjaxResult.success("文件同步成功！");
    }

	public boolean searchFile() {
		File file = new File(wdiodeumtServiceConfigPath);
		return file.exists();
	}

	public String getWdiodeConf(String name) {
		File src = new File(wdiodeConfigPath);
		String line = null, path = null;
		// 将配置文件内容持久化到数据库中
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(src));
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().startsWith(name) ) {
					path = line.trim().split("=")[1];
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error("读取本地配置文件失败-->" + e.getMessage());
			return null;
		}
		return path;
	}
	public boolean updateFile() {
//        File src = new File(ferryDirMapper.selectFerryDirList(null).get(0).getFileFerry() + fileName);
		File src = new File(wdiodeumtServiceConfigPath);
		List<WdiodeumtService> wdiodeumtServices = new ArrayList<>();
		// 将配置文件内容持久化到数据库中
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(src));
			WdiodeumtService wdiodeumtService = new WdiodeumtService();
			int flag = 0;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				flag++;
				if (line.startsWith("[") && line.endsWith("]")) {
					wdiodeumtService.setServiceName(line.substring(1, line.length() - 1));
				} else if (line.startsWith("#description")) {
					wdiodeumtService.setDescription(line.split("#description:")[1]);
					flag--;
				} else if (line.startsWith("wid=")) {
					wdiodeumtService.setWid(line.split("wid=")[1]);
				} else if (line.startsWith("wiport=")) {
					wdiodeumtService.setWiport(line.split("wiport=")[1]);
				} else if (line.startsWith("woport=")) {
					wdiodeumtService.setWoport(line.split("woport=")[1]);
				} else if (line.startsWith("protocol=")) {
					wdiodeumtService.setProtocol(line.split("protocol=")[1]);
				} else if (line.startsWith("allow_ip=")) {
					wdiodeumtService.setAllowIp(line.split("allow_ip=")[1]);
				} else if (line.startsWith("in_parmsa=")) {
					wdiodeumtService.setInParmsa(line.split("in_parmsa=")[1]);
				} else if (line.startsWith("in_parmsb=")) {
					wdiodeumtService.setInParmsb(line.split("in_parmsb=")[1]);
				} else if (line.startsWith("out_parms=")) {
					wdiodeumtService.setOutParms(line.split("out_parms=")[1]);
				} else if (line.startsWith("log_enable=")) {
					wdiodeumtService.setLogEnable(line.split("log_enable=")[1]);
				} else if (line.startsWith("logfile=")) {
					wdiodeumtService.setLogfile(line.split("logfile=")[1]);
				} else if (line.startsWith("is_enable=")) {
					wdiodeumtService.setIsEnable(line.split("is_enable=")[1]);
				} else
					flag--;

				if (flag == 12) {
					flag = 0;
					WdiodeumtService wdiodeumtService1 = new WdiodeumtService();
					wdiodeumtService1.setDataTime(new Date(System.currentTimeMillis()));
					wdiodeumtService1.setDescription(wdiodeumtService.getDescription());
					wdiodeumtService1.setServiceName(wdiodeumtService.getServiceName());
					wdiodeumtService1.setWid(wdiodeumtService.getWid());
					wdiodeumtService1.setWiport(wdiodeumtService.getWiport());
					wdiodeumtService1.setWoport(wdiodeumtService.getWoport());
					wdiodeumtService1.setProtocol(wdiodeumtService.getProtocol());
					wdiodeumtService1.setAllowIp(wdiodeumtService.getAllowIp());
					wdiodeumtService1.setInParmsa(wdiodeumtService.getInParmsa());
					wdiodeumtService1.setInParmsb(wdiodeumtService.getInParmsb());
					wdiodeumtService1.setOutParms(wdiodeumtService.getOutParms());
					wdiodeumtService1.setLogEnable(wdiodeumtService.getLogEnable());
					wdiodeumtService1.setLogfile(wdiodeumtService.getLogfile());
					wdiodeumtService1.setIsEnable(wdiodeumtService.getIsEnable());
					wdiodeumtServices.add(wdiodeumtService1);
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error("读取本地配置文件失败-->" + e.getMessage());
			return false;
		}
		insertToDatabase(wdiodeumtServices);
		return true;
	}

	private void insertToDatabase(List<WdiodeumtService> wdiodeumtServices) {
		wdiodeumtServiceMService.stopAll();
		wdiodeumtServiceMapper.truncateTable();
		wdiodeumtServices.forEach(wdiodeumtService -> {
			wdiodeumtServiceMapper.insertWdiodeumtService(wdiodeumtService);
		});
		wdiodeumtServiceMService.startAll();
	}

	@Override
	public AjaxResult uploadToDB() {
		if (searchFile() && updateFile()) {
			return AjaxResult.success("已将本地配置文件同步至数据库！");
		} else {
			return AjaxResult.error("本地文件不存在或读取相应的配置文件失败");
		}
	}

	public int persistConfig() {
		List<WdiodeumtService> wdiodeumtServices = selectWdiodeumtServiceList(new WdiodeumtService());
		StringBuilder stringBuilder = new StringBuilder();
		wdiodeumtServices.forEach(wdiodeumtService -> {
			stringBuilder.append("[").append(wdiodeumtService.getServiceName()).append("]").append("\n")
					.append("#description:").append(wdiodeumtService.getDescription()).append("\n").append("wid=")
					.append(wdiodeumtService.getWid()).append("\n").append("wiport=")
					.append(wdiodeumtService.getWiport()).append("\n").append("woport=")
					.append(wdiodeumtService.getWoport()).append("\n").append("protocol=")
					.append(wdiodeumtService.getProtocol()).append("\n").append("allow_ip=")
					.append(wdiodeumtService.getAllowIp()).append("\n").append("in_parmsa=")
					.append(wdiodeumtService.getInParmsa()).append("\n").append("in_parmsb=")
					.append(wdiodeumtService.getInParmsb()).append("\n").append("out_parms=")
					.append(wdiodeumtService.getOutParms()).append("\n").append("log_enable=")
					.append(wdiodeumtService.getLogEnable()).append("\n").append("logfile=")
					.append(wdiodeumtService.getLogfile()).append("\n").append("is_enable=")
					.append(wdiodeumtService.getIsEnable()).append("\n");
		});
		File file = new File(wdiodeumtServiceConfigPath);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
			fileOutputStream.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private WdiodeumtService oldWdiodeumtService(WdiodeumtService oldWdiodeumtService) {
		WdiodeumtService wdiodeumtService = new WdiodeumtService();
		wdiodeumtService.setId(oldWdiodeumtService.getId());
		wdiodeumtService.setDescription(oldWdiodeumtService.getDescription());
		wdiodeumtService.setDataTime(oldWdiodeumtService.getDataTime());
		wdiodeumtService.setServiceName(oldWdiodeumtService.getServiceName());
		wdiodeumtService.setWid(oldWdiodeumtService.getWid());
		wdiodeumtService.setWiport(oldWdiodeumtService.getWiport());
		wdiodeumtService.setWoport(oldWdiodeumtService.getWoport());
		wdiodeumtService.setProtocol(oldWdiodeumtService.getProtocol());
		wdiodeumtService.setAllowIp(oldWdiodeumtService.getAllowIp());
		wdiodeumtService.setInParmsa(oldWdiodeumtService.getInParmsa());
		wdiodeumtService.setInParmsb(oldWdiodeumtService.getInParmsb());
		wdiodeumtService.setOutParms(oldWdiodeumtService.getOutParms());
		wdiodeumtService.setLogEnable(oldWdiodeumtService.getLogEnable());
		wdiodeumtService.setLogfile(oldWdiodeumtService.getLogfile());
		wdiodeumtService.setIsEnable(oldWdiodeumtService.getIsEnable());
		return wdiodeumtService;
	}
}
