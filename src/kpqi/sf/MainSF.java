package kpqi.sf;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MainSF extends JavaPlugin {
	
	public static final String VER = "1.3";
	
	public static Plugin plugin;
	
	private static boolean usephapi;
	
	public static String main;
	
	public void onEnable() {
		plugin = this;
		if (!getConfig().isSet("main")) {
			getConfig().addDefault("usephapi", true);
			getConfig().addDefault("main", "Insert_Main_Here");
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		
		
		usephapi = getConfig().getBoolean("usephapi");
		main = getConfig().getString("main");
		
		if (usephapi) {
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				getLogger().warning("PlaceholderAPI 플러그인을 찾을 수 없습니다.");
				usephapi = false;
			}
		}
		
		getCommand("runsf").setExecutor(new SFCmd());
		getCommand("clearsf").setExecutor(new SFCmd1());
		getCommand("scoreboardframe").setExecutor(new CommandExecutor() {

			@Override
			public boolean onCommand(CommandSender sender, Command command,
					String label, String[] args) {
				if (args.length != 0) {
					sender.sendMessage("...");
				}
				sender.sendMessage("[ScoreboardFrame] Version : " + VER + " by Kpqi5858");
				sender.sendMessage("/runsf <name> : 이름이 name인 로드된 스코어보드를 실행합니다..");
				sender.sendMessage("필요 펄미션 : sf.scoreboard.<name>");
				sender.sendMessage("/clearsf : 현제 작동중인 스코어보드를 중지합니다.");
				sender.sendMessage("필요 펄미션 : sf.cancel");
				sender.sendMessage("/reloadsf : 스코어보드를 다시 로드합니다.");
				sender.sendMessage("필요 펄미션 : sf.cancel");
				sender.sendMessage("콘솔에서는 /runsf, /clearsf 명령어를 사용할 수 없습니다.");
				return true;
			}
			
		});
		getCommand("reloadsf").setExecutor(new SFCmd2());
		
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		
		getLogger().info("Scoreboard 파일을 로드합니다.");
		
		long ms1 = System.currentTimeMillis();
		
		try {
			ScoreFileLoader.reload();
			
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().warning("Scoreboard 파일을 로드하는 중에 알수 없는 오류가 발생했습니다.");
			getLogger().warning("플러그인을 비활성화 합니다.");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		getLogger().info("Scoreboard 파일을 로드하였습니다.");
		getLogger().info("걸린 시간 : " + (System.currentTimeMillis() - ms1) + "ms");
		getLogger().info("Scoreboard 로드된 항목들");
		for (String name : ScoreFileLoader.SCOREBOARDS.keySet()) {
			getLogger().info(name);
		}
		getLogger().info(usePlaceHolder() ? "PlaceholderAPI를 사용합니다." : "PlaceholderAPI를 사용하지 않습니다.");
		
		new ScoreboardThread();
	}

	public static boolean usePlaceHolder() {
		return usephapi;
	}
	
	/**
	 * 1.5.2버전인가?
	 * @return 1.5.2 = true, other = false
	 */
	public static boolean isUnsupportedVersion() {
		return Bukkit.getServer().getVersion().contains("1.5.2") || Bukkit.getServer().getVersion().contains("152") || Bukkit.getServer().getVersion().contains("1_5_2");
	}
}
