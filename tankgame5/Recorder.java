package tankgame5;

import java.io.*;
import java.util.Vector;

/**
 * 该类记录玩家信息，和文件交互
 * @Author: xjhqre
 * @DateTime: 2021/9/18 21:24
 */
public class Recorder {
    // 击毁敌方坦克数
    private static int shotEnemyTankNum = 0;
    // 定义IO对象
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    public static final String scoreFileName = "src\\score.txt";
    public static final String enemyTankFileName = "src\\enemyTank.txt";
    // 获取MyPanel里的敌方坦克集合
    private static Vector<EnemyTank> enemyTankVector = null;

    public static void setEnemyTankVector(Vector<EnemyTank> enemyTankVector) {
        Recorder.enemyTankVector = enemyTankVector;
    }

    public static int getShotEnemyTankNum() {
        return shotEnemyTankNum;
    }

    public static void setShotEnemyTankNum(int shotEnemyTankNum) {
        Recorder.shotEnemyTankNum = shotEnemyTankNum;
    }

    /**
     * 添加分数
     */
    public static void add() {
        shotEnemyTankNum++;
    }

    /**
     * 保存玩家分数
     */
    public static void save() {
        try {
            bw = new BufferedWriter(new FileWriter(scoreFileName));
            bw.write(shotEnemyTankNum + "");

            // 保存敌方坦克信息
            OutputStream out = new FileOutputStream(enemyTankFileName);
            ObjectOutput oo = new ObjectOutputStream(out);
            for (int i = 0; i < enemyTankVector.size(); i++) {
                EnemyTank enemyTank = enemyTankVector.get(i);
                oo.writeObject(enemyTank);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void load() {
        InputStream in;
        try {
            br = new BufferedReader(new FileReader(scoreFileName));
            try {
                String s = br.readLine();
                if (s != null) {
                    shotEnemyTankNum = Integer.parseInt(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
