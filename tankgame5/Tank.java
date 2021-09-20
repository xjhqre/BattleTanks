package tankgame5;

import java.io.Serial;
import java.io.Serializable;
import java.util.Vector;

/**
 * @Author: xjhqre
 * @DateTime: 2021/9/11 13:50
 * 坦克模板
 */
public class Tank implements Serializable {
    private int x; // 坦克的横坐标
    private int y; // 坦克的纵坐标
    private int direct = 0; // 坦克方向 上0 右1 下2 左3
    private int speed = 1;
    private boolean isLive = true;

    @Serial
    private static final long serialVersionUID = 1L;


    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * 坦克移动方法
     *
     * @param direction 坦克方向
     */
    public void move(int direction) {
        switch (direction) {
            case 0:
                if (y - speed - 30 > 0 && !isTouchEnemyTank()) {
                    y -= speed;
                }
                break;
            case 1:
                if (x + speed + 30 < 1000 && !isTouchEnemyTank()) {
                    x += speed;
                }
                break;
            case 2:
                if (y + speed + 30 < 750 && !isTouchEnemyTank()) {
                    y += speed;
                }
                break;
            case 3:
                if (x - speed - 30 > 0 && !isTouchEnemyTank()) {
                    x -= speed;
                }
                break;
        }
    }


    /**
     * 获取坦克方向
     *
     * @return 坦克方向
     */
    public int getDirect() {
        return direct;
    }

    /**
     * 设置坦克方向
     *
     * @param direct 坦克方向0， 1， 2， 3
     */
    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank() {
    }

    /**
     * 构造函数
     *
     * @param x 坦克的x坐标
     * @param y 坦克的y坐标
     */
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 获取坦克的x坐标
     *
     * @return 坦克的x坐标
     */
    public int getX() {
        return x;
    }

    /**
     * 设置坦克的x坐标
     *
     * @param x x坐标
     */
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTouchEnemyTank() {
        switch (this.getDirect()) {
            case 0:
                for (int i = 0; i < EnemyTank.getEnemyTankVector().size(); i++) {
                    Tank tank = EnemyTank.getEnemyTankVector().get(i);
                    // 不和自己比较
                    if (tank != this) {
                        if (this.getX() >= tank.getX() - 60
                                && this.getX() <= tank.getX() + 60
                                && this.getY() - this.getSpeed() >= tank.getY() - 60
                                && this.getY() - this.getSpeed() <= tank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
            case 1:
                for (int i = 0; i < EnemyTank.getEnemyTankVector().size(); i++) {
                    Tank tank = EnemyTank.getEnemyTankVector().get(i);
                    // 不和自己比较
                    if (tank != this) {
                        if (this.getX() + this.getSpeed() >= tank.getX() - 60
                                && this.getX() + this.getSpeed() <= tank.getX() + 60
                                && this.getY() >= tank.getY() - 60
                                && this.getY() <= tank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < EnemyTank.getEnemyTankVector().size(); i++) {
                    Tank tank = EnemyTank.getEnemyTankVector().get(i);
                    // 不和自己比较
                    if (tank != this) {
                        if (this.getX() >= tank.getX() - 60
                                && this.getX() <= tank.getX() + 60
                                && this.getY() + this.getSpeed() >= tank.getY() - 60
                                && this.getY() + this.getSpeed() <= tank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < EnemyTank.getEnemyTankVector().size(); i++) {
                    Tank tank = EnemyTank.getEnemyTankVector().get(i);
                    // 不和自己比较
                    if (tank != this) {
                        if (this.getX() - this.getSpeed() >= tank.getX() - 60
                                && this.getX() - this.getSpeed() <= tank.getX() + 60
                                && this.getY() >= tank.getY() - 60
                                && this.getY() <= tank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", direct=" + direct +
                ", speed=" + speed +
                ", isLive=" + isLive +
                '}';
    }
}
