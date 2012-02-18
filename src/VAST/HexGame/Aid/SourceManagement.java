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
  public static int PUZZLE_TOTAL_TYPES = 6;
  public static int PUZZLE_TOTAL_STAGES = 38;

  public static int PuzzleExchange = 0;
  public static int PuzzleUnite = 1;
  public static int PuzzleLock = 2;

  public static int PuzzleCounts[] = { 4, 5, 10 };

  public static final int ArrowHint = 0;
  public static final int RotateHint = 1;

  public static final int WordBackground182 = 0;
  public static final int WordBackground280 = 1;
  public static final int WordBackground316 = 2;

  public static final int WordBackgroundHeight = 70;
  public static final int WordBackgroundWidth182 = 182;
  public static final int WordBackgroundWidth280 = 280;
  public static final int WordBackgroundWidth316 = 316;

  public static final String RecordFolder = "j:/tmp/records";
  public static final String StatisticFile = RecordFolder + "statistic";

  private static final String mainFolder = "j:/tmp";

  public static final String HintFolder = mainFolder + "/images/hint";
  public static final String ButtonFolder = mainFolder + "/images/buttons";
  public static final String BonusFolder = mainFolder + "/images/bonus";
  public static final String AchievementFolder = mainFolder
      + "/images/achievements";
  public static final String PuzzleMainFolder = mainFolder + "/puzzle";
  public static final String[][] achivementFiles = {
      { "none.png", "flamebronze.png", "flamesilver.png", "flamegold.png" },
      { "none.png", "starbronze.png", "starsilver.png", "stargold.png" },
      { "none.png", "rotatebronze.png", "rotatesilver.png", "rotategold.png" },
      { "none.png", "timingbronze.png", "timingsilver.png", "timinggold.png" },
      { "none.png", "puzzlehalf.png", "puzzleall.png" } };

  public static final String[] PuzzleRecordFiles = {
      RecordFolder + "/exchange", RecordFolder + "/unite",
      RecordFolder + "/lock" };

  public static final String PuzzleFolder[] = { PuzzleMainFolder + "/Exchange",
      PuzzleMainFolder + "/Unite", PuzzleMainFolder + "/Lock" };

  public static final String FlameBonusFile = "flame.png";
  public static final String StarBonusFile = "star.png";

  public static final int StandardButtonWidth = 160;
  public static final int StandardButtonHeight = 60;
  public static final int StandardBonusRadious = 25;
  public static final String StandardButtonNormalFile = "buttonbackground.png";
  public static final String StandardButtonPressedFile = "buttonbackground.png";

  public static final String BallFolder = mainFolder + "/images/balls";

  public static final String LockFile = "lock0*.png";

  public static final String[] BallFiles = { "red0*.png", "blue0*.png",
      "green0*.png", "yellow0*.png", "purple0*.png", "white0*.png" };

  public static final String BackgroundFolder = mainFolder
      + "/images/backgrounds";

  public static final String ProgressbarFolder = mainFolder
      + "/images/progressbars";

  public static final String VerticalProgressbarForeFile = "verticalfore.png";
  public static final String VerticalProgressbarBackFile = "verticalback.png";
  public static final int VerticalProgressbarWidth = 120;
  public static final int VerticalProgressbarHeight = 550;
  public static final int VerticalProgressbarForeFrom = 57;
  public static final int VerticalProgressbarForeTo = 539;
  public static final int VerticalProgressbarWordXOffset = 58;
  public static final int VerticalProgressbarWordYOffset = 32;

  public static final String[] BackgroundFiles = { "mainmenubackground.png",
      "puzzlemenubackground.png", "mainmenubackground.png",
      "37gamebackground.png", "61gamebackground.png",
      "helpbackgroundwithbutton.png", "achievementbackground.png",
      "37twoplayergamebackground.png", "mainselectplayersbackground.png" };

  public static final String[] HintFile = { "arrow.png", "rotate.png" };

  public static final String WordBackgroundFolder = mainFolder
      + "/images/wordbackgrounds";

  public static final String[] WordBackgroundFile = { "label182.png",
      "label280.png", "label316.png" };

  public static String puzzleFile(int type, int stage, boolean advanced) {
    return PuzzleFolder[type] + (advanced ? "advanced" : "") + stage;
  }
}
