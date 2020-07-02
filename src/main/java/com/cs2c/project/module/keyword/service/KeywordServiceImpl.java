package com.cs2c.project.module.keyword.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.StringUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.commandInfo.domain.CommandInfo;
import com.cs2c.project.module.commandInfo.service.ICommandInfoService;
import org.apache.commons.exec.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.keyword.mapper.KeywordMapper;
import com.cs2c.project.module.keyword.domain.Keyword;
import com.cs2c.project.module.keyword.service.IKeywordService;
import com.cs2c.project.system.user.domain.User;
import com.cs2c.common.support.Convert;

/**
 * 关键字 服务层实现
 * 
 * @author Joenas
 * @date 2018-10-13
 */
@Service
public class KeywordServiceImpl implements IKeywordService 
{
	@Autowired
	private KeywordMapper keywordMapper;

	@Autowired
	private ICommandInfoService commandInfoService;

	private codezip codeZip = new codezip();
	
	/**
     * 查询关键字信息
     * 
     * @param id 关键字ID
     * @return 关键字信息
     */
    @Override
	public Keyword selectKeywordById(Integer id)
	{
	    return keywordMapper.selectKeywordById(id);
	}
	
	/**
     * 查询关键字列表
     * 
     * @param keyword 关键字信息
     * @return 关键字集合
     */
	@Override
	public List<Keyword> selectKeywordList(Keyword keyword)
	{
	    return keywordMapper.selectKeywordList(keyword);
	}
	
    /**
     * 新增关键字
     * 
     * @param keyword 关键字信息
     * @return 结果
     */
	@Override
	public int insertKeyword(Keyword keyword)
	{
		if (config_keyword("config_keyword", keyword.getKeyword(), "add") == UserConstants.CHANGE_1_RECORD)
			return keywordMapper.insertKeyword(keyword);

		return UserConstants.CHANGE_0_RECORD;
	}
	
//	/**
//     * 修改关键字
//     *
//     * @param keyword 关键字信息
//     * @return 结果
//     */
//	@Override
//	public int updateKeyword(Keyword keyword)
//	{
//	    return keywordMapper.updateKeyword(keyword);
//	}

	/**
     * 删除关键字对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteKeywordByIds(String ids)
	{
		Keyword keyword = null;
		for (Integer id : Convert.toIntArray(ids)) {
			keyword = selectKeywordById(id);
			if (config_keyword("config_keyword", keyword.getKeyword(), "remove") == UserConstants.CHANGE_1_RECORD)
				continue;

			return UserConstants.CHANGE_0_RECORD;
		}

		return keywordMapper.deleteKeywordByIds(Convert.toStrArray(ids));
	}

	/**
	 * 校验keyword是否已存在
	 */
	@Override
	public String checkKeyword(String keyword)
	{
		Keyword keyword1 = new Keyword();
		keyword1.setKeyword(keyword);

		List<Keyword> keywords = keywordMapper.selectKeywordList(keyword1);

		if (keywords.size() == 0)
			return UserConstants.NORMAL;

		return UserConstants.EXCEPTION;
	}

	private int config_keyword(String commandKey, String keyword, String oper) {
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
					.append(UserConstants.SPACE_ONE).append(keyword);
			CommandLine commandLine = CommandLine.parse(command.toString());

			try {
				Map<String, String> output = ShellUtils.runAndGetOutput(commandLine, jobTimeout, jobBackground);
				String stdout = output.get("stdout");
				String stderr = output.get("stderr");
				if (StringUtils.isEmpty(stderr) && StringUtils.isEmpty(stdout)) {
					return UserConstants.CHANGE_1_RECORD;
				} else {
					LogUtils.ERROR_LOG.error("配置关键字时 " + oper + " 操作出错， 执行命令为 " + command.toString() + "\nSTDERR ===> " + stderr + "\n" + "STROUT ===> " + stdout);
				}
			} catch (IOException e) {
				LogUtils.ERROR_LOG.error("配置关键字时， " + oper + "操作发生异常， 命令为 " + command.toString(), e);
				return UserConstants.CHANGE_0_RECORD;
			}
		} else {
			LogUtils.ERROR_LOG.error("数据库中不存在配置关键字的command或command信息错误, 获取command时参数为 " + commandKey);
		}
		return UserConstants.CHANGE_0_RECORD;
	}

    @Override
    public AjaxResult downloadF() {
        List<Keyword> list = selectKeywordList(new Keyword());
        String stdout = "";
        for(Keyword key : list) {
            stdout += key.getId()+"\t";
            if(key.getKeyword().length() >= 8) stdout +=key.getKeyword()+"\t";
            else stdout +=key.getKeyword()+"\t\t";
            
            stdout += key.getDescription()+"\n";
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "关键词配置-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(stdout, ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);

    }
}
