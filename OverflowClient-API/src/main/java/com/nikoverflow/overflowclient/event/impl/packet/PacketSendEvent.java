package com.nikoverflow.overflowclient.event.impl.packet;

import net.minecraft.network.protocol.Packet;

public class PacketSendEvent extends PacketEvent {

    public PacketSendEvent(Packet<?> packet) {
        super(packet);
    }
}