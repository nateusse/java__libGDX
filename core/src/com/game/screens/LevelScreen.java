package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.MyGdxGame;
import com.game.entities.Ninja;

import java.text.MessageFormat;
import java.util.*;

public class LevelScreen extends BaseScreen {


    private Ninja ninja;

    private static final float WORLD_WIDTH = 20;
    private static final float WORLD_HEIGHT = 20;
    private static final float BLOCK_SIZE = 1;
    public static final String NAME = "Pruebas de Tiled Maps";

    private OrthographicCamera cam;
    private Viewport viewport;

    private TiledMap tiledMap;
    private float unitScale;
    private TiledMapRenderer mapRenderer;
    int[] backgroundLayers = {0};
    int[] foregroundLayers = {1};
    List<Shape2D> collisionShapes;

    //private Texture characterTexture;
    private TextureAtlas characterTexture;
    private Sprite character;
    private Vector2 position;
    private float[] bounds;
    private boolean lookAtRight = true;
    private float stateTime = 0;
    private Map<AnimationState, Animation<TextureRegion>> animations;
    private AnimationState currentState = AnimationState.WALK_DOWN;
    private Animation<TextureRegion> currentAnimation;

    // 5 blocks / second
    private float velocity = 5f;


    //Texturas
    TextureAtlas atlas;


    private enum Orientation{
        NEGATIVE(-1), ZERO(0), POSITIVE(1);
        int factor;
        Orientation(int f){ factor = f; }

        Orientation stop(Orientation previousOriention){
            return (this == previousOriention) ? ZERO : this;
        }
    }

    private enum AnimationState {
        IDLE_UP, IDLE_DOWN, IDLE_SIDE, WALK_UP, WALK_DOWN, WALK_SIDE
    }

    private Orientation leftRight = Orientation.ZERO;
    private Orientation upDown = Orientation.ZERO;

    public LevelScreen(MyGdxGame game){
        super(game, NAME);

        cam = new OrthographicCamera();
        viewport = new ExtendViewport(20, 20, cam);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        // This map has been created with Tiled -- https://www.mapeditor.org/
        // you can edit city.tmx from Tiled!
        tiledMap = new TmxMapLoader().load("grass.tmx");
        // 1 World Unit = 32 pixels in map file
        unitScale = BLOCK_SIZE / 32f;
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
        collisionShapes = loadCollisionShapes();
        for (Shape2D s : collisionShapes){
            Gdx.app.log("SHAPE",
                    MessageFormat.format("{0}  {1}", s.getClass().getSimpleName(), s.toString()));
        }

        position = new Vector2(0,0);
        characterTexture = new TextureAtlas("ninja_run.atlas");
        Array<TextureAtlas.AtlasRegion> run = characterTexture.findRegions("ninja-run");
        //animations = loadAnimations(characterTexture);
        //currentAnimation = animations.get(currentState);
        currentAnimation =  new Animation<TextureRegion>(0.1f,run, Animation.PlayMode.LOOP);
        character = new Sprite(currentAnimation.getKeyFrames()[0]);

        //change size character
        character.setSize(1,1);
        bounds = new float[]{
                position.x-BLOCK_SIZE/2, position.y-BLOCK_SIZE/2,
                position.x-BLOCK_SIZE/2, position.y+BLOCK_SIZE/2,
                position.x+BLOCK_SIZE/2, position.y-BLOCK_SIZE/2,
                position.x+BLOCK_SIZE/2, position.y+BLOCK_SIZE/2
        };
    }


    @Override
    public void render(float delta){
        super.render(delta);
        updatePosition(delta);


        cam.position.set(position.x, position.y, 0);
        cam.position.x = MathUtils.clamp(cam.position.x, viewport.getWorldWidth()/2, WORLD_HEIGHT- viewport.getWorldHeight()/2);
        cam.position.y = MathUtils.clamp(cam.position.y, viewport.getWorldHeight()/2, WORLD_HEIGHT - viewport.getWorldHeight()/2);
        cam.update();

        stateTime += delta;
        //currentAnimation = animations.get(currentState);
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime);
        if (currentState == AnimationState.WALK_SIDE){
            if (frame.isFlipX() && lookAtRight){
                frame.flip(true, false);
            }
            if (!lookAtRight && !frame.isFlipX()){
                frame.flip(true, false);
            }
        }
        character.setRegion(frame);

        mapRenderer.setView(cam);
        mapRenderer.render(backgroundLayers);


        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.polygon(bounds);
        shapeRenderer.end();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        character.draw(batch);





        batch.end();

        mapRenderer.render(foregroundLayers);
    }

    private void updatePosition(float delta){
        // I will only do stuff when there is any movement
        if (leftRight.factor != 0 || upDown.factor != 0){
            position.set( character.getX(), character.getY());
            position.x += leftRight.factor * velocity * delta;
            position.y += upDown.factor * velocity * delta;

            for (Shape2D shape : collisionShapes){
                if (collides(shape, position)){
                    Gdx.app.log("Collision", "Collision detected");
                    return;
                }
            }

            position.x = MathUtils.clamp(position.x, 0f, WORLD_WIDTH - BLOCK_SIZE);
            position.y = MathUtils.clamp(position.y, 0f, WORLD_HEIGHT - BLOCK_SIZE);

            bounds[0] = position.x;
            bounds[1] = position.y;

            bounds[2] = position.x;
            bounds[3] = position.y+BLOCK_SIZE;

            bounds[4] = position.x+BLOCK_SIZE;
            bounds[5] = position.y;

            bounds[6] = position.x+BLOCK_SIZE;
            bounds[7] = position.y+BLOCK_SIZE;

            character.setPosition(position.x, position.y);
        }
    }

    private boolean collides(Shape2D collShape, Vector2 characterPos){
        return (collShape.contains(characterPos.x, characterPos.y)
                || collShape.contains(characterPos.x, characterPos.y+BLOCK_SIZE)
                || collShape.contains(characterPos.x+BLOCK_SIZE, characterPos.y)
                || collShape.contains(characterPos.x+BLOCK_SIZE, characterPos.y+BLOCK_SIZE));
    }

    public List<Shape2D> loadCollisionShapes(){
        List<Shape2D> shapes = new ArrayList<>();
        MapObjects touchables = tiledMap.getLayers().get("collisions").getObjects();
        for (MapObject obj : touchables){
            if (obj instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                shapes.add(
                        new Rectangle(rect.x * unitScale,
                                rect.y * unitScale,
                                rect.width*unitScale,
                                rect.height*unitScale));
            }
            if (obj instanceof EllipseMapObject){
                Ellipse ell = ((EllipseMapObject) obj).getEllipse();
                shapes.add(
                        new Ellipse((ell.x + ell.width/2)* unitScale,
                                (ell.y + ell.height/2) * unitScale,
                                ell.width*unitScale,
                                ell.height*unitScale));
            }
        }
        return shapes;
    }



    @Override
    public boolean keyDown(int key){
        super.keyUp(key);
        switch (key){
            case Input.Keys.LEFT:
                leftRight = Orientation.NEGATIVE;
                currentState = AnimationState.WALK_SIDE;
                lookAtRight = false;
                stateTime=0;
                break;
            case Input.Keys.RIGHT:
                leftRight = Orientation.POSITIVE;
                currentState = AnimationState.WALK_SIDE;
                lookAtRight = true;
                stateTime=0;
                break;
            case Input.Keys.DOWN:
                upDown = Orientation.NEGATIVE;
                currentState = AnimationState.WALK_DOWN;
                stateTime=0;
                break;
            case Input.Keys.UP:
                upDown = Orientation.POSITIVE;
                currentState = AnimationState.WALK_UP;
                stateTime=0;
                break;


        }




        return false;
    }




    @Override
    public boolean keyUp(int key){

        switch (key){
            case Input.Keys.LEFT:
                leftRight = leftRight.stop(Orientation.NEGATIVE);
                break;
            case Input.Keys.RIGHT:
                leftRight = leftRight.stop(Orientation.POSITIVE);
                break;
            case Input.Keys.DOWN:
                upDown = upDown.stop(Orientation.NEGATIVE);
                break;
            case Input.Keys.UP:
                upDown = upDown.stop(Orientation.POSITIVE);
                break;

        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }



    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void dispose(){
        super.dispose();
        tiledMap.dispose();
        characterTexture.dispose();
    }

}


