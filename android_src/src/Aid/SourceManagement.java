package Aid;

import android.content.res.Resources;
import VAST.HexGame.main.R;

/**
 * Class to save the paths of all resources.
 * 
 * @author SidneyTTW
 * 
 */
public class SourceManagement {
  public static Resources r;

  public static int BALL_IMAGE_SIZE = 60;

  public static int MAIN_MENU_ITEM_RADIOS = 60;

  public static int PUZZLE_TOTAL_TYPES = 3;
  public static int PUZZLE_TOTAL_STAGES = 38;

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

  public static final String StatisticFile = "statistic";
  public static final String[] PuzzleRecordFiles = { "exchange", "unite",
      "lock" };
  public static final String[] NonPuzzleRecordFiles = { "swapclassic",
      "rotateclassic", "swapendless", "rotateendless" };

  public static final int[][] achivementFiles = {
      { R.drawable.none, R.drawable.flamebronze, R.drawable.flamesilver,
          R.drawable.flamegold },
      { R.drawable.none, R.drawable.starbronze, R.drawable.starsilver,
          R.drawable.stargold },
      { R.drawable.none, R.drawable.rotatebronze, R.drawable.rotatesilver,
          R.drawable.rotategold },
      { R.drawable.none, R.drawable.timingbronze, R.drawable.timingsilver,
          R.drawable.timinggold },
      { R.drawable.none, R.drawable.puzzlehalf, R.drawable.puzzleall } };
  public static final int AchivementItemRadius = 90;

  public static final int FlameBonusFile = R.drawable.flame;
  public static final int StarBonusFile = R.drawable.star;

  public static final int StandardButtonWidth = 160;
  public static final int StandardButtonHeight = 60;
  public static final int StandardBonusRadious = 40;
  public static final int StandardButtonNormalFile = R.drawable.buttonbackground;
  public static final int StandardButtonPressedFile = R.drawable.buttonbackground;

  public static final int LockFile = R.drawable.lock01;

  public static final int[] BallFiles = { R.drawable.red01, R.drawable.blue01,
      R.drawable.green01, R.drawable.yellow01, R.drawable.purple01,
      R.drawable.white01 };

  public static final int VerticalProgressbarForeFile = R.drawable.verticalfore;
  public static final int VerticalProgressbarBackFile = R.drawable.verticalback;
  public static final int VerticalProgressbarWidth = 120;
  public static final int VerticalProgressbarHeight = 550;
  public static final int VerticalProgressbarForeFrom = 57;
  public static final int VerticalProgressbarForeTo = 539;
  public static final int VerticalProgressbarWordXOffset = 58;
  public static final int VerticalProgressbarWordYOffset = 32;

  public static final int[] BackgroundFiles = { R.drawable.mainmenubackground,
      R.drawable.puzzlemenubackground, R.drawable.mainmenubackground,
      R.drawable.thirtysevengamebackground, R.drawable.sixtyonegamebackground,
      R.drawable.helpbackgroundwithbutton, R.drawable.achievementbackground,
      R.drawable.thirtysevengamebackground,
      R.drawable.mainselectplayersbackground };

  public static final int[] HintFile = { R.drawable.arrow, R.drawable.rotate };

  public static final int MainMenuCircleItemFile = R.drawable.circle;
  public static final int MainMenuGameButtonsItemFile = R.drawable.mainmenugamebuttons;

  public static final int[] WordBackgroundFile = { R.drawable.label182,
      R.drawable.label280, R.drawable.label316 };

  public static int puzzleFile(int type, int stage, boolean advanced) {
    switch (type) {
    case 0:
      switch (stage) {
      case 0:
        return advanced ? R.raw.exchange1advanced : R.raw.exchange1;
      case 1:
        return advanced ? R.raw.exchange2advanced : R.raw.exchange2;
      case 2:
        return advanced ? R.raw.exchange3advanced : R.raw.exchange3;
      case 3:
        return advanced ? R.raw.exchange4advanced : R.raw.exchange4;
      }
    case 1:
      switch (stage) {
      case 0:
        return advanced ? R.raw.unite1advanced : R.raw.unite1;
      case 1:
        return advanced ? R.raw.unite2advanced : R.raw.unite2;
      case 2:
        return advanced ? R.raw.unite3advanced : R.raw.unite3;
      case 3:
        return advanced ? R.raw.unite4advanced : R.raw.unite4;
      case 4:
        return advanced ? R.raw.unite5advanced : R.raw.unite5;
      }
    case 2:
      switch (stage) {
      case 0:
        return advanced ? R.raw.lock1advanced : R.raw.lock1;
      case 1:
        return advanced ? R.raw.lock2advanced : R.raw.lock2;
      case 2:
        return advanced ? R.raw.lock3advanced : R.raw.lock3;
      case 3:
        return advanced ? R.raw.lock4advanced : R.raw.lock4;
      case 4:
        return advanced ? R.raw.lock5advanced : R.raw.lock5;
      case 5:
        return advanced ? R.raw.lock6advanced : R.raw.lock6;
      case 6:
        return advanced ? R.raw.lock7advanced : R.raw.lock7;
      case 7:
        return advanced ? R.raw.lock8advanced : R.raw.lock8;
      case 8:
        return advanced ? R.raw.lock9advanced : R.raw.lock9;
      case 9:
        return advanced ? R.raw.lock10advanced : R.raw.lock10;
      }
    }
    return 0;
  }

  public static final int PuzzleExchange = 0;
  public static final int PuzzleUnite = 1;
  public static final int PuzzleLock = 2;

  public static final int PUZZLE_TYPE_IMAGE_SIZE = 300;

  public static final int[] PuzzleTypeFile = { R.drawable.exchange,
      R.drawable.unite, R.drawable.lock };

  public static int PUZZLE_STAGE_IMAGE_RADIUS = 86;

  public static final String[] stageNames = { "01", "02", "03", "04", "05",
      "06", "07", "08", "09", "10" };

  public static int puzzleStageFile(int stage, boolean advanced, boolean locked) {
    switch (stage) {
    case -2:
      return R.drawable.button_exit;
    case -1:
      return advanced ? R.drawable.button_advanced : R.drawable.button_normal;
    case 0:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked01
          : R.drawable.stage_img_advanced01)
          : (locked ? R.drawable.stage_img_locked01 : R.drawable.stage_img01);
    case 1:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked02
          : R.drawable.stage_img_advanced02)
          : (locked ? R.drawable.stage_img_locked02 : R.drawable.stage_img02);
    case 2:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked03
          : R.drawable.stage_img_advanced03)
          : (locked ? R.drawable.stage_img_locked03 : R.drawable.stage_img03);
    case 3:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked04
          : R.drawable.stage_img_advanced04)
          : (locked ? R.drawable.stage_img_locked04 : R.drawable.stage_img04);
    case 4:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked05
          : R.drawable.stage_img_advanced05)
          : (locked ? R.drawable.stage_img_locked05 : R.drawable.stage_img05);
    case 5:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked06
          : R.drawable.stage_img_advanced06)
          : (locked ? R.drawable.stage_img_locked06 : R.drawable.stage_img06);
    case 6:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked07
          : R.drawable.stage_img_advanced07)
          : (locked ? R.drawable.stage_img_locked07 : R.drawable.stage_img07);
    case 7:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked08
          : R.drawable.stage_img_advanced08)
          : (locked ? R.drawable.stage_img_locked08 : R.drawable.stage_img08);
    case 8:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked09
          : R.drawable.stage_img_advanced09)
          : (locked ? R.drawable.stage_img_locked09 : R.drawable.stage_img09);
    case 9:
      return advanced ? (locked ? R.drawable.stage_img_advanced_locked10
          : R.drawable.stage_img_advanced10)
          : (locked ? R.drawable.stage_img_locked10 : R.drawable.stage_img10);
    }
    return 0;
  }
}
