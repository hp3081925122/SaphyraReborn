# Saphyra 1.21.1 NeoForge 迁移重做文档

## 项目边界

- 原项目：The Shattered Goddess (Mokel's Bossfight) - Saphyra
- 原项目页面：<https://www.curseforge.com/minecraft/mc-mods/the-shattered-goddess-mokels-bossfight-saphyra>
- 原版本证据：用户提供的 CurseForge 页面截图显示游戏版本为 1.20.1，加载器为 Forge。
- 目标版本：Minecraft 1.21.1 NeoForge 21.1.265。
- 当前阶段：已导入原始资源并完成部分 NeoForge 1.21.1 API 适配，实体旧映射清理与运行验收仍未完成。
- 许可证：Academic Free License (AFL) v3.0，详见 `ORIGINAL_LICENSE.md`。

## 功能覆盖清单

| 模块 | 目标实现 | 状态 |
| --- | --- | --- |
| Saphyra Boss 实体 | NeoForge 实体注册、属性、AI、阶段和技能 | 待实现 |
| Boss 技能与攻击 | 保留原触发条件、伤害次数、范围、预警、粒子和音效 | 待实现 |
| 模型、贴图、动画 | 保留原视觉表现并做 1.21.1 客户端渲染适配 | 待实现 |
| 召唤、战利品、配方 | 迁移配方、战利品、标签和召唤入口 | 待实现 |
| 配置 | 为 Boss 属性和技能参数提供 NeoForge 配置 | 待实现 |
| 存档与重载 | 验证死亡、卸载、重载、重启和跨维度状态 | 待验证 |

## 重做约束

1. 不以编译通过代替功能覆盖。实体、技能、粒子、动画和资源必须按注册 ID 与行为逐项核对。
2. 伤害应由 Boss 基础攻击力乘技能倍率得到，击退受到目标击退抗性影响。
3. 阶段切换使用百分比生命值判定，属性只在首次应用时写入实体标记，避免覆盖整合包调整。
4. MCR 药水状态、全局延时队列和命令驱动逻辑需要先核实实际用途，再替换为目标版本原生机制。
5. 渲染完成后使用 `E:/模组/自用工具源码+产物/Render Preview` 对照模型、动画、贴图、透明层、发光层、粒子和预警。

## 当前验证记录

- 已完成部分 1.21.1 BlockEntity 持久化 Provider、NeoForge ResourceLocation 工厂方法、透明方块 API、粒子与模型基础 API 的适配。
- `compileJava --offline` 仍失败，最新日志位于 `D:\\AI临时文件\\Saphyra重生_compile8.log`。
- 失败集中在 GhostSpirit、GhostZombie、InfestedWitch、LinaraHallowedTower2 的实体旧映射 API，以及对应渲染器。
- 服务端测试已尝试 `runServer --offline`，因编译失败未进入服务端进程，没有真实服务端行为证据。

## 验收项

- [ ] 获取并固定原始 JAR 或 MCR 工作区快照及哈希。
- [ ] 机械盘点实体、物品、方块、注册表、事件、procedure、资源和数据文件。
- [ ] 完成 Java、资源、数据和配置迁移。
- [ ] 运行 `compileJava` 并记录真实日志。
- [ ] 交给子代理执行服务端功能测试。
- [ ] 使用 Render Preview 执行客户端渲染对照。
- [ ] 复核 AFL v3.0 归属与分发说明。





## 迁移验收补充

- 原始 JAR：`backup/original/saphyra-2.0.2-forge-1.20.1-original.jar`，由用户提供并纳入本地 Git 备份。
- 目标环境：Minecraft 1.21.1、NeoForge 21.1.265、GeckoLib 4.8.4。
- Java 源码：由原始 JAR 反编译源码恢复后完成 NeoForge 注册、ResourceLocation、Holder、数据组件、事件、网络和实体构建 API 适配，保留原 procedure 技能队列、音效、粒子、伤害和实体注册清单。
- 编译证据：`D:\AI临时文件\Saphyra重生_compile_green3.log`，`compileJava --offline` 输出 `BUILD SUCCESSFUL`。
- 服务端验证：已交给子代理执行 `runServer --offline`，等待真实启动与注册日志。
- 客户端渲染验证：使用 `E:\模组\自用工具源码+产物\Render Preview\1.21.1-neoforge-render-preview`，待生成 jar 后执行。

| 项目 | 状态 |
| --- | --- |
| 原始 JAR 与本地 Git 备份 | 已完成 |
| Java 编译 | 已通过 |
| 服务端启动与功能测试 | 子代理进行中 |
| Render Preview 渲染对照 | 待执行 |
| 完整迁移交付 | 等待以上两项证据 |

## 最终验证补充

- 服务端二次启动验证：`runs/server/logs/latest.log` 出现 `Done (7.244s)! For help, type "help"`，完成事件注册、世界生成并加载 1290 recipes、1399 advancements。第一次 `ClientListener` 崩溃报告属于修复前状态，修复后无新崩溃。
- Render Preview 客户端目录：`E:\模组\自用工具源码+产物\Render Preview\1.21.1-neoforge-render-preview`。
- Render Preview 目录加载：`D:\AI临时文件\Saphyra重生_renderpreview3.log`，目录包含 `saphyra_reborn`，1906 项条目，Saphyra 实体构造检查无失败。
- 直接 HTTP 渲染证据：`D:\AI临时文件\RenderPreview\20261107-230758-26492\saphyra-witchboss-20.png` 和 `.json`，1280×720、foregroundPixels 17445、blackRatio 0、magentaRatio 0；技能动画截图 `saphyra-witchboss-slash-15.png`，foregroundPixels 19348、blackRatio 0、magentaRatio 0。
- 全量预览器验证在工具自带 `minecraft:player` 粒子条目触发工具侧异常，Saphyra 目录加载与指定实体/动画的直接渲染捕获已通过。

## 2026年11月08日 Boss 技能与动画优化

依据：对照原版 JAR 反编译源码（`D:\AI临时文件\Saphyra重生\decompiled`、`missing_decompiled`）与 1.20.1 SRG 映射。编译证据：`D:\AI临时文件\Saphyra重生_compile_opt3.log`（`compileJava --offline` 退出码 0）。未做服务端/客户端运行测试。

| 项目 | 原表现 / 原因 | 调整后 | 是否改变玩法 |
| --- | --- | --- | --- |
| 伤害来源方法对调 | 移植时 `m_7640_`(getDirectEntity) 与 `m_7639_`(getEntity) 整体对调，14 个实体的箭矢/药水免疫失效，`instanceof Player` 由“仅近战”变成“玩家造成的全部伤害” | 按原版恢复 | 否（恢复原版） |
| 每 tick 朝向命令 | `/tp @s ~ ~ ~ facing entity @p` 执行源无实体，`@s` 为空，原版即无效，每 tick 白解析命令 | 删除 6 处；PutridRose 攻击模式只剩这条命令，procedure 一并删除 | 否 |
| 全服停音乐 | `/stopsound @a music` 停掉全服所有维度玩家的音乐 | `BossMusic.stop` 只对同维度 128 格内玩家发停止音效包 | 远处/其他维度玩家不再被停音乐 |
| 重复扫描 | `if (!扫描) … else if (扫描为空)` 连扫两次；Mothboss 另多扫一次 ServerPlayer | 改为单次扫描 + else | 否 |
| 延时队列 | 移植版丢失原版“仅服务端线程入队”判断，客户端线程的调用会进服务端队列；任务异常会导致重复执行；停服不清空 | 恢复服务端线程判断、单任务异常隔离并记录日志、停服清空 | 否（恢复原版） |
| 技能定身 | 100 级缓慢药水（30 处）充当施法锁，可被牛奶等清除 | `BossCastLock`：持久化截止 tick + 移速 -100% 临时修饰，取 max 延长语义与药水一致 | 否 |
| Nameless 盾牌 | 100 级抗性药水充当无敌 | `BossCastLock.shield` + `getDamageAfterMagicAbsorb` 归零，保留受击反馈，尊重 bypasses_effects/resistance | 否 |
| 技能伤害 | 43 处硬编码（14，1 处 30），Boss 攻击力为 0 | 攻击力默认 14，伤害 = 攻击力 × 倍率（1.0 / 30÷14）；近战 `doHurtTarget` 返回 false（原版攻击力 0 对玩家无伤害） | 默认数值不变；对非玩家目标的 0 伤害近战受击/击退不再出现 |
| Boss 配置 | 无 | `config/saphyra_reborn-common.toml`：5 个 Boss 的生命/攻击/护甲，首次进入世界写入一次（NBT `SaphyraConfigApplied`），按原血量比例换算 | 新增 |
| 动画同步 | 每 tick 反射读取同步字符串（`EntityAnimationFactory`） | GeckoLib triggerableAnim，注册模型 JSON 中全部动画；`"empty"` 改为停止触发动画 | 否 |
| 死亡动画顺序 | 先判移动后判死亡，死亡时有击退会先播 walk；技能动画中死亡要等技能动画播完 | 死亡判断提前，procedure 控制器在死亡时让出 | 死亡动画立即播放 |
| Linara 动画名 | 原版调用 `greetings`，JSON 为 `greeting`，动画从未播放 | 改为 `greeting` | 招呼动画首次可见 |

未处理 / 待定：
- 门、雕像方块动画名（状态整数 `"0"` 等与 JSON 键 `1/2/3`、`idle` 不对应）：对应关系无法从原资源确定，保持原样。
- `STUN`/`IMMUNE` 只检查从未施加、未接线的动画（knightboss `stun`/`attackcharge`、Witchboss `spinattack`/`wandattack`、Mothboss `block`/`attacktriple` 等）、百分比血量转阶段：属于新增玩法，需要设计后再做。
- `/title @a` 仍为全服广播；Rot 投射物 4 点伤害未挂钩攻击力（无可靠施法者引用）。
- 待验证：服务端行为、Render Preview 动画对照。

## 2026年11月08日 无名守门人重做 & Boss 回血写法

设计与验收清单见 `docs/NAMELESS_REWORK.md`。

| 项 | 原实现 | 现实现 |
|---|---|---|
| 玩家死亡重置 Boss | 施加 HARM/HEAL 等级 10000，靠亡灵判定把伤害变成回血 | `setHealth(getMaxHealth())`，无名同时重置阶段与技能 |
| 腐朽效果给无名回血 | HARM II（亡灵回 16） | `heal(16)` |
| `NamelessEntityDiesProcedure` | 传入的是 Boss 自身，玩家判断永远不成立 | 删除（原版即无效） |
| 无名时间轴/技能 | `NamelessAttackPatternProcedure` + 11 个 Attack procedure + 全局闭包 | `boss/nameless/NamelessCombat` 实体内状态机，状态存 NBT |
| 腐朽雨 | 每枚落点约 30 个全局闭包 | 隐形投射物落地后自身转为腐朽池（NBT 记录进度），60 tick 抛出 3 枚下落投射物 |
| 下落投射物伤害 | 固定 4 | `skillDamage(施法者, 4/14)` |
| 音乐切换 | 先播新曲再 `/stopsound @a music` | 先停 128 格内音乐再播新曲 |
| 无名朝向 | 全程无重力，`onGround` 恒为 false，不寻路、不转身（原版同样存在） | 仅跃斩期间无重力，控制器主动转向目标 |
| `WalkToBlockProcedure` | 让实体移动到自己脚下（无效果） | 删除 |
| 动画 | 线性、命中帧比判定早 4~5 tick、无收招 | 保留原姿势，按判定 tick 重定时、加缓动与收招；新增 stagger、phaseshift；跃斩动画从起跳开始播 |

新增：二阶段（50%，可配置）、双斩两段（0.6×2）、盾反破防（6% 最大生命，可配置）、S2C `nameless_vfx` 特效包与客户端镜头震动。
配置：`bosses.nameless_mechanics`。
待验：游戏内特效观感、二阶段与破防手感（未做客户端/服务端测试）。

## 象牙恶魔重做（2026年11月09日）

| 项 | 原实现 | 现实现 |
|---|---|---|
| 出招时间轴 | `MothbossAttackPatternProcedure` + 全局闭包 | `boss/moth/MothCombat` 状态机，节点和分支不变 |
| 技能 procedure | AttackSmite / SwordOnce / twirl / GroundMoth / sky / Back | 已删除，逻辑并入 `MothCombat`；AttackDashForth / DashBack 被傀儡和 Saphyra 共用，保留 |
| 天罚、升空弹幕 | 每个目标 8 支下落或上升弹，固定 14 伤害 | 落点打击队列，伤害 = 攻击力 × 1.0，表现用几何特效 |
| 判定 | `inflate(r)` 方块 | 圆柱（按碰撞箱边缘算水平距离） |
| 二阶段 | 无 | 50% 血转阶段，新增突刺、三连斩、格挡反击（可配置） |
| 特效渲染 | 粒子 | `MothVfx` 包 + `MothVfxClient` 几何体；与无名守门人共用 `boss/client/BossVfxRender` |

详见 `docs/MOTH_REWORK.md`。

## 萨菲拉重做（2026年11月09日）

详见 `docs/SAPHYRA_REWORK.md`。

- 原 `SaphyraAttackPatternProcedure` 和 7 个 Attack procedure（含从未调用的跳劈）已删除，改由 `boss/witch/WitchCombat` 状态机实现。节点、分支、半径、倍率和悲恸值与原版一致，判定对齐到动画出手帧。
- 月光柱：
  - 删除 `MoonbeamOnEntityTickUpdateProcedure`（每 tick 塞一个全局闭包）和 `MoonbeamOnInitialEntitySpawnProcedure`（靠闭包控制消失）。
  - 改为实体年龄计时，持久化到 NBT `MoonAge`。
  - 伤害跟随 Boss 攻击力。
- 二阶段：`wandattack`、`jumpattack`、`spinattack` 做成新招，新增 `phaseshift` 动画。配置项在 `bosses.witchboss_mechanics`。
- 特效：新增 S2C 包 `witch_vfx` 和 `WitchVfxClient`，用几何特效替代原版每次 300~900 个粒子的喷发。
- 动画：所有攻击动画追加收招回 idle，并加了次级运动（帽子链、法杖、魔爪、袖子）。脚本在 `D:/AI临时文件/Saphyra重生/witch_rework/scripts/witch_anims.py`。
- 死亡对话和死亡后生成象牙恶魔的流程没有改动。

## 开局印记改为按玩家发放（2026年11月09日）

- 原版 `PlayerJoinedWorldProcedure` 按世界计数器 `playerjoinedworld == 1` 发放赤日印记，多人服务器上只有第一个进入的玩家能拿到。
- 现在改为玩家变量 `SigilGiven`：会存档，死亡克隆时也会复制。每个玩家首次登录时发 1 枚，提示只发给本人，不再全服广播。
- 旧存档中没有该标记、但背包里已有任意赤日印记（含破损的）的玩家，只补写标记，不重复发放。日志标签为 `[SigilDebug]`。
