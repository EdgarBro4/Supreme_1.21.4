/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.saveddata.SavedData.Factory;

public class LevelCapabilityData extends SavedData {
    public static final String ID = "capabilities";

    private final INBTSerializable<CompoundTag> serializable;
    private CompoundTag capNBT = null;

    public LevelCapabilityData(@Nullable INBTSerializable<CompoundTag> serializable) {
        this.serializable = serializable;
    }

    @Override
    public CompoundTag m_7176_(CompoundTag nbt, HolderLookup.Provider provider) {
        if (serializable != null)
            nbt = serializable.serializeNBT(provider);
        return nbt;
    }

    @Override
    public boolean m_77764_() {
        return true;
    }

    public static LevelCapabilityData compute(DimensionDataStorage data, @Nullable INBTSerializable<CompoundTag> caps) {
        var factory = new Factory<>(
            () -> new LevelCapabilityData(caps),
            (tag, lookup) -> {
                var ret = new LevelCapabilityData(caps);
                if (caps != null)
                    caps.deserializeNBT(lookup, tag);
                return ret;
            },
            null
        );

        return data.m_164861_(factory, ID);
    }
}
