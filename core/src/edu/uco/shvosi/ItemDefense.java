package edu.uco.shvosi;

public class ItemDefense extends ItemInstant {

    ItemDefense(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.DEFENSETEXTURE, cX, cY);
        this.name = "ItemDefense";
    }

    @Override
    public void collision(Entity entity) {
            Protagonist bernard = super.collision(entity, 0);
            if (bernard != null){
                bernard.addDefense();
            }
    }
}
