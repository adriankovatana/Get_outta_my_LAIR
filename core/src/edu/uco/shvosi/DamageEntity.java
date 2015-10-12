package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;

public class DamageEntity extends Entity{
    
    private int damage;

    public DamageEntity(int cX, int cY, int damage) {
        super(Constants.EntityGridCode.NONE, TextureLoader.BLANKTEXTURE, cX, cY);
        this.damage = damage;
        
        this.name = "Damage Entity";
    }
    
    @Override
    public void collision(Entity entity) {
        if(entity instanceof Protagonist){
            Protagonist bernard = (Protagonist) entity;
            bernard.takeDamage(this.damage);
        }
        else if(entity instanceof Antagonist){
            Antagonist enemy = (Antagonist) entity;
            enemy.takeDamage(this.damage);
        }
        this.setDead(true);
    }
    
    @Override
    public void performDeath() {
        this.remove();
    }
    
}
