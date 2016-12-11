package kpqi.sf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardThread extends BukkitRunnable {
	
	public static final Map<UUID, ScoreThread> TORUN = new HashMap<UUID, ScoreThread>();
	
	
	public ScoreboardThread() {
		this.runTaskTimer(MainSF.plugin, 1, 0);
	}
	
	@Override
	public void run() {
		for (ScoreThread thread : TORUN.values()) {
			thread.run();
		}
	}

}
