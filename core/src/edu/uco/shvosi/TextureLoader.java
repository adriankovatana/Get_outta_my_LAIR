package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class TextureLoader {

    public static final Skin SKIN = new Skin(Gdx.files.internal("ui/uiskin.json"));

    //bernard and his powers
    public static final Texture BERNARDTEXTURE = new Texture(Gdx.files.internal("characters/bernard2.png"));
    public static final Texture DORABERNARDJUMPTEXTURE = new Texture(Gdx.files.internal("characters/doraJump.png"));
    public static final Texture BERNARDSHIELDTEXTURE = new Texture(Gdx.files.internal("items/shieldbernard.png"));
    public static final Texture BERNARDGLANCETEXTURE = new Texture(Gdx.files.internal("characters/bernard_sheet.png"));
    public static final Texture BERNARDDEATHTEXTURE = new Texture(Gdx.files.internal("characters/bernard_death.png"));
    public static final Texture HEALTHTEXTURE = new Texture(Gdx.files.internal("items/health.png"));
    public static final Texture REDLASERTEXTURE = new Texture(Gdx.files.internal("RedLaserAnimation.png"));
    public static final Texture DETECTIONTEXTURE = new Texture(Gdx.files.internal("detection.png"));
    public static final Texture SKILLONETEXTURE = new Texture(Gdx.files.internal("skillOne.png"));
    public static final Texture BARRIERTEXTURE = new Texture(Gdx.files.internal("barrier.png"));
    public static final Texture HEALTEXTURE = new Texture(Gdx.files.internal("heal.png"));
    public static final Texture LIGHTBARRIERTEXTURE = new Texture(Gdx.files.internal("lightBarrier.png"));
    public static final Texture ICICLETEXTURE = new Texture(Gdx.files.internal("icicle.png"));
    public static final Texture LIGHTTEXTURE = new Texture(Gdx.files.internal("lightning.png"));
    public static final Texture LIGHTTEXTURE2 = new Texture(Gdx.files.internal("lightning2.png"));

    private TextureRegion[] detectionFrames;
    private TextureRegion[] barrierFrames;
    private TextureRegion[] healFrames;
    private TextureRegion[] jumpFrames;
    private TextureRegion[] lightBarrierFrames;
    private TextureRegion[] bernardDeathFrames;
    private TextureRegion[] icicleFrames;
    private TextureRegion[] light2Frames;
    private Array<TextureRegion> laserFrames;
    private Array<TextureRegion> lightFrames;
    private Array<TextureRegion> skillOneFrames;
    private Array<TextureRegion> bernardGlanceFrames;

    public static Animation bernardGlance;
    public static Animation bernardDeath;
    public static Animation redLaser;
    public static Animation skillOne;
    public static Animation skillTwo;
    public static Animation detectionSkill;
    public static Animation barrierSkill;
    public static Animation heal;
    public static Animation jump;
    public static Animation lightBarrier;
    public static Animation light;
    public static Animation icicle;
    public static Animation light2;

    //antagonist
    public static final Texture CLYDETEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture BONNIETEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture SUFFERTETEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture HAMMERTETEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture BLUESTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture WREKERTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture MELEETEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture WANDERTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture DRUNKTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture WREKINGTEXTURE = new Texture(Gdx.files.internal("characters/wreker_sheet.png"));
    public static final Texture HAMTEXTURE = new Texture(Gdx.files.internal("characters/hammer_sheet.png"));
    public static final Texture DRUNKENTEXTURE = new Texture(Gdx.files.internal("characters/drunk_sheet.png"));
    public static final Texture WANDTEXTURE = new Texture(Gdx.files.internal("characters/wander_sheet.png"));
    public static final Texture BLUETEXTURE = new Texture(Gdx.files.internal("characters/blues_sheet.png"));
    public static final Texture BLANKTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture CATLADYTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture CATTYTEXTURE = new Texture(Gdx.files.internal("characters/catLady_sheet.png"));
    public static final Texture CATTEXTURE = new Texture(Gdx.files.internal("characters/blank.png"));
    public static final Texture CATWALKTEXTURE = new Texture(Gdx.files.internal("characters/catwalk.png"));
    public static final Texture CATATTACKTEXTURE = new Texture(Gdx.files.internal("characters/catattack.png"));
    public static final Texture MELEEATTACKTEXTURE = new Texture(Gdx.files.internal("melee_sheet.png"));
    public static final Texture WANDERATTACKTEXTURE = new Texture(Gdx.files.internal("characters/wander_attack_sheet.png"));
    public static final Texture DRUNKATTACKTEXTURE = new Texture(Gdx.files.internal("characters/drunk_attack.png"));
    public static final Texture WREKERATTACKTEXTURE = new Texture(Gdx.files.internal("characters/wreker_attack_sheet.png"));
    public static final Texture SUFFRAGETTETEXTURE = new Texture(Gdx.files.internal("characters/suffragette_sheet.png"));
    public static final Texture HAMMERATTACKTETEXTURE = new Texture(Gdx.files.internal("characters/hammer_attack.png"));
    public static final Texture GREYGATELTEXTURE = new Texture(Gdx.files.internal("characters/greygatel.png"));
    public static final Texture GREYGATERTEXTURE = new Texture(Gdx.files.internal("characters/greygater.png"));
    public static final Texture REDGATELTEXTURE = new Texture(Gdx.files.internal("characters/redgatel.png"));
    public static final Texture REDGATERTEXTURE = new Texture(Gdx.files.internal("characters/redgater.png"));
    public static final Texture DEATHTEXTURE = new Texture(Gdx.files.internal("death.png"));
    public static final Texture FROSTBITETEXTURE = new Texture(Gdx.files.internal("frostbite.png"));
    public static final Texture CATLADYTRANSFORMTEXTURE = new Texture(Gdx.files.internal("characters/big_cat_attack.png"));
    public static final Texture MOONSHINERTEXTURE = new Texture(Gdx.files.internal("characters/moonshine.png"));
    public static final Texture MOONSHINERWALKTEXTURE = new Texture(Gdx.files.internal("characters/moonshine_walk_sheet.png"));
    public static final Texture MOONSHINERATTACKTEXTURE = new Texture(Gdx.files.internal("characters/moonshine_attack_sheet.png"));
    public static final Texture BONNIEWALKTEXTURE = new Texture(Gdx.files.internal("characters/bonnie_walk.png"));
    public static final Texture BONNIEATTACKTEXTURE = new Texture(Gdx.files.internal("characters/bonnie_attack.png"));
    public static final Texture CLYDEWALKTEXTURE = new Texture(Gdx.files.internal("characters/clyde_walk_sheet.png"));
    public static final Texture CLYDEATTACKTEXTURE = new Texture(Gdx.files.internal("characters/clyde_attack_sheet.png"));

    private Array<TextureRegion> catFrames;
    private Array<TextureRegion> bonnieFrames;
    private Array<TextureRegion> clydeFrames;
    private Array<TextureRegion> hammerFrames;
    private Array<TextureRegion> blueFrames;
    private Array<TextureRegion> drunkFrames;
    private Array<TextureRegion> wanderFrames;
    private Array<TextureRegion> catLadyFrames;
    private Array<TextureRegion> wrekerFrames;
    private Array<TextureRegion> meleeFrames;
    private Array<TextureRegion> wanderAttackFrames;
    private Array<TextureRegion> bonnieAttackFrames;
    private Array<TextureRegion> clydeAttackFrames;
    private Array<TextureRegion> wrekerAttackFrames;
    private Array<TextureRegion> drunkAttackFrames;
    private Array<TextureRegion> suffragetteFrames;
    private Array<TextureRegion> hammerAttackFrames;
    private Array<TextureRegion> catAttackFrames;
    private Array<TextureRegion> catLadyTransformFrames;
    private TextureRegion[] deathFrames;
    private TextureRegion[] frostBiteFrames;
    private Array<TextureRegion> moonshinerWalkFrames;
    private Array<TextureRegion> moonshinerAttackFrames;

    public static Animation catWalk;
    public static Animation bonnieWalk;
    public static Animation clydeWalk;
    public static Animation blueWalk;
    public static Animation hammerWalk;
    public static Animation catLadyWalk;
    public static Animation drunkWalk;
    public static Animation wanderWalk;
    public static Animation wrekerWalk;
    public static Animation meleeAttack;
    public static Animation wanderAttack;
    public static Animation drunkAttack;
    public static Animation wrekerAttack;
    public static Animation suffragetteWalk;
    public static Animation hammerAttack;
    public static Animation catAttack;
    public static Animation catLadyAttack;
    public static Animation death;
    public static Animation frost;
    public static Animation moonshinerAttack;
    public static Animation moonshinerWalk;
    public static Animation bonnieAttack;
    public static Animation clydeAttack;


    //skills
    public static final Texture MELEESKILLTEXTURE = new Texture(Gdx.files.internal("melee_sheet.png"));
    public static final Texture BLUESKILLTEXTURE = new Texture(Gdx.files.internal("blue_skill_sheet.png"));
    public static final Texture HAMMERDOWNTEXTURE = new Texture(Gdx.files.internal("hammer_down_sheet.png"));
    public static final Texture BOTTLESKILLTEXTURE = new Texture(Gdx.files.internal("characters/bottle_sheet.png"));

    private Array<TextureRegion> blueSkillFrames;
    private Array<TextureRegion> hammerDownFrames;
    private Array<TextureRegion> meleeSkillFrames;
    private Array<TextureRegion> bottleSkillFrames;
    public static Animation blueSkill;
    public static Animation hammerDownSkill;
    public static Animation meleeSkill;
    public static Animation bottleSkill;
    //end skills

    //traps and items
    public static final Texture TRAPTEXTURE = new Texture(Gdx.files.internal("traps/trap.png"));
    public static final Texture TRAPTEXTURE2 = new Texture(Gdx.files.internal("traps/trap2.png"));
    public static final Texture TRAPTEXTURE3 = new Texture(Gdx.files.internal("traps/trap3.png"));
    public static final Texture TRAPKUNAI = new Texture(Gdx.files.internal("traps/kunai.png"));
    public static final Texture TRAPPOWER = new Texture(Gdx.files.internal("traps/powerseal.png"));
    public static final Texture TRAPBLIND = new Texture(Gdx.files.internal("traps/smoke.png"));
    public static final Texture TRAPTRANSFORM = new Texture(Gdx.files.internal("traps/transform.png"));
    public static final Texture SHIELDTEXTURE = new Texture(Gdx.files.internal("items/shield.png"));
    public static final Texture DEFENSETEXTURE = new Texture(Gdx.files.internal("items/defense.png"));
    public static final Texture DAMAGETEXTURE = new Texture(Gdx.files.internal("items/damage.png"));
    public static final Texture RARECANDYTEXTURE = new Texture(Gdx.files.internal("items/rarecandy.png"));
    public static final Texture WHISTLETEXTURE = new Texture(Gdx.files.internal("items/whistle.png"));
    public static final Texture REDKEYTEXTURE = new Texture(Gdx.files.internal("items/redkey.png"));
    public static final Texture GREYKEYTEXTURE = new Texture(Gdx.files.internal("items/greykey.png"));
    public static final Texture BLOCKERTEXTURE = new Texture(Gdx.files.internal("traps/stop.png"));
    public static final Texture FOUNTAINTEXTURE = new Texture(Gdx.files.internal("fountain.png"));

    public static final Texture INVENTORYTEXTURE = new Texture(Gdx.files.internal("invent/Inventory.png"));
    public static final Texture INVENTORYSHIELDTEXTURE = new Texture(Gdx.files.internal("invent/InventoryShield.png"));
    public static final Texture INVENTORYWHISTLETEXTURE = new Texture(Gdx.files.internal("invent/InventoryWhistle.png"));
    public static final Texture INVENTORYREDKEYTEXTURE = new Texture(Gdx.files.internal("invent/InventoryRedKey.png"));
    public static final Texture INVENTORYGREYKEYTEXTURE = new Texture(Gdx.files.internal("invent/InventoryGreyKey.png"));

    private TextureRegion[] kunaiFrames;
    private TextureRegion[] powerFrames;
    private TextureRegion[] smokeFrames;
    private TextureRegion[] transformFrames;
    public static Animation kunaiTrap;
    public static Animation powerTrap;
    public static Animation smokeTrap;
    public static Animation transformTrap;

    //slide tiles
    public static final Texture SLIDELEFT = new Texture(Gdx.files.internal("traps/slidetiles/leftarrow.png"));
    public static final Texture SLIDERIGHT = new Texture(Gdx.files.internal("traps/slidetiles/rightarrow.png"));
    public static final Texture SLIDEUP = new Texture(Gdx.files.internal("traps/slidetiles/uparrow.png"));
    public static final Texture SLIDEDOWN = new Texture(Gdx.files.internal("traps/slidetiles/downarrow.png"));

    //UI
    public static final Texture HUD = new Texture(Gdx.files.internal("ui/HUD.png"));
    public static final Texture HEALTHPOOL = new Texture(Gdx.files.internal("ui/healthpool.png"));
    public static final Texture HPBARBACKGROUND = new Texture(Gdx.files.internal("ui/healthbarbackground.png"));
    public static final Texture HPBARFILL = new Texture(Gdx.files.internal("ui/healthbarfill.png"));
    public static final Texture XPBARFILL = new Texture(Gdx.files.internal("ui/xpfill.png"));
    public static final Texture TARGETSQUARE = new Texture(Gdx.files.internal("ui/square.png"));
    public static final Texture SKILLONEICON = new Texture(Gdx.files.internal("ui/icons/basiclaser.png"));
    public static final Texture SKILLTWOICON = new Texture(Gdx.files.internal("ui/icons/rotatelaser.png"));
    public static final Texture DETECTICON = new Texture(Gdx.files.internal("ui/icons/detect.png"));
    public static final Texture BARRIERICON = new Texture(Gdx.files.internal("ui/icons/barrier.png"));
    public static final Texture LIGHTINFUSEICON = new Texture(Gdx.files.internal("ui/icons/lightinfuse.png"));
    public static final Texture REDLASERICON = new Texture(Gdx.files.internal("ui/icons/redlaser.png"));
    public static final Texture FUSIONICON = new Texture(Gdx.files.internal("ui/icons/fuse.png"));

    private static final int FRAME_COLS = 5;
    private static final int FRAME_ROWS = 4;
    private static final int FRAME_ROWS3 = 3;
    private static final int FRAME_ROWS10 = 10;

    TextureLoader() {
        //bernard
        //Bernard's animation
        bernardGlanceFrames = new Array<TextureRegion>();
        for (int i = 0; i < 5; i++) {

            bernardGlanceFrames.add(new TextureRegion(BERNARDGLANCETEXTURE, 0, i * 100, 100, 100));
        }
        bernardGlance = new Animation(0.4f, bernardGlanceFrames, PlayMode.NORMAL);

        //Bernard's Death Animation
        TextureRegion[][] tmp10 = TextureRegion.split(TextureLoader.BERNARDDEATHTEXTURE, TextureLoader.BERNARDDEATHTEXTURE.getWidth() / 1, TextureLoader.BERNARDDEATHTEXTURE.getHeight() / 5);
        bernardDeathFrames = new TextureRegion[5 * 1];
        int index10 = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 1; j++) {
                bernardDeathFrames[index10++] = tmp10[i][j];
            }
        }
        bernardDeath = new Animation(0.3f, bernardDeathFrames);

        //Icicle
        TextureRegion[][] tmp12 = TextureRegion.split(TextureLoader.ICICLETEXTURE, TextureLoader.ICICLETEXTURE.getWidth() / 5, TextureLoader.ICICLETEXTURE.getHeight() / 3);
        icicleFrames = new TextureRegion[5 * 3];
        int index12 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                icicleFrames[index12++] = tmp12[i][j];
            }
        }
        icicle = new Animation(0.05f, icicleFrames);

        //Lightning 2
        TextureRegion[][] tmp13 = TextureRegion.split(TextureLoader.LIGHTTEXTURE2, TextureLoader.LIGHTTEXTURE2.getWidth() / 5, TextureLoader.LIGHTTEXTURE2.getHeight() / 3);
        light2Frames = new TextureRegion[5 * 3];
        int index13 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                light2Frames[index13++] = tmp13[i][j];
            }
        }
        light2 = new Animation(0.05f, light2Frames);

        //Big Ass Laser
        laserFrames = new Array<TextureRegion>(15);
        for (int i = 0; i < 15; i++) {
            laserFrames.add(new TextureRegion(REDLASERTEXTURE, 25, i * 156, 731, 156));
        }
        redLaser = new Animation(0.05f, laserFrames, PlayMode.NORMAL);

        //Dora's Jump
        TextureRegion[][] tmp8 = TextureRegion.split(TextureLoader.DORABERNARDJUMPTEXTURE, TextureLoader.DORABERNARDJUMPTEXTURE.getWidth() / 8, TextureLoader.DORABERNARDJUMPTEXTURE.getHeight() / 1);
        jumpFrames = new TextureRegion[8 * 1];
        int index8 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) {
                jumpFrames[index8++] = tmp8[i][j];
            }
        }
        jump = new Animation(0.1f, jumpFrames);

        //Skill One
        skillOneFrames = new Array<TextureRegion>(6);
        for (int i = 0; i < 6; i++) {
            skillOneFrames.add(new TextureRegion(SKILLONETEXTURE, 25, i * 90, 200, 100));
        }
        skillOne = new Animation(0.05f, skillOneFrames, PlayMode.NORMAL);

        //Skill Two
        skillTwo = new Animation(0.05f, skillOneFrames, PlayMode.LOOP);

        //Lightning added
        lightFrames = new Array<TextureRegion>(6);
        for (int i = 0; i < 6; i++) {
            lightFrames.add(new TextureRegion(LIGHTTEXTURE, 25, i * 50, 180, 60));
        }
        light = new Animation(0.05f, lightFrames, PlayMode.NORMAL);

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
        barrierSkill = new Animation(0.02f, barrierFrames);
        barrierSkill.setPlayMode(PlayMode.LOOP);

        //Light Barrier
        TextureRegion[][] tmp9 = TextureRegion.split(TextureLoader.LIGHTBARRIERTEXTURE, TextureLoader.LIGHTBARRIERTEXTURE.getWidth() / 4, TextureLoader.LIGHTBARRIERTEXTURE.getHeight() / 4);
        lightBarrierFrames = new TextureRegion[4 * 4];
        int index9 = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                lightBarrierFrames[index9++] = tmp9[i][j];
            }
        }
        lightBarrier = new Animation(0.04f, lightBarrierFrames);
        lightBarrier.setPlayMode(PlayMode.LOOP);

        //Heal
        TextureRegion[][] tmp5 = TextureRegion.split(TextureLoader.HEALTEXTURE, TextureLoader.HEALTEXTURE.getWidth() / FRAME_COLS, TextureLoader.HEALTEXTURE.getHeight() / FRAME_ROWS10);
        healFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS10];
        int index5 = 0;
        for (int i = 0; i < FRAME_ROWS10; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                healFrames[index5++] = tmp5[i][j];
            }
        }
        heal = new Animation(0.02f, healFrames);

        //antagonist
        //Antagonist's Death Animation
        TextureRegion[][] tmp11 = TextureRegion.split(TextureLoader.DEATHTEXTURE, TextureLoader.DEATHTEXTURE.getWidth() / FRAME_ROWS, TextureLoader.DEATHTEXTURE.getHeight() / FRAME_ROWS);
        deathFrames = new TextureRegion[FRAME_ROWS * FRAME_ROWS];
        int index11 = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_ROWS; j++) {
                deathFrames[index11++] = tmp11[i][j];
            }
        }
        death = new Animation(0.02f, deathFrames);

        //Antagonist's FrostBite Animation
        TextureRegion[][] tmp14 = TextureRegion.split(TextureLoader.FROSTBITETEXTURE, TextureLoader.FROSTBITETEXTURE.getWidth() / 5, TextureLoader.FROSTBITETEXTURE.getHeight() / 3);
        frostBiteFrames = new TextureRegion[5 * 3];
        int index14 = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                frostBiteFrames[index14++] = tmp14[i][j];
            }
        }
        frost = new Animation(0.05f, frostBiteFrames);
        frost.setPlayMode(PlayMode.LOOP);

        //Antagonist Drunk
        drunkFrames = new Array<TextureRegion>(6);
        for (int i = 0; i < 6; i++) {
            drunkFrames.add(new TextureRegion(DRUNKENTEXTURE, 25, i * 100, 100, 100));
        }

        drunkWalk = new Animation(0.50f, drunkFrames, PlayMode.LOOP);
        drunkAttackFrames = new Array<TextureRegion>(5);
        for (int i = 0; i < 5; i++) {
            drunkAttackFrames.add(new TextureRegion(DRUNKATTACKTEXTURE, 25, i * 100, 100, 100));
        }

        drunkAttack = new Animation(0.10f, drunkAttackFrames, PlayMode.LOOP);
        //Antagonist Bonnie
        bonnieFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
        bonnieFrames.add(new TextureRegion(BONNIEWALKTEXTURE, 25, i * 100, 100, 100));
        }

        bonnieWalk = new Animation(0.50f, bonnieFrames, PlayMode.LOOP);
        bonnieAttackFrames = new Array<TextureRegion>(5);
        for (int i = 0; i < 5; i++) {
            bonnieAttackFrames.add(new TextureRegion(BONNIEATTACKTEXTURE, 25, i * 100, 100, 100));
        }

        bonnieAttack = new Animation(0.10f, bonnieAttackFrames, PlayMode.LOOP);
        //Antagonist Clyde
        clydeFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
        clydeFrames.add(new TextureRegion(CLYDEWALKTEXTURE, 25, i * 100, 100, 100));
        }

        clydeWalk = new Animation(0.50f, clydeFrames, PlayMode.LOOP);
        clydeAttackFrames = new Array<TextureRegion>(5);
        for (int i = 0; i < 5; i++) {
            clydeAttackFrames.add(new TextureRegion(CLYDEATTACKTEXTURE, 25, i * 100, 100, 100));
        }

        clydeAttack = new Animation(0.10f, clydeAttackFrames, PlayMode.LOOP);        
        //Antagonist Blues
        blueFrames = new Array<TextureRegion>(5);
        for (int i = 0; i < 5; i++) {
            blueFrames.add(new TextureRegion(BLUETEXTURE, 25, i * 100, 100, 100));
        }

        blueWalk = new Animation(0.50f, blueFrames, PlayMode.LOOP);
        //
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
        //hammer
        hammerFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
            hammerFrames.add(new TextureRegion(HAMTEXTURE, 25, i * 100, 100, 100));
        }

        hammerWalk = new Animation(0.50f, hammerFrames, PlayMode.LOOP);

        hammerAttackFrames = new Array<TextureRegion>(7);
        for (int i = 0; i < 7; i++) {
            hammerAttackFrames.add(new TextureRegion(HAMMERATTACKTETEXTURE, 25, i * 100, 100, 100));
        }

        hammerAttack = new Animation(0.10f, hammerAttackFrames, PlayMode.LOOP);
        //cat
        catFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
            catFrames.add(new TextureRegion(CATWALKTEXTURE, 0, i * 100, 100, 100));
        }

        catWalk = new Animation(0.50f, catFrames, PlayMode.LOOP);

        catAttackFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
            catAttackFrames.add(new TextureRegion(CATATTACKTEXTURE, 0, i * 100, 100, 100));
        }

        catAttack = new Animation(0.10f, catAttackFrames, PlayMode.LOOP);

        //
        //
        //sufferagette
        suffragetteFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
            suffragetteFrames.add(new TextureRegion(SUFFRAGETTETEXTURE, 25, i * 100, 100, 100));
        }

        suffragetteWalk = new Animation(0.50f, suffragetteFrames, PlayMode.LOOP);
        //
        //antagonist wreker
        wrekerFrames = new Array<TextureRegion>(2);
        for (int i = 0; i < 2; i++) {
            wrekerFrames.add(new TextureRegion(WREKINGTEXTURE, 25, i * 100, 100, 100));
        }

        wrekerWalk = new Animation(0.50f, wrekerFrames, PlayMode.LOOP);

        wrekerAttackFrames = new Array<TextureRegion>(4);
        for (int i = 0; i < 4; i++) {
            wrekerAttackFrames.add(new TextureRegion(WREKERATTACKTEXTURE, 25, i * 100, 100, 100));
        }

        wrekerAttack = new Animation(0.10f, wrekerAttackFrames, PlayMode.LOOP);

        //
        //Antagonist catlady
        catLadyFrames = new Array<TextureRegion>(3);
        for (int i = 0; i < 3; i++) {
            catLadyFrames.add(new TextureRegion(CATTYTEXTURE, 25, i * 100, 100, 100));
        }

        catLadyWalk = new Animation(0.50f, catLadyFrames, PlayMode.LOOP);

        //catLady is Tranforming
        catLadyTransformFrames = new Array<TextureRegion>(4);
        for (int i = 0; i < 4; i++) {
            catLadyTransformFrames.add(new TextureRegion(CATLADYTRANSFORMTEXTURE, 25, i * 100, 100, 100));
        }

        catLadyAttack = new Animation(0.50f, catLadyTransformFrames, PlayMode.LOOP);

        //melee skill
        meleeFrames = new Array<TextureRegion>(7);
        for (int i = 0; i < 7; i++) {
            meleeFrames.add(new TextureRegion(MELEEATTACKTEXTURE, 25, i * 100, 100, 100));
        }

        meleeAttack = new Animation(0.03f, meleeFrames, PlayMode.NORMAL);

        //skills
        //blue
        blueSkillFrames = new Array<TextureRegion>(12);
        for (int i = 0; i < 12; i++) {
            blueSkillFrames.add(new TextureRegion(BLUESKILLTEXTURE, 0, i * 600, 600, 600));
        }

        blueSkill = new Animation(0.03f, blueSkillFrames, PlayMode.LOOP);
        //hammer down
        hammerDownFrames = new Array<TextureRegion>(6);
        for (int i = 0; i < 6; i++) {
            hammerDownFrames.add(new TextureRegion(HAMMERDOWNTEXTURE, 0, i * 500, 500, 500));
        }

        hammerDownSkill = new Animation(0.1f, hammerDownFrames, PlayMode.NORMAL);
        //melee
        meleeSkillFrames = new Array<TextureRegion>(7);
        for (int i = 0; i < 7; i++) {
            meleeSkillFrames.add(new TextureRegion(MELEESKILLTEXTURE, 25, i * 100, 100, 100));
        }

        meleeSkill = new Animation(0.03f, meleeSkillFrames, PlayMode.NORMAL);

        //bottle
        bottleSkillFrames = new Array<TextureRegion>(22);
        for (int i = 0; i < 22; i++) {
            bottleSkillFrames.add(new TextureRegion(BOTTLESKILLTEXTURE, 25, i * 100, 100, 100));
        }

        bottleSkill = new Animation(0.15f, bottleSkillFrames, PlayMode.NORMAL);

        
        //====================\\
        //     Moonshiner     \\
        //====================\\
        moonshinerWalkFrames = new Array<TextureRegion>(2);

        for (int i = 0; i < 2; i++) {
            moonshinerWalkFrames.add(new TextureRegion(MOONSHINERWALKTEXTURE, 0, i * 100, 100, 100));
        }

        moonshinerWalk = new Animation(0.3f, moonshinerWalkFrames, PlayMode.LOOP);

        moonshinerAttackFrames = new Array<TextureRegion>(7);

        for (int i = 0; i < 2; i++) {
            moonshinerAttackFrames.add(new TextureRegion(MOONSHINERATTACKTEXTURE, 0, i * 100, 100, 100));
        }

        moonshinerAttack = new Animation(0.03f, moonshinerAttackFrames, PlayMode.NORMAL);
        
                for(int i = 0; i < 9; i++)
        {
          bottleSkillFrames.add(new TextureRegion(BOTTLESKILLTEXTURE, 0, i * 100, 125, 100));
        }
        
        bottleSkill = new Animation(0.05f, bottleSkillFrames, PlayMode.NORMAL);

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

        //Transform Trap
        TextureRegion[][] tmp7 = TextureRegion.split(TextureLoader.TRAPTRANSFORM, TextureLoader.TRAPTRANSFORM.getWidth() / FRAME_COLS, TextureLoader.TRAPTRANSFORM.getHeight() / FRAME_ROWS);
        transformFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index7 = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                transformFrames[index7++] = tmp7[i][j];
            }
        }
        transformTrap = new Animation(0.05f, transformFrames);
    }

    public void dispose() {
        //A

        //B
        BARRIERTEXTURE.dispose();
        BARRIERICON.dispose();
        BERNARDTEXTURE.dispose();
        BERNARDDEATHTEXTURE.dispose();
        BERNARDGLANCETEXTURE.dispose();
        BERNARDSHIELDTEXTURE.dispose();
        BERNARDDEATHTEXTURE.dispose();
        BLANKTEXTURE.dispose();
        BLOCKERTEXTURE.dispose();
        BLUESKILLTEXTURE.dispose();
        BLUESTEXTURE.dispose();
        BLUETEXTURE.dispose();
        BOTTLESKILLTEXTURE.dispose();
        BONNIETEXTURE.dispose();
        BONNIEWALKTEXTURE.dispose();
        BONNIEATTACKTEXTURE.dispose();

        //C
        CATTEXTURE.dispose();
        CATATTACKTEXTURE.dispose();
        CATWALKTEXTURE.dispose();
        CATTYTEXTURE.dispose();
        CATLADYTEXTURE.dispose();
        CATLADYTRANSFORMTEXTURE.dispose();
        CLYDETEXTURE.dispose();
        CLYDEWALKTEXTURE.dispose();
        CLYDEATTACKTEXTURE.dispose();

        //D
        DAMAGETEXTURE.dispose();
        DEATHTEXTURE.dispose();
        DEFENSETEXTURE.dispose();
        DETECTICON.dispose();
        DETECTIONTEXTURE.dispose();
        DRUNKENTEXTURE.dispose();
        DRUNKATTACKTEXTURE.dispose();
        DORABERNARDJUMPTEXTURE.dispose();
        DRUNKTEXTURE.dispose();

        //E
        //F
        FROSTBITETEXTURE.dispose();
        FUSIONICON.dispose();

        //G
        GREYGATELTEXTURE.dispose();
        GREYGATERTEXTURE.dispose();
        GREYKEYTEXTURE.dispose();

        //H
        HAMMERTETEXTURE.dispose();
        HAMTEXTURE.dispose();
        HAMMERATTACKTETEXTURE.dispose();
        HAMMERDOWNTEXTURE.dispose();
        HEALTEXTURE.dispose();
        HUD.dispose();
        HEALTHPOOL.dispose();
        HPBARBACKGROUND.dispose();
        HPBARFILL.dispose();
        HEALTHTEXTURE.dispose();

        //I
        ICICLETEXTURE.dispose();
        INVENTORYTEXTURE.dispose();
        INVENTORYSHIELDTEXTURE.dispose();
        INVENTORYWHISTLETEXTURE.dispose();
        INVENTORYREDKEYTEXTURE.dispose();
        INVENTORYGREYKEYTEXTURE.dispose();

        //J //K
        //L
        LIGHTBARRIERTEXTURE.dispose();
        LIGHTINFUSEICON.dispose();
        LIGHTTEXTURE.dispose();
        LIGHTTEXTURE2.dispose();

        //M
        MELEEATTACKTEXTURE.dispose();
        MELEESKILLTEXTURE.dispose();
        MOONSHINERTEXTURE.dispose();
        MOONSHINERWALKTEXTURE.dispose();
        MOONSHINERATTACKTEXTURE.dispose();

        //N //O //P //Q
        //R
        RARECANDYTEXTURE.dispose();
        REDLASERICON.dispose();
        REDLASERTEXTURE.dispose();
        REDGATELTEXTURE.dispose();
        REDGATERTEXTURE.dispose();
        REDKEYTEXTURE.dispose();

        //S
        SHIELDTEXTURE.dispose();
        SKIN.dispose();
        SKILLONETEXTURE.dispose();
        SKILLONEICON.dispose();
        SKILLTWOICON.dispose();
        SLIDELEFT.dispose();
        SLIDERIGHT.dispose();
        SLIDEUP.dispose();
        SLIDEDOWN.dispose();
        SUFFRAGETTETEXTURE.dispose();
        SUFFERTETEXTURE.dispose();

        //T
        TARGETSQUARE.dispose();
        TRAPTEXTURE.dispose();
        TRAPTEXTURE2.dispose();
        TRAPTEXTURE3.dispose();
        TRAPKUNAI.dispose();
        TRAPPOWER.dispose();
        TRAPBLIND.dispose();
        TRAPTRANSFORM.dispose();

        //UV
        //W
        WANDTEXTURE.dispose();
        WANDERTEXTURE.dispose();
        WANDERATTACKTEXTURE.dispose();
        WHISTLETEXTURE.dispose();
        WREKERTEXTURE.dispose();
        WREKINGTEXTURE.dispose();
        WREKERATTACKTEXTURE.dispose();

        //X
        XPBARFILL.dispose();

        //YZ
    }
}
