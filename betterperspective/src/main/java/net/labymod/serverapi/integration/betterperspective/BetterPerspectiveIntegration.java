package net.labymod.serverapi.integration.betterperspective;

import com.google.auto.service.AutoService;
import net.labymod.serverapi.core.AbstractLabyModPlayer;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;
import net.labymod.serverapi.core.integration.LabyModProtocolIntegration;
import net.labymod.serverapi.core.model.moderation.Permission;

@AutoService(LabyModProtocolIntegration.class)
public class BetterPerspectiveIntegration implements LabyModProtocolIntegration {

  public static final Permission UNLOCK_CAMERA_PERMISSION = Permission.of(
      "better_perspective_unlock_camera"
  );

  private AbstractLabyModProtocolService protocolService;

  @Override
  public void initialize(AbstractLabyModProtocolService protocolService) {
    this.protocolService = protocolService;
  }

  @Override
  public LabyModIntegrationPlayer createIntegrationPlayer(AbstractLabyModPlayer<?> labyModPlayer) {
    return new BetterPerspectivePlayer(this.protocolService, labyModPlayer.getUniqueId());
  }
}
