# Nova Utility

Client-side Fabric starter for Minecraft Java 1.21.11. Includes a first-pass HUD and a small module registry. Built with Java 21, Fabric Loom remapping, and official Mojang mappings.

## Build

The GitHub Actions workflow builds the remapped JAR and verifies that `fabric.mod.json` and the compiled client entrypoint are present before uploading the artifact.

## Current scope

The HUD displays Nova Utility branding, FPS, and coordinates. Module registry entries are initial scaffolding; the ESP entries are placeholders and are not implemented yet.
