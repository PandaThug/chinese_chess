/*
 * Created by JFormDesigner on Thu Feb 02 13:58:50 CST 2017
 */

package myproject.cnxq;

import myproject.cnxq.util.MyOptionPane;
import myproject.cnxq.util.snapShot;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 * 窗体类，继承了JFrame做为整个程序的载体
 */

public class ChessWindow extends JFrame implements Constants {



    public ChessWindow() {
        initComponents();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void menuConfirmExit(ActionEvent e) {
        if(MyOptionPane.showConfirmDialog(this,"提示","你是否要退出？","是","否")){
            dispose();
        }
    }

    private void menuGameStartFirst(ActionEvent e) {
        if(MyOptionPane.showConfirmDialog(this,"提示","你是否要重新开局（先手）？","是","否")){
            panel.gameStartFirst();
            panel.repaint();
        }

    }

    private void menuGameStartLast(ActionEvent e) {
        if(MyOptionPane.showConfirmDialog(this,"提示","你是否要重新开局（后手）？","是","否")){
            panel.gameStartLast();
            panel.repaint();
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        if(MyOptionPane.showConfirmDialog(this,"提示","你是否要退出？","是","否")){
            dispose();
        }
    }


    private void menuBackChess(ActionEvent e) {
        MyOptionPane.showMessageDialog(this, "落子无悔大丈夫！", "提示");
    }


    private void menuSaveImage(ActionEvent e) throws Exception {
        //休眠1000ms，保证不会截图到菜单栏
        Thread.sleep(1000);

        //读取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();
        System.out.println(com.getPath());

        //读取系统时间组成文件名
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String strDate = df.format(date);
        String output = "象棋截图" +  strDate + ".png";
        snapShot.snapShot(com.getPath(),output,this);
    }



    //初始化棋盘，所有的对象均已经内置在类内，所以这里无需声明。
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem7 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menu3 = new JMenu();
        menuItem5 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menu2 = new JMenu();
        menuItem3 = new JMenuItem();

        //创建了chessPanel对象！！！！
        panel = new ChessPanel();

        //======== this ========
        setResizable(false);
        setTitle("\u4e2d\u56fd\u8c61\u68cb");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u6e38\u620f");

                //---- menuItem1 ----
                menuItem1.setText("\u91cd\u65b0\u5f00\u5c40\uff08\u5148\u624b\uff09");
                menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
                menuItem1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuGameStartFirst(e);
                    }
                });
                menu1.add(menuItem1);

                //---- menuItem4 ----
                menuItem4.setText("\u91cd\u65b0\u5f00\u5c40\uff08\u540e\u624b\uff09");
                menuItem4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuGameStartLast(e);
                    }
                });
                menu1.add(menuItem4);

                //---- menuItem7 ----
                menuItem7.setText("\u6094\u68cb");
                menuItem7.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuBackChess(e);
                    }
                });
                menu1.add(menuItem7);

                //---- menuItem2 ----
                menuItem2.setText("\u9000\u51fa");
                menuItem2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuConfirmExit(e);
                    }
                });
                menu1.add(menuItem2);
                menu1.addSeparator();
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText("记录战局");
                menuItem3.setText("截图到文件");
                menuItem3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            menuSaveImage(e);
                        }catch (Exception e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                });
                menu2.add(menuItem3);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);
        contentPane.add(panel);
        panel.setBounds(0, 0, 558, 620);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem4;
    private JMenuItem menuItem7;
    private JMenuItem menuItem2;
    private JMenu menu3;
    private JMenuItem menuItem5;
    private JMenuItem menuItem6;
    private JMenu menu2;
    private JMenuItem menuItem3;
    private ChessPanel panel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
