package com.beancore.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import com.beancore.config.Config;
import com.beancore.config.EnemyPlaneType;
import com.beancore.config.ImageConstants;
import com.beancore.entity.Bullet;
import com.beancore.entity.EnemyPlane;
import com.beancore.entity.MyPlane;
import com.beancore.factory.EnemyPlaneFactory;
import com.beancore.listener.BulletListener;
import com.beancore.listener.EnemyPlaneListener;
import com.beancore.util.Images;
import com.beancore.util.SoundPlayer;

public class GamePlayingPanel extends JPanel implements MouseMotionListener, BulletListener, EnemyPlaneListener,
	ImageObserver {
    private static final long serialVersionUID = 1L;
    private volatile List<Bullet> bullets;
    private volatile List<EnemyPlane> enemyPlanes;
    private int score;
    private MyPlane myPlane;

    private Thread paintThread;

    private int SCORE_IMG_POS_X = 5;
    private int SCORE_IMG_POS_Y = 5;

    private int remainTimeToPopSmallPlane;
    private int remainTimeToPopBigPlane;
    private int remainTimeToPopBossPlane;

    private SoundPlayer achievementSoundPlayer;

    private SoundPlayer smallPlaneKilledSoundPlayer;
    private SoundPlayer bigPlaneKilledSoundPlayer;
    private SoundPlayer bossPlaneKilledSoundPlayer;

    private SoundPlayer bossPlaneFlyingSoundPlayer;
    private SoundPlayer popWeaponSoundPlayer;

    private SoundPlayer fireBulletSoundPlayer;
    private SoundPlayer useBombSoundPlayer;

    private SoundPlayer getDoubleLaserSoundPlayer;
    private SoundPlayer getBombSoundPlayer;

    private SoundPlayer gameMusicSoundPlayer;
    private SoundPlayer gameOverSoundPlayer;

    public GamePlayingPanel() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
	this.initSoundPlayer();
	this.initComponents();
    }

    private void initComponents() {
	this.addMouseMotionListener(this);
	this.setSize(Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT);
	this.setDoubleBuffered(true);
	this.setOpaque(false);
    }

    private void initSoundPlayer() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
	achievementSoundPlayer = new SoundPlayer(Config.ACHIEVEMENT_AUDIO);

	smallPlaneKilledSoundPlayer = new SoundPlayer(Config.SMALL_PLANE_KILLED_AUDIO);
	bigPlaneKilledSoundPlayer = new SoundPlayer(Config.BIG_PLANE_KILLED_AUDIO);
	bossPlaneKilledSoundPlayer = new SoundPlayer(Config.BOSS_PLANE_KILLED_AUDIO);

	bossPlaneFlyingSoundPlayer = new SoundPlayer(Config.BOSS_PLANE_FLYING_AUDIO);
	popWeaponSoundPlayer = new SoundPlayer(Config.POP_WEAPON_AUDIO);

	fireBulletSoundPlayer = new SoundPlayer(Config.FIRE_BULLET_AUDIO);
	useBombSoundPlayer = new SoundPlayer(Config.USER_BOMB_AUDIO);

	getDoubleLaserSoundPlayer = new SoundPlayer(Config.GET_DOUBLE_LASER_AUDIO);
	getBombSoundPlayer = new SoundPlayer(Config.GET_BOMB_AUDIO);

	gameMusicSoundPlayer = new SoundPlayer(Config.GAME_MUSIC_AUDIO);
	gameOverSoundPlayer = new SoundPlayer(Config.GAME_OVER_AUDIO);
    }

    @Override
    public void onBulletLocationChanged(Bullet b) {
	if (b != null) {
	    b.setPosY(b.getPosY() - b.getSpeed());
	    if (b.getPosY() <= 0) {
		synchronized (this.bullets) {
		    this.bullets.remove(b);
		}
	    }

	    EnemyPlane enemyPlane = b.hitEnemyPlanes();
	    if (enemyPlane != null) {
		enemyPlane.drawFighting(this.getComponentGraphics(this.getGraphics()));
		if (enemyPlane.isKilled()) {
		    switch (enemyPlane.getEnemyType()) {
		    case SMALL_ENEMY_PLANE:
			this.smallPlaneKilledSoundPlayer.play();
			break;
		    case BIG_ENEMY_PLANE:
			this.bigPlaneKilledSoundPlayer.play();
			break;
		    case BOSS_ENEMY_PLANE:
			this.bossPlaneFlyingSoundPlayer.stop();
			this.bossPlaneKilledSoundPlayer.play();
			break;
		    }
		    synchronized (this) {
			this.score += enemyPlane.getKilledScore();
		    }
		    synchronized (this.enemyPlanes) {
			this.enemyPlanes.remove(enemyPlane);
		    }
		    synchronized (this.bullets) {
			this.bullets.remove(b);
		    }
		    enemyPlane.drawKilled(this.getComponentGraphics(this.getGraphics()));
		}
	    }
	}
    }

    @Override
    public void onEnemyPlaneLocationChanged(EnemyPlane p) {
	if (p != null) {
	    p.setPosY(p.getPosY() + p.getSpeed());
	    if (p.getPosY() >= this.getHeight()) {
		if (p.getEnemyType().equals(EnemyPlaneType.BOSS_ENEMY_PLANE)) {
		    this.bossPlaneFlyingSoundPlayer.stop();
		}
		synchronized (this.enemyPlanes) {
		    enemyPlanes.remove(p);
		}
	    } else {
		if (!p.isKilled() && p.getRectangle().intersects(myPlane.getRectange())) {
		    // game ends
		    synchronized (myPlane) {
			if (myPlane.isAlive()) {
			    this.stopGame();
			}
		    }
		}
	    }
	}
    }

    private void drawScore(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	List<Integer> intList = new ArrayList<Integer>();
	int scoreCopy = this.score;
	int quotient = 0;
	while ((quotient = scoreCopy / 10) != 0) {
	    intList.add(scoreCopy % 10);
	    scoreCopy = quotient;
	}
	intList.add(scoreCopy % 10);
	// draw
	int posX = this.SCORE_IMG_POS_X;
	int posY = this.SCORE_IMG_POS_Y;
	g2d.drawImage(Images.SCORE_IMG, posX, posY, ImageConstants.SCORE_IMG_WIDTH, ImageConstants.SCORE_IMG_HEIGHT,
		this);
	posX += ImageConstants.SCORE_IMG_WIDTH;
	posY += ImageConstants.SCORE_IMG_HEIGHT - ImageConstants.NUMBER_0_HEIGHT;
	int size = intList.size();
	for (int i = size - 1; i >= 0; i--) {
	    switch (intList.get(i)) {
	    case Config.NUMBER_0:
		g2d.drawImage(Images.NUMBER_0_IMG, posX, posY, ImageConstants.NUMBER_0_WIDTH,
			ImageConstants.NUMBER_0_HEIGHT, this);
		posX += ImageConstants.NUMBER_0_WIDTH;
		break;
	    case Config.NUMBER_1:
		g2d.drawImage(Images.NUMBER_1_IMG, posX, posY, ImageConstants.NUMBER_1_WIDTH,
			ImageConstants.NUMBER_1_HEIGHT, this);
		posX += ImageConstants.NUMBER_1_WIDTH;
		break;
	    case Config.NUMBER_2:
		g2d.drawImage(Images.NUMBER_2_IMG, posX, posY, ImageConstants.NUMBER_2_WIDTH,
			ImageConstants.NUMBER_2_HEIGHT, this);
		posX += ImageConstants.NUMBER_2_WIDTH;
		break;
	    case Config.NUMBER_3:
		g2d.drawImage(Images.NUMBER_3_IMG, posX, posY, ImageConstants.NUMBER_3_WIDTH,
			ImageConstants.NUMBER_3_HEIGHT, this);
		posX += ImageConstants.NUMBER_3_WIDTH;
		break;
	    case Config.NUMBER_4:
		g2d.drawImage(Images.NUMBER_4_IMG, posX, posY, ImageConstants.NUMBER_4_WIDTH,
			ImageConstants.NUMBER_4_HEIGHT, this);
		posX += ImageConstants.NUMBER_4_WIDTH;
		break;
	    case Config.NUMBER_5:
		g2d.drawImage(Images.NUMBER_5_IMG, posX, posY, ImageConstants.NUMBER_5_WIDTH,
			ImageConstants.NUMBER_5_HEIGHT, this);
		posX += ImageConstants.NUMBER_5_WIDTH;
		break;
	    case Config.NUMBER_6:
		g2d.drawImage(Images.NUMBER_6_IMG, posX, posY, ImageConstants.NUMBER_6_WIDTH,
			ImageConstants.NUMBER_6_HEIGHT, this);
		posX += ImageConstants.NUMBER_6_WIDTH;
		break;
	    case Config.NUMBER_7:
		g2d.drawImage(Images.NUMBER_7_IMG, posX, posY, ImageConstants.NUMBER_7_WIDTH,
			ImageConstants.NUMBER_7_HEIGHT, this);
		posX += ImageConstants.NUMBER_7_WIDTH;
		break;
	    case Config.NUMBER_8:
		g2d.drawImage(Images.NUMBER_8_IMG, posX, posY, ImageConstants.NUMBER_8_WIDTH,
			ImageConstants.NUMBER_8_HEIGHT, this);
		posX += ImageConstants.NUMBER_8_WIDTH;
		break;
	    case Config.NUMBER_9:
		g2d.drawImage(Images.NUMBER_9_IMG, posX, posY, ImageConstants.NUMBER_9_WIDTH,
			ImageConstants.NUMBER_9_HEIGHT, this);
		posX += ImageConstants.NUMBER_9_WIDTH;
		break;
	    }
	}
    }

    class PaintThread implements Runnable {

	@Override
	public void run() {
	    while (myPlane.isAlive()) {
		for (int i = 0; i < bullets.size(); i++) {
		    Bullet b = bullets.get(i);
		    onBulletLocationChanged(b);
		}

		for (int i = 0; i < enemyPlanes.size(); i++) {
		    EnemyPlane enemyPlane = enemyPlanes.get(i);
		    onEnemyPlaneLocationChanged(enemyPlane);
		}

		// ADD PLANE
		if (remainTimeToPopSmallPlane > 0) {
		    remainTimeToPopSmallPlane -= Config.MAIN_FRAME_REPAINT_INTERVAL;
		} else {
		    // pop a small enemy plane
		    EnemyPlane smallPlane = EnemyPlaneFactory.createEnemyPlane(GamePlayingPanel.this,
			    EnemyPlaneType.SMALL_ENEMY_PLANE);
		    synchronized (GamePlayingPanel.this.enemyPlanes) {
			enemyPlanes.add(smallPlane);
		    }
		    remainTimeToPopSmallPlane = Config.POP_SMALL_ENEMY_PLANE_INTERVAL;
		}

		if (remainTimeToPopBigPlane > 0) {
		    remainTimeToPopBigPlane -= Config.MAIN_FRAME_REPAINT_INTERVAL;
		} else {
		    // pop a big enemy plane
		    EnemyPlane bigPlane = EnemyPlaneFactory.createEnemyPlane(GamePlayingPanel.this,
			    EnemyPlaneType.BIG_ENEMY_PLANE);
		    synchronized (GamePlayingPanel.this.enemyPlanes) {
			enemyPlanes.add(bigPlane);
		    }
		    remainTimeToPopBigPlane = Config.POP_BIG_ENEMY_PLANE_INTERVAL;
		}

		if (remainTimeToPopBossPlane > 0) {
		    remainTimeToPopBossPlane -= Config.MAIN_FRAME_REPAINT_INTERVAL;
		} else {
		    // pop a boss enemy plane
		    EnemyPlane bossPlane = EnemyPlaneFactory.createEnemyPlane(GamePlayingPanel.this,
			    EnemyPlaneType.BOSS_ENEMY_PLANE);
		    synchronized (GamePlayingPanel.this.enemyPlanes) {
			enemyPlanes.add(bossPlane);
		    }
		    remainTimeToPopBossPlane = Config.POP_BOSS_ENEMY_PLANE_INTERVAL;
		    bossPlaneFlyingSoundPlayer.loop();
		}

		GamePlayingPanel.this.repaint();

		try {
		    Thread.sleep(Config.MAIN_FRAME_REPAINT_INTERVAL);
		} catch (InterruptedException e) {

		}
	    }
	}

    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	drawScore(g);
	myPlane.draw(g);
	for (int i = 0; i < this.enemyPlanes.size(); i++) {
	    EnemyPlane enemyPlane = this.enemyPlanes.get(i);
	    enemyPlane.draw(g);
	}
	for (int i = 0; i < this.bullets.size(); i++) {
	    Bullet b = this.bullets.get(i);
	    b.draw(g);
	}
    }

    public void startGame() {
	this.score = 0;
	this.remainTimeToPopSmallPlane = Config.POP_SMALL_ENEMY_PLANE_INTERVAL;
	this.remainTimeToPopBigPlane = Config.POP_BIG_ENEMY_PLANE_INTERVAL;
	this.remainTimeToPopBossPlane = Config.POP_BOSS_ENEMY_PLANE_INTERVAL;
	this.bullets = new LinkedList<Bullet>();
	this.enemyPlanes = new LinkedList<EnemyPlane>();
	this.myPlane = new MyPlane(this);
	this.myPlane.setAlive(true);
	this.myPlane.setPosX((Config.MAIN_FRAME_WIDTH - ImageConstants.MY_PLANE_WIDTH) / 2);
	this.myPlane.setPosY(Config.MAIN_FRAME_HEIGHT - ImageConstants.MY_PLANE_HEIGHT);
	this.gameMusicSoundPlayer.loop();
	this.fireBulletSoundPlayer.loop();
	this.paintThread = new Thread(new PaintThread());
	this.paintThread.start();
    }

    public void stopGame() {
	this.myPlane.setAlive(false);
	this.gameMusicSoundPlayer.stop();
	this.fireBulletSoundPlayer.stop();
	this.bossPlaneFlyingSoundPlayer.stop();
	this.gameOverSoundPlayer.play();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
	// nothing to do
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	if (this.myPlane != null && this.myPlane.isAlive()) {
	    myPlane.mouseMoved(e);
	    this.repaint();
	}
    }

    public List<Bullet> getBullets() {
	return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
	this.bullets = bullets;
    }

    public List<EnemyPlane> getEnemyPlanes() {
	return enemyPlanes;
    }

    public void setEnemyPlanes(List<EnemyPlane> enemyPlanes) {
	this.enemyPlanes = enemyPlanes;
    }

    public MyPlane getMyPlane() {
	return myPlane;
    }

    public int getScore() {
	return score;
    }

}
