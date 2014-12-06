package misson20000.games.steampunk.controllers.ai;

import misson20000.games.steampunk.controllers.AIController;
import misson20000.games.steampunk.models.Entity;
import misson20000.games.steampunk.models.Script;
import misson20000.games.steampunk.util.FireAndForget;

/**
 * Created by misson20000 on 12/3/14.
 */
public class DummyAI extends AIController {
  public DummyAI(Entity target) {
    super(target);
  }

  @Override
  public void addEvent(String hook, Script.Event evt) {

  }

  @Override
  public boolean command(String cmd, Script.Event evt) {
    return false;
  }
}
