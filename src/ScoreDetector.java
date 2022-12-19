public class ScoreDetector {

    //算红方的分数
    public static int scoreRed(int[][] board) {
        int r = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] < 0) {
                    int s = board[i][j];
                    r = r + Score.score(s);
                }
            }
        }
        return (95 - r);
    }

    //算黑方的分数
    public static int scoreBlack(int[][] board) {
        int r = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] > 0 && board[i][j] != 100) {
                    int s = board[i][j];
                    r = r + Score.score(s);
                }
            }
        }
        return (95 - r);
    }


    //如果一个棋盘的剩余分数小于等于0，宣布胜者并结束程序
}
