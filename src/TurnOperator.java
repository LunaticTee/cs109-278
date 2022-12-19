import java.util.Scanner;

class TurnOperator {
    //在玩家操作时，如果选择的是反面牌：
    //将其翻面
    static void turn(int x, int y, int[][] state,Var flagdo) {
        Scanner input = new Scanner(System.in);
        System.out.printf("该棋子将翻面\n");
        System.out.println("确定要将它翻面吗？输入0确定，输入-1重新选择棋子");
        int n = input.nextInt();
        if(n==0){
            state[x][y] = 1;
            flagdo.setFlagdo(0);
        }
        if(n==-1){
            flagdo.setFlagdo(1);
        }
    }
}
