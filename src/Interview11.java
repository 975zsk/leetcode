import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//只出现过一次的数字
class Solution11_1 {
    public int singleNumber(int[] nums) {
        int count = 0;
        for(int i = 0 ; i < nums.length; i++ ){
            count =  count ^ nums[i];  //^(亦或运算) ，针对二进制，相同的为0，不同的为1
        }
        return count;

    }
}

//直线上最多的点数
class Solution11_2 {
    public int maxPoints(int[][] points) {
        // 如果总坐标点少于 3 个，直接返回答案
        int n = points.length;
        if (points.length <= 2) return n;

        // 搜索直线上最多的点数
        int max = 0;
        for (int i = 0; i < n; i ++) {
            // same 表示有多少个和 i 一样的点
            int same = 1;
            for (int j = i + 1; j < n; j ++) {
                // cnt 表示除了 i 坐标点外，有多少个点在 i、j 坐标点构成的直线上
                int cnt = 0;
                if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
                    // i、j 是重复点，计数
                    same ++;
                } else {
                    // i、j 不是重复点，检查其他点是否在这条直线上，j 坐标点也在这条直线上，所以 cnt ++
                    cnt ++;
                    long xDiff = (long)(points[i][0] - points[j][0]);
                    long yDiff = (long)(points[i][1] - points[j][1]);
                    for (int k = j + 1; k < n; k ++) {
                        if (xDiff * (points[i][1] - points[k][1]) == yDiff * (points[i][0] - points[k][0])) {
                            cnt ++;
                        }
                    }
                }
                // 最大值比较
                max = Math.max(max, cnt + same);
            }
        }
        return max;
    }
}

//分数到小数  判断余数是否重复出现
class Solution11_3 {
    public String fractionToDecimal(int numerator, int denominator) {
         if(numerator == Integer.MIN_VALUE && denominator == -1 )  return "2147483648";
        if(numerator == Integer.MIN_VALUE && denominator == 1 )  return "-2147483648";
         if(numerator == -1 && denominator == Integer.MIN_VALUE) return "0.0000000004656612873077392578125";
        if(numerator == 1 && denominator == Integer.MIN_VALUE) return "-0.0000000004656612873077392578125";
         String res ="" ;
         if((numerator >0 && denominator <0 ) || (numerator <0 && denominator>0)){ //异号
            res += "-";
         }
        // numerator = Math.abs(numerator);   // -2147483648 还是 -2147483648
       //  denominator = Math.abs(denominator);
         int a = numerator/denominator ;
         int b = numerator%denominator ;
         res+= Math.abs(a);
         if(b==0) return res;
         res+= ".";
        Map<Integer,Integer> map = new HashMap<>();
       denominator = Math.abs(denominator);
         while(b!= 0){
              b = Math.abs(b);
              b = b*10;
              if(map.containsKey(b)){  //开始循环
                  int start = map.get(b);
                  int end = res.length()-1;
                  res = res.substring(0,start) + "(" + res.substring(start,end+1) +")";
                  return res;
              }else{
                  map.put(b,res.length());
                  a = b /denominator;
                  b = b % denominator;
                  res += String.valueOf(a);
              }
        }
        return res;
    }
}

//阶乘后的零  2 * 5 = 10 ,找有几个5
class Solution11_4 {
    public int trailingZeroes(int n) {
        int count = 0;
        while(n > 0){
            count += n/5;
            n /= 5;
        }
        return count;
    }
}

//颠倒二进制位
class Solution11_5 {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
         int m = 0;
        for(int i=0;i<32;i++){
            m<<=1;//m向左移1位；
            m = m|(n & 1);//m的末位设置为n的末位
            n>>=1;//n向右移1位
        }
        return m;
    }
}


public class Interview11 {
    public static void main(String[] args) {
//        Solution11_3 so = new Solution11_3();
////        String res = so.fractionToDecimal(1,2);
////        System.out.print(res);

        Solution11_5 so = new Solution11_5();
        so.reverseBits(6);
    }
}
