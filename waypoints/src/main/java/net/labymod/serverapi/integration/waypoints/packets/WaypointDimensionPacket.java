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

package net.labymod.serverapi.integration.waypoints.packets;

import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class WaypointDimensionPacket implements Packet {

  private static final Until DEFAULT_UNTIL = Until.VANILLA_DIMENSION_UPDATE;

  private String dimension;
  private Until until;

  /**
   * Update the current dimension of the player until the specified condition is met.
   *
   * @param dimension the dimension to use (if null, the current vanilla dimension is used)
   * @param until     the condition until the dimension is used
   */
  public WaypointDimensionPacket(@Nullable String dimension, @NotNull Until until) {
    Objects.requireNonNull(until, "Until cannot be null");
    this.dimension = dimension;
    this.until = until;
  }

  /**
   * Update the current dimension of the player until the dimension is updated by vanilla.
   *
   * @param dimension the dimension to use (if null, the current vanilla dimension is used)
   */
  public WaypointDimensionPacket(@Nullable String dimension) {
    this(dimension, DEFAULT_UNTIL);
  }

  @Override
  public void read(@NotNull PayloadReader reader) {
    this.dimension = reader.readOptionalString();
    this.until = Until.values()[reader.readVarInt()];
  }

  @Override
  public void write(@NotNull PayloadWriter writer) {
    writer.writeOptionalString(this.dimension);
    writer.writeVarInt(this.until.ordinal());
  }

  public @Nullable String getDimension() {
    return this.dimension;
  }

  public @NotNull Until until() {
    return this.until;
  }

  @Override
  public String toString() {
    return "WaypointDimensionPacket{" +
        "dimension='" + this.dimension + '\'' +
        ", until=" + this.until +
        '}';
  }

  public enum Until {
    /**
     * The supplied dimension is used until the player disconnects from the server.
     */
    DISCONNECT,
    /**
     * The supplied dimension is used until vanilla updates the dimension (f.e. by switching
     * worlds or sub servers)
     */
    VANILLA_DIMENSION_UPDATE,
  }
}
