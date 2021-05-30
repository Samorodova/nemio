package model.prize;

import manager.GameEngine;
import model.hero.Nemio;

import java.awt.*;

public interface Prize {

    int getPoint();

    void reveal();

    Rectangle getBounds();

    void onTouch(Nemio nemio, GameEngine engine);

}