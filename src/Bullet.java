/**
 * Created by lch on 28/03/2017.
 */
public class Bullet implements Runnable{
    int x;
    int y;
    int direct;
    int speed = 5;

    public boolean isLive = true;



    public Bullet(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }



    @Override
    public void run() {

        while(true){

            if(direct == 0)
                y -= speed*3;
            if(direct == 1)
                x += speed*3;
            if(direct == 2)
                y += speed*3;
            if(direct == 3)
                x -= speed*3;


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            if(x < 0 || x > 900 || y < 0 || y > 600) {
                isLive = false;
            }

        }



    }


    public boolean isLive() {
        return isLive;
    }
}
