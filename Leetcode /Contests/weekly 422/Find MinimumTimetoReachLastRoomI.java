class Pair {
    int x, y, time;
    public Pair(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
class Solution {
    int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        int[][] dist = new int[n][m];
        for(int[] d : dist) {
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.time, b.time));
        pq.add(new Pair(0, 0, 0));
        dist[0][0] = 0;
        while(!pq.isEmpty()) {
            Pair top = pq.poll();
            int x = top.x;
            int y = top.y;
            int time = top.time;
            if(time > dist[x][y]) continue;
            for(int[] d : dirs) {
                int nx = x + d[0];
                int ny = y + d[1];
                if(nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    int nt = 1 + Math.max(time, moveTime[nx][ny]);
                    if(nt < dist[nx][ny]) {
                        dist[nx][ny] = nt;
                        pq.offer(new Pair(nx, ny, nt));
                    }
                }
            }
        }
        return dist[n-1][m-1];
    }
}
