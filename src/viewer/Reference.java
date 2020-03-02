package viewer;

import com.jogamp.opengl.GL2;

public class Reference {

	public void draw(GL2 gl)
	{
		gl.glColor3f(1, 0, 0);
		
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(100, 0, 0);
		gl.glEnd();
		
		gl.glColor3f(0, 1, 0);
		
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, 100, 0);
		gl.glEnd();
		
		gl.glColor3f(0, 0, 1);
		
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, 0, -100);
		gl.glEnd();
	}
	
}
