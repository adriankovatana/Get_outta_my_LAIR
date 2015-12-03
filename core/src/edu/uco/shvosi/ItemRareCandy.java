package edu.uco.shvosi;

public class ItemRareCandy extends ItemInstant {

    ItemRareCandy(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.RARECANDYTEXTURE, cX, cY);
        this.name = "ItemRareCandy";
    }

    @Override
    public void collision(Entity entity) {
            Protagonist bernard = super.collision(entity, 0);
            if (bernard != null){
                bernard.addExp(bernard.getExpToLevel());
            }
    }
}


