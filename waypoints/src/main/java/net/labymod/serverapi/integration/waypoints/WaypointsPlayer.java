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

package net.labymod.serverapi.integration.waypoints;

import net.labymod.serverapi.core.AddonProtocol;
import net.labymod.serverapi.core.integration.LabyModIntegrationPlayer;
import net.labymod.serverapi.integration.waypoints.model.ServerWaypoint;
import net.labymod.serverapi.integration.waypoints.packets.WaypointDimensionPacket;
import net.labymod.serverapi.integration.waypoints.packets.WaypointPacket;
import net.labymod.serverapi.integration.waypoints.packets.WaypointRemovePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class WaypointsPlayer implements LabyModIntegrationPlayer {

  private final AddonProtocol addonProtocol;
  private final UUID uniqueId;

  protected WaypointsPlayer(AddonProtocol addonProtocol, UUID uniqueId) {
    this.addonProtocol = addonProtocol;
    this.uniqueId = uniqueId;
  }

  /**
   * Update the current dimension of the player until the specified condition is met.
   *
   * @param dimension the dimension to use (if null, the current vanilla dimension is used)
   * @param until     the condition until the dimension is used
   */
  public void setDimension(
      @Nullable String dimension,
      @NotNull WaypointDimensionPacket.Until until
  ) {
    this.addonProtocol.sendPacket(this.uniqueId, new WaypointDimensionPacket(dimension, until));
  }

  /**
   * Sets the dimension of the player to the provided dimension until the dimension is updated by
   * vanilla.
   *
   * @param dimension the dimension to use (if null, the current vanilla dimension is used)
   */
  public void setDimension(@Nullable String dimension) {
    this.addonProtocol.sendPacket(this.uniqueId, new WaypointDimensionPacket(dimension));
  }

  /**
   * Resets the dimension of the player to the current dimension the player is on.
   */
  public void resetDimension() {
    this.addonProtocol.sendPacket(this.uniqueId, new WaypointDimensionPacket(null));
  }

  /**
   * Add or update a single waypoint for the player.
   *
   * @param waypoint The waypoint to add or update
   */
  public void sendWaypoint(ServerWaypoint waypoint) {
    this.sendWaypoints(waypoint);
  }

  /**
   * Add or update multiple waypoints for the player.
   *
   * @param waypoints The waypoints to add or update
   */
  public void sendWaypoints(ServerWaypoint... waypoints) {
    this.addonProtocol.sendPacket(this.uniqueId, new WaypointPacket(waypoints));
  }

  /**
   * Add or update multiple waypoints for the player.
   *
   * @param waypoints The waypoints to add or update
   */
  public void sendWaypoints(List<ServerWaypoint> waypoints) {
    this.addonProtocol.sendPacket(this.uniqueId, new WaypointPacket(waypoints));
  }

  /**
   * Remove the waypoint with the provided waypoint id.
   *
   * @param waypointId The id of the waypoint to remove
   */
  public void removeWaypoint(String waypointId) {
    this.addonProtocol.sendPacket(this.uniqueId, new WaypointRemovePacket(waypointId));
  }

  /**
   * Remove the provided waypoint.
   *
   * @param waypoint The waypoint to remove
   */
  public void removeWaypoint(ServerWaypoint waypoint) {
    this.addonProtocol.sendPacket(this.uniqueId, new WaypointRemovePacket(waypoint));
  }

  @Override
  public String toString() {
    return "WaypointsPlayer{" +
        "uniqueId=" + this.uniqueId +
        '}';
  }
}
