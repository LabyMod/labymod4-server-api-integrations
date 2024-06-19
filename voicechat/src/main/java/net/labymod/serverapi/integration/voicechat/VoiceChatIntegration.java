package net.labymod.serverapi.integration.voicechat;

import com.google.auto.service.AutoService;
import net.labymod.serverapi.api.packet.Direction;
import net.labymod.serverapi.core.AbstractLabyModPlayer;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import net.labymod.serverapi.core.AddonProtocol;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;
import net.labymod.serverapi.core.integration.LabyModProtocolIntegration;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatMutePacket;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatOpenChannelsPacket;
import net.labymod.serverapi.integration.voicechat.packets.VoiceChatUnmutePacket;

@AutoService(LabyModProtocolIntegration.class)
public class VoiceChatIntegration implements LabyModProtocolIntegration {

  private AbstractLabyModProtocolService protocolService;
  private AddonProtocol addonProtocol;

  @Override
  public void initialize(AbstractLabyModProtocolService protocolService) {
    this.protocolService = protocolService;
    System.out.println("Initializing VoiceChatIntegration");

    this.addonProtocol = new AddonProtocol(protocolService, "voicechat");
    this.addonProtocol.registerPacket(0, VoiceChatMutePacket.class, Direction.CLIENTBOUND);
    this.addonProtocol.registerPacket(1, VoiceChatUnmutePacket.class, Direction.CLIENTBOUND);
    this.addonProtocol.registerPacket(2, VoiceChatOpenChannelsPacket.class, Direction.CLIENTBOUND);
  }

  @Override
  public LabyModIntegrationPlayer createIntegrationPlayer(AbstractLabyModPlayer<?> labyModPlayer) {
    return new VoiceChatPlayer(
        this.protocolService,
        this.addonProtocol,
        labyModPlayer.getUniqueId()
    );
  }
}
