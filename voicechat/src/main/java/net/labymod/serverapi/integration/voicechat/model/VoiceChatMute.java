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

package net.labymod.serverapi.integration.voicechat.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class VoiceChatMute {

  private final UUID uniqueId;
  private final String reason;
  private final long end;

  private VoiceChatMute(@NotNull UUID uniqueId, @Nullable String reason, long end) {
    Objects.requireNonNull(uniqueId, "uniqueId cannot be null");
    if (end < 0) {
      throw new IllegalArgumentException("end cannot be negative");
    }

    this.uniqueId = uniqueId;
    this.reason = reason;
    this.end = end;
  }

  /**
   * Creates a temporary mute
   *
   * @param uniqueId the unique id of the user to mute
   * @param reason   the reason of the mute
   * @param end      the timestamp until the user should be muted
   * @return a new voice chat mute
   */
  public static @NotNull VoiceChatMute create(
      @NotNull UUID uniqueId,
      @Nullable String reason,
      long end
  ) {
    return new VoiceChatMute(uniqueId, reason, end);
  }

  /**
   * Creates a permanent mute
   *
   * @param uniqueId the unique id of the user to mute
   * @param reason   the reason of the mute
   * @return a new voice chat mute
   */
  public static @NotNull VoiceChatMute create(
      @NotNull UUID uniqueId,
      @Nullable String reason
  ) {
    return create(uniqueId, reason, 0);
  }

  public @NotNull UUID getUniqueId() {
    return this.uniqueId;
  }

  public @Nullable String getReason() {
    return this.reason;
  }

  public long getEnd() {
    return this.end;
  }

  public boolean isPermanent() {
    return this.end == 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof VoiceChatMute that)) {
      return false;
    }

    return this.end == that.end && Objects.equals(this.uniqueId, that.uniqueId)
        && Objects.equals(this.reason, that.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uniqueId, this.reason, this.end);
  }

  @Override
  public String toString() {
    return "VoiceChatMute{" +
        "uniqueId=" + this.uniqueId +
        ", reason='" + this.reason + '\'' +
        ", end=" + this.end +
        '}';
  }
}
