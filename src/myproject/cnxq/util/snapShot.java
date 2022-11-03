package myproject.cnxq.util;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
/**
 * 用于截图的功能类
 */
public class snapShot{
    public static void snapShot(String fileName, String folder,JFrame mywindow) throws Exception {
        //获取窗体位置，大小等信息
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize = mywindow.getSize();
        Point leftUpPoint = mywindow.getLocationOnScreen();
        //修正坐标，保证窗体位于截图范围内
        leftUpPoint.x += 30;
        leftUpPoint.y += 80;
        windowsize.width -= 60;
        windowsize.height -= 90;
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        //利用robot进行截图
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        BufferedImage subimage = image.getSubimage(leftUpPoint.x,leftUpPoint.y,windowsize.width,windowsize.height);
        //输出到文件
        File screenFile = new File(fileName);
        if (!screenFile.exists()) {
            screenFile.mkdir();
        }
        File f = new File(screenFile, folder);
        ImageIO.write(subimage, "png", f);
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
            Desktop.getDesktop().open(f);
    }
//    public static void main(String[] args) throws InterruptedException {
////        snapShot test = new snapShot();
////        test.setSize(400,200);
////        test.setLocation(300,500);
////        test.setVisible(true);
//        Thread.sleep(1000);
//        try{
//            snapShot("e:\\你好","11.png",test);
//        }catch(Exception e){
//            e.printStackTrace();;
//        }
//    }
}
