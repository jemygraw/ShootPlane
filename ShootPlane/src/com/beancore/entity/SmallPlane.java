package com.beancore.entity;

import com.beancore.config.Config;
import com.beancore.config.EnemyPlaneType;
import com.beancore.config.ImageConstants;
import com.beancore.ui.MainFrame;
import com.beancore.util.Images;

public class SmallPlane extends EnemyPlane {

    public SmallPlane(MainFrame mainFrame, EnemyPlaneType enemyType) {
	super(mainFrame, enemyType);
    }

    @Override
    public void drawFighting() {
	new Thread(new DrawFighting()).start();
    }

    private void drawFightingRun() {
	this.setPlaneImage(Images.SMALL_PLANE_FIGHTING_IMG);
	this.setWidth(ImageConstants.SMALL_PLANE_FIGHTING_WIDTH);
	this.setHeight(ImageConstants.SMALL_PLANE_FIGHTING_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.SMALL_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}
    }

    @Override
    public void drawKilled() {
	new Thread(new DrawKilled()).start();
    }

    private void drawKilledRun() {
	this.setPlaneImage(Images.SMALL_PLANE_KILLED_IMG);
	this.setWidth(ImageConstants.SMALL_PLANE_KILLED_WIDTH);
	this.setHeight(ImageConstants.SMALL_PLANE_KILLED_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.SMALL_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}
	this.setPlaneImage(Images.SMALL_PLANE_ASHED_IMG);
	this.setWidth(ImageConstants.SMALL_PLANE_ASHED_WIDTH);
	this.setHeight(ImageConstants.SMALL_PLANE_ASHED_HEIGHT);
	super.draw();
	try {
	    Thread.sleep(Config.SMALL_PLANE_STATUS_CHANGE_INTERVAL);
	} catch (InterruptedException e) {

	}
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

}
