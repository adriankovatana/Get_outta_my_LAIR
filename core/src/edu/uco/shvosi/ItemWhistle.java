package edu.uco.shvosi;

public class ItemWhistle extends ItemHeld {
    
    ItemWhistle(int cX, int cY){
        super(Constants.EntityGridCode.ITEM, TextureLoader.WHISTLETEXTURE, cX, cY);       
        this.name = "ItemWhistle";
    }
}