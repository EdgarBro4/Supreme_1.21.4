/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.data.tags.TagsProvider.TagLookup;

@ApiStatus.Internal
public final class ForgeItemTagsProvider extends ItemTagsProvider {
    public ForgeItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, "forge", existingFileHelper);
    }

    @SuppressWarnings({ "unchecked", "removal" })
    @Override
    public void m_6577_(HolderLookup.Provider lookupProvider) {
        m_206421_(Tags.Blocks.BARRELS, Tags.Items.BARRELS);
        m_206421_(Tags.Blocks.BARRELS_WOODEN, Tags.Items.BARRELS_WOODEN);
        m_206424_(Tags.Items.BONES).m_255245_(Items.f_42500_);
        m_206421_(Tags.Blocks.BOOKSHELVES, Tags.Items.BOOKSHELVES);
        m_206424_(Tags.Items.BRICKS).addTags(Tags.Items.BRICKS_NORMAL, Tags.Items.BRICKS_NETHER);
        m_206424_(Tags.Items.BRICKS_NORMAL).m_255245_(Items.f_42460_);
        m_206424_(Tags.Items.BRICKS_NETHER).m_255245_(Items.f_42691_);
        m_206424_(Tags.Items.BUCKETS_EMPTY).m_255245_(Items.f_42446_);
        m_206424_(Tags.Items.BUCKETS_WATER).m_255245_(Items.f_42447_);
        m_206424_(Tags.Items.BUCKETS_LAVA).m_255245_(Items.f_42448_);
        m_206424_(Tags.Items.BUCKETS_MILK).m_255245_(Items.f_42455_);
        m_206424_(Tags.Items.BUCKETS_POWDER_SNOW).m_255245_(Items.f_151055_);
        m_206424_(Tags.Items.BUCKETS_ENTITY_WATER).m_255179_(Items.f_151057_, Items.f_42458_, Items.f_42456_, Items.f_220210_, Items.f_42459_, Items.f_42457_);
        m_206424_(Tags.Items.BUCKETS).addTags(Tags.Items.BUCKETS_EMPTY, Tags.Items.BUCKETS_WATER, Tags.Items.BUCKETS_LAVA, Tags.Items.BUCKETS_MILK, Tags.Items.BUCKETS_POWDER_SNOW, Tags.Items.BUCKETS_ENTITY_WATER);
        m_206421_(Tags.Blocks.BUDDING_BLOCKS, Tags.Items.BUDDING_BLOCKS);
        m_206421_(Tags.Blocks.BUDS, Tags.Items.BUDS);
        m_206421_(Tags.Blocks.CHAINS, Tags.Items.CHAINS);
        m_206421_(Tags.Blocks.CHESTS, Tags.Items.CHESTS);
        m_206421_(Tags.Blocks.CHESTS_ENDER, Tags.Items.CHESTS_ENDER);
        m_206421_(Tags.Blocks.CHESTS_TRAPPED, Tags.Items.CHESTS_TRAPPED);
        m_206421_(Tags.Blocks.CHESTS_WOODEN, Tags.Items.CHESTS_WOODEN);
        m_206421_(Tags.Blocks.CLUSTERS, Tags.Items.CLUSTERS);
        m_206421_(Tags.Blocks.COBBLESTONES, Tags.Items.COBBLESTONES);
        m_206421_(Tags.Blocks.COBBLESTONE_NORMAL, Tags.Items.COBBLESTONE_NORMAL);
        m_206421_(Tags.Blocks.COBBLESTONE_INFESTED, Tags.Items.COBBLESTONE_INFESTED);
        m_206421_(Tags.Blocks.COBBLESTONE_MOSSY, Tags.Items.COBBLESTONE_MOSSY);
        m_206421_(Tags.Blocks.COBBLESTONE_DEEPSLATE, Tags.Items.COBBLESTONE_DEEPSLATE);
        m_206421_(Tags.Blocks.CONCRETES, Tags.Items.CONCRETES);
        m_206424_(Tags.Items.CONCRETE_POWDERS)
                .m_255179_(Items.f_42315_, Items.f_42316_, Items.f_42317_,
                        Items.f_42318_, Items.f_42319_, Items.f_42320_,
                        Items.f_42321_, Items.f_42322_, Items.f_42323_,
                        Items.f_42324_, Items.f_42325_, Items.f_42326_,
                        Items.f_42327_, Items.f_42328_, Items.f_42277_,
                        Items.f_42278_);
        m_206424_(Tags.Items.CROPS).addTags(
                Tags.Items.CROPS_BEETROOT, Tags.Items.CROPS_CACTUS, Tags.Items.CROPS_CARROT,
                Tags.Items.CROPS_COCOA_BEAN, Tags.Items.CROPS_MELON, Tags.Items.CROPS_NETHER_WART,
                Tags.Items.CROPS_POTATO, Tags.Items.CROPS_PUMPKIN, Tags.Items.CROPS_SUGAR_CANE,
                Tags.Items.CROPS_WHEAT
        ).addOptionalTag(forgeItemTagKey("crops"));
        m_206424_(Tags.Items.CROPS_BEETROOT)
                .m_255245_(Items.f_42732_)
                .addOptionalTag(forgeItemTagKey("crops/beetroot"));
        m_206424_(Tags.Items.CROPS_CACTUS).m_255245_(Items.f_41982_);
        m_206424_(Tags.Items.CROPS_CARROT)
                .m_255245_(Items.f_42619_)
                .addOptionalTag(forgeItemTagKey("crops/carrot"));
        m_206424_(Tags.Items.CROPS_COCOA_BEAN).m_255245_(Items.f_42533_);
        m_206424_(Tags.Items.CROPS_MELON).m_255245_(Items.f_42028_);
        m_206424_(Tags.Items.CROPS_NETHER_WART)
                .m_255245_(Items.f_42588_)
                .addOptionalTag(forgeItemTagKey("crops/nether_wart"));
        m_206424_(Tags.Items.CROPS_POTATO)
                .m_255245_(Items.f_42620_)
                .addOptionalTag(forgeItemTagKey("crops/potato"));
        m_206424_(Tags.Items.CROPS_PUMPKIN).m_255245_(Items.f_42046_);
        m_206424_(Tags.Items.CROPS_SUGAR_CANE).m_255245_(Items.f_41909_);
        m_206424_(Tags.Items.CROPS_WHEAT)
                .m_255245_(Items.f_42405_)
                .addOptionalTag(forgeItemTagKey("crops/wheat"));
        addColored(Tags.Items.DYED, "{color}_banner");
        addColored(Tags.Items.DYED, "{color}_bed");
        addColored(Tags.Items.DYED, "{color}_candle");
        addColored(Tags.Items.DYED, "{color}_carpet");
        addColored(Tags.Items.DYED, "{color}_concrete");
        addColored(Tags.Items.DYED, "{color}_concrete_powder");
        addColored(Tags.Items.DYED, "{color}_glazed_terracotta");
        addColored(Tags.Items.DYED, "{color}_shulker_box");
        addColored(Tags.Items.DYED, "{color}_stained_glass");
        addColored(Tags.Items.DYED, "{color}_stained_glass_pane");
        addColored(Tags.Items.DYED, "{color}_terracotta");
        addColored(Tags.Items.DYED, "{color}_wool");
        addColoredTags(m_206424_(Tags.Items.DYED)::addTags, Tags.Items.DYED);
        m_206424_(Tags.Items.DUSTS).addTags(Tags.Items.DUSTS_GLOWSTONE, Tags.Items.DUSTS_REDSTONE);
        m_206424_(Tags.Items.DUSTS_GLOWSTONE)
                .m_255245_(Items.f_42525_)
                .addOptionalTag(forgeItemTagKey("dusts/glowstone"));
        m_206424_(Tags.Items.DUSTS_REDSTONE)
                .m_255245_(Items.f_42451_)
                .addOptionalTag(forgeItemTagKey("dusts/redstone"));
        addColored(Tags.Items.DYES, "{color}_dye");
        addColoredTags(m_206424_(Tags.Items.DYES)::addTags, Tags.Items.DYES);
        m_206424_(Tags.Items.EGGS).m_255245_(Items.f_42521_); // forge:eggs
        m_206424_(Tags.Items.ENCHANTING_FUELS).m_206428_(Tags.Items.GEMS_LAPIS); // forge:enchanting_fuels
        m_206421_(Tags.Blocks.END_STONES, Tags.Items.END_STONES);
        m_206424_(Tags.Items.ENDER_PEARLS)
                .m_255245_(Items.f_42584_)
                .addOptionalTag(forgeItemTagKey("ender_pearls"));
        m_206424_(Tags.Items.FEATHERS)
                .m_255245_(Items.f_42402_)
                .addOptionalTag(forgeItemTagKey("feathers"));
        m_206421_(Tags.Blocks.FENCE_GATES, Tags.Items.FENCE_GATES);
        m_206421_(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        m_206421_(Tags.Blocks.FENCES, Tags.Items.FENCES);
        m_206421_(Tags.Blocks.FENCES_NETHER_BRICK, Tags.Items.FENCES_NETHER_BRICK);
        m_206421_(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        m_206424_(Tags.Items.FERTILIZERS).m_255245_(Items.f_42499_);
        m_206421_(Tags.Blocks.FLOWERS_SMALL, Tags.Items.FLOWERS_SMALL);
        m_206421_(Tags.Blocks.FLOWERS_TALL, Tags.Items.FLOWERS_TALL);
        m_206421_(Tags.Blocks.FLOWERS, Tags.Items.FLOWERS);
        m_206424_(Tags.Items.FOODS_FRUIT).m_255179_(Items.f_42410_, Items.f_42436_, Items.f_42437_, Items.f_42730_, Items.f_42575_);
        m_206424_(Tags.Items.FOODS_VEGETABLE).m_255179_(Items.f_42619_, Items.f_42677_, Items.f_42620_, Items.f_42732_);
        m_206424_(Tags.Items.FOODS_BERRY).m_255179_(Items.f_42780_, Items.f_151079_);
        m_206424_(Tags.Items.FOODS_BREAD).m_255245_(Items.f_42406_);
        m_206424_(Tags.Items.FOODS_COOKIE).m_255245_(Items.f_42572_);
        m_206424_(Tags.Items.FOODS_RAW_MEAT).m_255179_(Items.f_42579_, Items.f_42485_, Items.f_42581_, Items.f_42697_, Items.f_42658_);
        m_206424_(Tags.Items.FOODS_RAW_FISH).m_255179_(Items.f_42526_, Items.f_42527_, Items.f_42528_, Items.f_42529_);
        m_206424_(Tags.Items.FOODS_COOKED_MEAT).m_255179_(Items.f_42580_, Items.f_42486_, Items.f_42582_, Items.f_42698_, Items.f_42659_);
        m_206424_(Tags.Items.FOODS_COOKED_FISH).m_255179_(Items.f_42530_, Items.f_42531_);
        m_206424_(Tags.Items.FOODS_SOUP).m_255179_(Items.f_42734_, Items.f_42400_, Items.f_42699_, Items.f_42718_);
        m_206424_(Tags.Items.FOODS_CANDY);
        m_206424_(Tags.Items.FOODS_PIE).m_255245_(Items.f_42687_).addOptionalTag(forgeItemTagKey("foods/pie"));
        m_206424_(Tags.Items.FOODS_EDIBLE_WHEN_PLACED).m_255245_(Items.f_42502_);
        m_206424_(Tags.Items.FOODS_FOOD_POISONING).m_255179_(Items.f_42675_, Items.f_42529_, Items.f_42591_, Items.f_42581_, Items.f_42583_);
        m_206424_(Tags.Items.FOODS_GOLDEN).m_255179_(Items.f_42436_, Items.f_42437_, Items.f_42677_);
        m_206424_(Tags.Items.FOODS)
                .m_255179_(Items.f_42674_, Items.f_42787_, Items.f_316650_, Items.f_42576_)
                .addTags(Tags.Items.FOODS_FRUIT, Tags.Items.FOODS_VEGETABLE, Tags.Items.FOODS_BERRY, Tags.Items.FOODS_BREAD, Tags.Items.FOODS_COOKIE,
                        Tags.Items.FOODS_RAW_MEAT, Tags.Items.FOODS_RAW_FISH, Tags.Items.FOODS_COOKED_MEAT, Tags.Items.FOODS_COOKED_FISH,
                        Tags.Items.FOODS_SOUP, Tags.Items.FOODS_CANDY, Tags.Items.FOODS_PIE, Tags.Items.FOODS_GOLDEN,
                        Tags.Items.FOODS_EDIBLE_WHEN_PLACED, Tags.Items.FOODS_FOOD_POISONING);
        m_206424_(Tags.Items.ANIMAL_FOODS)
                .addTags(ItemTags.f_316663_, ItemTags.f_316264_, ItemTags.f_316276_, ItemTags.f_315451_,
                        ItemTags.f_314035_, ItemTags.f_316289_, ItemTags.f_315238_, ItemTags.f_144311_, ItemTags.f_314631_,
                        ItemTags.f_316983_, ItemTags.f_315349_, ItemTags.f_316234_, ItemTags.f_314714_, ItemTags.f_316292_,
                        ItemTags.f_316653_, ItemTags.f_316572_, ItemTags.f_314144_, ItemTags.f_144310_, ItemTags.f_314532_,
                        ItemTags.f_314146_, ItemTags.f_271449_, ItemTags.f_314410_, ItemTags.f_314831_, ItemTags.f_316425_);
        m_206424_(Tags.Items.GEMS)
                .addTags(Tags.Items.GEMS_AMETHYST, Tags.Items.GEMS_DIAMOND, Tags.Items.GEMS_EMERALD, Tags.Items.GEMS_LAPIS, Tags.Items.GEMS_PRISMARINE, Tags.Items.GEMS_QUARTZ)
                .addOptionalTag(forgeItemTagKey("gems"));
        m_206424_(Tags.Items.GEMS_AMETHYST)
                .m_255245_(Items.f_151049_)
                .addOptionalTag(forgeItemTagKey("gems/amethyst"));
        m_206424_(Tags.Items.GEMS_DIAMOND)
                .m_255245_(Items.f_42415_)
                .addOptionalTag(forgeItemTagKey("gems/diamond"));
        m_206424_(Tags.Items.GEMS_EMERALD)
                .m_255245_(Items.f_42616_)
                .addOptionalTag(forgeItemTagKey("gems/emerald"));
        m_206424_(Tags.Items.GEMS_LAPIS)
                .m_255245_(Items.f_42534_)
                .addOptionalTag(forgeItemTagKey("gems/lapis"));
        m_206424_(Tags.Items.GEMS_PRISMARINE)
                .m_255245_(Items.f_42696_)
                .addOptionalTag(forgeItemTagKey("gems/prismarine"));
        m_206424_(Tags.Items.GEMS_QUARTZ)
                .m_255245_(Items.f_42692_)
                .addOptionalTag(forgeItemTagKey("gems/quartz"));
        m_206421_(Tags.Blocks.GLASS_BLOCKS, Tags.Items.GLASS_BLOCKS);
        m_206421_(Tags.Blocks.GLASS_BLOCKS_COLORLESS, Tags.Items.GLASS_BLOCKS_COLORLESS);
        m_206421_(Tags.Blocks.GLASS_BLOCKS_TINTED, Tags.Items.GLASS_BLOCKS_TINTED);
        m_206421_(Tags.Blocks.GLASS_BLOCKS_CHEAP, Tags.Items.GLASS_BLOCKS_CHEAP);
        m_206421_(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES);
        m_206421_(Tags.Blocks.GLASS_PANES_COLORLESS, Tags.Items.GLASS_PANES_COLORLESS);
        m_206421_(Tags.Blocks.GLAZED_TERRACOTTAS, Tags.Items.GLAZED_TERRACOTTAS);
        m_206421_(Tags.Blocks.GRAVEL, Tags.Items.GRAVEL);
        m_206424_(Tags.Items.GUNPOWDER).m_255245_(Items.f_42403_); // forge:gunpowder
        m_206424_(Tags.Items.HIDDEN_FROM_RECIPE_VIEWERS);
        m_206424_(Tags.Items.INGOTS)
                .addTags(Tags.Items.INGOTS_COPPER, Tags.Items.INGOTS_GOLD, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_NETHERITE);
                //.addOptionalTag(forgeItemTagKey("ingots")); // can't add because it would contain the contents of forge:ingots/brick and forge:ingots/nether_brick which are not in the c namespace
        m_206424_(Tags.Items.INGOTS_COPPER)
                .m_255245_(Items.f_151052_)
                .addOptionalTag(forgeItemTagKey("ingots/copper"));
        m_206424_(Tags.Items.INGOTS_GOLD)
                .m_255245_(Items.f_42417_)
                .addOptionalTag(forgeItemTagKey("ingots/gold"));
        m_206424_(Tags.Items.INGOTS_IRON)
                .m_255245_(Items.f_42416_)
                .addOptionalTag(forgeItemTagKey("ingots/iron"));
        m_206424_(Tags.Items.INGOTS_NETHERITE)
                .m_255245_(Items.f_42418_)
                .addOptionalTag(forgeItemTagKey("ingots/netherite"));
        m_206424_(Tags.Items.LEATHERS)
                .m_255245_(Items.f_42454_)
                .addOptionalTag(forgeItemTagKey("leather"));
        m_206424_(Tags.Items.MUSHROOMS)
                .m_255179_(Items.f_41952_, Items.f_41953_)
                .addOptionalTag(forgeItemTagKey("mushrooms"));
        m_206424_(Tags.Items.MUSIC_DISCS).m_255179_(Items.f_42752_, Items.f_42701_, Items.f_42702_, Items.f_42703_,
                Items.f_42704_, Items.f_42705_, Items.f_42706_, Items.f_42707_, Items.f_42708_,
                Items.f_42709_, Items.f_42710_, Items.f_42711_, Items.f_186363_, Items.f_220217_,
                Items.f_42712_, Items.f_283830_, Items.f_337043_, Items.f_337528_,
                Items.f_337210_);
        m_206424_(Tags.Items.NETHER_STARS)
                .m_255245_(Items.f_42686_)
                .addOptionalTag(forgeItemTagKey("nether_stars"));
        m_206421_(Tags.Blocks.NETHERRACK, Tags.Items.NETHERRACK);
        m_206424_(Tags.Items.NUGGETS)
                .addTags(Tags.Items.NUGGETS_GOLD, Tags.Items.NUGGETS_IRON)
                .addOptionalTag(forgeItemTagKey("nuggets"));
        m_206424_(Tags.Items.NUGGETS_IRON)
                .m_255245_(Items.f_42749_)
                .addOptionalTag(forgeItemTagKey("nuggets/iron"));
        m_206424_(Tags.Items.NUGGETS_GOLD)
                .m_255245_(Items.f_42587_)
                .addOptionalTag(forgeItemTagKey("nuggets/gold"));
        m_206421_(Tags.Blocks.OBSIDIANS, Tags.Items.OBSIDIANS);
        m_206421_(Tags.Blocks.OBSIDIANS_NORMAL, Tags.Items.OBSIDIANS_NORMAL);
        m_206421_(Tags.Blocks.OBSIDIANS_CRYING, Tags.Items.OBSIDIANS_CRYING);
        m_206421_(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE, Tags.Items.ORE_BEARING_GROUND_DEEPSLATE);
        m_206421_(Tags.Blocks.ORE_BEARING_GROUND_NETHERRACK, Tags.Items.ORE_BEARING_GROUND_NETHERRACK);
        m_206421_(Tags.Blocks.ORE_BEARING_GROUND_STONE, Tags.Items.ORE_BEARING_GROUND_STONE);
        m_206421_(Tags.Blocks.ORE_RATES_DENSE, Tags.Items.ORE_RATES_DENSE);
        m_206421_(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
        m_206421_(Tags.Blocks.ORE_RATES_SPARSE, Tags.Items.ORE_RATES_SPARSE);
        m_206421_(Tags.Blocks.ORES, Tags.Items.ORES);
        m_206421_(Tags.Blocks.ORES_COAL, Tags.Items.ORES_COAL);
        m_206421_(Tags.Blocks.ORES_COPPER, Tags.Items.ORES_COPPER);
        m_206421_(Tags.Blocks.ORES_DIAMOND, Tags.Items.ORES_DIAMOND);
        m_206421_(Tags.Blocks.ORES_EMERALD, Tags.Items.ORES_EMERALD);
        m_206421_(Tags.Blocks.ORES_GOLD, Tags.Items.ORES_GOLD);
        m_206421_(Tags.Blocks.ORES_IRON, Tags.Items.ORES_IRON);
        m_206421_(Tags.Blocks.ORES_LAPIS, Tags.Items.ORES_LAPIS);
        m_206421_(Tags.Blocks.ORES_QUARTZ, Tags.Items.ORES_QUARTZ);
        m_206421_(Tags.Blocks.ORES_REDSTONE, Tags.Items.ORES_REDSTONE);
        m_206421_(Tags.Blocks.ORES_NETHERITE_SCRAP, Tags.Items.ORES_NETHERITE_SCRAP);
        m_206421_(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        m_206421_(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK);
        m_206421_(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        m_206421_(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES, Tags.Items.PLAYER_WORKSTATIONS_CRAFTING_TABLES);
        m_206421_(Tags.Blocks.PLAYER_WORKSTATIONS_FURNACES, Tags.Items.PLAYER_WORKSTATIONS_FURNACES);
        m_206421_(Tags.Blocks.PUMPKINS, Tags.Items.PUMPKINS);
        m_206421_(Tags.Blocks.PUMPKINS_NORMAL, Tags.Items.PUMPKINS_NORMAL);
        m_206421_(Tags.Blocks.PUMPKINS_CARVED, Tags.Items.PUMPKINS_CARVED);
        m_206421_(Tags.Blocks.PUMPKINS_JACK_O_LANTERNS, Tags.Items.PUMPKINS_JACK_O_LANTERNS);
        m_206424_(Tags.Items.RAW_MATERIALS)
                .addTags(Tags.Items.RAW_MATERIALS_COPPER, Tags.Items.RAW_MATERIALS_GOLD, Tags.Items.RAW_MATERIALS_IRON)
                .addOptionalTag(forgeItemTagKey("raw_materials"));
        m_206424_(Tags.Items.RAW_MATERIALS_COPPER)
                .m_255245_(Items.f_151051_)
                .addOptionalTag(forgeItemTagKey("raw_materials/copper"));
        m_206424_(Tags.Items.RAW_MATERIALS_GOLD)
                .m_255245_(Items.f_151053_)
                .addOptionalTag(forgeItemTagKey("raw_materials/gold"));
        m_206424_(Tags.Items.RAW_MATERIALS_IRON)
                .m_255245_(Items.f_151050_)
                .addOptionalTag(forgeItemTagKey("raw_materials/iron"));
        m_206424_(Tags.Items.RODS)
                .addTags(Tags.Items.RODS_WOODEN, Tags.Items.RODS_BLAZE, Tags.Items.RODS_BREEZE)
                .addOptionalTag(forgeItemTagKey("rods"));
        m_206424_(Tags.Items.RODS_BLAZE)
                .m_255245_(Items.f_42585_)
                .addOptionalTag(forgeItemTagKey("rods/blaze"));
        m_206424_(Tags.Items.RODS_BREEZE).m_255245_(Items.f_315544_);
        m_206424_(Tags.Items.RODS_WOODEN)
                .m_255245_(Items.f_42398_)
                .addOptionalTag(forgeItemTagKey("rods/wooden"));
        m_206421_(Tags.Blocks.ROPES, Tags.Items.ROPES);
        m_206421_(Tags.Blocks.SAND, Tags.Items.SAND); // forge:sand
        m_206421_(Tags.Blocks.SAND_COLORLESS, Tags.Items.SAND_COLORLESS); // forge:sand/colorless
        m_206421_(Tags.Blocks.SAND_RED, Tags.Items.SAND_RED); // forge:sand/red
        m_206421_(Tags.Blocks.SANDSTONE_BLOCKS, Tags.Items.SANDSTONE_BLOCKS);
        m_206421_(Tags.Blocks.SANDSTONE_SLABS, Tags.Items.SANDSTONE_SLABS);
        m_206421_(Tags.Blocks.SANDSTONE_STAIRS, Tags.Items.SANDSTONE_STAIRS);
        m_206421_(Tags.Blocks.SANDSTONE_RED_BLOCKS, Tags.Items.SANDSTONE_RED_BLOCKS);
        m_206421_(Tags.Blocks.SANDSTONE_RED_SLABS, Tags.Items.SANDSTONE_RED_SLABS);
        m_206421_(Tags.Blocks.SANDSTONE_RED_STAIRS, Tags.Items.SANDSTONE_RED_STAIRS);
        m_206421_(Tags.Blocks.SANDSTONE_UNCOLORED_BLOCKS, Tags.Items.SANDSTONE_UNCOLORED_BLOCKS);
        m_206421_(Tags.Blocks.SANDSTONE_UNCOLORED_SLABS, Tags.Items.SANDSTONE_UNCOLORED_SLABS);
        m_206421_(Tags.Blocks.SANDSTONE_UNCOLORED_STAIRS, Tags.Items.SANDSTONE_UNCOLORED_STAIRS);
        m_206424_(Tags.Items.SEEDS).addTags(Tags.Items.SEEDS_BEETROOT, Tags.Items.SEEDS_MELON, Tags.Items.SEEDS_PUMPKIN, Tags.Items.SEEDS_WHEAT);
        m_206424_(Tags.Items.SEEDS_BEETROOT).m_255245_(Items.f_42733_);
        m_206424_(Tags.Items.SEEDS_MELON).m_255245_(Items.f_42578_);
        m_206424_(Tags.Items.SEEDS_PUMPKIN).m_255245_(Items.f_42577_);
        m_206424_(Tags.Items.SEEDS_WHEAT).m_255245_(Items.f_42404_);
        m_206424_(Tags.Items.SLIME_BALLS)
                .m_255245_(Items.f_42518_)
                .addOptionalTag(forgeItemTagKey("slimeballs"));
        m_206424_(Tags.Items.SHULKER_BOXES)
                .m_255179_(Items.f_42265_, Items.f_42266_, Items.f_42267_,
                        Items.f_42268_, Items.f_42269_, Items.f_42270_,
                        Items.f_42271_, Items.f_42272_, Items.f_42273_,
                        Items.f_42274_, Items.f_42275_, Items.f_42224_,
                        Items.f_42225_, Items.f_42226_, Items.f_42227_,
                        Items.f_42228_, Items.f_42229_);
        m_206421_(Tags.Blocks.STONES, Tags.Items.STONES);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_BONE_MEAL, Tags.Items.STORAGE_BLOCKS_BONE_MEAL);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_COAL, Tags.Items.STORAGE_BLOCKS_COAL);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_COPPER, Tags.Items.STORAGE_BLOCKS_COPPER);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_DIAMOND, Tags.Items.STORAGE_BLOCKS_DIAMOND);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_DRIED_KELP, Tags.Items.STORAGE_BLOCKS_DRIED_KELP);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_EMERALD, Tags.Items.STORAGE_BLOCKS_EMERALD);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_GOLD, Tags.Items.STORAGE_BLOCKS_GOLD);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_IRON, Tags.Items.STORAGE_BLOCKS_IRON);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_LAPIS, Tags.Items.STORAGE_BLOCKS_LAPIS);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_NETHERITE, Tags.Items.STORAGE_BLOCKS_NETHERITE);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_RAW_COPPER, Tags.Items.STORAGE_BLOCKS_RAW_COPPER);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_RAW_GOLD, Tags.Items.STORAGE_BLOCKS_RAW_GOLD);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_RAW_IRON, Tags.Items.STORAGE_BLOCKS_RAW_IRON);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_REDSTONE, Tags.Items.STORAGE_BLOCKS_REDSTONE);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_SLIME, Tags.Items.STORAGE_BLOCKS_SLIME);
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_WHEAT, Tags.Items.STORAGE_BLOCKS_WHEAT);
        m_206424_(Tags.Items.STRINGS)
                .m_255245_(Items.f_42401_)
                .addOptionalTag(forgeItemTagKey("strings"));
        m_206421_(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
        m_206421_(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);
        m_206424_(Tags.Items.VILLAGER_JOB_SITES).m_255179_(
                Items.f_42768_, Items.f_42770_, Items.f_42543_, Items.f_42771_,
                Items.f_42544_, Items.f_42726_, Items.f_42772_, Items.f_42773_,
                Items.f_42774_, Items.f_42719_, Items.f_42775_, Items.f_42769_, Items.f_42776_);

        // Tools and Armors
        m_206424_(Tags.Items.TOOLS_SHIELD)
                .m_255245_(Items.f_42740_)
                .addOptionalTag(forgeItemTagKey("tools/shields"));
        m_206424_(Tags.Items.TOOLS_BOW)
                .m_255245_(Items.f_42411_)
                .addOptionalTag(forgeItemTagKey("tools/bows"));
        m_206424_(Tags.Items.TOOLS_BRUSH).m_255245_(Items.f_271356_);
        m_206424_(Tags.Items.TOOLS_CROSSBOW)
                .m_255245_(Items.f_42717_)
                .addOptionalTag(forgeItemTagKey("tools/crossbows"));
        m_206424_(Tags.Items.TOOLS_FISHING_ROD)
                .m_255245_(Items.f_42523_)
                .addOptionalTag(forgeItemTagKey("tools/fishing_rods"));
        m_206424_(Tags.Items.TOOLS_SHEAR)
                .m_255245_(Items.f_42574_)
                .addOptionalTag(forgeItemTagKey("tools/shears"));
        m_206424_(Tags.Items.TOOLS_SPEAR).m_255245_(Items.f_42713_);
        m_206424_(Tags.Items.TOOLS_MACE).m_255245_(Items.f_314862_);
        m_206424_(Tags.Items.TOOLS_IGNITER).m_255245_(Items.f_42409_);
        m_206424_(Tags.Items.MINING_TOOL_TOOLS).m_255179_(Items.f_42422_, Items.f_42427_, Items.f_42385_, Items.f_42432_, Items.f_42390_, Items.f_42395_);
        m_206424_(Tags.Items.MELEE_WEAPON_TOOLS).m_255179_(
                Items.f_314862_, Items.f_42713_,
                Items.f_42420_, Items.f_42425_, Items.f_42430_, Items.f_42383_, Items.f_42388_, Items.f_42393_,
                Items.f_42423_, Items.f_42428_, Items.f_42433_, Items.f_42386_, Items.f_42391_, Items.f_42396_
        );
        m_206424_(Tags.Items.RANGED_WEAPON_TOOLS).m_255179_(Items.f_42411_, Items.f_42717_, Items.f_42713_);
        m_206424_(Tags.Items.TOOLS)
                .addTags(ItemTags.f_271207_, ItemTags.f_271298_, ItemTags.f_271360_, ItemTags.f_271138_, ItemTags.f_271388_)
                .addTags(Tags.Items.TOOLS_BOW, Tags.Items.TOOLS_BRUSH, Tags.Items.TOOLS_CROSSBOW, Tags.Items.TOOLS_FISHING_ROD, Tags.Items.TOOLS_SHEAR, Tags.Items.TOOLS_IGNITER, Tags.Items.TOOLS_SHIELD, Tags.Items.TOOLS_SPEAR, Tags.Items.TOOLS_MACE, Tags.Items.MINING_TOOL_TOOLS, Tags.Items.MELEE_WEAPON_TOOLS, Tags.Items.RANGED_WEAPON_TOOLS);
        m_206424_(Tags.Items.ARMORS)
                .addTags(ItemTags.f_316976_, ItemTags.f_314764_, ItemTags.f_316741_, ItemTags.f_317094_)
                .addOptionalTag(forgeItemTagKey("armors"));
        m_206424_(Tags.Items.ENCHANTABLES).addTags(ItemTags.f_317078_, ItemTags.f_317097_, ItemTags.f_316107_, ItemTags.f_316261_, ItemTags.f_314984_, ItemTags.f_314570_, ItemTags.f_313995_, ItemTags.f_316827_, ItemTags.f_317054_, ItemTags.f_317092_, ItemTags.f_314471_, ItemTags.f_314461_, ItemTags.f_314809_, ItemTags.f_314986_);

        // Backwards compat definitions for pre-1.21 legacy `forge:` tags.
        // TODO: Remove backwards compat tag entries in 1.22
        m_206421_(forgeBlockTagKey("barrels"), forgeItemTagKey("barrels"));
        m_206421_(forgeBlockTagKey("barrels/wooden"), forgeItemTagKey("barrels/wooden"));
        m_206424_(forgeItemTagKey("bones")).m_255245_(Items.f_42500_);
        m_206421_(forgeBlockTagKey("bookshelves"), forgeItemTagKey("bookshelves"));
        m_206421_(forgeBlockTagKey("chests"), forgeItemTagKey("chests"));
        m_206421_(forgeBlockTagKey("chests/ender"), forgeItemTagKey("chests/ender"));
        m_206421_(forgeBlockTagKey("chests/trapped"), forgeItemTagKey("chests/trapped"));
        m_206421_(forgeBlockTagKey("chests/wooden"), forgeItemTagKey("chests/wooden"));
        m_206421_(forgeBlockTagKey("cobblestone"), forgeItemTagKey("cobblestone"));
        m_206421_(forgeBlockTagKey("cobblestone/normal"), forgeItemTagKey("cobblestone/normal"));
        m_206421_(forgeBlockTagKey("cobblestone/infested"), forgeItemTagKey("cobblestone/infested"));
        m_206421_(forgeBlockTagKey("cobblestone/mossy"), forgeItemTagKey("cobblestone/mossy"));
        m_206421_(forgeBlockTagKey("cobblestone/deepslate"), forgeItemTagKey("cobblestone/deepslate"));
        m_206424_(forgeItemTagKey("crops"))
                .addTags(forgeItemTagKey("crops/beetroot"), forgeItemTagKey("crops/carrot"), forgeItemTagKey("crops/nether_wart"),
                        forgeItemTagKey("crops/potato"), forgeItemTagKey("crops/wheat"));
        m_206424_(forgeItemTagKey("crops/beetroot")).m_255245_(Items.f_42732_);
        m_206424_(forgeItemTagKey("crops/carrot")).m_255245_(Items.f_42619_);
        m_206424_(forgeItemTagKey("crops/nether_wart")).m_255245_(Items.f_42588_);
        m_206424_(forgeItemTagKey("crops/potato")).m_255245_(Items.f_42620_);
        m_206424_(forgeItemTagKey("crops/wheat")).m_255245_(Items.f_42405_);
        m_206424_(forgeItemTagKey("foods/pie")).m_255245_(Items.f_42687_);
        m_206424_(forgeItemTagKey("dusts")).addTags(forgeItemTagKey("dusts/glowstone"), Tags.Items.DUSTS_PRISMARINE, forgeItemTagKey("dusts/redstone"));
        m_206424_(forgeItemTagKey("dusts/glowstone")).m_255245_(Items.f_42525_);
        m_206424_(forgeItemTagKey("dusts/prismarine")).m_255245_(Items.f_42695_);
        m_206424_(forgeItemTagKey("dusts/redstone")).m_255245_(Items.f_42451_);
        addColored(m_206424_(forgeItemTagKey("dyes"))::addTags, forgeItemTagKey("dyes"), "{color}_dye");
        m_206424_(forgeItemTagKey("eggs")).m_255245_(Items.f_42521_);
        m_206424_(forgeItemTagKey("enchanting_fuels")).m_206428_(forgeItemTagKey("gems/lapis"));
        m_206421_(forgeBlockTagKey("end_stones"), forgeItemTagKey("end_stones"));
        m_206424_(forgeItemTagKey("ender_pearls")).m_255245_(Items.f_42584_);
        m_206424_(forgeItemTagKey("feathers")).m_255245_(Items.f_42402_);
        m_206421_(forgeBlockTagKey("fence_gates"), forgeItemTagKey("fence_gates"));
        m_206421_(forgeBlockTagKey("fence_gates/wooden"), forgeItemTagKey("fence_gates/wooden"));
        m_206421_(forgeBlockTagKey("fences"), forgeItemTagKey("fences"));
        m_206421_(forgeBlockTagKey("fences/nether_brick"), forgeItemTagKey("fences/nether_brick"));
        m_206421_(forgeBlockTagKey("fences/wooden"), forgeItemTagKey("fences/wooden"));
        m_206424_(forgeItemTagKey("gems"))
                .addTags(forgeItemTagKey("gems/amethyst"), forgeItemTagKey("gems/diamond"), forgeItemTagKey("gems/emerald"),
                        forgeItemTagKey("gems/lapis"), forgeItemTagKey("gems/prismarine"), forgeItemTagKey("gems/quartz"));
        m_206424_(forgeItemTagKey("gems/amethyst")).m_255245_(Items.f_151049_);
        m_206424_(forgeItemTagKey("gems/diamond")).m_255245_(Items.f_42415_);
        m_206424_(forgeItemTagKey("gems/emerald")).m_255245_(Items.f_42616_);
        m_206424_(forgeItemTagKey("gems/lapis")).m_255245_(Items.f_42534_);
        m_206424_(forgeItemTagKey("gems/prismarine")).m_255245_(Items.f_42696_);
        m_206424_(forgeItemTagKey("gems/quartz")).m_255245_(Items.f_42692_);
        m_206421_(forgeBlockTagKey("glass"), forgeItemTagKey("glass"));
        m_206421_(forgeBlockTagKey("glass/tinted"), forgeItemTagKey("glass/tinted"));
        m_206421_(forgeBlockTagKey("glass/silica"), forgeItemTagKey("glass/silica"));
        copyColored(forgeBlockTagKey("glass"), forgeItemTagKey("glass"));
        m_206421_(forgeBlockTagKey("glass_panes"), forgeItemTagKey("glass_panes"));
        copyColored(forgeBlockTagKey("glass_panes"), forgeItemTagKey("glass_panes"));
        m_206421_(forgeBlockTagKey("glass_panes/colorless"), forgeItemTagKey("glass_panes/colorless"));
        m_206421_(forgeBlockTagKey("gravel"), forgeItemTagKey("gravel"));
        m_206424_(forgeItemTagKey("gunpowder")).m_255245_(Items.f_42403_);
        m_206424_(forgeItemTagKey("heads")).m_255179_(Items.f_42682_, Items.f_42683_, Items.f_42680_, Items.f_42678_, Items.f_42679_, Items.f_42681_);
        m_206424_(forgeItemTagKey("ingots"))
                .addTags(forgeItemTagKey("ingots/brick"), forgeItemTagKey("ingots/copper"), forgeItemTagKey("ingots/gold"),
                        forgeItemTagKey("ingots/iron"), forgeItemTagKey("ingots/netherite"), forgeItemTagKey("ingots/nether_brick"));
        m_206424_(forgeItemTagKey("ingots/brick")).m_255245_(Items.f_42460_);
        m_206424_(forgeItemTagKey("ingots/copper")).m_255245_(Items.f_151052_);
        m_206424_(forgeItemTagKey("ingots/gold")).m_255245_(Items.f_42417_);
        m_206424_(forgeItemTagKey("ingots/iron")).m_255245_(Items.f_42416_);
        m_206424_(forgeItemTagKey("ingots/netherite")).m_255245_(Items.f_42418_);
        m_206424_(forgeItemTagKey("ingots/nether_brick")).m_255245_(Items.f_42691_);
        m_206424_(forgeItemTagKey("leather")).m_255245_(Items.f_42454_);
        m_206424_(forgeItemTagKey("mushrooms")).m_255179_(Items.f_41952_, Items.f_41953_);
        m_206424_(forgeItemTagKey("nether_stars")).m_255245_(Items.f_42686_);
        m_206421_(forgeBlockTagKey("netherrack"), forgeItemTagKey("netherrack"));
        m_206424_(forgeItemTagKey("nuggets")).addTags(forgeItemTagKey("nuggets/iron"), forgeItemTagKey("nuggets/gold"));
        m_206424_(forgeItemTagKey("nuggets/iron")).m_255245_(Items.f_42749_);
        m_206424_(forgeItemTagKey("nuggets/gold")).m_255245_(Items.f_42587_);
        m_206421_(forgeBlockTagKey("obsidian"), forgeItemTagKey("obsidian"));
        m_206421_(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE, Tags.Items.ORE_BEARING_GROUND_DEEPSLATE);
        m_206421_(Tags.Blocks.ORE_BEARING_GROUND_NETHERRACK, Tags.Items.ORE_BEARING_GROUND_NETHERRACK);
        m_206421_(Tags.Blocks.ORE_BEARING_GROUND_STONE, Tags.Items.ORE_BEARING_GROUND_STONE);
        m_206421_(Tags.Blocks.ORE_RATES_DENSE, Tags.Items.ORE_RATES_DENSE);
        m_206421_(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
        m_206421_(Tags.Blocks.ORE_RATES_SPARSE, Tags.Items.ORE_RATES_SPARSE);
        m_206421_(forgeBlockTagKey("ores"), forgeItemTagKey("ores"));
        m_206421_(Tags.Blocks.ORES_COAL, Tags.Items.ORES_COAL);
        m_206421_(Tags.Blocks.ORES_COPPER, Tags.Items.ORES_COPPER);
        m_206421_(Tags.Blocks.ORES_DIAMOND, Tags.Items.ORES_DIAMOND);
        m_206421_(Tags.Blocks.ORES_EMERALD, Tags.Items.ORES_EMERALD);
        m_206421_(Tags.Blocks.ORES_GOLD, Tags.Items.ORES_GOLD);
        m_206421_(Tags.Blocks.ORES_IRON, Tags.Items.ORES_IRON);
        m_206421_(Tags.Blocks.ORES_LAPIS, Tags.Items.ORES_LAPIS);
        m_206421_(forgeBlockTagKey("ores/quartz"), forgeItemTagKey("ores/quartz"));
        m_206421_(Tags.Blocks.ORES_REDSTONE, Tags.Items.ORES_REDSTONE);
        m_206421_(forgeBlockTagKey("ores/netherite_scrap"), forgeItemTagKey("ores/netherite_scrap"));
        m_206421_(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);
        m_206421_(Tags.Blocks.ORES_IN_GROUND_NETHERRACK, Tags.Items.ORES_IN_GROUND_NETHERRACK);
        m_206421_(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        m_206424_(forgeItemTagKey("raw_materials")).addTags(forgeItemTagKey("raw_materials/copper"), forgeItemTagKey("raw_materials/gold"), forgeItemTagKey("raw_materials/iron"));
        m_206424_(forgeItemTagKey("raw_materials/copper")).m_255245_(Items.f_151051_);
        m_206424_(forgeItemTagKey("raw_materials/gold")).m_255245_(Items.f_151053_);
        m_206424_(forgeItemTagKey("raw_materials/iron")).m_255245_(Items.f_151050_);
        m_206424_(forgeItemTagKey("rods")).addTags(forgeItemTagKey("rods/blaze"), forgeItemTagKey("rods/wooden"));
        m_206424_(forgeItemTagKey("rods/blaze")).m_255245_(Items.f_42585_);
        m_206424_(forgeItemTagKey("rods/wooden")).m_255245_(Items.f_42398_);
        m_206421_(Tags.Blocks.SAND, Tags.Items.SAND);
        m_206421_(Tags.Blocks.SAND_COLORLESS, Tags.Items.SAND_COLORLESS);
        m_206421_(Tags.Blocks.SAND_RED, Tags.Items.SAND_RED);
        m_206421_(forgeBlockTagKey("sandstone"), forgeItemTagKey("sandstone"));
        m_206424_(Tags.Items.SEEDS).addTags(Tags.Items.SEEDS_BEETROOT, Tags.Items.SEEDS_MELON, Tags.Items.SEEDS_PUMPKIN, Tags.Items.SEEDS_WHEAT);
        m_206424_(Tags.Items.SEEDS_BEETROOT).m_255245_(Items.f_42733_);
        m_206424_(Tags.Items.SEEDS_MELON).m_255245_(Items.f_42578_);
        m_206424_(Tags.Items.SEEDS_PUMPKIN).m_255245_(Items.f_42577_);
        m_206424_(Tags.Items.SEEDS_WHEAT).m_255245_(Items.f_42404_);
        m_206424_(forgeItemTagKey("shears")).m_255245_(Items.f_42574_); // yes, it's forge:shears not forge:tools/shears
        m_206424_(forgeItemTagKey("slimeballs")).m_255245_(Items.f_42518_);
        m_206421_(Tags.Blocks.STAINED_GLASS, Tags.Items.STAINED_GLASS);
        m_206421_(Tags.Blocks.STAINED_GLASS_PANES, Tags.Items.STAINED_GLASS_PANES);
        m_206421_(forgeBlockTagKey("stone"), forgeItemTagKey("stone"));
        m_206421_(forgeBlockTagKey("storage_blocks"), forgeItemTagKey("storage_blocks"));
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_AMETHYST, Tags.Items.STORAGE_BLOCKS_AMETHYST);
        m_206421_(forgeBlockTagKey("storage_blocks/coal"), forgeItemTagKey("storage_blocks/coal"));
        m_206421_(forgeBlockTagKey("storage_blocks/copper"), forgeItemTagKey("storage_blocks/copper"));
        m_206421_(forgeBlockTagKey("storage_blocks/diamond"), forgeItemTagKey("storage_blocks/diamond"));
        m_206421_(forgeBlockTagKey("storage_blocks/emerald"), forgeItemTagKey("storage_blocks/emerald"));
        m_206421_(forgeBlockTagKey("storage_blocks/gold"), forgeItemTagKey("storage_blocks/gold"));
        m_206421_(forgeBlockTagKey("storage_blocks/iron"), forgeItemTagKey("storage_blocks/iron"));
        m_206421_(forgeBlockTagKey("storage_blocks/lapis"), forgeItemTagKey("storage_blocks/lapis"));
        m_206421_(Tags.Blocks.STORAGE_BLOCKS_QUARTZ, Tags.Items.STORAGE_BLOCKS_QUARTZ);
        m_206421_(forgeBlockTagKey("storage_blocks/redstone"), forgeItemTagKey("storage_blocks/redstone"));
        m_206421_(forgeBlockTagKey("storage_blocks/raw_copper"), forgeItemTagKey("storage_blocks/raw_copper"));
        m_206421_(forgeBlockTagKey("storage_blocks/raw_gold"), forgeItemTagKey("storage_blocks/raw_gold"));
        m_206421_(forgeBlockTagKey("storage_blocks/raw_iron"), forgeItemTagKey("storage_blocks/raw_iron"));
        m_206421_(forgeBlockTagKey("storage_blocks/netherite"), forgeItemTagKey("storage_blocks/netherite"));
        m_206424_(forgeItemTagKey("string")).m_255245_(Items.f_42401_);
        m_206424_(forgeItemTagKey("tools/shields")).m_255245_(Items.f_42740_);
        m_206424_(forgeItemTagKey("tools/bows")).m_255245_(Items.f_42411_);
        m_206424_(forgeItemTagKey("tools/crossbows")).m_255245_(Items.f_42717_);
        m_206424_(forgeItemTagKey("tools/fishing_rods")).m_255245_(Items.f_42523_);
        m_206424_(forgeItemTagKey("tools/tridents")).m_255245_(Items.f_42713_);
        m_206424_(forgeItemTagKey("tools"))
                .addTags(ItemTags.f_271388_, ItemTags.f_271207_, ItemTags.f_271360_, ItemTags.f_271138_, ItemTags.f_271298_)
                .addTags(forgeItemTagKey("tools/shields"), forgeItemTagKey("tools/bows"), forgeItemTagKey("tools/crossbows"), forgeItemTagKey("tools/fishing_rods"), forgeItemTagKey("tools/tridents"));
        m_206424_(Tags.Items.ARMORS_HELMETS).m_255179_(Items.f_42407_, Items.f_42354_, Items.f_42464_, Items.f_42468_, Items.f_42476_, Items.f_42472_, Items.f_42480_);
        m_206424_(Tags.Items.ARMORS_CHESTPLATES).m_255179_(Items.f_42408_, Items.f_42465_, Items.f_42469_, Items.f_42477_, Items.f_42473_, Items.f_42481_);
        m_206424_(Tags.Items.ARMORS_LEGGINGS).m_255179_(Items.f_42462_, Items.f_42466_, Items.f_42470_, Items.f_42478_, Items.f_42474_, Items.f_42482_);
        m_206424_(Tags.Items.ARMORS_BOOTS).m_255179_(Items.f_42463_, Items.f_42467_, Items.f_42471_, Items.f_42479_, Items.f_42475_, Items.f_42483_);
        m_206424_(forgeItemTagKey("armors")).addTags(Tags.Items.ARMORS_HELMETS, Tags.Items.ARMORS_CHESTPLATES, Tags.Items.ARMORS_LEGGINGS, Tags.Items.ARMORS_BOOTS);
    }

    private void addColored(TagKey<Item> group, String pattern) {
        String prefix = group.f_203868_().m_135815_().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor color : DyeColor.values()) {
            ResourceLocation key = ResourceLocation.m_339182_("minecraft", pattern.replace("{color}", color.m_41065_()));
            TagKey<Item> tag = getForgeItemTag(prefix + color.m_41065_());
            Item item = BuiltInRegistries.f_257033_.m_122327_(key);
            if (item == null || item == Items.f_41852_)
                throw new IllegalStateException("Unknown vanilla item: " + key);
            m_206424_(tag).m_255245_(item);
        }
    }

    private void addColored(Consumer<TagKey<Item>> consumer, TagKey<Item> group, String pattern) {
        String prefix = group.f_203868_().m_135815_() + '/';
        for (DyeColor color  : DyeColor.values()) {
            ResourceLocation key = ResourceLocation.m_339182_("minecraft", pattern.replace("{color}",  color.m_41065_()));
            TagKey<Item> tag = forgeItemTagKey(prefix + color.m_41065_());
            Item item = ForgeRegistries.ITEMS.getValue(key);
            if (item == null || item  == Items.f_41852_)
                throw new IllegalStateException("Unknown vanilla item: " + key.toString());
            m_206424_(tag).m_255245_(item);
            consumer.accept(tag);
        }
    }

    private void copyColored(TagKey<Block> blockGroup, TagKey<Item> itemGroup) {
        String blockPre = blockGroup.f_203868_().m_135815_().toUpperCase(Locale.ENGLISH) + '_';
        String itemPre = itemGroup.f_203868_().m_135815_().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor color  : DyeColor.values()) {
            TagKey<Block> from = getForgeBlockTag(blockPre + color.m_41065_());
            TagKey<Item> to = getForgeItemTag(itemPre + color.m_41065_());
            m_206421_(from, to);
        }
        m_206421_(getForgeBlockTag(blockPre + "colorless"), getForgeItemTag(itemPre + "colorless"));
    }

    private static void addColoredTags(Consumer<TagKey<Item>> consumer, TagKey<Item> group) {
        String prefix = group.f_203868_().m_135815_().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor color : DyeColor.values()) {
            TagKey<Item> tag = getForgeItemTag(prefix + color.m_41065_());
            consumer.accept(tag);
        }
    }

    @SuppressWarnings("unchecked")
    private static TagKey<Block> getForgeBlockTag(String name) {
        try {
            name = name.toUpperCase(Locale.ENGLISH);
            return (TagKey<Block>) Tags.Blocks.class.getDeclaredField(name).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(Tags.Blocks.class.getName() + " is missing tag name: " + name);
        }
    }

    @SuppressWarnings("unchecked")
    private static TagKey<Item> getForgeItemTag(String name) {
        try {
            name = name.toUpperCase(Locale.ENGLISH);
            return (TagKey<Item>) Tags.Items.class.getDeclaredField(name).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(Tags.Items.class.getName() + " is missing tag name: " + name);
        }
    }

    private static ResourceLocation forgeRl(String path) {
        return ResourceLocation.m_339182_("forge", path);
    }

    private static TagKey<Block> forgeBlockTagKey(String path) {
        return BlockTags.create(forgeRl(path));
    }

    private static TagKey<Item> forgeItemTagKey(String path) {
        return ItemTags.create(forgeRl(path));
    }

    @Override
    public String m_6055_() {
        return "Forge Item Tags";
    }
}
