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

  public @NotNull AddonProtocol voiceChatProtocol() {
    if (this.addonProtocol == null) {
      throw new IllegalStateException("VoiceChatIntegration is not initialized");
    }

    return this.addonProtocol;
  }
}
