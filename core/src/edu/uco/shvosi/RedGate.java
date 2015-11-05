package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class RedGate extends Antagonist {
    
    private TextureRegion temp;


    public RedGate(int cX, int cY, Constants.Direction direction) {
        super(Constants.EnemyType.REDGATE, TextureLoader.REDGATELTEXTURE, cX, cY);
        if (direction == Constants.Direction.RIGHT){
            textureRegion.setTexture(TextureLoader.REDGATERTEXTURE);
        }
        this.maxHealth = 100000;
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, this.getX(), this.getY());
    }
    
    public boolean isCollision(Entity entity) {
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getCX();
            Integer yCoordinate = bernard.getCY() + 1;
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY()) {
                return true;
            }
        }
        return false;
    }
    
}
