package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ItemShield extends Entity {
    private int type;
    private int state = 0;
    Sound invent = Gdx.audio.newSound(Gdx.files.internal("sounds/invent.mp3"));
    
    public ItemShield(int cX, int cY){
        super(Constants.EntityGridCode.ITEM, TextureLoader.SHIELDTEXTURE, cX, cY);
        
        this.name = "ItemShield";
    }
    
    @Override
    public void collision(Entity entity){
       if (entity instanceof Protagonist && !this.turnFinished) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getDCX();
            Integer yCoordinate = bernard.getDCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
//                if (bernard.getItemHeld() == 0){
//                    bernard.setItemHeld(1);
//                    GameScreen.invent.setImage(TextureLoader.INVENTORYSHIELDTEXTURE);
                    invent.play(Constants.MASTERVOLUME);
                    this.dead = true;
                    this.state = 1;
                    this.turnFinished = true;
//                }
                
            }
        }
    }
    
    @Override
    public void performActions(){
        this.turnFinished = true;
    }
}