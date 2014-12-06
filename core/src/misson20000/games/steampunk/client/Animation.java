package misson20000.games.steampunk.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by misson20000 on 11/14/14.
 */
public enum Animation {
  WALK("anims/walk.anim"), IDLE("anims/idle.anim");

  private int[] frames;
  private int fps;

  private Animation(String path) {
    try {
      FileHandle file = Gdx.files.internal(path);
      BufferedReader read = file.reader(256);
      String fpsLine = read.readLine();
      String[] parts = fpsLine.split("\\s");
      this.fps = Integer.parseInt(parts[0]);
      String framesLine = read.readLine();
      String[] frames = framesLine.split(",\\s*");
      this.frames = new int[frames.length];
      for(int i = 0; i < frames.length; i++) {
        this.frames[i] = Integer.parseInt(frames[i]);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public int getFrame(int i) {
    return frames[i];
  }

  public int getFps() {
    return fps;
  }

  public int getFrameCount() {
    return frames.length;
  }
}
