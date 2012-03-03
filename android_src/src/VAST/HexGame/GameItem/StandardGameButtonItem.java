/**
 * 
 */
package VAST.HexGame.GameItem;

import Aid.MyColor;
import Aid.SourceManagement;
import VAST.HexGame.Widgets.RectButtonItem;

/**
 * Class of standard button in the game. You can use constructor with no
 * parameters to create a standard button which few text. You can also use
 * constructor with given image files to create a button, be aware that the
 * images won't be scaled.
 * 
 * @author SidneyTTW
 * 
 */
public class StandardGameButtonItem extends RectButtonItem {
  /**
   * Constructor with no parameters.
   */
  public StandardGameButtonItem() {
    setImageSeries(SourceManagement.StandardButtonNormalFile);
    setPressedImageSeries(SourceManagement.StandardButtonPressedFile);
    setWidth(SourceManagement.StandardButtonWidth);
    setHeight(SourceManagement.StandardButtonHeight);
    setColor(MyColor.white);
  }

  /**
   * Constructor with given image files.
   * 
   * @param idNormal
   *          The id of the normal images.
   * @param idPressed
   *          The id of the pressed images.
   */
  public StandardGameButtonItem(int idNormal, int idPressed) {
    setImageSeries(idNormal);
    if (idPressed > 0)
      this.setPressedImageSeries(idPressed);
  }
}
