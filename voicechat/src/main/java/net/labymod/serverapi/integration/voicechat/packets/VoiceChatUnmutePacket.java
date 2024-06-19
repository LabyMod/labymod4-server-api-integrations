package net.labymod.serverapi.integration.voicechat.packets;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class VoiceChatUnmutePacket implements Packet {

  private UUID uniqueId;

  /**
   * Creates a new unmute packet
   *
   * @param uniqueId the unique id of the user to unmute
   */
  public VoiceChatUnmutePacket(@NotNull UUID uniqueId) {
    Objects.requireNonNull(uniqueId, "uniqueId cannot be null");
    this.uniqueId = uniqueId;
  }

  @Override
  public void read(@NotNull PayloadReader reader) {
    this.uniqueId = reader.readUUID();
  }

  @Override
  public void write(@NotNull PayloadWriter writer) {
    writer.writeUUID(this.uniqueId);
  }

  public @NotNull UUID getUniqueId() {
    return this.uniqueId;
  }

  @Override
  public String toString() {
    return "VoiceChatUnmutePacket{" +
        "uniqueId=" + this.uniqueId +
        '}';
  }
}
