/**
 * 
 */
package AidPackage;

import java.awt.Graphics2D;

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
  Graphics2D graphics;

  public MyGraphics(Graphics2D graphics) {
    this.graphics = graphics;
  }

  public void translate(int x, int y) {
    graphics.translate(x, y);
  }

  public void rotate(double rotation) {
    graphics.rotate(rotation);

  }

  public void scale(double xRate, double yRate) {
    graphics.scale(xRate, yRate);
  }

  public void drawImage(MyImage image, int x, int y) {
    graphics.drawImage(image.image, x, y, null);
  }

  public void drawString(String text, int x, int y) {
    graphics.drawString(text, x, y);
  }

  public int stringWidth(String text) {
    return graphics.getFontMetrics().stringWidth(text);
  }

  public int stringHeight() {
    return graphics.getFontMetrics().getAscent()
        - graphics.getFontMetrics().getDescent();
  }

  public MyFont getFont() {
    return new MyFont(graphics.getFont());
  }

  public void setFont(MyFont font) {
    graphics.setFont(font.font);
  }

  public void setColor(MyColor color) {
    graphics.setColor(color.color);
  }

  public void drawImage(MyImage image, int x, int y, int width, int height) {
    graphics.drawImage(image.image, x, y, width, height, null);
  }

  public MyStroke getStroke() {
    return new MyStroke(graphics.getStroke());
  }

  public void setStroke(MyStroke stroke) {
    graphics.setStroke(stroke.stroke);

  }

  public void drawPolygon(MyPolygon polygon) {
    graphics.drawPolygon(polygon.polygon);
  }

  public void setPaint(MyRadialGradientPaint paint) {
    graphics.setPaint(paint.paint);
  }

  public void fillOval(int x, int y, int width, int height) {
    graphics.fillOval(x, y, width, height);
  }

  public void fillPolygon(MyPolygon polygon) {
    graphics.fillPolygon(polygon.polygon);
  }

  public void fillRect(int x, int y, int width, int height) {
    graphics.fillRect(x, y, width, height);
  }

  public void setPaint(MyLinearGradientPaint paint) {
    graphics.setPaint(paint.paint);
  }

  public void drawLine(int x1, int y1, int x2, int y2) {
    graphics.drawLine(x1, y1, x2, y2);
  }

  public MyShape getClip() {
    return new MyShape(graphics.getClip());
  }

  public void setClip(int x, int y, int width, int height) {
    graphics.setClip(x, y, width, height);
  }

  public void setClip(MyShape shape) {
    graphics.setClip(shape.shape);
  }

  public MyComposite getComposite() {
    return new MyComposite(graphics.getComposite());
  }

  public void setComposite(MyComposite composite) {
    graphics.setComposite(composite.composite);
  }

}
