package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class LaserSkill extends Skill {

    private Animation light;
    private float elapsedLight = 0f;
    ParticleEffect laserParticle;
    private int count = 0;
    
    public LaserSkill() {
        super(0, 0, TextureLoader.light2,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill1.mp3")));
        this.baseDamage = 25;
        this.damage = this.baseDamage;
        this.width = 2;

        this.damageEntities.add(new DamageEntity(0, 0, this.damage, Constants.SkillName.LASERSKILL));
        this.damageEntities.add(new DamageEntity(0, 0, this.damage, Constants.SkillName.LASERSKILL));
        laserParticle = new ParticleEffect();
        laserParticle.load(Gdx.files.internal("laserFusion.p"), Gdx.files.internal(""));
        light = TextureLoader.light;
    }

    public void draw(Batch batch, float alpha, Entity entity) {
        this.update();
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            if (bernard.getLightBarrierLimit() > 0 && elapsed >= 0.25f) {
                elapsedLight += Gdx.graphics.getDeltaTime();
                batch.draw(light.getKeyFrame(elapsedLight), entity.getX(), entity.getY() + 50, Constants.TILEDIMENSION / 2, Constants.TILEDIMENSION / 2, Constants.TILEDIMENSION * width + 50, Constants.TILEDIMENSION * height, 1, 1, 90);
                if (animation.isAnimationFinished(elapsed)) {
                    elapsedLight = 0f;
                    bernard.setLightBarrierLimit(bernard.getLightBarrierLimit() - 1);
//                    String l = String.valueOf(bernard.getLightBarrierLimit());
//                    Gdx.app.log("LightBarrier", l);
                    if (bernard.getLightBarrierLimit() == 0) {
                        bernard.setExecuteLightBarrier(false);
                        bernard.lightningInfusionCooldown = 5;
                    }
                }
            }
        }
        laserParticle.start();
        laserParticle.getEmitters().first().setPosition(entity.getX() + 50, entity.getY() + 15);
        laserParticle.getEmitters().get(1).setPosition(entity.getX() + 50, entity.getY() + 15);
        laserParticle.draw(batch, Gdx.graphics.getDeltaTime());
        laserParticle.allowCompletion();
    }
    @Override
    public boolean isAnimationFinished() {
        if (this.animation.isAnimationFinished(this.elapsed)) {
            this.elapsed = 0f;
            laserParticle.reset();
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        laserParticle.dispose();
    }
}
