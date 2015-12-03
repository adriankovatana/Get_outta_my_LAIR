package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemHeart extends ItemInstant {

    private boolean activateHeal;
    private float elapsedHeal;
    private Animation heal;
    Sound health = Gdx.audio.newSound(Gdx.files.internal("sounds/health.mp3"));

    public ItemHeart(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.HEALTHTEXTURE, cX, cY);
        this.name = "ItemHeart";
        heal = TextureLoader.heal;
        activateHeal = false;
        elapsedHeal = 0f;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (activateHeal) {
            elapsedHeal += Gdx.graphics.getDeltaTime();
            TextureRegion temp = heal.getKeyFrame(elapsedHeal);
            batch.draw(heal.getKeyFrame(elapsedHeal), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (heal.isAnimationFinished(elapsedHeal)) {
                activateHeal = false;
                elapsedHeal = 0f;
                this.state = 1;
                this.dead = true;
            }
        } else {
            super.draw(batch, alpha);
        }
    }

    @Override
    public void collision(Entity entity) {
            Protagonist bernard = super.collision(entity, 0);
            if (bernard != null){
                bernard.heal(25);
            }
    }
}
