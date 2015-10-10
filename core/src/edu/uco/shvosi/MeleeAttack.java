package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MeleeAttack extends EnemySkill {

       private TextureRegion temp;

    public MeleeAttack(int x, int y, Animation mainAnimation, int damage) {
        super(x, y, mainAnimation);
        this.damage = damage;
        
        this.name = "MeleeAttack";
    }

    @Override
    public void draw(Batch batch, float alpha, Antagonist e) {
        update();

        

    }
}
