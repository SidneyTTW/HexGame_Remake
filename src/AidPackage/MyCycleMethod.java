/**
 * 
 */
package AidPackage;

import java.awt.MultipleGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;

/**
 * Class to replace the CycleMethod in awt.
 * 
 * @author SidneyTTW
 * 
 */
public class MyCycleMethod {

  CycleMethod cycleMethod;

  public static final MyCycleMethod NO_CYCLE = new MyCycleMethod(
      CycleMethod.NO_CYCLE);

  public static final MyCycleMethod REFLECT = new MyCycleMethod(
      MultipleGradientPaint.CycleMethod.REFLECT);

  public MyCycleMethod(CycleMethod cycleMethod) {
    this.cycleMethod = cycleMethod;
  }

}
