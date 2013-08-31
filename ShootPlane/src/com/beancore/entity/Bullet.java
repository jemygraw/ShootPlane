package com.beancore.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.List;

import com.beancore.config.BulletType;
import com.beancore.config.Config;
import com.beancore.config.ImageConstants;
import com.beancore.listener.BulletListener;
import com.beancore.ui.MainFrame;
import com.beancore.util.Images;

public class Bullet {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int speed;
    private BulletType bulletType;

    private MainFrame mainFrame;
    private BulletListener listener;
    private Image bulletImage;

    public Bullet(MainFrame mainFrame, BulletType bulletType) {
	this.mainFrame = mainFrame;
	this.bulletType = bulletType;
	switch (this.bulletType) {
	case YELLOW_BULLET:
	    bulletImage = Images.YELLOW_BULLET_IMG;
	    width = ImageConstants.YELLOW_BULLET_WIDTH;
	    height = ImageConstants.YELLOW_BULLET_HEIGHT;
	    speed = Config.YELLOW_BULLET_MOVE_SPEED;
	    break;
	case BLUE_BULLET:
	    bulletImage = Images.BLUE_BULLET_IMG;
	    width = ImageConstants.YELLOW_BULLET_WIDTH;
	    height = ImageConstants.YELLOW_BULLET_HEIGHT;
	    speed = Config.BLUE_BULLET_MOVE_SPEED;
	    break;
	}
    }

    public int getPosX() {
	return posX;
    }

    public void setPosX(int posX) {
	this.posX = posX;
    }

    public int getPosY() {
	return posY;
    }

    public void setPosY(int posY) {
	this.posY = posY;
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    public BulletType getBulletType() {
	return bulletType;
    }

    public void setBulletType(BulletType bulletType) {
	this.bulletType = bulletType;
    }

    public MainFrame getMainFrame() {
	return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
	this.mainFrame = mainFrame;
    }

    public BulletListener getListener() {
	return listener;
    }

    public void setListener(BulletListener listener) {
	this.listener = listener;
    }

    public Image getBulletImage() {
	return bulletImage;
    }

    public void setBulletImage(Image bulletImage) {
	this.bulletImage = bulletImage;
    }

    public int getSpeed() {
	return speed;
    }

    public void setSpeed(int speed) {
	this.speed = speed;
    }

    public Rectangle getRectangle() {

	return new Rectangle(posX, posY, width, height);

    }

    public void draw() {
	Graphics2D g2d = (Graphics2D) mainFrame.getGraphics();
	g2d.drawImage(bulletImage, posX, posY, width, height, mainFrame);

    }

    public EnemyPlane hitEnemyPlanes() {
	List<EnemyPlane> enmeyPlanes = this.mainFrame.getEnemyPlanes();
	for (int i = 0; i < enmeyPlanes.size(); i++) {
	    EnemyPlane enemyPlane = enmeyPlanes.get(i);
	    if (this.getRectangle().intersects(enemyPlane.getRectangle())) {
		enemyPlane.addHittedCount();
		return enemyPlane;
	    }
	}
	return null;
    }

    public void addBulletListener(MainFrame mainFrame) {
	this.mainFrame = mainFrame;
    }

}
