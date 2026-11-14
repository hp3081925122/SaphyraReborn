# 测试把人传到了基岩下面，掉进虚空。重生后把结局走完。
import time

PREMEMBER = "saphyra_reborn:puppet_of_yiva_remembrance"
SWORD = "saphyra_reborn:spirit_rune_sword"


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


current = state().get("screen") or {}
if "Death" in str(current.get("type", "")):
    click("重生")
    frames(40)
print("重生后", player()["x"], player()["y"], player()["z"], "记忆", find_item(PREMEMBER))
command("tp @s 16 -58 16")
command("effect give @s minecraft:resistance 1000000 255 true")
command("effect give @s minecraft:slow_falling 30 0 true")
frames(20)
print("莉娜拉", entities(64, "saphyra_reborn:linara_clock_tower"))
print("附近物品数", len(entities(32, "minecraft:item")))
if find_item(PREMEMBER) < 0:
    command("execute as @e[type=item,distance=..80] run tp @s @s")
    frames(10)
    command("tp @e[type=item,distance=..80] @s")
    frames(20)
print("捡起后记忆", find_item(PREMEMBER), "x", count_item(PREMEMBER) if find_item(PREMEMBER) >= 0 else 0)
found = entities(80, "saphyra_reborn:linara_clock_tower")
if not found:
    print("莉娜拉不在附近，等她的对话结束")
else:
    ent = found[0]
    command("tp @s %.1f %.1f %.1f" % (float(ent["x"]) + 1.5, float(ent["y"]), float(ent["z"])))
    look(at=(float(ent["x"]), float(ent["y"]) + 1.5, float(ent["z"])))
    frames(20)
    if find_item(PREMEMBER) >= 0:
        idx = find_item(PREMEMBER)
        if idx <= 8:
            select(idx)
        use_entity(type="saphyra_reborn:linara_clock_tower", radius=8)
        frames(20)
        print("交出记忆后剩余", count_item(PREMEMBER))

def wait_sword():
    started = time.time()
    def check():
        if find_item(SWORD) >= 0:
            return True
        if time.time() - started > 2:
            command("tp @e[type=item,distance=..12] @s")
        return find_item(SWORD) >= 0
    wait_until(check, 20, interval=0.4, desc=SWORD)

if find_item(PREMEMBER) < 0 and not entities(80, "saphyra_reborn:linara_clock_tower"):
    print("记忆掉进虚空，莉娜拉也不在了。召唤和她的出现已经发生过。")
else:
    try:
        wait_sword()
        print("拿到符文剑", count_item(SWORD))
        screenshot("ending_rewards")
    except Exception as error:
        print("结局物品", error)
        print("附近实体", entities(16)[:8])
