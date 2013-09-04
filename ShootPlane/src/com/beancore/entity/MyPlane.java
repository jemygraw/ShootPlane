package com.beancore.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import com.beancore.config.BulletType;
import com.beancore.config.Config;
import com.beancore.config.ImageConstants;
import com.beancore.factory.BulletFactory;
import com.beancore.ui.GamePlayingPanel;
import com.beancore.util.Images;

public class MyPlane {
    /**
     * Current plane position
     * */
    private int posX;
    private int posY;

    private int width;
    private int height;
    private Image planeImage;
    private Image planeFlyingImage;
    private boolean isAlive;
    private boolean hitDoubleLaser;
    private List<Bomb> holdBombList;
    private BulletType bulletType;
    private GamePlayingPanel playingPanel;
    private boolean flip;

    public MyPlane(GamePlayingPanel playingPanel) {
	this.isAlive = true;
	this.flip = true;
	this.playingPanel = playingPanel;
	this.width = ImageConstants.MY_PLANE_WIDTH;
	this.height = ImageConstants.MY_PLANE_HEIGHT;
	this.planeImage = Images.MY_PLANE_IMG;
	this.planeFlyingImage = Images.MY_PLANE_FLYING_IMG;
	this.holdBombList = new LinkedList<Bomb>();
	new Thread(new LauchBulletThread()).start();
    }

    public Rectangle getRectange() {
	int fix = width / 3;
	return new Rectangle(posX + fix, posY, width / 3, height);
    }

    public void draw(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	if (flip) {
	    g2d.drawImage(planeImage, posX, posY, width, height, playingPanel);
	} else {
	    g2d.drawImage(planeFlyingImage, posX, posY, width, height, playingPanel);
	}
	flip = !flip;
    }

    public void mouseMoved(MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	posX = x - width / 2;
	posY = y - height / 2;
    }

    public void lauchBullet() {
	if (isAlive) {
	    if (hitDoubleLaser) {
		Bullet bullets[] = BulletFactory.createBlueBullet(this);
		for (Bullet bullet : bullets) {
		    bullet.addBulletListener(this.playingPanel);
		    synchronized (this.playingPanel.getBullets()) {
			this.playingPanel.getBullets().add(bullet);
		    }
		}
	    } else {
		Bullet bullet = BulletFactory.createYellowBullet(this);
		bullet.addBulletListener(this.playingPanel);
		synchronized (this.playingPanel.getBullets()) {
		    this.playingPanel.getBullets().add(bullet);
		}
	    }
	}
    }

    class LauchBulletThread implements Runnable {
	public void run() {
	    while (isAlive) {
		try {
		    Thread.sleep(Config.BULLET_FIRE_INTERVAL);
		} catch (InterruptedException e) {

		}
		lauchBullet();
	    }
	}
    }

    public int getHoldBombCount() {
	return this.holdBombList.size();
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

    public Image getPlaneImage() {
	return planeImage;
    }

    public void setPlaneImage(Image planeImage) {
	this.planeImage = planeImage;
    }

    public boolean isAlive() {
	return isAlive;
    }

    public void setAlive(boolean isAlive) {
	this.isAlive = isAlive;
    }

    public boolean isHitDoubleLaser() {
	return hitDoubleLaser;
    }

    public void setHitDoubleLaser(boolean hitDoubleLaser) {
	this.hitDoubleLaser = hitDoubleLaser;
    }

    public List<Bomb> getHoldBombList() {
	return holdBombList;
    }

    public void setHoldBombList(List<Bomb> holdBombList) {
	this.holdBombList = holdBombList;
    }

    public BulletType getBulletType() {
	return bulletType;
    }

    public void setBulletType(BulletType bulletType) {
	this.bulletType = bulletType;
    }

    public GamePlayingPanel getPlayingPanel() {
	return playingPanel;
    }

    public void setPlayingPanel(GamePlayingPanel playingPanel) {
	this.playingPanel = playingPanel;
    }
}
