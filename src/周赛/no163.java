package 周赛;

import sequence.OneTo200;

import java.util.*;

public class no163 {

    //1260 二维网格迁移
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int n = grid.length ;
        int m = grid[0].length ;
        int[] list = new int[m * n];

        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < m ;j++){
                int index = i * m + j;
                list[(index+k)% (m*n)] = grid[i][j];
            }
        }
        for(int i = 0 ; i < n ; i++){
            List<Integer> row = new ArrayList<>();
            for(int j = 0 ; j < m ;j++){
                int index = i * m + j;
                row.add(list[index]) ;
            }
            result.add(row);
        }
        return result;
    }

    //1261 在受污染的二叉树中查找元素
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }
    class FindElements {
        private TreeNode copy;
        public FindElements(TreeNode root) {
            if(root!= null){
                root.val = 0;
                dfs(root);
                copy = root;
            }
        }
        private void dfs (TreeNode root){
            if(root == null) return ;
            if(root.left != null) {
                root.left.val = root.val * 2 +1;
                dfs(root.left);
            }
            if(root.right != null){
                root.right.val = root.val * 2 +2;
                dfs(root.right);
            }
        }
        //可以在重新构造的时候， 将元素的值加入set集合
        public boolean find(int target) {
              return dfs_find(copy,target);
        }
        private boolean dfs_find(TreeNode root ,int target){
            if(root == null) return false;
            if(root.val > target) return false;
            if(root.val == target) return true;
            boolean flag = false;
            if(root.left!= null){
                flag = dfs_find(root.left ,target);
            }
            if(flag) return true;
            if(root.right!= null){
                flag = dfs_find(root.right,target);
            }
            return flag;
        }
    }

    //1262 可被3整除的最大和
    public int maxSumDivThree(int[] nums) {
        List<Integer> one = new ArrayList<>();
        List<Integer> two = new ArrayList<>();
        int sum = 0;
        for(int i = 0 ; i < nums.length ;i++){
            sum+= nums[i];
            if (nums[i] % 3 == 1 ) one.add(nums[i]);
            else if( nums[i] % 3 == 2) two.add(nums[i]);
        }
        //tot 是 3 的倍数，那么我们不需要丢弃任何数；
        //tot 模 3 余 1，此时我们有两种选择：要么丢弃 b 中最小的 1 个数，要么丢弃 c 中最小的 2 个数；
        //tot 模 3 余 2，此时我们有两种选择：要么丢弃 b 中最小的 2 个数，要么丢弃 c 中最小的 1 个数。
        if(sum % 3 == 0) return sum;
        Collections.sort(one);
        Collections.sort(two);
        if(sum % 3 == 1){
            if(two.size() < 2 || one.get(0) < two.get(0)+ two.get(1)) sum = sum - one.get(0);
            else sum = sum - two.get(0)- two.get(1);
        }else{
            if(one.size() < 2 ||one.get(0) + one.get(1) > two.get(0)) sum = sum - two.get(0);
            else sum = sum - one.get(0) - one.get(1);
        }
        return sum;
    }

    //1263 推箱子

    public static void main(String[] args) {
        no163 so = new no163();
        int[] nums = {3,6,5,1,8};
        so.maxSumDivThree(nums);

    }
}
