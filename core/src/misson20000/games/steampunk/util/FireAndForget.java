package misson20000.games.steampunk.util;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by misson20000 on 12/3/14.
 */
public class FireAndForget {
  private static List<Forgettable> tasks = new ArrayList<Forgettable>();

  public static void timer(final int ms, final Runnable run) {
    tasks.add(new Forgettable() {
      private long timer = ms;
      @Override
      public boolean update() {
        timer-= Gdx.graphics.getDeltaTime()*1000.0;
        if(timer <= 0) {
          run.run();
          return true;
        }
        return false;
      }
    });
  }

  public static void fire(Forgettable f) {
    tasks.add(f);
  }

  public static void update() {
    Iterator<Forgettable> i = tasks.iterator();
    while(i.hasNext()) {
      Forgettable f = i.next();
      if(f.update()) {
        i.remove();
      }
    }
  }

  public abstract static class Forgettable {
    // returns true when it can be removed from list
    public abstract boolean update();
  }
}
