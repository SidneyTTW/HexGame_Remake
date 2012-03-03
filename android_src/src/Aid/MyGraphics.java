/**
 * 
 */
package Aid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Class to package graphics, maybe the Graghics2D of awt, maybe paint of
 * android, it depends on the platform.
 * 
 * Currently, it only supports Graghics2D.
 * 
 * @author SidneyTTW
 * 
 */
public class MyGraphics {
  Canvas graphics;
  Paint paint;
  int alpha = 255;
  MyColor color;

  /**
   * @return the paint
   */
  public Paint getPaint() {
    return paint;
  }

  /**
   * @param paint
   *          the paint to set
   */
  public void setPaint(Paint paint) {
    this.paint = paint;
    color = new MyColor(paint.getColor());
  }

  public MyGraphics(Canvas graphics) {
    this.graphics = graphics;
    this.paint = new Paint();
    color = new MyColor(paint.getColor());
  }

  public void translate(float x, float y) {
    graphics.translate(x, y);
  }

  public void rotate(float rotation) {
    graphics.rotate((float) (rotation/MathAid.PI*180));
  }

  public void scale(float xRate, float yRate) {
    graphics.scale(xRate, yRate);
  }

  public void drawImage(MyImage image, float x, float y) {
    paint.setAlpha(alpha);
    graphics.drawBitmap(image.image, x, y, paint);
  }

  public void drawString(String text, float x, float y) {
    paint.setAlpha(alpha * color.alpha() / 255);
    graphics.drawText(text, x, y, paint);
  }

  public float stringWidth(String text) {
    float[] widths = new float[text.length()];
    paint.getTextWidths(text, widths);
    float result = 0;
    for (int i = 0; i < text.length(); ++i)
      result += widths[i];
    return result;
  }

  public float stringHeight() {
    return paint.getFontMetrics().descent - paint.getFontMetrics().top;
  }

  public MyFont getFont() {
    return new MyFont(paint.getTypeface(), (int) paint.getTextSize());
  }

  public void setFont(MyFont font) {
    paint.setTypeface(font.font);
    paint.setTextSize(font.size);
  }

  public void setColor(MyColor color) {
    this.color = new MyColor(color);
    paint.setColor(color.color);
    paint.setAlpha(color.alpha() * alpha / 255);
  }
  
  public int getAlpha() {
    return alpha;
  }

  public void drawImage(MyImage image, float x, float y, float width, float height) {
    paint.setAlpha(alpha);
    float w = image.getWidth();
    float h = image.getHeight();
    graphics.translate(x, y);
    graphics.scale(width / w, height / h);
    graphics.drawBitmap(image.image, 0, 0, paint);
    graphics.scale(w / width, h / height);
    graphics.translate(-x, -y);
  }

  public MyStroke getStroke() {
    return new MyStroke(paint.getStrokeWidth(), paint.getStrokeCap(), paint.getStrokeJoin());
  }

  public void setStroke(MyStroke stroke) {
    paint.setStrokeWidth(stroke.width);
    paint.setStrokeCap(stroke.cap);
    paint.setStrokeJoin(stroke.joint);
  }

  public void drawPolygon(MyPolygon polygon) {
    polygon.testClosed();
    paint.setStyle(Paint.Style.STROKE);  
    graphics.drawPath(polygon.polygon, paint);
  }

  public void setPaint(MyRadialGradientPaint paint) {
    this.paint.setShader(paint.shader);
  }

  public void fillOval(float x, float y, float width, float height) {
    paint.setStyle(Paint.Style.FILL);  
    graphics.drawOval(new RectF(x, y, x + width, y + height), paint);
  }

  public void fillPolygon(MyPolygon polygon) {
    polygon.testClosed();
    paint.setStyle(Paint.Style.FILL);  
    graphics.drawPath(polygon.polygon, paint);
  }

  public void fillRect(float x, float y, float width, float height) {
    paint.setStyle(Paint.Style.FILL);  
    graphics.drawRect(x, y, x + width, y + height, paint);
  }

  public void setPaint(MyLinearGradientPaint paint) {
    this.paint.setShader(paint.shader);
  }

  public void drawLine(float x1, float y1, float x2, float y2) {
    graphics.drawLine(x1, y1, x2, y2, paint);
  }

  public void drawRoundRect(float x, float y, float width, float height, float xr, float yr) {
    paint.setStyle(Paint.Style.STROKE);  
    graphics.drawRoundRect(new RectF(x, y, x + width, y + height), xr, yr, paint);
  }

  public void save() {
    graphics.save();
  }

  public void setClip(float x, float y, float width, float height) {
    graphics.clipRect(x, y, x + width, y + height);
  }

  public void restore() {
    graphics.restore();
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha;
    paint.setAlpha(alpha * color.alpha() / 255);
  }
}
