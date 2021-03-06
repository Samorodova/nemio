package model;

import model.brick.Brick;
import model.brick.Foam;
import model.brick.Food;
import model.enemy.Enemy;
import model.hero.Nemio;
import model.prize.BoostItem;
import model.prize.Coin;
import model.prize.Prize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {

    private double remainingTime;
    private Nemio nemio;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Brick> groundBricks = new ArrayList<>();
    private ArrayList<Prize> revealedPrizes = new ArrayList<>();
    private ArrayList<Brick> revealedBricks = new ArrayList<>();
    private ArrayList<Food> foodBricks = new ArrayList<>();
    private ArrayList<Foam> foamBricks = new ArrayList<>();
    private EndFlag endPoint;
    private BufferedImage backgroundImage;
    private double bottomBorder = 720 - 96;
    private double topBorder = 0;
    private double leftBorder = 0;
    private double rightBorder = 1268;
    private String path;


    public Map(double remainingTime, BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.remainingTime = remainingTime;
    }


    public Nemio getNemio() {
        return nemio;
    }

    public void setNemio(Nemio nemio) {
        this.nemio = nemio;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Food> getFoodBricks() {
        return foodBricks;
    }

    public ArrayList<Foam> getFoamBricks() {
        return foamBricks;
    }

    public ArrayList<Prize> getRevealedPrizes() {
        return revealedPrizes;
    }

    public ArrayList<Brick> getAllBricks() {
        ArrayList<Brick> allBricks = new ArrayList<>();

        allBricks.addAll(bricks);
        allBricks.addAll(groundBricks);
        allBricks.addAll(foamBricks);

        return allBricks;
    }



    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    public void addGroundBrick(Brick brick) {
        this.groundBricks.add(brick);
    }

    public void addFoodBrick(Food food) {
        this.foodBricks.add(food);
    }

    public void addFoamBrick(Foam foam) {
        this.foamBricks.add(foam);
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void drawMap(Graphics2D g2){
        drawBackground(g2);
        drawPrizes(g2);
        drawBricks(g2);
        drawEnemies(g2);
        drawNemio(g2);
        endPoint.draw(g2);
    }

    private void drawPrizes(Graphics2D g2) {
        for(Prize prize : revealedPrizes){
            if(prize instanceof Coin){
                ((Coin) prize).draw(g2);
            }
            else if(prize instanceof  BoostItem){
                ((BoostItem) prize).draw(g2);
            }
        }
    }

    private void drawBackground(Graphics2D g2){
        g2.drawImage(backgroundImage, 0, 0, null);
    }

    private void drawBricks(Graphics2D g2) {
        for(Brick brick : bricks){
            if(brick != null)
                brick.draw(g2);
        }

        for(Brick brick : groundBricks){
            brick.draw(g2);
        }

        for(Brick brick : foodBricks){
            if(brick != null)
                brick.draw(g2);
        }

        for(Brick brick : foamBricks){
            if(brick != null)
                brick.draw(g2);
        }
    }

    private void drawEnemies(Graphics2D g2) {
        for(Enemy enemy : enemies){
            if(enemy != null)
                enemy.draw(g2);
        }
    }

    private void drawNemio(Graphics2D g2) {
        nemio.draw(g2);
    }

    public void updateLocations() {
        nemio.updateLocation();
        for(Enemy enemy : enemies){
            enemy.updateEnemiesLocation();
        }

        endPoint.updateLocation();
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public double getTopBorder() { return topBorder; }

    public double getLeftBorder() { return leftBorder; }

    public void addRevealedPrize(Prize prize) {
        revealedPrizes.add(prize);
    }

    public void setEndPoint(EndFlag endPoint) {
        this.endPoint = endPoint;
    }

    public EndFlag getEndPoint() {
        return endPoint;
    }

    public void removeEnemy(Enemy object) {
        enemies.remove(object);
    }

    public void removeFood(Brick object) {
        foodBricks.remove(object);
    }

    public void removePrize(Prize object) {
        revealedPrizes.remove(object);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void updateTime(double passed){
        remainingTime = remainingTime - passed;
    }

    public boolean isTimeOver(){
        return remainingTime <= 0;
    }

    public double getRemainingTime() {
        return remainingTime;
    }
}
