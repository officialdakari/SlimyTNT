package ru.officialdakari.slimytnt;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import ru.officialdakari.slimytnt.handlers.AnyBlockBreakHandler;
import ru.officialdakari.slimytnt.handlers.PlayerBlockPlaceHandler;

public class CustomTNTItem extends SlimefunItem {

    private String headHash;
    private Integer power;
    private Boolean fire;
    private Integer fuse;

    public CustomTNTItem(ItemGroup itemGroup, String head, Integer power, Integer fuse, Boolean fire, ItemStack[] recipe) {
        super(itemGroup, new SlimefunItemStack("TNT_X" + String.valueOf(power), Utils.headItemFromTexture(head), "&fTNT x" + String.valueOf(power)), RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.headHash = head;
        this.power = power;
        this.fire = fire;
        this.fuse = fuse;
    }

    @Override
    public void preRegister() {
        PlayerBlockPlaceHandler placeHandler = new PlayerBlockPlaceHandler(this::onPlaced);
        BlockUseHandler useHandler = this::onBlockUse;
        AnyBlockBreakHandler breakHandler = new AnyBlockBreakHandler(this::onBroken);

        addItemHandler(placeHandler, breakHandler, useHandler);
    }

    public void onBlockUse(PlayerRightClickEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        Optional<Block> optionalBlock = e.getClickedBlock();

        if (optionalBlock.isEmpty()) {
            return;
        }

        Block block = optionalBlock.get();

        e.cancel();

        if (!Slimefun.getProtectionManager().hasPermission(p, block, Interaction.INTERACT_BLOCK)) {
            return;
        }

        Boolean ignite = false;
        if (item.getType() == Material.FLINT_AND_STEEL) {
            if (p.getGameMode() != GameMode.CREATIVE) {
                item.setDurability((short) (item.getDurability() - (short) 1));
            }
            ignite = true;
        } else if (item.getType() == Material.FIRE_CHARGE) {
            if (p.getGameMode() != GameMode.CREATIVE) {
                item.setAmount(item.getAmount() - 1);
            }
            ignite = true;
        }
        if (ignite) {
            String strUUID = BlockStorage.getLocationInfo(block.getLocation(), "fakeBlockDisplay");
            if (strUUID == null) {
                return;
            }
            UUID uuid = UUID.fromString(strUUID);
            Entity entity = block.getWorld().getEntity(uuid);
            if (entity != null) {
                entity.remove();
            }
            block.setType(Material.AIR);
            BlockStorage.clearBlockInfo(block.getLocation(), ignite);
            TNTPrimed tnt = (TNTPrimed) p.getWorld().spawnEntity(block.getLocation().add(0.5, 0.5, 0.5), EntityType.TNT);
            tnt.setFuseTicks(fuse * 20);
            tnt.setMetadata("slimytnt", new FixedMetadataValue(SlimyTNTPlugin.plugin, this.power));
        }
    }

    public void onPlaced(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();
        World world = block.getWorld();
        Location loc = block.getLocation();

        block.setType(Material.CHEST);

        ItemDisplay bd = (ItemDisplay) world.spawnEntity(new Location(world, loc.getBlockX(), loc.getBlockY() + 0.5, loc.getBlockZ()), EntityType.ITEM_DISPLAY);
        // BlockData blockData = Material.PLAYER_HEAD.createBlockData();
        // Skull skull = (Skull) blockData.createBlockState();

        // skull.setBlockData(blockData);
        // Utils.applyHeadTexture(skull, headHash, null);
        bd.setItemStack(Utils.headItemFromTexture(headHash));

        bd.setTransformation(new Transformation(new Vector3f(0.5f, 0.5f, 0.5f), new AxisAngle4f(), new Vector3f(2, 2, 2), new AxisAngle4f()));
        bd.setMetadata("slimytnt_blockv", new FixedMetadataValue(SlimyTNTPlugin.plugin, String.valueOf(loc.getX()) + ":" + String.valueOf(loc.getY()) + ":" + String.valueOf(loc.getZ())));

        BlockStorage.addBlockInfo(loc, "fakeBlockDisplay", bd.getUniqueId().toString());
    }

    public void onBroken(Block block) {
        String strUUID = BlockStorage.getLocationInfo(block.getLocation(), "fakeBlockDisplay");
        if (strUUID == null) {
            return;
        }
        UUID uuid = UUID.fromString(strUUID);
        Entity entity = block.getWorld().getEntity(uuid);
        if (entity != null) {
            entity.remove();
        }
    }
}
