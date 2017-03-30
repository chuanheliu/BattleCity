/**
 * Created by lch on 28/03/2017.
 */
public abstract class Tank {
    int x = 0; //坦克的坐标
    int y = 0;
    //坦克方向 上-0 右-1 下-2 左-3
    int direct = 0;
    //tank speed
    int speed = 2;

    boolean isLive = true;



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public Tank(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void moveUp(){
        this.direct = 0;
        if(y > 0) {
            y -= speed * 2;
        }
    }

    public void moveRight(){
        this.direct = 1;
        if(x < (900-60)) {
            x += speed * 2;
        }
    }

    public void moveDown(){
        this.direct = 2;
        if(y < (600-60)) {
            y += speed * 2;
        }
    }

    public void moveLeft(){
        this.direct = 3;
        if (x > 0) {
            x -= speed * 2;
        }
    }





}