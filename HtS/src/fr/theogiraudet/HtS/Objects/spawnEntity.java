package fr.theogiraudet.HtS.Objects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import fr.theogiraudet.HtS.HtS;

public class spawnEntity {
	
	private static HtS main;

	public spawnEntity(HtS htS) {
		spawnEntity.main = htS;
	}
	
	public static void summon(EntityType entity, World w, int x, int y, int z) {
		w.spawnEntity(new Location(w,x,y,z), entity);
	}
	
	public static void summon(EntityType entity, World w, int x, int y, int z, String name, Boolean visible) {
		Entity e = w.spawnEntity(new Location(w,x,y,z), entity);
		e.setCustomName(name);
		e.setCustomNameVisible(visible);
	}
	
	public static void summon(Boolean glow, EntityType entity, World w, int x, int y, int z) {
		Entity e = w.spawnEntity(new Location(w,x,y,z), entity);
		e.setGlowing(glow);
	}
	
	public static void summon(EntityType entity, Boolean gravity,World w, int x, int y, int z) {
		Entity e = w.spawnEntity(new Location(w,x,y,z), entity);
		e.setGravity(gravity);
	}
	
	public static void summon(EntityType entity, World w, Boolean god, int x, int y, int z) {
		Entity e = w.spawnEntity(new Location(w,x,y,z), entity);
		e.setInvulnerable(god);
	}
	
	public static void summon(EntityType entity, World w, int x, int y, int z, Boolean silent) {
		Entity e = w.spawnEntity(new Location(w,x,y,z), entity);
		e.setSilent(silent);
	}
	
	public static void summon(EntityType entity, World w,int x, int y, int z, Player p) {
		Entity e = w.spawnEntity(new Location(w,x,y,z), entity);
		e.setMetadata(p.getName(), new FixedMetadataValue(main, "null"));
	}
	
	public static void summon(EntityType entity, World w, int x, int y, int z, Player p, String name, Boolean visible, Boolean glow, Boolean gravity, Boolean god, Boolean silent) {
		Entity e = w.spawnEntity(new Location(w,x,y,z), entity);
		e.setCustomName(name);
		e.setCustomNameVisible(visible);
		e.setGlowing(glow);
		e.setGravity(gravity);
		e.setInvulnerable(god);
		e.setSilent(silent);
		e.setMetadata(p.getName(), new FixedMetadataValue(main, "null"));		
	}
	
	public static void summon(EntityType entity, World w, Location location) {
		w.spawnEntity(location, entity);
	}
	
	public static void summon(EntityType entity, World w, Location location, String name, Boolean visible) {
		Entity e = w.spawnEntity(location, entity);
		e.setCustomName(name);
		e.setCustomNameVisible(visible);
	}
	
	public static void summon(Boolean glow, EntityType entity, World w, Location location) {
		Entity e = w.spawnEntity(location, entity);
		e.setGlowing(glow);
	}
	
	public static void summon(EntityType entity, Boolean gravity,World w, Location location) {
		Entity e = w.spawnEntity(location, entity);
		e.setGravity(gravity);
	}
	
	public static void summon(EntityType entity, World w, Boolean god, Location location) {
		Entity e = w.spawnEntity(location, entity);
		e.setInvulnerable(god);
	}
	
	public static void summon(EntityType entity, World w, Location location, Boolean silent) {
		Entity e = w.spawnEntity(location, entity);
		e.setSilent(silent);
	}
	
	public static void summon(EntityType entity, World w, Location location, Player p) {
		Entity e = w.spawnEntity(location, entity);
		e.setMetadata(p.getName(), new FixedMetadataValue(main, "null"));
	}
	
	public static void summon(EntityType entity, World w, Location location, Player p, String name, Boolean visible, Boolean glow, Boolean gravity, Boolean god, Boolean silent) {
		Entity e = w.spawnEntity(location, entity);
		e.setCustomName(name);
		e.setCustomNameVisible(visible);
		e.setGlowing(glow);
		e.setGravity(gravity);
		e.setInvulnerable(god);
		e.setSilent(silent);
		e.setMetadata(p.getName(), new FixedMetadataValue(main, "null"));		
	}
}
