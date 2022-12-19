import java.util.Random;

public class AI {
    public static void randomChoose(int[][] board, int[][] state) {
        int[] v = {0, 1, 2, 3, 4, 5, 6, 7};
        int[] h = {0, 1, 2, 3};
        Random r1 = new Random();
        for (int i = 0; i < 8; i++) {
            int index = r1.nextInt(8);
            int temp = v[index];
            v[index] = v[i];
            v[i] = temp;
        }
        Random r2 = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r2.nextInt(4);
            int temp = h[index];
            h[index] = h[i];
            h[i] = temp;
        }
        int x = -1;
        int y = -1;
        define:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (randomChooseJudge(v[i], h[j], board, state)) {
                    x = v[i];
                    y = h[j];
                    break define;
                }
            }
        }
        if (state[x][y] != 0 && board[x][y] != 7) {
            randomChooseMove(x, y, board, state);
        }
        if (state[x][y] != 0 && board[x][y] == 7) {
            randomChooseCannon(x, y, board, state);
        }
        if (state[x][y] == 0) {
            randomChooseTurn(x, y, board, state);
        }

    }


    public static boolean randomChooseJudge(int x, int y, int[][] board, int[][] state) {
        boolean r = false;
        int n = 0;
        if (state[x][y] == 0) {
            n = 1;
        }
        if (Math.abs(board[x][y]) != 7) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (MoveJudge.mj(x, y, i, j, board, state)) {
                        n = 1;
                    }
                }
            }
        }
        if (Math.abs(board[x][y]) == 7) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (CannonJudge.cj(x, y, i, j, board, state)) {
                        n = 1;
                    }
                }
            }
        }
        if ((state[x][y]==1&&board[x][y] * DarkChess.c > 0)||(state[x][y]==1&&board[x][y]==100)) {
            n = 0;
        }
        if (n == 1) {
            r = true;
        }
        return r;
    }


    public static void randomChooseTurn(int x, int y, int[][] board, int[][] state) {
        state[x][y] = 1;
    }

    public static void randomChooseMove(int x, int y, int[][] board, int[][] state) {
        int[] v = {0, 1, 2, 3, 4, 5, 6, 7};
        int[] h = {0, 1, 2, 3};
        Random r1 = new Random();
        for (int i = 0; i < 8; i++) {
            int index = r1.nextInt(8);
            int temp = v[index];
            v[index] = v[i];
            v[i] = temp;
        }
        Random r2 = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r2.nextInt(4);
            int temp = h[index];
            h[index] = h[i];
            h[i] = temp;
        }
        define:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if ((MoveJudge.mj(x, y, v[i], h[j], board, state))) {
                    board[v[i]][h[j]] = board[x][y];
                    board[x][y] = 100;
                    break define;
                }
            }
        }
    }

    public static void randomChooseCannon(int x, int y, int[][] board, int[][] state) {
        int[] v = {0, 1, 2, 3, 4, 5, 6, 7};
        int[] h = {0, 1, 2, 3};
        Random r1 = new Random();
        for (int i = 0; i < 8; i++) {
            int index = r1.nextInt(8);
            int temp = v[index];
            v[index] = v[i];
            v[i] = temp;
        }
        Random r2 = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r2.nextInt(4);
            int temp = h[index];
            h[index] = h[i];
            h[i] = temp;
        }
        define:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if ((CannonJudge.cj(x, y, v[i], h[j], board, state))) {
                    int k = board[v[i]][h[j]];
                    int s = state[v[i]][h[j]];
                    board[v[i]][h[j]] = board[x][y];
                    state[v[i]][h[j]] = 1;
                    board[x][y] = 100;
                    if (s == 0) {
                        System.out.printf("*被吃掉的棋子是%s\n", Convert.convert(k));
                    }
                    break define;
                }
            }
        }
    }


    public static int calculateScore(int x, int y, int[][] board, int[][] state) {
        int s = 0;
        if (state[x][y] == 0) {
            for (int i = Math.max(0, x - 1); i <= Math.min(7, x + 1); i++) {
                for (int j = Math.max(0, y - 1); j <= Math.min(3, y + 1); j++) {
                    if (i != x && j != y && state[i][j] == 1) {
                        s++;
                    } else {
                        if (state[i][j] == 1) {
                            s--;
                        }
                    }
                }
            }
        }
        if (state[x][y] == 1 && Math.abs(board[x][y]) != 7) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (MoveJudge.mj(x, y, i, j, board, state) && board[i][j] != 100) {
                        s = Math.max(s,Score.score(board[i][j]));
                    }
                    if (MoveJudge.mj(x, y, i, j, board, state) && board[i][j] == 100) {
                        int[][] tM = new int[8][4];
                        int t = 0;
                        for(int x1 = 0; x1 < 8;x1++){
                            for(int y1 = 0;y1<4;y1++){
                                if(MoveJudge.mj(i,j,x1,y1,board,state)){
                                    tM[x1][y1] = Score.score(board[x1][y1])/2;
                                    t = Math.max(t,tM[x1][y1]);
                                }
                            }
                        }
                        s = Math.max(s,t);
                    }
                }
            }
        }
        if (state[x][y] == 1 && Math.abs(board[x][y]) == 7) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (CannonJudge.cj(x, y, i, j, board, state) && state[i][j] == 0) {
                        s = Math.max(s, 5);
                    }
                    if (CannonJudge.cj(x, y, i, j, board, state) && state[i][j] == 1) {
                        s = Math.max(s, Score.score(board[i][j])+1);
                    }
                }
            }
        }

        return s;
    }

    public static void AIChoose(int[][] board, int[][] state) {
        int[][] score = new int[8][4];
        int s0 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (!randomChooseJudge(i, j, board, state)) {
                    score[i][j] = -100;
                }
                if (randomChooseJudge(i, j, board, state)) {
                    score[i][j] = calculateScore(i, j, board, state);
                    s0 = Math.max(s0, calculateScore(i, j, board, state));
                }
            }
        }
        int x = -1;
        int y = -1;
        define:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if(score[i][j]==s0){
                    x = i;
                    y = j;
                    break define;
                }
            }
        }
        //------------------------------------------------------------------
        if(state[x][y]==1&& Math.abs(board[x][y]) != 7) {
            int[][] sM = new int[8][4];
            int m = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (MoveJudge.mj(x, y, i, j, board, state) && board[i][j]!=100) {
                        sM[i][j] = Score.score(board[i][j]);
                        m = Math.max(m,sM[i][j]);
                    }
                    else{
                        sM[i][j] = 0;
                    }
                }
            }
            int x1 = -1;
            int y1 = -1;
            Define:
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if(sM[i][j]==m){
                        x1 = i;
                        y1 = j;
                        break Define;
                    }
                }
            }
            board[x1][y1]=board[x][y];
            board[x][y]=100;
        }
        //---------------------------------------------------------------------------------
        if(state[x][y]==1&& Math.abs(board[x][y]) == 7) {
            int[][] sM = new int[8][4];
            int m = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (CannonJudge.cj(x, y, i, j, board, state) && state[i][j] == 0) {
                        sM[i][j] = 5;
                        m = Math.max(m,5);
                    }
                    if (CannonJudge.cj(x, y, i, j, board, state) && state[i][j] == 1) {
                        sM[i][j] = Score.score(board[i][j])+1;
                        m = Math.max(m,sM[i][j]);
                    }
                }
            }
            int x1 = -1;
            int y1 = -1;
            Define:
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if(sM[i][j]==m){
                        x1 = i;
                        y1 = j;
                        break Define;
                    }
                }
            }
            int k = board[x1][y1];
            int s = state[x1][y1];
            board[x1][y1] = board[x][y];
            state[x1][y1] = 1;
            board[x][y] = 100;
            if (s == 0) {
                System.out.printf("*被吃掉的棋子是%s\n", Convert.convert(k));
            }
        }
        //------------------------------------------------------------------------------
        if(state[x][y]==0){
            state[x][y]=1;
        }
    }

}
