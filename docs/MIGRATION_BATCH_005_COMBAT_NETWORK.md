# 迁移批次 005：战斗状态与网络同步

## 原始同步范围

原模组网络消息包括：

- 玩家闪避按键消息。
- 玩家变量同步，字段包括 Dash、Rotting、Grief、Manic、Xplayer、Zplayer、Devotion。
- 地图变量与世界变量同步。
- 动画消息，向客户端传递动画键、目标实体和覆盖标记。

## 目标 1.21.1 设计

- 玩家变量使用 NeoForge AttachmentType，开启死亡复制和网络同步。
- 变量修改后调用 attachment sync，不再使用 Forge capability 与 LazyOptional。
- 每条消息改为 `CustomPacketPayload`，通过 `RegisterPayloadHandlersEvent` 注册。
- 服务端处理客户端请求时校验发送玩家和目标归属。
- 动画消息只在客户端播放，服务端保留实体状态同步。

## 当前状态

- 已确认旧 Forge `SimpleChannel`、`NetworkEvent.Context`、`PacketDistributor` 和 capability API 在目标版本不存在。
- 尚未替换为最终 payload 实现。
- 战斗 procedure 的数值和触发逻辑仍保留在源码中，当前不删减。
- 这一批次未完成，不能进行最终服务端验收。

## 当前编译证据

`compileJava --offline` 已将错误集中到旧 Forge 网络接口和变量 capability。客户端事件、注册表、GeckoLib、实体 Tick、资源导入已不再是当前首批错误。
`MokelsBossfightSaphyraModVariables` 仍需要从 capability/LazyOptional 改成 AttachmentType，并保留死亡复制、持久化和同步。

## 临时编译适配说明

当前源码中加入了旧消息接口的临时兼容层，仅用于继续暴露后续 1.21.1 编译错误。该层尚未接入真实 payload 传输，不能视为迁移完成，也不能用于最终运行测试。最终版本必须删除该兼容层并改为 `CustomPacketPayload`。
