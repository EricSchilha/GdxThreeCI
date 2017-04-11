package gdx.threeci;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class SprSprite extends Sprite {
    private Rectangle rectBounds;
    private Sprite sprSprite;
    private Texture txSprite;
    private TextureRegion trFrame;
    private float fW, fH, fSx, fSy;
    private int nX, nY, nColumns, nRows, nStartX, nStartY;
    private int nPos, nFrame;
    private int nCurrentDir; //NORTHWEST SOUTHWEST NORTHEAST SOUTHEAST WEST EAST NORTH SOUTH
    private Animation<TextureRegion> araniSprite[];
    private Sprite[] arSprSprite = new Sprite[8];
    private int nType;
    private int[] arnDirsX = {-1, 0, 1, 0};
    private int[] arnDirsY = {0, 1, 0, -1};
    private int[] arnPositions;

    public SprSprite(Texture _txSprite, int _nX, int _nY, int _nColumns, int _nRows, int _nType, int[] _arnPositions) {
        nColumns = _nColumns;
        nRows = _nRows;
        nX = _nX;
        nY = _nY;
        txSprite = _txSprite;
        nType = _nType;
        arnPositions = _arnPositions;
    }

    public void create() {
        nStartX = nX;
        nStartY = nY;
        nFrame = 0;
        nPos = 0; // the position in the SpriteSheet - 0 to 7
        fH = txSprite.getHeight() / nRows;
        fW = txSprite.getWidth() / nColumns;
        rectBounds = new Rectangle(nX, nY, fW, fH);
        araniSprite = new Animation[8];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprSprite = new Sprite(txSprite, (int) fSx, (int) fSy, (int) fW, (int) fH);
                arSprSprite[j] = new Sprite(sprSprite);
            }
            araniSprite[i] = new Animation<>(1, arSprSprite);
            arSprSprite = new Sprite[8];
        }
    }

    public Rectangle getBounds() {
        return rectBounds;
    }

    public boolean isHit(int x, int y) {
        y = Gdx.graphics.getHeight() - y;
        if (x >= nX && x <= nX + fH - 20 && y >= nY && y <= nY + fW - 5) {
            return true;
        }
        return false;
    }

    public void collide() {
        nX = nStartX;
        nY = nStartY;
    }

    void update(int nDirX, int nDirY) {
        nFrame++;
        nStartX = nX;
        nStartY = nY;
        nX += arnDirsX[nDirX];
        nY += arnDirsY[nDirY];
        rectBounds.setPosition(nX, nY);
    }

    public Sprite getSprite() {
        return sprSprite;
    }

    public TextureRegion getFrame() {
        if (nFrame > 31) {
            nFrame = 0;
        }
        trFrame = araniSprite[arnPositions[nPos]].getKeyFrame((float) Math.floor(nFrame / 4), true);
        return trFrame;
    }

    public void checkKeyPress() {
        switch (nType) {
            case 0:
                if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
                    update(0, 1);
                    nPos = nCurrentDir = 0;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) && Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
                    update(0, 3);
                    nPos = nCurrentDir = 1;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
                    update(2, 1);
                    nPos = nCurrentDir = 2;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
                    update(2, 3);
                    nPos = nCurrentDir = 3;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
                    update(0, 0);
                    nPos = nCurrentDir = 4;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
                    update(2, 2);
                    nPos = nCurrentDir = 5;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
                    update(1, 1);
                    nPos = nCurrentDir = 6;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
                    update(3, 3);
                    nPos = nCurrentDir = 7;
                }
                break;
            case 1:
                if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
                    update(0, 1);
                    nPos = nCurrentDir = 0;
                } else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
                    update(0, 3);
                    nPos = nCurrentDir = 1;
                } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
                    update(2, 1);
                    nPos = nCurrentDir = 2;
                } else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) {
                    update(2, 3);
                    nPos = nCurrentDir = 3;
                } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    update(0, 0);
                    nPos = nCurrentDir = 4;
                } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    update(2, 2);
                    nPos = nCurrentDir = 5;
                } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    update(1, 1);
                    nPos = nCurrentDir = 6;
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    update(3, 3);
                    nPos = nCurrentDir = 7;
                }
                break;
        }
    }

    public float getX() {
        return nX;
    }

    public float getY() {
        return nY;
    }

}
