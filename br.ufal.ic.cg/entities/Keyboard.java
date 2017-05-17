package entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private Camera camera;

	public Keyboard(Camera camera) {
		this.camera = camera;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	static int shoulder = 0;

	public void keyPressed(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.VK_L) {
			camera.luz = !camera.luz;
		}

		if (event.getKeyCode() == KeyEvent.VK_C) {
			camera.doorAngle += 2;
			camera.distanceDoor += 0.05;

			if (camera.doorAngle >= 60) {
				camera.doorAngle = 60;
			}

			if (camera.distanceDoor > -53) {
				camera.distanceDoor = -53;
			}

		}

		if (event.getKeyCode() == KeyEvent.VK_X) {
			camera.doorAngle -= 2;
			camera.distanceDoor -= 0.03;

			if (camera.doorAngle <= -30) {
				camera.doorAngle = -30;
			}

			if (camera.distanceDoor < -54) {
				camera.distanceDoor = -54;
			}

		}

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

		if (event.getKeyCode() == KeyEvent.VK_W) {
			camera.cameraSpeed = 3F;
		}

		if (event.getKeyCode() == KeyEvent.VK_S) {
			camera.cameraSpeed = -3F;
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
		if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			camera.cameraSpeed = 0;
		}

	}

}
