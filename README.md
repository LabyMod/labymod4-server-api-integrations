# Official LabyMod 4 Server API Integrations

## Overview

This is the repository for official Integrations for the LabyMod 4 Server API. Integrations are modules that provide
additional functionality to the Server API and are most commonly used to provide the protocol of addons.

## Project Structure

Each Integration is a separate module:

- `betterperspective` - The Integration for the BetterPerspective addon. This Integration does not contain a Protocol
  but instead provides a Permission.
- `voicechat` - The Integration for the VoiceChat addon. This Integration provides the Protocol that VoiceChat uses to
  communicate with the server.

## Usage

An extensive guide on how to use the LabyMod 4 Server API can be found on
the [LabyMod Developer Portal](https://dev.labymod.net/pages/server/). Alternatively, feel free to ask for help on
our [Discord Server for Developers](https://labymod.net/dc/dev).

## Building

### Prerequisites

- Java Development Kit (JDK) 17 or higher

### Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/LabyMod/labymod4-server-api-integrations.git
   cd labymod4-server-api-integrations
   ```

2. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```

3. All compiled JAR files are in the `build/commonOutput` directory.

## License

This project is licensed under
the [MIT License](https://github.com/LabyMod/labymod4-server-api-integrations/blob/master/LICENSE). 