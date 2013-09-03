package com.beancore;

import com.beancore.ui.MainFrame;

public class Main {
    public static void main(String args[]) throws InterruptedException {
	MainFrame mainFrame;
	try {
	    mainFrame = new MainFrame();
	    mainFrame.loadGame();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
}
