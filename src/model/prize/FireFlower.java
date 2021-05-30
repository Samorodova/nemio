package model.prize;

import manager.GameEngine;
import model.hero.Nemio;
import model.hero.NemioForm;
import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class FireFlower extends BoostItem {

    public FireFlower(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(150);
    }

    @Override
    public void onTouch(Nemio nemio, GameEngine engine) {
        nemio.acquirePoints(getPoint());

        ImageLoader imageLoader = new ImageLoader();

        if(!nemio.getNemioForm().isFire()){
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(NemioForm.FIRE);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(NemioForm.FIRE);

            Animation animation = new Animation(leftFrames, rightFrames);
            NemioForm newForm = new NemioForm(animation, true, true);
            nemio.setNemioForm(newForm);
            nemio.setDimension(48, 96);

            engine.playFireFlower();
        }
    }

    @Override
    public void updateLocation(){}

}