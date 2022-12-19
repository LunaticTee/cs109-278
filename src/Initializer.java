import java.util.ArrayList;
import java.util.Random;

class Initializer {

    public static void Init(int[][] board, int[][] state, ArrayList<int[][]> BL,ArrayList<int[][]> SL) {
        //随机数组
        int[] random = new int[32];
        random[0] = 1;
        random[1] = 2;
        random[2] = 2;
        random[3] = 3;
        random[4] = 3;
        random[5] = 4;
        random[6] = 4;
        random[7] = 5;
        random[8] = 5;
        random[9] = 6;
        random[10] = 6;
        random[11] = 6;
        random[12] = 6;
        random[13] = 6;
        random[14] = 7;
        random[15] = 7;
        for (int i = 16; i < 32; i++) {
            random[i] = -random[i - 16];
        }
        Random r = new Random();
        for (int i = 0; i < random.length; i++) {
            int r1 = r.nextInt(random.length);
            int temp = 100;
            temp = random[i];
            random[i] = random[r1];
            random[r1] = temp;
        }
        //1.初始化虚拟棋盘

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = random[4 * i + j];
            }
        }

        //2.初始化状态棋盘


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = 0;
            }
        }

        //3.初始化公示棋盘

        if (Var.getMode() == 0) {
            Monitor_NM.nm(board, state);
        }
        if (Var.getMode() == 1) {
            Monitor_CM.cm(board, state);
        }

        //4.初始化分数
        System.out.println("红色方的分数为：0");
        System.out.printf("黑色方的分数为：0\n");
        //5.初始化棋谱
        BL.clear();
        SL.clear();
        BoardList.setBoardlist(board,state,BL,SL);



    }



}
