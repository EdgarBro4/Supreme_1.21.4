/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.minecraftforge.common.Tags.Fluids.*;

@ApiStatus.Internal
public final class ForgeFluidTagsProvider extends FluidTagsProvider {
    public ForgeFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "forge", existingFileHelper);
    }

    @Override
    public void m_6577_(HolderLookup.Provider lookupProvider) {
        m_206424_(WATER).m_255245_(net.minecraft.world.level.material.Fluids.f_76193_).m_255245_(net.minecraft.world.level.material.Fluids.f_76192_);
        m_206424_(LAVA).m_255245_(net.minecraft.world.level.material.Fluids.f_76195_).m_255245_(net.minecraft.world.level.material.Fluids.f_76194_);
        m_206424_(MILK).m_176839_(ForgeMod.MILK.getId()).m_176839_(ForgeMod.FLOWING_MILK.getId());
        m_206424_(GASEOUS);
        m_206424_(HONEY);
        m_206424_(POTION);
        m_206424_(SUSPICIOUS_STEW);
        m_206424_(MUSHROOM_STEW);
        m_206424_(RABBIT_STEW);
        m_206424_(BEETROOT_SOUP);
        m_206424_(HIDDEN_FROM_RECIPE_VIEWERS);

        // Backwards compat definitions for pre-1.21 legacy `forge:` tags.
        // TODO: Remove backwards compat tag entries in 1.22
        m_206424_(forgeTagKey("milk"))
                .m_176839_(ForgeMod.MILK.getId())
                .m_176839_(ForgeMod.FLOWING_MILK.getId());
    }

    private static TagKey<Fluid> forgeTagKey(String path) {
        return FluidTags.create(ResourceLocation.m_339182_("forge", path));
    }

    @Override
    public String m_6055_() {
        return "Forge Fluid Tags";
    }
}
