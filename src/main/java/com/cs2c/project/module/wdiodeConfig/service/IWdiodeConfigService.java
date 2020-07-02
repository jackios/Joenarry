package com.cs2c.project.module.wdiodeConfig.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.wdiodeConfig.domain.EditViewWdiodeConfig;
import com.cs2c.project.module.wdiodeConfig.domain.ViewWdiodeConfig;
import com.cs2c.project.module.wdiodeConfig.domain.WdiodeConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * wdiode配置 服务层
 * 
 * @author Joenas
 * @date 2018-10-14
 */
public interface IWdiodeConfigService 
{
	/**
     * 查询wdiode配置信息
     * 
     * @param id wdiode配置ID
     * @return wdiode配置信息
     */
	public WdiodeConfig selectWdiodeConfigById(Integer id);
	
	/**
     * 查询wdiode配置列表
     * 
     * @param viewWdiodeConfig wdiode配置信息
     * @return wdiode配置集合
     */
	public List<ViewWdiodeConfig> selectWdiodeConfigList(ViewWdiodeConfig viewWdiodeConfig);

	/**
	 * 查询wdiode配置列表
	 *
	 * @param viewWdiodeConfig wdiode配置信息
	 * @param  id 主键信息
	 * @return wdiode配置集合
	 */
	public List<ViewWdiodeConfig> selectWdiodeConfigList(ViewWdiodeConfig viewWdiodeConfig, Integer id);

	/**
     * 修改wdiode配置
     * 
     * @param editViewWdiodeConfig wdiode配置信息
     * @return 结果
     */
	public int updateWdiodeConfig(EditViewWdiodeConfig editViewWdiodeConfig, String reversion);

	/**
	 * 回滚配置
	 *
	 * @param id
	 * @return 结果
	 */
	public int reversion(Integer id);

	/**
	 * 还原配置
	 *
	 * @return 结果
	 */
	public int reinit();

	/**
	 * 导出配置文件
	 *
	 * @param id
	 * @return 结果
	 */
	public AjaxResult export(Integer id);

	/**
	 * 上传配置文件
	 *
	 * @param file
	 * @return 结果
	 */
	public AjaxResult upload(MultipartFile file);

	/**
	 * 根据键查询实际信息
	 * @param w_key
	 * @return
	 */
	public EditViewWdiodeConfig selectWdiodeConfigByKey(String w_key);


	/**
	 *
	 * @param reversion
	 * @return
	 */
	public String checkComment(String comment, String reversion);

	/**
	 * 查询任意数量的历史版本
	 * @param number
	 * @return
	 */
	public List<WdiodeConfig> selectMulWdiodeConfigList(Integer number);

	/**
	 * 删除wdiode配置信息
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteWdiodeConfigByIds(String ids);
	
	public AjaxResult downloadF();
}
