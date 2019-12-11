package sequence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiveTo600 {

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
    //543 二叉树的直径
    int max = 1;  //最多路径的结点数量
    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return max - 1;
    }
    private int depth(TreeNode node){
        if(node == null) return 0;
        int L = depth(node.left);
        int R = depth(node.right);
        max = Math.max(max, L+R+1); //更新max
        return Math.max(L,R)+1;
    }

    //树的直径 两次dfs
    Map<Integer,List<Integer>> map = new HashMap<>();
    int max_len = 0;
    int point ;
    boolean [] visited ;

    public void dfs(int t , int len){
        if(len > max_len){
            max_len = len;
            point = t;
        }
        int size = map.get(t).size();
        for(int i = 0 ; i< size ;i++){
            int node = map.get(t).get(i);
            if(!visited[node] ){
                visited[node] = true;
                dfs(node,len + 1);
            }
        }
    }
    public int diameterOfTree(Map<Integer,List<Integer>> map){
        int size = map.size();
        visited = new boolean[size];
        visited[0] = true;
        dfs(0,0);

        //找到point后，从point开始dfs
        visited = new boolean[size];
        visited[point] = true;
        dfs(point,0);
        return max_len;
    }




}
