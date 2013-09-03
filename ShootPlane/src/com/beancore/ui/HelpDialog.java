package com.beancore.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.beancore.config.Config;
import com.beancore.util.FileUtil;

public class HelpDialog extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextPane helpContentTextPane;
    private JScrollPane scrollPane;

    public HelpDialog() {
	this.initComponent();
    }

    private void initComponent() {
	this.helpContentTextPane = new JTextPane();
	this.helpContentTextPane.setEditable(false);
	this.helpContentTextPane.setContentType("text/html");
	try {
	    this.helpContentTextPane.setText(FileUtil.readFileToString(Config.HELP_FILE_PATH));
	} catch (IOException e) {
	    e.printStackTrace();
	}

	this.scrollPane = new JScrollPane(this.helpContentTextPane);
	this.scrollPane.setAutoscrolls(true);

	Container c = this.getContentPane();
	c.add(this.scrollPane, BorderLayout.CENTER);

	this.setTitle("Help");
	this.setIconImage(new ImageIcon(Config.LOGO_IMG).getImage());
	this.setSize(Config.HELP_DIALOG_WIDTH, Config.HELP_DIALOG_HEIGHT);
	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

}
