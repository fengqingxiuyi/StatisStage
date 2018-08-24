package com.fqxyi.statisstageserver.common.util;

/**
 * @author ShenBF
 * @desc TextUtil
 * @date 2018/8/24
 */
public class TextUtil {

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

}
