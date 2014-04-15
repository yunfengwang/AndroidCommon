package com.wyf.android.common.util;

public class StringUtil {

  public static boolean isNullOrEmpty(String str) {
    return ("" == str || null == str);
  }

  public static boolean isNotNullNorEmpty(String str) {
    return !isNullOrEmpty(str);
  }
  
}