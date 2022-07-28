package fr.varowz.itemeffect.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.varowz.itemeffect.ActionEffect;
import fr.varowz.itemeffect.Main;

public class onInteract implements Listener {

	private Main main;
	
	public onInteract(Main main) {
		this.main=main;
	}
	
	@EventHandler
	public void onInteraction(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = event.getItem();
		
		if(item==null) {return;}
		
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			
			for(String string: main.getConfig().getConfigurationSection("ItemEffect").getKeys(false)) {
				
				if(main.getConfig().getConfigurationSection("ItemEffect")
						.getString(string + ".Effect.EffectOn") == "RIGHT_CLICK") {
				
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
	
	

}
