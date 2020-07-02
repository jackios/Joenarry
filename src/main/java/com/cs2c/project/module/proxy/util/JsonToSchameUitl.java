package com.cs2c.project.module.proxy.util;

import cn.hutool.json.JSONUtil;
import com.cs2c.common.utils.LogUtils;
import com.cs2c.common.utils.http.HttpUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class JsonToSchameUitl {
	/**
	 * 返回当前数据类型
	 * 
	 * @param source
	 * @return
	 */
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	public String getTypeValue(Object source) {

		if (source instanceof String) {
			return "String";
		}

		if (source instanceof Integer) {
			return "Integer";
		}

		if (source instanceof Float) {
			return "Float";
		}

		if (source instanceof Long) {
			return "Long";
		}

		if (source instanceof Double) {
			return "Double";
		}

		if (source instanceof Date) {
			return "Date";
		}

		if (source instanceof Boolean) {
			return "Boolean";
		}

		return "null";
	}

	/**
	 * 把Object变成JsonSchema
	 * 
	 * @param source
	 * @return
	 */
	public Object generateJsonSchema(Object source) {

		Object result = new Object();

		// 判断是否为JsonObject
		if (source instanceof JSONObject) {
			JSONObject jsonResult = JSONObject.fromObject(result);
			JSONObject sourceJSON = JSONObject.fromObject(source);
			Iterator<?> iterator = sourceJSON.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object nowValue = sourceJSON.get(key);

				if (nowValue == null || nowValue.toString().equals("null")) {
					jsonResult.put(key, "null");

				} else if (isJsonObject(nowValue)) {
					jsonResult.put(key, generateJsonSchema(nowValue));
				} else if (isJsonArray(nowValue)) {
					JSONArray tempArray = JSONArray.fromObject(nowValue);
					JSONArray newArray = new JSONArray();

					if (tempArray != null && tempArray.size() > 0) {
						for (int i = 0; i < tempArray.size(); i++) {
							newArray.add(generateJsonSchema(tempArray.get(i)));
						}
						jsonResult.put(key, newArray);
					}
				} else if (nowValue instanceof List) {
					List<Object> newList = new ArrayList<Object>();

					for (int i = 0; i < ((List<?>) nowValue).size(); i++) {
						newList.add(((List<?>) nowValue).get(i));
					}

					jsonResult.put(key, newList);
				} else {

					jsonResult.put(key, getTypeValue(nowValue));
				}

			}
			return jsonResult;
		}

		if (source instanceof JSONArray) {
			JSONArray jsonResult = JSONArray.fromObject(source);
			JSONArray tempArray = new JSONArray();
			if (jsonResult != null && jsonResult.size() > 0) {
				for (int i = 0; i < jsonResult.size(); i++) {
					tempArray.add(generateJsonSchema(jsonResult.get(i)));
				}
				return tempArray;
			}

		}

		return getTypeValue(source);

	}

	/**
	 * JSON格式比对
	 * 
	 * @param currentJSON
	 * @param expectedJSON
	 * @return
	 */
	public JSONObject diffJson(JSONObject currentJSON, JSONObject expectedJSON) {

		JSONObject jsonDiff = new JSONObject();

		Iterator<?> iterator = expectedJSON.keys();

		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Object expectedValue = expectedJSON.get(key);
			Object currentValue = currentJSON.get(key);
			if (!expectedValue.toString().equals(currentValue.toString())) {
				JSONObject tempJSON = new JSONObject();
				tempJSON.put("value", currentValue);
				tempJSON.put("expected", expectedValue);
				jsonDiff.put(key, tempJSON);
			}
		}
		return jsonDiff;
	}

	/**
	 * 判断是否为JSONObject
	 * 
	 * @param value
	 * @return
	 */
	public boolean isJsonObject(Object value) {

		try {
			if (value instanceof JSONObject) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断是否为JSONArray
	 * 
	 * @param value
	 * @return
	 */
	public boolean isJsonArray(Object value) {

		try {

			if (value instanceof JSONArray) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * JSON格式比对，值不能为空,且key需要存在
	 * 
	 * @param current
	 * @param expected
	 * @return
	 */
	public JSONObject diffFormatJson(Object current, Object expected) {

		JSONObject jsonDiff = new JSONObject();

		if (isJsonObject(expected)) {

			JSONObject expectedJSON = JSONObject.fromObject(expected);
			JSONObject currentJSON = JSONObject.fromObject(current);

			Iterator<?> iterator = JSONObject.fromObject(expectedJSON).keys();

			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object expectedValue = expectedJSON.get(key);

				if (!currentJSON.containsKey(key)) {
					JSONObject tempJSON = new JSONObject();
					tempJSON.put("actualKey", "不存在此" + key);
					tempJSON.put("expectedKey", key);
					jsonDiff.put(key, tempJSON);

				}

				if (currentJSON.containsKey(key)) {

					Object currentValue = currentJSON.get(key);

					if (expectedValue != null && currentValue == null
							|| expectedValue.toString() != "null" && currentValue.toString() == "null") {
						JSONObject tempJSON = new JSONObject();
						tempJSON.put("actualValue", "null");
						tempJSON.put("expectedValue", expectedValue);
						jsonDiff.put(key, tempJSON);
					}

					if (expectedValue != null && currentValue != null) {
						if (isJsonObject(expectedValue) && !JSONObject.fromObject(expectedValue).isNullObject()
								|| isJsonArray(expectedValue) && !JSONArray.fromObject(expectedValue).isEmpty()) {
							JSONObject getResultJSON = new JSONObject();
							getResultJSON = diffFormatJson(currentValue, expectedValue);
							if (getResultJSON != null) {
								jsonDiff.putAll(getResultJSON);
							}
						}
					}
				}
			}
		}

		if (isJsonArray(expected)) {
			JSONArray expectArray = JSONArray.fromObject(expected);
			JSONArray currentArray = JSONArray.fromObject(current);

			if (expectArray.size() != currentArray.size()) {
				JSONObject tempJSON = new JSONObject();
				tempJSON.put("actualLenth", currentArray.size());
				tempJSON.put("expectLenth", expectArray.size());
				jsonDiff.put("Length", tempJSON);
			}

			if (expectArray.size() != 0) {
				Object expectIndexValue = expectArray.get(0);
				Object currentIndexValue = currentArray.get(0);

				if (expectIndexValue != null && currentIndexValue != null) {
					if (isJsonObject(expectIndexValue) && !JSONObject.fromObject(expectIndexValue).isNullObject()
							|| isJsonArray(expectIndexValue) && !JSONArray.fromObject(expectIndexValue).isEmpty()) {
						JSONObject getResultJSON = new JSONObject();
						getResultJSON = diffFormatJson(currentIndexValue, expectIndexValue);
						if (getResultJSON != null) {
							jsonDiff.putAll(getResultJSON);
						}
					}
				}
			}
		}

		return jsonDiff;
	}

	/**
	 * json文件转化字符串
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String JsonToStr(String fileName) throws Exception {
		// 读取文件数据
		StringBuffer stringBuffer = new StringBuffer();
		File file = new File(fileName);
		if (!file.exists()) {
//            log.info("Can not find"+fileName);
			LogUtils.ERROR_LOG.error("Can't find file" + fileName);
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String string = null;
			while ((string = bufferedReader.readLine()) != null) {
				stringBuffer.append(string);
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	/**
	 * 将Object写入文件
	 * 
	 * @param o
	 * @param filName
	 */
	public static void writeObjectToFile(Object o, String filName) {
		File file = new File(filName);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
			objectOutputStream.writeObject(o);
			objectOutputStream.flush();
			objectOutputStream.close();
			log.info("write object success");
		} catch (IOException e) {
			log.info("write object failed");
			e.printStackTrace();
		}
	}

	/**
	 * 将object写入文件 上个有乱码
	 * 
	 * @param o
	 * @param file
	 */
	public void write(Object o, String file) {
		File f = new File(file);
		try {
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = o.toString().getBytes();
			fos.write(b);
			fos.flush();
			fos.close();
			log.info(" write  success  -----");
		} catch (IOException e) {
			log.info("write errror---");
			e.printStackTrace();
		}

	}

	public static void write1(Object o, String file) {
		File f = new File(file);
		try {
			FileOutputStream fos = new FileOutputStream(f);
			byte[] b = JsonSchameFormart(o).toString().getBytes();
			fos.write(b);
			fos.flush();
			fos.close();
			log.info(" write  success  -----");
		} catch (IOException e) {
			log.info("write errror---");
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param level
	 * @return String
	 */
	private static String getLevelObject(int level) {
		StringBuffer levelsb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			levelsb.append("\t");
		}
		return levelsb.toString();
	}

	public static Object JsonSchameFormart(Object o) {
		int level = 0;
		// 存放格式化的schame
		StringBuffer sb = new StringBuffer();
		for (int index = 1; index < o.toString().length() - 1; index++) {
			// 获取o中的每个字符
			char c = sb.charAt(index);
			// level大于0并且sb中的最后一个字符串为\n ，sb就加入\t
			if (level > 0 && '\n' == sb.charAt(sb.length() - 1)) {
				sb.append(getLevelObject(level));
			}
			// 遇到"{"和"["要增加空格和换行，遇到"{"和"["要减少空格，以对应，，遇到"," 换行
			switch (c) {
			case '{':
			case '[':
				sb.append(c + "\n");
				level++;
				break;
			case ',':
				sb.append(c + "\n");
				break;
			case ']':
			case '}':
				sb.append("\n");
				level--;
				sb.append(getLevelObject(level));
				sb.append(c);
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public void main(String[] args) {

		JsonToSchameUitl jsonToSchame = new JsonToSchameUitl();
		// com.cs2c.project.mylql.JsonToSchame jsonToSchame = new
		// com.cs2c.project.mylql.JsonToSchame();

		/*
		 * String str1 =
		 * "{\"status\":201,\"msg\":\"今天您已经领取过，明天可以继续领取哦！\",\"res\":{\"remainCouponNum\":\"5\",\"userId\":\"123123213222\"}}";
		 * 
		 * JSONObject jsonObject1 = JSONObject.fromObject(str1);
		 * System.out.println("转换成JSONschame:" +
		 * jsonToSchame.generateJsonSchema(jsonObject1).toString()); String str2 =
		 * "{\"status\":201,\"msg2\":\"今天您已经领取过，明天可以继续领取哦！\",\"res\":{\"remainCouponNum\":\"5\",\"userId\":\"123123213222\"}}";
		 * 
		 * JSONObject jsonObject2 = JSONObject.fromObject(str2);
		 * 
		 * 
		 * String str3 =
		 * "{\"status\":null,\"msg\":\"今天您已经领取过，明天可以继续领取哦！\",\"res\":{\"remainCouponNum\":\"5\",\"userId\":\"123123213222\"}}";
		 * 
		 * JSONObject jsonObject3 = JSONObject.fromObject(str3);
		 * 
		 * System.out.println("当前str2没有msg字段: " +
		 * jsonToSchame.diffFormatJson(jsonObject2,jsonObject1).toString());
		 * 
		 * System.out.println("当前str2中的status为null值:" +
		 * jsonToSchame.diffFormatJson(jsonObject3,jsonObject1).toString());
		 */

		String json = "null";
		try {
			json = JsonToStr("d:/abc/b.json");
			JSONObject jsonObject1 = JSONObject.fromObject(json);
			System.out.println("转换成JSONschame:" + jsonToSchame.generateJsonSchema(jsonObject1).toString());
			String fileStr = "d:/abc/b.schame";

			// write(JsonSchameFormart(jsonToSchame.generateJsonSchema(jsonObject1)),fileStr);
			// write(jsonToSchame.generateJsonSchema(jsonObject1).toString(),fileStr);
			write(JSONUtil.formatJsonStr(jsonToSchame.generateJsonSchema(jsonObject1).toString()), fileStr);

			/*
			 * BufferedInputStream in = FileUtil.getInputStream("d:/abc/b.schame");
			 * BufferedOutputStream out = FileUtil.getOutputStream("d:/abc/b.schame"); long
			 * copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
