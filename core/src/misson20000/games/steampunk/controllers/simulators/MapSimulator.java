package misson20000.games.steampunk.controllers.simulators;

import misson20000.games.steampunk.controllers.Controller;
import misson20000.games.steampunk.controllers.MapController;
import misson20000.games.steampunk.models.Entity;
import misson20000.games.steampunk.models.Map;
import misson20000.games.steampunk.views.renderers.EntityRenderer;

/**
 * Created by misson20000 on 10/4/14.
 */
public class MapSimulator extends MapController implements Simulator {
  public Map model;

  public MapSimulator(Map map) {
    super();
    this.model = map;
    /*Entity e = new Entity();
    e.x = e.y = 100;
    e.controller = e.simulator = new EntitySimulator(e);
    map.addEntity(e);*/
  }

  @Override
  public void update() {
    for(int i = 0; i < model.entities.size(); i++) {
      model.entities.get(i).simulator.update();
    }
    model.script.update();
  }

  @Override
  public void addEntity(Entity e) {
    e.controller = e.simulator = new EntitySimulator(e, model.player);
  }
}
