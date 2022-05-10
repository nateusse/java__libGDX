package com.game.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.game.utils.BaseActor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction;
public class Pie extends BaseActor {
    private Vector2 velocityVec;
    public Pie(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("pie.png");
        setSpeed(400);
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
       // wrapAroundWorld();
    }



}
