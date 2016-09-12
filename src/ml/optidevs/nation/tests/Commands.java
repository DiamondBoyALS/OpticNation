package ml.optidevs.nation.tests;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import ml.optidevs.nation.Lang;
import ml.optidevs.nation.Main;

public class Commands implements CommandExecutor {
	static Main m;
	FileConfiguration con;

	public Commands(Main main) {
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
}
