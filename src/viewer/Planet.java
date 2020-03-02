package viewer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Planet {

	//scale factor for radius of sun in km
	private final double PLANET_SCALE_FACTOR = 87;
	private final double ORBIT_SCALE_FACTOR = 1000;
	private double planetRadius, orbitRadius, orbitSpeed;
	private double x, z, yawAngle;
	private double red, green, blue;
	private double alpha = 0.8;
	private boolean lineOn = true;
	private double time;
	
	
	public Planet(double radius, double orbit, double speed)
	{
		this.planetRadius = radius;
		this.orbitRadius = orbit;
		this.orbitSpeed = speed;
		x = orbit * -1;
		z = orbit;
		yawAngle = 0;
		
		red = 1;
		green = 1;
		blue = 1;
	}
	
	public void setColour(double r, double g, double b)
	{
		red = r;
		green = g;
		blue = b;
	}
	
	public void draw(GLUT glut, GL2 gl)
	{
		
		gl.glColor4d(red, green, blue, 1);
		glut.glutSolidSphere(planetRadius, 100, 100);
		
	}
	
	public void translate(GL2 gl)
	{
		
		gl.glTranslated(x, 0, z);
	
	}
	public void animate()
	{
		time = System.currentTimeMillis() / 1000.0;
		yawAngle += orbitSpeed;
		yawAngle %= 360 + time;
		
		x = Math.sin(Math.toRadians(yawAngle)) * orbitRadius;
		z = Math.cos(Math.toRadians(yawAngle)) * orbitRadius;
	}
	public void drawOrbitLines(GL2 gl)
	{
		gl.glColor4d(1, 1, 1, alpha);
		gl.glBegin(GL2.GL_LINE_LOOP);
		
		for(int i = 0; i < 360; i++)
		{
			gl.glVertex3d(Math.sin(Math.toRadians(i)) * orbitRadius, 0, 
					Math.cos(Math.toRadians(i)) * orbitRadius);
		}
		
		gl.glEnd();	
		
	}
	public void toggleLines()
	{
		lineOn = !lineOn;
		
		if(lineOn)
		{
			alpha = 0.8;
		}
		else
		{
			alpha = 0;
		}
	}
}
