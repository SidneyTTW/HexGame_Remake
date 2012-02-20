/**
 * 
 */
package VAST.HexGame.Aid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import AidPackage.FileProcessor;

/**
 * Class to get the information of puzzle games.
 * 
 * @author SidneyTTW
 * 
 */
public class PuzzleInfo {
  public static int totalStages() {
    return SourceManagement.PUZZLE_TOTAL_STAGES;
  }

  public static int totalStages(int type) {
    if (type < 0 || type >= SourceManagement.PUZZLE_TOTAL_TYPES)
      return 0;
    return SourceManagement.PuzzleCounts[type];
  }

  public static int clearedStages() {
    int result = 0;
    for (int i = 0; i < SourceManagement.PUZZLE_TOTAL_TYPES; ++i)
      result += clearedStages(i, true) + clearedStages(i, false);
    return result;
  }

  public static int clearedStages(int type, boolean advanced) {
    int[] currentMinSteps = minSteps(type, advanced);
    if (currentMinSteps == null)
      return 0;
    int result = 0;
    for (int i = 0; i < currentMinSteps.length; ++i)
      if (currentMinSteps[i] >= 0)
        ++result;
    return result;
  }

  public static int minStep(int type, int index, boolean advanced) {
    if (type < 0 || type >= SourceManagement.PUZZLE_TOTAL_TYPES
        || index >= totalStages(type))
      return 0;
    if (!FileProcessor.exists(SourceManagement.PuzzleRecordFiles[type]))
      FileProcessor.createFile(SourceManagement.PuzzleRecordFiles[type],
          SourceManagement.PuzzleCounts[type]);
    int data[] = FileProcessor
        .readData(SourceManagement.PuzzleRecordFiles[type]);
    index = index + (advanced ? data.length / 2 : 0);
    if (index < 0 || index >= data.length)
      return 0;
    return data[index];
  }

  public static int[] minSteps(int type, boolean advanced) {
    if (type < 0 || type >= SourceManagement.PUZZLE_TOTAL_TYPES)
      return null;
    if (!FileProcessor.exists(SourceManagement.PuzzleRecordFiles[type]))
      FileProcessor.createFile(SourceManagement.PuzzleRecordFiles[type],
          SourceManagement.PuzzleCounts[type]);
    int data[] = FileProcessor
        .readData(SourceManagement.PuzzleRecordFiles[type]);
//    int [] result = new int[data.length / 2];
//    for (int i = 0;i < data.length / 2;++i)
//      result[i] = data[i + (advanced ? data.length / 2 : 0)];
    int [] result = new int[10];
    for (int i = 0;i < 10;++i)
      result[i] = 1;
    return result;
  }

  public static boolean testMinSteps(int type, int stage, boolean advanced, int steps) {
    if (type < 0 || type >= SourceManagement.PUZZLE_TOTAL_TYPES
        || stage >= totalStages(type) || steps < 0)
      return false;
    int lastSteps = minStep(type, stage, advanced);
    boolean result = false;
    if (lastSteps <= 0)
      result = true;
    FileProcessor.writeData(SourceManagement.PuzzleRecordFiles[type], stage + (advanced ? totalStages(type) : 0), steps);
    return result;
  }

  public static void readColorIndexes(int type, int stage, boolean advanced,
      int[] current, int[] target) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          SourceManagement.puzzleFile(type, stage, advanced)));

      StringTokenizer tokenizer;
      int lineNumber = 0;
      while (reader.ready()) {
        int[] arrayToWrite = (lineNumber == 0) ? current : target;
        int index = 0;
        tokenizer = new StringTokenizer(reader.readLine());
        while (tokenizer.hasMoreTokens()) {
          arrayToWrite[index] = Integer.parseInt(tokenizer.nextToken());
          ++index;
        }
        ++lineNumber;
      }
    } catch (IOException e) {
    }
  }

}
