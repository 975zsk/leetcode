package sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class SevenTo800 {
    //747.至少是其他数字两倍的最大数
    public int dominantIndex(int[] nums) {
        int max = Integer.MIN_VALUE;
        int second_max = Integer.MIN_VALUE;
        int max_index = -1;
        for (int i = 0 ; i< nums.length ;i++){
            if(nums[i] > max){
                second_max = max;
                max = nums[i];
                max_index = i;
            }else {
                if (nums[i] > second_max) second_max = nums[i];
            }
        }
        return max >= second_max * 2 ? max_index : -1;
    }

    //756 堆金字塔
    //回溯
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        Map<String,List<String>> map = new HashMap<>();
        for(String str : allowed){
            String key = str.substring(0,2);
            String value = str.substring(2);
            if(map.containsKey(key)){
                map.get(key).add(value);
            }else{
                List<String> temp = new ArrayList<>();
                temp.add(value);
                map.put(key,temp);
            }
        }
        return dfs(bottom,"",map);
    }
    public boolean dfs(String bottom ,String nextbottom, Map<String,List<String>> map) {
        if (bottom.length() == 2 && nextbottom.length() == 1) return true;
        if (nextbottom.length() == bottom.length() - 1) {  //这一层可以搭，寻找下一层
            return dfs(nextbottom, "", map);
        }
        //继续搭这一层
        int pos = nextbottom.length();

        String key = bottom.substring(pos, pos + 1);
        if (map.containsKey(key)) {
            List<String> nextone = map.get(key);  //接下来的几种搭法
            for (String next : nextone) {
                if (dfs(bottom, nextbottom + next, map)) return true;
            }
        }
        return false;
    }


    //725 分割链表
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode [] list_head = new ListNode[k];
        if(root == null) return list_head;
        //count
        ListNode temp = root;
        int n = 1;
        while(temp.next!= null){
            temp = temp.next;
            n++;
        }
        int bucket = n / k ;
        int left = n % k ;
        if(bucket == 0){
            bucket = 1;
            left = 0;
        }
        temp = root;
        ListNode head = root;
        int i = 1 ;
        int bucket_i = 0;
        ListNode p = head;
        while(temp!= null){
            if(i == 1) {
                if(bucket!= 1) {
                    list_head[bucket_i] = temp;
                    bucket_i++;
                    temp = temp.next;
                    i++;
                }
                else if(bucket == 1 ){  //比较特殊
                    list_head[bucket_i] = temp;
                    bucket_i++;
                    if (left > 0) {
                        temp = temp.next;
                        left--;
                    }
                    p = temp;
                    temp = temp.next;
                    p.next = null;
                }
            }
            else if(i == bucket ) {
                if (left > 0) {
                    temp = temp.next;
                    left--;
                }
                p = temp;
                temp = temp.next;
                p.next = null;
                i = 1;

            }else {
                temp = temp.next;
                i++;
            }
        }
        return list_head;
    }

    //764 最大加号标志

    public static void main(String[] args) {
        SevenTo800 one = new SevenTo800();
//        int[] nums = {6,2,0,8,7,0,6,6,7,8,1,2,4,5,6};
//        String result = one.cut(nums);
//        System.out.print(result);

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        ListNode node7 = new ListNode(7);
        ListNode node8= new ListNode(8);
        ListNode node9 = new ListNode(9);
        ListNode node10 = new ListNode(10);
        ListNode node111 = new ListNode(11);
        node1.next = node2;
        node2.next = node3;
        //node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;
        node8.next = node9;
        node9.next = node10;

        one.splitListToParts(node1,5);
       // System.out.print(result);
    }

}
