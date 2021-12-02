package com.weaver.inte.jna.utils;


import java.math.BigDecimal;

/***
 * 
 * @author saps.weaver
 *
 */
public class StringUtils {

	public final static String empty = "";

	public static String ifNull(Object obj, String defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return obj.toString();
	}

	public static String ifNull(Object obj) {
		return ifNull(obj, empty);
	}

	public static long ifLongNull(Object obj, long defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Long.parseLong(obj.toString());
	}

	public static long ifLongNull(Object obj) {
		return ifLongNull(obj, 0);
	}

	public static double ifDoubleNull(Object obj, double defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Double.parseDouble(obj.toString());
	}

	public static double ifDoubleNull(Object obj) {
		return ifDoubleNull(obj, 0);
	}

	public static Integer ifIntegerNull(Object arg) {
		if (isNull(arg)) {
			return null;
		}
		return Integer.parseInt(arg.toString());
	}

	public static BigDecimal ifBigDecimalNull(Object arg) {
		if (isNull(arg)) {
			return BigDecimal.ZERO;
		}
		if (arg instanceof BigDecimal) {
			return (BigDecimal) arg;
		}
		return new BigDecimal(arg.toString());
	}

	public static int ifIntNull(Object obj, int defaultValue) {
		if (isNull(obj)) {
			return defaultValue;
		}
		return Integer.parseInt(obj.toString());
	}

	public static int ifIntNull(Object obj) {
		return ifIntNull(obj, 0);
	}

	public static boolean isNull(Object obj) {
		if (obj == null || empty.equals(obj.toString())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	public static boolean isempty(Object str) {
		return (str == null || empty.equals(str));
	}

}