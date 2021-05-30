package model.brick;

import manager.GameEngine;
import model.hero.Nemio;

import java.awt.image.BufferedImage;

public class GroundBrick extends Brick{

    public GroundBrick(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(false);
        setEmpty(true);
    }

    @Override
    public void onTouch(Nemio nemio, GameEngine engine) {

    }

}
