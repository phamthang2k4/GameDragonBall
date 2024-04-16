package GameState;

import java.util.ArrayList;

public class GameStateManager {
	private ArrayList<GameState> gameStates;
	//Là một ArrayList chứa tất cả các trạng thái của trò chơi.
	private int currentState;
	//Biến này lưu trữ trạng thái hiện tại của trò chơi.

	public static final int NUMGAMESTATES = 2;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	//Các hằng số dùng để định danh các trạng thái khác nhau của trò chơi.

	public GameStateManager() {

		gameStates = new ArrayList<GameState>();

		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));

	}

	public void setState(int state) {
		//Phương thức này được sử dụng để đặt trạng thái mới cho trò chơi. 
		//Nó nhận vào một mã trạng thái và cập nhật currentState tương ứng.
		currentState = state;
		gameStates.get(currentState).init();
	}

	public void update() {
		//Phương thức này gọi phương thức update() của trạng thái hiện tại 
		//để cập nhật trạng thái của trò chơi.
		try {
			gameStates.get(currentState).update();
		} catch (Exception e) {
		}
	}

	public void draw(java.awt.Graphics2D g) {
		//Phương thức này gọi phương thức draw() của trạng thái hiện tại 
		//để vẽ trạng thái của trò chơi lên màn hình.
		try {
			gameStates.get(currentState).draw(g);
		} catch (Exception e) {
		}
	}
	
	//Các phương thức này được sử dụng để xử lý sự kiện phím ấn và 
	//phát hành cho trạng thái hiện tại.
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}

	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}

}

//	private GameState[] gameStates;
//	private int currentState;
//	
//	public static final int NUMGAMESTATES = 2;
//	public static final int MENUSTATE = 0;
//	public static final int LEVEL1STATE = 1;
//	
//	public GameStateManager() {
//		
//		gameStates = new GameState[NUMGAMESTATES];
//		
//		currentState = MENUSTATE;
//		//loadState(currentState);
//		
//	}
//	
//	private void loadState(int state) {
//		if(state == MENUSTATE)
//			gameStates[state] = new MenuState(this);
////		if(state == LEVEL1STATE)
////			gameStates[state] = new Level1State(this);
//	}
//	
//	private void unloadState(int state) {
//		gameStates[state] = null;
//	}
//	
//	public void setState(int state) {
//		//unloadState(currentState);
//		currentState = state;
//		//loadState(currentState);
//		gameStates[currentState].init();
//	}
//	
//	public void update() {
//		try {
//			gameStates[currentState].update();
//		} catch(Exception e) {}
//	}
//	
//	public void draw(java.awt.Graphics2D g) {
//		try {
//			gameStates[currentState].draw(g);
//		} catch(Exception e) {}
//	}
//	
//	public void keyPressed(int k) {
//		gameStates[currentState].keyPressed(k);
//	}
//	
//	public void keyReleased(int k) {
//		gameStates[currentState].keyReleased(k);
//	}

// ----------------------------------------------
