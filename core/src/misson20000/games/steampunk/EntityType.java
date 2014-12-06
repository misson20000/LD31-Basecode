package misson20000.games.steampunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import misson20000.games.steampunk.controllers.AIController;
import misson20000.games.steampunk.models.Entity;
import misson20000.games.steampunk.models.Rectangle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by misson20000 on 12/1/14.
 */
public class EntityType {
  public int id;
  public String name;
  public String sprite;
  public String aiType;
  public Rectangle bbox;

  private static EntityType loadType(JSONObject typ) {
    EntityType etyp = new EntityType();
    etyp.name = (String) typ.get("name");
    etyp.sprite = (String) typ.get("sprite");
    etyp.aiType = (String) typ.get("aiType");
    JSONObject jbox = (JSONObject) typ.get("bbox");
    etyp.bbox = new Rectangle(((Long) jbox.get("x")).intValue(),
                              ((Long) jbox.get("y")).intValue(),
                              ((Long) jbox.get("w")).intValue(),
                              ((Long) jbox.get("h")).intValue());
    return etyp;
  }

  public static void loadTypes(Map<String, EntityType> map, String fname) throws IOException {
    FileHandle h = Gdx.files.internal(fname);
    JSONArray types = (JSONArray) JSONValue.parse(new FileReader(h.file()));
    if(types != null) {
      for(int i = 0; i < types.size(); i++) {
        EntityType typ = loadType((JSONObject) types.get(i));
        typ.id = i;
        map.put(typ.name, typ);
      }
    }
  }

  public Sprite getSprite() {
    return Sprite.getSprite(sprite);
  }

  public AIController getAI(Entity e) {
    try {
      if(Resources.instance.ai.containsKey(aiType)) {
        return Resources.instance.ai.get(aiType)
                                    .getConstructor(Entity.class)
                                    .newInstance(e);
      } else {
        return null;
      }
    } catch(Exception ex) {}
    return null;
  }
}
