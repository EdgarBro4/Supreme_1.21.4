/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.CompletableFuture;

import static net.minecraftforge.common.Tags.EntityTypes.*;

@ApiStatus.Internal
public final class ForgeEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ForgeEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "forge", existingFileHelper);
    }

    @Override
    public void m_6577_(HolderLookup.Provider lookupProvider) {
        m_206424_(BOSSES)
            .m_255179_(EntityType.f_20565_, EntityType.f_20496_)
            .addOptionalTag(forgeTagKey("bosses"));
        m_206424_(MINECARTS).m_255179_(
            EntityType.f_20469_,
            EntityType.f_20470_,
            EntityType.f_20472_,
            EntityType.f_20473_,
            EntityType.f_20474_,
            EntityType.f_20475_,
            EntityType.f_20471_
        );
        m_206424_(BOATS).m_255179_(
            EntityType.f_349187_,
            EntityType.f_349389_,
            EntityType.f_349597_,
            EntityType.f_349304_,
            EntityType.f_347771_,
            EntityType.f_348265_,
            EntityType.f_347267_,
            EntityType.f_348037_,
            EntityType.f_347886_,
            EntityType.f_349309_,
            EntityType.f_346786_,
            EntityType.f_348421_,
            EntityType.f_346650_,
            EntityType.f_349357_,
            EntityType.f_348306_,
            EntityType.f_348007_,
            EntityType.f_347078_,
            EntityType.f_349364_
        );
        m_206424_(CAPTURING_NOT_SUPPORTED);
        m_206424_(TELEPORTING_NOT_SUPPORTED);

        // Backwards compat definitions for pre-1.21 legacy `forge:` tags.
        // TODO: Remove backwards compat tag entries in 1.22
        m_206424_(forgeTagKey("bosses")).m_255179_(EntityType.f_20565_, EntityType.f_20496_);
    }

    private static TagKey<EntityType<?>> forgeTagKey(String path) {
        return EntityTypeTags.create(ResourceLocation.m_339182_("forge", path));
    }

    @Override
    public String m_6055_() {
        return "Forge EntityType Tags";
    }
}
