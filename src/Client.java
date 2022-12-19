import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    public static Socket socket;


    public static void send() {
        new Thread(() -> {
            OutputStream os = null;
            try {
                os = Client.socket.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintStream ps = new PrintStream(os);
            ps.println("客户端连接成功");
        }).start();
    }

    public static void outDataFromClient(int[][] board, int[][] state) throws IOException {
        System.out.println("outoutoutClient");
        OutputStream os = Client.socket.getOutputStream();
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

    public static void inDataFromServer(int[][] board, int[][] state) throws IOException {
        System.out.println("inininClient");
        new Thread(() -> {
            while(true) {
                InputStream is = null;
                try {
                    is = Client.socket.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String text = null;
                try {
                    text = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("aaa");
                int[][] F = new int[16][4];
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
                View.redraw(board, state);
                if(DarkChess.round == 2){
                    for(int i = 0;i<8;i++){
                        for(int j = 0;j<4;j++){
                            if(state[i][j]==1){
                                DarkChess.c = board[i][j];
                                if (DarkChess.c > 0) {
                                    System.out.printf("先手是红色方\n");
                                    View.textArea.append("\n先手是红色方");
                                }
                                if (DarkChess.c < 0) {
                                    System.out.printf("先手是黑色方\n");
                                    View.textArea.append("\n先手是黑色方");
                                }
                            }
                        }
                    }
                }
                System.out.printf("红色方的分数为：%d\n", ScoreDetector.scoreRed(board));
                View.textArea.append("\n红色方的分数为:" + ScoreDetector.scoreRed(board));
                System.out.printf("黑色方的分数为：%d\n", ScoreDetector.scoreBlack(board));
                View.textArea.append("\n黑色方的分数为:" + ScoreDetector.scoreRed(board));
                DarkChess.round++;
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
                System.out.println(DarkChess.round);

            }
        }).start();
    }

    /*
     * 客户端
     * */
    public static void main(String[] args) throws IOException {
        int[][] board = new int[8][4];
        int[][] state = new int[8][4];
        ArrayList<int[][]> BL = new ArrayList<>();
        ArrayList<int[][]> SL = new ArrayList<>();
        Var.d = 1;
        DarkChess.c = 0;
        DarkChess.n = 11;
        View.player = 2;
        View.createView(board, state, BL, SL);
        View.textArea.setText(null);
        //与服务器进行连接
        try {
            Client.socket = new Socket("127.0.0.1", 12138);
            Client.send();
            DarkChess.round = 1;
            Client.inDataFromServer(board, state);
            System.out.println("xxx");
            View.frame.setVisible(true);
            View.textArea.setText("第1回合开始");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //接收服务器发来的消息
        //1.获取输入流
        //2.将输入流的类型转换为字符流 一次读一行
    }


}
