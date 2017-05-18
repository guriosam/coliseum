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

	GL2 gl;

	Light light;

	float doorAngle = 60;
	float distanceDoor = -53.5f;

	// Define camera variables
	float cameraAzimuth = 270.0f, cameraSpeed = 0.0f, cameraElevation = -25.0f;

	// Set camera at (0, 0, -20)
	float cameraCoordsPosx = -50.0f, cameraCoordsPosy = 30f, cameraCoordsPosz = 0.0f;

	// Set camera orientation
	float cameraUpx = 0.0f, cameraUpy = 1.0f, cameraUpz = 0.0f;
	boolean luz;

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

		gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		aimCamera(gl, glu);
		moveCamera();

		createColiseum(drawable);

		// Iluminação
		light = new Light();
		
		if (!luz) {
			light.init_lighting(gl);
		} else {
			light.remove_lighting(gl);
		}
		
		gl.glFlush();

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.0f, 0.5f, 1.0f, 0.0f);
		textures.carregar_texturas(drawable);
		gl.glEnable(GL2.GL_DEPTH_TEST);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60.0, width / (float) height, 0.1, 350.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);

	}

	private void createColiseum(GLAutoDrawable drawable) {
		// Floor
		float groundWidth = 200;
		float groundHeight = 200;
		createCube(drawable, 3, groundWidth, 0.2f, groundHeight, -0.5f, 0, 0, 0, 0, 0);

		int textureWall = textures.pedra;

		Floor1 floor1 = new Floor1(glu, textures);
		floor1.createRing(drawable, 0, 64, 5, textureWall, 1);
		Floor2 floor2 = new Floor2(glu, textures);
		floor2.createRing(drawable, 25, 64, 5, textureWall, 2);
		Floor3 floor3 = new Floor3(glu, textures);
		floor3.createRing(drawable, 35, 64, 5, textureWall, 3);
		Floor3 floor4 = new Floor3(glu, textures);
		floor4.createRing(drawable, 45, 64, 5, textureWall, 4);
		Floor5 floor5 = new Floor5(glu, textures);
		floor5.createRing(drawable, 55, 64, 5, textureWall, 5);

		// Grades
		// createRingLines(drawable, 53, 64, 4, 3, 5);

		// Ramp
		createCube(drawable, textures.pedra, 8, 0.2f, 3, -57f, 3, 24, -45, 45, 25);

		// Door
		createCube(drawable, textures.door, 0.2f, 5, 5f, distanceDoor, 3, 30f, 0, doorAngle, 0);

		createGladiadores(drawable);
		createLeao(drawable);
		
		int deslocarCadeira = 16;
		int rotationX = 0;
		int rotationY = 0;
		int rotationZ = 0;
		createCube(drawable, textures.fabricRed, 1f, 10f, 7f, deslocarCadeira + 5, 10f, 0.5f, rotationX, rotationY, rotationZ);
		createCube(drawable, textures.fabricRed, 5f, 3f, 5f, deslocarCadeira + 3, 5f, 0.5f, rotationX, rotationY, rotationZ);
		createCube(drawable, textures.textureTable, 5f, 5f, 1.5f, deslocarCadeira + 3, 5f, 4f, rotationX, rotationY, rotationZ);
		createCube(drawable, textures.textureTable, 5f, 5f, 1.5f, deslocarCadeira + 3, 5f, -2.5f, rotationX, rotationY, rotationZ);
		/// Lança
		int alturaLanca = 7;
		int deslocLanca = 3;
		// Cabo
		createCube(drawable, textures.madeira, 0.1f, 5, 0.1f, deslocLanca + 0.8f, alturaLanca, 0.5f, 45, 0, 0);
		// Ponta
		createCube(drawable, 200, 0.2f, 0.1f, 0.1f, deslocLanca + 2.6f, alturaLanca - 1.8f, 0.5f, 60, 45, 15);

		createArvores(drawable, textures.madeira, textures.folha);

	}

	private void createArvores(GLAutoDrawable drawable, int textureCaule, int textureFolhas) {
		// Arvore
		int alturaArvore = 10;
		// Caule
		createCube(drawable, textureCaule, 2, 20, 2, 65, alturaArvore, -65, 0, 0, 0);
		// Folhas
		createCube(drawable, textureFolhas, 10, 10, 10, 65, alturaArvore + 10, -65, 0, 0, 0);

		createCube(drawable, textureCaule, 2, 20, 2, 45, alturaArvore, -68, 0, 0, 0);
		// Folhas
		createCube(drawable, textureFolhas, 10, 10, 10, 45, alturaArvore + 10, -68, 0, 0, 0);

		createCube(drawable, textureCaule, 2, 20, 2, 25, alturaArvore, -73, 0, 0, 0);
		// Folhas
		createCube(drawable, textureFolhas, 10, 10, 10, 25, alturaArvore + 10, -73, 0, 0, 0);

		createCube(drawable, textureCaule, 2, 20, 2, -40, alturaArvore, -95, 0, 0, 0);
		// Folhas
		createCube(drawable, textureFolhas, 10, 10, 10, -40, alturaArvore + 10, -95, 0, 0, 0);

		createCube(drawable, textureCaule, 2, 20, 2, -60, alturaArvore, -65, 0, 0, 0);
		// Folhas
		createCube(drawable, textureFolhas, 10, 10, 10, -60, alturaArvore + 10, -65, 0, 0, 0);

		createCube(drawable, textureCaule, 2, 20, 2, -80, alturaArvore, -55, 0, 0, 0);
		// Folhas
		createCube(drawable, textureFolhas, 10, 10, 10, -80, alturaArvore + 10, -55, 0, 0, 0);

		createCube(drawable, textureCaule, 2, 20, 2, -60, alturaArvore, -85, 0, 0, 0);
		// Folhas
		createCube(drawable, textureFolhas, 10, 10, 10, -60, alturaArvore + 10, -85, 0, 0, 0);

	}

	private void createLeao(GLAutoDrawable drawable) {
		/// Leao
		int alturaLeao = 6;
		int deslocLeao = 0;
		// Corpo
		createCube(drawable, 5, 1, 1, 3, deslocLeao + 0.5f, alturaLeao, 0, 0, 0, 0);
		// Cabeça
		createCube(drawable, textures.laranja, 1.5f, 1.5f, 2f, deslocLeao + 0.5f, alturaLeao + 1, 1.5f, 0, 0, 25);
		// Crista
		createCube(drawable, 5, 1.8f, 1.8f, 0.5f, deslocLeao + 0.5f, alturaLeao + 1.2f, 1f, 0, 0, 25);
		// Pernas
		createCube(drawable, textures.laranja, 0.3f, 1, 0.3f, deslocLeao + 0.8f, alturaLeao - 1, 0.5f, -1, 0, 0);
		createCube(drawable, textures.laranja, 0.3f, 1, 0.3f, deslocLeao + 0.8f, alturaLeao - 1, -1.2f, -1, 0, 0);
		createCube(drawable, textures.laranja, 0.3f, 1, 0.3f, deslocLeao + 0.2f, alturaLeao - 1, 0.5f, -1, 0, 0);
		createCube(drawable, textures.laranja, 0.3f, 1, 0.3f, deslocLeao + 0.2f, alturaLeao - 1, -1.2f, -1, 0, 0);
		// Rabo
		createCube(drawable, textures.laranja, 0.2f, 0.2f, 1, deslocLeao + 0.5f, alturaLeao, -2f, 0, 0, 0);
	}

	private void createGladiadores(GLAutoDrawable drawable) {
		/// Gladiador 1
		int alturaGladiador1 = 8;
		int deslocGladiador1 = -8;
		int cota_de_malha = textures.malha;
		// Corpo
		createCube(drawable, cota_de_malha, 1, 1.8f, 1, deslocGladiador1 - 0.5f, alturaGladiador1 - 0.5f, 0, 0, 0, 0);
		// Cabeça
		createCube(drawable, 5, 1f, 1, 1.5f, deslocGladiador1 - 0.5f, alturaGladiador1 + 1, 0, 0, 0, 0);
		// Braços
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador1 + 0f, alturaGladiador1, 1, 0, 0, 0);
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador1 + 0f, alturaGladiador1, -1, 0, 0, 0);
		// Pernas
		createCube(drawable, cota_de_malha, 1f, 2, 0.4f, deslocGladiador1 - 0.5f, alturaGladiador1 - 2, 0.3f, 0, 0, 0);
		createCube(drawable, cota_de_malha, 1f, 2, 0.4f, deslocGladiador1 + -0.5f, alturaGladiador1 - 2, -0.3f, 0, 0,
				0);

		/// Gladiador 2
		int alturaGladiador2 = 8;
		int deslocGladiador2 = 8;
		// Corpo
		createCube(drawable, cota_de_malha, 1, 1.8f, 1, deslocGladiador2 + 0.5f, alturaGladiador2 - 0.5f, 0, 0, 0, 0);
		// Cabeça
		createCube(drawable, 5, 1f, 1, 1.5f, deslocGladiador2 + 0.5f, alturaGladiador2 + 1, 0, 0, 0, 0);
		// Braços
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador2 + 0f, alturaGladiador2, 1, 0, 0, 0);
		createCube(drawable, 5, 2, 0.5f, 1, deslocGladiador2 + 0f, alturaGladiador2, -1, 0, 0, 0);
		// Pernas
		createCube(drawable, cota_de_malha, 1f, 2, 0.4f, deslocGladiador2 + 0.5f, alturaGladiador2 - 2, 0.3f, 0, 0, 0);
		createCube(drawable, 5, 1f, 2, 0.4f, deslocGladiador2 + 0.5f, alturaGladiador2 - 2, -0.3f, 0, 0, 0);

	}

	private void createCube(GLAutoDrawable drawable, int texture, float width, float height, float lenght, float x,
			float y, float z, float rotateX, float rotateY, float rotateZ) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glPushMatrix();

		gl.glTranslatef(-z, y, -x);
		gl.glRotated(rotateX, 1, 0, 0);
		gl.glRotated(rotateY, 0, 1, 0);
		gl.glRotated(rotateZ, 0, 0, 1);
		gl.glTranslatef(z, -y, x);

		// ---------Textura------

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
		gl.glColor3f(1f, 1f, 1f);

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

	private void createRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		int size = 24;
		if (floor == 5) {
			size = 11;
		}
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		GLUquadric disk = glu.gluNewQuadric();
		int x = 0;
		int y = 0;

		// inner ring
		createInnerRing(drawable, start, end, height, texture, floor);
		// outer ring
		createOuterRing(drawable, start, end, height, texture, floor);

		// Floor

		int limit = 11;

		if (floor > 3) {
			gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.textureEdge);
		} else {
			gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.floor);
		}
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
				if (floor < 4) {
					if (i < limit) {
						glu.gluPartialDisk(disk, start + 1, end - 1, 5, 1, m, 20);
					} else {
						glu.gluPartialDisk(disk, start + 1, end - 5, 5, 1, m, 20);
					}
				}
				m += 20;
			}
			gl.glPopMatrix();
		}

		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.pedra);

		// Borda Externa
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
					glu.gluPartialDisk(disk, end - 1, end, 5, 1, m, 20);
				} else {
					glu.gluPartialDisk(disk, end - 4, end - 3, 5, 1, m, 20);
				}
				m += 20;
			}
			gl.glPopMatrix();
		}

		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.pedraEscura);
		// Borda interna
		if (floor < 4) {
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
						glu.gluPartialDisk(disk, start, start + 1, 5, 1, m, 20);
					} else {
						glu.gluPartialDisk(disk, start, start + 1, 5, 1, m, 20);
					}
					m += 20;
				}
				gl.glPopMatrix();
			}
		}

		gl.glPopMatrix();
		gl.glEnd();

		gl.glFlush();

	}

	private void createOuterRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		// Circulo Completo
		int quant = 24;

		if (floor == 5) {
			// 3/4 de Círculo
			quant = 11;
		}

		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		GLUquadric disk = glu.gluNewQuadric();

		int x = 0;
		int y = 0;

		for (int f = 0; f < floor; f++) {
			float k = 0;
			for (float j = (5 * f); j < height - 0.5; j += 0.02) {
				gl.glPushMatrix();
				gl.glTranslatef(1, j, 1);
				glu.gluQuadricTexture(disk, true);
				gl.glColor3f(1f, 1f, 1f);
				gl.glRotatef(90, -1, 0, 0);
				x = 5;
				y = 10;
				for (int i = 0; i < quant; i++) {
					if (f < 3) {
						if (i > 6 && i < 11) {
							// glu.gluPartialDisk(disk, end - 1, end, 5, 1, x,
							// y);
						} else {
							// glu.gluPartialDisk(disk, end - 15 + k, end - 3.5,
							// 5, 1, x, y);
						}
					} else {
						if (i < 11) {
							// glu.gluPartialDisk(disk, end - 1, end, 5, 1, x,
							// y);
						} else {
							// glu.gluPartialDisk(disk, end - 4.5, end - 3.5, 5,
							// 1, x, y);
						}
					}
					x += 20;
				}
				gl.glPopMatrix();
				if (k < 11) {
					k += 0.015;
				}
			}
		}
	}

	private void createInnerRing(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {
		GL2 gl = drawable.getGL().getGL2();

		// Circulo Completo
		int quant = 24;

		if (floor == 5) {
			// 3/4 de Círculo
			quant = 11;
		}
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

		GLUquadric disk = glu.gluNewQuadric();
		int x = 0;
		int y = 0;

		for (int f = 0; f < floor; f++) {
			float k = 0;
			for (float j = (5 * f); j < height; j += 0.02) {
				gl.glPushMatrix();
				gl.glColor3f(0.6f, 0.6f, 0.6f);
				gl.glTranslatef(1, j, 1);

				glu.gluQuadricTexture(disk, true);
				gl.glColor3f(1f, 1f, 1f);

				gl.glRotatef(90, -1, 0, 0);
				x = 5;
				y = 10;
				if (floor > 2) {
					for (int i = 0; i < quant; i++) {
						if (floor < 4) {
							if (f == floor - 1 && floor > 2) {
								if (start != 0) {
									glu.gluPartialDisk(disk, start - 8 + k, start + 0.5, 5, 1, x, y);
								}
							} else {
								glu.gluPartialDisk(disk, start, start + 0.5, 5, 1, x, y);
							}
						}
						x += 20;
					}
				} else {
					glu.gluPartialDisk(disk, start, start + 0.5, 30, 5, 0, 360);
				}

				gl.glPopMatrix();
				if (k < 11) {
					k += 0.03;
				}
			}

		}
	}

	private void createRingLines(GLAutoDrawable drawable, int start, int end, int height, int texture, int floor) {

		GL2 gl = drawable.getGL().getGL2();

		int size = 24;
		if (floor == 5) {
			size = 11;
		}

		GLUquadric disk = glu.gluNewQuadric();
		float x = 0;
		int y = 0;
		/*
		 * for (float j = 0; j < height; j += 0.02) { gl.glPushMatrix();
		 * gl.glColor3f(0.6f, 0.6f, 0.6f); gl.glTranslatef(1, j, 1);
		 * gl.glBindTexture(GL2.GL_TEXTURE_2D, texture); gl.glRotatef(90, -1, 0,
		 * 0); x = -5; y = 10; for (int i = 0; i < size; i++) {
		 * glu.gluPartialDisk(disk, start, start + 0.5, 5, 1, x, y); x += 20; }
		 * 
		 * gl.glPopMatrix(); }
		 */
		for (float j = 0; j < height; j += 0.02) {
			gl.glPushMatrix();
			gl.glColor3f(0.6f, 0.6f, 0.6f);
			gl.glTranslatef(1, j, 1);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
			gl.glRotatef(90, -1, 0, 0);
			x = 15.5f;
			y = 10;
			for (int i = 0; i < size; i++) {
				if (i < 11) {
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 1f, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 2f, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 3f, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 4f, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 5f, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 6f, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 7f, 0.5f);
					glu.gluPartialDisk(disk, end - 0.5, end, 5, 1, x + 8f, 0.5f);
				} else {
					// glu.gluPartialDisk(disk, end - 4.5, end - 4, 5, 1, x,
					// y);
				}

				if (i < size - 2) {
					x += 20;
				}

			}

			gl.glPopMatrix();
		}

		gl.glEnd();

		gl.glFlush();

	}

}
