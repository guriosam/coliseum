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


	 
	
}
