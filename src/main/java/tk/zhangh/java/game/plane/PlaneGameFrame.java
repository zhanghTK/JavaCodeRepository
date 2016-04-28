package tk.zhangh.java.game.plane;

import tk.zhangh.java.game.util.GameUtil;
import tk.zhangh.java.game.util.MyFrame;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * 飞机大战游戏窗口
 * Created by ZhangHao on 2016/4/27.
 */
public class PlaneGameFrame extends MyFrame{
    Image bg = GameUtil.getImage("images/plane/bg.jpg");
    Plane plane = new Plane("images/plane/plane.png", 50, 50);
    ArrayList<Bullet> bulletArrayList = new ArrayList<Bullet>();

    @Override
    public void paint(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        plane.draw(g);
        for (int i = 0; i < bulletArrayList.size(); i++) {
            Bullet bullet = bulletArrayList.get(i);
            bullet.draw(g);
            if (bullet.getRect().intersects(plane.getRect())){
                System.out.println("boom");
            }
        }
    }

    @Override
    public void lunchFrame() {
        super.lunchFrame();
        addKeyListener(new KeyMonitor());

        for (int i = 0; i < 50; i++) {
            Bullet bullet = new Bullet();
            bulletArrayList.add(bullet);
        }
    }

    public static void main(String[] args) {
        new PlaneGameFrame().lunchFrame();
    }

    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("释放：" + e.getKeyCode());
            plane.minusDirection(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("按下：" + e.getKeyCode());
            plane.addDirection(e);
        }
    }
}