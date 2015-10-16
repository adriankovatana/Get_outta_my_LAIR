package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class TrapType4 extends Trap {

    ParticleEffect poisonParticle;

    public TrapType4(int cX, int cY) {
        super(TextureLoader.TRAPTEXTURE3, cX, cY,
                Gdx.audio.newSound(Gdx.files.internal("sounds/trap3.mp3")));
        this.damage = 10;
        poisonParticle = new ParticleEffect();
        poisonParticle.load(Gdx.files.internal("traps/poison.p"), Gdx.files.internal("traps"));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (this.activate) {
            poisonParticle.start();
            poisonParticle.getEmitters().first().setPosition(this.getCX() * Constants.TILEDIMENSION + 50, this.getCY() * Constants.TILEDIMENSION + 50);
            poisonParticle.draw(batch, Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void collision(Entity entity) {
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getDCX();
            Integer yCoordinate = bernard.getDCY();

            if (xCoordinate == this.getCX() && yCoordinate == this.getCY()) {
                this.setVisible(true);
                this.activate = true;
                this.sound.play(Constants.MASTERVOLUME);
//                if (bernard.getShieldFlag() == 1) {
//                    bernard.setImage(TextureLoader.BERNARDTEXTURE);
//                    bernard.setShieldFlag(0);
//                } else {
//                    if (bernard.getExecuteBarrier() == true) {
//                        bernard.setHealth(bernard.getHealth() - this.damage / 2);
//                        bernard.setBarrierDamage(this.damage / 2);
//                        bernard.setBarrierLimit(bernard.getBarrierLimit() - 1);
//                    } else {
//                        bernard.setHealth(bernard.getHealth() - this.damage);
//                    }
//                }
                if (this.state == 0) {
                    bernard.takeDamage(this.damage);
                    this.state = 1;
                }
                //bernard.setPoison(true);
                this.turnFinished = true;
                bernard.setPoison(true);
            }
            if (bernard.getActiveSkill() != null && bernard.getActiveSkill().getName() == Constants.SkillName.DETECTION
                    && bernard.getDetectionCollisionBox().intersects(this.getCX(), this.getCY(), 3, 3)) {
                this.setVisible(true);
            }
        }
    }
}
