package VAST.HexGame.GameWidget;

import Aid.MyGraphics;
import Aid.MyPoint;
import VAST.HexGame.Aid.PuzzleInfo;
import VAST.HexGame.Effect.EffectPainter;
import VAST.HexGame.Game.*;
import VAST.HexGame.GameItem.IntegerItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;
import VAST.HexGame.Widgets.RectButtonItem;

/**
 * Class to play a puzzle game.
 * 
 * @author SidneyTTW
 * 
 */
public class PuzzleGameWidget extends AbstractStandardGameWidget {
  protected static final int Stay = 0;
  protected static final int ShowHint = 1;
  protected static final int HideHint = 2;

  protected static final double HINT_BASE_RATE = 0.4;

  protected static final int HINT_ANIM_MAX = 5;

  protected int hintAnimCount = 0;
  protected int animDirection = Stay;

  private static final int PuzzleHintButton = AbstractStandardGameWidget.BUILT_IN_BUTTON_COUNT + 0;

  protected RectButtonItem puzzleHintButton;

  protected IntegerItem currentMoveItem;
  protected IntegerItem leastMoveItem;

  public class PuzzleGameRecord {
    int type;
    int stage;
    boolean advanced;
    int[] originalColorIndexes = null;
    int[] targetColorIndexes = null;
    int minSteps = -1;

    public void load(int type, int stage, boolean advanced) {
      this.type = type;
      this.stage = stage;
      this.advanced = advanced;

      originalColorIndexes = new int[61];
      targetColorIndexes = new int[61];
      PuzzleInfo.readColorIndexes(type, stage, advanced, originalColorIndexes,
          targetColorIndexes);
    }
  }

  PuzzleGameRecord record;

  public PuzzleGameWidget(int type, int stage, boolean advanced) {
    this.gesture = AbstractStandardGameWidget.StandardGesture.Rotate;

    record = new PuzzleGameRecord();
    record.load(type, stage, advanced);

    balls = Ball.intsToBalls(record.originalColorIndexes);

    standardInit();

    gameBoard = new SixtyOneGameBoard();
    // if (balls == null) {
    // balls = new Ball[gameBoard.totalBallCount()];
    // for (int i = 0; i < gameBoard.totalBallCount(); ++i)
    // balls[i] = new Ball(1);
    // }

    rule = new StandardGameRule();
    gameEffectAdapter = new GameEffectAdapter(new EffectPainter());
    coreController = new CoreController(this, rule, balls);
    ballFiller = null;

    connectionCalculator = null;
    gestureController = new RotateGestureController(this, rule);

    rule.setBallFiller(ballFiller);
    rule.setConnectionCalculator(connectionCalculator);
    rule.setCoreController(coreController);
    rule.setEffectPainter(gameEffectAdapter);
    rule.setGameBoard(gameBoard);
    rule.setGestureController(gestureController);
    rule.setSomeBooleans(true, false, false);

    puzzleHintButton = new RectButtonItem();
    puzzleHintButton.setWidth((int) (width() * HINT_BASE_RATE));
    puzzleHintButton.setHeight((int) (height() * HINT_BASE_RATE));
    puzzleHintButton.setLogicalPosition(new MyPoint((int) (width()
        * HINT_BASE_RATE / 2), (int) (height() * HINT_BASE_RATE / 2)));
    addItem(puzzleHintButton, AbstractSimpleWidget.ItemType.ButtonItem);

    currentMoveItem = new IntegerItem(280);
    currentMoveItem.setDescription("Current moved steps:");
    currentMoveItem.setNumber(0);
    currentMoveItem.setLogicalPosition(new MyPoint((int) (width() * 0.15),
        (int) (height() * 0.45)));
    addItem(currentMoveItem, AbstractSimpleWidget.ItemType.SimpleItem);

    leastMoveItem = new IntegerItem(280);
    leastMoveItem.setDescription(" Least moved steps: ");
    leastMoveItem.setNumber(0);
    leastMoveItem.setLogicalPosition(new MyPoint((int) (width() * 0.15),
        (int) (height() * 0.6)));
    addItem(leastMoveItem, AbstractSimpleWidget.ItemType.SimpleItem);

    resetButton.setLogicalPosition(new MyPoint((int) (width() * 0.15),
        (int) (height() * 0.8)));
    exitButton.setLogicalPosition(new MyPoint((int) (width() * 0.15),
        (int) (height() * 0.9)));

  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameInterface#eliminated(int)
   */
  @Override
  public void eliminated(int count) {
    // Not supposed to be called
  }

  @Override
  public void goodMove() {
    super.goodMove();
    currentMoveItem.setNumber(currentMoveItem.getNumber() + 1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameWidget.AbstractStandardGameWidget#reset()
   */
  @Override
  public void reset() {
    for (int i = 0; i < balls.length; ++i)
      balls[i] = Ball.intToBall(record.originalColorIndexes[i]);
    currentMoveItem.setNumber(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.GameWidget.AbstractStandardGameWidget#exit()
   */
  @Override
  public void exit() {
    mainWidget.changeControl(null, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragTo(int, java.awt.Point)
   */
  @Override
  public void dragTo(int indexOfTheDraggableItem, MyPoint position) {
    // Not supposed to be called
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Widgets.AbstractSimpleWidget#dragApplied(int,
   * java.awt.Point)
   */
  @Override
  public void dragApplied(int indexOfTheDraggableItem, MyPoint position) {
    // Not supposed to be called
  }

  @Override
  public void paint(MyGraphics g) {
    BasicPainter.paintBackGround(BasicPainter.Game61, g, width(), height(), 0);
    super.paint(g);
    BasicPainter.paintPuzzleGameBallHint(gameBoard, record.targetColorIndexes,
        g, HideHint, rate());
  }

  @Override
  public void advance() {
    super.advance();
    switch (animDirection) {
    case ShowHint:
      if (hintAnimCount < HINT_ANIM_MAX)
        ++hintAnimCount;
      break;
    case HideHint:
      if (hintAnimCount > 0)
        --hintAnimCount;
      else {
        resetMask.setVisible(false);
        resetMask.setEnabled(false);
        animDirection = Stay;
      }
      break;
    }
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    super.buttonClicked(indexOfTheButton);
    switch (indexOfTheButton) {
    case ResetMaskButton:
      if (animDirection == Stay || animDirection == ShowHint)
        animDirection = HideHint;
      break;
    case PuzzleHintButton:
      animDirection = ShowHint;
      resetMask.setVisible(true);
      resetMask.setEnabled(true);
      break;
    }
  }

  private double rate() {
    return HINT_BASE_RATE + (1 - HINT_BASE_RATE) * hintAnimCount
        / HINT_ANIM_MAX;
  }

}
