package model.brick;

import manager.GameEngine;
import model.hero.Nemio;

import java.awt.image.BufferedImage;

public class Coral extends Brick{

    public Coral(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(false);
        setEmpty(true);
        setDimension(96, 96);
    }

    @Override
    public void onTouch(Nemio nemio, GameEngine engine) {

    }
}
