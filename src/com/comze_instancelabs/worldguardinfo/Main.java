package com.comze_instancelabs.worldguardinfo;

/**
 * 
 * @author instancelabs
 *
 */

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Main extends JavaPlugin implements Listener{
	WorldGuardPlugin worldGuard = null;
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		worldGuard = (WorldGuardPlugin) getWorldGuard();
	}
	
	public Plugin getWorldGuard(){
    	return Bukkit.getPluginManager().getPlugin("WorldGuard");
    }
	
	@EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        if (event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().hasPermission("worldguardinfo.use") && event.getPlayer().getItemInHand().getType() == Material.SPIDER_EYE)
        {
        	ApplicableRegionSet set = WGBukkit.getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(event.getClickedBlock().getLocation());
    		String rg = "";
    		for (ProtectedRegion region : set) {
    		    rg = region.getId();
    		}
    		if(ProtectedRegion.isValidId(rg) && worldGuard.getRegionManager(event.getPlayer().getWorld()).getRegion(rg) != null){
    			ProtectedRegion t = worldGuard.getRegionManager(event.getPlayer().getWorld()).getRegion(rg);
    			Player p = event.getPlayer();
    			p.sendMessage("§3You requested more information for " + rg);
    			// priority
    			p.sendMessage("§2Priority: " + Integer.toString(t.getPriority()));
    			// members
    			ArrayList<String> members = new ArrayList<String>();
    			members.addAll(t.getMembers().getPlayers());
    			String allmembers = "";
    			for(String member : members){
    				allmembers += member + ",";
    			}
    			if(allmembers.length() > 0){
    				allmembers = allmembers.substring(0, allmembers.length() - 1);
    			}
    			p.sendMessage("§2Members: " + allmembers);
    			// owners
    			ArrayList<String> owners = new ArrayList<String>();
    			owners.addAll(t.getOwners().getPlayers());
    			String allowners = "";
    			for(String owner : owners){
    				allowners += owner + ",";
    			}
    			if(allowners.length() > 0){
    				allowners = allowners.substring(0, allowners.length() - 1);
    			}
    			p.sendMessage("§2Owners: " + allowners);
    			// flag
    			ArrayList<Flag> flags_keys = new ArrayList<Flag>();
    			ArrayList<Object> flags_values = new ArrayList<Object>();
    			flags_keys.addAll(t.getFlags().keySet());
    			flags_values.addAll(t.getFlags().values());
    			String allflags = "";
    			int count = 0;
    			p.sendMessage("§2Flags: ");
    			for(Flag flag : flags_keys){
    				//allflags += flag.getName() +  ": " + flags_values.get(count).toString() + ",";
    				//count += 1;
    				p.sendMessage("§5" + flag.getName() + ": " + flags_values.get(count).toString());
    				count += 1;
    			}
    			//if(allflags.length() > 0){
    			//	allflags = allflags.substring(0, allflags.length() - 1);
    			//}
    			//p.sendMessage("§2Flags: " + allflags);
    		}else{
    			
    		}
        }
    }
}
