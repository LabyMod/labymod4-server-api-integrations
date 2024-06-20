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

  public void mute(@NotNull VoiceChatMute mute) {
    Objects.requireNonNull(mute, "Mute can not be null. Use VoiceChatPlayer#unmute to unmute");
    this.mute = mute;
    for (AbstractLabyModPlayer<?> player : this.protocolService.getPlayers()) {
      player.sendPacket(new VoiceChatMutePacket(mute));
    }
  }

  public void unmute() {
    this.mute = null;
    for (AbstractLabyModPlayer<?> player : this.protocolService.getPlayers()) {
      player.sendPacket(new VoiceChatUnmutePacket(this.uniqueId));
    }
  }

  public void openVoiceChatChannels() {
    this.addonProtocol.sendPacket(this.uniqueId, new VoiceChatOpenChannelsPacket());
  }

  public @Nullable VoiceChatMute getMute() {
    return this.mute;
  }

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
