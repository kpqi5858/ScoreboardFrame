package kpqi.sf;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.VariableTriggers.VariableTriggers;

public class VTVarIntegration {
	
	public static String getVariableValue(String name, Player p) {
		if (!MainSF.usevtph) return null;
		
		if (p != null) {
			//VT Placeholders
			name = name.replaceAll("<playername>", p.getName());
		}
		
		//Yeah! Get value from plugin!
		VariableTriggers plugin = (VariableTriggers) Bukkit.getServer().getPluginManager().getPlugin("VariableTriggers");
		return plugin.vars.getStr(name);
	}
}
