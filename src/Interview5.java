import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Definition for a Node.
class Node2 {
    public int val;
    public Node next;
    public Node random;

    public Node2() {}

    public Node2(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
//拷贝带有随机指针的链表
//class Solution5_1 {
//    public Node copyRandomList(Node head) {
//        if(head == null) return null;
//        Node root = new Node(head.val,null,null);
//        Node node = root;  //复制的数组
//        Node temp = head.next;  //原数组
//        Map<Node,Node> map = new HashMap<Node,Node>();
//        map.put(head,node);
//        while(temp!= null){
//            Node t = new Node(temp.val,null,null);
//            node.next = t;
//            map.put(temp,t);  //将复制数组和原数组一一对应
//            node= node.next;
//            temp = temp.next;
//        }
//        //回头
//        node = root;
//        temp = head;
//        while(temp!= null){
//            node.random = map.get(temp).random;
//            node = node.next;
//            temp = temp.next;
//        }
//        return root;
//    }
//}

//Definition for singly-linked list.
 class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
 }
//判断是否是环形链表
class Solution5_2 {
    //需要一个set结构 13ms
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<ListNode>();
        if(head == null) return false;
        ListNode temp = head.next;
        set.add(head);
        while(temp!=null){
               if(set.contains(temp)) return true;
               set.add(temp);
               temp = temp.next;
        }
        return false;
    }

    //不需要set，把每个节点的next都指向head
    public boolean hasCycle2(ListNode head) {
        if(head == null){
            return false;
        }
        ListNode p = head.next;
        while(p != null){
            if(p == head){
                return true;
            }
            ListNode pre = p;
            p = p.next;
            pre.next = head;
        }
        return false;
    }
}

//排序链表 nlogn 归并排序
class Solution5_3 {
    public ListNode sortList(ListNode head) {

        if(head == null ||  head.next == null) return head;
        ListNode mid = getMid(head);
        ListNode right = mid.next;
        mid.next = null; //精髓的一句
        return mergeSort(sortList(head),sortList(right));
    }

    //利用快慢指针 得到中间的元素，偶数取前者
    private ListNode getMid(ListNode head){
        if(head == null || head.next ==null) return head;
        ListNode slow = head;
        ListNode quick = head;
        while(quick.next!= null && quick.next.next != null){
            slow = slow.next;
            quick = quick.next.next;
        }
        return slow;
    }

    //归并两个有序链表
    private ListNode mergeSort(ListNode left, ListNode right){
        ListNode p1 = left ,p2 = right;
        ListNode head;
        if(left.val < right.val){
            head = left;
            left= left.next;
        }else{
            head = right;
            right = right.next;
        }
        ListNode temp = head;
        while(left!= null && right!= null){
            if(left.val<= right.val){
                temp.next = left;
                left = left.next;
            }else{
                temp.next = right;
                right = right.next;
            }
            temp= temp.next;
        }
        if(left!= null){
            temp.next = left;
        }
        if(right!=null){
            temp.next = right;
        }
        return head;
    }

}

//相交链表 o(n) 0(1)\
//！非常好的题
//https://blog.csdn.net/cjh_android/article/details/83994215
class Solution5_4 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        ListNode  ha = headA,hb = headB;
        while(ha != hb){
            ha = (ha == null)? headB : ha.next;
            hb = (hb == null)? headA : hb.next;
        }
        return ha;
    }
}

//反转链表
class Solution5_5 {
    //递归 26ms 慢
    public ListNode reverseList(ListNode head) {

        if(head == null || head.next==null) return head;
        ListNode node = head;
        ListNode tobereNode = head.next;
        node.next = null;

        ListNode reverseNode = reverseList(tobereNode);
        ListNode temp = reverseNode;
        while(temp.next!= null){
            temp = temp.next;
        }
        temp.next = node;
        return reverseNode;
    }
    //答案，循环一圈 o(n)
    public ListNode reverseList2(ListNode head) {
        ListNode pre = null;
        while(head != null){
            //备份下一个节点
            ListNode next = head.next;
            //head的下一个节点指向pre
            head.next = pre;
            //移动pre和head指针
            pre = head;
            head = next;
        }
        return pre;
    }
}

//回文链表
// 反转 后半 进行比较
class Solution5_6 {
    public boolean isPalindrome(ListNode head) {
        if(head == null ) return true;
        else if(head.next == null) return true;
        ListNode mid = getMid(head);
        ListNode half = reverseLink(mid.next);
        mid.next = null;
        while(head!= null && half!=null){
            if(head.val!= half.val) return false;
            else{
                head = head.next;
                half = half.next;
            }
        }
        return true;
    }

    private ListNode reverseLink(ListNode head){
        ListNode pre = null;
        while(head != null){
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
    private ListNode getMid(ListNode head){
        if(head == null || head.next ==null) return head;
        ListNode fast = head, slow = head;
        while(fast.next != null && fast.next.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}

//删除链表中的结点
//只给要删除的结点 ，不给整个链表的头结点
//o(1) 0(1)
//！好题
class Solution5_7 {
    public void deleteNode(ListNode node) {
         //4519 删 5  4119 419
        node.val = node.next.val;
        node.next = node.next.next;
    }
}

//奇偶链表
class Solution5_8 {
    public ListNode oddEvenList(ListNode head) {

        if(head == null || head.next == null || head.next.next == null) return head;
        ListNode odd = head;  //qi
        ListNode even = head.next; //ou
        ListNode evenhead = even;
        while(odd.next!=null  &&odd.next.next!= null){
            odd.next = even.next;
            odd = odd.next ;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenhead;
        return head;
    }
}
public class Interview5 {
}
