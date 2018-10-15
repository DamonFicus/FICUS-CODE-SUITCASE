package leetcode;

import java.util.*;

/**
 * Created by DanmonFicus on 2018/10/15.
 * LeetCode :删除排序数组中的重复项
 * 输入[1,1,2]
 * 输出[1,2]
 * Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 *
 * example:
 * Given nums = [0,0,1,1,1,2,2,3,3,4],
 Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.
 It doesn't matter what values are set beyond the returned length.
 */
public class RemoveDuplicates {

    /**
     * 使用工具方式直接输入源的时候做去重
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        Set set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }
        Object[] afterArray = set.toArray();
        Arrays.sort(afterArray);
        System.out.println(Arrays.deepToString(afterArray));
        return afterArray.length;
    }

    /**
     * 1.有序
     * 2.去重
     * @param disorderNums
     * @return
     */
    public int removeDuplicates2(int[] disorderNums){
        System.out.println("beforeSort:"+ Arrays.toString(disorderNums));
        //排序
        for (int i = 0; i < disorderNums.length; i++) {
            for (int j = 0; j < disorderNums.length; j++) {
                if (disorderNums[i] < disorderNums[j]) {
                    int temp = disorderNums[i];
                    disorderNums[i] = disorderNums[j];
                    disorderNums[j] = temp;
                }
            }
        }
        System.out.println("afterSort:"+Arrays.toString(disorderNums));
        //去重
        ArrayList arrayList = new ArrayList();
        for(int i:disorderNums){
            if(arrayList.contains(i)){
                continue;
            }else{
                arrayList.add(i);
            }
        }

        int [] newIntArray= new int[arrayList.size()];
        for(int i=0;i<arrayList.size();i++){
            newIntArray[i]= (int) arrayList.get(i);
        }
        disorderNums=newIntArray;
        System.out.println("afterRemove:"+Arrays.toString(disorderNums));
        return disorderNums.length;
    }

    public static void main(String[] args) {

        System.out.println("=========ByCollections=========");
        int[] nums={0,0,1,1,1,2,2,3,3,4};
        RemoveDuplicates removeDuplicates = new RemoveDuplicates();
        int returnLength=removeDuplicates.removeDuplicates(nums);
        System.out.println("returnLength:"+returnLength);

        System.out.println("=========Bymanul=========");

//        int[] disorderNums={0,4,1,3,2,2,1,3,0,4};
        int[] disorderNums={1,1,2};
        int returnLength2=removeDuplicates.removeDuplicates2(disorderNums);
        System.out.println("returnLength2:"+returnLength2);
    }
}
