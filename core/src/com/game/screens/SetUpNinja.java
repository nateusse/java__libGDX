package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SetUpNinja implements Screen {

    private static final float WORLD_WIDTH = 10;
    private static final float WORLD_HEIGHT = 10;
    private SpriteBatch batch;
    private Camera cam;
    private Viewport viewport;
    private Texture texture;

    public SetUpNinja() {
        batch= new SpriteBatch();
        viewport = new ExtendViewport(WORLD_WIDTH/2,WORLD_HEIGHT/2);  /*camara dimessions metiad, 5 y 5*/
        //viewport = new ExtendViewport(WORLD_WIDTH,WORLD_HEIGHT);  /*camara dimessions total, no division*/
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
        cam = viewport.getCamera();
        texture = new Texture("ninja_cat/animations/run/ninja-run_00.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined); //please paint everything realted qith my cam, my world, not pixels*/
        batch.begin();
        //why not 4(4,5) mmmm  SOLVED! porque las medidas no erarn 10 10
        batch.draw(texture,0,0,2,2); /*paint figure 0,0 an be 2X2*/
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
