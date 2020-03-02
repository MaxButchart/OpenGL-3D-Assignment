package viewer;


import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import utils.Camera;



public class StartCode implements GLEventListener, KeyListener {

	private static int WIN_HEIGHT = 800;
	private static int WIN_WIDTH = 1200;
	private Camera camera;
	private GLUT glut;
	
	private int keyCode;
	
	//lighting
	float globalAmbient[] = { 0.4f, 0.4f, 0.4f, 1 }; 	// global light properties
	public float[] lightPosition = { 0.0f, 0.0f, 0.0f, 1.0f };
	public float[] ambientLight = { 0.5f, 0.5f, 0.5f, 1 };
	public float[] diffuseLight = { 0.5f, 0.5f, 0.5f, 0.8f };
	
	Sun sun = new Sun();
	Planet mercury = new Planet(2.450, 116, 4.1477);
	Planet venus = new Planet(6, 216, 1.6244);
	Planet earth = new Planet(6.5, 300, 1);
	Planet moon = new Planet(1.7, 10.4, 13.37);
	Planet mars = new Planet(3.400, 456, 0.5313);
	Planet phobos = new Planet(0.111, 3.7, 1140.625);
	Planet deimos = new Planet(0.122, 5.5, 276.923);
	
	//Reference ref = new Reference();
	
	//animation
	private float theta = 0;
	
	
	
	

	@Override
	public void display(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		
		// clear the depth and color buffers
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		
		
		camera.draw(gl);
		
		lights(gl);
		theta++;
		if(theta>=360)
			theta = theta%360;
			
		
		//ref.draw(gl);
		sun.draw(glut, gl);
	
		gl.glPushMatrix();
		
		
		mercury.translate(gl);
		mercury.draw(glut, gl);
		mercury.animate();
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		
		venus.translate(gl);
		venus.draw(glut, gl);
		venus.animate();
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		
		earth.translate(gl);
		earth.draw(glut, gl);
		earth.animate();
		moon.translate(gl);
		moon.draw(glut, gl);
		moon.animate();
		
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		
		mars.translate(gl);
		mars.draw(glut, gl);
		mars.animate();
		phobos.translate(gl);
		phobos.draw(glut, gl);
		phobos.animate();
		deimos.translate(gl);
		deimos.draw(glut, gl);
		deimos.animate();
		
		gl.glPopMatrix();
		
		mercury.drawOrbitLines(gl);
		venus.drawOrbitLines(gl);
		earth.drawOrbitLines(gl);
		mars.drawOrbitLines(gl);
		
		sun.drawCarona(glut, gl);

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		gl.setSwapInterval(1);

		// enable depth test and set shading mode
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_BLEND);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
	
		
		camera = new Camera();
		camera.draw(gl);
		
		mercury.setColour(0.9, 0.9, 0.9);
		venus.setColour(0.8, 0.6, 0.6);
		earth.setColour(0.1, 0.2, 0.8);
		mars.setColour(0.9, 0.1, 0.1);
		phobos.setColour(0.3, 0.3, 0.3);
		deimos.setColour(0.9, 0.9, 0.9);
		
		
		glut = new GLUT();		

	}
	
	public void lights(GL2 gl){
		// set the global ambient light level
	    gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, globalAmbient, 0); 
	    //set light 0 properties
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		//normalise the normal surface vectors
		gl.glEnable(GL2.GL_NORMALIZE);
		//position light 0 at the origin
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
		//enable light 0
		gl.glEnable(GL2.GL_LIGHT0);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		camera.newWindowSize(width, height);
	}

	public static void main(String[] args) {
		Frame frame = new Frame("Inner Solar System Viewer");
		GLCanvas canvas = new GLCanvas();
		StartCode app = new StartCode();
		canvas.addGLEventListener(app);
		frame.add(canvas);
		frame.setSize(WIN_WIDTH, WIN_HEIGHT);
		final FPSAnimator animator = new FPSAnimator(canvas, 60);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Run this on another thread than the AWT event queue to
				// make sure the call to Animator.stop() completes before
				// exiting
				new Thread(new Runnable() {

					@Override
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		// Center frame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		canvas.addKeyListener(app);
		canvas.setFocusable(true);
		canvas.requestFocusInWindow();
		animator.start();


	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		keyCode = e.getKeyCode();
		//System.out.println(e.getKeyChar() + " typed");
		
		
		//controls for the camera
		if(keyCode == KeyEvent.VK_W)
		{
			camera.moveForwards();
		}
		if(keyCode == KeyEvent.VK_S)
		{
			camera.moveBackwards();
		}
		if(keyCode == KeyEvent.VK_A)
		{
			camera.strafeLeft();
		}
		if(keyCode == KeyEvent.VK_D)
		{
			camera.strafeRight();
		}
		if(keyCode == KeyEvent.VK_UP)
		{
			camera.pitchUp();
		}
		if(keyCode == KeyEvent.VK_DOWN)
		{
			camera.pitchDown();
		}
		if(keyCode == KeyEvent.VK_LEFT)
		{
			camera.turnLeft();
		}
		if(keyCode == KeyEvent.VK_RIGHT)
		{
			camera.turnRight();
		}
		if(keyCode == KeyEvent.VK_E)
		{
			camera.moveUp();
		}
		if(keyCode == KeyEvent.VK_C)
		{
			camera.moveDown();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.VK_O)
		{
			mercury.toggleLines();
			venus.toggleLines();
			earth.toggleLines();
			mars.toggleLines();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
