package kpqi.sf;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
	 
	/**
	 * �÷��̾ ������ ���� ��, config�� ������ �⺻ ���ھ�� ����
	 * @param e
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent e) {
		if (MainSF.main.equals("Insert_Main_Here")) return;
		String name = MainSF.main;
		Player sender = e.getPlayer();
		
		if (ScoreFileLoader.SCOREBOARDS.containsKey(name)) {
			if (!sender.hasPermission("sf.scoreboard." + name)) {
				sender.sendMessage("[ScoreboardFrame] ��c�޹̼��� �ʿ��մϴ�.");
				return;
			}
			if (ScoreboardThread.TORUN.containsKey(sender.getUniqueId())) {
				return;
			}
			ScoreThread thread = new ScoreThread(ScoreFileLoader.SCOREBOARDS.get(name), sender, MainSF.usePlaceHolder());
			ScoreboardThread.TORUN.put(sender.getUniqueId(), thread);
			return;
		} else {
			sender.sendMessage("[ScoreboardFrame] " + name + "��c �̸��� ���ھ�尡 �����ϴ�.");
			return;
		}
	}
	
	/**
	 * �÷��̾ ������ ������, �������� ���ھ�� ���
	 * @param e
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onLeft(PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		
		if (!ScoreboardThread.TORUN.containsKey(p.getUniqueId())) {
			return;
		}
		
		ScoreboardThread.TORUN.remove(p.getUniqueId());

		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
