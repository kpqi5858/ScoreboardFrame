package kpqi.sf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreThread implements Runnable {
	
	Player p;
	
	long delay;
	
	int current = 0; //frame

	int maxfr;
	
	long age = 0;
	
	public int taskid;
	
	boolean canceled = false;
	
	List<List<String>> rea;
	
	public ScoreThread(ScoreboardData data, Player pl, boolean useplaceapi) {
		p = pl;
		delay = data.delay;
		maxfr = data.data.size();
		rea = data.data;
	}
	
	@Override
	public void run() {
		if (age % delay == 0) {
			if (maxfr == current) {
				current = 0;
			}
			if (canceled) {
				return;
			}
			
			List<String> convert = new ArrayList<String>(rea.get(current));
			
			//I found the NullPointer bug, When List is empty, or contains only title, skip the instruction. And view empty scoreboard.
			if (convert.isEmpty() || convert.size() == 1) {
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				current++;
				age+=1L;
				return;
			}
			
			Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
			
			Objective t = s.registerNewObjective("Kpqi_Plugin", "dummy");
			t.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			//PlaceHolderAPI
			if (MainSF.usePlaceHolder()) {
				List<String> temp = new ArrayList<String>();
				temp = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(p, convert);
				convert = temp;
			}
			
			int score = 1;
			
			t.setDisplayName(convert.get(0).replace('&', '」'));
			
			convert.remove(0);
			
			//안뒤집으면 거꾸로됨
			Collections.reverse(convert);
			
			//1.5.2/or other unsupported version Support
			if (MainSF.isUnsupportedVersion()) {
				for (String to : convert) {
					String to1 = to.replace('&', '」');
					//Yeah. Handle this error!
					if (to1.getBytes().length >= 16) {
						to1 = new String(Arrays.copyOfRange(to1.getBytes(), 0, 15));
						score++;
						continue;
					}
					t.getScore(new FakePlayer(to1)).setScore(score);
					score++;
				}
			} else {
				for (String to : convert) {
					String to1 = to.replace('&', '」');
					
					//I found the bug that entry cannot be 40 char long
					if (to1.length() > 40) {
						to1 = to1.substring(0, 40);
					}
					t.getScore(to1).setScore(score);
					score++;
				}
			}
			
			
			p.setScoreboard(s);
			
			current++;
		}
		age+=1L;
	}
	
	public void setCancel(boolean state) {
		canceled = state;
	}

}
