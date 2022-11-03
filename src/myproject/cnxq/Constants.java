package myproject.cnxq;

/**
 * 各种棋子的常量的数值对应
 */

public interface Constants {
    int EMPTY = 0;
    int select = 1;

    //红子
    int redJiang = 8;
    int redShi = 9;
    int redXiang = 10;
    int redMa = 11;
    int redChe = 12;
    int redPao = 13;
    int redZu = 14;

    //黑子
    int blackJiang = 16;
    int blackShi = 17;
    int blackXiang = 18;
    int blackMa = 19;
    int blackChe = 20;
    int blackPao = 21;
    int blackZu = 22;

    int lineHeight = 57;//每两个棋子点的间隔是57个像素.
    int x0 = 51, y0 = 53;//第一个点的坐标
    int chessSize = 55; //棋子图片的大小是55像素

    //楚河汉界的两个y值
    int riverYb = 4;
    int riverYR = 5;




}
