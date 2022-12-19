import java.util.Scanner;

class CannonOperator {
    //在玩家操作时，如果选择的是正面牌且是炮：
    //执行炮特有的合法操作
    public static void cannon(int x, int y, int[][] board, int[][] state, Var flagdo) {
        int r = 0;
        Scanner input = new Scanner(System.in);
        showCannon(x,y,board,state);
        System.out.println("请输入一个你要吃掉的棋子或移动的位置,或输入-1以重新选择坐标：");
        do {
            int a = input.nextInt();
            if (a == -1) {
                flagdo.setFlagdo(1);
                return;
            }
            int b = input.nextInt();
            if (!CannonJudge.cj(x, y, a, b, board, state)) {
                System.out.println("这不是一个合法的位置！请重新输入或输入-1以重新选择坐标：");
                r = 1;

            }
            if (CannonJudge.cj(x, y, a, b, board, state)) {
                int k = board[a][b];
                int s = state[a][b];
                board[a][b] = board[x][y];
                state[a][b] = 1;
                board[x][y] = 100;
                flagdo.setFlagdo(0);
                if (s == 0) {
                    System.out.printf("*被吃掉的棋子是%s\n", Convert.convert(k));
                }
                r = 0;

            }

        } while (r == 1);
    }
    public static void showCannon(int x,int y,int[][] board,int[][] state){
        System.out.println("以下是可供选择的位置：");
        for(int i = 0;i<8;i++){
            for(int j = 0;j<4;j++) {
                if(CannonJudge.cj(x,y,i,j,board,state)){
                    System.out.println(i+" "+j);
                }
            }
        }
    }
}
