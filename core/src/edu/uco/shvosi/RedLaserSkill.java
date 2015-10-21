/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author cody
 */
class RedLaserSkill extends Skill {

    private Animation light;
    private float elapsedLight = 0f;

    public RedLaserSkill() {
        super(0, 0, TextureLoader.redLaser,
                Gdx.audio.newSound(Gdx.files.internal("sounds/attack.mp3")));
        this.damage = 20;
        this.width = 3;

        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
        this.damageEntities.add(new DamageEntity(0, 0, this.damage));

        light = TextureLoader.light;
    }

    public void draw(Batch batch, float alpha, Entity entity) {
        this.update();

        if (entity.textureRegion.isFlipX()) {
            temp = animation.getKeyFrame(elapsed);
            temp.flip(true, false);
            batch.draw(animation.getKeyFrame(elapsed), entity.getX() - Constants.TILEDIMENSION * width, entity.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
            temp.flip(true, false);
        } else {
            batch.draw(animation.getKeyFrame(elapsed), entity.getX() + Constants.TILEDIMENSION, entity.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        }
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            if (bernard.getLightBarrierLimit() > 0 && elapsed >= 0.25f) {
                elapsedLight += Gdx.graphics.getDeltaTime();
                if (bernard.textureRegion.isFlipX()) {
                    temp = light.getKeyFrame(elapsedLight);
                    temp.flip(true, false);
                    batch.draw(light.getKeyFrame(elapsedLight), bernard.getX() - Constants.TILEDIMENSION * width, bernard.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                    temp.flip(true, false);
                } else {
                    batch.draw(light.getKeyFrame(elapsedLight), bernard.getX() + Constants.TILEDIMENSION, bernard.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                }
                if (light.isAnimationFinished(elapsedLight)) {
                    bernard.setLightBarrierLimit(bernard.getLightBarrierLimit() - 1);
//                    String l = String.valueOf(bernard.getLightBarrierLimit());
//                    Gdx.app.log("LightBarrier", l);
                    if (bernard.getLightBarrierLimit() == 0) {
                        bernard.setExecuteLightBarrier(false);
                    }
                    elapsedLight = 0f;
                }
            }
        }
    }

    @Override
    public boolean isAnimationFinished() {
        if (this.animation.isAnimationFinished(this.elapsed)) {
            this.elapsed = 0f;
            this.width = 3;
            return true;
        } else {
            return false;
        }
    }
}
