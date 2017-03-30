import java.util.Vector;

/**
 * Created by lch on 28/03/2017.
 */
public class PlayerTank extends Tank {


    Vector<Bullet> bullets = new Vector<Bullet>();
    Bullet bullet = null;



    public PlayerTank(int x, int y){
        super(x, y);
        this.speed = 10;
    }


    public void shot() {

        if (this.isLive) {
            Thread t = new Thread();
            if (getDirect() == 0) {
                bullet = new Bullet(x + 20, y, 0);
                bullets.add(bullet);
            } else if (getDirect() == 1) {
                bullet = new Bullet(x + 60, y + 20, 1);
                bullets.add(bullet);
            } else if (getDirect() == 2) {
                bullet = new Bullet(x + 20, y + 60, 2);
                bullets.add(bullet);
            } else if (getDirect() == 3) {
                bullet = new Bullet(x, y + 20, 3);
                bullets.add(bullet);
            } else
                System.out.println("wrong");
            ;

            new Thread(bullet).start();
        }
    }
}