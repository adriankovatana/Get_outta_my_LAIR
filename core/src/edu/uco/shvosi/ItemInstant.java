
package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;


public class ItemInstant extends Item{

    public ItemInstant(Constants.EntityGridCode gridCode, Texture texture, int cX, int cY) {
        super(gridCode, texture, cX, cY);
    }
    
    public Protagonist collision(Entity entity, int a) {
        if (entity instanceof Protagonist && !this.turnFinished) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getCX();
            Integer yCoordinate = bernard.getCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                super.kill();
                return(bernard);
            }
        }
        return(null);
    }
}
