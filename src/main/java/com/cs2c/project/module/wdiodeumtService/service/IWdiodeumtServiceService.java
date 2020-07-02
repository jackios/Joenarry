package com.cs2c.project.module.wdiodeumtService.service;

import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.wdiodeumtService.domain.WdiodeumtService;

import java.util.List;

/**
 * 端口注册服务 服务层
 *
 * @author Joenas
 * @date 2020-03-09
 */
public interface IWdiodeumtServiceService {
    /**
     * 查询端口注册服务信息
     *
     * @param id 端口注册服务ID
     * @return 端口注册服务信息
     */
    public WdiodeumtService selectWdiodeumtServiceById(Integer id);

    /**
     * 查询端口注册服务列表
     *
     * @param wdiodeumtService 端口注册服务信息
     * @return 端口注册服务集合
     */
    public List<WdiodeumtService> selectWdiodeumtServiceList(WdiodeumtService wdiodeumtService);

    /**
     * 新增端口注册服务
     *
     * @param wdiodeumtService 端口注册服务信息
     * @return 结果
     */
    public int insertWdiodeumtService(WdiodeumtService wdiodeumtService);

    /**
     * 修改端口注册服务
     *
     * @param wdiodeumtService 端口注册服务信息
     * @return 结果
     */
    public int updateWdiodeumtService(WdiodeumtService wdiodeumtService);

    /**
     * 删除端口注册服务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWdiodeumtServiceByIds(String ids);

    public int deleteWdiodeumtServiceById(Integer id);

    int selectWdiodeumtServiceByServiceName(String serviceName);

    int selectWdiodeumtServiceByWid(String wid);

    int checkPort(String port);

    int checkRemote(String remoteIp, String remotePort);

	AjaxResult fileSync();

    AjaxResult uploadToDB();
}
