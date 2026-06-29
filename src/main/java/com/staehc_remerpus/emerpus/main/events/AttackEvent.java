package com.staehc_remerpus.emerpus.main.events;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

public class AttackEvent extends Event {
    public final Entity target;

    public AttackEvent(Entity target) {
        this.target = target;
    }
}