package com.cs2c.project.module.keywordFilter.service;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.keywordFilter.mapper.KeywordFilterMapper;
import com.cs2c.project.module.keywordFilter.domain.KeywordFilter;
import com.cs2c.project.module.keywordFilter.service.IKeywordFilterService;
import com.cs2c.project.system.user.domain.User;
import com.cs2c.common.exception.file.codezip;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.security.ShiroUtils;
import com.cs2c.framework.web.domain.AjaxResult;

/**
 * 关键字过滤记录 服务层实现
 * 
 * @author Joenas
 * @date 2018-11-15
 */
@Service
public class KeywordFilterServiceImpl implements IKeywordFilterService 
{
	@Autowired
	private KeywordFilterMapper keywordFilterMapper;

	private codezip codeZip = new codezip();
	/**
     * 查询关键字过滤记录信息
     * 
     * @param id 关键字过滤记录ID
     * @return 关键字过滤记录信息
     */
    @Override
	public KeywordFilter selectKeywordFilterById(Integer id)
	{
	    return keywordFilterMapper.selectKeywordFilterById(id);
	}
	
	/**
     * 查询关键字过滤记录列表
     * 
     * @param keywordFilter 关键字过滤记录信息
     * @return 关键字过滤记录集合
     */
	@Override
	public List<KeywordFilter> selectKeywordFilterList(KeywordFilter keywordFilter)
	{
	    return keywordFilterMapper.selectKeywordFilterList(keywordFilter);
	}
	
    /**
     * 新增关键字过滤记录
     * 
     * @param keywordFilter 关键字过滤记录信息
     * @return 结果
     */
	@Override
	public int insertKeywordFilter(KeywordFilter keywordFilter)
	{
	    return keywordFilterMapper.insertKeywordFilter(keywordFilter);
	}
	
	/**
     * 修改关键字过滤记录
     * 
     * @param keywordFilter 关键字过滤记录信息
     * @return 结果
     */
	@Override
	public int updateKeywordFilter(KeywordFilter keywordFilter)
	{
	    return keywordFilterMapper.updateKeywordFilter(keywordFilter);
	}

	/**
     * 删除关键字过滤记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteKeywordFilterByIds(String ids)
	{
		return keywordFilterMapper.deleteKeywordFilterByIds(Convert.toStrArray(ids));
	}

    @Override
    public AjaxResult downloadF() {
        List<KeywordFilter> list = selectKeywordFilterList(new KeywordFilter());
        String stdout = "";
        StringBuffer sb = new StringBuffer();
        for(KeywordFilter key : list) {
            
            if(key.getFilename().length() >= 24) sb.append(key.getFilename()+"\t");
            else if(key.getFilename().length() >= 16) sb.append(key.getFilename()+"\t\t");
            else if(key.getFilename().length() >= 8) sb.append(key.getFilename()+"\t\t\t");
            else sb.append(key.getFilename()+"\t\t\t\t");
            
            sb.append( key.getDate()+"\t");
            sb.append( key.getStatus()+"\n");
            
        }
        //System.out.println("stdout :"+stdout);
       
        Calendar cal = Calendar.getInstance(); 
        //System.out.println("Calendar");
        String fname = "过滤查询-"+ (cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".zip";
        //System.out.println(fname);
        byte[] data = codeZip.makeZip(sb.toString(), ShiroUtils.getLoginName());
        //System.out.println(data.length);
        return AjaxResult.success2(fname,data);
    }
	
}
