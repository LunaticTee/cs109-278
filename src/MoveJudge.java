class MoveJudge {
    //判断非炮选择情况下的合法位置
    public static boolean mj(int x, int y, int a, int b, int[][] board, int[][] state) {
        boolean r = false;
        if (Math.abs(a - x) + Math.abs(b - y) == 1 && state[a][b] == 1) {
            if (board[a][b] == 100) {
                r = true;
            }
            if (board[a][b] != 100 && board[x][y] * board[a][b] < 0) {
                if (Math.abs(board[a][b]) == 1) {
                    if (Math.abs(board[x][y]) == 6 ||Math.abs(board[x][y]) == 1) {
                        r = true;
                    }
                }
                if (Math.abs(board[a][b]) == 7 && Math.abs(board[x][y]) != 6) {
                    r = true;
                }

                if (Math.abs(board[x][y]) <= Math.abs(board[a][b])) {
                    r = true;

                }

            }

        }

        return (r);
    }

}
