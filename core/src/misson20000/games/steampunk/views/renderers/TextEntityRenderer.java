package misson20000.games.steampunk.views.renderers;

import misson20000.games.steampunk.Resources;
import misson20000.games.steampunk.client.RenderStage;
import misson20000.games.steampunk.models.Entity;

import javax.xml.soap.Text;

/**
 * Created by misson20000 on 12/4/14.
 */
public class TextEntityRenderer extends EntityRenderer {
  protected String txt;

  public TextEntityRenderer(Entity e, Resources r) {
    super(e, r);
  }

  public TextEntityRenderer setText(String txt) {
    this.txt = txt;
    return this;
  }

  @Override
  public void render(RenderStage r) {
    switch(r) {
      case LIGHT_MAP: break;
      case DIFFUSE:
        res.font.draw(res.gameBatch, txt, model.x, model.y);
        break;
    }
  }
}
