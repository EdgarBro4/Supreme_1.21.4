/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.client.loading;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.Mth;
import net.minecraftforge.fml.StartupMessageManager;
import net.minecraftforge.fml.earlydisplay.DisplayWindow;
import net.minecraftforge.fml.loading.progress.ProgressMeter;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30C;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This is an implementation of the LoadingOverlay that calls back into the early window rendering, as part of the
 * game loading cycle. We completely replace the {@link #render(GuiGraphics, int, int, float)} call from the parent
 * with one of our own, that allows us to blend our early loading screen into the main window, in the same manner as
 * the Mojang screen. It also allows us to see and tick appropriately as the later stages of the loading system run.
 *
 * It is somewhat a copy of the superclass render method.
 */
public class ForgeLoadingOverlay extends LoadingOverlay {
    private final Minecraft minecraft;
    private final ReloadInstance reload;
    private final DisplayWindow displayWindow;
    private final ProgressMeter progress;

    public ForgeLoadingOverlay(final Minecraft mc, final ReloadInstance reloader, final Consumer<Optional<Throwable>> errorConsumer, DisplayWindow displayWindow) {
        super(mc, reloader, errorConsumer, false);
        this.minecraft = mc;
        this.reload = reloader;
        this.displayWindow = displayWindow;
        displayWindow.addMojangTexture(mc.m_91097_().m_118506_(f_96160_).m_117963_());
        this.progress = StartupMessageManager.prependProgressBar("Minecraft Progress", 100);
    }

    public static Supplier<LoadingOverlay> newInstance(Supplier<Minecraft> mc, Supplier<ReloadInstance> ri, Consumer<Optional<Throwable>> handler, DisplayWindow window) {
        return ()->new ForgeLoadingOverlay(mc.get(), ri.get(), handler, window);
    }

    @Override
    protected boolean renderContents(GuiGraphics gui, float fade) {
        progress.setAbsolute(Mth.m_14045_((int)(this.reload.m_7750_() * 100f), 0, 100));

        int alpha = (int)(fade * 255);
        this.displayWindow.render(alpha);

        int width = gui.m_280182_();
        int height = gui.m_280206_();

        var fbWidth = this.minecraft.m_91268_().m_85441_();
        var fbHeight = this.minecraft.m_91268_().m_85442_();
        GL30C.glViewport(0, 0, fbWidth, fbHeight);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, fade);
        Matrix4f pos = gui.m_280168_().m_85850_().m_252922_();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(CoreShaders.f_347205_);
        RenderSystem.setShaderTexture(0, displayWindow.getFramebufferTextureId());
        GL30C.glTexParameterIi(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_FILTER, GlConst.GL_NEAREST);
        GL30C.glTexParameterIi(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAG_FILTER, GlConst.GL_NEAREST);

        var buf = Tesselator.m_85913_().m_339075_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        buf.m_339083_(pos, 0,     0,      0f).m_167083_(0, 0).m_340057_(1f, 1f, 1f, fade);
        buf.m_339083_(pos, 0,     height, 0f).m_167083_(0, 1).m_340057_(1f, 1f, 1f, fade);
        buf.m_339083_(pos, width, height, 0f).m_167083_(1, 1).m_340057_(1f, 1f, 1f, fade);
        buf.m_339083_(pos, width, 0,      0f).m_167083_(1, 0).m_340057_(1f, 1f, 1f, fade);
        BufferUploader.m_231202_(buf.m_339905_());

        // I dont know what exactly this does, but without it the screen flickers black.
        // So as a hack we just render the mojang logo as a 0x0 cube
        // TODO: Remove this when early screen is re-written to not be a texture based renderer
        var logo = gui.getBufferSource().m_6299_(RenderType.m_352786_());
        logo.m_339083_(pos, 0, 0, 0f).m_167083_(0, 0).m_340057_(1f, 1f, 1f, fade);
        logo.m_339083_(pos, 0, 0, 0f).m_167083_(0, 1).m_340057_(1f, 1f, 1f, fade);
        logo.m_339083_(pos, 0, 0, 0f).m_167083_(1, 1).m_340057_(1f, 1f, 1f, fade);
        logo.m_339083_(pos, 0, 0, 0f).m_167083_(1, 0).m_340057_(1f, 1f, 1f, fade);

        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1f);

        return false;
    }
}
