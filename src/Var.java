import java.util.ArrayList;

public class Var {
    public static int x0;
    public static int y0;
    public static int d;
    private static int flagdo;

    public static void setFlagdo(int flagdo) {
        Var.flagdo = flagdo;
    }

    public static int getFlagdo() {
        return flagdo;
    }

    private static int mode;

    public void setMode(int mode) {
        this.mode = mode;
    }

    public static int getMode() {
        return mode;
    }




    private String user;
    private String password;
    private String ensurePassword;
    private static ArrayList<String> U = new ArrayList<>();
    private static ArrayList<String> P = new ArrayList<>();
    private static ArrayList<Double> W = new ArrayList<>();//胜率

    private static ArrayList<Integer> N = new ArrayList<>();

    public static void setN(ArrayList<Integer> n) {
        N = n;
    }

    public static ArrayList<Integer> getN() {
        return N;
    }

    public static void setU(ArrayList<String> u) {
        U = u;
    }
    public static ArrayList<String> getU() {
        return U;
    }

    public static void setP(ArrayList<String> p) {
        P = p;
    }

    public static ArrayList<String> getP() {
        return P;
    }

    public static void setW(ArrayList<Double> w) {
        W = w;
    }

    public static ArrayList<Double> getW() {
        return W;
    }


    public static int clock;





    public void setUser(String user){
        this.user = user;
    }
    public String getUser(){
        return user;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }
    public void setEnsurePassword(String ensurePassword){
        this.ensurePassword=ensurePassword;
    }
    public String getEnsurePassword(){
        return ensurePassword;
    }

}
