package com.zr.app;

import java.math.BigDecimal;

/**
 * Created by zhengzr14796 on 2016/5/25.
 */
public class Test2 {
    public static void main(String[] args) {
//        BigDecimal totalSum = new BigDecimal(0);
//        for(int i=0;i<600;i++){
//            totalSum = totalSum.add(new BigDecimal("1.001"));
//        }
//        System.out.println(totalSum);

        System.out.println(String.format("%016d", Integer.parseInt(fillDouble("101.01"))));
    }

    /**
     * 将金额由元转换为分
     *
     * @param dOld
     * @return
     */
    public static String fillDouble(String dOld) {
        BigDecimal bd1 = new BigDecimal(dOld);
        BigDecimal bd2 = new BigDecimal("100");
        double fAmount = bd1.multiply(bd2).doubleValue();
        String TransAmt = "" + Math.round(fAmount);//转换为以分为单位
        return TransAmt;

    }
}
