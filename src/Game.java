import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



class Fire{

    private int x;
    private int y;


    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
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


}

public class Game extends JPanel implements KeyListener,ActionListener {



    Timer timer = new Timer(5,this);

    private  int passedTime = 0;
    private int spentFire = 0;


    private BufferedImage image;

    private ArrayList<Fire> fires = new ArrayList<Fire>();



    private int firedirY = 1;

    private int ballX = 0;

    private int balldirX = 2;

    private int spaceShipX = 0;

    private int dirSpaceX = 20;

    public boolean control(){


        for (Fire fire : fires){


            if(new Rectangle(fire.getX(),fire.getY(),10,20).intersects(new Rectangle(ballX,0,20,20))){

                return true;

            }


        }
        return  false;
    }



    public Game() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("spaceship.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setBackground(Color.BLACK);

        timer.start();


    }





    @Override
    public void paint(Graphics g) {
        super.paint(g);

        passedTime +=5;

        g.setColor(Color.red);

        g.fillOval(ballX,0,20,20);

        g.drawImage(image,spaceShipX,490,image.getWidth() / 10, image.getHeight() / 10, this);


        g.setColor(Color.YELLOW);

        for (Fire fire : fires){

            g.fillRect(fire.getX(), fire.getY(),10,20);

        }
       /* for(Fire fire : fires){

            if(fire.getY() < 0){

                fires.remove(fire);

            }

        }*/




        if (control()){

            timer.stop();
            String message = "You won...\n"+
                             "Spent Fire : " + spentFire +
                             "\nPassed Time : " + passedTime / 1000.0 + " seconds";

            JOptionPane.showMessageDialog(this,message);
            System.exit(0);


        }


    }

    @Override
    public void repaint() {
        super.repaint();
    }






    @Override
    public void keyTyped(KeyEvent e) {



    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if(c == KeyEvent.VK_LEFT){

            if(spaceShipX <= 0){

                spaceShipX = 0;
            }

            else{
                spaceShipX -= dirSpaceX;

            }
        }

        else if(c == KeyEvent.VK_RIGHT){

            if(spaceShipX >= 750){

                spaceShipX = 750;
            }

            else{
                spaceShipX += dirSpaceX;

            }
        }

        else if (c == KeyEvent.VK_CONTROL) {

            fires.add(new Fire(spaceShipX+15,490));
            spentFire ++;


        }


    }

    @Override
    public void keyReleased(KeyEvent e) {



    }


    @Override
    public void actionPerformed(ActionEvent e) {

        for(Fire fire : fires){

            fire.setY(fire.getY() - firedirY);


        }


        ballX += balldirX;

        if(ballX >= 750){

            balldirX = -balldirX;

        }

        if(ballX <= 0){

            balldirX = -balldirX;

        }

        repaint();

    }

}
