package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BarrierSkill extends Skill {

    public BarrierSkill(int x, int y, Animation mainAnimation, int damage) {
        super(x, y, mainAnimation,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill4.mp3")));
        this.damage = damage;
    }
}
