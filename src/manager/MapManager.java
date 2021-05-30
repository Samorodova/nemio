package manager;

import model.GameObject;
import model.Map;
import model.brick.Brick;
import model.brick.OrdinaryBrick;
import model.enemy.Enemy;
import model.hero.Nemio;
import model.prize.BoostItem;
import model.prize.Coin;
import model.prize.Prize;
import view.ImageLoader;

import java.awt.*;
import java.util.ArrayList;

public class MapManager {

    private Map map;

    public MapManager() {}

    public void updateLocations() {
        if (map == null)
            return;

        map.updateLocations();
    }

    public void resetCurrentMap(GameEngine engine, int activeMap) {
        Nemio nemio = getNemio();
        nemio.resetLocation();
        engine.resetCamera();
        createMap(engine.getImageLoader(), map.getPath(), activeMap);
        map.setNemio(nemio);
    }

    public boolean createMap(ImageLoader loader, String path, int activeMap) {
        MapCreator mapCreator = new MapCreator(loader);
        map = mapCreator.createMap("/maps/" + path, 400, activeMap);

        return map != null;
    }

    public boolean createNotFirstMap(ImageLoader loader, String path, int activeMap) {
        MapCreator mapCreator = new MapCreator(loader);
        map = mapCreator.createMap("/maps/" + path, 400, getRemainingLives(), getScore(), getCoins(), activeMap);

        return map != null;
    }

    public void acquirePoints(int point) {
        map.getNemio().acquirePoints(point);
    }

    public Nemio getNemio() {
        return map.getNemio();
    }


    public boolean isGameOver() {
        return getNemio().getRemainingLives() == 0 || map.isTimeOver();
    }

    public int getScore() {
        return getNemio().getPoints();
    }

    public int getRemainingLives() {
        return getNemio().getRemainingLives();
    }

    public int getCoins() {
        return getNemio().getCoins();
    }

    public void drawMap(Graphics2D g2) {
        map.drawMap(g2);
    }

    public int passMission() {
        if(getNemio().getX() >= map.getEndPoint().getX() && !map.getEndPoint().isTouched()){
            map.getEndPoint().setTouched(true);
            int height = (int)getNemio().getY();
            return height * 2;
        }
        else
            return -1;
    }

    public boolean endLevel(){
        return getNemio().getX() >= map.getEndPoint().getX() + 320;
    }

    public void checkCollisions(GameEngine engine, int activeMap) {
        if (map == null) {
            return;
        }

        checkBottomCollisions(engine, activeMap);
        checkTopCollisions(engine, activeMap);
        checkNemioHorizontalCollision(engine, activeMap);
        checkEnemyCollisions();
        checkPrizeCollision();
        checkPrizeContact(engine);
    }

    private void checkBottomCollisions(GameEngine engine, int activeMap) {
        Nemio nemio = getNemio();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<Enemy> enemies = map.getEnemies();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        Rectangle nemioBottomBounds = nemio.getBottomBounds();

        if (!nemio.isJumping())
            nemio.setFalling(true);

        for (Brick brick : bricks) {
            Rectangle brickTopBounds = brick.getTopBounds();
            if (nemioBottomBounds.intersects(brickTopBounds)) {
                nemio.setY(brick.getY() - nemio.getDimension().height + 1);
                nemio.setFalling(false);
                nemio.setVelY(0);
            }
        }

        boolean nemioDies = false;

        Rectangle nemioBounds = nemio.getBottomBounds();
        for(Enemy enemy : enemies){
            Rectangle enemyBounds = enemy.getTopBounds();
            if (nemioBounds.intersects(enemyBounds)) {
                nemioDies = nemio.onTouchEnemy(engine);
                toBeRemoved.add(enemy);
            }
        }
        removeObjects(toBeRemoved);

        if (nemio.getY() + nemio.getDimension().height >= map.getBottomBorder()) {
            nemio.setY(map.getBottomBorder() - nemio.getDimension().height);
            // nemio.setFalling(false);
            nemio.setJumpingDown(false);
            nemio.setJumpingUp(false);
            nemio.setVelY(0);
        }

        removeObjects(toBeRemoved);

        if(nemioDies) {
            resetCurrentMap(engine, activeMap);
        }
    }

    private void checkTopCollisions(GameEngine engine, int activeMap) {
        Nemio nemio = getNemio();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<Enemy> enemies = map.getEnemies();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        Rectangle nemioTopBounds = nemio.getTopBounds();
        for (Brick brick : bricks) {
            Rectangle brickBottomBounds = brick.getBottomBounds();
            if (nemioTopBounds.intersects(brickBottomBounds)) {
                nemio.setVelY(0);
                nemio.setY(brick.getY() + brick.getDimension().height);
                Prize prize = brick.reveal(engine);
                if(prize != null)
                    map.addRevealedPrize(prize);
            }
        }

        boolean nemioDies = false;

        Rectangle nemioBounds = nemio.getTopBounds();
        for(Enemy enemy : enemies){
            Rectangle enemyBounds = enemy.getBottomBounds();
            if (nemioBounds.intersects(enemyBounds)) {
                nemioDies = nemio.onTouchEnemy(engine);
                toBeRemoved.add(enemy);
            }
        }
        removeObjects(toBeRemoved);

        if(nemioDies) {
            resetCurrentMap(engine, activeMap);
        }

        if (nemio.getY() <= map.getTopBorder()) {
            nemio.setY(0);
            nemio.setJumpingUp(false);
            nemio.setJumpingDown(false);
            nemio.setVelY(0);
        }
    }

    private void checkNemioHorizontalCollision(GameEngine engine, int activeMap){
        Nemio nemio = getNemio();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<Enemy> enemies = map.getEnemies();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        boolean nemioDies = false;
        boolean toRight = nemio.getToRight();

        Rectangle nemioBounds = toRight ? nemio.getRightBounds() : nemio.getLeftBounds();

        for (Brick brick : bricks) {
            Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
            if (nemioBounds.intersects(brickBounds)) {
                nemio.setVelX(0);
                if(toRight)
                    nemio.setX(brick.getX() - nemio.getDimension().width);
                else
                    nemio.setX(brick.getX() + brick.getDimension().width);
            }
        }

        for(Enemy enemy : enemies){
            Rectangle enemyBounds;
            if (!toRight) enemyBounds = enemy.getRightBounds();
            else enemyBounds = enemy.getLeftBounds();
            if (nemioBounds.intersects(enemyBounds)) {
                nemioDies = nemio.onTouchEnemy(engine);
                toBeRemoved.add(enemy);
            }
        }
        removeObjects(toBeRemoved);


        if (nemio.getX() <= engine.getCameraLocation().getX() && nemio.getVelX() < 0) {
            nemio.setVelX(0);
            nemio.setX(engine.getCameraLocation().getX());
        }

        if(nemioDies) {
            resetCurrentMap(engine, activeMap);
        }
    }

    private void checkEnemyCollisions() {
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<Enemy> enemies = map.getEnemies();

        for (Enemy enemy : enemies) {
            boolean standsOnBrick = false;

            for (Brick brick : bricks) {
                Rectangle enemyBounds = enemy.getLeftBounds();
                Rectangle brickBounds = brick.getRightBounds();

                Rectangle enemyBottomBounds = enemy.getBottomBounds();
                Rectangle brickTopBounds = brick.getTopBounds();

                Rectangle enemyTopBounds = enemy.getTopBounds();
                Rectangle brickBottomBounds = brick.getBottomBounds();

                if (enemy.getVelX() > 0) {
                    enemyBounds = enemy.getRightBounds();
                    brickBounds = brick.getLeftBounds();
                }

                if (enemyBounds.intersects(brickBounds)) {
                    enemy.setVelX(-enemy.getVelX());
                }

                if (enemyBottomBounds.intersects(brickTopBounds)){
                    //  enemy.setFalling(false);
                    enemy.setJumpingUp(true);
                    enemy.setVelY(-enemy.getVelY());
                    enemy.setY(brick.getY() - enemy.getDimension().height);
                    standsOnBrick = true;
                }

                if(enemyTopBounds.intersects(brickBottomBounds)) {
                    enemy.setJumpingDown(true);
                    enemy.setVelY((-enemy.getVelY()));
                    enemy.setY(brick.getY() + brick.getDimension().height);
                }
            }

            if(enemy.getY() + enemy.getDimension().height > map.getBottomBorder()){
                // enemy.setFalling(false);
                enemy.setJumpingUp(true);
                enemy.setVelY(-enemy.getVelY());
                enemy.setY(map.getBottomBorder()-enemy.getDimension().height);
            }

            if(enemy.getY() < map.getTopBorder()) {
                enemy.setJumpingDown(true);
                enemy.setVelY(-enemy.getVelY());
                enemy.setY(map.getTopBorder());
            }

            if(enemy.getX() < map.getLeftBorder()) {
                enemy.setVelX(-enemy.getVelX());
            }

            if (!standsOnBrick && enemy.getY() < map.getBottomBorder()){
                enemy.setFalling(true);
            }
        }
    }

    private void checkPrizeCollision() {
        ArrayList<Prize> prizes = map.getRevealedPrizes();
        ArrayList<Brick> bricks = map.getAllBricks();

        for (Prize prize : prizes) {
            if (prize instanceof BoostItem) {
                BoostItem boost = (BoostItem) prize;
                Rectangle prizeBottomBounds = boost.getBottomBounds();
                Rectangle prizeRightBounds = boost.getRightBounds();
                Rectangle prizeLeftBounds = boost.getLeftBounds();
                boost.setFalling(true);

                for (Brick brick : bricks) {
                    Rectangle brickBounds;

                    if (boost.isFalling()) {
                        brickBounds = brick.getTopBounds();

                        if (brickBounds.intersects(prizeBottomBounds)) {
                            boost.setFalling(false);
                            boost.setVelY(0);
                            boost.setY(brick.getY() - boost.getDimension().height + 1);
                            if (boost.getVelX() == 0)
                                boost.setVelX(2);
                        }
                    }

                    if (boost.getVelX() > 0) {
                        brickBounds = brick.getLeftBounds();

                        if (brickBounds.intersects(prizeRightBounds)) {
                            boost.setVelX(-boost.getVelX());
                        }
                    } else if (boost.getVelX() < 0) {
                        brickBounds = brick.getRightBounds();

                        if (brickBounds.intersects(prizeLeftBounds)) {
                            boost.setVelX(-boost.getVelX());
                        }
                    }
                }

                if (boost.getY() + boost.getDimension().height > map.getBottomBorder()) {
                    boost.setFalling(false);
                    boost.setVelY(0);
                    boost.setY(map.getBottomBorder() - boost.getDimension().height);
                    if (boost.getVelX() == 0)
                        boost.setVelX(2);
                }

            }
        }
    }

    private void checkPrizeContact(GameEngine engine) {
        ArrayList<Prize> prizes = map.getRevealedPrizes();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        Rectangle nemioBounds = getNemio().getBounds();
        for(Prize prize : prizes){
            Rectangle prizeBounds = prize.getBounds();
            if (prizeBounds.intersects(nemioBounds)) {
                prize.onTouch(getNemio(), engine);
                toBeRemoved.add((GameObject) prize);
            } else if(prize instanceof Coin){
                prize.onTouch(getNemio(), engine);
            }
        }

        removeObjects(toBeRemoved);
    }


    private void removeObjects(ArrayList<GameObject> list){
        if(list == null)
            return;

        for(GameObject object : list){
            if(object instanceof Enemy){
                map.removeEnemy((Enemy)object);
            }
            else if(object instanceof Coin || object instanceof BoostItem){
                map.removePrize((Prize)object);
            }
        }
    }

    public void updateTime(){
        if(map != null)
            map.updateTime(1);
    }

    public int getRemainingTime() {
        return (int)map.getRemainingTime();
    }
}
