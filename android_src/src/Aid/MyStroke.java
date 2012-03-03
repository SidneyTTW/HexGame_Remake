/**
 * 
 */
package Aid;

import android.graphics.Paint;

/**
 * Class to replace the Stroke in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyStroke {
	public static final Paint.Cap CAP_SQUARE = Paint.Cap.SQUARE;
	public static final Paint.Join JOIN_ROUND = Paint.Join.ROUND;
	float width;
	Paint.Cap cap;
	Paint.Join joint;

  public MyStroke(float width, Paint.Cap cap, Paint.Join joint) {
		this.width = width;
		this.cap = cap;
		this.joint = joint;
	}

}
