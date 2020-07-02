package com.cs2c.project.module.wdiodeumtService.service;

/**
 * 服务管理 服务层
 * 
 * @author Joenas
 * @date 2018-10-23
 */
public interface IWdiodeumtServiceMService
{
	/**
     * 查询服务管理信息
     * 
     * @param id 服务管理ID
     * @return 服务管理信息
     */
	//public ServiceM selectServiceMById(Integer id);
	
	/**
     * 查询服务管理列表
     * 
     * @param serviceM 服务管理信息
     * @return 服务管理集合
     */
	//public List<ServiceM> selectServiceMList(ServiceM serviceM);
	
	/**
     * 新增服务管理
     * 
     * @param serviceM 服务管理信息
     * @return 结果
     */
	//public int insertServiceM(ServiceM serviceM);
	
	/**
     * 修改服务管理
     * 
     * @param serviceM 服务管理信息
     * @return 结果
     */
	//public int updateServiceM(ServiceM serviceM);
		
	/**
     * 删除服务管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	//public int deleteServiceMByIds(String ids);

	public int start(String ids);
	public int stop(String ids);
	public int sysreboot(String ids);
	public int poweroff(String ids);
	public int reboot(String ids);
	public int reload(String ids);
	public int enable(String ids);
	public int disable(String ids);
	public String getServiceCurrentStatus(String cmd, String oper);

	int startAll();

	int stopAll();
}
