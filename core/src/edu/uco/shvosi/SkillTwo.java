/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author cody
 */
class SkillTwo extends Skill {

    private float rotation;

    public SkillTwo() {
        super(0, 0, TextureLoader.skillTwo);
        this.damage = 10;
        rotation = 0f;
    }
}
