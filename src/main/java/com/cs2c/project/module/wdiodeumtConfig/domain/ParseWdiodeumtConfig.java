package com.cs2c.project.module.wdiodeumtConfig.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseWdiodeumtConfig {
	public static ParseWdiodeumtConfig INSTANCE = new ParseWdiodeumtConfig();

	private ParseWdiodeumtConfig() {
	}

	public Map<String, Object> parseWdiodeumtConfig(WdiodeumtConfig wdiodeumtConfig) {
		HashMap<String, Object> parseContents = new HashMap<>();

		parseContents.put("data_time", wdiodeumtConfig.getDataTime()); // 版本管理使用
		parseContents.put("description", wdiodeumtConfig.getDescription()); // 版本管理使用

		/*
		 * config_info => { "key1" : { "description" : "xxxx", "data_time" : "yyyy",
		 * "value" : "zzzz" }, "key1" : { "description" : "1111", "data_time" : "2222",
		 * "value" : "3333" } "key2" : { "description" : "xxxx", "data_time" : "xxxx",
		 * "value" : "xxxx" } .......... }
		 */

		String lines[] = wdiodeumtConfig.getContents().split(System.lineSeparator()), description = "", key, value;
		List<ViewWdiodeumtConfig> viewWdiodeumtConfigs = new ArrayList<>();
		for (String line : lines) {

			if (line.trim().equals("")) {
				continue;
			} else if (line.startsWith("#")) {
				description = line.substring(1, line.length());
			} else {
				key = line.split("=")[0];
				value = line.split("=")[1];
				ViewWdiodeumtConfig viewWdiodeumtConfig = new ViewWdiodeumtConfig(key, value,
						description.equals("") ? null : description, wdiodeumtConfig.getDataTime());
				viewWdiodeumtConfigs.add(viewWdiodeumtConfig);
				description = "";
			}
		}
		parseContents.put("configinfo", viewWdiodeumtConfigs);

		return parseContents;
	}
}
