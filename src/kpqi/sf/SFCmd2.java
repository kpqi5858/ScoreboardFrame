package kpqi.sf;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SFCmd2 implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission("sf.reload")) {
			try {
				sender.sendMessage("[ScoreboardFrame] Scoreboard ������ �ٽ� �ε��մϴ�.");
				long ms = System.currentTimeMillis();
				ScoreFileLoader.reload();
				sender.sendMessage("[ScoreboardFrame] Scoreboard ������ �ε��Ͽ����ϴ�.");
				sender.sendMessage("[ScoreboardFrame] �ɸ� �ð� : " + (System.currentTimeMillis() - ms) + "ms");
				sender.sendMessage("[ScoreboardFrame] Scoreboard �ε�� �׸��");
				for (String name : ScoreFileLoader.SCOREBOARDS.keySet()) {
					sender.sendMessage(name);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage("[ScoreboardFrame] ��c��ȯ�� �ʿ��մϴ�.");
		}
		return true;
	}

}
