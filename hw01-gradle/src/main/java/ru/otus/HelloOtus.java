package ru.otus;
import com.google.common.math.BigDecimalMath;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HelloOtus {
    public static double hwGuavaCall(){
        return BigDecimalMath.roundToDouble(BigDecimal.valueOf(132213), RoundingMode.UP);
    }
}