package edu.uco.shvosi;

public class Constants {
    public static final int TILEDIMENSION = 100;
    public static final int SCREENWIDTH = 1024;
    public static final int SCREENHEIGHT = 576;
    public static final float MOVEACTIONDURATION = 0.1f;
    public static float MASTERVOLUME = 0.1f;
    
    
    public static enum MapGridCode {
        FLOOR, WALL, STRUCTURE, EXIT;
    }
    
    public static enum EntityGridCode {
        NONE, PLAYER, ENEMY, ITEM, TRAP;
    }
    
    public static enum EnemyType {
	NONE,
        WANDERER,
        DRUNK,
        CATLADY,
        WREKER,
        BLUES,
        FLAPPER,
        SUFFRAGETTE,
        HAMMER,
        BONNIE,
        CLYDE,
        GREYGATE,
        REDGATE;
    }
    
    public static enum EnemyAttackType {
	NONE,
        MELEEATTACK;
    }
    
    public static enum ItemType {
	NONE,
        HEALTH,
        SHIELD,
        WHISTLE,
        REDKEY,
        GREYKEY;
    }
    
    public static enum TrapType {
        NONE,
        TRAP1,
        TRAP2,
        TRAP3,
        TRAP4,
        TRAP5,
        SLIDETILE,
        BLOCKER;
    }
    
    public static enum Direction {
        NONE,
        UP, DOWN, LEFT, RIGHT,
        UP_LEFT, UP_RIGHT,
        DOWN_LEFT, DOWN_RIGHT;
    }
    
    public static enum ArrowType {
        NONE,
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }
    
    public static enum GateType {
	NONE,
        LEFT,
        RIGHT;
    }
    
    public static enum TurnAction {
        NONE,
        MOVE, ATTACK;
    }
    
    public static enum SkillName {
        NONE,
        SKILLONE,
        SKILLTWO,
        DETECTION,
        BARRIERSKILL,
        LIGHTBARRIERSKILL,
        REDLASERSKILL,
        MELEESKILL,
        BLUESSKILL;
    }
}