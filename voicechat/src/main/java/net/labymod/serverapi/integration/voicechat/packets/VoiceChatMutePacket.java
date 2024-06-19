package net.labymod.serverapi.integration.voicechat.packets;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.serverapi.integration.voicechat.model.VoiceChatMute;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VoiceChatMutePacket implements Packet {

  private List<VoiceChatMute> mutes;

  public VoiceChatMutePacket(@NotNull List<VoiceChatMute> mutes) {
    Objects.requireNonNull(mutes, "Mutes cannot be null");
    this.mutes = mutes;
  }

  public VoiceChatMutePacket(@NotNull VoiceChatMute... mutes) {
    Objects.requireNonNull(mutes, "Mutes cannot be null");
    this.mutes = Collections.unmodifiableList(Arrays.asList(mutes));
  }

  @Override
  public void read(@NotNull PayloadReader reader) {
    this.mutes = reader.readList(() -> VoiceChatMute.create(
        reader.readUUID(),
        reader.readOptionalString(),
        reader.readBoolean() ? reader.readLong() : 0
    ));
  }

  @Override
  public void write(@NotNull PayloadWriter writer) {
    writer.writeCollection(this.mutes, mute -> {
      writer.writeUUID(mute.getUniqueId());
      writer.writeOptionalString(mute.getReason());

      boolean hasEnd = !mute.isPermanent();
      writer.writeBoolean(hasEnd);
      if (hasEnd) {
        writer.writeLong(mute.getEnd());
      }
    });
  }

  public @NotNull List<VoiceChatMute> getMutes() {
    return this.mutes;
  }

  @Override
  public String toString() {
    return "VoiceChatMutePacket{" +
        "mutes=" + this.mutes +
        '}';
  }
}
