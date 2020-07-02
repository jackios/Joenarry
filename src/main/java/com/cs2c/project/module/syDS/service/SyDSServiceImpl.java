package com.cs2c.project.module.syDS.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cs2c.project.module.syDS.mapper.SyDSMapper;
import com.cs2c.project.module.syDS.domain.SyDS;
import com.cs2c.project.module.syDS.service.ISyDSService;
import com.cs2c.project.module.syDS.domain.doSyDS;
import com.alibaba.druid.support.calcite.DDLColumn;
import com.cs2c.common.constant.UserConstants;
import com.cs2c.common.support.Convert;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.os.ShellUtils;
import com.cs2c.framework.web.domain.AjaxResult;
import com.cs2c.project.module.wdiodeConfig.service.WdiodeConfigServiceImpl;

import java.net.Socket;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * DS设置 服务层实现
 * 
 * @author Joenas
 * @date 2019-01-03
 */
@Service
public class SyDSServiceImpl implements ISyDSService 
{
	@Autowired
	private SyDSMapper syDSMapper;
	@Autowired
	private WdiodeConfigServiceImpl WdiodeConfigService;
	
	String syDS_confige_path = new String("/etc/");
    //@Autowired
    Map<Integer ,doSyDS> DS = new HashMap<Integer, doSyDS>();
	/**
     * 查询DS设置信息
     * 
     * @param id DS设置ID
     * @return DS设置信息
     */
    @Override
	public SyDS selectSyDSById(Integer id)
	{
	    return syDSMapper.selectSyDSById(id);
	}
	
	/**
     * 查询DS设置列表
     * 
     * @param syDS DS设置信息
     * @return DS设置集合
     */
	@Override
	public List<SyDS> selectSyDSList(SyDS syDS)
	{
	    return syDSMapper.selectSyDSList(syDS);
	}
	
    /**
     * 新增DS设置
     * 
     * @param syDS DS设置信息
     * @return 结果
     */
	@Override
    public AjaxResult insertSyDS(SyDS syDS)
    {
	    if(syDS.getDontResend() == null)
	        syDS.setDontResend("off");

        if(syDS.getGroupId().equals("tcn")) {syDS.setExternalId(1);}
        else if(syDS.getGroupId().equals("rsw")){syDS.setExternalId(0);}
        //if(syDSMapper.selectSyDSByGroupId(syDS.getGroupId()) != null) {syDSMapper.deleteSyDSByGroupId(syDS.getGroupId());}
        if(syDS.getRegistrationUrl() == null) {syDS.setRegistrationUrl(WdiodeConfigService.selectWdiodeConfigByKey("local_ip").getW_value());;}
        if(syDS.getIp() == null) {syDS.setIp("127.0.0.1");}
        if(syDS.getRoutingTime() == null) {syDS.setRoutingTime(5000);}
        if(syDS.getPushTime() == null) {syDS.setPushTime(10000);}
        if(syDS.getDbDriver() == null) {syDS.setDbDriver("com.mysql.jdbc.Driver");}
        syDS.setCol1("无");
        try {
            updateTable(syDS);
        }catch(SQLException e){
            System.out.println("bed end 1");
            e.printStackTrace();
            return AjaxResult.error("数据库无法链接，检查配置文件");
        }catch(ClassNotFoundException e) {
            System.out.println("bed end 2");
            return AjaxResult.error("数据库驱动错误，检查驱动是否安装正确");
        } catch (FileNotFoundException e) {
            LogUtils.ERROR_LOG.error("创建properties配置文件描述符时， 发生异常, 参数为 " + syDS, e);
            return AjaxResult.error("创建properties配置文件描述符时发生异常");
        } catch (IOException e) {
            LogUtils.ERROR_LOG.error("写入properties配置时， 发生异常, 参数为 " + syDS, e);
            return AjaxResult.error("写入properties配置时发生异常");
        } catch (Exception e) {
            LogUtils.ERROR_LOG.error("数据库表格式错误, 参数为 " + syDS, e);
            return AjaxResult.error("数据库表格式错误");
        }
        
        return syDSMapper.insertSyDS(syDS) > 0 ? AjaxResult.success() : AjaxResult.error();
    }
	
	
	public void updateTable(SyDS syDS) throws Exception {
	    
	    build_properties(syDS, 0);

	    String engine = "store-001";
        if (syDS.getGroupId().equals("tcn")) {engine = "store-001";}
        else if(syDS.getGroupId().equals("rsw")) {engine = "corp-000";}
        
	    String command = "/usr/sbin/syDS/bin/symadmin --engine " + engine  + " create-sym-tables";

	    System.out.println("command:"+command);
        
	    CommandLine commandLine = CommandLine.parse(command);
	    String stderr= null , stdout = null;
	    try {
	        Map<String, String> map = ShellUtils.runAndGetOutput(commandLine, 60*1000*5, false);
	        stderr = map.get("stderr");
	        stdout = map.get("stdout");
	    } catch (IOException e) {
	    } finally {
	    }
	    //System.out.println("stdout :"+stdout);
	    //System.out.println("stderr :"+stderr);
	    updateUrl(syDS);
	}
	
	/**
     * 修改DS设置
     * 
     * @param syDS DS设置信息
     * @return 结果
     */
	@Override
	public AjaxResult updateSyDS(SyDS syDS)
	{
	    SyDS OsyDS = syDSMapper.selectSyDSById(syDS.getId());
        stop_system(OsyDS);
        
        if(syDS.getRegistrationUrl() == null) {syDS.setRegistrationUrl(WdiodeConfigService.selectWdiodeConfigByKey("local_ip").getW_value());;}
        if(syDS.getIp() == null) {syDS.setIp("127.0.0.1");}
        if(syDS.getRoutingTime() == null) {syDS.setRoutingTime(5000);}
        if(syDS.getPushTime() == null) {syDS.setPushTime(10000);}
	    syDS.setCol1("无");
        try {
            updateTable(syDS);
        }catch(SQLException e){
            System.out.println("bed end 1");
            e.printStackTrace();
            return AjaxResult.error("数据库无法链接，检查配置文件");
        }catch(ClassNotFoundException e) {
            System.out.println("bed end 2");
            return AjaxResult.error("数据库驱动错误，检查驱动是否安装正确");
        } catch (FileNotFoundException e) {
            LogUtils.ERROR_LOG.error("创建properties配置文件描述符时， 发生异常, 参数为 " + syDS, e);
            return AjaxResult.error("创建properties配置文件描述符时发生异常");
        } catch (IOException e) {
            LogUtils.ERROR_LOG.error("写入properties配置时， 发生异常, 参数为 " + syDS, e);
            return AjaxResult.error("写入properties配置时发生异常");
        } catch (Exception e) {
            LogUtils.ERROR_LOG.error("数据库表格式错误, 参数为 " + syDS, e);
            return AjaxResult.error("数据库表格式错误");
        }
        
        return syDSMapper.updateSyDS(syDS) > 0 ? AjaxResult.success() : AjaxResult.error();
        
	}

	/**
     * 删除DS设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
    public int deleteSyDSByIds(String ids)
    {
	    String[] Ids = ids.split(",");
        for(String id:Ids) {
            SyDS syDS = syDSMapper.selectSyDSById(Integer.valueOf(id));
            stop_system(syDS);
        }
        return syDSMapper.deleteSyDSByIds(Convert.toStrArray(ids));
    }
    
    public void test_properties(SyDS syDS) throws SQLException, ClassNotFoundException {
        
        Connection conn = null;
        String url = "jdbc:mysql://"+syDS.getIp()+"/"+syDS.getDataBaseN()+"?useSSL=false";
        Class.forName(syDS.getDbDriver());
        System.out.println("url : "+ url + " username :"+syDS.getUsrn()+"user pw :***");
        conn = DriverManager.getConnection(url,syDS.getUsrn(),syDS.getUsrp());
        conn.close();
        
    }
	
	   /**
     * 删除DS设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public AjaxResult build_properties(SyDS syds) {
        try {
            return build_properties(syds, 1);
        }catch(SQLException e){
            System.out.println("bed end 1");
            return AjaxResult.error("数据库无法链接，检查配置文件");
        }catch(ClassNotFoundException e) {
            System.out.println("bed end 2");
            return AjaxResult.error("数据库驱动错误，检查驱动是否安装正确");
        } catch (FileNotFoundException e) {
            LogUtils.ERROR_LOG.error("创建properties配置文件描述符时， 发生异常, 参数为 " + syds, e);
            return AjaxResult.error("创建properties配置文件描述符时发生异常");
        } catch (IOException e) {
            LogUtils.ERROR_LOG.error("写入properties配置时， 发生异常, 参数为 " + syds, e);
            return AjaxResult.error("写入properties配置时发生异常");
        }
    }
    
	public AjaxResult build_properties (SyDS syDS,int mod) throws ClassNotFoundException, SQLException, IOException
    {
	    if (mod == 1) 
	         syDS= syDSMapper.selectSyDSById(syDS.getId());

	    System.out.println("build start");
        StringBuilder sb = new StringBuilder();
        
        test_properties(syDS);
        
        System.out.println(syDS);
        String user,id;
        if(syDS.getGroupId().equals("tcn")) {user = "store";id = "001";}
        else if (syDS.getGroupId().equals("rsw")) {user = "corp";id = "000";}
        else {
            LogUtils.ERROR_LOG.error("身份异常(为‘tcn’、‘rsw’外的其他值), 身份为 " + syDS.getGroupId(), new IOException());
            return AjaxResult.error("身份异常(为‘tcn’、‘rsw’外的其他值)");
        }

        int port = 8080;
        boolean flag = true; 
        while(flag) {
            try {  
                Socket socket = new Socket("127.0.0.1",port);  //建立一个Socket连接
                socket.close();
            } catch (IOException e) {  flag = false;
            }  finally {
                System.out.println("port at :"+port +" is "+ flag +" inused");
            }
            if(flag) {port++;}  
        }
        sb.append("engine.name=").append(user).append("-").append(id).append(System.getProperty("line.separator"))
                .append("db.driver=").append(syDS.getDbDriver()).append(System.getProperty("line.separator"))
                .append("db.url=jdbc:mysql://").append(syDS.getIp()).append("/")
                .append(syDS.getDataBaseN()).append("?useSSL=false").append(System.getProperty("line.separator"))
                .append("db.user=").append(syDS.getUsrn()).append(System.getProperty("line.separator"))
                .append("db.password=").append(syDS.getUsrp()).append(System.getProperty("line.separator"))
                .append("registration.url=");
        if(syDS.getGroupId().equals("rsw")) {
            sb.append(System.getProperty("line.separator")).append("sync.url=");}
        sb.append("http://").append(syDS.getRegistrationUrl()).append(":").append(port).append("/sync/corp-000").append(System.getProperty("line.separator"))
                .append("group.id=").append(user).append(System.getProperty("line.separator"))
                .append("external.id=").append(id).append(System.getProperty("line.separator"))
                .append("job.routing.period.time.ms=").append(syDS.getRoutingTime()).append(System.getProperty("line.separator"))
                .append("job.push.period.time.ms=").append(syDS.getPushTime()).append(System.getProperty("line.separator"))
                .append("job.pull.period.time.ms=10000").append(System.getProperty("line.separator"))
                .append("is.not.Resend=").append(syDS.getDontResend().equals("on") ? "true" : "false").append(System.getProperty("line.separator"));
        File file = new File(syDS_confige_path+user+"-"+id+".properties");
        System.out.println(sb.toString()+"/n and "+syDS_confige_path+user+"-"+id+".properties");
        
        if (file.exists()) {
            file.delete();
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(syDS_confige_path+user+"-"+id+".properties", true)));

        bw.write(sb.toString());
        bw.newLine();
        bw.flush();
        bw.close();
        syDS.setCol1("否");
        syDSMapper.ResetSyDS(0);
        if(syDS.getId() != null) 
            syDSMapper.updateSyDS(syDS);
        return AjaxResult.success();

    }
	
	public AjaxResult start_system(SyDS syds)
    {
	    SyDS syDS = syDSMapper.selectSyDSById(syds.getId());
        try {
            test_properties(syDSMapper.selectSyDSById(syDS.getId()));
        }catch(SQLException e){
            System.out.println("bed end 1");
            return AjaxResult.error("数据库无法链接，检查配置文件并重新生成");
        }catch(ClassNotFoundException e) {
            System.out.println("bed end 2");
            return AjaxResult.error("数据库驱动错误，检查驱动是否安装正确");
        }
        if(!DS.containsKey(syDS.getId())){
            doSyDS dSD = new doSyDS();
            dSD.setEnginePort(null, null, syDS);
            DS.put(syDS.getId(), dSD);
        }
        doSyDS doSyDS = DS.get(syDS.getId());
        
        File file = new File("/etc/DSpid");
        if (file.exists()) {
            file.delete();
        }
        
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {}

        String engine;
        int port = 8080;
        if (syDS.getGroupId().equals("tcn")) {engine = "store-001";port = 9090;}
        else if(syDS.getGroupId().equals("rsw")) {engine = "corp-000";}
        else{
            LogUtils.ERROR_LOG.error("生成指令时， 发生异常, 参数为 " + syDS);
            return AjaxResult.error("身份错误");
        }
        
        beforstart(syDS);
        
        boolean flag = true; 
        while(flag) {
            try {  
                Socket socket = new Socket("127.0.0.1",port);  //建立一个Socket连接
                
                socket.close();
            } catch (IOException e) {  flag = false;  
            }  finally {
                System.out.println("port at :"+port +" is "+ flag +" inused");
            }
            if(flag) {port++;}  
        }
      
        doSyDS.setEnginePort(engine, String.valueOf(port) , syDS);
        doSyDS.setDaemon(true);
        doSyDS.start();
        int to = 0;
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            file = new File("/etc/DSpid");
            BufferedReader reader;
            String pid = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                pid = reader.readLine();
                reader.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
            if(pid != null) {
                syDS.setCol1(pid);
                file.delete();
                break;
            }
            to ++;
            if (to>=60) {
                doSyDS.interrupt();
                doSyDS = new doSyDS();
                DS.put(syDS.getId(), doSyDS);
                return AjaxResult.error("超时，请检查后重试");
                }
        }
        syDS.setCol2("0");
        syDS.setCol3(getDate(new Date()));
        syDSMapper.updateSyDS(syDS);  
        DS.put(syDS.getId(), doSyDS);
        
        return AjaxResult.success();
    }
	
    public String getDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateString = formatter.format(date);  
        return dateString;
    }
	/*
    public int stop_system(SyDS syDS) 
    {
        if(!DS.getGoing()) {return UserConstants.CHANGE_0_RECORD;}
        try {
        DS.interrupt();
        syDS.setCol1("否");
        syDSMapper.updateSyDS(syDS);
        return UserConstants.CHANGE_1_RECORD;
        }
        catch(SecurityException e)
        {
            LogUtils.ERROR_LOG.error("终止symmetric同步时发生异常, 参数为 ：" +e);
        }
        return UserConstants.CHANGE_0_RECORD;
    }*/
    @Override
    public int sys_init(SyDS syDS) {
        syDSMapper.init(1);
        return UserConstants.CHANGE_1_RECORD;
    }

    @Override
    public AjaxResult stop_system(SyDS syds) {
        SyDS syDS = syDSMapper.selectSyDSById(syds.getId());
        boolean in ;
        try {
                Integer.valueOf(syDS.getCol1());
                in = true;
        } catch (Exception e) {
            in = false;
        }
        if (DS.containsKey(syDS.getId()) || in) {
            if(DS.containsKey(syDS.getId())) {
                doSyDS dSD = DS.get(syDS.getId());
                //dSD.interrupt();
                dSD = new doSyDS();
                DS.put(syDS.getId(), dSD);
            }
            if(in) {
                String command = new String("kill -9 "+syDS.getCol1());
                CommandLine commandLine = CommandLine.parse(command);
                Map<String,String> out = new HashMap<>();
                try {
                    out = ShellUtils.runAndGetOutput(commandLine, 30, false);
                    //ShellResultHandler resultHandler = ShellUtils.run(commandLine, 0, false);
                } catch (IOException e) {
                }
                for (String key : out.keySet()) { 
                    System.out.println(key +" : " +out.get(key));
                  } 
                if (out.get("stderr").trim().startsWith("kill")){
                    File file = new File("/etc/DSpid");
                    if (file.exists()) {
                        BufferedReader reader;
                        String pid = null;
                        try {
                            reader = new BufferedReader(new FileReader(file));
                            pid = reader.readLine();
                            reader.close();
                        } catch (IOException e) {}
                        file.delete();
                        command = new String("kill -9 "+pid);
                        commandLine = CommandLine.parse(command);
                        out = new HashMap<>();
                        try {
                            out = ShellUtils.runAndGetOutput(commandLine, 30, false);
                        } catch (IOException e) {}
                    }else {
                        syDS.setCol1("无");
                        syDS.setCol2(" - ");
                        syDS.setCol3(" - ");
                        syDSMapper.updateSyDS(syDS);
                        return AjaxResult.error("进程丢失，请检查进程是否已提前关闭");
                    }
                }
                
            } 
            syDS.setCol1("无");
            syDS.setCol2(" - ");
            syDS.setCol3(" - ");
            syDSMapper.updateSyDS(syDS);
            return AjaxResult.success();
        }else {
            return AjaxResult.error("关闭错误，可能由于数据混乱");
        }
        
    }
    
    public void beforstart(SyDS syds) {
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(syds.getDbDriver());
            conn = DriverManager.getConnection("jdbc:mysql://"+syds.getIp()+"/"+syds.getDataBaseN(),syds.getUsrn(),syds.getUsrp());
            stmt = conn.createStatement();
            String sql;
            if(syds.getGroupId().equals("tcn")) {
                sql = "delete from sym_node_host where node_id = '001'";
            }else if(syds.getGroupId().equals("rsw")){
                sql = "delete from sym_node_host where node_id = '000'";
            }else {
                return;
            }
            stmt.execute(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
            if(stmt !=null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            } catch (SQLException e) {}
        }
    } 
    
    @Override
    public void upcol2() {

        List<SyDS> SYDS = syDSMapper.selectSyDSing("asd");
        for(SyDS syds:SYDS) {
//            List<SyDS> rows = syds.getGroupId().equals("tcn") ? syDSMapper.upcol2tcn(syds.getCol3()) : syDSMapper.upcol2rsw(syds.getCol3());
//            int row = 0;
//            for (SyDS sy :rows) {
//                row += Integer.parseInt(sy.getCol3()) ;
//            }
            syds = getConn(syds);
            syDSMapper.updateSyDS(syds);
            //System.out.println(syds.getId()+"  row :"+row);
        }
    } 
    public SyDS getConn(SyDS syDS) {
        Connection conn = null;
        Statement stmt = null;
        int ln = 0;
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+syDS.getIp()+"/"+syDS.getDataBaseN()+"?useSSL=false",
                    syDS.getUsrn(),syDS.getUsrp());
            stmt = conn.createStatement();
            String sql;
            sql =  syDS.getGroupId().equals("tcn") ? "select data_row_count from  "+ syDS.getDataBaseN() +".sym_outgoing_batch where "
                    + "channel_id = 'to_corp' and create_time >= "+syDS.getCol3(): 
                        "select load_row_count from "+ syDS.getDataBaseN() +".sym_incoming_batch where "
                                + "channel_id = 'to_corp' and create_time >= "+syDS.getCol3() ;
                    
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            if (syDS.getGroupId().equals("tcn")) {
                while (rs.next()) {ln += rs.getInt("data_row_count");}
            }else {
                while (rs.next()) {ln += rs.getInt("load_row_count");}
            }
            
            syDS.setCol2(ln+"");
            stmt.close();
            conn.close();
        }catch(SQLException se){
            
        }catch(Exception e){
        }
        return syDS;
    }
    
    public void updateUrl(SyDS syDS) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:mysql://"+syDS.getIp()+"/"+syDS.getDataBaseN()+"?useSSL=false";
        String tables = syDS.getDataBaseTableN().trim();
        String[] tableList = tables.split(",");
        
        Class.forName(syDS.getDbDriver());
        System.out.println("url : "+ url + " username :"+syDS.getUsrn()+"user pw :***");
        conn = DriverManager.getConnection(url,syDS.getUsrn(),syDS.getUsrp());
        stmt = conn.createStatement();

        stmt.executeUpdate("delete from sym_trigger_router");
        stmt.executeUpdate("delete from sym_trigger");
        stmt.executeUpdate("delete from sym_router");
        stmt.executeUpdate("delete from sym_channel where channel_id in ('sale_transaction', 'item','to_corp')");
        stmt.executeUpdate("delete from sym_node_group_link");
        stmt.executeUpdate("delete from sym_node_group");
        stmt.executeUpdate("delete from sym_node_host");
        stmt.executeUpdate("delete from sym_node_identity");
        stmt.executeUpdate("delete from sym_node_security");
        stmt.executeUpdate("delete from sym_node");

        stmt.executeUpdate("insert into sym_channel "
                + "(channel_id, processing_order, max_batch_size, enabled, description) "
                + "values('to_corp', 1, 100000, 1, 'channel to corp')");
        stmt.executeUpdate("insert into sym_node_group (node_group_id) values ('corp')");
        stmt.executeUpdate("insert into sym_node_group (node_group_id) values ('store')");
        
        stmt.executeUpdate("insert into sym_node_group_link "
                + "(source_node_group_id, target_node_group_id, data_event_action) "
                + "values ('corp', 'store', 'R')");
        stmt.executeUpdate("insert into sym_node_group_link "
                + "(source_node_group_id, target_node_group_id, data_event_action) "
                + "values ('store', 'corp', 'P')");
        
        stmt.executeUpdate("insert into sym_router "
                + "(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time) "
                + "values('corp_2_store', 'corp', 'store', 'default',current_timestamp, current_timestamp)");
        stmt.executeUpdate("insert into sym_router "
                + "(router_id,source_node_group_id,target_node_group_id,router_type,create_time,last_update_time) "
                + "values('store_2_corp', 'store', 'corp', 'default',current_timestamp, current_timestamp)");
        System.out.println("tableList.length :" + tableList.length);
        for(String table : tableList) {
            System.out.println("table :" + table);
            String[] where = table.split("\\.");
            System.out.println("where.length :" + where.length);
            if (where.length == 1) {
                stmt.executeUpdate("insert into sym_trigger  "
                        + "(trigger_id,source_table_name,channel_id,last_update_time ,create_time)"
                        + "values('"+ table +"','"+ table +"' ,'to_corp' ,current_timestamp,current_timestamp)");
                stmt.executeUpdate("insert into sym_trigger  "
                        + "(trigger_id,source_table_name,channel_id,sync_on_insert, sync_on_update, sync_on_delete,last_update_time,create_time)"
                        + "values('"+ table +"_corp','"+ table +"','to_corp',0,0,0,current_timestamp,current_timestamp)");
                
                stmt.executeUpdate("insert into sym_trigger_router "
                        + "(trigger_id,router_id,initial_load_order,last_update_time,create_time)"
                        + "values('"+ table +"','store_2_corp', 200, current_timestamp, current_timestamp)");
                stmt.executeUpdate("insert into sym_trigger_router "
                        + "(trigger_id,router_id,initial_load_order,last_update_time,create_time)"
                        + "values('"+ table +"_corp','corp_2_store', 200, current_timestamp, current_timestamp)");
            }else if(where.length == 2) {
                stmt.executeUpdate("insert into sym_trigger "
                        + "(trigger_id,source_catalog_name,source_table_name,channel_id,last_update_time ,create_time)"
                        + "values('"+ where[0]+"_"+ where[1] +"','"+ where[0] +"' ,'"+ where[1] +"' ,'to_corp' ,current_timestamp,current_timestamp)");
                stmt.executeUpdate("insert into sym_trigger "
                        + "(trigger_id,source_catalog_name,source_table_name,channel_id,sync_on_insert, sync_on_update, sync_on_delete,last_update_time,create_time)"
                        + "values('"+ where[0]+"_"+ where[1] +"_corp','"+ where[0] +"' ,'"+ where[1] +"' ,'to_corp',0,0,0,current_timestamp,current_timestamp)");
                
                stmt.executeUpdate("insert into sym_trigger_router "
                        + "(trigger_id,router_id,initial_load_order,last_update_time,create_time)"
                        + "values('"+ where[0]+"_"+ where[1] +"','store_2_corp', 200, current_timestamp, current_timestamp)");
                stmt.executeUpdate("insert into sym_trigger_router "
                        + "(trigger_id,router_id,initial_load_order,last_update_time,create_time)"
                        + "values('"+ where[0]+"_"+ where[1] +"_corp','corp_2_store', 200, current_timestamp, current_timestamp)");
            }else {throw new Exception();}
        }

        stmt.executeUpdate("insert into sym_node "
                + "(node_id,node_group_id,external_id,sync_enabled,sync_url,schema_version,symmetric_version,database_type,database_version,batch_to_send_count,batch_in_error_count,created_at_node_id)" 
                + " values ('000','corp','000',1,null,null,null,null,null,0,0,'000')");
        stmt.executeUpdate("insert into sym_node "
                + "(node_id,node_group_id,external_id,sync_enabled,sync_url,schema_version,symmetric_version,database_type,database_version,batch_to_send_count,batch_in_error_count,created_at_node_id)" 
                + " values ('001','store','001',1,null,null,null,null,null,0,0,'000')");
        
        stmt.executeUpdate("insert into sym_node_security "
                + "(node_id,node_password,registration_enabled,registration_time,initial_load_enabled,initial_load_time,created_at_node_id) "
                + "values ('000','5d1c92bbacbe2edb9e1ca5dbb0e481',0,current_timestamp,0,current_timestamp,'000')");            
        stmt.executeUpdate("insert into sym_node_security "
                + "(node_id,node_password,registration_enabled,registration_time,initial_load_enabled,initial_load_time,created_at_node_id) "
                + "values ('001','5d1c92bbacbe2edb9e1ca5dbb0e481',0,current_timestamp,0,current_timestamp,'001')");    

        if (syDS.getGroupId().equals("tcn"))
            stmt.executeUpdate("insert into sym_node_identity values ('001')");
        else if (syDS.getGroupId().equals("rsw"))
            stmt.executeUpdate("insert into sym_node_identity values ('000')");


        stmt.close();

        conn.close();

    }
    
    
    
}





