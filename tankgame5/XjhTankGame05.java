package tankgame5;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

/**
 * @Author: xjhqre
 * @DateTime: 2021/9/11 13:55
 */
public class XjhTankGame05 extends JFrame {
    // 定义MyPanel
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        XjhTankGame05 xjhTankGame05 = new XjhTankGame05();
    }

    public XjhTankGame05() throws IOException, ClassNotFoundException {
        System.out.println("请选择：1. 新游戏    2. 继续游戏");
        int key = scanner.nextInt();
        mp = new MyPanel(key);

        // 将mp放入Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();

        this.add(mp); // 把面板（就是游戏的绘图区域）加入框架
        this.setSize(1400, 900); // 设置框架大小
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击关闭按钮后关闭JVM
        this.setVisible(true); // 显示框架可见

        // 启动窗口时读取数据
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Recorder.load();
            }
        });

        // 关闭窗口时保存数据
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.save();
                System.exit(0);
            }
        });
    }
}
