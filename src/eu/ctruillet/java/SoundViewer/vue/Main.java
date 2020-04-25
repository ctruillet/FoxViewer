/*
 * Copyright (c) 2020.
 * Clément Truillet (clement@ctruillet.eu)
 */

package eu.ctruillet.java.SoundViewer.vue;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import drop.DropEvent;
import drop.SDrop;
import eu.ctruillet.java.SoundViewer.controleur.ControlDrop;
import eu.ctruillet.java.SoundViewer.modele.AudioFile;
import processing.core.PApplet;
import java.io.File;

public class Main extends PApplet{
	//Attributs
	public static PApplet processing;
	private File file;
	private Minim minim;
	private AudioPlayer player;
	private ControlDrop controlDrop;
	private SDrop drop;

	//Constructeur

	//Méthodes
	public static void main(String[] args) {
		PApplet.main("eu.ctruillet.java.SoundViewer.vue.Main", args);
	}

	public void settings(){
		size(640,480);
	}

	public void setup(){
		//Initialisation
		processing = this;

		minim = new Minim(this);
		this.controlDrop = new ControlDrop(this);
		drop = new SDrop(this);

		player = minim.loadFile("./data/zombie.mp3");
		player.play();

		colorMode(HSB,player.bufferSize()-22,255,255);

	}
	private float getValueAt(AudioPlayer player, int i){
		if(i>player.bufferSize()-1){
			return (float) 0.0;
		}
		return (player.left.get(i) + player.right.get(i))/2;
	}

	private float getValueLeftAt(AudioPlayer player, int i){
		if(i>player.bufferSize()-1){
			return (float) 0.0;
		}
		return player.left.get(i);
	}

	private float getValueRightAt(AudioPlayer player, int i){
		if(i>player.bufferSize()-1){
			return (float) 0.0;
		}
		return player.right.get(i);
	}

	public void draw() {
		background(0);
		noStroke();
		translate(0, height / 2);

		for (int i = 0; i < player.bufferSize() - 22; i += 22) {
			fill(i, 255, 255);

			rect(i,
					-200 * abs(getValueRightAt(player, i)),
					20,
					200 * abs(getValueRightAt(player, i))
			);


			rect(i,
					200 * abs(getValueLeftAt(player, i)),
					20,
					-200 * abs(getValueLeftAt(player, i))
			);

		}
	}

	void dropEvent(DropEvent theDropEvent) {
		controlDrop.dropEvent(theDropEvent);
		for (AudioFile audioFile : controlDrop.getQueue()) {
			System.out.println(audioFile);

		}
	}


}
