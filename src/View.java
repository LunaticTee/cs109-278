import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import java.util.Scanner;

public class View {
    public static int player;

    public static JFrame frame = new JFrame("翻翻棋");
    public static JPanel p = new JPanel(new GridLayout(8, 4));
    public static JPanel button = new JPanel();

    public static JButton endGame = new JButton("退出游戏");
    public static JButton b = new JButton("结束游戏");
    public static JButton r = new JButton("悔棋");
    public static JButton rs = new JButton("重新开始");
    public static JButton c = new JButton("切换为作弊模式");

    public static JPanel all = new JPanel(new BorderLayout());
    public static JPanel[][] grids = new JPanel[8][4];
    public static JLabel[][] labels = new JLabel[8][4];
    public static int cheat;
    public static JTextArea textArea = new JTextArea();

    public static void createView(int[][] board, int[][] state, ArrayList<int[][]> BL, ArrayList<int[][]> SL) {
        Var.x0 = -1;
        Var.y0 = -1;
        cheat = -1;

        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame.setDefaultLookAndFeelDecorated(true);

                // 创建及设置窗口
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                button.setLayout(new FlowLayout(FlowLayout.LEFT));
                //创建一个文本框

                  // 自动换行
                textArea.setLineWrap(true);
                  // 设置字体
                textArea.setFont(new Font(null, Font.PLAIN, 18));
                JScrollPane jScrollPane = new JScrollPane(
                        textArea,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );

                // 显示窗口
                frame.pack();
                frame.setVisible(false);
                p.setSize(640, 480);
                frame.setSize(640, 480);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        JPanel t = new JPanel();
                        Color c = new Color(255,255,255);
                        t.setBackground(c);
                        int finalI = i;
                        int finalJ = j;
                        t.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if(DarkChess.n==11){
                                    if(player==2){
                                        try {
                                            MainGameClient.methodClient(finalI,finalJ,board,state,BL,SL);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                    if(player==1){
                                        try {
                                            MainGameClient.methodServer(finalI,finalJ,board,state,BL,SL);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                }
                                if (e.isMetaDown()) {
                                    if (cheat == 1) {
                                        textArea.append("\n"+"该位置的棋子是"+(board[finalI][finalJ]));
                                    }
                                }
                                if (DarkChess.n == 0 && !e.isMetaDown()) {
                                    MainGameUI.method(finalI, finalJ, board, state, BL, SL);
                                }
                                if (DarkChess.n == 10 && !e.isMetaDown()) {
                                        MainGameAI.method(finalI, finalJ, board, state, BL, SL);
                                        MainGameAI.method(-1, -1, board, state, BL, SL);
                                }
                                if(Var.d == 2 || Var.d == 3){
                                    t.setBackground(Color.GRAY);
                                }
                            }
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                Color c = new Color(0,0,255);
                                t.setBackground(c);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                Color c = new Color(255,255,255);
                                t.setBackground(c);
                                if(Var.x0!=-1&&Var.y0!=-1) {
                                    grids[Var.x0][Var.y0].setBackground(Color.GRAY);
                                    if(Var.d==2){
                                        for(int i = 0; i < 8;i++){
                                            for(int j = 0;j<4;j++){
                                                if(MoveJudge.mj(Var.x0,Var.y0,i,j,board,state)){
                                                    grids[i][j].setBackground(Color.YELLOW);
                                                }
                                            }
                                        }
                                    }
                                    if(Var.d==3){
                                        for(int i = 0; i < 8;i++){
                                            for(int j = 0;j<4;j++){
                                                if(CannonJudge.cj(Var.x0,Var.y0,i,j,board,state)){
                                                    grids[i][j].setBackground(Color.YELLOW);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        });
                        p.add(t);
                        t.setBorder(new LineBorder(Color.black));
                        t.setVisible(true);
                        p.setVisible(true);
                        grids[i][j] = t;

                        JLabel l = new JLabel();
                        labels[i][j] = l;
                        t.add(l);

                    }
                }
    //            button.addActionListener((e) -> {
    //                System.out.println("clicked");
    //            });

                button.add(b);
                b.addActionListener(e -> {
                    exit(BL, SL);
                });
                button.add(endGame);
                endGame.addActionListener(e ->{
                    frame.setVisible(false);
                });
                endGame.setVisible(false);

                button.add(r);
                r.addActionListener(e -> {
                    regret(BL, SL, board, state);
                });

                button.add(rs);
                rs.addActionListener(e -> {
                    restart(BL, SL, board, state);
                });

                button.add(c);
                c.addActionListener(e -> {
                    if (cheat == -1) {
                        c.setText("切换为普通模式");
                    }
                    if (cheat == 1) {
                        c.setText("切换为作弊模式");
                    }
                    cheat = -cheat;
                });
                all.add(p, "North");
                all.add(button, "South");
                all.add(jScrollPane);
                frame.add(all);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void redraw(int[][] board, int[][] state) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (state[i][j] == 0) {
                    labels[i][j].setText("0");
                } else {
                    labels[i][j].setText(String.valueOf(board[i][j]));
                }
            }

        }
    }
    public static void setBack(int x,int y){
        for(int i = 0; i < 8;i++){
            for(int j = 0;j<4;j++){
                    grids[i][j].setBackground(Color.WHITE);
            }
        }
    }





    public static void regret(ArrayList<int[][]> BL, ArrayList<int[][]> SL, int[][] board, int[][] state) {
        if (DarkChess.round == 2 || DarkChess.round == 3) {
            System.out.println("你现在不能悔棋！");
            return;
        }
        BL.remove(DarkChess.round - 2);
        SL.remove(DarkChess.round - 2);
        BL.remove(DarkChess.round - 3);
        SL.remove(DarkChess.round - 3);
        if (DarkChess.round == 4) {
            DarkChess.c = 0;
        }
        DarkChess.round = DarkChess.round - 2;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = BL.get(DarkChess.round - 2)[i][j];
                state[i][j] = SL.get(DarkChess.round - 2)[i][j];
            }
        }
        Monitor_NM.nm(board, state);
        redraw(board, state);
    }

    public static void restart(ArrayList<int[][]> BL, ArrayList<int[][]> SL, int[][] board, int[][] state) {
        int userOption = JOptionPane.showConfirmDialog(null, "是否重新开始？", "重新开始", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (userOption == JOptionPane.OK_OPTION) {
            System.out.println("游戏即将重新开始...");
            DarkChess.round = 2;
            DarkChess.c = 0;
            Var.d = 1;
            Initializer.Init(board, state, BL, SL);
            redraw(board, state);
        }
    }

    public static void exit(ArrayList<int[][]> BL, ArrayList<int[][]> SL) {
        int userOption = JOptionPane.showConfirmDialog(null, "游戏结束，是否保存进度？", "结束游戏", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);  //确认对话框
//如果用户选择的是OK
        if (userOption == JOptionPane.OK_OPTION) {
            SaveLoad.save(BL, SL);
        }
        int userOption2 = JOptionPane.showConfirmDialog(null, "是否查看棋谱？", "结束游戏", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (userOption2 == JOptionPane.OK_OPTION) {
            ShowBoardList.showList(BL, SL);
        }
        Var.d = 0;
        View.b.setVisible(false);
        View.r.setVisible(false);
        View.rs.setVisible(false);
        View.c.setVisible(false);
        endGame.setVisible(true);
    }

    public static void draw(ArrayList<int[][]> BL, ArrayList<int[][]> SL, Var flagdo) {
        Scanner input = new Scanner(System.in);
        int x = 0;
        int d = 0;
        int e = 0;
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

            }
        }
        if (x == 101 && d == -1) {
            System.out.println("对方拒绝和棋后，本回合内不能求和！");
            flagdo.setFlagdo(1);

        }
    }


}
