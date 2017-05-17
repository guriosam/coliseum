package entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public abstract class Floor {

	protected GLU glu;
	protected Textures textures;
	protected int quantidade;
	protected int limite;

	public Floor(GLU glu, Textures textures) {
		this.glu = glu;
		this.textures = textures;
		quantidade = 36;
		limite = 21;
	}

	protected void createOuterRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		GLUquadric disk = glu.gluNewQuadric();

		int x = 0;
		int y = 0;

		for (float j = (floor - 1) * 5; j < height*floor - 1; j += 0.02) {
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
					glu.gluPartialDisk(disk, end - 7, end - 6, 5, 1, x, y);
				} else {
					// Parte menor
					glu.gluPartialDisk(disk, end - 7, end - 6, 5, 1, x, y);
				}

				x += 10;
			}
			gl.glPopMatrix();
		}

	}

	public abstract void createRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor);
	
	protected abstract void createFloor(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor);
}
