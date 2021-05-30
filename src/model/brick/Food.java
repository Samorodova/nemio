package model.brick;

import java.awt.image.BufferedImage;

public class Food extends Brick{

    public Food(double x, double y, BufferedImage style){
        super(x, y, style);
        setBreakable(false);
        setEmpty(true);
        setDimension(96, 96);
    }
}
