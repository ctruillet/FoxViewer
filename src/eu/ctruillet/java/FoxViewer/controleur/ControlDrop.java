/*
 * Copyright (c) 2020.
 * Clément Truillet (clement@ctruillet.eu)
 */

package eu.ctruillet.java.FoxViewer.controleur;


import ddf.minim.Minim;
import drop.DropEvent;
import eu.ctruillet.java.FoxViewer.modele.AudioFile;
import processing.core.PApplet;


import java.io.File;
import java.util.ArrayList;

public class ControlDrop {
	//Attributs
	private ArrayList<AudioFile> queue = new ArrayList<>();
	private Minim minim;
	private int curseur=-1;
	private boolean isPlaying = false;


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
//			}else if(isDirectory(file)){
//				this.addAudioFileInRepertory(file);
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

	public AudioFile getCurrentFile(){
		if(!isPlaying && queue.size()>=1){
			isPlaying=true;
			curseur++;
		}

		if(curseur < 0 || curseur >= queue.size()){
			return null;
		}

		return queue.get(curseur);
	}

	public AudioFile getNextFile(){
		if(curseur < 0 || curseur+1 >= queue.size() ){
			return null;
		}
		return queue.get(curseur+1);

	}

	public AudioFile getPreviousFile(){
		if(curseur < 1 || curseur-1 > queue.size()){
			return null;
		}
		return queue.get(curseur-1);
	}

	public void next(){
		if(curseur+1<this.queue.size()){
			this.getCurrentFile().pause();
			curseur++;
			this.getCurrentFile().resume();
			//this.getPreviousFile().rewind();
			System.out.println("NEXT");
		}

	}

	public void previous(){
		if(curseur-1 >= 0) {
			this.getCurrentFile().pause();
			curseur--;
			this.getCurrentFile().resume();
			//this.getNextFile().rewind();
			System.out.println("PREVIOUS");
		}
	}

	public void rewind(){
		this.getCurrentFile().rewind();
		System.out.println("REWIND");
	}

	public void pauseResume(){
		if (this.getCurrentFile() == null) {
			return;
		}
		if(!this.getCurrentFile().isPausing()){
			this.getCurrentFile().pause();
			System.out.println("PAUSE");
		}else{
			this.getCurrentFile().resume();
			System.out.println("RESUME");
		}


	}

	@Override
	public String toString() {
		return "ControlDrop{" + "\n" +
				"curseur=" + curseur + "\n" +
				"queue=" + queue + "\n" +
				'}';
	}
}
