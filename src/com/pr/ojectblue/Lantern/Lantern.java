package com.pr.ojectblue.Lantern;


import java.util.HashMap;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;


import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
public class Lantern extends JavaPlugin implements Listener {
 
	public static HashMap<Player, Boolean> pluginEnabled = new HashMap<Player, Boolean>();
	public static HashMap<Player, Location> previousLoc = new HashMap<Player, Location>();
	public static HashMap<Player, Integer> previousBlock = new HashMap<Player, Integer>();
	public static HashMap<Player, Byte> previousBlockData = new HashMap<Player, Byte>();
	public static HashMap<Player, Sign> signData = new HashMap<Player, Sign>();

	public void onPlayerJoin(PlayerJoinEvent evt){
		Lantern.pluginEnabled.put(evt.getPlayer(), true);
	}

	public void onPlayerQuit(PlayerQuitEvent evt){
		Player p = evt.getPlayer();
		Location l = p.getLocation();
		l.setY(l.getY() - 1);
		if(l.getWorld().getBlockAt(l).getType() == Material.GLOWSTONE){
			if(Lantern.pluginEnabled.get(p)){
				l.getWorld().getBlockAt(l).setTypeId(previousBlock.get(p));
			}
		}
	}

	
	public void onEnable(){
		getLogger().info("Lantern 0.1 has been enabled!");
		getServer().getPluginManager().registerEvents(this, this);
	}
 
	public void onDisable(){
		getLogger().info("Lantern 0.1 has been disabled.");
	}  
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent evt) {
		Location loc = evt.getPlayer().getLocation();
		World w = loc.getWorld();
		
		
		/*
		loc.setY(loc.getY() -1);
		Block b = w.getBlockAt(loc);
		//b.setTypeId(89);
		if(b.getType()!=Material.AIR)
		{
			evt.getPlayer().sendBlockChange(loc, 89,(byte)0);
		}
	
	
		*/
	


		//if(TheFlashlight.pluginEnabled.get(evt.getPlayer())){

			

				Player p = evt.getPlayer();
				if (p.hasPermission("lantern.use"))
				{
				
				loc.setY(loc.getY() - 1);
				
				
				//Added this bit in to make the lantern still work while jumping or falling.
				while (w.getBlockAt(loc).getType()==Material.AIR)
				{
					//getLogger().info("In loop");
					loc.setY(loc.getY() - 1);
				}
				
				try { 
					 
				if(previousLoc.containsKey(p)){
					if(!signData.containsKey(p)){
						p.sendBlockChange(previousLoc.get(p), previousBlock.get(p), (byte)previousBlockData.get(p));
					} else {
						Sign oldSign = signData.get(p);

						Sign newSign = (Sign) w.getBlockAt(previousLoc.get(p)).getState();
						p.sendBlockChange(previousLoc.get(p), previousBlock.get(p), (byte)previousBlockData.get(p));
						for(int i = 0; i <= 3; i++){
							newSign.setLine(i, oldSign.getLine(i));newSign.update();
						}
						signData.remove(p);
					}
				}		
				}
				catch (ClassCastException e)
				{
					
					
				}


				if(w.getBlockAt(loc).getType() != Material.AIR && w.getBlockAt(loc).getType() != Material.WATER){

					if(w.getBlockAt(loc).getTypeId() == 323 || w.getBlockAt(loc).getTypeId() == 68 || w.getBlockAt(loc).getType() == Material.SIGN || w.getBlockAt(loc).getType() == Material.SIGN_POST){
						signData.put(p, (Sign)w.getBlockAt(loc).getState());
					}
					
					try { 
						 
					
					
			
						if (p.getInventory().getHelmet().getTypeId() == Material.GOLD_HELMET.getId())
						{
							previousBlock.put(p, w.getBlockAt(loc).getTypeId());
							previousBlockData.put(p, w.getBlockAt(loc).getData());
							previousLoc.put(p, loc);
					
							p.sendBlockChange(loc, Material.GLOWSTONE, (byte)0);
						}
					}
						catch (NullPointerException e) {
							  // ...
							}
					}
				}
		//}
	

}
	
	
	
	
	
	
	
	
}


