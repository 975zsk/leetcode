package sequence;

import java.lang.reflect.Array;
import java.nio.charset.CharsetEncoder;
import java.util.*;

public class TwoTo300 {
    //221 最大正方形
    public int maximalSquare(char[][] matrix) {
        int rows = matrix.length;
        if(rows == 0) return 0;
        int cols = matrix[0].length;
        if(cols == 0) return 0;

        int[][] dp = new int[rows+1][cols+1];
        int maxsqlen = 0;
        for(int i = 1; i<= rows ; i++){
            for(int j = 1 ; j <= cols ;j++){
                if(matrix[i-1][j-1] == '1'){
                    dp[i][j] = Math.min(Math.min(dp[i][j-1],dp[i-1][j]),dp[i-1][j-1]) +1;
                    maxsqlen = Math.max(maxsqlen,dp[i][j]);
                }
            }
        }
        return maxsqlen * maxsqlen;
    }

    //216 组合总数  k个数相加= n
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(n,k,0,1,new ArrayList<>(),result);
        return  result;
    }
    public void dfs(int n ,int k ,int sum,int pos,List<Integer> row,List<List<Integer>> result){
        if(row.size() == k ){
            if(sum == n) result.add(new ArrayList<>(row));
            return ;
        }
        if(pos == 10) return ;
        //剪枝
        for(int i = pos ; i <= 9; i++){
            if(sum+i > n) return ;
            if(sum +i <= n ){
                row.add(i);
                dfs(n,k,sum+i ,i+1,row,result);
                row.remove(row.size()-1);
            }
        }
    }

    //39组合总数
    //candidates 不重复 每个可以用多次
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        //注意这个pos变量 可以避免重复 和子集一个道理
        dfs2(target,0,0,candidates,new ArrayList<>(),result);
        return  result;
    }
    public void dfs2(int n,int sum,int pos,int[]candidates,List<Integer> row ,List<List<Integer>>result){
        if(sum == n){
            List<Integer> temp = new ArrayList<>(row);
            result.add(new ArrayList<>(row));
            return ;
        }
        if(sum > n) return ;
        for(int i = pos ; i < candidates.length ;i++ ){
            if(sum + candidates[i] <= n){
                row.add(candidates[i]);
                dfs2(n,sum+candidates[i] ,i,candidates,row,result);
                row.remove(row.size()-1);
            }
        }
    }

    //40.组合总数
   //candidates重复 ，每个可以用一次
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        //注意这个pos变量 可以避免重复 和子集一个道理
        dfs3(target,0,0,candidates,new ArrayList<>(),result);
        return  result;
    }
    public void dfs3(int n,int sum,int pos,int[]candidates,List<Integer> row ,List<List<Integer>>result){
        if(sum == n){
            List<Integer> temp = new ArrayList<>(row);
            result.add(new ArrayList<>(row));
            return ;
        }
        if(sum > n) return ;
        for(int i = pos ; i < candidates.length ;i++ ){
            if(i!= pos && candidates[i]== candidates[i-1]) continue;   //update
            if(sum + candidates[i] <= n){
                row.add(candidates[i]);
                dfs3(n,sum+candidates[i] ,i+1,candidates,row,result);    //update
                row.remove(row.size()-1);
            }
        }
    }

    //201 数字范围按位与
    //即求从高处开始，有多少是相同的
    public int rangeBitwiseAnd(int m, int n){
        int count = 0;
        while(m!= n){
            m>>= 1 ;
            n>>= 1 ;
            count++;
        }
        n<<=count;
        return n;
    }

    //206 移除链表元素
    public ListNode removeElements(ListNode head, int val) {
        ListNode newhead = new ListNode(-1);
        newhead.next = head;
        ListNode pre = newhead;
        ListNode tmp = head;
        while(tmp!= null){
            if(tmp.val == val){
                pre.next = tmp.next;
                tmp = tmp.next;
                continue;
            }
            tmp = tmp.next;
            pre = pre.next;
        }
        return newhead.next;
    }

    //209 长度最小的子数组
    public int minSubArrayLen(int s, int[] nums) {
        int start = 0;
        int end = -1; ;
        int sum= 0 ;
        int min_length = nums.length;
        boolean flag = false;
        for(int i = 0 ; i< nums.length ;i++ ){
            sum += nums[i];
            end++;
            while(sum >= s){
                sum = sum - nums[start];
                min_length = Math.min(min_length,end- start +1);
                start++;
                flag = true;
            }
        }
        return flag ? min_length : 0;
    }

    //213 打家劫舍 2
    //成环状
    //解决办法： 把环状排列的房间 转化为 两个单排列 nums[0: n-1] 和nums[1:n]
    public int rob(int[] nums) {
        if(nums.length == 0) return 0 ;
        if(nums.length == 1) return nums[0];
        return Math.max(myRob(Arrays.copyOfRange(nums,0,nums.length-1))
                        ,myRob(Arrays.copyOfRange(nums,1,nums.length)));
    }
    private int myRob(int[] nums){
        int pre = 0;
        int cur = 0;
        int tmp;
        for(int num:nums){
            tmp = cur;
            cur = Math.max(pre+ num,cur);
            pre = tmp;
        }
        return cur;
    }

    //337 大家劫舍3
    //二叉树结构
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
     }
    public int rob(TreeNode root) {
        return myRob3(root).val;
    }
    private TreeNode myRob3(TreeNode root){
        //要变成满二叉树,注意是怎么处理的
        if(root == null){
            TreeNode newNode = new TreeNode(0);
            return myRob3(newNode);
        }
        if(root.left == null && root.right == null){
            root.left = new TreeNode(0);
            root.right = new TreeNode(0);
            return root;
        }
        root.left = myRob3(root.left);
        root.right = myRob3(root.right);
        root.val = Math.max(root.left.val + root.right.val , root.left.left.val + root.left.right.val + root.right.left.val+ root.right.right.val + root.val);
        return root;
    }

    //220 存在重复元素3
    //大小为k的滑动窗口 差值 <t   用滑动窗口超时 用桶排序
    //这道题值得看一下 转化的思路不太一样
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if(t<0 ) return false;
        Map<Integer,Integer> buckets = new HashMap<>();
        int w = t+1; // [x,x+t]
        for(int i = 0 ; i< nums.length ;i++){
            int id = getId(nums[i],w);
            if(buckets.containsKey(id)) return true;
            if(buckets.containsKey(id-1) && Math.abs(nums[i] - buckets.get(id-1)) <w) return true;
            if(buckets.containsKey(id+1) && Math.abs(nums[i] - buckets.get(id+1)) <w) return true;
            buckets.put(id,nums[i]);
            if(i-k >= 0){
                buckets.remove(getId(nums[i-k],w));
            }
        }
        return false;
    }
    // In Java, -3 / 5 = 0` and but we need -3 / 5 = -1
    private int getId(int x ,int w){
        return x < 0 ? (x + 1) / w - 1 : x / w;
    }

    //完全二叉树结点的个数
    public int countNodes(TreeNode root) {
        if(root == null) return 0;
        if(root.left == null) return 1;
        int left = countNodes(root.left);
        if(root.right == null) return left+1;
        int right = countNodes(root.right);
        return left + right + 1;
    }

    //223 矩形面积
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        if(A> E){
            return computeArea(E,F,G,H,A,B,C,D); //调整第一个矩形在左边
        }
       int area1 = (D-B) * (C-A);
       int area2 = (H-F) * (G-E);
       if(B>= H || D<=F || C<=E){ //没有重叠
           return area1 + area2;
       }
       int[] point1 = new int[2];
       point1[0] = Math.max(A,E);
       point1[1] = Math.max(B,F);

       int[] point2 = new int[2];
       point2[0] = Math.min(C,G);
       point2[1] = Math.min(D,H);
       return area1+area2 - Math.abs(point1[0] -point2[0])* Math.abs(point1[1] - point2[1]);
    }


    //224 基本计算器
    public int calculate(String s) {
        Stack<Character> operator = new Stack<Character>();
        Stack<Integer> number = new Stack<Integer>();
        int num = 0;
        for(int i = 0 ; i< s.length() ;i++){
            char c = s.charAt(i);
            if(c== ' ') continue;
            if(is_number(c)){
                if(num== 0)  num = c-'0';
                else num = num* 10 + c-'0';
            }else{
                if(c == '+' || c== '-'|| c== ')'){
                    if(number.isEmpty()){
                        number.push(num);
                        operator.push(c);
                        num = 0;
                        continue;
                    }
                    char op = operator.peek();
                    if(op == '('){
                        number.push(num);
                        operator.push(c);
                        num= 0;
                        continue;
                    }
                    if(c== ')' && num == 0) num = number.pop();
                    int num1 = number.pop();
                    operator.pop();
                    int new_num = cal(num1,num,op);
                    num = 0;
                    number.push(new_num);
                    if(c== ')'){
                        operator.pop(); // 弹出 （
                        //一直计算 知道遇到（
                    }else{
                        operator.push(c);
                    }
                }
                else if(c == '('){
                    operator.push('(');
                }
            }
        }
        if(num!= 0) number.push(num);
        while(!operator.isEmpty()){
            char c = operator.pop();
            int num2 = number.pop();
            int num1 = number.pop();
            int new_num = cal(num1,num2,c);
            number.push(new_num);
        }
        return number.pop();
    }
    private boolean is_number(char c){
        if(c - '0' >= 0 && c-'0' <= 9 ) return true;
        return false;
    }
    private int cal(int num1,int num2,char operator){
        if(operator == '+') return num1 + num2;
        if(operator == '-') return num1 - num2;
        return 0;
    }


    public static void main(String[] args) {
        TwoTo300 so = new TwoTo300();
        String str = "(1+(4+5+2)-3)+(6+8)";
       // System.out.println(so.calculate(str));
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().freeMemory()+"KB");
        System.gc();
        System.out.println(Runtime.getRuntime().freeMemory());

    }
}
