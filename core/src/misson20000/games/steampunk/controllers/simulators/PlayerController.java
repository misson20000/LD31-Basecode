package misson20000.games.steampunk.controllers.simulators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import misson20000.games.steampunk.client.Animation;
import misson20000.games.steampunk.models.Player;

/**
 * Created by misson20000 on 10/4/14.
 */
public class PlayerController extends EntitySimulator {
  public Player model;
  public float jtime;

  public PlayerController(Player player) {
    super(player, player);
    this.model = player;
    jtime = 1.5f;
  }

  @Override
  public void update() {
    super.update();
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)
            || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      model.anim = Animation.WALK;
    } else {
      model.anim = Animation.IDLE;
    }
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && xv < 2) {
      xv+= 1.5;
      model.facing = false;
    }
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)  && xv > -2) {
      xv-= 1.5;
      model.facing = true;
    }

    if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && blockD != null && jtime >= 0.2f) {
      jtime = 0.0f;
    }
    if(jtime < 0.2f) {
      if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
        yv = 6.0f;
        jtime += Gdx.graphics.getDeltaTime();
      } else {
        jtime = 1.5f;
      }
    }
  }
}
