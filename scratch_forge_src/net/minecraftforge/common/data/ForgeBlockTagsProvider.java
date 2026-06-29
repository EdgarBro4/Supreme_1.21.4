/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

// We typically don't do static imports as S2S can't remap them {as they are not qualified}, however this conflicts with vanilla and our tag class names, and our tags don't get obfed so its one line of warning.
import static net.minecraftforge.common.Tags.Blocks.*;

@ApiStatus.Internal
public final class ForgeBlockTagsProvider extends BlockTagsProvider {
    public ForgeBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, "forge", existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void m_6577_(HolderLookup.Provider p_256380_) {
        m_206424_(BARRELS)
                .m_206428_(BARRELS_WOODEN)
                .addOptionalTag(forgeTagKey("barrels"));
        m_206424_(BARRELS_WOODEN)
                .m_255245_(Blocks.f_50618_)
                .addOptionalTag(forgeTagKey("barrels/wooden"));
        m_206424_(BOOKSHELVES)
                .m_255245_(Blocks.f_50078_)
                .addOptionalTag(forgeTagKey("bookshelves"));
        m_206424_(BUDDING_BLOCKS).m_255245_(Blocks.f_152491_);
        m_206424_(BUDS).m_255245_(Blocks.f_152495_).m_255245_(Blocks.f_152494_).m_255245_(Blocks.f_152493_);
        m_206424_(CHAINS).m_255245_(Blocks.f_50184_);
        m_206424_(CHESTS)
                .addTags(CHESTS_ENDER, CHESTS_TRAPPED, CHESTS_WOODEN)
                .addOptionalTag(forgeTagKey("chests"));
        m_206424_(CHESTS_ENDER).m_255245_(Blocks.f_50265_); // forge:chests/ender
        m_206424_(CHESTS_TRAPPED).m_255245_(Blocks.f_50325_); // forge:chests/trapped
        m_206424_(CHESTS_WOODEN)
                .m_255179_(Blocks.f_50087_, Blocks.f_50325_)
                .addOptionalTag(forgeTagKey("chests/wooden"));
        m_206424_(CHORUS_ADDITIONALLY_GROWS_ON)
                .m_206428_(END_STONES);
        m_206424_(CLUSTERS).m_255245_(Blocks.f_152492_);
        m_206424_(COBBLESTONES)
                .addTags(COBBLESTONE_NORMAL, COBBLESTONE_INFESTED, COBBLESTONE_MOSSY, COBBLESTONE_DEEPSLATE)
                .addOptionalTag(forgeTagKey("cobblestone"));
        m_206424_(COBBLESTONE_NORMAL).m_255245_(Blocks.f_50652_); // forge:cobblestone/normal
        m_206424_(COBBLESTONE_INFESTED).m_255245_(Blocks.f_50227_); // forge:cobblestone/infested
        m_206424_(COBBLESTONE_MOSSY).m_255245_(Blocks.f_50079_); // forge:cobblestone/mossy
        m_206424_(COBBLESTONE_DEEPSLATE).m_255245_(Blocks.f_152551_); // forge:cobblestone/deepslate
        m_206424_(CONCRETES).m_255179_(Blocks.f_50542_, Blocks.f_50543_, Blocks.f_50544_, Blocks.f_50545_, Blocks.f_50494_, Blocks.f_50495_, Blocks.f_50496_, Blocks.f_50497_, Blocks.f_50498_, Blocks.f_50499_, Blocks.f_50500_, Blocks.f_50501_, Blocks.f_50502_, Blocks.f_50503_, Blocks.f_50504_, Blocks.f_50505_);
        addColored(DYED, "{color}_banner");
        addColored(DYED, "{color}_bed");
        addColored(DYED, "{color}_candle");
        addColored(DYED, "{color}_carpet");
        addColored(DYED, "{color}_concrete");
        addColored(DYED, "{color}_concrete_powder");
        addColored(DYED, "{color}_glazed_terracotta");
        addColored(DYED, "{color}_shulker_box");
        addColored(DYED, "{color}_stained_glass");
        addColored(DYED, "{color}_stained_glass_pane");
        addColored(DYED, "{color}_terracotta");
        addColored(DYED, "{color}_wall_banner");
        addColored(DYED, "{color}_wool");
        addColoredTags(m_206424_(DYED)::m_206428_, DYED);
        m_206424_(END_STONES).m_255245_(Blocks.f_50259_); // forge:end_stones
        m_206424_(ENDERMAN_PLACE_ON_BLACKLIST); // forge:enderman_place_on_blacklist
        m_206424_(FENCE_GATES).addTags(FENCE_GATES_WOODEN); // forge:fence_gates
        m_206424_(FENCE_GATES_WOODEN).m_255179_(Blocks.f_50192_, Blocks.f_50474_, Blocks.f_50475_, Blocks.f_50476_, Blocks.f_50477_, Blocks.f_50478_, Blocks.f_50665_, Blocks.f_50666_, Blocks.f_220850_, Blocks.f_244313_, Blocks.f_271274_);
        m_206424_(FENCES).addTags(FENCES_NETHER_BRICK, FENCES_WOODEN); // forge:fences
        m_206424_(FENCES_NETHER_BRICK).m_255245_(Blocks.f_50198_); // forge:fences/nether_brick
        m_206424_(FENCES_WOODEN).m_206428_(BlockTags.f_13098_); // forge:fences/wooden
        m_206424_(Tags.Blocks.FLOWERS_SMALL)
                .m_255179_(Blocks.f_50111_, Blocks.f_50112_, Blocks.f_50113_, Blocks.f_50114_, Blocks.f_50115_, Blocks.f_50116_, Blocks.f_50117_, Blocks.f_50118_, Blocks.f_50119_, Blocks.f_50120_, Blocks.f_50121_, Blocks.f_50071_, Blocks.f_50070_, Blocks.f_271329_, Blocks.f_370909_, Blocks.f_371390_)
                .addOptionalTag(BlockTags.f_13037_);
        m_206424_(Tags.Blocks.FLOWERS_TALL)
                .m_255179_(Blocks.f_50355_, Blocks.f_50356_, Blocks.f_50358_, Blocks.f_50357_, Blocks.f_276668_)
                .m_176839_(ResourceLocation.m_340282_("tall_flowers"));
        m_206424_(Tags.Blocks.FLOWERS)
                .m_255179_(Blocks.f_152471_, Blocks.f_152542_, Blocks.f_220831_, Blocks.f_271445_, Blocks.f_50491_, Blocks.f_152540_)
                .addTags(Tags.Blocks.FLOWERS_SMALL, Tags.Blocks.FLOWERS_TALL)
                .addOptionalTag(BlockTags.f_13041_);
        m_206424_(GLASS_BLOCKS)
                .addTags(GLASS_BLOCKS_COLORLESS, GLASS_BLOCKS_CHEAP, GLASS_BLOCKS_TINTED)
                .addOptionalTag(forgeTagKey("glass"));
        m_206424_(GLASS_BLOCKS_COLORLESS)
                .m_255245_(Blocks.f_50058_)
                .addOptionalTag(forgeTagKey("glass/colorless"));
        m_206424_(GLASS_BLOCKS_CHEAP)
                .m_255179_(Blocks.f_50058_, Blocks.f_50147_, Blocks.f_50148_, Blocks.f_50202_, Blocks.f_50203_, Blocks.f_50204_, Blocks.f_50205_, Blocks.f_50206_, Blocks.f_50207_, Blocks.f_50208_, Blocks.f_50209_, Blocks.f_50210_, Blocks.f_50211_, Blocks.f_50212_, Blocks.f_50213_, Blocks.f_50214_, Blocks.f_50215_)
                .addOptionalTag(forgeTagKey("glass/silica"));
        m_206424_(GLASS_BLOCKS_TINTED)
                .m_255245_(Blocks.f_152498_)
                .addOptionalTag(forgeTagKey("glass/tinted"));
        m_206424_(GLASS_PANES)
                .addTags(GLASS_PANES_COLORLESS).m_255179_(Blocks.f_50303_, Blocks.f_50304_, Blocks.f_50305_, Blocks.f_50306_, Blocks.f_50307_, Blocks.f_50361_, Blocks.f_50362_, Blocks.f_50363_, Blocks.f_50364_, Blocks.f_50365_, Blocks.f_50366_, Blocks.f_50367_, Blocks.f_50368_, Blocks.f_50369_, Blocks.f_50370_, Blocks.f_50371_)
                .addOptionalTag(forgeTagKey("glass_panes"));
        m_206424_(GLASS_PANES_COLORLESS)
                .m_255245_(Blocks.f_50185_)
                .addOptionalTag(forgeTagKey("glass_panes/colorless"));
        m_206424_(GLAZED_TERRACOTTAS).m_255179_(Blocks.f_50526_, Blocks.f_50527_, Blocks.f_50528_, Blocks.f_50529_, Blocks.f_50530_, Blocks.f_50531_, Blocks.f_50532_, Blocks.f_50533_, Blocks.f_50534_, Blocks.f_50535_, Blocks.f_50536_, Blocks.f_50537_, Blocks.f_50538_, Blocks.f_50539_, Blocks.f_50540_, Blocks.f_50541_);
        m_206424_(GRAVEL).m_255245_(Blocks.f_49994_); // forge:gravel
        m_206424_(SKULLS).m_255179_(Blocks.f_50310_, Blocks.f_50311_, Blocks.f_50312_, Blocks.f_50313_, Blocks.f_50316_, Blocks.f_50317_, Blocks.f_50314_, Blocks.f_50315_, Blocks.f_50318_, Blocks.f_50319_, Blocks.f_260630_, Blocks.f_260585_, Blocks.f_50320_, Blocks.f_50321_);
        m_206424_(HIDDEN_FROM_RECIPE_VIEWERS);
        m_206424_(NETHERRACK).m_255245_(Blocks.f_50134_); // forge:netherrack
        m_206424_(OBSIDIANS_NORMAL).m_255245_(Blocks.f_50080_);
        m_206424_(OBSIDIANS_CRYING).m_255245_(Blocks.f_50723_);
        m_206424_(OBSIDIANS)
                .addTags(OBSIDIANS_NORMAL, OBSIDIANS_CRYING)
                .addOptionalTag(forgeTagKey("obsidian"));
        m_206424_(ORE_BEARING_GROUND_DEEPSLATE).m_255245_(Blocks.f_152550_); // forge:ore_bearing_ground/deepslate
        m_206424_(ORE_BEARING_GROUND_NETHERRACK).m_255245_(Blocks.f_50134_); // forge:ore_bearing_ground/netherrack
        m_206424_(ORE_BEARING_GROUND_STONE).m_255245_(Blocks.f_50069_); // forge:ore_bearing_ground/stone
        m_206424_(ORE_RATES_DENSE).m_255179_(Blocks.f_152505_, Blocks.f_152506_, Blocks.f_152472_, Blocks.f_152473_, Blocks.f_50059_, Blocks.f_50173_);
        m_206424_(ORE_RATES_SINGULAR).m_255179_(Blocks.f_50722_, Blocks.f_49997_, Blocks.f_152469_, Blocks.f_152474_, Blocks.f_152479_, Blocks.f_152467_, Blocks.f_152468_, Blocks.f_50089_, Blocks.f_50264_, Blocks.f_49995_, Blocks.f_49996_, Blocks.f_50331_);
        m_206424_(ORE_RATES_SPARSE).m_255245_(Blocks.f_49998_);
        m_206424_(ORES)
                .addTags(ORES_COAL, ORES_COPPER, ORES_DIAMOND, ORES_EMERALD, ORES_GOLD, ORES_IRON, ORES_LAPIS, ORES_NETHERITE_SCRAP, ORES_REDSTONE, ORES_QUARTZ)
                .addOptionalTag(forgeTagKey("ores"));
        m_206424_(ORES_COAL).m_206428_(BlockTags.f_144262_); // forge:ores/coal
        m_206424_(ORES_COPPER).m_206428_(BlockTags.f_144264_); // forge:ores/copper
        m_206424_(ORES_DIAMOND).m_206428_(BlockTags.f_144259_); // forge:ores/diamond
        m_206424_(ORES_EMERALD).m_206428_(BlockTags.f_144263_); // forge:ores/emerald
        m_206424_(ORES_GOLD).m_206428_(BlockTags.f_13043_); // forge:ores/gold
        m_206424_(ORES_IRON).m_206428_(BlockTags.f_144258_); // forge:ores/iron
        m_206424_(ORES_LAPIS).m_206428_(BlockTags.f_144261_); // forge:ores/lapis
        m_206424_(ORES_QUARTZ)
                .m_255245_(Blocks.f_50331_)
                .addOptionalTag(forgeTagKey("ores/quartz"));
        m_206424_(ORES_REDSTONE).m_206428_(BlockTags.f_144260_); // forge:ores/redstone
        m_206424_(ORES_NETHERITE_SCRAP)
                .m_255245_(Blocks.f_50722_)
                .addOptionalTag(forgeTagKey("ores/netherite_scrap"));
        m_206424_(ORES_IN_GROUND_DEEPSLATE).m_255179_(Blocks.f_152469_, Blocks.f_152506_, Blocks.f_152474_, Blocks.f_152479_, Blocks.f_152467_, Blocks.f_152468_, Blocks.f_152472_, Blocks.f_152473_);
        m_206424_(ORES_IN_GROUND_NETHERRACK).m_255179_(Blocks.f_49998_, Blocks.f_50331_);
        m_206424_(ORES_IN_GROUND_STONE).m_255179_(Blocks.f_49997_, Blocks.f_152505_, Blocks.f_50089_, Blocks.f_50264_, Blocks.f_49995_, Blocks.f_49996_, Blocks.f_50059_, Blocks.f_50173_);
        m_206424_(PLAYER_WORKSTATIONS_CRAFTING_TABLES).m_255245_(Blocks.f_50091_);
        m_206424_(PLAYER_WORKSTATIONS_FURNACES).m_255245_(Blocks.f_50094_);
        m_206424_(PUMPKINS).addTags(PUMPKINS_NORMAL, PUMPKINS_CARVED, PUMPKINS_JACK_O_LANTERNS);
        m_206424_(PUMPKINS_NORMAL).m_255245_(Blocks.f_50133_);
        m_206424_(PUMPKINS_CARVED).m_255245_(Blocks.f_50143_);
        m_206424_(PUMPKINS_JACK_O_LANTERNS).m_255245_(Blocks.f_50144_);
        m_206424_(SAND).addTags(SAND_COLORLESS, SAND_RED); // forge:sand
        m_206424_(RELOCATION_NOT_SUPPORTED);
        m_206424_(ROPES);
        m_206424_(SAND_COLORLESS).m_255245_(Blocks.f_49992_); // forge:sand/colorless
        m_206424_(SAND_RED).m_255245_(Blocks.f_49993_); // forge:sand/red

        m_206424_(SANDSTONE_RED_BLOCKS).m_255179_(Blocks.f_50394_, Blocks.f_50396_, Blocks.f_50395_, Blocks.f_50473_);
        m_206424_(SANDSTONE_UNCOLORED_BLOCKS).m_255179_(Blocks.f_50062_, Blocks.f_50064_, Blocks.f_50063_, Blocks.f_50471_);
        m_206424_(SANDSTONE_BLOCKS)
                .addTags(SANDSTONE_RED_BLOCKS, SANDSTONE_UNCOLORED_BLOCKS)
                .addOptionalTag(forgeTagKey("sandstone"));
        m_206424_(SANDSTONE_RED_SLABS).m_255179_(Blocks.f_50467_, Blocks.f_50468_, Blocks.f_50644_);
        m_206424_(SANDSTONE_UNCOLORED_SLABS).m_255179_(Blocks.f_50406_, Blocks.f_50407_, Blocks.f_50649_);
        m_206424_(SANDSTONE_SLABS).addTags(SANDSTONE_RED_SLABS, SANDSTONE_UNCOLORED_SLABS);
        m_206424_(SANDSTONE_RED_STAIRS).m_255179_(Blocks.f_50397_, Blocks.f_50630_);
        m_206424_(SANDSTONE_UNCOLORED_STAIRS).m_255179_(Blocks.f_50263_, Blocks.f_50636_);
        m_206424_(SANDSTONE_STAIRS).addTags(SANDSTONE_RED_STAIRS, SANDSTONE_UNCOLORED_STAIRS);

        m_206424_(STONES)
                .m_255179_(Blocks.f_50334_, Blocks.f_50228_, Blocks.f_50122_, Blocks.f_50069_, Blocks.f_152550_, Blocks.f_152496_);
                //.addOptionalTag(forgeTagKey("stone")); // can't add this because it would include infested/polished variants which aren't contained in Fabric's `c:stones`
        m_206424_(STORAGE_BLOCKS)
                .addTags(STORAGE_BLOCKS_BONE_MEAL, STORAGE_BLOCKS_COAL,
                STORAGE_BLOCKS_COPPER, STORAGE_BLOCKS_DIAMOND, STORAGE_BLOCKS_DRIED_KELP,
                STORAGE_BLOCKS_EMERALD, STORAGE_BLOCKS_GOLD, STORAGE_BLOCKS_IRON,
                STORAGE_BLOCKS_LAPIS, STORAGE_BLOCKS_NETHERITE, STORAGE_BLOCKS_RAW_COPPER,
                STORAGE_BLOCKS_RAW_GOLD, STORAGE_BLOCKS_RAW_IRON, STORAGE_BLOCKS_REDSTONE,
                STORAGE_BLOCKS_SLIME, STORAGE_BLOCKS_WHEAT)
                //.addOptionalTag(forgeTagKey("storage_blocks")); // can't add this because it would include contents from the non-common forge:storage_blocks/amethyst and forge:storage_blocks/quartz, which are not in the c namespace
                .addOptionalTags(forgeTagKey("storage_blocks/coal"), forgeTagKey("storage_blocks/copper"),
                        forgeTagKey("storage_blocks/diamond"), forgeTagKey("storage_blocks/emerald"),
                        forgeTagKey("storage_blocks/gold"), forgeTagKey("storage_blocks/iron"),
                        forgeTagKey("storage_blocks/lapis"), forgeTagKey("storage_blocks/netherite"),
                        forgeTagKey("storage_blocks/raw_copper"), forgeTagKey("storage_blocks/raw_gold"),
                        forgeTagKey("storage_blocks/raw_iron"), forgeTagKey("storage_blocks/redstone"));
        m_206424_(STORAGE_BLOCKS_BONE_MEAL).m_255245_(Blocks.f_50453_);
        m_206424_(STORAGE_BLOCKS_COAL)
                .m_255245_(Blocks.f_50353_)
                .addOptionalTag(forgeTagKey("storage_blocks/coal"));
        m_206424_(STORAGE_BLOCKS_COPPER)
                .m_255245_(Blocks.f_152504_)
                .addOptionalTag(forgeTagKey("storage_blocks/copper"));
        m_206424_(STORAGE_BLOCKS_DIAMOND)
                .m_255245_(Blocks.f_50090_)
                .addOptionalTag(forgeTagKey("storage_blocks/diamond"));
        m_206424_(STORAGE_BLOCKS_DRIED_KELP).m_255245_(Blocks.f_50577_);
        m_206424_(STORAGE_BLOCKS_EMERALD)
                .m_255245_(Blocks.f_50268_)
                .addOptionalTag(forgeTagKey("storage_blocks/emerald"));
        m_206424_(STORAGE_BLOCKS_GOLD)
                .m_255245_(Blocks.f_50074_)
                .addOptionalTag(forgeTagKey("storage_blocks/gold"));
        m_206424_(STORAGE_BLOCKS_IRON)
                .m_255245_(Blocks.f_50075_)
                .addOptionalTag(forgeTagKey("storage_blocks/iron"));
        m_206424_(STORAGE_BLOCKS_LAPIS)
                .m_255245_(Blocks.f_50060_)
                .addOptionalTag(forgeTagKey("storage_blocks/lapis"));
        m_206424_(STORAGE_BLOCKS_NETHERITE)
                .m_255245_(Blocks.f_50721_)
                .addOptionalTag(forgeTagKey("storage_blocks/netherite"));
        m_206424_(STORAGE_BLOCKS_RAW_COPPER)
                .m_255245_(Blocks.f_152599_)
                .addOptionalTag(forgeTagKey("storage_blocks/raw_copper"));
        m_206424_(STORAGE_BLOCKS_RAW_GOLD)
                .m_255245_(Blocks.f_152600_)
                .addOptionalTag(forgeTagKey("storage_blocks/raw_gold"));
        m_206424_(STORAGE_BLOCKS_RAW_IRON)
                .m_255245_(Blocks.f_152598_)
                .addOptionalTag(forgeTagKey("storage_blocks/raw_iron"));
        m_206424_(STORAGE_BLOCKS_REDSTONE)
                .m_255245_(Blocks.f_50330_)
                .addOptionalTag(forgeTagKey("storage_blocks/redstone"));
        m_206424_(STORAGE_BLOCKS_SLIME).m_255245_(Blocks.f_50374_);
        m_206424_(STORAGE_BLOCKS_WHEAT).m_255245_(Blocks.f_50335_);
        m_206424_(Tags.Blocks.STRIPPED_LOGS).m_255179_(
                Blocks.f_50008_, Blocks.f_256740_, Blocks.f_50006_,
                Blocks.f_271326_, Blocks.f_50009_, Blocks.f_50007_,
                Blocks.f_220835_, Blocks.f_50010_, Blocks.f_50005_);
        m_206424_(Tags.Blocks.STRIPPED_WOODS).m_255179_(
                Blocks.f_50048_, Blocks.f_50046_, Blocks.f_271145_,
                Blocks.f_50049_, Blocks.f_50047_, Blocks.f_220837_,
                Blocks.f_50044_, Blocks.f_50045_);
        m_206424_(VILLAGER_JOB_SITES).m_255179_(
                Blocks.f_50618_, Blocks.f_50620_, Blocks.f_50255_, Blocks.f_50621_,
                Blocks.f_50256_, Blocks.f_152476_, Blocks.f_152477_, Blocks.f_152478_,
                Blocks.f_50715_, Blocks.f_50622_, Blocks.f_50623_, Blocks.f_50624_,
                Blocks.f_50617_, Blocks.f_50625_, Blocks.f_50619_, Blocks.f_50679_);

        // Backwards compat definitions for pre-1.21 legacy `forge:` tags.
        // TODO: Remove backwards compat tag entries in 1.22
        m_206424_(forgeTagKey("barrels")).m_206428_(forgeTagKey("barrels/wooden"));
        m_206424_(forgeTagKey("barrels/wooden")).m_255245_(Blocks.f_50618_);
        m_206424_(forgeTagKey("bookshelves")).m_255245_(Blocks.f_50078_);
        m_206424_(forgeTagKey("chests")).addTags(forgeTagKey("chests/ender"), forgeTagKey("chests/trapped"), forgeTagKey("chests/wooden"));
        m_206424_(forgeTagKey("chests/wooden")).m_255179_(Blocks.f_50087_, Blocks.f_50325_);
        m_206424_(forgeTagKey("cobblestone")).addTags(forgeTagKey("cobblestone/normal"), forgeTagKey("cobblestone/infested"), forgeTagKey("cobblestone/mossy"), forgeTagKey("cobblestone/deepslate"));
        m_206424_(forgeTagKey("glass")).addTags(forgeTagKey("glass/colorless"), forgeTagKey("stained_glass"), forgeTagKey("glass/tinted"));
        m_206424_(forgeTagKey("glass/colorless")).m_255245_(Blocks.f_50058_);
        m_206424_(forgeTagKey("glass/silica")).m_255179_(Blocks.f_50058_, Blocks.f_50215_, Blocks.f_50211_, Blocks.f_50212_, Blocks.f_50209_, Blocks.f_50207_, Blocks.f_50213_, Blocks.f_50203_, Blocks.f_50208_, Blocks.f_50205_, Blocks.f_50202_, Blocks.f_50148_, Blocks.f_50206_, Blocks.f_50210_, Blocks.f_50214_, Blocks.f_50147_, Blocks.f_50204_);
        m_206424_(forgeTagKey("glass/tinted")).m_255245_(Blocks.f_152498_);
        addColored(m_206424_(forgeTagKey("stained_glass"))::m_255245_, forgeTagKey("glass"), "{color}_stained_glass");
        m_206424_(forgeTagKey("glass_panes")).addTags(forgeTagKey("glass_panes/colorless"), forgeTagKey("stained_glass_panes"));
        m_206424_(forgeTagKey("glass_panes/colorless")).m_255245_(Blocks.f_50185_);
        addColored(m_206424_(forgeTagKey("stained_glass_panes"))::m_255245_, forgeTagKey("glass_panes"), "{color}_stained_glass_pane");
        m_206424_(forgeTagKey("obsidian")).m_255245_(Blocks.f_50080_);
        m_206424_(forgeTagKey("ores")).addTags(forgeTagKey("ores/coal"), forgeTagKey("ores/copper"), forgeTagKey("ores/diamond"), forgeTagKey("ores/emerald"), forgeTagKey("ores/gold"), forgeTagKey("ores/iron"), forgeTagKey("ores/lapis"), forgeTagKey("ores/redstone"), forgeTagKey("ores/quartz"), forgeTagKey("ores/netherite_scrap"));
        m_206424_(forgeTagKey("ores/quartz")).m_255245_(Blocks.f_50331_);
        m_206424_(forgeTagKey("ores/netherite_scrap")).m_255245_(Blocks.f_50722_);
        m_206424_(forgeTagKey("sandstone")).m_255179_(Blocks.f_50062_, Blocks.f_50064_, Blocks.f_50063_, Blocks.f_50471_, Blocks.f_50394_, Blocks.f_50396_, Blocks.f_50395_, Blocks.f_50473_);
        m_206424_(forgeTagKey("stone")).m_255179_(Blocks.f_50334_, Blocks.f_50228_, Blocks.f_50122_, Blocks.f_50226_, Blocks.f_50069_, Blocks.f_50387_, Blocks.f_50281_, Blocks.f_50175_, Blocks.f_152550_, Blocks.f_152555_, Blocks.f_152596_, Blocks.f_152496_);
        m_206424_(forgeTagKey("storage_blocks"))
                .addTags(forgeTagKey("storage_blocks/amethyst"), forgeTagKey("storage_blocks/coal"), forgeTagKey("storage_blocks/copper"), forgeTagKey("storage_blocks/diamond"), forgeTagKey("storage_blocks/emerald"), forgeTagKey("storage_blocks/gold"), forgeTagKey("storage_blocks/iron"), forgeTagKey("storage_blocks/lapis"), forgeTagKey("storage_blocks/quartz"), forgeTagKey("storage_blocks/raw_copper"), forgeTagKey("storage_blocks/raw_gold"), forgeTagKey("storage_blocks/raw_iron"), forgeTagKey("storage_blocks/redstone"), forgeTagKey("storage_blocks/netherite"));
        m_206424_(forgeTagKey("storage_blocks/amethyst")).m_255245_(Blocks.f_152490_);
        m_206424_(forgeTagKey("storage_blocks/coal")).m_255245_(Blocks.f_50353_);
        m_206424_(forgeTagKey("storage_blocks/copper")).m_255245_(Blocks.f_152504_);
        m_206424_(forgeTagKey("storage_blocks/diamond")).m_255245_(Blocks.f_50090_);
        m_206424_(forgeTagKey("storage_blocks/emerald")).m_255245_(Blocks.f_50268_);
        m_206424_(forgeTagKey("storage_blocks/gold")).m_255245_(Blocks.f_50074_);
        m_206424_(forgeTagKey("storage_blocks/iron")).m_255245_(Blocks.f_50075_);
        m_206424_(forgeTagKey("storage_blocks/lapis")).m_255245_(Blocks.f_50060_);
        m_206424_(forgeTagKey("storage_blocks/quartz")).m_255245_(Blocks.f_50333_);
        m_206424_(forgeTagKey("storage_blocks/raw_copper")).m_255245_(Blocks.f_152599_);
        m_206424_(forgeTagKey("storage_blocks/raw_gold")).m_255245_(Blocks.f_152600_);
        m_206424_(forgeTagKey("storage_blocks/raw_iron")).m_255245_(Blocks.f_152598_);
        m_206424_(forgeTagKey("storage_blocks/redstone")).m_255245_(Blocks.f_50330_);
        m_206424_(forgeTagKey("storage_blocks/netherite")).m_255245_(Blocks.f_50721_);
    }

    private void addColored(TagKey<Block> group, String pattern) {
        String prefix = group.f_203868_().m_135815_().toUpperCase(Locale.ENGLISH) + '_';
        for (var color : DyeColor.values()) {
            var key = ResourceLocation.m_339182_("minecraft", pattern.replace("{color}", color.m_41065_()));
            TagKey<Block> tag = getForgeTag(prefix + color.m_41065_());
            var block = ForgeRegistries.BLOCKS.getValue(key);
            if (block == null || block == Blocks.f_50016_)
                throw new IllegalStateException("Unknown vanilla block: " + key);
            m_206424_(tag).m_255245_(block);
        }
    }

    private void addColored(Consumer<Block> consumer, TagKey<Block> group, String pattern) {
        String prefix = group.f_203868_().m_135815_().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor color  : DyeColor.values()) {
            ResourceLocation key = ResourceLocation.m_339182_("minecraft", pattern.replace("{color}",  color.m_41065_()));
            TagKey<Block> tag = getForgeTag(prefix + color.m_41065_());
            Block block = ForgeRegistries.BLOCKS.getValue(key);
            if (block == null || block  == Blocks.f_50016_)
                throw new IllegalStateException("Unknown vanilla block: " + key.toString());
            m_206424_(tag).m_255245_(block);
            consumer.accept(block);
        }
    }

    private static void addColoredTags(Consumer<TagKey<Block>> consumer, TagKey<Block> group) {
        String prefix = group.f_203868_().m_135815_().toUpperCase(Locale.ENGLISH) + '_';
        for (var color : DyeColor.values()) {
            TagKey<Block> tag = getForgeTag(prefix + color.m_41065_());
            consumer.accept(tag);
        }
    }

    @SuppressWarnings("unchecked")
    private static TagKey<Block> getForgeTag(String name) {
        try {
            name = name.toUpperCase(Locale.ENGLISH);
            return (TagKey<Block>) Tags.Blocks.class.getDeclaredField(name).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(Tags.Blocks.class.getName() + " is missing tag name: " + name);
        }
    }

    private static TagKey<Block> forgeTagKey(String path) {
        return BlockTags.create(ResourceLocation.m_339182_("forge", path));
    }

    @Override
    public String m_6055_() {
        return "Forge Block Tags";
    }
}
