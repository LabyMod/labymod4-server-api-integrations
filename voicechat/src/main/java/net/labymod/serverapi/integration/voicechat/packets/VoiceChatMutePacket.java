/*
 * MIT License
 *
 * Copyright (c) 2024 LabyMedia GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
