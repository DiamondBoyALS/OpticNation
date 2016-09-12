package ml.optidevs.nation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ml.optidevs.nation.Lang;

public class Main extends JavaPlugin {

	static Logger log = Bukkit.getServer().getLogger();
	public static YamlConfiguration LANG;
	public static File LANG_FILE;
	public PluginDescriptionFile desc = getDescription();
	public static YamlConfiguration TESTS;
	public static File TESTS_FILE;

	@Override
	public void onEnable() {
		loadConfigs();
		registerEvents(this, new ml.optidevs.nation.tests.Signs(this), new ml.optidevs.nation.tests.GUIListener());
		getCommand("Tests").setExecutor(new ml.optidevs.nation.tests.Commands(this));
		
		log(Level.INFO, "Plugin Enabled v" + desc.getVersion());

	}

	@Override
	public void onDisable() {
		log(Level.INFO, "Plugin Disabled v" + desc.getVersion());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(cl("&6" + desc.getName() + " &8&l»&b OpticNation is comming soon!"));
		return true;
	}

	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	public static Main gi() {
		return (Main) Bukkit.getServer().getPluginManager().getPlugin("OpticNation");
	}

	public void loadConfig() {
		getConfig().getDefaults();
		saveDefaultConfig();
		reloadConfig();
	}

	public void loadLang() {
		File lang = new File(getDataFolder(), "lang.yml");
		if (!lang.exists()) {
			try {
				getDataFolder().mkdir();
				lang.createNewFile();
				InputStream defConfigStream = this.getResource("lang.yml");
				if (defConfigStream != null) {
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(LANG_FILE);
					defConfig.save(lang);
					Lang.setFile(defConfig);
					return;
				}
			} catch (IOException e) {
				log.severe("Couldn't create language file.");
				log.severe("This is a fatal error. Now disabling");
			}
		}
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		for (Lang item : Lang.values()) {
			if (conf.getString(item.getPath()) == null) {
				conf.set(item.getPath(), item.getDefault());
			}
		}
		Lang.setFile(conf);
		Main.LANG = conf;
		Main.LANG_FILE = lang;
		try {
			conf.save(getLangFile());
		} catch (IOException e) {
			e.printStackTrace();
			log(Level.SEVERE, "Failed to save lang.yml.");
			log(Level.SEVERE, "Report this stack trace to OptiDevs.");
		}
	}

	public YamlConfiguration getLang() {
		return LANG;
	}

	public File getLangFile() {
		return LANG_FILE;
	}

	public void loadTests() {
		File tests = new File(getDataFolder(), "tests.yml");
		if (!tests.exists()) {
			try {
				getDataFolder().mkdir();
				tests.createNewFile();
				InputStream defConfigStream = getResource("tests.yml");
				if (defConfigStream != null) {
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(TESTS_FILE);
					defConfig.save(tests);
					return;
				}
			} catch (IOException e) {
				log(Level.SEVERE, "Couldn't create tests file.");
				log(Level.SEVERE, "This is a fatal error. Now disabling");
			}
		}
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(tests);

		Main.TESTS = conf;
		Main.TESTS_FILE = tests;
		try {
			conf.save(getTestsFile());
		} catch (IOException e) {
			e.printStackTrace();
			log(Level.SEVERE, "Failed to save tests.yml.");
			log(Level.SEVERE, "Report this stack trace to OptiDevs.");
		}
	}

	public YamlConfiguration getTests() {
		return TESTS;
	}

	public File getTestsFile() {
		return TESTS_FILE;
	}

	public void log(Level level, String msg) {
		msg = msg.replaceAll("[" + gi().getDescription().getName() + "] ", "");
		msg = msg.trim();
		msg = "[" + gi().getDescription().getName() + "] " + msg;
		msg = msg.trim();
		log.log(level, msg);
	}

	public void log(String msg) {
		msg = msg.replaceAll("[" + gi().getDescription().getName() + "] ", "");
		msg = msg.trim();
		msg = "[" + gi().getDescription().getName() + "] " + msg;
		msg = msg.trim();
		log.log(Level.INFO, msg);
	}

	public String cl(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static String clS(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static String[] clS(String[] str) {
		for (int i = 0; i < str.length; i++) {
			str[i] = ChatColor.translateAlternateColorCodes('&', str[i]);
		}
		return str;
	}

	public static List<String> clS(List<String> str) {
		String[] stra = { "ERROR" };
		for (int i = 0; i < str.size(); i++)
			stra[i] = str.get(i);
		for (int i = 0; i < stra.length; i++) {
			stra[i] = ChatColor.translateAlternateColorCodes('&', stra[i]);
		}
		return Arrays.asList(stra);
	}

	public void loadConfigs() {
		loadConfig();
		loadLang();
		loadTests();
	}
}
