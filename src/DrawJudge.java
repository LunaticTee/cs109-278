public class DrawJudge {
    public static boolean dj(int[][] board, int[][] state, int c, int round) {
        int r = 0;
        int z = 0;
        int x1 = -1;
        int x2 = -1;
        int y1 = -1;
        int y2 = -1;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                if (state[x][y] == 0) {
                    r++;
                }
                if (state[x][y] == 1 && Math.abs(board[x][y]) == 1) {
                    z++;
                    if (z == 1) {
                        x1 = x;
                        y1 = y;
                    }
                    if (z == 2) {
                        x2 = x;
                        y2 = y;
                    }
                }

            }
        }
        if (r == 0 && z == 2 && Math.abs(x1 - x2) + Math.abs(y1 - y2) != 1) {
            return (false);
        } else {
            return (true);
        }
    }
}
