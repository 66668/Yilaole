package com.yilaole.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号
 */

public class MobileUtils {

    /**
     * <p>
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * <p>
     * 联通：130、131、132、152、155、156、185、186、176、（170、171 小米移动）
     * <p>
     * 电信：133、153、180、189、（1349卫通）、177、（170 小米移动，这个号段即有联通的也有电信的）
     *
     * @return 有效返回true, 否则返回false
     */
    public static boolean isMobile(String mobiles) {
        // Pattern p =
        // Pattern.compile("^((147)|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
