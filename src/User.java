import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;


public class User {
    static Var U = new Var();
    static Var P = new Var();
    static Var W = new Var();
    static Var N = new Var();
    static Var user = new Var();
    static Var password = new Var();
    static Var ensurePassword = new Var();

    public static JButton login = new JButton();
    public static JButton register = new JButton();
    public static JButton quitLogin = new JButton();
    public static JButton showWinningRate = new JButton();
    public static JButton back = new JButton();



    public static void showUser(){
        int flag = 0;
        Scanner input = new Scanner(System.in);
        ArrayList<String> U0 =  SaveLoad.loadU();
        U.setU(U0);
        ArrayList<String> P0 =  SaveLoad.loadP();
        P.setP(P0);
        ArrayList<Double> W0 =  SaveLoad.loadW();
        W.setW(W0);
        ArrayList<Integer> N0 =  SaveLoad.loadN();
        N.setN(N0);
        if(Var.clock!=-1){
            System.out.println("欢迎回来！"+U.getU().get(Var.clock));
        }
        System.out.println("0 登录");
        System.out.println("1 注册");
        System.out.println("-1 返回");
        System.out.println("2 退出登录");
        System.out.println("3 显示胜率");
        System.out.println("4 排行榜");
        do {
            int n = input.nextInt();
            if (n == 0) {
                login();
                System.out.println("-1 返回");
                System.out.println("2 退出登录");
                System.out.println("3 显示胜率");
                System.out.println("4 排行榜");
            }
            if (n == 1) {
                register();
                flag = 1;
            }
            if (n == -1) {
                flag = 1;
            }
            if (n == 2) {
                Var.clock=-1;
                System.out.println("退出登录成功！");
                flag = 1;
            }
            if (n == 3) {
                showWinningRate();
                flag = 1;
            }
            if (n == 4) {
                showRank();
                flag = 1;
            }
        }while(flag != 1);
    }

    public static void register(){
        int r = 0;
        while(r!=1) {
            Scanner input = new Scanner(System.in);
            System.out.println("请输入您的用户名:");
            String t1 =  input.nextLine();
            user.setUser(t1);
            for (String s : U.getU()) {
                if (user.getUser().equals(s)) {
                    System.out.println("用户名已存在！");
                    break;
                }
            }
            System.out.println("请输入您的密码:");
            String t2 =  input.nextLine();
            password.setPassword(t2);
            System.out.println("请确认您的密码：");
            String t3 =  input.nextLine();
            ensurePassword.setEnsurePassword(t3);
            if (!password.getPassword().equals(ensurePassword.getEnsurePassword())) {
                System.out.println("两次密码输入不一致！请重新输入密码：");
            }
            if (password.getPassword().equals(ensurePassword.getEnsurePassword())) {
                System.out.println("账号注册成功!");
                ArrayList<String> U1 = U.getU();
                U1.add(user.getUser());
                U.setU(U1);
                ArrayList<String> P1 = P.getP();
                P1.add(password.getPassword());
                P.setP(P1);
                ArrayList<Double> W1 = W.getW();
                W1.add((double)0);
                W.setW(W1);
                ArrayList<Integer> N1 = N.getN();
                N1.add(0);
                N.setN(N1);
                SaveLoad.saveU(U.getU());
                SaveLoad.saveP(P.getP());
                SaveLoad.saveW(W.getW());
                SaveLoad.saveN(N.getN());
                r=1;
            }
        }
    }


    public static void login(){
        Scanner input = new Scanner((System.in));
        System.out.println("请输入您的用户名:");
        String t1 =  input.nextLine();
        user.setUser(t1);
        int n = 0;
        if(U.getU().contains(user.getUser())){
            for(String s: U.getU()){
                if(!user.getUser().equals(s)){
                    n++;
                }
                if(user.getUser().equals(s)){
                    break;
                }
            }
        }
        int p = 0;
        System.out.println("请输入您的密码:");
        while(p!=1) {
            String t2 =  input.nextLine();
            password.setPassword(t2);
            if (password.getPassword().equals(P.getP().get(n))) {
                System.out.printf("登陆成功！欢迎%s！\n", user.getUser());
                Var.clock=n;
                System.out.println(Var.clock);
                p=1;
            }
            if (!password.getPassword().equals(P.getP().get(n))) {
                System.out.println("密码错误！请重新输入密码，或输入-1退出");
            }
            if(password.getPassword().equals("-1")){
                p = 1;
            }
        }
    }

    public static void showWinningRate(){
        if(Var.clock!=-1){
            System.out.println("您的胜率为"+W.getW().get(Var.clock));
        }
        if(Var.clock==-1){
            System.out.println("您还未登录！");
        }
    }
    public static void showRank(){
        System.out.println("排名"+"  "+"用户"+"  "+"胜率");
        ArrayList<Double> W1 = W.getW();
        int k = 0;
        int r = 1;
        while(k!=1) {
            for (Double d : W1) {
                int n = 0;
                for(Double d1: W1){
                    if(d>=d1){
                        n++;
                    }
                }
                if(n==W1.size()&&d!=0){
                    System.out.printf("%d",r);
                    System.out.printf("   %s",U.getU().get(W1.indexOf(d)));
                    System.out.printf("  %.1f",d*100);
                    System.out.println("%");
                    W1.set(W1.indexOf(d), (double) 0);
                    r++;
                }
                if(n== W1.size()&&d==0){
                    k=1;
                }
            }
        }
    }


    public static void plusWinningRate(){
        int n = N.getN().get(Var.clock);
        ArrayList<Integer> N1 = N.getN();
        N1.set(Var.clock, n+1);
        N.setN(N1);
        ArrayList<Double> W1 = W.getW();
        double w = W1.get(Var.clock);
        W1.set(Var.clock, (n*w+1)/(n+1));
        W.setW(W1);
        SaveLoad.saveN(N.getN());
        SaveLoad.saveW(W.getW());
    }

    public static void minusWinningRate(){
        int n = N.getN().get(Var.clock);
        ArrayList<Integer> N1 = N.getN();
        N1.set(Var.clock, n+1);
        N.setN(N1);
        ArrayList<Double> W1 = W.getW();
        double w = W1.get(Var.clock);
        W1.set(Var.clock, (n*w)/(n+1));
        W.setW(W1);
        SaveLoad.saveN(N.getN());
        SaveLoad.saveW(W.getW());
    }
}
