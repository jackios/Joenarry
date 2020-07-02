package com.cs2c.project.module.wdiodeConfig.domain;

import com.alibaba.fastjson.JSONObject;
import com.cs2c.common.utils.LogUtils;
import java.lang.reflect.Field;
import java.util.*;

public class ParseWdiodeConfig {
    public static ParseWdiodeConfig INSTANCE = new ParseWdiodeConfig();

    private ParseWdiodeConfig() {}

    public Map<String, Object> parseWdiodeConfig(WdiodeConfig wdiodeConfig) {
        HashMap<String, Object> parseContents = new HashMap<>();

        parseContents.put("data_time", wdiodeConfig.getDataTime()); // 版本管理使用
        parseContents.put("description", wdiodeConfig.getDescription()); // 版本管理使用



        /*
        config_info =>
        {
            "key1" : {
                "description" : "xxxx",
                "data_time" : "yyyy",
                "value" : "zzzz"
            },
            "key1" : {
                "description" : "1111",
                "data_time" : "2222",
                "value" : "3333"
            }
            "key2" : {
                "description" : "xxxx",
                "data_time" : "xxxx",
                "value" : "xxxx"
            }
            ..........
        }
         */


        JSONObject contents = JSONObject.parseObject(wdiodeConfig.getContents());
        List<ViewWdiodeConfig> viewWdiodeConfigs = new ArrayList<>();
        for (String key : contents.keySet()) {
            JSONObject object = contents.getJSONObject(key);
            ViewWdiodeConfig viewWdiodeConfig = new ViewWdiodeConfig(key, object.getString("value"), object.getString("description"), object.getDate("data_time"));
            viewWdiodeConfigs.add(viewWdiodeConfig);
        }
        parseContents.put("configinfo", viewWdiodeConfigs);

        return parseContents;
    }
}
