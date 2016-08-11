package com.kjuns.validation; 

import java.util.Map;

public class FieldValiRule {
	
	private String name;
	
	private String dataType;
	
	/** validate(校验类型“notEmpty”等) - Vali */
	private Map<String,Vali> rules;
	
	public FieldValiRule(String name, String dataType, Map<String,Vali> rules){
		this.name = name;
		this.dataType = dataType;
		this.rules = rules;
	}

	/** 字段名 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/** 字段的数据类型 */
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/** validate(校验类型“notEmpty”等) - Vali */
	public Map<String, Vali> getRules() {
		return rules;
	}

	public void setRules(Map<String, Vali> rulesMap) {
		this.rules = rulesMap;
	}
}
 