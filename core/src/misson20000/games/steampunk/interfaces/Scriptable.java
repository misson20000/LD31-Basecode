package misson20000.games.steampunk.interfaces;

import misson20000.games.steampunk.models.Script;

/**
 * Created by misson20000 on 12/3/14.
 */
public interface Scriptable {
  public void addEvent(String hook, Script.Event evt);
  public boolean command(String cmd, Script.Event evt);
}
