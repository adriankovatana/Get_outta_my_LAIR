/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


class BottleSkill extends Skill {

    private Animation bottle;
   
    
    public BottleSkill() {
        super(0, 0, TextureLoader.bottle,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill1.mp3")));
        this.damage = 10;
        this.width = 4;

        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
        //this.damageEntities.add(new DamageEntity(0, 0, this.damage));

        bottle = TextureLoader.bottle;
    }

    public void draw(Batch batch, float alpha, Entity entity) {
        this.update();
        batch.draw(animation.getKeyFrame(elapsed), entity.getX(), entity.getY(), Constants.TILEDIMENSION *4, Constants.TILEDIMENSION *4);
        
    }

    public int getDamage() {
        return this.damage;
    }

}
