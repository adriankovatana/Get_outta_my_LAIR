package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Batch;


public class RedGate extends Antagonist {
    
    private int x;
    private int y;


    public RedGate(int cX, int cY, Constants.Direction direction) {
        super(Constants.EnemyType.REDGATE, TextureLoader.REDGATELTEXTURE, cX, cY);
        x = cX;
        y = cY;
        if (direction == Constants.Direction.RIGHT){
            textureRegion.setTexture(TextureLoader.REDGATERTEXTURE);
        }
        this.maxHealth = 100000;
        this.name = "RedGate";
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, this.getX(), this.getY());
    }
    
 /*   public boolean isCollision(Entity entity) {
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
