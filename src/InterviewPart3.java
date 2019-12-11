import java.lang.reflect.Array;
import java.util.*;

//乘积最大序列
class Solution3_1 {
    public int maxProduct(int[] nums) {

        //dp[i] 以i结尾的最大值
        int max = nums[0];
        int min = nums[0];
        int result = nums[0];
        for(int i= 1 ; i< nums.length;i++){
            int last_max = max;
            int last_min = min;
            max = Math.max(nums[i]*last_max, nums[i]*last_min);
            max = Math.max(nums[i],max);

            min = Math.min(nums[i]*last_min,nums[i]*last_max);
            min = Math.min(min,nums[i]);

            result = Math.max(result,max);
        }
        return result;
    }
}

//求众数 抵消法
class Solution3_2 {
    public int majorityElement(int[] nums) {

        int count = 1;
        int temp = nums[0];

        for(int i = 1 ; i< nums.length;i++){
            if(temp != nums[i]){
                count--;
            }else{
                count++;
            }

            if(count == -1){
                temp = nums[i];
                count =1;
            }
        }
        return temp;
    }
}

//旋转数组
//mod 避免死循环 数组越界
class Solution3_3 {
    public void rotate(int[] nums, int k) {


        int step = nums.length;
        int index = 0;
        int last = nums[0];
        int is_loop = 0;

        while(step > 0){
            index = (index+k)%nums.length;
            if(is_loop == index){
                int temp = last;
                //swap(last,nums[index]);
                last = nums[index];
                nums[index] = temp;
                step--;

                if(index == nums.length-1)break;
                index ++;
                is_loop ++;
                last = nums[index];
                continue;
            }
            int temp = last;
            //swap(last,nums[index]);
            last = nums[index];
            nums[index] = temp;
            step--;
        }
    }

}

//存在重复数组
class Solution3_4 {
    public boolean containsDuplicate(int[] nums) {

        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i = 0 ; i< nums.length ;i++){
            if(map.containsKey(nums[i])) return true;
            else map.put(nums[i],1);
        }

        return false;
    }
}

//移动0
//法1：ca 慢

//class Solution3_5 {
//    public void moveZeroes(int[] nums) {
//        int right = nums.length;
//        for(int i = 0 ; i< right; i++){
//            if(nums[i] != 0) continue;
//            for(int j = i+1 ; j <right;j++ ){
//                nums[j-1] = nums[j];
//            }
//            right--;
//            nums[right] = 0;
//            i= -1;
//        }
//    }
//}
class Solution3_5 {
    public void moveZeroes(int[] nums) {
       int n = 0;
       for(int i = 0 ; i< nums.length ;i++){
           if(nums[i]!= 0){
               nums[i-n] = nums[i];
           }else{
               n++;
           }
       }
       for(int i = nums.length- n ; i<nums.length;i++){
           nums[i] = 0;
       }
    }
}

//打乱数组
//class Solution3_6 {
//
//    public Solution3_6(int[] nums) {
//
//    }
//
//    /** Resets the array to its original configuration and return it. */
//    public int[] reset() {
//
//    }
//
//    /** Returns a random shuffling of the array. */
//    public int[] shuffle() {
//
//    }
//}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */

//两个数组的交集
class Solution3_7 {
    public int[] intersect(int[] nums1, int[] nums2) {

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        List<Integer> result = new ArrayList<Integer>();
       // int len = Math.min(nums1.length,nums2.length);
        int index1 = 0 ;
        int index2 = 0;
        while(index1< nums1.length && index2< nums2.length){
            if(nums1[index1] < nums2[index2]) index1++;
            else if(nums1[index1] > nums2[index2]) index2++;
            else {
                result.add(nums1[index1]);
                index1++;
                index2++;
            }
        }
        int[] re = new int[result.size()];
        int i = 0;
        for(Integer one:result){
            re[i] = one;
            i++;
        }
        return re;
    }
}

//递增的三元子序列!! 很好的题！！
//o(n)的动态规划无法实现
class Solution3_8 {
    public boolean increasingTriplet(int[] nums) {

        int m1 = Integer.MAX_VALUE;  //三个数中的第1个
        int m2 = Integer.MAX_VALUE;  //三个数中的第2个

        for(int i = 0 ; i<nums.length;i++){
            if(nums[i] <= m1) m1 = nums[i];
            else if(nums[i] <= m2) m2 = nums[i];
            else return true;   //比m2还大，说明成立
        }
        return false;
    }

    public void  dp(int[] nums){

    }

    //foward:    8  3  3  1  1
    //nums:      8  3  5  1  6
    //backward:  8  6  6  6  6
    public boolean method3(int[] nums){

        int len = nums.length;
        int[] foward = new int[len];
        int[] backward = new int [len];

        foward[0] = backward[0] = nums[0];
        for(int i = 1; i < len ; i++){
            foward[i] = Math.min(nums[i],nums[i-1]);
        }
        for(int i = 1; i <len ; i++){
            backward[i] = Math.max(nums[i],nums[i-1]);
        }

        for(int i = 0 ; i< len; i++){
            if(nums[i] > foward[i] && nums[i] <backward[i]) return true;
        }
        return false;
    }
}

//除自身以外数组的乘积
//不可以使用除法
//算左边的元素的乘积 ，右边元素的乘积
class Solution3_9 {
    public int[] productExceptSelf(int[] nums) {

        int [] left_product = new int[nums.length];
        int [] right_product = new int[nums.length];
        left_product[0] = 1;
        for(int i = 1 ; i<nums.length;i++){
            left_product[i] = nums[i-1] * left_product[i-1];
        }
        right_product[nums.length-1] = 1;
        for(int i = nums.length-2; i>=0 ;i--){
            right_product[i] = right_product[i+1] * nums[i+1];
        }
        int[] result = new int[nums.length];
        for(int i = 0 ; i< nums.length; i++){
            result[i] = left_product[i]* right_product[i];
        }
        return result;
    }
}

public class InterviewPart3 {
    public static void main(String[] args) {
//        Solution3_1 solution = new Solution3_1();
//        int[] nums = {-4,-3,-2};
//        int result = solution.maxProduct(nums);
//        System.out.print(result);

        Solution3_7 solution = new Solution3_7();
        int[] nums1 = {1,2,2,1};
        int[] nums2 = {2,2};
        int[] result = solution.intersect(nums1,nums2);
        System.out.print(result);
    }
}