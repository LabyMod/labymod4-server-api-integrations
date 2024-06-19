package net.labymod.serverapi.integration.betterperspective;

import com.google.auto.service.AutoService;
import net.labymod.serverapi.core.AbstractLabyModProtocolService;
import net.labymod.serverapi.core.integration.LabyModProtocolIntegration;

@AutoService(LabyModProtocolIntegration.class)
public class BetterPerspectiveIntegration implements LabyModProtocolIntegration {

  @Override
  public void initialize(AbstractLabyModProtocolService abstractLabyModProtocolService) {
    System.out.println("Initializing ToggleSneakIntegration");
  }
}
