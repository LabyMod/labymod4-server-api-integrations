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

package net.labymod.serverapi.integration.waypoints.model;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class ServerWaypoint {

  private final String id;
  private final String name;
  private final double x;
  private final double y;
  private final double z;
  private final String dimension;
  private final ServerWaypointIconType iconType;
  private final String icon;
  private final Integer color;

  /**
   * Use the {@link Builder} to create a new instance of this class instead.
   */
  @ApiStatus.Internal
  public ServerWaypoint(
      @NotNull String id,
      @NotNull String name,
      double x,
      double y,
      double z,
      @Nullable String dimension,
      @NotNull ServerWaypointIconType iconType,
      @Nullable String icon,
      @Nullable Integer color
  ) {
    Objects.requireNonNull(id, "Id cannot be null");
    Objects.requireNonNull(name, "Name cannot be null");
    this.id = id;
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
    this.dimension = dimension;
    this.iconType = iconType;
    this.icon = icon;
    this.color = color;
  }

  public static @NotNull Builder builder() {
    return new Builder();
  }

  /**
   * @return the unique identifier of the waypoint, primarily used to be able to edit or remove the
   * waypoint.
   */
  public @NotNull String getId() {
    return this.id;
  }

  /**
   * @return the display name of the waypoint.
   */
  public @NotNull String getName() {
    return this.name;
  }

  /**
   * @return the x-coordinate of the waypoint.
   */
  public double getX() {
    return this.x;
  }

  /**
   * @return the y-coordinate of the waypoint.
   */
  public double getY() {
    return this.y;
  }

  /**
   * @return the z-coordinate of the waypoint.
   */
  public double getZ() {
    return this.z;
  }

  /**
   * @return the dimension of the waypoint, if {@code null} the waypoint is using the current
   * dimension of the player.
   */
  public String getDimension() {
    return this.dimension;
  }

  /**
   * @return the type of the icon. Defaults to {@link ServerWaypointIconType#BUILTIN}.
   */
  public @NotNull ServerWaypointIconType iconType() {
    return this.iconType;
  }

  /**
   * @return the icon of the waypoint. If the icon type is {@link ServerWaypointIconType#BUILTIN}
   * , the value is either a predefined icon (or {@code null} for the default icon). If the icon
   * type is {@link ServerWaypointIconType#URL}, the value is the URL of the icon.
   */
  public @Nullable String getIcon() {
    return this.icon;
  }

  /**
   * @return the color of the waypoint. The color is represented as an RGB integer.
   */
  public @Nullable Integer getColor() {
    return this.color;
  }

  /**
   * @return {@code true} if the waypoint is valid, otherwise {@code false}.
   */
  public final boolean isValid() {
    return this.id != null && this.name != null;
  }

  public @NotNull Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof ServerWaypoint)) {
      return false;
    }
    ServerWaypoint that = (ServerWaypoint) object;
    return Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.id);
  }

  @Override
  public String toString() {
    return "ServerWaypoint{" +
        "id='" + this.id + '\'' +
        ", name='" + this.name + '\'' +
        ", x=" + this.x +
        ", y=" + this.y +
        ", z=" + this.z +
        ", dimension='" + this.dimension + '\'' +
        ", iconType=" + this.iconType +
        ", icon='" + this.icon + '\'' +
        ", color=" + this.color +
        '}';
  }

  public enum ServerWaypointIconType {
    BUILTIN,
    URL,
  }

  public static class Builder {

    private final String id;
    private String name = null;
    private double x = 0.0;
    private double y = 0.0;
    private double z = 0.0;
    private String dimension = null;
    private ServerWaypointIconType iconType = ServerWaypointIconType.BUILTIN;
    private String icon = null;
    private Integer color;

    Builder() {
      this.id = String.valueOf(UUID.randomUUID());
    }

    Builder(@NotNull ServerWaypoint waypoint) {
      Objects.requireNonNull(waypoint, "Waypoint cannot be null");
      if (!waypoint.isValid()) {
        throw new IllegalArgumentException("Waypoint to edit must be valid");
      }

      this.id = waypoint.id;
      this.name = waypoint.name;
      this.x = waypoint.x;
      this.y = waypoint.y;
      this.z = waypoint.z;
      this.dimension = waypoint.dimension;
      this.iconType = waypoint.iconType;
      this.icon = waypoint.icon;
      this.color = waypoint.color;
    }

    /**
     * Sets the name of the waypoint.
     *
     * @param name the name of the waypoint
     * @return the builder instance
     * @throws NullPointerException if the provided name is {@code null}
     */
    public @NotNull Builder name(@NotNull String name) {
      Objects.requireNonNull(name, "Name cannot be null");
      this.name = name;
      return this;
    }

    /**
     * Sets the x-coordinate of the waypoint.
     *
     * @param x the x-coordinate of the waypoint
     * @return the builder instance
     */
    public @NotNull Builder x(double x) {
      this.x = x;
      return this;
    }

    /**
     * Sets the y-coordinate of the waypoint.
     *
     * @param y the y-coordinate of the waypoint
     * @return the builder instance
     */
    public @NotNull Builder y(double y) {
      this.y = y;
      return this;
    }

    /**
     * Sets the z-coordinate of the waypoint.
     *
     * @param z the z-coordinate of the waypoint
     * @return the builder instance
     */
    public @NotNull Builder z(double z) {
      this.z = z;
      return this;
    }

    /**
     * Sets the dimension of the waypoint. If the dimension is {@code null}, the client will use
     * the current dimension of the player.
     *
     * @param dimension the dimension of the waypoint
     * @return the builder instance
     */
    public @NotNull Builder dimension(@Nullable String dimension) {
      this.dimension = dimension;
      return this;
    }

    /**
     * Sets the icon of the waypoint to a custom URL.
     *
     * @param iconUrl the URL of the icon
     * @return the builder instance
     * @throws NullPointerException if the provided icon is {@code null}
     */
    public @NotNull Builder customIcon(@NotNull String iconUrl) {
      Objects.requireNonNull(iconUrl, "Icon url cannot be null");
      this.iconType = ServerWaypointIconType.URL;
      this.icon = iconUrl;
      return this;
    }

    /**
     * Sets the icon of the waypoint to a predefined icon.
     *
     * @param icon the predefined icon
     * @return the builder instance
     * @throws NullPointerException if the provided icon is {@code null}
     */
    public @NotNull Builder builtinIcon(@NotNull String icon) {
      Objects.requireNonNull(icon, "Icon cannot be null");
      this.iconType = ServerWaypointIconType.BUILTIN;
      this.icon = icon;
      return this;
    }

    /**
     * Sets the icon of the waypoint to the default icon.
     *
     * @return the builder instance
     */
    public @NotNull Builder defaultIcon() {
      this.iconType = ServerWaypointIconType.BUILTIN;
      this.icon = null;
      return this;
    }

    /**
     * Sets the color of the waypoint. Defaults to white.
     *
     * @param color the color of the waypoint
     * @return the builder instance
     */
    public @NotNull Builder color(int color) {
      this.color = color;
      return this;
    }

    /**
     * Resets the color of the waypoint to white.
     *
     * @return the builder instance
     */
    public @NotNull Builder resetColor() {
      this.color = null;
      return this;
    }

    public @NotNull ServerWaypoint build() {
      Objects.requireNonNull(this.id, "Id cannot be null");
      Objects.requireNonNull(this.name, "Name cannot be null");
      if (this.iconType == null) {
        this.iconType = ServerWaypointIconType.BUILTIN;
      }

      return new ServerWaypoint(
          this.id,
          this.name,
          this.x,
          this.y,
          this.z,
          this.dimension,
          this.iconType,
          this.icon,
          this.color
      );
    }
  }
}
