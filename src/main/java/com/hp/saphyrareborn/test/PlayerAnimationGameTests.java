package com.hp.saphyrareborn.test;

import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModItems;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.procedures.ManicEffectStartedProcedure;
import com.hp.saphyrareborn.procedures.RuleOfWisdomrightclickedProcedure;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

/** Server-side checks for the manic and wisdom player animations. */
@EventBusSubscriber(modid = MokelsBossfightSaphyraMod.MODID)
public final class PlayerAnimationGameTests {
    private PlayerAnimationGameTests() {
    }

    @SubscribeEvent
    public static void register(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> env = event.registerEnvironment(
                Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "plain"),
                new TestEnvironmentDefinition.AllOf());
        register(event, env, "manic_start", PlayerAnimationGameTests::manicStart);
        register(event, env, "wisdom_use", PlayerAnimationGameTests::wisdomUse);
    }

    private static void register(RegisterGameTestsEvent event, Holder<TestEnvironmentDefinition<?>> env, String name, Consumer<GameTestHelper> body) {
        Identifier id = Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, name);
        TestData<Holder<TestEnvironmentDefinition<?>>> data = new TestData<>(env, Identifier.withDefaultNamespace("empty"), 100, 0, true);
        event.registerTest(id, new Body(data, body));
    }

    private static void manicStart(GameTestHelper helper) {
        ServerPlayer player = survival(helper);
        ManicEffectStartedProcedure.execute(helper.getLevel(), player);
        if (!player.hasEffect(MokelsBossfightSaphyraModMobEffects.STAY_STILL)) {
            helper.fail("manic did not apply stay_still");
            return;
        }
        if (!player.hasEffect(MokelsBossfightSaphyraModMobEffects.COOLDOWN)) {
            helper.fail("manic did not apply cooldown");
            return;
        }
        helper.succeed();
    }

    private static void wisdomUse(GameTestHelper helper) {
        ServerPlayer player = survival(helper);
        ItemStack stack = new ItemStack(MokelsBossfightSaphyraModItems.RULE_OF_HATERED.get());
        player.setItemSlot(EquipmentSlot.MAINHAND, stack);
        RuleOfWisdomrightclickedProcedure.execute(helper.getLevel(), player);
        if (!player.getCooldowns().isOnCooldown(stack)) {
            helper.fail("wisdom did not start the item cooldown");
            return;
        }
        if (!player.hasEffect(MokelsBossfightSaphyraModMobEffects.STAY_STILL)) {
            helper.fail("wisdom did not apply stay_still");
            return;
        }
        helper.succeed();
    }

    private static ServerPlayer survival(GameTestHelper helper) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        player.setGameMode(GameType.SURVIVAL);
        NetworkRegistry.configureMockConnection(player.connection.getConnection());
        return player;
    }

    /** Encodes as a normal function test so a joining client can read the registry. The body runs on the server. */
    private static final class Body extends FunctionGameTestInstance {
        private final Consumer<GameTestHelper> body;

        private Body(TestData<Holder<TestEnvironmentDefinition<?>>> info, Consumer<GameTestHelper> body) {
            super(ResourceKey.create(Registries.TEST_FUNCTION, Identifier.fromNamespaceAndPath(MokelsBossfightSaphyraMod.MODID, "inline")), info);
            this.body = body;
        }

        @Override
        public void run(GameTestHelper helper) {
            this.body.accept(helper);
        }
    }
}
