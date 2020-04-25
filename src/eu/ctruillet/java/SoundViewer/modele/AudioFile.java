/*
 * Copyright (c) 2020.
 * Clément Truillet (clement@ctruillet.eu)
 */

package eu.ctruillet.java.SoundViewer.modele;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

import java.io.File;

public class AudioFile {
	//Attributs
	private File file;
	private AudioPlayer player;

	//Constructeur
	public AudioFile(File file, Minim minim){
		this.file=file;
		this.player = minim.loadFile(file.getAbsolutePath());
	}


	//Méthodes
	public AudioPlayer loadFile(){
		return this.player;
	}

	public void play(){
		player.play();
	}

	public void stop(){
		player.pause();
	}

	public void pause(){
		player.pause();
	}

	public void rewind(){
		player.rewind();
	}

	@Override
	public String toString() {
		return "AudioFile{" +
				"name=" + file.getName() +
				"type=" + file.getAbsolutePath() +
				'}';
	}
}
