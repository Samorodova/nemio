package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class GameObject {

    private double x, y;
    private double velX, velY;
    private Dimension dimension;
    private BufferedImage style;
    private double gravityAcc;
    private boolean falling, jumpingUp, jumpingDown;

    public GameObject(double x, double y, BufferedImage style){
        setLocation(x, y);
        setStyle(style);

        if(style != null){
            setDimension(style.getWidth(), style.getHeight());
        }

        setVelX(0);
        setVelY(0);
        setGravityAcc(0.38);
        jumpingUp = false;
        jumpingDown = false;
        falling = true;
    }

    public void draw(Graphics g){
        BufferedImage style = getStyle();

        if(style != null){
            g.drawImage(style, (int)x, (int)y, null);
        }

        //for debugging
        /*Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.draw(getTopBounds());
        g2.draw(getBottomBounds());
        g2.draw(getRightBounds());
        g2.draw(getLeftBounds());*/
    }

    public void updateLocation() {
        if((jumpingUp || jumpingDown) && velY <= 0) {
            jumpingUp = false;
            jumpingDown = false;
            velY *= -1;
        }
       else if(jumpingUp){
            velY = velY - gravityAcc;
            y = y - velY;
       }
       else if(jumpingDown) {
           velY = velY - gravityAcc;
           y = y + velY;
       }

        x = x + velX;
    }

    public void updateEnemiesLocation() {
        y = y - velY;
        x = x + velX;
    }

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Dimension getDimension(){
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public void setDimension(int width, int height){ this.dimension =  new Dimension(width, height); }

    public BufferedImage getStyle() {
        return style;
    }

    public void setStyle(BufferedImage style) {
        this.style = style;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getGravityAcc() {
        return gravityAcc;
    }

    public void setGravityAcc(double gravityAcc) {
        this.gravityAcc = gravityAcc;
    }

    public Rectangle getTopBounds(){
        return new Rectangle((int)x+dimension.width/6, (int)y, 2*dimension.width/3, dimension.height/2);
    }

    public Rectangle getBottomBounds(){
        return new Rectangle((int)x+dimension.width/6, (int)y + dimension.height/2, 2*dimension.width/3, dimension.height/2);
    }

    public Rectangle getLeftBounds(){
        return new Rectangle((int)x, (int)y + dimension.height/4, dimension.width/4, dimension.height/2);
    }

    public Rectangle getRightBounds(){
        return new Rectangle((int)x + 3*dimension.width/4, (int)y + dimension.height/4, dimension.width/4, dimension.height/2);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, dimension.width, dimension.height);
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumpingUp&&jumpingDown;
    }

    public void setJumpingUp(boolean jumpingUp) {
        this.jumpingUp = jumpingUp;
    }

    public void setJumpingDown(boolean jumpingDown) {
        this.jumpingDown = jumpingDown;
    }
}