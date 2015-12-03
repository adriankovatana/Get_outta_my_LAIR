package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class Item extends Entity{

    public int state = 0;
    
    Item(Constants.EntityGridCode gridCode, Texture texture, int cX, int cY) {
        super(gridCode, texture, cX, cY);
    }
      
    @Override
    public void performActions() {
        this.turnFinished = true;
    }
    
    public void kill(){
        this.dead = true;
        this.state = 1;
        this.turnFinished = true;
    }
    
}



