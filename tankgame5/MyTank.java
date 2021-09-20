package tankgame5;

import java.util.Vector;

/**
 * @Author: xjhqre
 * @DateTime: 2021/9/11 13:50
 * 坦克主角
 */
public class MyTank extends Tank {
    // 定义一个Shot对象，表示一个射击
    Shot shot = null;
    public MyTank(int x, int y) {
        super(x, y);
    }

    // 定义子弹集合
    private Vector<Shot> shots = new Vector<>();

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    // 射击方法
    public void shotEnemy() {
        if (shots.size() == 5) {
            return;
        }
        // 创建 Shot 对象，根据当前myTank对象的位置和方向创建
        switch (getDirect()) {
            case 0 -> shot = new Shot(getX(), getY()-30, 0);
            case 1 -> shot = new Shot(getX() + 30, getY(), 1);
            case 2 -> shot = new Shot(getX(), getY() + 30, 2);
            case 3 -> shot = new Shot(getX()-30, getY(), 3);
        }
        shot.setSpeed(2);
        // 启动shot线程
        shots.add(shot);
        new Thread(shot).start();
    }

}
