package com.kjuns.model;

import java.util.HashMap;
import java.util.Map;


public class AliPaymentPrevOrder {
	private String service;
	private String partner;
	private String _input_charset;
	private String notify_url;
	private String out_trade_no;
	private String subject;
	private String payment_type;
	private String seller_id;
	private String total_fee;
	
	public AliPaymentPrevOrder(){
		
	}
	
	public AliPaymentPrevOrder(String _total_fee,String _out_trade_no){
		this.out_trade_no = _out_trade_no;
		this.total_fee = _total_fee;
		this.service= "mobile.securitypay.pay";
		this.payment_type = "1";
		this._input_charset = "utf-8";
	}
	
	public Map<String,String> getMap(){
		Map<String,String> result = new HashMap<String,String>();
		result.put("service", "\""+service+"\"");
		result.put("partner", "\""+partner+"\"");
		result.put("_input_charset", "\""+_input_charset+"\"");
		result.put("notify_url", "\""+notify_url+"\"");
		result.put("out_trade_no", "\""+out_trade_no+"\"");
		result.put("subject", "\""+subject+"\"");
		result.put("payment_type", "\""+payment_type+"\"");
		result.put("seller_id", "\""+seller_id+"\"");
		result.put("total_fee", "\""+total_fee+"\"");
		result.put("it_b_pay", "\"30m\"");
		result.put("body", "\"请您尽快完成支付\"");
		result.put("show_url", "\"m.alipay.com\"");
		return result;
	}
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	
}
