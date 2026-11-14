# Saphyra Reborn

**An unofficial 1.21.1 NeoForge port of [The Shattered Goddess (Mokel's Bossfight) - Saphyra](https://www.curseforge.com/minecraft/mc-mods/the-shattered-goddess-mokels-bossfight-saphyra) by Mokel.**
This port is not made, endorsed or supported by Mokel. Please do not report issues of this port to the original author.

Saphyra Reborn brings Mokel's story-driven boss adventure to Minecraft 1.21.1 NeoForge: four story structures, NPCs, keys and fog locks, three main bosses and an extra side boss.

## What this port adds

- Full port from 1.20.1 Forge to 1.21.1 NeoForge, with many mapping bugs fixed (unhittable bosses, floating mobs, missing animations, broken block interactions, effects that never ticked).
- Full Simplified Chinese translation; hardcoded texts moved into language files.
- Reworked boss fights with second phases, clearer telegraphs and hit timing that matches the animations.
- Weapon stats restored, new weapon abilities and hit effects.
- Quality-of-life: highlights for story chests and the next story block (visible through walls), the lock of a held key is highlighted, key tooltips show which door they open, optional Jade hints.
- Side quest for the Puppet of Yiva using content the original left unfinished.

## Requirements

- NeoForge for Minecraft 1.21.1
- GeckoLib
- Player Animator
- Optional: Jade

## Walkthrough

Use `/locate structure saphyra_reborn:<id>` if you cannot find a building: `fractured_clock_tower`, `the_three_hallowed_towers`, `church_of_the_oath_pus`, `observatory`.

1. **Start**: every player receives a **Sigil of the Red Sun** on first join.
2. **Fractured Clock Tower**: right-click the oddment on top with the sigil. Linara appears and gives you the **Hallowed Key (Worship)** and an **Invitation**.
3. **Three Hallowed Towers**: open the Worship fog, give the invitation to Saphyra and receive the **Pus Key (Hatred)**.
4. **Church of the Oath Pus**: collect the Steps and Dagger keys from chests and open their fogs. Behind the Dagger fog you find the **Manic Eye** and **The First God**. Take the **Sinister Key**, open the Hatred fog, then open the **Blood Boss Door** with the Sinister Key.
5. **Lyior Statue**: activate it, then right-click it with the broken sigil to get the **Sigil of the Red Sun (Stage 2)**.
6. **Boss 1 – Nameless Gatekeeper**: walk into the hall in Survival mode to awaken it. It drops **Remembrance: Nameless Gatekeeper**.
7. **Back to the towers**: show the remembrance to Saphyra to get the Chain keys, open the Chain fog and give Marlene **The First God**, then the remembrance. You get the **Sinister Bag** and the **Sword Gate Key**.
8. **Tower oddment**: use the Stage 2 sigil on the oddment, get the **Pus Key (Lovers)** from Linara and give her the **Manic Eye** (starts the side quest).
9. **Boss 2 – Saphyra → Ivory Devil**: open the Azure Boss Door at the Observatory with the Sword Gate Key. Defeat Saphyra, then the Ivory Devil, and take **Remembrance: Saphyra** to Marlene for the **Bottomless Bag**.

**Side quest – Puppet of Yiva**: after you give Linara the Manic Eye she hands you the **Sigil of the Red Sun (Stage 3)**. Use it inside the Fractured Clock Tower to summon the Puppet of Yiva, then bring its remembrance to Linara in the tower for her reward.

Boss stats and second-phase settings can be changed in the config.

## Credits

- Original mod, art, models, sounds, story and characters: **Mokel** – [The Shattered Goddess (Mokel's Bossfight) - Saphyra](https://www.curseforge.com/minecraft/mc-mods/the-shattered-goddess-mokels-bossfight-saphyra).
- The original was created with MCreator.
- This port keeps the original assets and story; it only ports, fixes and extends them. If you enjoy it, please support the original author.

## License & source

Licensed under the **Academic Free License v3.0**, the same license as the original work. Modifications are listed in `NOTICE.md`.
Source: [github.com/hp3081925122/SaphyraReborn](https://github.com/hp3081925122/SaphyraReborn)

---

# Saphyra 重生

**本模组是 Mokel 的 [The Shattered Goddess (Mokel's Bossfight) - Saphyra](https://www.curseforge.com/minecraft/mc-mods/the-shattered-goddess-mokels-bossfight-saphyra) 的非官方 1.21.1 NeoForge 移植版。**
模组内容归原作者 Mokel 所有，本人只负责移植与维护；原作者没有参与本移植，也没有为它背书。移植版的问题请不要反馈给原作者。

Saphyra 重生把 Mokel 的剧情 Boss 冒险带到了 Minecraft 1.21.1 NeoForge：四座剧情建筑、NPC、钥匙与锁雾、三个主线 Boss，外加一个支线 Boss。

## 本移植的改动

- 从 1.20.1 Forge 完整迁移到 1.21.1 NeoForge，修复了大量映射错误（Boss 打不到、生物悬空、动画不播放、方块无法交互、效果从不生效等）。
- 完整简体中文翻译，硬编码文本全部移进语言文件。
- 重做 Boss 战：加入二阶段，预警更清楚，伤害判定与动画对齐。
- 恢复武器属性，新增武器能力与命中特效。
- 便利功能：剧情箱子和下一个剧情方块会高亮（可透墙看到），手持钥匙时高亮对应的门，钥匙提示会写明能打开哪扇门，可选 Jade 提示。
- 用原作未完成的内容做了伊瓦的傀儡支线。

## 前置

- NeoForge（Minecraft 1.21.1）
- GeckoLib
- Player Animator
- 可选：Jade

## 流程

找不到建筑时可以用 `/locate structure saphyra_reborn:<ID>`：`fractured_clock_tower`、`the_three_hallowed_towers`、`church_of_the_oath_pus`、`observatory`。

1. **开局**：每个玩家第一次进入世界时获得 **赤日印记**。
2. **破碎钟楼**：手持印记右键楼顶的奇物，莉娜拉出现，给你 **圣塔印记钥匙（崇拜）** 和 **邀请函**。
3. **三座圣塔**：打开崇拜雾，把邀请函交给萨菲拉，获得 **脓疱印记钥匙（憎恨）**。
4. **脓誓教堂**：从箱子里拿阶梯、匕首钥匙并打开对应的雾，匕首雾后有 **狂乱之眼** 和 **《人造神之传说》**。拿到 **邪异之门钥匙**，打开憎恨雾，再用邪异之门钥匙打开 **鲜血首领之门**。
5. **莱奥雕像**：激活雕像，再用破损的赤日印记右键它，得到 **赤日印记（第二阶段）**。
6. **Boss 一：无名守门人**：以生存模式走进大厅唤醒它，掉落 **记忆：无名守门人**。
7. **回圣塔**：把记忆给萨菲拉看，拿到锁链钥匙打开锁链雾；先把《人造神之传说》、再把记忆交给玛琳，获得 **邪异袋** 和 **剑之门钥匙**。
8. **圣塔奇物**：用第二阶段印记右键奇物，从莉娜拉处获得 **脓疱印记钥匙（爱恋）**，并把 **狂乱之眼** 交给她（开启支线）。
9. **Boss 二：萨菲拉 → 象牙恶魔**：用剑之门钥匙打开观星台的苍蓝首领之门，先后击败萨菲拉和象牙恶魔，把 **记忆：萨菲拉** 交给玛琳，获得 **无底袋**。

**支线：伊瓦的傀儡**：把狂乱之眼交给莉娜拉后，她会给你 **赤日印记（第三阶段）**。在破碎钟楼内使用它召唤伊瓦的傀儡，击败后把它的记忆交给钟楼里的莉娜拉领取奖励。

Boss 数值和二阶段参数可以在配置文件中修改。

## 鸣谢

- 原模组、美术、模型、音效、剧情与角色：**Mokel**，[The Shattered Goddess (Mokel's Bossfight) - Saphyra](https://www.curseforge.com/minecraft/mc-mods/the-shattered-goddess-mokels-bossfight-saphyra)。
- 原模组使用 MCreator 制作。
- 本移植沿用原作的资源与剧情，只做移植、修复和扩展。喜欢的话请支持原作者。

## 许可与源码

采用 **Academic Free License v3.0**，与原作相同的许可。修改内容见 `NOTICE.md`。
源码：[github.com/hp3081925122/SaphyraReborn](https://github.com/hp3081925122/SaphyraReborn)
