package com.example.connect4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.graphics.*;
import android.graphics.drawable.Drawable;

public class Connect4View extends View {
	Paint paintGrid = new Paint();
	Paint paintCircleHuman = new Paint();
	Paint paintCircleComputer = new Paint();
	
	DisplayMetrics metrics = this.getContext().getResources().getDisplayMetrics();;         
	int width = metrics.widthPixels;
	int height = metrics.heightPixels-38;
	
	// Don't put search level more than 6!
	int searchLevel = 4;
	int rowNumber = 6;
	int colNumber = 7;
	
	float cellL = width/colNumber;
	float gameHeight = cellL*6;
	float gameWidth = width;
	float controlHeight = height - gameWidth;	

	private AnimateDrawable mDrawable;
	private GameImp game;


	public Connect4View(Context context) {
		super(context);
		this.initGUI(context);
	}

	public Connect4View(Context context, AttributeSet attrs) {
		 
		super( context, attrs );
		this.initGUI(context);
	}
		 
	public Connect4View(Context context, AttributeSet attrs, int defStyle) {
		 
		super( context, attrs, defStyle );
		this.initGUI(context);
	}

	public void setGame(GameImp game){
		this.game = game;
		initDroppingBall((int) (cellL/3), (int)controlHeight-15,(int) (cellL/3), (int)controlHeight-15);
	}
	
	private void initGUI(Context context){
		paintGrid.setColor(Color.GRAY);
		paintGrid.setTextSize(18);
		paintCircleHuman.setColor(Color.RED);
		paintCircleComputer.setColor(Color.BLUE);
		
		initDroppingBall((int) (cellL/3), (int)controlHeight-15,(int) (cellL/3), (int)controlHeight-15);
		
		this.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				
				if(game.getCurrentTurn() != GameImp.HUMAN_SIDE) return false;
				int y = (int) event.getY();
				if(y < controlHeight - 50) return false;
				
				int x = (int) event.getX();
				
				int col = getColumnFromPosition(x);
				int stopCellNum = getStopCellNum(game.getGameData(),col);
				int stopY = (int)controlHeight;
				if (stopCellNum > 0){
					stopY = (int) (controlHeight + stopCellNum*cellL);
				}
				
				initDroppingBall((int) (col*cellL+cellL/3), (int)controlHeight-15, (int) (col*cellL+cellL/3), stopY-15);
				
				game.next(col, GameImp.HUMAN_SIDE);
				return false;
			}
		});		
	}
	
    @Override protected void onDraw(Canvas canvas) {
    		drawGrid(canvas);
    		mDrawable.draw(canvas);
    		this.drawGameStatus(canvas,game);
        
    		checkNextComputer();
    		
    		//this.drawCalculationMessage(canvas, game);
    		
    		drawWin(canvas);

		invalidate();
    }


	private void checkNextComputer() {
		if(!mDrawable.getStartFlag() && game.getCurrentTurn() == GameImp.COMPUTER_SIDE && game.getGameStatus() == GameImp.GAME_STATUS_STARTED){
			game.nextComputer();
		}
	}

    private void drawWin(Canvas canvas){
		boolean win = game.checkWin();
		
		if (win){
			String result = "YOU WIN";
			Paint paint = new Paint();
			paint.setTextSize(40);
			paint.setTextScaleX(2);
			paint.setColor(Color.RED);
			int status = game.getGameStatus();
			
			if( status==GameImp.GAME_STATUS_YOU_LOSE) {
				paint.setColor(Color.BLUE);
				result = "YOU LOSE";
			}else if(status == GameImp.GAME_STATUS_DRAW){
				paint.setColor(Color.BLUE);
				result = "DRAW";
			}
			 
			canvas.drawText(result, width/2-180, controlHeight/2, paint);
		}
    }

	private void drawGameStatus(Canvas canvas, GameImp game){
    	for (int i=0; i<rowNumber ;i++){
    		for(int j=0;j<colNumber;j++){
    			int[][] data = game.getGameData();
    			int[][] prevData = game.getPrevGameData();
    			if (data[i][j] != 0){
    				boolean flag = mDrawable.getStartFlag();
    				if(!flag){
    					this.drawCircleInGrid(canvas, i, j, data[i][j]);
    				}else{
    					this.drawCircleInGrid(canvas, i, j, prevData[i][j]);
    				}
    			}
    		}
    	}
    }
    
    private void drawGrid(Canvas canvas){
    	//draw vertical line
    	for(int i=1;i < colNumber; i++ ){
    		canvas.drawLine(cellL*i,controlHeight,cellL*i,height,paintGrid);
    	}
    	
    	//draw horizontal line
    	for(int j=rowNumber;j >=0; j-- ){
    		canvas.drawLine(0,gameHeight/rowNumber*j+controlHeight,width,gameHeight/rowNumber*j+controlHeight,paintGrid);
    	}   	
    }
    
    private void initDroppingBall(int startX, int startY, int stopX, int stopY){
        Drawable dr = this.getContext().getResources().getDrawable(android.R.drawable.arrow_down_float);
        dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
        Animation an = new TranslateAnimation(startX, stopX, startY, stopY);
        an.setDuration(1500);
        an.setRepeatCount(0);
        an.initialize(10, 10, 10, 10);
        
        mDrawable = new AnimateDrawable(dr, an);
        
        mDrawable.enableStartFlag();
        an.start();
    }
    
    private void drawCircleInGrid(Canvas canvas, int cellNumX, int cellNumY, int side){
    	if(side != GameImp.HUMAN_SIDE && side != GameImp.COMPUTER_SIDE) return;
    	
    	int cx = this.getCellCenterX(cellNumY);
    	int cy = this.getCellCenterY(cellNumX);
    	Paint paint = (side==GameImp.HUMAN_SIDE)?this.paintCircleHuman:this.paintCircleComputer;
    	canvas.drawCircle(cx, cy, cellL/2, paint);
    }
    
    private int getCellCenterX(int cellNumX){
    	int cx = (int) (cellNumX*cellL+cellL/2);
    	return cx;
    }
    
    private int getCellCenterY(int cellNumY){
    	int cy = (int) (cellNumY*cellL+cellL/2+controlHeight);
    	return cy;
    }
    
    private int getColumnFromPosition(int posX){
    	int colNumX = (int) (posX/cellL);
    	return colNumX;
    }
    
	private int getStopCellNum(int[][] gameData, int col) {
		for(int i=rowNumber-1;i >=0;i--){
			if(gameData[i][col] == 0){
				return i;
			}
		}
		return -1;
	}

}
