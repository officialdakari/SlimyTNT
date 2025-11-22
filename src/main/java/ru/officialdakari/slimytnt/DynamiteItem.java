/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.officialdakari.slimytnt;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import net.kyori.adventure.util.TriState;

/**
 *
 * @author officialdakari
 */
public class DynamiteItem extends SlimefunItem {

    private Integer power;
    private Boolean fire;

    public DynamiteItem(ItemGroup itemGroup, Material m, Integer power, Boolean fire, ItemStack[] recipe) {
        super(itemGroup, new SlimefunItemStack("DYNAMITE_X" + String.valueOf(power), m, "&fDynamite x" + String.valueOf(power)), RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.power = power;
        this.fire = fire;
    }

    @Override
    public void preRegister() {
        addItemHandler((ItemUseHandler)this::onUse);
    }

    public void onUse(PlayerRightClickEvent event) {
        Player p = event.getPlayer();
        Location loc = p.getLocation();
        if (p.getGameMode() != GameMode.CREATIVE) {
            event.getItem().add(-1);
        }
        Snowball tnt = (Snowball) p.getWorld().spawnEntity(p.getLocation().clone().add(0, 1.5, 0).add(loc.getDirection().multiply(0.5)), EntityType.SNOWBALL);
        tnt.setVisualFire(TriState.TRUE);
        tnt.setBounce(true);
        tnt.setGravity(true);
        tnt.setVelocity(loc.getDirection().multiply(0.6));
        tnt.setMetadata("slimydynamite", new FixedMetadataValue(SlimyTNTPlugin.plugin, this.power));
    }
}
