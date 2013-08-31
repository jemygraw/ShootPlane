package com.beancore.config;

public class Config {
    // non-changeable configuration
    public final static String LOGO_IMG = "images/logo.png";
    public final static String SHOOT_BACKGROUND_IMG = "images/shoot_background.png";
    public final static String SHOOT_IMG = "images/shoot.png";
    public final static String FONT_IMG = "images/font.png";

    public final static String ACHIEVEMENT_AUDIO = "sound/achievement.wav";

    public final static String SMALL_PLANE_KILLED_AUDIO = "sound/small_plane_killed.wav";
    public final static String BIG_PLANE_KILLED_AUDIO = "sound/big_plane_killed.wav";
    public final static String BOSS_PLANE_KILLED_AUDIO = "sound/boss_plane_killed.wav";

    public final static String BOSS_PLANE_FLYING_AUDIO = "sound/boss_plane_flying.wav";
    public final static String POP_WEAPON_AUDIO = "sound/pop_weapon.wav";

    public final static String USER_BOMB_AUDIO = "sound/use_bomb.wav";
    public final static String FIRE_BULLET_AUDIO = "sound/fire_bullet.wav";

    public final static String GAME_MUSIC_AUDIO = "sound/game_music.wav";
    public final static String GAME_OVER_AUDIO = "sound/game_over.wav";

    public final static String GET_BOMB_AUDIO = "sound/get_bomb.wav";
    public final static String GET_DOUBLE_LASER_AUDIO = "sound/get_double_laser.wav";

    public final static int MAIN_FRAME_WIDTH = 480;
    public final static int MAIN_FRAME_HEIGHT = 852;

    public final static int KILL_SMALL_PLANE_SCORE = 1000;
    public final static int KILL_BIG_PLANE_SCORE = 6000;
    public final static int KILL_BOSS_PLANE_SCORE = 30000;

    public final static int BULLET_COUNT_TO_KILL_SMALL_PLANE = 1;
    public final static int BULLET_COUNT_TO_KILL_BIG_PLANE = 10;
    public final static int BULLET_COUNT_TO_KILL_BOSS_PLANE = 30;

    public final static int SMALL_PLANE_STATUS_CHANGE_INTERVAL = 200;
    public final static int BIG_PLANE_STATUS_CHANGE_INTERVAL = 200;
    public final static int BOSS_PLANE_STATUS_CHANGE_INTERVAL = 200;

    public final static int GAME_LOADING_INTERVAL = 600;

    public final static int NUMBER_0 = 0;
    public final static int NUMBER_1 = 1;
    public final static int NUMBER_2 = 2;
    public final static int NUMBER_3 = 3;
    public final static int NUMBER_4 = 4;
    public final static int NUMBER_5 = 5;
    public final static int NUMBER_6 = 6;
    public final static int NUMBER_7 = 7;
    public final static int NUMBER_8 = 8;
    public final static int NUMBER_9 = 9;

    // changeable configuration
    public static int DOUBLE_LASER_LAST_TIME = 30000;// 30 seconds

    public static int BULLET_FIRE_INTERVAL = 120;

    public static int MAIN_FRAME_REPAINT_INTERVAL = 80;

    public static int YELLOW_BULLET_MOVE_SPEED = 50;
    public static int BLUE_BULLET_MOVE_SPEED = 50;

    public static int ENEMY_PLANE_MOVE_SPEED_MIN = 50;
    public static int ENEMY_PLANE_MOVE_SPEED_MAX = 60;

    public static int POP_WEAPON_MOV_FORWARD_SPEED = 60;
    public static int POP_WEAPON_MOV_BACK_SPEED = 20;

    public static int POP_DOUBLE_LASER_INTERVAL = 30000;
    public static int POP_BOMBO_INTERVAL = 60000;

    public static int POP_SMALL_ENEMY_PLANE_INTERVAL = 500;
    public static int POP_BIG_ENEMY_PLANE_INTERVAL = 5000;
    public static int POP_BOSS_ENEMY_PLANE_INTERVAL = 10000;

}
