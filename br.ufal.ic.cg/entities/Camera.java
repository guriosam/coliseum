package entities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Camera implements GLEventListener {

	private Keyboard keyboard;
	private GLU glu;
	private GLUT glut;
	private Textures textures;

	float doorAngle = 100;

	// Define camera variables
	float cameraAzimuth = 90.0f, cameraSpeed = 0.0f, cameraElevation = 0.0f;

	// Set camera at (0, 0, -20)
	float cameraCoordsPosx = 5.0f, cameraCoordsPosy = 10f, cameraCoordsPosz = 0.0f;

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
		// gl.glColor3f(1f, 1f, 1 f);
		// glut.glutSolidSphere(0.03f, 20, 20);
		// gl.glPopMatrix();

		createColiseum(drawable);

		// Iluminação
		// Light light = new Light();
		// light.init_lighting(gl);

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
		glu.gluPerspective(60.0, width / (float) height, 0.1, 180.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

	}

	private void createColiseum(GLAutoDrawable drawable) {
		// Floor
		float groundWidth = 150;
		float groundHeight = 150;
		createCube(drawable, 3, groundWidth, 0.2f, groundHeight, -0.5f, 0, 0, 0, 0, 0);

		createRing(drawable, 53, 64, 25, 2, 5);
		createRing(drawable, 42, 64, 20, 4, 4);
		createRing(drawable, 31, 64, 15, 15, 3);
		createRing(drawable, 20, 64, 10, 5, 2);
		createRing(drawable, 0, 64, 5, 3, 1);

		// Ramp
		// createCube(drawable, 10, 8, 0.2f, 3, -32f, 3, 13, -45, 45, 25);

		// Door
		// createCube(drawable, 10, 0.2f, 5, 8, -28f, 3, 21, 0, doorAngle, 0);

		/// Gladiador 1
		int alturaGladiador1 = 8;
		int deslocGladiador1 = -8;
		// Corpo
		createCube(drawable, 4, 1, 1.8f, 1, deslocGladiador1 - 0.5f, alturaGladiador1 - 0.5f, 0, 0, 0, 0);
		// Cabeça
		createCube(drawable, 5, 1f, 1, 1.5f, deslocGladiador1 - 0.5f, alturaGladiador1 + 1, 0, 0, 0, 0);
		// Braços
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador1 + 0f, alturaGladiador1, 1, 0, 0, 0);
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador1 + 0f, alturaGladiador1, -1, 0, 0, 0);
		// Pernas
		createCube(drawable, 5, 1f, 2, 0.4f, deslocGladiador1 - 0.5f, alturaGladiador1 - 2, 0.3f, 0, 0, 0);
		createCube(drawable, 5, 1f, 2, 0.4f, deslocGladiador1 + -0.5f, alturaGladiador1 - 2, -0.3f, 0, 0, 0);

		/// Gladiador 2
		int alturaGladiador2 = 8;
		int deslocGladiador2 = 8;
		// Corpo
		createCube(drawable, 4, 1, 1.8f, 1, deslocGladiador2 + 0.5f, alturaGladiador2 - 0.5f, 0, 0, 0, 0);
		// Cabeça
		createCube(drawable, 5, 1f, 1, 1.5f, deslocGladiador2 + 0.5f, alturaGladiador2 + 1, 0, 0, 0, 0);
		// Braços
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador2 + 0f, alturaGladiador2, 1, 0, 0, 0);
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador2 + 0f, alturaGladiador2, -1, 0, 0, 0);
		// Pernas
		createCube(drawable, 5, 1f, 2, 0.4f, deslocGladiador2 + 0.5f, alturaGladiador2 - 2, 0.3f, 0, 0, 0);
		createCube(drawable, 5, 1f, 2, 0.4f, deslocGladiador2 + 0.5f, alturaGladiador2 - 2, -0.3f, 0, 0, 0);

		/// Leao
		int alturaLeao = 6;
		int deslocLeao = 0;
		// Corpo
		createCube(drawable, 4, 1, 1, 3, deslocLeao + 0.5f, alturaLeao, 0, 0, 0, 0);
		// Cabeça
		createCube(drawable, 5, 1.5f, 1.5f, 2f, deslocLeao + 0.5f, alturaLeao + 1, 1.5f, 0, 0, 25);
		// Crista
		createCube(drawable, 4, 1.8f, 1.8f, 0.5f, deslocLeao + 0.5f, alturaLeao + 1.2f, 1f, 0, 0, 25);
		// Pernas
		createCube(drawable, 5, 0.3f, 1, 0.3f, deslocLeao + 0.8f, alturaLeao - 1, 0.5f, -1, 0, 0);
		createCube(drawable, 5, 0.3f, 1, 0.3f, deslocLeao + 0.8f, alturaLeao - 1, -1.2f, -1, 0, 0);
		createCube(drawable, 5, 0.3f, 1, 0.3f, deslocLeao + 0.2f, alturaLeao - 1, 0.5f, -1, 0, 0);
		createCube(drawable, 5, 0.3f, 1, 0.3f, deslocLeao + 0.2f, alturaLeao - 1, -1.2f, -1, 0, 0);
		// Rabo
		createCube(drawable, 5, 0.2f, 0.2f, 1, deslocLeao + 0.5f, alturaLeao, -2f, 0, 0, 0);
		
		///Lança
		
		
		///Espada
		
		
		///Escudo
		
		
		//Pedra ?
		
		
		

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
			// gl.glTexCoord3f(0.0f, 0.0f, 1.0f);
			// gl.glVertex3f((float) (Math.cos(j)), 0, (float) (Math.sin(j)));
		}
		gl.glEnd();

		gl.glPopMatrix();
		gl.glFlush();

	}

	private void drawCube(GLAutoDrawable drawable, float x, float y, float z, float width, float height, float lenght) {

		GL2 gl = drawable.getGL().getGL2();

		// gl.glPushMatrix();

		gl.glColor3f(0.6f, 0.6f, 0.6f);

		gl.glBegin(GL2.GL_QUADS);

		x = -x;
		z = -z;

		// Left Face (comprimentoio,altura,largura)
		// gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, width / 2 + x);// frente
																		// baixo
		// gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, width / 2 + x);// atras
																		// baixo
		// gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);// atras
																		// cima
		// gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, width / 2 + x);// frente
																		// cima

		// Right face
		// gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, -width / 2 + x);// frente
																		// baixo
		// gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// frente
																		// cima
		// gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, -width / 2 + x);// atras
																		// Cima
		// gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// atras
																		// baixo

		// Top Face
		// gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
																		// cima
		// gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// cima
		// gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// baixo
		// gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
																		// baixo

		// Bottom Face
		// gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// cima
		// gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// baixo
		// gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// baixo
		// gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// cima

		// Front face
		// gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// baixo
		// gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
																		// cima
		// gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// Cima
		// gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// baixo

		// back Face
		// gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
																		// baixo
		// gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-lenght / 2 + z, -height / 2 + y, width / 2 + x);// esquerda
																		// baixo
		// gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, width / 2 + x);// esquerda
																		// Cima
		// gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
		// cima
		gl.glEnd();
	}

	private void createRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		int size = 24;
		if (floor == 5) {
			size = 11;
		}

		GLUquadric disk = glu.gluNewQuadric();
		int x = 0;
		int y = 0;
		for (float j = 0; j < height; j += 0.02) {
			gl.glPushMatrix();
			gl.glColor3f(0.6f, 0.6f, 0.6f);
			gl.glTranslatef(1, j, 1);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
			gl.glRotatef(90, -1, 0, 0);
			x = 5;
			y = 10;
			for (int i = 0; i < size; i++) {
				glu.gluPartialDisk(disk, start, start + 0.5, 5, 1, x, y);
				x += 20;
			}

			gl.glPopMatrix();
		}

		for (float j = 0; j < height; j += 0.02) {
			gl.glPushMatrix();
			gl.glColor3f(0.6f, 0.6f, 0.6f);
			gl.glTranslatef(1, j, 1);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
			gl.glRotatef(90, -1, 0, 0);
			x = 5;
			y = 10;
			for (int i = 0; i < size; i++) {
				if (i < 11) {
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x, y);
				} else {
					glu.gluPartialDisk(disk, end - 4.5, end - 4, 5, 1, x, y);
				}

				x += 20;
			}

			gl.glPopMatrix();
		}

		int limit = 11;
		if (floor == 1) {
			limit = 10;
		}

		for (float j = height - 1; j < height; j += 0.02) {
			gl.glPushMatrix();
			gl.glColor3f(0.6f, 0.6f, 0.6f);
			gl.glTranslatef(1, j, 1);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
			x = 5;
			y = 15;
			gl.glRotatef(90, -1, 0, 0);
			int m = 0;
			for (int i = 0; i < size; i++) {
				if (i < limit) {
					glu.gluPartialDisk(disk, start, end, 5, 1, m, 20);
				} else {
					glu.gluPartialDisk(disk, start, end - 4, 5, 1, m, 20);
				}
				m += 20;
			}
			gl.glPopMatrix();
		}

		gl.glPopMatrix();

		gl.glFlush();
	}
}
