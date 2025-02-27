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

import com.google.auto.service.AutoService;
import net.labymod.serverapi.api.packet.Direction;
import net.labymod.serverapi.core.AbstractLabyModPlayer;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import net.labymod.serverapi.core.AddonProtocol;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;
import net.labymod.serverapi.core.integration.LabyModProtocolIntegration;
import net.labymod.serverapi.integration.voicechat.model.VoiceChatMute;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatMutePacket;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatOpenChannelsPacket;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatUnmutePacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@AutoService(LabyModProtocolIntegration.class)
public class VoiceChatIntegration implements LabyModProtocolIntegration {

  private AddonProtocol addonProtocol;
  private AbstractLabyModProtocolService protocolService;

  @Override
  public void initialize(AbstractLabyModProtocolService protocolService) {
    if (this.protocolService != null) {
      throw new IllegalStateException("VoiceChatIntegration is already initialized");
    }

    this.protocolService = protocolService;

    this.addonProtocol = new AddonProtocol(protocolService, "voicechat");
    this.addonProtocol.registerPacket(0, VoiceChatMutePacket.class, Direction.CLIENTBOUND);
    this.addonProtocol.registerPacket(1, VoiceChatUnmutePacket.class, Direction.CLIENTBOUND);
    this.addonProtocol.registerPacket(2, VoiceChatOpenChannelsPacket.class, Direction.CLIENTBOUND);

    protocolService.registry().registerProtocol(this.addonProtocol);
  }

  @Override
  public LabyModIntegrationPlayer createIntegrationPlayer(AbstractLabyModPlayer<?> labyModPlayer) {
    List<VoiceChatMute> mutes = new ArrayList<>();
    for (AbstractLabyModPlayer<?> player : this.protocolService.getPlayers()) {
      VoiceChatPlayer integrationPlayer = player.getIntegrationPlayer(VoiceChatPlayer.class);
      if (integrationPlayer != null && integrationPlayer.isMuted()) {
        mutes.add(integrationPlayer.getMute());
      }
    }

    if (!mutes.isEmpty()) {
      labyModPlayer.sendPacket(new VoiceChatMutePacket(mutes));
    }

    return new VoiceChatPlayer(
        this.protocolService,
        this.addonProtocol,
        labyModPlayer.getUniqueId()
    );
  }

  /**
   * @return the voice chat protocol
   */
  public @NotNull AddonProtocol voiceChatProtocol() {
    if (this.addonProtocol == null) {
      throw new IllegalStateException("VoiceChatIntegration is not initialized");
    }

    return this.addonProtocol;
  }
}
