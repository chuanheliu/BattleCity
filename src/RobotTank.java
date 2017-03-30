import java.util.Vector;

/**
 * Created by lch on 28/03/2017.
 */
public class RobotTank extends Tank implements Runnable{



    Vector<Bullet> bullets = new Vector<Bullet>();
    Bullet bullet = null;

    public RobotTank(int x, int y) {
        super(x, y);
    }



    @Override
    public void run() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    if (bullets.size() < 2){
                        try{
                            Thread.sleep((int)(Math.random()*5000));
                        }
                        catch(Exception e) {
                        }
                        shot();
                    }
                }
            }
        }).start();


        while(true){

            int steps = (int) (Math.random()*50);

            if(this.direct == 0)
                for(int i = 0; i < steps; i++) {
                    moveUp();
                    wait50();
                }
            if(this.direct == 1)
                for(int i = 0; i < steps; i++) {
                    moveRight();
                    wait50();
                }
            if(this.direct == 2)
                for(int i = 0; i < steps; i++) {
                    moveDown();
                    wait50();
                }
            if(this.direct == 3)
                for(int i = 0; i < steps; i++) {
                    moveLeft();
                    wait50();
                }
            this.direct = (int) (Math.random()*4);

            if(!this.isLive)
                break;
        }
    }



    public void wait50(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void shot(){

        if(this.isLive) {
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
