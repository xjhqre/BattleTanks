package tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;
import java.util.Vector;

/**
 * @Author: xjhqre
 * @DateTime: 2021/9/11 13:50
 * 坦克大战的绘图区域
 */

// 为了监听键盘事件，要实现接口KeyListener
// 为了让 Panel 不停的重绘子弹，需要将 MyPanel 实现Runnable,当做一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    // 定义我的坦克

    MyTank myTank = null;

    // 定义敌人坦克，放入Vector
    Vector<EnemyTank> enemyTankVector = new Vector<>();
    int enemyTankSize = 6;

    // 定义炸弹集合和爆炸图片
    Vector<Bomb> bombs = new Vector<>();
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(int key) throws IOException, ClassNotFoundException {
        // 初始化我的坦克
        myTank = new MyTank(500, 600);
        myTank.setSpeed(5);
        Random random = new Random();

        // 判断是否是新游戏或是继续游戏
        switch (key) {
            case 1:
                // 新游戏， 初始化敌人的坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    // 创建敌人坦克
                    int r1 = random.nextInt(4);
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 60);
                    // 设置敌方坦克初始方向
                    enemyTank.setDirect(r1);
                    // 添加到敌方坦克集合中
                    enemyTankVector.add(enemyTank);
                    // 设置敌方坦克类的成员集合
                    EnemyTank.setEnemyTankVector(enemyTankVector);
                    // 设置敌方坦克的myTank属性为我方坦克
                    EnemyTank.setMyTank(myTank);

                    // 启动敌人坦克的自由移动线程
                    new Thread(enemyTank).start();

                    // 敌方坦克自由射击
                    new Thread(() -> {
                        while (true) {
                            int r2 = random.nextInt(5000);
                            // 随机发射子弹
                            try {
                                Thread.sleep(r2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (enemyTank.isLive() && enemyTank.getShots().size() < 5) {
                                Shot s = null;

                                // 判断坦克方向创建对应子弹
                                switch (enemyTank.getDirect()) {
                                    case 0:
                                        s = new Shot(enemyTank.getX(), enemyTank.getY() - 30, 0);
                                        break;
                                    case 1:
                                        s = new Shot(enemyTank.getX() + 30, enemyTank.getY(), 1);
                                        break;
                                    case 2:
                                        s = new Shot(enemyTank.getX(), enemyTank.getY() + 30, 2);
                                        break;
                                    case 3:
                                        s = new Shot(enemyTank.getX() - 30, enemyTank.getY(), 3);
                                        break;
                                }
                                enemyTank.getShots().add(s);
                                new Thread(s).start();
                            }
                            if (!enemyTank.isLive()) {
                                break; // 退出线程
                            }
                        }
                    }, "敌方坦克自由射击").start();
                }
                break;
            case 2:
                ObjectInputStream oi = new ObjectInputStream(new FileInputStream(Recorder.enemyTankFileName));
                while (true) {
                    try {
                        EnemyTank enemyTank = (EnemyTank) oi.readObject();
                        // 当抛出EOFException时 则表明已经读到文件末尾
                        // 添加到敌方坦克集合中
                        System.out.println(enemyTank);
                        enemyTankVector.add(enemyTank);
                        // 设置敌方坦克类的成员集合
                        EnemyTank.setEnemyTankVector(enemyTankVector);
                        // 设置敌方坦克的myTank属性为我方坦克
                        EnemyTank.setMyTank(myTank);

                        // 启动敌人坦克的自由移动线程
                        new Thread(enemyTank).start();

                        // 启动上一次游戏还存在的子弹，退出游戏后，敌方坦克弹夹中的子弹的线程被关闭，需要重新启动
                        for (int i = 0; i < enemyTank.getShots().size(); i++) {
                            Shot shot = enemyTank.getShots().get(i);
                            new Thread(shot).start();
                        }

                        // 敌方坦克自由射击
                        new Thread(() -> {
                            while (true) {
                                int r2 = random.nextInt(5000);
                                // 随机发射子弹
                                try {
                                    Thread.sleep(r2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (enemyTank.isLive() && enemyTank.getShots().size() < 5) {
                                    Shot s = null;

                                    // 判断坦克方向创建对应子弹
                                    switch (enemyTank.getDirect()) {
                                        case 0:
                                            s = new Shot(enemyTank.getX(), enemyTank.getY() - 30, 0);
                                            break;
                                        case 1:
                                            s = new Shot(enemyTank.getX() + 30, enemyTank.getY(), 1);
                                            break;
                                        case 2:
                                            s = new Shot(enemyTank.getX(), enemyTank.getY() + 30, 2);
                                            break;
                                        case 3:
                                            s = new Shot(enemyTank.getX() - 30, enemyTank.getY(), 3);
                                            break;
                                    }
                                    enemyTank.getShots().add(s);
                                    new Thread(s).start();
                                }
                                if (!enemyTank.isLive()) {
                                    break; // 退出线程
                                }
                            }
                        }, "敌方坦克自由射击").start();
                    } catch (EOFException e) {
                        break;
                    }
                }
                break;
            default:
                System.out.println("你的输入有误");
        }


        // 初始化炸弹图片
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/3.png"));

        // 将敌方坦克集合赋值给Recorder里的集合
        Recorder.setEnemyTankVector(enemyTankVector);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 显示背景，默认黑色
        g.fillRect(0, 0, 1000, 750);

        // 显示玩家分数
        showInfo(g);

        // 画出自己的坦克
        if (myTank != null && myTank.isLive()) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);
        }

        // 画出自己的子弹集合
        for (int i = 0; i < myTank.getShots().size(); i++) {
            Shot shot = myTank.getShots().get(i);
            if (shot != null && shot.isLive()) {
                g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
            } else {
                // 删除失效的子弹
                myTank.getShots().remove(shot);
            }
        }

        // 画出敌人的坦克
        for (EnemyTank enemyTank : enemyTankVector) {
            // 判断当前坦克是否存活
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
            }

            // 画出敌人坦克的子弹
            for (int i = 0; i < enemyTank.getShots().size(); i++) {
                // 取出子弹
                Shot shot = enemyTank.getShots().get(i);
                // 绘制
                if (shot.isLive()) {
                    g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                } else {
                    // 删除出界的子弹
                    enemyTank.getShots().remove(shot);
                }
            }
        }

        // 画出炸弹
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);

            // 根据bomb的life值画出不同的图片
            if (bomb.getLife() > 60) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 30) {
                g.drawImage(image2, bomb.getX(), bomb.getY(), 60, 60, this);
            } else {
                g.drawImage(image3, bomb.getX(), bomb.getY(), 60, 60, this);
            }

            // 减少炸弹生命值
            bomb.lifeDown();
            // 如果炸弹生命值为0，就从集合中删除
            if (bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }
    }

    /**
     * 编写画坦克的方法
     *
     * @param x         x坐标
     * @param y         y坐标
     * @param g         画笔
     * @param direction 坦克方向（上下左右）
     * @param type      坦克的类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        // 根据坦克的类型设置颜色
        switch (type) {
            case 0: // 主角
                g.setColor(Color.CYAN);
                break;
            case 1: // 敌人
                g.setColor(Color.YELLOW);
                break;
        }

        // 表示方向，0：向上    1：向右    2：向下    3：向左
        switch (direction) {
            case 0: // 表示向上
                g.fill3DRect(x - 20, y - 30, 10, 60, false); // 画出坦克左边轮子
                g.fill3DRect(x + 10, y - 30, 10, 60, false); // 画出坦克右边轮子
                g.fill3DRect(x - 10, y - 20, 20, 40, false); // 画出坦克主体
                g.fillOval(x - 10, y - 10, 20, 20); // 画出坦克盖子
                g.drawLine(x, y, x, y - 30); // 画出炮筒
                break;
            case 1:
                g.fill3DRect(x - 30, y - 20, 60, 10, false); // 画出坦克左边轮子
                g.fill3DRect(x - 30, y + 10, 60, 10, false); // 画出坦克右边轮子
                g.fill3DRect(x - 20, y - 10, 40, 20, false); // 画出坦克主体
                g.fillOval(x - 10, y - 10, 20, 20); // 画出坦克盖子
                g.drawLine(x, y, x + 30, y); // 画出炮筒
                break;
            case 2:
                g.fill3DRect(x - 20, y - 30, 10, 60, false); // 画出坦克左边轮子
                g.fill3DRect(x + 10, y - 30, 10, 60, false); // 画出坦克右边轮子
                g.fill3DRect(x - 10, y - 20, 20, 40, false); // 画出坦克主体
                g.fillOval(x - 10, y - 10, 20, 20); // 画出坦克盖子
                g.drawLine(x, y, x, y + 30); // 画出炮筒
                break;
            case 3:
                g.fill3DRect(x - 30, y - 20, 60, 10, false); // 画出坦克左边轮子
                g.fill3DRect(x - 30, y + 10, 60, 10, false); // 画出坦克右边轮子
                g.fill3DRect(x - 20, y - 10, 40, 20, false); // 画出坦克主体
                g.fillOval(x - 10, y - 10, 20, 20); // 画出坦克盖子
                g.drawLine(x, y, x - 30, y); // 画出炮筒
                break;
            default:
                System.out.println("暂时不处理");
        }
    }


    /**
     * 判断子弹是否击中坦克
     *
     * @param shot 子弹对象
     * @param Tank 坦克对象
     */
    public void hitTank(Shot shot, Tank Tank) {
        switch (Tank.getDirect()) {
            case 0: // 坦克向上
            case 2: // 坦克向下
                if (shot.getX() > Tank.getX() - 20 && shot.getX() < Tank.getX() + 20
                        && shot.getY() > Tank.getY() - 30 && shot.getY() < Tank.getY() + 30) {
                    shot.setLive(false);
                    Tank.setLive(false);

                    // 创建bomb对象，加入到bombs集合中
                    Bomb bomb = new Bomb(Tank.getX()-30, Tank.getY()-30);
                    bombs.add(bomb);
                }
                break;
            case 1: // 坦克向右
            case 3: // 坦克向左
                if (shot.getX() > Tank.getX() - 30 && shot.getX() < Tank.getX() + 30
                        && shot.getY() > Tank.getY() - 20 && shot.getY() < Tank.getY() + 20) {
                    shot.setLive(false);
                    Tank.setLive(false);

                    // 创建bomb对象，加入到bombs集合中
                    Bomb bomb = new Bomb(Tank.getX()-30, Tank.getY()-30);
                    bombs.add(bomb);
                }
                break;
        }
    }


    // 移除死亡的敌方坦克
    public void removeEnemyTank() {
        // 遍历子弹集合判断是否击中敌人坦克
        for (int i = 0; i < myTank.getShots().size(); i++) {
            Shot shot = myTank.getShots().get(i);
            if (shot != null && shot.isLive()) {
                // 遍历所有的敌人坦克
                for (int j = 0; j < enemyTankVector.size(); j++) {
                    EnemyTank enemyTank = enemyTankVector.get(j);
                    hitTank(shot, enemyTank);
                    if (!enemyTank.isLive()) {
                        enemyTankVector.remove(enemyTank);
                        Recorder.add();
                    }
                }
            }
        }
    }


    // 移除死亡的我方坦克
    public void removeMyTank() {
        // 遍历所有的敌人坦克
        for (int i = 0; i < enemyTankVector.size(); i++) {
            // 取出坦克
            EnemyTank enemyTank = enemyTankVector.get(i);
            // 遍历敌人坦克的所有子弹
            for (int j = 0; j < enemyTank.getShots().size(); j++) {
                // 取出子弹
                Shot shot = enemyTank.getShots().get(j);
                // 判断子弹是否击中我方坦克
                if (myTank.isLive() && shot.isLive()) {
                    hitTank(shot, myTank);
                }
            }
        }

    }


    // 监听按键事件
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 处理WSAD键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) { // 按下W键
            // 改变坦克方向
            myTank.setDirect(0);
            // 修改坦克的坐标
            myTank.move(0);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.setDirect(1);
            myTank.move(1);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.setDirect(2);
            myTank.move(2);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.setDirect(3);
            myTank.move(3);
        }

        // 如果用户按下的是J,就需要发射
        if (e.getKeyCode() == KeyEvent.VK_J) {
            myTank.shotEnemy();
        }

//        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void showInfo(Graphics g) {
        // 设置画笔颜色
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("累计击毁敌方坦克数量：", 1020, 30);
        g.drawString(Recorder.getShotEnemyTankNum() + "", 1100, 100);
        drawTank(1060, 90, g, 0, 0);
    }

    @Override
    public void run() { // 每隔100毫秒，重绘区域
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            removeEnemyTank();
//            removeMyTank();

            // 重绘
            this.repaint();
        }
    }
}
