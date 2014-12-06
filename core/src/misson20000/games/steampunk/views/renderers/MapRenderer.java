package misson20000.games.steampunk.views.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import misson20000.games.steampunk.Resources;
import misson20000.games.steampunk.client.RenderStage;
import misson20000.games.steampunk.models.Entity;
import misson20000.games.steampunk.models.Map;
import misson20000.games.steampunk.models.Point;
import misson20000.games.steampunk.views.MapView;

/**
 * Created by misson20000 on 10/4/14.
 */
public class MapRenderer extends MapView implements Renderer {
  public Resources res;

  public Map model;
  private OrthogonalTiledMapRenderer tileRenderer;
  private float time;

  public MapRenderer(Map map, Resources resources) {
    super();
    this.res = resources;
    this.model = map;
    this.tileRenderer = null;
    this.time = 0;
  }

  @Override
  public void render(RenderStage stage) {
    switch(stage) {
      case LIGHT_MAP:
        res.gameBatch.begin();
        for(int i = 0; i < model.entities.size(); i++) {
          model.entities.get(i).renderer.render(stage);
        }
        res.gameBatch.end();
        break;
      case DIFFUSE:
        res.bgBatch.begin();
        for(int l = 3; l>0; l--) {
          for(int x = 0; x < Gdx.graphics.getWidth(); x += 128) {
            res.bgBatch.setColor(255 * ((float) (4 - l) / 3), 220 * ((float) (4 - l) / 3), 200 * ((float) (4 - l) / 3), 1); // ffdcb8
            res.bgBatch.draw(res.cloud, (int) (x - ((time * 200 / l) % 128)), (l - 1) * 30);
          }
        }
        res.bgBatch.end();
        this.tileRenderer.setView(res.camera);
        this.tileRenderer.render();
        res.gameBatch.begin();
        for(int i = 0; i < model.entities.size(); i++) {
          model.entities.get(i).renderer.render(stage);
        }
        res.gameBatch.end();
        MapProperties prop = model.tiles.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        int mapw = mapWidth * tilePixelWidth;
        int maph = mapHeight * tilePixelHeight;

        //res.shapes.begin(ShapeRenderer.ShapeType.Filled);
        ////res.shapes.setProjectionMatrix(res.camera.combined);
        //res.shapes.setColor(1, 0, 0, 1);
        //res.shapes.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-res.camera.position.y-(maph/2));
        //res.shapes.end();
        res.hudBatch.begin();
        res.hudBatch.setColor(1, 1, 1, 1);

        Vector3 coords = res.camera.unproject(new Vector3(Gdx.input.getX(),
                                                          Gdx.input.getY(), 0));
        res.font.draw(res.hudBatch, "X: " + coords.x + " Y: " + coords.y, 0, Gdx.graphics.getHeight());
        res.hudBatch.end();
    }
  }

  @Override
  public void view() {
    time+= Gdx.graphics.getDeltaTime();
    this.render(RenderStage.DIFFUSE); // for simple mode rendering
  }

  @Override
  public void addEntity(Entity e) {
    if(e.view == null) {
      e.view = e.renderer = new EntityRenderer(e, res);
    }
  }

  //The 'tmx' argument is for senders
  @Override
  public void loadTMX(TiledMap map, String tmx) {
    if(tileRenderer != null) {
      tileRenderer.dispose();
    }
    tileRenderer = new OrthogonalTiledMapRenderer(map, 1);
  }
}
