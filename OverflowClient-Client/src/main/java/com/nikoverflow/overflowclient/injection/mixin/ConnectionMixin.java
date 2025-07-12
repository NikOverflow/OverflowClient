package com.nikoverflow.overflowclient.injection.mixin;

import com.nikoverflow.overflowclient.OverflowClient;
import com.nikoverflow.overflowclient.event.impl.packet.PacketReceiveEvent;
import com.nikoverflow.overflowclient.event.impl.packet.PacketSendEvent;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Connection.class)
public class ConnectionMixin {

    @Unique
    private static final ThreadLocal<Packet<?>> sendPacket = new ThreadLocal<>();
    @Unique
    private static final ThreadLocal<Packet<?>> receivedPacket = new ThreadLocal<>();

    @Inject(method = "exceptionCaught", at = @At("HEAD"))
    private void overflow$exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable, CallbackInfo ci) {
        sendPacket.remove();
        receivedPacket.remove();
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true, order = 999)
    private void overflow$channelRead0(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        if(packet instanceof ClientboundBundlePacket clientboundBundlePacket) {
            List<Packet<? super ClientGamePacketListener>> newPackets = new ArrayList<>();
            for(Packet<? super ClientGamePacketListener> subPacket : clientboundBundlePacket.subPackets()) {
                PacketReceiveEvent event = new PacketReceiveEvent(subPacket);
                OverflowClient.getInstance().getEventManager().callEvent(event);
                if(event.isCancelled()) continue;
                @SuppressWarnings("unchecked")
                Packet<? super ClientGamePacketListener> convertedPacket = (Packet<? super ClientGamePacketListener>) event.getPacket();
                newPackets.add(convertedPacket);
            }
            if(newPackets.isEmpty()) ci.cancel();
            receivedPacket.set(new ClientboundBundlePacket(newPackets));
        } else {
            PacketReceiveEvent event = new PacketReceiveEvent(packet);
            OverflowClient.getInstance().getEventManager().callEvent(event);
            if(event.isCancelled()) ci.cancel();
            receivedPacket.set(event.getPacket());
        }
    }

    @ModifyVariable(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), argsOnly = true)
    private Packet<?> overflow$channelRead0$2(Packet<?> packet) {
        try {
            return receivedPacket.get();
        } finally {
            receivedPacket.remove();
        }
    }

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true, order = 999)
    private void overflow$sendPacket(Packet<?> packet, ChannelFutureListener sendListener, boolean flush, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        OverflowClient.getInstance().getEventManager().callEvent(event);
        if(event.isCancelled()) ci.cancel();
        sendPacket.set(event.getPacket());
    }

    @ModifyVariable(method = "sendPacket", at = @At("HEAD"), argsOnly = true)
    private Packet<?> overflow$sendPacket$2(Packet<?> packet) {
        try {
            return sendPacket.get();
        } finally {
            sendPacket.remove();
        }
    }
}