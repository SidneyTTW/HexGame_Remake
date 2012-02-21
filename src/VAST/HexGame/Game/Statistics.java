/**
 * 
 */
package VAST.HexGame.Game;

import VAST.HexGame.Aid.PuzzleInfo;
import VAST.HexGame.Aid.SourceManagement;
import VAST.HexGame.GameItem.AchievementItem;
import VAST.HexGame.Widgets.MainWidgetInterface;
import AidPackage.FileProcessor;
import AidPackage.MyPoint;

/**
 * Class to control the statistics.
 * 
 * @author SidneyTTW
 * 
 */
public class Statistics {
  public static final int FlameGetCount = 0;
  public static final int StarGetCount = 1;
  public static final int FlameUsedCount = 2;
  public static final int StarUsedCount = 3;
  public static final int GoodMoveCount = 4;
  public static final int BadMoveCount = 5;
  public static final int SwapClassicPoint = 6;
  public static final int RotateClassicPoint = 7;
  public static final int SwapEndlessPoint = 8;
  public static final int RotateEndlessPoint = 9;
  public static final int SwapTimingPoint = 10;
  public static final int RotateTimingPoint = 11;
  public static final int RotatePuzzleFinished = 12;
  public static final int RotatePuzzleTotal = 13;

  public static final int FlameGet = 0;
  public static final int StarGet = 1;
  public static final int RotateClassic = 2;
  public static final int Timing = 3;
  public static final int RotatePuzzle = 4;

  private static MainWidgetInterface mainWidget = null;

  public static final int[][] stages = {
      { 10, 100, 1000 },
      { 10, 100, 1000 },
      { 500, 1000, 2000 },
      { 400, 700, 1000 },
      { SourceManagement.PUZZLE_TOTAL_STAGES / 2,
          SourceManagement.PUZZLE_TOTAL_STAGES } };

  /**
   * @param mainWidget
   *          the mainWidget to set
   */
  public static void setMainWidget(MainWidgetInterface mainWidget) {
    Statistics.mainWidget = mainWidget;
  }

  public static int getStatistic(int type) {
    if (type == RotatePuzzleFinished)
      return PuzzleInfo.clearedStages();
    if (type == RotatePuzzleTotal)
      return PuzzleInfo.totalStages();

    // Create the file if necessary
    if (!FileProcessor.exists(SourceManagement.StatisticFile)) {
      int tmp[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
      FileProcessor.createFile(SourceManagement.StatisticFile, 12);
      FileProcessor.writeDataArr(SourceManagement.StatisticFile, tmp);
    }
    return FileProcessor.readData(SourceManagement.StatisticFile, type);
  }

  public static void addStatistic(int type, int value) {
    changeStatistic(type, getStatistic(type) + value);
  }

  public static void changeStatistic(int type, int value) {
    if (type == RotatePuzzleTotal)
      return;
    if (type == RotatePuzzleFinished)
      achievementGrow(RotatePuzzle, getStatistic(type), value);

    // Get the last value
    int lastValue = getStatistic(type);

    // Record the new value
    FileProcessor.writeData(SourceManagement.StatisticFile, type, value);

    int achievementType = statisticTypeToAchievementType(type);
    if (achievementType >= 0)
      achievementGrow(achievementType, lastValue, value);
  }

  public static int statisticTypeToAchievementType(int type) {
    switch (type) {
    case FlameGetCount:
      return FlameGet;
    case StarGetCount:
      return StarGet;
    case RotateClassicPoint:
      return RotateClassic;
    case SwapTimingPoint:
    case RotateTimingPoint:
      return Timing;
    case RotatePuzzleFinished:
      return RotatePuzzle;
    default:
      return -1;
    }
  }

  public static int achievementLevel(int type, int value) {
    int result;
    for (result = 0; result < stages[type].length
        && stages[type][result] <= value; ++result)
      ;
    return result;
  }

  public static void achievementGrow(int type, int originalValue,
      int currentValue) {
    if (mainWidget == null)
      return;
    int original = achievementLevel(type, originalValue);
    int current = achievementLevel(type, currentValue);
    if (current > original) {
      AchievementItem item = new AchievementItem();
      item.setImageSeries(SourceManagement.AchievementFolder,
          SourceManagement.achivementFiles[type][current]);
      MyPoint size = mainWidget.topWidgetSize();
      size = new MyPoint(size.x * 2, size.y * 2);
      mainWidget.addAnimItem(item, new MyPoint((int) (0.37 * size.x), -10),
          new MyPoint((int) (0.37 * size.x), (int) (0.2 * size.y)), size, 80);
    }
  }
}
