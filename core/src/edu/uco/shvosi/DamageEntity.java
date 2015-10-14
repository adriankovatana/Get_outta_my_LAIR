package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
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
//        if(entity instanceof Protagonist && !this.turnFinished){
//            Protagonist bernard = (Protagonist) entity;
//            if(bernard.getCX() == this.getCX() && bernard.getCY() == this.getCY()){
//                bernard.takeDamage(this.damage);
//                Gdx.app.log(this.name, "Did " + this.damage + " to " + bernard.getName());
////                this.setDead(true);
////                this.turnFinished = true;
//            }
//        }
//        else if(entity instanceof Antagonist && !this.turnFinished){
//            Antagonist enemy = (Antagonist) entity;
//            if(enemy.getCX() == this.getCX() && enemy.getCY() == this.getCY()){
//                enemy.takeDamage(this.damage);
//                Gdx.app.log(this.name, "Did " + this.damage + " to " + enemy.getName());
////                this.setDead(true);
////                this.turnFinished = true;
//            }
//        }
//        this.setDead(true);
//        this.turnFinished = true;
        if(entity instanceof Protagonist){
            Protagonist bernard = (Protagonist) entity;
            if(bernard.getCX() == this.getCX() && bernard.getCY() == this.getCY()){
                bernard.takeDamage(this.damage);
                Gdx.app.log(this.name, "Did " + this.damage + " to " + bernard.getName());
//                this.setDead(true);
//                this.turnFinished = true;
            }
        }
        else if(entity instanceof Antagonist){
            Antagonist enemy = (Antagonist) entity;
            if(enemy.getCX() == this.getCX() && enemy.getCY() == this.getCY()){
                enemy.takeDamage(this.damage);
                Gdx.app.log(this.name, "Did " + this.damage + " to " + enemy.getName());
//                this.setDead(true);
//                this.turnFinished = true;
            }
        }
        else{
            Gdx.app.log(this.name, "Missed");
        }
        this.setDead(true);
        //this.turnFinished = true;
    }
    
    @Override
    public void performDeath() {
        //this.remove();
    }
    
}
