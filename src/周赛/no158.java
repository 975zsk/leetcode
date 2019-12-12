package 周赛;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class no158 {

    //1221 分割平衡字符串
    public int balancedStringSplit(String s) {
        int r = 0 ;
        int l = 0;
        int result = 0;
        for(int i = 0 ; i< s.length(); i++){
            if(s.charAt(i) == 'R') r++;
            else l++;
            if(r== l) {
                result ++;
                r = l = 0;
            }
        }
        return result;
    }

    //1222. 可以攻击国王的皇后
    int[][] directions = {{0,1},{0,-1},{1,0},{-1,0},{1,-1},{-1,1},{1,1},{-1,-1}};
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {

        Set<String> set = new HashSet<>();
        for(int i = 0 ; i< queens.length;i++){
            String str = queens[i][0] + "_" +queens[i][1];
            set.add(str);
        }
        //最多八种情况
        int king_x = king[0];
        int king_y = king[1];
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0 ; i< directions.length ;i++) {
            int x = king_x + directions[i][0];
            int y = king_y + directions[i][1];
            boolean falg = true;
            while(x>= 0 && x < 8 && y>= 0 && y<8 && falg) {
                String temp = x + "_" + y;
                if(set.contains(temp)){
                    List<Integer> queen = new ArrayList<>();
                    queen.add(x);
                    queen.add(y);
                    result.add(queen);
                    falg = false;
                }
                x = x + directions[i][0];
                y = y + directions[i][1];
            }
        }
        return result;
    }

    //1223 掷骰子模拟
    public int dieSimulator(int n, int[] rollMax) {

    }
    

}
