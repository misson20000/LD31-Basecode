package misson20000.games.steampunk.models;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.*;
import misson20000.games.steampunk.Resources;
import misson20000.games.steampunk.controllers.MapController;
import misson20000.games.steampunk.controllers.simulators.MapSimulator;
import misson20000.games.steampunk.interfaces.Scriptable;
import misson20000.games.steampunk.util.Tokenizer;
import misson20000.games.steampunk.views.MapView;
import misson20000.games.steampunk.views.renderers.MapRenderer;
import misson20000.games.steampunk.views.renderers.TextEntityRenderer;

import javax.annotation.Resource;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.List;

/**
 * Created by misson20000 on 10/4/14.
 */
public class Map extends Model implements Scriptable {
  public int pixWidth;
  public int pixHeight;

  public Player player;
  public java.util.Map<String, Entity> idMap;

  public Map(Player player) {
    this.idMap = new HashMap<String, Entity>();
    this.entities = new ArrayList<Entity>();
    this.player = player;
  }

  public List<Entity> entities;

  public MapView       view;
  public MapSimulator  simulator;
  public MapRenderer   renderer;
  //public MapSender     sender;
  public MapController controller;

  public TiledMap     tiles;

  public Script script;

  public void addEntity(Entity e) {
    entities.add(e);
    view.addEntity(e);
    controller.addEntity(e);
    this.idMap.put(e.id, e);
    e.map = this;
  }

  public void loadMap(String tmx, String script) throws IOException {
    tiles = new TmxMapLoader().load(tmx);
    view.loadTMX(tiles, tmx);
    MapProperties prop = tiles.getProperties();
    int mapWidth = prop.get("width", Integer.class);
    int mapHeight = prop.get("height", Integer.class);
    int tilePixelWidth = prop.get("tilewidth", Integer.class);
    int tilePixelHeight = prop.get("tileheight", Integer.class);
    pixWidth = mapWidth * tilePixelWidth;
    pixHeight = mapHeight * tilePixelHeight;
    for(Iterator<MapLayer> iter = tiles.getLayers().iterator(); iter.hasNext();) {
      for(Iterator<MapObject> jter = iter.next().getObjects().iterator(); jter.hasNext();) {
        MapObject o = jter.next();
        if(o instanceof RectangleMapObject) {
          com.badlogic.gdx.math.Rectangle r = ((RectangleMapObject) o).getRectangle();
          Entity e = new Entity();
          e.type = Resources.instance.entityTypes.get(o.getProperties()
                                                       .get("type", String.class));
          //e.x = Integer.parseInt(o.getProperties().get("x", String.class)) * tilePixelWidth;
          //e.y = Integer.parseInt(o.getProperties().get("y", String.class)) * tilePixelHeight;
          e.x = (int) r.getX();
          e.y = (int) r.getY();
          e.properties = o.getProperties();
          e.id = o.getProperties()
                  .get("id", String.class);
          addEntity(e);
        }
      }
    }

    Reader r = new FileReader(Gdx.files.internal(script).file());
    Tokenizer t = new Tokenizer(r, true);
    this.script = new Script(t, this);
  }

  @Override
  public void addEvent(String hook, Script.Event evt) {
    if(hook.equals("onload")) {
      evt.execute();
    }
  }

  @Override
  public boolean command(String cmd, Script.Event evt) {
    String[] parts = cmd.split(" ");
    if(parts[0].equals("text")) {
      Entity txt = new Entity();
      txt.type = Resources.instance.entityTypes.get("text");
      txt.id = parts[1];
      txt.x = Integer.parseInt(parts[2]);
      txt.y = Integer.parseInt(parts[3]);
      StringBuilder sb = new StringBuilder();
      for(int i = 4; i < parts.length; i++) {
        sb.append(parts[i] + " ");
      }
      txt.applyGravity = false;
      txt.view = txt.renderer = new TextEntityRenderer(txt, Resources.instance).setText(sb.toString());
      addEntity(txt);
    }
    return false;
  }
}
