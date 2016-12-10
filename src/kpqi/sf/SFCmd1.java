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
			sender.sendMessage("[ScoreboardFrame] �ֿܼ����� �� ��ɾ ����� �� �����ϴ�.");
			return true;
		}
		
		if (!sender.hasPermission("sf.cancel")) {
			sender.sendMessage("[ScoreboardFrame] ��c��ȯ�� �ʿ��մϴ�.");
			return false;
		}
		
		final Player p = (Player) sender;
		
		if (!ScoreboardThread.TORUN.containsKey(p.getUniqueId())) {
			p.sendMessage("[ScoreboardFrame] ��rScoreboardFrame��c �� ���ھ�带 �����ϰ� ���� �ʽ��ϴ�.");
			return false;
		}

		ScoreboardThread.TORUN.remove(p.getUniqueId());
		
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

		return true;
	}

}
