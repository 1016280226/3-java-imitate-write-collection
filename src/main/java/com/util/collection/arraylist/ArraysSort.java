package com.util.collection.arraylist;

import org.junit.Test;

/**
 * @Author: Calvin
 * @Date: 2019/4/3 17:53
 */
public class ArraysSort {

    private static int[] arrarys = {23, 37, 18, 56, 13};

    private int coutTimes;
    /**
     * 冒泡排序法
     */
    @Test
    public void test1(){

        for(int i = 0; i< arrarys.length; i++){
            for(int j =0; j<arrarys.length; j++){
                if(arrarys[i] > arrarys[j]){
                    int min = arrarys[j];
                    arrarys[j] = arrarys[i];
                    arrarys[i] = min;
                }
                coutTimes ++;
            }
        }
        for(int i = 0; i< arrarys.length; i++){
            if(i == arrarys.length - 1){
                System.out.print( arrarys[i]);
            }else{
                System.out.print( arrarys[i]+ ">");
            }
        }
        System.out.println("数组长度为:" + arrarys.length + "  运行了"+ coutTimes + "次");
    }

}
