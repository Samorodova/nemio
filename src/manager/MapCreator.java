package manager;

import model.EndFlag;
import model.brick.*;
import model.prize.*;
import view.ImageLoader;
import model.Map;
import model.enemy.Enemy;
import model.enemy.Shark;
import model.enemy.Net;
import model.hero.Nemio;

import java.awt.*;
import java.awt.image.BufferedImage;

class MapCreator {

    private ImageLoader imageLoader;

    private BufferedImage backgroundImage;
    private BufferedImage coin, food, foam;
    private BufferedImage ordinaryBrick, surpriseBrick, groundBrick, pipe;
    private BufferedImage sharkLeft, sharkRight, netLeft, netRight, endFlag;


    MapCreator(ImageLoader imageLoader) {

        this.imageLoader = imageLoader;
        BufferedImage sprite = imageLoader.loadImage("/sprite.png");

        this.backgroundImage = imageLoader.loadImage("/background.png");
        this.coin = imageLoader.loadImage("/dollar.png");
        this.food = imageLoader.loadImage("/food.png");
        this.foam = imageLoader.loadImage("/foam.png");
        this.ordinaryBrick = imageLoader.loadImage("/water.png");
        this.groundBrick = imageLoader.loadImage("/sand.png");
        this.sharkLeft = imageLoader.loadImage("/shark.png");
        this.sharkRight = imageLoader.loadImage("/sharkR.png");
        this.pipe = imageLoader.loadImage("/reef.png");
        this.netLeft = imageLoader.loadImage("/net.png");
        this.netRight = imageLoader.loadImage("/net.png");
        this.surpriseBrick = imageLoader.loadImage("/coinBlock.png");
        this.endFlag = imageLoader.loadImage("/end.png");
    }

    Map createMap(String mapPath, double timeLimit, int activeMap) {
        BufferedImage mapImage = imageLoader.loadImage(mapPath);

        if (mapImage == null) {
            System.out.println("Given path is invalid...");
            return null;
        }

        Map createdMap = new Map(timeLimit, backgroundImage);
        String[] paths = mapPath.split("/");
        createdMap.setPath(paths[paths.length-1]);

        int pixelMultiplier = 48;

        int nemio = new Color(160, 160, 160).getRGB();
        int ordinaryBrick = new Color(0, 0, 255).getRGB();
        int surpriseBrick = new Color(255, 255, 0).getRGB();
        int groundBrick = new Color(255, 0, 0).getRGB();
        int pipe = new Color(0, 255, 0).getRGB();
        int shark = new Color(0, 255, 255).getRGB();
        int net = new Color(255, 0, 255).getRGB();
        int end = new Color(160, 0, 160).getRGB();
        int plancton = new Color(128, 128, 128).getRGB();
        int foam = new Color(255, 255, 255).getRGB();

        for (int x = 0; x < mapImage.getWidth(); x++) {
            for (int y = 0; y < mapImage.getHeight(); y++) {

                int currentPixel = mapImage.getRGB(x, y);
                int xLocation = x*pixelMultiplier;
                int yLocation = y*pixelMultiplier;

                if (currentPixel == ordinaryBrick) {
                    Brick brick = new OrdinaryBrick(xLocation, yLocation, this.ordinaryBrick);
                    createdMap.addBrick(brick);
                }
                else if (currentPixel == surpriseBrick) {
                    Prize prize = generateCoin(xLocation, yLocation);
                    Brick brick = new CoinBrick(xLocation, yLocation, this.surpriseBrick, prize);
                    createdMap.addBrick(brick);
                }
                else if (currentPixel == pipe) {
                    Brick brick = new Coral(xLocation, yLocation, this.pipe);
                    createdMap.addGroundBrick(brick);
                }
                else if (currentPixel == groundBrick) {
                    Brick brick = new GroundBrick(xLocation, yLocation, this.groundBrick);
                    createdMap.addGroundBrick(brick);
                }
                else if (currentPixel == plancton) {
                    Food food = new Food(xLocation, yLocation, this.food);
                    createdMap.addFoodBrick(food);
                }
                /*else if (currentPixel == foam) {
                    Foam foams = new Foam(xLocation, yLocation, this.foam);
                    createdMap.addFoamBrick(foams);
                }*/
                else if (currentPixel == shark) {
                    Enemy enemy = new Shark(xLocation, yLocation, this.sharkLeft);
                    ((Shark)enemy).setRightImage(sharkRight);
                    if(activeMap == 1) {
                        enemy.setVelX(0);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 2) {
                        enemy.setVelX(3);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 3) {
                        enemy.setVelX(5);
                        enemy.setVelY(3);
                    }
                    createdMap.addEnemy(enemy);
                }
                else if (currentPixel == net) {
                    Enemy enemy = new Net(xLocation, yLocation, this.netLeft);
                    ((Net)enemy).setRightImage(netRight);
                    if(activeMap == 1) {
                        enemy.setVelX(0);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 2) {
                        enemy.setVelX(3);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 3) {
                        enemy.setVelX(5);
                        enemy.setVelY(3);
                    }
                    createdMap.addEnemy(enemy);
                }
                else if (currentPixel == nemio) {
                    Nemio nemioObject = new Nemio(xLocation, yLocation);//y+347
                    createdMap.setNemio(nemioObject);
                }
                else if(currentPixel == end){
                    EndFlag endPoint= new EndFlag(xLocation+24, yLocation, endFlag);
                    createdMap.setEndPoint(endPoint);
                }
            }
        }

        System.out.println("Map is created..");
        return createdMap;
    }

    Map createMap(String mapPath, double timeLimit, int remainingLives, int points, int coins, int activeMap) {
        BufferedImage mapImage = imageLoader.loadImage(mapPath);

        if (mapImage == null) {
            System.out.println("Given path is invalid...");
            return null;
        }

        Map createdMap = new Map(timeLimit, backgroundImage);
        String[] paths = mapPath.split("/");
        createdMap.setPath(paths[paths.length-1]);

        int pixelMultiplier = 48;

        int nemio = new Color(160, 160, 160).getRGB();
        int ordinaryBrick = new Color(0, 0, 255).getRGB();
        int coinBrick = new Color(255, 255, 0).getRGB();
        int groundBrick = new Color(255, 0, 0).getRGB();
        int pipe = new Color(0, 255, 0).getRGB();
        int shark = new Color(0, 255, 255).getRGB();
        int net = new Color(255, 0, 255).getRGB();
        int end = new Color(160, 0, 160).getRGB();
        int plancton = new Color(128, 128, 128).getRGB();
        int foam = new Color(255, 255, 255).getRGB();

        for (int x = 0; x < mapImage.getWidth(); x++) {
            for (int y = 0; y < mapImage.getHeight(); y++) {

                int currentPixel = mapImage.getRGB(x, y);
                int xLocation = x*pixelMultiplier;
                int yLocation = y*pixelMultiplier;

                if (currentPixel == ordinaryBrick) {
                    Brick brick = new OrdinaryBrick(xLocation, yLocation, this.ordinaryBrick);
                    createdMap.addBrick(brick);
                }
                else if (currentPixel == coinBrick) {
                    Prize prize = generateCoin(xLocation, yLocation);
                    Brick brick = new CoinBrick(xLocation, yLocation, this.surpriseBrick, prize);
                    createdMap.addBrick(brick);
                }
                else if (currentPixel == pipe) {
                    Brick brick = new Coral(xLocation, yLocation, this.pipe);
                    createdMap.addGroundBrick(brick);
                }
                else if (currentPixel == groundBrick) {
                    Brick brick = new GroundBrick(xLocation, yLocation, this.groundBrick);
                    createdMap.addGroundBrick(brick);
                }
                else if (currentPixel == plancton) {
                    Food food = new Food(xLocation, yLocation, this.food);
                    createdMap.addFoodBrick(food);
                }
                /*else if (currentPixel == foam) {
                    Foam foams = new Foam(xLocation, yLocation, this.foam);
                    createdMap.addFoamBrick(foams);
                }*/
                else if (currentPixel == shark) {
                    Enemy enemy = new Shark(xLocation, yLocation, this.sharkLeft);
                    ((Shark)enemy).setRightImage(sharkRight);
                    if(activeMap == 1) {
                        enemy.setVelX(0);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 2) {
                        enemy.setVelX(3);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 3) {
                        enemy.setVelX(6);
                        enemy.setVelY(6);
                    }
                    createdMap.addEnemy(enemy);
                }
                else if (currentPixel == net) {
                    Enemy enemy = new Net(xLocation, yLocation, this.netLeft);
                    ((Net)enemy).setRightImage(netRight);
                    if(activeMap == 1) {
                        enemy.setVelX(0);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 2) {
                        enemy.setVelX(3);
                        enemy.setVelY(3);
                    }
                    else if(activeMap == 3) {
                        enemy.setVelX(6);
                        enemy.setVelY(6);
                    }
                    createdMap.addEnemy(enemy);
                }
                else if (currentPixel == nemio) {
                    Nemio nemioObject = new Nemio(xLocation, yLocation, remainingLives, points, coins);//y+347
                    createdMap.setNemio(nemioObject);
                }
                else if(currentPixel == end){
                    EndFlag endPoint= new EndFlag(xLocation+24, yLocation, endFlag);
                    createdMap.setEndPoint(endPoint);
                }
            }
        }

        System.out.println("Map is created..");
        return createdMap;
    }

    private Prize generateCoin(double x, double y){
        Prize generated;
        generated = new Coin(x, y, this.coin, 50);
        return generated;
    }


}
