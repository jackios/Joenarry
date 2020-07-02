package com.cs2c.project.module.fileTransfer.mapper;

import com.cs2c.project.module.fileTransfer.domain.FileTransfer;
import java.util.List;

import org.apache.ibatis.annotations.Param;	

/**
 * 文件传输查询 数据层
 * 
 * @author Joenas
 * @date 2020-04-21
 */
public interface FileTransferMapper 
{
	/**
     * 查询文件传输查询信息
     * 
     * @param id 文件传输查询ID
     * @return 文件传输查询信息
     */
	public FileTransfer selectFileTransferById(Long id);
	
	
	/**
     * 查询文件传输查询信息
     * 
     * @param id 文件传输查询ID
     * @return 文件传输查询信息
     */
	public int selectFileTransferByYear(@Param("year")String year, @Param("month")String month, 
			@Param("day")String day, @Param("hour")String hour, @Param("dir")String dir);
	
	/**
     * 查询文件传输查询列表
     * 
     * @param fileTransfer 文件传输查询信息
     * @return 文件传输查询集合
     */
	public List<FileTransfer> selectFileTransferList(FileTransfer fileTransfer);
	
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
     * 删除文件传输查询
     * 
     * @param id 文件传输查询ID
     * @return 结果
     */
	public int deleteFileTransferById(Long id);
	
	/**
     * 批量删除文件传输查询
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFileTransferByIds(String[] ids);
	
}