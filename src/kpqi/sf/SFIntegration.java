package kpqi.sf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.external.EZPlaceholderHook;

public class SFIntegration extends EZPlaceholderHook {
	
	public static final Map<UUID, Map<String, String>> CMD_PLACEHOLDERS = new HashMap<UUID, Map<String, String>>();
	
	public SFIntegration() {
		super(MainSF.plugin, "sfph");
		this.hook();
	}

	@Override
	public String onPlaceholderRequest(Player arg0, String arg1) {
		if (arg1.startsWith("cmd_")) {
			if (arg0 == null) return "";
			if (!CMD_PLACEHOLDERS.containsKey(arg0.getUniqueId())) {
				CMD_PLACEHOLDERS.put(arg0.getUniqueId(), new HashMap<String, String>());
			}
			String res = CMD_PLACEHOLDERS.get(arg0.getUniqueId()).get(arg1.substring(4));
			return res != null ? res : "";
		}
		else if (arg1.startsWith("vt_")) {
			if (MainSF.usevtph) {
				return VTVarIntegration.getVariableValue(arg1.substring(3), arg0);
			} else return null;
		}
		return null;
	}
	
	
	static class CmdIntegrationListener implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("[ScoreboardFrame] Version : " + MainSF.VER + " by Kpqi5858");
				sender.sendMessage("[ScoreboardFrame] 콘솔에서는 이 명령어를 사용할 수 없습니다.");
				return true;
			}
			
			if (!MainSF.usecmdph) {
				sender.sendMessage("[ScoreboardFrame] Version : " + MainSF.VER + " by Kpqi5858");
				sender.sendMessage("[ScoreboardFrame] CMD 플레이스홀더 설정이 꺼져있습니다.");
				return true;
			}
			Player p = (Player) sender;
			
			if (args.length >= 1) {
				if (args.length == 1) {
					if (!CMD_PLACEHOLDERS.containsKey(p.getUniqueId())) {
						CMD_PLACEHOLDERS.put(p.getUniqueId(), new HashMap<String, String>());
					}
					CMD_PLACEHOLDERS.get(p.getUniqueId()).remove(args[0]);
				} else {
					StringBuilder value = new StringBuilder();
					boolean first = true;
					for (String s : args) {
						if (first) {
							first = false;
							continue;
						}
						value.append(s);
						value.append(' ');
					}
					String resValue = value.toString();
					
					if (!CMD_PLACEHOLDERS.containsKey(p.getUniqueId())) {
						CMD_PLACEHOLDERS.put(p.getUniqueId(), new HashMap<String, String>());
					}
					CMD_PLACEHOLDERS.get(p.getUniqueId()).put(args[0], resValue);
				}
			} else {
				sender.sendMessage("[ScoreboardFrame] 하나 이상의 매개변수가 필요합니다.");
				return true;
			}
			return true;
		}
		
		
	}
}
