package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

public class DamageEntity extends Entity {

    private int damage;
    private Constants.SkillName skill;

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public DamageEntity(int cX, int cY, int damage) {
        super(Constants.EntityGridCode.NONE, TextureLoader.BLANKTEXTURE, cX, cY);
        this.damage = damage;

        this.name = "Damage Entity";
    }

    public DamageEntity(int cX, int cY, int damage, Constants.SkillName skill) {
        super(Constants.EntityGridCode.NONE, TextureLoader.BLANKTEXTURE, cX, cY);
        this.damage = damage;
        this.skill = skill;
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
        if (entity instanceof Protagonist) {
            Protagonist bernard = (Protagonist) entity;
            if (bernard.getCX() == this.getCX() && bernard.getCY() == this.getCY()) {
                int adjustedDamage = this.damage - bernard.getDefense();
                if (adjustedDamage < 1){
                    adjustedDamage = 1;
                }               
                bernard.takeDamage(adjustedDamage);
                Gdx.app.log(this.name, "Did " + adjustedDamage + " to " + bernard.getName());
            }
        } else if (entity instanceof Antagonist) {
            Antagonist enemy = (Antagonist) entity;
            if (enemy.getCX() == this.getCX() && enemy.getCY() == this.getCY()) {
                if (skill == Constants.SkillName.LASERSKILL) {
                    enemy.setIgnite(true);
                    enemy.setIgniteCounter(5);
//                    Gdx.app.log("Ignite", "Skill");
                } else if (skill == Constants.SkillName.FREEZINGSKILL) {
                    if (statusEffectChance() == 0) {
                        enemy.setFrost(true);
                        enemy.setFrostBiteCounter(3);
//                        Gdx.app.log("FrostBite", "Skill");
                    }
                }
                enemy.takeDamage(this.damage);
                Gdx.app.log(this.name, "Did " + this.damage + " to " + enemy.getName());
//                this.setDead(true);
//                this.turnFinished = true;
            }
        } else {
            Gdx.app.log(this.name, "Missed");
        }
        this.setDead(true);
        //this.turnFinished = true;
    }

    @Override
    public void performDeath() {
        //this.remove();
    }

    public int statusEffectChance() {
        Random rand = new Random();
        int randomNumber = rand.nextInt((4 - 0) + 1);
        return randomNumber;
    }
}
