package com.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.MyGdxGame;
import com.game.scene.Scene;
import com.game.screens.BaseScreen;
import com.game.utils.BaseActor;

import java.util.EnumMap;
import java.util.Map;

import static com.badlogic.gdx.Gdx.input;

public class Ninja extends Actor {


    private Pie pie;
    private Scene scene;
    private final ShapeRenderer shape;
    public static final float BLOCK_SIZE = 1;
    private Texture characterTexture;
    private Vector2 nextPos;
    private boolean lookAtRight = true;
    private float stateTime = 0;
    private Map<AnimationState, Animation<TextureRegion>> animations;

    private Orientation horizontal = Orientation.ZERO;
    private Orientation vertical = Orientation.ZERO;
    private AnimationState currentState = AnimationState.WALK_DOWN;

    // 5 blocks / s econd
    private float velocity = 5f;



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



    public Ninja(Scene scene, float x, float y, ShapeRenderer shape){
        this.scene = scene;
        setPosition(x, y);
        setSize(BLOCK_SIZE, BLOCK_SIZE);
        this.shape = shape;
        this.nextPos = new Vector2(x,y);
        characterTexture = new Texture("Link_spritesheet.png");
        //animations = loadAnimations(characterTexture);


    }




    public void dispose(){
        characterTexture.dispose();
    }

}


