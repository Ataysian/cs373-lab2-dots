// performs the minimax search for the dots game
// cs373
// (c) 2018 Alex C. Taylor

public class Minimax {
    
    public Minimax(DotsBoard initialState){
	
    }

    public int maxValue(DotsBoard state){
	if(state.isOver()){
	    return state.getValue();
	}
	int v = -100;
	ArrayList<DotsBoard> actions = state.getChildren();
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    v = Math.max(v, minValue(actions.get(x)));
	}
	return v;
    }

    public int maxValue(DotsBoard state, int alpha, int beta){
	if(state.isOver()){
	    return state.getValue();
	}
	int v = -100;
	ArrayList<DotsBoard> actions = state.getChildren();
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    v = Math.max(v, minValue(actions.get(x), alpha, beta));
	    if(v >= beta){
		return v;
	    }
	    alpha = Math.max(alpha, v);
	}
	return v;
    }

    public int minValue(DotsBoard state){
	if(state.isOver()){
	    return state.getValue();
	}
	int v = 100;
	ArrayList<DotsBoard> actions = state.getChildren();
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    v = Math.min(v, maxValue(actions.get(x)));
	}
	return v;
    }

    public int minValue(DotsBoard state, int alpha, int beta){
	if(state.isOver()){
	    return state.getValue();
	}
	int v = 100;
	ArrayList<DotsBoard> actions = state.getChildren();
	int length = actions.size();
	for(int x = 0; x < length; x++){
	    v = Math.min(v, maxValue(actions.get(x), alpha, beta));
	    if(v <= alpha){
		return v;
	    }
	    beta = Math.min(beta, v);
	}
	return v;
    }
}