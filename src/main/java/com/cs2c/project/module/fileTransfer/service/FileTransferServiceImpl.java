package com.cs2c.project.module.fileTransfer.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.aspectj.apache.bcel.generic.IINC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.fileTransfer.mapper.FileTransferMapper;
import com.cs2c.project.module.fileTransfer.domain.FileTransfer;
import com.cs2c.project.module.fileTransfer.service.IFileTransferService;
import com.cs2c.common.support.Convert;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 文件传输查询 服务层实现
 * 
 * @author Joenas
 * @date 2020-04-21
 */
@Service
public class FileTransferServiceImpl implements IFileTransferService {
	@Autowired
	private FileTransferMapper fileTransferMapper;
	
	private String filepath = "/var/wdiode/c";

	/**
	 * 查询文件传输查询信息
	 * 
	 * @param id 文件传输查询ID
	 * @return 文件传输查询信息
	 */
	@Override
	public FileTransfer selectFileTransferById(Long id) {
		return fileTransferMapper.selectFileTransferById(id);
	}

	/**
	 * 查询文件传输查询列表
	 * 
	 * @param fileTransfer 文件传输查询信息
	 * @return 文件传输查询集合
	 */
	@Override
	public List<FileTransfer> selectFileTransferList(FileTransfer fileTransfer, String searchValue) {
		if (searchValue != null && !"null".equals(searchValue.trim()))
			fileTransfer.setName(".*" + searchValue + ".*");
		System.out.println(fileTransfer);
		return fileTransferMapper.selectFileTransferList(fileTransfer);
	}

	/**
	 * 新增文件传输查询
	 * 
	 * @param fileTransfer 文件传输查询信息
	 * @return 结果
	 */
	@Override
	public int insertFileTransfer(FileTransfer fileTransfer) {
		return fileTransferMapper.insertFileTransfer(fileTransfer);
	}

	/**
	 * 修改文件传输查询
	 * 
	 * @param fileTransfer 文件传输查询信息
	 * @return 结果
	 */
	@Override
	public int updateFileTransfer(FileTransfer fileTransfer) {
		return fileTransferMapper.updateFileTransfer(fileTransfer);
	}

	/**
	 * 删除文件传输查询对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteFileTransferByIds(String ids) {
		return fileTransferMapper.deleteFileTransferByIds(Convert.toStrArray(ids));
	}

	@Override
	public String selectFileTransferbyYear(String dir, String Year) {
		int nums ;
		String str = "";
		dir = filepath + dir + ".*";
		for (int k = 1; k <= 12; k++) {
			nums = fileTransferMapper.selectFileTransferByYear(Year, k+"", null, null, dir);
			str +=  "-" + nums;
		}
		str = str.substring(1);

		return str;
	}

	@Override
	public String selectFileTransferbyMonth(String dir, String Year, String Month) {
		int nums, times;
		String str = "";
		dir = filepath + dir + ".*";
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Integer.parseInt(Year), Integer.parseInt(Month), 1);
	    times = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int k = 1; k <= times; k++) {
			nums = fileTransferMapper.selectFileTransferByYear(Year, Month, k+"", null, dir);
			str +=  "-" + nums;
		}
		str = str.substring(1);

		return str;
	}
	@Override
	public String selectFileTransferbyDate(String dir, String Year, String Month, String Date) {
		int nums ;
		String str = "";
		dir = filepath + dir + ".*";
		for (int k = 1; k <= 24; k++) {
			nums = fileTransferMapper.selectFileTransferByYear(Year, Month, Date, k+"", dir);
			str +=  "-" + nums;
		}
		str = str.substring(1);

		return str;
	}

	public AjaxResult test() {
		
		List<FileTransfer> list = null;
		try {
			list = fileTransferMapper.selectFileTransferList(new FileTransfer());
		} catch (Exception e) {
			return AjaxResult.error("链接错误，数据库缺失。");
		}
		String str = list.size() + "";
		List<String> list2 = new ArrayList<String>();
		for (FileTransfer oo : list) {
			if (oo.getDir().startsWith(filepath)) {
				String dir = oo.getDir().substring(filepath.length());
				
				if (!list2.contains(dir) ) {
					list2.add(dir);
				}
			
			}
		}
		list2.remove("/");
		for (String ss : list2) {
			str += "-" + ss;
		}
		// System.out.println(str + list.size());
		return AjaxResult.success(str);
	}
}
