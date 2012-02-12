/**
 * 
 */
package VAST.HexGame.Game;

import java.util.Vector;

/**
 * Class of standard connection.
 * 
 * @author SidneyTTW
 * 
 */
public class StandardConnection implements ConnectionInterface {
  /**
   * The connections of each index.
   */
  Vector[][] connectionsOfIndex = null;

  /**
   * All the connections.
   */
  Vector<Vector<Integer>> connections = new Vector<Vector<Integer>>();

  /**
   * Constructor.
   * 
   * @param ballNumber
   *          The number of the balls.
   */
  public StandardConnection(int ballNumber) {
    connectionsOfIndex = new Vector[ballNumber][ConnectionInterface.ConnectionPositionsCount];
    for (int i = 0; i < ballNumber; ++i) {
      for (int j = 0; j < ConnectionInterface.ConnectionPositionsCount; ++j)
        connectionsOfIndex[i][j] = null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.ConnectionInterface#relatedChains(int)
   */
  @Override
  public Vector<Integer>[] relatedChains(int index) {
    if (index >= 0 && index < connectionsOfIndex.length)
      return connectionsOfIndex[index];
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.ConnectionInterface#isInAChain(int)
   */
  @Override
  public boolean isInAChain(int index) {
    for (int i = 0;i < 10;++i)
      if (i != 3 && connectionsOfIndex[index][i] != null)
        return true;
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.ConnectionInterface#allChains()
   */
  @Override
  public Vector<Vector<Integer>> allChains() {
    return connections;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.ConnectionInterface#addChain(int, java.util.Vector)
   */
  @Override
  public void addChain(int index, int direction, Vector<Integer> chain) {
    if (index >= 0 && index < connectionsOfIndex.length && direction >= 0
        && direction < ConnectionInterface.ConnectionPositionsCount) {
      switch (direction) {
      case ConnectionInterface.ToLeftDown:
      case ConnectionInterface.ToRightDown:
      case ConnectionInterface.ToRight:
        if (chain.isEmpty() || index != chain.elementAt(0)) {
          return;
        }
        connections.add(chain);
        for (int i = 0;i < chain.size();++i)
          connectionsOfIndex[chain.elementAt(i)][direction] = chain;
        break;
      case ConnectionInterface.IsCenter:
        connections.add(chain);
        for (int i = 0;i < chain.size();++i)
          connectionsOfIndex[chain.elementAt(i)][ConnectionInterface.IsLeftDown + i] = chain;
      }
    }
  }
}
