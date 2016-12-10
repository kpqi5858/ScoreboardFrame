package kpqi.sf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.plugin.Plugin;

public class ScoreFileLoader {
	
	public static Map<String, ScoreboardData> SCOREBOARDS = new HashMap<String, ScoreboardData>();
	
	//public static Map<String, List<List<String>>> SCOREBOARDS = new HashMap<String, List<List<String>>>();
	
	//public static Map<String, Integer> SCOREBOARDS_DELAY = new HashMap<String, Integer>();
	
	static Plugin p = MainSF.plugin;
	
	static File path = new File("" + p.getDataFolder().getAbsolutePath());
	
	public static void reload() throws IOException {
		List<File> f = new ArrayList<File>();
		File[] list = path.listFiles();
		if (list == null) return;
		if (list.length == 0) return;
		for (File t : list) {
			if (t.isFile()) {
				int pos = t.getName().lastIndexOf(".");
				if (t.getName().substring(pos + 1).equalsIgnoreCase("txt")) {
					f.add(t);
				}
			}
		}
		
		for (File t : f) {
			int ldx = t.getName().lastIndexOf(".");
			String name = t.getName().substring(0, ldx);
			try {
				SCOREBOARDS.put(name, parase(t, name));
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	private static ScoreboardData parase(File f, String rw) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(f));
		List<String> readed = new ArrayList<String>();
		
		String s;
		while ((s = in.readLine()) != null) {
			readed.add(s);
		}
		
		int resultDelay = 0;;
		try {
			int delay = Integer.parseInt(readed.get(0));
			if (delay < 0) {
				p.getLogger().warning("" + f.getName() + " 파일에 오류가 있습니다.");
				p.getLogger().warning("오류 : 딜레이는 음수가 될 수 없습니다.");
				in.close();
				throw new RuntimeException("Delay cannot be negative");
			}
			if (delay == 0) delay = 1; //0으로하면 오류납니다
			
			resultDelay = delay;
			
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			p.getLogger().warning("" + f.getName() + " 파일에 오류가 있습니다.");
			p.getLogger().warning("오류 : 딜레이가 숫자가 아니거나, 찾을 수 없습니다.");
			in.close();
			throw new RuntimeException("Delay is not a vaild number/not found.");
		}
		
		in.close();
		readed.remove(0);
		
		List<List<String>> result = new ArrayList<List<String>>();
		
		List<String> temp = new ArrayList<String>();
		
		for (String str : readed) {
			if (str.equals(";")) {
				result.add(temp);
				temp = new ArrayList<String>();
				continue;
			}
			temp.add(str);
		}
		
		return new ScoreboardData(result, resultDelay);
	}
}
