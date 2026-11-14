# /place structure 会放下方块，但不会像世界生成那样登记结构起点。这里按世界生成的方式补上起点，再使用第三阶段印记。
import time

STAGE3 = "saphyra_reborn:sigil_of_the_red_sun_stage_3"
PREMEMBER = "saphyra_reborn:puppet_of_yiva_remembrance"
SWORD = "saphyra_reborn:spirit_rune_sword"


def stage(text):
    print("\n==== " + text + " ====", flush=True)
    command('title @s times 0 50 10')
    command('title @s title {"text":"%s","color":"gold"}' % text)


def find_item(item_id):
    value = js(
        "var inv = player.getInventory();"
        "var found = -1;"
        "for (var i = 0; i < 36; i++) {"
        "  var stack = inv.getItem(i);"
        "  if (!stack.isEmpty() && String(stack.getItem().builtInRegistryHolder().key().location()) == '%s') { found = i; break; }"
        "}"
        "found" % item_id
    )
    return int(value)


def count_item(item_id):
    value = js(
        "var inv = player.getInventory();"
        "var n = 0;"
        "for (var i = 0; i < 36; i++) {"
        "  var stack = inv.getItem(i);"
        "  if (!stack.isEmpty() && String(stack.getItem().builtInRegistryHolder().key().location()) == '%s') n += stack.getCount();"
        "}"
        "n" % item_id
    )
    return int(value)


def hold(item_id):
    idx = find_item(item_id)
    expect(idx >= 0, "背包里没有 " + item_id)
    if idx <= 8:
        select(idx)
        return
    key("key.inventory")
    wait_screen("inventory")
    slot(idx, "swap", button=0)
    close()
    wait_closed()
    select(0)


def wait_item(item_id, timeout):
    print("等待", item_id, flush=True)
    started = time.time()

    def check():
        if find_item(item_id) >= 0:
            return True
        if time.time() - started > 2:
            command("execute as @e[type=item,distance=..16] run tp @s @p")
        return find_item(item_id) >= 0

    wait_until(check, timeout, interval=0.4, desc=item_id)
    print("拿到", item_id, "x", count_item(item_id))


def dismiss():
    if screen() is not None:
        close()
        frames(5)


window("show")
dismiss()
command("effect give @s minecraft:resistance 1000000 255 true")
command("effect give @s minecraft:night_vision 1000000 0 true")
expect(find_item(STAGE3) >= 0, "没有第三阶段印记")

stage("登记钟楼结构")
box = js(
    "var Level = Java.loadClass('net.minecraft.world.level.Level');"
    "var Registries = Java.loadClass('net.minecraft.core.registries.Registries');"
    "var ResourceKey = Java.loadClass('net.minecraft.resources.ResourceKey');"
    "var ResourceLocation = Java.loadClass('net.minecraft.resources.ResourceLocation');"
    "var ChunkPos = Java.loadClass('net.minecraft.world.level.ChunkPos');"
    "var SectionPos = Java.loadClass('net.minecraft.core.SectionPos');"
    "var Holder = Java.loadClass('net.minecraft.core.Holder');"
    "var HolderSet = Java.loadClass('net.minecraft.core.HolderSet');"
    "var Predicate = Java.loadClass('java.util.function.Predicate');"
    "var MethodHandles = Java.loadClass('java.lang.invoke.MethodHandles');"
    "var MethodType = Java.loadClass('java.lang.invoke.MethodType');"
    "var LambdaMetafactory = Java.loadClass('java.lang.invoke.LambdaMetafactory');"
    "var Bool = Java.loadClass('java.lang.Boolean');"
    "var sl = server.getLevel(Level.OVERWORLD);"
    "var key = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse('saphyra_reborn:fractured_clock_tower'));"
    "var structure = sl.registryAccess().registryOrThrow(Registries.STRUCTURE).get(key);"
    "var gen = sl.getChunkSource().getGenerator();"
    "var biomes = structure.biomes();"
    "var lookup = MethodHandles.lookup();"
    "var contains = lookup.findVirtual(HolderSet, 'contains', MethodType.methodType(Bool.TYPE, Holder));"
    "var factory = LambdaMetafactory.metafactory(lookup, 'test', MethodType.methodType(Predicate, HolderSet), MethodType.methodType(Bool.TYPE, Java.loadClass('java.lang.Object')), contains, MethodType.methodType(Bool.TYPE, Holder));"
    "var biomeOk = factory.getTarget().invokeWithArguments(Java.loadClass('java.util.Arrays').asList(biomes));"
    "var start = structure.generate(sl.registryAccess(), gen, gen.getBiomeSource(), sl.getChunkSource().randomState(), sl.getStructureManager(), sl.getSeed(), new ChunkPos(0, 0), 0, sl, biomeOk);"
    "if (!start.isValid()) 'invalid';"
    "else {"
    "  var box = start.getBoundingBox();"
    "  var origin = start.getChunkPos();"
    "  var home = sl.getChunk(origin.x, origin.z);"
    "  sl.structureManager().setStartForStructure(SectionPos.bottomOf(home), structure, start, home);"
    "  var minX = SectionPos.blockToSectionCoord(box.minX());"
    "  var maxX = SectionPos.blockToSectionCoord(box.maxX());"
    "  var minZ = SectionPos.blockToSectionCoord(box.minZ());"
    "  var maxZ = SectionPos.blockToSectionCoord(box.maxZ());"
    "  var ref = origin.toLong();"
    "  for (var cx = minX; cx <= maxX; cx++) {"
    "    for (var cz = minZ; cz <= maxZ; cz++) {"
    "      var chunk = sl.getChunk(cx, cz);"
    "      sl.structureManager().addReferenceForStructure(SectionPos.bottomOf(chunk), structure, ref, chunk);"
    "    }"
    "  }"
    "  box.minX() + ',' + box.minY() + ',' + box.minZ() + ' -> ' + box.maxX() + ',' + box.maxY() + ',' + box.maxZ();"
    "}"
)
print("结构范围", box)
expect("->" in str(box), "没能登记钟楼结构：" + str(box))
mins, maxs = str(box).split(" -> ")
minx, miny, minz = [int(float(v)) for v in mins.split(",")]
maxx, maxy, maxz = [int(float(v)) for v in maxs.split(",")]
cx = (minx + maxx) // 2
cy = min(max(miny + 8, miny), maxy)
cz = (minz + maxz) // 2
command("tp @s %.1f %.1f %.1f" % (cx + 0.5, cy, cz + 0.5))
frames(20)
inside = js(
    "var Level = Java.loadClass('net.minecraft.world.level.Level');"
    "var Registries = Java.loadClass('net.minecraft.core.registries.Registries');"
    "var ResourceKey = Java.loadClass('net.minecraft.resources.ResourceKey');"
    "var ResourceLocation = Java.loadClass('net.minecraft.resources.ResourceLocation');"
    "var BlockPos = Java.loadClass('net.minecraft.core.BlockPos');"
    "var sl = server.getLevel(Level.OVERWORLD);"
    "var key = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse('saphyra_reborn:fractured_clock_tower'));"
    "var structure = sl.registryAccess().registryOrThrow(Registries.STRUCTURE).get(key);"
    "String(sl.structureManager().getStructureAt(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()), structure).isValid())"
)
print("站在结构里", inside, "位置", cx, cy, cz)
expect(inside == "true", "登记之后仍然不在钟楼结构里")

stage("伊瓦的傀儡")
hold(STAGE3)
before = count_item(STAGE3)
look(yaw=90, pitch=0)
frames(20)
since = state().get("chat_count", 0)
use_item()
frames(30)
expect(count_item(STAGE3) == before - 1, "第三阶段印记没有消耗")
assert_chat("苏醒", timeout=5, since=since)
wait_until(lambda: len(entities(32, "saphyra_reborn:puppet_of_yiva_nemor")) > 0, 8, interval=0.4, desc="puppet")
frames(60)
command("kill @e[type=saphyra_reborn:puppet_of_yiva_nemor,distance=..64]")
wait_item(PREMEMBER, 15)
wait_until(lambda: len(entities(48, "saphyra_reborn:linara_clock_tower")) > 0, 15, interval=0.4, desc="linara")
found = entities(48, "saphyra_reborn:linara_clock_tower")
ent = found[0]
look(at=(float(ent["x"]), float(ent["y"]) + 1.5, float(ent["z"])))
frames(20)
hold(PREMEMBER)
use_entity(type="saphyra_reborn:linara_clock_tower", radius=16)
frames(20)
expect(find_item(PREMEMBER) < 0, "傀儡的记忆没有交给莉娜拉")
wait_item(SWORD, 20)
screenshot("ending_rewards")
print("结局奖励已拿到")
