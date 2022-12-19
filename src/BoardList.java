import java.util.ArrayList;

public class BoardList {
    public static void setBoardlist(int[][] board, int[][] state, ArrayList BL,ArrayList SL){
        int[][] B = new int[8][4];
        int[][] S = new int[8][4];
        for(int i = 0;i<8;i++){
            for(int j=0;j<4;j++){
                B[i][j]=board[i][j];
                S[i][j]=state[i][j];
            }
        }
        BL.add(B);
        SL.add(S);
    }
}
