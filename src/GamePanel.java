import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class GamePanel extends JPanel implements KeyListener, Runnable{

    private PlayerTank player = null;
    private Vector<RobotTank> robots = new Vector<RobotTank>();
    private int enSize = 5;

    //to store the bombs action.
    Vector<Bomb> bombs = new Vector<Bomb>();

    //3 pics to create a boom action.
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public GamePanel(){
        player = new PlayerTank(100,100);

        //create the tanks of enemy
        for(int i = 0; i < enSize; i++){
            RobotTank robotTank = new RobotTank(5+200*i,20);
            new Thread(robotTank).start();
            robots.add(robotTank);
        }

        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/3.png"));
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,900,600);

        drawPlayerTank(g);
        drawRobotTank(g);
        drawPlayerBullet(g);
        drawRobotsBullet(g);
        drawBomb(g);
    }

    private void drawPlayerTank(Graphics g){
        if(player.isLive)
            drawTank(g,0, player.getDirect(), player.getX(), player.getY());
    }

    private void drawRobotTank(Graphics g){
        for(int i = 0; i < robots.size(); i++){
            if(robots.get(i).isLive)
                drawTank(g,1,robots.get(i).getDirect(),robots.get(i).getX(),robots.get(i).getY());
            if(!robots.get(i).isLive)
                robots.remove(i);
        }
    }

    private void drawPlayerBullet(Graphics g){
        for(int i = 0; i < player.bullets.size(); i++){
            Bullet bullet = player.bullets.get(i);
            if(bullet != null && bullet.isLive){
                g.setColor(Color.CYAN);
                g.draw3DRect(bullet.x, bullet.y, 2,2,false);
            }
            if(!bullet.isLive)
                player.bullets.remove(i);
        }
    }

    private void drawRobotsBullet(Graphics g){
        for(int i = 0; i < robots.size(); i++) {
            RobotTank robot = robots.get(i);
            for (int j = 0; j < robot.bullets.size(); j++) {
                Bullet bullet = robot.bullets.get(j);
                if (bullet != null && bullet.isLive) {
                    g.setColor(Color.YELLOW);
                    g.draw3DRect(bullet.x, bullet.y, 2, 2, false);
                }
                if (!bullet.isLive)
                    robot.bullets.remove(j);
            }
        }
    }

    private void drawBomb(Graphics g){
        for(int i = 0; i < bombs.size(); i++){

            Bomb b = bombs.get(i);

            if (b.life <= 2)
                g.drawImage(image1, b.x, b.y, 60, 60, this);
            if (b.life < 4 && b.life > 2)
                g.drawImage(image2, b.x, b.y, 60, 60, this);
            if (b.life >= 4)
                g.drawImage(image3, b.x, b.y, 60, 60, this);

            b.lifeDown();

            if (b.life == 0) {
                bombs.remove(b);
            }

        }
    }




    //draw tank
    private void drawTank(Graphics g, int tankType, int direct, int x, int y){
        if(tankType == 0)
            g.setColor(Color.CYAN);
        else if(tankType == 1)
            g.setColor(Color.yellow);

        if(direct == 0) {
            g.fill3DRect(x, y, 10, 60, false);
            g.fill3DRect(x + 30, y, 10, 60, false);
            g.fill3DRect(x + 10, y + 10, 20, 40, false);
            g.fillOval(x + 10, y + 20, 20, 20);
            g.drawLine(x + 20, y + 30, x + 20, y);
        }
        if(direct == 1){
            g.fill3DRect(x, y,60,10,false);
            g.fill3DRect(x, y+30,60,10,false);
            g.fill3DRect(x+10,y+10,40,20,false);
            g.fillOval(x+20,y+10,20,20);
            g.drawLine(x+30,y+20,x+60,y+20);
        }
        if(direct == 2) {
            g.fill3DRect(x, y, 10, 60, false);
            g.fill3DRect(x + 30, y, 10, 60, false);
            g.fill3DRect(x + 10, y + 10, 20, 40, false);
            g.fillOval(x + 10, y + 20, 20, 20);
            g.drawLine(x + 20, y + 30, x + 20, y+60);
        }
        if(direct == 3){
            g.fill3DRect(x, y,60,10,false);
            g.fill3DRect(x, y+30,60,10,false);
            g.fill3DRect(x+10,y+10,40,20,false);
            g.fillOval(x+20,y+10,20,20);
            g.drawLine(x+30,y+20, x,y+20);
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //listen to the keyboard, control the tank.
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            player.moveUp();
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            player.moveRight();
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            player.moveDown();
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            player.moveLeft();

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(player.bullets.size() <= 2)
                player.shot();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    //make this a thread that will repaint every 100ms.
    //implements Runnable and override run method.
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            attackRobot();
            attackPlayer();

            repaint();
        }
    }


    //we do not know when to hit the tank so it is better to set the method in run
    private void attackTank(Bullet bullet, Tank tank) {
        if(tank.getDirect() == 0 || tank.getDirect() == 2) {
            if (tank.x < bullet.x && bullet.x < tank.x + 40 && tank.y < bullet.y && bullet.y < tank.y + 60) {
                //tank disappear
                tank.isLive = false;
                //bullet disappear
                bullet.isLive = false;
                //boom action
                Bomb b = new Bomb(tank.x,tank.y);
                bombs.add(b);
            }
        }

        if(tank.getDirect() == 1 || tank.getDirect() == 3) {
            if (tank.x < bullet.x && bullet.x < tank.x + 60 && tank.y < bullet.y && bullet.y < tank.y + 40) {
                tank.isLive = false;
                bullet.isLive = false;

                //boom action
                Bomb b = new Bomb(tank.x,tank.y);
                bombs.add(b);
            }
        }
    }

    private void attackPlayer(){
        for(int i = 0; i < robots.size(); i++){
            for(int j = 0; j < robots.get(i).bullets.size(); j++){
                if(player.isLive)
                    attackTank(robots.get(i).bullets.get(j),player);
            }
        }
    }

    private void attackRobot(){
        for(int i = 0; i < player.bullets.size(); i++){
            for(int j = 0; j < robots.size(); j++){
                if(robots.get(j).isLive)
                    attackTank(player.bullets.get(i),robots.get(j));
            }
        }
    }

}