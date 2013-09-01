package com.beancore.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.beancore.config.Config;
import com.beancore.util.Images;

public class GameLoadingPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Image gameLoadingTextImg;
    private JLabel gameLoadingPlaneLabel;
    private JLabel gameLoadingTextLabel;
    private ImageIcon[] gameLoadingPlaneImgList;

    public GameLoadingPanel() {
	this.createLoadingPanel();
    }

    private void createLoadingPanel() {
	this.gameLoadingPlaneImgList = new ImageIcon[3];
	this.gameLoadingPlaneImgList[0] = new ImageIcon(Images.GAME_LOADING_IMG1);
	this.gameLoadingPlaneImgList[1] = new ImageIcon(Images.GAME_LOADING_IMG2);
	this.gameLoadingPlaneImgList[2] = new ImageIcon(Images.GAME_LOADING_IMG3);
	this.gameLoadingTextImg = Images.GAME_LOADING_TEXT_IMG;

	gameLoadingPlaneLabel = new JLabel();
	gameLoadingPlaneLabel.setOpaque(false);
	gameLoadingTextLabel = new JLabel(new ImageIcon(this.gameLoadingTextImg));
	gameLoadingTextLabel.setOpaque(false);
	GridLayout gridLayout = new GridLayout(2, 1);

	FlowLayout flowLayout1 = new FlowLayout(FlowLayout.CENTER);
	JPanel panel1 = new JPanel();
	panel1.setLayout(flowLayout1);
	panel1.add(gameLoadingPlaneLabel);
	panel1.setOpaque(false);

	FlowLayout flowLayout2 = new FlowLayout(FlowLayout.CENTER);
	JPanel panel2 = new JPanel();
	panel2.setLayout(flowLayout2);
	panel2.add(gameLoadingTextLabel);
	panel2.setOpaque(false);

	this.setLayout(gridLayout);
	this.setOpaque(false);
	this.add(panel1);
	this.add(panel2);
    }

    public void loadingGame() {
	int times = 3;
	for (int i = 0; i < times; i++) {
	    this.gameLoadingPlaneLabel.setIcon(this.gameLoadingPlaneImgList[i]);
	    try {
		Thread.sleep(Config.GAME_LOADING_INTERVAL);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}
    }

}
