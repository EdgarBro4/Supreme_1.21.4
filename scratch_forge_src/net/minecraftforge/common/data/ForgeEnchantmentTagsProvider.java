/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.CompletableFuture;

import static net.minecraftforge.common.Tags.Enchantments.*;

@ApiStatus.Internal
public final class ForgeEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public ForgeEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "forge", existingFileHelper);
    }

    @Override
    public void m_6577_(HolderLookup.Provider lookupProvider) {
        m_206424_(INCREASE_BLOCK_DROPS)
                .m_255204_(Enchantments.f_316753_);
        m_206424_(INCREASE_ENTITY_DROPS)
                .m_255204_(Enchantments.f_316023_);
        m_206424_(WEAPON_DAMAGE_ENHANCEMENTS)
                .m_255204_(Enchantments.f_44977_)
                .m_255204_(Enchantments.f_44978_)
                .m_255204_(Enchantments.f_44979_)
                .m_255204_(Enchantments.f_314636_)
                .m_255204_(Enchantments.f_44956_);
        m_206424_(ENTITY_SPEED_ENHANCEMENTS)
                .m_255204_(Enchantments.f_44976_)
                .m_255204_(Enchantments.f_220304_)
                .m_255204_(Enchantments.f_44973_);
        m_206424_(ENTITY_AUXILIARY_MOVEMENT_ENHANCEMENTS)
                .m_255204_(Enchantments.f_315602_)
                .m_255204_(Enchantments.f_44974_);
        m_206424_(ENTITY_DEFENSE_ENHANCEMENTS)
                .m_255204_(Enchantments.f_314710_)
                .m_255204_(Enchantments.f_44968_)
                .m_255204_(Enchantments.f_44969_)
                .m_255204_(Enchantments.f_44966_)
                .m_255204_(Enchantments.f_44970_)
                .m_255204_(Enchantments.f_315602_);
    }

    @Override
    public String m_6055_() {
        return "Forge Enchantment Tags";
    }
}
