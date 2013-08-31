package com.beancore.entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.beancore.config.CatchableWeaponType;
import com.beancore.ui.MainFrame;

public abstract class CatchableWeapon {
    private int posX;
    private int posY;

    private int width;
    private int height;
    private Image weaponImage;

    private MainFrame mainFrame;
    private CatchableWeaponType weaponType;

    public CatchableWeapon(MainFrame mainFrame, CatchableWeaponType weaponType) {
	this.mainFrame = mainFrame;
	this.weaponType = weaponType;
    }

    public Rectangle getRectangle() {
	return new Rectangle(posX, posY, width, height);
    }

    public void draw() {
	Graphics2D g2d = (Graphics2D) mainFrame.getGraphics();
	g2d.drawImage(weaponImage, posX, posY, width, height, mainFrame);
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

    public Image getWeaponImage() {
	return weaponImage;
    }

    public void setWeaponImage(Image weaponImage) {
	this.weaponImage = weaponImage;
    }

    public MainFrame getMainFrame() {
	return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
	this.mainFrame = mainFrame;
    }

    public CatchableWeaponType getWeaponType() {
	return weaponType;
    }

    public void setWeaponType(CatchableWeaponType weaponType) {
	this.weaponType = weaponType;
    }

}
