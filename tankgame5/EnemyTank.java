package tankgame5;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

/**
 * @Author: xjhqre
 * @DateTime: 2021/9/12 18:53
 * 敌人的坦克
 */
public class EnemyTank extends Tank implements Runnable, Serializable {
    // 构造方法
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    // 敌方坦克子弹集合
    private Vector<Shot> shots = new Vector<>();

    // 返回子弹集合
    public Vector<Shot> getShots() {
        return shots;
    }

    // MyPenel中的敌方坦克集合
    public static Vector<EnemyTank> enemyTankVector = new Vector<>();

    // 获取MyPanel中的敌方坦克集合
    public static Vector<EnemyTank> getEnemyTankVector() {
        return enemyTankVector;
    }

    // 设置MyPanel中的敌方坦克集合
    public static void setEnemyTankVector(Vector<EnemyTank> enemyTankVector) {
        EnemyTank.enemyTankVector = enemyTankVector;
    }

    public static MyTank myTank = new MyTank(100, 100);

    public static MyTank getMyTank() {
        return myTank;
    }

    public static void setMyTank(MyTank myTank) {
        EnemyTank.myTank = myTank;
    }

    // 敌方坦克自由移动
    private void enemyMove() {
        // 敌方坦克自由移动
        Random random = new Random();
        int r1 = random.nextInt(150) + 30;
        switch (getDirect()) {
            case 0:
                // 随机移动步数
                for (int i = 0; i < r1; i++) {
                    if (!isTouchMyTank()) {
                        move(0);
                    }
                    // 休眠时间等于画板刷新时间
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 1:
                // 随机移动步数
                for (int i = 0; i < r1; i++) {
                    if (!isTouchMyTank()) {
                        move(1);
                    }
                    // 休眠时间等于画板刷新时间
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 2:
                // 随机移动步数
                for (int i = 0; i < r1; i++) {
                    if (!isTouchMyTank()) {
                        move(2);
                    }
                    // 休眠时间等于画板刷新时间
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 3:
                // 随机移动步数
                for (int i = 0; i < r1; i++) {
                    if (!isTouchMyTank()) {
                        move(3);
                    }
                    // 休眠时间等于画板刷新时间
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }

        // 移动后停止一定时间
        int r2 = random.nextInt(2000) + 500;
        try {
            Thread.sleep(r2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 然后随机改变坦克方向
        int r3 = random.nextInt(4);
        setDirect(r3);

        // 改变方向后后停止一定时间
        int r4 = random.nextInt(1000) + 500;
        try {
            Thread.sleep(r4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断敌方坦克是否与我方坦克重叠
     *
     * @return
     */
    public boolean isTouchMyTank() {
        switch (this.getDirect()) {
            case 0:
                if (this.getX() >= myTank.getX() - 60
                        && this.getX() <= myTank.getX() + 60
                        && this.getY() - this.getSpeed() >= myTank.getY() - 60
                        && this.getY() - this.getSpeed() <= myTank.getY() + 60) {
                    return true;
                }
                break;
            case 1:
                if (this.getX() + this.getSpeed() >= myTank.getX() - 60
                        && this.getX() + this.getSpeed() <= myTank.getX() + 60
                        && this.getY() >= myTank.getY() - 60
                        && this.getY() <= myTank.getY() + 60) {
                    return true;
                }
                break;
            case 2:
                if (this.getX() >= myTank.getX() - 60
                        && this.getX() <= myTank.getX() + 60
                        && this.getY() + this.getSpeed() >= myTank.getY() - 60
                        && this.getY() + this.getSpeed() <= myTank.getY() + 60) {
                    return true;
                }
                break;
            case 3:

                if (this.getX() - this.getSpeed() >= myTank.getX() - 60
                        && this.getX() - this.getSpeed() <= myTank.getX() + 60
                        && this.getY() >= myTank.getY() - 60
                        && this.getY() <= myTank.getY() + 60) {
                    return true;
                }
                break;
        }
        return false;
    }



    @Override
    public void run() {
        while (true) {
//            enemyShot();
            enemyMove();
            // 如果坦克死亡则结束进程
            if (!isLive()) {
                break; // 退出线程
            }
        }

    }
}
