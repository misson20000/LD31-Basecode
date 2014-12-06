package misson20000.games.steampunk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import misson20000.games.steampunk.controllers.AIController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by misson20000 on 10/4/14.
 */
public class Resources {
  public static final Resources instance = new Resources();
  //Client side resources for rendering

  public Texture getTexture(String path) {
    if(!textures.containsKey(path)) {
      Texture tex;
      textures.put(path, tex = new Texture(Gdx.files.internal(path)));
      return tex;
    } else {
      return textures.get(path);
    }
  }
  public Map<String, Texture> textures = new HashMap<String, Texture>();
  public Map<String, Sprite> sprites = new HashMap<String, Sprite>();
  public Map<String, EntityType> entityTypes;
  public Map<String, Class<? extends AIController>> ai;

  public SpriteBatch gameBatch;
  public SpriteBatch bgBatch;
  public SpriteBatch hudBatch;

  public BitmapFont font;
  public Game game;
  public OrthographicCamera camera;
  public Texture cloud;
  public ShapeRenderer shapes;
}
