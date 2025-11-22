package ru.officialdakari.slimytnt.handlers;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;

public class AnyBlockBreakHandler extends BlockBreakHandler {

    Consumer<Block> handler;

    public AnyBlockBreakHandler(Consumer<Block> handler) {
        super(true, true);
        this.handler = handler;
    }

    @Override
    public void onAndroidBreak(AndroidMineEvent event) {
        this.handler.accept(event.getBlock());
    }

    @Override
    public void onPlayerBreak(BlockBreakEvent event, ItemStack i, List<ItemStack> d) {
        this.handler.accept(event.getBlock());
    }    

    @Override
    public void onExplode(Block b, List<ItemStack> drops) {
        this.handler.accept(b);
    }

    
}