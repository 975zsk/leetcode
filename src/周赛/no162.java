package 周赛;

import java.util.*;

public class no162 {
    //1252 奇数值单元格的数目
    public int oddCells(int n, int m, int[][] indices) {

        int [] row = new int[n];
        int [] col = new int[m];
        for(int i = 0 ; i< indices.length ;i++){
            int row0 = indices[i][0];
            row[row0]++;
            int col0 = indices[i][1];
            col[col0]++;
        }
        int res = 0;
        for(int i = 0 ; i < n ;i++){
            for(int j = 0 ; j<m ;j++){
                if((row[i] + col[j]) %2 == 1) res++;
            }
        }
        return res;
    }

    //1253 重构 2 行二进制矩阵
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {

        int n = colsum.length;
        List<Integer> first_list = new ArrayList<>();
        List<Integer> second_list = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        int[] first = new int[n];
        int[] second = new int[n];
        Queue<Integer> exchange = new LinkedList<>();
        int sum_first = 0;
        int sum_second = 0;
        for(int i = 0 ; i< colsum.length ;i++){
            if(colsum[i] == 2){
                if(sum_first+1 > upper || sum_second+1 > lower) {
                    if(exchange.size()== 0){

                        return result;
                    }else{    //can exchange
                        int index = exchange.poll();
                        int temp = first[index];
                        first[index] = second[index];
                        second[index] = temp;
                        if(temp == 1){
                            sum_first-- ; sum_second++;
                        }else{
                            sum_first++; sum_second--;
                        }
                    }
                }
                first[i] = 1;
                sum_first++;
                second[i] = 1;
                sum_second++;
            }else if (colsum[i] == 0){
                first[i] = 0;
                second[i] = 0;
            }else{
                if(sum_first+1 <= upper) {
                    first[i] = 1;
                    sum_first ++;
                    second[i] = 0;
                    exchange.add(i);
                }else if(sum_second+1 <= lower){
                    first[i] = 0;
                    second[i]= 1;
                    sum_second++;
                    exchange.add(i);
                }else{

                    return result;
                }
            }
        }
        if(sum_first!= upper || sum_second != lower) return result;
        for(int i = 0 ;i< n;i++){
            first_list.add(first[i]);
            second_list.add(second[i]);
        }
        result.add(first_list);
        result.add(second_list);
        return result;
    }

    //1254 统计封闭岛屿的数目
    int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
    public int closedIsland(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        //step1
        for(int i = 0 ; i < row ;i++){
            dfs1(grid,i,0,row,col);
            dfs1(grid,i,col-1,row,col);
        }
        for(int j = 0 ; j < col ;j++){
            dfs1(grid,0,j,row,col);
            dfs1(grid,row-1,j,row,col);
        }
        //step2
        int sum = 2;
        for(int i = 0 ; i < row ;i++){
            for(int j = 0 ; j < col;j++){
                if(grid[i][j] == 0){
                    dfs2(grid,i,j,row,col,sum);
                    sum++;
                }
            }
        }
        return sum-2;
    }
    private void dfs2(int[][]grid, int i ,int j ,int row,int col,int sum){
        if(grid[i][j]!= 0) return ;
        grid[i][j] = sum;
        for(int k = 0 ; k< 4 ;k++ ){
            int new_i = i + directions[k][0];
            int new_j = j+ directions[k][1];
            if(new_i >= 0 && new_i<row && new_j>= 0 && new_j< col && grid[new_i][new_j] == 0){
                dfs2(grid,new_i,new_j,row,col,sum);
            }
        }
    }
    private void dfs1(int[][] grid, int i ,int j,int row, int col ){
        if(grid[i][j] ==1 ) return ;
        grid[i][j] = 1;
        for(int k = 0 ; k< 4 ;k++ ){
            int new_i = i + directions[k][0];
            int new_j = j+ directions[k][1];
            if(new_i >= 0 && new_i<row && new_j>= 0 && new_j< col && grid[new_i][new_j] == 0){
                dfs1(grid,new_i,new_j,row,col);
            }
        }
    }


    //1255 得分最高的单词集合
    int max = 0;
    int[] word_score ;
    public int maxScoreWords(String[] words, char[] letters, int[] score) {

        //第一步： 算每个单词的得分 每个字母的数量
        word_score = new int[words.length];
        int i = 0;
        for(String word: words){
            int temp = 0;
            for( int j = 0 ; j < word.length() ; j++){
                temp += score[word.charAt(j)- 'a'];
            }
            word_score[i] = temp;
            i++;
        }
        int [] letters_left = new int[26];
        for(char c : letters){
            letters_left[c-'a'] ++;
        }

        //第二步： 01 背包遍历 每个单词取或不取
        backstracking(0,words,letters_left,0);
        return max;
    }
    private void backstracking(int index,String[] words,int[] letters_left,int score){

        if(index == words.length){
            max = Math.max(max,score);
            return ;
        }
        String word = words[index];
        if(canTake(word,letters_left)){
            //拿
            for(int i = 0 ; i< word.length() ;i++){
                letters_left[word.charAt(i)-'a'] --;
            }
            backstracking(index+1,words,letters_left,score + word_score[index]);
            for(int i = 0 ; i< word.length() ;i++){
                letters_left[word.charAt(i)-'a'] ++;
            }
        }
        backstracking(index+1,words,letters_left,score);

    }
    private boolean canTake(String word, int[]letters_left){
        int[] copy = letters_left.clone();
        for(int i = 0 ; i< word.length() ;i++){
            if(copy[word.charAt(i)-'a'] <=0 ) return false;
            copy[word.charAt(i)-'a'] --;
        }
        return true;
    }


}
