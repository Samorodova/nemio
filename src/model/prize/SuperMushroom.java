package model.prize;

import manager.GameEngine;
import model.hero.Nemio;
import model.hero.NemioForm;
import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

public class SuperMushroom extends BoostItem{

    public SuperMushroom(double x, double y, BufferedImage style) {
        super(x, y, style);
        setPoint(125);
    }

    @Override
    public void onTouch(Nemio nemio, GameEngine engine) {
        nemio.acquirePoints(getPoint());

        ImageLoader imageLoader = new ImageLoader();

        if(!nemio.getNemioForm().isSuper()){
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(NemioForm.SUPER);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(NemioForm.SUPER);

            Animation animation = new Animation(leftFrames, rightFrames);
            NemioForm newForm = new NemioForm(animation, true, false);
            nemio.setNemioForm(newForm);
            nemio.setDimension(48, 96);

            engine.playSuperMushroom();
        }
    }
}