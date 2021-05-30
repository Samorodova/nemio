package model.hero;

import manager.Camera;
import manager.GameEngine;
import view.Animation;
import model.GameObject;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Nemio extends GameObject{

    private int remainingLives;
    private int coins;
    private int points;
    private double invincibilityTimer;
    private NemioForm nemioForm;
    private boolean toRight = true;
    private boolean toUp = true;

    public Nemio(double x, double y){
        super(x, y, null);
        setDimension(48,48);

        remainingLives = 3;
        points = 0;
        coins = 0;
        invincibilityTimer = 0;

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(NemioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(NemioForm.SMALL);

        Animation animation = new Animation(leftFrames, rightFrames);
        nemioForm = new NemioForm(animation, false, false);
        setStyle(nemioForm.getCurrentStyle(toRight, false, false));
    }

    public Nemio(double x, double y, int remainingLives, int points, int coins) {
        super(x, y, null);
        setDimension(48,48);

        this.remainingLives = remainingLives;
        this.points = points;
        this.coins = coins;
        invincibilityTimer = 0;

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(NemioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(NemioForm.SMALL);

        Animation animation = new Animation(leftFrames, rightFrames);
        nemioForm = new NemioForm(animation, false, false);
        setStyle(nemioForm.getCurrentStyle(toRight, false, false));
    }

    @Override
    public void draw(Graphics g){
        boolean movingInX = (getVelX() != 0);
        boolean movingInY = (getVelY() != 0);

        setStyle(nemioForm.getCurrentStyle(toRight, movingInX, movingInY));

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

        if(!nemioForm.isSuper() && !nemioForm.isFire()){
            remainingLives--;
            engine.playNemioDies();
            return true;
        }
        else{
            engine.shakeCamera();
            nemioForm = nemioForm.onTouchEnemy(engine.getImageLoader());
            setDimension(48, 48);
            return false;
        }
    }

    public Fireball fire(){
        return nemioForm.fire(toRight, getX(), getY());
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

    public NemioForm getNemioForm() {
        return nemioForm;
    }

    public void setNemioForm(NemioForm nemioForm) {
        this.nemioForm = nemioForm;
    }

    public boolean isSuper() {
        return nemioForm.isSuper();
    }

    public boolean getToRight() {
        return toRight;
    }

    public boolean getToUp() {
        return toUp;
    }

    public void resetLocation() {
        setVelX(0);
        setVelY(0);
        setX(50);
        setJumpingUp(false);
        setJumpingDown(false);
        setStanding(true);
    }
}