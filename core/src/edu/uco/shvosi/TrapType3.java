package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TrapType3 extends Trap {
    Sound sound;

    public TrapType3(int cX, int cY) {
        super(TextureLoader.TRAPTEXTURE3, cX, cY);
        this.animation = TextureLoader.smokeTrap;
        this.damage = 10;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/trap3.mp3"));
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (this.activate) {
            this.elapsed += Gdx.graphics.getDeltaTime();
            batch.draw(this.animation.getKeyFrame(this.elapsed), this.getX(), this.getY(),
                    Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (this.animation.isAnimationFinished(this.elapsed)) {
                this.activate = false;
                this.elapsed = 0f;
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
                        bernard.takeDamage(this.damage);
//                    }
//                }
            }
//            if (bernard.getExecuteDetection() == true && bernard.getDetectionCollisionBox().intersects(this.getCX(), this.getCY(), 3, 3)) {
//                this.setVisible(true);
//            }
        }
    }
}
