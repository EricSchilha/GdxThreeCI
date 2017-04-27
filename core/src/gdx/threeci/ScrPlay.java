package gdx.threeci;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//
//SOURCES
//Code Structure: https://github.com/Ashbuggs/ITWTiledScratch-UpdatedOrthoCam
//Getting Collision Layer: Connor Anderson: https://www.youtube.com/watch?v=TiHx4-j0rrw
//Reference: https://github.com/AbhinavA10/TiledMapScratch
//Collision Layer without Box2D: http://stackoverflow.com/questions/20063281/libgdx-collision-detection-with-tiledmap
//Keeping cam within bounds of Tiled Map: https://gamedev.stackexchange.com/questions/74926/libgdx-keep-cam-within-bounds-of-tiledmap
public class ScrPlay extends ApplicationAdapter implements InputProcessor, Screen{
    Stage stage;
    TbsMenu tbsMenu;
    TbMenu tbMenu, tbGameover;
    GamMain game;
    SpriteBatch batch;
    SprSprite sprVlad;
    Texture txVlad;
    OrthographicCamera cam;
    TiledMap tmMap;
    OrthogonalTiledMapRenderer tmrRenderer;
    MapLayer collisionObjectLayer;
    MapObjects objects;
    int[] arnPosVlad = {3, 6, 2, 5, 7, 0, 1, 4};
    private float rotationSpeed;
    int mapLeft = 0;
    int mapRight = 50 * 32;//Width * Pixels Per Tile
    int mapBottom = 0;
    int mapTop = 50 * 32;//Height * Pixels Per Tile
    float camHalfWidth;
    float camHalfHeight;
    float camLeft;
    float camRight;
    float camBottom;
    float camTop;

    public ScrPlay(GamMain _game) {
            game = _game;
    }
    /*public void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.updateState(0); // switch to Play screen.
            }
        });
    }
    public void btnGameoverListener() {
        tbGameover.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.updateState(2);
            }
        });
    }*/
    //@Override
    public void show() {


        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        rotationSpeed = 0.5f;
        camHalfWidth = cam.viewportWidth * .5f;
        camHalfHeight = cam.viewportHeight * .5f;
        camLeft = cam.position.x - camHalfWidth;
        camRight = cam.position.x + camHalfWidth;
        camBottom = cam.position.y - camHalfHeight;
        camTop = cam.position.y + camHalfHeight;


        batch = new SpriteBatch();
        stage = new Stage();
        /*tbsMenu = new TbsMenu();
        tbMenu = new TbMenu("BACK", tbsMenu);
        tbGameover = new TbMenu("GAMEOVER", tbsMenu);
        tbGameover.setY(0);
        tbGameover.setX(0);
        tbMenu.setY(0);
        tbMenu.setX(440);
        stage.addActor(tbMenu);
        stage.addActor(tbGameover);*/
        Gdx.input.setInputProcessor(this);
        /*btnMenuListener();
        btnGameoverListener();*/

        tmMap = new TmxMapLoader().load("TiledMapV2.tmx");
        tmrRenderer = new OrthogonalTiledMapRenderer(tmMap);
        collisionObjectLayer = tmMap.getLayers().get("collision");
        objects = collisionObjectLayer.getObjects();
        //Creating Sprite

        txVlad = new Texture("vlad.png");
        sprVlad = new SprSprite(txVlad, 250, 250, 8, 8, 0, arnPosVlad);
        sprVlad.create();
    }

    //@Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        cam.update();
        tmrRenderer.setView(cam);
        tmrRenderer.render();
        stage.act(delta);
        stage.draw();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, sprVlad.getBounds())) {
                System.out.println("Rectangle was hit ");
                sprVlad.collide();
            }
        }
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        sprVlad.checkKeyPress();
        batch.draw(sprVlad.getFrame(), sprVlad.getX() - 15, sprVlad.getY() - 5);//Adjustments based on offset from spritesheet
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.getX() < 30) {//left
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.getX() > Gdx.graphics.getWidth() - 30) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.getY() > Gdx.graphics.getHeight() - 30) {//up
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.getY() < 30) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }


        //Keeping the Map on the screen
        cam.position.x = MathUtils.clamp(cam.position.x, camHalfWidth, mapRight - camHalfWidth);
        cam.position.y = MathUtils.clamp(cam.position.y, camHalfHeight, mapTop - camHalfHeight);
        /*if(mapRight < cam.viewportWidth) {
            cam.position.x = mapRight / 2;
        }
        else if(camLeft <= mapLeft) {
            cam.position.x = mapLeft + camHalfWidth;
        }
        else if(camRight >= mapRight) {
            cam.position.x = mapRight - camHalfWidth;
        }

        if(mapTop < cam.viewportHeight) {
            cam.position.y = mapTop / 2;
        }
        else if(camBottom <= mapBottom) {
            cam.position.y = mapBottom + camHalfHeight;
        }
        else if(camTop >= mapTop) {
            cam.position.y = mapTop - camHalfHeight;
        }*/
        /*
        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);*/
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
        tmrRenderer.dispose();
        tmMap.dispose();
    }

}