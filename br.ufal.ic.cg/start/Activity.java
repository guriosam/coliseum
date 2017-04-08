package start;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import entities.Camera;
import entities.Keyboard;
import entities.Light;
import entities.Textures;

public class Activity {

	private Light light;
	private Keyboard keyboard;
	private Camera camera;
	private GLCapabilities caps;
	private GLCanvas canvas;
	private Textures textures;

	private GLUT glut;
	private GLU glu;

	private FPSAnimator animator;
	private GLCanvas glcanvas;

	public Activity() {
		glut = new GLUT();
		glu = new GLU();
		textures = new Textures();
		camera = new Camera(glu, glut, textures);
		keyboard = new Keyboard(camera);
		light = new Light();


		caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		canvas = new GLCanvas(caps);
		canvas.setSize(1024, 800);
		canvas.addGLEventListener(camera);
		canvas.addKeyListener(keyboard);

		// caps.setDoubleBuffered(true);// request double buffer display mode

		// canvas.addMouseListener(this);
		animator = new FPSAnimator(canvas, 60);

		// creating frame
		final JFrame frame = new JFrame("Coliseum");
		// frame.setUndecorated(true);
		// frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		// adding canvas to it
		frame.getContentPane().add(canvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// getContentPane().add(canvas);
	}

	public void run() {
		animator.start();
	}

	public static void main(String[] args) {
		new Activity().run();

	}

	// -----------------------------TELA------------------------------------

	private static final float PI = 3.1415f;

		private void createCube2(GLAutoDrawable drawable, int texture, float width, float height, float lenght, float x,
			float y, float z, float rotateX, float rotateY, float rotateZ) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glPushMatrix();

		gl.glTranslatef(-z, y, -x);
		gl.glRotated(rotateX, 1, 0, 0);
		gl.glRotated(rotateY, 0, 1, 0);
		gl.glRotated(rotateZ, 0, 0, 1);

		gl.glScalef(lenght, height, width);
		gl.glColor3f(0.6f, 0.6f, 0.6f);
		glut.glutSolidCube(1f);
		gl.glScalef(0, 0, 0);

		gl.glTranslatef(z, -y, x);

		gl.glPopMatrix();
		gl.glFlush();
	}

	// -----------------------------TECLADO------------------------------------

	/*
	 * private void createLab(GLAutoDrawable drawable) { // chao do laboratorio
	 * //createCube(drawable, texture, 8f, 0.2f, 3.5f, -5f, -0.0001f, 3.6f, 0,
	 * 0, 0); // teto do laboratorio //createCube(drawable, textureRoof, 8f,
	 * 0.2f, 3.5f, -5f, 2.11f, 3.6f, 0, 0, 0);
	 * 
	 * // Parede lab fundo // createCube(drawable, textureWindow2, 0.2f, 2f,
	 * 3.5f, -9f, 1, 3.6f, 0, // 0, 0); //createCube(drawable, textureWall,
	 * 0.2f, 1f, 3.5f, -9f, 0.5f, 3.6f, 0, 0, 0);// meia // parede
	 * //createCube(drawable, textureWall, 0.3f, 2f, 0.25f, -8.8f, 1f, 2f, 0, 0,
	 * 0);// Borda // Parede //createCube(drawable, textureJanelaLado, 0.051f,
	 * 0.08f, 3.5f, -9f, 1.65f, 3.6f, 0, 0, 0);// Janela // deitada
	 * //createCube(drawable, textureJanelaEmPe, 0.05f, 2f, 0.06f, -9f, 1.1f,
	 * 2.7f, 0, 0, 0);// Janela // em // pé //createCube(drawable,
	 * textureJanelaEmPe, 0.05f, 2f, 0.06f, -9f, 1.1f, 3.2f, 0, 0, 0);
	 * //createCube(drawable, textureJanelaEmPe, 0.05f, 2f, 0.06f, -9f, 1.1f,
	 * 3.7f, 0, 0, 0); //createCube(drawable, textureJanelaEmPe, 0.05f, 2f,
	 * 0.06f, -9f, 1.1f, 4.2f, 0, 0, 0); //createCube(drawable,
	 * textureJanelaEmPe, 0.05f, 2f, 0.06f, -9f, 1.1f, 4.7f, 0, 0, 0);
	 * 
	 * // Parede lab esq //createCube(drawable, textureWall, 7, 2, 0.2f, -5.5f,
	 * 1, 1.83f, 0, 0, 0);
	 * 
	 * // parede lab dir //createCube(drawable, textureWall, 7, 2, 0.2f, -5.5f,
	 * 1, 5.3f, 0, 0, 0);
	 * 
	 * // Quadro Branco Lab //createCube(drawable, textureWhiteBoard, 2.1f,
	 * 1.1f, 0.05f, -4.3f, 1.2f, 2f, 0, 0, 0); //createCube(drawable,
	 * textureEdge, 2.15f, 1.15f, 0.05f, -4.3f, 1.2f, 1.98f, 0, 0, 0);
	 * //createCube(drawable, textureEdge, 0.5f, 0.03f, 0.15f, -4.3f, 0.67f,
	 * 2.1f, 0, 0, 0);
	 * 
	 * //createTable(drawable); //createDoor(drawable); //createPcs(drawable);
	 * //createChairs(drawable);
	 * 
	 * }
	 * 
	 * private void createChairs(GLAutoDrawable drawable) {
	 * 
	 * GL2 gl = drawable.getGL().getGL2();
	 * 
	 * gl.glPushMatrix(); // ---------------------Cadeira
	 * 1---------------------------------
	 * 
	 * // Costas createCylinder(drawable, textureChair, 0.18f, 0.18f, 0.04f,
	 * -3.77f, 0.61f, 3.25f, 90, 0, 0); createCylinder(drawable, textureChair,
	 * 0.18f, 0.18f, 0.04f, -3.77f, 0.61f, 3.25f, 0, 0, 0); // sentar
	 * createCylinder(drawable, textureChair, 0.21f, 0.18f, 0.04f, -3.77f,
	 * 0.35f, 3.505f, 0, 0, 90); createCylinder(drawable, textureChair, 0.21f,
	 * 0.18f, 0.04f, -3.77f, 0.35f, 3.505f, 0, 90, 90); // Apoio
	 * createCylinder(drawable, textureChair2, 0.03f, 0.18f, 0.03f, -3.77f,
	 * 0.48f, 3.21f, 0, 0, 0); createCylinder(drawable, textureChair2, 0.03f,
	 * 0.18f, 0.03f, -3.77f, 0.326f, 3.365f, 0, 0, 90); // Pés Centro
	 * createCylinder(drawable, textureChair2, 0.03f, 0.08f, 0.03f, -3.77f,
	 * 0.23f, 3.52f, 0, 0, 0); // Pés createCylinder(drawable, textureChair2,
	 * 0.025f, 0.125f, 0.025f, -3.77f, 0.15f, 3.42f, 0, 0, 90);
	 * createCylinder(drawable, textureChair2, 0.025f, 0.125f, 0.025f, -3.65f,
	 * 0.15f, 3.49f, 0, 72, 90); createCylinder(drawable, textureChair2, 0.025f,
	 * 0.125f, 0.025f, -3.7f, 0.15f, 3.61f, 0, 144, 90);
	 * createCylinder(drawable, textureChair2, 0.025f, 0.125f, 0.025f, -3.845f,
	 * 0.15f, 3.61f, 0, 216, 90); createCylinder(drawable, textureChair2,
	 * 0.025f, 0.125f, 0.025f, -3.885f, 0.15f, 3.485f, 0, 288, 90);
	 * 
	 * // Rodinhas
	 * 
	 * gl.glColor3f(0, 0, 0);
	 * 
	 * gl.glTranslated(-3.29f, 0.142f, 3.77f); glut.glutSolidSphere(0.032, 50,
	 * 50);
	 * 
	 * gl.glTranslated(-0.42, 0f, 0.145f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(0.265f, 0f, 0.09f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(-0.012, 0f, -0.47f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(-0.25, 0f, 0.1f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(3.707f, -0.142f, -3.635f);
	 * 
	 * gl.glColor3f(1, 1, 1); gl.glPopMatrix();
	 * 
	 * gl.glPushMatrix(); // ---------------------Cadeira
	 * 2---------------------------------
	 * 
	 * // Costas createCylinder(drawable, textureChair, 0.18f, 0.18f, 0.04f,
	 * -5.27f, 0.61f, 4.25f, 90, 0, 0); createCylinder(drawable, textureChair,
	 * 0.18f, 0.18f, 0.04f, -5.27f, 0.61f, 4.25f, 0, 0, 0); // sentar
	 * createCylinder(drawable, textureChair, 0.21f, 0.18f, 0.04f, -5.27f,
	 * 0.35f, 4.505f, 0, 0, 90); createCylinder(drawable, textureChair, 0.21f,
	 * 0.18f, 0.04f, -5.27f, 0.35f, 4.505f, 0, 90, 90); // Apoio
	 * createCylinder(drawable, textureChair2, 0.03f, 0.18f, 0.03f, -5.27f,
	 * 0.48f, 4.21f, 0, 0, 0); createCylinder(drawable, textureChair2, 0.03f,
	 * 0.18f, 0.03f, -5.27f, 0.326f, 4.365f, 0, 0, 90); // Pés Centro
	 * createCylinder(drawable, textureChair2, 0.03f, 0.08f, 0.03f, -5.27f,
	 * 0.23f, 4.52f, 0, 0, 0); // Pés createCylinder(drawable, textureChair2,
	 * 0.025f, 0.125f, 0.025f, -5.27f, 0.15f, 4.42f, 0, 0, 90);
	 * createCylinder(drawable, textureChair2, 0.025f, 0.125f, 0.025f, -5.15f,
	 * 0.15f, 4.49f, 0, 72, 90); createCylinder(drawable, textureChair2, 0.025f,
	 * 0.125f, 0.025f, -5.2f, 0.15f, 4.61f, 0, 144, 90);
	 * createCylinder(drawable, textureChair2, 0.025f, 0.125f, 0.025f, -5.345f,
	 * 0.15f, 4.61f, 0, 216, 90); createCylinder(drawable, textureChair2,
	 * 0.025f, 0.125f, 0.025f, -5.385f, 0.15f, 4.485f, 0, 288, 90);
	 * 
	 * // Rodinhas gl.glColor3f(0, 0, 0);
	 * 
	 * gl.glTranslated(-4.29f, 0.142f, 5.27); glut.glutSolidSphere(0.032, 50,
	 * 50);
	 * 
	 * gl.glTranslated(-0.42, 0f, 0.145f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(0.265f, 0f, 0.09f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(-0.012, 0f, -0.47f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(-0.25, 0f, 0.1f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(4.707f, -0.142f, -5.135f);
	 * 
	 * gl.glColor3f(1, 1, 1); gl.glPopMatrix();
	 * 
	 * gl.glPushMatrix(); // ---------------------Cadeira
	 * 3---------------------------------
	 * 
	 * // Costas createCylinder(drawable, textureChair, 0.18f, 0.18f, 0.04f,
	 * -6.27f, 0.61f, 4.25f, 90, 0, 0); createCylinder(drawable, textureChair,
	 * 0.18f, 0.18f, 0.04f, -6.27f, 0.61f, 4.25f, 0, 0, 0); // sentar
	 * createCylinder(drawable, textureChair, 0.21f, 0.18f, 0.04f, -6.27f,
	 * 0.35f, 4.505f, 0, 0, 90); createCylinder(drawable, textureChair, 0.21f,
	 * 0.18f, 0.04f, -6.27f, 0.35f, 4.505f, 0, 90, 90); // Apoio
	 * createCylinder(drawable, textureChair2, 0.03f, 0.18f, 0.03f, -6.27f,
	 * 0.48f, 4.21f, 0, 0, 0); createCylinder(drawable, textureChair2, 0.03f,
	 * 0.18f, 0.03f, -6.27f, 0.326f, 4.365f, 0, 0, 90); // Pés Centro
	 * createCylinder(drawable, textureChair2, 0.03f, 0.08f, 0.03f, -6.27f,
	 * 0.23f, 4.52f, 0, 0, 0); // Pés createCylinder(drawable, textureChair2,
	 * 0.025f, 0.125f, 0.025f, -6.27f, 0.15f, 4.42f, 0, 0, 90);
	 * createCylinder(drawable, textureChair2, 0.025f, 0.125f, 0.025f, -6.15f,
	 * 0.15f, 4.49f, 0, 72, 90); createCylinder(drawable, textureChair2, 0.025f,
	 * 0.125f, 0.025f, -6.2f, 0.15f, 4.61f, 0, 144, 90);
	 * createCylinder(drawable, textureChair2, 0.025f, 0.125f, 0.025f, -6.345f,
	 * 0.15f, 4.61f, 0, 216, 90); createCylinder(drawable, textureChair2,
	 * 0.025f, 0.125f, 0.025f, -6.385f, 0.15f, 4.485f, 0, 288, 90);
	 * 
	 * // Rodinhas gl.glColor3f(0, 0, 0);
	 * 
	 * gl.glTranslated(-4.29f, 0.142f, 6.27); glut.glutSolidSphere(0.032, 50,
	 * 50);
	 * 
	 * gl.glTranslated(-0.42, 0f, 0.145f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(0.265f, 0f, 0.09f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(-0.012, 0f, -0.47f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(-0.25, 0f, 0.1f); glut.glutSolidSphere(0.032, 50, 50);
	 * 
	 * gl.glTranslated(4.707f, -0.142f, -6.135f);
	 * 
	 * gl.glColor3f(1, 1, 1); gl.glPopMatrix(); }
	 * 
	 * private void createCylinder(GLAutoDrawable drawable, int texture, float
	 * width, float height, float lenght, float x, float y, float z, float
	 * rotateX, float rotateY, float rotateZ) {
	 * 
	 * GL2 gl = drawable.getGL().getGL2();
	 * 
	 * gl.glPushMatrix(); gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
	 * 
	 * gl.glTranslatef(-z, y, -x); gl.glRotated(rotateX, 1, 0, 0);
	 * gl.glRotated(rotateY, 0, 1, 0); gl.glRotated(rotateZ, 0, 0, 1);
	 * gl.glScalef(lenght, height, width); gl.glBegin(GL2.GL_QUAD_STRIP); for
	 * (int j = 0; j <= 360; j += DEF_D) { gl.glTexCoord2f(1f, 0f);
	 * gl.glVertex3f((float) (Math.cos(j)), +1, (float) (Math.sin(j)));
	 * gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f((float) (Math.cos(j)), -1,
	 * (float) (Math.sin(j))); } gl.glEnd();
	 * 
	 * gl.glPopMatrix(); gl.glFlush();
	 * 
	 * }
	 * 
	 * private void createTable(GLAutoDrawable drawable) {
	 * 
	 * // mesa lab maior createCube2(drawable, textureTable, 5.7f, 0.06f, 1,
	 * -6.1f, 0.52f, 4.8f, 0, 0, 0); // mesa apoio createCube2(drawable,
	 * textureTable, 0.1f, 0.45f, 1, -7f, 0.27f, 4.8f, 0, 0, 0);
	 * createCube2(drawable, textureTable, 0.1f, 0.45f, 1, -5.8f, 0.27f, 4.8f,
	 * 0, 0, 0); createCube2(drawable, textureTable, 0.1f, 0.45f, 1, -4.6f,
	 * 0.27f, 4.8f, 0, 0, 0); createCube2(drawable, textureTable, 0.1f, 0.45f,
	 * 1, -3.3f, 0.27f, 4.8f, 0, 0, 0); // mesa lab fundo createCube2(drawable,
	 * textureTable, 1f, 0.06f, 2.6f, -8.5f, 0.519f, 3.2f, 0, 0, 0); // mesa
	 * apoio createCube2(drawable, textureTable, 1f, 0.45f, 0.1f, -8.5f, 0.27f,
	 * 3.3f, 0, 0, 0);
	 * 
	 * }
	 * 
	 * private void createPcs(GLAutoDrawable drawable) {
	 * 
	 * // PC3 createCube(drawable, texturePC, 0.6f, 0.15f, 0.6f, -6.1f, 0.63f,
	 * 4.8f, 0, 0, 0); createCube(drawable, texturePCGabinete, 0.59f, 0.149f,
	 * 0.049f, -6.1f, 0.63f, 4.5f, 0, 0, 0); createCube(drawable, texturePC,
	 * 0.15f, 0.5f, 0.15f, -6.1f, 0.75f, 4.8f, 0, 0, 0); createCube(drawable,
	 * texturePCMonitor, 0.55f, 0.34f, 0.05f, -6.1f, 1f, 4.7f, 0, 0, 0);
	 * 
	 * // PC 2 createCube(drawable, texturePC, 0.55f, 0.15f, 0.51f, -4.8f,
	 * 0.63f, 4.8f, 0, 0, 0); createCube(drawable, texturePCGabinete, 0.549f,
	 * 0.149f, 0.049f, -4.8f, 0.63f, 4.55f, 0, 0, 0); createCube(drawable,
	 * texturePC, 0.15f, 0.5f, 0.15f, -4.8f, 0.75f, 4.9f, 0, 0, 0);
	 * createCube(drawable, texturePCMonitor, 0.51f, 0.38f, 0.05f, -4.8f, 1f,
	 * 4.8f, 0, 0, 0);
	 * 
	 * // PC 1 createCube(drawable, texturePC, 0.55f, 0.15f, 0.51f, -3.6f,
	 * 0.63f, 4.8f, 0, 0, 0); createCube(drawable, texturePCGabinete, 0.549f,
	 * 0.149f, 0.049f, -3.6f, 0.63f, 4.55f, 0, 0, 0); createCube(drawable,
	 * texturePC, 0.15f, 0.5f, 0.15f, -3.6f, 0.75f, 4.9f, 0, 0, 0);
	 * createCube(drawable, texturePCMonitor, 0.51f, 0.38f, 0.05f, -3.6f, 1f,
	 * 4.8f, 0, 0, 0); createCube(drawable, texturePCMonitor2, 0.509f, 0.379f,
	 * 0.049f, -3.6f, 1f, 4.79f, 0, 0, 0);
	 * 
	 * // monitores createCube(drawable, texturePCMonitor, 0.51f, 0.38f, 0.05f,
	 * -8.5f, 0.8f, 4.8f, 0, 0, 0); createCube(drawable, texturePC, 0.12f, 0.3f,
	 * 0.12f, -8.5f, 0.65f, 4.87f, 0, 0, 0); createCube(drawable, texturePC,
	 * 0.5f, 0.02f, 0.35f, -8.5f, 0.55f, 4.8f, 0, 0, 0);
	 * 
	 * // monitores createCube(drawable, texturePC, 0.05f, 0.38f, 0.51f, -8.5f,
	 * 0.8f, 3.6f, 0, 0, 0); createCube(drawable, texturePC, 0.12f, 0.3f, 0.12f,
	 * -8.5f, 0.65f, 3.6f, 0, 0, 0); createCube(drawable, texturePC, 0.35f,
	 * 0.02f, 0.5f, -8.5f, 0.55f, 3.6f, 0, 0, 0);
	 * 
	 * // Pc diferente createCube(drawable, texturePCMonitor, 0.05f, 0.38f,
	 * 0.51f, -8.4f, 0.8f, 2.6f, 0, 0, 0); createCube(drawable, texturePC,
	 * 0.12f, 0.3f, 0.12f, -8.5f, 0.65f, 2.6f, 0, 0, 0); createCube(drawable,
	 * texturePC, 0.35f, 0.02f, 0.5f, -8.5f, 0.55f, 2.6f, 0, 0, 0);
	 * createCube(drawable, textureKeyboard, 0.19f, 0.02f, 0.5f, -8.15f, 0.55f,
	 * 2.6f, 0, 0, 0); createCube(drawable, texturePC, 0.37f, 0.6f, 0.2f, -8.4f,
	 * 0.8f, 2.2f, 0, 0, 0); createCube(drawable, texturePCGabinete2, 0.369f,
	 * 0.59f, 0.19f, -8.39f, 0.8f, 2.2f, 0, 0, 0);
	 * 
	 * // Ar condicionado createAr(drawable, 1.05f, 0.25f, 0.15f, -6.4f, 1.7f,
	 * 2f);
	 * 
	 * }
	 * 
	 * private void createDoor(GLAutoDrawable drawable) { GL2 gl =
	 * drawable.getGL().getGL2();
	 * 
	 * gl.glPushMatrix(); gl.glTranslatef(-5.2f, 0.5f, 2f);
	 * gl.glRotatef(shoulder, 0.0f, -1.0f, 0.0f); gl.glTranslatef(5.2f, -0.5f,
	 * -2f); // porta createCube(drawable, textureDoor, 0.10f, 1.43f, 0.75f,
	 * -2f, 0.72f, 4.82f, 0, 0, 0); gl.glPopMatrix(); // porta vidro
	 * gl.glEnable(GL2.GL_BLEND); gl.glColor4f(1, 1, 1, 0.2f);
	 * createCube2(drawable, textureTable, 0.05f, 0.56f, 0.8f, -2f, 1.75f, 4.8f,
	 * 0, 0, 0); gl.glDisable(GL2.GL_BLEND); // porta vidro cima
	 * createCube(drawable, textureTable, 0.10f, 0.07f, 0.75f, -2f, 1.95f,
	 * 4.82f, 0, 0, 0); // porta vidro baixo createCube(drawable, textureTable,
	 * 0.10f, 0.07f, 0.75f, -2f, 1.47f, 4.82f, 0, 0, 0); // porta vidro lados
	 * createCube(drawable, textureTable, 0.10f, 0.5f, 0.07f, -2f, 1.68f,
	 * 4.486f, 0, 0, 0); createCube(drawable, textureTable, 0.10f, 0.5f, 0.07f,
	 * -2f, 1.68f, 5.17f, 0, 0, 0);
	 * 
	 * }
	 * 
	 * private void createAr(GLAutoDrawable drawable, float width, float height,
	 * float lenght, float x, float y, float z) {
	 * 
	 * GL2 gl = drawable.getGL().getGL2();
	 * 
	 * x = -x; z = -z;
	 * 
	 * // Left Face gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArEsq2);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(0.0f, 0.0f);
	 * gl.glVertex3f(-lenght / 2 + z, -height / 2 + y + 0.07f, width / 2 + x);//
	 * frente // baixo gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(lenght / 2 +
	 * z, -height / 2 + y + 0.07f, width / 2 + x);// atras // baixo
	 * gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(lenght / 2 + z, height / 2 +
	 * y, width / 2 + x);// atras // cima gl.glTexCoord2f(0.0f, 1.0f);
	 * gl.glVertex3f(-lenght / 2 + z, height / 2 + y, width / 2 + x);// frente
	 * gl.glEnd(); // cima
	 * 
	 * // Left down Face gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArEsq);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(0.0f, 0.0f);
	 * gl.glVertex3f(-lenght / 2 + z + 0.05f, -height / 2 + y, width / 2 + x);//
	 * frente // baixo gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(lenght / 2 +
	 * z, -height / 2 + y, width / 2 + x);// atras // baixo
	 * gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(lenght / 2 + z, -height / 2 +
	 * y + 0.07f, width / 2 + x);// atras // cima gl.glTexCoord2f(0.0f, 1.0f);
	 * gl.glVertex3f(-lenght / 2 + z, -height / 2 + y + 0.07f, width / 2 + x);//
	 * frente gl.glEnd(); // cima
	 * 
	 * // Right face gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArDir2);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(1.0f, 0.0f);
	 * gl.glVertex3f(-lenght / 2 + z, -height / 2 + y + 0.07f, -width / 2 +
	 * x);// frente // baixo gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-lenght
	 * / 2 + z, height / 2 + y, -width / 2 + x);// frente // cima
	 * gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(lenght / 2 + z, height / 2 +
	 * y, -width / 2 + x);// atras // Cima gl.glTexCoord2f(0.0f, 0.0f);
	 * gl.glVertex3f(lenght / 2 + z, -height / 2 + y + 0.07f, -width / 2 + x);//
	 * atras gl.glEnd(); // baixo
	 * 
	 * // Right face down gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArDir);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(1.0f, 0.0f);
	 * gl.glVertex3f(-lenght / 2 + z + 0.05f, -height / 2 + y, -width / 2 +
	 * x);// frente // baixo gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-lenght
	 * / 2 + z, -height / 2 + y + 0.07f, -width / 2 + x);// frente // cima
	 * gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(lenght / 2 + z, -height / 2 +
	 * y + 0.07f, -width / 2 + x);// atras // Cima gl.glTexCoord2f(0.0f, 0.0f);
	 * gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// atras
	 * gl.glEnd(); // baixo
	 * 
	 * // Top Face gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArDir2);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(0.0f, 1.0f);
	 * gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
	 * // cima gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-lenght / 2 + z,
	 * height / 2 + y, width / 2 + x);// esquerda // cima gl.glTexCoord2f(1.0f,
	 * 0.0f); gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);//
	 * esquerda // baixo gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(lenght / 2 +
	 * z, height / 2 + y, -width / 2 + x);// direita gl.glEnd(); // baixo
	 * 
	 * // Bottom Face gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArDir2);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(1.0f, 1.0f);
	 * gl.glVertex3f(-lenght / 2 + z + 0.05f, -height / 2 + y, -width / 2 +
	 * x);// direita // cima gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(lenght /
	 * 2 + z, -height / 2 + y, -width / 2 + x);// direita // baixo
	 * gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(lenght / 2 + z, -height / 2 +
	 * y, width / 2 + x);// esquerda // baixo gl.glTexCoord2f(1.0f, 0.0f);
	 * gl.glVertex3f(-lenght / 2 + z + 0.05f, -height / 2 + y, width / 2 + x);//
	 * esquerda // cima gl.glEnd();
	 * 
	 * // Front face gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(1.0f, 0.0f);
	 * gl.glVertex3f(lenght / 2 + z, -height / 2 + y, -width / 2 + x);// direita
	 * // baixo gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(lenght / 2 + z,
	 * height / 2 + y, -width / 2 + x);// direita // cima gl.glTexCoord2f(0.0f,
	 * 1.0f); gl.glVertex3f(lenght / 2 + z, height / 2 + y, width / 2 + x);//
	 * esquerda // Cima gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(lenght / 2 +
	 * z, -height / 2 + y, width / 2 + x);// esquerda gl.glEnd(); // baixo
	 * 
	 * // back Face gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArFrente);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(0.0f, 0.0f);
	 * gl.glVertex3f(-lenght / 2 + z, -height / 2 + y + 0.07f, -width / 2 +
	 * x);// direita // baixo gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-lenght
	 * / 2 + z, -height / 2 + y + 0.07f, width / 2 + x);// esquerda // baixo
	 * gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-lenght / 2 + z, height / 2 +
	 * y, width / 2 + x);// esquerda // Cima gl.glTexCoord2f(0.0f, 1.0f);
	 * gl.glVertex3f(-lenght / 2 + z, height / 2 + y, -width / 2 + x);// direita
	 * gl.glEnd(); // cima
	 * 
	 * // back low face gl.glBindTexture(GL2.GL_TEXTURE_2D, textureArFrente2);
	 * gl.glBegin(GL2.GL_QUADS); gl.glTexCoord2f(0.0f, 0.0f);
	 * gl.glVertex3f(-lenght / 2 + z + 0.05f, -height / 2 + y, -width / 2 +
	 * x);// direita // baixo gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-lenght
	 * / 2 + z + 0.05f, -height / 2 + y, width / 2 + x);// esquerda // baixo
	 * gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-lenght / 2 + z, -height / 2 +
	 * y + 0.07f, width / 2 + x);// esquerda // Cima gl.glTexCoord2f(0.0f,
	 * 1.0f); gl.glVertex3f(-lenght / 2 + z, -height / 2 + y + 0.07f, -width / 2
	 * + x);// direita gl.glEnd(); // cima
	 * 
	 * gl.glFlush();
	 * 
	 * }
	 */
	
}
