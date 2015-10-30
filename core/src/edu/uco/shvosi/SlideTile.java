package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class SlideTile extends Trap {

    private Constants.Direction direction;

    public SlideTile(int cX, int cY, Constants.Direction direction, Texture texture) {
        super(texture, cX, cY, Constants.TrapType.SLIDETILE,
                Gdx.audio.newSound(Gdx.files.internal("sounds/trap2.mp3")));
        this.direction = direction;
        this.setVisible(true);

    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
    }

    @Override
    public void collision(Entity entity) {
        if (entity instanceof Protagonist && !this.turnFinished) {
            Protagonist bernard = (Protagonist) entity;

            if (bernard.getCX() == this.getCX() && bernard.getCY() == this.getCY() && this.state == 0) {

                if (bernard.slideDirection == Constants.Direction.NONE) {
                    bernard.setSliding(true);
                    bernard.slideCounter = 0;
                    bernard.slideCounter++;
                    bernard.slideDirection = this.direction;
                    //bernard.seqAction.addAction(bernard.normalMoveToAction());
                    this.sound.play(Constants.MASTERVOLUME);
                } else if (bernard.slideDirection != this.direction) {
                    bernard.setSliding(true);
                    bernard.seqAction.addAction(bernard.slideMoveToAction());
                    bernard.slideCounter = 0;
                    bernard.slideCounter++;
                    bernard.slideDirection = this.direction;
                    this.sound.play(Constants.MASTERVOLUME);
                } else {
                    bernard.slideCounter++;
                }

                if (direction == Constants.Direction.RIGHT) {
                    bernard.setCX(bernard.getCX() + 1);
                    bernard.setPCX(bernard.getCX());
                } else if (direction == Constants.Direction.UP) {
                    bernard.setCY(bernard.getCY() + 1);
                    bernard.setPCY(bernard.getCY());
                } else if (direction == Constants.Direction.DOWN) {
                    bernard.setCY(bernard.getCY() - 1);
                    bernard.setPCY(bernard.getCY());
                } else if (direction == Constants.Direction.LEFT) {
                    bernard.setCX(bernard.getCX() - 1);
                    bernard.setPCX(bernard.getCX());
                }

            }

        }
    }
}
