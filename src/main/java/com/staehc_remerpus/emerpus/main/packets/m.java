package com.staehc_remerpus.emerpus.main.packets;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.client.Minecraft;

public class m extends ChannelDuplexHandler {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // Incoming packet handling
            // Can be used for Velocity module packet filtering etc.
        } catch (Exception e) {
            // Silently ignore
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        try {
            // Outgoing packet handling
        } catch (Exception e) {
            // Silently ignore
        }
        super.write(ctx, msg, promise);
    }

    public static void inject() {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc.getConnection() != null && mc.getConnection().getConnection() != null) {
                var channel = mc.getConnection().getConnection().channel();
                if (channel != null && channel.pipeline().get("supreme_handler") == null) {
                    channel.pipeline().addBefore("packet_handler", "supreme_handler", new m());
                }
            }
        } catch (Exception e) {
            // Silently ignore
        }
    }
}