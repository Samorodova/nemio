package model.enemy;

import manager.GameEngine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Net extends Enemy{

    private BufferedImage rightImage;
    private GameEngine engine;

    public Net(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(0);
        setVelY(3);

    }

    @Override
    public void draw(Graphics g){
        if(getVelX() > 0){
            g.drawImage(rightImage, (int)getX(), (int)getY(), null);
        }
        else
            super.draw(g);
    }

    public void setRightImage(BufferedImage rightImage) {
        this.rightImage = rightImage;
    }
}
