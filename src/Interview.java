import java.util.HashMap;
import java.util.Map;

class Main {
    public static void main(String[] args) {
         Solution1 solution1 = new Solution1();

//         int[][] matrix = {
//                 {1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}
//         };
        int[][] matrix = {
                {1,1},
        };
         boolean count = Solution3.searchMatrix(matrix,0);
         System.out.print(count);
    }
}



    //只出现一次的数字，线性复杂度 0 空间 排序、hashset、字典 都不行
    //PS异或法;
    // 根据异或运算的特点，相同的数字经过异或运算后结果为0，
    // 除单独出现一次的数字外，其他数字都是出现两次的，那么这些数字经过异或运算后结果一定是0。
    // 而任何数字与0进行异或运算都是该数字本身。
    // 所以对数组所有元素进行异或运算，运算结果就是题目的答案。
    class Solution1 {
        public int singleNumber(int[] nums) {
            int count = 0;
            for(int i = 0 ; i < nums.length; i++ ){
                count =  count ^ nums[i];
            }
            return count;
        }
    }

//求众数 数组中次数> n/2 的元素
//sort  下标 n/2  n^3
//字典法  n
class Solution2 {
    public int majorityElement(int[] nums) {

        Map<Integer,Integer> diectionary = new HashMap<>();

        for(int i = 0 ; i<nums.length ;i++){
            if(!diectionary.containsKey(nums[i])){
                diectionary.put(nums[i],1);
            }else{
                diectionary.put(nums[i],diectionary.get(nums[i])+1);
            }
        }
        int max = 0 ;
        int max_key = 0;
        for(int i = 0 ; i< nums.length;i++){
            if(diectionary.get(nums[i])>max){
                max = diectionary.get(nums[i]);
                max_key = nums[i];
            }
        }
        return max_key;
    }
}

//搜索二维矩阵 升序
//利用性质
class Solution3 {

    //思路：把不符合的值切掉
    public static boolean searchMatrix(int[][] matrix, int target) {

        if(matrix.length ==0 || matrix[0].length == 0) return false;
        int row_l=0 ,row_r= matrix.length-1 ;
        int col_l=0, col_r = matrix[0].length-1;

        while(row_l<row_r || col_l< col_r){

            for(int i = col_l ; i<=col_r; i++){
                if(matrix[row_r][col_l] < target){
                    col_l ++;
                    if(col_l == matrix[0].length) return false;
                }else{
                    if(matrix[row_r][col_l] == target) return true;
                    break;
                }
            }

            for(int i = row_l ; i<=row_r;i++){
                if(matrix[row_l][col_r] < target){
                    row_l ++;
                    if(row_l == matrix.length) return false;
                }else{
                    if(matrix[row_l][col_r] == target) return true;
                    break;
                }
            }

            for(int i = row_r ; i >= row_l; i--){
                if( matrix[row_r][col_l] > target ){
                    row_r--;
                    if(row_r == -1) return false;
                }else{
                    if(matrix[row_r][col_l] == target) return true;
                    break;
                }
            }
            for(int i = col_r ; i >= col_l ; i--){
                if(matrix[row_l][col_r] > target){
                    col_r--;
                    if(col_r == -1) return false;
                }else{
                    if(matrix[row_l][col_l] == target) return true;
                    break;
                }
            }
        }
        if(matrix[row_r][col_r] == target) {
            return true;
        }else{
            return false;
        }
    }

    //答案思路：走一条路 ，分治,思路和上面一样，速度也一样，但是代码简洁了很多
    public static boolean searchMatrix2(int[][] matrix, int target) {
        if(matrix.length == 0) return false;
        int row = matrix.length;
        int col = matrix[0].length;
        int i = 0 ,j = col-1;
        while(i>= 0 && i <row && j>= 0 && j<col){

            if(matrix[i][j] > target) j--;
            else if (matrix[i][j] <target) i++;
            else{
                return true;
            }
        }
        return false;
    }

    //找target 出现了几次
    public static int searchMatrixCount(int[][] matrix, int target) {
        if(matrix.length == 0) return 0;

        int row = matrix.length;
        int col = matrix[0].length;
        int i = 0 ,j = col-1;
        int count =0;
        while(i>= 0 && i <row && j>= 0 && j<col){

            if(matrix[i][j] > target) j--;
            else if (matrix[i][j] <target) i++;
            else{
                count++;
                if(i+1 <row) i++;
                else return count;
            }
        }
        return count;
    }
}

//合并有序数组
class Solution4 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
           int [] result = new int[m+n];

           int index1 = 0;
           int index2 = 0;
           int index3 = 0;
           while( index1<m && index2<n){

               if(nums1[index1] < nums2[index2]){
                   result[index3] = nums1[index1];
                   index1++;

               }else{
                   result[index3] = nums2[index2];
                   index2++;
               }
               index3++;
           }
           //补全
           if(index1<m) {
               for( ;index1<m;index1++ ){
                   result[index3]= nums1[index1];
                   index3++;
               }
           }

        if(index2<m) {
            for( ;index2<n;index2++ ){
                result[index3]= nums2[index2];
                index3++;
            }
        }

        System.arraycopy(result,0,nums1,0,m+n);
    }
}

//扔鸡蛋测楼层 不是二分。。。。是动态规划。。。。。
class Solution5 {
    public int superEggDrop(int K, int N) {

        //思路1：  不会写N-k
        //dp[i][j] 表示 第i层 消耗j个鸡蛋能探测的最坏情况下的步数 ,若鸡蛋碎了,下一层，dp[i][j] = dp[i-1][j-1]+1 ；鸡蛋没碎，上一层 dp[i][j] = dp[N-i][j]+1
        //dp[n][k] = min(max(dp[i-1][k-1], dp[n-i][k]) + 1 )  1<= i <= n  第一个鸡蛋砸在哪一层
//        int dp[][] = new int[N+1][K+1];
//
//        for(int i = 0 ; i<= N ; i++){
//            dp[i][0] = i;
//        }
//
//        for(int i = 0; i <=N ;i++){
//            for(int j = 0 ; j<=K ;j++){
//                dp[i][j] = Math.max(dp[i-1][j]+1,dp[N-i][j]+1);   //n-i 怎么处理 不会
//            }
//        }

        //思路2：
        // dp[k][m] 表示k个鸡蛋在m步内最多能测出的层数。
        //如果鸡蛋没碎，我们接下来会在更高的楼层扔，最多能确定 X + dp[k][m-1] 层的结果；
        //如果鸡蛋碎了，我们接下来会在更低的楼层扔，最多能确定 Y + dp[k-1][m-1]层的结果 (假设在第X层上还有Y层)。
        //因此，这次扔鸡蛋，我们最多能测出 dp[k-1][m-1](摔碎时能确定的层数) + dp[k][m-1] (没摔碎时能确定的层数) + 1 (本层) 层的结果。
        //为什么+ ，因为要取最多，这里和你在哪一层楼仍是无关的
        if(K==0) return 0;
        if(K==1) return N;
        int dp[][] = new int[K+2][N+2];
        dp[0][0] = 0;
        for(int i = 0 ; i<= K ;i++){  //dp[k][0] = 0
            dp[i][0] = 0;
        }
        for(int i = 1 ; i<=N ;i++){ //dp[1][m] = m (m > 0)
            dp[1][i] = i;
        }

        for(int i = 1 ; i<= N ;i++){
            for(int j = 1 ; j<=K ;j++){
                dp[j][i] = dp[j-1][i-1] +dp[j][i-1]+1;
                if(dp[j][i] >= N) {
                    return i;
                }
            }
        }
        return N;
    }
}