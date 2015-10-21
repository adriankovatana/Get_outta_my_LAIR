package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class TrapType6 extends Trap {

    private Constants.Direction dir = Constants.Direction.LEFT;
    
    
    public TrapType6(int cX, int cY) {
        super(TextureLoader.TRAPSLIDE, cX, cY, Constants.TrapType.TRAP6,
        Gdx.audio.newSound(Gdx.files.internal("sounds/trap2.mp3")));
        this.damage = 0;
        this.setVisible(true);
 //       dir = direction;
        
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
                
                if (dir == Constants.Direction.RIGHT){
                    bernard.setCX(bernard.getCX() + 1);
                    bernard.setDirection(Constants.Direction.RIGHT);
                }
                else if (dir == Constants.Direction.UP){
                    bernard.setCY(bernard.getCY() + 1);
                    bernard.setDirection(Constants.Direction.UP);
                }
                else if (dir == Constants.Direction.DOWN){
                    bernard.setCY(bernard.getCY() - 1);
                    bernard.setDirection(Constants.Direction.DOWN);
                }
                else {
                    bernard.setCX(bernard.getCX() - 1);
                    bernard.setDirection(Constants.Direction.LEFT);
                }
                bernard.moveAction();
                
                this.turnFinished();
                
            }
            
        }
    }
}
