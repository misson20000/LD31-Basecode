package misson20000.games.steampunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by misson20000 on 10/4/14.
 */
public class Sprite {
  public Sprite(TextureRegion... frames) {
    this.frames = frames;
  }
  public Sprite(Texture tex, int size) {
    TextureRegion regions[][] = new TextureRegion(tex).split(size, size);
    frames = new TextureRegion[regions.length*regions[0].length];
    for(int y = 0; y < regions.length; y++) {
      for(int x = 0; x < regions[y].length; x++) {
        frames[(y*regions.length)+x] = regions[y][x];
      }
    }
  }

  public static Sprite getSprite(String s) {
    return Resources.instance.sprites.get(s);
  }

  private static Sprite loadSprite(JSONObject o) {
    Sprite s = new Sprite(Resources.instance.getTexture((String) o.get("path")), ((Long) o.get("size")).intValue());
    s.name = (String) o.get("name");
    System.out.println("Loaded sprite name: " + s.name + " path: " + (String) o.get("path"));
    return s;
  }

  public static void loadSprites(Map<String, Sprite> map, String fname) throws IOException {
    FileHandle h = Gdx.files.internal(fname);
    JSONArray types = (JSONArray) JSONValue.parse(new FileReader(h.file()));
    if(types != null) {
      for(int i = 0; i < types.size(); i++) {
        Sprite typ = loadSprite((JSONObject) types.get(i));
        map.put(typ.name, typ);
      }
    }
  }


  public Sprite(String str, int size) {
    this(Resources.instance.getTexture(str), size);
  }
  public TextureRegion frames[];
  public String name;
}
