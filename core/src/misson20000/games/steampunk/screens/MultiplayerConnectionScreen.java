package misson20000.games.steampunk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import misson20000.games.steampunk.Resources;

/**
 * Created by misson20000 on 10/6/14.
 */
public class MultiplayerConnectionScreen implements Screen {
  private TextField field;
  private Resources res;
  private Texture quagmire_cords;
  private float connTimer;

  public MultiplayerConnectionScreen(Resources res) {
    quagmire_cords = new Texture(Gdx.files.internal("quagmire-cords.png"));
    this.res = res;
    Stage stage = new Stage();
    Gdx.input.setInputProcessor(stage);
    this.field = new TextField("localhost:4906", new TextField.TextFieldStyle(res.font, new Color(1, 1, 1, 1), null, null, null));
    stage.addActor(field);
    stage.setKeyboardFocus(field);
    field.setSelection(0, field.getText().length());
    resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    connTimer = 0.0f;
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

    res.gameBatch.begin(); {
      res.gameBatch.draw(quagmire_cords, Gdx.graphics.getWidth()/3, 0, Gdx.graphics.getWidth()/3,
                       ((Gdx.graphics.getWidth()/3)*quagmire_cords.getHeight())/quagmire_cords.getWidth());
      res.font.draw(res.gameBatch, "Server Address:Port", Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2/3);
      field.draw(res.gameBatch, 1.0f);
      res.gameBatch.end(); }

    res.shapes.begin(ShapeRenderer.ShapeType.Line); {
      res.shapes.rect(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()*2/3,
                      Gdx.graphics.getWidth()*3/5, 30);
    res.shapes.end(); }
    /*res.shapes.begin(ShapeRenderer.ShapeType.Filled); {
      res.shapes.setColor(1, 1, 1, connTimer);
      res.shapes.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      res.shapes.setColor(1, 1, 1, 1);
    res.shapes.end(); }*/


    if(connTimer > 0 || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      connTimer+= delta;
    }
    if(connTimer >= 0.5) {
      connect();
    }
  }

  private void connect() {

  }

  /**
   * @param width
   * @param height
   * @see ApplicationListener#resize(int, int)
   */
  @Override
  public void resize(int width, int height) {
    field.setX(Gdx.graphics.getWidth()/5+10); field.setY(Gdx.graphics.getHeight()*2/3);
    field.setWidth(Gdx.graphics.getWidth()*3/5);
    field.setHeight(30);
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
