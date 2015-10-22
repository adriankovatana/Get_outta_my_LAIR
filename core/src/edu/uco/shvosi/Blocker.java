package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Blocker extends Trap {

    private Constants.Direction dir = Constants.Direction.LEFT;
    
    Blocker(int cX, int cY) {
        super(TextureLoader.BLOCKERTEXTURE, cX, cY, Constants.TrapType.BLOCKER,
        Gdx.audio.newSound(Gdx.files.internal("sounds/trap2.mp3")));
        this.setVisible(false);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
    }

    @Override
    public void collision(Entity entity) {
    }   
}
