package model.enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Goomba extends Enemy{

    private BufferedImage rightImage;

    public Goomba(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(0);
        setVelY(3);
        setJumpingUp(true);
    }

    @Override
    public void draw(Graphics g){
        if(getVelX() > 0 && getVelY() > 0){
            g.drawImage(rightImage, (int)getX(), (int)getY(), null);
        }
        else
            super.draw(g);
    }

    public void setRightImage(BufferedImage rightImage) {
        this.rightImage = rightImage;
    }
}
