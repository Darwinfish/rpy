package com.zr.app;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zhengzr14796 on 2016/6/14.
 */
public class NumUtils {

    public static String abandonPercent(String num){
        DecimalFormat numFormat = new DecimalFormat("0.00");
        return numFormat.format(new BigDecimal(num).multiply(new BigDecimal(100)));
    }
}
