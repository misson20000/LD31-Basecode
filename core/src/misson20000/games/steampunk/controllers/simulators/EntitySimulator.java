package misson20000.games.steampunk.controllers.simulators;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import misson20000.games.steampunk.controllers.AIController;
import misson20000.games.steampunk.controllers.Controller;
import misson20000.games.steampunk.interfaces.Scriptable;
import misson20000.games.steampunk.models.*;
import misson20000.games.steampunk.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by misson20000 on 10/4/14.
 */
public class EntitySimulator extends Controller implements Simulator,Scriptable {
  public Player player;
  public Entity model;
  public float xv;
  public float yv;
  public Object blockD;
  public Object blockR;
  public Object blockL;
  public Object blockU;
  private AIController ai;

  //Scripting stuff
  private List<Pair<Integer, Script.Event>> rangeEnter = new ArrayList<Pair<Integer, Script.Event>>();
  private List<Pair<Integer, Script.Event>> rangeExit = new ArrayList<Pair<Integer, Script.Event>>();
  private int lastDist = -1; // actually dist squared

  public EntitySimulator(Entity e, Player p) {
    this.player = p;
    this.model = e;
    this.ai = e.type.getAI(e);
  }

  public int tx(int x) {
    return x/16;
  }

  public int ty(int y) {
    //return (model.map.pixHeight-y)/16;
    return y/16;
  }

  protected int getSlope(int l, int r, float a) {
    return (int) ((r-l)*a)+l;
  }

  protected int getWorldSlope(int x, int y) {
    Iterator<MapLayer> i = model.map.tiles.getLayers().iterator();
    for(; i.hasNext();) {
      MapLayer l1 = i.next();
      if(l1 instanceof TiledMapTileLayer) {
        TiledMapTileLayer l = (TiledMapTileLayer) l1;
        if(l.getCell(tx(x), ty(y)) != null && l.getCell(tx(x), ty(y)).getTile().getProperties().containsKey("slopeLeft")) {
          TiledMapTileLayer.Cell cell = l.getCell(tx(x), ty(y));
          return getSlope(Integer.parseInt(cell.getTile().getProperties().get("slopeLeft", String.class)),
                          Integer.parseInt(cell.getTile().getProperties().get("slopeRight", String.class)),
                          (float) (x - (Math.floor(x/16)*16)) / 16.0f);
        }
      }
    }
    return -1;
  }

  public boolean cellCollide(TiledMapTileLayer.Cell cell, int x, int y, int dir) {
    if(cell != null && !cell.getTile().getProperties().containsKey("nocollide")) {
      if(cell.getTile().getProperties().containsKey("slopeLeft") && dir == 0) {
        return y - (Math.floor(y/16)*16) < getSlope(Integer.parseInt(cell.getTile().getProperties().get("slopeLeft", String.class)),
                                                    Integer.parseInt(cell.getTile().getProperties().get("slopeRight", String.class)),
                                                    (float) (x - (Math.floor(x/16)*16)) / 16.0f);
      } else {
        return true;
      }
    }
    return false;
  }

  public void doCollision() {
    blockD = blockR = blockL = blockU = null;
    TiledMap m = model.map.tiles;
    for(int i = 0; i < m.getLayers().getCount(); i++) {
      if(!(m.getLayers()
            .get(i)
            .getProperties()
            .containsKey("nocollide"))) {
        if(m.getLayers().get(i) instanceof TiledMapTileLayer) {
          TiledMapTileLayer l = ((TiledMapTileLayer) m.getLayers()
                                                      .get(i));
          TiledMapTileLayer.Cell t;
          Rectangle bbox = model.type.bbox;
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x),
                                   ty(model.y + bbox.y - 1)),
                         model.x + bbox.x,
                         model.y + bbox.y - 1, 0)) { blockD = t; }
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x + bbox.w),
                                   ty(model.y + bbox.y - 1)),
                         model.x + bbox.x + bbox.w,
                         model.y + bbox.y - 1, 0)) { blockD = t; }
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x + bbox.w + 1),
                                   ty(model.y + bbox.y)),
                         model.x + bbox.x + bbox.w + 1,
                         model.y + bbox.y, 1)) { blockR = t; }
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x + bbox.w + 1),
                                   ty(model.y + bbox.y + bbox.h)),
                         model.x + bbox.x + bbox.w + 1,
                         model.y + bbox.y + bbox.h, 1)) { blockR = t; }
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x - 1),
                                   ty(model.y + bbox.y)),
                         model.x + bbox.x - 1,
                         model.y + bbox.y, 2)) { blockL = t; }
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x - 1),
                                   ty(model.y + bbox.y + bbox.h)),
                         model.x + bbox.x - 1,
                         model.y + bbox.y + bbox.h, 2)) { blockL = t; }
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x),
                                   ty(model.y + bbox.y + bbox.h + 1)),
                         model.x + bbox.x,
                         model.y + bbox.y + bbox.h + 1, 3)) { blockU = t; }
          if(cellCollide(t = l.getCell(tx(model.x + bbox.x + bbox.w),
                                   ty(model.y + bbox.y + bbox.h + 1)),
                         model.x + bbox.x + bbox.w,
                         model.y + bbox.y + bbox.h + 1, 3)) { blockU = t; }
        }
      }
    }
  }

  private boolean followSlope(Object block) {
    return false;
    /*if(getWorldSlope(model.x, model.y) >= 0) {
      model.y = (int) (Math.floor(model.y/16)*16) + getWorldSlope(model.x, model.y);
      return true;
    } else if(getWorldSlope(model.x, model.y - 1) >= 0) {
      model.y = (int) (Math.floor(model.y/16)*16) + getWorldSlope(model.x, model.y - 1);
      return true;
    }
    return false;*/
  }

  @Override
  public void update() {
    int distSq = (int) (Math.pow((model.x - player.x), 2) + Math.pow((model.y - player.y), 2));

    for(int i = 0; i < rangeEnter.size(); i++) {
      if(distSq <= rangeEnter.get(i).a && (lastDist > rangeEnter.get(i).a || lastDist < 0)) {
        rangeEnter.get(i).b.execute();
      }
    }
    for(int i = 0; i < rangeExit.size(); i++) {
      if(distSq > rangeExit.get(i).a && lastDist <= rangeExit.get(i).a && lastDist >= 0) {
        rangeExit.get(i).b.execute();
      }
    }

    this.lastDist = distSq;

    doCollision();

    ai.update(this);

    if(model.applyGravity) {
      //Apply Gravity
      yv -= 0.7;
    }

    //Apply Air Friction
    friction(0, 0, 0.5f);

    //Apply Ground Friction
    if(blockD != null) {
      if(blockD instanceof Entity) {
        Entity e = (Entity) blockD;
        //If this simulator actually exists, we're a server or singleplayer instance,
        //  so everything should have a simulator
        friction(e.simulator.xv, e.simulator.xv, 0.5f);
      } else {
        friction(0, 0, 0.5f);
      }
    }

    if(xv > 0) {
      for(int i = 0; i < xv; i++) {
        doCollision();
        if(blockR != null && !followSlope(blockR)) {
          xv = 0;
          break;
        }
        model.x++;
      }
    } else {
      for(int i = 0; i > xv; i--) {
        doCollision();
        if(blockL != null && !followSlope(blockL)) {
          xv = 0;
          break;
        }
        model.x--;
      }
    }
    if(yv > 0) {
      for(int i = 0; i < yv; i++) {
        doCollision();
        if(blockU != null) { yv*=-0.5; break; }
        model.y++;
      }
    } else {
      for(int i = 0; i > yv; i--) {
        doCollision();
        if(blockD != null) { yv = 0; break; }
        model.y--;
      }
    }
  }

  private void friction(float txv, float tyv, float f) {
    if(xv>txv) {
      if(xv>=txv+f) {
        xv-=f;
      } else {
        xv = txv;
      }
    } else if(xv<txv) {
      if(xv<=txv-f) {
        xv+= f;
      } else {
        xv = txv;
      }
    }
    if(yv>tyv) {
      if(yv>=tyv+f) {
        yv-=f;
      } else {
        yv = tyv;
      }
    } else if(yv<tyv) {
      if(yv<=tyv-f) {
        yv+= f;
      } else {
        yv = tyv;
      }
    }
  }

  @Override
  public void addEvent(String cmd, Script.Event evt) {
    String parts[] = cmd.split(" ");
    if(parts[0].equals("range")) {
      System.out.println("range " + parts[1] + parts[2]);
      if(parts[1].equals("enter")) {
        rangeEnter.add(new Pair(Integer.parseInt(parts[2]), evt));
      }
      if(parts[1].equals("exit")) {
        rangeExit.add(new Pair(Integer.parseInt(parts[2]), evt));
      }
    } else {
        ai.addEvent(cmd, evt);
    }
  }

  @Override
  public boolean command(String cmd, Script.Event evt) {
    String parts[] = cmd.split(" ");
    if(parts[0].equals("delete")) {
      model.map.entities.remove(model);
      return false;
    } else {
      return ai.command(cmd, evt);
    }
  }
}
