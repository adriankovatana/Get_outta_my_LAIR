
package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class ItemHeld extends Item{

    public ItemHeld(Constants.EntityGridCode gridCode, Texture texture, int cX, int cY) {
        super(gridCode, texture, cX, cY);
    }
    
    @Override
    public void collision(Entity entity){
       if (entity instanceof Protagonist && !this.turnFinished) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getCX();
            Integer yCoordinate = bernard.getCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {                
                bernard.addInventory(this);
                if (bernard.getActive() == null){
                    bernard.setActive(this);
                }
                super.kill();                        
            }
        }
    }
}
