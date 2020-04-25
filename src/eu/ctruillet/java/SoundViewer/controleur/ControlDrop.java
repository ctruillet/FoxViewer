/*
 * Copyright (c) 2020.
 * Clément Truillet (clement@ctruillet.eu)
 */

package eu.ctruillet.java.SoundViewer.controleur;


import ddf.minim.Minim;
import drop.DropEvent;
import eu.ctruillet.java.SoundViewer.modele.AudioFile;
import processing.core.PApplet;


import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ControlDrop {
	//Attributs
	private ArrayList<AudioFile> queue = new ArrayList<>();
	private Minim minim;

	//Constructeur
	public ControlDrop(PApplet fileSystemHandler){
		this.minim = new Minim(fileSystemHandler);

	}

	//Méthodes


	public void dropEvent(DropEvent theDropEvent) {
		if(isGoodType(theDropEvent.file())){
			File file = theDropEvent.file();
			queue.add(new AudioFile(file, minim));

		}else if(isDirectory(theDropEvent.file())){
			this.addAudioFileInRepertory(theDropEvent.file());
		}
	}

	private void addAudioFileInRepertory(File repertory){
		for(File file : repertory.listFiles()){
			if(isGoodType(file)){
				queue.add(new AudioFile(file, minim));

			}else if(isDirectory(file)){
				this.addAudioFileInRepertory(file);
			}
		}
	}

	private boolean isDirectory(File file){
		return file.isDirectory();
	}

	private boolean isGoodType(File file){
		if(file.getName().endsWith("mp4") || file.getName().endsWith("mp3")){
			return true;
		}
		return false;
	}

	public ArrayList<AudioFile> getQueue() {
		return queue;
	}
}
