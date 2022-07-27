package fr.varowz.itemeffect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.varowz.itemeffect.commands.CommandItemEffect;

public class Main extends JavaPlugin{
	
	private String prefix;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		setPrefix(getConfig().getString("Lang.Prefix").replace("&", "�"));
		
		getCommand("itemeffect").setExecutor(new CommandItemEffect(this));
		
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "�aPlugin �7[�e1.0.0�7] �ais online.");
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "�aPlugin �7[�e1.0.0�7] �cDev by VaroWz.");
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "�aPlugin �7[�e1.0.0�7] �9Discord: https://discord.gg/SbKrKehCpq.");
		
	}
	
	
	@Override
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage(getPrefix() + "�cPlugin �7[�e1.0.4�7] �cis offline.");
		
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}