import java.text.DecimalFormat;
import java.util.*;

public class bi_shi {
        public static void main(String[] args) {
            String user="bgbbbgbggbgbg";
            System.out.println(getIndex2(user));
            System.out.println(getMax(user,3));
        }

        public static int getMax(String str,int k){
            str = str+str;  //处理环
            int first_girl = -1;
            int count = 0;
            int max_value = 0;
            for(int i = 0; i< str.length();i++){
                if(str.charAt(i) == 'g') {
                    if(first_girl == -1 ){
                        first_girl = i;
                    }
                    count++;
                    if (count < k) continue;
                    else if (count == k) {
                        int boy = 0;
                        for(int j = first_girl ; j  <= i ;j++){
                            if(str.charAt(j) == 'b') {
                                boy++;
                            }
                        }
                        max_value = Math.max(max_value,boy);
                        //first_girl qianyi
                        first_girl += 1;
                        while (str.charAt(first_girl) == 'b') {
                            first_girl++;

                        }
                        count--;
                    }
                }
            }
            return max_value;
        }

        public static int getIndex(String str){
            int [] nums = new int[str.length()];
            int last_index = -1;
            int sum = 0;
            for(int i = 0 ; i < str.length() ;i++){
                if(str.charAt(i) == 'b'){
                    nums[i] = sum;
                    if(last_index!= -1) {
                        nums[last_index]+= sum;
                    }
                    sum = 0;
                    last_index = i;
                }else{
                    sum++;
                }
            }
            int first_left = 0;
            for(int i = 0 ; i < nums.length;i++){
                if(nums[i]!= 0 ) {
                    nums[i]+= sum;
                    nums[last_index] = nums[last_index] + first_left + sum;
                    break;
                }
                first_left++;
            }
            //find
            int max = 0;
            int max_index = -1;
            for(int i = 0 ; i< nums.length ;i++){
                if(nums[i]!= 0 && nums[i] > max){
                        max = nums[i];
                        max_index = i;
                }
            }
            return max_index;
        }

        //用链表存索引,这个好 记一下！
        public static int getMax2(String user,int k){
            user=user+user;
            Deque<Integer> gril=new LinkedList<>();
            Deque<Integer> boy=new LinkedList<>();
            int ans=0;
            for(int i=0;i<user.length();i++){

                if('b'==user.charAt(i))
                    boy.addLast(i);
                else if(gril.isEmpty() || gril.size()<k)
                    gril.addLast(i);
                else{
                    //ans=Math.max(ans,boy.size());
                    while (boy.getFirst()<gril.getFirst())
                        boy.pollFirst();
                    gril.pollFirst();
                    gril.addLast(i);
                }
                ans=Math.max(ans,boy.size());
            }

            return ans;
        }

        public static String getIndex2(String users){
        List<Integer> list=new LinkedList<>();
        for(int i=0;i<users.length();i++)
            if('b'==users.charAt(i)) list.add(i);

        int happy=Integer.MIN_VALUE,happ_index=list.get(0);
        for(int i=0;i<list.size();i++){
            int pre=list.get(i)- ( i>0 ?list.get(i-1):-1 )  -1;
            if(i == 0 ) pre+= users.length() - list.get(list.size()-1) -1;
            int next=(i<list.size()-1?list.get(i+1):users.length())    -list.get(i) -1;
            if(i == list.size()-1 ) next += list.get(0);
            if((pre+next)>happy){
                happy=pre+next;
                happ_index=list.get(i);
            }
        }
        return String.valueOf(happ_index);
    }
}

class Node {
    public int key;
    public int val;
    public Node next;
    public Node prev;
    public Node(int k, int v) {
        this.key = k;
        this.val = v;
    }
}
class MyLinkedList{
    private Node head;
    private Node tail;
    private int size; // 链表元素数

    public MyLinkedList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
    public int getSize() { return size; }

    //将结点添加至链表的头
    public void addFirst(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        size++;
    }
    // 删除链表中的指定node节点
    public void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }
    // 删除链表中最后一个节点
    public Node removeLast() {
        if (tail.prev == head)
            return null;
        Node last = tail.prev;
        remove(last);
        return last;
    }
}

class Solution {
    public static void main(String[] args) {
       Solution cache = new Solution (2);
       cache.put(1,1);
       cache.put(2,2);
       System.out.println(cache.get(1));
        cache.put(3,3);
        System.out.println(cache.get(2));
        cache.put(4,4);
        System.out.println( cache.get(1));
        System.out.println( cache.get(3));
        System.out.println(cache.get(4));
    }

    private HashMap<Integer, Node> map;  //存储已有的key值
    private MyLinkedList cache;          //存储缓存表
    private int capacity;              // 最大容量

    public Solution(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.cache = new MyLinkedList();
    }
    public int get(int key) {
        if (!this.map.containsKey(key))
            return -1;
        int val = this.map.get(key).val;
        this.put(key, val);
        return val;
    }
    public void put(int key, int val) {
        Node node = new Node(key, val);
        if (this.map.containsKey(key)) {
            cache.remove(this.map.get(key));
            cache.addFirst(node);
            //更新map对应的索引
            this.map.put(key, node);
        } else {
            if (this.capacity == this.cache.getSize()) {  //满了，要淘汰
                Node last = this.cache.removeLast();
                this.map.remove(last.key);
            }
            this.cache.addFirst(node);
            this.map.put(key, node);
        }
    }
}


class Test {
    public static void main(String[] args) {
//       Scanner in = new Scanner(System.in);
//       int n = in.nextInt();
//       int [] nums = new int[n];
//       int i = 0;
//       while(in.hasNextInt()){
//           nums[i] = in.nextInt();
//           i++;
//       }
        int[][] matrix = {{1,5,9},
                {10,11,13},
                {12,13,15}};
        System.out.println(kthSmallest(matrix,8));
    }

    //法1
//    public static double solution2(int n ,int[] xi){
//        Arrays.sort(xi);
//        int max = xi[n-1];
//        int min = xi[0];
//        int sum = 0;
//
//        for(int i = 1 ; i <= max ;i++){
//            int temp_sum = i;
//            if(i < min){
//                temp_sum = (int)Math.pow(temp_sum,n);
//                sum+= temp_sum;
//                continue;
//            }
//            int less = lessThanMe(xi,i);
//            for(int j = 0 ; j < less;i++){
//                temp_sum = temp_sum * (i-1);
//            }
//            for(int j = 0 ; j < (n-1-less);j++){
//                temp_sum = temp_sum * i;
//            }
//            sum+= temp_sum;
//        }
//        int all = all(n,xi);
//        return sum /(double)all;
//    }
//    public static int lessThanMe(int[] xi, int m){
//        for(int i = 0 ; i < xi.length ; i++){
//            if(xi[i] >= m) return i;
//        }
//        return xi.length-1;
//    }
//
//    public static int all(int n ,int[]xi){
//        int all = 1;
//        for(int i = 0 ; i< xi.length ;i++){
//            all = all * xi[i];
//        }
//        return all;
//    }

    //法2
    public static double solution(int n ,int[] xi){
        List<Integer> result = new ArrayList<>();
        dp(n,xi,0,0,result);
        double sum = 0;
        for(Integer num : result){
            sum+= (double)num;
        }
        return sum /(double)result.size();
    }
    public static void dp(int n ,int [] xi ,int level,int max, List<Integer> result){
        if( level == n){
            result.add(max);
            return;
        }
        for(int i = 1 ; i <= xi[level] ;i++){
            if(level == 0 ) max = 0;
            dp(n,xi,level+1,Math.max(max,i),result);
        }
    }


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

    //面试题：忘了啥了，挺简单的
    public void solution(int[] nums ,int m){
        if(nums.length == 0) return ;
        int[] temp = new int[m];
        for(int i = 0 ; i < nums.length ;i++){
            if(i < m){
                temp[i] = nums[i];
            }else{
                nums[i-m] = nums[i];
            }
        }
        for(int i = 0 ; i< m ;i++){
            nums[nums.length-m +i] = temp[i];
        }
    }

    //面试题： 给定一串数字，去掉重复数字，保留最小的结果，不能打乱
    public String solution(int[] nums,boolean[] visited){
        List<List<Integer>> map  = new ArrayList<>();
        for(int i = 0 ; i <10 ;i++){
            List<Integer> row = new ArrayList<>();
            map.add(row);
        }
        for(int i = 0 ; i< nums.length ;i++){
            int num = nums[i];
            map.get(num).add(i);  //存索引
        }
        //从大往小看 9-1
        int[] temp = new int[nums.length];
        int index = nums.length;
        for(int i = 9 ; i>=0 ;i--){
            if(!map.get(i).isEmpty() && !visited[i]) {
                int num_length = map.get(i).size();
                boolean flag = false;
                for (int j = num_length - 1; j >= 0; j--) {  //遍历该num的索引
                    if (map.get(i).get(j) < index) {
                        temp[map.get(i).get(j)] = i;
                        flag = true;
                        index = map.get(i).get(j);
                        break;
                    }
                }
                //该数所有的索引都比前面大，加到last
                if (!flag) {
                    temp[map.get(i).get(num_length-1)] = i;
                }
            }
        }
        String result = "";
        // boolean isEmpty_zero = map.get(0).isEmpty();
        int i = 0;
        while(temp[i] == 0) i++;
        for(int j = i ; j <temp.length; j++){
            if(temp[j]!= 0){
                result += temp[j];
                visited[temp[j]] = true;
            }
        }
        return result;
    }
    public String cut( int[] nums){
        int index = 0;
        for(int i = 0 ; i < nums.length ;i++){
            if(nums[i] == 0){
                index = i ;
                break;
            }
        }
        boolean [] visited = new boolean[10];
        int[] left = new int[index];
        int[] right = new int[nums.length - index -1 ];
        for(int i = 0 ; i< index ; i++){
            left[i] = nums[i];
        }
        for(int i = index+1 ; i< nums.length ;i++){
            right[i-index-1] = nums[i];
        }
        String left_str = solution(left,visited);
        String right_str =  solution(right,visited);
        return left_str + "0" + right_str;
    }

    public int solution(int[] nums){
        int count = nums[0];
        int last_index = 0;
        int max = 0;
        for(int i = 1 ; i < nums.length ;i++){
            if(nums[i] >= count){
                count += nums[i];
            }else{
                max = Math.max(max, i - last_index);
                last_index = i;
                count = nums[i];
            }
        }
        if(last_index != nums.length-1){
            max = Math.max(max, nums.length- last_index);
        }
        return max;
    }

    public int solution2(int x){
        Stack<Integer> num = new Stack<>();
        while(x > 0){
            x = x - 9;
            if(x <= 0){
                num.push(x + 9);
            }else{
                num.push(9);
            }
        }
        String str = "";
        while(!num.isEmpty()){
            str+= num.pop();
        }
        return Integer.parseInt(str);
    }

    public int solution3(int[] nums){
        int count = 0;
        for(int i = 0 ;i< nums.length;i++){
            for(int j = i+1 ; j< nums.length ;j++){
                if(nums[j] >nums[i]){
                    count+= nums[j] -nums[i];
                }
            }
        }
        return count;
    }

    public int solution4(int A ,int B ,int p ,int q){

        return bs(A,B,p,q,0,Integer.MAX_VALUE);
    }
    public int bs ( int A ,int B,int p ,int q ,int level,int now_min){
        if(A>= B){
            return level;
        }
        if(level >= now_min) return now_min;

        //A + p
        int num1 = bs(A+p ,B,p,q,level+1,now_min);
        now_min = Math.min(num1,now_min);
        //p*q
        int  num2 = bs(A,B,p*q,q ,level+1,now_min);
        now_min = Math.min(now_min,num2);
        return now_min;
    }

    //hulu的笔试题
    //lintcode 最大平均值子数组
    //解法1：暴力
    public double maxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        long[] sums = new long[n + 1];
        sums[0] = 0L;
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        double maxAvg = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < n - k + 1; i++) {
            for (int j = i + k; j < n + 1; j++) {
                long sum = sums[j] - sums[i];
                double avg = ((double) sum) / (j - i);
                maxAvg = Math.max(maxAvg, avg);
            }
        }
        return maxAvg;
    }
    //解法2： 二分
    public double maxAverage2(int[] nums, int k) {
        double low = (double) nums[0];
        double high = (double) nums[0];
        for (int i = 0; i < nums.length; i++) {
            low = Math.min(low, nums[i]);
            high = Math.max(high, nums[i]);
        }
        while (high - low >= 1e-6) {
            double mid = low + (high - low) / 2;
            if (checkValid(nums, k, mid)) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return high;
    }
    private boolean checkValid(int[] nums, int k, double mid) {
        double minPrev = 0;
        double[] sums = new double[nums.length + 1];
        sums[0] = 0.0;
        for (int i = 1; i < nums.length; i++) {
            sums[i ] = sums[i-1] + nums[i-1] - mid;
            if (i >= k && sums[i] - minPrev > 0) {  //!!
                return true;
            }
            if (i >= k) {
                minPrev = Math.min(minPrev, sums[i + 1 - k]);  //!!
            }
        }
        return false;
    }
}