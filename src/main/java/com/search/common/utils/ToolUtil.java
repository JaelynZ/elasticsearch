
package com.search.common.utils;

/**
 * @author Zhangjl
 * @date 2018/5/4 17:29
 * @description
 */
public class ToolUtil {

    private ToolUtil(){

    }


    /**
     * 判断字符串是否符合json格式
     * @param json
     * @return
     */
    public static Boolean validIsJson(String json){
        return json.matches("^[\r\f\t\\x20]*[\\{\\[][\\s\\S]*");
    }

    /**
     * 字符串null字段转换成字符串"";
     * -> str == null ? "" : str
     * @param str
     * @return
     */
    public static String nullToString(String str){
        return str == null ? "" : str;
    }
    /**
     * 整型null字段转换成字符串"";
     * -> str == null ? "" : str.toString()
     * @param str
     * @return
     */
    public static String nullToString(Integer str){
        return str == null ? "" : str.toString();
    }

    /**
     * 字符串null字段转换成目标字符串;
     * -> str == null ? targetStr : str
     * @param str
     * @return
     */
    public static String nullToString(String str, String targetStr){
        return str == null ? targetStr : str;
    }

    /**
     * 为空字段转换成目标;
     * -> ch == null ? targetCh : ch
     * @param ch
     * @param targetCh
     * @return
     */
    public static Integer nullToInteger(Integer ch, Integer targetCh){
        return ch == null ? targetCh : ch;
    }

    /**
     * 数据源与条件对象比较判断，如果相等返回指定目标源，不等返回数据源;
     * -> source == condition ? target : source
     * @param source
     * @param condition
     * @param target
     * @return
     */
    public static Object objToObj(Object source, Object condition, Object target) {
        return source == condition ? target : source;
    }

    /**
     * 判断条件是否未true,为true返回source，为false返回target;
     * -> condition ? source : target
     * @param condition
     * @param source
     * @param target
     * @return
     */
    public static Object conditionToValue(boolean condition, Object source, Object target){
        return condition ? source : target;
    }

}