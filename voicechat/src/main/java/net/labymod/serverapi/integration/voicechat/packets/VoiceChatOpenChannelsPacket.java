package net.labymod.serverapi.integration.voicechat.packets;

import net.labymod.serverapi.api.packet.Packet;

public class VoiceChatOpenChannelsPacket implements Packet {

  /**
   * Creates a new open channels packet. Users receiving this packet will open the voice channels
   * gui.
   */
  public VoiceChatOpenChannelsPacket() {
    // NO-OP
  }
}
