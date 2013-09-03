package com.beancore.entity;

import com.beancore.config.CatchableWeaponType;
import com.beancore.ui.GamePlayingPanel;

public class Bomb extends CatchableWeapon {

    public Bomb(GamePlayingPanel gamePlayingPanel, CatchableWeaponType weaponType) {
	super(gamePlayingPanel, weaponType);
    }

}
