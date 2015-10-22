package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class SlideTile extends Trap {

    private Constants.Direction direction;
    
    
    public SlideTile(int cX, int cY, Constants.Direction direction, Texture texture) {
        super(texture, cX, cY, Constants.TrapType.SLIDETILE,
        Gdx.audio.newSound(Gdx.files.internal("sounds/trap2.mp3")));
        this.damage = 0;
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
            Integer xCoordinate = bernard.getCX();
            Integer yCoordinate = bernard.getCY();

            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                this.sound.play(Constants.MASTERVOLUME);
                
                    bernard.setSliding(true);
                
                    if (direction == Constants.Direction.RIGHT){
                        bernard.setDCX(bernard.getCX() + 1);
                        bernard.setCX(bernard.getCX() + 1);
                        bernard.setDirection(Constants.Direction.RIGHT);
                    }
                    else if (direction == Constants.Direction.UP){
                        bernard.setDCY(bernard.getCY() + 1);
                        bernard.setCY(bernard.getCY() + 1);
                        bernard.setDirection(Constants.Direction.UP);
                    }
                    else if (direction == Constants.Direction.DOWN){
                        bernard.setDCY(bernard.getCY() - 1);
                        bernard.setCY(bernard.getCY() - 1);
                        bernard.setDirection(Constants.Direction.DOWN);
                    }
                    else if (direction == Constants.Direction.LEFT){
                        bernard.setDCX(bernard.getCX() - 1);
                        bernard.setCX(bernard.getCX() - 1);
                        bernard.setDirection(Constants.Direction.LEFT);
                    }
                    bernard.moveAction();
                    bernard.finishTurn();
                
                    this.turnFinished();
                
            }
            
        }
    }
}
