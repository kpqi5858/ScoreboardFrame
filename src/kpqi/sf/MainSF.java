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
				getLogger().warning("PlaceholderAPI �÷������� ã�� �� �����ϴ�.");
				usephapi = false;
			}
		}
		if (usevtph) {
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				getLogger().warning("PlaceholderAPI �÷������� ã�� �� �����ϴ�.");
				getLogger().warning("VariableTriggers �÷����ΰ� �����Ǳ� ���ؼ��� PlaceholderAPI �÷������� �ʿ��մϴ�.");
				usevtph = false;
			}
			
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("VariableTriggers")) {
				getLogger().warning("VariableTriggers �÷������� ã�� �� �����ϴ�.");
				usevtph = false;
			}
		}
		if (usecmdph) {
			if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				getLogger().warning("PlaceholderAPI �÷������� ã�� �� �����ϴ�.");
				getLogger().warning("�ٸ� �÷����ε�� �����ϱ� ���ؼ��� PlaceholderAPI �÷������� �ʿ��մϴ�.");
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
				sender.sendMessage("[ScoreboardFrame] /runsf <name> : �̸��� name�� �ε�� ���ھ�带 �����մϴ�.");
				sender.sendMessage("[ScoreboardFrame] �ʿ� �޹̼� : sf.scoreboard.<name>");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] /clearsf : ���� �۵����� ���ھ�带 �����մϴ�.");
				sender.sendMessage("[ScoreboardFrame] �ʿ� �޹̼� : sf.cancel");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] /reloadsf : ���ھ�带 �ٽ� �ε��մϴ�.");
				sender.sendMessage("[ScoreboardFrame] �ʿ� �޹̼� : sf.reload");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] /placeholdersf <�����̸�> [��] : PlaceholderAPI ���� %sfph_cmd_<�����̸�>% ���� [��]�� �޾ƿü� �ֽ��ϴ�.");
				sender.sendMessage("[ScoreboardFrame] ���� ������ <�����̸�> ������ ���� �ʱ�ȭ �˴ϴ�.");
				sender.sendMessage("[ScoreboardFrame] �ʿ� �޹̼� : ����");
				sender.sendMessage("[ScoreboardFrame] ");
				sender.sendMessage("[ScoreboardFrame] �ֿܼ����� /runsf, /clearsf, /placeholdersf ��ɾ ����� �� �����ϴ�.");
				return true;
			}
			
		});
		getCommand("reloadsf").setExecutor(new SFCmd2());
		
		if (usephapi) {
			new SFIntegration();
		}
		
		getCommand("placeholdersf").setExecutor(new SFIntegration.CmdIntegrationListener());
		
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		
		getLogger().info("Scoreboard ������ �ε��մϴ�.");
		
		long ms1 = System.currentTimeMillis();
		
		try {
			ScoreFileLoader.reload();
			
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().warning("Scoreboard ������ �ε��ϴ� �߿� �˼� ���� ����� ������ �߻��߽��ϴ�.");
			getLogger().warning("�÷������� ��Ȱ��ȭ �մϴ�.");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		getLogger().info("Scoreboard ������ �ε��Ͽ����ϴ�.");
		getLogger().info("�ɸ� �ð� : " + (System.currentTimeMillis() - ms1) + "ms");
		getLogger().info("Scoreboard �ε�� �׸��");
		for (String name : ScoreFileLoader.SCOREBOARDS.keySet()) {
			getLogger().info(name);
		}
		getLogger().info(usePlaceHolder() ? "PlaceholderAPI�� ����մϴ�." : "PlaceholderAPI�� ������� �ʽ��ϴ�.");
		
		new ScoreboardThread();
	}

	public static boolean usePlaceHolder() {
		return usephapi;
	}
	
	/**
	 * 1.5.2�����ΰ�?
	 * @return 1.5.2 = true, other = false
	 */
	public static boolean isUnsupportedVersion() {
		return Bukkit.getServer().getVersion().contains("1.5.2") || Bukkit.getServer().getVersion().contains("152") || Bukkit.getServer().getVersion().contains("1_5_2");
	}
}
