package misson20000.games.steampunk.views;

import com.badlogic.gdx.maps.tiled.TiledMap;
import misson20000.games.steampunk.models.Entity;

/**
 * Created by misson20000 on 10/4/14.
 */
public abstract class MapView extends View {
  public abstract void addEntity(Entity e);
  public abstract void loadTMX(TiledMap tiles, String tmx);
}
