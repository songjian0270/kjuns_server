package com.kjuns.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CommonConstants {
	
	// ==================================== 正则 ==========================================
	/** Email地址正则表达式 */
	public static final String REGULAR_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	/** 用户名校验的正则表达式(字母、数字、下划线、中文) */
	public static final String REGULAR_USER_NAME = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
	/** 格式(id为数字)：“id1,id2,id3” */
	public static final String REGULAR_IDS_FORMAT = "\\d+(,\\d+)*";
	/** 数字 */
	public static final String REGULAR_NUMERAL = "\\d+";

	// ==================================== date & time format ====================================
	/** 日期/时间格式 "yyyy-MM-dd HH:mm:ss,SSS" */
	public static final SimpleDateFormat DATETIME_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/** 日期/时间格式 "yyyyMMddHHmmssSSS" */
	public static final SimpleDateFormat DATETIME_FULL_STR = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/** 日期/时间格式 "yyyy-MM-dd HH:mm:ss" */
	public static final SimpleDateFormat DATETIME_SEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** 日期/时间格式 "yyyyMMddHHmmss" */
	public static final SimpleDateFormat DATETIME_SEC_STR = new SimpleDateFormat("yyyyMMddHHmmss");
	/** 日期/时间格式 "yyyy-MM-dd" */
	public static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
	/** 日期/时间格式 "yyyy" */
	public static final SimpleDateFormat DATE_YEAR = new SimpleDateFormat("yyyy");
	/** 日期/时间格式 "yyyyMM" */
	public static final SimpleDateFormat DATE_YEAR_MONTH = new SimpleDateFormat("yyyyMM");
	/** 日期/时间格式 "yyyyMMdd" */
	public static final SimpleDateFormat DATE_STR = new SimpleDateFormat("yyyyMMdd");
	/** 日期/时间格式 "yyMMdd" */
	public static final SimpleDateFormat DATE_SHORT_STR = new SimpleDateFormat("yyMMdd");
	/** 日期/时间格式 "yyMMdd" */
	public static final SimpleDateFormat DATE_SHORT_YEAR_MONTH = new SimpleDateFormat("yyMM");
	/** 日期/时间格式 "MMdd" */
	public static final SimpleDateFormat DATE_NO_YEAR = new SimpleDateFormat("MMdd");
	/** 获得月份 "MM" */
	public static final SimpleDateFormat DATE_NO_MONTH = new SimpleDateFormat("MM");
	/** 获得天 "dd" */
	public static final SimpleDateFormat DATE_NO_DAY = new SimpleDateFormat("dd");
	/** 日期/时间格式 "HHmmss" */
	public static final SimpleDateFormat TIME_SEC_STR = new SimpleDateFormat("HHmmss");
	/** 日期/时间格式 "HH:mm:ss" */
	public static final SimpleDateFormat TIME_SEC = new SimpleDateFormat("HH:mm:ss");
	/** 日期格式 "yyyy/MM/dd" */
	public static final SimpleDateFormat DATE_INSUR = new SimpleDateFormat("yyyy/MM/dd");
	/** 日期格式 "MM月dd日HH时mm分" */
	public static final SimpleDateFormat DATE_FULL_CHN = new SimpleDateFormat("MM月dd日HH时mm分");
	/** 日期格式 "MM月dd日 HH:mm" */
	public static final SimpleDateFormat DATE_WED_RENT_FULL_CHN = new SimpleDateFormat("MM月dd日 HH:mm");
	/** 日期格式 "HH:mm" */
	public static final SimpleDateFormat DATE_WED_REVERT_FULL_CHN = new SimpleDateFormat("HH:mm");
	
	//1:formal 0:test
	// ==================================== 环境   ====================================
	public static final Integer TEST = 0;
	public static final Integer FORMAL = 1;
	
	// ==================================== 身份   ====================================
	public static final Integer NORMAL_USER = 0;   		//粉丝
	public static final Integer kjuns_USER = 1;			//练习生
	public static final Integer CENTER_kjuns_USER = 2;   //签约练习生
	// ==================================== 资源类别   ====================================
	public static final Integer LIVE_TYPE_VIDEO = 2;
	public static final Integer LIVE_TYPE_AUDIO = 0;
	public static final String LIVE_TYPE_FIX_VIDEO = "VIDEO";
	public static final String LIVE_TYPE_FIX_AUDIO = "AUDIO";
	
	
	// ==================================== 视频类别   ====================================
	public static final String DYNAMIC = "dynamic";
	public static final String WORK = "work";
	
	public static final String DYNAMIC_N = "01";
	public static final String WORK_N = "02";
	public static final String VOTE_N = "03";
	public static final String LIVE_N = "04";
	
	// ==================================== 签到奖励类型 ====================================
	public static final String SIGN_ITEM_EXP = "1";   		//经验
	public static final String SIGN_ITEM_AIDOU = "2";		//爱豆
	
	// ==================================== 推荐用户同时符合tag数 ====================================
	public static final String RECOMMEND_INTER_TAG_1 = "1";   	//符合1个tag
	public static final String RECOMMEND_INTER_TAG_2 = "2";		//符合2个tag
	public static final String RECOMMEND_INTER_TAG_3 = "3";		//符合3个tag

	// ==================================== 直播相关常量  ====================================

	public static final String LIVE_CLOSED = "2";		//直播已关闭
	public static final String LIVE_LIVEING = "1";		//正在直播
	public static final String LIVE_READY = "0";		//直播间就绪
	public static final String LIVE_NOT_EXIST = "-1"; 	//没有直播间
	
	// ==================================== 动态相关常量  ====================================
	
	public static final String IMG = "1";	
	public static final String VIDEO = "2";
	
	public static final String SERVICETYPE_DYNAMIC_VIDEO = "011";		//动态视频
	public static final String SERVICETYPE_DYNAMIC_IMG = "012";			//动态图片
	public static final String SERVICETYPE_DYNAMIC_AUDIO = "013";		//动态音频
	public static final String SERVICETYPE_DYNAMIC_OTHER = "019";		//动态其他
		
	public static final String SERVICETYPE_WORK_VIDEO = "021";
	public static final String SERVICETYPE_WORK_IMG = "022";
	public static final String SERVICETYPE_WORK_AUDIO = "023";
	public static final String SERVICETYPE_WORK_OTHER = "029";

	public static final String SERVICETYPE_VOTE_VIDEO = "031";		//投票视频
	public static final String SERVICETYPE_VOTE_IMG = "032";		//投票图片
	public static final String SERVICETYPE_VOTE_AUDIO = "033";		//投票音频
	public static final String SERVICETYPE_VOTE_OTHER = "039";		//投票其他
	
	public static final String SERVICETYPE_LIVE_VIDEO = "041";		//直播间视频
	public static final String SERVICETYPE_LIVE_IMG = "042";		//直播间图片
	public static final String SERVICETYPE_LIVE_AUDIO = "043";		//直播间音频
	public static final String SERVICETYPE_LIVE_OTHER = "049";		//直播间其他

	public static final String DYNAMIC_DATATYPE_AUDIO = "com.app.dynamic.audio";
	public static final String DYNAMIC_DATATYPE_IMG = "com.app.dynamic.resources";
	public static final String DYNAMIC_DATATYPE_VIDEO = "com.app.dynamic.video";
	
	public static final String WORK_DATATYPE_AUDIO = "com.app.work.audio";
	public static final String WORK_DATATYPE_IMG = "com.app.work.resources";
	public static final String WORK_DATATYPE_VIDEO = "com.app.work.video";
	
	public static final String VOTE_DATATYPE_AUDIO = "com.app.vote.audio";
	public static final String VOTE_DATATYPE_IMG = "com.app.vote.resources";
	public static final String VOTE_DATATYPE_VIDEO = "com.app.vote.video";
	
	public static final String LIVE_DATATYPE_AUDIO = "com.app.live.audio";
	public static final String LIVE_DATATYPE_IMG = "com.app.live.resources";
	public static final String LIVE_DATATYPE_VIDEO = "com.app.live.video";

	public static final String DYNAMIC_TABLE_AUDIO = "user_dynamic_app_resource_audio";
	public static final String DYNAMIC_TABLE_IMG = "user_dynamic_app_resource_image";
	public static final String DYNAMIC_TABLE_VIDEO = "user_dynamic_app_resource_video";
	
	public static final String WORK_TABLE_AUDIO = "user_work_app_resource_audio";
	public static final String WORK_TABLE_IMG = "user_work_app_resource_image";
	public static final String WORK_TABLE_VIDEO = "user_work_app_resource_video";
	
	public static final String VOTE_TABLE_AUDIO = "user_vote_app_resource_audio";
	public static final String VOTE_TABLE_IMG = "user_vote_app_resource_image";
	public static final String VOTE_TABLE_VIDEO = "user_vote_app_resource_video";
	
	public static final String LIVE_TABLE_AUDIO = "user_live_app_resource_audio";
	public static final String LIVE_TABLE_IMG = "user_live_app_resource_image";
	public static final String LIVE_TABLE_VIDEO = "user_live_app_resource_video";
	
	public static final String DYNAMIC_TABLE = "user_dynamic";
	public static final String WORK_TABLE = "user_work";
	public static final String VOTE_TABLE = "user_vote";
	public static final String LIVE_TABLE = "user_live";
	
	public static final String DYNAMIC_TYPE = "dynamic";
	public static final String WORK_TYPE = "work";
	public static final String VOTE_TYPE = "vote";
	public static final String LIVE_TYPE = "live";

	public static final String TABLE_ID_SUFFIX = "_id";
	public static final String COMMENT_TABLE_SUFFIX = "_comments";
	public static final String LIKE_TABLE_SUFFIX = "_likes";
	public static final String DEAL_TABLE_SUFFIX = "_deal";
	public static final String SHARE_TABLE_SUFFIX = "_share";
	public static final String DEAL_COMMENTS_TABLE_SUFFIX = "_deal_comments";
	
	
	public static final String DYNAMIC_TYPE_TABLE = "user_dynamic_types";
	public static final String WORK_TYPE_TABLE = "user_work_types";
	
	public static final String FAQ_DATATYPE_IMG = "com.app.faq.image";

	// ==================================== 更新相关常量  ====================================
	public static final String IS_UPDATE_NOW = "1"; //必须更新
	public static final String NO_UPDATE_NOW = "0"; //不是必须更新

	// ==================================== 加好友相关常量  ====================================
	public static final Integer INVITE_USER_WITH_ADDRESS_BOOK = 0;
	public static final Integer INVITE_USER_WITH_SINAWEIBO = 1;
	
	// ==================================== 查询条数主页显示条数常量  ====================================
	public static final Integer SEARCH_PAGE_HOME_PAGESIZE = 5;
	
	// ==================================== 信息提示模板  ====================================
	public static final String PAY_MSG_CONTENT = "充值的{content}爱豆已到达您的账户";
	public static final String APPLY_MSG_CONTENT = "恭喜你，你已成功申请练习生";
	// 描述
	public static final String AIDOU_BODY = "偶像计划{num}个爱豆";
	
	// ==================================== 申请练习生相关常量  ====================================
//	public static final String APPLY_kjuns_STATE_NO = "-1";  //未申请
	public static final String APPLY_kjuns_STATE_REQUESTED = "0"; //已申请
	public static final String APPLY_kjuns_STATE_IN_REVIEW = "1"; //审核中
	public static final String APPLY_kjuns_STATE_REVIEW_PASS = "2"; //审核通过
	public static final String APPLY_kjuns_STATE_REJECT = "3"; //拒绝

	public static final String APPLY_kjuns_STATE_STEP1 = "21"; //完善信息
	public static final String APPLY_kjuns_STATE_STEP2 = "22"; //身份证明
//	public static final String APPLY_kjuns_STATE_STEP3 = "23"; //提交作品

	// ==================================== 业务常量  ==================================== 
	public static final Integer VOTE_HOT_SORT = 0; //热门
	public static final Integer VOTE_DATE_SORT = 1; //时间顺序
	
	// ==================================== 业务常量  ==================================== 
	public static final Integer SEND_CONTENT = 1;
	public static final Integer REPLY_OTHER_COMMENT = 2;
	public static final Integer SHARE = 3;
	public static final Integer COMMENT = 4;
	public static final Integer FOCUS = 5;
	public static final Integer LIKE = 6;
	public static final Integer DEAL = 7;
	public static final Integer SIGN = 8;
	
	// =========== 复合ID长度 ================
	public static final Integer FH_ID_LEN = 27;
	
	// =========== 推荐用户个数 ================
	public static final Integer RECOMMENT_USER_LEN = 5;
	
	// =========== 分享常量 =============
	public static final String QQ = "QQ";
	public static final String WX = "WX";
	public static final String WB = "WB";
	
	// ========= 动态title空提示 =====
	public static final String DYNAMIC_TITLE_NULL = "这个人很懒，什么都没留下😷";
	
	// ========== 环信 schema =====
	
	public static final String API_HTTP_SCHEMA = "https";
	// DEFAULT_PASSWORD
	public static final String DEFAULT_PASSWORD = "123456";
	public static final String MESSAGE_USERS = "users";
	public static final String MESSAGE_CHATGROUPS = "chatgroups";
	public static final String MESSAGE_CHATROOMS = "chatrooms";
	
	public static final String MESSAGE_MSG_TYPE_TXT = "txt";
	public static final String MESSAGE_MSG_TYPE_IMG = "img";
	public static final String MESSAGE_MSG_TYPE_AUDIO = "audio";
	public static final String MESSAGE_MSG_TYPE_VIDEO = "video";
	public static final String MESSAGE_MSG_TYPE_CMD = "cmd";
	
	public static final String MESSAGE_EXT_DATA_TYPE_BARRAGE = "barrage";
	public static final String MESSAGE_EXT_DATA_TYPE_IMG = "img";
	public static final String MESSAGE_EXT_DATA_TYPE_STATISTICS = "statistics";
	public static final String MESSAGE_EXT_DATA_TYPE_RANK = "rank";
	public static final String MESSAGE_EXT_DATA_TYPE_STATUS= "status";
	public static final String MESSAGE_EXT_DATA_TYPE_OTHER = "other";
	
	public static final String DANMU_TYPE_NOMAL = "normalDanmu";
	public static final String DANMU_TYPE_DEAL = "dealDanmu";
	
	/// =========== 个人里程碑======= 
	public static final Integer MILESTONE_SEND_TYPE_PIC = 1; 
	
	public static final String USER_MILESTONE = "user_milestone";
	public static final String kjuns_MILESTONE = "kjuns_milestone";
	//id填充000000000000000000000000000 999999999999999999999999999
	public static final String ID_FILL = "999999999999999999999999999";
	
	public static final String ID_10_FILL = "0000000000";
	
	public static final String [] kjuns_NODE_FIELDS = new String[]{"register","lv1","lv20","lv40"};
	public static final String [] kjuns_FIELDS = new String[]{"register","lv1","lv20","lv40","focus300","like500","comment700",
		"share800","deal100000"};
									
	public static final String [] NORMAL_NODE_FIELDS = new String[]{"lv1","lv20"};
	public static final String [] NORMAL_FIELDS = new String[]{"lv1","lv20","deal_1st","deal5000",
		"deal50000","deal100000"};
	
	public static final Map<Integer, String> MILESTONE_kjuns_KEY = new HashMap<Integer, String>() { 
		private static final long serialVersionUID = -1388612799003153815L;{
			put(0,"加入偶像计划");put(1, "偶像学员初等部学员");put(2, "偶像学员中等部学员");
			put(3, "偶像学员高等部学员");put(4, "获得300个粉丝 完成任务:第一闪耀");
			put(5, "获得500个赞 完成任务:初试舞台");put(6, "获得700条回复 完成任务:崭露头角");
			put(7, "获取800次分享 完成任务:小有人气");
			put(8, "获得100000颗爱豆 完成任务:打开梦想大门");
		}
	};
	
	public static final Map<Integer, String> MILESTONE_USER_KEY = new HashMap<Integer, String>() { 
		private static final long serialVersionUID = -1388612799003153825L;{
			put(0, "加入偶像计划");put(1, "天降的粉丝");put(2, "第一次喂豆 完成任务:怦然心动");
			put(3, "喂豆累计5000 完成任务:默默支持");put(4, "喂豆累计50000 完成任务:爱的付出");
			put(5, "喂豆累计100000 完成任务:应援小天使");
		}
	};
	
	public static final Map<Long, String> MILESTONE_kjuns_LV_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153835L;{
			put(1l, "lv1");put(20l, "lv20");put(40l, "lv40");
		}
	};
	
	public static final Map<Long, String> MILESTONE_USER_LV_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153845L;{
			put(1l, "lv1");put(20l, "lv20");put(40l, "lv40");
		}
	};
	
	public static final Map<Long, String> MILESTONE_USER_DEAL_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153855L;{
			put(1l, "deal_1st");put(5000l, "deal5000");put(50000l, "deal50000");put(100000l, "deal100000");
		}
	};
	
	public static final Map<Long, String> MILESTONE_kjuns_DEAL_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153865L;{
			put(100000l, "deal100000");
		}
	};
	
	public static final Map<Long, String> MILESTONE_kjuns_FOCUS_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153875L;{
			put(300l, "focus300");
		}
	};
	
	public static final Map<Long, String> MILESTONE_kjuns_LIKE_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153865L;{
			put(300l, "like300");
		}
	};
	
	public static final Map<Long, String> MILESTONE_kjuns_COMMENT_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153855L;{
			put(700l, "comment700");
		}
	};
	
	public static final Map<Long, String> MILESTONE_kjuns_SHARE_KEY = new HashMap<Long, String>() { 
		private static final long serialVersionUID = -1388612799003153895L;{
			put(800l, "share800");
		}
	};
}
