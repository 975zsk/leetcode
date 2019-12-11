package sequence;

import java.util.*;

public class ZeroTo100 {

    //四数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {

        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for(int i = 0 ; i< nums.length-3 ;i++){
            if(i >0 && nums[i] == nums[i-1]) continue;
            if (nums[i] + nums[nums.length - 3] + nums[nums.length - 2] + nums[nums.length - 1] < target) continue;
            if (nums[i] + nums[i+1] + nums[i+2] + nums[i+3] > target)  break;
            for(int j = i+1; j < nums.length-2 ; j++){
                if( j > i+1 &&  nums[j] == nums[j-1]) continue;
                if (nums[i] + nums[j] + nums[nums.length - 2] + nums[nums.length - 1] < target) continue;
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target)  break;
                int l = j+1;
                int r = nums.length-1;
                int sum = nums[i]+nums[j];
                while(l <r){
                    if( (l >j+1 && nums[l] == nums[l-1]) ||nums[l] + nums[r] + sum < target){
                        l++;
                    }else if((r< nums.length-1 && nums[r] == nums[r+1]) ||nums[l]+nums[r] + sum >target){
                        r--;
                    }else{
                        List<Integer> row = new ArrayList<>();
                        row.add(nums[i]);
                        row.add(nums[j]);
                        row.add(nums[l]);
                        row.add(nums[r]);
                        result.add(row);
                        l++; r--;
                    }
                }
            }
        }
        return result;
    }

//Definition for singly-linked list.
 public class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
 }

    //合并k个排序列表
    //优先级队列 维护头结点
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length ==0) return null;
        int k = lists.length;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(k, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                if (o1.val < o2.val) return -1;
                else if (o1.val == o2.val) return 0;
                else return 1;
            }
        });
        ListNode  head = new ListNode(0);
        ListNode p = head;
        for(ListNode node : lists){
            if(node!= null) queue.add(node);
        }
        while(!queue.isEmpty()){
            p.next = queue.poll();
            p = p.next;
            if(p.next!= null) queue.add(p.next);
        }
        return head.next;
    }

    //两两交换链表的值  to do
    public ListNode swapPairs(ListNode head) {
        if(head.next == null) return head;
        ListNode new_head = head.next;
        ListNode p = head;
        ListNode q = head.next;
        while(p.next!= null){
            p.next = q.next;
            q.next = p;
            p = p.next;
            if(p.next!= null) q = p.next;
        }
        return head;
    }

    //31 下一个排列
    //组合数学的那个
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while(i >= 0 && nums[i+1] <= nums[i]){
            i--;
        }
        if(i >= 0 ){
            int j = nums.length -1;
            while(j >= 0 && nums[j] <= nums[i]){
                j--;
            }
            swap(nums,i,j);
        }
        reverse(nums,i+1);
    }
    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //60 第k个排列

    //10.正则表达式匹配  .单个 * 包括前面的
    public boolean isMatch3(String s, String p) {
        int m = s.length() , n = p.length();
        boolean [][] f = new boolean[m+1][n+1];
        f[0][0] = true;
        //初始化 s的前0个字符和p的前i个字符是否匹配
        for(int i = 2 ; i<= n ; i++){
            f[0][i] = f[0][i-2] && p.charAt(i-1) == '*';
        }
        for(int i = 1 ; i< m +1 ; i++){
            for(int j = 1; j< n +1 ;j++){
                if(s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.'){
                    f[i][j] = f[i-1][j-1];
                }else if(p.charAt(j-1) == '*'){
                    f[i][j] = f[i][j-2]    //匹配空串
                            || f[i-1][j] && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) =='.') ;  //
                }
            }
        }
        return f[m][n];
    }


    //44 通配符匹配 动态规划  ？单个 * 0个或多个前面的那一个元素
    public boolean isMatch(String s, String p) {
        int m = s.length() , n = p.length();
        boolean [][] f = new boolean[m+1][n+1];
        f[0][0] = true;
        //初始化 s的前0个字符和p的前i个字符是否匹配
        for(int i = 1 ; i<= n ; i++){
            f[0][i] = f[0][i-1] && p.charAt(i-1) == '*';
        }
        for(int i = 1 ; i< m +1 ; i++){
            for(int j = 1; j< n +1 ;j++){
                if(s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?'){
                    f[i][j] = f[i-1][j-1];
                }else if(p.charAt(j-1) == '*'){
                    f[i][j] = f[i-1][j] || f[i][j-1] ;  // *多匹配 或者 * 匹配空串
                }
            }
        }
        return f[m][n];
    }
    //指针移动
    public boolean isMatch2(String s, String p) {
        int m = s.length() ,n = p.length();
        int i = 0 ;
       int j = 0;
       int start = -1 ,match = 0;
       while(i < s.length()){
           if(j <n && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
               i++; j++;
           }else if( j <n && p.charAt(j) == '*'){
               start = j;
               match = i;
               j++;
           }else if( start != -1){
               j = start +1;  //在 * 匹配过程中是不变的
               match ++;
               i++;
           }else return false;
       }
       //后面的* 匹配空串
        for(int k = j ; k < n ;k++){
            if(p.charAt(k)!= '*') return false;
        }
        return true;
    }

    //91 解码方法
    //动态规划 12321的解码数 = 1232的 + 123的
    public int numDecodings(String s) {
        if(s.charAt(0) == '0' ) return 0;
        if(s.length() == 1) return 1;
         int [] dp = new int[s.length()+1];
         dp[0] = dp[1] = dp[2] =1;
         if(s.charAt(0) == '1' || (s.charAt(0) == '2' && s.charAt(1)<= '6')) dp[2] = 2;
         for(int  i = 2 ; i <= s.length();i++){
             //如果后一位合法
             if(s.charAt(i-1) != '0'){
                 dp[i] += dp[i-1];
             }
             //如果后两位合法
             if(s.charAt(i-2) == '1' || (s.charAt(i-2) == '2' && s.charAt(i-1)<= '6')){
                 dp[i] += dp[i-2];
             }
         }
         return dp[s.length()];
    }

    //639 解码方法2
    //dp[i] = dp[i-1]×（第i个单独解码个数） + dp[i-2]×（第i-1和i个一起解码的个数）
    public int numDecodings2(String s) {
        int n = s.length();
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : s.charAt(0) == '*' ? 9 : 1;
        for(int i = 1; i < n; i ++) {
            char cur = s.charAt(i);
            char pre = s.charAt(i - 1);
            if(cur == '0') {
                if(pre == '1' || pre == '2') dp[i + 1] = dp[i - 1];
                if(pre == '*') dp[i + 1] = 2 * dp[i - 1];
            }

            if(cur == '*') {
                dp[i + 1] = 9 * dp[i];
                if(pre == '1') dp[i + 1] += 9 * dp[i - 1];
                if(pre == '2') dp[i + 1] += 6 * dp[i - 1];
                if(pre == '*') dp[i + 1] += 15 * dp[i - 1];
            }

            if(cur >= '1' && cur <= '6') {
                dp[i + 1] = dp[i];
                if(pre == '1' || pre == '2') dp[i + 1] += dp[i - 1];
                if(pre == '*') dp[i + 1] += 2 * dp[i - 1];
            }

            if(cur >= '7' && cur <= '9') {
                dp[i + 1] = dp[i];
                if(pre == '1' || pre == '*') dp[i + 1] += dp[i - 1];
            }

            dp[i + 1] = dp[i + 1] % ((int)Math.pow(10, 9) + 7);
        }
        return (int)dp[n];
    }

    //35 搜索插入位置
    public int searchInsert(int[] nums, int target) {
        for(int i = 0 ; i < nums.length ;i++){
            if(nums[i] >= target){
                return i;
            }
        }
        return nums.length;
    }

    //27 移除元素
    public int removeElement(int[] nums, int val) {
        int ans = 0;
        for(int num: nums) {
            if(num != val) {
                nums[ans] = num;
                ans++;
            }
        }
        return ans;
    }

    //43 字符串相乘
    public String multiply(String num1, String num2) {

        int m = num1.length() ;
        int n = num2.length();
        String result = "";
        for(int i = n-1 ; i>= 0 ;i--){
            StringBuilder reverse_row= new StringBuilder();
            int  temp = 0;
            int   carry = 0;
            int a = num2.charAt(i) - '0';
            for(int j = m-1 ;j>=0 ;j--){
                int b = num1.charAt(j)-'0';
                temp = (a * b + carry) %10;
                carry = (a* b + carry) /10;
                reverse_row.append(temp);
            }
            if(carry!= 0) reverse_row.append(carry);
            int pow = n - (i+1);
            while(pow>0){
                reverse_row.insert(0,"0");
                pow--;
            }
            result = add(result,reverse_row.reverse().toString());
        }

        //将result前面的0去掉
        int i = 0;
        while(i < result.length()-1 && result.charAt(i) == '0') result = result.substring(i+1);
        return result;
    }
    public String add(String num1,String num2){
        if(num1.length() == 0) return num2;
        if(num2.length() ==0) return num1;
        int m = num1.length()-1;
        int n = num2.length()-1;
        StringBuilder s = new StringBuilder();
        int carry = 0;
        while(m>=0 || n>= 0 || carry!=0){
            int num1Val = m>= 0 ? num1.charAt(m) -'0':0;
            int num2Val = n>=0  ? num2.charAt(n) -'0':0;
            int sum = carry+ num1Val + num2Val;
            carry = sum/10;
            s.append(sum%10);
             m--;
             n--;
        }
        return s.reverse().toString();
    }

    //54 螺旋矩阵
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int row = matrix.length;
        if(row == 0) return result;
        int col = matrix[0].length;
        if(col == 0) return result;

        boolean[][] visited = new boolean[row][col];
        int sum = row * col;
        int type = 1;
        int i = 0, j= 0;
        while(result.size() < sum){
            switch (type){
                case 1:
                    while(j < col && !visited[i][j]){
                        result.add(matrix[i][j]);
                        visited[i][j] = true;
                        j++;
                    }
                    j--;
                    i++;
                    type = (type+1) % 4;
                    break;
                case 2:
                    while( i < row && !visited[i][j]){
                        result.add(matrix[i][j]);
                        visited[i][j] = true;
                        i++;
                    }
                    i--;
                    j--;
                    type = (type+1)% 4;
                    break;
                case 3:
                    while( j>=0 && !visited[i][j]){
                        result.add(matrix[i][j]);
                        visited[i][j] = true;
                        j--;
                    }
                    j++;
                    i--;
                    type = (type +1) %4;
                    break;
                case 0 :
                    while(i>=0 && !visited[i][j]){
                        result.add(matrix[i][j]);
                        visited[i][j] = true;
                        i--;
                    }
                    i++;
                    j++;
                    type = (type+1) % 4;
                    break;
            }
        }
        return result;
    }

    //59 螺旋矩阵2
    public int[][] generateMatrix(int n) {
       int sum = n* n;
       int num = 1;
       int[][] matrix = new int[n][n];
        boolean[][] visited = new boolean[n][n];
        int type = 1;
        int i = 0, j= 0;
        while(num <= sum){
            switch (type){
                case 1:
                    while(j < n && !visited[i][j]){
                        matrix[i][j] = num;
                        visited[i][j] = true;
                        j++;
                        num++;
                    }
                    j--;
                    i++;
                    type = (type+1) % 4;
                    break;
                case 2:
                    while( i < n && !visited[i][j]){
                        matrix[i][j] = num;
                        visited[i][j] = true;
                        i++;
                        num++;
                    }
                    i--;
                    j--;
                    type = (type+1)% 4;
                    break;
                case 3:
                    while( j>=0 && !visited[i][j]){
                        matrix[i][j] = num;
                        visited[i][j] = true;
                        j--;
                        num++;
                    }
                    j++;
                    i--;
                    type = (type +1) %4;
                    break;
                case 0 :
                    while(i>=0 && !visited[i][j]){
                        matrix[i][j] = num;
                        visited[i][j] = true;
                        i--;
                        num++;
                    }
                    i++;
                    j++;
                    type = (type+1) % 4;
                    break;
            }
        }
        return matrix;
    }


    //61旋转链表
    public ListNode rotateRight(ListNode head, int k) {

        if(head == null) return head;
        //链表成环
        ListNode temp = head;
        int n = 1;
        while(temp.next != null){
            temp = temp.next;
            n++;
        }
        temp.next = head;

        ListNode new_tail = head;
        for(int i = 1; i < n- k % n  ; i++){
            new_tail = new_tail.next;
        }
        ListNode new_head = new_tail.next;
        new_tail.next = null;
        return new_head;
    }

    //82.删除链表中的重复元素
    //快慢指针
    public ListNode deleteDuplicates(ListNode head) {

        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = head;
        ListNode slow = dummy;
        while(fast != null){
           if(fast.next== null || fast.val != fast.next.val){
                if(slow.next == fast){  //没重复
                    slow = fast;
                }else{                  //重复了
                    slow.next = fast.next;
                }
            }
            fast = fast.next;
        }
        return dummy.next;
    }

    //63 不同路径2
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        if(row == 0) return 0 ;
        int col = obstacleGrid[0].length;
        if(col == 0) return 0;
        int[][] dp = new int[row][col];
        dp[0][0] = obstacleGrid[0][0] == 1 ? 0: 1;
        if(dp[0][0] == 0) return 0;
        for(int j = 1 ; j < col ;j++){
            if(obstacleGrid[0][j] == 1){
                dp[0][j] = 0;
            }else {
                dp[0][j] = dp[0][j-1] ==1 ?  1: 0 ;
            }
        }
        for(int i = 1 ; i < row ; i++){
            dp[i][0] = 1;
            if(obstacleGrid[i][0] == 1){
                dp[i][0] = 0;
            }else{
                dp[i][0] = dp[i-1][0] == 1? 1:0;
            }
        }
        for(int i = 1 ; i< row ; i++){
            for(int j = 1 ; j < col ; j++){
                if(obstacleGrid[i][j] == 1){
                    dp[i][j] = 0;
                }else{
                    dp[i][j] = dp[i -1][j] + dp[i][j-1];
                }
            }
        }
        return dp[row-1][col-1];
    }

    //64 最小路径和
    public int minPathSum(int[][] grid) {
        for(int i = 0 ; i < grid.length ;i++){
            for(int j = 0 ; j < grid[0].length ;j++){
                if(i == 0  && j == 0) continue;
                else if (i ==0  ){
                    grid[i][j] = grid[i][j-1] + grid[i][j];
                }else if(j == 0 ){
                    grid[i][j] = grid[i-1][j] + grid[i][j];
                }else {
                    grid[i][j] = Math.min(grid[i][j-1],grid[i-1][j]) + grid[i][j] ;
                }
            }
        }
        return grid[grid.length -1 ][grid[0].length -1];
    }

    //57 插入区间
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<int[]>();
        if(intervals.length == 0){
            result.add(newInterval);
        }
        boolean flag = false;
        int i ;
        for(i = 0 ; i< intervals.length ;i++){
            if(!flag) {
                if ( newInterval[0] > intervals[i][1]) {
                    result.add(intervals[i]);
                    continue;
                }
                if(newInterval[1] < intervals[i][0]){
                    int[] temp = {newInterval[0], newInterval[1]};
                    result.add(temp);
                    result.add(intervals[i]);
                    flag = true;
                }
                else if (newInterval[1] <= intervals[i][1]) {
                    int[] temp = {Math.min(newInterval[0],intervals[i][0]), intervals[i][1]};
                    result.add(temp);
                    flag = true;
                }else{
                    newInterval[0] = Math.min(newInterval[0],intervals[i][0]);
                    newInterval[1] = Math.max(newInterval[1],intervals[i][1]);
                    if(i == intervals.length-1){
                        int [] temp = {newInterval[0],newInterval[1]};
                        result.add(temp);
                    }
                }
            }else{
                result.add(intervals[i]);
            }
        }
        if(!flag){
            result.add(newInterval);
        }

        int[][] final_result = new int[result.size()][2];
        for(int j = 0 ; j< result.size();j++){
            final_result[j] = result.get(j);
        }
        return final_result;
    }


    //65 有效数字
    //状态机！！！很好的题
    public boolean isNumber(String s) {
        int state = 0;
        int[][] transfer = new int[][]{{ 0, 1, 6, 2,-1},
                {-1,-1, 6, 2,-1},
                {-1,-1, 3,-1,-1},
                { 8,-1, 3,-1, 4},
                {-1, 7, 5,-1,-1},
                { 8,-1, 5,-1,-1},
                { 8,-1, 6, 3, 4},
                {-1,-1, 5,-1,-1},
                { 8,-1,-1,-1,-1}};
        char [] ss = s.toCharArray();
        for(int i = 0 ; i < ss.length ; ++ i){
            int col = getCol(ss[i]);
            if(col < 0) return false;
            state = transfer[state][col];
            if(state < 0 ) return false;
        }
        if(state == 3 || state == 5||state == 6 || state == 8){
            return true;
        }
        return false;
    }

    public int getCol(char c){
        switch(c){
            case ' ' : return 0;
            case '+':
            case '-': return 1;
            case '.': return 3;
            case 'e': return 4;
            default:
                if(c >= 48 && c<= 57) return 2;
        }
        return -1;
    }

    //71 简化路径
    public String simplifyPath(String path) {
        String[] s = path.split("/");
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < s.length; i++) {
            if (!stack.isEmpty() && s[i].equals(".."))
                stack.pop();
            else if (!s[i].equals("") && !s[i].equals(".") && !s[i].equals(".."))
                stack.push(s[i]);
        }
        if (stack.isEmpty())
            return "/";

        StringBuffer res = new StringBuffer();
        for (int i = 0; i < stack.size(); i++) {
            res.append("/" + stack.get(i));
        }
        return res.toString();
    }

    //编辑距离  将word1 转为word2的最小操作数
    //经典动态规划
    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        if(n * m == 0)return m+n;

        int[][] dp = new int[n+1][m+1];
        for(int i =  0 ; i < n+1; i++){
            dp[i][0] = i;
        }
        for(int j = 0 ; j < m+1 ;j++){
            dp[0][j] = j;
        }

        for(int i = 1; i < n+1; i++){
            for(int j = 1 ; j < m+1 ;j++){
                int a = dp[i-1][j] + 1;
                int b = dp[i][j-1] + 1;
                int c = dp[i-1][j-1] ;
                if(word1.charAt(i) == word2.charAt(j)){
                    c+= 1;
                }
                dp[i][j] = Math.min(c,Math.min(a,b));
            }
        }
        return dp[n][m];
    }

    //77组合 C（n,k）
    private ArrayList<List<Integer>> res;
    private void generateCombinations(int n, int k, int start, List<Integer> list) {
        if (list.size() == k) {
            res.add(new ArrayList<>(list));
            return;
        }
        //减枝  i <= n-(k-list.size())+1
        for (int i = start; i <= n - (k - list.size()) + 1; i++) {
            list.add(i);
            generateCombinations(n, k, i + 1, list);
            list.remove(list.size() - 1);

        }
    }
    public List<List<Integer>> combine(int n, int k) {

        res = new ArrayList<>();
        if (n <= 0 || k <= 0 || k > n) {
            return res;
        }
        List<Integer> list = new ArrayList<>();
        generateCombinations(n, k, 1, list);
        return res;
    }


    //80 删除排序数组中的重复项 II
    //每个元素最多留两个
    public int removeDuplicates(int[] nums) {
        int index = 0;
        int count = 0;

        for(int i = 0 ; i< nums.length ;i++){
            if(i == 0) {
                index++;
                count++;
                continue;
            }
            if(nums[i] == nums[i-1]){
                count++;
                if(count <= 2){
                    nums[index] = nums[i];
                    index++;
                }
            }else{
                count = 1;
                nums[index] = nums[i];
                index++;
            }
        }
        return index-1;
    }

    //42 接雨水
    //动态编程
    public int trap(int[] height) {
        int length = height.length;
        if(length ==0 ) return 0;
        int[] left_max = new int[length];
        int[] right_max = new int[length];
        left_max[0] = height[0];
        for(int i = 1 ; i< length;i++){
            left_max[i] = Math.max(left_max[i-1],height[i]);
        }
        right_max[length-1] = height[length-1];
        for(int i = length-2 ; i>=0 ;i--){
            right_max[i] = Math.max(height[i],right_max[i+1]);
        }
        int result = 0;
        for(int i = 1; i < length-1 ; i++){
            result += Math.min(left_max[i],right_max[i]) - height[i];
        }
        return result;
    }

    //407接雨水 立体


    //84 柱状图中的最大矩形
    //法一： 优化的暴力 容易理解
    public int largestRectangleArea(int[] heights) {
        if (heights.length == 0) {
            return 0;
        }
        //求每个柱子的左边第一个小的柱子的下标
        int[] leftLessMin = new int[heights.length];
        leftLessMin[0] = -1;
        for (int i = 1; i < heights.length; i++) {
            int l = i - 1;
            while (l >= 0 && heights[l] >= heights[i]) {
                l = leftLessMin[l];
            }
            leftLessMin[i] = l;
        }

        //求每个柱子的右边第一个小的柱子的下标
        int[] rightLessMin = new int[heights.length];
        rightLessMin[heights.length - 1] = heights.length;
        for (int i = heights.length - 2; i >= 0; i--) {
            int r = i + 1;
            while (r <= heights.length - 1 && heights[r] >= heights[i]) {
                r = rightLessMin[r];
            }
            rightLessMin[i] = r;
        }

        //求包含每个柱子的矩形区域的最大面积，选出最大的
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            int area = (rightLessMin[i] - leftLessMin[i] - 1) * heights[i];
            maxArea = Math.max(area, maxArea);
        }
        return maxArea;
    }
    //法二： 维护一个递增的栈！！ 模板解法 记住
    public int largestRectangleArea2(int[] heights) {
        Stack < Integer > stack = new Stack < > ();
        stack.push(-1);  //初始为-1 ，保证能从头计算
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])   //遇到下降了，依次弹出，直到遇到比当前i索引小的，同时计算maxArea
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - 1- stack.peek()));
            stack.push(i);
        }
        while (stack.peek() != -1)  //最后一个上升序列，没有弹出元素，要单独计算
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - 1 - stack.peek() ));
        return maxarea;
    }

    //85 最大矩形
    //法1：每一行都是84题   n3
    public int maximalRectangle(char[][] matrix) {
        if(matrix.length == 0|| matrix[0].length == 0 ) return 0;
        int[]heights = new int[matrix[0].length];
        int max = 0;
        for(int i = 0 ; i < matrix.length ;i++){
            for(int j = 0 ; j< matrix[0].length;j++){
                if(i == 0 ){
                     heights[j] = matrix[i][j] == '1' ? 1: 0;
                }
                else {
                    heights[j] = matrix[i][j] == '1'? heights[j] + 1 : 0;
                }
            }
            max = Math.max(max,largestRectangleArea2(heights));
        }
        return max;
    }


    //74 搜索二维矩阵
    //每行第一个整数 大于前一行走后一个整数
    public boolean searchMatrix(int[][] matrix, int target) {
        //先二分找对应的行 ，再在行里二分
        int row = matrix.length;
        if(row == 0 ) return false;
        int col = matrix[0].length;
        if(col == 0) return false;
        int low = 0;
        int high =  row;
        while(low < high){
            int mid = low + (high - low) /2;
            if(matrix[mid][0] == target) return true;
            else if(matrix[mid][0] < target){
                low = mid+1;
            }else{
                high = mid;
            }
        }
        low = low -1;
        if(low < 0) return false;
        return findRow(matrix[low],target);
    }
    public boolean findRow(int[] row,int target){
        int left = 0 ;
        int right = row.length-1;
        while(left <= right){
            int mid = left + (right - left)/2;
            if(row[mid] == target){
                return true;
            }
            if(row[mid] < target) left = mid+1;
            else right = mid-1;
        }
        return false;
    }

    //86 分割链表
    public ListNode partition(ListNode head, int x) {

        if(head == null) return head;
        ListNode lessHead = new ListNode(-1);
        ListNode biggerHead = new ListNode(-1);
        ListNode temp = head;
        ListNode p = lessHead;
        ListNode q = biggerHead;
        while(temp!= null){
            if(temp.val < x){
                p.next = temp;
                temp = temp.next;
                p = p.next;
                p.next = null;
            }else{
                q.next = temp;
                temp = temp.next;
                q = q.next;
                q.next = null;
            }
        }
        p.next = biggerHead.next;
        return lessHead.next;
    }


    //93复原IP地址
    //划分为4个串
    public List<String> restoreIpAddresses(String s) {
        List<String> ans = new ArrayList<>(); //保存最终的所有结果
        getAns(s, 0, "", ans, 0);
        return ans;
    }
    private void getAns(String s, int start, String temp, List<String> ans, int count) {
        if (s.length() - start > 3 * (4 - count)) {
            return;
        }
        //当前刚好到达了末尾
        if (start == s.length()) {
            //当前刚好是 4 部分，将结果加入
            if (count == 4) {
                ans.add(temp.substring(0,temp.length()-1));
            }
            return;
        }
        //当前超过末位，或者已经到达了 4 部分结束掉
        if (start > s.length() || count == 4) {
            return;
        }

        //保存的当前的解
        String newtemp = "";

        //加入 1 位数
        newtemp = temp + s.charAt(start) + "" + '.';
        getAns(s, start + 1, newtemp, ans, count + 1);

        //如果开头是 0，只能是1 位数
        if (s.charAt(start) == '0')
            return;

        //加入 2 位数
        if (start + 1 < s.length()) {
            newtemp = temp + s.substring(start, start + 2) + "" + '.';
            getAns(s, start + 2, newtemp, ans, count + 1);
        }

        //加入 3 位数
        if (start + 2 < s.length()) {
            int num = Integer.parseInt(s.substring(start, start + 3));
            if (num >= 0 && num <= 255) {
                newtemp = temp + s.substring(start, start + 3) + "" + '.';
                getAns(s, start + 3, newtemp, ans, count + 1);
            }
        }
    }

    //89 格雷编码
    //镜像反射   （ 不难 但是没见过
    public List<Integer> grayCode(int n) {
        List<Integer> res = new ArrayList<>();
        res.add(0);
        int head = 1;
        for(int i = 0 ; i< n ;i++){  //递推的次数
            for(int j = res.size() -1 ; j>=0 ;j--){
                res.add(head + res.get(j));
            }
            head <<= 1;
        }
        return res;
    }

    class Pair{
        int a ;
        char b ;

        public Pair(int a ,char b){
            this.a = a ;
            this.b = b;
        }
    }
    //76 最小覆盖字串 滑动窗口
    public String minWindow(String s, String t) {
            //两个计数器
            Map<Character,Integer> window = new HashMap<>();
            Map<Character,Integer> needs = new HashMap<>();

            for(int i = 0 ; i < t.length();i++){
                int count = needs.getOrDefault(t.charAt(i),0);
                needs.put(t.charAt(i),count+1);
            }
            //先记录下对应的坐标
            List<Pair> filteredS = new ArrayList<Pair>();
            for(int i = 0 ; i < s.length() ;i++){
                char c = s.charAt(i);
                if(t.contains(""+c)){
                    filteredS.add(new Pair(i,c));
                }
            }
            int l = 0 ,r = 0;
            int match = 0 ; //记录window中已经有多少个符合要求了
            int minLen = Integer.MAX_VALUE;
            int start = 0;
            int end = -1;
            while( r < filteredS.size()){
                char c = filteredS.get(r).b;
                int count = window.getOrDefault(c,0);
                window.put(c,count+1);
                if(window.get(c) == needs.get(c)){
                    match ++;
                }
                while(match == needs.size()){
                    if(filteredS.get(r).a - filteredS.get(l).a +1  < minLen){
                        start =  filteredS.get(l).a;
                        end = filteredS.get(r).a;
                        minLen = filteredS.get(r).a - filteredS.get(l).a+1;
                    }
                    char c2 = filteredS.get(l).b;
                    window.put(c2,window.get(c2)-1);
                    l++;
                    if(window.get(c2) < needs.get(c2)){
                        match --;
                    }
                }
                r++;
            }
            return s.substring(start,end+1 );
    }


    //81 搜索旋转排序数组 II 含重复元素
    public boolean search(int[] nums, int target) {
        int start = 0 ;
        int end = nums.length -1;
        int mid ;
        while(start < end){
            mid = start + (end - start )/2;
            if(nums[mid] == target) return true;
            if(nums[start] == nums[mid]){  //重复元素
                start++;
                continue;
            }
            if(nums[start] < nums[mid]){  //前半部分有序
                if(nums[mid] > target && nums[start] <= target) { //target在前半部分
                    end = mid -1 ;
                }else{
                    start = mid+1;
                }
            }else{   //后半部分有序
                if(nums[start] > nums[mid]){
                    if(nums[mid] < target && nums[end]>= target ){
                        start = mid +1;
                    }else{
                        end = mid -1;
                    }
                }
            }
        }
        if(start < nums.length && nums[start] == target) return true;
        return false;
    }

    //交错字符串
    //回溯 -> 二维动规
    public boolean isInterleave(String s1, String s2, String s3) {
        if(s3.length() != s2.length() + s1.length()){
            return false;
        }
        boolean [][] dp = new boolean [s1.length() +1][s2.length() +1];
        for(int i = 0 ; i <= s1.length() ;i++){
            for(int j = 0 ; j <= s2.length() ;j++){
                if(i == 0  && j ==0){
                    dp[i][j] = true;
                }else if ( i == 0 ){
                    dp[i][j] = dp[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j-1);
                } else if ( j== 0){
                    dp[i][j] = dp[i-1][j] && s1.charAt(i-1) ==s3.charAt(i+j-1);
                }else{
                    dp[i][j] = (dp[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j-1)) || (dp[i][j -1] && s2.charAt(j-1) == s3.charAt(i+j-1));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public static void main(String[] args) {
        ZeroTo100 one = new ZeroTo100();


       // System.out.print(str);



    }

}
