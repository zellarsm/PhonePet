package com.example.connect4;



public class GameImp {

	private int gameStatus = 1;
	
	private int[][] data;
	private int[][] prevData;
	private int turn = 1;
	int row = 6;
	int column = 7;
	public final static int HUMAN_SIDE = 1;
	public final static int COMPUTER_SIDE = -1;
	
	public final static int GAME_STATUS_YOU_WIN = 100;
	public final static int GAME_STATUS_YOU_LOSE = -100;
	public final static int GAME_STATUS_DRAW = 0;
	public final static int GAME_STATUS_STARTED = 1;
	public final static int GAME_STATUS_STOPPED = -1;
	
	private Connect4MinimaxMemAI ai = null;
	
	public GameImp(int r, int c, int startTurn){
		int row = r;
		int column = c;
		data = new int[row][column];
		prevData = new int[row][column];
		
		ai = new Connect4MinimaxMemAI(4, row, column);
		
		this.turn = startTurn;
	}
	
	public void reset(){
		data = new int[row][column];
		prevData = new int[row][column];
	}
	
	public int[][] getGameData(){
		return this.data;
	}
	
	public int[][] getPrevGameData(){
		return this.prevData;
	}
	
	public int getCurrentTurn(){
		return this.turn;
	}
	
	public int getGameStatus(){
		return this.gameStatus;
	}
	
	public int getPlyDepth(){
		return ai.getPlyDepth();
	}
	
	public void setPlyDepth(int depth){
		ai.setPlyDepth(depth);
	}
			
	//side is only (1, -1), 1 means human, -1 means computer
	public boolean next(int col, int side){
		if (side != HUMAN_SIDE && side != COMPUTER_SIDE)  //not a valid step 
			return false;
		
		if(col >  column) //not a valid step
			return false;
		
		if (data[0][col] != 0) //this column is full
			return false;
		
		if(gameStatus != GameImp.GAME_STATUS_STARTED){  //game ended
			return false;
		}
		
		for(int i=0;i<row;i++){
			for (int j = 0; j< column;j++){
				prevData[i][j] = data[i][j];
			}
		}

		this.turn = -1*side; //Switch turn
		
		for(int i=row-1;i>=0;i--){
			if(data[i][col] == 0){
				data[i][col]=side;
				break;
			}
		}
		
		return true;
	}
	
	public boolean checkWin(){
		if(data[0][0] != 0 && data[0][1]!=0&&data[0][2] != 0 && data[0][3]!=0&&data[0][4] != 0 && data[0][5]!=0&&data[0][6] != 0){
			this.gameStatus = GameImp.GAME_STATUS_DRAW;
			return true;
		}
		//check each row
		for(int i=0; i< row;i++){
			int max = 1;
			
			for(int j=0;j<column;j++){  
				if(data[i][j] !=0 && j>0 && data[i][j-1]==data[i][j]){
					max++;
					if(max>=4) {
						if(data[i][j] == HUMAN_SIDE)
							this.gameStatus = GameImp.GAME_STATUS_YOU_WIN;
						else
							this.gameStatus = GameImp.GAME_STATUS_YOU_LOSE;
						return true;
					}
				}else{
					max = 1;
				}
			}
		}

		//check each column
		for(int j=0; j< column;j++){
			int max = 1;
			for(int i=0;i<row;i++){  
				if(data[i][j] !=0 && i>0 && data[i-1][j]==data[i][j]){
					max++;
					if(max>=4) {
						if(data[i][j] == HUMAN_SIDE)
							this.gameStatus = GameImp.GAME_STATUS_YOU_WIN;
						else
							this.gameStatus = GameImp.GAME_STATUS_YOU_LOSE;
						return true;
					}
				}else{
					max = 1;
				}
			}
		}

		//check each "/"
		for(int n=0;n<(row+column);n++){
			int max = 1;
			
			int x = 0;
			int y = n;

			while (x <= column && y>=0){
				if(x>=row ||x<0 ||y<0 ||y >= column) {
					x++;
					y--;

					continue;
				}
				if(data[x][y] !=0 && x>0 && y< column-1&& data[x-1][y+1]==data[x][y]){
					max++;
					if(max >=4){
						if(data[x][y] == HUMAN_SIDE)
							this.gameStatus = GameImp.GAME_STATUS_YOU_WIN;
						else
							this.gameStatus = GameImp.GAME_STATUS_YOU_LOSE;
						return true;
					}
				}else{
					max = 1;
				}
			
				x++;
				y--;
			}
		}
		
		//check each "\"
		for(int n=0;n<(row+column);n++){
			int max = 1;
			
			int x = row -1;
			int y = n;

			while (x <= column && y>=0){
				if(x>=row ||x<0 ||y<0 ||y >= column) {
					x--;
					y--;

					continue;
				}
				
				if(data[x][y] !=0 && x>0 && y>0 && data[x-1][y-1]==data[x][y]){
					max++;
					if(max >=4) {
						if(data[x][y] == HUMAN_SIDE)
							this.gameStatus = GameImp.GAME_STATUS_YOU_WIN;
						else
							this.gameStatus = GameImp.GAME_STATUS_YOU_LOSE;
						return true;
					}
				}else{
					max = 1;
				}
			
				x--;
				y--;
			}
		}
		return false;
	}
	
	public void nextComputer(){
		int x = minimaxNextComputer();
		next(x, COMPUTER_SIDE);
	}
	
	private int nextComputerStep = -1;
	private int calculateCounts = -1;
	public int getNextComputerStepShow(){
		return nextComputerStep;
	}
	public int getCalculatedCounts(){
		return calculateCounts;
	}
	
	//minimax to calulate the next step based on search levels
	private int minimaxNextComputer(){
		int next = ai.getMinimaxNextStep(this.data);
		nextComputerStep = next;
		int count = ai.getCaluatedCount();
		calculateCounts = count;
		//System.out.println("Computer will take next step: "+next+", Calculated counts:" + count);
		return next;
	}
	

	
	
}

