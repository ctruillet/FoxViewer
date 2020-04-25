/*
 * Copyright (c) 2020.
 * Clément Truillet (clement@ctruillet.eu)
 */

package eu.ctruillet.java.FoxViewer.modele;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

import java.io.File;

public class AudioFile {
	//Attributs
	private File file;
	private AudioPlayer player;
	private boolean isPlaying = false;
	private boolean isPausing = false;

	//Constructeur
	public AudioFile(File file, Minim minim){
		this.file=file;
		this.player = minim.loadFile(file.getAbsolutePath());
	}


	//Méthodes
	public AudioPlayer loadFile(){
		return this.player;
	}

	public void play() {
		if (!isPlaying) {
			player.play();
			isPlaying = true;
		}
	}

	public void resume(){
		player.play();
		isPausing=false;
	}

	public void stop(){
		player.pause();
		player.rewind();
		isPlaying=false;
	}

	public void pause(){
		player.pause();
		isPausing=true;
	}

	public void rewind(){
		player.rewind();
	}

	public boolean isPlaying(){
		return isPlaying;
	}

	@Override
	public String toString() {
		return "AudioFile{" +                      "\n" +
				"\tname=" + file.getName() + 		   "\n" +
				"\ttype=" + file.getAbsolutePath() + "\n" +
				"\tposition=" + player.position() +  "\n" +
				"\ttaille=" + player.length() +  	   "\n" +
				'}';
	}

	public float getValueAt(int i){
		if(i>player.bufferSize()-1){
			return (float) 0.0;
		}
		return (player.left.get(i) + player.right.get(i))/2;
	}

	public float getValueLeftAt(int i){
		if(i>player.bufferSize()-1){
			return (float) 0.0;
		}
		return player.left.get(i);
	}

	public float getValueRightAt(int i){
		if(i>player.bufferSize()-1){
			return (float) 0.0;
		}
		return player.right.get(i);
	}

	public int getBufferSize(){
		return player.bufferSize();
	}

	public boolean isEnding(){
		return (!player.isPlaying() && !isPausing);
	}

	public boolean isPausing(){
		return isPausing;
	}

	public File getFile(){
		return this.file;
	}
}
