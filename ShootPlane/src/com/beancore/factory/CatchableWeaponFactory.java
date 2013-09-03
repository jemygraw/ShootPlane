package com.beancore.factory;

import java.util.Random;

import com.beancore.config.CatchableWeaponType;
import com.beancore.config.Config;
import com.beancore.config.ImageConstants;
import com.beancore.entity.Bomb;
import com.beancore.entity.CatchableWeapon;
import com.beancore.entity.DoubleLaser;
import com.beancore.ui.GamePlayingPanel;
import com.beancore.util.Images;

public class CatchableWeaponFactory {
    public static final Random rand = new Random();

    public static CatchableWeapon createCatchableWeapon(GamePlayingPanel playingPanel, CatchableWeaponType weaponType) {
	CatchableWeapon weapon = null;
	switch (weaponType) {
	case BOMB:
	    weapon = new Bomb(playingPanel, weaponType);
	    weapon.setWidth(ImageConstants.BOMB_WIDTH);
	    weapon.setHeight(ImageConstants.BOMB_HEIGHT);
	    weapon.setWeaponImage(Images.BOMB_IMG);
	    weapon.setSpeed(Config.POP_WEAPON_MOVE_SPEED);
	    break;
	case DOUBLE_LASER:
	    weapon = new DoubleLaser(playingPanel, weaponType);
	    weapon.setWidth(ImageConstants.DOUBLE_LASER_WIDTH);
	    weapon.setHeight(ImageConstants.DOUBLE_LASER_HEIGHT);
	    weapon.setWeaponImage(Images.DOUBLE_LASER_IMG);
	    weapon.setSpeed(Config.POP_WEAPON_MOVE_SPEED);
	    break;
	}

	int posX = rand.nextInt(playingPanel.getWidth() - weapon.getWidth());
	int posY = 0;
	weapon.setPosX(posX);
	weapon.setPosY(posY);

	return weapon;
    }
}