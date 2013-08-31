package com.beancore.entity;

import java.awt.Graphics;

import com.beancore.config.Config;
import com.beancore.config.EnemyPlaneType;
import com.beancore.config.ImageConstants;
import com.beancore.ui.GamePlayingPanel;
import com.beancore.util.Images;

public class BossPlane extends EnemyPlane {

    public BossPlane(GamePlayingPanel getPlayingPanel, EnemyPlaneType enemyType) {
	super(getPlayingPanel, enemyType);
    }

    @Override
    public void drawFighting(Graphics g) {
	new Thread(new DrawFighting(g)).start();
    }

    @Override
    public void drawKilled(Graphics g) {
	new Thread(new DrawKilled(g)).start();
    }

    class DrawFighting implements Runnable {
	private Graphics g;

	DrawFighting(Graphics g) {
	    this.g = g;
	}

	@Override
	public void run() {
	    drawFightingRun(g);
	}
    }

    class DrawKilled implements Runnable {
	private Graphics g;

	DrawKilled(Graphics g) {
	    this.g = g;
	}

	@Override
	public void run() {
	    drawKilledRun(g);
	}

    }

    public void drawFightingRun(Graphics g) {
	this.setPlaneImage(Images.BOSS_PLANE_FIGHTING_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_FIGHTING_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_FIGHTING_HEIGHT);
	super.draw(g);
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}
    }

    public void drawKilledRun(Graphics g) {
	this.setPlaneImage(Images.BOSS_PLANE_HITTED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_HITTED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_HITTED_HEIGHT);
	super.draw(g);
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}

	this.setPlaneImage(Images.BOSS_PLANE_BADDLY_WOUNDED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_HEIGHT);
	super.draw(g);
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}

	this.setPlaneImage(Images.BOSS_PLANE_KILLED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_KILLED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_KILLED_HEIGHT);
	super.draw(g);
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}

	this.setPlaneImage(Images.BOSS_PLANE_ASHED_IMG);
	this.setWidth(ImageConstants.BOSS_PLANE_ASHED_WIDTH);
	this.setHeight(ImageConstants.BOSS_PLANE_ASHED_HEIGHT);
	super.draw(g);
	try {
	    Thread.sleep(Config.BOSS_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}
    }
}
