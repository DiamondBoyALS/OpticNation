package ml.optidevs.nation.signs;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import ml.optidevs.nation.Main;

public class Tests implements Listener {
	static Main m;
	FileConfiguration con;
	public YamlConfiguration TESTS;
	public static File TESTS_FILE;

	public Tests(Main main) {
		m = main;
		con = m.getConfig();

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
			return;
		}
		Block block = e.getClickedBlock();
		Player p = e.getPlayer();
		Material myBlock = block.getType();
		if (myBlock.equals(Material.SIGN_POST) || myBlock.equals(Material.WALL_SIGN)) {
			Sign sign = (Sign) block.getState();
			String[] ln = sign.getLines();
			if (ln[1].equals(m.cl("&4&l»&4 Error &4&l«"))) {
				p.sendMessage(
						m.cl("&4Error &8&l»&4 This sign has errors! Please contact a staff member about this issue."));
			} else if (ln[1].equals(m.cl("&6&l»&b Test &6&l«"))) {
				ml.optidevs.nation.commands.Tests.startTest(m, p, ln[2]);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSignEdit(SignChangeEvent e) {
		String[] arr = { e.getLine(0), e.getLine(1), e.getLine(2), e.getLine(3) };

		if (arr[0].equalsIgnoreCase("[test]") || arr[0].equalsIgnoreCase("[tests]")) {
			e.setLine(0, "");
			e.setLine(1, m.cl("&6&l»&b Test &6&l«"));
			e.setLine(2, "");
			e.setLine(3, "");
			if (arr[1].length() <= 0) {
				e.setLine(1, m.cl("&4&l»&4 Error &4&l«"));
				e.setLine(2, m.cl("&4No Test Specified!"));
				return;
			}
			if (Main.TESTS.contains("Tests." + arr[1].toLowerCase())) {
				if (Main.TESTS.getBoolean("Tests." + arr[1].toLowerCase() + ".Enabled")) {
					e.setLine(2, m.cl(Main.TESTS.getString("Tests." + arr[1].toLowerCase() + ".Name")));
					e.setLine(3, m.cl(Main.TESTS.getString("Tests." + arr[1].toLowerCase() + ".Note")));
				} else {
					e.setLine(1, m.cl("&4&l»&4 Error &4&l«"));
					e.setLine(2, m.cl("&4Test Disabled!"));
				}
			} else {
				e.setLine(1, m.cl("&4&l»&4 Error &4&l«"));
				e.setLine(2, m.cl("&4Test not found!"));
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onSignBreak(BlockBreakEvent e) {
		Block block = e.getBlock();
		Player p = e.getPlayer();
		Material myBlock = block.getType();
		if (myBlock.equals(Material.SIGN_POST) || myBlock.equals(Material.WALL_SIGN)) {
			Sign sign = (Sign) block.getState();
			String[] ln = sign.getLines();
			if (ln[1].equals(m.cl("&4&l»&4 Error &4&l«"))) {
				if (p.isSneaking()) {
					p.sendMessage(m.cl("&6Sign deleted!"));
					e.setCancelled(false);
					return;
				} else {
					e.setCancelled(true);
					p.sendMessage(m.cl(
							"&4Error &8&l»&4 This sign has errors! Please contact a staff member about this issue."));
				}
			} else if (ln[1].equals(m.cl("&6&l»&b Test &6&l«"))) {
				if (p.isSneaking()) {
					p.sendMessage(m.cl("&6Sign deleted!"));
					e.setCancelled(false);
					return;
				} else {
					e.setCancelled(true);
					ml.optidevs.nation.commands.Tests.startTest(m, p, ln[2]);
				}
			}
		}
	}

}
