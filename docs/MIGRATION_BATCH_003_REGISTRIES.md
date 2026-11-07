# 迁移批次 003：注册表盘点与 NeoForge 适配设计

## 原始注册表证据

原始 JAR 的 MCreator 注册类显示：

- 实体注册类 25 个实体类型。
- 方块注册类 54 个顶层方块类。
- 物品注册类 72 个顶层物品类。
- 药水效果 20 个。
- 粒子、声音、绘画、方块实体、创意标签和键位均有独立注册类。
- 原始注册表命名空间为 `mokels_bossfight_saphyra`，部分语言键残留 `mokels_the_shattered_goddess` 和 `mokels_witch_boss`。

## 目标设计

- 目标命名空间统一为 `saphyra_reborn`。
- 使用 NeoForge 1.21.1 的 `DeferredRegister`、`RegisterEvent` 和数据组件/标签机制。
- 实体属性注册改用目标版本的 `EntityAttributeCreationEvent` 入口。
- 客户端渲染注册与服务端注册分离，避免服务端加载客户端类。
- 不直接复制 Forge 1.20.1 的 MCreator 初始化类，先按注册类型拆分。

## 当前状态

- 已完成：注册清单盘点，语言历史命名空间统一为目标命名空间。
- 待实现：NeoForge 注册类和实体类型。
- 待核对：原始方块实体、粒子、声音、药水效果和创意标签的完整字段。
- 待验证：所有资源引用与新注册 ID 一致。

## 证据文件

原始反编译注册类位于 `D:\AI临时文件\Saphyra重生\decompiled\net\mcreator\mokelsbossfightsaphyra\init`，仅作行为和字段参考，不作为最终源码。

## 编译验证 2026-11-07

已运行 `compileJava`。NeoForge 用户开发包和 GeckoLib 4.8.4 已解析，源码进入 Java 编译阶段。当前失败原因是 Forge 1.20.1 到 NeoForge 1.21.1 的 API 差异，主要集中在：

- NeoForge 事件和客户端事件包路径。
- 1.21.1 已移除或重构的 Forge capability、LazyOptional、IItemHandler 和 SidedInvWrapper。
- 原始 MCreator 生成源码缺失一个 `RightClickBossDoorKeyProcedure` 引用文件。
- 方块路径类型、网络 SimpleChannel 和部分客户端扩展 API。

这批次仍未完成，下一步先修正公共 API 适配层，再恢复注册表编译。
