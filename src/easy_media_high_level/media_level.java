package easy_media_high_level;

import jdk.nashorn.api.tree.Tree;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class media_level {

    //三数之和
    //双指针+排序
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        for(int i = 0 ;  i < nums.length-2 ;i++){
            if(nums[i] > 0 ) break ; //trick
            if( i > 0 && nums[i] == nums[i-1]) continue;
            int a = nums[i];
            int l = i+1;
            int r = nums.length-1;
            while(l < r){
                if( a + nums[l] + nums[r] == 0){
                    List<Integer> row = new ArrayList<Integer>();
                    row.add(a); row.add(nums[l]); row.add(nums[r]);
                    result .add(row);
                    l++; r--;
                    while(r>l && nums[r] == nums[r+1])r--;
                }else if ( a + nums[l] + nums[r] > 0) {
                    r--;
                    while(r>l && nums[r] == nums[r+1])r--;
                }
                else l++;
            }
        }
        return result;
    }

    //矩阵置零
    // 给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法
    //一个简单的改进方案是使用 O(m + n) 的额外空间，即用两个数组记下需要变为0的行，列。但这仍然不是最好的解决方案。
    //你能想出一个常数空间的解决方案吗;如果[i][j]==0，我们将第 i 行和第 j 列的所有非零元素设成很大的负虚拟值（比如说 -1000000）
    //一个很好的思路！！可以看看！
    public void setZeroes(int[][] matrix) {
        boolean flag = false;   //第一列是否是0
        for(int i = 0 ; i<matrix.length ; i++){
            if(matrix[i][0] == 0){
                flag = true;
            }
            for(int j = 1 ; j < matrix[0].length ; j++){
                if(matrix[i][j] == 0){
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;

                }
            }
        }
        for(int i = 1 ; i < matrix.length ; i++){
            for (int j = 1 ; j< matrix[0].length ;j++){
                if(matrix[i][0] == 0 || matrix[0][j]== 0){
                    matrix[i][j] = 0;
                }
            }
        }
        //最后去看看 第一行和第一列是不是需要都变为0
        if(matrix[0][0] == 0){ //第一行
            for(int j = 0 ; j < matrix[0].length ; j++){
                matrix[0][j] = 0;
            }
        }
        if(flag){   //第一列
            for(int i = 0 ; i < matrix.length; i++){
                matrix[i][0] = 0;
            }
        }
    }

    //字母异位词分组
    //哈希：  1 - 计数散列分组  2- 字母顺序排序分组
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List> ans = new HashMap<>();
        int [] count = new int[26];
        for(String str : strs){
            Arrays.fill(count,0);
            for(char c : str.toCharArray()) count[c-'a']++;
            //计数散列
            StringBuilder sb = new StringBuilder("");
            for(int i = 0; i < 26 ;i++){
                sb.append("#");
                sb.append(count[i]);
            }
            String key = sb.toString();
            if(!ans.containsKey(key)) ans.put(key,new ArrayList());
            ans.get(key).add(str);
        }
        return new ArrayList(ans.values());
    }

    //无重复字符的最长子串
    //队列
    public int lengthOfLongestSubstring(String s) {
          if(s.length() == 0 ) return 0;
           Queue<Character> queue = new LinkedList<Character>();
           int max = 0;
           for(int i = 0 ; i < s.length() ; i++){
               Character temp = s.charAt(i);
               if(queue.isEmpty()){
                   queue.add(temp);
                   max =Math.max(max,queue.size());
                   continue;
               }
               while(queue.contains(temp)){
                   queue.poll();
               }
               queue.add(temp);
               max =Math.max(max,queue.size());
           }
           return max;
    }
    //最佳解法：滑动窗口
    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    //最长回文子串
    //通俗解法：扩展中心

    //动态规划
    public String longestPalindrome(String s) {
        int length = s.length();
        boolean [][] P = new boolean[length][length];
        int maxLen = 0;
        String maxPal = "";
        for(int len = 1 ; len <= length ;len++){
            for(int start = 0 ; start <length  ;start ++){
                int end = start + len -1;
                if(end >= length) break;
                if(len == 1  ) P[start][end] = true;
                else if(len ==2) P[start][end] = s.charAt(start)==s.charAt(end);
                else P[start][end] = P[start+1][end-1] && s.charAt(start) == s.charAt(end);
                if(P[start][end] && len > maxLen){
                    maxPal = s.substring(start,end+1);
                }
            }
        }
        return maxPal;
    }
    //上述降为 o(n)空间复杂度 看不懂
    public String longestPalindrome7(String s) {
        int n = s.length();
        String res = "";
        boolean[] P = new boolean[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= i; j--) {
                P[j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || P[j - 1]);
                if (P[j] && j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }

    //三元递增子序列
    public boolean increasingTriplet(int[] nums) {
        int m1 = Integer.MAX_VALUE;
        int m2 = Integer.MAX_VALUE;
        for(int i = 0 ; i< nums.length ;i++){
            if(nums[i] <= m1) m1 = nums[i];
            else if(nums[i] <= m2)  m2 = nums[2];
            else return true;
        }
        return false;
    }

    /**
     * Definition for singly-linked list.
     */
     public class ListNode {
          int val;
          ListNode next;
          ListNode(int x) { val = x; }
      }

    //逆序的两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
         int count = 0;
         ListNode head = new ListNode(-1);
         ListNode p = head;
         while(l1!= null && l2 != null){
             int a = (l1.val + l2.val+count)%10;
             ListNode temp = new ListNode(a);
             count = (l1.val + l2.val+count)/10;
             p.next = temp;
             l1 = l1.next;
             l2 = l2.next;
             p = p.next;
         }
         while(l1!= null){
             ListNode temp = new ListNode((l1.val+count)%10);
             count = (l1.val + count)/10;
             l1 = l1.next;
             p.next = temp;
             p = p.next;
         }
        while(l2!= null){
            ListNode temp = new ListNode((l2.val+count)%10);
            count = (l2.val + count)/10;
            l2 = l2.next;
            p.next = temp;
            p = p.next;
        }
         if(count!= 0){
             ListNode temp = new ListNode(count);
             p.next = temp;
         }
         return head.next;
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
    //中序遍历二叉树
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> result = new ArrayList<>();
        TreeNode curr = root;
        while( curr!= null ||!stack.isEmpty()){
            while(curr!= null){
                stack.add(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            result.add(curr.val);
            curr = curr.right;
        }
        return result;
    }

    //二叉树锯齿形层次遍历  不想reverse
    //法一：双栈 一个栈记录一层
    //法二：链表记录
    public List<List<Integer>> zizagLevelOrder(TreeNode root){
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // 记录是否反转
        boolean isReverse = false;
        while(!queue.isEmpty()){
            LinkedList<Integer> level = new LinkedList<>();
            int length = queue.size();
            for(int i = 0 ; i< length;i++){
                TreeNode node = queue.poll();
                if(node.left!= null) queue.add(node.left);
                if(node.right!= null) queue.add(node.right);
                if(!isReverse) {
                    level.add(node.val);
                }else{
                    level.addFirst(node.val);
                }
            }
            isReverse = !isReverse;
            result.add(level);
        }
        return result;
    }

    //从前序和中序遍历构造二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(preorder,inorder,0,preorder.length-1,0);
    }
    public TreeNode helper(int[] preorder ,int[] inorder,int left,int right,int index){
        if(left>right || left<0 || right>=inorder.length || index>= inorder.length){
            return null;
        }
        TreeNode node  = new TreeNode(preorder[index]);
        if(left == right) return node;
        int i = 0;
        for(i = left ; i <= right ;i++){
            if(inorder[i] == node.val)
            break;
        }
        index ++;
        node.left = helper(preorder,inorder,left,i-1,index);
        index = index+(i-1 - left)+1;
        node.right = helper(preorder,inorder,i+1,right,index);
        return node;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val,Node _left,Node _right,Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };
    //填充每个节点的下一个右侧节点指针
    public Node connect(Node root) {
        if(root == null) return root;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node node = queue.poll();
            if(node == root) node.next = null;
            if(node.left!= null) {
                adjustLeft(node.left,node);
                queue.add(node.left);
            }
            if(node.right!= null) {
                adjustRight(node.right,node);
                queue.add(node.right);
            }
        }
        return root;
    }
    public void adjustLeft(Node node,Node parent){
        node.next = parent.right;
    }
    public void adjustRight(Node node,Node parent){
        if(parent.next == null) node.next = null;
        else node.next = parent.next.left;
    }

    //二叉搜索树中第k小的元素
    //实质： 中序遍历后的结果的第k个元素

    //岛屿数量
    //实质：flooding

    //电话号码的字母组合
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if(digits.length() == 0) return result;
        Map<Character,String> map = new HashMap<>();
        map.put('2',"abc");
        map.put('3',"def");
        map.put('4',"ghi");
        map.put('5',"jkl");
        map.put('6',"mno");
        map.put('7',"pqrs");
        map.put('8',"tuv");
        map.put('9',"wxyz");
        Queue<String> queue = new LinkedList<>();
        for(int i = 0; i < digits.length() ;i++){
            int size = queue.size();
            Character dig = digits.charAt(i);
            String map_str = map.get(dig);
            for(int j = 0 ; j< size ; j++){
                String temp = queue.poll();
                for(int k = 0 ; k<map_str.length();k++){
                    queue.add(temp+map_str.charAt(k));
                }
            }
            if(queue.isEmpty()){
                for(int k = 0 ; k<map_str.length();k++){
                    queue.add(String.valueOf(map_str.charAt(k)));
                }
            }
        }
        result = new ArrayList<>(queue);
        return result;
    }

    //生成括号
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        String temp = "";
        int left = 0;
        int right = 0;
        backstacking(result,temp,0,0,n);
        return result;
    }
    public void backstacking(List<String> result ,String temp ,int left ,int right,int n){
        if(left == n && right == n){
            result.add(temp);
            return;
        }
        if(left == n && right <left){
            backstacking( result, temp +")",left,right+1,n);
            return ;
        }
        if(left < n && right <left ){
            backstacking( result, temp +")",left,right+1,n);
            backstacking( result, temp +"(",left+1,right,n);
            return ;
        }
        if(left < n && right == left){
            backstacking( result, temp +"(",left+1,right,n);
            return ;
        }
    }

    //全排列1-  1 2 3
    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> temp = new ArrayList<>();
        for(int num : nums){
            temp.add(num);
        }
        backtrack(temp,0,n,result);
        return result;
    }
    public void backtrack(List<Integer> nums , int first ,int n,List<List<Integer>> result ){
        if(first == n){
            result.add(new ArrayList<Integer>(nums));
        }
        for(int i = first ; i<n ;i++){
            Collections.swap(nums,first,i);
            backtrack(nums,first+1,n,result);
            Collections.swap(nums,first,i);
        }
    }

    //全排列2-可重复  1 1 2  回溯剪枝
    public List<List<Integer>> permuteUnique(int[] nums) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> temp = new ArrayList<>();
        //Arrays.sort(nums);
        for(int num : nums){
            temp.add(num);
        }
        backtrack3(temp,0,n,result);
        return result;
    }
    public void backtrack3(List<Integer> nums , int first ,int n,List<List<Integer>> result ){
        if(first == n){
            result.add(new ArrayList<Integer>(nums));
        }
        List<Integer> hasExchange = new ArrayList<Integer>();
        for(int i = first ; i<n ;i++){
            if(!hasExchange.contains(nums.get(i))) {
                hasExchange.add(nums.get(i));
                Collections.swap(nums, first, i);
                backtrack3(nums, first + 1, n, result);
                Collections.swap(nums, first, i);
            }
        }
    }

    //子集 -不含重复
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> temp = new ArrayList<>();
        backstrack2(temp,nums,0,nums.length,result);
        return result;
    }
    public void backstrack2(List<Integer> tmp ,int[]nums, int i , int n , List<List<Integer>> result){
        result.add(new ArrayList<>(tmp));
        for(int j = i ; j < n ; j++){
            tmp.add(nums[j]);
            backstrack2(tmp,nums,j+1,n,result);
            tmp.remove(tmp.size()-1);
        }
    }

    //子集2 - 含重复  1 1 2
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> temp = new ArrayList<>();
        Arrays.sort(nums);
        backstrack4(temp,nums,0,nums.length,result);
        return result;
    }
    public void backstrack4(List<Integer> tmp ,int[]nums, int i , int n , List<List<Integer>> result){
        result.add(new ArrayList<>(tmp));
        for(int j = i ; j < n ; j++){
            if(j>i && nums[j] == nums[j-1]) continue;
            tmp.add(nums[j]);
            backstrack4(tmp,nums,j+1,n,result);
            tmp.remove(tmp.size()-1);
        }
    }

    //单词搜索
    public boolean exist(char[][] board, String word) {
        if(word.length()==0) return true;
        int row_length = board.length;
        if(row_length == 0) return false;
        int col_length = board[0].length;
        if(col_length == 0) return false;
        boolean[][] visited = new boolean[row_length][col_length];
        for(int i = 0 ; i<row_length;i++){
            for (int j = 0 ; j < col_length ;j++){
                if(board[i][j] == word.charAt(0)){
                    visited[i][j] = true;
                    int length = 0;
                    if(backstrack5(board,i,j,visited,word,length+1)) return true;
                    visited[i][j] = false;
                }
            }
        }
        return false;
    }
    int[][]state = {{0,1},{0,-1},{1,0},{-1,0}};
    public boolean backstrack5(char[][] board ,int i ,int j ,boolean[][] visited,String word,int length){
        if(length >= word.length()) return true;
        for(int k = 0 ; k <state.length;k++){
            int x = i + state[k][0];
            int y = j + state[k][1];
            if(x >=0 && x < board.length && y>=0 && y < board[0].length  && board[x][y]== word.charAt(length) &&  !visited[x][y]){
                visited[x][y] = true;
                if(backstrack5(board,x,y,visited,word,length+1)) return true;
                visited[x][y] = false;
            }
        }
        return false;
    }

    //三个颜色分类
    //两个指针 0 全放头 2 全放尾
    public void sortColors(int[] nums) {
        int low = 0 ;
        int high = nums.length-1;
        int i = 0;
        while(i <=high){
            if(nums[i] == 2){
                swap(nums,i,high);
                high--;
            }else if(nums[i] == 0){
                swap(nums,i,low);
                low++;
                i++;
            }else{
                i++;
            }
        }
    }
    public void swap(int[] nums, int i ,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //前k个高频元素 nlogn
    //长度为k的优先级队列排序 105ms
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> counter = new HashMap<>();
        for(int i = 0 ; i< nums.length ;i++){
            if(counter.containsKey(nums[i])) counter.put(nums[i],counter.get(nums[i])+1);
            else counter.put(nums[i],1);
        }
        // init heap 'the less frequent element first'
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>((n1, n2) -> counter.get(n1) - counter.get(n2));

        for (int n :counter.keySet()){
            heap.add(n);
            if(heap.size() > k){
                heap.poll();
            }
        }
        // build output list
        List<Integer> top_k = new LinkedList();
        while (!heap.isEmpty())
            top_k.add(heap.poll());
        Collections.reverse(top_k);
        return top_k;
    }
    //桶排序
    public List<Integer> topKFrequent2(int[] nums, int k) {
        //step1—用哈希表统计数组中各元素出现的频次，表中“键”为元素数值，“值”为对应元素出现的频次
        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        for(int num:nums){
            if(map.get(num)==null) map.put(num, 1);
            else map.put(num, map.get(num)+1);//重复出现，则累计频次
        }
        //step2—桶排序
        List<Integer>[] bucket=new List[nums.length+1];//定义足够数量的桶
        for(int key:map.keySet()){
            int count=map.get(key);
            //把出现频次相同的元素“扔”到序号等于频次的桶中
            if(bucket[count]==null) bucket[count]=new ArrayList<Integer>();
            bucket[count].add(key);
        }
        //step3—“逆序”取数据
        List<Integer> result=new ArrayList<Integer>();
        for(int i=nums.length;i>0;i--)//注意i的起始值，当数组只有一个数据时
        {
            if(bucket[i]!=null && result.size()<k)
                result.addAll(bucket[i]);
        }
        return result;
    }

    //数组中第k个最大元素
    public int findKthLargest(int[] nums, int k) {
        return  quick_sort(nums,k,0,nums.length-1);
    }
    public int quick_sort(int[] nums,int k ,int l ,int r){
        int left = l ;
        int right = r;
        int pirot = nums[r];
        boolean flag = true;
        while(left <right){
            if(flag) {
                while (nums[left] < pirot) left++;
                if(left>=right) break;
                swap2(nums, left, right);
                right--;
            }else{
                while(nums[right] >pirot) right--;
                if(left>=right) break;
                swap2(nums,left,right);
                left++;
            }
            flag = !flag;
        }

        if(r - k + 1 == left) return nums[left];
        else if ( r - k + 1 < left) return quick_sort(nums,k-(r-left+1),l,left-1);
        else  return quick_sort(nums,k,left+1,r);
    }
    public void swap2(int[] nums, int i , int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //寻找峰值 二分法
    public int findPeakElement(int[] nums) {
        return search(nums, 0, nums.length - 1);
    }
    public int search(int[] nums, int l, int r) {
        if (l == r)
            return l;
        int mid = (l + r) / 2;
        if (nums[mid] > nums[mid + 1])   //处于下降空间
            return search(nums, l, mid);
        return search(nums, mid + 1, r);   //处于上升空间
    }

    //在排序数组中查找元素的第一个和最后一个位置，即range
    public int[] searchRange(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        int mid = 0;
        int []result = {-1,-1};
        while(left <=right){
            mid = (left + right)/2 ;
            if(nums[mid] > target){
                right = mid-1;
            }else if(nums[mid] < target){
                left = mid+1;
            }else{
                //向前找
                for(int i = mid ; i>=0 && nums[i] == target;i--) result[0] = i;
                //向后找
                for(int i = mid ; i< nums.length && nums[i] == target;i++) result[1] = i;
                return result;
            }
        }
        return result;
    }

    //合并区间
    public int[][] merge(int[][] intervals) {
        if(intervals.length== 0) return new int[0][2];
        List<int[]> lists = new ArrayList<int[]>();
        for(int i = 0 ;i < intervals.length ;i++){
            int[] row = intervals[i];
            lists.add(row);
        }
        Collections.sort(lists, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int[] last = lists.get(0);
        List<int[]> result = new ArrayList<>();
        for(int i = 1; i < lists.size() ;i++){
            int [] temp = lists.get(i);
            if(temp[0] > last[1]) {
                result.add(last);
                last = temp;
            }else {
                last[1] = Math.max(last[1],temp[1]);
            }
        }
        result.add(last);
        //转数组
        int[][] result2 = new int[result.size()][2];
        int index = 0;
        for(int[] row :result){
            result2[index] = row;
            index++;
        }
        return result2;
    }

    //搜索旋转排序数组
    public int search(int[] nums, int target) {
        if(nums.length == 0) return -1;
        int i = 0;
        for(i = 1 ; i< nums.length ;i++){
            if(nums[i] < nums[i-1]) break;
        }
        return Math.max(search(nums,target,0,i-1) ,search(nums,target,i,nums.length-1));
    }
    public int search(int[] nums ,int target, int l ,int r ){
        if(l >r) return  -1;
        int mid = (l + r)/ 2;
        if(nums[mid] == target) return mid;
        else if(nums[mid] > target) return search(nums,target,l,mid-1);
        else return search(nums,target,mid+1,r);
    }

    //跳跃游戏
    public boolean canJump(int[] nums) {
        if(nums == null) return false;
        int lastPosition = nums.length - 1;
        for(int i = nums.length - 1; i>= 0; i--){
            if(nums[i] +i >= lastPosition){
                lastPosition = i;
            }
        }
        return lastPosition == 0;
    }

    //跳跃游戏2
    public int jump(int[] nums) {
        if(nums.length<= 1 ) return 0;

        int[] dp = new int[nums.length];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i = 0 ; i< nums.length ;i++){
            for(int j = 1 ; j <= nums[i] ;j++){
                dp[i+j] = Math.min(dp[i] +1,dp[i+j]);
                if(i+j +1== nums.length) return dp[i+j];
            }
        }
        return dp[nums.length-1];
    }

    public int jump2(int[] nums) {
        if(nums.length<= 1 ) return 0;
        int maxPosition = 0;
        int step = 0;
        int end = 0;
        for(int i = 0 ; i< nums.length-1;i++){
            maxPosition = Math.max(maxPosition,nums[i]+i);
            if(i == end){  //我在上一跳的范围内能走的距离
                end = maxPosition;   //end 更新为我下一跳的范围
                step++;
            }
        }
        return step;
    }

    //不同路径 机器人
    public int uniquePaths(int m, int n) {
        int dp[][] = new int[m][n];
        for(int i = 0 ; i < m;i++){
            dp[i][0]= 1;
        }
        for(int j = 0 ; j <n ;j++){
            dp[0][j] = 1;
        }
        for(int i = 1 ; i<m ;i++){
            for(int j = 1 ; j<n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    //零钱兑换 最少的硬币数
    public int coinChange(int[] coins, int amount) {
        if(amount == 0 ) return 0 ;
        int[] dp = new int[amount +1];
        Arrays.fill(dp,-1);
        dp[0] = 0;
        for(int i = 1; i <= amount ;i++){
            for(int j = 0 ; j<coins.length ;j++){
                if(coins[j] <= i && dp[i-coins[j]]!= 0){
                    if(dp[i] == 0 || dp[i] > dp[i-coins[j]]+1){
                        dp[i] = dp[i-coins[j]] + 1;
                    }
                }

            }
        }
        return dp[amount];
    }

    //零钱兑换2   总共多少种组合数
    //完全背包问题
    public int change(int amount, int[] coins) {
        if(amount == 0) return 0;
        int [] dp = new int[amount+1];
        dp[0] = 1;
        for(int coin :coins){
            for(int i = coin; i<= amount ; i++){
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    //最长递增子序列
    //n^2
    public int lengthOfLIS(int[] nums) {
        if(nums.length == 0) return 0;
        int [] dp = new int[nums.length];
        dp[0] = 1; //以i结尾的最长的子序列
        int max = 1;
        for(int i = 1 ; i< nums.length;i++){
            for(int j = 0 ; j< i ;j++){
                if(nums[j] < nums[i]){
                    dp[i] = Math.max(dp[i],dp[j]);
                }
            }
            dp[i] = dp[i] +1;
            max= Math.max(max,dp[i]);
        }
        return max;
    }

    //快乐数
    public boolean isHappy(int n) {
        for(int i = 0 ;i  <100 ;i++) {
            int sum = 0;
            while (n != 0) {
                sum+= (n%10) * (n%10);
                n = n / 10;
            }
            if (sum == 1) return true;
            n = sum;
        }
        return false;
    }

    //实现pow(x,n)
    //二分乘  logn
    public double myPow(double x, int n) {
        long N = n;
        //处理正负
        if(n <0){
            x = 1/x;
            N = -N;
        }
        return fastPow(x,N);
    }
    double fastPow(double x, long  n){
        if(n == 0) return 1.0;
        double half = fastPow(x,n/2);
        if( n%2 == 0) return half * half;
        else return half* half * x;
    }

    //x的平方根  实现 sqrt(x)
    public int mySqrt(int x) {
        long a = x;
        long x0 = a;
        while(x0 * x0 > a){
            x0 = (x0 + a / x0)/2;
        }
        return (int)x0;
    }
    public int mySqrt2(int x) {
        if(x == 0) return 0;
        long left = 1;
        long right = x/2;
        while(left <right){
            long mid = (left + right)/2;
            long square = mid * mid ;
            if(square > x){
                right = mid- 1;
            }else if( square < x ){
                left = mid ;
            }else return (int)mid;
        }
        return (int)left;
    }

    //两数相除
    //成倍的减
    public int divide(int dividend, int divisor) {
        return 0;
    }

    //两整数之和
    //位运算
    public int getSum(int a, int b) {
        boolean sign = true;
        int a2 = a*a ;
        int b2 = b*b;
        int c = 2* a * b;
        if(a <0 && b < 0) sign = false;
        else if( a< 0 && a2 > b2) sign = false;
        else if( b<0 && b2> a2) sign = false;
        int result = (int)Math.sqrt((double)(a2+b2+c));
        return sign ? result: 0-result ;
    }
    public int getSum2(int a ,int b){
        int and = a & b;
        int eor = a ^ b;
        if(and!= 0){
            and <<= 1;
        }
        while((and & eor)!= 0 ){
            int newAnd = and & eor;
            eor = and ^ eor;
            and = newAnd <<1;
        }
        return and ^ eor;
    }

    //任务调度
    public int leastInterval(char[] tasks, int n) {
        if(tasks.length == 0) return 0;
        int [] counter = new int[26];
        int max = 0;
        for(int i = 0 ; i< tasks.length ;i++){
            counter[tasks[i] -'A']++;
            if(counter[tasks[i] - 'A'] > max){
                max = counter[tasks[i] - 'A'];
            }
        }
        int ext = 0;
        for(int i = 0 ;i < counter.length ;i++){
            if(counter[i] == max) ext++;
        }
        int sum = (max-1)* (n+1)+ext;
        if(sum > tasks.length) return sum;
        else return tasks.length;
    }

    public static void main(String[] args) {
        media_level so = new media_level();
        //int a = so.strStr("mississippi", "issip");
        //String res = so.countAndSay(5);
        char [][] board = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
       // so.exist(board,"ABCCED");
       // System.out.print(result);
        //int[] nums = {5,7,7,8,8,10};
       // so.searchRange(nums,8);
       // so.findKthLargest(nums,2);

//        int [] nums = {10,9,2,5,3,7,101,18};
//        so.lengthOfLIS(nums);
        so.isHappy(7);
    }

}
