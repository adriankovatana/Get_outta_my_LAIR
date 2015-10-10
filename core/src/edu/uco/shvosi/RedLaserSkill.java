/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author cody
 */
class RedLaserSkill extends Skill {
    
    public RedLaserSkill(int x, int y, Animation mainAnimation, int damage) {
        super(x, y, mainAnimation);
        this.damage = damage;
    }

}
