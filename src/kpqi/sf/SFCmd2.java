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
				sender.sendMessage("[ScoreboardFrame] Scoreboard 파일을 다시 로드합니다.");
				long ms = System.currentTimeMillis();
				ScoreFileLoader.reload();
				sender.sendMessage("[ScoreboardFrame] Scoreboard 파일을 로드하였습니다.");
				sender.sendMessage("[ScoreboardFrame] 걸린 시간 : " + (System.currentTimeMillis() - ms) + "ms");
				sender.sendMessage("[ScoreboardFrame] Scoreboard 로드된 항목들");
				for (String name : ScoreFileLoader.SCOREBOARDS.keySet()) {
					sender.sendMessage(name);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage("[ScoreboardFrame] §c권환이 필요합니다.");
		}
		return true;
	}

}
