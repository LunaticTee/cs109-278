public class ColorJudge {
    public static boolean cj(int x, int y, int c, int round, int[][] board) {
        boolean r = true;
        if (round % 2 == 1) {
            if (board[x][y] * c > 0) {
                r = false;
            }
        }
        if (round % 2 == 0) {
            if (board[x][y] * c < 0) {
                r = false;
            }
        }
        return (r);
    }
}
