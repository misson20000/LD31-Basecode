package misson20000.games.steampunk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import misson20000.games.steampunk.Resources;

/**
 * Created by misson20000 on 10/2/14.
 */
public class TitleMenuScreen implements Screen {
  private Resources res;
  private double time;
  private int selected;

  public TitleMenuScreen(Resources resources) {
      this.res = resources;
      this.time = 0;
  }

  @Override
  public void dispose() {
  }

  @Override
  public void render(float delta) {
    this.time+= delta;

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    res.gameBatch.begin();
    for(int l = 3; l>0; l--) {
      for(int x = 0; x < Gdx.graphics.getWidth(); x += 128) {
        res.gameBatch.setColor(255*((float)(4-l)/3), 220*((float)(4-l)/3), 200*((float)(4-l)/3), 1); // ffdcb8
        res.gameBatch.draw(res.cloud, (int) (x - ((time * 200/l) % 128)), (l-1)*30);
      }
    }
    //game.font.setScale(5);
    res.gameBatch.setColor(1, 1, 1, 1);
    res.font.draw(res.gameBatch, "Singleplayer", Gdx.graphics.getWidth()/2 -res.font.getBounds("Singleplayer").width/2,
                                     Gdx.graphics.getHeight()/2-res.font.getBounds("Singleplayer").height/2);
    res.font.draw(res.gameBatch, "Multiplayer", Gdx.graphics.getWidth()/2 -res.font.getBounds("Multiplayer").width/2,
                                     Gdx.graphics.getHeight()/2-res.font.getBounds("Multiplayer").height/2-20);
    res.gameBatch.end();

    if(selected == 0) {
      res.shapes.begin(ShapeRenderer.ShapeType.Line);
      res.shapes.setColor(1, 1, 1, 1);
      res.shapes.rect((Gdx.graphics.getWidth()/2 -res.font.getBounds("Singleplayer").width /2)-5,
                      (Gdx.graphics.getHeight()/2-res.font.getBounds("Singleplayer").height)-10,
                                                 (res.font.getBounds("Singleplayer").width )+10,
                                                 (res.font.getBounds("Singleplayer").height)+10);
      res.shapes.end();
    } else {
      res.shapes.begin(ShapeRenderer.ShapeType.Line);
      res.shapes.setColor(1, 1, 1, 1);
      res.shapes.rect((Gdx.graphics.getWidth()/2 -res.font.getBounds("Multiplayer").width /2)-5,
                      (Gdx.graphics.getHeight()/2-res.font.getBounds("Multiplayer").height)-30,
                                                 (res.font.getBounds("Multiplayer").width )+10,
                                                 (res.font.getBounds("Multiplayer").height)+10);
      res.shapes.end();
    }

    if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
      selected = (++selected) % 2;
    } else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      selected = (++selected) % 2;
    }

    if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
      if(selected == 0) {
        res.game.setScreen(new GameScreen(res));
      } else {
        res.game.setScreen(new MultiplayerConnectionScreen(res));
      }
    }
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void show() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}
