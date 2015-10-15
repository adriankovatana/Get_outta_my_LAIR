package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BarrierSkill extends Skill {

    public BarrierSkill() {
        super(0, 0, TextureLoader.barrierSkill,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill4.mp3")));
        this.damage = 0;
    }
    
    @Override
        public void draw(Batch batch, float alpha, Entity entity) {
        this.update();
        if(entity.textureRegion.isFlipX()){
            temp = animation.getKeyFrame(elapsed);
            temp.flip(true, false);
            batch.draw(animation.getKeyFrame(elapsed), entity.getX(), entity.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
            temp.flip(true, false);
        }
        else
            batch.draw(animation.getKeyFrame(elapsed), entity.getX(), entity.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);

    }
}
