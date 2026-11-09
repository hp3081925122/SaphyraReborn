# Boss 重做流程

日期：2026年11月09日

本文汇总无名守门人、象牙恶魔、萨菲拉三次重做中实际用过的步骤、共用代码和踩过的坑，下一个 Boss（伊瓦的傀儡、腐烂玫瑰）按这里的步骤做。各 Boss 的具体设计见同目录的 `NAMELESS_REWORK.md`、`MOTH_REWORK.md`、`SAPHYRA_REWORK.md`。

## 0. 总原则

- 原版的玩法是需求：节点、分支概率、近远判断、判定半径、伤害倍率和悲恸值都不改。原版的 MCreator 写法只是实现方式，可以重写。
- 不能削减效果。粒子量可以降，但必须用几何特效补上表现。
- 修复原版缺陷时，在设计文档里写明：原表现、原因、调整后的行为。改变玩法的地方要单独标注，例如萨菲拉跳劈的站定时机提前了。
- 伤害 = Boss 攻击力 × 技能倍率。召唤物的伤害也要跟随 Boss 攻击力。
- 阶段按百分比血量判定，阈值写进配置。

## 1. 摸清原版

1. **出招入口**：从实体的 `tick()` 找到 `XxxAttackPatternProcedure`。记下以下内容：
   - 时间轴变量（通常是 persistentData 里的 `IA`）
   - 各节点的 tick 和归零值
   - 50/50 分支
   - 近远判断（通常是 14×14×14 盒子里有没有玩家）
   - 突进、后撤与技能的先后顺序
2. **逐个读 Attack procedure**，每个技能整理成一行：
   - 动画名
   - 定身时长（`BossCastLock.lock`）
   - 每次 `queueServerWork` 的延迟、判定半径（`inflate`）、倍率和悲恸值
   - 伤害源是否带攻击者（不带就没有击退）
   - 粒子数量
3. **找未使用的内容**：
   - 对比 `TRIGGERABLE_ANIMATIONS` 和动画文件，找出没有被任何 procedure 调用的动画。
   - 全局搜索每个 procedure 的调用者，找出写好了却从未调用的 procedure（例如萨菲拉的 `AttackJumpAttackProcedure`）。
4. **查附属逻辑**：召唤物、投射物、死亡流程（萨菲拉死后会生成象牙恶魔）、`PlayerDiesProcedure` 的重置逻辑、音乐时间轴。
5. **找出手帧**：用探针脚本列出攻击骨骼角速度 >250°/s 的区间，与原判定 tick 对比。前三个 Boss 的原判定普遍晚 4~6 tick。

```bash
python "D:/AI临时文件/Saphyra重生/witch_rework/scripts/probe.py" <动画json> <动画名...>
```

## 2. 写设计文档

在 `docs/<BOSS>_REWORK.md` 中按以下顺序写：

1. 原版行为（附证据：节点表、技能表）
2. 已证实的原实现问题
3. 重做方案（判定 tick 列表）
4. 二阶段
5. 特效表
6. 动画
7. 配置
8. 未改动的部分
9. 验收项

## 3. 服务端：状态机

文件结构，以萨菲拉为例：

| 文件 | 作用 |
|---|---|
| `boss/<boss>/XxxSkill.java` | 枚举：动画名、定身 tick、技能总时长 |
| `boss/<boss>/XxxCombat.java` | 每只 Boss 一份，挂在实体字段上 |
| `boss/<boss>/XxxVfx.java` | S2C 特效包，带 kind、坐标、yaw、size、extra |
| `boss/<boss>/client/XxxVfxClient.java` | 客户端几何特效 |

Combat 的固定骨架（直接复制 `WitchCombat` 改）：

- `tick()` 按以下顺序执行：
  1. `clock++`
  2. 突进延迟
  3. 音乐
  4. 朝向
  5. 调试日志
  6. 检查转阶段
  7. 眩晕时取消技能
  8. 时间轴推进：节点到了而技能未结束时，记入 `pendingPoint`，等技能结束后再出招
  9. `tickSkill`
- 所有延时动作都用技能内的 `skillTick` 判断，禁止使用全局 `queueServerWork`。需要跨技能的延时伤害，用 Combat 自己的队列（例如象牙恶魔的 `Strike` 记录）。
- **判定一律用圆柱**：
  - 水平方向：目标碰撞箱边缘到中心的距离 ≤ r。
  - 竖直方向：从 −1 到 max(3, r×0.4)。
  - 扇形招式另加半角判断。
  - 不要用 `inflate` 方块范围，它在斜角方向会多打 41%。
- **伤害源**：原版带攻击者就用 `damageSources().mobAttack(boss)`；不带就用 `new DamageSource(MOB_ATTACK holder)`，保留“没有击退”的原版手感。
- **悲恸值**：直接改 `PlayerVariables.Grief` 后调用 `syncPlayerVariables`。
- **转阶段**：
  - 持续 60 tick，期间无敌（实体 `hurt` 中检查 `isInvulnerable`）。
  - t30 爆发：推开周围目标，不造成伤害，同时把同步数据 `PHASE2` 置为 true。
  - 之后出招节奏乘以 `cadence`，新招按 `new_skill_chance` 替换原节点。
- **朝向**：空闲时每 tick 最多转 30°，出招中 10°；锁定方向的技能转 0°。实体需要覆写 `createBodyControl`，让身体朝向跟随 yRot。
- **音乐**：先 `BossMusic.stop`，再播放新主题。
- **存档**：只保存时间轴、`pendingPoint`、是否二阶段、转阶段剩余 tick。进行中的技能在读档时清空。
- **调试日志**（英文）：
  - 每 100 tick 输出一次 `[XxxDebug]`。
  - 玩家被命中时输出 `[XxxHitDebug]`，包含技能、tick、半径、距离和伤害。

实体改动清单：
- 在 `TRIGGERABLE_ANIMATIONS` 中加入新动画。
- 加 `PHASE2` 同步数据和 `combat` 字段。
- `hurt` 中加无敌判断，save/load 中读写 `XxxCombat`。
- `tick` 中客户端跑光环，服务端跑 `combat.tick()`。
- 加 `restartAnimation`（先 stop 再 trigger，同名动画才能重播）和 `stopProcedureAnimation`。
- 在 `PlayerDiesProcedure` 中调用 `getCombat().resetFight()`。
- 删除旧的 pattern 和 Attack procedure：先全局搜索确认没有其他调用者。突进和后撤的 procedure 被多个 Boss 共用，不能删。

共用基础设施（`com.hp.saphyrareborn.boss`）：

| 类 | 用途 |
|---|---|
| `BossCastLock` | `lock`（定身，用速度修饰实现）、`shield`（格挡免伤）、`release`、`blocksDamage` |
| `BossAttributes.skillDamage(boss, 倍率)` | 读取攻击力属性。配置属性只在首次加入世界时写入一次，用 NBT 标记 |
| `BossConfig` | 每个 Boss 一个 `xxx_mechanics` 段：`phase2_enabled`、`phase2_health_ratio`、`phase2_cadence`、`phase2_new_skill_chance` |
| `BossMusic.stop` | 停止附近玩家的 Boss 音乐 |
| `client/BossVfxRender` | 发光渲染类型 `GLOW`、`Painter`（quad、annulus、arcBand、wall、beam）、镜头震动 `addShake`、碎屑 `debris` |

## 4. 客户端：几何特效

- 新建 S2C 包时复制 `WitchVfx`：`registrar("1").optional().playToClient`，客户端收到后放入 `INBOX`，INBOX 上限 256。发送范围 96 格。
- 客户端复制 `WitchVfxClient` 的框架：
  - `ClientTickEvent.Post` 中取包，`ACTIVE` 最多 96 个，在这里推进 age、刷粒子点缀。
  - `RenderLevelStageEvent.AFTER_PARTICLES` 中用 `GLOW` 一次画完，只画 128 格内的特效。
  - 需要跟随 Boss 的特效（光环、旋刃、聚能），在 4 格内找最近的 Boss 实体作为锚点。
- 每个 Boss 定一套 3~4 色的配色：
  - 无名守门人：腐绿
  - 象牙恶魔：象牙白和幽蓝
  - 萨菲拉：月银、巫紫和幽灵青
- 特效尺寸必须和判定半径一致：特效比判定小，玩家会觉得自己在特效外还被打到。
- 粒子只做点缀，每次几到几十个。原版几百上千的粒子喷发要换成几何体。

## 5. 动画

脚本模板：`D:/AI临时文件/Saphyra重生/witch_rework/scripts/witch_anims.py`（象牙恶魔版在 `moth_rework/scripts/moth_anims.py`）。

1. **备份**：先把原版动画复制到 `D:/AI临时文件/Saphyra重生/<boss>_rework/original/`。之后每次都从备份重新生成，保证脚本可以重复运行。
2. **收招**：每个攻击动画末尾追加 0.4s，缓动回到 idle 的第一帧。旋转角按 `target + 360×round((last−target)/360)` 就近回正。
3. **不要拉长动画去迁就判定**：象牙恶魔第一版这样做，实测出招偏慢，已撤回。正确做法是动画保持原速，把服务端判定移到出手帧上。
4. **次级运动烘焙**：
   - 用源骨骼的速度驱动弹簧滞后（披风、帽子、武器、袖子、头）。
   - 命中帧加重心下沉和错步，静止段叠加呼吸。
   - 叠加量乘以包络，首尾为 0，接缝不跳。
   - 共线的关键帧会被精简。
   - 角速度超过 1500°/s 的通道（旋转招）不叠加。
5. **链式骨骼的叠加会累积**：子骨骼会继承父骨骼的旋转。萨菲拉帽子 4 节链最初的增益让帽尖甩到约 100°，所以每节的增益要按链长减小。
6. **缓动支持**：脚本不支持的缓动会直接报错，不会退回线性。原版用到的缓动先统计一遍，再补到 `ease()` 里。
7. **新动画**：优先从已有动画中采样姿态来拼（萨菲拉的 `phaseshift` 采样了跳劈的蓄力和召唤的举杖），比手写角度可靠。
8. **GeckoLib 注意事项**：
   - 触发动画没有驱动的骨骼会保持 idle 的值。萨菲拉的魔爪和法杖在 idle 中缩放为 0，所以新动画要显示它们时，必须显式写 scale。
   - 关键帧上的缓动作用于进入该帧的过渡。

## 6. Blockbench 检查

Blockbench MCP 打开后用 `risky_eval` 操作：

1. 用 `loadModelFile({content, path})` 载入 geo，新建 `Texture` 并赋给所有面，再用 `Animator.loadFile({content, path})` 载入动画。
2. 用之前定义的 `window.__sheet(动画名, 时间数组, 输出png, {pos, target})` 渲染分帧拼图，再用 Read 查看。
3. 每次调用最多渲染约 6 帧，再多会超时。重新载入动画前，先删除旧的 `Animation.all`。
4. 每个动画至少检查命中帧前后、收招，以及新动画的每个关键姿态，各从正面和侧面看一次。
5. Blockbench 里看不到 idle 的缩放（魔爪等部位会以绑定姿态显示），这一点与游戏内不同，不算错误。

## 7. 验证与交付

1. 只运行 `./gradlew compileJava`，不打 jar，也不使用 `--offline`（会因为缺少 installertools 缓存而失败）。
2. 用户要求时才 runClient。`DownloadAssets` 卡住时，可以把本机启动器 `assets/objects` 中哈希相同的文件复制进 `~/.gradle/caches/minecraft/assets/objects`。
3. 在 `MIGRATION_1211_NEOFORGE.md` 末尾追加该 Boss 的重做小节。
4. 提交时只加入本次改动的文件，`mctest/` 不入库。提交信息不带任何 Claude 署名。完成后推送远程。
5. 交付说明中列出：具体改动、和原版不同的地方、测试项（例如把 `phase2_new_skill_chance` 调成 1.0 来测新招），以及需要关注的调试日志。

## 8. 进度

| Boss | 状态 | 文档 |
|---|---|---|
| 无名守门人 | 已重做 | `NAMELESS_REWORK.md` |
| 象牙恶魔 | 已重做 | `MOTH_REWORK.md` |
| 萨菲拉 | 已重做，待游戏内测试 | `SAPHYRA_REWORK.md` |
| 伊瓦的傀儡 | 未开始（入口 `PuppetAttackPatternProcedure`，5 段攻击动画） | — |
| 腐烂玫瑰 | 未开始（原地射击，只有 scream 和 spawn 两个动画） | — |

## 9. 风险

脚本都放在 `D:/AI临时文件/Saphyra重生/` 下。这个目录曾经整体丢失过一次，无名守门人的烘焙脚本没能找回。开始做新 Boss 前，先确认模板脚本还在；如果丢了，以本文第 5 节和象牙恶魔、萨菲拉的设计文档为准重新写。
