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
class SkillTwo extends Skill {

    private float rotation;
    private float damageInterval;
    private float prevDamageTime;
    private int tileCount;

    public SkillTwo() {
        super(0, 0, TextureLoader.skillTwo,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill2.mp3")));
        this.damage = 5;
        rotation = 0f;
        width = 2;

        tileCount = 0;
        prevDamageTime = 0f;

        damageInterval = super.animation.getAnimationDuration() * 6 / 8;

        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
    }

    @Override
    public void draw(Batch batch, float alpha, Entity entity) {
        this.update(entity);
        if (entity.textureRegion.isFlipX()) {
            batch.draw(animation.getKeyFrame(elapsed), entity.getX(), entity.getY(), Constants.TILEDIMENSION / 2, Constants.TILEDIMENSION / 2, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height, 1, 1, rotation + 180);
        } else {
            batch.draw(animation.getKeyFrame(elapsed), entity.getX(), entity.getY(), Constants.TILEDIMENSION / 2, Constants.TILEDIMENSION / 2, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height, 1, 1, rotation);
        }

    }

    public void update(Entity entity) {
        super.update();
        rotation += 200 * Gdx.graphics.getDeltaTime();
        if (this.elapsed - this.prevDamageTime >= this.damageInterval) {
            this.prevDamageTime = this.elapsed;
            this.tileCount++;
            if (entity.textureRegion.isFlipX()) {
                switch (this.tileCount) {
                    case 1:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCY(this.damageEntities.get(0).getCY() - 1);
                        break;
                    case 2:
                    case 3:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCX(this.damageEntities.get(0).getCX() + 1);
                        break;
                    case 4:
                    case 5:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCY(this.damageEntities.get(0).getCY() + 1);
                        break;
                    case 6:
                    case 7:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCX(this.damageEntities.get(0).getCX() - 1);
                        break;
                    default:
                }
            } else {
                switch (tileCount) {
                    case 1:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCY(this.damageEntities.get(0).getCY() + 1);
                        break;
                    case 2:
                    case 3:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCX(this.damageEntities.get(0).getCX() - 1);
                        break;
                    case 4:
                    case 5:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCY(this.damageEntities.get(0).getCY() - 1);
                        break;
                    case 6:
                    case 7:
                        this.damageEntities.get(0).setDead(false);
                        this.damageEntities.get(0).setCX(this.damageEntities.get(0).getCX() + 1);
                        break;
                    default:
                }
            }
            Map.miscEntityList.add(this.damageEntities.get(0));
        }
    }

    @Override
    public boolean isAnimationFinished() {
        if (this.animation.isAnimationFinished(this.elapsed / 6)) {
            this.elapsed = 0f;
            this.rotation = 0f;
            tileCount = 0;
            this.prevDamageTime = 0f;
            this.damageEntities.get(0).setDead(true);
            return true;
        } else {
            return false;
        }
    }
}
