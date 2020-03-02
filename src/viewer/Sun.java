package viewer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Sun {

	public void draw(GLUT glut, GL2 gl)
	{
		
		gl.glColor4d(1, 1, 0, 1);
		glut.glutSolidSphere(15, 100, 100);
		
		
		
	}
	public void drawCarona(GLUT glut, GL2 gl)
	{
		gl.glColor4d(1, 1, 0, 0.2);
		glut.glutSolidSphere(16, 100, 100);
	}
	
}
