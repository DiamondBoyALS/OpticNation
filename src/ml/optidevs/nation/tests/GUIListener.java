package ml.optidevs.nation.tests;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ml.optidevs.nation.Main;

public class GUIListener implements Listener {
	public static ml.optidevs.nation.tests.Test[] testInstances = {new ml.optidevs.nation.tests.Test()};

	public static void startTest(Main m, Player p, String test) {
		test = ChatColor.stripColor(test);
		if (Main.TESTS.contains("Tests." + test.toLowerCase())) {
			// p.sendMessage(m.cl("&4Error &8&l»&4 Test not found."));
			// return;
		}
		p.sendMessage(m.cl("&6Startting the " + test + " test..."));
		// format: null, size of inventory (must be divisible by 9), "GUI name"

		int til = 0;
		if (testInstances.length > 0) {
			til = 0;
		} else {
			til = testInstances.length;
		}
		testInstances[til] = new ml.optidevs.nation.tests.Test(p, test);
	}

	@EventHandler
	public void testInteract(InventoryClickEvent e) {
		if (e.getInventory().getName().contains("Test")) {
			e.setCancelled(true);
			for (int i = 0; i < testInstances.length; i++) {
				testInstances[i].choice(e.getSlot(), (Player) e.getWhoClicked());
			}
		}
	}

	public class GUIItem {
		int loc;
		ItemStack item;

		public GUIItem(int Location, ItemStack itemstack) {
			loc = Location;
			item = itemstack;
		}

		public ItemStack getItem() {
			return item;
		}

		public int getLocation() {
			return loc;
		}
	}

}
