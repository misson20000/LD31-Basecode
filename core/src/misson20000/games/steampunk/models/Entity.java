package misson20000.games.steampunk.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import misson20000.games.steampunk.EntityType;
import misson20000.games.steampunk.client.Animation;
import misson20000.games.steampunk.controllers.simulators.EntitySimulator;
import misson20000.games.steampunk.interfaces.Scriptable;
import misson20000.games.steampunk.views.renderers.EntityRenderer;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by misson20000 on 10/3/14.
 */
public class Entity extends Model implements Scriptable {
  public int x;
  public int y;
  public String id;
  public Map map;

  public EntitySimulator simulator; //null if it has a receiver
  public EntityRenderer  renderer;  //null if it has a sender
  public EntityType type;
  public int width;
  public int height;

  public boolean facing;
  public Animation anim;

  public MapProperties properties;
  public boolean applyGravity = true;
  public Entity() {
    anim = Animation.IDLE;
  }


  @Override
  public void addEvent(String hook, Script.Event evt) {
    String parts[] = hook.split(" ");
    this.simulator.addEvent(hook, evt);
  }
  @Override
  public boolean command(String cmd, Script.Event evt) {
    return this.simulator.command(cmd, evt);
  }
}
