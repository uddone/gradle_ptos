package com.gsil.gradleptos.util;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParserHelper {
	
	public String jsonKeyValue (String fileJson) throws ParseException {
		
		JSONParser parser = new JSONParser();
		
		JSONObject jsonObj = (JSONObject)parser.parse(fileJson);			
		
		String updateType = "";
		
		try 
		{			
			updateType = jsonObj.get("update_type").toString();						
		}		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return updateType;

	    
	}

}
