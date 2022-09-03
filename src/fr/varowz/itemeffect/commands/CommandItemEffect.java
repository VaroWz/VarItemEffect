package fr.varowz.itemeffect.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.varowz.itemeffect.Main;

public class CommandItemEffect implements CommandExecutor {

	private Main main;
	
	public CommandItemEffect(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("itemeffect")) {
			
			if(sender.hasPermission("itemeffect.commands")) {
				if(args.length == 0) {
					HelpCommand(sender);
				}
				if(args.length == 1) {
					String arg = args[0];
					
					if(arg.equalsIgnoreCase("reload") || arg.equalsIgnoreCase("rl")) {
						
						if(sender.hasPermission("itemeffect.reload")) {
							
							main.reloadConfig();
							sender.sendMessage("§aConfig reloaded !");
							
						}
						else {
							sender.sendMessage(main.getConfig().getString("Lang.NoPerm").replace("&", "§"));
						}
					}
					else if(arg.equalsIgnoreCase("give")) {
						HelpCommand(sender);
					}
					else if(arg.equalsIgnoreCase("list")) {
						ListCommand(sender);
					}
					else {
						HelpCommand(sender);
					}
				}
				if(args.length == 2) {
					HelpCommand(sender);
					
				}
				if(args.length == 3) {
					String arg = args[0];
					String arg2 = args[1];
					String arg3 = args[2];
					
					if(arg.equalsIgnoreCase("give")) {
						
						if(sender.hasPermission("itemeffect.give")) {
							if(Bukkit.getPlayer(arg2) != null) {
							
								for(String string: main.getConfig().getConfigurationSection("ItemEffect").getKeys(false)) {
									
									if(String.valueOf(arg3).equals(string)) {
										Player player = Bukkit.getPlayer(arg2);
										GiveItemEffect(player, String.valueOf(arg3), 1);
										player.sendMessage(main.getConfig().getString("Lang.ReceiveItemEffect")
												.replace("&", "§")
												.replace("{ammount}", "1")
												.replace("{itemname}", arg3));
										sender.sendMessage("§fVous avez donné 1 §a" + arg3 + " §fà §a" + arg2);
									}
									else {
										sender.sendMessage("bug");
									}
								}
							}
						}
						else {
							sender.sendMessage(main.getConfig().getString("Lang.NoPerm").replace("&", "§"));
						}
					}
				}
				if(args.length == 4) {
					String arg = args[0];
					String arg2 = args[1];
					String arg3 = args[2];
					String arg4 = args[3];
					
					if(arg.equalsIgnoreCase("give")) {
						
						if(sender.hasPermission("itemeffect.give")) {
							if(Bukkit.getPlayer(arg2) != null) {
							
								for(String string: main.getConfig().getConfigurationSection("ItemEffect").getKeys(false)) {
									
									if(String.valueOf(arg3).equals(string)) {
										Player player = Bukkit.getPlayer(arg2);
										GiveItemEffect(player, String.valueOf(arg3), Integer.valueOf(arg4));
										player.sendMessage(main.getConfig().getString("Lang.ReceiveItemEffect")
												.replace("&", "§")
												.replace("{ammount}", arg4)
												.replace("{itemname}", arg3));
										sender.sendMessage("§fVous avez donné §a" + arg4 + "  §a" + arg3 + " §fà §a" + arg2);
									}
									else {
										sender.sendMessage("bug");
									}
								}
							}
						}
						else {
							sender.sendMessage(main.getConfig().getString("Lang.NoPerm").replace("&", "§"));
						}
					}
				}
			}
			else {
				sender.sendMessage(main.getConfig().getString("Lang.NoPerm").replace("&", "§"));
			}
		}
		return false;
	}
	
	public void GiveItemEffect(Player player, String itemname, int ammount) {
		
		ItemStack item = new ItemStack(Material.valueOf(main.getConfig().getString("ItemEffect."+itemname+".Material").replace("&", "§")));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(main.getConfig().getString("ItemEffect."+itemname+".Name").replace("&", "§"));
		List<String> lore = main.getConfig().getStringList("ItemEffect."+itemname+".Lore");
		List<String> loree = new ArrayList<String>();
		for(int i = 0; i < lore.size(); i++) {
			String line = lore.get(i).replace("&", "§");
			
			loree.add(line);
		}
		meta.setLore(loree);
		
		if(main.getConfig().getBoolean("ItemEffect."+itemname+".Glow") == true) {
			meta.addEnchant(Enchantment.ARROW_INFINITE, 0, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		item.setItemMeta(meta);
		
		for(int i =0; i<ammount; i++) {
			player.getInventory().addItem(item);
		}
	}
	
	public void ListCommand(CommandSender sender) {
		sender.sendMessage("§9§m-------LIST-------");
		
		for(String string: main.getConfig().getConfigurationSection("ItemEffect").getKeys(false)) {
			
			sender.sendMessage("§f"+string+": "+ main.getConfig().getString("ItemEffect."+string+".Name").replace("&", "§"));
			
		}
		sender.sendMessage("§9§m------------------");
	}
	
	public void HelpCommand(CommandSender sender) {
		sender.sendMessage("§9§m--------------------------------");
		sender.sendMessage("§3/itemeffect §bsee all commands.");
		sender.sendMessage("§3/itemeffect give <player> <itemname> [number] §bGive item to a player.");
		sender.sendMessage("§3/itemeffect list §bSee all itemeffects.");
		sender.sendMessage("§3/itemeffect reload §bReload config.");
		sender.sendMessage("§9§m--------------------------------");
	}

}
