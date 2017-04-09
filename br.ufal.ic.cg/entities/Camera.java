package entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Camera implements GLEventListener {

	private Keyboard keyboard;
	private GLU glu;
	private GLUT glut;
	private Textures textures;
	private final int DEF_D = 5;

	// Define camera variables
	float cameraAzimuth = 90.0f, cameraSpeed = 0.0f, cameraElevation = 0.0f;

	// Set camera at (0, 0, -20)
	float cameraCoordsPosx = 5.0f, cameraCoordsPosy = 1f, cameraCoordsPosz = 0.0f;

	// Set camera orientation
	float cameraUpx = 0.0f, cameraUpy = 1.0f, cameraUpz = 0.0f;

	public Camera(GLU glu, GLUT glut, Textures textures) {
		this.glu = glu;
		this.glut = glut;
		this.textures = textures;
	}

	public void moveCamera() {
		float[] tmp = polarToCartesian(cameraAzimuth, cameraSpeed, cameraElevation);

		// Replace old x, y, z coords for camera
		cameraCoordsPosx += tmp[0];
		cameraCoordsPosy += tmp[1];
		cameraCoordsPosz += tmp[2];
	}

	public void aimCamera(GL2 gl, GLU glu) {
		gl.glLoadIdentity();

		// Calculate new eye vector
		float[] tmp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation);

		// Calculate new up vector
		float[] camUp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation + 90);

		cameraUpx = camUp[0];
		cameraUpy = camUp[1];
		cameraUpz = camUp[2];

		// Não deixar ir abaixo do chão
		if (cameraCoordsPosy < 1) {
			cameraCoordsPosy = 1;
		}

		glu.gluLookAt(cameraCoordsPosx, cameraCoordsPosy, cameraCoordsPosz, cameraCoordsPosx + tmp[0],
				cameraCoordsPosy + tmp[1], cameraCoordsPosz + tmp[2], cameraUpx, cameraUpy, cameraUpz);
	}

	private float[] polarToCartesian(float azimuth, float length, float altitude) {
		float[] result = new float[3];
		float x, y, z;

		// Do x-z calculation

		float theta = (float) Math.toRadians(90 - azimuth);
		float tantheta = (float) Math.tan(theta);
		float radian_alt = (float) Math.toRadians(altitude);
		float cospsi = (float) Math.cos(radian_alt);

		x = (float) Math.sqrt((length * length) / (tantheta * tantheta + 1));
		z = tantheta * x;

		x = -x;

		if ((azimuth >= 180.0 && azimuth <= 360.0) || azimuth == 0.0f) {
			x = -x;
			z = -z;
		}

		// Calculate y, and adjust x and z
		y = (float) (Math.sqrt(z * z + x * x) * Math.sin(radian_alt));

		if (length < 0) {
			x = -x;
			z = -z;
			y = -y;
		}

		x = x * cospsi;
		z = z * cospsi;

		// In contrast we could use the simplest form for computing Cartesian
		// from Spherical as follows:
		// x = (float)(length *
		// Math.sin(Math.toRadians(altitude))*Math.cos(Math.toRadians(azimuth)));
		// y = (float)(length *
		// Math.sin(Math.toRadians(altitude))*Math.sin(Math.toRadians(azimuth)));
		// z = (float)(length * Math.cos(Math.toRadians(altitude)));

		result[0] = x;
		result[1] = y;
		result[2] = z;

		return result;
	}

	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		// gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		aimCamera(gl, glu);
		moveCamera();

		// gl.glPushMatrix();
		// gl.glTranslated(0, 1, 0);
		// gl.glColor3f(1f, 1f, 1f);
		// glut.glutSolidSphere(0.5f, 20, 20);
		// gl.glPopMatrix();
		//
		// gl.glPushMatrix();
		// gl.glTranslated(-8f, 0.2f, 4f);
		// gl.glTranslated(0, 1, 0);
		// gl.glColor3f(1f, 1f, 1f);
		// glut.glutSolidSphere(0.5f, 20, 20);
		// gl.glPopMatrix();
		//
		// gl.glPushMatrix();
		// gl.glTranslated(-3.5f, 2f, 7.5f);
		// gl.glColor3f(1f, 1f, 1f);
		// glut.glutSolidSphere(0.03f, 20, 20);
		// gl.glPopMatrix();
		//
		// gl.glPushMatrix();
		// gl.glTranslated(-3.5f, 2f, 5.2f);
		// gl.glColor3f(1f, 1f, 1f);
		// glut.glutSolidSphere(0.03f, 20, 20);
		// gl.glPopMatrix();
		//
		// gl.glPushMatrix();
		// gl.glTranslated(-3.5f, 2f, 3.2f);
		// gl.glColor3f(1f, 1f, 1f);
		// glut.glutSolidSphere(0.03f, 20, 20);
		// gl.glPopMatrix();

		createCorredor(drawable);
		// createLab(drawable);

		// Iluminação
		// init_lighting(gl);

		gl.glFlush();

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		textures.carregar_texturas(drawable);
		gl.glEnable(GL2.GL_DEPTH_TEST);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60.0, width / (float) height, 0.1, 90.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

	}

	private void createCorredor(GLAutoDrawable drawable) {
		// chao do corredor
		float groundWidth = 72;
		float groundHeight = 72;
		createCube(drawable, 3, groundWidth, 0.2f, groundHeight, -0.5f, 0, 0, 0, 0, 0);

		// Criar Teto
		// createCube(drawable, textureRoof2, 3, 0.2f, 18, -0.5f, 2.1f, 0, 0, 0,
		// 0);

		// Parede esquerda maior //
		// createCube(drawable, 0, 0.2f, 2, 14.5f, -2, 1, -2.8f);
		for (float i = 0; i < 10; i += 1) {
			//createCube(drawable, textures.getTextureArc1(), 0.2f, 2, 1.0f, -2, 1, i, 0, 0.5f, 0);
		}

		// (Drawable, textureColor,
		//largura, altura, comprimento, 
		//x, y, z, 
		//rotate vertical, rotate diagonal, rotate horizontal)
		
		createCube(drawable, textures.getTextureArc1(), 
				0.2f, 2, 3.0f,
				-2, 5, 20, 
				0, 0.0f, -25);
		
		createCube(drawable, textures.getTextureArc1(), 
				0.2f, 2, 3.0f,
				-4, 5, 20, 
				0, 0.0f, -25);
		
		createCube(drawable, textures.getTextureArc1(), 
				0.2f, 2, 3.0f,
				-2, 5, -20, 
				0, 0.0f, 25);
		
		createCube(drawable, textures.getTextureArc1(), 
				0.2f, 2, 3.0f,
				-4, 5, -20, 
				0, 0.0f, 25);
		
		//createCube(drawable, textures.getTextureArc1(), 
			//	0.2f, 2, 5.0f,
			//	-12, 5, 17, 
			//	0, 25.0f, -25);
		
		//out circle
		createCylinder(drawable, textures.getTextureArc1(),
				0.2f, 2, 27.0f,
				-2, 0, 0,
				0, 0.5f, 0);
		
		createCylinder(drawable, textures.getTextureArc1(),
				0.2f, 2, 27.0f,
				-2, 3.5f, 0,
				0, 0.5f, 0);
		
		createCylinder(drawable, textures.getTextureArc1(),
				0.2f, 2, 27.0f,
				-2, 7.0f, 0,
				0, 0.5f, 0);
		
		//inner circles
		
		createCylinder(drawable, 2,
				0.2f, 2, 22.0f,
				-2, 0, 0,
				0, 0.5f, 0);
		
		createCylinder(drawable, 2,
				0.2f, 2, 22.0f,
				-2, 3f, 0,
				0, 0.5f, 0);
		

		
		// inner inner circle
		
		createCylinder(drawable, 3,
				0.2f, 2, 17.0f,
				-2, 0, 0,
				0, 0.5f, 0);
		

		// createCube(drawable, 1, 0.2f, 2, 4f, -2, 1, -0.2f, 0, 0, 0);
		// createCube(drawable, 1, 0.2f, 2, 4f, -2, 1, -3.5f, 0, 0, 0);
		// createCube(drawable, 1, 0.2f, 2, 4f, -2, 1, -6.8f, 0, 0, 0);

		// Parede esquerda menor
		// createCube(drawable, textureWall, 0.2f, 2, 4.7f, -2, 1, 7.6f, 0, 0,
		// 0);

		// Parede direita
		// createCube(drawable, textureCorredor, 0.2f, 2, 4f, 1, 1, 3.1f, 0, 0,
		// 0);
		// createCube(drawable, textureCorredor, 0.2f, 2, 4f, 1, 1, -0.2f, 0, 0,
		// 0);
		// createCube(drawable, textureCorredor, 0.2f, 2, 4f, 1, 1, -3.5f, 0, 0,
		// 0);
		// createCube(drawable, textureCorredor, 0.2f, 2, 4f, 1, 1, -6.8f, 0, 0,
		// 0);
		// createCube(drawable, textureCorredor, 0.2f, 2, 4f, 1, 1, 6.4f, 0, 0,
		// 0);

		// Parede direita menor
		// createCube(drawable, textureWall, 0.2f, 2, 3f, 1, 1, 8.5f, 0, 0, 0);

		// fundo
		// createCube(drawable, textureFundo, 3f, 2, 0.2f, -0.5f, 1, 9f, 0, 0,
		// 0);

		// Frente
		// createCube(drawable, textureWall, 3f, 2, 0.2f, -0.5f, 1, -8.8f, 0, 0,
		// 0);

	}

	private void createCube(GLAutoDrawable drawable, int texture, float width, float height, float lenght, float x,
			float y, float z, float rotateX, float rotateY, float rotateZ) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glPushMatrix();

		gl.glTranslatef(-z, y, -x);
		gl.glRotated(rotateX, 1, 0, 0);
		gl.glRotated(rotateY, 0, 1, 0);
		gl.glRotated(rotateZ, 0, 0, 1);
		//
		// gl.glScalef(lenght, height, width);
		// glut.glutSolidCube(1f);
		// gl.glScalef(0, 0, 0);

		gl.glTranslatef(z, -y, x);

		// ---------Textura------

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
		gl.glColor3f(1f, 1f, 1f);
		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,
		// GL2.GL_LINEAR_MIPMAP_NEAREST);
		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER,
		// GL2.GL_LINEAR_MIPMAP_LINEAR);
		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S,
		// GL2.GL_REPEAT);
		// gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T,
		// GL2.GL_REPEAT);
		//
		gl.glBegin(GL2.GL_QUADS);

		x = -x;
		z = -z;

		// Left Face (comprimento,altura,largura)
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, width / 2 + x);// frente
																		// baixo
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, width / 2 + x);// atras
																		// baixo
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);// atras
																		// cima
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, width / 2 + x);// frente
																		// cima

		// Right face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, -width / 2 + x);// frente
																		// baixo
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// frente
																		// cima
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, -width / 2 + x);// atras
																		// Cima
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// atras
																		// baixo

		// Top Face
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
																		// cima
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// cima
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// baixo
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
																		// baixo

		// Bottom Face
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// cima
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// baixo
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// baixo
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// cima

		// Front face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// baixo
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
																		// cima
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// Cima
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// baixo

		// back Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// baixo
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// baixo
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// Cima
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
																		// cima
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glEnd();
		gl.glPopMatrix();
		gl.glFlush();
	}

	private void createCylinder(GLAutoDrawable drawable, int texture, float width, float height, float lenght, float x,
			float y, float z, float rotateX, float rotateY, float rotateZ) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glPushMatrix();
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		gl.glTranslatef(-z, y, -x);
		gl.glScalef(lenght, height, lenght);
		gl.glBegin(GL2.GL_QUADS);
		for (double j = 0; j <= 360; j += 0.1) {
			gl.glTexCoord3f(1f, 0f, 0f);
			gl.glVertex3f((float) (Math.cos(j)), 2, (float) (Math.sin(j)));
			gl.glTexCoord3f(0.0f, 1.0f, 0f);
			gl.glVertex3f((float) (Math.cos(j)), 0, (float) (Math.sin(j)));
			//gl.glTexCoord3f(0.0f, 0.0f, 1.0f);
			//gl.glVertex3f((float) (Math.cos(j)), 0, (float) (Math.sin(j)));
		}
		gl.glEnd();

		gl.glPopMatrix();
		gl.glFlush();

	}
}
