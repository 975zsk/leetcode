package sequence;

import java.util.*;

public class ThreeTo400 {

    //有序矩阵的第k小的数 leetcode.378
    public static int kthSmallest(int[][] matrix, int k) {

        int row = matrix.length;
        int col = matrix[0].length;
        int low = matrix[0][0];
        int high =  matrix[row-1][col-1];
        while(low < high ){
            int mid = low + (high - low )/2 ;
            //遍历每行统计 <= mid的数有多少
            int count = 0;
            for(int i = 0 ; i < row ; i++){
                count+= search(matrix[i],mid);
            }
            if(k<= count){
                if(count == k-1 )  return mid;
                high = mid;
            }else{
                low = mid+1;
            }
        }
        return low;
    }
    //每一行 <= target 的数目，也就是 下边界   < target 是不行的！！ 会出现 结果数在原矩阵中不存在
    public static int search(int[] row ,int num){
        int low = 0;
        int high = row.length;   //不是 length-1  ！！   比如  1 5 9  num = 12

        while(low < high){
            int mid = low + (high - low )/2;
            if(row[mid] > num){
                high = mid;
            }else{
                low = mid+1;
            }
        }
        return low;
    }

    //373. 查找和最小的K对数字  340ms...
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<List<Integer>> queue = new PriorityQueue<>( new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                int sum1 = o1.get(0) + o1.get(1);
                int sum2 = o2.get(0) + o2.get(1);
                if(sum1 < sum2) return 1;
                else if (sum1 == sum2 ) return 0;
                else return -1;
            }
        });
        int index1 = 0 , index2 = 0;
        boolean is_num1 = true;
        while(index1 < nums1.length && index2 < nums2.length && index1 < k){
                if(is_num1){
                    for(int index = index2; index <= k+index2 && index < nums2.length;index++){
                        addPoint(Arrays.asList(nums1[index1],nums2[index]),queue,k);
                    }
                    index1++;
                    is_num1 = !is_num1;
                }else{
                    for(int index = index1; index <= k+index1 && index < nums1.length;index++){
                        addPoint(Arrays.asList(nums1[index],nums2[index2]),queue,k);
                    }
                    index2++;
                    is_num1 = !is_num1;
                }
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List [] arrays = new List [queue.size()];
        int i = 0;
        while(!queue.isEmpty()){
            arrays[i] = queue.poll();
            i++;
        }
        for(i = arrays.length-1 ; i >=0 ; i--){
            result.add(arrays[i]);
        }
        return result;
    }
    public void  addPoint(List<Integer> point ,PriorityQueue<List<Integer>>  queue ,int k){
        queue.add(point);
        if(queue.size() > k ){
            queue.poll();
        }
    }

    //373. 查找和最小的K对数字  答案
    //将问题转化为合并k个有序链表
    //其实是行列有序的
    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        int n1 = nums1.length, n2 = nums2.length;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> (nums1[o1[0]]+nums2[o1[1]]) - (nums1[o2[0]]+nums2[o2[1]])); //存索引 而非值
        List<List<Integer>> result = new ArrayList<>();

        if (n1 == 0 || n2 == 0 || k == 0) return result;
        for (int i = 0; i < n1; i++) pq.offer(new int[]{i, 0});  // index1= 0  每个链表的表头

        while(pq.size() > 0 && k > 0) {
            int[] pair = pq.poll();
            if (pair[1] + 1 < n2) pq.offer(new int[]{pair[0], pair[1]+1});
            List<Integer> temp = new ArrayList<>();
            temp.add(nums1[pair[0]]);
            temp.add(nums2[pair[1]]);
            result.add(temp);
            k--;
        }
        return result;
    }

    //309 最佳买卖股票时机含冷冻期
    int maxProfit_with_cool(int[] prices) {
        int n = prices.length ;
        int dp_i_0 = 0 , dp_i_1 = Integer.MIN_VALUE;
        int dp_pre_0 = 0;
        for(int i = 0 ; i< n ;i++){
            int temp = dp_i_0 ;
            dp_i_0 = Math.max(dp_i_0,dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1,dp_pre_0 - prices[i]);
            dp_pre_0 = temp;
        }
        return dp_i_0 ;
    }

    //310 最小高度树
    //拓扑排序的思想，每次删除度为1的点
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<>();
        int[] degree = new int[n];
        Map<Integer,List<Integer>> graph = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();  //叶子结点
        for (int [] edge : edges){
            degree[edge[0]]++;
            degree[edge[1]]++;
            if(!graph.containsKey(edge[0])) graph.put(edge[0],new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            if(!graph.containsKey(edge[1])) graph.put(edge[1],new ArrayList<>());
            graph.get(edge[1]).add(edge[0]);
        }
        for(int i = 0 ; i < n ;i++){
            if(degree[i] == 1) q.add(i);
        }
        int rest = n;
        if(rest == 1) ans.add(0);
        while(rest >2){
            int size = q.size();
            for(int j = 0 ; j< size ;j++){
                int cur = q.poll();
                degree[cur] = 0;
                for(int node : graph.get(cur)){
                    if(degree[node] !=0) degree[node] --;
                    if(degree[node] == 1) q.add(node);
                }
            }
            rest -=size;
        }
        while(!q.isEmpty()){
            ans.add(q.poll());
        }
        return ans;
    }

    //312 戳气球
    //动态规划 假设i是最后一个爆的
    public int maxCoins(int[] nums) {
        int n = nums.length +2;
        int[][] dp = new int[n][n];
        int[] new_nums = new int[n];

        for(int i = 0 ; i< nums.length ;i++){
            new_nums[i+1] = nums[i];
        }
        new_nums[0] = new_nums[n-1] = 1;
        dp[0][0] = dp[n-1][n-1] = 1;
        //len = 1
        for(int i = 1; i < n-1; i++){
            dp[i][i] = new_nums[i-1] * new_nums[i] * new_nums[i+1];
        }
        //len = 2
        for(int len = 2 ; len <= n-2 ;len++){
            for(int i = 1 ; i< n-len ;i++){
                int j = i+len-1;
                for(int k = i ; k<= j ;k++){
                    int left  = ((k == i) ? 0 : dp[i][k-1]);
                    int right = ((k == j) ? 0 : dp[k+1][j]);
                    dp[i][j] = Math.max(dp[i][j], new_nums[i-1] * new_nums[k] * new_nums[j+1] + left + right);
                }
            }
        }
        return dp[1][n-2];
    }


    public static void main(String[] args) {

        ThreeTo400 so = new ThreeTo400();
        int[] nums = {3, 1, 5, 8};
        so.maxCoins(nums);
    }
}
