package com.example.connect4;

import java.util.ArrayList;

public class Connect4MinimaxMemAI {

	private int depth = 2;
	private int row = 6;
	private int column = 7;

	ArrayList<int[]> levelDataList = new ArrayList<int[]>();
	private int[][] rootNode;
	private int[][] workNode;
	
	public Connect4MinimaxMemAI(int searchLevel, int row, int col){
		this.depth = searchLevel;
		this.row = row;
		this.column = col;

	}
	
	public void setPlyDepth(int plydepth){
		this.depth = plydepth;
		levelDataList.clear();
	}
	
	public int getPlyDepth(){
		return this.depth;
	}

	
	//Main API to let computer calculate next step, return column
	public int getMinimaxNextStep(int[][] data){
		this.rootNode = data;
		this.workNode = this.copyData(rootNode);
		
		currentPos = -1;
//		int value = this.minimax(0, 0);
		calulatedCount = 0;
		int value = this.minimaxalphabeta(0, 0, -100000,100000);
		return currentPos;
	}


	private int currentPos = 0;
	private int minimax(int level, int pos){
		if(level >= depth){
			int[][] nodeData = this.getLeafBoardSnapshot(this.rootNode, pos);
			int value = this.getEstimateValue(nodeData);
//			levelDataList.get(3)[pos] = value;
			return value;
		}
		
		int[] children = this.getChildren(rootNode,level, pos);
    	
		if(level%2 == 0){     //this is max level
			int max = -100000;
			for(int i = 0;i<children.length;i++){
				int v = minimax(level+1, children[i]);
				if (v > max) {
					max = v;
					if(level==0) currentPos = i;
				}
			}
			return max;
		}
        else{                //this is min level
        	int min = 100000;
        	for(int i=0; i<children.length; i++){
        		int m = minimax(level+1, children[i]);
        		if(m<min) {
        			min=m;
        		}
        	}
        	return min;
        }
	}

	int calulatedCount = 0;
	
	public int getCaluatedCount(){
		return this.calulatedCount;
	}
	private int minimaxalphabeta(int level, int pos, int alpha, int beta){
		calulatedCount++;
		int[][] nodeData = this.getBoardSnapshot(rootNode, level, pos);
		
		if(level >= depth || this.isLeafNode(nodeData)){
			int value = this.getEstimateValue(nodeData);
			return value;
		}
		
		int[] children = this.getChildren(nodeData,level, pos);
    	
		if(level%2 == 0){     //this is max level
			int vNode = alpha;
			for(int i = 0;i<children.length;i++){
				
				if(children[i] == -1) continue; //invalid node
				
				int vChild = minimaxalphabeta(level+1, children[i], vNode, beta );
				if (vChild > vNode) {
					vNode = vChild;
					if(level==0) currentPos = i;
				}
				if(vNode > beta) 
					return beta;
			}
			return vNode;
		}
        else{                //this is min level
        	int vNode = beta;
        	for(int i=0; i<children.length; i++){
        		
        		if(children[i] == -1) continue; //invalid node
        		
        		int vChild = minimaxalphabeta(level+1, children[i], alpha, vNode);
        		if(vChild<vNode) {
        			vNode=vChild;
        		}
        		if(vNode < alpha) return alpha;
        	}
        	return vNode;
        }
	}
	
	private int minimaxalphabeta2(int level, int pos, int alpha, int beta){
		if(level >= depth){
			int[][] nodeData = this.getLeafBoardSnapshot(this.rootNode, pos);
			int value = this.getEstimateValue(nodeData);
			return value;
		}
		
		int[] children = this.getChildren(rootNode, level, pos);
    	
		if(level%2 == 0){     //this is max level
			int vNode = alpha;
			for(int i = 0;i<children.length;i++){
				int vChild = minimaxalphabeta(level+1, children[i], vNode, beta );
				if (vChild > vNode) {
					vNode = vChild;
					if(level==0) currentPos = i;
				}
				if(vNode > beta) 
					return beta;
			}
			return vNode;
		}
        else{                //this is min level
        	int vNode = beta;
        	for(int i=0; i<children.length; i++){
        		int vChild = minimaxalphabeta(level+1, children[i], alpha, vNode);
        		if(vChild<vNode) {
        			vNode=vChild;
        		}
        		if(vNode < alpha) return alpha;
        	}
        	return vNode;
        }
	}

	//if this node has already 4 connects, this is a leaf node
	private boolean isLeafNode(int[][] node){
		int counts = this.getCounts(node, 4);
		if(counts > 0){
			return true;
		}else if(node[0][0]!=0 && node[0][1] !=0 && node[0][2]!=0&&node[0][3]!=0 && node[0][4] !=0 && node[0][5]!=0 && node[0][6]!=0){
			return true;
		}else{
			return false;
		}
	}
	
	private int[] getChildren(int[][] node, int level, int pos){
		int[] children = new int[column];
		for(int i=0;i<column; i++){
			if(node[0][i] != 0){
				children[i]=-1;
			}else{
				children[i]=pos*column+i;
			}
		}
		return children;
	}


	private int[][] getLeafBoardSnapshot(int[][] rootData, int pos){
		int[][] snapshot = this.copyData(rootData);
		int[] levelNext = new int[depth];
		
		int side = -1;
		
		//calculate the route to given position
		for(int i = depth-1; i>= 0;i--){
			levelNext[i]= (int) ((pos/Math.pow(column, depth -i-1)))%column;
		}
		
		for(int i =0; i<depth;i++){
			this.insertColumn(snapshot, levelNext[i], side);
			side = -1*side;
		}
		
		return snapshot;
	}
	
	private int[][] getBoardSnapshot(int[][] rootNode, int level, int pos){
		if(level==0) return rootNode;
		int side = -1;
		int[][] workNode = this.copyData(rootNode);		
		int[] levelNext = new int[level];
		for(int i = level-1; i>= 0;i--){
			levelNext[i]= (int) ((pos/Math.pow(column, level -i-1)))%column;
		}
		
		for(int i =0; i<level;i++){
			this.insertColumn(workNode, levelNext[i], side);
			side = -1*side;
		}
		
		return workNode;
	}
	
	private int[][] insertColumn(int[][] data, int column, int side){
		for(int i =row-1;i>=0;i--){
			if(data[i][column] == 0){
				data[i][column] = side;
				break;
			}
		}
			
		return data;
	}
	
	private int[][] copyData(int[][] data){
		int[][] tmpData = new int[row][column];
		for(int r = 0; r<row; r++){
			for(int c = 0; c<column; c++){
				tmpData[r][c] = data[r][c];
			}
		}
		return tmpData;
	}
	
	private int getEstimateValue(int[][] data){
		int v = 0;
		
		int w0 = 5;
		int w1 = 10;
		int w2 = 20;
		int w3 = 40;
		int w4 = 60;
		int w5 = 1000;
		int w6 = -10;
		int w7 = -20;
		int w8 = -40;
		int w9 = -60;
		int w10 = -1000;

		int x1 = get2Counts1Com(data);
		int x2 = get2Counts2Com(data);
		int x3 = get3Counts1Com(data);
		int x4 = get3Counts2Com(data);
		int x5 = get4CountsCom(data);
		
		int x6 = get2Counts1Hum(data);
		int x7 = get2Counts2Hum(data);
		int x8 = get3Counts1Hum(data);
		int x9 = get3Counts2Hum(data);
		int x10 = get4CountsHum(data);
		//v = w0 + w2*x2 + w3*x3 +w4*x4 +w5*x5 + w7*x7+w8*x8+w9*x9+w10*x10;
		v = w0 + w1*x1 + w2*x2 + w3*x3 +w4*x4 +w5*x5 + w6*x6 +w7*x7+w8*x8+w9*x9+w10*x10;

		return v;
	}
	
	//get connect 2 counts open 1 side
	private int get2Counts1Com(int[][] data){
		return this.getCounts1(data, 2, GameImp.COMPUTER_SIDE);
	}
	
	//get connect 2 counts open both sides
	private int get2Counts2Com(int[][] data){
		return this.getCounts2(data, 2, GameImp.COMPUTER_SIDE);
	}
	
	//get connect 3 counts open 1 side 
	private int get3Counts1Com(int[][] data){
		return this.getCounts1(data, 3, GameImp.COMPUTER_SIDE);
	}
	
	private int get3Counts2Com(int[][] data){
		return this.getCounts2(data, 3, GameImp.COMPUTER_SIDE);
	}
	
	private int get4CountsCom(int[][] data){
		return this.getCounts1(data, 4, GameImp.COMPUTER_SIDE);
	}

	//get connect 2 counts open 1 side
	private int get2Counts1Hum(int[][] data){
		return this.getCounts1(data, 2, GameImp.HUMAN_SIDE);
	}
	
	//get connect 2 counts open both sides
	private int get2Counts2Hum(int[][] data){
		return this.getCounts2(data, 2, GameImp.HUMAN_SIDE);
	}
	
	//get connect 3 counts open 1 side 
	private int get3Counts1Hum(int[][] data){
		return this.getCounts1(data, 3, GameImp.HUMAN_SIDE);
	}
	
	private int get3Counts2Hum(int[][] data){
		return this.getCounts2(data, 3, GameImp.HUMAN_SIDE);
	}
	
	private int get4CountsHum(int[][] data){
		return this.getCounts1(data, 4, GameImp.HUMAN_SIDE);
	}
	
	private int getCounts1(int[][] data, int number, int side){
		int count = 0;
		//check each row
		for(int i=0; i< row;i++){
			int max = 1;
			
			for(int j=0;j<column;j++){  
				if(data[i][j] !=0 && j>0 && data[i][j-1]==data[i][j] && data[i][j]==side){
					max++;
					if(max>=number) count++;
				}else{
					max = 1;
				}
			}
		}

		//check each column
		for(int j=0; j< column;j++){
			int max = 1;
			for(int i=row-1;i>=0;i--){  
				if(data[i][j] !=0 && i<row-1 && data[i+1][j]==data[i][j]&& data[i][j]==side){
					max++;
					if(max>=number) {
						if(i>0 && data[i-1][j] ==0)
						count++;
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
				if(data[x][y] !=0 && x>0 && y< row-1 && data[x-1][y+1]==data[x][y]&& data[x][y]==side){
					max++;
					if(max>=number) count++;
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
				
				if(data[x][y] !=0 && x>0 && y>0 && data[x-1][y-1]==data[x][y]&& data[x][y]==side){
					max++;
					if(max>=number) {
						if(( x >= number && y < row-number && data[x-number][y+number]==0)||( x<column-2 && y > 0&& data[x+1][y-1] == 0))  //check at least 1 side are available
							count++;
					}
				}else{
					max = 1;
				}
			
				x--;
				y--;
			}
		}
		return count;
	}
	
	private int getCounts(int[][] data, int number){
		int count = 0;
		//check each row
		for(int i=0; i< row;i++){
			int max = 1;
			
			for(int j=0;j<column;j++){  
				if(data[i][j] !=0 && j>0 && data[i][j-1]==data[i][j] ){
					max++;
					if(max>=number){
						if((j>number-1 && data[i][j-number]==0) || (j<column-2 && data[i][j+1] == 0))  //check at least 1 side are available
						count++;
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
					if(max>=number) {
						
						count++;
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
				if(data[x][y] !=0 && x>0 && y< row-1 && data[x-1][y+1]==data[x][y]){
					max++;
					if(max>=number) {
						if(( x >= number && y < row-number && data[x-number][y+number]==0) || (x<column-2 && y > 0&& data[x+1][y-1] == 0))  //check at least 1 side are available
						count++;
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
					if(max>=number) count++;
				}else{
					max = 1;
				}
			
				x--;
				y--;
			}
		}
		return count;
	}
	
	private int getCounts2(int[][] data, int number, int side){
		int count = 0;
		//check each row
		for(int i=0; i< row;i++){
			int max = 1;
			
			for(int j=0;j<column;j++){  
				if(data[i][j] !=0 && j>0 && data[i][j-1]==data[i][j]&& data[i][j]==side){
					max++;
					if(max>=number){
						if(j>number-1 && data[i][j-number]==0 && j<column-2 && data[i][j+1] == 0)  //check both side are available
						count++;
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
				if(data[x][y] !=0 && x>0 && y< row -1 && data[x-1][y+1]==data[x][y]&& data[x][y]==side){
					max++;
					if(max>=number) {
						if( x >= number && y < row-number && data[x-number][y+number]==0 && x<column-2 && y > 0&& data[x+1][y-1] == 0)  //check both side are available
							count++;
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
				
				if(data[x][y] !=0 && x>0 && y>0 && data[x-1][y-1]==data[x][y]&& data[x][y]==side){
					max++;
					if(max>=number) {
						if( x> number && y > number && data[x-number][y-number]==0 && x<column-2 && y < row-2 && data[x+1][y+1] == 0)  //check both side are available
							count++;
					}
				}else{
					max = 1;
				}
			
				x--;
				y--;
			}
		}
		return count;
	}
}

