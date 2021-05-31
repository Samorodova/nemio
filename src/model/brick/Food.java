package model.brick;

import manager.GameEngine;
import model.hero.Nemio;

import java.awt.image.BufferedImage;

public class Food extends Brick{

    private int point = 50;
    private boolean acquired = false;

    public Food(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(false);
        setEmpty(true);
        setDimension(48, 48);
    }

    public void onTouch(Nemio nemio, GameEngine engine) {
        if(!acquired){
            acquired = true;
            nemio.acquirePoints(point);
            engine.playFood();
        }
    }
}
