/*
 * MIT License
 *
 * Copyright (c) 2025 LabyMedia GmbH
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
