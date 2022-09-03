package fr.varowz.itemeffect.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
						.getString(string + ".Effect.EffectOn").equalsIgnoreCase("RIGHT_CLICK")) {
					
					if(item.getType() == Material.valueOf(main.getConfig().getString("ItemEffect."+string + ".Material"))
							&& item.hasItemMeta()
							&& item.getItemMeta().hasDisplayName()
							&& item.getItemMeta().getDisplayName().equalsIgnoreCase(main.getConfig().getString("ItemEffect."+string+".Name").replace("&", "§"))) {
						
						player.removePotionEffect(PotionEffectType.getByName(main.getConfig().getConfigurationSection("ItemEffect")
								.getString(string+".Effect.Type")));
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(main.getConfig().getString("ItemEffect."+string+".Effect.Type")),
								20*main.getConfig().getInt("ItemEffect."+string+".Effect.Duration"),
								main.getConfig().getInt("ItemEffect."+string+".Effect.Power")-1));
						
						if(item.getAmount() > 1){
							item.setAmount(item.getAmount() -1);
						}
						else {
							item.setType(Material.LEATHER);
							player.setItemInHand(new ItemStack(Material.AIR));
						}
							
					}
				}
			}
		}
	}
}
