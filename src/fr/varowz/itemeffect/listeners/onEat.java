package fr.varowz.itemeffect.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.varowz.itemeffect.Main;

public class onEat implements Listener {
	
	private Main main;
	
	public onEat(Main main) {
		this.main=main;
	}

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		
		for(String string: main.getConfig().getConfigurationSection("ItemEffect").getKeys(false)) {
			
			if(main.getConfig().getConfigurationSection("ItemEffect")
					.getString(string + ".Effect.EffectOn") == "EAT") {
			
				Bukkit.broadcastMessage("ok");
				
				if(item.getType() == Material.valueOf(main.getConfig().getConfigurationSection("ItemEffect")
						.getString(string + ".Material"))
						&& item.hasItemMeta()
						&& item.getItemMeta().hasDisplayName()
						&& item.getItemMeta().getDisplayName() == main.getConfig().getConfigurationSection("ItemEffect")
						.getString(string+".Name")) {
					
					player.removePotionEffect(PotionEffectType.getByName(main.getConfig().getConfigurationSection("ItemEffect")
							.getString(string+".Effect.Type")));
					
					player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(main.getConfig().getConfigurationSection("ItemEffect")
							.getString(string+".Effect.Type")),
							20*main.getConfig().getConfigurationSection("ItemEffect").getInt(string+".Effect.Duration"),
							main.getConfig().getConfigurationSection("ItemEffect").getInt(string+".Effect.Power")-1));
				}
			}
		}
	}

}
