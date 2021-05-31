package model.hero;

import manager.Camera;
import manager.GameEngine;
import model.GameObject;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Nemio extends GameObject{

    private int remainingLives;
    private int coins;
    private int points;
    private boolean toRight = true;
    private boolean toUp = true;
    private boolean toBottom = true;

    public Nemio(double x, double y){
        super(x, y, null);
        setDimension(48,48);

        remainingLives = 3;
        points = 0;
        coins = 0;

        ImageLoader imageLoader = new ImageLoader();

        if(toRight){
            setStyle(imageLoader.loadImage("/clown-fish.png"));
        }
        else
            setStyle(imageLoader.loadImage("/clown-fishL.png"));
    }

    public Nemio(double x, double y, int remainingLives, int points, int coins) {
        super(x, y, null);
        setDimension(48,48);

        this.remainingLives = remainingLives;
        this.points = points;
        this.coins = coins;

        ImageLoader imageLoader = new ImageLoader();
        if(toRight){
            setStyle(imageLoader.loadImage("/clown-fish.png"));
        }
        else
            setStyle(imageLoader.loadImage("/clown-fishL.png"));
    }

    @Override
    public void draw(Graphics g) {
        ImageLoader imageLoader = new ImageLoader();
        if(toRight){
            setStyle(imageLoader.loadImage("/clown-fish.png"));
        }
        else
            setStyle(imageLoader.loadImage("/clown-fishL.png"));
        super.draw(g);
    }

    public void jump(GameEngine engine, boolean toUp) {
        if(toUp) {
            setJumpingUp(true);
            setVelY(5);
        }
        else {
            setJumpingDown(true);
            setVelY(5);
        }
        //engine.playJump();
    }

    public void move(boolean toRight, Camera camera) {
        if(toRight){
            setVelX(5); //next level - increase speed
        }
        else if(camera.getX() < getX()){
            setVelX(-5);
        }
        this.toRight = toRight;
    }

    public boolean onTouchEnemy(GameEngine engine){
            remainingLives--;
            engine.playNemioDies();
            return true;
    }

    public void acquireCoin() {
        coins++;
    }

    public void acquirePoints(int point){
        points = points + point;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public int getPoints() {
        return points;
    }

    public int getCoins() {
        return coins;
    }


    public boolean getToRight() {
        return toRight;
    }

    public boolean getToUp() {
        return toUp;
    }

    public boolean getToBottom() {
        return toBottom;
    }

    public void resetLocation() {
        setVelX(0);
        setVelY(0);
        setX(50);
        setJumpingUp(false);
        setJumpingDown(false);
    }
}
