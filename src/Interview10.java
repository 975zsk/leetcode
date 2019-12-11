import java.util.*;
//单词接龙
class Solution10_1 {

    //dfs 超时
    public int result = Integer.MAX_VALUE;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        Map<String,Integer> map = new HashMap<>();
        for(String str : wordList) map.put(str,1);
        map.put(beginWord,1);
        if(!map.containsKey(endWord)) return 0;
         dfs(beginWord,endWord,map,0);
         if(result > map.size()) return 0;
         return result;
    }
    public void dfs(String beginWord,String endWord,Map<String,Integer> map,int len){

        if(beginWord.equals(endWord)) {
            result = Math.min(len+1,result);
        }
        String temp = beginWord;
        map.put(temp,0);
        for(String str:map.keySet()){
            if(map.get(str)!= 0 && canTrans(temp,str)){
                dfs(str,endWord,map,len+1);
            }
        }
        map.put(temp,1);
    }
    public boolean canTrans(String a,String b){
        int count = 0;
        for(int i = 0; i<a.length() ;i++){
            if(a.charAt(i)!= b.charAt(i)) count++;
        }
        if(count==1) return true;
        else return false;
    }

    //答案的bfs 115ms
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        Set<String> visited = new HashSet<>();  //上一层的queue
        visited.add(beginWord);
        int dist = 1;
        while (!visited.contains(endWord)) {
            Set<String> temp = new HashSet<>();  //这一层的queue
            for (String word: visited) {
                for (int i = 0; i < word.length(); i++) {
                    char[] chars = word.toCharArray();
                    for (int j = (int)'a'; j < (int)'z' + 1; j++) {
                        chars[i] = (char)j;
                        String newWord = new String(chars);    //因为测试用例的每个单词都比较短，所以遍历所以新单词的可能性，判断在不在词典中
                        if (wordSet.contains(newWord)) {
                            temp.add(newWord);
                            wordSet.remove(newWord);  //保证不重复遍历
                        }
                    }
                }
            }
            dist += 1;
            if (temp.size() == 0) {
                return 0;
            }
            visited = temp;
        }
        return dist;
    }
   //自己的bfs 超时
    public int ladderLength3(String beginWord, String endWord, List<String> wordList) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        int level = 0;
        while (!visited.contains(endWord)){
            if(queue.size() == 0) return 0;

            Queue<String> temp_queue = new LinkedList<>();
            while(!queue.isEmpty()){
                String now_temp_str = queue.poll();
                for(String str : wordList){   //去遍历词典可能会超时
                    if(!visited.contains(str) && canTrans(str,now_temp_str)) temp_queue.add(str);
                }
                visited.add(now_temp_str);
                wordList.remove(now_temp_str);  //还是超时
            }
            queue = temp_queue;
            level++;
        }
        return level;
    }
}

//岛屿数量 bfs
class Solution10_2 {
    int[][] state ={{0,1},{1,0},{0,-1},{-1,0}};
    public int numIslands(char[][] grid) {
        if(grid.length == 0) return 0;
        if(grid[0].length ==0) return 0;
        int number = 0;
        boolean [][]visited = new boolean[grid.length][grid[0].length];
        for(int i = 0; i<grid.length ;i++){
            for(int j = 0 ; j<grid[0].length ;j++){
                if(!visited[i][j] && grid[i][j] =='1') {
                    dfs(grid,i,j,visited);
                    number++;
                }
            }
        }
        return  number;
    }
    public  void dfs(char [][] grid, int i ,int j,boolean[][] visited){
        visited[i][j] = true;
        for(int[] st : state){
            int x = i+st[0];
            int y = j+st[1];
            if(x>= 0 && x<grid.length && y>=0 && y<grid[0].length && grid[x][y] == '1' && !visited[x][y]){
                dfs(grid,x,y,visited);
            }
        }
    }
}

//课程表
class Solution10_3 {
    //自己的答案 361ms  bfs
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer,List<Integer>> map = new HashMap<>();
        boolean[] visited = new boolean[numCourses];
        //统计入度为0;
        for(int i = 0; i<prerequisites.length;i++){
            if(!map.containsKey(prerequisites[i][0])){
                map.put(prerequisites[i][0],new ArrayList<Integer>());
            }
            if(!map.containsKey(prerequisites[i][1])){
                map.put(prerequisites[i][1],new ArrayList<Integer>());
            }
            map.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }
        numCourses = map.size();
        int count = 0;
        while(count <numCourses) {
            int find = 0;
            for (int course : map.keySet()) {
                if (!visited[course] &&map.get(course).size() ==0){
                    visited[course] = true;
                    count++;
                    find ++;
                    //把和其相关的都去掉入度都-1
                    for(int temp :map.keySet()){
                        if(map.get(temp).contains(course)){
                            List<Integer> temp_list= map.get(temp);
                            for(int i = 0; i<temp_list.size();i++){
                                if(temp_list.get(i) == course) {
                                    temp_list.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if(find ==0) return false;
        }
        return true;
    }

    //答案 dfs 8ms  有环就false
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] visited = new int[numCourses]; // 0:未访问;1:已访问;-1:冲突
        // 初始化ArrayList数组
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<Integer>());
        }
        // 用邻接表形式构建有向图
        for (int[] pre : prerequisites) { // [0,1]表示1->0，1为前驱课程 pre[1]为先修课程 pre[0]为完成这门课程后可以学的课程
            graph.get(pre[1]).add(pre[0]);
        }
        // DFS遍历有向图
        for (int i = 0; i < numCourses; i++) {
            if (!canFinishDFS(graph, visited, i)) { // 存在环
                return false;
            }
        }
        return true;
    }
    public boolean canFinishDFS(List<List<Integer>> graph, int[] visited, int i) {
        if (visited[i] == -1) { // 访问过了
            return false;
        }
        if (visited[i] == 1) {  //这个点以后没有环，可以不用访问了。
            return true;
        }
        visited[i] = -1;
        for (int index = 0; index < graph.get(i).size(); index++) {
            int adj = graph.get(i).get(index); // 取每一组List中的各个值
            if (!canFinishDFS(graph, visited, adj)) {
                return false;
            }
        }
        visited[i] = 1;   //这个点以后没有环
        return true;
    }
}

//课程表2  要拓扑排序
class Solution10_4 {
    //自己写的bfs 400ms
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer,List<Integer>> map = new HashMap<>();
        boolean[] visited = new boolean[numCourses];
        int[] result = new int[numCourses];
        int index = 0;
        //统计入度为0;
        for(int i = 0; i<prerequisites.length;i++){
            if(!map.containsKey(prerequisites[i][0])){
                map.put(prerequisites[i][0],new ArrayList<Integer>());
            }
            if(!map.containsKey(prerequisites[i][1])){
                map.put(prerequisites[i][1],new ArrayList<Integer>());
            }
            map.get(prerequisites[i][0]).add(prerequisites[i][1]);
        }
        numCourses = map.size();
        int count = 0;
        while(count <numCourses) {
            int find = 0;
            for (int course : map.keySet()) {
                if (!visited[course] &&map.get(course).size() ==0){
                    visited[course] = true;
                    result[index] =course;
                    index++;
                    count++;
                    find ++;
                    //把和其相关的都去掉入度都-1
                    for(int temp :map.keySet()){
                        if(map.get(temp).contains(course)){
                            List<Integer> temp_list= map.get(temp);
                            for(int i = 0; i<temp_list.size();i++){
                                if(temp_list.get(i) == course) {
                                    temp_list.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if(find ==0) return new int[0];
        }
        //把没有被约束的点放入
        for(int i = 0 ; i<result.length;i++){
            if(!map.containsKey(i)){
                result[index++] = i;
            }
        }
        return result;
    }

    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        //采用bfs进行求解.类比之前的课程表,依然要先把有向图graph做出来,索引是先决课程1,列表中是受该先决课程影响的课程0.
        //bfs需要用一个数组limit表示每门课先决课程的数量,在bfs时,先决课程为0的,先要放在queue中,作为先访问的课程
        //先访问的课程如果是其他课程的先决,被访问后则在limit中应该在对应其他课程上减少先决的数量.当先决为0时,添加进offer.

        //先做好有向图graph.
        ArrayList[] graph = new ArrayList[numCourses];   //这里graph用ArrayList<<ArrayList<>>(),也可以.
        for(int i = 0; i < numCourses; i++){
            graph[i] = new ArrayList<Integer>();  //先把数组中填充空白列表
        }

        int[] limit = new int[numCourses];  //limit先决课程数量
        for(int[] pre : prerequisites){  //填充列表,并且把受控课程的先决数量标记清楚.
            graph[pre[1]].add(pre[0]);
            limit[pre[0]]++;
        }

        return bfs(graph, limit);
    }

    private int[] bfs(ArrayList[] graph, int[] limit){
        int[] order = new int[graph.length];  //要返回的排序数组
        Queue<Integer> queue = new ArrayDeque<>();
        //先把先决课程数量为0的填进queue中,作为层遍历的起始.
        for(int i = 0; i < limit.length; i++){
            if(limit[i] == 0){
                queue.offer(i);
            }
        }

        int index = 0;    //order数组的索引.
        while(!queue.isEmpty()){
            int head = queue.poll();  //开始访问
            order[index++] = head;    //放进数组中

            //更新先决课程数量,并把更新后为数量0的放入queue中.
            for(int i = 0; i < graph[head].size(); i++){   // graph[head]中是被head影响的课程temp;
                int temp = (int)graph[head].get(i); // ##注意要强转成int,并且ArrayList不能被强转,得get
                limit[temp]--;
                if(limit[temp] == 0){   //影响temp的先决课程为0个,则可以上temp课
                    queue.offer(temp);
                }
            }
        }

        return index == limit.length ? order : new int[]{};
    }
}
public class Interview10 {
    public static void main(String[] args) {
//        Solution10_1 so = new Solution10_1();
//        List<String> list = new ArrayList<>();
//        list.add("hot");
//        list.add("dot");
//        list.add("dog");
//        list.add("lot");
//        list.add("log");
//        list.add("cog");
//        so.ladderLength("hit","cog",list);

//            Solution10_2 so  = new Solution10_2();
//            char [][] grids= {
//                { '1','1','1','1','0'},
//                { '1','1','0','1','0'},
//                { '1','1','0','0','0'},
//                { '0','0','0','0','0'}
//            };
//            so.numIslands(grids);

        Solution10_4 so = new Solution10_4();
        int[][] nums = {
        };
        so.findOrder(2,nums);
    }
}
