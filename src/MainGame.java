import java.util.ArrayList;
import java.util.Scanner;

public class MainGame {

    public static void maingame(int[][] board, int[][] state, int[][] pub, ArrayList<int[][]> BL, ArrayList<int[][]> SL, Var mode,int round,int c) {
        Scanner input = new Scanner(System.in);


        Var flagdo = new Var();


        int k = 0;
        int d = 1;
        int e;


        while (ScoreDetector.scoreRed(board) < 60 && ScoreDetector.scoreBlack(board) < 60) {

            do {
                if (!DrawJudge.dj(board, state, c, round)) {
                    System.out.println("无棋可走");
                    System.out.println("游戏结束，双方平局！");
                    System.exit(0);
                }
                if (round % 2 == 0 && k == 0) {
                    System.out.printf("第%d回合开始\n", round / 2);
                    k = 1;
                }

                flagdo.setFlagdo(0);
                if (round % 2 == 1) {
                    if (c > 0 && d != -1) {
                        System.out.println("后手方(黑色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始,输入101求和：");
                    }
                    if (c > 0 && d == -1) {
                        System.out.println("后手方(黑色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始：");
                    }
                    if (c < 0 && d != -1) {
                        System.out.println("后手方(红色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始,输入101求和：");
                    }
                    if (c < 0 && d == -1) {
                        System.out.println("后手方(红色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始：");
                    }
                }
                if (round % 2 == 0) {
                    if (c == 0 && d != -1) {
                        System.out.println("先手方请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始,输入101求和：");
                    }
                    if (c > 0 && d != -1) {
                        System.out.println("先手方(红色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始,输入101求和：");
                    }
                    if (c < 0 && d != -1) {
                        System.out.println("先手方(黑色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始,输入101求和：");
                    }
                    if (c == 0 && d == -1) {
                        System.out.println("先手方请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始：");
                    }
                    if (c > 0 && d == -1) {
                        System.out.println("先手方(红色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始：");
                    }
                    if (c < 0 && d == -1) {
                        System.out.println("先手方(黑色方)请输入一个坐标,或输入-1提前结束游戏,输入99悔棋,输入100重新开始：");
                    }
                }
                int x = input.nextInt();
                if (x == -1) {
                    System.out.println("游戏结束，是否保存进度？输入1保存，输入-1不保存");
                    int e0 = input.nextInt();
                    if (e0==1){
                        SaveLoad.save(BL,SL);
                        System.out.println("保存成功！");
                    }
                    System.out.println("输入0直接退出，输入1查看棋谱：");
                    e = input.nextInt();
                    if (e == 0) {
                        return;
                    }
                    if (e == 1) {
                        ShowBoardList.showList(BL, SL);
                        return;
                    }
                }
                if (x == 100) {
                    System.out.println("游戏即将重新开始...");
                    round = 2;
                    c = 0;
                    Initializer.Init(board,state,BL,SL);
                    flagdo.setFlagdo(1);
                    continue;
                }


                if (x == 101 && d != -1) {
                    System.out.println("确定要和棋吗？对方玩家输入0以和棋，输入-1以拒绝和棋：");
                    d = input.nextInt();
                    if (d == 0) {
                        System.out.println("游戏结束，双方平局！输入0直接退出，输入1查看棋谱：");
                        e = input.nextInt();
                        if (e == 0) {
                            return;
                        }
                        if (e == 1) {
                            ShowBoardList.showList(BL, SL);
                            return;
                        }
                    }
                    if (d == -1) {
                        System.out.println("对方玩家拒绝了和棋！");
                        flagdo.setFlagdo(1);
                        continue;
                    }
                }
                if (x == 101 && d == -1) {
                    System.out.println("对方拒绝和棋后，本回合内不能求和！");
                    flagdo.setFlagdo(1);
                    continue;
                }
                if (x == 999 && mode.getMode() == 0) {
                    System.out.println("*作弊模式已启用");
                    mode.setMode(1);
                    flagdo.setFlagdo(1);
                    continue;
                }
                if (x == 999 && mode.getMode() == 1) {
                    System.out.println("*作弊模式已停用");
                    mode.setMode(1);
                    flagdo.setFlagdo(1);
                    continue;
                }


                if (x == 99 && round != 2 && round != 3) {
                    BL.remove(round - 2);
                    SL.remove(round - 2);
                    BL.remove(round - 3);
                    SL.remove(round - 3);
                    if (round == 4) {
                        c = 0;
                    }
                    round = round - 2;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 4; j++) {
                            board[i][j] = BL.get(round - 2)[i][j];
                            state[i][j] = SL.get(round - 2)[i][j];
                        }
                    }
                    Monitor_NM.nm(board, state);
                    flagdo.setFlagdo(1);

                    continue;
                }
                if (x == 99 && (round == 2 || round == 3)) {
                    System.out.println("你现在不能悔棋！");
                    flagdo.setFlagdo(1);
                    continue;
                }


                int y = input.nextInt();
                if ((x != 0 && x != 1 && x != 2 && x != 3 && x != 4 && x != 5 && x != 6 && x != 7) || (y != 0 && y != 1 && y != 2 && y != 3)) {
                    System.out.println("这不是棋盘中的位置！");
                    flagdo.setFlagdo(1);
                    continue;
                }
                if (board[x][y] == 100) {
                    System.out.println("该位置没有棋子！");
                    flagdo.setFlagdo(1);
                }
                if (board[x][y] != 100 && state[x][y] == 1) {
                    if (!ColorJudge.cj(x, y, c, round, board)) {
                        System.out.println("你必须选择属于你颜色的棋子或是翻面的棋子！");
                        flagdo.setFlagdo(1);
                    }
                    if (ColorJudge.cj(x, y, c, round, board)) {
                        if (Math.abs(board[x][y]) != 7 && board[x][y] != 100) {
                            MoveOperator.move(x, y, board, state, flagdo);
                        }
                        if (Math.abs(board[x][y]) == 7) {
                            CannonOperator.cannon(x, y, board, state, flagdo);
                        }
                    }
                }
                if (state[x][y] == 0) {
                    TurnOperator.turn(x, y, state, flagdo);
                }
                if (round == 2) {
                    if (board[x][y] > 0) {
                        System.out.printf("先手是红色方\n");
                    }
                    if (board[x][y] < 0) {
                        System.out.printf("先手是黑色方\n");
                    }
                    c = board[x][y];
                }

            } while (Var.getFlagdo() == 1);


            if (c != 0) {
                if (mode.getMode() == 0) {
                    Monitor_NM.nm(board, state);
                }
                if (mode.getMode() == 1) {
                    Monitor_CM.cm(board, state);
                }
                System.out.printf("红色方的分数为：%d\n", ScoreDetector.scoreRed(board));
                System.out.printf("黑色方的分数为：%d\n", ScoreDetector.scoreBlack(board));
            }
            round++;
            k = 0;
            d = 1;
            BoardList.setBoardlist(board, state, BL, SL);


        }


        if (ScoreDetector.scoreRed(board) >= 60) {
            System.out.printf("游戏结束，红色方胜利！");
            System.out.println("输入0直接退出，输入1查看棋谱：");
            e = input.nextInt();
            if (e == 0) {
                return;
            }
            if (e == 1) {
                ShowBoardList.showList(BL, SL);
                return;
            }
        }
        if (ScoreDetector.scoreBlack(board) >= 60) {
            System.out.printf("游戏结束，黑色方胜利！");
            System.out.println("输入0直接退出，输入1查看棋谱：");
            e = input.nextInt();
            if (e == 0) {
                return;
            }
            if (e == 1) {
                ShowBoardList.showList(BL, SL);
                return;
            }
        }
    }
}
