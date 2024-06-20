package net.labymod.serverapi.integration.betterperspective;

import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;
import net.labymod.serverapi.core.packet.clientbound.game.moderation.PermissionPacket;

import java.util.UUID;

public class BetterPerspectivePlayer implements LabyModIntegrationPlayer {

  private final AbstractLabyModProtocolService protocolService;
  private final UUID uniqueId;

  protected BetterPerspectivePlayer(AbstractLabyModProtocolService protocolService, UUID uniqueId) {
    this.protocolService = protocolService;
    this.uniqueId = uniqueId;
  }

  public void allowUnlockCamera() {
    this.protocolService.labyModProtocol().sendPacket(
        this.uniqueId,
        new PermissionPacket(BetterPerspectiveIntegration.UNLOCK_CAMERA_PERMISSION.allow())
    );
  }

  public void denyUnlockCamera() {
    this.protocolService.labyModProtocol().sendPacket(
        this.uniqueId,
        new PermissionPacket(BetterPerspectiveIntegration.UNLOCK_CAMERA_PERMISSION.deny())
    );
  }

  @Override
  public String toString() {
    return "BetterPerspectivePlayer{" +
        "uniqueId=" + this.uniqueId +
        '}';
  }
}
