package myproject.cnxq;

import javax.swing.*;
import java.awt.*;

/**
 * 整个程序的入口！！！！main函数的所在。
 */

public class ChessGame {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
        }catch (Exception e){}

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChessWindow chessWindow = new ChessWindow();
                chessWindow.setVisible(true);
            }
        });
    }
}
