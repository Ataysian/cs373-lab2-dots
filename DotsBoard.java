// Represents the board from which you play dots
// cs373
// (c) 2018 Alex C. Taylor

import java.util.*;

public class DotsBoard {

    /*
      edges represents 9 dots, 0-8 listed out like
      0 1 2
      3 4 5
      6 7 8
      The board is a 2D array where edges[i][j] = 1 means that there's an edge
      between i and j.
     */
    public int[][] edges = new int[9][9];
    private int[][] board = new int[2][2];
    private int[][] nextBoard = new int[2][2];
    private int[][] possibleMoves = new int[9][];
    private DotsBoard parent;
    private boolean whoseTurn; //true = Max's turn, false = Min's turn
    private int value = 0; //calculated upon construction
    
    //Fills the board with 0, Max has first turn
    public DotsBoard(){
	for(int x = 0; x < 9; x++){
	    Arrays.fill(edges[x], 0);
	}
	for(int x = 0; x < 2; x++){
	    Arrays.fill(board[x], 0);
	    Arrays.fill(nextBoard[x], 0);
	}
	possibleMoves[0] = new int[] {1,3};
	possibleMoves[1] = new int[] {0,2,4};
	possibleMoves[2] = new int[] {1,5};
	possibleMoves[3] = new int[] {0,4,6};
	possibleMoves[4] = new int[] {1,3,5,7};
	possibleMoves[5] = new int[] {2,4,8};
	possibleMoves[6] = new int[] {3,7};
	possibleMoves[7] = new int[] {4,6,8};
	possibleMoves[8] = new int[] {5,7};
	whoseTurn = true;
	parent = null;
    }

    public DotsBoard(DotsBoard mommy, int[][] newEdges, int[][] newBoard, boolean turn){
	edges = newEdges;
	board = newBoard;
	nextBoard[0] = board[0].clone();
	nextBoard[1] = board[1].clone();
	whoseTurn = turn;
	parent = mommy;
	for(int x = 0; x < 2; x++){
	    for(int y = 0; y < 2; y++){
		value += board[x][y];
	    }
	}
	possibleMoves[0] = new int[] {1,3};
	possibleMoves[1] = new int[] {0,2,4};
	possibleMoves[2] = new int[] {1,5};
	possibleMoves[3] = new int[] {0,4,6};
	possibleMoves[4] = new int[] {1,3,5,7};
	possibleMoves[5] = new int[] {2,4,8};
	possibleMoves[6] = new int[] {3,7};
	possibleMoves[7] = new int[] {4,6,8};
	possibleMoves[8] = new int[] {5,7};
    }
    
    //returns an arraylist of the children of this board (possible moves)
    public ArrayList<DotsBoard> getChildren(){
	ArrayList<DotsBoard> children = new ArrayList<DotsBoard>();
	ArrayList<Integer> seen = new ArrayList<Integer>();
	DotsBoard child;
	for(int x = 0; x < 9; x++){
	    int length = possibleMoves[x].length;
	    for(int y = 0; y < length; y++){
		if(!seen.contains(possibleMoves[x][y])){
		    if(edges[x][possibleMoves[x][y]] == 0){
			child = this.makeEdge(x, possibleMoves[x][y]);
			children.add(child);
		    }
		    seen.add(x);
		}
	    }
	}
	return children;
    }
    
    /*
      returns a new dots board with an edge between vertex i and vertex
      j if possible. 0 <= i,j <= 8
    */
    public DotsBoard makeEdge(int i, int j){
	int length = possibleMoves[i].length;
        boolean isPossible = false;
	int[][] newEdges = new int[9][];
	for(int x = 0; x < 9; x++){
	    newEdges[x] = edges[x].clone();
	}
	for(int x = 0; x < length; x++){
	    if(possibleMoves[i][x] == j){
		isPossible = true;
	    }
	}
	if(isPossible){
	    newEdges[i][j] = 1;
	    newEdges[j][i] = 1;
	    DotsBoard next;
	    if(checkBoard(newEdges)){
		next = new DotsBoard(this, newEdges, nextBoard, whoseTurn);
	    }
	    else{
	        next = new DotsBoard(this, newEdges, nextBoard, !whoseTurn);
	    }
	    return next;
	}
	else
	    return this;
    }

    /*
      checks to see if a move led to an enclosed square, filling the square if so
     */
    private boolean checkBoard(int[][] edges){
	boolean madeMove = false;
	if(board[0][0] == 0){
	    if(edges[0][1]==1 && edges[0][3]==1 && edges[4][3]==1 && edges[4][1]==1){
		if(whoseTurn) nextBoard[0][0] = 1;
		else nextBoard[0][0] = 2;
		madeMove = true;
	    }
	}
	if(board[0][1] == 0){
	    if(edges[1][2]==1 && edges[1][4]==1 && edges[5][4]==1 && edges[5][2]==1){
		if(whoseTurn) nextBoard[0][1] = 1;
		else nextBoard[0][1] = 2;
		madeMove = true;
	    }
	}
	if(board[1][0] == 0){
	    if(edges[3][4]==1 && edges[3][6]==1 && edges[7][6]==1 && edges[7][4]==1){
		if(whoseTurn) nextBoard[1][0] = 1;
		else nextBoard[1][0] = 2;
		madeMove = true;
	    }
	}
	if(board[1][1] == 0){
	    if(edges[4][5]==1 && edges[4][7]==1 && edges[8][7]==1 && edges[8][5]==1){
		if(whoseTurn) nextBoard[1][1] = 1;
		else nextBoard[1][1] = 2;
		madeMove = true;
	    }
	}
	return madeMove;
    }

    public int getValue(){
	return value;
    }

    public boolean isOver(){
	for(int x = 0; x < 2; x++){
	    for(int y = 0: y < 2; y++){
		if(board[x][y] == 0)
		    return false;
	    }
	}
	return true;
    }

    public boolean equals(DotsBoard otherBoard){
	for(int x = 0; x < 9; x++){
	    for(int y = 0; y < 9; y++){
		if(edges[x][y] != otherBoard.edges[x][y])
		    return false;
	    }
	}
	return true;
    }
    public String toString(){
	String result = "";
	for(int x = 0; x < 8; x++){
	    switch (x) {
	    case 0:
		if(edges[x][1] == 1) result += "*--";
		else result += "*  ";
		break;
	    case 1:
		if(edges[x][2] == 1) result += "*--";
		else result += "*  ";
		break;
	    case 2:
		result += "*\n";
		for(int y = 3; y < 6; y++){
		    switch (y) {
		    case 3:
			if(edges[y][0] == 1 && board[0][0] != 0) 
			    result += "|" + board[0][0] + " ";
			else if(edges[y][0] == 1) result += "|  ";
			else result += "   ";
			break;
		    case 4:
			if(edges[y][1] == 1 && board[0][1] != 0)
			    result += "|" + board[0][1] + " ";
			else if(edges[y][1] == 1) result += "|  ";
			else result += "   ";
			break;
		    case 5:
			if(edges[y][2] == 1) result += "|\n";
			else result += "\n";
			break;
		    }
		}
		break;
	    case 3:
		if(edges[x][4] == 1) result += "*--";
		else result += "*  ";
		break;
	    case 4:
		if(edges[x][5] == 1) result += "*--";
		else result += "*  ";
		break;
	    case 5:
		result += "*\n";
		for(int y = 6; y < 9; y++){
		    switch (y) {
		    case 6:
			if(edges[y][3] == 1 && board[1][0] != 0)
			    result += "|" + board[1][0] + " ";
			else if(edges[y][3] == 1) result += "|  ";
			else result += "   ";
			break;
		    case 7:
			if(edges[y][4] == 1 && board[1][1] != 0)
			    result += "|" + board[1][1] + " ";
			else if(edges[y][4] == 1) result += "|  ";
			else result += "   ";
			break;
		    case 8:
			if(edges[y][5] == 1) result += "|\n";
			else result += "\n";
			break;
		    }
		}
		break;
	    case 6:
		if(edges[x][7] == 1) result += "*--";
		else result += "*  ";
		break;
	    case 7:
		if(edges[x][8] == 1) result += "*--*";
		else result += "*  *";
		break;
	    }
	}
	return result;
    }

    public int[][] getEdges(){
	return edges;
    }
    
    public static void main(String[] args){
	DotsBoard test = new DotsBoard();
	DotsBoard test2 = test.makeEdge(0,1);
	DotsBoard test3 = test2.makeEdge(0,3);
	DotsBoard test4 = test3.makeEdge(4,3);
	DotsBoard test5 = test4.makeEdge(4,1);
	test5 = test5.makeEdge(3,6);
	test5 = test5.makeEdge(7,6);
	test5 = test5.makeEdge(4,7);
	test5 = test5.makeEdge(7,8);
	test5 = test5.makeEdge(1,2);
	test5 = test5.makeEdge(2,5);
	test5 = test5.makeEdge(5,8);
	test5 = test5.makeEdge(5,4);
	ArrayList<DotsBoard> bros = test.getChildren();
	int size = bros.size();
	for(int x = 0; x < size; x++){
	    System.out.println(bros.get(x).toString() + "\n-------");
	}
    }
}