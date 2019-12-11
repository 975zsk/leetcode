
//动态规划专题

import java.awt.event.MouseAdapter;
import java.util.*;

//至少有K个重复字符的最长子串
class Solution9_1 {

    //思路1： 分治： 先统计所有字符出现的情况，有小于k的，则包含该字符的字串通通不成立，再递归去解左子串和右子串
    public int longestSubstring(String s, int k) {

        if(s.length()<k) return 0;
        if( k ==1) return s.length();
        return longestSubString(s,0,s.length()-1,k);
    }

    public int longestSubString(String s,int left,int right, int k){
        if(right-left +1 <k) return 0;
        int [] counts = new int[26];
        for(int i = left; i<=right ;i++){
            counts[s.charAt(i)-'a']++;
        }
        int maxLength = 0;  int last = left;   //不是二分，是多分。 用一个last记录当前第几段
        boolean isSplit = false;
        for(int i = left ; i<=right ;i++){
            if(isSplit(counts,s.charAt(i),k)){
                maxLength = Math.max(longestSubString(s,last,i-1,k),maxLength);
                last = i+1;
                isSplit = true;
            }else if(i == right && isSplit){           //3个分割点，，应该切4段
                maxLength = Math.max(maxLength,longestSubString(s,last,i,k));
            }
        }
        if(isSplit){
            return maxLength;
        }else{
            return right-left +1;
        }
    }

    boolean isSplit(int[] count ,char c,int k){
        int i = c-'a';
        return count[i] <k && count[i] >0 ;
    }

    //mask:103ms,遍历一遍字符串，以当前字符为字串的开头，重点为用mask快速判断一次字符串是否每个元素都已经满足了至少出现k次 o(n)
    public int longestSubstring2(String s, int k) {

       int res = 0 ,i = 0,n = s.length();
       int mask = 0 ;
       int max_idx = 1;

       while(i + k <=n) {
           int[] m = new int[26];
           mask =0;
           max_idx = i;
           for (int j = i; j < n; j++) {                           ///----
               int t = s.charAt(j) - 'a';
               m[t]++;
               if (m[t] < k) mask |= (1 << t); //将mask对应位改为1                  //important and amazing!! 如果为1，表示该字母不够k次，如果为0就表示已经出现了k次,最后判断mask=0？
               else mask &= (~(1 << t));       //改为0                              //体味一下1!
               if (mask == 0) {
                   res = Math.max(res, j - i + 1);
                   max_idx = j;                                    ///------
               }
           }
           i = max_idx+1;  //在i 到 j 之间满足，第j+1位以后不满足，直接从j开始就行
       }
       return res;
    }

}

//二叉树中的最大路径和,从一个节点出发，到达任意一个结点
class Solution9_2 {

    int whole_max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if(root == null) return 0;
        int max = df(root);
        return Math.max(whole_max,max);
    }

    public int df (TreeNode root){
         int left = 0;
         int right = 0;
         if(root.left!= null){
             left = Math.max(left,df(root.left));
         }
         if(root.right != null){
             right = Math.max(right,df(root.right));
         }
        //我自己的在路径中 +左右子树选大值
        int max = 0;
         max = Math.max(Math.max(root.val + left, root.val + right),root.val);
        //我自己在路劲中+ 左右子树 ,这种情况的话不能连接父节点，所以需要一个全局的最大值变量来记录下这个量
         whole_max = Math.max(whole_max, root.val + left+ right);
         return max;
    }
}

//最长连续序列 0(n)
//常规的是排序 nlogn
class Solution9_3 {
    //思路1：理论上不会超时，但是遇到-2157999，+2157。这种极端情况就超时了
    public int longestConsecutive(int[] nums) {

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i = 0 ; i< nums.length ;i++){
            if(nums[i] > max ) max = nums[i];
            else if(nums[i] <min) min = nums[i];

            if(!map.containsKey(nums[i])) map.put(nums[i],1);
            else{
                map.put(nums[i],map.get(nums[i])+1);
            }
        }
        int length = 0;
        int max_length = 0;
        boolean flag = true;
        for(int i = min ; i<= max ;i++){
            if(!map.containsKey(i) ) {
                if(flag) {
                    max_length = Math.max(length,max_length);
                    flag = false;
                    length = 0;
                }
            }else{
                flag = true;
                length++;
            }
        }
        if(flag)  max_length = Math.max(length,max_length);
        return max_length;
    }

    //思路2： 答案的解法，比较通用 806ms
    public int longestConsecutive2(int[] nums) {
        if(nums==null||nums.length==0) {     //判断边界条件
            return 0;
        }
        Map<Integer, Integer> map=new HashMap<Integer, Integer>();
        for (int i = 0 ; i<nums.length ;i++){
            if(!map.containsKey(nums[i])){
                map.put(nums[i],1);
                boolean has_right = false;
                //right
                int temp = nums[i];
                if(map.containsKey(temp+1)){
                    has_right = true;
                    while (map.containsKey(temp + 1)) {
                        temp++;
                    }
                    map.put(temp,map.get(temp)+1);
                    map.put(nums[i],map.get(temp));
                }
                //left
                int temp2 = nums[i];
                int count = 0 ; //比当前元素小的个数
                if(map.containsKey(temp2-1)){
                    while(map.containsKey(temp2-1)){
                        temp2--;
                        count++;
                    }
                    if(has_right){
                        map.put(temp,map.get(temp)+count);
                        map.put(temp2,map.get(temp));
                    }else{
                        map.put(temp2,map.get(temp2)+1);
                        map.put(nums[i],map.get(temp2));
                    }
                }
            }
        }
        int max=Integer.MIN_VALUE;      //用来保存最长连续序列有多少元素
        for(Integer key:map.keySet()) {
            if(max<map.get(key)) {
                max=map.get(key);
            }
        }
        return max;
    }

    //思路2的快速版 ，抓住一点： 只在字串的头尾保存长度
    public int longestConsecutive3(int[] nums) {
        if (nums == null) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int res = 0;
        for (int num : nums) {
            if (map.containsKey(num)) {
                continue;
            }
            int left = 0, right = 0;
            if (map.containsKey(num - 1)) {
                left = map.get(num - 1);
            }
            if (map.containsKey(num + 1)) {
                right = map.get(num + 1);
            }
            int len = left + right + 1;
            res = Math.max(res, len);
            map.put(num, 1);
            map.put(num -left, len);  // 字串的头保存长度
            map.put(num +right, len);  // 字串的尾保存长度
        }
        return res;

    }
}

//打家劫舍
// 0-1 问题
class Solution9_4 {
    //递归
    public int rob(int[] nums) {
          return df(nums,0,0);
    }

    public int df(int[] nums ,int index,int sum){
        if(index >= nums.length) return sum;
        return Math.max(df(nums,index+2,nums[index]+sum),df(nums,index+1,sum));
    }

    //dp
    public int rob2(int[] nums) {
        if(nums.length == 0) return 0;
        if(nums.length ==1) return nums[0];
        if(nums.length == 2) return Math.max(nums[0],nums[1]);
        int[] res = new int[nums.length];
        res[0] = nums[0];
        res[1] = Math.max(nums[0],nums[1]);
        for(int i = 2 ; i<nums.length ;i++){
            res[i] = Math.max(res[i-2]+ nums[i],res[i-1]);
        }
        return res[nums.length-1];
    }


}

//完全平方数
class Solution9_5 {

    //错误的，不能用贪心。12 ！= 9+1+1+1 dp[i] = dp[i - j * j] + dp[j*j];不成立 所以要遍历所有的完全平方数的可能
    public int numSquares(int n) {
        int [] dp = new int[n+1];
        for(int i = 0 ; i< dp.length;i++){
            dp[i] = Integer.MAX_VALUE;
        }
        for(int i = 1; i*i<=n ;i++){
            dp[i*i] = 1;
        }
        for(int i = 2 ; i<=n;i++){
            for(int j = 1; j*j<i ;j++){
                dp[i] = Math.min(dp[i],dp[i-j*j]+1);
            }
        }
        return dp[n];
    }
}

//最长上升子序列
//从o（n2） 降为o(nlogn)
class Solution9_6 {
    public int lengthOfLIS(int[] nums) {

        Queue<Integer> queue = new LinkedList<>();
        queue.add(nums[0]);
        for(int i =1; i<nums.length ;i++){
            if(nums[i] > queue.peek()) queue.add(nums[i]);
            else if( nums[i] < queue.peek()){
                //二分找他应该排列的位置

            }
        }
        return 1;
    }
    public void find(Queue<Integer> queue,int num){

    }
}

//零钱兑换
class Solution9_7 {
    public int coinChange(int[] coins, int amount) {

        if(amount ==0) return 0;
        int [] dp = new int[amount+1];
        for(int i = 0 ; i< dp.length;i++){
            dp[i] = -1;
        }
        dp[0] = 0;
//        for(int i = 0; i<coins.length && coins[i] <=amount;i++){
//            dp[coins[i]] = 1;
//        }
        for(int i = 1 ; i<=amount;i++){
            for(int j = 0; j<coins.length ;j++){
                if(coins[j] <=i && dp[i-coins[j]] != -1){  //dp[i-coins[j]]== -1  无解 ，所以不用再循环了
                    if(dp[i] == -1 || dp[i] > dp[i-coins[j]]+1)
                         dp[i] = dp[i - coins[j]] + 1;
                }
            }
        }
        return dp[amount];
    }
}

//矩阵中的最长递增路径  flooding
class Solution9_8 {
    int[][] state = {{0,1},{1,0},{0,-1},{-1,0}};
    public int longestIncreasingPath(int[][] matrix) {
        int row = matrix.length;
        if(row ==0 ) return 0;
        int col = matrix[0].length;

        int[][] dp = new int[row][col]; //以我为起点。
        int result = 0;

        for(int i = 0; i< row;i++){
            for(int j = 0; j<col;j++){
                result = Math.max(dfs(dp,matrix,i,j),result);
            }
        }
        return  result;
    }

    public int dfs (int[][] dp,int[][] matrix ,int i , int j){
        if(dp[i][j]!= 0) return dp[i][j];
        dp[i][j] = 1;
        for(int[] s :state){
            int x = i+s[0];
            int y = j+s[1];
            if(x>=0 && x<matrix.length && y>=0 && y<matrix[0].length && matrix[x][y] >matrix[i][j]){
                dp[i][j] = Math.max(dp[i][j],dfs(dp,matrix,x,y)+1);
            }
        }
        return  dp[i][j];
    }
}
public class Interview9 {
    public static void main(String[] args) {
        Solution9_7 so = new Solution9_7();
        int[] nums = {186,419,83,408};
        int result = so.coinChange(nums,6249);
        System.out.print(result);

    }
}
