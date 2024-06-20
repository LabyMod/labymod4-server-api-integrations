package net.labymod.serverapi.integration.voicechat.packets;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;

public class VoiceChatOpenChannelsPacket implements Packet {

  /**
   * Creates a new open channels packet. Users receiving this packet will open the voice channels
   * gui.
   */
  public VoiceChatOpenChannelsPacket() {
    // NO-OP
  }

  @Override
  public void read(@NotNull PayloadReader reader) {
    // NO-OP
  }

  @Override
  public void write(@NotNull PayloadWriter writer) {
    // NO-OP
  }
}
