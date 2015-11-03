package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class FreezingSkill extends Skill {

    private Animation light2;
    private float elapsedLight2 = 0f;
    private int count = 0;
    ParticleEffect snowParticle;

    public FreezingSkill() {
        super(0, 0, TextureLoader.icicle,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill3.mp3")));
        this.baseDamage = 15;
        this.damage = this.baseDamage;
        for (int i = 0; i < 8; i++) {
            this.damageEntities.add(new DamageEntity(0, 0, this.damage));
        }
        snowParticle = new ParticleEffect();
        snowParticle.load(Gdx.files.internal("snow.p"), Gdx.files.internal(""));
        light2 = TextureLoader.light2;
    }

    @Override
    public void draw(Batch batch, float alpha, Entity entity) {
        this.update(entity);
        snowParticle.start();
        snowParticle.getEmitters().first().setPosition(entity.getX() - 50, entity.getY() + 200);
        snowParticle.draw(batch, Gdx.graphics.getDeltaTime());
        snowParticle.allowCompletion();

        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            if (bernard.getLightBarrierLimit() > 0) {
                elapsedLight2 += Gdx.graphics.getDeltaTime();
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX() + Constants.TILEDIMENSION, bernard.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX() + Constants.TILEDIMENSION, bernard.getY() + Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX() + Constants.TILEDIMENSION, bernard.getY() - Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX(), bernard.getY() + Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX(), bernard.getY() - Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX() - Constants.TILEDIMENSION, bernard.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX() - Constants.TILEDIMENSION, bernard.getY() + Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
                batch.draw(light2.getKeyFrame(elapsedLight2), bernard.getX() - Constants.TILEDIMENSION, bernard.getY() - Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
            }
            if (light2.isAnimationFinished(elapsedLight2)) {
                elapsedLight2 = 0f;
                if (count == 2) {
                    bernard.setLightBarrierLimit(bernard.getLightBarrierLimit() - 1);
//                    String l = String.valueOf(bernard.getLightBarrierLimit());
//                    Gdx.app.log("LightBarrier", l);
                    if (bernard.getLightBarrierLimit() == 0) {
                        bernard.setExecuteLightBarrier(false);
                    }
                }
            }
        }
        batch.draw(animation.getKeyFrame(elapsed), entity.getX() + Constants.TILEDIMENSION, entity.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        batch.draw(animation.getKeyFrame(elapsed), entity.getX() + Constants.TILEDIMENSION, entity.getY() + Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        batch.draw(animation.getKeyFrame(elapsed), entity.getX() + Constants.TILEDIMENSION, entity.getY() - Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        batch.draw(animation.getKeyFrame(elapsed), entity.getX(), entity.getY() + Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        batch.draw(animation.getKeyFrame(elapsed), entity.getX(), entity.getY() - Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        batch.draw(animation.getKeyFrame(elapsed), entity.getX() - Constants.TILEDIMENSION, entity.getY(), Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        batch.draw(animation.getKeyFrame(elapsed), entity.getX() - Constants.TILEDIMENSION, entity.getY() + Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        batch.draw(animation.getKeyFrame(elapsed), entity.getX() - Constants.TILEDIMENSION, entity.getY() - Constants.TILEDIMENSION, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
    }

    public void update(Entity entity) {
        super.update();
        if (this.animation.isAnimationFinished(this.elapsed) && count < 2) {
            this.damageEntities.get(0).setDead(false);
            this.damageEntities.get(0).setCX(entity.getCX() + 1);
            this.damageEntities.get(0).setCY(entity.getCY());
            this.damageEntities.get(1).setDead(false);
            this.damageEntities.get(1).setCX(entity.getCX() + 1);
            this.damageEntities.get(1).setCY(entity.getCY() + 1);
            this.damageEntities.get(2).setDead(false);
            this.damageEntities.get(2).setCX(entity.getCX() + 1);
            this.damageEntities.get(2).setCY(entity.getCY() - 1);
            this.damageEntities.get(3).setDead(false);
            this.damageEntities.get(3).setCX(entity.getCX());
            this.damageEntities.get(3).setCY(entity.getCY() + 1);
            this.damageEntities.get(4).setDead(false);
            this.damageEntities.get(4).setCX(entity.getCX());
            this.damageEntities.get(4).setCY(entity.getCY() - 1);
            this.damageEntities.get(5).setDead(false);
            this.damageEntities.get(5).setCX(entity.getCX() - 1);
            this.damageEntities.get(5).setCY(entity.getCY());
            this.damageEntities.get(6).setDead(false);
            this.damageEntities.get(6).setCX(entity.getCX() - 1);
            this.damageEntities.get(6).setCY(entity.getCY() + 1);
            this.damageEntities.get(7).setDead(false);
            this.damageEntities.get(7).setCX(entity.getCX() - 1);
            this.damageEntities.get(7).setCY(entity.getCY() - 1);
            for (int i = 0; i < 8; i++) {
                Map.miscEntityList.add(this.damageEntities.get(i));
            }
        }
    }

    @Override
    public boolean isAnimationFinished() {
        if (this.animation.isAnimationFinished(this.elapsed)) {
            this.elapsed = 0f;
            count++;
            for (int i = 0; i < 8; i++) {
                this.damageEntities.get(i).setDead(true);
            }
        }
        if (count == 3) {
            count = 0;
            snowParticle.reset();
            return true;
        } else {
            return false;
        }
    }
}
