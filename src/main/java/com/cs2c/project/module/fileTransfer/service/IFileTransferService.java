package com.cs2c.project.module.fileTransfer.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.fileTransfer.domain.FileTransfer;
import java.util.List;

/**
 * 文件传输查询 服务层
 * 
 * @author Joenas
 * @date 2020-04-21
 */
public interface IFileTransferService 
{
	/**
     * 查询文件传输查询信息
     * 
     * @param id 文件传输查询ID
     * @return 文件传输查询信息
     */
	public FileTransfer selectFileTransferById(Long id);
	
	/**
     * 查询文件传输查询列表
     * 
     * @param fileTransfer 文件传输查询信息
     * @return 文件传输查询集合
     */
	public List<FileTransfer> selectFileTransferList(FileTransfer fileTransfer, String searchValue);
	
	/**
     * 新增文件传输查询
     * 
     * @param fileTransfer 文件传输查询信息
     * @return 结果
     */
	public int insertFileTransfer(FileTransfer fileTransfer);
	
	/**
     * 修改文件传输查询
     * 
     * @param fileTransfer 文件传输查询信息
     * @return 结果
     */
	public int updateFileTransfer(FileTransfer fileTransfer);
		
	/**
     * 删除文件传输查询信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFileTransferByIds(String ids);

	/**
	 * 按年搜索
	 * 
	 * @param fileTransfer 年份数据
	 * @return 搜索结果列表
	 */
	public String selectFileTransferbyDate(String dir, String Year, String Month, String Date);
	public String selectFileTransferbyMonth(String dir, String Year, String Month);
	public String selectFileTransferbyYear(String dir, String Year);
	
	public AjaxResult test();
	
}
