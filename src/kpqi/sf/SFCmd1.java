package kpqi.sf;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SFCmd1 implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("[ScoreboardFrame] Version : " + MainSF.VER + " by Kpqi5858");
			sender.sendMessage("[ScoreboardFrame] 콘솔에서는 이 명령어를 사용할 수 없습니다.");
			return true;
		}
		
		if (!sender.hasPermission("sf.cancel")) {
			sender.sendMessage("[ScoreboardFrame] §c권환이 필요합니다.");
			return false;
		}
		
		final Player p = (Player) sender;
		
		if (!ScoreboardThread.TORUN.containsKey(p.getUniqueId())) {
			p.sendMessage("[ScoreboardFrame] §rScoreboardFrame§c 의 스코어보드를 실행하고 있지 않습니다.");
			return false;
		}

		ScoreboardThread.TORUN.remove(p.getUniqueId());
		
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

		return true;
	}

}
