import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

public class Server {
    public static ServerSocket server;
    public static Socket socket;

    public static void startServer(int[][] board, int[][] state, ArrayList<int[][]> BL, ArrayList<int[][]> SL) throws IOException {
        //搭建服务器
        server = new ServerSocket(12138);
        System.out.println("服务器开启成功");
        View.frame.setVisible(true);
        while (true) {
            new Thread(() -> {
                try {
                    //等待客户连接
                    socket = server.accept();
                    System.out.println("一个客户端连接了");
                    //向客户端发送消息
                    //1.获取输出流
                    Initializer.Init(board, state, BL, SL);
                    DarkChess.c = 0;
                    DarkChess.round = 2;
                    Var.d = 1;
                    View.player = 1;


                    //接收客户端发来的消息
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String text = br.readLine();
                    System.out.println("客户端消息：" + text);

                    outDataFromServer(board, state);
                    View.frame.setVisible(true);
                    View.textArea.setText("第1回合开始");
                    View.redraw(board, state);

                    View.player = 1;
                    inDataFromClient(board,state);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }


    public static void outDataFromServer(int[][] board, int[][] state) throws IOException {
        System.out.println("outoutoutServer");
        OutputStream os = Server.socket.getOutputStream();
        PrintStream ps = new PrintStream(os);
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                s.append(board[i][j]).append("\t");
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                s.append(state[i][j]).append("\t");
            }
        }
        System.out.println(s);
        ps.println(s);
    }
    public static void inDataFromClient(int[][] board, int[][] state) throws IOException {
        System.out.println("inininServer");
        new Thread(() -> {
            while(true) {
                InputStream is = null;
                try {
                    is = Server.socket.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                //---
                String text = null;
                try {
                    text = br.readLine();
                } catch (IOException ignored) {
                }
                //---
                System.out.println("aaa");
                int[][] F = new int[16][4];
                if(text==null){
                    return;
                }
                String[] strings = text.split("\\t");
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 4; j++) {
                        F[i][j] = Integer.parseInt(strings[4 * i + j]);
                    }
                }
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        board[i][j] = F[i][j];
                        System.out.printf("%d ", board[i][j]);
                    }
                }
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        state[i][j] = F[i + 8][j];
                        System.out.printf("%d ", state[i][j]);
                    }
                }
                DarkChess.round++;
                System.out.printf("红色方的分数为：%d\n", ScoreDetector.scoreRed(board));
                View.textArea.append("\n红色方的分数为:" + ScoreDetector.scoreRed(board));
                System.out.printf("黑色方的分数为：%d\n", ScoreDetector.scoreBlack(board));
                View.textArea.append("\n黑色方的分数为:" + ScoreDetector.scoreRed(board));
                View.redraw(board, state);
                if (!DrawJudge.dj(board, state, DarkChess.c, DarkChess.round)) {
                    JOptionPane.showMessageDialog(null, "游戏结束，双方平局", "无棋可走", JOptionPane.OK_OPTION);
                    Var.d = 0;
                    View.frame.setVisible(false);
                }
                if (ScoreDetector.scoreRed(board) >= 60) {
                    Var.d = 0;
                    int userOption2 = JOptionPane.showConfirmDialog(null, "游戏结束，红色方胜利！", "结束游戏", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
                    View.b.setVisible(false);
                    View.r.setVisible(false);
                    View.rs.setVisible(false);
                    View.c.setVisible(false);
                    View.endGame.setVisible(true);
                }
                if (ScoreDetector.scoreBlack(board) >= 60) {
                    Var.d = 0;
                    int userOption2 = JOptionPane.showConfirmDialog(null, "游戏结束，黑色方胜利！", "结束游戏", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
                    View.b.setVisible(false);
                    View.r.setVisible(false);
                    View.rs.setVisible(false);
                    View.c.setVisible(false);
                    View.endGame.setVisible(true);
                }
                System.out.printf("第%d回合开始\n", DarkChess.round / 2);
                View.textArea.append("\n第" + DarkChess.round / 2 + "回合开始");
            }
        }).start();
    }



}
