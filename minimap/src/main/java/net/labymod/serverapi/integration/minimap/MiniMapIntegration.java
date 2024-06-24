package net.labymod.serverapi.integration.minimap;

import com.google.auto.service.AutoService;
import net.labymod.serverapi.core.AbstractLabyModPlayer;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import net.labymod.serverapi.core.AddonProtocol;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;
import net.labymod.serverapi.core.integration.LabyModProtocolIntegration;
import org.jetbrains.annotations.NotNull;

@AutoService(LabyModProtocolIntegration.class)
public class MiniMapIntegration implements LabyModProtocolIntegration {

  private AddonProtocol addonProtocol;
  private AbstractLabyModProtocolService protocolService;

  @Override
  public void initialize(AbstractLabyModProtocolService protocolService) {
    if (this.protocolService != null) {
      throw new IllegalStateException("MiniMapIntegration is already initialized");
    }

    this.protocolService = protocolService;

    this.addonProtocol = new AddonProtocol(protocolService, "minimap");
    // todo add packets

    this.protocolService.registry().registerProtocol(this.addonProtocol);
  }

  @Override
  public LabyModIntegrationPlayer createIntegrationPlayer(AbstractLabyModPlayer<?> labyModPlayer) {
    return new MiniMapPlayer(this.addonProtocol, labyModPlayer.getUniqueId());
  }

  /**
   * @return the minimap protocol
   */
  public @NotNull AddonProtocol minimapProtocol() {
    if (this.addonProtocol == null) {
      throw new IllegalStateException("MiniMapIntegration is not initialized");
    }

    return this.addonProtocol;
  }
}
