import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterviewPart2 {

    public static void main(String[] args) {
       //Solution2_1 solution = new Solution2_1();
       // boolean count = solution.isPalindrome("A man, a plan, a canal: Panama");
        //System.out.print(count);

//        Solution2_3 solution = new Solution2_3();
//        List<String> words = new ArrayList<String>();
//        words.add("leet");
//        words.add("code");
//        boolean result = solution.wordBreak("leetcode",words);
//        System.out.print(result);

        Solution2_8 solution = new Solution2_8();
        int result = solution.firstUniqChar("loveleetcode");
        System.out.print(result);

    }
}

class Solution2_9 {
    public void reverseString(char[] s) {

        int len = s.length/2;
        char temp ;
        for(int i = 0 ; i<len;i++){
            temp = s[i];
            s[i] = s[s.length-i-1];
            s[s.length-i-1] = temp;
        }
    }
}


//字符串中第一个唯一字符
//两层循环 超时

//class Solution2_8 {
//    public int firstUniqChar(String s) {
//
//        for(int i = 0 ; i< s.length() ;i++){
//            int j = 0;
//            for( j = i+1 ; j<s.length();j++){
//                if(s.charAt(i) == s.charAt(j)){
//                    break;
//                }
//            }
//            if(j == s.length()) return i;
//        }
//        return -1;
//    }
//}
class Solution2_8 {
    public int firstUniqChar(String s) {
       int[] table = new int[26];

       for(int i = 0 ; i<s.length();i++){
           table[s.charAt(i)-'a']++;
        }

        for(int i = 0 ; i< s.length();i++){
            if(table[s.charAt(i)-'a'] == 1) return i;
        }
        return -1;
    }
}

//字母异位词
class Solution2_7 {
    public boolean isAnagram(String s, String t) {

        int[] s_table = new int[26];
        int[] t_table = new int[26];

        int len = Math.max(s.length(),t.length());
        for(int i = 0 ; i<len ;i++){
            if(i<s.length()){
                char s_char = s.charAt(i);
                s_table[s_char-'a']++;
            }

            if(i<t.length()){
                char t_char = t.charAt(i);
                t_table[t_char-'a']++;
            }
        }
        for(int i = 0 ; i<26 ;i++){
            if(s_table[i]!= t_table[i]) return false;
        }
        return true;
    }
}

//基于字典树的单词搜索
class Solution2_6 {

    int[]wx = {0,0,-1,1};
    int[]wy = {1,-1,0,0};
    int row = 0;
    int col = 0;

    public List<String> findWords(char[][] board, String[] words) {

        TrieNode root = new TrieNode();

        for(String word :words){
            TrieNode temp = root;
            for(int i = 0 ; i< word.length();i++){
                if(!temp.map.containsKey(word.charAt(i))){
                    TrieNode newNode = new TrieNode();
                    temp.map.put(word.charAt(i),newNode);
                }
                temp = temp.map.get(word.charAt(i));
            }
            temp.isLeaf = true;
        }

        List<String> result = new ArrayList<String>();
        row = board.length;
        if(row ==0) return result;
        col = board[0].length;
        boolean [][] visited = new boolean[row][col];
        for(int i = 0 ; i < row ;i ++){
            for(int j = 0 ; j<col; j++){
                String temp = "";
                visit(i,j,board,root,result,temp,visited);
            }
        }
        return result;
    }

    void visit(int i ,int j,char[][] board,TrieNode node,List<String> result,String temp,boolean[][] visited){
        if(node.isLeaf && !result.contains(temp)) {
            result.add(temp);
            return;
        }
        if( i<0|| i >=row || j<0 || j>= col || visited[i][j] ||!node.map.containsKey(board[i][j])) return ;
        visited[i][j] = true;
        node = node.map.get(board[i][j]);
        temp+= board[i][j];

        for(int k =0 ; k<4;k++){
            int xx = i + wx[k];
            int yy = j + wy[k];
            visit(xx,yy,board,node,result,temp,visited);
            //temp = temp.substring(0,temp.length());
        }
        visited[i][j] = false;
    }

    private class TrieNode{
        boolean isLeaf ;
        Map<Character, TrieNode> map;

        public TrieNode(){
            map =  new HashMap<Character, TrieNode>();
            isLeaf = false;
        }
    }
}

class Trie {

    private class TrieNode{
        boolean isLeaf ;
        Map<Character, TrieNode> map;

        public TrieNode(){
            map =  new HashMap<Character,TrieNode>();
            isLeaf = false;
        }
    }

    TrieNode root ;
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {

        if(word == null || word.length() == 0) return ;
        TrieNode node = root;
        for(int i = 0 ; i <word.length();i++){
            if(!node.map.containsKey(word.charAt(i))){
                TrieNode newNode = new TrieNode();
                node.map.put(word.charAt(i),newNode);
            }
            node = node.map.get(word.charAt(i));
        }
        node.isLeaf = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {

        if(word == null || word.length() == 0) return false;
        TrieNode node = root;
        for(int i = 0 ; i<word.length() ;i++){
            node = node.map.get(word.charAt(i));
            if(node == null) return false;
        }
        return node.isLeaf;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {

        if(prefix == null || prefix.length() == 0) return false;
        TrieNode node = root;
        for(int i = 0 ; i<prefix.length() ;i++){
            node = node.map.get(prefix.charAt(i));
            if(node == null) return false;
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */

//时间超了， 第一遍把字符串摘出来 时间*两倍
//用双指针
class Solution2_1 {
    public boolean isPalindrome(String s) {

//        String ss ="";
//        for(int i = 0 ; i < s.length();i++){
//            char s1 = s.charAt(i);
//            if( (s1 >= 'A' && s1 <= 'Z') || (s1 >= 'a' && s1 <= 'z')|| Character.isDigit(s1)) ss+= Character.toLowerCase(s1);
//        }
//        int length = ss.length();
//        int index = length/2 ;
//        for(int i = 0 ; i<index;i++ ){
//            if(ss.charAt(i) != ss.charAt(length - i -1)) return false;
//        }
//        return true;

        int left =0 ;
        int right = s.length()-1;

        while(left<right){
            while(!isAlphaNum(s.charAt(left))&& left<right ) left++;
            while(!isAlphaNum(s.charAt(right))&& left<right ) right--;

            char left_char = Character.toLowerCase(s.charAt(left));
            char right_char = Character.toLowerCase(s.charAt(right));

            if(left_char!= right_char) return false;

            left++; right--;
        }
        return true;
    }

    boolean isAlphaNum(char s1){
        if((s1 >= 'A' && s1 <= 'Z') || (s1 >= 'a' && s1 <= 'z')|| Character.isDigit(s1)) return true;
        return false;
    }
}

//经典回溯
//拆分字符串
class Solution2_2 {
    public List<List<String>> partition(String s) {

        List<List<String>> result = new ArrayList<List<String>>();
        List<String> row = new ArrayList<String>();

        next(s,row,result);
        return result;
    }

     void  next(String s,List<String> row ,List<List<String>> result){

        if(s.isEmpty()){
            result.add(new ArrayList<String>(row));
            return;
        }
         for(int i = 0 ; i< s.length();i++){
             String left =  s.substring(0,i+1);
             String right = s.substring(i+1);

             if(isPalindrome(left)){
                 row.add(left);
                 next(right,row,result);
                 row.remove(row.size()-1);
             }
         }
     }

     boolean isPalindrome(String s) {
         int length = s.length();
         int index = length/2 ;
         for(int i = 0 ; i<index;i++ ){
            if(s.charAt(i) != s.charAt(length - i -1)) return false;
         }
        return true;
    }

}

//单词拆分
//思路一：回溯 超时
//class Solution2_3 {
//    public boolean wordBreak(String s, List<String> wordDict) {
//        return backtrack(s,wordDict);
//    }
//
//    boolean backtrack(String s, List<String> wordDict){
//        if(s.isEmpty()) return true;
//        for(int i = 0 ; i< s.length();i++){
//            String left = s.substring(0,i+1);
//            String right = s.substring(i+1);
//            if(wordDict.contains(left)){
//               if(backtrack(right,wordDict)) return true;
//            }
//        }
//        return false;
//    }
//}
//dp[j] = dp[i] && dict.contains(s.substring(i, j))
class Solution2_3 {
    public boolean wordBreak(String s, List<String> wordDict) {

        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;

        for(int i = 0; i < s.length() ;i++){
            if(!dp[i]) continue;

            for(String str:wordDict){
                if(i+ str.length()<= s.length() && s.substring(i,i+str.length()).equals(str)){
                    dp[i+str.length()] = true;
                }
            }
        }
        return dp[s.length()];
    }
}