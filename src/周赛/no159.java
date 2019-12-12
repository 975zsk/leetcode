package 周赛;

import sequence.TwoTo300;

import java.util.*;

public class no159 {

    //1232. 缀点成线
    public boolean checkStraightLine(int[][] coordinates) {
        int type = 0;
        if(coordinates.length <= 1) return true;
        int x1 = coordinates[0][0];
        int y1  = coordinates[0][1] ;
        int x2 = coordinates[1][0];
        int y2  = coordinates[1][1];
        if(x1 == x2) type = 1;   //斜率为无穷
        int x0 , y0 ;
        for(int i = 2 ; i < coordinates.length ;i++){
            x0 = x1;
            y0 = y1;

            x1 = x2;
            y1 = y2;
            x2 = coordinates[i][0];
            y2 = coordinates[i][1];
            if(type == 1 ){
                if(x1!= x2) return false;
            }else{
                if( (y2 - y1) * (x1- x0) != (y1-y0) * (x2-x1) ) return false;
            }
        }
        return true;
    }

    //1233. 删除子文件夹
    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        Set<String> fathers = new HashSet<>();

        for(int i = 0 ; i < folder.length ;i++){
            if(fathers.isEmpty()) fathers.add(folder[i]);
            else{
                String[] list = folder[i].split("/");
                String father_str = "";
                boolean falg = true;
                for(int j = 1 ; j < list.length ;j++){
                    father_str += "/" + list[j];
                    if(fathers.contains(father_str)){
                        falg = false;
                        break;
                    }
                }
                if(falg) fathers.add(folder[i]);
            }
        }
        List<String> result = new ArrayList<>();
        result.addAll(fathers);
        return result;
    }


    //1234  替换子串得到平衡字符串
    //只能替换一个子串
    //最经典的滑动窗口题
    public int balancedString(String s) {
        int n = s.length() /4;
        int len = s.length();
        int[] count = new int[26];
        for(int i = 0 ; i< s.length() ;i++){
            count[s.charAt(i) - 'A'] ++;
        }
        int delete_q = (count['Q'-'A'] - n) > 0 ? count['Q'-'A'] - n : 0;
        int delete_e = (count['E'-'A'] - n) > 0 ? count['E'-'A'] - n : 0;
        int delete_w = (count['W'-'A'] - n) > 0 ? count['W'-'A'] - n : 0;
        int delete_r = (count['R'-'A'] - n) > 0 ? count['R'-'A'] - n : 0;
        count['R'-'A'] = count['W'-'A'] = count['E'-'A'] = count['Q'-'A'] = 0;
        //滑动窗口的规则：
        int left = 0,right = 0;
        int min = len;
        if(delete_e == 0 && delete_q == 0 && delete_r== 0 &&delete_w ==0) return 0;
        while(right < len){
            count[s.charAt(right)- 'A'] ++;

            //可以左移
            while(left< len & count['Q'-'A'] >= delete_q && count['E'-'A'] >=delete_e && count['W'-'A'] >=delete_w && count['R'-'A'] >=delete_r){
                min = Math.min(min,right - left + 1);
                count[s.charAt(left) -'A'] --;
                left ++;
            }
            right++;
        }
        return min;
    }


    //1235 规划兼职工作
    //动态规划
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        List<String> info = new ArrayList<>();
        int n = startTime.length;
        for(int i = 0 ; i < n ;i++){
            String str = startTime[i] + "_" + endTime[i] + "_" + profit[i];
            info.add(str);
        }
        Collections.sort(info, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] list1  = o1.split("_");
                String[] list2  = o2.split("_");
                return Integer.parseInt(list1[1]) -  Integer.parseInt(list2[1]);
            }
        });
        int maxTime = Integer.parseInt(info.get(n-1).split("_")[1]);
        int [] dp = new int[maxTime+1];
        int time = 1;
        int max = 0;
        for(int i = 0 ; i< n ;i++){    //以任务为维度进行循环，可以处理多个任务同时结束的情况
            int start = Integer.parseInt(info.get(i).split("_")[0]);
            int end = Integer.parseInt(info.get(i).split("_")[1]);
            int pro = Integer.parseInt(info.get(i).split("_")[2]);
            if(time == end){  //在该时间，有结束的任务
                dp[end] = Math.max(dp[end] ,dp[start] + pro);  //多个任务同时结束
            }else{
                while(time < end) {
                    dp[time] = dp[time-1];  //递推式：当前没有任务结束，则下个时间点收益 = 我
                    time++;
                }
                dp[end] = Math.max(dp[end-1] ,dp[start] + pro); //递推式
            }
            max = Math.max(max,dp[time]);
        }
        return max;
    }


    public static void main(String[] args) {
        no159 so = new no159();
        String[] folder = {"/a","/a/b","/c/d","/c/d/e","/c/f"};
        //so.removeSubfolders(folder);

        String str = "WWEQERQWQWWRWWERQWEQ";
        so.balancedString(str);

    }
}
