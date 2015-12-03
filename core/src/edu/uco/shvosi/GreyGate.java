package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.List;


public class GreyGate extends Antagonist {
    
    private int x;
    private int y;

    public GreyGate(int cX, int cY, Constants.Direction direction) {
        super(Constants.EnemyType.GREYGATE, TextureLoader.GREYGATELTEXTURE, cX, cY);
        x = cX;
        y = cY;
        if (direction == Constants.Direction.RIGHT){
            textureRegion.setTexture(TextureLoader.GREYGATERTEXTURE);
        }
        this.maxHealth = 100000;
        health = maxHealth;
        this.name = "GreyGate";
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, this.getX(), this.getY());
    }
    
    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        
    }
    
/*
    public boolean isCollision(Entity entity) {
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getCX();
            Integer yCoordinate = bernard.getCY() + 1;
            if ((xCoordinate == this.x && yCoordinate == this.y)|| (xCoordinate == this.x - 1 && yCoordinate == this.y)) {
                return true;
            }
        }
        return false;
    }
*/    
    @Override
    public void takeDamage(int damage){
        if(damage == 100000){
            this.setDead(true);
            this.remove();
        }
    }
}
