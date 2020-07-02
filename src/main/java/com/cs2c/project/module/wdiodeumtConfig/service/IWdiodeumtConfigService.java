package com.cs2c.project.module.wdiodeumtConfig.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.wdiodeumtConfig.domain.EditViewWdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.domain.ViewWdiodeumtConfig;
import com.cs2c.project.module.wdiodeumtConfig.domain.WdiodeumtConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 端口转发配置文件 服务层
 * 
 * @author Joenas
 * @date 2020-03-05
 */
public interface IWdiodeumtConfigService 
{
	/**
     * 查询端口转发配置文件信息
     * 
     * @param id 端口转发配置文件ID
     * @return 端口转发配置文件信息
     */
	public WdiodeumtConfig selectWdiodeumtConfigById(Integer id);
	
	/**
     * 查询端口转发配置文件列表
     * 
     * @param number 端口转发配置文件信息
     * @return 端口转发配置文件集合
     */
		public List<WdiodeumtConfig> selectMulWdiodeumtConfigList(Integer number);
	
	/**
     * 新增端口转发配置文件
     * 
     * @param wdiodeumtConfig 端口转发配置文件信息
     * @return 结果
     */
	public int insertWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig);
	
	/**
     * 修改端口转发配置文件
     * 
     * @param wdiodeumtConfig 端口转发配置文件信息
     * @return 结果
     */
	public int updateWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig);
		
	/**
     * 删除端口转发配置文件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteWdiodeumtConfigByIds(String ids);

	/**
	 * 查询wdiodeumt配置列表
	 *
	 * @param viewWdiodeumtConfig wdiodeumt配置信息
	 * @return wdiode配置集合
	 */
	public List<ViewWdiodeumtConfig> selectWdiodeumtConfigList(ViewWdiodeumtConfig viewWdiodeumtConfig);

	/**
	 * 查询wdiode配置列表
	 *
	 * @param viewWdiodeumtConfig wdiode配置信息
	 * @param  id 主键信息
	 * @return wdiode配置集合
	 */
	public List<ViewWdiodeumtConfig> selectWdiodeumtConfigList(ViewWdiodeumtConfig viewWdiodeumtConfig, Integer id);

	/**
	 * 修改wdiode配置
	 *
	 * @param editViewWdiodeumtConfig wdiode配置信息
	 * @return 结果
	 */
	public int updateWdiodeumtConfig(EditViewWdiodeumtConfig editViewWdiodeumtConfig, String reversion);

	/**
	 * 回滚配置
	 *
	 * @param id
	 * @return 结果
	 */
	public AjaxResult reversion(Integer id);

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
	public EditViewWdiodeumtConfig selectWdiodeumtConfigByKey(String w_key);


	/**
	 *
	 * @param reversion
	 * @return
	 */
	public String checkComment(String comment, String reversion);
	public AjaxResult downloadF();

    AjaxResult fileSync();

    AjaxResult uploadToDB();
}
