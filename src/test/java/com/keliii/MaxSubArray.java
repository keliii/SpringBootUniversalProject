package com.keliii;


import static java.lang.Math.max;

/**
 * Created by keliii on 2017/6/21.
 */
public class MaxSubArray {

    public static void main(String[] args) {
//        System.out.println(StringUtil.md5("123456"));

//        System.out.println(Math.toDegrees(Math.atan(1)));


        int[] a = {-3,1,1,3,-8,1,3,2};
        try {
            System.out.println(maxSubArray(a));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int maxSubArray(int[] pData)throws Exception{
        //输入控制
        if(pData==null)
            throw new Exception("input can not be null");
        int n=pData.length;
        if(n==0)
            throw new Exception("have no data,must have one element");
        //只有一个数字的情况
        if(n==1)
            return pData[0];

        //初始化
        int[] f=new int[n]; //用数组f保存计算过程中的值
        f[0]=pData[0];
        int maxsofar=f[0];   //用maxsofar保留当前最大值

        for(int i=1;i<n;i++){
            if(f[i-1]<0)
                f[i]=pData[i];
            else
                f[i]=f[i-1]+pData[i];
            //记录最大值
            maxsofar=max(maxsofar,f[i]);
        }//end for

        return maxsofar;
    }//end maxSubArray()
}
