package com.nikoverflow.overflowclient.injection.mixin;

import com.nikoverflow.overflowclient.OverflowClient;
import com.nikoverflow.overflowclient.event.impl.packet.PacketSendEvent;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ConnectionMixin {

    @Unique
    private final ThreadLocal<Packet<?>> packet = new ThreadLocal<>();

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void overflow$sendPacket(Packet<?> packet, PacketSendListener sendListener, boolean flush, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        OverflowClient.getInstance().getEventManager().callEvent(event);
        this.packet.set(event.getPacket());
        if(event.isCancelled()) ci.cancel();
    }

    @ModifyArg(method = "sendPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;doSendPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;Z)V"))
    private Packet<?> overflow$sendPacket$2(Packet<?> packet) {
        packet = this.packet.get();
        this.packet.remove();
        return packet;
    }
}