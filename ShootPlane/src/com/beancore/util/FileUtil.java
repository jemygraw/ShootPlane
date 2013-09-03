package com.beancore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.beancore.entity.Score;

public class FileUtil {
    public static String readFileToString(String filePath) throws IOException {
	StringBuilder sb = new StringBuilder();
	File file = new File(filePath);
	BufferedReader br = new BufferedReader(new FileReader(file));
	String line = null;
	while ((line = br.readLine()) != null) {
	    sb.append(line).append("\r\n");
	}
	br.close();
	return sb.toString();
    }

    public static void writeScore(List<Score> scoreList, String filePath) throws FileNotFoundException, IOException {
	ObjectOutputStream objOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
	for (Score score : scoreList) {
	    objOutputStream.writeObject(score);
	}
	objOutputStream.writeObject(null);
	objOutputStream.flush();
	objOutputStream.close();
    }

    public static List<Score> readScore(String filePath) throws FileNotFoundException, IOException,
	    ClassNotFoundException {
	List<Score> scoreList = new ArrayList<Score>();
	ObjectInputStream objInputStream = new ObjectInputStream(new FileInputStream(filePath));
	Object obj = null;
	while ((obj = objInputStream.readObject()) != null) {
	    scoreList.add((Score) obj);
	}
	objInputStream.close();
	return scoreList;
    }
}
