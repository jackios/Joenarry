package com.cs2c.project.module.serviceReq.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.aspectj.apache.bcel.generic.IINC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.serviceReq.mapper.ServiceReqMapper;
import com.cs2c.project.module.serviceReq.domain.ServiceReq;
import com.cs2c.project.module.serviceReq.service.IServiceReqService;
import com.cs2c.common.support.Convert;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 文件传输查询 服务层实现
 * 
 * @author Joenas
 * @date 2020-04-21
 */
@Service
public class ServiceReqServiceImpl implements IServiceReqService {
	@Autowired
	private ServiceReqMapper serviceReqMapper;
	
	private String filepath = "";

	/**
	 * 查询文件传输查询信息
	 * 
	 * @param id 文件传输查询ID
	 * @return 文件传输查询信息
	 */
	@Override
	public ServiceReq selectServiceReqById(Long id) {
		return serviceReqMapper.selectServiceReqById(id);
	}

	/**
	 * 查询文件传输查询列表
	 * 
	 * @param serviceReq 文件传输查询信息
	 * @return 文件传输查询集合
	 */
	@Override
	public List<ServiceReq> selectServiceReqList(ServiceReq serviceReq, String searchValue) {
		if (searchValue != null && !"null".equals(searchValue.trim()))
			serviceReq.setUrl(".*" + searchValue + ".*");
		System.out.println(serviceReq);
		return serviceReqMapper.selectServiceReqList(serviceReq);
	}

	/**
	 * 新增文件传输查询
	 * 
	 * @param serviceReq 文件传输查询信息
	 * @return 结果
	 */
	@Override
	public int insertServiceReq(ServiceReq serviceReq) {
		return serviceReqMapper.insertServiceReq(serviceReq);
	}

	/**
	 * 修改文件传输查询
	 * 
	 * @param serviceReq 文件传输查询信息
	 * @return 结果
	 */
	@Override
	public int updateServiceReq(ServiceReq serviceReq) {
		return serviceReqMapper.updateServiceReq(serviceReq);
	}

	/**
	 * 删除文件传输查询对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteServiceReqByIds(String ids) {
		return serviceReqMapper.deleteServiceReqByIds(Convert.toStrArray(ids));
	}

	@Override
	public String selectServiceReqbyYear(String dir, String Year) {
		int nums ;
		String str = "";
		dir = filepath + dir + ".*";
		for (int k = 1; k <= 12; k++) {
			nums = serviceReqMapper.selectServiceReqByYear(Year, k+"", null, null, dir);
			str +=  "-" + nums;
		}
		str = str.substring(1);

		return str;
	}

	@Override
	public String selectServiceReqbyMonth(String dir, String Year, String Month) {
		int nums, times;
		String str = "";
		dir = filepath + dir + ".*";
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, Integer.parseInt(Year));
		calendar.set(Calendar.MONTH, Integer.parseInt(Month));
	   	times = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int k = 1; k <= times; k++) {
			nums = serviceReqMapper.selectServiceReqByYear(Year, Month, k+"", null, dir);
			str +=  "-" + nums;
		}
		str = str.substring(1);

		return str;
	}
	@Override
	public String selectServiceReqbyDate(String dir, String Year, String Month, String Date) {
		int nums ;
		String str = "";
		dir = filepath + dir + ".*";
		for (int k = 1; k <= 24; k++) {
			nums = serviceReqMapper.selectServiceReqByYear(Year, Month, Date, k+"", dir);
			str +=  "-" + nums;
		}
		str = str.substring(1);

		return str;
	}

	public AjaxResult test() {
		
		List<ServiceReq> list = null;
		try {
			list = serviceReqMapper.selectServiceReqList(new ServiceReq());
		} catch (Exception e) {
			return AjaxResult.error("链接错误，数据库缺失。");
		}
		String str = list.size() + "";
		List<String> list2 = new ArrayList<String>();
		for (ServiceReq oo : list) {
			if (oo.getHost().startsWith(filepath)) {
				String dir = oo.getHost().substring(filepath.length());
				
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
