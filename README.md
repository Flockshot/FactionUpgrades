# FactionUpgrades

![Version](https://img.shields.io/badge/version-1.0-blue) ![Dependencies](https://img.shields.io/badge/dependencies-Factions%20%7C%20Vault%20%7C%20KOTH-red) ![License](https://img.shields.io/badge/license-MIT-green)

**FactionUpgrades** is a comprehensive RPG-style progression system for Factions servers. It allows Factions to collectively pool their money to purchase permanent buffs, abilities, and expansions for their members.

This plugin incentivizes team play and economy engagement by giving Factions meaningful long-term goals.

---

## 🚀 Features

The plugin includes a robust GUI menu where players can unlock various tiers of upgrades.

### 🛡️ Core Upgrades
* **Faction Flight:** Allows members to fly within their own territory.
* **Power Boost:** Permanently increases the max power of the faction.
* **Faction Warps:** Increases the maximum number of `/f warp` points available.
* **Faction Size:** Increases the maximum member limit.
* **TNT Bank:** Expands the faction's TNT storage capacity.

### ⚔️ Combat & Gameplay
* **Damage Modifiers:** Upgrade to deal more damage or take less damage in faction territory.
* **Potion Effects:** Permanent buffs like **Speed** and **Strength** for members.
* **Gapple Cooldown:** Reduces the cooldown for consuming Golden Apples.
* **KOTH Capture:** Speeds up the capture timer for King of the Hill events (hooks into KOTH plugin).
* **Mob XP:** Multiplies experience gained from killing mobs.

---

## 📦 Installation & Building

### Prerequisites
* **Java 8+**
* **Spigot/Paper Server** (1.8 - 1.20+)
* **Dependencies:**
    * [Factions](https://www.spigotmc.org/resources/factionsuuid.1035/) (or MassiveCore Factions)
    * [Vault](https://www.spigotmc.org/resources/vault.34315/) (for Economy)
    * [KOTH](https://www.spigotmc.org/resources/koth-king-of-the-hill.7689/) (Optional, for capture upgrades)

### Build Instructions
1.  Clone the repository:
2.  Build with Maven:
3.  Place the jar in `/plugins/`, alongside Factions and Vault.

---

## 🎮 Commands & Permissions

| Command | Permission | Description |
| :--- | :--- | :--- |
| `/facupgrade` | None | Opens the Upgrades GUI menu. |
| `/facupgrade info` | None | Displays plugin version and author information. |
| `/facupgrade reload` | `facupgrades.admin.reload` | Reloads the configuration file. |

*Note: The GUI automatically checks if the player belongs to a faction before opening.*

---

## ⚙️ Configuration

You can configure the cost, max levels, and specific values for every upgrade in `config.yml`.

```yaml
# Permission Command to execute (supports LuckPerms context)
PermissionCommand: lp user %player% permission set %perm%

Upgrades:
  faction-flight:
    enabled: true
    message: '&dyour faction has now level &7%upgrade% &dof flight upgrade!'
    levels:
      '1':
        cost: 10000
        value: 1 # Value often represents boolean (1=true) or magnitude
  
  faction-powerboost:
    enabled: true
    levels:
      '1':
        cost: 10000
        value: 10 # Adds 10 extra power capacity
      '2':
        cost: 20000
        value: 20
```

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
