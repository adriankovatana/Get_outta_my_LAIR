package edu.uco.shvosi;

public class ItemDamage extends ItemInstant {

    ItemDamage(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.DAMAGETEXTURE, cX, cY);
        this.name = "ItemDamage";
    }

    @Override
    public void collision(Entity entity) {
            Protagonist bernard = super.collision(entity, 0);
            if (bernard != null){
                bernard.addDamage();
            }
    }
}
