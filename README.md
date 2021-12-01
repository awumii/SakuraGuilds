# 🌸 SakuraGuilds 🌸
WIP guild plugin for Paper 1.16+ with GUI management.  
Requirements for using on your server:
  * Java: 16
  * Paper: 1.16+

## ⚠️ May not be ready for use in production environment
This plugin has been used for more than 5 months on an active server, but it has undergone a lot of changes since then. Breaking changes can be made at any point.

## 📝 Features
* Full GUI management. Use commands or GUI for managing your guild, whatever you prefer.
* Advanced permission system with premade ranks.
* Classic war system, with a "nexus" block in the center of a guild
* Nexus shield, temporarily protecting guilds from attackers.
* Chunk claiming system.
* Ranking system with divisions.
* Chat channels.
* MySQL/MariaDB Support
* Additional features after installing some popular plugins:
  * HolographicDisplays: display holograms with informations about guilds or claims.
  * WorldEdit/FastAsyncWorldEdit: paste schematic at the guild's nexus.
  * AureliumSkills: require a certain power level for player's to be able to create guilds.
  * PlaceholderAPI: use SakuraGuild's variables in other plugins.
  * Vault: donate to your guild to upgrade it.

🖊️ TODO:
* Guild wars
* Guild buildings
* API

## 🖼️ Screenshots
Todo

## 📎 Placeholders
* %sakuraguilds_gtop_POSITION% - name of the guild at the specified ranking position (+ configured suffix).
* %sakuraguilds_ptop_POSITION% - name of the player at the specified ranking position (+ configured suffix).
* %sakuraguilds_kills% - player's kills.
* %sakuraguilds_deaths% - player's deaths.
* %sakuraguilds_channel% - player's chat channel's name.
* %sakuraguilds_trophies% - player's trophy amount.
* %sakuraguilds_guild_name% - player's guild name.
* %sakuraguilds_guild_rank% - player's guild rank.
* %sakuraguilds_guild_rank_icon% - player's guild rank icon.
* %sakuraguilds_guild_trophies% - player's guild trophy amount.
* %sakuraguilds_guild_division% - player's guild division title.
* %sakuraguilds_guild_position% - player's guild global ranking position.

## 🕵️ Privacy Insight
  * This plugin does not require an internet connection, and it doesen't use any network features.
    * The server may communicate with the Mojang servers to acquire skins for GUI skulls.
  * Only the user UUIDs are stored in the database.
