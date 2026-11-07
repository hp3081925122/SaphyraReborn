# 迁移批次 002：资源与数据初步导入

## 范围

将原 JAR 中 `assets/mokels_bossfight_saphyra` 与 `data/mokels_bossfight_saphyra` 导入目标项目，并将目录命名空间改为 `saphyra_reborn`。

## 已导入

- assets 文件：616
- data 文件：60
- 模型、纹理、GeoLib 模型、动画、声音、粒子、语言文件均保留。
- 战利品表、配方、结构、世界生成和标签目录均保留。

## 注意事项

- 原语言文件仍包含 `mokels_the_shattered_goddess` 与 `mokels_witch_boss` 等历史显示键，不能仅按文件目录替换，需要在注册表批次确定最终 ID 后统一修正。
- NBT 结构文件未做字符串替换，需在结构迁移批次使用 NBT 解析器核对命名空间和 DataVersion。
- 资源导入不代表客户端渲染已完成，GeckoLib 4.8.4 适配和 Render Preview 对照属于后续批次。

## 验证

- 已完成文件数量和目录存在性检查。
- 待完成资源加载、模型渲染、动画播放和结构放置验证。
