package sequence;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class FourTo500 {

    //452 用最少数量的箭引爆气球

    public int findMinArrowShots(int[][] points) {
        if(points.length <= 1) return 1;
        Arrays.sort(points,new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0]) return -1;
                else if (o1[0] == o2[0]) {
                    if(o1[1] < o2[1]) return -1;
                    else return 1;
                } else return 1;
            }
        });
        int arrow = 1;
        int end = points[0][1];
        for(int i = 1; i < points.length ;i++){
            if(points[i][0] <= end){
                end = Math.min(end,points[i][1]);
            }else{
                arrow++;
                end = points[i][1];
            }
        }
        return arrow;
    }


    //472 连接词
    //返回所有连接词： 连接词的定义为：一个字符串完全是由至少两个给定数组中的单词组成的。
    //字典树
    class TrieNode {
        private boolean isWord; //表示这个节点是否一个单词的结尾
        private Map<Character, TrieNode> next;
        public TrieNode() {
            this(false);
        }
        public TrieNode(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }
    }
    class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void add(String word) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (cur.next.get(c) == null) {
                    cur.next.put(c, new TrieNode());
                }
                cur = cur.next.get(c);
            }
            if (!cur.isWord) {
                cur.isWord = true;
            }
        }

        public boolean search(String word) {
            TrieNode cur = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (cur.next.get(c) == null) {
                    return false;
                }
                cur = cur.next.get(c);
            }
            return cur.isWord;
        }
        //index为开始检查的起始索引 count为isWord为true的次数 即在这条路径中有几个单词
        public boolean checkIsConcatenatedWord(String word, int index, int count) {
            TrieNode cur = root;
            for (int i = index; i < word.length(); i++) {
                char c = word.charAt(i);
                if(cur.next.get(c) == null) { //如果字典树中没有c这条路径 直接返回false
                    return false;
                }
                if (cur.next.get(c).isWord) {
                    if (i == word.length() - 1) {//如果已经到达word的尾部并且word这里是单词的结果 则看计数count是否大于1
                        return count >= 1;
                    }
                    if (checkIsConcatenatedWord(word, i + 1, count + 1)) { //如果没有到达尾部 则从index+1的位置继续检查 count计数器加1
                        return true;
                    }
                }
                cur = cur.next.get(c);
            }
            return false;
        }
    }
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Trie trie = new Trie();
        List<String> res = new ArrayList<>();
        if (words.length < 3) return res;
        for (String word : words) trie.add(word);
        for (String word : words){
            if (trie.checkIsConcatenatedWord(word, 0, 0)) {
                res.add(word);
            }
        }
        return res;
    }

    //LFU缓存  最近最不常使用
    class LFUCache {
        class Node implements Comparable<Node>{
            int key , value , times ,clock ;
            public Node(int key, int value, int times, int clock) {
                this.key = key;
                this.value = value;
                this.times = times;
                this.clock = clock;
            }
            @Override
            public int compareTo(Node o) {
                int diff = this.times  - o.times ;
                return diff == 0 ? this.clock - o.clock : diff;
            }
        }
        int GLOBAL_CLOCK = 0;
        int size ;
        int capacity ;
        PriorityQueue<Node> queue;
        Map<Integer,Node> map ;
        public LFUCache(int capacity) {
            if(capacity <= 0) return ;
            this.capacity = capacity;
            size = 0;
            queue = new PriorityQueue<>(size);
            map = new HashMap<>();
        }
        public int get(int key){
            if(capacity<= 0) return -1;
            if(size <= 0 || map.get(key) == null) return -1;
            Node node = map.get(key);
            node.times++;
            node.clock = ++GLOBAL_CLOCK;
            queue.remove(node);
            queue.add(node);
            map.put(key,node);
            return node.value;
        }
        public void put(int key, int value) {
            if(capacity <= 0) return ;
            if(!map.containsKey(key)){
                Node node = new Node(key,value,1,++GLOBAL_CLOCK);
                if(size == capacity){
                    Node lastNode = queue.poll();
                    map.remove(lastNode.key);
                }else{
                    size++;
                }
                map.put(key,node);
                queue.add(node);
            }else{
                Node curNode = map.get(key);
                queue.remove(curNode);
                curNode.times++;
                curNode.clock = ++ GLOBAL_CLOCK;
                curNode.value = value;
                map.put(key,curNode);
                queue.add(curNode);
            }
        }
    }


}
