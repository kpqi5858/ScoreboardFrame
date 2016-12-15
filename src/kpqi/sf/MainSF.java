package kpqi.sf;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MainSF extends JavaPlugin {
	
	public static final String VER = "1.4";
	
	public static Plugin plugin;
	
	private static boolean usephapi;
	public static String main;
	
	public static boolean usecmdph;
	public static boolean usevtph;
	
	public void onEnable() {
		plugin = this;
		if (!getConfig().isSet("main")) {
			getConfig().addDefault("usephapi", true);
			getConfig().addDefault("main", "Insert_Main_Here");
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
		}
		if (!getConfig().isSet("placeholder.command")) {
			getConfig().addDefault("placeholder.command", false);
			getConfig().addDefault("placeholder.vt", true);
			getConfig().options().copyDefaults(true);
			saveConfig();
			reloadConfig();
		}
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		
		
		usephapi = getConfig().getBoolean("usephapi");
		main = getConfig().getString("main");
		
		usecmdph = getConfig().getBoolean("placeholder.command");
		usevtph = getConfig().getBoolean("placeholder.vt");
		
		if (usephapi) {
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				getLogger().warning("PlaceholderAPI 플러그인을 찾을 수 없습니다.");
				usephapi = false;
			}
		}
		if (usevtph) {
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				getLogger().warning("PlaceholderAPI 플러그인을 찾을 수 없습니다.");
				getLogger().warning("VariableTriggers 플러그인과 연동되기 위해서는 PlaceholderAPI 플러그인이 필요합니다.");
				usevtph = false;
			}
			
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("VariableTriggers")) {
				getLogger().warning("VariableTriggers 플러그인을 찾을 수 없습니다.");
				usevtph = false;
			}
		}
		if (usecmdph) {
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				getLogger().warning("PlaceholderAPI 플러그인을 찾을 수 없습니다.");
				getLogger().warning("다른 플러그인들과 연동하기 위해서는 PlaceholderAPI 플러그인이 필요합니다.");
				usecmdph = false;
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
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] /runsf <name> : 이름이 name인 로드된 스코어보드를 실행합니다.");
				sender.sendMessage("[ScoreboardFrame] 필요 펄미션 : sf.scoreboard.<name>");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] /clearsf : 현제 작동중인 스코어보드를 중지합니다.");
				sender.sendMessage("[ScoreboardFrame] 필요 펄미션 : sf.cancel");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] /reloadsf : 스코어보드를 다시 로드합니다.");
				sender.sendMessage("[ScoreboardFrame] 필요 펄미션 : sf.reload");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] /placeholdersf <변수이름> [값] : PlaceholderAPI 사용시 %sfph_cmd_<변수이름>% 으로 [값]을 받아올수 있습니다.");
				sender.sendMessage("[ScoreboardFrame] 값이 없으면 <변수이름> 변수의 값이 초기화 됩니다.");
				sender.sendMessage("[ScoreboardFrame] 필요 펄미션 : 없음");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] 콘솔에서는 /runsf, /clearsf, /placeholdersf 명령어를 사용할 수 없습니다.");
				return true;
			}
			
		});
		getCommand("reloadsf").setExecutor(new SFCmd2());
		
		if (usephapi) {
			new SFIntegration();
		}
		
		getCommand("placeholdersf").setExecutor(new SFIntegration.CmdIntegrationListener());
		
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		
		getLogger().info("Scoreboard 파일을 로드합니다.");
		
		long ms1 = System.currentTimeMillis();
		
		try {
			ScoreFileLoader.reload();
			
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().warning("Scoreboard 파일을 로드하는 중에 알수 없는 입출력 오류가 발생했습니다.");
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
