package model.brick;

import manager.GameEngine;
import manager.MapManager;
import model.Map;
import model.hero.Nemio;
import model.prize.Prize;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OrdinaryBrick extends Brick {

    public OrdinaryBrick(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(true);
        setEmpty(true);
    }


    @Override
    public Prize reveal(GameEngine engine){
        return null;
    }

    @Override
    public void onTouch(Nemio nemio, GameEngine engine) {

    }
}