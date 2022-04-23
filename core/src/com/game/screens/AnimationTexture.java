package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AnimationTexture implements Screen {

    private SpriteBatch batch;

    TextureAtlas atlas;
    private static final float WORLD_WIDTH = 10;
    private static final float WORLD_HEIGHT = 10;
    private Camera cam;
    private Viewport viewport;
    private Texture texture;
    private float stateTime; //new, para tiempo de duracion de cada frame
    private Animation<TextureRegion> animation;

    //private TextureRegion region;  cancelado por las animaciones


    public AnimationTexture() {
        batch= new SpriteBatch();
        //viewport = new ExtendViewport(WORLD_WIDTH/2,WORLD_HEIGHT/2);  /*camara dimessions metiad, 5 y 5*/
        viewport = new ExtendViewport(WORLD_WIDTH,WORLD_HEIGHT);  /*camara dimessions total, no division*/
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
        cam = viewport.getCamera();

        atlas = new TextureAtlas("tr_run.atlas");
        Array<TextureAtlas.AtlasRegion> run = atlas.findRegions("ninja-run");

        /*TextureRegion[][] regions = TextureRegion.split(texture,472, 457);  //replacew atals with texturteRegion pa correr en ekl mmismo cuadro, ahora no bitches con el de arriba*/
        //animation = new Animation<TextureRegion>(0.2f,regions[0]);
        //animation.setPlayMode(Animation.PlayMode.LOOP);
        animation = new Animation<TextureRegion>(0.1f,run, Animation.PlayMode.LOOP);
        stateTime= 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;

        batch.setProjectionMatrix(cam.combined); //please paint everything realted qith my cam, my world, not pixels*/
        batch.begin();
        //why not 4(4,5) mmmm  SOLVED! porque las medidas no erarn 10 10
        batch.draw(animation.getKeyFrame(stateTime),0,0,2,2); /*paint figure 0,0 an be 2X2*/

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
