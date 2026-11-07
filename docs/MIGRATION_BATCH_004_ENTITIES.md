# 迁移批次 004：实体源码与公共 API 适配

## 已处理

- 纳入原始实体、投射物、模型和渲染相关反编译源码。
- GeckoLib 4.8.4 包路径改为 1.21.1 NeoForge API。
- 删除 Forge `NetworkHooks` / `PlayMessages` 生成式生成包依赖，保留实体标准类型构造路径。
- 将 Forge 注册表引用改为 NeoForge 注册表入口。
- 将 Forge `MobType` 旧接口移除，目标版本使用新的伤害类型/标签语义。
- 将 1.20.1 `BlockPathTypes` 改为 1.21.1 `PathType`。
- 为原 JAR 缺失的 `RightClickBossDoorKeyProcedure` 建立行为委托适配类。
- 为编译缺失的 procedure class 重新从原始 class 文件单独反编译并纳入源码。

## 当前编译反馈

当前仍未通过 `compileJava`。剩余错误主要集中在：

- NeoForge 1.21.1 实体注册 builder 与旧版 `setCustomClientFactory` 的差异。
- NeoForge 1.21.1 玩家 tick 事件已拆分为 `PlayerTickEvent`。
- 原始 MCreator 网络变量和 SimpleChannel 需要重写为 NeoForge payload 注册。
- 仍有部分客户端渲染和物品扩展需要迁移。

## 保真要求

实体攻击模式、技能 procedure、投射物命中和动画状态继续保留在源码中，当前只做加载器 API 适配，不删减行为。

## 后续编译进展 2026-11-07

- GeckoLib `CoreGeoBone` 已改为 4.8.4 的 `GeoBone`。
- 1.21.1 客户端事件已改为 `net.neoforged.neoforge.client.event`。
- 玩家和实体 Tick 事件已开始改为 `PlayerTickEvent.Post` 与 `EntityTickEvent.Post`。
- 注册表旧 `RegistryObject` 已开始改为 `DeferredHolder`，注册表键改为原版 `Registries`。
- 当前 `compileJava --offline` 已将主要错误收敛到旧 Forge 网络 API、NeoForge payload 迁移和少量物品能力引用。
- 当前仍未通过编译，不能进入服务端或渲染验收。
