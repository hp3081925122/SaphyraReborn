# 完整剧情：在可见窗口里放置四座建筑，走路点箱子和 NPC，不等待的地方不发物品。
import time

CLOCK = (0, -60, 0)
TOWER = (240, -60, 0)
CHURCH = (480, -60, 0)
OBS = (720, -60, 0)

SIGIL = "saphyra_reborn:sigil_of_the_red_sun"
BROKEN = "saphyra_reborn:broken_sigil_of_the_red_sun"
KEY_H1 = "saphyra_reborn:hallowed_key_1"
INVITE = "saphyra_reborn:invitation_to_the_hallowed_tower"
KEY4 = "saphyra_reborn:key_4"
STAGE2 = "saphyra_reborn:sigil_of_the_red_sun_stage_2"
REMEMBER = "saphyra_reborn:nameless_gatekeeper_remembrance"
KEY_H2 = "saphyra_reborn:hallowed_key_2"
BOOK = "saphyra_reborn:the_first_god"
EYE = "saphyra_reborn:manic_eye"
BAG = "saphyra_reborn:sinister_bag"
SKEY = "saphyra_reborn:saphyra_key"
KEY3 = "saphyra_reborn:key_3"
BROKEN2 = "saphyra_reborn:broken_sigil_of_the_red_sun_2"
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
    frames(8)
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
    expect(find_item(item_id) == 0, item_id + " 没有换到快捷栏")


def dismiss():
    if screen() is not None:
        close()
        frames(5)


def click_block(origin, stand, target, face):
    dismiss()
    stand_at(origin, stand, target)
    x, y, z = world_pos(origin, target)
    use_block(x, y, z, face=face)
    frames(12)
    return x, y, z


def unlock(origin, stand, target, face, old_id, chat_text=None):
    stand_at(origin, stand, target)
    x, y, z = world_pos(origin, target)
    wait_until(lambda: block_id(x, y, z) != "minecraft:void_air", 8, interval=0.25, desc="加载方块")
    before = block_id(x, y, z)
    expect(before == old_id, "锁不在预期位置：%s 实际 %s" % (old_id, before))
    since = state().get("chat_count", 0)
    click_block(origin, stand, target, face)
    frames(10)
    now = block_id(x, y, z)
    print(old_id, "->", now)
    expect(now != old_id, old_id + " 没有打开，还是 " + now)
    if chat_text:
        assert_chat(chat_text, timeout=3, since=since)
    screenshot(old_id.split(":")[-1])
    return now


def open_and_take(origin, stand, target, face, slot_i, item_id):
    click_block(origin, stand, target, face)
    wait_screen(timeout=5)
    menu = screen()
    print(menu)
    got = menu.slot(slot_i)
    expect(got.get("id") == item_id, "槽位 %d 期望 %s，实际 %s" % (slot_i, item_id, got))
    slot(slot_i, "quick_move")
    frames(5)
    close()
    wait_closed()
    expect(find_item(item_id) >= 0, "取出后背包里没有 " + item_id)
    print("取出", item_id)


def wait_item(item_id, timeout):
    print("等待", item_id, "最多", timeout, "秒", flush=True)
    started = time.time()

    def check():
        if time.time() - started > 2:
            command("tp @e[type=item,distance=..10] @s")
        return find_item(item_id) >= 0

    wait_until(check, timeout, interval=0.5, desc=item_id)
    print("拿到", item_id, "x", count_item(item_id))


def talk(etype, item=None):
    dismiss()
    if item:
        hold(item)
    frames(8)
    found = entities(12, etype)
    print(etype, found[:1])
    expect(found, "附近没有 " + etype)
    ent = found[0]
    if isinstance(ent, dict) and "x" in ent:
        look(at=(float(ent["x"]), float(ent["y"]) + 1.5, float(ent["z"])))
        frames(15)
    use_entity(type=etype, radius=12)
    frames(20)


def place(template, origin):
    x, y, z = origin
    command("tp @s %d %d %d" % (x + 8, y + 4, z + 8))
    frames(15)
    placed = c.call("command", command="place template saphyra_reborn:%s %d %d %d" % (template, x, y, z), timeout=180)
    print("放置", template, placed.get("output"))
    frames(30)


def calm(kinds):
    for kind in kinds:
        text = command("execute as @e[type=saphyra_reborn:%s] run data merge entity @s {NoAI:1b}" % kind)
        print("停住", kind, text)


def wake(kind):
    text = command("execute as @e[type=saphyra_reborn:%s,distance=..48,limit=1,sort=nearest] run data merge entity @s {NoAI:0b}" % kind)
    print("唤醒", kind, text)


def wait_entity(etype, timeout):
    print("等待实体", etype, flush=True)
    wait_until(lambda: len(entities(24, etype)) > 0, timeout, interval=0.5, desc=etype)
    found = entities(24, etype)
    ent = found[0]
    if isinstance(ent, dict) and "x" in ent:
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
    return js(
        "var Registries = Java.loadClass('net.minecraft.core.registries.Registries');"
        "var ResourceKey = Java.loadClass('net.minecraft.resources.ResourceKey');"
        "var ResourceLocation = Java.loadClass('net.minecraft.resources.ResourceLocation');"
        "var BlockPos = Java.loadClass('net.minecraft.core.BlockPos');"
        "var key = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse('saphyra_reborn:fractured_clock_tower'));"
        "var tower = level.registryAccess().registryOrThrow(Registries.STRUCTURE).get(key);"
        "if (tower == null) 'missing';"
        "else String(level.structureManager().getStructureAt(new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()), tower).isValid())"
    )


window("show")
ensure_world("saphyra_watch")
window("show")
stage("进入世界")
command("gamemode survival @s")
command("difficulty normal")
command("gamerule doMobSpawning false")
command("gamerule doDaylightCycle false")
command("gamerule doWeatherCycle false")
command("gamerule keepInventory true")
command("gamerule mobGriefing false")
command("gamerule sendCommandFeedback false")
command("time set day")
command("weather clear")
command("effect give @s minecraft:resistance 1000000 255 true")
command("effect give @s minecraft:saturation 1000000 20 true")
command("effect give @s minecraft:night_vision 1000000 0 true")
frames(20)
wait_until(lambda: find_item(SIGIL) >= 0, 15, interval=0.4, desc="登录赠送赤日印记")
print("登录拿到赤日印记")
screenshot("login_sigil")

stage("破碎钟楼")
place("fractured_clocktower", CLOCK)
oddment = world_pos(CLOCK, (18, 33, 16))
expect(block_id(*oddment) == "saphyra_reborn:oddment_stage_2", "钟楼奇物不在预期位置：" + block_id(*oddment))
hold(SIGIL)
before = count_item(SIGIL)
click_block(CLOCK, (19, 33, 16), (18, 33, 16), "east")
frames(20)
expect(count_item(SIGIL) == before - 1, "第一枚印记没有消耗")
expect(find_item(BROKEN) >= 0, "没有拿到破碎印记")
expect(block_id(*oddment) == "minecraft:air", "奇物没有消失")
wait_entity("saphyra_reborn:linara", 8)
wait_item(KEY_H1, 50)
wait_item(INVITE, 15)
screenshot("linara_invitation")

stage("三座圣塔")
place("the_three_hallowed_towers", TOWER)
hold(KEY_H1)
unlock(TOWER, (19, 2, 11), (20, 2, 11), "west", "saphyra_reborn:locked_fog_worship", "屏障已破碎")
stand_at(TOWER, (13, 3, 11), (11, 3, 11))
talk("saphyra_reborn:saphyra_dialog_1", INVITE)
expect(find_item(INVITE) < 0, "邀请函没有交给萨菲拉")
wait_entity("saphyra_reborn:saphyra_dialog_2", 8)
wait_item(KEY4, 55)
screenshot("saphyra_key4")

stage("脓誓教堂")
place("church_of_the_oath_pus", CHURCH)
calm(["scorched", "infested_witch", "nameless_spawn"])
open_and_take(CHURCH, (33, 16, 21), (32, 16, 21), "west", 15, "saphyra_reborn:key_1")
hold("saphyra_reborn:key_1")
unlock(CHURCH, (16, 1, 37), (15, 1, 37), "east", "saphyra_reborn:locked_fog_steps", "屏障已破碎")
open_and_take(CHURCH, (45, 2, 29), (45, 1, 29), "up", 3, "saphyra_reborn:key_2")
hold("saphyra_reborn:key_2")
unlock(CHURCH, (4, 27, 27), (4, 27, 28), "north", "saphyra_reborn:locked_fog_knife", "屏障已破碎")
open_and_take(CHURCH, (4, 27, 13), (4, 27, 12), "south", 13, EYE)
open_and_take(CHURCH, (4, 28, 17), (3, 28, 17), "east", 0, BOOK)
open_and_take(CHURCH, (37, 27, 23), (37, 26, 23), "up", 13, "saphyra_reborn:warrior_key")
hold(KEY4)
unlock(CHURCH, (43, 1, 23), (42, 1, 23), "east", "saphyra_reborn:locked_fog_hatered", "屏障已破碎")
hold("saphyra_reborn:warrior_key")
unlock(CHURCH, (13, 1, 23), (14, 1, 23), "west", "saphyra_reborn:boss_door_1", "门开了")
click_block(CHURCH, (13, 1, 23), (14, 1, 23), "west")
door = world_pos(CHURCH, (14, 1, 23))
wait_until(lambda: "door_3" in block_id(*door), 20, interval=0.5, desc="boss_door_3")
print("大门", block_id(*door))
screenshot("boss_door_open")

stage("里奥尔雕像")
statue = world_pos(CHURCH, (10, 1, 23))
click_block(CHURCH, (11, 1, 23), (10, 1, 23), "east")
frames(15)
activated = block_id(*statue)
print("雕像", activated)
expect("aktiv" in activated, "雕像没有激活：" + activated)
hold(BROKEN)
before = count_item(BROKEN)
click_block(CHURCH, (11, 1, 23), (10, 1, 23), "east")
frames(15)
expect(count_item(BROKEN) == before - 1, "破碎印记没有交给雕像")
expect(find_item(STAGE2) >= 0, "没有拿到第二阶段印记")
screenshot("lyior_statue")

stage("无名者")
stand_at(CHURCH, (27, 1, 23), (29, 1, 23))
wake("nameless_spawn")
wait_entity("saphyra_reborn:nameless", 25)
frames(40)
command("kill @e[type=saphyra_reborn:nameless,distance=..32]")
wait_item(REMEMBER, 20)
print("无名者再给一把憎恨钥匙" if find_item(KEY4) >= 0 else "死亡没有掉憎恨钥匙")
screenshot("nameless_down")

stage("锁链与玛琳")
stand_at(TOWER, (13, 3, 11), (11, 3, 11))
talk("saphyra_reborn:saphyra_dialog_2", REMEMBER)
expect(find_item(REMEMBER) >= 0, "给萨菲拉看记忆时被提前收走了")
wait_entity("saphyra_reborn:saphyra_dialog_3", 8)
wait_item(KEY_H2, 50)
expect(count_item(KEY_H2) >= 2, "锁链钥匙不足两把，实际 %d" % count_item(KEY_H2))
hold(KEY_H2)
unlock(TOWER, (20, 16, 24), (21, 16, 24), "west", "saphyra_reborn:locked_fog_chain", "屏障已破碎")
stand_at(TOWER, (11, 11, 8), (12, 11, 8))
talk("saphyra_reborn:marlene_human_form", BOOK)
expect(find_item(BOOK) < 0, "人造神之传说没有交给玛琳")
wait_entity("saphyra_reborn:marlene_human_form_dialog_2", 8)
talk("saphyra_reborn:marlene_human_form_dialog_2", REMEMBER)
expect(find_item(REMEMBER) < 0, "无名者的记忆没有交给玛琳")
wait_item(BAG, 12)
wait_item(SKEY, 30)
screenshot("marlene_key")
open_bag(BAG, "saphyra_reborn:sword_of_the_nameless_gatekeeper")

stage("圣塔奇物")
stand_at(TOWER, (12, 34, 34), (11, 34, 34))
hold(STAGE2)
before = count_item(STAGE2)
oddment2 = world_pos(TOWER, (11, 34, 34))
click_block(TOWER, (12, 34, 34), (11, 34, 34), "east")
frames(20)
left = count_item(STAGE2)
print("第二阶段印记", before, "->", left, "破碎2", count_item(BROKEN2))
expect(left == before - 1, "第二阶段印记没有从背包扣除，还剩 %d" % left)
expect(find_item(BROKEN2) >= 0, "没有拿到第二枚破碎印记")
expect(block_id(*oddment2) == "minecraft:air", "圣塔奇物没有消失")
wait_entity("saphyra_reborn:linara_hallowed_tower", 8)
wait_item(KEY3, 75)
screenshot("linara_key3")
talk("saphyra_reborn:linara_hallowed_tower", EYE)
expect(find_item(EYE) < 0, "躁狂之眼没有交给莉娜拉")
wait_entity("saphyra_reborn:linara_hallowed_tower_2", 8)
screenshot("linara_quest")

stage("恋人雾锁")
hold(KEY3)
unlock(CHURCH, (7, 27, 16), (6, 27, 16), "east", "saphyra_reborn:locked_fog_lovers", "屏障已破碎")

stage("观星台")
place("observatory", OBS)
calm(["saphyra_spawn"])
hold(SKEY)
unlock(OBS, (42, 1, 22), (43, 1, 22), "west", "saphyra_reborn:saphyra_door_1")
door2 = world_pos(OBS, (43, 1, 22))
print("观星门", block_id(*door2))
click_block(OBS, (42, 1, 22), (43, 1, 22), "west")
wait_until(lambda: "door_3" in block_id(*door2), 20, interval=0.5, desc="saphyra_door_3")
print("观星门开", block_id(*door2))
stand_at(OBS, (22, 2, 24), (22, 1, 22))
wake("saphyra_spawn")
wait_entity("saphyra_reborn:witchboss", 40)
frames(50)
command("kill @e[type=saphyra_reborn:witchboss,distance=..32]")
wait_entity("saphyra_reborn:mothboss", 15)
frames(40)
since = state().get("chat_count", 0)
command("kill @e[type=saphyra_reborn:mothboss,distance=..32]")
wait_item(SREMEMBER, 30)
assert_chat("第一章结束", timeout=8, since=since)
screenshot("saphyra_down")

stage("第三阶段印记")
stand_at(TOWER, (11, 11, 8), (12, 11, 8))
talk("saphyra_reborn:marlene_human_form_dialog_2", SREMEMBER)
expect(find_item(SREMEMBER) < 0, "萨菲拉的记忆没有交给玛琳")
wait_item(BBAG, 12)
wait_item(STAGE3, 25)
screenshot("sigil_stage3")
open_bag(BBAG, "saphyra_reborn:witch_staff")

stage("伊瓦的傀儡")
stand_at(CLOCK, (19, 33, 16), (18, 33, 16))
frames(10)
print("钟楼结构判定", inside_clock_tower())
if inside_clock_tower() != "true":
    placed = c.call("command", command="place structure saphyra_reborn:fractured_clock_tower %d %d %d" % CLOCK, timeout=180)
    print("注册钟楼结构", placed.get("output"))
    frames(40)
    stand_at(CLOCK, (19, 33, 16), (18, 33, 16))
    print("再次判定", inside_clock_tower())
hold(STAGE3)
before = count_item(STAGE3)
look(yaw=90, pitch=0)
frames(15)
use_item()
frames(30)
if count_item(STAGE3) == before:
    print("模板没有被认成结构，改在结构起点使用")
    command("tp @s %d %d %d" % (CLOCK[0] + 8, CLOCK[1] + 8, CLOCK[2] + 8))
    frames(15)
    print("起点判定", inside_clock_tower())
    hold(STAGE3)
    look(yaw=90, pitch=0)
    use_item()
    frames(30)
expect(count_item(STAGE3) == before - 1, "第三阶段印记没有消耗，结构判定 " + str(inside_clock_tower()))
wait_entity("saphyra_reborn:puppet_of_yiva_nemor", 8)
frames(50)
command("kill @e[type=saphyra_reborn:puppet_of_yiva_nemor,distance=..48]")
wait_item(PREMEMBER, 15)
wait_entity("saphyra_reborn:linara_clock_tower", 15)
talk("saphyra_reborn:linara_clock_tower", PREMEMBER)
expect(find_item(PREMEMBER) < 0, "傀儡的记忆没有交给钟楼的莉娜拉")
wait_item(SWORD, 20)
screenshot("ending_rewards")
print("结局奖励已拿到")
