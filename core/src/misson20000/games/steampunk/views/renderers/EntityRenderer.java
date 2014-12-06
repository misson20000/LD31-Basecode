package misson20000.games.steampunk.views.renderers;

import com.badlogic.gdx.Gdx;
import misson20000.games.steampunk.Resources;
import misson20000.games.steampunk.client.RenderStage;
import misson20000.games.steampunk.Sprite;
import misson20000.games.steampunk.models.Entity;
import misson20000.games.steampunk.views.View;

/**
 * Created by misson20000 on 10/4/14.
 */
public class EntityRenderer extends View implements Renderer {
  public Resources res;
  public Entity model;
  public Sprite sprite;
  public int frame;
  public float timer;

  public EntityRenderer(Entity e, Resources resources) {
    this.res = resources;
    this.model = e;
    this.sprite = e.type.getSprite();
    this.frame = 0;
    this.timer = 0.1f;
  }

  @Override
  public void render(RenderStage stage) {
    switch(stage) {
      case LIGHT_MAP:

        break;
      case DIFFUSE:
        if(this.frame >= model.anim.getFrameCount()
                || this.timer > 1.0f / model.anim.getFps()) {
          this.frame = 0;
          this.timer = 1.0f / model.anim.getFps();
        }
        int fr = model.anim.getFrame(frame);
        if(model.facing) {
          res.gameBatch.draw(sprite.frames[fr], model.x+sprite.frames[fr].getRegionWidth(), model.y,
                             -sprite.frames[fr].getRegionWidth(),
                             sprite.frames[fr].getRegionHeight());
        } else {
          res.gameBatch.draw(sprite.frames[fr], model.x, model.y);
        }
        this.timer-= Gdx.graphics.getDeltaTime();
        if(model.anim != null) {
          if(this.timer <= 0.0f) {
            this.timer = 1.0f / model.anim.getFps();
            this.frame++;
          }
        } else {
          this.frame = 0;
        }
        break;
    }
  }

  @Override
  public void view() {
    render(RenderStage.DIFFUSE); // for simple mode
  }
}
