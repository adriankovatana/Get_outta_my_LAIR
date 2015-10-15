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
class SkillOne extends Skill {
    
    public SkillOne() {
        super(0, 0, TextureLoader.skillOne,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill1.mp3")));
        this.damage = 10;
        this.width = 2;
        
        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
        this.damageEntities.add(new DamageEntity(0, 0, this.damage));
    }

    public boolean isAnimationFinished() {
        if (this.animation.isAnimationFinished(this.elapsed)) {
            this.elapsed = 0f;
            return true;
        } else {
            return false;
        }
    }
    
    public int getDamage() {
        return this.damage;
    }
}
