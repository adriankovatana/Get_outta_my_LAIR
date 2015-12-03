package edu.uco.shvosi;

public class ItemRedKey extends ItemHeld {
    
    ItemRedKey(int cX, int cY){
        super(Constants.EntityGridCode.ITEM, TextureLoader.REDKEYTEXTURE, cX, cY);       
        this.name = "ItemRedKey";
    }
}

