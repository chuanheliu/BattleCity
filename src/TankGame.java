import javax.swing.*;
import java.awt.*;

/**
 * Created by lch on 28/03/2017.
 */
public class TankGame extends JFrame{

    GamePanel panel = null;
    public TankGame(){
        panel = new GamePanel();
        new Thread(panel).start();
        this.add(panel);
        this.addKeyListener(panel);
        this.setBounds(250,130,900,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        new TankGame();
    }

}





