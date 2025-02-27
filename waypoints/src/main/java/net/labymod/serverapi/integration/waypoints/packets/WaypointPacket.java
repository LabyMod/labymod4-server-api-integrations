/*
 * MIT License
 *
 * Copyright (c) 2024 LabyMedia GmbH
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

package net.labymod.serverapi.integration.waypoints.packets;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import net.labymod.serverapi.integration.waypoints.model.ServerWaypoint;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WaypointPacket implements Packet {

  private List<ServerWaypoint> waypoints;

  public WaypointPacket(@NotNull List<ServerWaypoint> waypoints) {
    Objects.requireNonNull(waypoints, "Waypoints cannot be null");
    this.waypoints = waypoints;
  }

  public WaypointPacket(@NotNull ServerWaypoint... waypoints) {
    Objects.requireNonNull(waypoints, "Waypoints cannot be null");
    this.waypoints = Collections.unmodifiableList(Arrays.asList(waypoints));
  }

  @Override
  public void read(@NotNull PayloadReader reader) {
    this.waypoints = reader.readList(() -> new ServerWaypoint(
        reader.readString(),
        reader.readString(),
        reader.readDouble(),
        reader.readDouble(),
        reader.readDouble(),
        reader.readOptionalString(),
        ServerWaypoint.ServerWaypointIconType.values()[reader.readVarInt()],
        reader.readOptionalString(),
        reader.readBoolean() ? reader.readVarInt() : null
    ));
  }

  @Override
  public void write(@NotNull PayloadWriter writer) {
    writer.writeCollection(this.waypoints, waypoint -> {
      writer.writeString(waypoint.getId());
      writer.writeString(waypoint.getName());
      writer.writeDouble(waypoint.getX());
      writer.writeDouble(waypoint.getY());
      writer.writeDouble(waypoint.getZ());
      writer.writeOptionalString(waypoint.getDimension());
      writer.writeVarInt(waypoint.iconType().ordinal());
      writer.writeOptionalString(waypoint.getIcon());
      if (waypoint.getColor() != null) {
        writer.writeBoolean(true);
        writer.writeVarInt(waypoint.getColor());
      } else {
        writer.writeBoolean(false);
      }
    });
  }

  public List<ServerWaypoint> getWaypoints() {
    return this.waypoints;
  }

  @Override
  public String toString() {
    return "WaypointPacket{" +
        "waypoints=" + this.waypoints +
        '}';
  }
}
