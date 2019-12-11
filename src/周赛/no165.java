package 周赛;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class no165 {

    //1275 找出井字棋的获胜者
    public String tictactoe(int[][] moves) {
        char[][] board = new char[3][3];
        char c = 'X';
        for(int i = 0 ; i < moves.length ;i++){
            board[moves[i][0]][moves[i][1]] = c;
            if(c =='X') c = 'O';
            else c = 'X';
        }
        char win = '#';
        boolean flag = false;
        //row
        for(int i = 0 ; i < 3  && !flag;i++){
            int count = 1;
            for(int j = 0 ; j< 2 ;j++){
                if(board[i][j] != board[i][j+1]) break;
                count++;
            }
            if(count == 3) {
                win = board[i][0];
                flag = true;
            }
        }
        //col
        for(int i = 0 ; i < 3  && !flag;i++){
            int count = 1;
            for(int j = 0 ; j< 2 ;j++){
                if(board[j][i] != board[j+1][i]) break;
                count++;
            }
            if(count == 3) {
                win = board[0][i];
                flag = true;
            }
        }
        //交叉
        if(board[0][0] == board[1][1] && board[1][1]== board[2][2]) win = board[0][0];
        if(board[0][2] == board[1][1] && board[1][1]== board[2][0]) win = board[0][2];

        if(win == 'X') return "A";
        else if(win == 'O') return "B";
        else {
            if(moves.length < 9 ) return "Pending";
            else return "Draw" ;
        }
    }

    //1276 不浪费原料的汉堡制作方案
    public List<Integer> numOfBurgers(int tomatoSlices, int cheeseSlices) {

        List<Integer> result = new ArrayList<>();
        if((tomatoSlices - 2 * cheeseSlices ) < 0) return result;
        if ((tomatoSlices - 2 * cheeseSlices ) %2 != 0 ) return result;
        int x = (tomatoSlices - 2 * cheeseSlices )/2;
        int y = cheeseSlices - x;
        if( y < 0) return result;
        result.add(x);  result.add(y);
        return result;
    }

    //1277 统计全为1的正方形子矩阵
    public int countSquares(int[][] matrix) {
        int rows = matrix.length;
        if(rows == 0) return 0;
        int cols = matrix[0].length;
        if(cols == 0) return 0;
        int sum = 0;
        int[][] dp = new int[rows+1][cols+1];
        for(int i = 1; i<= rows ; i++){
            for(int j = 1 ; j <= cols ;j++){
                if(matrix[i-1][j-1] == 1){
                    dp[i][j] = Math.min(Math.min(dp[i][j-1],dp[i-1][j]),dp[i-1][j-1]) +1;
                    sum+= dp[i][j];
                }
            }
        }
        return sum;
    }

    //1278 分割回文串3
    public int palindromePartition2(String s, int k) {
        int n = s.length();
        int [][] f = new int [n+1][k+1];   //前i个字符串被分割为j个回文
        //因此在状态转移时，必须要满足 i >= j 且 i0 >= j - 1。
        // 此外，当 j = 1 时，我们并不需要枚举 i0，这是因为将前 i 个字符分割成 j = 1 个非空字符串的方法是唯一的。
        f[0][0] = 0 ;
        for(int i = 1; i <= n ; i++){
            for(int j = 1 ; j <= Math.min(k,j);j++){
                if(j == 1){
                    f[i][j] = cost(s,0,i-1);
                }else{
                    for(int i0 = j-1 ; i0<i ;i0 ++){
                        f[i][j] = Math.min(f[i][j] , f[i0][j-1] + cost(s,i0,i-1));
                    }
                }
            }
        }
        return f[n][k];
    }
    private int cost(String s ,int l, int r){
        int ret = 0;
        for(int i = l  ,j = r ; i < j ;i++,j--){
            if(s.charAt(i)!= s.charAt(j)) ret++;
        }
        return ret;
    }
    //进一步： cost函数可以用动态规划求出
    public int palindromePartition(String s, int k) {
        int n = s.length();
        //预处理cost函数
        int[][] cost = new int[n][n];   //从i到j个字符改成回文串需要多少个操作
        for(int len = 2 ; len <= n ;len++){
            for(int i = 0 ; i+len-1 < n ;i++){
                int j = i+ len -1 ;
                cost[i][j] = cost[i+1][j-1] + (s.charAt(i) == s.charAt(j) ? 0 : 1);
            }
        }
        int [][] f = new int [n+1][k+1];   //前i个字符串被分割为j个回文

        //因此在状态转移时，必须要满足 i >= j 且 i0 >= j - 1。
        // 此外，当 j = 1 时，我们并不需要枚举 i0，这是因为将前 i 个字符分割成 j = 1 个非空字符串的方法是唯一的。
        f[0][0] = 0 ;
        for(int i = 1; i <= n ; i++){
            Arrays.fill(f[i],Integer.MAX_VALUE);
            for(int j = 1 ; j <= Math.min(k,j);j++){
                if(j == 1){
                    f[i][j] = cost[0][i-1];
                }else{
                    for(int i0 = j-1 ; i0<i ;i0 ++){
                        f[i][j] = Math.min(f[i][j] , f[i0][j-1] + cost[i0][i-1]);
                    }
                }
            }
        }
        return f[n][k];
    }

}
