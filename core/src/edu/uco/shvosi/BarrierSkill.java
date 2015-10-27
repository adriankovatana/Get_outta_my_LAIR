package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BarrierSkill extends Skill {

    private Animation heal;
    private float elapsedHeal = 0f;

    public BarrierSkill() {
        super(0, 0, TextureLoader.barrierSkill,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill4.mp3")));
        this.damage = 0;
        heal = TextureLoader.heal;
    }

    @Override
    public void draw(Batch batch, float alpha, Entity entity) {
        this.update();
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            if (bernard.getHealEffect() == true) {
                elapsedHeal += Gdx.graphics.getDeltaTime();
                batch.draw(heal.getKeyFrame(elapsedHeal), bernard.getX(), bernard.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                if (heal.isAnimationFinished(elapsedHeal)) {
                    bernard.setHealEffect(false);
                    bernard.heal(bernard.getBarrierDamage() / 2 + 10);
                    bernard.resetBarrierDamage();
                    bernard.setExecuteBarrier(false);
                    bernard.barrierCooldown = 5;
                    elapsedHeal = 0f;
                }
            }
        }
        if (entity.textureRegion.isFlipX()) {
            temp = animation.getKeyFrame(elapsed);
            temp.flip(true, false);
            batch.draw(animation.getKeyFrame(elapsed), entity.getX() - 24, entity.getY() - 36, Constants.TILEDIMENSION * width + 50, Constants.TILEDIMENSION * height + 50);
            temp.flip(true, false);
        } else {
            batch.draw(animation.getKeyFrame(elapsed), entity.getX() - 24, entity.getY() - 36, Constants.TILEDIMENSION * width + 50, Constants.TILEDIMENSION * height + 50);
        }
    }
}
