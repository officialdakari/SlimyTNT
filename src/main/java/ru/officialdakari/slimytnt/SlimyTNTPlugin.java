package ru.officialdakari.slimytnt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import ru.officialdakari.slimytnt.handlers.EventListener;

public class SlimyTNTPlugin extends JavaPlugin implements SlimefunAddon {

    Config config;
    static SlimyTNTPlugin plugin;

    @Override
    public void onEnable() {
        config = new Config(this);

        plugin = this;

        // itemMetaPortalGunId = new NamespacedKey(this, "unique_portals_id");
        // itemMetaPortalGunColor1 = new NamespacedKey(this, "pg_1color");
        // itemMetaPortalGunColor2 = new NamespacedKey(this, "pg_2color");

        NamespacedKey categoryId = new NamespacedKey(this, "toomuchtnt");
        ItemStack categoryItem = CustomItemStack.create(Material.TNT, "&cTooMuchTNT");

        ItemGroup itemGroup = new ItemGroup(categoryId, categoryItem);
        
        SlimefunItem tntx5 = new CustomTNTItem(itemGroup, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmJkMDU2ZThmYzVhNTBkYWQxNzRjYzc4ZjU1ZmQwODBiMjk0Y2QyMmUwN2MxMzQ3OTAyMDVjOGY0YWJhZWMyYiJ9fX0=", 5, 6, false, new ItemStack[] {
            new ItemStack(Material.TNT, 4)
        });
        
        SlimefunItem tntx20 = new CustomTNTItem(itemGroup, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFhZTJiNjkxYjlhOTU3N2MzNWU0ZDg4NTQ0M2JlYzk1NzNlN2M3YzNkNDYxOWM4ZjljN2VlOWU1MDIxIn19fQ==", 20, 10, false, new ItemStack[] {
            tntx5.getItem().asQuantity(4)
        });
        
        SlimefunItem tntx80 = new CustomTNTItem(itemGroup, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNlNmQ4MjE4NmEzNzdmNTQzNWU5ODZkMWY5NmIwNDY5MTk3MTM4NjFiYmJiZGVlMzBlYWRiODMxYzI0In19fQ==", 80, 15, false, new ItemStack[] {
            tntx20.getItem().asQuantity(4)
        });

        tntx5.register(this);
        tntx20.register(this);
        tntx80.register(this);

        SlimefunItem dynax1 = new DynamiteItem(itemGroup, Material.RED_DYE, 1, false, new ItemStack[] {
            new ItemStack(Material.TNT)
        });

        SlimefunItem dynax5 = new DynamiteItem(itemGroup, Material.BLUE_DYE, 5, false, new ItemStack[] {
            tntx5.getItem().asOne()
        });

        dynax1.register(this);
        dynax5.register(this);

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        
    }

    @Override
    public void onDisable() {

    }

    @Override
    public JavaPlugin getJavaPlugin() {
        // This is a method that links your SlimefunAddon to your Plugin.
        // Just return "this" in this case, so they are linked
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        // Here you can return a link to your Bug Tracker.
        // This link will be displayed to Server Owners if there is an issue
        // with this Addon. Return null if you have no bug tracker.
        // Normally you can just use GitHub's Issues tab:
        // https://github.com/YOURNAME/YOURPROJECT/issues
        return null;
    }
}
