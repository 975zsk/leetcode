package 周赛;

import java.util.ArrayList;
import java.util.List;

public class no160 {
    //1237 找出给定方程的正整数解
    class CustomFunction {
        // Returns f(x, y) for any given positive integers x and y.
        // Note that f(x, y) is increasing with respect to both x and y.
        // i.e. f(x, y) < f(x + 1, y), f(x, y) < f(x, y + 1)
        public int f(int x, int y){
            return 1;
        }
    };
    public List<List<Integer>> findSolution(CustomFunction customfunction, int z) {

        int x = 1;
        int y = 1000;
        List<List<Integer>> result = new ArrayList<>();
        for(; x <= 1000 && y>=1; ){
            if(customfunction.f(x,y) < z){
                x++;
                // if(customfunction.f(x,y) >z) return result;
            }else if(customfunction.f(x,y) >z){
                y--;
                // if(customfunction.f(x,y) < z) return result;
            }else{
                List<Integer> row = new ArrayList<>();
                row.add(x);
                row.add(y);
                result.add(row);
                x++;y--;
            }
        }
        return result;
    }

    //1238. 循环码排列
    //格雷码
    public List<Integer> circularPermutation(int n, int start) {

        //step1 :生成格雷码  (int型)
        List<Integer> grayCode = new ArrayList<>();
        grayCode.add(0);
        grayCode.add(1);
        for(int i = 2 ; i <= n ;i++){
            for(int j = grayCode.size()-1 ; j>= 0 ;j--){
                grayCode.add( grayCode.get(j) + (1 << (i-1)));
            }
        }
        //二分找到start的位置
        int l = 0 , r = grayCode.size()-1;
        while(l <= r){
            if(grayCode.get(l) == start || grayCode.get(r) == start) break;
            l++; r--;
        }

        //step2 ： 移动数组
        //截断格雷码，重排序
        List<Integer> result = new ArrayList<>();
        int index = 0;
        if(grayCode.get(l) == start) index = l;
        else index = r;

        for(int i = index ; i < grayCode.size() ;i++) result.add(grayCode.get(i));
        for(int i = 0 ; i<index; i++) result.add(grayCode.get(i));
        return result;
    }
    // 生成格雷码 （string格式）
    //grayCode[0] = "0";
    //grayCode[1] = "1";
    //String[] grayCode_n = generateGrayCode(n,1,grayCode);
    private String[] generateGrayCode(int n,int level, String[] grayCode){
        String[] newCode =  new String[grayCode.length * 2];
        if(level == n){
            return grayCode ;
        }
        if(n==1){
            return grayCode;
        }
        for(int i = 0 ; i < grayCode.length;i++){
            newCode[i] = "0" + grayCode[i];
            newCode[grayCode.length*2 -1 -i] = "1" + grayCode[i];
        }
        String[] next = generateGrayCode(n,level+1 ,newCode);
        return next;
    }


    //1239 串联字符串的最大长度
    int max_len = 0;
    boolean[] valid;
    public int maxLength(List<String> arr) {
        int n = arr.size();
        int[] table = new int[26];
        valid = new boolean[n];
        for(int i = 0 ; i < arr.size() ;i++) valid[i] = isWordValid(arr.get(i));
        dfs(0,n,table,arr,0);
        return max_len;
    }
    private boolean isWordValid(String word){
        int[] table = new int[26];
        for(int i = 0 ;i < word.length() ;i++){
            if(table[word.charAt(i)-'a']>0) return false;
            table[word.charAt(i)-'a']++;
        }
        return true;
    }
    private void dfs(int index,int n, int[] table ,List<String> arr,int len){

        if(index >= n){
            max_len = Math.max(max_len,len);
            return ;
        }
        String word = arr.get(index);
        boolean flag = valid[index];
        if(flag) {
            for (int j = 0; j < word.length(); j++) {
                if (table[word.charAt(j) - 'a'] > 0) {
                    flag = false;
                    break;
                }
            }
        }
        if(flag){  //该单词合法 可以拿
            for(int j = 0 ; j < word.length() ; j++){
                table[word.charAt(j) -'a'] ++ ;
            }
            len += word.length();
            dfs(index+1,n,table,arr,len);
            len -= word.length();
            for(int j = 0 ; j < word.length() ; j++){
                table[word.charAt(j) -'a'] -- ;
            }
        }
        //不拿
        dfs(index+1,n,table,arr,len);
    }


    //1240 铺瓷砖
    public int tilingRectangle(int n, int m) {

        return 0;
    }
}
