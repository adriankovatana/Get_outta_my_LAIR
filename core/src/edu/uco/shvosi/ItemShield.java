package edu.uco.shvosi;

public class ItemShield extends ItemHeld {
    
    ItemShield(int cX, int cY){
        super(Constants.EntityGridCode.ITEM, TextureLoader.SHIELDTEXTURE, cX, cY);        
        this.name = "ItemShield";
    }
}