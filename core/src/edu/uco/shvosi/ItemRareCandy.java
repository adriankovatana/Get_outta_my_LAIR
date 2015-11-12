package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Batch;

public class ItemRareCandy extends Entity {

    private int state = 0;


    public ItemRareCandy(int cX, int cY) {
        super(Constants.EntityGridCode.ITEM, TextureLoader.RARECANDYTEXTURE, cX, cY);
        this.name = "ItemRareCandy";
    }

    @Override
    public void draw(Batch batch, float alpha) {      
            super.draw(batch, alpha);
    }

    @Override
    public void collision(Entity entity) {
        if (entity instanceof Protagonist && !this.turnFinished) {
            Protagonist bernard = (Protagonist) entity;
            Integer xCoordinate = bernard.getCX();
            Integer yCoordinate = bernard.getCY();
            if (xCoordinate == this.getCX() && yCoordinate == this.getCY() && this.state == 0) {
                bernard.addExp(bernard.getExpToLevel());
                this.dead = true;
                this.state = 1;
                this.turnFinished = true;
            }
        }
    }

    @Override
    public void performActions() {
        this.turnFinished = true;
    }
}

