package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TextureLoader {

    //bernard and his powers
    public static final Texture BERNARDTEXTURE = new Texture(Gdx.files.internal("characters/bernard.png"));
    public static final Texture BERNARDSHIELDTEXTURE = new Texture(Gdx.files.internal("characters/bernard_shield.png"));
    public static final Texture HEALTHTEXTURE = new Texture(Gdx.files.internal("items/health.png"));
    public static final Texture REDLASERTEXTURE = new Texture(Gdx.files.internal("RedLaserAnimation.png"));
    public static final Texture DETECTIONTEXTURE = new Texture(Gdx.files.internal("detection.png"));
    public static final Texture SKILLONETEXTURE = new Texture(Gdx.files.internal("skillOne.png"));
    public static final Texture BARRIERTEXTURE = new Texture(Gdx.files.internal("barrier.png"));
    public static final Texture HEALTEXTURE = new Texture(Gdx.files.internal("heal.png"));
    

    private TextureRegion[] detectionFrames;
    private TextureRegion[] barrierFrames;
    private TextureRegion[] healFrames;
    private Array<TextureRegion> laserFrames;
    private Array<TextureRegion> skillOneFrames;
    public static Animation redLaser;
    public static Animation skillOne;
    public static Animation skillTwo;
    public static Animation detectionSkill;
    public static Animation barrierSkill;
    public static Animation heal;

    //antagonist
    public static final Texture MELEETEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture WANDERTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture DRUNKTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture DRUNKENTEXTURE = new Texture(Gdx.files.internal("characters/drunk_sheet.png"));
    public static final Texture WANDTEXTURE = new Texture(Gdx.files.internal("characters/wander_sheet.png"));
    public static final Texture BLANKTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture CATLADYTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture CATTYTEXTURE = new Texture(Gdx.files.internal("characters/catLady_sheet.png"));
    public static final Texture MELEEATTACKTEXTURE = new Texture(Gdx.files.internal("melee_sheet.png"));
    public static final Texture WANDERATTACKTEXTURE = new Texture(Gdx.files.internal("characters/wander_attack_sheet.png"));


    private Array<TextureRegion> drunkFrames;
    private Array<TextureRegion> wanderFrames;
    private Array<TextureRegion> catLadyFrames;
    private Array<TextureRegion> meleeFrames;
    private Array<TextureRegion> wanderAttackFrames;
    public static Animation catLadyWalk;
    public static Animation drunkWalk;
    public static Animation wanderWalk;
    public static Animation meleeAttack;
    public static Animation wanderAttack;



    //traps and items
    public static final Texture TRAPTEXTURE = new Texture(Gdx.files.internal("traps/trap.png"));
    public static final Texture TRAPTEXTURE2 = new Texture(Gdx.files.internal("traps/trap2.png"));
    public static final Texture TRAPTEXTURE3 = new Texture(Gdx.files.internal("traps/trap3.png"));
    public static final Texture TRAPKUNAI = new Texture(Gdx.files.internal("traps/kunai.png"));
    public static final Texture TRAPPOWER = new Texture(Gdx.files.internal("traps/powerseal.png"));
    public static final Texture TRAPBLIND = new Texture(Gdx.files.internal("traps/smoke.png"));
    public static final Texture INVENTORYTEXTURE = new Texture(Gdx.files.internal("Inventory.png"));
    public static final Texture INVENTORYSHIELDTEXTURE = new Texture(Gdx.files.internal("InventoryShield.png"));
    public static final Texture SHIELDTEXTURE = new Texture(Gdx.files.internal("items/shield.png"));
    private TextureRegion[] kunaiFrames;
    private TextureRegion[] powerFrames;
    private TextureRegion[] smokeFrames;
    public static Animation kunaiTrap;
    public static Animation powerTrap;
    public static Animation smokeTrap;

    private static final int FRAME_COLS = 5;
    private static final int FRAME_ROWS = 4;
    private static final int FRAME_ROWS3 = 3;
    private static final int FRAME_ROWS10 = 10;

    TextureLoader() {
        //bernard
        //Big Ass Laser
        laserFrames = new Array<TextureRegion>(15);
        for (int i = 0; i < 15; i++) {
            laserFrames.add(new TextureRegion(REDLASERTEXTURE, 25, i * 156, 731, 156));
        }

        redLaser = new Animation(0.05f, laserFrames, PlayMode.NORMAL);
        //Skill One
        skillOneFrames = new Array<TextureRegion>(6);
        for (int i = 0; i < 6; i++) {
            skillOneFrames.add(new TextureRegion(SKILLONETEXTURE, 25, i * 90, 200, 100));
        }
        skillOne = new Animation(0.05f, skillOneFrames, PlayMode.NORMAL);

        //Skill Two
        skillTwo = new Animation(0.05f, skillOneFrames, PlayMode.LOOP);

        //Detection
        TextureRegion[][] tmp3 = TextureRegion.split(TextureLoader.DETECTIONTEXTURE, TextureLoader.DETECTIONTEXTURE.getWidth() / FRAME_COLS, TextureLoader.DETECTIONTEXTURE.getHeight() / FRAME_COLS);
        detectionFrames = new TextureRegion[FRAME_COLS * FRAME_COLS];
        int index3 = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                detectionFrames[index3++] = tmp3[i][j];
            }
        }
        detectionSkill = new Animation(0.05f, detectionFrames);

        //Barrier
        TextureRegion[][] tmp4 = TextureRegion.split(TextureLoader.BARRIERTEXTURE, TextureLoader.BARRIERTEXTURE.getWidth() / FRAME_COLS, TextureLoader.BARRIERTEXTURE.getHeight() / FRAME_ROWS);
        barrierFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index4 = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                barrierFrames[index4++] = tmp4[i][j];
            }
        }
        barrierSkill = new Animation(0.05f, barrierFrames);
        barrierSkill.setPlayMode(PlayMode.LOOP);

        //Heal
        TextureRegion[][] tmp5 = TextureRegion.split(TextureLoader.HEALTEXTURE, TextureLoader.HEALTEXTURE.getWidth() / FRAME_COLS, TextureLoader.HEALTEXTURE.getHeight() / FRAME_ROWS10);
        healFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS10];
        int index5 = 0;
        for (int i = 0; i < FRAME_ROWS10; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                healFrames[index5++] = tmp5[i][j];
            }
        }
        heal = new Animation(0.03f, healFrames);

        //antagonist
        //Antagonist Drunk
        drunkFrames = new Array<TextureRegion>(6);
        for (int i = 0; i < 6; i++) {
            drunkFrames.add(new TextureRegion(DRUNKENTEXTURE, 25, i * 100, 100, 100));
        }

        drunkWalk = new Animation(0.50f, drunkFrames, PlayMode.LOOP);

        //Antagonist wnaderer
        wanderFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
            wanderFrames.add(new TextureRegion(WANDTEXTURE, 25, i * 100, 100, 100));
        }

        wanderWalk = new Animation(0.50f, wanderFrames, PlayMode.LOOP);
        
        wanderAttackFrames = new Array<TextureRegion>(5);
        for (int i = 0; i < 5; i++) {
            wanderAttackFrames.add(new TextureRegion(WANDERATTACKTEXTURE, 25, i * 100, 100, 100));
        }

        wanderAttack = new Animation(0.10f, wanderAttackFrames, PlayMode.LOOP);

        //Antagonist catlady
        catLadyFrames = new Array<TextureRegion>(3);
        for (int i = 0; i < 3; i++) {
            catLadyFrames.add(new TextureRegion(CATTYTEXTURE, 25, i * 100, 100, 100));
        }

        catLadyWalk = new Animation(0.50f, catLadyFrames, PlayMode.LOOP);
        
        meleeFrames = new Array<TextureRegion>(7);
        for (int i = 0; i < 7; i++) {
            meleeFrames.add(new TextureRegion(MELEEATTACKTEXTURE, 25, i * 100, 100, 100));
        }

        meleeAttack = new Animation(0.03f, meleeFrames, PlayMode.NORMAL);

        //traps and items
        //Kunai Trap
        TextureRegion[][] tmp = TextureRegion.split(TextureLoader.TRAPKUNAI, TextureLoader.TRAPKUNAI.getWidth() / FRAME_COLS, TextureLoader.TRAPKUNAI.getHeight() / FRAME_ROWS);
        kunaiFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                kunaiFrames[index++] = tmp[i][j];
            }
        }
        kunaiTrap = new Animation(0.05f, kunaiFrames);

        //Powerseal Trap
        TextureRegion[][] tmp2 = TextureRegion.split(TextureLoader.TRAPPOWER, TextureLoader.TRAPPOWER.getWidth() / FRAME_COLS, TextureLoader.TRAPPOWER.getHeight() / FRAME_ROWS);
        powerFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index2 = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                powerFrames[index2++] = tmp2[i][j];
            }
        }
        powerTrap = new Animation(0.05f, powerFrames);

        //Blind Trap
        TextureRegion[][] tmp6 = TextureRegion.split(TextureLoader.TRAPBLIND, TextureLoader.TRAPBLIND.getWidth() / 10, TextureLoader.TRAPBLIND.getHeight() / 1);
        smokeFrames = new TextureRegion[10 * 1];
        int index6 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 10; j++) {
                smokeFrames[index6++] = tmp6[i][j];
            }
        }
        smokeTrap = new Animation(0.05f, smokeFrames);
    }

    public void dispose() {
        BERNARDTEXTURE.dispose();
        WANDERTEXTURE.dispose();
        DRUNKTEXTURE.dispose();
        DRUNKENTEXTURE.dispose();
        HEALTHTEXTURE.dispose();
        TRAPTEXTURE.dispose();
        TRAPTEXTURE2.dispose();
        TRAPTEXTURE3.dispose();
        TRAPKUNAI.dispose();
        TRAPPOWER.dispose();
        TRAPBLIND.dispose();
        DETECTIONTEXTURE.dispose();
        BARRIERTEXTURE.dispose();
        HEALTEXTURE.dispose();
        REDLASERTEXTURE.dispose();
        SKILLONETEXTURE.dispose();
        BLANKTEXTURE.dispose();
        CATTYTEXTURE.dispose();
        CATLADYTEXTURE.dispose();

    }
}
