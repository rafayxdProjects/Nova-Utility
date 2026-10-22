# Nova Utility

Starter source project for **Minecraft Java 1.21.11 + Fabric**, intended to build in GitHub Actions and be tested with Mojo Launcher.

## What is included

- Minimal client-only Fabric mod entrypoint.
- Gradle/Loom project configuration targeting Java 21.
- GitHub Actions workflow that builds the JAR and uploads it as an artifact.
- Initial structure for adding the Nova Utility module system.

**This ZIP is a starter scaffold, not the finished client.** Auto Crystal, NoFall, Elytra Fly, ESP modules, Auto Clicker, Baritone integration, ClickGUI, and AI are not implemented yet.

## GitHub Actions build

1. Upload these files to the root of your `rafayxdProjects/Nova-Utility` repository.
2. Commit the files to `main` (or `master`).
3. Open the repository's **Actions** tab.
4. Select **Nova Utility Build** and choose **Run workflow**, or let it run after a push.
5. Open the completed run and download the `Nova-Utility-JAR` artifact.
6. Test the JAR in a separate Minecraft instance before using it in your main profile.

## Version checks

The build configuration targets Minecraft 1.21.11, Java 21, Fabric Loader, Fabric API, and Fabric Loom. Dependency versions can change; if GitHub Actions reports that a dependency version cannot be resolved, check the current compatible versions at https://fabricmc.net/develop/ and update `gradle.properties`.

## Safety and multiplayer

No client can guarantee bypassing every anticheat. Develop and test modules in single-player or servers you control, and follow each multiplayer server's rules.

## Project layout

```text
Nova-Utility/
├── .github/workflows/build.yml
├── src/main/java/dev/nova/client/NovaUtilityClient.java
├── src/main/resources/fabric.mod.json
├── build.gradle
├── gradle.properties
└── settings.gradle
```
