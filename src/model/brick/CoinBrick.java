package model.brick;

import manager.GameEngine;
import model.hero.Nemio;
import model.prize.Prize;

import java.awt.image.BufferedImage;

public class CoinBrick extends Brick{

    private Prize prize;

    public CoinBrick(double x, double y, BufferedImage style, Prize prize) {
        super(x, y, style);
        setBreakable(false);
        setEmpty(false);
        this.prize = prize;
    }

    @Override
    public Prize reveal(GameEngine engine){
        BufferedImage newStyle;
        newStyle = engine.getImageLoader().loadImage("/sand.png");

        if(prize != null){
            prize.reveal();
        }

        setEmpty(true);
        setStyle(newStyle);

        Prize toReturn = this.prize;
        this.prize = null;
        return toReturn;
    }

    @Override
    public Prize getPrize(){
        return prize;
    }

    @Override
    public void onTouch(Nemio nemio, GameEngine engine) {

    }
}