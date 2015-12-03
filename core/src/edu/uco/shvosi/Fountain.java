package edu.uco.shvosi;

public class Fountain extends ItemInstant {

    Fountain(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.FOUNTAINTEXTURE, cX, cY);
        this.name = "Fountain";
    }

    @Override
    public void collision(Entity entity) {
            Protagonist bernard = super.collision(entity, 1);
            if (bernard != null){
                bernard.heal(500);
            }
    }
}