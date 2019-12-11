import java.util.*;

//最小栈
class MinStack {

    /** initialize your data structure here. */
    private Stack<Integer> min = new Stack<>();  //出现过的最小值
    private Stack<Integer> s = new Stack<>();
    public MinStack() {
    }

    public void push(int x) {
        s.push(x);
        if(min.empty() || min.peek() >= x) min.push(x);
    }

    public void pop() {
        int x = s.pop();
        if(x == min.peek()) min.pop();
    }

    public int top() {
        return s.peek();
    }

    public int getMin() {
        return min.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */

//数组中第k大元素
class Solution4_2 {
    public int findKthLargest(int[] nums, int k) {
        //快排 + 分治
       return  quick_sort(nums,k,0,nums.length-1);
    }

    public int quick_sort(int[] nums,int k ,int index_l ,int index_r){

        int left = index_l;
        int right = index_r;
        int len = right- left +1;
        int pirot = nums[right];
        boolean is_left= true;
        while(left <right){
            if(is_left) {
                while (nums[left] < pirot) left++;
                if(left>=right) break;
                //left 和right 互换
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                right --;
                is_left = false;
            }else{
                while(nums[right] > pirot) right--;
                if(left>=right) break;
                int temp = nums[right];
                nums[right] = nums[left];
                nums[left] = temp;
                left++;
                is_left = true;
            }
        }
        int rela_left = left - index_l;
        if(rela_left == len -k ) return nums[left];
        else if(rela_left > len -k ) return quick_sort(nums,k-(len-rela_left),index_l,left-1);
        else  return quick_sort(nums,k,left+1,index_r);
    }
}

//数据流中的中位数
// 用两个推保存数据，保持两个堆的数据保持平衡（元素个数相差不超过1）
// 大顶堆存放的数据要比小顶堆的数据小，
// 当两个推中元素为偶数个，将新加入元素加入到大顶堆， 如果要加入的数据，比小顶堆的最小元素大，先将该元素插入小顶堆，然后将小顶堆的最小元素插入到大顶堆。
// 当两个推中元素为奇数个，将新加入元素加入到小顶堆，如果要加入的数据，比大顶堆的最大元素小，先将该元素插入大顶堆，然后将大顶堆的最大元素插入到小顶堆。
class MedianFinder {

    /** initialize your data structure here. */
    private Queue<Integer> left = new PriorityQueue<>(descComparator);   //大的在队头
    private Queue<Integer> right = new PriorityQueue<>();   //小的在队头
    private int size = 0;
    public MedianFinder() {

        this.size = 0;
    }

    public void addNum(int num) {

        if(size == 0) left.add(num);
        else if(size%2 == 0 ){  //偶数
            if(num > right.peek()){
                int temp = right.poll();
                right.add(num);
                left.add(temp);
            }else{
                left.add(num);
            }
        }else{     //奇数
            if(num < left.peek()){
                int temp = left.poll();
                left.add(num);
                right.add(temp);
            }else{
                right.add(num);
            }
        }
        size++;
    }

    public double findMedian() {

        double median = 0.0;
        if(size %2 == 0) median = (left.peek()+right.peek())/2.0;
        else median = left.peek();
        return median;
    }

     static Comparator<Integer> descComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if(o1 > o2) return -1;
            else if (o1 <o2) return 1;
            else return 0;
        }
    };
}

//有序矩阵中第k小的元素
class Solution4_4 {
    public int kthSmallest(int[][] matrix, int k) {

        int row = matrix.length;
        if(row == 0) return 0;
        int col = matrix[0].length;
        if(col == 0 ) return 0;

        Queue<Integer> max = new PriorityQueue<>(descComparator);
        int size = k;
        int i = 0 ;
        int j = matrix[0].length-1;
        while(i>=0 && i <row && j>= 0 && j<col){

            int m = 0;
            for(; m <k && m<=j ;m++){
                if(max.size() < size) {
                    max.add(matrix[i][m]);
                }else{
                    if(max.peek() > matrix[i][m]) {
                        max.poll();
                        max.add(matrix[i][m]);
                    }
                }
            }
            j = m-1;
            i++;
           // k = k - m;
        }
        return max.peek();
    }
    static Comparator<Integer> descComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            if(o1 > o2) return -1;
            else if (o1 <o2) return 1;
            else return 0;
        }
    };
    //答案方法
    //使用二分查找，主要性能的提升市优先级队列和二分的性能
    public int kthSmallest2(int[][] matrix, int k) {
        int row = matrix.length, col = matrix[0].length;
        int low = matrix[0][0], high = matrix[row - 1][col - 1];
        while (low < high) {
            int mid = (low + high) / 2;
            int count = getCount(matrix, row, col, mid);
            //System.out.println("mid=" + mid + "count=" + count);
            if (count < k)
                low = mid + 1;
            else
                high = mid;
        }
        return low;
    }
    private int getCount(int[][] num, int row, int col, int target) {
        int i = row - 1, j = 0, res = 0;
        while (i >= 0 && j < col) {
            //System.out.println("i=" + i + "j=" + j + "res=" + res + "target="+target);
            if (num[i][j] <= target) {
                res += i + 1;
                j++;
            } else {
                i--;
            }
        }
        return res;
    }
}

//前k个高频元素
//最小堆
class Solution4_5 {
    public List<Integer> topKFrequent(int[] nums, int k) {

        Map<Integer,Integer> count =  new HashMap<>();

        for(int i = 0 ; i<nums.length;i++){
            if(!count.containsKey(nums[i])) count.put(nums[i],1);
            else count.put(nums[i],count.get(nums[i])+1);
        }

        Queue<Integer> q = new PriorityQueue<>(new Comparator<Integer>() { //desc
            @Override
            public int compare(Integer a, Integer b) {
                if(count.get(a) > count.get(b)) return 1;
                else return -1;
            }
        });

        for(Integer key : count.keySet()){
            if(q.size() <k) q.add(key);
            else {
                if(count.get(q.peek())< count.get(k) ){
                    q.remove();
                    q.add(k);
                }
            }
        }
        List<Integer> ret = new ArrayList<>();
        for (Integer x : q) {
            ret.add(x);
        }
        return ret;
    }
}

//返回滑动窗口的最大值 线性
//双端队列保存索引
class Solution4_6 {
    public int[] maxSlidingWindow(int[] nums, int k) {

        Deque<Integer> q = new LinkedList<>();   //该队列用来保存index
        int [] result = new int[nums.length -k+1];
        int index = 0;
        if(nums.length == 0 ) return nums;
        for(int i= 0 ;i<nums.length ;i++){
            if(q.isEmpty()) q.add(i);
            else {
                if(i - q.getFirst()  >=k){
                    q.removeFirst();
                }
                while(!q.isEmpty() && nums[q.getFirst()] < nums[i]){ //从队头
                    q.removeFirst();
                }
                while(!q.isEmpty() && nums[q.getLast()] < nums[i]){
                    q.removeLast();
                }
                q.add(i);
            }
            if(i>= k-1) {
                result[index] = nums[q.getFirst()];
                index++;
            }
        }
        return result;
    }
}

//基本计算器
class Solution4_7 {

    //用时太长了1000ms，原因：等于循环了两轮 第一轮只去除了 * /
    public int calculate(String s) {

       Stack<Integer> number = new Stack<Integer>();
        Stack<Character> op = new Stack<Character>();
        int carry = 0;
        for(int i = 0 ; i<s.length();i++){
            char now = s.charAt(i);
            if(now == ' ' ) continue;
            else if(Character.isDigit(now)){
                carry = carry * 10 + Integer.parseInt(Character.toString(now));
            }else{   //遇到字符
                number.add(carry);
                    if( !op.isEmpty() &&(op.peek() == '*' || op.peek() == '/')){
                        char temp_op =op.pop();
                        int  a = number.pop();
                        int  b = number.pop();
                        int  new_number = cal(b,a,temp_op);
                        number.add(new_number);
                        op.add(now);
                        carry = 0;
                    }else{

                        op.add(now);
                        carry = 0;
                    }
            }
        }
        number.add(carry);
        if( !op.isEmpty() &&(op.peek() == '*' || op.peek() == '/')){
            char temp_op =op.pop();
            int  a = number.pop();
            int  b = number.pop();
            int  new_number = cal(b,a,temp_op);
            number.add(new_number);
        }
        int result = number.remove(0);
        while(!op.isEmpty()){
            result = cal(result,number.remove(0),op.remove(0));
        }
        return result;
    }

    public int cal(int a,int b,char op){
        if(op =='+') return a+b;
        if(op == '-') return a-b;
        if(op =='*') return a*b;
        if(op =='/') {
            if(a==0) return 0;
            return a/b;
        }
        return 0;
    }

    //答案  用时34ms 思路是一样的就是写法问题
    public int calculate2(String s) {
        char op = '+';  //记录上一个op
        int num = 0;
        Stack<Integer>stack = new Stack<Integer>();
        int i = 0;
        int length = s.length();
        while(i<length){
            char c = s.charAt(i);
            if(c>='0' && c<='9'){   //数字
                num = num*10 + c-'0';
            }
            if(i == length-1 || (c <'0' && c!=' ')){
                //对上个数字进行处理
                if(op =='*'){
                    stack.push(stack.pop() * num);
                }else if(op =='/'){
                    stack.push(stack.pop() / num);
                }else if(op =='-'){
                    stack.push(-num);
                }else{
                    stack.push(num);
                }
                op = c;
                num=0;
            }
            i++;
        }
        int result = 0;
        while(!stack.isEmpty()){
            result+=stack.pop();
        }
        return result;
    }
}

//扁平化嵌套列表迭代器
 interface NestedInteger {

            // @return true if this NestedInteger holds a single integer, rather than a nested list.
            public boolean isInteger();

           // @return the single integer that this NestedInteger holds, if it holds a single integer
           // Return null if this NestedInteger holds a nested list
            public Integer getInteger();

              // @return the nested list that this NestedInteger holds, if it holds a nested list
              // Return null if this NestedInteger holds a single integer
             public List<NestedInteger> getList();
 }
 class NestedIterator implements Iterator<Integer> {

    private Stack<NestedInteger> s ;
    public NestedIterator(List<NestedInteger> nestedList) {
       s  = new Stack<NestedInteger>();
        for(int i = nestedList.size()-1; i>= 0;i--){
            s.push(nestedList.get(i));
        }
    }
    @Override
    public Integer next() {
        NestedInteger t = s.pop();

        return t.getInteger();
    }

    @Override
    public boolean hasNext() {
        while(!s.isEmpty()){
            NestedInteger t = s.peek();
            if( t.isInteger()) return true;
            else{  //是一个list 把list中的元素拿出来分别加入栈中
                s.pop();
                for(int i = t.getList().size()-1 ; i>= 0 ;i--){
                    s.push(t.getList().get(i));
                }
            }
        }
        return false;
    }
}

//逆波兰表达式求值
class Solution4_8 {
    public int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack<Integer>();
        for(int i = 0 ; i<tokens.length;i++){
            if(tokens[i].equals("+") ){
                int a = s.pop();
                int b = s.pop();
                s.add(b+a);
            }else if(tokens[i].equals("-")){
                int a = s.pop();
                int b = s.pop();
                s.add(b-a);
            }else if(tokens[i].equals( "*")){
                int a = s.pop();
                int b = s.pop();
                s.add(b*a);
            }else if(tokens[i].equals( "/")){
                int a = s.pop();
                int b = s.pop();
                s.add(b/a);
            }else{
                s.add(Integer.parseInt(tokens[i]));
            }
        }
        return s.peek();
    }
}
public class InterviewPart4 {
    public static void main(String[] args) {
//        Solution3_1 solution = new Solution3_1();
//        int[] nums = {-4,-3,-2};
//        int result = solution.maxProduct(nums);
//        System.out.print(result);

//        Solution4_2 solution = new Solution4_2();
//        int[] nums1 = {3,2,3,1,2,4,5,5,6};
//       // int[] nums1 = {3,2,1,5,6,4};
//        int result = solution.findKthLargest(nums1,4);
//        System.out.print(result);

//        Solution4_6 solution = new Solution4_6();
//        int[] nums1 = {9,10,9,-7,-4,-8,2,-6};
//        // int[] nums1 = {3,2,1,5,6,4};
//        int[] result = solution.maxSlidingWindow(nums1,5);
//        System.out.print(result);

        Solution4_7 solution = new Solution4_7();

        int result = solution.calculate("3+2*2+6-9/5+8+5*1/4");
        System.out.print(result);
    }

}