package io.github.ludongrong.dbcoder.util;

public class StringUtil {

    public static String makeAllWordFirstLetterUpperCase(String sqlName) {
        String[] strs = sqlName.toLowerCase().split("_");
        String result = "";
        String preStr = "";
        for (int i = 0; i < strs.length; i++) {
            if (preStr.length() == 1) {
                result += strs[i];
            } else {
                result += capitalize(strs[i]);
            }
            preStr = strs[i];
        }
        return result;
    }

    public static String toJavaVariableName(String str) {
        return uncapitalize(toJavaClassName(str));
    }

    public static String toJavaClassName(String str) {
        return makeAllWordFirstLetterUpperCase(StringUtil.toUnderscoreName(str));
    }

    public static String getJavaClassSimpleName(String clazz) {
        if (clazz == null)
            return null;
        if (clazz.lastIndexOf(".") >= 0) {
            return clazz.substring(clazz.lastIndexOf(".") + 1);
        }
        return clazz;
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    /**
     * 首字母转大写或小写
     * @param str
     * @param capitalize
     * @return
     */
    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    public static String toUnderscoreName(String name) {
        if (name == null)
            return null;

        String filteredName = name;
        if (filteredName.indexOf("_") >= 0 && filteredName.equals(filteredName.toUpperCase())) {
            filteredName = filteredName.toLowerCase();
        }
        if (filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
            filteredName = filteredName.toLowerCase();
        }

        StringBuffer result = new StringBuffer();
        if (filteredName != null && filteredName.length() > 0) {
            result.append(filteredName.substring(0, 1).toLowerCase());
            for (int i = 1; i < filteredName.length(); i++) {
                String preChart = filteredName.substring(i - 1, i);
                String c = filteredName.substring(i, i + 1);
                if (c.equals("_")) {
                    result.append("_");
                    continue;
                }
                if (preChart.equals("_")) {
                    result.append(c.toLowerCase());
                    continue;
                }
                if (c.matches("\\d")) {
                    result.append(c);
                } else if (c.equals(c.toUpperCase())) {
                    result.append("_");
                    result.append(c.toLowerCase());
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

	/**
	 * 截取括号前字符
	 * 
	 * <pre>
	 *   NULL对象 -> 空字符串
	 *   空字符串 -> 空字符串
	 *   VARCHAR -转换-> VARCHAR
	 *   VARCHAR(20) -转换-> VARCHAR
	 *   VARCHAR(20)(20) -转换-> VARCHAR
	 * </pre>
	 * 
	 * @param str 字符串
	 * @return
	 */
	public static String subStringBeforeParenthesis(String str) {
		
		String beforeParenthesisStr;

        if (str == null) {
            return "";
        }
		
		int i = str.indexOf("(");
		if (i > 0) {
			beforeParenthesisStr = str.substring(0, i);
		} else {
			beforeParenthesisStr = str;
		}
		
		return beforeParenthesisStr;
	}
}
