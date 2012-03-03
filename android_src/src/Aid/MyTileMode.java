package Aid;

import android.graphics.Shader;

public class MyTileMode {
  Shader.TileMode mode;

  public static final MyTileMode NO_CYCLE = new MyTileMode(
      Shader.TileMode.CLAMP);

  public static final MyTileMode REFLECT = new MyTileMode(
      Shader.TileMode.MIRROR);

  public MyTileMode(Shader.TileMode mode) {
    this.mode = mode;
  }
}
