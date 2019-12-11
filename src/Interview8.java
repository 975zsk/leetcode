import java.util.*;

//最大数，简单
class Solution8_1{
    public String largestNumber(int[] nums) {
        String [] list = new String[nums.length];
        for(int i = 0 ;i <nums.length;i++){
            list[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o2+o1).compareTo(o1+o2);
            }
        });
        //不可以这么写了，不知道为啥。。。
//        Arrays.sort(nums,new Comparator<Integer>(){
//            @Override
//            public int compare(Integer a, Integer b) {
//                String str_a = a.toString();
//                String str_b = b.toString();
//                int len = Math.min(str_a.length(),str_b.length());
//                for(int i = 0 ; i<len ;i++){
//                    if(str_a.charAt(i)-'0' != str_b.charAt(i)-'0'){
//                        return str_a.charAt(i)- str_b.charAt(i);
//                    }
//                }
//                return str_a.length() - str_b.length();
//            }
//        });
        StringBuilder sb = new StringBuilder();
        if(list[0].equals("0")) return "0";
        for(String temp: list){
            sb.append(temp);
        }
        return sb.toString();
    }
}

//摆动排序2 ,快排找中位数
class Solution8_2 {
    //排序： 然后 0 len/2+1  1  len/2+2 ..交替拿     nlogn
    public void wiggleSort(int[] nums) {
        int len = (nums.length + 1) / 2;
        int[] a = Arrays.copyOfRange(nums, 0, nums.length);
        Arrays.sort(a);
        int k = 0, p = (nums.length - 1) / 2, q = nums.length - 1;
        boolean left = true;
        while (k < nums.length) {
          if(left) {
              nums[k++] = a[p--];
          }else{
              nums[k++] = a[q--];
          }
          left = !left;
        }
    }

    //o(n) 的算法 用快排找中位数
    public void wiggleSort2(int[] nums){

        int [] tem =Arrays.copyOfRange(nums,0,nums.length);
        int mid = findMid(tem,0,tem.length-1,tem.length/2);

        int k = 0, p = (nums.length - 1) / 2, q = nums.length - 1;
        boolean left = true;
        while (k < nums.length) {
            if(left) {
                nums[k++] = tem[p--];
            }else{
                nums[k++] = tem[q--];
            }
            left = !left;
        }

    }
    public int findMid(int[] nums, int l ,int r ,int rank){
           int left = l ,right = r;
           int now = nums[left];
           //找到一个的位置
           while(left < right){
               while(left <right && nums[right]>= now){
                   right--;
               }
               nums[left] = nums[right];
               while(left <right && nums[left] <= now){
                   left++;
               }
               nums[right] = nums[left];
           }
           nums[left] = now;
           if(left -l  == rank){
               return now;
           }else if ( left - l < rank){
               return findMid(nums,left+1,r,rank-(left-l +1));
           }else{
               return findMid(nums,l,right-1,rank);
           }
    }
}

//寻找峰值，二分
class Solution8_3 {
    //必须是logn的
    public int findPeakElement(int[] nums) {

    if(nums.length ==1) return 0;
    return find(nums,0,nums.length-1);
    }

    public  int find(int[]nums,int l ,int r){
        if(r-l <2) {
            if(l==0 && nums[1] <nums[0]) return 0;
            else if( r == nums.length-1 && nums[r] > nums[r-1]) return nums.length-1;
            return -1;
        }
        int mid_index = (l+r)/2;
        int index1= -1;
        int index2= -1;
        if(nums[mid_index] > nums[mid_index+1] && nums[mid_index] > nums[mid_index-1]) return mid_index;
        else if(nums[mid_index] > nums[mid_index+1] && nums[mid_index] < nums[mid_index-1]) {
            index1 = find(nums,l,mid_index);
            if(index1 >= 0) return index1;
            index2 = find(nums,mid_index+1,r);
            if(index2 >= 0) return index2;
        }else if(nums[mid_index] < nums[mid_index+1] && nums[mid_index] >nums[mid_index-1]){
            index1 = find(nums,l,mid_index-1);
            if(index1 >= 0) return index1;
            index2 = find(nums,mid_index,r);
            if(index2 >= 0) return index2;
        }else{
            index1 = find(nums,l,mid_index);
            if(index1 >= 0) return index1;
            index2 = find(nums,mid_index,r);
            if(index2 >= 0) return index2;
        }
        return -1;
    }
}

//寻找重复数(只出现一次)
//不能改变原数组， o(1)空间  o(n2)
//！很好的题 转化为寻找环的起点问题  。快慢指针
class Solution8_4 {
    //类似于操场跑圈，快速指针和慢速指针同时同宿舍出发，快速指针先到操场开始跑圈，慢速指针后到操场开始跑圈，但是快速指针一定会在某个时刻与慢速指针到达同一位置。
    public int findDuplicate(int[] nums) {
        //找快慢指针第一次相交的店，这个点不一定是圆环的起点。
        int quick = nums[0];
        int slow = nums[0];
        do{
            quick = nums[nums[quick]];
            slow = slow;
        }while(quick!=slow);
        //找圆环的起点，根据数学知识 ，宿舍到操场的距离 = 刚才相遇时快指针- 慢指针的距离
        int p1 = nums[0];
        int p2 = slow;
        while(p1!=p2){
            p1 = nums[p1];
            p2 = nums[p2];
        }
        return p1;
    }
}

//计算右侧小于当前元素的个数
class Solution8_5 {
    //好想的 二分搜索，找自己的位置, 超时  虽然查找的过程只要nlogn  但插入元素 需要o(n) so n^2
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> q = new LinkedList<>();  //排序好的
        Integer[] res = new Integer[nums.length];
        for(int i = nums.length-1 ; i>= 0 ; i--){
            int left = 0, right = q.size();
            while(left<right){
                int mid = left + (right - left)/2;

                if( q.get(mid)>= nums[i]) right = mid;
                else left = mid+1;
            }
            res[i] = right;
            q.add(right,nums[i]);
        }
        List<Integer> count = Arrays.asList(res);
        return count;
    }

    //使用bitree：树状数组 查询和修改复杂度 都为logn，主要勇于数组的单点修改和区间求和 没看懂
    //x-(x&(-x))刚巧是前一个棵树的根节点，
    //x+(x&(-x))刚巧是我的根节点，
    public static  List<Integer> countSmaller3(int[] nums) {
        if (nums.length == 0) {
            return new ArrayList<>();
        }
        int min = Integer.MAX_VALUE; // nums数组最小值，用来重置区间 eg: 5 6 0 2 1   6 7 1 3 2
        for (int value : nums) {
            if (value < min) {
                min = value;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nums[i] - min + 1;
        }
        int max = Integer.MIN_VALUE;  // nums数组最大值  ,是树形数组的长度
        for (int value : nums) {
            if (value > max) {
                max = value;
            }
        }
        int[] BITree = new int[max + 1];  //树型数组从1开始计算
        BITree[0] = 0;
        int[] countArr = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            int count = getSum(nums[i] - 1, BITree);   //nums[i] -1  比nums[i]小
            countArr[i] = count;
            update(nums[i], BITree); //比nums[i] 小
        }
        List<Integer> result = new ArrayList<>();
        for (int value : countArr) {
            result.add(value);
        }
        return result;
    }
    //背住
    public static int getSum(int value, int[] BITree) { // 获得a[i]从1，value的和
        int sum = 0;
        while (value > 0) {
            sum += BITree[value];
            value -= (value & -value);
        }
        return sum;
    }
    //背住
    public static void update(int value, int[] BITree) {
        while (value <= BITree.length - 1) {
            BITree[value] += 1;
            value += (value & -value); //获得根节点
        }
    }
}

public class Interview8 {
    public static void main(String[] args) {
//        Solution8_2 solu = new Solution8_2();
//        int [] nums = {1,5,11,6,4,72,8,5,10,5,8};
//        solu.wiggleSort2(nums);
//        System.out.print(nums.toString());

//        Solution8_3 so = new Solution8_3();
//        int[] nums = {3,2,1};
//        int index =so.findPeakElement(nums);
//        System.out.print(index);

//        Solution8_4 so = new Solution8_4();
//        int [] nums = {1,3,4,2,2};
//        int index = so.findDuplicate(nums);
//        System.out.print(index);

        Solution8_5 so = new Solution8_5();
        int[] nums = {5,6,0,2,1};
       List<Integer> result  = so.countSmaller3(nums);
        System.out.print(result.toString());


    }
}
