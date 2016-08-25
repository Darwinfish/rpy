package com.zr.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzr14796 on 2016/7/6.
 */
public class Computer {


    static List<int[]> allSorts = new ArrayList<int[]>();

    public static void main (String[] args){
        BigDecimal result1;
        BigDecimal result2;
        BigDecimal result3;
        BigDecimal bigDecimal1,bigDecimal2,bigDecimal3,bigDecimal4,bigDecimal5,bigDecimal6 ;

        int[] numArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        permutation(numArray, 0, numArray.length - 1);
        int[][] a = new int[allSorts.size()][]; // 你要的二维数组a
        allSorts.toArray(a);

        // 打印验证
        for (int i=0; i<a.length; i++) {
            int[] nums = a[i];
            for (int j=0; j<nums.length; j++) {
                bigDecimal1 = new BigDecimal(nums[0]*100+nums[1]*10+nums[2]);
                bigDecimal2 = new BigDecimal(nums[3]*10+nums[4]);
                bigDecimal3 = new BigDecimal(nums[5]);
                bigDecimal4 = new BigDecimal(nums[6]);
                bigDecimal5 = new BigDecimal(nums[7]);
                bigDecimal6 = new BigDecimal(nums[8]);
                result1 = bigDecimal1.divide(bigDecimal2,4,BigDecimal.ROUND_HALF_UP);
                result2 = bigDecimal3.subtract(bigDecimal4);
                result3 = bigDecimal5.divide(bigDecimal6,4,BigDecimal.ROUND_HALF_UP);
                if (result1.equals(result2) && result2.equals(result3)){
                    System.out.print(nums[j]);
                }
            }

        }
        System.out.println(a.length);
    }

    public static void permutation(int[] nums, int start, int end) {
        if (start == end) { // 当只要求对数组中一个数字进行全排列时，只要就按该数组输出即可
            int[] newNums = new int[nums.length]; // 为新的排列创建一个数组容器
            for (int i=0; i<=end; i++) {
                newNums[i] = nums[i];
            }
            allSorts.add(newNums); // 将新的排列组合存放起来
        } else {
            for (int i=start; i<=end; i++) {
                int temp = nums[start]; // 交换数组第一个元素与后续的元素
                nums[start] = nums[i];
                nums[i] = temp;
                permutation(nums, start + 1, end); // 后续元素递归全排列
                nums[i] = nums[start]; // 将交换后的数组还原
                nums[start] = temp;
            }
        }
    }

}
