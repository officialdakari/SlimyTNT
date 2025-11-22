package ru.officialdakari.slimytnt.handlers;

import java.util.function.Consumer;

import org.bukkit.event.block.BlockPlaceEvent;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;

public class PlayerBlockPlaceHandler extends BlockPlaceHandler {

    Consumer<BlockPlaceEvent> handler;

    public PlayerBlockPlaceHandler(Consumer<BlockPlaceEvent> handler) {
        super(false);
        this.handler = handler;
    }

    @Override
    public void onPlayerPlace(BlockPlaceEvent bpe) {
        this.handler.accept(bpe);
    }
}