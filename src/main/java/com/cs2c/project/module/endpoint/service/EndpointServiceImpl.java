package com.cs2c.project.module.endpoint.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import com.cs2c.project.system.menu.domain.Menu;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.exec.CommandLine;
import org.omg.CORBA.FloatSeqHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.endpoint.mapper.EndpointMapper;
import com.cs2c.project.module.endpoint.domain.Endpoint;
import com.cs2c.project.module.endpoint.service.IEndpointService;
import com.cs2c.common.support.Convert;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 端点 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-28
 */
@Service
public class EndpointServiceImpl implements IEndpointService 
{
	@Autowired
	private ICommandInfoService commandInfoService;

	@Autowired
	private EndpointMapper endpointMapper;

	/**
     * 查询端点信息
     * 
     * @param id 端点ID
     * @return 端点信息
     */
    @Override
	public Endpoint selectEndpointById(Integer id)
	{
	    return endpointMapper.selectEndpointById(id);
	}
	
	/**
     * 查询端点列表
     * 
     * @param endpoint 端点信息
     * @return 端点集合
     */
	@Override
	public List<Endpoint> selectEndpointList(Endpoint endpoint)
	{
	    return endpointMapper.selectEndpointList(endpoint);
	}
	
    /**
     * 新增端点
     * 
     * @param endpoint 端点信息
     * @return 结果
     */
	@Override
	public int insertEndpoint(Endpoint endpoint)
	{
		//SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd");

		endpoint.setCreateBy(ShiroUtils.getLoginName());
		endpoint.setCreateTime(new Date());

		if (!("E".equals(endpoint.getType()) || "N".equals(endpoint.getType()) || "F".equals(endpoint.getType()))) {
			return UserConstants.CHANGE_0_RECORD;
		}

		String endpointPath = getAbsoluteEndpointPath(endpoint);
		Boolean ret = null;
		if ("E".equals(endpoint.getType())) {
			ret = endpoint_control("endpoint_control", "add", endpoint.getType(), endpointPath, "", "");
		} else if ("F".equals(endpoint.getType()) ) {
			ret = endpoint_control("endpoint_control", "add", endpoint.getType(), endpointPath, endpoint.getUsername(), endpoint.getPassword());
		} else {
			ret = endpoint_control("endpoint_control", "add", endpoint.getType(), endpointPath, endpoint.getPerms(), endpoint.getAllows());
		}

		if (ret) {
			return endpointMapper.insertEndpoint(endpoint);
		} else {
			LogUtils.ERROR_LOG.error("新增端点失败，请查看日志确认失败原因, 实例为 " + endpoint);
		}

		return UserConstants.CHANGE_0_RECORD;
	}
	
	/**
     * 修改端点
     * 
     * @param endpoint 端点信息
     * @return 结果
     */
	@Override
	public int updateEndpoint(Endpoint endpoint)
	{
	    endpoint.setUpdateBy(ShiroUtils.getLoginName());
		Endpoint oldEndpoint = endpointMapper.selectEndpointById(endpoint.getId());
		endpoint.setParentId(oldEndpoint.getParentId());

		Boolean ret = null;
		// 基本类型端点 允许更新端点名称
		if ("E".equals(endpoint.getType())) {
			if (! oldEndpoint.getName().trim().equals(endpoint.getName().trim())) {
				String endpointPath = getAbsoluteEndpointPath(endpoint);
				String oldEndpointPath = getAbsoluteEndpointPath(oldEndpoint);

				// update type oldPath newPath
				ret = endpoint_control("endpoint_control", "update", "E", oldEndpointPath, endpointPath, "", "");
			}
		}
		// NFS类型允许更新 主机列表 和 权限
		else if ("N".equals(endpoint.getType())){
			if (! (oldEndpoint.getAllows().trim().equals(endpoint.getAllows()) && oldEndpoint.getPerms().trim().equals(endpoint.getPerms().trim()))) {
				String endpointPath = getAbsoluteEndpointPath(endpoint);
				// update type path perms allows
				ret = endpoint_control("endpoint_control", "update", "N", endpointPath, endpoint.getPerms(), endpoint.getAllows(), "");
			}
		}
		// FTP类型允许更新 密码
		else if ("F".equals(endpoint.getType())) {
			if (!oldEndpoint.getPassword().trim().equals(endpoint.getPassword().trim())) {
				// update type password
				ret = endpoint_control("endpoint_control", "update", "F", endpoint.getUsername(), endpoint.getPassword(), "", "");
			}
		}

		if (ret != null && !ret) {
			LogUtils.ERROR_LOG.error("更新端点失败");
			return UserConstants.CHANGE_0_RECORD;
		} else {
			return endpointMapper.updateEndpoint(endpoint);
		}
	}

	/**
     * 删除端点对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEndpointByIds(String ids)
	{
		Boolean ret = null;

		Integer id = Convert.toIntArray(ids)[0];

		Endpoint endpoint = endpointMapper.selectEndpointById(id);
		String endpointPath = getAbsoluteEndpointPath(endpoint);

		if ("E".equals(endpoint.getType())) {
			ret = endpoint_control("endpoint_control", "delete", endpoint.getType(), endpointPath, "", "");
		} else if ("F".equals(endpoint.getType()) ) {
			ret = endpoint_control("endpoint_control", "delete", endpoint.getType(), endpointPath, endpoint.getUsername(), endpoint.getPassword());
		} else {
			ret = endpoint_control("endpoint_control", "delete", endpoint.getType(), endpointPath, endpoint.getPerms(), endpoint.getAllows());
		}

		if (ret) {
			return endpointMapper.deleteEndpointByIds(Convert.toStrArray(ids));
		} else {
			LogUtils.ERROR_LOG.error("删除端点失败，请查看日志确认失败原因, 实例为 " + endpoint);
		}

		return UserConstants.CHANGE_0_RECORD;
	}

	/**
	 * 查询子菜单数量
	 *
	 * @param parentId 菜单ID
	 * @return 结果
	 */
	@Override
	public int selectCountEndpointByParentId(Integer parentId)
	{
		return endpointMapper.selectCountEndpointByParentId(parentId);
	}

	/**
	 * 校验菜单名称是否唯一
	 *
	 * @param endpoint 菜单信息
	 * @return 结果
	 */
	@Override
	public String checkEndpointNameUnique(Endpoint endpoint)
	{
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
		if (endpoint.getName().contains(" ") || !pattern.matcher(endpoint.getName()).find()) {
			return UserConstants.MENU_NAME_NOT_UNIQUE;
		}

		int id = StringUtils.isNull(endpoint.getId()) ? -1 : endpoint.getId();
		Endpoint info = endpointMapper.checkEndpointNameUnique(endpoint.getName(), endpoint.getParentId());

		if (StringUtils.isNotNull(info) && info.getId().intValue() != id)
		{
			return UserConstants.MENU_NAME_NOT_UNIQUE;
		}
		return UserConstants.MENU_NAME_UNIQUE;
	}

	/**
	 * 校验用户名是否唯一
	 *
	 * @param username 菜单信息
	 * @return 结果
	 */
	@Override
	public String checkUserNameUnique(String username)
	{
		if (username.contains(" ")) {
			return UserConstants.MENU_NAME_NOT_UNIQUE;
		}

		Boolean ret = endpoint_control("endpoint_control", "check", username, "", "", "", "");

		if (!ret)
		{
			return UserConstants.MENU_NAME_NOT_UNIQUE;
		}
		return UserConstants.MENU_NAME_UNIQUE;
	}

//	private String getAbsoluteEndpointPath(Endpoint endpoint) {
//		List<Endpoint> allDatas = endpointMapper.selectEndpointList(new Endpoint());
//
//		Map<Integer, Endpoint> allDataMaps = convertListToMap(allDatas);
//
//		StringBuilder sb = new StringBuilder(endpoint.getName()).append(File.separator);
//		Endpoint tmpInst = null;
//		int MaxDepth = 20;
//
//		int parentId = endpoint.getParentId();
//		while (MaxDepth < 20) {
//			if (parentId == 0) { // 0 => 根目录
//				break;
//			}
//
//			tmpInst = allDataMaps.get(parentId);
//			sb.append(tmpInst.getName()).append(File.separator);
//
//			parentId = tmpInst.getParentId();
//			MaxDepth--;
//		}
//
//		return sb.toString();
//	}

	private String getAbsoluteEndpointPath(Endpoint endpoint) {
		List<Endpoint> allDatas = endpointMapper.selectEndpointList(new Endpoint());

		Map<Integer, Endpoint> allDataMaps = convertListToMap(allDatas);

		String[] paths = new String[20];
		int index = 0;
		paths[index++] = endpoint.getName();
		Endpoint tmpInst = null;
		int MaxDepth = 20;

		if (endpoint.getParentId() == 0) {
			return endpoint.getName() + File.separator;
		}

		int parentId = endpoint.getParentId();

		while (MaxDepth > 0) {
			tmpInst = allDataMaps.get(parentId);

			if (tmpInst == null )
				break;

			paths[index++] = tmpInst.getName();

			if (parentId == 0) { // 0 => 根目录
				break;
			}

			parentId = tmpInst.getParentId();
			MaxDepth--;
		}

		StringBuilder sb = new StringBuilder();
		while (--index >= 0) {
			sb.append(paths[index].trim()).append(File.separator);
		}
		return sb.toString();
	}

	private Map<Integer, Endpoint> 	convertListToMap(List<Endpoint> allDatas) {
		Map<Integer, Endpoint> result = new HashMap<>();

		for (Endpoint instance : allDatas) {
			result.put(instance.getId(), instance);
		}

		return result;
	}


	/**
	 * 新增删除使用
	 * @param commandKey
	 * @param oper
	 * @param type
	 * @param endpoint
	 * @param usernameOrPerms
	 * @param passwdOrAllows
	 * @return
	 */
	private  Boolean endpoint_control(String commandKey, String oper, String type, String endpoint, String usernameOrPerms, String passwdOrAllows) {
		if ("E".equals(type)) {
			return endpoint_control(commandKey, oper, "Z", type, endpoint, "", "");
		} else {
			return endpoint_control(commandKey, oper, "Z", type, endpoint, usernameOrPerms, passwdOrAllows);
		}
	}
	/**
	 * 端点管理
	 *
	 * @param commandKey
	 * @param oper
	 * @param type 端点类型
	 * @param endpoint
	 * @return
	 */
	private Boolean endpoint_control(String commandKey, String oper, String currentType, String type, String endpoint, String usernameOrPerms, String passwdOrAllows) {
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
					.append(UserConstants.SPACE_ONE).append(currentType)
					.append(UserConstants.SPACE_ONE).append(type)
					.append(UserConstants.SPACE_ONE).append("\"" + endpoint + "\"")
					.append(UserConstants.SPACE_ONE).append(usernameOrPerms)
					.append(UserConstants.SPACE_ONE).append(passwdOrAllows);

			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
					return true;
				} else {
					LogUtils.ERROR_LOG.error("执行端点操作出错，命令 " + command.toString() + " 时出错\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("执行端点操作失败，命令 " + command.toString() + "失败 ", e);
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在端点控制的command或command信息错误, 获取command时参数为 " + commandKey);
		}
		return false;
	}
	
    @Override
    public String checkAllow(Endpoint endpoint) {
        String ip = endpoint.getAllows();
        int point = 0 , star = 0 ;
        for(int i=0;i<ip.length();i++){
            char ch = ip.charAt(i);
            if( ch == '.') {point++;}
            if( ch == '*') {star++;}
        }
        if( star > point/3) {
            return "1";
        }
        return "0";
    }
	
}
