package misson20000.games.steampunk.controllers.ai;

import misson20000.games.steampunk.client.Animation;
import misson20000.games.steampunk.controllers.simulators.EntitySimulator;
import misson20000.games.steampunk.models.Entity;
import misson20000.games.steampunk.models.Script;

/**
 * Created by misson20000 on 12/3/14.
 */
public class GenericNPCAI extends DummyAI {
  protected int walkTarget;
  protected boolean walkDir;
  protected boolean walking;
  protected Script.Event walkEvent;
  protected Script.Event jumpEvent;

  protected boolean jumping;

  public GenericNPCAI(Entity target) {
    super(target);
  }

  @Override
  public void update(EntitySimulator sim) {
    if(walking) {
      if(walkDir) {
        sim.xv = 2;
        if(target.x >= walkTarget) {
          walking = false;
          walkEvent.pause = false;
          target.anim = Animation.IDLE;
        }
      } else {
        sim.xv = -2;
        if(target.x <= walkTarget) {
          walking = false;
          walkEvent.pause = false;
          target.anim = Animation.IDLE;
        }
      }
    }
    if(jumping && sim.blockD != null && sim.yv <= 0) {
      jumping = false;
      jumpEvent.pause = false;
    }
  }

  public void walkToX(int x) {
    walking = true;
    walkTarget = x;
    walkDir = target.x > x;
  }

  @Override
  public boolean command(String cmd, Script.Event evt) {
    String parts[] = cmd.split(" ");
    if(parts[0].equals("jump")) {
      target.simulator.yv = 10;
      this.jumping = true;
      this.jumpEvent = evt;
      evt.pause = true;
      return true;
    }
    if(parts[0].equals("walk")) {
      walkToX(Integer.parseInt(parts[1]));
      evt.pause = true;
      walkEvent = evt;
      walkDir = walkTarget > target.x;
      target.facing = !walkDir;
      target.anim = Animation.WALK;
      return true;
    }
    return false;
  }
}
