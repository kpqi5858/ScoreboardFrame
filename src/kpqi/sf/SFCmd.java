package kpqi.sf;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SFCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("[ScoreboardFrame] Version : " + MainSF.VER + " by Kpqi5858");
			sender.sendMessage("[ScoreboardFrame] �ֿܼ����� �� ��ɾ ����� �� �����ϴ�.");
			return true;
		}
		
		switch(args.length) {
		case 1:
			String name = args[0];
			
			if (ScoreFileLoader.SCOREBOARDS.containsKey(name)) {
				if (!sender.hasPermission("sf.scoreboard." + name)) {
					sender.sendMessage("[ScoreboardFrame] ��c��ȯ�� �ʿ��մϴ�.");
					return true;
				}
				if (ScoreboardThread.TORUN.containsKey(((Player) sender).getUniqueId())) {
					return true;
				}
				ScoreThread thread = new ScoreThread(ScoreFileLoader.SCOREBOARDS.get(name), ((Player) sender), -1, MainSF.usePlaceHolder());

				ScoreboardThread.TORUN.put(((Player) sender).getUniqueId(), thread);
				return true;
			} else {
				sender.sendMessage("[ScoreboardFrame]" + name + "��c �̸��� ���ھ�尡 �����ϴ�.");
				return true;
			}
		
		default:
			sender.sendMessage("[ScoreboardFrame] ��c�߸��� ��ɾ� ���� �Դϴ�.");
			return true;
		}
	}

}
