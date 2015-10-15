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

/**
 *
 * @author cody
 */
class RedLaserSkill extends Skill {
    
    public RedLaserSkill() {
        super(0, 0, TextureLoader.redLaser,
                Gdx.audio.newSound(Gdx.files.internal("sounds/attack.mp3")));
        this.damage = 20;
        this.width = 3;
        
        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
    }

}
