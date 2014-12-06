package misson20000.games.steampunk.controllers;

import misson20000.games.steampunk.controllers.simulators.EntitySimulator;
import misson20000.games.steampunk.interfaces.Scriptable;
import misson20000.games.steampunk.models.Entity;

/**
 * Created by misson20000 on 12/3/14.
 */
public abstract class AIController implements Scriptable {
  protected Entity target;
  public AIController(Entity target) {
    this.target = target;
  }

  public void update(EntitySimulator sim) {

  }
}
