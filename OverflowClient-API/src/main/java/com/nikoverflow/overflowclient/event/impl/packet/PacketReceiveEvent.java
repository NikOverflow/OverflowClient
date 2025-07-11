package com.nikoverflow.overflowclient.event.impl.packet;

import com.nikoverflow.overflowclient.event.Cancellable;
import com.nikoverflow.overflowclient.event.Event;
import net.minecraft.network.protocol.Packet;

public class PacketReceiveEvent extends Event implements Cancellable {

    private boolean cancelled;
    private Packet<?> packet;

    public PacketReceiveEvent(Packet<?> packet) {
        cancelled = false;
        this.packet = packet;
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}