package com.kjuns.util.tld;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class DateTag extends TagSupport {

    private static final long serialVersionUID = 6464168398214506236L;

    private String value;
    
    private String hommization;

    @Override
    public int doStartTag() throws JspException {
        String vv = "" + value;
        try {
        	
        	
            long timeStamp = Long.valueOf(vv.trim());
            Date date= new Date(Long.valueOf(timeStamp+"000"));
            Long time = (new Date().getTime()-date.getTime())/1000;
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateContent = dateformat.format(date);
        	if(hommization!=null && hommization.equals("true")){
	        	int days = (int) (time / (3600 * 24));
	            if (days == 0){
	                int second = (int) (time/60l);
	                if (second <= 0) {
	                	dateContent =  "1分钟内";
	                } else if (second < 60) {
	                	dateContent = second+"分钟前";
	                } else {
	                    int hour = (int) (time/3600);
	                    dateContent = hour+"小时前";
	                }
	            } else {
	                if (days<30) {
	                    dateContent = days+"天前";
	                } else {
	                    pageContext.getOut().write(dateContent);
	                    return super.doStartTag();
	                }
	            }
	        }
            pageContext.getOut().write(dateContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }

	public void setHommization(String hommization) {
		this.hommization = hommization;
	}

    
}