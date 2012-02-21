/**
 * 
 */
package VAST.HexGame.GameItem;

import AidPackage.MyColor;
import VAST.HexGame.Aid.SourceManagement;
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
    setImageSeries(SourceManagement.ButtonFolder,
        SourceManagement.StandardButtonNormalFile);
    setPressedImageSeries(SourceManagement.ButtonFolder,
        SourceManagement.StandardButtonPressedFile);
    setWidth(SourceManagement.StandardButtonWidth);
    setHeight(SourceManagement.StandardButtonHeight);
    setColor(MyColor.white);
  }

  /**
   * Constructor with given image files.
   * 
   * @param dirNormal
   *          The folder path of the normal images.
   * @param fileNormal
   *          The file name of the normal images.
   * @param dirPressed
   *          The folder path of the pressed images.
   * @param filePressed
   *          The file name of the pressed images.
   */
  public StandardGameButtonItem(String dirNormal, String fileNormal,
      String dirPressed, String filePressed) {
    setImageSeries(dirNormal, fileNormal);
    if (dirPressed != null && filePressed != null)
      this.setPressedImageSeries(dirPressed, filePressed);
  }
}
