/*
 * Copyright (c) 2020.
 * Clément Truillet (clement@ctruillet.eu)
 */

package eu.ctruillet.java.FoxViewer.vue;

import ddf.minim.Minim;
import drop.DropEvent;
import drop.SDrop;
import eu.ctruillet.java.FoxViewer.controleur.ControlDrop;
import eu.ctruillet.java.FoxViewer.modele.AudioFile;
import processing.core.PApplet;

import java.awt.event.KeyEvent;
import java.io.File;

public class Main extends PApplet{
	//Attributs
	public static PApplet processing;
	private File file;
	private Minim minim;
	private AudioFile player;
	private ControlDrop controlDrop;
	private SDrop drop;

	//Constructeur

	//Méthodes
	public static void main(String[] args) {
		PApplet.main("eu.ctruillet.java.FoxViewer.vue.Main", args);
	}

	public void settings(){
		size(640,480);

	}

	public void setup(){
		//Initialisation
		processing = this;

		surface.setIcon(loadImage("doc/logo.png"));
		surface.setTitle("FoxViewer");

		minim = new Minim(this);
		this.controlDrop = new ControlDrop(this);
		drop = new SDrop(this);
	}




	public void draw() {

		background(0,0,0, (float) 0.9);
		noStroke();
		textAlign(CENTER);

		translate(0, height / 2);

		player = controlDrop.getCurrentFile();

		if(player!=null) {
			player.play();
			colorMode(HSB, player.getBufferSize(), 255, 255,1);

			for (int i = 0; i < player.getBufferSize(); i += 22) {
				fill(i, 255, 255);

				rect(i,
						-200 * abs(player.getValueRightAt(i)),
						20,
						200 * abs(player.getValueRightAt(i))
				);

				rect(	i,
						200 * abs(player.getValueLeftAt(i)),
						20,
						-200 * abs(player.getValueLeftAt(i))
				);
			}

			colorMode(RGB,255,255,255,1);

			if(player.isEnding()){
				controlDrop.next();
			}

			if(player.isPausing()){
				fill(255,255,255,1);
				rect(width - 50,-height/2 + 30,7,20);
				rect(width - 40 ,-height/2 + 30,7,20);
			}

			colorMode(RGB);
			fill(0,200,0);

			surface.setTitle(controlDrop.getCurrentFile().getFile().getName());
			text(controlDrop.getCurrentFile().getFile().getName(),width/2,height/2-45);

			if(controlDrop.getPreviousFile()!=null){
				textAlign(LEFT);
				text(controlDrop.getPreviousFile().getFile().getName(),10,height/2-15);
			}

			if(controlDrop.getNextFile()!=null){
				textAlign(RIGHT);
				text(controlDrop.getNextFile().getFile().getName(),width-10,height/2-15);
			}



		}
	}

	public void dropEvent(DropEvent theDropEvent) {
		controlDrop.dropEvent(theDropEvent);
		for (AudioFile audioFile : controlDrop.getQueue()) {
			System.out.println(audioFile);

		}
	}

	public void keyPressed(){
		if(key==CODED){
			if(keyCode== KeyEvent.VK_RIGHT){
				controlDrop.next();
			}else if(keyCode== KeyEvent.VK_LEFT){
				controlDrop.previous();
			}
		}else if(key==' '){
			controlDrop.pauseResume();
		}else if(key==ENTER){
			controlDrop.rewind();
		}

	}


}
