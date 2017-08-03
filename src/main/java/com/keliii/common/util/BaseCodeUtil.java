package com.keliii.common.util;

import com.sun.tools.corba.se.idl.InterfaceGen;
import com.sun.tools.corba.se.idl.SymtabEntry;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.util.Random;

/**
 * Created by keliii on 2017/6/27.
 */
public class BaseCodeUtil {

    private final static int head = 3000;
    private final static String key = "i3jn4b5hf8w0dmcnwz8djenguos0wplsmiv84nvc7";

    private final static char[] chars = new char[]{'Q', 'A', 'Z', 'S', 'E', 'D', 'C', 'F', 'T', 'G', 'B', 'H', 'U', 'J', 'M', 'K', 'L',
            'X', 'V', 'N', 'W', 'R', 'P'};

    private final static int length = chars.length;

    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        for (int i = 10000; i < 20000; i++) {
            getCode(i);
        }
        System.out.println(System.currentTimeMillis() - start);

    }

    private static String getCode(int number) {
        /**
         *
         * {couponSchemeId[16]-redeemSerialNum[32]-checkCode[15]}
         *  couponSchemeId: 0 - 65535
         *  redeemSerialNum: int
         *  checkCode: 15 = 8 + 7   randomNum[8] + checkCode[7]
         *
         */
        long head = 1000l;
//        System.out.println("head = " + Long.toBinaryString(head));
        long a = head << 32;
//        System.out.println("a = " + Long.toBinaryString(a));

        Long m = Long.valueOf(number);
//        System.out.println("m = " + Long.toBinaryString(m));
        a = a | m;
//        System.out.println("a = " + Long.toBinaryString(a));
        Random random = new Random(System.currentTimeMillis());
        long l1 = random.nextInt(0xFF);
        while(l1 < 0xF) {
            l1 = random.nextInt(0xFF);
        }
        long l2 = a % l1;
        a = a << 8;
        a = a | l1;
        a = a << 7;
        a = a | l2;
//        System.out.println("a = " + Long.toBinaryString(a));
//        System.out.println("a = " + a);


        //进制转换后映射字符
        StringBuffer sb = new StringBuffer();
        while (a > 0) {
            long y = a % length;
            a = a / length;
            sb.append(chars[(int) y]);
        }
        System.out.println("sb = " + sb.toString());
        return sb.toString();
    }


    /**
     * 编码集
     * {'Q','A','Z','S','E','D','C','F','T','G','B','H','U','J','M','K','L',
     'X','V','N','W','R','Y','P'}
     *
     * 26 - I - O = 24
     *
     * {couponSchemeId[6]-redeemSerialNum[48]-checkCode[10]}
     *
     *
     * 64 * 8 / 16
     *
     *
     *
     *
     */


}
