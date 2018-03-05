// performs the minimax search for the dots game
// cs373
// (c) 2018 Alex C. Taylor

import java.util.*;

public class Minimax {
    
    private ArrayList<DotsBoard> initChildren;
    private DotsBoard current;
    private Hashtable<DotsBoard, Integer> seenMax = new Hashtable<DotsBoard, Integer>();
    private Hashtable<DotsBoard, Integer> seenMin = new Hashtable<DotsBoard, Integer>();
    private static int counter = 0;

    public Minimax(){

    }
    
    public DotsBoard search(DotsBoard initialState){
	int v = maxValue(initialState);
	initChildren = initialState.getChildren();
	DotsBoard child = null;
	int size = initChildren.size();
	for(int x = 0; x < size; x++){
	    child = initChildren.get(x);
	    if(v == child.getValue()){
		return child;
	    }
	}
	return child;
    }

    public DotsBoard pruningSearch(DotsBoard initialState, int min, int max){
	int v = maxValue(initialState, min, max);
	initChildren = initialState.getChildren();
	DotsBoard child = null;
	int size = initChildren.size();
	for(int x = 0; x < size; x++){
	    child = initChildren.get(x);
	    if(v == child.getValue()){
		return child;
	    }
	}
	return child;
    }

    public int maxValue(DotsBoard state){
	counter++;
	System.out.println(counter);
	if(state.isOver()){
	    return state.getValue();
	}
	int v = -100;
	ArrayList<DotsBoard> actions = state.getChildren();
	DotsBoard child;
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    child = actions.get(x);
	    if(seenMin.containsKey(child)){
		v = Math.max(v, seenMin.get(child));
		//System.out.println("MAX_SEEN");
		continue;
	    }
	    else{
		int min = minValue(child);
		seenMin.put(child, min);
		v = Math.max(v, min);
	    }
	}
	return v;
    }

    //alpha beta pruning version
    public int maxValue(DotsBoard state, int alpha, int beta){
	counter++;
	System.out.println(counter);
	if(state.isOver()){
	    return state.getValue();
	}
	int v = -100;
	ArrayList<DotsBoard> actions = state.getChildren();
	DotsBoard child;
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    child = actions.get(x);
	    if(seenMin.containsKey(child)){
		v = Math.max(v, seenMin.get(child));
		continue;
	    }
	    else {
		int min = minValue(child, alpha, beta);
		seenMin.put(child, min);
		v = Math.max(v, min);
		if(v >= beta){
		    return v;
		}
		alpha = Math.max(alpha, v);
	    }
	}
	return v;
    }

    public int minValue(DotsBoard state){
	counter++;
	System.out.println(counter);
	if(state.isOver()){
	    return state.getValue();
	}
	int v = 100;
	ArrayList<DotsBoard> actions = state.getChildren();
	DotsBoard child;
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    child = actions.get(x);
	    if(seenMax.containsKey(child)){
		//System.out.println("MIN_SEEN");
		v = Math.min(v, seenMax.get(child));
		continue;
	    }
	    else {
		int max = maxValue(child);
		seenMax.put(child, max);
		v = Math.min(v, max);
	    }
	}
	return v;
    }
    
    //alpha beta pruning version
    public int minValue(DotsBoard state, int alpha, int beta){
	counter++;
	System.out.println(counter);
	if(state.isOver()){
	    return state.getValue();
	}
	int v = 100;
	ArrayList<DotsBoard> actions = state.getChildren();
	DotsBoard child;
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    child = actions.get(x);
	    if(seenMax.containsKey(child)){
		v = Math.min(v, seenMax.get(child));
		continue;
	    }
	    else {
		int max = maxValue(child, alpha, beta);
		seenMax.put(child, max);
		v = Math.min(v, max);
		if(v <= alpha){
		    return v;
		}
		beta = Math.min(beta, v);
	    }
	}
	return v;
    }
    
    public static void main(String[] args){
	DotsBoard board = new DotsBoard();
	DotsBoard result;
	Minimax muon = new Minimax();
	if(args.length > 0 && args[0].equals("prune")){
	    result = muon.pruningSearch(board, -1000, 1000);
	    while(!result.isOver()){
		System.out.println("Value: " + result.getValue() + "\n" + result.toString());
		result = muon.pruningSearch(result, -1000, 1000);
	    }
	    System.out.println("Value: " + result.getValue() + "\n" + result.toString());
	}
	else {
	    result = muon.search(board);
	    while(!result.isOver()){
		System.out.println("Value: " + result.getValue() + "\n" + result.toString());
		result = muon.search(result);
	    }
	    System.out.println("Value: " + result.getValue() + "\n" + result.toString());
	}
    }
}