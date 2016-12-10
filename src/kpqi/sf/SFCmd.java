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
			sender.sendMessage("[ScoreboardFrame] 콘솔에서는 이 명령어를 사용할 수 없습니다.");
			return true;
		}
		
		switch(args.length) {
		case 1:
			String name = args[0];
			
			if (ScoreFileLoader.SCOREBOARDS.containsKey(name)) {
				if (!sender.hasPermission("sf.scoreboard." + name)) {
					sender.sendMessage("[ScoreboardFrame] §c권환이 필요합니다.");
					return true;
				}
				if (ScoreboardThread.TORUN.containsKey(((Player) sender).getUniqueId())) {
					return true;
				}
				ScoreThread thread = new ScoreThread(ScoreFileLoader.SCOREBOARDS.get(name), ((Player) sender), -1, MainSF.usePlaceHolder());

				ScoreboardThread.TORUN.put(((Player) sender).getUniqueId(), thread);
				return true;
			} else {
				sender.sendMessage("[ScoreboardFrame]" + name + "§c 이름의 스코어보드가 없습니다.");
				return true;
			}
		
		default:
			sender.sendMessage("[ScoreboardFrame] §c잘못된 명령어 사용법 입니다.");
			return true;
		}
	}

}
