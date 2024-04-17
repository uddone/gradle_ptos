package com.gsil.gradleptos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;
import java.util.Map;


/**
* @author Hansol
* @brief 반환값 정의 클래스
* @date 21-06-24
* Testing Object : Query Auto
*/

public class EventResult implements EventResultI<Map<String, Object>>
{
	// 수행 메서드 명
	private String rqMethod;
	// 결과 코드
	private Integer rsCode = -1;
	// 결과 메시지
	private String rsMsg;
	// 결과 값 List
	private List<Map<String, Object>> rsMap;
	// 결과 값 Map
	private Map<String, Object> rsObj;
	// Exception Err 메시지
	private String exMsg;

	public String getRqMethod() {
		return rqMethod;
	}

	public void setRqMethod(String rqMethod) {
		this.rqMethod = rqMethod;
	}

	public int getRsCode() {
		return rsCode;
	}

	public void setRsCode(int rsCode) {
		this.rsCode = rsCode;
	}

	public String getRsMsg() {
		return rsMsg;
	}

	public void setRsMsg(String rsMsg) {
		this.rsMsg = rsMsg;
	}

	public String getExMsg() {
		return exMsg;
	}

	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}

	public Map<String, Object> getRsObj() {
		return rsObj;
	}

	public void setRsObj(Map<String, Object> rsObj) {
		this.rsObj = rsObj;
	}

	public void putRsObj(String key, Object value) {
		this.rsObj.put(key, value);
	}

	public List<Map<String, Object>> getRsMap() {
		return rsMap;
	}

	public void setRsMap(List<Map<String, Object>> rsMap) {
		this.rsMap = rsMap;
	}

	public void convertStringToArray(Integer index, String targetName) {
		if (index == null) index = 0;
		String jsonString = this.getRsMap().get(index).get(targetName) == null ? null : this.getRsMap().get(index).get(targetName).toString();
		try {
			if (jsonString != null && !jsonString.isEmpty()) {
				JSONParser parser = new JSONParser();
				JSONArray arrayResult = (JSONArray) parser.parse(jsonString);
				if (!arrayResult.isEmpty()) {
					this.getRsMap().get(index).put(targetName, arrayResult);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void convertStringToArray(String targetName) {
		convertStringToArray(0, targetName);
	}

	public void convertStringToArrayAll(String targetName) {
		for (int i = 0; i < this.getRsMap().size(); i++) {
			convertStringToArray(i, targetName);
		}
	}

	public void convertStringToArrayInObj(Integer index, String targetName) {
		if (index == null) index = 0;
		String jsonString = this.getRsObj().get(targetName) == null ? null : this.getRsObj().get(targetName).toString();
		try {
			if (jsonString != null && !jsonString.isEmpty()) {
				JSONParser parser = new JSONParser();
				JSONArray arrayResult = (JSONArray) parser.parse(jsonString);
				if (!arrayResult.isEmpty()) {
					this.getRsObj().put(targetName, arrayResult);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void convertStringToArrayInObj(String targetName) {
		convertStringToArrayInObj(0, targetName);
	}

	public void convertStringToObject(Integer index, String targetName) {
		if (index == null) index = 0;
		String jsonString = this.getRsMap().get(index).get(targetName).toString();
		try {
			if (jsonString != null && !jsonString.isEmpty()) {
				JSONParser parser = new JSONParser();
				JSONObject arrayResult = (JSONObject) parser.parse(jsonString);
				this.getRsMap().get(index).put(targetName, arrayResult);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void convertStringToPhoneNumber(String targetName) {
		String jsonString = this.getRsMap().get(0).get(targetName).toString();
		try {
			if (jsonString != null && !jsonString.isEmpty()) {
				String beforeText = jsonString.replaceAll("\\D", "");
				if (beforeText.length() == 8) {
					this.getRsMap().get(0).put(targetName, beforeText.replaceFirst("^(\\d{4})(\\d{4})$", "$1-$2"));
				} else if (beforeText.length() == 12) {
					this.getRsMap().get(0).put(targetName, beforeText.replaceFirst("(^\\d{4})(\\d{4})(\\d{4})$", "$1-$2-$3"));
				}
				this.getRsMap().get(0).put(targetName, beforeText.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void convertStringToObject(String targetName) {
		convertStringToObject(0, targetName);
	}

}
