package easy_media_high_level;

import java.util.*;


//初级算法篇
public class easy_level {

    public int removeDuplicates(int[] nums) {

        int index = 0 ;
        int number = Integer.MIN_VALUE;

        for(int i = 0 ; i <nums.length ; i++){
            if (nums[i] > number){
                nums[index] = nums[i];
                number = nums[i];
                index++;
            }
        }
        return index;
    }

    //买卖股票的最佳时机2
    public int maxProfit(int[] prices) {

        if(prices.length <=1) return 0;
        int buy = prices[0];
        int sell = prices[0];
        int profit = 0;
        for(int i = 1 ; i < prices.length; i++){

            if(prices[i] - sell > 0){
                sell = prices[i];
            }
            else if( prices[i] <sell ){
                profit += sell - buy;
                buy  = prices[i];
                sell = prices[i];
            }
        }
        profit += sell - buy;
        return profit;
    }

    //一个数+1
    public int[] plusOne(int[] digits) {

        digits[digits.length-1] = (digits[digits.length -1] +1 )%10;
        if( digits[digits.length-1] == 0){  //需要进位
            boolean flag = true;
            int index = digits.length-2;
            while(flag && index>=0){
                digits[index] = (digits[index]+1)%10;
                if(digits[index] !=0) flag = false;
                else index--;
            }
            if(index == -1) {  //需要加一位
                int[] newDigits = new int[digits.length+1];
                newDigits[0] = 1;
                return newDigits;
            }
        }
        return digits;
    }

    //两数之和
    public int[] twoSum(int[] nums, int target) {
       // Arrays.sort(nums);
        int left = target;
        for(int i = 0 ; i < nums.length ;i++){
            left = target - nums[i];
            for(int j = i+1 ; j< nums.length ; j++){
                if(left == nums[j]){
                    int [] result = {i,j};
                    return result;
                }
            }
        }
        return null;
    }

    //两数之和 答案
    public int[] twoSum2(int[] nums,int target){
        int[]result = new int[2];
        Map<Integer,Integer>  map = new HashMap<>();

        for(int i = 0 ; i< nums.length ;i++){
            int a = nums[i];
            if(map.containsKey(target - a)){
                result[0] = map.get(target-a);
                result[1] = i;
                return result;
            }else{
                map.put(nums[i],i);
            }
        }

        return null;
    }

    //有效的数独
    public boolean isValidSudoku(char[][] board) {

        List<ArrayList<Integer>> row = new ArrayList<ArrayList<Integer>>();
        List<ArrayList<Integer>> col = new ArrayList<ArrayList<Integer>>();
        List<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();

        for(int i = 0 ; i < 9 ;i++){
            ArrayList<Integer> new_row = new ArrayList<Integer>();
            row.add(new_row);
            for(int j = 0 ; j < 9 ;j++ ){

                if(col.size() <= j) {
                    ArrayList<Integer> new_col = new ArrayList<Integer>();
                    col.add(new_col);
                }

                int index = 0;
                if( i<= 2 ){
                    if(j <= 2) index = 0;
                    else if( j <= 5) index = 1;
                    else index = 2;
                }else if ( i <= 5){
                    if(j <= 2) index = 3;
                    else if( j <= 5) index = 4;
                    else index =5;
                }else{
                    if(j <= 2) index = 6;
                    else if( j <= 5) index = 7;
                    else index = 8;
                }

                if(grid.size() <= index){
                    ArrayList<Integer> new_grid = new ArrayList<Integer>();
                    grid.add(new_grid);
                }

                char number_char = board[i][j] ;
                if(! Character.isDigit(number_char))  continue;
                int number = board[i][j] ;


                if(row.get(i).contains(number) || col.get(j).contains(number)) return false;

                if(grid.get(index).contains(number)) return false;

                row.get(i).add(number);
                col.get(j).add(number);
                grid.get(index).add(number);
            }
        }
        return true;
    }

    //有效的数独
    public boolean isValidSudoku2(char[][] board) {
        boolean[][] row=new boolean[9][9];
        boolean[][] col=new boolean[9][9];
        boolean[][] block=new boolean[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]!='.'){
                    int c=board[i][j]-'1';
                    int blo=(i/3)*3 + j/3;
                    if(row[i][c]==true || col[j][c]==true || block[blo][c]==true){
                        return false;
                    } else{
                        row[i][c]=true;
                        col[j][c]=true;
                        block[blo][c]=true;
                    }
                }

            }
        }
        return true;
    }

    //旋转图像 有bug 懒得调
    public void rotate(int[][] matrix) {

        int length = matrix.length;
        boolean [][] visited = new boolean[length][length];

        int step = 0;
        int i  = 0;
        int j = 0;
        int temp = matrix[0][0];
        while(step <= length * length){
            matrix[i][j] = temp;
            if(visited[i][j]) {
                if(i <length-1 && !visited[i][j]){
                    i++;
                }else if( j <length -1 && !visited[i][j]){
                    j++;
                }
                temp = matrix[i][j];
                continue;
            }
            temp = matrix[j][length - i - 1];
            matrix[j][length - i - 1] = matrix[i][j];
            visited[i][j] = true;

            i = j;
            j = length - i - 1;
            step++;
        }
    }

    //旋转图像
    public void rotate2(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n/2; i++ ) {
            for (int j = i; j < n - i - 1; j ++ ){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n-j-1][i];
                matrix[n-j-1][i] = matrix[n-i-1][n-j-1];
                matrix[n-i-1][n-j-1] = matrix[j][n-i-1];
                matrix[j][n-i-1] = tmp;
            }
        }
    }

    //整数反转
    public int reverse(int x) {
        boolean flag = true;
        if(x <0 ) {
            flag = false;
            x = Math.abs(x);
        }
        long res = 0;
        int m = 0;
        while( x > 0) {
            m = x % 10;
            res = res * 10 + m;
            if (res > Integer.MAX_VALUE ) return 0;
            x = x / 10;
        }
        if(!flag) return 0 - (int)res;
        return (int)res;
    }

    //自定义的aoti
    public int myAtoi(String str) {
        if(str.isEmpty()) return 0;
        boolean flag = false;
        boolean num_start = false;
        int num_length = 0;
        char temp = str.charAt(0);
        if(temp!= ' '&& temp != '-'&& temp != '+'&& !Character.isDigit(temp)) return 0;
        long res = 0;
        if(Character.isDigit(temp) ) {
            res = temp -  '0';
            num_start = true;
            if(res!= 0) num_length++;
        }
        if(temp == '-')  {
            flag = true;
            num_start = true;
        }
        if(temp == '+') num_start = true;
        for(int i = 1 ; i< str.length() ;i++){
            temp = str.charAt(i);
            if(!Character.isDigit(temp) ) {
                if(num_start) break;
                else if (temp == ' ' ) continue;
                else if (temp == '-') {
                    flag = true;
                    num_start = true;
                }
                else if(temp == '+')  num_start = true;
                else  return 0;
            }else {
                num_start = true;
                res = res * 10 + (temp- '0');
                if(res!= 0) num_length++;
                if(num_length >10) break;
            }
        }

        if(res > Integer.MAX_VALUE) {
            if ( flag) return Integer.MIN_VALUE;
            else return Integer.MAX_VALUE;
        }else{
            if(flag ) return 0 - (int)res;
            else return (int)res;
        }
    }

    //实现 strStr()
    public int strStr(String haystack, String needle) {
        if(needle.isEmpty()) return 0;
        int start_index = 0;
        int length = 0;
        boolean flag = false;
         for(int i = 0 ; i< haystack.length() ;i++){
             char temp = haystack.charAt(i);
             if(temp == needle.charAt(length)){
                 if(!flag){
                     flag = true;
                     start_index = i ;
                     length = 1;
                 }else{
                     length ++;
                 }
                 if(length == needle.length()) break;
             }else{
                 flag = false;
                 length = 0;
             }
         }
         if(length < needle.length()) start_index = -1;
         return start_index;
    }

    //报数
    public String countAndSay(int n) {

        String res = "1";
        for(int i = 2 ; i <= n ;i++){
            int num = 0;
            int count = 0;
            String new_res = "";
            for(int index = 0 ; index <res.length() ;index++){
                if(num == 0){
                    num = (res.charAt(index) - '0');
                    count++;
                }
                else if(num == (res.charAt(index) - '0')) count++;
                else{
                    new_res += String.valueOf(count) + String.valueOf(num);
                    num = (res.charAt(index) - '0');
                    count = 1;
                }
            }
            if(count != 0) new_res += String.valueOf(count) + String.valueOf(num);
            res = new_res;
        }

        return res;
    }

    //字符串数组的最长公共前缀
    public String longestCommonPrefix(String[] strs) {

        if(strs.length ==0) return "";
        String first = strs[0];
        String res = "";
        boolean flag = true;
        for(int i = 0 ; i < first.length() ;i++ ){
            char temp = first.charAt(i);
            for(int j = 1; j < strs.length ; j++){
                if(i > strs[j].length()-1 ) return res;
                if(strs[j].charAt(i) != temp) flag = false;
            }
            if(!flag) return res;
            res+= temp;
        }
        return res;
    }

    /**
     * Definition for singly-linked list.
     */
    public class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
    }

    //删除链表中倒数第n个节点
    //一趟扫描 双指针
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode first = head;
        ListNode second = head;
        while(n-- > 0){
            first = first.next;
        }
        //first 走到尾 ，说明要删除的是头节点
        if(first == null)return head.next;

        while(first.next!= null){
            second = second.next;
            first = first.next;
        }
        //此时second在要删除的节点的前一个节点
        second.next = second.next.next;
        return head;
    }

    //反转链表
    public ListNode reverseList(ListNode head) {

        if(head== null) return head;
        if(head.next == null) return head;
        ListNode first = head;
        ListNode second = reverseList(head.next);

        ListNode temp = second;
        while(temp.next!= null){
            temp = temp.next;
        }
        temp.next = first;
        first.next = null;
        return second;
    }

    //反转链表 递归标准写法
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    //反转链表 头插法
    public ListNode reverseList3(ListNode head){
        ListNode prev = null;
        ListNode curr = head;
        ListNode nextTemp = null ;
        while(curr != null){
           nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }


    //合并两个有序链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
         ListNode first = l1;
         ListNode second = l2;
         ListNode head = new ListNode(-1);
         ListNode q;
         if(l1== null && l2 ==null) return null;

         q = head;
         while(first!= null && second!= null){
             if(first.val < second.val){
                 q.next  = first;
                 first = first.next;
             }else {
                 q.next = second;
                 second = second.next;
             }
             q = q.next;
         }
         while(first!= null){
             q.next = first;
             first = first.next;
             q= q.next;
         }
         while(second!= null){
             q.next = second;
             second = second.next;
             q= q.next;
         }
         return head.next;
    }

    //回文链表
    //快慢指针找中间节点 后半截翻转

    //判断是否是环形链表
    //方法1： 哈希表  方法2： 快慢指针
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while(fast!= null && fast.next!= null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) return true;
        }
        return false;
    }

    //环形链表  找出环的索引index
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        ListNode  intersect = null;
        while(fast!= null && fast.next!= null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) {
                intersect = fast;
                break;
            }
        }
        if(intersect== null) return null;

        ListNode start = head;

        while(start != intersect){
            start = start.next;
            intersect = intersect.next;
        }
        return intersect;
    }

/**
 * Definition for a binary tree node.
 */
 public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
 }

    //二叉树的最大深度
    public int maxDepth(TreeNode root) {
     if(root == null) return 0;
     if(root.left== null && root.right == null) return 1;
     else if(root.left == null) return maxDepth(root.right)+1;
     else if(root.right == null) return maxDepth(root.left)+1;
     return Math.max(maxDepth(root.left)+1,maxDepth(root.right)+1);
    }

    //判断是否是有效的二叉搜索树
    //二叉搜索树，右边的树的所有值都大于我
    public boolean isValidBST(TreeNode root) {
         return helper(root,null,null);
    }
    public boolean helper(TreeNode root, Integer lower , Integer upper){
         if(root == null) return true;
         int val = root.val;
         if(lower != null && val <= lower) return false;
         if(upper!= null && val >= upper) return false;
         if(! helper(root.left,lower,val))  return false;
         if(!helper(root.right,val,upper)) return false;
         return true;
    }

    //对称二叉树
    //镜像 递归
    public boolean isSymmetric(TreeNode root) {
          return isMirrot(root,root);
    }
    public boolean isMirrot(TreeNode root1 ,TreeNode root2){
        if(root1 == null && root2 == null) return true;
        if(root1 == null || root2 == null) return false;
        return (root1.val == root2.val) && isMirrot(root1.left,root2.right) && isMirrot(root1.right, root2.left);
    }

    //二叉树的层次遍历
    //怎么处理这个输入输出。。控制层次
    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> levels = new ArrayList<List<Integer>>();
        if (root == null) return levels;

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        int level = 0;
        while ( !queue.isEmpty() ) {
            // start the current level
            levels.add(new ArrayList<Integer>());

            // number of elements in the current level
            int level_length = queue.size();
            for(int i = 0; i < level_length; ++i) {
                TreeNode node = queue.remove();

                // fulfill the current level
                levels.get(level).add(node.val);

                // add child nodes of the current level
                // in the queue for the next level
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            // go to next level
            level++;
        }
        return levels;
    }

    //二叉树的层次遍历 倒序输出
    //.上述基础方法+ collections.reverse

    //将有序数组转化为二叉搜索树
    //实质，每个数组的中心点为根节点，二分
    public TreeNode sortedArrayToBST(int[] nums) {
         return helper2(nums,0,nums.length-1);
    }
    public  TreeNode helper2(int[] nums, int left ,int right){
        if(left > right) return null;
        int mid = (left+right)/2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = helper2(nums,left,mid-1);
        node.right = helper2(nums,mid+1,right);
        return node;
    }

    //排序： 第一个错误的版本号
    //二分
//    public int firstBadVersion(int n) {
//      int left = 0;
//      int right = n;
//      while(left <right){
//          int mid = left + (right -left)/2;
//          if(isBadVersion(mid)){
//              right = mid;
//          }else{
//              left = mid+1;
//          }
//      }
//      return left;
//    }

    //p爬楼梯
    public int climbStairs(int n) {
        if(n ==1) return 1;
        if(n == 2) return 2;
        if(n == 0) return 0;
        int dp[] = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i <= n ;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    //买股票的最佳时机1 -最多一笔交易
    public int maxProfit2(int[] prices){
       if(prices.length == 0) return 0;
       int low = prices[0];
       int high = prices[0];
       int profit = 0;
       for(int i = 1 ; i< prices.length;i++){
           if(prices[i] < low){
               profit = Math.max(profit,high - low);
               low = prices[i];
               high = prices[i];
           }else if(prices[i] > high){
               high = prices[i];
           }
       }
        profit = Math.max(profit,high - low);
        return profit;
    }


    //最大子序和
    public int maxSubArray(int[] nums) {
        if(nums.length ==0) return 0;
       int sum = nums[0];
       int ans = nums[0];
       for(int i = 1 ; i < nums.length ;i++){
           if(sum >=0) {
               sum+= nums[i];
           }else{
               sum = nums[i];
           }
           ans = Math.max(ans,sum);
       }
       return ans;
    }

    //打乱数组
    private int[] array;
    private int[] original;
    Random rand = new Random();
//    public Solution(int[] nums) {
//          array = nums;
//          original = nums.clone();
//    }
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        array = original;
        original = original.clone();
        return original;
    }
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        for(int i = 0 ; i< array.length;i++){
            swapAt(i,randRange(i,array.length));
        }
        return array;
    }
    private int randRange(int min, int max) {
        return rand.nextInt(max - min) + min;
    }
    private void swapAt(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    //Fizz Buzz
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for(int i = 1 ; i< n ; i++){
            if(i % 15 == 0) result.add("FizzBuzz");
            else if ( i % 3 == 0 ) result.add("Fizz");
            else if (i % 5 == 0 ) result.add("Buzz");
            else result.add(String.valueOf(i));
        }
        return result;
    }

    //厄拉多塞筛法 计数质数
    public int countPrimes(int n) {
        boolean []nums = new boolean[n];
        for(int i = 2; i< nums.length ;i++){
            nums[i] = true;
        }
        int res = 0;
        for(int i = 2 ; i < n ;i++){   //只需遍历根号n
            if(nums[i]){
                for(int j = i * 2 ; j <nums.length ;j = j+i){
                    nums[j] = false;
                }
                res++;
            }
        }
        return res;
    }

    //3的幂  基础方法：迭代
    public boolean isPowerOfThree(int n) {
        return Integer.toString(n, 3).matches("^10*$");
    }

    //罗马数字转成整数
    public int romanToInt(String s) {
      Map<String,Integer> map = new HashMap<>();
      map.put("I",1);
      map.put("IV",4);
      map.put("IX",9);
      map.put("V",5);
      map.put("X",10);
      map.put("XL",40);
      map.put("XC",90);
      map.put("L",50);
      map.put("C",100);
      map.put("CD",400);
      map.put("CM",900);
      map.put("D",500);
      map.put("M",1000);
      int result = 0;
      for(int i = 0 ; i< s.length() ;i++){
           String two = i+2 <=s.length() ? s.substring(i,i+2) : "no";
          String one = s.substring(i,i+1);
          if(map.containsKey(two)){
              result += map.get(two);
              i++;
          }else{
              result += map.get(one);
          }
      }
      return result;
    }

    //整数转罗马数字
    public String intToRoman(int num) {
        int [] matchs = {
                1,4,5,9,10,40,50,90,100,400,500,900,1000
        };
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"I");
        map.put(4,"IV");
        map.put(9,"IX");
        map.put(5,"V");
        map.put(10,"X");
        map.put(40,"XL");
        map.put(90,"XC");
        map.put(50,"L");
        map.put(100,"C");
        map.put(400,"CD");
        map.put(900,"CM");
        map.put(500,"D");
        map.put(1000,"M");

        String result = "";
        int index = 12;
        while(num> 0 && index >= 0){
            int a = num / matchs[index];
            num = num % matchs[index];
            for(int i = 0 ; i < a ; i++ ){
                result += map.get(matchs[index]);
            }
            index --;
        }
        return result;
    }

    //位1的个数
    //位掩码
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
         int bits = 0 ;
         int mask = 1;
         for(int i = 0 ; i< 32; i++){
             if((n & mask)!= 0){
                 bits ++;
             }
             mask <<= 1; //相当于 *2
         }
         return bits;
    }

    //海明距离
    public int hammingDistance(int x, int y) {

         int c = x^y ;
         //计算c的海明重量
         return hammingWeight(c);
    }

    //颠倒二进制位
    //常考题！！ 记一下
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int result = 0;
        for(int i = 0 ; i<= 32 ;i++){
            int temp = n>>i;
            temp = temp &1;
            temp = temp <<(31-i);
            result |= temp;
        }
        return result;
    }

    //帕斯卡三角形，杨辉三角
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(numRows == 0) return result;
        List<Integer> first_line = new ArrayList<>();
        first_line.add(1);
        result.add(first_line);
        List<Integer> last_line = first_line;
        for(int i = 2; i <= numRows;i++){
            List<Integer> this_line = new ArrayList<>();
            this_line.add(1);
            for(int j = 1; j < i-1;j++){
                this_line.add(last_line.get(j-1) + last_line.get(j));
            }
            this_line.add(1);
            result.add(this_line);
            last_line = this_line;
        }
        return result;
    }

    //有效的括号
    //栈结构
    public boolean isValid(String s) {
        if(s.length() == 0) return true;
        Stack<Character> stack = new Stack<>();
        for(int i = 0 ; i< s.length();i++){
            Character symbol = s.charAt(i);
            if(stack.isEmpty()) {
                stack.push(symbol);
                continue;
            }
            Character top = stack.peek();
            if(canPop(top,symbol)){
                stack.pop();
            }else{
                stack.push(symbol);
            }
        }
        if(stack.isEmpty()) return true;
        else return false;
    }
    public boolean canPop(Character top,Character symbol){
        if(top == '(' && symbol ==')') return true;
        else if(top == '{' && symbol == '}') return true;
        else if(top == '[' && symbol ==']') return  true;
        return  false;
    }

    //缺少数字
    //线性复杂度，常数空间
    public int missingNumber(int[] nums) {
        int sum = 0;
        for(int i = 0 ; i <= nums.length; i++){
            sum+= nums[i];
        }
        return calSum(nums.length) - sum;
    }

    public int calSum(int n){
        return (1+n)* n /2;
    }

    public static void main(String[] args) {
        easy_level so = new easy_level();
        //int a = so.strStr("mississippi", "issip");
        //String res = so.countAndSay(5);
      // String result =  easy_level.judge("3+4*5+6");
     //  System.out.print(result);

       int[][] matrix = new int[2][2];
       matrix[0][1] = matrix[0][0] = 1;

        int[][] matrix2 = new int[2][2];
        matrix2[1][1] = matrix2[1][0] = 1;

       int[] row ;
       row = matrix[1].clone();
       matrix[1] =  matrix2[1].clone();
       matrix2[1] = row.clone();

       System.out.println(matrix[0][0] + " " + matrix[0][1]);
       System.out.println(matrix[1][0] + " " + matrix[1][1]);

        System.out.println(matrix2[0][0] + " " + matrix2[0][1]);
        System.out.println(matrix2[1][0] + " " + matrix2[1][1]);

        matrix2[1][1] = 1;
        matrix[1][0] = 0;

        System.out.println(matrix[0][0] + " " + matrix[0][1]);
        System.out.println(matrix[1][0] + " " + matrix[1][1]);

        System.out.println(matrix2[0][0] + " " + matrix2[0][1]);
        System.out.println(matrix2[1][0] + " " + matrix2[1][1]);

        int at = ((int) (Math.random() * 40)) % 40;
        System.out.println(at);

    }

    String[] E_list = {"E+T","T"};
    String[] T_list = {"T*F","F"};

    public static String judge(String str){
        List<Character> pro = new ArrayList<Character>();
        pro.add('E');
        String pro_str = "E";
        int number = 0;
        List<Integer> number_lists = new ArrayList<Integer>();

        for(int i= 0 ; i<str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                number_lists.add(number);
                number = 0;
            }else{
                number = str.charAt(i)- '0' + number*10;
            }
        }
        if(number!= 0) number_lists.add(number);
        String result = dp("",pro_str,str,number_lists,0);
        return result;
    }

    public static String dp( String pro,String pro_str, String str,List<Integer> number_lists,int index){

        if(pro_str.length()> str.length()) return "FALSE";
        boolean canAdd = true;
        if(pro_str.length() == str.length()) {
            canAdd = false;
            if(!pro_str.contains("E") && !pro_str.contains("T")&& !pro_str.contains("F")){
                if(pro_str.equals(str)){
                    return "TRUE  "+ pro;
                }
            }
        }
        for(int i = 0 ; i< pro_str.length(); i++){
              if(pro_str.charAt(i)=='E'){
                  //E+T
                  pro+= "E";
                  if(canAdd) {
                      String new_pro_str = pro_str.substring(0, i ) + "E+T" + pro_str.substring(i + 1, pro_str.length());
                      String result = dp(pro, new_pro_str, str, number_lists, index);
                      if(result.startsWith("TRUE")) return result;
                  }
                  //T
                  String new_pro_str2 = pro_str.substring(0,i) + "T" +pro_str.substring(i+1,pro_str.length());
                  String result = dp(pro,new_pro_str2,str,number_lists,index);
                  if(result.startsWith("TRUE")) return result;
              }else if(pro_str.charAt(i) == 'T'){

                  pro+= "T";
                  //T*F
                  if(canAdd) {
                      String new_pro_str = pro_str.substring(0, i ) + "T*F" + pro_str.substring(i + 1, pro_str.length());
                      String result = dp(pro, new_pro_str, str, number_lists, index);
                      if(result.startsWith("TRUE")) return result;
                  }
                  //F
                  String new_pro_str2 = pro_str.substring(0,i) + "F" +pro_str.substring(i+1,pro_str.length());
                  String result = dp(pro,new_pro_str2,str,number_lists,index);
                  if(result.startsWith("TRUE")) return result;

              }else if(pro_str.charAt(i) == 'F' && index <number_lists.size()){
                  int number = number_lists.get(index);
                  String new_pro_str = pro_str.substring(0,i) + number + pro_str.substring(i+1,pro_str.length());
                  pro+="F";
                  index++;
                 String result =  dp(pro, new_pro_str, str, number_lists, index);
                  if(result.startsWith("TRUE")) return result;
              }
        }
        return "FALSE";
    }
}


