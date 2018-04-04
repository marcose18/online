import java.io.*;
import java.util.*;

public class CF1 {
    static ArrayList<Integer>edges[];
    static ArrayList<Integer>topics[];
    static int array[][];
    static ArrayList<Integer>list;
    static boolean matrix[][];
    static int prev;
    static void dfs(int idx, boolean visited[], int fix){
        if(visited[idx])return;
//        System.out.println(idx);
        visited[idx] = true;
        int max = 0, pos = -1;
        for(int child: edges[idx]){
            if(array[child][1] > max && !visited[child]){
                max = array[child][1];
                pos = child;
            }
        }
        if(pos != -1)
            dfs(pos, visited, fix);
//        System.out.println(idx);
        list.add(idx);
        if(matrix[idx][fix] && prev == -1) {
            prev = idx;
//            System.out.println(prev + " " + idx + " " + matrix[idx][fix] + " " + fix);
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        edges = new ArrayList[n + 1];
        topics = new ArrayList[n + 1];
        array = new int[n + 1][2];
        for(int i = 1;i <= n;i++){
            edges[i] = new ArrayList<>();
            topics[i] = new ArrayList<>();
        }
        matrix = new boolean[n + 1][n + 1];
        for(int i = 1;i <= n;i++) {
            st = new StringTokenizer(br.readLine());
            int q = Integer.parseInt(st.nextToken());
            for (int j = 1; j <= q; j++) {
                int a = Integer.parseInt(st.nextToken());
                topics[i].add(a);
            }
            st = new StringTokenizer(br.readLine());
            array[i][0] = Integer.parseInt(st.nextToken());
            array[i][1] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            for (int j = 1; j <= o; j++) {
                int a = Integer.parseInt(st.nextToken());
                edges[i].add(a);
                matrix[i][a] = true;
            }
        }
            int freq[] = new int[k + 1];
            st = new StringTokenizer(br.readLine());
            for(int i = 1;i <= k;i++)
                freq[i] = Integer.parseInt(st.nextToken());
            Node order[] = new Node[n + 1];
            for(int i = 1;i <= n;i++) {
                int tot = 0;
                for (int idx : topics[i])
                    tot += freq[idx];
                double temp = array[i][1];
                order[i] = new Node(i, temp);
            }
            order[0] = new Node(-1, -100000.0);
            Arrays.sort(order);
            boolean visited[];
            HashSet<Integer>set = new HashSet<>();
            HashSet<Integer>indexed = new HashSet<>();
            int max = 0;
            for(int i = 0;i < n && r > 0;i++){
                prev = -1;
                list = new ArrayList<>();
                visited = new boolean[n + 1];
//                System.out.println(order[i].x + " " + order[i].y + " " + i);
                dfs(order[i].x, visited, order[i].x);
                int ctr = 0;
                for(int j = 0;j < list.size();j++)
                    if(list.get(j) == prev)
                        ctr = j;
                int distinct = 0;
                for(int j = list.size() - 1;j >= ctr;j--) {
//                    System.out.print(list.get(j) + " ");
                    for(int idx: topics[list.get(j)])
                        if(set.contains(idx))
                            continue;
                        else ++distinct;
                }
//                System.out.println(ctr + " " + list + " " + set + " " + distinct);
                if(distinct == 0 && array[order[i].x][0]*(list.size() - ctr) >= max)continue;
                max = Math.max(max, array[order[i].x][0]*(list.size() - ctr));
                System.out.print((list.size() - ctr) + " ");
                indexed.add(i);
                for(int j = list.size() - 1;j >= ctr;j--) {
                    System.out.print(list.get(j) + " ");
                    for(int idx: topics[list.get(j)]) {
                        set.add(idx);
//                        System.out.println(list.get(j) + " " + set + " " + topics[list.get(j)] + " " + "1111");
                    }
                }
                System.out.println();
                --r;
            }
            while(r > 0){
                --r;
                for(int j = 0;j < n;j++){
                    if(!indexed.contains(j)){
                        System.out.println("1 " + j);
                        indexed.add(j);
                        break;
                    }
                }
            }
//            for(int i = 0;i < r;i++){
//                System.out.println("1 " + order[i].x);
//            }
        }
    static class Node implements Comparable<Node>{
        int x;
        double y;
        Node(int x, double y){
            this.x = x;
            this.y = y;
        }
        public int compareTo(Node other){
            return Double.compare(other.y, y);
        }
    }
}

