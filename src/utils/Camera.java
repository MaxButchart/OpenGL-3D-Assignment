package utils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	
	private static final double FOV = 20;
	
	private final double MOVE_SPEED = 20;
	
	 double windowWidth      = 1;
	 double windowHeight     = 1;
	 
	 private double camX = 0;
	 private double camY = 176.32;
	 private double camZ = -1000;
	 
	 private double pitchAngle = -10;
	 private double yawAngle = 0;
	 
    
	// the point to look at
	private double lookAt[] = {0, 0, 0};
	
	private double lookAtDistance = Math.abs(camZ + lookAt[2]);
	
	
	public void draw(GL2 gl){
		// set up projection first
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluPerspective(FOV, (float) windowWidth / (float) windowHeight, 0.1, 2000);
        // set up the camera position and orientation
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(camX, camY, camZ, //eye
                	     lookAt[0], lookAt[1], lookAt[2], // looking at 
                         0.0, 1.0, 0.0); // up
        
        
	}
	
	/**
     * Sets up the lookAt point - could be a specified object's location
     * @param x X coordinate of the lookAt point
     * @param y Y coordinate of the lookAt point
     * @param z Z coordinate of the lookAt point
     */
    public void setLookAt(double x, double y, double z) {
        lookAt = new double[]{x, y, z};
    }
    
    public void setEye(double x, double y, double z){
    	camX = x;
    	camY = y;
    	camZ = z;
    }
	
	 /**
     * Passes a new window size to the camera.
     * This method should be called from the <code>reshape()</code> method
     * of the main program.
     *
     * @param width the new window width in pixels
     * @param height the new window height in pixels
     */
    public void newWindowSize(int width, int height) {
        windowWidth = Math.max(1.0, width);
        windowHeight = Math.max(1.0, height);
    }
    
    //methods for moving the camera
    public void pitchUp()
    {
    	if(pitchAngle < 90)
    	{
    		pitchAngle += 1;
    		lookAt[1] = (Math.sin(Math.toRadians(pitchAngle)) * lookAtDistance);
    		lookAt[2] = (Math.cos(Math.toRadians(pitchAngle)) * lookAtDistance);
    	}
     	else
    	{
    		pitchAngle = 89;
    		lookAt[1] = (Math.sin(Math.toRadians(pitchAngle)) * lookAtDistance);
    		lookAt[2] = (Math.cos(Math.toRadians(pitchAngle)) * lookAtDistance);
    	}
    }
    public void pitchDown()
    {	
    	if(pitchAngle > -90)
    	{
    		pitchAngle -= 1;
    		lookAt[1] = (Math.sin(Math.toRadians(pitchAngle)) * lookAtDistance);
    	}
    	else
    	{
    		pitchAngle = -89;
    		lookAt[1] = (Math.sin(Math.toRadians(pitchAngle)) * lookAtDistance);
    	}
    }
    public void moveUp()
    {
    	camY += MOVE_SPEED;
    	lookAt[1] += MOVE_SPEED;
    }
    public void moveDown()
    {
    	camY -= MOVE_SPEED;
    	lookAt[1] -= MOVE_SPEED;
    }
    public void moveForwards()
    {
    	camY += (Math.sin(Math.toRadians(pitchAngle))) * MOVE_SPEED;
    	lookAt[1] = lookAt[1] + (Math.sin(Math.toRadians(pitchAngle)))  * MOVE_SPEED;
    	
    	camX += (Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;
    	lookAt[0] += (Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;
    	
    	camZ += (Math.sin(Math.toRadians(yawAngle +90)))  * MOVE_SPEED;
    	lookAt[2] += (Math.sin(Math.toRadians(yawAngle + 90)))  * MOVE_SPEED;
    }
    public void moveBackwards()
    {
    	camY -= (Math.sin(Math.toRadians(pitchAngle)))  * MOVE_SPEED;
    	lookAt[1] -= (Math.sin(Math.toRadians(pitchAngle)))  * MOVE_SPEED;
    	
    	camX -= (Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;
    	lookAt[0] -= (Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;

    	camZ -= (Math.sin(Math.toRadians(yawAngle + 90)))  * MOVE_SPEED;
    	lookAt[2] -= (Math.sin(Math.toRadians(yawAngle + 90)))  * MOVE_SPEED;
    }
    public void strafeLeft() 
    {
    	camZ += Math.abs(Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;
    	lookAt[2] += Math.abs(Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;

    	camX += Math.abs(Math.sin(Math.toRadians(yawAngle + 90)))  * MOVE_SPEED;
    	lookAt[0] += Math.abs(Math.sin(Math.toRadians(yawAngle + 90)))  * MOVE_SPEED;
    }
    public void strafeRight()
    {
    	camZ -= Math.abs(Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;
    	lookAt[2] -= Math.abs(Math.sin(Math.toRadians(yawAngle)))  * MOVE_SPEED;
    	
    	camX -= Math.abs(Math.sin(Math.toRadians(yawAngle + 90)))  * MOVE_SPEED;
    	lookAt[0] -= Math.abs(Math.sin(Math.toRadians(yawAngle + 90)))  * MOVE_SPEED;
    }
    public void turnRight()
    {
    	yawAngle -= 1;
    	
    	if(yawAngle < 0)
    	{
    		yawAngle = 360;
    	}
    	
    	lookAt[0] = (Math.sin(Math.toRadians(yawAngle)) * lookAtDistance);
		lookAt[2] = (Math.cos(Math.toRadians(yawAngle)) * lookAtDistance);
		


		
		
    }
    public void turnLeft()
    {
    	yawAngle += 1;
    	
    	if(yawAngle > 360)
    	{
    		yawAngle = 0;
    	}
    	
    	lookAt[0] = (Math.sin(Math.toRadians(yawAngle)) * lookAtDistance);
		lookAt[2] = (Math.cos(Math.toRadians(yawAngle)) * lookAtDistance);
		

		
	
    }

}
