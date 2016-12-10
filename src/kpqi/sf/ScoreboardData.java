package kpqi.sf;

import java.util.List;

public class ScoreboardData {
	
	public List<List<String>> data;
	public int delay;
	
	public ScoreboardData(List<List<String>> data, int delay) {
		this.data = data;
		this.delay = delay;
	}
}
