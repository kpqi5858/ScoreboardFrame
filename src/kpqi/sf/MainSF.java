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
				getLogger().warning("PlaceholderAPI �÷������� ã�� �� �����ϴ�.");
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
				sender.sendMessage("/runsf <name> : �̸��� name�� �ε�� ���ھ�带 �����մϴ�..");
				sender.sendMessage("�ʿ� �޹̼� : sf.scoreboard.<name>");
				sender.sendMessage("/clearsf : ���� �۵����� ���ھ�带 �����մϴ�.");
				sender.sendMessage("�ʿ� �޹̼� : sf.cancel");
				sender.sendMessage("/reloadsf : ���ھ�带 �ٽ� �ε��մϴ�.");
				sender.sendMessage("�ʿ� �޹̼� : sf.cancel");
				sender.sendMessage("�ֿܼ����� /runsf, /clearsf ��ɾ ����� �� �����ϴ�.");
				return true;
			}
			
		});
		getCommand("reloadsf").setExecutor(new SFCmd2());
		
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		
		getLogger().info("Scoreboard ������ �ε��մϴ�.");
		
		long ms1 = System.currentTimeMillis();
		
		try {
			ScoreFileLoader.reload();
			
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().warning("Scoreboard ������ �ε��ϴ� �߿� �˼� ���� ������ �߻��߽��ϴ�.");
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
