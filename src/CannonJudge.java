public class CannonJudge {
    public static boolean cj(int x, int y, int a, int b, int[][] board, int[][] state) {
        boolean r = false;
        if (board[a][b] != 100 && Math.abs(x - a) + Math.abs(y - b) >= 1) {
            if (x == a) {
                int s = 0;
                for (int i = 1; i <= Math.abs(b - y); i++) {
                    if (board[x][Math.min(b, y) + i] != 100) {
                        s++;
                    }
                }
                if ((s == 2 && state[a][b] == 1 && board[x][y] * board[a][b] < 0) || (s == 2 && state[a][b] == 0)) {
                    r = true;
                }
            }
            if (y == b) {
                int s = 0;
                for (int i = 1; i <= Math.abs(a - x); i++) {
                    if (board[Math.min(a, x) + i][y] != 100) {
                        s++;
                    }
                }
                if ((s == 2 && state[a][b] == 1 && board[x][y] * board[a][b] < 0) || (s == 2 && state[a][b] == 0)) {
                    r = true;
                }
            }
        }


        return (r);
    }
}
