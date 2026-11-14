import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int[][] exitDistance(char[][] plan) {
        if (plan == null || plan.length == 0) {
            return new int[0][0];
        }
        
        int rows = plan.length;
        int cols = plan[0].length;
        int[][] result = new int[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (plan[i][j] == 'E') {
                    result[i][j] = 0;
                    queue.offer(new int[]{i, j});
                } else if (plan[i][j] == '#') {
                    result[i][j] = -1;
                } else {
                    result[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && result[nr][nc] == Integer.MAX_VALUE) {
                    result[nr][nc] = result[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (result[i][j] == Integer.MAX_VALUE) {
                    result[i][j] = -1;
                }
            }
        }
        
        return result;
    }
}
