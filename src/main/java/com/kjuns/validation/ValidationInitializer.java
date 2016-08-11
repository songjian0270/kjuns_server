package com.kjuns.validation; 

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationInitializer {
	
	private static Logger logger = LoggerFactory.getLogger(ValidationInitializer.class);
	
	private static final File[] FILE_LIST = new File(ValidationInitializer.class.getResource("/").getPath() + "validation/").listFiles();
	
	public static Map<String,FieldValiRule[]> init(){
		Map<String,FieldValiRule[]> valiMap = new  HashMap<String,FieldValiRule[]>();
		try {
			for (File file : FILE_LIST) {
				parse(file, valiMap);
			}
		} catch (NullPointerException ex) {
			logger.error("初始化校验规则文件未找到  init......");
		}
		return valiMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static void parse(File file, Map<String,FieldValiRule[]> valiMap){
		logger.info("解析文件：{}",file);
		SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(file);
            Element root = doc.getRootElement();//跟节点validators
            for (Iterator it = root.elementIterator(); it.hasNext();) {
            	//遍历/获取第二层“validator”节点
            	Element valiTag =(Element) it.next();
            	String target = valiTag.attributeValue("target");
            	String method = valiTag.attributeValue("method");
            	List fieldList = valiTag .elements("field"); //字段名和字段的数据类型
            	int fieldCount = fieldList.size();
            	FieldValiRule[] fieldValiRule = new FieldValiRule[fieldCount];
            	for (int i = 0 ; i < fieldCount ; i++) {
            		Element fieldEle = (Element)fieldList.get(i);
            		String name = fieldEle.attributeValue("name");
            		String dataType = fieldEle.attributeValue("dataType");
            		Map<String,Vali> rulesMap = new HashMap<String,Vali>(); 
            		for (Iterator fIt = fieldEle.elementIterator(); fIt.hasNext();) {
            			Element ruleEle =(Element) fIt.next();
            			String validate = ruleEle.attributeValue("validate");
            			String value = ruleEle.attributeValue("value");
            			String msg = ruleEle.attributeValue("msg");
            			Vali vali = new Vali(value,msg);
            			rulesMap.put(validate, vali);
            		}
            		fieldValiRule[i] = new FieldValiRule(name, dataType, rulesMap);
				}
            	valiMap.put(target+"."+method, fieldValiRule);
            }
        } catch (Exception ex) {
        	logger.error("校验规则文件解析异常  parse......");
        }
		//return valiMap;
    }

}
 