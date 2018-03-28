package com.keliii;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by keliii on 2017/8/22.
 */
public class RSA {
    public static void main(String[] args) {
        long p = 997207, q = 997219;
        long n = p * q;
        int d = 107;
        Set<Long> set = new HashSet<>();

        long start = System.currentTimeMillis();
        for (long i = 0; i < 1000000; i++) {
            BigDecimal a = BigDecimal.valueOf(i).pow(d);
            BigDecimal b = a.remainder(BigDecimal.valueOf(n));
            set.add(b.longValue());
//            System.out.println(b.toString());
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
        System.out.println(set.size());

    }
}
