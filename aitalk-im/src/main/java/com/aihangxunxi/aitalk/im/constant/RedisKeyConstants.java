package com.aihangxunxi.aitalk.im.constant;

/**
 * Redis key 常量
 *
 * @version wangchaochao
 * @version 3.0 2020/4/11
 */
public class RedisKeyConstants {

	/**
	 * 存放用户信息的文件夹 存放用户头像 昵称 店铺相关 key的写法 USER_KEY_PRE + 用户的ID
	 */
	public static final String USER_KEY_PRE = "user:";

	/**
	 * 存放用户信息的文件夹 存放用户头像 昵称 店铺相关 key的写法 USER_KEY_PRE + 用户的ID
	 */
	public static final String SYSTEM_NUMBER = "system-number:";

	/**
	 * 信息的浏览量文件夹 存放信息的浏览量 key = PAGEVIEWS_KEY_PRE + 信息的ID
	 */
	public static final String INFOVIEW_KEY_PRE = "info-view:";

	/**
	 * 信息的点赞文件夹 存放信息的点赞量 key = PAGEVIEWS_KEY_PRE + 信息的ID
	 */
	public static final String INFOSUPPORT_KEY_PRE = "info-support:";

	/**
	 * 信息的文件夹 存放信息的转发量 key = PAGEVIEWS_KEY_PRE + 信息的ID
	 */
	public static final String INFORWARD_KEY_PRE = "info-forward:";

	/**
	 * 信息的文件夹 存放信息的收藏量 key = PAGEVIEWS_KEY_PRE + 信息的ID
	 */
	public static final String INFOCOLLECT_KEY_PRE = "info-collect:";

	/**
	 * 手机验证码 释放时间范围需要1分钟 key = CAPTCHA_KEY_PRE + 手机号
	 */
	public static final String CAPTCHA_KEY_PRE = "captcha:";

	/**
	 * 验证码滑块 释放时间范围需要1天 key = CAPTCHA_SLIDER_KEY_PRE + 设备ID
	 */
	public static final String CAPTCHA_SLIDER_KEY_PRE = "captcha:slider:";

	/**
	 * 存放用户的授权信息access@refresh
	 */
	public static final String AUTH = "auth:";

	/**
	 * 存放的token
	 */
	public static final String ACCESS_TOKEN = "auth:access:";

	/**
	 * 存放的刷新token信息
	 */
	public static final String REFRESH_TOKEN = "auth:refresh:";

	/**
	 * 存放的小程序的授权信息access@refresh
	 */
	public static final String AUTH_MIN_PROGRAM = "auth:minProgram:";

	/**
	 * 存放的小程序的token
	 */
	public static final String MIN_PROGRAM_ACCESS_TOKEN = "auth:minProgram:access:";

	/**
	 * 存放的小程序的刷新token信息
	 */
	public static final String MIN_PROGRAM_REFRESH_TOKEN = "auth:minProgram:refresh:";

	/**
	 * 存放的小程序的授权信息access@refresh
	 */
	public static final String AUTH_H5 = "auth:h5:";

	/**
	 * 存放的小程序的token
	 */
	public static final String H5_ACCESS_TOKEN = "auth:h5:access:";

	/**
	 * 存放的小程序的刷新token信息
	 */
	public static final String H5_REFRESH_TOKEN = "auth:h5:refresh:";

	/**
	 * 系统设置表信息 sys_setting
	 */
	public static final String SYS_SETTING = "sys-setting:";

	/**
	 * sys_setting表：是否支持视频发布开关
	 */
	public static final String VIDEO_OPEN = "video_open";

	/**
	 * 视频大小限制
	 */
	public static final String VIDEO_SIZE_LIMIT = "video_size_limit";

	/**
	 * 视频时长限制
	 */
	public static final String VIDEO_DURATION_LIMIT = "video_duration_limit";

	/**
	 * 合作咨询说明图
	 */
	public static final String COOPERATIVE_CONSULTATION_INTRODUCTION_IMG = "cooperative_consultation_introduction_img";

	/**
	 * 帮助说明图
	 */
	public static final String HELP_INTRODUCTION_IMG = "help_introduction_img";

	/**
	 * sys_setting表：app爱航公告开关
	 */
	public static final String NOTICE_OPEN = "notice_open";

	/**
	 * sys_setting表：app爱航公告公布最大条数
	 */
	public static final String NOTICE_PUBLISH_COUNT = "notice_publish_count";

	/**
	 * sys_setting表：app爱航主推信息最大条数
	 */
	public static final String NOTICE_PUSH_COUNT = "notice_push_count";

	/**
	 * sys_setting表：信息服务包推广信息条数
	 */
	public static final String INFO_EXTENSION_COUNT = "info_extension_count";

	/**
	 * sys_setting表：运营活动周期开始
	 */
	public static final String OPERATE_ACTIVITY_START = "operate_activity_start";

	/**
	 * sys_setting表：运营活动周期截止
	 */
	public static final String OPERATE_ACTIVITY_END = "operate_activity_end";

	/**
	 * sys_setting表：安卓运营活动赠送服务包
	 */
	public static final String OPERATE_ACTIVITY_PRESENT_ANDROID = "operate_activity_present_android";

	/**
	 * sys_setting表：苹果运营活动赠送服务包
	 */
	public static final String OPERATE_ACTIVITY_PRESENT_IOS = "operate_activity_present_ios";

	/**
	 * sys_setting表：信息推广时图片检测合法性开关
	 */
	public static final String IMG_SCAN_OPEN = "img_scan_open";

	/**
	 * sys_setting表：店铺动态是否禁止评论总开关
	 */
	public static final String REVIEW_OPEN = "review_open";

	/**
	 * sys_setting表：自动确认收货天数限制
	 */
	public static final String AUTO_RECEIVE_DAYS_LIMIT = "auto_receive_days_limit";

	/**
	 * sys_setting表：商品销量统计天数范围
	 */
	public static final String GOODS_SALES_VOLUME_DAYS_LIMIT = "goods_sales_volume_days_limit";

	/**
	 * sys_setting表：店铺评分统计天数范围
	 */
	public static final String STORE_SCORE_DAYS_LIMIT = "store_score_days_limit";

	/**
	 * sys_setting表：店铺与骑手定位距离最大阀值
	 */
	public static final String STORE_RIDER_DISTANCE_MAXIMUM = "store_rider_distance_maximum";

	/**
	 * sys_setting表：提现手续费比例
	 */
	public static final String WITHDRAW_COMMISSION_PCT = "withdraw_commission_pct";

	/**
	 * sys_setting表：用户创建公司数量限制
	 */
	public static final String ORGANIZATION_COUNT_LIMIT = "organization_count_limit";

	/**
	 * sys_setting表：收货后可评价天数限制
	 */
	public static final String EVALUATE_DAYS_LIMIT = "evaluate_days_limit";

	/**
	 * sys_setting表：每日系统提现总金额限制
	 */
	public static final String EVERYDAY_WITHDRAW_AMOUNT_LIMIT = "everyday_withdraw_amount_limit";

	/**
	 * sys_setting表：每日系统提现单次金额限制
	 */
	public static final String EVERYDAY_WITHDRAW_SINGLE_AMOUNT_LIMIT = "everyday_withdraw_single_amount_limit";

	/**
	 * sys_setting表：用户实名认证修改次数限制
	 */
	public static final String USER_CERTIFICATION_UPDATE_COUNT_LIMIT = "user_certification_update_count_limit";

	/**
	 * sys_setting表：信息标签个数限制
	 */
	public static final String INFO_LABEL_COUNT_LIMIT = "info_label_count_limit";

	/**
	 * sys_setting表：酒店预定公告图
	 */
	public static final String HOTEL_BANNER_IMG = "hotel_banner_img";

	/**
	 * sys_setting表：酒店预订公告详情图
	 */
	public static final String HOTEL_BANNER_DETAIL_IMG = "hotel_banner_detail_img";

	/**
	 * sys_setting表：最大好友数
	 */
	public static final String FRIENDS_COUNT_LIMIT = "friends_count_limit";

	/**
	 * sys_setting表：最大创建群数
	 */
	public static final String GROUP_CREATE_COUNT_LIMIT = "group_create_count_limit";

	/**
	 * sys_setting表：群成员最大数
	 */
	public static final String GROUP_MEMBER_COUNT_LIMIT = "group_member_count_limit";

	/**
	 * sys_setting表：群成员最大数
	 */
	public static final String STORE_GOODS_COUNT_LIMIT = "store_goods_count_limit";

	/**
	 * sys_reward_setting表：爱航用户奖励时间限制
	 */
	public static final String TIME_LIMIT_USER = "time_limit_user";

	/**
	 * sys_reward_setting表：酒店用户奖励时间限制
	 */
	public static final String TIME_LIMIT_HOTEL = "time_limit_hotel";

	/**
	 * sys_reward_setting表：服务商用户奖励时间限制
	 */
	public static final String TIME_LIMIT_AGENT = "time_limit_agent";

	/**
	 * sys_reward_setting表：购买服务包爱航用户奖励比例
	 */
	public static final String SCALE_SERVICE_PACK_USER = "scale_service_pack_user";

	/**
	 * sys_reward_setting表：购买服务包酒店用户奖励比例
	 */
	public static final String SCALE_SERVICE_PACK_HOTEL = "scale_service_pack_hotel";

	/**
	 * sys_reward_setting表：购买服务包服务商用户奖励比例
	 */
	public static final String SCALE_SERVICE_PACK_AGENT = "scale_service_pack_agent";

	/**
	 * sys_reward_setting表：酒店预订爱航用户奖励比例
	 */
	public static final String SCALE_HOTEL_RESERVATION_USER = "scale_hotel_reservation_user";

	/**
	 * sys_reward_setting表：酒店预订酒店用户奖励比例
	 */
	public static final String SCALE_HOTEL_RESERVATION_HOTEL = "scale_hotel_reservation_hotel";

	/**
	 * sys_reward_setting表：酒店预订服务商用户奖励比例
	 */
	public static final String SCALE_HOTEL_RESERVATION_AGENT = "scale_hotel_reservation_agent";

	/**
	 * sys_reward_setting表：计划爱航用户奖励时间限制
	 */
	public static final String PLAN_TIME_LIMIT_USER = "plan_time_limit_user";

	/**
	 * sys_reward_setting表：计划酒店用户奖励时间限制
	 */
	public static final String PLAN_TIME_LIMIT_HOTEL = "plan_time_limit_hotel";

	/**
	 * sys_reward_setting表：计划服务商用户奖励时间限制
	 */
	public static final String PLAN_TIME_LIMIT_AGENT = "plan_time_limit_agent";

	/**
	 * sys_reward_setting表：计划购买服务包爱航用户奖励比例
	 */
	public static final String PLAN_SCALE_SERVICE_PACK_USER = "plan_scale_service_pack_user";

	/**
	 * sys_reward_setting表：计划购买服务包酒店用户奖励比例
	 */
	public static final String PLAN_SCALE_SERVICE_PACK_HOTEL = "plan_scale_service_pack_hotel";

	/**
	 * sys_reward_setting表：计划购买服务包服务商用户奖励比例
	 */
	public static final String PLAN_SCALE_SERVICE_PACK_AGENT = "plan_scale_service_pack_agent";

	/**
	 * sys_reward_setting表：计划酒店预订爱航用户奖励比例
	 */
	public static final String PLAN_SCALE_HOTEL_RESERVATION_USER = "plan_scale_hotel_reservation_user";

	/**
	 * sys_reward_setting表：计划酒店预订酒店用户奖励比例
	 */
	public static final String PLAN_SCALE_HOTEL_RESERVATION_HOTEL = "plan_scale_hotel_reservation_hotel";

	/**
	 * sys_reward_setting表：计划酒店预订服务商用户奖励比例
	 */
	public static final String PLAN_SCALE_HOTEL_RESERVATION_AGENT = "plan_scale_hotel_reservation_agent";

	/**
	 * sys_reward_setting表：爱航用户奖励规则生效时间
	 */
	public static final String USER_RULE_ACTIVE_TIME = "user_rule_active_time";

	/**
	 * sys_reward_setting表：酒店用户奖励规则生效时间
	 */
	public static final String HOTEL_RULE_ACTIVE_TIME = "hotel_rule_active_time";

	/**
	 * sys_reward_setting表：服务商用户奖励规则生效时间
	 */
	public static final String AGENT_RULE_ACTIVE_TIME = "agent_rule_active_time";

	/**
	 * 信息的评论量文件夹 存放信息的评论量 key = PAGEVIEWS_KEY_PRE + 信息的ID
	 */
	public static final String INFOCOMMENT_KEY_PRE = "info_comment:";

	/**
	 * 支付宝开发者应用私钥，由开发者自己生成
	 */
	public static final String ALI_PAY_PRIVATE_KEY = "aliPay:privateKey";

	/**
	 * 支付宝公钥，由支付宝生成
	 */
	public static final String ALI_PAY_PUBLIC_KEY = "aliPay:publicKey";

	/**
	 * 微信支付公众号秘钥
	 */
	public static final String WECHAT_PAY_SECRET = "wechat:secret:pay";

	/**
	 * 微信登录授权秘钥
	 */
	public static final String WECHAT_AUTH_SECRET = "wechat:secret:auth";

	/**
	 * 微信支付证书密码
	 */
	public static final String WECHAT_PAY_CERT_PWD = "wechat:certPwd";

	/**
	 * 微信登录的code
	 */
	public static final String WECHAT_LOGIN_CODE = "wechat:login:code:";

	/**
	 * 酒店系统设置表信息 hotel_sys_setting
	 */
	public static final String HOTEL_SYS_SETTING = "hotel_sys-setting:";

	/* 中库之家会员折扣默认:0.8 */
	public static final String PARTNER_DISCOUNT = "partnerDiscount";

	/* 爱航会员折扣默认:9折 aihangDiscount; */
	public static final String AIHANG_DISCOUNT = "aihangDiscount";

	/* 门市价计算倍数默认:1.5 */
	public static final String RACKRATE_MULTIPLE = "rackRateMultiple";

	/* 接单超时设置默认:60分钟 */
	public static final String TAKE_ORDER_TIME_OUT_SETTIRNG = "rackRateMultiple";

	/* 操作超时判定时间范围开始默认:6:00 */
	public static final String TIME_OUT_FRAME_START = "timeoutFrameStart";

	/* 操作超时判定时间范围结束默认:23:00 */
	public static final String TIME_OUT_FRAME_END = "timeoutFrameEnd";

	/* 入住后可评价天数限制默认:30天 */
	public static final String HOTEL_EVALUATE_DAYS_LIMIT = "evaluateDaysLimit";

	/* 自动确认入住天数默认:7(天) */
	public static final String AUTO_STAY_LIMIT = "autoStayLimit";

	/* 可预定酒店天数限制默认:7(天) */
	public static final String RESERVE_DAYS_LIMIT = "reserveDaysLimit";

	/**
	 * Database Column Remarks: 酒店详情打折图片
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column public.sys_hotel_setting.hotel_detail_discount_img
	 *
	 * @mbg.generated Thu Jul 02 13:51:52 CST 2020
	 */
	private String hotelDetailDiscountImg;

	/* 酒店详情打折图片 */
	public static final String HOTEL_DETAIL_DISCOUNT_IMG = "hotelDetailDiscountImg";

	/* 查看房型打折图片 */
	public static final String ROOM_TYPE_DISCOUNT_IMG = "roomTypeDiscountImg";

	/* 确认订单打折图片 */
	public static final String CONFIRM_ORDER_DISCOUNT_IMG = "confirmOrderDiscountImg";

	/* 预定首页图片 */
	public static final String INDEX_PAGE_IMG = "indexPageImg";

	/* 中库会之家会员卡说明图 */
	public static final String CARD_INTRODUCTION_IMG = "cardIntroductionImg";

	/* 奖励规则说明图 */
	public static final String REWARD_RULE_IMG = "rewardRuleImg";

	/* 预定首页详情图片 */
	public static final String INDEX_PAGE_DETAIL_IMG = "indexPageDetailImg";

}
