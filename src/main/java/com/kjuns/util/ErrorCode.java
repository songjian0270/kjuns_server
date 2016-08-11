package com.kjuns.util; 


public enum ErrorCode {
	
	//系统级
	SUCCESS("000000","成功"), 
	SYS_ERROR("999999","系统异常"),
	FAILED("900000","无效操作"),
	CLIENT_VERSION_LOW("900001","客户端版本过低,请及时更新"),//强制升级,注意：此错误码不能变动，且不能重复
	JSON_ERROR("900010","json格式错误"),
	NET_ERROR_READ_TIMED_OUT("900002","网络不给力，请求服务器超时"),
	DENY_ACCESS("900003","访问受限"),
	INPUT_ERROR("900004","输入错误"),
	PARAM_ERROR("900005","参数错误"),
	CLIENT_INVALID("900006","HEADER错误"),
	VERIFY_SIGNATURE_ERROR("900007","验签错误"),
	IP_JURISDICTION_ERROR("900008","IP权限不足"),
	CALL_URL_ERROR("900009", "请检查接口访问地址是否正确"),
	REPEAT_SUBMIT_ERROR("900010", "请勿重复提交"),

	/*UNIQUE_CONSTRAINT_ERROR("400007","违反唯一性约束"), */
	//INVALID_CHAR_ERROR("400008","输入内容不能含有特殊符号（或表情图）"),
	
	//用户部分
	LOGIN_ERROR("200001","登陆失败" ),
	LOGIN_THIRD_PART_ERROR("200002", "第三方登陆失败"),
	MOBILE_NOT_BINDING("200003","未绑定手机"),
	MOBILE_NOT_EXIST("200004","手机号不存在"),
	MOBILE_BINDING_ERROR("200005","该手机已注册或绑定，如需绑定，请联系客服人员"),
	NEED_LOGIN("200006","没有登录或登录失效，请重新登录"),
	NOT_PERFECT_USER_INFO("200007","请完善信息"),
	USER_INFO_NOT_EXIST("200009","用户信息不存在"),
	THIRD_PART_BINDING_ERROR("200011","该账号已注册或绑定，如需绑定，请联系客服人员"),
	NICK_NAME_EXIST_ERROR("200012","昵称不能重复"),
	NOT_EXIST_APPLY_ERROR("200013","未提交审核信息"),
	NOT_QUICK_SEND_COMMENT("200014","评论不能太过频繁"),
	/*MOBILE_CODE_HIGH_FREQUENCY("200017","超出操作频率限制"),
	MOBILE_CODE_QUANTITY_LIMIT("200018","超出操作次数限制"),*/
	
	EXIST("500000","已存在"),
	
	//短信错误
	SMS_SEND_FAILD("300001","短信验证码发送失败"),
	SMS_CODE_TIMEOUT("300002","短信验证码超时或不存在，请重新获取"),
	SMS_CODE_ERROR("300003","短信验证码错误"),
	//SMS_CODE_MULTIPLE("300004","短信验证码已下发，1分钟内使用有效"),
	SMS_CODE_REPEAT("300005","请在一分钟后再获取验证码"),
	
	//动态
	CONTENT_NOT_SLEF("700001","不能删除别人内容"),
	CONTENT_DEL("700002","内容已被删除"),

	;
	
	private String code;
	
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ErrorCode(String code, String desc) {   
        this.code = code;   
        this.desc = desc;   
    }
	
	//通过value获取对应的枚举对象
    public static ErrorCode getErrorCode(String key) {
      for (ErrorCode errorCode : ErrorCode.values()) {
          if (key.equals(errorCode.getCode())) {
              return errorCode;
          }
      }
      return null;
  }
	
}
 