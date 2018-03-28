package com.keliii.common.util;

import javassist.bytecode.ByteArray;

import java.math.BigInteger;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by keliii on 2017/6/27.
 */
public class BaseCodeUtil {

    private final static int head = 3000;
    private final static String key = "i3jn4b5hf8w0dmcnwz8djenguos0wplsmiv84nvc7";

    private final static char[] chars = new char[]{
            'Q', 'A', 'Z', 'S', 'E',
            'D', 'C', 'F', 'T', 'G',
            'B', 'H', 'U', 'J', 'M',
            'K', 'L', 'X', 'V', 'N',
            'W', 'R', 'Y', 'P'};

    private final static int length = chars.length;

    private static String RC4_key = "123456osidkflsdivoielcilwet[sdf9a7s6898d9fghk57gfk46fs8ds9f97934jkmnbe9vnkj412k49sdr09ufalskjad8cvsdifaerjihefwexc7890";

    public static void main(String[] args) {

        Set<String> set = new HashSet<>();
        Long start = System.currentTimeMillis();
        for (int i = 10000; i < 20000; i++) {
            String code = getCode(i);
            System.out.println(code);
            set.add(code);
        }

//        String code = getCode(10002);
//        System.out.println("获取code：" + code);
//        decryCode(code);


        System.out.println(System.currentTimeMillis() - start);
        System.out.println(set.size());

    }

    /**
     * 解析code
     *
     * @param code
     */
    private static void decryCode(String code) {
        BigInteger data = toBigInteger(code);
        System.out.println("解码1：" + data);

//        data.pow(BigInteger.valueOf(1281456901841743691l))


//        data = ;


//        System.out.println("解码2：" + re);

    }

    /**
     * String类型的code 转 BigInteger
     */
    private static BigInteger toBigInteger(String code) {
        BigInteger data = BigInteger.ZERO;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            data = data.add(BigInteger.valueOf(getCharsIndex(c)).multiply(BigInteger.valueOf(length).pow(i)));
        }
        return data;
    }

    /**
     * 获得字符所在位置（对应的数值）
     *
     * @param ch
     * @return
     */
    private static int getCharsIndex(char ch) {
        for (int i = 0; i < chars.length; i++) {
            if (ch == chars[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取code
     *
     * @param number
     * @return
     */
    private static String getCode(int number) {
        /**
         *
         * {couponSchemeId[16]-redeemSerialNum[32]-checkCode[15]}
         *  couponSchemeId: 0 - 65535
         *  redeemSerialNum: int
         *  checkCode: 15 = 8 + 7   randomNum[8] + checkCode[7]
         *
         * long p = 9382582933l, q = 9742582933l;
         * 分解因子
         * p 331*3*2*2*2362181
         * q 3*2*2*811881911
         * 最小公倍数L
         * 331*3*2*2*2362181*811881911 = 7617549360948143052
         *
         * 互质积N：140737489008702360
         *
         * 设
         * E = 107
         *
         * 解密密匙
         * DE-KL=1
         * D = 1281456901841743691
         *
         *
         *
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
        while (l1 < 0xF) {
            l1 = random.nextInt(0xFF);
        }
        long l2 = a % l1;
        a = a << 8;
        a = a | l1;
        a = a << 7;
        a = a | l2;

//        System.out.println("原数据：" + a);

        // RSA

        int p = 47;
        BigInteger data = BigInteger.valueOf(number).pow(p);
        data = data.remainder(BigInteger.valueOf(140737489008702360l));
        System.out.println("后数据：" + data);
        return toHString(data);

        //RC4 Base24

//        byte[] data = RC4.encry_RC4_byte(a + "", RC4_key);
//        StringBuffer sb = new StringBuffer();
//        long d = 0;
//        int f = 0;
//        for (int i = 0; i < data.length; i++) {
//            if (f < 3 && i < data.length - 1) {
//                d = d << 8;
//                d = d | data[i];
//                f++;
//            } else {
//                d = d & 0xFFF;
//                System.out.println(d);
//                sb.append(longToCode(d));
//                f = 0;
//                d = 0;
//                if (i == data.length - 1) break;
//                i--;
//            }
//        }
//        return sb.toString();

        //RC4 Base64

//        return RC4.encry_RC4_string(""+a,RC4_key);
    }

    private static String longToCode(final long num) {
        return toHString(BigInteger.valueOf(num));
    }

    /**
     * 将BigInteger的数值编码为字串
     *
     * @param count
     * @return
     */
    private static String toHString(final BigInteger count) {
        //进制转换后映射字符
        StringBuffer sb = new StringBuffer();

        BigInteger n = new BigInteger(count.toString());
        BigInteger[] r = null;

        BigInteger blength = BigInteger.valueOf(length);
        while (n.compareTo(blength) > 0) {
            r = n.divideAndRemainder(blength);
            n = r[0];
            sb.append(chars[r[1].intValue()]);
        }
        if (r == null) return null;

        sb.append(chars[r[0].intValue()]);
//        System.out.println("sb = " + sb.toString());
        return sb.toString();
    }


    private static class DecryResult {
    }


    /**
     * 编码集
     * {'Q','A','Z','S','E',
     * 'D','C','F','T','G',
     * 'B','H','U','J','M',
     * 'K','L','X','V','N',
     * 'W','R','Y','P'}
     * <p>
     * 26 - I - O = 24
     * <p>
     * {couponSchemeId[6]-redeemSerialNum[48]-checkCode[10]}
     * <p>
     * <p>
     * 64 * 8 / 16
     */
}