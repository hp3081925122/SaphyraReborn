# 第一次掉落和莉娜拉掉进了虚空。这里只重放交还记忆，奖励仍走她自己的过程。
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


command("tp @s 16 -58 16")
command("give @s saphyra_reborn:puppet_of_yiva_remembrance")
command("summon saphyra_reborn:linara_clock_tower 18 -58 16")
frames(30)
found = entities(8, "saphyra_reborn:linara_clock_tower")
print(found[:1])
expect(found, "没有召唤出莉娜拉")
ent = found[0]
look(at=(float(ent["x"]), float(ent["y"]) + 1.5, float(ent["z"])))
idx = find_item(PREMEMBER)
expect(idx >= 0, "没有记忆")
select(idx if idx <= 8 else 0)
frames(15)
use_entity(id=ent["id"])
frames(20)
print("记忆剩余", count_item(PREMEMBER))
expect(count_item(PREMEMBER) == 0, "莉娜拉没有收下记忆")
started = time.time()

def check():
    if find_item(SWORD) >= 0:
        return True
    if time.time() - started > 2:
        command("tp @e[type=item,distance=..8] @s")
    return find_item(SWORD) >= 0

wait_until(check, 20, interval=0.4, desc=SWORD)
print("拿到", SWORD, count_item(SWORD))
screenshot("ending_rewards")
