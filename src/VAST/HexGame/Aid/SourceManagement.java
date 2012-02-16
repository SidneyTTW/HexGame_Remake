/**
 * 
 */
package VAST.HexGame.Aid;

/**
 * Class to save the paths of all resources.
 * 
 * @author SidneyTTW
 *
 */
public class SourceManagement {
  public static final int ArrowHint = 0;
  public static final int RotateHint = 1;

  private static final String mainFolder = "j:/tmp";
  
  public static final String HintFolder = mainFolder + "/images/hint";
  public static final String ButtonFolder = mainFolder + "/images/buttons";

  public static final int StandardButtonWidth = 160;
  public static final int StandardButtonHeight = 60;
  public static final String StandardButtonNormalFile = "buttonbackground.png";
  public static final String StandardButtonPressedFile = "buttonbackground.png";

  public static final String BallFolder = "j:/tmp/images/balls";

  public static final String LockFile = "lock0*.png";

  public static final String[] BallFiles = { "red0*.png", "blue0*.png",
      "green0*.png", "yellow0*.png", "purple0*.png", "white0*.png" };

  public static final String BackgroundFolder = "j:/tmp/images/backgrounds";

  public static final String[] BackgroundFiles = { "mainmenubackground.png",
      "puzzlemenubackground.png", "mainmenubackground.png",
      "37gamebackground.png", "61gamebackground.png",
      "helpbackgroundwithbutton.png", "achievementbackground.png",
      "37twoplayergamebackground.png", "mainselectplayersbackground.png" };
  
  public static final String [] HintFile = {"arrow.png", "rotate.png"};

}
