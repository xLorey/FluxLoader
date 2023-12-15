<div align="center">
    <h1>Flux Loader</h1>
    <img src="docs/images/FluxLoader.png" height="400px">
</div>

<p align="center">
    <img alt="PZ Version" src="https://img.shields.io/badge/Project_Zomboid-v41.78.16-blue">
    <img alt="Java version" src="https://img.shields.io/badge/Java-17-orange">
    <a href="https://discord.gg/BwSuTdEGJ4" style="text-decoration: none;">
         <img alt="Discord" src="https://img.shields.io/discord/1174285070761197599.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2">
    </a>
    <a href="[https://discord.gg/BwSuTdEGJ](https://github.com/xLorey/FluxLoader-PZ/actions/workflows/gradle.yml)4" style="text-decoration: none;">
         <img alt="Java CI with Gradle" src="https://github.com/xLorey/FluxLoader-PZ/actions/workflows/gradle.yml/badge.svg">
    </a>
    <img alt="GitHub License" src="https://img.shields.io/github/license/xLorey/FluxLoader-PZ">
    <img alt="GitHub issues" src="https://img.shields.io/github/issues-raw/xlorey/FluxLoader-PZ">
    <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/xlorey/FluxLoader-PZ">
    <img alt="GitHub release (with filter)" src="https://img.shields.io/github/v/release/xlorey/FluxLoader-PZ">
</p>

**Flux Loader** is a universal toolchain for creating plugins (mods) for the [Project Zomboid](https://store.steampowered.com/app/108600/Project_Zomboid/) game, oriented for both the client and the server. 
This project is inspired by tools such as [Storm](https://github.com/pzstorm/storm), [PaperMC](https://github.com/PaperMC/Paper), [FabricMC](https://github.com/FabricMC/fabric), etc.

# Introduction
The problem of writing mods for Project Zomboid is largely due to the lack of detailed documentation and the limitations of the official API designed for modding. The main way to create mods through Lua provides only limited features, which poses certain difficulties for mod developers if they want to implement more complex and deep functionality. This means that developers have to use workarounds to implement more complex ideas and concepts, which can lead to increased development complexity and potential compatibility and stability problems. 

In addition, the lack of official support for such methods may lead to the risk that mods may stop working with future updates of the game. All this affects mod developers, limiting their creativity and increasing the risks during development.

That is why, inspired by popular mod loaders in other games (for example, Minecraft), where you can easily expand the functionality of the game, it was decided to create a tool that solves these problems.

# Features
Our tools already have:
- The ability to easily subscribe to all game events, as well as to our custom ones (including the creation of new events);
- The ability to easily add plugins by moving the Jar file to the plugins folder;
- Custom documented tools for working with game methods.

# Installation
To install this project, you need to do the following:
1) Download the latest version from the [releases page](https://github.com/xLorey/FluxLoader-PZ/releases)
2) Transfer the downloaded Jar archive to the folder with the game (or server).
3) Open the terminal (Windows command prompt) in the folder where the downloaded Jar file is located. Or enter the following command
```bash
cd /D "{path}"
```
``cd`` - command to change the terminal context

``/D`` - the flag is necessary to change the directory (disk), you do not need to change it

``{path}`` - is your path to the jar file folder

> [!TIP]
> For example, "C:\Program Files (x86)\Steam\steamapps\common\ProjectZomboid"

5) Enter the installation command in the context of the folder with the downloaded jar file:
```bash
java -jar ./FluxLoader-x-x-x.jar --install
```
``java`` - JDK CLI call

``-jar`` - flag for working with jar

``./FluxLoader-x-x-x.jar`` - archive file name, where x is the file version

``--install`` - flag for installing the tool.

> [!NOTE]
> To uninstall, use '--uninstall' instead of '--install'

# How to use
After the successful installation of Flux Loader, you can write and/or download ready-made plugins and transfer them to the `plugins` folder, it is located in the game folder, in the same place as the main folder of the game `zombie`

# For developer
If you are a developer or just want to get acquainted with the documentation about available methods, events, etc., you can look in our [Wiki](docs/Wiki.md). As the project develops, it will be expanded and corrected.

# Disclaimer
> [!WARNING]
> We do not approve of the creation of cheat modifications and will try to stop attempts to create them with this project!
> If you are for this reason, please stop using this project.

This software is provided "as is", without any express or implied warranties. The team is not responsible for mods created by developers using the Flux Loader API.

# Contribute
We welcome any help in the development of this project! How can you help:
- **Test** - install the loader and just play. Mods are not necessary to install, so you will help find hidden bugs. If something turns up, please report it to Issue so that other developers know what needs to be fixed.
- **Create mods** - is a good way to develop our community and bring new functionality to the game.
- **Take part in the development** - you can always create a fork and make corrections by sending them for acceptance (pull request).
- **Join our community at discord** - we want to create a healthy community of enthusiastic developers where everyone can learn something and find new friends.

# License 
This project is licensed under [GNU General Public License v3.0](https://github.com/xLorey/FluxLoader-PZ/blob/master/LICENSE).
