package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import CreationSystem.CreationSystem;
import CreationSystem.GUI.HUD;

public class CreationScene implements Scene {

	CreationSystem system;
	HUD display;
	
	@Override
	public void start() {
		system = new CreationSystem();
		display = new HUD(system);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		system.update();
		display.update();
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		system.keyPressed(evt);
	}

	@Override
	public void render(Graphics g) {
		display.paint(g);
	}

}
