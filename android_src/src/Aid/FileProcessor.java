/**
 * 
 */
package Aid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;

/**
 * Class to process files.
 * 
 * @author SidneyTTW
 * 
 */
public class FileProcessor {
  public static String fileFolder = null;

  public static Activity activity;

  public static boolean createFile(String fileName, int count) {
    activity.deleteFile(fileName);
    try {
      FileOutputStream os = activity.openFileOutput(fileName,
          Context.MODE_PRIVATE);
      byte[] ints = new byte[count * 4];
      for (int i = 0; i < count * 4; ++i)
        ints[i] = 0;
      os.write(ints);
      os.close();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public static boolean exists(String fileName) {
    try {
      activity.openFileInput(fileName);
      return true;
    } catch (FileNotFoundException e) {
      return false;
    }
  }

  public static boolean remove(String fileName) {
    return activity.deleteFile(fileName);
  }

  public static int countOfInt(String fileName) {
    try {
      return activity.openFileInput(fileName).available() / 4;
    } catch (IOException e) {
      return 0;
    }
  }

  public static boolean writeData(String fileName, int pos, int data) {
    int[] originalData = null;
    if (exists(fileName)) {
      originalData = readData(fileName);
    }
    if (originalData == null) {
      originalData = new int[pos + 1];
      for (int i = 0; i < pos; ++i)
        originalData[i] = 0;
      originalData[pos] = data;
    } else if (originalData.length <= pos) {
      int[] newArray = new int[pos + 1];
      for (int i = 0; i < originalData.length; ++i)
        newArray[i] = originalData[i];
      for (int i = originalData.length; i < pos; ++i)
        newArray[i] = 0;
      newArray[pos] = data;
      originalData = newArray;
    } else
      originalData[pos] = data;
    writeDataArr(fileName, originalData);
    return true;
  }

  public static int readData(String fileName, int pos) {
    try {
      FileInputStream is = activity.openFileInput(fileName);
      is.skip(pos * 4);
      int result = 0;
      byte[] buffer = new byte[4];
      is.read(buffer);
      for (int i = 0; i < 4; ++i)
        result |= (((int) buffer[i]) & 0xFFFFFFFF) << ((3 - i) * 8);
      return result;
    } catch (IOException e) {
      return 0;
    }
  }

  public static boolean writeDataArr(String fileName, int[] data) {
    activity.deleteFile(fileName);
    try {
      FileOutputStream os = activity.openFileOutput(fileName,
          Context.MODE_PRIVATE);
      byte[] bytes = new byte[data.length * 4];
      for (int i = 0; i < data.length; ++i) {
        for (int j = 0; j < 4; ++j) {
          bytes[i * 4 + j] = (byte) (data[i] >> ((3 - j) * 8));
        }
      }
      os.write(bytes);
      os.close();
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public static int[] readData(String fileName) {
    try {
      FileInputStream is = activity.openFileInput(fileName);
      int size = is.available() / 4;
      int[] result = new int[size];
      for (int i = 0; i < size; ++i) {
        result[i] = 0;
        for (int j = 0; j < 4; ++j) {
          result[i] += is.read() << ((3 - j) * 8);
        }
      }
      return result;
    } catch (IOException e) {
      return null;
    }
  }
}
