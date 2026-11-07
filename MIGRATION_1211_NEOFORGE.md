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




