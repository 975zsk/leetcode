import java.util.*;

//excell 表列序号
class Solution6_1 {
    public int titleToNumber(String s) {
        int count = 0;
        for(int i = 0 ; i< s.length();i++){
            char c = s.charAt(i);
            count = count * 26 + c -'A'+1;
        }
        return count;
    }
}

//四数相加= 0
//暴力 太慢 用HashMap记录两元素之和的所有可能性与频率,可以把复杂度对半砍
class Solution6_2 {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {

        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0 ; i< A.length ; i++){
            for(int j = 0 ; j<B.length;j++){
                int sum = A[i]+B[j];
                if(map.containsKey(sum)) map.put(sum,map.get(sum)+1);
                else map.put(sum,1);
            }
        }
        int res = 0;
        for(int i = 0 ; i<C.length;i++){
            for(int j = 0 ; j<D.length;j++){
                int sum = 0- C[i]-D[j];
                if(map.containsKey(sum)) res+=map.get(sum);
            }
        }
        return res;
    }
}

//常数时间插入、删除和获取随机元素
class RandomizedSet {

    List<Integer> number;
    Map<Integer,Integer> map;  //value：index
    int size = 0;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        map = new HashMap<Integer, Integer>();
        number = new ArrayList<Integer>();
        this.size = 0;
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(map.containsKey(val)) return false;
        else{
            number.add(size,val);
            map.put(val,size);
            size++;
            return true;
        }
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(!map.containsKey(val)) return false;
        else{
            int tail = number.get(size-1);
            int index = map.get(val);
            map.put(tail,index);
            number.set(index,tail);
            size--;
            map.remove(val);       //把list中的最后一个数字放到要remove的元素上去
        }
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random rdm = new Random();
        return number.get(rdm.nextInt(size));
    }
}

public class Interview6 {
}
