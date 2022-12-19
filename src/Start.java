import java.util.ArrayList;
import java.util.Scanner;

class Start {
    public static int global = 0;
    //给玩家选择：新游戏、加载棋盘、退出游戏三个选项，若玩家选择新游戏或加载棋盘，给玩家选择：NM或CM两个选项
    public static void StartGame(int[][] board, int[][] state, int[][] pub, Var mode, ArrayList<int[][]> BL, ArrayList<int[][]> SL,int round,int c) {
        mode(mode);
        Initializer.Init(board, state, BL, SL);
        round = 2;
    }
    public static void StartGameUI(int[][] board, int[][] state, Var mode, ArrayList<int[][]> BL, ArrayList<int[][]> SL) {

        mode(mode);
        Initializer.Init(board, state, BL, SL);
        DarkChess.c = 0;
        DarkChess.round = 2;
        Var.d = 1;
        View.frame.setVisible(true);
        View.textArea.setText("第1回合开始");
        View.redraw(board, state);
    }
    public static void StartGameAI(int[][] board, int[][] state, int[][] pub, Var mode, ArrayList<int[][]> BL, ArrayList<int[][]> SL,int round,int c) {
        Initializer.Init(board, state, BL, SL);
        DarkChess.c = 0;
        DarkChess.round = 2;
        Var.d = 1;
        View.frame.setVisible(true);
        View.textArea.setText("第1回合开始");
        View.redraw(board, state);
    }


    public static void LoadGame(ArrayList<int[][]> BL, ArrayList<int[][]> SL) {
        int k = 0;
        ArrayList<int[][]> L = SaveLoad.load(k);
        if(k==1){
            return;
        }
        for (int i = 0; i < L.size() / 2; i++) {
            BL.add(L.get(i));
        }
        for (int i = L.size() / 2; i < L.size(); i++) {
            SL.add(L.get(i));
        }
        int[][] S0 = SL.get(1);
        for (int l = 0; l < BL.size(); l++) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 4; j++) {
                    if (S0[i][j] == 1) {
                        DarkChess.c = BL.get(l)[i][j];
                    }
                }
            }
        }
        DarkChess.round = BL.size() + 1;
        int[][] board = BL.get(BL.size() - 1);
        int[][] state = SL.get(SL.size() - 1);
        Var mode = new Var();
        mode(mode);
        Monitor_NM.nm(board, state);
        Var.d = 1;
        View.frame.setVisible(true);
        View.redraw(board, state);
    }

    public static void mode(Var mode) {
        System.out.println("输入0开始普通模式，输入1开始作弊模式");
        Scanner input = new Scanner(System.in);
        int m = input.nextInt();
        mode.setMode(m);
        if (mode.getMode() == 0) {
            System.out.println("您选择了普通模式，您将不能查看翻面的棋子");
        }
        if (mode.getMode() == 1) {
            System.out.println("您选择了作弊模式，您可以在每次操作结束后查看翻面的棋子");
        }
        System.out.println("游戏即将开始......");
    }


}
