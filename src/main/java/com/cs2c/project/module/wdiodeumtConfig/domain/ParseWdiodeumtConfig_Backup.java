package com.cs2c.project.module.wdiodeumtConfig.domain;

import com.alibaba.fastjson.JSONObject;
import com.cs2c.common.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ParseWdiodeumtConfig_Backup {
    public ParseWdiodeumtConfig_Backup INSTANCE = new ParseWdiodeumtConfig_Backup();

    private ParseWdiodeumtConfig_Backup() {}

    public Map<String, Object> parseWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig) {
        HashMap<String, Object> parseContents = new HashMap<>();

        parseContents.put("data_time", wdiodeumtConfig.getDataTime()); // 版本管理使用
        parseContents.put("description", wdiodeumtConfig.getDescription()); // 版本管理使用

        ViewWdiodeumtConfig viewWdiodeumtConfig = new ViewWdiodeumtConfig();
        parseContents.put("configinfo", viewWdiodeumtConfig);

        /*
        config_info =>
        {
            "key1" : {
                "description" : "xxxx",
                "data_time" : "xxxx",
                "value" : "xxxx"
            },
            "key1" : {
                "description" : "xxxx",
                "data_time" : "xxxx",
                "value" : "xxxx"
            }
            "key2" : {
                "description" : "xxxx",
                "data_time" : "xxxx",
                "value" : "xxxx"
            }
            ..........
        }
         */


        JSONObject contents = JSONObject.parseObject(wdiodeumtConfig.getContents());
        Field[] fields = ViewWdiodeumtConfig.class.getDeclaredFields();

        if (fields.length != contents.size()) {
            LogUtils.ERROR_LOG.error("数据库中核心配置文件的信息不正确，请先排查再进行操作");
            return parseContents;
        }

        for (Field field : fields) {
            try {
                field.set(viewWdiodeumtConfig, contents.getString(field.getName()));
            } catch (IllegalAccessException e) {
                LogUtils.ERROR_LOG.error("注入配置项" + field.getName() + "的值时发生异常", e);
                return parseContents;
            }
        }

        return parseContents;
    }

}
