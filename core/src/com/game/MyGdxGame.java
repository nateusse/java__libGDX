package com.game;

import com.badlogic.gdx.Game;
import com.game.screens.AnimationTexture;
import com.game.screens.SetUpNinja;
import com.game.screens.TextureNinja;

public class MyGdxGame extends Game {
	@Override
	public void create () {
		setScreen(new AnimationTexture());
	}
}
