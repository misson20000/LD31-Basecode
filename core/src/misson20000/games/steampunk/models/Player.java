package misson20000.games.steampunk.models;
import misson20000.games.steampunk.Resources;

import java.awt.*;

/**
 * Created by misson20000 on 10/4/14.
 */
public class Player extends Entity {
  public Player() {
    super();
    this.type = Resources.instance.entityTypes.get("player");
    this.x = 860;
    this.y = 660;
    this.width = 32;
    this.height = 32;
  }
}
