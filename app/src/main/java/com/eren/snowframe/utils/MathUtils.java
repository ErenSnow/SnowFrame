package com.eren.snowframe.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Created by tiny on 2016/5/12.
 * 计算的公共方法
 */
public class MathUtils {
    /**
     * 计算百分比
     *
     * @param progress
     * @param max
     * @return **%
     */
    public static String percentage(double progress, double max) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(clamp(progress / max, 0, 0) * 100) + "%";
    }

    public static double clamp(double x, double min, double max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    public static double percentageValue(double prorgress, double max) {

        BigDecimal b = new BigDecimal(prorgress / max);
        return b.setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    public static double div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        return b1.divide(new BigDecimal(v2), BigDecimal.ROUND_HALF_UP, 2).doubleValue();
    }

    public static int random(int max) {
        return new Random().nextInt(max);
    }
}
