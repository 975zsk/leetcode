package sequence;

import jdk.nashorn.api.tree.Tree;

import java.sql.BatchUpdateException;
import java.util.*;

public class OneTo200 {

    //187 重复的DNA序列
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        if(s.length() < 10) return res;
        //储存已经遍历过的子字符串
        Set<String> set1 = new HashSet<>();
        //储存符合条件的子字符串
        Set<String> set2 = new HashSet<>();
        for(int i = 0;i + 10 <= s.length();i++){
            String seq = s.substring(i,i+10);
            if(set1.contains(seq)){
                set2.add(seq);
            }
            set1.add(seq);
        }
        res.addAll(set2);
        return res;
    }

    //136 只出现一次的数字1  二进制
    //137 只出现一次的数字2  三进制
    public int singleNumber(int[] nums) {
        int ones = 0, twos = 0, threes = 0;

        for(int num : nums){
            twos |= ones & num;   //先看二次的
            ones ^= num;
            threes = ones & twos;
            ones &= ~threes;

            twos &= ~threes;
        }
        return ones;
    }
    //只出现一次的元素3   有两次元素只出现一次，其余三两次
    //分治+异或运算
    public int[] singleNumber3(int[] nums) {
        int  xor = 0 ;
        for(int num: nums){
            xor ^= num;
        }
        //取异或值最后一个二进制为1的数作为mask
        int mask = xor & (-xor);

        int [] ans = new int[2];
        for(int num : nums){
            if( (num & mask ) == 0){
                ans[0] ^= num;
            }else{
                ans[1] ^= num;
            }
        }
        return ans;
    }

    //174 地下城游戏
    public int calculateMinimumHP(int[][] dungeon) {

        if(dungeon == null || dungeon.length == 0 || dungeon[0].length == 0){
            return 0 ;
        }
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] dp = new int[row][col];
        dp[row-1][col-1] = Math.max(0 , -dungeon[row-1][col-1]);

        for(int i = row -1 ; i >= 0 ;i--){
            int needMin = dp[i+1][col-1] - dungeon[i][col-1];
            dp[i][col-1] = Math.max(0 ,needMin);
        }

        for(int j = col -1 ;j>= 0 ;j--){
            int needMin = dp[row-1][j] - dungeon[row-1][j];
            dp[row-1][j] = Math.max(0,needMin);
        }
        for(int i = row -2 ; i>=0 ;i--){
            for(int j = col-2 ;j>=0 ;j--){
                //needMin + globalDun[i][j] = Math.min(dp[i + 1][j], dp[i][j + 1])
                int needMin = Math.min(dp[i+1][j],dp[i][j+1]) - dungeon[i][j];
                dp[i][j] = Math.max(0 ,needMin);
            }
        }
        return dp[0][0]+1;
    }

    /**
     * Definition for a binary tree node.
     * */
     public class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
     }

     //115 不同的子序列
    //动态规划
     public int numDistinct(String s, String t) {
         int[][] dp = new int[t.length()+1][s.length()+1];
         for(int j = 0 ; j < s.length()+1;j++) dp[0][j] = 1;
         for(int i = 1 ; i < t.length() +1;i++){
             for(int j = 1 ; j< s.length()+1 ;j++){
                 if(s.charAt(j-1) == t.charAt(i-1)) dp[i][j] = dp[i-1][j-1] + dp[i][j-1];  //用这个a 不用这个a
                 else dp[i][j] = dp[i][j-1];
             }
         }
         return dp[t.length()][s.length()];
     }

     //117 填充每个节点的下一个右侧节点指针 II
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
     public Node connect(Node root) {
         if(root == null) return root;
         Queue<Node> nodes = new LinkedList<>();
         root.next = null;
         nodes.add(root);
         while(!nodes.isEmpty()){
             // 把该层的结点相连
             Queue<Node> next_level_nodes = new LinkedList<>();
             Node pre = nodes.poll();
             if(pre.left!= null) next_level_nodes.add(pre.left);
             if(pre.right!= null) next_level_nodes.add(pre.right);
             while(!nodes.isEmpty()){
                 Node temp = nodes.poll();
                 if(temp.left!= null) next_level_nodes.add(temp.left);
                 if(temp.right!= null) next_level_nodes.add(temp.right);
                 pre.next = temp;
                 pre = temp;
             }
             nodes = next_level_nodes;
         }
         return root;
     }

     //126 单词接龙 2  最短转换
     //法1：先bfs一次 找每个单词与初始单词的最短距离 ，用于剪枝
     // 再dfs找路径
     //法2：直接bfs 搜到我要的层，把这层搜完就停止
     //法3：bfs  双向搜索  + dfs
     public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
         List<List<String>> ans = new ArrayList<>();
         if (!wordList.contains(endWord)) {
             return ans;
         }
         // 利用 BFS 得到所有的邻居节点
         HashMap<String, List<String>> map = new HashMap<>();   //单词，和单词的邻接结点，这里不是全部的，是最短层以内 和可达endword的
         bfs(beginWord, endWord, wordList, map);
         List<String> temp = new ArrayList<String>();
         // temp 用来保存当前的路径
         temp.add(beginWord);
         findLaddersHelper(beginWord, endWord, map, temp, ans);
         return ans;
     }
    //利用递归实现了双向搜索
    private void bfs(String beginWord, String endWord, List<String> wordList, HashMap<String, List<String>> map) {
        Set<String> set1 = new HashSet<String>();
        set1.add(beginWord);
        Set<String> set2 = new HashSet<String>();
        set2.add(endWord);
        Set<String> wordSet = new HashSet<String>(wordList);  //总池子
        bfsHelper(set1, set2, wordSet, true, map);
    }
    // direction 为 true 代表向下扩展，false 代表向上扩展
    private boolean bfsHelper(Set<String> set1, Set<String> set2, Set<String> wordSet, boolean direction,
                              HashMap<String, List<String>> map) {
        //set1 为空了，就直接结束
        //比如下边的例子就会造成 set1 为空   "hot"   "dog"   ["hot","dog"]
        if(set1.isEmpty()){
            return false;
        }
        if (set1.size() > set2.size()) {    // set1 的数量多，就反向扩展
            return bfsHelper(set2, set1, wordSet, !direction, map);
        }
        // 将已经访问过单词删除 再前面的层 出现过的话 ，在后面的层出现只会更长，这个地方提速！
        wordSet.removeAll(set1);
        wordSet.removeAll(set2);
        boolean done = false;
        // 保存新扩展得到的节点
        Set<String> set = new HashSet<String>();
        for (String str : set1) {
            for (int i = 0; i < str.length(); i++) {
                char[] chars = str.toCharArray();
                // 尝试所有字母
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if(chars[i] == ch){
                        continue;
                    }
                    chars[i] = ch;
                    String word = new String(chars);
                    // 根据方向得到 map 的 key 和 val
                    String key = direction ? str : word;  //也就是说，最后还是像链表一样的结构， 用hashmap实现，key：str当前单词， value：word下一个单词
                    String val = direction ? word : str;
                    List<String> list = map.containsKey(key) ? map.get(key) : new ArrayList<String>();
                    //如果相遇了就保存结果
                    if (set2.contains(word)) {
                        done = true;
                        list.add(val);
                        map.put(key, list);
                    }
                    //如果还没有相遇，并且新的单词在 word 中，那么就加到 set 中
                    if (!done && wordSet.contains(word)) {
                        set.add(word);
                        list.add(val);
                        map.put(key, list);
                    }
                }
            }
        }
        //一般情况下新扩展的元素会多一些，所以我们下次反方向扩展  set2
        return done || bfsHelper(set2, set, wordSet, !direction, map);  //如果 down结束了，就不再向继续了，否则继续。

    }
    //得到map邻接表后，从上往下搜索
    private void findLaddersHelper(String beginWord, String endWord, HashMap<String, List<String>> map,
                                   List<String> temp, List<List<String>> ans) {
        if (beginWord.equals(endWord)) {
            ans.add(new ArrayList<String>(temp));
            return;
        }
        // 得到所有的下一个的节点
        List<String> neighbors = map.getOrDefault(beginWord, new ArrayList<String>());
        for (String neighbor : neighbors) {
            temp.add(neighbor);
            findLaddersHelper(neighbor, endWord, map, temp, ans);
            temp.remove(temp.size() - 1);
        }
    }


     //127 单词接龙1 的解法
     public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
         List<List<String>> result = new ArrayList<>();
         List<String> row = new ArrayList<>();
         row.add(beginWord);
         dfs(wordList,beginWord,endWord,row,result);
         return result;
     }
     private boolean  dfs( List<String> wordList ,String tempWord,String endWord, List<String> row ,List<List<String>> result){
         if(tempWord .equals(endWord)){
             result.add(new ArrayList<>(row));
             return true;
         }
         for(int i = 0 ; i< wordList.size() ;i++ ){
             String word = wordList.get(i);
             if(!row.contains(word) && match(tempWord,word)){
                  //是否只差一个字母
                  row.add(word);
                  dfs(wordList,word,endWord,row,result);
                  row.remove(row.size()-1);
             }
         }
         return false;
     }
     private boolean match(String a, String b){
         int diff = 0;
         for(int i = 0 ; i< a.length();i++){
             if(a.charAt(i) != b.charAt(i)) diff++;
             if(diff >=2) return false;
         }
         if(diff == 0 ) return false;
         return true;
     }


     //130 被围绕的区域
    //常规操作： 从边缘的0 开始泛红bfs，设置为# ，然后 再遍历一遍矩阵 覆盖0 为X ，#为0
     public class Pos{
         int i;
         int j;
         Pos(int i, int j) {
             this.i = i;
             this.j = j;
         }
     }
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 从边缘第一个是o的开始搜索
                boolean isEdge = i == 0 || j == 0 || i == m - 1 || j == n - 1;
                if (isEdge && board[i][j] == 'O') bfs(board, i, j);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == '#') board[i][j] = 'O';
            }
        }
    }
    private void bfs(char[][] board, int i, int j) {
        Queue<Pos> queue = new LinkedList<>();
        queue.add(new Pos(i, j));
        board[i][j] = '#';
        while (!queue.isEmpty()) {
            Pos current = queue.poll();
            // 上
            if (current.i - 1 >= 0 && board[current.i - 1][current.j] == 'O') {
                queue.add(new Pos(current.i - 1, current.j));
                board[current.i - 1][current.j] = '#';
                // 没有continue.
            }
            // 下
            if (current.i + 1 <= board.length - 1 && board[current.i + 1][current.j] == 'O') {
                queue.add(new Pos(current.i + 1, current.j));
                board[current.i + 1][current.j] = '#';
            }
            // 左
            if (current.j - 1 >= 0 && board[current.i][current.j - 1] == 'O') {
                queue.add(new Pos(current.i, current.j - 1));
                board[current.i][current.j - 1] = '#';
            }
            // 右
            if (current.j + 1 <= board[0].length - 1 && board[current.i][current.j + 1] == 'O') {
                queue.add(new Pos(current.i, current.j + 1));
                board[current.i][current.j + 1] = '#';
            }
        }
    }
    //法2：并查集操作： 很慢。。。 只是新学了一个数据结构 挺有趣的
     class UnionFind {
         // 并查集的思想就是，同一个连通区域内的所有点的根节点是同一个。
         // 将每个点映射成一个数字。
         // 先假设每个点的根节点就是他们自己，然后我们以此输入连通的点对，然后将其中一个点的根节点赋成另一个节点的根节点，
         // 这样这两个点所在连通区域又相互连通了。

         int[] parents;
         public UnionFind(int totalNodes) {
             parents = new int[totalNodes];
             for (int i = 0; i < totalNodes; i++) { //初始化时每个点的父节点都是自己
                 parents[i] = i;
             }
         }
         // 合并连通区域是通过find来操作的, 即看这两个节点是不是在一个连通区域内.
         void union(int node1, int node2) {
             int root1 = find(node1);
             int root2 = find(node2);
             if (root1 != root2) {
                 parents[root2] = root1;
             }
         }
         //寻找结点指向的父亲
         int find(int node) {
             while (parents[node] != node) {
                 // 当前节点的父节点 指向父节点的父节点.
                 // 保证一个连通区域最终的parents只有一个.
                 parents[node] = parents[parents[node]];
                 node = parents[node];
             }
             return node;
         }
         //判断两个结点 是否连通
         boolean isConnected(int node1, int node2) {
             return find(node1) == find(node2);
         }
     }
     public void solve2(char[][] board) {
         if (board == null || board.length == 0)
             return;
         int rows = board.length;
         int cols = board[0].length;
         // 用一个虚拟节点, 边界上的O 的父节点都是这个虚拟节点
         UnionFind uf = new UnionFind(rows * cols + 1);
         int dummyNode = rows * cols;
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if (board[i][j] == 'O') {
                     // 遇到O进行并查集操作合并
                     if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                         // 边界上的O,把它和dummyNode 合并成一个连通区域.
                         uf.union(node(i, j ,cols), dummyNode);
                     } else {
                         // 上下左右有0的合并成一个连通区域
                         if (i > 0 && board[i - 1][j] == 'O')
                             uf.union(node(i, j ,cols), node(i - 1, j ,cols));
                         if (i < rows - 1 && board[i + 1][j] == 'O')
                             uf.union(node(i, j ,cols), node(i + 1, j ,cols));
                         if (j > 0 && board[i][j - 1] == 'O')
                             uf.union(node(i, j ,cols), node(i, j - 1 ,cols));
                         if (j < cols - 1 && board[i][j + 1] == 'O')
                             uf.union(node(i, j ,cols), node(i, j + 1 ,cols));
                     }
                 }
             }
         }
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 if (uf.isConnected(node(i, j ,cols), dummyNode)) {
                     // 和dummyNode 在一个连通区域的,那么就是O；
                     board[i][j] = 'O';
                 } else {
                     board[i][j] = 'X';
                 }
             }
         }
     }
     int node(int i, int j,int cols) {
        return i * cols + j;
     }

     // 129 根到叶子结点的数字之和
    // dfs
     public int sumNumbers(TreeNode root) {
       return dfs(root,0);
     }
     private int dfs(TreeNode node, int sum ){
         if(node == null) return sum;
          int val = node.val;
          sum = sum* 10 + val;
          int left = 0 ,right = 0;
          if(node.left == null && node.right == null){
              return sum;
          }
          if(node.left != null) {
              left = dfs(node.left, sum);
          }
          if(node.right != null) {
              right = dfs(node.right, sum);
          }
          return left + right;
     }

     //132 分割回文串2  返回最小分割次数
     public int minCut(String s) {
         boolean[][] dp = new boolean[s.length()][s.length()];  //dp[i][j] 从i到j是否是回文的
         int length = s.length();
         for (int len = 1; len <= length; len++) {
             for (int i = 0; i <= s.length() - len; i++) {
                 int j = i + len - 1;
                 dp[i][j] = s.charAt(i) == s.charAt(j) && (len < 3 || dp[i + 1][j - 1]);
             }
         }
         int[] dp2  = new int[s.length()];
         dp2[0] = 0;
         for(int end = 1 ; end < s.length() ;end++){
             int temp = Integer.MAX_VALUE;  //以end结尾的最小的切割数
             for(int start = 0 ; start <= end ; start ++){
                 if(dp[start][end]){
                     if(start == 0) {
                         temp = 0 ;
                         break ; //第一个匹配到， 不需要切割
                     }else{
                         temp = Math.min(temp,dp2[start-1]+1);
                     }
                 }
             }
             dp2[end] = temp;
         }
         return dp2[s.length()-1];
     }

     //133 克隆图 深克隆  bfs
    //我总是搞不清楚这个的逻辑 其实很简单 牵扯的地址什么的
     class Node2 {
         public int val;
         public List<Node2> neighbors;

         public Node2() {}

         public Node2(int _val,List<Node2> _neighbors) {
             val = _val;
             neighbors = _neighbors;
         }
     };
     public Node2 cloneGraph(Node2 node) {
         if (node == null) {
             return node;
         }
         Queue<Node2> queue = new LinkedList<>();
         Map<Integer,Node2> map = new HashMap<>();
         //第一个结点
         Node2 first_node = new Node2();
         first_node.val = node.val;
         first_node.neighbors = new ArrayList<>();
         queue.add(node);
         map.put(first_node.val,first_node);
         while(!queue.isEmpty()){
             Node2 cur = queue.poll();
             for(Node2 temp : cur.neighbors){
                 if(!map.containsKey(temp.val)){
                     Node2 new_node = new Node2();
                     new_node.val = temp.val;
                     new_node.neighbors = new ArrayList<>();
                     map.put(new_node.val,new_node);
                     queue.add(temp);
                 }
                 map.get(cur.val).neighbors.add(map.get(temp.val));
             }
         }
         return first_node;
     }


     //134 加油站
    //环形 可以从任意点出发 而不是只能从0索引出发
     public int canCompleteCircuit(int[] gas, int[] cost) {
         int n = gas.length;
         int total_tank = 0;
         int cur_tank = 0;
         int start_station = 0;
         for(int i = 0 ; i< n ;i++){
             total_tank += gas[i] - cost[i];
             cur_tank += gas[i] - cost[i];
             if(cur_tank < 0){
                 cur_tank = 0;
                 start_station = i+1;
             }
         }
         return total_tank>=0 ? start_station :-1;
     }

     //135 分发糖果 上下爬山
    //逻辑处理可以看看！！ 锻炼逻辑能力
    public int candy(int[] ratings){
        int sum = 0;
        int up = 0;
        int down = 0;
        int last_flag = 0;   //1 up  -1 down  0 =
        for(int i = 1 ; i< ratings.length ;i++){
            int flag = (ratings[i] > ratings[i-1] ) ? 1: (ratings[i] < ratings[i-1]? -1 : 0);  //此时是上坡还是下坡
            if(last_flag > 0 && flag == 0){
                sum+= count(up+1);
                up = 0;
            }
            else if(last_flag < 0 && flag>= 0){
                sum+= count(up)+ count(down) + Math.max(up,down)+1 -1 ;
                if(flag == 0) {
                    sum++; up = 0;
                }else{
                    up = 1;
                }
                down = 0;
            }
            else if(last_flag == 0  && flag == 0){
                sum++;
            }
            else if(flag >0) up++;
            else if(flag <0) down ++;
            last_flag = flag;
        }
        sum+= count(up) + count(down) + Math.max(up,down)+1;
        return sum;
    }
    private int count(int n) {
        return (n * (n + 1)) / 2;
    }

    //140 单词拆分2
    //先用动态规划判断单词是否可以拆分 ，再回溯
    public List<String> wordBreak(String s, List<String> wordDict) {
         int len = s.length();
         boolean [] dp = new boolean[len];  //以i结尾的string 是否能拆分

        dp[0] = wordDict.contains(s.charAt(0));
        for(int i = 0 ; i< s.length() ;i++){
            if(wordDict.contains(s.substring(0,i+1))){
                dp[i] = true;
                continue;
            }
            for(int j = 0 ; j < i ;j++){
                if(dp[i] && wordDict.contains(s.substring(i+1,j+1))){
                    dp[i] = true;
                    break;
                }
            }
        }
        List<String> res = new ArrayList<>();
        if(dp[len-1]){
            LinkedList<String> queue = new LinkedList<>();
            dfs(s,len-1,wordDict,res,dp,queue);
        }
        return res;
    }
    private void dfs(String s ,int end ,List<String> wordDict ,List<String> res ,boolean []dp , LinkedList<String> queue){
         if(wordDict.contains(s.substring(0,end+1))){  //到头了
             queue.addFirst(s.substring(0,end+1));
             StringBuilder stringBuilder = new StringBuilder();  //拼接
             for (String word : queue) {
                 stringBuilder.append(word);
                 stringBuilder.append(" ");
             }
             stringBuilder.deleteCharAt(stringBuilder.length() - 1);
             res.add(stringBuilder.toString());
             queue.removeFirst();
         }
         for(int i = 0 ; i< end ;i++){
             if(dp[i]){
                 String new_node = s.substring(i+1 ,end+1);
                 if(wordDict.contains(new_node)){
                     queue.addFirst(new_node);
                     dfs(s,i,wordDict,res,dp,queue);
                     queue.removeFirst();
                 }
             }
         }
    }

    //143 重排链表
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public void reorderList(ListNode head) {
         if(head == null) return ;
         ListNode slow = head;
         ListNode fast = head;
         while(fast!= null && fast.next != null){
             slow = slow.next;
             fast = fast.next.next;
         }
         ListNode reverseHead = reverseList(slow.next);
         slow.next = null;  //截断！
         ListNode res = new ListNode(-1);
         ListNode q = res;
         while(reverseHead!= null){
             res.next = head;
             head = head.next;
             res = res.next;
             res.next = reverseHead;
             reverseHead = reverseHead.next;
             res = res.next;
         }
         res.next = head;
         head = q.next;
    }
    public ListNode reverseList(ListNode node){
         ListNode pre = null;
         ListNode p = node;
         while(p!= null){
             ListNode next = p.next;
             p.next = pre;
             pre = p;
             p = next;
         }
         return pre;
    }

    //146 LRU缓存机制（最近最少使用）
    class LRUCache {
        class DlinkedNode{
            int key;
            int value ;
            DlinkedNode pre;
            DlinkedNode next;
        }
         int size ;
         int capacity ;
         DlinkedNode head ;
         DlinkedNode tail ;
         Map<Integer,DlinkedNode> cache;  //记录有没有  key:node

        public LRUCache(int capacity) {
            size = 0;
            this.capacity = capacity;
            head = new DlinkedNode();
            tail = new DlinkedNode();
            cache = new HashMap<>();
            head.next = tail;
            tail.pre = head;
        }
        public int get(int key) {
            DlinkedNode node = cache.get(key);
            if(node == null) return -1;
            moveToHead(node);
            return node.value;
        }
        public void put(int key, int value) {
            DlinkedNode node = cache.get(key);
            if(node == null){
                DlinkedNode newNode = new DlinkedNode();
                newNode.key = key;
                newNode.value = value;
                cache.put(key,newNode);
                addNode(newNode);
                size ++;
                if(size >capacity){
                    DlinkedNode tail = PopTail();
                    cache.remove(tail.key);
                    size--;
                }
            }else{
                node.value = value;
                moveToHead(node);
            }
        }

        private void moveToHead(DlinkedNode node){
             removeNode(node);
             addNode(node);
        }
        private void addNode(DlinkedNode node){
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
        }
        private void removeNode(DlinkedNode node){
            DlinkedNode prev = node.pre;
            DlinkedNode next = node.next;
            prev.next = next;
            next.pre = prev;
        }
        private DlinkedNode PopTail(){
            DlinkedNode node = tail.pre;
            DlinkedNode pre = node.pre;
            pre.next = tail;
            tail.pre = pre;
            node.pre = null;
            node.next = null;
            return node;
        }
    }

    //147 对联表进行插入排序
    public ListNode insertionSortList(ListNode head) {
         if(head == null || head.next == null) return head;
        ListNode newHead = head;
        ListNode p = head.next;
        head.next = null;
        while(p!= null){
            ListNode next = p.next;
            p.next = null;
            newHead = sort(newHead,p);
            p = next;
        }
        return newHead;
    }
    public ListNode sort(ListNode head,ListNode node){
        ListNode cur = head;
        if(node.val <= head.val){  //插在头部
            node.next = cur;
            head = node;
            return head;
        }
        while(cur.next!= null ){  //插在中间
            if(cur.next.val > node.val){
                ListNode temp = cur.next;
                cur.next = node;
                node.next = temp;
                return head;
            }
            cur = cur.next;
        }
        cur.next = node;
        return head;
    }

    //151 翻转字符串中的单词
    public String reverseWords(String s) {
         if(s.length() == 0 ) return s;
       String[] list = s.split(" ");
       StringBuilder sb = new StringBuilder();
       for(int i = list.length-1 ; i>= 0 ;i--){
           if(!list[i].equals(" ")){
               sb.append(list[i]);
               sb.append(" ");
           }
       }
       if(sb.length()>0){
           sb.deleteCharAt(sb.length()-1);
       }
       return sb.toString();
    }


    //152 乘积最大子序列
    public int maxProduct(int[] nums) {
         int max = Integer.MIN_VALUE;
         int imax = 1;
         int imin = 1;
         for(int i = 0 ; i< nums.length ;i++){
             if(nums[i]< 0){
                 int tmp = imax;
                 imax = imin;
                 imin = tmp;
             }
             imax = Math.max(imax * nums[i] ,imax);
             imin = Math.min(imin * nums[i],imin);
             max= Math.max(imax,max);
         }
         return max ;
    }

    //153 寻找旋转排序数组的最小值
    public int findMin(int[] nums) {
         int left = 0;
         int right = nums.length-1;

         while(left < right){
             int mid = left + (right - left)/2;
             if(nums[mid] >= nums[left] && nums[mid] >= nums[right]){
                 left = mid + 1;
             }else if(nums[mid] <= nums[left] && nums[mid]<= nums[right]){
                 right = mid ;
             }else{
                 return nums[left];
             }
         }
         return nums[left];
    }

    //154 寻找旋转排序数组的最小值2
    //可能存在重复
    public int findMin2(int[] nums) {
        int left = 0;
        int right = nums.length-1;

        while(left < right){
            int mid = left + (right - left)/2;
            if(nums[mid] >= nums[left] && nums[mid] > nums[right]){
                left = mid + 1;
            }else if(nums[mid] < nums[left] && nums[mid]<= nums[right]){
                right = mid;
            }else {
                right --; //这个地方错了好多，不能直接想着 这边是顺序 或者 10 1 10 10 10 10 10 这种情况 就直接返回 nums[left]
            }
        }
        return nums[left];
    }

    //164 最大间距
    //桶排序 + 鸽巢原理
    class Bucket{
       public boolean used = false;
       int min = Integer.MAX_VALUE;  //桶里的最小值
       int max = 0;  //桶里的最大值
    }
    public int maximumGap(int[] nums) {
         if(nums.length <= 1) return 0;
         int max = nums[0];
         int min = nums[0];
         for(int i = 0 ; i < nums.length ;i++){
             if(nums[i] > max) max = nums[i];
             if(nums[i] < min) min = nums[i];
         }
         int bucketSize = (max - min)/ (nums.length -1);
         if(bucketSize == 0) bucketSize = 1;
         int bucketNum = (max - min)/bucketSize +1;
         Bucket[] buckets = new Bucket[bucketNum];

         //创建桶
         for(int i = 0 ; i< nums.length ;i++){
             int bucketIdx = (nums[i] - min) /bucketSize ;
             if(buckets[bucketIdx] == null){
                 buckets[bucketIdx] = new Bucket();
                 buckets[bucketIdx].used = true ;
             }
             buckets[bucketIdx].min = Math.min(nums[i], buckets[bucketIdx].min);
             buckets[bucketIdx].max = Math.max(nums[i],buckets[bucketIdx].max);
         }
         //寻找间距
        int preMax = min;
         int maxGap = 0;
         for(Bucket bucket : buckets){
             if(bucket == null) continue;
             maxGap = Math.max(maxGap,bucket.min - preMax);
             preMax = bucket.max;
         }
         return maxGap;
    }

    //165 比较版本号
    public int compareVersion(String version1, String version2) {
         String[] list_v1 = version1.split("\\.");
         String[] list_v2 = version2.split("\\.");
         int len = Math.min(list_v1.length ,list_v2.length);
         for(int i = 0 ; i < len ; i++){
             int v1 = Integer.parseInt(list_v1[i]);
             int v2 = Integer.parseInt(list_v2[i]);
             if(v1 > v2) return  1;
             else if (v1 < v2) return -1;
         }
         if(list_v1.length== list_v2.length){
             return 0;
         }
         if(list_v1.length > len ){
             for(int i = len ; i < list_v1.length ;i++){
                 if(Integer.parseInt(list_v1[i]) != 0) return 1;
             }
         }
         if(list_v2.length > len ){
            for(int i = len ; i < list_v2.length ;i++){
                if(Integer.parseInt(list_v2[i]) != 0) return -1;
            }
        }
          return 0;
    }

    //173 二叉搜索树迭代器
    //用栈模拟中序遍历
    class BSTIterator {
        private LinkedList <TreeNode> stack = new LinkedList<TreeNode>();
        public BSTIterator(TreeNode root) {
            if (root == null) return;
            stack.push(root);
            while (root.left != null){
                stack.push(root.left);
                root = root.left;
            }
        }
        /** @return the next smallest number */
        public int next() {
            TreeNode node = this.stack.pop();
            if(node.right!= null){
                stack.push(node.right);
                TreeNode t = node.right;
                while(t.left != null){
                    stack.push(t.left);
                    t = t.left;
                }
            }
            return node.val;
        }
        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }

    //199 二叉树的右视图
    // 层次遍历 取每层最末尾的元素
    public List<Integer> rightSideView(TreeNode root) {
         if(root == null) return new ArrayList<>();
         Map<Integer,TreeNode> map = new HashMap<>();
         int depth = 0;
         Queue<TreeNode> nodesQueue = new LinkedList<>();
         nodesQueue.add(root);
         while(!nodesQueue.isEmpty()) {
             Queue<TreeNode> nextlevel = new LinkedList<>();
             while (!nodesQueue.isEmpty()) {
                 TreeNode node = nodesQueue.poll();
                 map.put(depth, node);
                 if (node.left != null) {
                     nextlevel.add(node.left);
                 }
                 if (node.right != null) {
                     nextlevel.add(node.right);
                 }
             }
             nodesQueue = nextlevel;
             depth++;
         }
         List<Integer> rightValue = new ArrayList<>();
         for(int i = 0 ; i< depth;i++){
             rightValue.add(map.get(i).val);
         }
         return rightValue;
    }


    public static void main(String[] args) {
        OneTo200 one = new OneTo200();
        String v1 = "0.1.2";
        String v2 = "1.1.3";
        one.compareVersion(v1,v2);

    }
}
