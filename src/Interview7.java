import jdk.nashorn.api.tree.Tree;

import javax.swing.tree.TreeCellRenderer;
import java.util.*;

// Definition for a binary tree node.
class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

//二叉搜索树中第K小的元素
class Solution7_1 {

    //2ms //左 中 右遍历
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<Integer>();
        visit(root,list,k);
        return list.get(list.size()-1);
    }
    public void visit(TreeNode node,List<Integer> list,int k){
        if(list.size()>= k) return ;
        if(node.left!= null){
            visit(node.left,list,k);
        }
        list.add(node.val);
        if(list.size()>= k) return ;
        if(node.right!=null){
            visit(node.right,list,k);
        }
    }

    //如果会不断的插入结点？ ，每次都遍历不好，二分
    public int kthSmallest2(TreeNode root,int k){
        int lct = countNodes(root.left);

        if(lct >=k ) return kthSmallest2(root.left,k);
        else if( k > lct+1){
            return kthSmallest2(root.right,k-lct-1);
        }
        return root.val;
    }
    public int countNodes(TreeNode root){
        if(root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}

// 二叉树的最近公共祖先
// 背下来吧！
 class Solution7_2 {
    //如果root为null，肯定返回null；
    //如果root的值等于其中的某一个，那么就返回这个值；
    //如果root的值和两个都不相等，递归的判断左右孩子；
    //如果返回左右孩子的两个值都不为null，说明root节点的左右子树中各有一个，返回root即可！若其中一个为null，返回另一个即可（说明在一边的子树中，首先遍历到的肯定是所要求的节点了）！
     public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

         if(root == null || root.val== p.val || root.val == q.val) return root;
          TreeNode left = lowestCommonAncestor(root.left,p,q);
          TreeNode right = lowestCommonAncestor(root.right,p,q);
          if(left!= null && right !=null) return root;
          if(left == null) return right;
          return left;
     }
 }

 //二叉树的序列化和反序列化   层次遍历 超时了,
// 层次遍历,思路：队列
 class Code {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        String res = "";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode temp = queue.poll();
            if(temp!= null) {
                res += Integer.toString(temp.val) + ",";
                queue.add(temp.left);
                queue.add(temp.right);
            }else{
                res +="#,";
            }
        }
        while(res.endsWith("#,")) res = res.substring(0,res.length()-2);
        return res.substring(0,res.length()-1);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[]  res = data.split("#");
        int length = res.length;
        for(int i = res.length-1 ; i>=0 && res[i].equals("null") ;i--){
            length --;
        }
        if(length <= 0) return null;
        List<TreeNode> list = new ArrayList<>();

        int p = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(res[0]));
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode temp = queue.poll();
                if(p< length){   //left
                    if(res[p].equals("null")){
                        temp.left = null;
                    }else{
                        TreeNode node = new TreeNode(Integer.parseInt(res[p]));
                        queue.add(node);
                        temp.left = node;
                    }
                }
                if(p +1 <length){   //right
                    if(res[p+1].equals("null"))  temp.right =null;
                    else{
                        TreeNode node = new TreeNode(Integer.parseInt(res[p+1]));
                        queue.add(node);
                        temp.right= node;
                    }
                }
                p += 2;
        }
        return root;
    }

    //依然是层次遍历，答案，也超时。
    public String  serialize2(TreeNode root){
        if(root == null) return null;

        String res = "";
        Queue<TreeNode> queue = new LinkedList<>();
        int count = (1 << treeDepth(root)) - 1;//计数，拿到此树的深度后计算对应完全二叉树节点数
        queue.add(root);
        count--;
        TreeNode tmp = null;
        while(!queue.isEmpty() && count >=0){
            tmp = queue.poll();
            if(tmp != null){
                res+= tmp.val+",";
                queue.add(tmp.left);
                queue.add(tmp.right);
            }else{
                res+= "#,";
                queue.add(null);
                queue.add(null);
            }
            count--;
        }
        return res.substring(0,res.length()-1);
    }

     public  int treeDepth(TreeNode root){
         int depth = 0;
         if(root == null){
             return depth;
         }else{
             int lDepth = treeDepth(root.left) + 1;
             int rDepth = treeDepth(root.right) + 1;
             return lDepth > rDepth ? lDepth : rDepth;
         }
     }

    public TreeNode deserialize2(String str) {
         if(str == null || str.length() == 0){
             return null;
         }
       return Deserialize(str.split(","), 0);
     }

     //一棵满二叉树，left = 2*index+1 ,right = 2*index+2
     public TreeNode Deserialize(String[] strings,int index){

        TreeNode node = null;
        if(index <strings.length){
            if(!strings[index].equals("#")){
                node = new TreeNode(Integer.parseInt(strings[index]));
                node.left =  Deserialize(strings,index*2+1);
                node.right = Deserialize(strings,index*2+2);
            }
        }
        return node;
     }
}

//天际线问题
//好难
class Solution7_4 {
    public List<List<Integer>> getSkyline(int[][] buildings) {

        List<int[]> height = new ArrayList<>();
        for(int[] building : buildings){
            height.add(new int[]{building[0],-building[2]});  //将每个矩形转换为点， 左边的点高度存为-
            height.add(new int[]{building[1],building[2]}); //右边的点高度为正
        }
        Collections.sort(height, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if(a[0]!=b[0]){
                    return a[0] - b[0];
                }else{
                    return a[1] - b[1];
                }
            }
        });
        //大顶堆
        Queue<Integer> max= new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> point = null;
        max.add(0); //地平线
        int pre_max = 0;
        for(int[] h : height){
            if(h[1] <0) {   //左边的点
                max.add(-h[1]);
            }else{
                max.remove(h[1]);
            }
            int cur_max= max.peek();
            if(pre_max != cur_max){  //拐点
                point = new ArrayList<>();
                point.add(h[0]);
                point.add(cur_max);
                result.add(point);
                pre_max = cur_max;
            }
        }
        return result;
    }
}

public class Interview7 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);

        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);

        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
//        Serialize code  = new Serialize();
//         String  str = code.serialize(node1);
//        System.out.print(str);
//        TreeNode  newroot = code.deserialize(str);

        Code code2 = new Code();
        //TreeNode newnode = null;
        String  str2 = code2.serialize2(node1);
        System.out.print(str2);
        TreeNode  newroot2 = code2.deserialize2(str2);
    }

}
