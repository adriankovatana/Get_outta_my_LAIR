package edu.uco.shvosi;

public class ItemGreyKey extends ItemHeld {
    
    ItemGreyKey(int cX, int cY){
        super(Constants.EntityGridCode.ITEM, TextureLoader.GREYKEYTEXTURE, cX, cY);      
        this.name = "GreyKey";
    }
}
