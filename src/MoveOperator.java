import java.util.Scanner;

class MoveOperator {
    //在玩家操作时，如果选择的是正面牌且不是炮：
    //允许玩家将选择的棋子移动到上下左右四个位置中合法的位置之一
    //重置所有棋盘
    public static void move(int x, int y, int[][] board, int[][] state, Var flagdo) {
        int r = 0;
        Scanner input = new Scanner(System.in);
        showMove(x,y,board,state);
        System.out.println("请输入一个你要吃掉的棋子或移动的位置,或输入-1以重新选择坐标：");
        do {
            int a = input.nextInt();
            if (a == -1) {
                flagdo.setFlagdo(1);
                return;
            }
            int b = input.nextInt();
            if ((a != 0 && a != 1 && a != 2 && a != 3 && a != 4 && a != 5 && a != 6 && a != 7) || (b != 0 && b != 1 && b != 2 && b != 3)) {
                System.out.println("这不是棋盘中的位置！请重新输入或输入-1以重新选择坐标：");
                r = 1;
                continue;
            }
            if (!MoveJudge.mj(x, y, a, b, board, state)) {
                System.out.println("这不是一个合法的位置！请重新输入或输入-1以重新选择坐标：");
                r = 1;

            }
            if (MoveJudge.mj(x, y, a, b, board, state)) {
                board[a][b] = board[x][y];
                board[x][y] = 100;
                r = 0;
                flagdo.setFlagdo(0);

            }

        } while (r == 1);
    }

    public static void showMove(int x, int y, int[][] board,int[][] state){
        System.out.println("以下是可供选择的位置：");
        for(int i = 0;i<8;i++){
            for(int j = 0;j<4;j++) {
                if(MoveJudge.mj(x,y,i,j,board,state)){
                    System.out.println(i+" "+j);
                }
            }
        }
    }
}
