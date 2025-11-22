/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.officialdakari.slimytnt.handlers;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;

/**
 *
 * @author officialdakari
 */
public class EventListener implements Listener {

    @EventHandler
    public void onTnt(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.TNT) {
            List<MetadataValue> meta = entity.getMetadata("slimytnt");
            if (meta.isEmpty()) {
                return;
            }
            Integer power = meta.get(0).asInt();
            event.setCancelled(true);
            entity.getWorld().createExplosion(event.getLocation(), power * 2);
        }
    }

    @EventHandler
    public void onDynamite(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.SNOWBALL) {
            List<MetadataValue> meta = entity.getMetadata("slimydynamite");
            if (meta.isEmpty()) {
                return;
            }
            Integer power = meta.get(0).asInt();
            event.setCancelled(true);
            entity.getWorld().createExplosion(entity.getLocation(), power * 2);
        }
    }

}
