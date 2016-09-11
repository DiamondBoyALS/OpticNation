package ml.optidevs.nation.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ml.optidevs.nation.Lang;
import ml.optidevs.nation.Main;

public class Tests implements CommandExecutor {
	static Main m;
	FileConfiguration con;

	public Tests(Main main) {
		m = main;
		con = m.getConfig();

	}




	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0){
			sender.sendMessage(Lang.INVALID_ARGS.toString());
			return true;
		} else if(args[0].equalsIgnoreCase("list")){
			List<String> keys = Main.TESTS.getStringList("TestList");
			sender.sendMessage(m.cl("&7&lAvalible Tests:"));
			for(String s : keys){
				sender.sendMessage(m.cl(" &7- " + s));
			}
		}
		return false;
	}
	

	public static void startTest(Main m, Player p, String test) {
		test = ChatColor.stripColor(test);
		if (Main.TESTS.contains("Tests." + test.toLowerCase())) {
			p.sendMessage(m.cl("&4Error &8&l»&4 Test not found."));
			return;
		}
		p.sendMessage(m.cl("&6Startting the " + test + " test..."));
	}
}
