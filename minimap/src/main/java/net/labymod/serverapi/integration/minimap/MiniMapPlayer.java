package net.labymod.serverapi.integration.minimap;

import net.labymod.serverapi.core.AddonProtocol;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;

import java.util.UUID;

public class MiniMapPlayer implements LabyModIntegrationPlayer {

  private final AddonProtocol addonProtocol;
  private final UUID uniqueId;

  protected MiniMapPlayer(AddonProtocol addonProtocol, UUID uniqueId) {
    this.addonProtocol = addonProtocol;
    this.uniqueId = uniqueId;
  }
}
