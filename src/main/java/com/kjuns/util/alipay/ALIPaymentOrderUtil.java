package com.kjuns.util.alipay;

import com.kjuns.model.AliPaymentPrevOrder;


public class ALIPaymentOrderUtil {

	public static AliPaymentPrevOrder getAiDou(String tradeNum, String amount) {
		return new AliPaymentPrevOrder(amount,tradeNum);
	}
}
