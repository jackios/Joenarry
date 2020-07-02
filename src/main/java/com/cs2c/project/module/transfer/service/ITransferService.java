package com.cs2c.project.module.transfer.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.transfer.domain.Transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * 传输控制 服务层
 * 
 * @author Joenas
 * @date 2018-10-25
 */
public interface ITransferService 
{

	public ArrayList<Transfer> getTransferList();


	/**
     * 查询传输控制列表
     * 
     * @param transfer 传输控制信息
     * @return 传输控制集合
     */
	public List<Transfer> selectTransferList(Transfer transfer);

	/**
     * 修改传输控制
	 *
	 * @param ports
	 * @param pids
	 * @param operation
	 * @return
	 */
	public AjaxResult changeStatus(String ports, String pids, String operation);
		
	/**
     * 删除传输控制信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTransferByIds(String ids);

	public AjaxResult download(String  path, String filename);

	public AjaxResult genPreview(String dir, String filename);

	public AjaxResult downloadPreview(String  path, String filename);
	
	public AjaxResult downloadF();
}
