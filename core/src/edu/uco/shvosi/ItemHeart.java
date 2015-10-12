package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemHeart extends Entity {

    private int type;
    private int healAmount;
    private int state = 0;
    
    private boolean activateHeal;
    private float elapsedHeal;
    private Animation heal;
    private TextureRegion temp;
    Sound health = Gdx.audio.newSound(Gdx.files.internal("sounds/health.mp3"));

    public ItemHeart(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.HEALTHTEXTURE, cX, cY);
        this.healAmount = 25;
        heal = TextureLoader.heal;
        activateHeal = false;
        elapsedHeal = 0f;
        
        this.name = "ItemHeart";
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (activateHeal) {
            elapsedHeal += Gdx.graphics.getDeltaTime();
            temp = heal.getKeyFrame(elapsedHeal);
            batch.draw(heal.getKeyFrame(elapsedHeal), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (heal.isAnimationFinished(elapsedHeal)) {
                activateHeal = false;
                elapsedHeal = 0f;
                this.state = 1;
                this.dead = true;
            }
        }
    }

    @Override
    public void collision(Entity entity) {
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getDCX();
            Integer yCoordinate = bernard.getDCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                health.play(Constants.MASTERVOLUME);
                bernard.heal(this.healAmount);
                activateHeal = true;
            }
        }
    }
}
