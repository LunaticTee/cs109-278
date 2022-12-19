public class Color {
    public static String color1(String s,int fontColor,int contType,int backgroundColor){
        return String.format("\033[%d;%d;%dm%s\033[0m",fontColor,contType,backgroundColor,s);
    }
    public static String color2(String s,int fontColor){
        return String.format("\033[%dm%s\033[0m",fontColor,s);
    }

}
