class Board {
    public static void show(int[][] board, int[][] state) {
        //虚拟棋盘[存储当前棋盘的所有棋子类型或无棋子状态]
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%d  ", board[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");


        //状态棋盘[仅存储三种棋子状态]
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%d  ", state[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");


        //公示棋盘[存储公示性质的状态]

        //分数棋盘A[存储棋子A的分数或在无棋子时标0]
        //分数棋盘B[存储棋子B的分数或在无棋子时标0]

    }
}
