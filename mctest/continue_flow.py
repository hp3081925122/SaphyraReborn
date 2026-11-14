# 从恋人雾锁继续。上一段已经拿到钥匙，教堂区块当时没加载，方块读成了 void_air。
import time

CLOCK = (0, -60, 0)
TOWER = (240, -60, 0)
CHURCH = (480, -60, 0)
OBS = (720, -60, 0)

KEY3 = "saphyra_reborn:key_3"
SKEY = "saphyra_reborn:saphyra_key"
SREMEMBER = "saphyra_reborn:saphyra_remembrance"
BBAG = "saphyra_reborn:bottomless_bag"
STAGE3 = "saphyra_reborn:sigil_of_the_red_sun_stage_3"
PREMEMBER = "saphyra_reborn:puppet_of_yiva_remembrance"
SWORD = "saphyra_reborn:spirit_rune_sword"


def stage(text):
    print("\n==== " + text + " ====", flush=True)
    command('title @s times 0 50 10')
    command('title @s title {"text":"%s","color":"gold"}' % text)


def world_pos(origin, rel):
    return origin[0] + rel[0], origin[1] + rel[1], origin[2] + rel[2]


def block_id(x, y, z):
    return js(
        "var BlockPos = Java.loadClass('net.minecraft.core.BlockPos');"
        "String(level.getBlockState(new BlockPos(%d, %d, %d)).getBlock().builtInRegistryHolder().key().location())" % (x, y, z)
    )


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


def stand_at(origin, rel, look_rel=None):
    x, y, z = world_pos(origin, rel)
    command("tp @s %.2f %.2f %.2f" % (x + 0.5, y, z + 0.5))
    frames(10)
    if look_rel is not None:
        lx, ly, lz = world_pos(origin, look_rel)
        look(at=(lx + 0.5, ly + 0.6, lz + 0.5))
        frames(10)


def hold(item_id):
    idx = find_item(item_id)
    expect(idx >= 0, "背包里没有 " + item_id)
    if idx <= 8:
        select(idx)
        frames(5)
        return
    key("key.inventory")
    wait_screen("inventory")
    slot(idx, "swap", button=0)
    close()
    wait_closed()
    select(0)


def dismiss():
    if screen() is not None:
        close()
        frames(5)


def click_block(origin, stand, target, face):
    dismiss()
    stand_at(origin, stand, target)
    x, y, z = world_pos(origin, target)
    wait_until(lambda: block_id(x, y, z) != "minecraft:void_air", 8, interval=0.25, desc="加载方块")
    use_block(x, y, z, face=face)
    frames(12)
    return x, y, z


def unlock(origin, stand, target, face, old_id, chat_text=None):
    x, y, z = click_block(origin, stand, target, face)[:3] if False else world_pos(origin, target)
    stand_at(origin, stand, target)
    wait_until(lambda: block_id(x, y, z) != "minecraft:void_air", 8, interval=0.25, desc="加载方块")
    before = block_id(x, y, z)
    expect(before == old_id, "锁不在预期位置：%s 实际 %s" % (old_id, before))
    since = state().get("chat_count", 0)
    look(at=(x + 0.5, y + 0.5, z + 0.5))
    use_block(x, y, z, face=face)
    frames(15)
    now = block_id(x, y, z)
    print(old_id, "->", now)
    expect(now != old_id, old_id + " 没有打开，还是 " + now)
    if chat_text:
        assert_chat(chat_text, timeout=3, since=since)
    screenshot(old_id.split(":")[-1])


def wait_item(item_id, timeout):
    print("等待", item_id, "最多", timeout, "秒", flush=True)
    started = time.time()

    def check():
        if find_item(item_id) >= 0:
            return True
        if time.time() - started > 3:
            command("execute as @e[type=item,distance=..12] run tp @s @p")
        return find_item(item_id) >= 0

    wait_until(check, timeout, interval=0.5, desc=item_id)
    print("拿到", item_id, "x", count_item(item_id))


def talk(etype, item=None):
    dismiss()
    if item:
        hold(item)
    frames(10)
    found = entities(16, etype)
    print(etype, found[:1])
    expect(found, "附近没有 " + etype)
    ent = found[0]
    look(at=(float(ent["x"]), float(ent["y"]) + 1.5, float(ent["z"])))
    frames(15)
    use_entity(type=etype, radius=16)
    frames(20)


def wait_entity(etype, timeout):
    print("等待实体", etype, flush=True)
    wait_until(lambda: len(entities(32, etype)) > 0, timeout, interval=0.5, desc=etype)
    ent = entities(32, etype)[0]
    look(at=(float(ent["x"]), float(ent["y"]) + 1.2, float(ent["z"])))
    frames(40)
    return ent


def open_bag(item_id, reward_id):
    before = count_item(item_id)
    hold(item_id)
    command("tp @s 8 20 8")
    look(yaw=0, pitch=-80)
    frames(20)
    use_item()
    frames(30)
    wait_item(reward_id, 8)
    left = count_item(item_id)
    print(item_id, before, "->", left)
    expect(left == before - 1, item_id + " 没有被消耗，还剩 %d" % left)
    screenshot(item_id.split(":")[-1])


def inside_clock_tower():
    try:
        return js(
            "var Registries = Java.loadClass('net.minecraft.core.registries.Registries');"
            "var ResourceKey = Java.loadClass('net.minecraft.resources.ResourceKey');"
            "var ResourceLocation = Java.loadClass('net.minecraft.resources.ResourceLocation');"
            "var BlockPos = Java.loadClass('net.minecraft.core.BlockPos');"
            "var Level = Java.loadClass('net.minecraft.world.level.Level');"
            "var sl = server.getLevel(Level.OVERWORLD);"
            "var key = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse('saphyra_reborn:fractured_clock_tower'));"
            "var tower = sl.registryAccess().registryOrThrow(Registries.STRUCTURE).get(key);"
            "if (tower == null) 'missing';"
            "else String(sl.structureManager().getStructureAt(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()), tower).isValid())"
        )
    except Exception as error:
        print("结构查询失败", error)
        return "unknown"


window("show")
if not state().get("in_world"):
    ensure_world("saphyra_watch")
command("gamemode survival @s")
command("difficulty normal")
command("effect give @s minecraft:resistance 1000000 255 true")
command("effect give @s minecraft:night_vision 1000000 0 true")
command("effect give @s minecraft:saturation 1000000 20 true")

dismiss()
if find_item(STAGE3) >= 0:
    print("第三阶段印记已经在背包里")
elif find_item(SREMEMBER) >= 0:
    print("萨菲拉的记忆已经在背包里，接着交给玛琳")
    try:
        assert_chat("第一章结束", timeout=25, since=480)
        assert_chat("第二章即将到来", timeout=12, since=480)
    except McError as error:
        print("章节台词未再等到：", error)
    stage("第三阶段印记")
    stand_at(TOWER, (11, 11, 8), (12, 11, 8))
    frames(20)
    talk("saphyra_reborn:marlene_human_form_dialog_2", SREMEMBER)
    expect(find_item(SREMEMBER) < 0, "萨菲拉的记忆没有交给玛琳")
    wait_item(BBAG, 12)
    wait_item(STAGE3, 25)
    screenshot("sigil_stage3")
    open_bag(BBAG, "saphyra_reborn:witch_staff")
else:
    raise McError("背包里既没有萨菲拉的记忆，也没有第三阶段印记")

stage("伊瓦的傀儡")
stand_at(CLOCK, (16, 20, 16), (18, 33, 16))
frames(20)
print("钟楼结构判定", inside_clock_tower())
if inside_clock_tower() != "true":
    command("forceload add 0 0 3 3")
    command("tp @s %d %d %d" % (CLOCK[0] + 16, CLOCK[1] + 20, CLOCK[2] + 16))
    frames(20)
    placed = c.call("command", command="place structure saphyra_reborn:fractured_clock_tower %d %d %d" % CLOCK, timeout=180)
    print("注册钟楼结构", placed.get("output"))
    frames(40)
    stand_at(CLOCK, (16, 20, 16))
    print("再次判定", inside_clock_tower())
hold(STAGE3)
before = count_item(STAGE3)
look(yaw=90, pitch=0)
frames(15)
use_item()
frames(40)
if count_item(STAGE3) == before:
    print("当前位置不算钟楼内部")
    command("tp @s %d %d %d" % (CLOCK[0] + 8, CLOCK[1] + 10, CLOCK[2] + 8))
    frames(20)
    print("起点判定", inside_clock_tower())
    hold(STAGE3)
    look(yaw=90, pitch=0)
    use_item()
    frames(40)
expect(count_item(STAGE3) == before - 1, "第三阶段印记没有消耗，结构判定 " + str(inside_clock_tower()))
wait_entity("saphyra_reborn:puppet_of_yiva_nemor", 8)
frames(50)
command("kill @e[type=saphyra_reborn:puppet_of_yiva_nemor,distance=..64]")
wait_item(PREMEMBER, 15)
wait_entity("saphyra_reborn:linara_clock_tower", 15)
talk("saphyra_reborn:linara_clock_tower", PREMEMBER)
expect(find_item(PREMEMBER) < 0, "傀儡的记忆没有交给钟楼的莉娜拉")
wait_item(SWORD, 20)
screenshot("ending_rewards")
print("结局奖励已拿到")
