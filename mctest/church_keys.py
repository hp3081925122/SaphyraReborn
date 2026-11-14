# 在脓誓教堂结构内部走动：打开装钥匙的箱子/木桶，再用钥匙开对应的雾锁和首领门。
import json
import os

paths = json.load(open(os.path.join(os.path.dirname(__file__), "church_paths.json"), encoding="utf-8"))

# 结构原点。相对坐标来自 church_of_the_oath_pus.nbt。
OX, OY, OZ = 80, -60, 80


def world_pos(rel):
    return OX + rel[0], OY + rel[1], OZ + rel[2]


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


def move_along(name):
    print("移动", name, "共", len(paths[name]), "步")
    for rel in paths[name]:
        x, y, z = world_pos(rel)
        command("tp @s %.2f %.2f %.2f" % (x + 0.5, y, z + 0.5))
    frames(8)
    p = player()
    print("到达", round(p["x"], 1), round(p["y"], 1), round(p["z"], 1))


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
    expect(find_item(item_id) == 0, item_id + " 没有换到快捷栏")


def open_and_take(rel, face, slot_i, item_id):
    x, y, z = world_pos(rel)
    look(at=(x + 0.5, y + 0.5, z + 0.5))
    use_block(x, y, z, face=face)
    wait_screen(timeout=5)
    menu = screen()
    print(menu)
    got = menu.slot(slot_i)
    expect(got.get("id") == item_id, "槽位 %d 期望 %s，实际 %s" % (slot_i, item_id, got))
    slot(slot_i, "quick_move")
    close()
    wait_closed()
    expect(find_item(item_id) >= 0, "取出后背包里没有 " + item_id)
    print("取出", item_id)


def unlock(rel, face, old_id, chat_text):
    x, y, z = world_pos(rel)
    before = block_id(x, y, z)
    expect(before == old_id, "锁不在预期位置：%s 实际 %s" % (old_id, before))
    since = state().get("chat_count", 0)
    look(at=(x + 0.5, y + 0.5, z + 0.5))
    use_block(x, y, z, face=face)
    frames(10)
    now = block_id(x, y, z)
    print("打开", old_id, "->", now)
    expect(now != old_id, old_id + " 没有打开，还是 " + now)
    assert_chat(chat_text, timeout=3, since=since)
    screenshot(old_id.split(":")[-1])


ensure_world("saphyra_flow")
command("gamemode survival @s")
command("difficulty peaceful")
command("gamerule doMobSpawning false")
command("gamerule doDaylightCycle false")
command("gamerule keepInventory true")
command("time set day")
command("effect give @s minecraft:resistance 1000000 4 true")
command("effect give @s minecraft:saturation 1000000 20 true")
command("effect give @s minecraft:night_vision 1000000 0 true")
command("clear @s")

command("tp @s %d %d %d" % (OX + 11, OY + 2, OZ + 23))
command("forceload add %d %d %d %d" % (OX, OZ, OX + 3, OZ + 3))
placed = c.call(
    "command",
    command="place template saphyra_reborn:church_of_the_oath_pus %d %d %d" % (OX, OY, OZ),
    timeout=180,
)
print("放置教堂", placed.get("output"))
frames(20)

barrel = world_pos((32, 16, 21))
expect(block_id(*barrel) == "minecraft:barrel", "教堂里没有钥匙木桶，实际 " + block_id(*barrel))

# 阶梯钥匙在二层木桶，对应一层的雾（阶梯）
move_along("to_key1")
screenshot("at_key1_barrel")
open_and_take((32, 16, 21), "west", 15, "saphyra_reborn:key_1")
hold("saphyra_reborn:key_1")
move_along("to_steps")
unlock((15, 1, 37), "east", "saphyra_reborn:locked_fog_steps", "屏障已破碎")

# 匕首钥匙在一层箱子，对应顶层的雾（匕首）
move_along("to_key2")
screenshot("at_key2_chest")
open_and_take((45, 1, 29), "up", 3, "saphyra_reborn:key_2")
hold("saphyra_reborn:key_2")
move_along("to_knife")
unlock((4, 27, 28), "north", "saphyra_reborn:locked_fog_knife", "屏障已破碎")

# 邪异之门钥匙在顶层箱子，对应一层的鲜血首领之门
move_along("to_warrior")
screenshot("at_warrior_chest")
open_and_take((37, 26, 23), "up", 13, "saphyra_reborn:warrior_key")
hold("saphyra_reborn:warrior_key")
move_along("to_door")
unlock((14, 1, 23), "west", "saphyra_reborn:boss_door_1", "门开了")

print("教堂内箱子钥匙 -> 对应的锁：通过")
