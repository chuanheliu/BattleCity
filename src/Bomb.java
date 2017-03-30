/**
 * Created by lch on 29/03/2017.
 */
public class Bomb {

    int x,y;

    //bomb life
    int life = 6;

    boolean isLive = true;

    public Bomb(int x, int y){
        this.x = x;
        this.y = y;
    }


    public void lifeDown(){
        if(life > 0){
            life--;
        }else{
            this.isLive = false;
        }
    }

}
