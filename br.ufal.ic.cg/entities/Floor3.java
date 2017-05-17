package entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class Floor3 extends Floor {

	private GL2 gl;

	public Floor3(GLU glu, Textures textures) {
		super(glu, textures);
	}

	@Override
	public void createRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {
		gl = drawable.getGL().getGL2();

		// inner ring
		createInnerRing(drawable, start, end, height, texture, floor);
		// outer ring
		createOuterRing(drawable, start, end, height, texture, floor);

		// floor
		createFloor(drawable, start, end, height, texture, floor);

		gl.glFlush();

	}

	private void createInnerRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		GLUquadric disk = glu.gluNewQuadric();
		int x = 0;
		int y = 0;

		for (float j = 5 * (floor - 1); j < height * floor; j += 0.02) {
			gl.glPushMatrix();
			gl.glColor3f(0.6f, 0.6f, 0.6f);
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glRotatef(90, -1, 0, 0);
			x = 5;
			y = 10;

			for (int i = 0; i < quantidade; i++) {
				glu.gluPartialDisk(disk, start, start + 1, 5, 1, x, y);
				x += 20;
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
			glu.gluPartialDisk(disk, start + 1, end - 1, 30, 5, 0, 210);
			glu.gluPartialDisk(disk, start + 1, end - 7, 30, 5, 200, 160);
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
			glu.gluPartialDisk(disk, end - 7, end - 6, 30, 5, 200, 160);
			gl.glPopMatrix();
		}

		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.pedraEscura);
		// Borda interna
		for (float j = height * floor - 1; j < height * floor; j += 0.02) {
			gl.glPushMatrix();
			gl.glTranslatef(1, j, 1);
			glu.gluQuadricTexture(disk, true);
			gl.glColor3f(1f, 1f, 1f);
			gl.glRotatef(90, -1, 0, 0);
			int m = 0;
			for (int i = 0; i < quantidade; i++) {
				if (i < limite) {
					glu.gluPartialDisk(disk, start, start + 1, 5, 1, m, 20);
				} else {
					glu.gluPartialDisk(disk, start, start + 1, 5, 1, m, 20);
				}
				m += 20;
			}
			gl.glPopMatrix();
		}

		gl.glEnd();
	}

}
