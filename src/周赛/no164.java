package 周赛;

import sequence.TwoTo300;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class no164 {

    //1266 切比雪夫距离
    public int minTimeToVisitAllPoints(int[][] points) {
        int step = 0;
        for(int i = 0 ;i < points.length-1 ;i++){
            step += Math.max(Math.abs(points[i+1][0] - points[i][0]) ,Math.abs(points[i+1][1]- points[i][1]));
        }
        return step;
    }

    //1267 统计参与通信的服务器
    //想复杂了
    public int countServers(int[][] grid) {
        int row = grid.length;
        if(row == 0) return 0;
        int col = grid[0].length;
        if(col == 0) return 0;
        int[] count_row = new int[row];
        int[] count_col = new int[col];
        for(int i = 0 ; i< row ; i++){
            for(int j = 0 ; j< col ;j++){
                if(grid[i][j] == 1){
                    count_row[i] ++;
                    count_col[j] ++;
                }
            }
        }
        int ans = 0;
        for(int i = 0 ; i < row ;i++){
            for(int j = 0 ; j < col ;j++){
                if(grid[i][j] == 1 && (count_row[i] >1 || count_col[j] >1)) ans++;
            }
        }
        return ans;
    }

    //错误解。。不是flooding
    int result = 0;
    boolean[][] visited;
    int row ,col;
    int[][] direction = {{1,0},{-1,0},{0,1},{0,-1}};
    public int countServers2(int[][] grid) {
        row = grid.length;
        col = grid[0].length;
        visited = new boolean[row][col];
        for(int i = 0 ; i< row ;i++){
            for(int j = 0 ; j< col ;j++){
                if(grid[i][j] == 1 && !visited[i][j]){
                    int sum = flooding(grid,i,j);
                    if(sum!= 1) result+= sum;
                }
            }
        }
        return result;
    }
    public int flooding(int[][]grid,int index_i,int index_j){
        int sum = 0;
        for(int i = 0 ; i< direction.length ;i++){
            int new_i = index_i + direction[i][0];
            int new_j = index_j + direction[i][1];
            if(new_i >= 0 && new_i <row &&new_j >= 0 && new_j <col && grid[new_i][new_j] ==1 && !visited[new_i][new_j]){
                visited[new_i][new_j] = true;
                sum++;
                sum += flooding(grid,new_i,new_j);
            }
        }
        return sum;
    }

    //1268 搜索推荐系统
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        int start = 0;
        List<List<String>> result = new ArrayList<>();
        boolean flag ;

        for(int i = 1 ; i <= searchWord.length() ;i++){
            String str = searchWord.substring(0,i);
            flag = true;
            List<String> row = new ArrayList<>();
            for( ; start < products.length && flag ;start++){
                if(products[start].startsWith(str)){
                     //往后数3个
                    for(int k = 0 ; k < 3 && start + k < products.length ;k++ ){
                        if(products[k+start].startsWith(str))  {
                            row.add(products[k+start]);
                        }else{
                            break;
                        }
                    }
                    flag = false;
                }
            }
            result.add(row);
            start --;
        }
        return result;
    }

    //1269 停在原地的方案数
    public int numWays(int steps, int arrLen) {
        int mod = 1000000007;
        arrLen = Math.min(steps +1 ,arrLen);
        int [][] dp = new int[steps+1][arrLen];
        dp[0][0] = 1;
        for(int i = 1 ; i <= steps; i++){
            for(int j = 0 ; j < arrLen ;j++){
                dp[i][j] = dp[i-1][j];
                if(j-1 >= 0 ) dp[i][j] = ( dp[i][j] + dp[i-1][j-1] ) %mod;
                if(j+1 <arrLen) dp[i][j] = ( dp[i][j] + dp[i-1][j+1] ) %mod;
            }
        }
        return dp[steps][0];
    }

    public static void main(String[] args) {
        no164 so = new no164();
        String str = "(1+(4+5+2)-3)+(6+8)";
        // System.out.println(so.calculate(str));
        System.out.println(Runtime.getRuntime().freeMemory());
    }

}
