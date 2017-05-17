package entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Floor5 extends Floor {

	private GL2 gl;

	public Floor5(GLU glu, Textures textures) {
		super(glu, textures);
	}

	@Override
	public void createRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {
		gl = drawable.getGL().getGL2();

		// outer ring
		createOuterRing(drawable, start, end, height, texture, floor);

		createFloor(drawable, start, end, height, texture, floor);

		gl.glFlush();

	}

	@Override
	protected void createOuterRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		GLUquadric disk = glu.gluNewQuadric();

		int x = 0;
		int y = 0;
		float k = 0;
		for (float j = (floor - 1) * 5; j < height * floor - 1; j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);
			gl.glRotatef(90, -1, 0, 0);
			x = 5;
			y = 5;

			for (int i = 0; i < quantidade; i++) {
				if (i < limite) {
					// Parte maior
					glu.gluPartialDisk(disk, end - 1, end, 5, 1, x, y);
					// glu.gluPartialDisk(disk, end - 7, end - 6, 5, 1, x, y);
					glu.gluPartialDisk(disk, end - 13 + k, end - 6, 5, 1, x, y);
				}
				x += 10;
				if (k < 6) {
					k += 0.0008;
				}
			}
			gl.glPopMatrix();
		}

	}

	protected void createFloor(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		GLUquadric disk = glu.gluNewQuadric();

		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.floor);

		for (float j = height * (floor) - 1; j < height * (floor); j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);
			gl.glRotatef(90, -1, 0, 0);
			glu.gluPartialDisk(disk, end - 6, end - 1, 30, 5, 0, 210);
			// glu.gluPartialDisk(disk, start + 1, end - 7, 30, 5, 200, 160);
			gl.glPopMatrix();
		}

		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.pedra);
		// Borda Externa
		for (float j = height * (floor) - 1; j < height * (floor); j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);
			gl.glRotatef(90, -1, 0, 0);
			glu.gluPartialDisk(disk, end - 1, end, 30, 5, 0, 210);
			// glu.gluPartialDisk(disk, end - 7, end - 6, 30, 5, 200, 160);
			gl.glPopMatrix();
		}

		// Borda interna
		for (float j = height * floor - 1; j < height * floor; j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);
			gl.glRotatef(90, -1, 0, 0);
			glu.gluPartialDisk(disk, end - 7, end - 6, 30, 5, 0, 210);
			gl.glPopMatrix();
		}

		gl.glEnd();
	}

}
