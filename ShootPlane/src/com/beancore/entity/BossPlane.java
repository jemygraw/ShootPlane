package com.beancore.entity;

import com.beancore.config.Config;
import com.beancore.config.EnemyPlaneType;
import com.beancore.config.ImageConstants;
import com.beancore.ui.MainFrame;
import com.beancore.util.Images;

public class BossPlane extends EnemyPlane {

    public BossPlane(MainFrame mainFrame, EnemyPlaneType enemyType) {
	super(mainFrame, enemyType);
    }

    @Override
    public void drawFighting() {
	new Thread(new DrawFighting()).start();
    }

    @Override
    public void drawKilled() {
	new Thread(new DrawKilled()).start();
    }

    class DrawFighting implements Runnable {
	@Override
	public void run() {
	    drawFightingRun();
	}
    }

    class DrawKilled implements Runnable {

	@Override
	public void run() {
	    drawKilledRun();
	}

    }

    public void drawFightingRun() {
	this.setPlaneImage(Images.BOSS_PLANE_FIGHTING_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_FIGHTING_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_FIGHTING_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}
    }

    public void drawKilledRun() {
	this.setPlaneImage(Images.BOSS_PLANE_HITTED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_HITTED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_HITTED_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}

	this.setPlaneImage(Images.BOSS_PLANE_BADDLY_WOUNDED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}

	this.setPlaneImage(Images.BOSS_PLANE_KILLED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_KILLED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_KILLED_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}

	this.setPlaneImage(Images.BOSS_PLANE_ASHED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_ASHED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_ASHED_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}
    }
}
