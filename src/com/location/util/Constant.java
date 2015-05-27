package com.location.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Constant {
	/**
	 * �����ռ�
	 */
	public static final String NAMESPACE = "http://WebXml.com.cn/";
	/**
	 * ��ȡ��������ϢURL
	 */
	public static String URL = "http://www.webxml.com.cn/webservices/MobileCodeWS.asmx";
	//public static String URL = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
	/**
	 * ��ù����ط�����
	 */
	public static String GET_LOCATION_METHOD_NAME = "getMobileCodeInfo";
	/**
	 * ��ù�����soapAction
	 */
	public static String GET_LOCATION_SOAP_ACTION = "http://WebXml.com.cn/getMobileCodeInfo";
	/**
	 * ��ȡ�����ط�������key
	 */
	public static String GET_LOCATION_RESULT_NAME = "getMobileCodeInfoResult";
	/**
	 * ��ȡ�ַ����е�����
	 * @param oriText
	 * @return
	 */
	public static List<String> GetChineseWord(String oriText)
	{
		List<String> chineseWordsList = new ArrayList<String>();
		String regex="([\u4e00-\u9fa5]+)";
		Matcher matcher = Pattern.compile(regex).matcher(oriText);
		while(matcher.find()){
			chineseWordsList.add(matcher.group());
		}
		return chineseWordsList;
	}
}
