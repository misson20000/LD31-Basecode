package misson20000.games.steampunk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import misson20000.games.steampunk.controllers.AIController;
import misson20000.games.steampunk.controllers.ai.DummyAI;
import misson20000.games.steampunk.controllers.ai.GenericNPCAI;
import misson20000.games.steampunk.controllers.ai.KnightAI;
import misson20000.games.steampunk.screens.TitleMenuScreen;
import misson20000.games.steampunk.util.FireAndForget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LD48Game extends Game {
  public Resources resources;

  @Override
  public void create() {
    try {
      resources = Resources.instance;
      resources.gameBatch = new SpriteBatch();
      resources.bgBatch = new SpriteBatch();
      resources.hudBatch = new SpriteBatch();
      resources.font = new BitmapFont();
      resources.cloud = new Texture(Gdx.files.internal("cloud.png"));
      resources.shapes = new ShapeRenderer();
      resources.entityTypes = new HashMap<String, EntityType>();
      resources.ai = new HashMap<String, Class<? extends AIController>>();

      // register ai functions
      resources.ai.put("dummy", DummyAI.class);
      resources.ai.put("generic-npc", GenericNPCAI.class);
      resources.ai.put("knight", KnightAI.class);

      resources.game = this;
      EntityType.loadTypes(resources.entityTypes, "etypes.json");
      Sprite.loadSprites(resources.sprites, "sprites.json");
      this.setScreen(new TitleMenuScreen(resources));
    } catch(Throwable t) {
      t.printStackTrace();
    }
  }
  @Override
  public void render() {
    super.render();
    FireAndForget.update();
  }

  @Override
  public void dispose() {
    resources.gameBatch.dispose();
    resources.bgBatch.dispose();
    resources.font.dispose();
    resources.cloud.dispose();
    resources.shapes.dispose();
    Iterator<Map.Entry<String, Texture>> i = resources.textures.entrySet().iterator();
    while(i.hasNext()) {
      i.next().getValue().dispose();
    }
  }
}
