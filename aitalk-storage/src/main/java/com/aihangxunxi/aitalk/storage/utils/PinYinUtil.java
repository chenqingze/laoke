package com.aihangxunxi.aitalk.storage.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {

	private static String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static String num = "1234567890";

	/**
	 * 得到 全拼
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {
		char[] t1 = null;
		if (src == null) {
			return "#";
		}

		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				}
				else {
					t4 += Character.toString(t1[i]);
				}
			}
			t4 = t4.substring(0, 1);
			if (abc.contains(t4)) {
				t4 = t4.toUpperCase();
			}
			else if (num.contains(t4)) {
				t4 = "#";
			}
			else {
				t4 = "#";
			}
			return t4;
		}
		catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		t4 = t4.substring(0, 1);
		if (abc.contains(t4)) {
			t4 = t4.toUpperCase();
		}
		else if (num.contains(t4)) {
			t4 = "#";
		}
		else {
			t4 = "#";
		}
		return t4;
	}

	/**
	 * 得到中文首字母
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		if (str == null) {
			return "#";
		}
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			}
			else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 将字符串转移为ASCII码
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

}
