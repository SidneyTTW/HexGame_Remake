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
      return -1;
    if (!FileProcessor.exists(SourceManagement.PuzzleRecordFiles[type]))
      FileProcessor.createFile(SourceManagement.PuzzleRecordFiles[type],
          SourceManagement.PuzzleCounts[type]);
    int data[] = FileProcessor
        .readData(SourceManagement.PuzzleRecordFiles[type]);

    if (index < 0 || index >= data.length)
      return -1;
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
    return data;
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
