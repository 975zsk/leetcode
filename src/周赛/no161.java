package 周赛;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class no161 {

    //1247 交换字符使得字符串相同
    public int minimumSwap(String s1, String s2) {
        int len = s1.length();
        int type1 = 0 ; // x y
        int type2 = 0 ; // y x
        for(int i = 0 ;i< len ;i++){
            if(s1.charAt(i)=='x' && s2.charAt(i) =='y') type1++;
            if(s1.charAt(i)== 'y' && s2.charAt(i) =='x') type2++;
        }

        if(type1%2 == 1 && type2%2 ==1){
            return type1/2 + type2/2 +2;
        }else if (type1%2 == 0 && type2%2 ==0){
            return type1/2 + type2/2 ;
        }
        return -1;
    }

    //1248 统计优美子数组
    //双指针 滑动窗口
    public int numberOfSubarrays(int[] nums, int k) {
        //step1: 记录下奇数的位置
        List<Integer> arr = new ArrayList<>();
        arr.add(-1);
        for(int i = 0 ; i< nums.length ;i++){
            if(nums[i]%2 == 1){
                arr.add(i);
            }
        }
        arr.add(nums.length);
        //step2:滑动窗口 计算公式：
        int res = 0;
        for(int index = 1 ; index+k < arr.size(); index++){
            res+= (arr.get(index) - arr.get(index-1)) * (arr.get(index+k) - arr.get(index+k-1));
        }
        return res;
    }


    //1249 移除无效的括号
    public String minRemoveToMakeValid(String s) {
        Stack<Integer> left = new Stack<Integer>();
        boolean[] invaild = new boolean[s.length()];

        StringBuilder result = new StringBuilder();
        for(int i = 0 ; i< s.length() ;i++){
            if(s.charAt(i) == '(' ){
                left.push(i);
                invaild[i] = true;
            }else if(s.charAt(i) == ')'){
                if(left.isEmpty()){
                    invaild[i] = true;
                }else{
                    invaild[left.pop()] = false;
                }
            }
        }

        for (int i = 0; i < s.length(); i++) {
            if (!invaild[i]) {
                result.append(s.charAt(i));
            }
        }

        return result.toString();
    }

    //1250 检查好数组
    //求最大公约数 gcd(a,b) = 1 等同于  存在x，y ax+by = 1
    //利用裴蜀定理，自左至右求出最大公因数即可
    //
    //    若最大公因数为1，一定存在两两互质的最大公因数，可以(使用这两个互质的公因数)实现「好数组」
    //        注：最大公因数可以通过有限次乘法运算求出
    //    若最大公因数不为1，所有数都有共同的(大于1的)公因数，不能实现「好数组」
    public boolean isGoodArray(int[] nums) {
        int res = nums[0];
        for(int i = 1; i< nums.length ;i++){
            res = gcd(res,nums[i]);
        }
        return res == 1;
    }
    private int gcd(int a ,int b){
        return b == 0 ? a : gcd(b,a%b);
    }

}
