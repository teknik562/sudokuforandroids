package de.fhd.medien.mait.sfa;
import java.io.Serializable;


public class Score implements Serializable {
	
	static final long serialVersionUID = 1;
	
	private String userName = "";
	private int level = 0;
	private int cheatCount = 0;
	private int sumTime = 0;
	
	private int[][] solvedField = null;
	private int[][] userManipulatedField = null;
	private int[][][] candidates = null;
	
	Score(String _userName, int _level, int _cheatCount, int _sumTime, int[][] _solvedField, int[][] _userManipulatedField, int[][][] _candidates){
		userName = _userName;
		level = _level;
		cheatCount = _cheatCount;
		sumTime = _sumTime;
		
		solvedField = _solvedField;
		userManipulatedField = _userManipulatedField;
		candidates = _candidates;
	}
	
	
}
