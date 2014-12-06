package misson20000.games.steampunk.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import misson20000.games.steampunk.Resources;
import misson20000.games.steampunk.client.RenderStage;
import misson20000.games.steampunk.controllers.simulators.MapSimulator;
import misson20000.games.steampunk.controllers.simulators.PlayerController;
import misson20000.games.steampunk.models.Map;
import misson20000.games.steampunk.models.Player;
import misson20000.games.steampunk.models.Point;
import misson20000.games.steampunk.util.FireAndForget;
import misson20000.games.steampunk.views.renderers.MapRenderer;

/**
 * Created by misson20000 on 10/3/14.
 */
public class GameScreen implements Screen {
  public Map map;
  public Resources res;
  public Player player;

  public Point scrollTarget;
  public Point scroll;

  public Rectangle deadzone;

  public GameScreen(Resources resources) {
    try {
      this.res = resources;
      res.camera = new OrthographicCamera();
      resize(Gdx.graphics.getWidth(),
             Gdx.graphics.getHeight());




      player = new Player();
      map = new Map(player);
      map.view = map.renderer = new MapRenderer(map, resources);
      map.controller = map.simulator = new MapSimulator(map);
      map.loadMap("level1.tmx", "level1.script");
      map.addEntity(player);
      player.controller = player.simulator = new PlayerController(player);

      this.scrollTarget = new Point(0, 0);
      this.scroll = new Point(50, 50);
      this.deadzone = new Rectangle(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3,
                                    Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
    } catch(Throwable t) {
      t.printStackTrace();
    }
  }

  /**
   * Called when the screen should render itself.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    /*if(Gdx.input.isKeyPressed(Input.Keys.W)) { res.camera.translate(0,  100*delta); }
    if(Gdx.input.isKeyPressed(Input.Keys.A)) { res.camera.translate(-100*delta, 0); }
    if(Gdx.input.isKeyPressed(Input.Keys.S)) { res.camera.translate(0, -100*delta); }
    if(Gdx.input.isKeyPressed(Input.Keys.D)) { res.camera.translate( 100*delta, 0); }*/

    updateScrollTarget();
    updateScroll();
    res.camera.update();

    res.gameBatch.setProjectionMatrix(res.camera.combined);
    //GameScreens should only exist client side, so it's okay to acess the renderer
    map.renderer.render(RenderStage.DIFFUSE);

    map.simulator.update();
  }

  protected void updateScrollTarget() {
    int edge = player.x + (int) deadzone.x;
    if(scrollTarget.x < edge) {
      scrollTarget.x = edge;
    }
    edge = player.x + (int) deadzone.x - (int) deadzone.width;
    if(scrollTarget.x > edge) {
      scrollTarget.x = edge;
    }
    scrollTarget.y = player.y;
    /*edge = player.y - (int) deadzone.y;
    if(scrollTarget.y > edge) {
      scrollTarget.y = edge;
    }
    edge = player.y + player.height - (int) deadzone.y - (int) deadzone.height;
    if(scrollTarget.y < edge) {
      scrollTarget.y = edge;
    }*/
  }

  protected void updateScroll() {
    scroll.x+= (scrollTarget.x/* + (player.facing ? -40 : 40)*/ - scroll.x) * 0.1;
    scroll.y+= (scrollTarget.y + (Gdx.input.isKeyPressed(Input.Keys.UP) ? 80 : (Gdx.input.isKeyPressed(Input.Keys.DOWN) ? -80 : 0)) - scroll.y) * 0.1;

    res.camera.position.x = scroll.x;
    res.camera.position.y = scroll.y;
  }

  /**
   * @param width
   * @param height
   * @see ApplicationListener#resize(int, int)
   */
  @Override
  public void resize(int width, int height) {
    res.camera.setToOrtho(false, width, height);
  }

  /**
   * Called when this screen becomes the current screen for a {@link Game}.
   */
  @Override
  public void show() {

  }

  /**
   * Called when this screen is no longer the current screen for a {@link Game}.
   */
  @Override
  public void hide() {

  }

  /**
   * @see ApplicationListener#pause()
   */
  @Override
  public void pause() {

  }

  /**
   * @see ApplicationListener#resume()
   */
  @Override
  public void resume() {

  }

  /**
   * Called when this screen should release all resources.
   */
  @Override
  public void dispose() {

  }
}
