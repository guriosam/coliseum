package entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Floor1 extends Floor {

	public Floor1(GLU glu, Textures textures) {
		super(glu, textures);
	}

	@Override
	public void createRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {
		GL2 gl = drawable.getGL().getGL2();

		// inner ring
		createOuterRing(drawable, start, end, height, texture, floor);

		// Floor
		createArena(drawable, start, end, height, texture, floor);

		createFloor(drawable, start, end, height, texture, floor);

		gl.glFlush();

	}

	private void createArena(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.textureSand);

		GLUquadric disk = glu.gluNewQuadric();
		int x = 0;
		int y = 0;

		for (float j = height - 1; j < height; j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);

			x = 5;
			y = 15;
			gl.glRotatef(90, -1, 0, 0);
			int m = 0;
			for (int i = 0; i < quantidade; i++) {
				glu.gluPartialDisk(disk, start, start + 25, 5, 1, m, 20);
				m += 20;
			}
			gl.glPopMatrix();
		}
	}

	protected void createFloor(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		int limit = 10;

		int size = 24;

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.textureSand);

		GLUquadric disk = glu.gluNewQuadric();
		int x = 0;
		int y = 0;

		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.floor);
		// Chão do Terreo
		for (float j = height - 1; j < height; j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);

			x = 5;
			y = 15;
			gl.glRotatef(90, -1, 0, 0);
			int m = 0;
			for (int i = 0; i < size; i++) {
				if (i < limit) {
					glu.gluPartialDisk(disk, start + 20, end - 1.5, 5, 1, m, 20);
				} else {
					glu.gluPartialDisk(disk, start + 20, end - 7, 5, 1, m, 20);
				}
				m += 20;
			}
			gl.glPopMatrix();
		}
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.pedra);
		// Borda Externa
		for (float j = height * (floor) - 1; j < height * (floor); j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);
			x = 5;
			y = 15;
			gl.glRotatef(90, -1, 0, 0);
			// glu.gluPartialDisk(disk, end - 1, end, 5, 1, m, 20);
			glu.gluPartialDisk(disk, end - 1, end, 30, 5, 0, 210);
			glu.gluPartialDisk(disk, end - 7, end - 6, 30, 5, 200, 160);
			// glu.gluPartialDisk(disk, end - 7, end - 6, 5, 1, m, 20);
			gl.glPopMatrix();
		}

	}

}
