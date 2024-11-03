class Solution {
    int[][] dirs = new int[][]{{-1,0}, {1,0}, {0,-1}, {0,1}};
    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.add(new int[]{0, 0, 0, 1});
        int[][] dp = new int[n][m];
        for(int[] arr : dp) {
            Arrays.fill(arr, -1);
        }
        dp[0][0] = 0;
        while(!pq.isEmpty()) {
            int[] top = pq.remove();
            int r = top[0];
            int c = top[1];
            int add = top[3];
            for(int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                if(nr < 0 || nc < 0 || nr == n || nc == m) continue;
                int cost = Math.max(moveTime[nr][nc], top[2]) + add;
                if(nr == n-1 && nc == m-1) return cost;
                if(dp[nr][nc] == -1 || dp[nr][nc] > cost) {
                    dp[nr][nc] = cost;
                    pq.add(new int[]{nr, nc, cost, (top[3] == 1) ? 2 : 1});
                }
            }
        }
        return -1;
    }
}
