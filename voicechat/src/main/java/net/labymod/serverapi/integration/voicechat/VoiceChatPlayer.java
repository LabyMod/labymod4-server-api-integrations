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

package net.labymod.serverapi.integration.voicechat;

import net.labymod.serverapi.core.AbstractLabyModPlayer;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import net.labymod.serverapi.core.AddonProtocol;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;
import net.labymod.serverapi.integration.voicechat.model.VoiceChatMute;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatMutePacket;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatOpenChannelsPacket;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatUnmutePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class VoiceChatPlayer implements LabyModIntegrationPlayer {

  private final AbstractLabyModProtocolService protocolService;
  private final AddonProtocol addonProtocol;
  private final UUID uniqueId;
  private VoiceChatMute mute;

  protected VoiceChatPlayer(
      AbstractLabyModProtocolService protocolService,
      AddonProtocol addonProtocol,
      UUID uniqueId
  ) {
    this.protocolService = protocolService;
    this.addonProtocol = addonProtocol;
    this.uniqueId = uniqueId;
  }

  /**
   * Mutes the current player with the provided mute, also sends to mute packet to all other
   * LabyMod players on the server. As long as not unmuted via {@link #unmute} this mute will also
   * be sent to new LabyMod players joining the server
   *
   * @param mute the mute to apply
   */
  public void mute(@NotNull VoiceChatMute mute) {
    Objects.requireNonNull(mute, "Mute can not be null. Use VoiceChatPlayer#unmute to unmute");
    this.mute = mute;
    for (AbstractLabyModPlayer<?> player : this.protocolService.getPlayers()) {
      player.sendPacket(new VoiceChatMutePacket(mute));
    }
  }

  /**
   * Unmutes the current player, also sends the unmute packet to all other LabyMod players on the
   * server
   */
  public void unmute() {
    this.mute = null;
    for (AbstractLabyModPlayer<?> player : this.protocolService.getPlayers()) {
      player.sendPacket(new VoiceChatUnmutePacket(this.uniqueId));
    }
  }

  /**
   * Opens the voice chat channels screen for the current player
   */
  public void openVoiceChatChannels() {
    this.addonProtocol.sendPacket(this.uniqueId, new VoiceChatOpenChannelsPacket());
  }

  /**
   * @return the mute of the player, null if the player is not muted (server-side)
   */
  public @Nullable VoiceChatMute getMute() {
    return this.mute;
  }

  /**
   * @return whether the player is muted or not
   */
  public boolean isMuted() {
    return this.mute != null;
  }

  @Override
  public String toString() {
    return "VoiceChatPlayer{" +
        "mute=" + this.mute +
        ", uniqueId=" + this.uniqueId +
        '}';
  }
}
