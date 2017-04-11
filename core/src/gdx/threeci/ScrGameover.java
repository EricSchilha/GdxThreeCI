package gdx.threeci;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static com.badlogic.gdx.math.MathUtils.random;

public class ScrGameover implements InputProcessor, Screen {
    Stage stage;
    TbsMenu tbsMenu;
    SprSprite sprVlad, sprOgre;
    TbMenu tbPlay, tbMenu;
    GamMain game;
    Texture txVlad, txOgre;
    BitmapFont screenName;
    SpriteBatch batch;
    Camera cam;
    int nSpriteX, nSpriteY; // starting coordinates for the dudes.
    int[] arnPosVlad = {3, 6, 2, 5, 7, 0, 1, 4};
    int[] arnPosOgre = {3, 6, 2, 5, 7, 0, 1, 4};

    public ScrGameover(GamMain _game) {
        game = _game;
    }

    public void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.updateState(0);
            }
        });
    }

    public void btnPlayListener() {
        tbPlay.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.updateState(1); // switch to Play screen.
            }
        });
    }

    //@Override
    public void show() {
        cam = new Camera();
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        screenName = new BitmapFont();
        tbPlay = new TbMenu("PLAY", tbsMenu);
        tbMenu = new TbMenu("BACK", tbsMenu);
        tbMenu.setY(0);
        tbMenu.setX(0);
        tbPlay.setY(0);
        tbPlay.setX(440);
        stage.addActor(tbPlay);
        stage.addActor(tbMenu);
        nSpriteX = (Gdx.graphics.getWidth() / 2);
        nSpriteY = (Gdx.graphics.getHeight() / 2);
        txVlad = new Texture("vlad.png");
        txOgre = new Texture("ogre.png");
        sprVlad = new SprSprite(txVlad, nSpriteX - 50, nSpriteY, 8, 8, 0, arnPosVlad);
        sprOgre = new SprSprite(txOgre, nSpriteX + 50, nSpriteY, 8, 8, 1, arnPosOgre);
        Gdx.input.setInputProcessor(stage);
        btnPlayListener();
        btnMenuListener();
        sprVlad.create();
        sprOgre.create();
    }

    //@Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.render();
        stage.act(delta);
        stage.draw();
        batch.begin();
        sprVlad.checkKeyPress();
        sprOgre.checkKeyPress();
        if(sprVlad.getBounds().overlaps(sprOgre.getBounds())){
            sprVlad.collide();
            sprOgre.collide();
        }
        if (Gdx.input.justTouched() && sprVlad.isHit(Gdx.input.getX(), Gdx.input.getY())) {
            System.out.println("Hit");
        }
        batch.draw(sprVlad.getFrame(), sprVlad.getX() - 15, sprVlad.getY() - 5);//Adjustments based on offset from spritesheet
        batch.draw(sprOgre.getFrame(), sprOgre.getX() - 15, sprOgre.getY() - 5);//Adjustments based on offset from spritesheet
        batch.end();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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
        stage.dispose();
    }
}
