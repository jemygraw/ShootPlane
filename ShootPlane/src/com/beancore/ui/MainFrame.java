package com.beancore.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.beancore.util.ImageLoader;
import com.beancore.util.Images;
import com.beancore.util.SoundPlayer;

public class MainFrame extends JFrame implements MouseMotionListener, BulletListener, EnemyPlaneListener {

    private static final long serialVersionUID = 1L;
    private ImageLoader imgLoader;
    private Image[] gameLoadingPlaneImgList;
    private Image gameLoadingTextImg;
    private JLabel gameLoadingPlaneLabel;
    private JLabel gameLoadingTextLabel;

    private JPanel gameLoadingPanel;

    private List<Bullet> bullets;
    private List<EnemyPlane> enemyPlanes;
    private int score;
    private MyPlane myPlane;

    private volatile boolean gameRunning;

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

    public MainFrame() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
	this.loadImage();
	this.initSoundPlayer();
	this.initComponents();
	this.setBackgroundImage();
    }

    /**
     * Load the image part from the whole image, shoot_background.png shoot.png
     * are both extracted from the weixin apk file
     * */
    private void loadImage() throws IOException {
	this.imgLoader = new ImageLoader(Config.SHOOT_BACKGROUND_IMG);
	gameLoadingPlaneImgList = new Image[3];
	gameLoadingPlaneImgList[0] = this.imgLoader.getImage(ImageConstants.GAME_LOADING_PLANE_1_POS_X,
		ImageConstants.GAME_LOADING_PLANE_1_POS_Y, ImageConstants.GAME_LOADING_PLANE_1_WIDTH,
		ImageConstants.GAME_LOADING_PLANE_1_HEIGHT);
	gameLoadingPlaneImgList[1] = this.imgLoader.getImage(ImageConstants.GAME_LOADING_PLANE_2_POS_X,
		ImageConstants.GAME_LOADING_PLANE_2_POS_Y, ImageConstants.GAME_LOADING_PLANE_2_WIDTH,
		ImageConstants.GAME_LOADING_PLANE_2_HEIGHT);
	gameLoadingPlaneImgList[2] = this.imgLoader.getImage(ImageConstants.GAME_LOADING_PLANE_3_POS_X,
		ImageConstants.GAME_LOADING_PLANE_3_POS_Y, ImageConstants.GAME_LOADING_PLANE_3_WIDTH,
		ImageConstants.GAME_LOADING_PLANE_3_HEIGHT);

	Images.SHOOT_BACKGROUND_IMG = this.imgLoader.getImage(ImageConstants.GAME_BACKGROUND_IMG_POS_X,
		ImageConstants.GAME_BACKGROUND_IMG_POS_Y, ImageConstants.GAME_BACKGROUND_IMG_WIDTH,
		ImageConstants.GAME_BACKGROUND_IMG_HEIGHT);

	this.gameLoadingTextImg = this.imgLoader.getImage(ImageConstants.GAME_LOADING_TEXT_IMG_POS_X,
		ImageConstants.GAME_LOADING_TEXT_IMG_POS_Y, ImageConstants.GAME_LOADING_TEXT_IMG_WIDTH,
		ImageConstants.GAME_LOADING_TEXT_IMG_HEIGHT);

	this.imgLoader = new ImageLoader(Config.SHOOT_IMG);
	Images.YELLOW_BULLET_IMG = this.imgLoader.getImage(ImageConstants.YELLOW_BULLET_POS_X,
		ImageConstants.YELLOW_BULLET_POS_Y, ImageConstants.YELLOW_BULLET_WIDTH,
		ImageConstants.YELLOW_BULLET_HEIGHT);
	Images.BLUE_BULLET_IMG = this.imgLoader.getImage(ImageConstants.BLUE_BULLET_POS_X,
		ImageConstants.BLUE_BULLET_POS_Y, ImageConstants.BLUE_BULLET_WIDTH, ImageConstants.BLUE_BULLET_HEIGHT);
	Images.MY_PLANE_IMG = this.imgLoader.getImage(ImageConstants.MY_PLANE_POS_X, ImageConstants.MY_PLANE_POS_Y,
		ImageConstants.MY_PLANE_WIDTH, ImageConstants.MY_PLANE_HEIGHT);
	Images.SMALL_PLANE_IMG = this.imgLoader.getImage(ImageConstants.SMALL_PLANE_POS_X,
		ImageConstants.SMALL_PLANE_POS_Y, ImageConstants.SMALL_PLANE_WIDTH, ImageConstants.SMALL_PLANE_HEIGHT);
	Images.BIG_PLANE_IMG = this.imgLoader.getImage(ImageConstants.BIG_PLANE_POS_X, ImageConstants.BIG_PLANE_POS_Y,
		ImageConstants.BIG_PLANE_WIDTH, ImageConstants.BIG_PLANE_HEIGHT);
	Images.BOSS_PLANE_IMG = this.imgLoader.getImage(ImageConstants.BOSS_PLANE_POS_X,
		ImageConstants.BOSS_PLANE_POS_Y, ImageConstants.BOSS_PLANE_WIDTH, ImageConstants.BOSS_PLANE_HEIGHT);
	Images.BOMB_IMG = this.imgLoader.getImage(ImageConstants.BOMB_POS_X, ImageConstants.BOMB_POS_Y,
		ImageConstants.BOMB_WIDTH, ImageConstants.BOMB_HEIGHT);

	Images.SMALL_PLANE_FIGHTING_IMG = this.imgLoader.getImage(ImageConstants.SMALL_PLANE_FIGHTING_POS_X,
		ImageConstants.SMALL_PLANE_FIGHTING_POS_Y, ImageConstants.SMALL_PLANE_FIGHTING_WIDTH,
		ImageConstants.SMALL_PLANE_FIGHTING_HEIGHT);
	Images.SMALL_PLANE_KILLED_IMG = this.imgLoader.getImage(ImageConstants.SMALL_PLANE_KILLED_POS_X,
		ImageConstants.SMALL_PLANE_KILLED_POS_Y, ImageConstants.SMALL_PLANE_KILLED_WIDTH,
		ImageConstants.SMALL_PLANE_KILLED_HEIGHT);
	Images.SMALL_PLANE_ASHED_IMG = this.imgLoader.getImage(ImageConstants.SMALL_PLANE_ASHED_POS_X,
		ImageConstants.SMALL_PLANE_ASHED_POS_Y, ImageConstants.SMALL_PLANE_ASHED_WIDTH,
		ImageConstants.SMALL_PLANE_ASHED_HEIGHT);

	Images.BIG_PLANE_FIGHTING_IMG = this.imgLoader.getImage(ImageConstants.BIG_PLANE_FIGHTING_POS_X,
		ImageConstants.BIG_PLANE_FIGHTING_POS_Y, ImageConstants.BIG_PLANE_FIGHTING_WIDTH,
		ImageConstants.BIG_PLANE_FIGHTING_HEIGHT);
	Images.BIG_PLANE_HITTED_IMG = this.imgLoader.getImage(ImageConstants.BIG_PLANE_HITTED_POS_X,
		ImageConstants.BIG_PLANE_HITTED_POS_Y, ImageConstants.BIG_PLANE_HITTED_WIDTH,
		ImageConstants.BIG_PLANE_HITTED_HEIGHT);
	Images.BIG_PLANE_BADDLY_WOUNDED_IMG = this.imgLoader.getImage(ImageConstants.BIG_PLANE_BADDLY_WOUNDED_POS_X,
		ImageConstants.BIG_PLANE_BADDLY_WOUNDED_POS_Y, ImageConstants.BIG_PLANE_BADDLY_WOUNDED_WIDTH,
		ImageConstants.BIG_PLANE_BADDLY_WOUNDED_HEIGHT);
	Images.BIG_PLANE_KILLED_IMG = this.imgLoader.getImage(ImageConstants.BIG_PLANE_KILLED_POS_X,
		ImageConstants.BIG_PLANE_KILLED_POS_Y, ImageConstants.BIG_PLANE_KILLED_WIDTH,
		ImageConstants.BIG_PLANE_KILLED_HEIGHT);
	Images.BIG_PLANE_ASHED_IMG = this.imgLoader.getImage(ImageConstants.BIG_PLANE_ASHED_POS_X,
		ImageConstants.BIG_PLANE_ASHED_POS_Y, ImageConstants.BIG_PLANE_ASHED_WIDTH,
		ImageConstants.BIG_PLANE_ASHED_HEIGHT);

	Images.BOSS_PLANE_FIGHTING_IMG = this.imgLoader.getImage(ImageConstants.BOSS_PLANE_FIGHTING_POS_X,
		ImageConstants.BOSS_PLANE_FIGHTING_POS_Y, ImageConstants.BOSS_PLANE_FIGHTING_WIDTH,
		ImageConstants.BOSS_PLANE_FIGHTING_HEIGHT);
	Images.BOSS_PLANE_HITTED_IMG = this.imgLoader.getImage(ImageConstants.BOSS_PLANE_HITTED_POS_X,
		ImageConstants.BOSS_PLANE_HITTED_POS_Y, ImageConstants.BOSS_PLANE_HITTED_WIDTH,
		ImageConstants.BOSS_PLANE_HITTED_HEIGHT);
	Images.BOSS_PLANE_BADDLY_WOUNDED_IMG = this.imgLoader.getImage(ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_POS_X,
		ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_POS_Y, ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_WIDTH,
		ImageConstants.BOSS_PLANE_BADDLY_WOUNDED_HEIGHT);
	Images.BOSS_PLANE_KILLED_IMG = this.imgLoader.getImage(ImageConstants.BOSS_PLANE_KILLED_POS_X,
		ImageConstants.BOSS_PLANE_KILLED_POS_Y, ImageConstants.BOSS_PLANE_KILLED_WIDTH,
		ImageConstants.BOSS_PLANE_KILLED_HEIGHT);
	Images.BOSS_PLANE_ASHED_IMG = this.imgLoader.getImage(ImageConstants.BOSS_PLANE_ASHED_POS_X,
		ImageConstants.BOSS_PLANE_ASHED_POS_Y, ImageConstants.BOSS_PLANE_ASHED_WIDTH,
		ImageConstants.BOSS_PLANE_ASHED_HEIGHT);

	Images.SCORE_IMG = this.imgLoader.getImage(ImageConstants.SCORE_IMG_POS_X, ImageConstants.SCORE_IMG_POS_Y,
		ImageConstants.SCORE_IMG_WIDTH, ImageConstants.SCORE_IMG_HEIGHT);

	this.imgLoader = new ImageLoader(Config.FONT_IMG);
	Images.X_MARK_IMG = this.imgLoader.getImage(ImageConstants.X_MARK_POS_X, ImageConstants.X_MARK_POS_Y,
		ImageConstants.X_MARK_WIDTH, ImageConstants.X_MARK_HEIGHT);

	Images.NUMBER_0_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_0_POS_X, ImageConstants.NUMBER_0_POS_Y,
		ImageConstants.NUMBER_0_WIDTH, ImageConstants.NUMBER_0_HEIGHT);
	Images.NUMBER_1_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_1_POS_X, ImageConstants.NUMBER_1_POS_Y,
		ImageConstants.NUMBER_1_WIDTH, ImageConstants.NUMBER_1_HEIGHT);
	Images.NUMBER_2_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_2_POS_X, ImageConstants.NUMBER_2_POS_Y,
		ImageConstants.NUMBER_2_WIDTH, ImageConstants.NUMBER_2_HEIGHT);
	Images.NUMBER_3_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_3_POS_X, ImageConstants.NUMBER_3_POS_Y,
		ImageConstants.NUMBER_3_WIDTH, ImageConstants.NUMBER_3_HEIGHT);
	Images.NUMBER_4_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_4_POS_X, ImageConstants.NUMBER_4_POS_Y,
		ImageConstants.NUMBER_4_WIDTH, ImageConstants.NUMBER_4_HEIGHT);
	Images.NUMBER_5_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_5_POS_X, ImageConstants.NUMBER_5_POS_Y,
		ImageConstants.NUMBER_5_WIDTH, ImageConstants.NUMBER_5_HEIGHT);
	Images.NUMBER_6_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_6_POS_X, ImageConstants.NUMBER_6_POS_Y,
		ImageConstants.NUMBER_6_WIDTH, ImageConstants.NUMBER_6_HEIGHT);
	Images.NUMBER_7_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_7_POS_X, ImageConstants.NUMBER_7_POS_Y,
		ImageConstants.NUMBER_7_WIDTH, ImageConstants.NUMBER_7_HEIGHT);
	Images.NUMBER_8_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_8_POS_X, ImageConstants.NUMBER_8_POS_Y,
		ImageConstants.NUMBER_8_WIDTH, ImageConstants.NUMBER_8_HEIGHT);
	Images.NUMBER_9_IMG = this.imgLoader.getImage(ImageConstants.NUMBER_9_POS_X, ImageConstants.NUMBER_9_POS_Y,
		ImageConstants.NUMBER_9_WIDTH, ImageConstants.NUMBER_9_HEIGHT);
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

    private void initComponents() {
	this.setTitle("Shoot Plane - jemygraw@gmail.com");
	this.setIconImage(new ImageIcon(Config.LOGO_IMG).getImage());
	this.setSize(Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT);
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	this.setBounds((d.width - Config.MAIN_FRAME_WIDTH) / 2, (d.height - Config.MAIN_FRAME_HEIGHT) / 2,
		Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT);
	this.setResizable(false);
	this.addMouseMotionListener(this);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createLoadingPanel() {
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

	gameLoadingPanel = new JPanel();
	gameLoadingPanel.setLayout(gridLayout);
	gameLoadingPanel.setOpaque(false);
	gameLoadingPanel.add(panel1);
	gameLoadingPanel.add(panel2);
    }

    private void setBackgroundImage() {
	ImageIcon bgImgIcon = new ImageIcon(Images.SHOOT_BACKGROUND_IMG);
	JLabel bgLabel = new JLabel(bgImgIcon);
	this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
	bgLabel.setBounds(0, 0, bgImgIcon.getIconWidth(), bgImgIcon.getIconHeight());
	((JPanel) this.getContentPane()).setOpaque(false);
    }

    public void loadGame() {
	this.repaint();
	if (this.gameLoadingPanel == null) {
	    this.createLoadingPanel();
	}
	Container c = this.getContentPane();
	BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
	c.setLayout(boxLayout);

	c.add(Box.createVerticalGlue());
	c.add(gameLoadingPanel);
	c.add(Box.createVerticalGlue());

	this.setVisible(true);
	int times = 3;
	for (int i = 0; i < times; i++) {
	    this.gameLoadingPlaneLabel.setIcon(new ImageIcon(this.gameLoadingPlaneImgList[i]));
	    try {
		Thread.sleep(600);
	    } catch (Exception e) {
	    }

	}
	c.removeAll();
	this.startGame();
    }

    public void startGame() {
	this.gameRunning = true;
	this.remainTimeToPopSmallPlane = Config.POP_SMALL_ENEMY_PLANE_INTERVAL;
	this.remainTimeToPopBigPlane = Config.POP_BIG_ENEMY_PLANE_INTERVAL;
	this.remainTimeToPopBossPlane = Config.POP_BOSS_ENEMY_PLANE_INTERVAL;
	this.bullets = new LinkedList<Bullet>();
	this.enemyPlanes = new LinkedList<EnemyPlane>();
	this.myPlane = new MyPlane(this);
	this.myPlane.setAlive(true);
	this.myPlane.setHitDoubleLaser(true);
	this.myPlane.setPosX((Config.MAIN_FRAME_WIDTH - ImageConstants.MY_PLANE_WIDTH) / 2);
	this.myPlane.setPosY(Config.MAIN_FRAME_HEIGHT - ImageConstants.MY_PLANE_HEIGHT);
	this.gameMusicSoundPlayer.loop();
	this.fireBulletSoundPlayer.loop();
	new Thread(new PaintThread()).start();
    }

    public void haltGame() {

    }

    public void stopGame() {
	int oldScore = this.score;
	this.gameMusicSoundPlayer.stop();
	this.fireBulletSoundPlayer.stop();
	this.bossPlaneFlyingSoundPlayer.stop();
	this.gameOverSoundPlayer.play();
	this.score = 0;
	this.gameRunning = false;
	this.myPlane.setAlive(false);
	this.enemyPlanes.clear();
	this.bullets.clear();

	int state = JOptionPane.showConfirmDialog(this, "Game Over, Score:" + oldScore + ", Start Again?", "Game Over",
		JOptionPane.YES_NO_OPTION);
	switch (state) {
	case JOptionPane.OK_OPTION:
	    loadGame();
	    break;
	case JOptionPane.NO_OPTION:
	    break;
	case JOptionPane.CANCEL_OPTION:
	    break;
	}
    }

    @Override
    public void onBulletLocationChanged(Bullet b) {
	if (b != null) {
	    b.setPosY(b.getPosY() - b.getSpeed());
	    if (b.getPosY() <= 0) {
		this.bullets.remove(b);
	    }

	    EnemyPlane enemyPlane = b.hitEnemyPlanes();
	    if (enemyPlane != null) {
		enemyPlane.drawFighting();
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
		    this.score += enemyPlane.getKilledScore();
		    this.enemyPlanes.remove(enemyPlane);
		    this.bullets.remove(b);
		    enemyPlane.drawKilled();
		}
	    }
	}
    }

    @Override
    public void onEnemyPlaneLocationChanged(EnemyPlane p) {
	if (p != null) {
	    p.setPosY(p.getPosY() + p.getSpeed());
	    if (p.getPosY() >= (this.getHeight())) {
		if (p.getEnemyType().equals(EnemyPlaneType.BOSS_ENEMY_PLANE)) {
		    this.bossPlaneFlyingSoundPlayer.stop();
		}
		enemyPlanes.remove(p);
	    }

	    if (p.getRectangle().intersects(myPlane.getRectange())) {
		// game ends
		synchronized (this) {
		    if (gameRunning) {
			this.stopGame();
		    }
		}
	    }
	}
    }

    @Override
    public void paint(Graphics g) {
	super.paint(g);
	if (gameRunning) {
	    drawScore(g);
	    myPlane.draw();
	    for (int i = 0; i < this.enemyPlanes.size(); i++) {
		EnemyPlane enemyPlane = this.enemyPlanes.get(i);
		enemyPlane.draw();
	    }
	    for (int i = 0; i < this.bullets.size(); i++) {
		Bullet b = this.bullets.get(i);
		b.draw();
	    }

	}

    }

    private int SCORE_IMG_POS_X = 10;
    private int SCORE_IMG_POS_Y = 30;

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
	    while (gameRunning) {
		try {
		    Thread.sleep(Config.MAIN_FRAME_REPAINT_INTERVAL);
		} catch (InterruptedException e) {

		}
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
		    EnemyPlane smallPlane = EnemyPlaneFactory.createEnemyPlane(MainFrame.this,
			    EnemyPlaneType.SMALL_ENEMY_PLANE);
		    enemyPlanes.add(smallPlane);
		    remainTimeToPopSmallPlane = Config.POP_SMALL_ENEMY_PLANE_INTERVAL;
		}

		if (remainTimeToPopBigPlane > 0) {
		    remainTimeToPopBigPlane -= Config.MAIN_FRAME_REPAINT_INTERVAL;
		} else {
		    // pop a big enemy plane
		    EnemyPlane bigPlane = EnemyPlaneFactory.createEnemyPlane(MainFrame.this,
			    EnemyPlaneType.BIG_ENEMY_PLANE);
		    enemyPlanes.add(bigPlane);
		    remainTimeToPopBigPlane = Config.POP_BIG_ENEMY_PLANE_INTERVAL;
		}

		if (remainTimeToPopBossPlane > 0) {
		    remainTimeToPopBossPlane -= Config.MAIN_FRAME_REPAINT_INTERVAL;
		} else {
		    // pop a boss enemy plane
		    EnemyPlane bossPlane = EnemyPlaneFactory.createEnemyPlane(MainFrame.this,
			    EnemyPlaneType.BOSS_ENEMY_PLANE);
		    enemyPlanes.add(bossPlane);
		    remainTimeToPopBossPlane = Config.POP_BOSS_ENEMY_PLANE_INTERVAL;
		    bossPlaneFlyingSoundPlayer.loop();
		}

		MainFrame.this.repaint();
	    }
	}

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	// nothing to do
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	if (gameRunning) {
	    myPlane.mouseMoved(e);
	    this.repaint();
	}
    }

}
