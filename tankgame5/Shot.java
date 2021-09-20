package tankgame5;

import java.io.Serializable;

/**
 * @Author: xjhqre
 * @DateTime: 2021/9/12 19:28
 * 射击子弹
 */
public class Shot implements Runnable, Serializable {
    private int x;
    private int y;
    private int direct = 0;
    private int speed = 2;
    private boolean isLive = true; // 子弹是否存货
    // 构造器


    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    @Override
    public void run() { // 射击行为
        while (true) {
            // 子弹需要休眠 50ms，防止刷新太快看不见
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            // 测试
//            System.out.println("x="+ x + ", y = " + y);

            // 到达边界或击中敌人坦克
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <=750 && isLive)) {
//                System.out.println("子弹线程结束");
                isLive = false;
                break;
            }
        }
    }
}
