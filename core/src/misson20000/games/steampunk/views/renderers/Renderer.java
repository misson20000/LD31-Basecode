package misson20000.games.steampunk.views.renderers;

import misson20000.games.steampunk.Resources;
import misson20000.games.steampunk.client.RenderStage;

/**
 * Created by misson20000 on 10/4/14.
 */
public interface Renderer {
  public Resources resources = null;
  public void render(RenderStage stage);
}
