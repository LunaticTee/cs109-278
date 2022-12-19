import javax.swing.*;
import java.util.ArrayList;

public class MainGameUI {
    boolean ai_first, ai_second;



    public static void method(int x, int y, int[][] board, int[][] state, ArrayList<int[][]> BL, ArrayList<int[][]> SL) {
        if (Var.d == 1) {

            if ((x != 0 && x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7) || (y != 0 && y != 1 && y != 2 && y != 3)) {
                System.out.println("这不是棋盘中的位置！");
                return;
            }
            if (board[x][y] == 100) {
                return;
            }
            if (board[x][y] != 100 && state[x][y] == 1) {
                if (!ColorJudge.cj(x, y, DarkChess.c, DarkChess.round, board)) {
                    System.out.println("你必须选择属于你颜色的棋子或是翻面的棋子！");
                    View.textArea.append("\n你必须选择属于你颜色的棋子或是翻面的棋子！");
                    return;
                }
                if (ColorJudge.cj(x, y, DarkChess.c, DarkChess.round, board)) {
                    if (Math.abs(board[x][y]) != 7 && board[x][y] != 100) {
                        Var.d = 2;

                        Var.x0 = x;
                        Var.y0 = y;
                        return;
                    }
                    if (Math.abs(board[x][y]) == 7) {
                        Var.d = 3;
                        Var.x0 = x;
                        Var.y0 = y;
                        return;
                    }
                }
            }
            if (state[x][y] == 0) {
                state[x][y] = 1;
            }
        }

        if (Var.d == 2) {
            if (x == Var.x0 && y == Var.y0) {
                Var.x0 = -1;
                Var.y0 = -1;
                View.setBack(x,y);
                Var.d = 1;
                return;
            }
            if (!MoveJudge.mj(Var.x0, Var.y0, x, y, board, state)) {
                System.out.println("这不是一个合法的位置！");
                View.textArea.append("\n这不是一个合法的位置！");
            }
            if (MoveJudge.mj(Var.x0, Var.y0, x, y, board, state)) {
                board[x][y] = board[Var.x0][Var.y0];
                board[Var.x0][Var.y0] = 100;
                Var.d = 1;
                View.setBack(Var.x0,Var.y0);
                Var.x0 = -1;Var.y0 = -1;
            }
        }
        if (Var.d == 3) {
            if (x == Var.x0 && y == Var.y0) {
                Var.x0 = -1;
                Var.y0 = -1;
                View.setBack(x,y);
                Var.d = 1;
                return;
            }
            if (!CannonJudge.cj(Var.x0, Var.y0, x, y, board, state)) {
                System.out.println("这不是一个合法的位置！");
                View.textArea.append("\n这不是一个合法的位置！");
            }
            if (CannonJudge.cj(Var.x0, Var.y0, x, y, board, state)) {
                int k = board[x][y];
                int s = state[x][y];
                board[x][y] = board[Var.x0][Var.y0];
                state[x][y] = 1;
                board[Var.x0][Var.y0] = 100;
                if (s == 0) {
                    System.out.printf("*被吃掉的棋子是%s\n", Convert.convert(k));
                    View.textArea.append("\n*被吃掉的棋子是"+Convert.convert(k));
                }
                Var.d = 1;
                View.setBack(Var.x0,Var.y0);
                Var.x0 = -1;Var.y0 = -1;
            }
        }
        if (Var.d == 1) {
            if (DarkChess.round == 2) {
                if (board[x][y] > 0) {
                    System.out.printf("先手是红色方\n");
                    View.textArea.append("\n先手是红色方");
                }
                if (board[x][y] < 0) {
                    System.out.printf("先手是黑色方\n");
                    View.textArea.append("\n先手是黑色方");
                }
                DarkChess.c = board[x][y];
            }
            if (DarkChess.c != 0) {
                Monitor_NM.nm(board, state);
            }
            System.out.printf("红色方的分数为：%d\n", ScoreDetector.scoreRed(board));
            View.textArea.append("\n红色方的分数为:"+ScoreDetector.scoreRed(board));
            System.out.printf("黑色方的分数为：%d\n", ScoreDetector.scoreBlack(board));
            View.textArea.append("\n黑色方的分数为:"+ScoreDetector.scoreRed(board));
            BoardList.setBoardlist(board, state, BL, SL);
            View.redraw(board, state);
            DarkChess.round++;
            if (DarkChess.round % 2 == 0) {
                System.out.printf("第%d回合开始\n", DarkChess.round / 2);
                View.textArea.append("\n第"+ DarkChess.round/2+"回合开始");
            }
        }
        if (!DrawJudge.dj(board, state, DarkChess.c, DarkChess.round)) {
            JOptionPane.showMessageDialog(null, "游戏结束，双方平局", "无棋可走", JOptionPane.OK_OPTION);
            Var.d = 0;
            View.frame.setVisible(false);
        }
        if (ScoreDetector.scoreRed(board) >= 60) {
            Var.d = 0;
            int userOption2 = JOptionPane.showConfirmDialog(null, "游戏结束，红色方胜利！是否查看棋谱？", "结束游戏", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (userOption2 == JOptionPane.OK_OPTION) {
                ShowBoardList.showList(BL, SL);
            }
            View.b.setVisible(false);
            View.r.setVisible(false);
            View.rs.setVisible(false);
            View.c.setVisible(false);
            View.endGame.setVisible(true);
        }
        if (ScoreDetector.scoreBlack(board) >= 60) {
            Var.d = 0;
            int userOption2 = JOptionPane.showConfirmDialog(null, "游戏结束，黑色方胜利！是否查看棋谱？", "结束游戏", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (userOption2 == JOptionPane.OK_OPTION) {
                ShowBoardList.showList(BL, SL);
            }
            View.b.setVisible(false);
            View.r.setVisible(false);
            View.rs.setVisible(false);
            View.c.setVisible(false);
            View.endGame.setVisible(true);
        }
    }
}
