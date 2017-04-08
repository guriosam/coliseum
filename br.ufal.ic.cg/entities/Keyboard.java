package entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private Camera camera;
	
	public Keyboard(Camera camera){
		this.camera = camera;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {

	}

	static int shoulder = 0;

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			camera.cameraElevation += 2;
		}

		if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			camera.cameraElevation -= 2;
		}

		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			camera.cameraAzimuth += 3;
		}

		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			camera.cameraAzimuth -= 3;
		}

		if (event.getKeyCode() == KeyEvent.VK_I) {
			camera.cameraSpeed = 0.1F;
		}

		if (event.getKeyCode() == KeyEvent.VK_O) {
			camera.cameraSpeed = -0.1F;
		}

		if (event.getKeyCode() == KeyEvent.VK_D) {
			if (shoulder >= 85) {
				return;
			}
			shoulder = (shoulder + 5) % 360;
		}
		if (event.getKeyCode() == KeyEvent.VK_F) {
			if (shoulder == 0) {
				return;
			}
			shoulder = (shoulder - 5) % 360;
		}

		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}

		if (camera.cameraAzimuth > 359)
			camera.cameraAzimuth = 1;

		if (camera.cameraAzimuth < 1)
			camera.cameraAzimuth = 359;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_I || event.getKeyCode() == KeyEvent.VK_O) {
			camera.cameraSpeed = 0;
		}

	}


}
