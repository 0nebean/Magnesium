package net.onebean.gateway.common;

import net.onebean.util.StringUtils;



/**
 * 门户redis缓存使用到的key
 *
 * @author Icc
 *
 */
public class CacheConstants {

	/***
	 *
	 * @author Icc
	 * 以uag为单位存储的缓存的keys
	 */
	public static enum UagScopeKeys {
		APP_INFO("UAG:OPENAPI:APP_INFO"),
		LOGIN_SMS_CODE("UAG:OPENAPI:LOGIN:SMS:CODE"),
		UAG_LOGIN_FLAG_DEVICETOKEN_KEY("UAG:OPENAPI:LOGIN:FLAG:DEVICETOKEN"),
		UAG_LOGIN_FLAG_ACCOUNT_KEY("UAG:OPENAPI:LOGIN:FLAG:ACCOUNT"),
		UAG_OPENAPI_LOGIN_SMS_CODE("UAG:OPENAPI:LOGIN:SMS:CODE"),
		UAG_SMS_SEND_COUNT_PHONE_NUMBER_AN_HOUR("UAG:SMS:SEND:COUNT:PHONE:NUMBER:AN:HOUR"),
		UAG_SMS_SEND_IP_COUNT_DAY("UAG:SMS:SEND:IP:COUNT:DAY"),
		APP_API_RELATION("UAG:OPENAPI:APP_API_RELATION"),
		URI_API_RELATION("UAG:OPENAPI:URI_API_RELATION"),
		IP_ADDRESS_WHITE_LIST("UAG:OPENAPI:IP_ADDRESS_WHITE_LIST"),
		UAG_OPENAPI_UNLOGIN_ACCESS_WHITELIST_API("UAG:OPENAPI:UNLOGIN:ACCESS:WHITELIST:API"),
		ACCESSTOKEN_KEY("UAG:OPENAPI:ACCESSTOKEN"),
		PRIVATE_TOKEN_KEY("PRIVATETOKEN"),
		;

		private String value;

		private UagScopeKeys(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}

	}

	/**
	 * 生成key
	 * @param keys keys
	 * @return key
	 */
	public static String generateKey(String ...keys){
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : keys) {
			if(StringUtils.isNotEmpty(key)){
				stringBuilder.append(key).append(":");
			}
		}
		String key = stringBuilder.toString();
		return key.substring(0,key.length()-1);
	}


}

