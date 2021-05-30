package model.prize;

import manager.GameEngine;
import model.hero.Nemio;

import java.awt.image.BufferedImage;

public class OneUpMushroom extends BoostItem{

    public OneUpMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(200);
    }

    @Override
    public void onTouch(Nemio nemio, GameEngine engine) {
        nemio.acquirePoints(getPoint());
        nemio.setRemainingLives(nemio.getRemainingLives() + 1);
        engine.playOneUp();
    }
}