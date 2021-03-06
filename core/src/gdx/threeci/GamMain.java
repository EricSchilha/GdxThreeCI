package gdx.threeci;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GamMain extends Game {
	ScrMenu scrMenu;
	ScrPlay scrPlay;
	ScrGameover scrGameover;
	int nScreen; // 0 for menu, 1 for play, and 2 for game over

	public void updateState(int _nScreen) {
		nScreen = _nScreen;
		if (nScreen == 0) {
			setScreen(scrMenu);
		} else if (nScreen == 1) {
			setScreen(scrPlay);
		} else if (nScreen == 2) {
			setScreen(scrGameover);
		}
	}
	@Override
	public void create() {
		nScreen = 0;
		// notice that "this" is passed to each screen. Each screen now has access to methods within the "game" master program
		scrMenu = new ScrMenu(this);
		scrPlay = new ScrPlay(this);
		scrGameover = new ScrGameover(this);
		updateState(2);
	}
	@Override
	public void render() {
		super.render();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
}
