class Score {


    //定义每个棋子，将它们用数字分类：
    //将士象车马炮卒分别为：


    static int score(int s) {
        switch (Math.abs(s)) {
            case 1:
                return (30);
            case 2:
                return (10);
            case 6:
                return (1);
            case 0:
                return (0);
            default:
                return (5);
        }

    }


    //每个棋子的位置有三种flag状态：反（-1），正（1），无棋子（0）
    //在虚拟棋盘中，若flag=1或-1，则显示数字类型，若flag=0,则显示0
    //在公示棋盘中，若flag=-1，则显示-1，若flag=1,则显示数字类型，若flag=0,则显示0
    //定义每个棋子所对应的分数函数


}
