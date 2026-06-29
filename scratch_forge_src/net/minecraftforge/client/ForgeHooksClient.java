/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.client;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Transformation;

import net.minecraft.ChatFormatting;
import net.minecraft.FileUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.SpriteGetter;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.locale.Language;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.ClientPauseChangeEvent;
import net.minecraftforge.client.event.ClientPlayerChangeGameTypeEvent;
import net.minecraftforge.client.event.SystemMessageReceivedEvent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ForgeEventFactoryClient;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.minecraftforge.client.gui.ClientTooltipComponentManager;
import net.minecraftforge.client.gui.ModMismatchDisconnectedScreen;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.GeometryLoaderManager;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import net.minecraftforge.client.textures.TextureAtlasSpriteLoaderManager;
import net.minecraftforge.common.ForgeI18n;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.gametest.ForgeGameTestHooks;
import net.minecraftforge.network.NetworkContext;
import net.minecraftforge.network.NetworkInitialization;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.ServerStatusPing;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.versions.forge.ForgeVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApiStatus.Internal
public class ForgeHooksClient {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker CLIENTHOOKS = MarkerManager.getMarker("CLIENTHOOKS");

    //private static final ResourceLocation ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    /**
     * Contains the *extra* GUI layers.
     * The current top layer stays in Minecraft#currentScreen, and the rest serve as a background for it.
     */
    private static final Stack<Screen> guiLayers = new Stack<>();

    public static void resizeGuiLayers(Minecraft minecraft, int width, int height) {
        guiLayers.forEach(screen -> screen.m_6574_(minecraft, width, height));
    }

    public static void clearGuiLayers(Minecraft minecraft) {
        while (!guiLayers.isEmpty())
            popGuiLayerInternal(minecraft);
    }

    private static void popGuiLayerInternal(Minecraft minecraft) {
        if (minecraft.f_91080_ != null)
            minecraft.f_91080_.m_7861_();
        minecraft.f_91080_ = guiLayers.pop();
    }

    public static void pushGuiLayer(Minecraft minecraft, Screen screen) {
        if (minecraft.f_91080_ != null)
            guiLayers.push(minecraft.f_91080_);
        minecraft.f_91080_ = Objects.requireNonNull(screen);
        screen.m_6575_(minecraft, minecraft.m_91268_().m_85445_(), minecraft.m_91268_().m_85446_());
        minecraft.m_240477_().m_168785_(screen.m_142562_());
    }

    public static void popGuiLayer(Minecraft minecraft) {
        if (guiLayers.isEmpty()) {
            minecraft.m_91152_(null);
            return;
        }

        popGuiLayerInternal(minecraft);
        if (minecraft.f_91080_ != null)
            minecraft.m_240477_().m_168785_(minecraft.f_91080_.m_142562_());
    }

    public static float getGuiFarPlane() {
        // 11000 units for the overlay background,
        // and 10000 units for each layered Screen,

        return 11000.0F + 10000.0F * (1 + guiLayers.size());
    }

    public static boolean onClientPauseChangePre(boolean pause) {
        return MinecraftForge.EVENT_BUS.post(new ClientPauseChangeEvent.Pre(pause));
    }

    public static void onClientPauseChangePost(boolean pause) {
        MinecraftForge.EVENT_BUS.post(new ClientPauseChangeEvent.Post(pause));
    }

    /*
    public static ResourceLocation getArmorTexture(Entity entity, ItemStack armor, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean inner) {
        var result = armor.getItem().getArmorTexture(armor, entity, slot, layer, inner);
        return result != null ? result : layer.texture(inner);
    }
    */

    public static boolean onDrawHighlight(LevelRenderer context, Camera camera, HitResult target, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
        switch (target.m_6662_()) {
            case BLOCK:
                if (!(target instanceof BlockHitResult blockTarget)) return false;
                return MinecraftForge.EVENT_BUS.post(new RenderHighlightEvent.Block(context, camera, blockTarget, partialTick, poseStack, bufferSource));
            case ENTITY:
                if (!(target instanceof EntityHitResult entityTarget)) return false;
                return MinecraftForge.EVENT_BUS.post(new RenderHighlightEvent.Entity(context, camera, entityTarget, partialTick, poseStack, bufferSource));
            default:
                return false; // NO-OP - This doesn't even get called for anything other than blocks and entities
        }
    }

    public static boolean renderSpecificFirstPersonHand(InteractionHand hand, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick, float interpPitch, float swingProgress, float equipProgress, ItemStack stack) {
        return MinecraftForge.EVENT_BUS.post(new RenderHandEvent(hand, poseStack, bufferSource, packedLight, partialTick, interpPitch, swingProgress, equipProgress, stack));
    }

    public static void onTextureStitchedPost(TextureAtlas map) {
        ModLoader.get().postEvent(new TextureStitchEvent.Post(map));
    }

    public static void onBlockColorsInit(BlockColors blockColors) {
        ModLoader.get().postEvent(new RegisterColorHandlersEvent.Block(blockColors));
    }

    public static Model getArmorModel(HumanoidRenderState state, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> _default) {
        return IClientItemExtensions.of(itemStack).getGenericArmorModel(state, itemStack, slot, _default);
    }

    /** Copies humanoid model properties from the original model to another, used for armor models */
    @SuppressWarnings("unchecked")
    public static <T extends HumanoidRenderState> void copyModelProperties(HumanoidModel<T> original, HumanoidModel<?> replacement) {
        // this function does not make use of the <T> generic, so the unchecked cast should be safe
        original.m_102872_((HumanoidModel<T>)replacement);
        replacement.f_102808_.f_104207_ = original.f_102808_.f_104207_;
        replacement.f_102809_.f_104207_ = original.f_102809_.f_104207_;
        replacement.f_102810_.f_104207_ = original.f_102810_.f_104207_;
        replacement.f_102811_.f_104207_ = original.f_102811_.f_104207_;
        replacement.f_102812_.f_104207_ = original.f_102812_.f_104207_;
        replacement.f_102813_.f_104207_ = original.f_102813_.f_104207_;
        replacement.f_102814_.f_104207_ = original.f_102814_.f_104207_;
    }

    //This properly moves the domain, if provided, to the front of the string before concatenating
    public static String fixDomain(String base, String complex) {
        int idx = complex.indexOf(':');
        if (idx == -1)
            return base + complex;

        String name = complex.substring(idx + 1);
        if (idx > 1) {
            String domain = complex.substring(0, idx);
            return domain + ':' + base + name;
        } else {
            return base + name;
        }
    }

    /**
     * Initialization of Forge Renderers.
     */
    static {
        //FluidRegistry.renderIdFluid = RenderingRegistry.getNextAvailableRenderId();
        //RenderingRegistry.registerBlockHandler(RenderBlockFluid.instance);
    }

    private static VersionChecker.Status getForgeVersionStatus() {
        final class LazyInit {
            private static final VersionChecker.Status INSTANCE = ForgeVersion.getStatus();

            static {
                forgeStatusLine = switch (INSTANCE) {
                    // case FAILED -> " Version check failed";
                    // case UP_TO_DATE -> "Forge up to date";
                    // case AHEAD -> "Using non-recommended Forge build, issues may arise.";
                    case OUTDATED, BETA_OUTDATED -> I18n.m_118938_("forge.update.newversion", ForgeVersion.getTarget());
                    default -> null;
                };
            }

            private LazyInit() {}
        }

        return LazyInit.INSTANCE;
    }

    // NO-OP method, kept for bin-compat. Used in TitleScreen.java
    @Deprecated(forRemoval = true, since = "1.21.4")
    public static void renderMainMenu(TitleScreen gui, GuiGraphics graphics, Font font, int width, int height, int alpha) {
        /*
        VersionChecker.Status status = getForgeVersionStatus();

        if (status == VersionChecker.Status.BETA || status == VersionChecker.Status.BETA_OUTDATED) {
            // Render a warning at the top of the screen
            Component line = Component.translatable("forge.update.beta.1", ChatFormatting.RED, ChatFormatting.RESET).withStyle(ChatFormatting.RED);
            graphics.drawCenteredString(font, line, width / 2, 4, 0xFFFFFF | alpha);
            line = Component.translatable("forge.update.beta.2");
            graphics.drawCenteredString(font, line, width / 2, 4 + (font.lineHeight + 1), 0xFFFFFF | alpha);
        }
         */
    }

    public static String forgeStatusLine;

    @Nullable
    public static SoundInstance playSound(SoundEngine manager, SoundInstance sound) {
        return MinecraftForge.EVENT_BUS.fire(new PlaySoundEvent(manager, sound)).getSound();
    }

    public static void drawScreen(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.m_280168_().m_85836_();
        for (Screen layer : guiLayers) {
            // Prevent the background layers from thinking the mouse is over their controls and showing them as highlighted.
            drawScreenInternal(layer, guiGraphics, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTick);
            guiGraphics.m_280168_().m_252880_(0, 0, 10000);
        }
        drawScreenInternal(screen, guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.m_280168_().m_85849_();
    }

    private static void drawScreenInternal(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!MinecraftForge.EVENT_BUS.post(new ScreenEvent.Render.Pre(screen, guiGraphics, mouseX, mouseY, partialTick)))
            screen.m_280264_(guiGraphics, mouseX, mouseY, partialTick);
        MinecraftForge.EVENT_BUS.post(new ScreenEvent.Render.Post(screen, guiGraphics, mouseX, mouseY, partialTick));
    }

    public static Vector3f getFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, float fogRed, float fogGreen, float fogBlue) {
        // Modify fog color depending on the fluid
        FluidState state = level.m_6425_(camera.m_90588_());
        Vector3f fluidFogColor = new Vector3f(fogRed, fogGreen, fogBlue);
        if (camera.m_90583_().f_82480_ < (double)((float)camera.m_90588_().m_123342_() + state.m_76155_(level, camera.m_90588_())))
            fluidFogColor = IClientFluidTypeExtensions.of(state).modifyFogColor(camera, partialTick, level, renderDistance, darkenWorldAmount, fluidFogColor);

        ViewportEvent.ComputeFogColor event = new ViewportEvent.ComputeFogColor(camera, partialTick, fluidFogColor.x(), fluidFogColor.y(), fluidFogColor.z());
        MinecraftForge.EVENT_BUS.post(event);

        fluidFogColor.set(event.getRed(), event.getGreen(), event.getBlue());
        return fluidFogColor;
    }

    public static FogParameters getFogParameters(FogRenderer.FogMode mode, FogType type, Camera camera, float partialTick, float renderDistance, FogParameters original) {
        var ret = original;
        // Modify fog rendering depending on the fluid
        FluidState state = camera.m_90592_().m_9236_().m_6425_(camera.m_90588_());
        if (camera.m_90583_().f_82480_ < (double)((float)camera.m_90588_().m_123342_() + state.m_76155_(camera.m_90592_().m_9236_(), camera.m_90588_())))
            ret = IClientFluidTypeExtensions.of(state).modifyFogRender(camera, mode, renderDistance, partialTick, ret);

        var event = new ViewportEvent.RenderFog(mode, type, camera, partialTick, ret.f_349109_(), ret.f_346953_(), ret.f_347351_());
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return new FogParameters(
                event.getNearPlaneDistance(), event.getFarPlaneDistance(), event.getFogShape(),
                ret.f_347897_(), ret.f_346815_(), ret.f_349182_(), ret.f_348838_()
            );
        }

        return ret;
    }

    public static void onModifyBakingResult(ModelBakery modelBakery, ModelBakery.BakingResult results) {
        ModLoader.get().postEvent(new ModelEvent.ModifyBakingResult(modelBakery, results));
    }

    public static void onModelBake(ModelManager modelManager, ModelBakery modelBakery) {
        ModLoader.get().postEvent(new ModelEvent.BakingCompleted(modelManager, modelBakery));
    }

    public static TextureAtlasSprite[] getFluidSprites(BlockAndTintGetter level, BlockPos pos, FluidState fluidStateIn) {
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluidStateIn);
        ResourceLocation overlayTexture = props.getOverlayTexture(fluidStateIn, level, pos);
        @SuppressWarnings("deprecation")
        var BLOCKS = TextureAtlas.f_118259_;
        var atlas = Minecraft.m_91087_().m_91258_(BLOCKS);
        return new TextureAtlasSprite[] {
            atlas.apply(props.getStillTexture(fluidStateIn, level, pos)),
            atlas.apply(props.getFlowingTexture(fluidStateIn, level, pos)),
            overlayTexture == null ? null : atlas.apply(overlayTexture),
        };
    }

    public static boolean calculateFaceWithoutAO(BlockAndTintGetter getter, BlockState state, BlockPos pos, BakedQuad quad, boolean isFaceCubic, float[] brightness, int[] lightmap) {
        if (quad.hasAmbientOcclusion())
            return false;

        BlockPos lightmapPos = isFaceCubic ? pos.m_121945_(quad.m_111306_()) : pos;

        brightness[0] = brightness[1] = brightness[2] = brightness[3] = getter.m_7717_(quad.m_111306_(), quad.m_111307_());
        lightmap[0] = lightmap[1] = lightmap[2] = lightmap[3] = LevelRenderer.m_109537_(getter, state, lightmapPos);
        return true;
    }

    private static int slotMainHand = 0;

    public static boolean shouldCauseReequipAnimation(@NotNull ItemStack from, @NotNull ItemStack to, int slot) {
        boolean fromInvalid = from.m_41619_();
        boolean toInvalid   = to.m_41619_();

        if (fromInvalid && toInvalid) return false;
        if (fromInvalid || toInvalid) return true;

        boolean changed = false;
        if (slot != -1) {
            changed = slot != slotMainHand;
            slotMainHand = slot;
        }
        return from.m_41720_().shouldCauseReequipAnimation(from, to, changed);
    }

    public static CustomizeGuiOverlayEvent.BossEventProgress onCustomizeBossEventProgress(GuiGraphics guiGraphics, Window window, LerpingBossEvent bossInfo, int x, int y, int increment) {
        var evt = new CustomizeGuiOverlayEvent.BossEventProgress(window, guiGraphics,
                Minecraft.m_91087_().m_353064_().m_338527_(false), bossInfo, x, y, increment);
        MinecraftForge.EVENT_BUS.post(evt);
        return evt;
    }

    public static void onCustomizeChatEvent(GuiGraphics guiGraphics, ChatComponent chat, Window window, int mouseX, int mouseY, int tickCount) {
        var minecraft = Minecraft.m_91087_();
        var evt = new CustomizeGuiOverlayEvent.Chat(window, guiGraphics, minecraft.m_353064_().m_338557_(), 0, chat.m_93814_() - 40);
        MinecraftForge.EVENT_BUS.post(evt);
        guiGraphics.m_280168_().m_85836_();
        // We give the absolute Y position of the chat component in the event and account for the chat component's own offsetting here.
        guiGraphics.m_280168_().m_85837_(evt.getPosX(), (evt.getPosY() - chat.m_93814_() + 40) / chat.m_93815_(), 0.0D);
        chat.m_280165_(guiGraphics, tickCount, mouseX, mouseY, false);
        guiGraphics.m_280168_().m_85849_();
    }

    public static void onCustomizeDebugEvent(GuiGraphics guiGraphics, Window window, float partialTick, List<String> text, boolean isLeft) {
        var evt = new CustomizeGuiOverlayEvent.DebugText(window, guiGraphics, partialTick, text,
                isLeft ? CustomizeGuiOverlayEvent.DebugText.Side.Left : CustomizeGuiOverlayEvent.DebugText.Side.Right);
        MinecraftForge.EVENT_BUS.post(evt);
    }

    public static void onClientChangeGameType(PlayerInfo info, GameType currentGameMode, GameType newGameMode) {
        if (currentGameMode != newGameMode) {
            MinecraftForge.EVENT_BUS.post(new ClientPlayerChangeGameTypeEvent(info, currentGameMode, newGameMode));
        }
    }

    public static void onMovementInputUpdate(Player player, ClientInput movementInput) {
        MinecraftForge.EVENT_BUS.post(new MovementInputUpdateEvent(player, movementInput));
    }

    public static boolean onScreenKeyPressed(Screen screen, int keyCode, int scanCode, int modifiers) {
        return ForgeEventFactoryClient.onScreenKeyPressedPre(screen, keyCode, scanCode, modifiers)
            || screen.m_7933_(keyCode, scanCode, modifiers)
            || ForgeEventFactoryClient.onScreenKeyPressedPost(screen, keyCode, scanCode, modifiers);
    }

    public static boolean onScreenKeyReleased(Screen screen, int keyCode, int scanCode, int modifiers) {
        return ForgeEventFactoryClient.onScreenKeyReleasedPre(screen, keyCode, scanCode, modifiers)
            || screen.m_7920_(keyCode, scanCode, modifiers)
            || ForgeEventFactoryClient.onScreenKeyReleasedPost(screen, keyCode, scanCode, modifiers);
    }

    public static boolean onScreenCharTyped(Screen screen, char codePoint, int modifiers) {
        return ForgeEventFactoryClient.onScreenCharTypedPre(screen, codePoint, modifiers)
            || screen.m_5534_(codePoint, modifiers)
            || ForgeEventFactoryClient.onScreenCharTypedPost(screen, codePoint, modifiers);
    }

    public static void onKeyInput(int key, int scanCode, int action, int modifiers) {
        MinecraftForge.EVENT_BUS.post(new InputEvent.Key(key, scanCode, action, modifiers));
    }

    public static boolean isNameplateInRenderDistance(Entity entity, double squareDistance) {
        if (entity instanceof LivingEntity living) {
            var attribute = living.m_21051_(ForgeMod.NAMETAG_DISTANCE.getHolder().get());
            if (attribute != null) {
                return !(squareDistance > (attribute.m_22135_() * attribute.m_22135_()));
            }
        }
        return !(squareDistance > 4096.0f);
    }

    public static boolean shouldRenderEffect(MobEffectInstance effectInstance) {
        return IClientMobEffectExtensions.of(effectInstance).isVisibleInInventory(effectInstance);
    }

    @Nullable
    public static SpriteContents loadSpriteContents(ResourceLocation name, Resource resource, FrameSize frameSize, NativeImage image, ResourceMetadata animationMeta) {
        try {
            ForgeTextureMetadata forgeMeta = ForgeTextureMetadata.forResource(resource);
            return forgeMeta.loader() == null ? null : forgeMeta.loader().loadContents(name, resource, frameSize, image, animationMeta, forgeMeta);
        } catch (IOException e) {
            LOGGER.error("Unable to get Forge metadata for {}, falling back to vanilla loading", name);
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static TextureAtlasSprite loadTextureAtlasSprite(ResourceLocation atlasName, SpriteContents contents, int atlasWidth, int atlasHeight, int spriteX, int spriteY, int mipmapLevel) {
        if (contents.forgeMeta == null || contents.forgeMeta.loader() == null)
            return null;

        return contents.forgeMeta.loader().makeSprite(atlasName, contents, atlasWidth, atlasHeight, spriteX, spriteY, mipmapLevel);
    }

    private static final Map<ModelLayerLocation, Supplier<LayerDefinition>> layerDefinitions = new HashMap<>();

    public static void registerLayerDefinition(ModelLayerLocation layerLocation, Supplier<LayerDefinition> supplier) {
        layerDefinitions.put(layerLocation, supplier);
    }

    public static void loadLayerDefinitions(ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder) {
        for (var entry : layerDefinitions.entrySet()) {
            builder.put(entry.getKey(), entry.getValue().get());
        }
    }

    public static void processForgeListPingData(ServerStatus packet, ServerData target) {
        packet.forgeData().ifPresentOrElse(forgeData -> {
            final Map<String, String> mods = forgeData.getRemoteModData();
            final Map<ResourceLocation, ServerStatusPing.ChannelData> remoteChannels = forgeData.getRemoteChannels();
            final int fmlver = forgeData.getFMLNetworkVersion();

            int wantedVer = NetworkInitialization.getVersion();
            boolean fmlNetMatches = fmlver == wantedVer;
            boolean channelsMatch = NetworkRegistry.checkListPingCompatibilityForClient(remoteChannels);
            AtomicBoolean result = new AtomicBoolean(true);
            final List<String> extraClientMods = new ArrayList<>();
            ModList.get().forEachModContainer((modid, mc) ->
                    mc.getCustomExtension(IExtensionPoint.DisplayTest.class).ifPresent(ext-> {
                        boolean foundModOnServer = ext.remoteVersionTest().test(mods.get(modid), true);
                        result.compareAndSet(true, foundModOnServer);
                        if (!foundModOnServer)
                            extraClientMods.add(modid);
                    })
            );
            boolean modsMatch = result.get();

            final Map<String, String> extraServerMods = mods.entrySet().stream().
                    filter(e -> !Objects.equals(IExtensionPoint.DisplayTest.IGNORESERVERONLY, e.getValue())).
                    filter(e -> !ModList.get().isLoaded(e.getKey())).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            LOGGER.debug(CLIENTHOOKS, "Received FML ping data from server at {}: FMLNETVER={}, mod list is compatible : {}, channel list is compatible: {}, extra server mods: {}", target.f_105363_, fmlver, modsMatch, channelsMatch, extraServerMods);

            String extraReason = null;

            if (!extraServerMods.isEmpty()) {
                extraReason = "fml.menu.multiplayer.extraservermods";
                LOGGER.info(CLIENTHOOKS, ForgeI18n.parseMessage(extraReason) + ": {}", extraServerMods.entrySet().stream()
                        .map(e -> e.getKey() + "@" + e.getValue())
                        .collect(Collectors.joining(", ")));
            }

            if (!modsMatch) {
                extraReason = "fml.menu.multiplayer.modsincompatible";
                LOGGER.info(CLIENTHOOKS, "Client has mods that are missing on server: {}", extraClientMods);
            }

            if (!channelsMatch)
                extraReason = "fml.menu.multiplayer.networkincompatible";
            if (fmlver < wantedVer)
                extraReason = "fml.menu.multiplayer.serveroutdated";
            if (fmlver > wantedVer)
                extraReason = "fml.menu.multiplayer.clientoutdated";

            target.forgeData = new ExtendedServerListData("FML", extraServerMods.isEmpty() && fmlNetMatches && channelsMatch && modsMatch, mods.size(), extraReason, forgeData.isTruncated());
        }, () -> target.forgeData = new ExtendedServerListData("VANILLA", NetworkRegistry.canConnectToVanillaServer(),0, null));
    }

    private static final ResourceLocation ICON_SHEET = ResourceLocation.m_339182_(ForgeVersion.MOD_ID, "textures/gui/icons.png");
    public static void drawForgePingInfo(JoinMultiplayerScreen gui, ServerData target, GuiGraphics guiGraphics, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
        int idx;
        String tooltip;
        if (target.forgeData == null)
            return;
        switch (target.forgeData.type()) {
            case "FML":
                if (target.forgeData.isCompatible()) {
                    idx = 0;
                    tooltip = ForgeI18n.parseMessage("fml.menu.multiplayer.compatible", target.forgeData.numberOfMods());
                } else {
                    idx = 16;
                    if(target.forgeData.extraReason() != null) {
                        String extraReason = ForgeI18n.parseMessage(target.forgeData.extraReason());
                        tooltip = ForgeI18n.parseMessage("fml.menu.multiplayer.incompatible.extra", extraReason);
                    } else {
                        tooltip = ForgeI18n.parseMessage("fml.menu.multiplayer.incompatible");
                    }
                }
                if (target.forgeData.truncated())
                    tooltip += "\n" + ForgeI18n.parseMessage("fml.menu.multiplayer.truncated");
                break;
            case "VANILLA":
                if (target.forgeData.isCompatible()) {
                    idx = 48;
                    tooltip = ForgeI18n.parseMessage("fml.menu.multiplayer.vanilla");
                } else {
                    idx = 80;
                    tooltip = ForgeI18n.parseMessage("fml.menu.multiplayer.vanilla.incompatible");
                }
                break;
            default:
                idx = 64;
                tooltip = ForgeI18n.parseMessage("fml.menu.multiplayer.unknown", target.forgeData.type());
        }

        guiGraphics.m_280218_(RenderType::m_355513_, ICON_SHEET, x + width - 18, y + 10, 16, 16, 0, idx, 16, 16, 256, 256);

        if(relativeMouseX > width - 15 && relativeMouseX < width && relativeMouseY > 10 && relativeMouseY < 26) {
            //this is not the most proper way to do it,
            //but works best here and has the least maintenance overhead
            var lines = Arrays.stream(tooltip.split("\n")).map(Component::m_237113_).toList();
            gui.m_257959_(Lists.transform(lines, Component::m_7532_));
        }
    }

    private static Connection getClientConnection() {
        return Minecraft.m_91087_().m_91403_() != null ? Minecraft.m_91087_().m_91403_().m_104910_() : null;
    }

    public static void handleClientLevelClosing(ClientLevel level) {
        Connection client = getClientConnection();
        // ONLY revert a non-local connection
        if (client != null && !client.m_129531_())
            GameData.revertToFrozen();
    }

    public static void onRegisterParticleProviders(ParticleEngine particleEngine) {
        ModLoader.get().postEvent(new RegisterParticleProvidersEvent(particleEngine));
    }

    public static void onRegisterKeyMappings(Options options) {
        ModLoader.get().postEvent(new RegisterKeyMappingsEvent(options));
    }

    @Nullable
    public static Component onClientChat(ChatType.Bound boundChatType, Component message, UUID sender) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent(boundChatType, message, sender);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getMessage();
    }

    @Nullable
    public static Component onClientPlayerChat(ChatType.Bound boundChatType, Component message, PlayerChatMessage playerChatMessage, UUID sender) {
        ClientChatReceivedEvent.Player event = new ClientChatReceivedEvent.Player(boundChatType, message, playerChatMessage, sender);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getMessage();
    }

    @Nullable
    public static Component onClientSystemMessage(Component message, boolean overlay) {
        var event = new SystemMessageReceivedEvent(message, overlay);
        return MinecraftForge.EVENT_BUS.post(event) ? null : event.getMessage();

    }

    @NotNull
    public static String onClientSendMessage(String message) {
        ClientChatEvent event = new ClientChatEvent(message);
        return MinecraftForge.EVENT_BUS.post(event) ? "" : event.getMessage();
    }

    public static final Supplier<ShaderProgram> SHADER_UNLIT_TRANSLUCENT = Suppliers.memoize(() ->
        new ShaderProgram(ResourceLocation.m_339182_("forge", "rendertype_entity_unlit_translucent"), DefaultVertexFormat.f_85812_, ShaderDefines.f_347193_)
    );

    public static Font getTooltipFont(@NotNull ItemStack stack, Font fallbackFont) {
        Font stackFont = IClientItemExtensions.of(stack).getFont(stack, IClientItemExtensions.FontContext.TOOLTIP);
        return stackFont == null ? fallbackFont : stackFont;
    }

    public static RenderTooltipEvent.Pre onRenderTooltipPre(@NotNull ItemStack stack, GuiGraphics graphics, int x, int y, int screenWidth, int screenHeight, @NotNull List<ClientTooltipComponent> components, @NotNull Font fallbackFont, @NotNull ClientTooltipPositioner positioner) {
        var preEvent = new RenderTooltipEvent.Pre(stack, graphics, x, y, screenWidth, screenHeight, getTooltipFont(stack, fallbackFont), components, positioner);
        MinecraftForge.EVENT_BUS.post(preEvent);
        return preEvent;
    }

    public static List<ClientTooltipComponent> gatherTooltipComponents(ItemStack stack, List<? extends FormattedText> textElements, int mouseX, int screenWidth, int screenHeight, Font fallbackFont) {
        return gatherTooltipComponents(stack, textElements, Optional.empty(), mouseX, screenWidth, screenHeight, fallbackFont);
    }

    public static List<ClientTooltipComponent> gatherTooltipComponents(ItemStack stack, List<? extends FormattedText> textElements, Optional<TooltipComponent> itemComponent, int mouseX, int screenWidth, int screenHeight, Font fallbackFont) {
        List<Either<FormattedText, TooltipComponent>> elements = textElements.stream()
                .map((Function<FormattedText, Either<FormattedText, TooltipComponent>>) Either::left)
                .collect(Collectors.toCollection(ArrayList::new));
        itemComponent.ifPresent(c -> elements.add(1, Either.right(c)));
        return gatherTooltipComponentsFromElements(stack, elements, mouseX, screenWidth, screenHeight, fallbackFont);
    }

    public static List<ClientTooltipComponent> gatherTooltipComponentsFromElements(ItemStack stack, List<Either<FormattedText, TooltipComponent>> elements, int mouseX, int screenWidth, int screenHeight, Font fallbackFont) {
        Font font = getTooltipFont(stack, fallbackFont);

        var event = new RenderTooltipEvent.GatherComponents(stack, screenWidth, screenHeight, elements, -1);
        if (MinecraftForge.EVENT_BUS.post(event)) return List.of();

        // text wrapping
        int tooltipTextWidth = event.getTooltipElements().stream()
                .mapToInt(either -> either.map(font::m_92852_, component -> 0))
                .max()
                .orElse(0);

        boolean needsWrap = false;

        int tooltipX = mouseX + 12;
        if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
            tooltipX = mouseX - 16 - tooltipTextWidth;
            if (tooltipX < 4) { // if the tooltip doesn't fit on the screen
                if (mouseX > screenWidth / 2)
                    tooltipTextWidth = mouseX - 12 - 8;
                else
                    tooltipTextWidth = screenWidth - 16 - mouseX;
                needsWrap = true;
            }
        }

        if (event.getMaxWidth() > 0 && tooltipTextWidth > event.getMaxWidth()) {
            tooltipTextWidth = event.getMaxWidth();
            needsWrap = true;
        }

        int tooltipTextWidthF = tooltipTextWidth;
        if (needsWrap) {
            return event.getTooltipElements().stream()
                    .flatMap(either -> either.map(
                            text -> splitLine(text, font, tooltipTextWidthF),
                            component -> Stream.of(ClientTooltipComponent.m_169950_(component))
                    ))
                    .toList();
        }
        return event.getTooltipElements().stream()
                .map(either -> either.map(
                        text -> ClientTooltipComponent.m_169948_(text instanceof Component ? ((Component) text).m_7532_() : Language.m_128107_().m_5536_(text)),
                        ClientTooltipComponent::m_169950_
                ))
                .toList();
    }

    private static Stream<ClientTooltipComponent> splitLine(FormattedText text, Font font, int maxWidth) {
        if (text instanceof Component component && component.getString().isEmpty())
            return Stream.of(component.m_7532_()).map(ClientTooltipComponent::m_169948_);
        return font.m_92923_(text, maxWidth).stream().map(ClientTooltipComponent::m_169948_);
    }

    public static Comparator<ParticleRenderType> makeParticleRenderTypeComparator(List<ParticleRenderType> renderOrder) {
        Comparator<ParticleRenderType> vanillaComparator = Comparator.comparingInt(renderOrder::indexOf);
        return (typeOne, typeTwo) -> {
            boolean vanillaOne = renderOrder.contains(typeOne);
            boolean vanillaTwo = renderOrder.contains(typeTwo);

            if (vanillaOne && vanillaTwo)
                return vanillaComparator.compare(typeOne, typeTwo);
            if (!vanillaOne && !vanillaTwo)
                return Integer.compare(System.identityHashCode(typeOne), System.identityHashCode(typeTwo));
            return vanillaOne ? -1 : 1;
        };
    }

    public static boolean isBlockInSolidLayer(BlockState state) {
        var model = Minecraft.m_91087_().m_91289_().m_110910_(state);
        return model.getRenderTypes(state, RandomSource.m_216327_(), ModelData.EMPTY).contains(RenderType.m_110451_());
    }

    public static void createWorldConfirmationScreen(Runnable doConfirmedWorldLoad) {
        Component title = Component.m_237115_("selectWorld.backupQuestion.experimental");
        Component msg = Component.m_237115_("selectWorld.backupWarning.experimental")
                .m_130946_("\n\n")
                .m_7220_(Component.m_237115_("forge.selectWorld.backupWarning.experimental.additional"));

        Screen screen = new ConfirmScreen(confirmed -> {
            if (confirmed)
                doConfirmedWorldLoad.run();
            else
                Minecraft.m_91087_().m_91152_(null);
        }, title, msg, CommonComponents.f_130659_, CommonComponents.f_130656_);

        Minecraft.m_91087_().m_91152_(screen);
    }

    public static boolean renderFireOverlay(Player player, PoseStack mat) {
        return renderBlockOverlay(player, mat, RenderBlockScreenEffectEvent.OverlayType.FIRE, Blocks.f_50083_.m_49966_(), player.m_20183_());
    }

    public static boolean renderWaterOverlay(Player player, PoseStack mat) {
        return renderBlockOverlay(player, mat, RenderBlockScreenEffectEvent.OverlayType.WATER, Blocks.f_49990_.m_49966_(), player.m_20183_());
    }

    public static boolean renderBlockOverlay(Player player, PoseStack mat, RenderBlockScreenEffectEvent.OverlayType type, BlockState block, BlockPos pos) {
        return MinecraftForge.EVENT_BUS.post(new RenderBlockScreenEffectEvent(player, mat, type, block, pos));
    }

    public static int getMaxMipmapLevel(int width, int height) {
        return Math.min(
                Mth.m_14173_(Math.max(1, width)),
                Mth.m_14173_(Math.max(1, height))
        );
    }

    public static ResourceLocation getShaderImportLocation(String basePath, boolean isRelative, String importPath) {
        final var loc = ResourceLocation.m_338530_(importPath);
        final var normalised = FileUtil.m_179924_(
            (isRelative ? basePath : "shaders/include/") + loc.m_135815_());
        return loc.m_247449_(normalised);
    }

    // Make sure the below method is only ever called once (by forge).
    private static boolean initializedClientHooks = false;
    // Runs during Minecraft construction, before initial resource loading.
    @ApiStatus.Internal
    public static void initClientHooks(Minecraft mc, ReloadableResourceManager resourceManager) {
        if (initializedClientHooks)
            throw new IllegalStateException("Client hooks initialized more than once");
        initializedClientHooks = true;

        ForgeGameTestHooks.registerGametests();
        ModLoader.get().postEvent(new RegisterClientReloadListenersEvent(resourceManager));
        ModLoader.get().postEvent(new EntityRenderersEvent.RegisterLayerDefinitions());
        ModLoader.get().postEvent(new EntityRenderersEvent.RegisterRenderers());
        TextureAtlasSpriteLoaderManager.init();
        ClientTooltipComponentManager.init();
        EntitySpectatorShaderManager.init();
        ForgeHooksClient.onRegisterKeyMappings(mc.f_91066_);
        //GuiOverlayManager.init();
        DimensionSpecialEffectsManager.init();
        NamedRenderTypeManager.init();
        ColorResolverManager.init();
        ItemDecoratorHandler.init();
        PresetEditorManager.init();
    }

    public static boolean onClientDisconnect(Connection connection, Minecraft mc, Screen parent, Component message) {
        var mismatch = NetworkContext.get(connection).getMismatchs();
        if (mismatch == null)
            return false;
        mc.m_91152_(new ModMismatchDisconnectedScreen(parent, CommonComponents.f_130661_, message, mismatch));
        return true;
    }

    public static boolean onScreenMouseDrag(Screen screen, double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
        return ForgeEventFactoryClient.onScreenMouseDragPre(screen, mouseX, mouseY, mouseButton, dragX, dragY)
            || screen.m_7979_(mouseX, mouseY, mouseButton, dragX, dragY)
            || ForgeEventFactoryClient.onScreenMouseDragPost(screen, mouseX, mouseY, mouseButton, dragX, dragY);
    }

    public static BlockModel deserializeBlockModel(BlockModel model, List<BlockElement> elements, JsonObject json, JsonDeserializationContext context) {
        IUnbakedGeometry<?> geometry = null;
        if (json.has("loader")) {
            var name = ResourceLocation.m_338530_(GsonHelper.m_13906_(json, "loader"));
            var loader = GeometryLoaderManager.get(name);
            if (loader == null)
                throw new JsonParseException(String.format(Locale.ENGLISH, "Model loader '%s' not found. Registered loaders: %s", name, GeometryLoaderManager.getLoaderList()));

            geometry = loader.read(json, context);
        }

        if (geometry != null) {
            elements.clear();
            model.customData.setCustomGeometry(geometry);
        }

        if (json.has("transform")) {
            JsonElement transform = json.get("transform");
            model.customData.setRootTransform(context.deserialize(transform, Transformation.class));
        }

        if (json.has("render_type")) {
            var renderTypeHintName = GsonHelper.m_13906_(json, "render_type");
            model.customData.setRenderTypeHint(ResourceLocation.m_338530_(renderTypeHintName));
        }

        if (json.has("render_type_fast")) {
            var renderTypeHintName = GsonHelper.m_13906_(json, "render_type_fast");
            model.customData.setRenderTypeFastHint(ResourceLocation.m_338530_(renderTypeHintName));
        }

        if (json.has("visibility")) {
            var visibility = GsonHelper.m_13930_(json, "visibility");
            for (var part : visibility.entrySet())
                model.customData.visibilityData.setVisibilityState(part.getKey(), part.getValue().getAsBoolean());
        }

        return model;
    }

    /** This is a dirty fucking hack, but it needs to send in the top most render type. */
    public static ModelBaker wrapRenderType(ModelBaker parent, RenderTypeGroup group) {
        if (group == null || group == RenderTypeGroup.EMPTY || parent.renderType() != null)
            return parent;
        return new WrapedModelBaker(parent, group);
    }

    public static ModelBaker wrapRenderType(ModelBaker parent, RenderTypeGroup group, RenderTypeGroup groupFast) {
        if (group == null || group == RenderTypeGroup.EMPTY || parent.renderType() != null)
            return parent;
        return new WrapedModelBaker(parent, group, groupFast);
    }

    // a record for performance reasons
    private record WrapedModelBaker(ModelBaker parent, RenderTypeGroup group, RenderTypeGroup renderTypeFast) implements ModelBaker {
        private WrapedModelBaker(ModelBaker parent, RenderTypeGroup group) {
            this(parent, group, RenderTypeGroup.EMPTY);
        }

        @Override
        public RenderTypeGroup renderType() {
            return group;
        }

        @Override
        public BakedModel m_245240_(ResourceLocation name, ModelState state) {
            return parent.m_245240_(name, state);
        }

        @Override
        public SpriteGetter m_372755_() {
            return parent.m_372755_();
        }

        @Override
        public ModelDebugName m_373542_() {
            return parent.m_373542_();
        }
    }
}
