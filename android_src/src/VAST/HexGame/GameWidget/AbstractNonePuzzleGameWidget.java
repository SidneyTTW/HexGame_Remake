/**
 * 
 */
package VAST.HexGame.GameWidget;

import java.util.Vector;

import Aid.FileProcessor;
import Aid.MyGraphics;
import Aid.MyPoint;
import Aid.MyRectangle;
import Aid.SoundController;
import Aid.SourceManagement;
import VAST.HexGame.Effect.EffectPainter;
import VAST.HexGame.Game.Ball;
import VAST.HexGame.Game.BasicPainter;
import VAST.HexGame.Game.CoreController;
import VAST.HexGame.Game.GameEffectAdapter;
import VAST.HexGame.Game.RotateGestureController;
import VAST.HexGame.Game.RotateStandardConnectionCalculator;
import VAST.HexGame.Game.StandardGameRule;
import VAST.HexGame.Game.Statistics;
import VAST.HexGame.Game.SwapGestureController;
import VAST.HexGame.Game.SwapStandardConnectionCalculator;
import VAST.HexGame.Game.ThirtySevenGameBoard;
import VAST.HexGame.GameItem.AbstractBonusItem;
import VAST.HexGame.GameItem.FlameBonusItem;
import VAST.HexGame.GameItem.IntegerItem;
import VAST.HexGame.GameItem.StandardGameButtonItem;
import VAST.HexGame.GameItem.StarBonusItem;
import VAST.HexGame.GameItem.VerticalProgressBarItem;
import VAST.HexGame.Widgets.AbstractSimpleWidget;

/**
 * Abstract class of standard none puzzle game widgets.
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractNonePuzzleGameWidget extends
    AbstractStandardGameWidget {
  private static final int HintButton = AbstractStandardGameWidget.BUILT_IN_BUTTON_COUNT + 0;
  public static final int BUILT_IN_BUTTON_COUNT = AbstractStandardGameWidget.BUILT_IN_BUTTON_COUNT + 1;

  private static final int FlameItem = 0;
  private static final int StarItem = 1;
  public static final int BUILT_IN_BONUS_ITEM_COUNT = AbstractStandardGameWidget.BUILT_IN_BONUS_ITEM_COUNT + 2;

  protected AbstractBonusItem flameItem = null;
  protected AbstractBonusItem starItem = null;

  protected StandardGameButtonItem hintButton;

  protected VerticalProgressBarItem verticalBar;

  protected IntegerItem highestScoreItem;

  protected boolean rollBack;
  protected boolean autoRotate;
  protected boolean endlessFill;

  protected boolean preEnded = false;
  protected boolean ended = false;

  /**
   * A class to load or save game records.
   * 
   * @author SidneyTTW
   * 
   */
  protected class NonPuzzleGameRecord {
    static final int SwapClassic = 0;
    static final int RotateClassic = 1;
    static final int SwapEndless = 2;
    static final int RotateEndless = 3;
    static final int SwapTiming = 4;
    static final int RotateTiming = 5;

    Ball[] balls = null;
    int highestScore = 0;
    int currentLevel = 1;
    int levelMinScore = 0;
    int levelCurrentScore = 0;
    int levelMaxScore = 30;
    int flame = 0;
    int star = 0;

    public void load() {
      int type = getType();
      switch (type) {
      case SwapClassic:
      case RotateClassic:
      case SwapEndless:
      case RotateEndless:
        int[] colorIndexes = new int[37];
        int[] data = FileProcessor
            .readData(SourceManagement.NonPuzzleRecordFiles[type]);
        if (data == null)
          return;
        int i = 0;
        currentLevel = data[i++];
        levelMinScore = data[i++];
        levelCurrentScore = data[i++];
        levelMaxScore = data[i++];
        flame = data[i++];
        star = data[i++];
        for (int j = 0; j < 37; ++j)
          colorIndexes[j] = data[i++];
        balls = Ball.intsToBalls(colorIndexes);
        break;
      }
      highestScore = Statistics.getStatistic(type + 6);
    }

    public void save() {
      int type = getType();
      switch (type) {
      case SwapClassic:
      case RotateClassic:
      case SwapEndless:
      case RotateEndless:
        int[] colorIndexes;
        if (balls != null)
          colorIndexes = Ball.ballsToInts(balls);
        else {
          colorIndexes = new int[37];
          for (int i = 0; i < 37; ++i)
            colorIndexes[i] = -1;
        }
        int[] data = new int[37 + 6];
        int i = 0;
        data[i++] = currentLevel;
        data[i++] = levelMinScore;
        data[i++] = levelCurrentScore;
        data[i++] = levelMaxScore;
        data[i++] = flame;
        data[i++] = star;
        for (int j = 0; j < 37; ++j)
          data[i++] = colorIndexes != null ? colorIndexes[j] : -1;
        FileProcessor.writeDataArr(SourceManagement.NonPuzzleRecordFiles[type],
            data);
        break;
      }
      testHighest(levelCurrentScore);
    }

    public boolean testHighest(int currentScore) {
      int type = getType();
      int lastHighest = Statistics.getStatistic(type
          + Statistics.SwapClassicPoint);
      if (currentScore > lastHighest) {
        Statistics.changeStatistic(type + Statistics.SwapClassicPoint,
            currentScore);
        return true;
      }
      return false;
    }

    public void clear() {
      FileProcessor.remove(SourceManagement.NonPuzzleRecordFiles[getType()]);
    }
  }

  protected void standardInit() {
    super.standardInit();

    flameItem = new FlameBonusItem();
    starItem = new StarBonusItem();

    flameItem.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.4)));

    starItem.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.55)));

    addItem(flameItem, AbstractSimpleWidget.ItemType.SimpleItem);
    addItem(starItem, AbstractSimpleWidget.ItemType.SimpleItem);

    hintButton = new StandardGameButtonItem();
    hintButton.setText("Hint");
    hintButton.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.7)));
    addItem(hintButton, AbstractSimpleWidget.ItemType.ButtonItem);

    highestScoreItem = new IntegerItem(180);
    highestScoreItem.setDescription("Highest Score");
    highestScoreItem.setNumber(0);
    highestScoreItem.setLogicalPosition(new MyPoint((int) (width() * 0.1),
        (int) (height() * 0.1)));
    addItem(highestScoreItem, AbstractSimpleWidget.ItemType.SimpleItem);

    verticalBar = new VerticalProgressBarItem();
    verticalBar.setLogicalPosition(new MyPoint((int) (width() * 0.25),
        (int) (height() * 0.5)));
    addItem(verticalBar, AbstractSimpleWidget.ItemType.SimpleItem);

    gameBoard = new ThirtySevenGameBoard();
    if (balls == null) {
      balls = new Ball[gameBoard.totalBallCount()];
      for (int i = 0; i < gameBoard.totalBallCount(); ++i)
        balls[i] = null;
    }

    rule = new StandardGameRule();
    gameEffectAdapter = new GameEffectAdapter(new EffectPainter());
    coreController = new CoreController(this, rule, balls);

    switch (gesture) {
    case Swap:
      connectionCalculator = new SwapStandardConnectionCalculator();
      gestureController = new SwapGestureController(this, rule);
      break;
    case Rotate:
      connectionCalculator = new RotateStandardConnectionCalculator();
      gestureController = new RotateGestureController(this, rule);
      break;
    }

    rule.setConnectionCalculator(connectionCalculator);
    rule.setCoreController(coreController);
    rule.setEffectPainter(gameEffectAdapter);
    rule.setGameBoard(gameBoard);
    rule.setGestureController(gestureController);
    rule.setSomeBooleans(rollBack, autoRotate, endlessFill);
  }

  /**
   * Give hint to the user.
   */
  protected void hint() {
    if (preEnded)
      return;
    if (connectionCalculator == null)
      return;
    int hintIndex = connectionCalculator.hint(balls, gameBoard);
    MyPoint hintPos;
    int hintType;
    if (hintIndex >= 0) {
      hintPos = gameBoard.ballLogicalPositionOfIndex(hintIndex).clone();
      switch (gesture) {
      case Swap:
        hintPos.translate(0, -gameBoard.getBallRadius() * 2);
        hintType = SourceManagement.ArrowHint;
        break;
      case Rotate:
        hintType = SourceManagement.RotateHint;
        break;
      default:
        return;
      }
    } else {
      hintType = SourceManagement.ArrowHint;
      if (!flameItem.isEmpty()) {
        hintPos = flameItem.getLogicalPosition().clone();
        hintPos.translate(0, -flameItem.getRadius() * 2);
      } else if (!starItem.isEmpty()) {
        hintPos = starItem.getLogicalPosition().clone();
        hintPos.translate(0, -starItem.getRadius() * 2);
      } else {
        return;
      }
    }
    if (gameEffectAdapter != null)
      gameEffectAdapter.hintAt(hintPos, hintType);
  }

  @Override
  public void dragTo(int indexOfTheDraggableItem, MyPoint position) {
    if (preEnded)
      return;
    super.dragTo(indexOfTheDraggableItem, position);
    if (gameEffectAdapter != null)
      gameEffectAdapter.clearBonusEliminationHints();
    int index = gameBoard.ballIndexAtLogicalPosition(position);
    if (index < 0)
      return;
    Vector<Integer> indexesToEliminate = null;
    switch (indexOfTheDraggableItem) {
    case FlameItem:
      if (flameItem.isEmpty())
        return;
      indexesToEliminate = flameChain(index);
      break;
    case StarItem:
      if (starItem.isEmpty())
        return;
      indexesToEliminate = starChain(index);
      break;
    default:
      return;
    }
    if (gameEffectAdapter != null)
      for (int i = 0; i < indexesToEliminate.size(); ++i)
        if (indexesToEliminate.elementAt(i) >= 0)
          gameEffectAdapter.bonusEliminationHintAt(gameBoard,
              indexesToEliminate.elementAt(i));
  }

  @Override
  public void dragApplied(int indexOfTheDraggableItem, MyPoint position) {
    if (preEnded)
      return;
    super.dragApplied(indexOfTheDraggableItem, position);
    if (gameEffectAdapter != null)
      gameEffectAdapter.clearBonusEliminationHints();
    int index = gameBoard.ballIndexAtLogicalPosition(position);
    if (index < 0)
      return;
    switch (indexOfTheDraggableItem) {
    case FlameItem: {
      if (flameItem.isEmpty())
        return;
      flameItem.minusOne();
      if (gameEffectAdapter != null)
        gameEffectAdapter.flash(new MyRectangle(0, 0, width(), height()));
      // Add sound effect
      SoundController.addSound(SoundController.UseFlame);
      // Connect to statistic
      Statistics.addStatistic(Statistics.FlameUsedCount, 1);
      // Apply the bonus item
      Vector<Integer> indexesToEliminate = flameChain(index);
      coreController.eliminate(indexesToEliminate);
      break;
    }
    case StarItem: {
      if (starItem.isEmpty())
        return;
      starItem.minusOne();
      if (gameEffectAdapter != null)
        gameEffectAdapter.flash(new MyRectangle(0, 0, width(), height()));
      // Add sound effect
      SoundController.addSound(SoundController.UseStar);
      // Connect to statistic
      Statistics.addStatistic(Statistics.StarUsedCount, 1);
      Vector<Integer> indexesToEliminate = starChain(index);
      coreController.eliminate(indexesToEliminate);
      break;
    }
    }
  }

  protected Vector<Integer> flameChain(int index) {
    Vector<Integer> result = new Vector<Integer>(
        gameBoard.chainAroundIndex(index));
    result.add(index);
    return result;
  }

  protected Vector<Integer> starChain(int index) {
    Vector<Integer> result = new Vector<Integer>();
    result.add(index);
    for (int i = 0; i < 6; ++i) {
      int currentIndex = gameBoard.nearbyIndex(index, i);
      while (currentIndex >= 0) {
        result.add(currentIndex);
        currentIndex = gameBoard.nearbyIndex(currentIndex, i);
      }
    }
    return result;
  }

  @Override
  public void buttonClicked(int indexOfTheButton) {
    if (preEnded)
      return;
    super.buttonClicked(indexOfTheButton);
    switch (indexOfTheButton) {
    case HintButton:
      hint();
      break;
    }
  }

  @Override
  public void paint(MyGraphics g) {
    BasicPainter.paintBackGround(BasicPainter.Game37, g, width(), height(), 0);
    super.paint(g);
  }

  /**
   * @return Type index of the game.
   */
  public int getType() {
    return typeBase() + typeOffset();
  }

  /**
   * @return The base of the type.
   */
  public abstract int typeBase();

  /**
   * @return The offset of the type.
   */
  public int typeOffset() {
    switch (gesture) {
    case Swap:
      return 0;
    case Rotate:
      return 1;
    default:
      return 0;
    }
  }
}
