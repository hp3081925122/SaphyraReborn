package com.hp.saphyrareborn.procedures;

import java.util.List;
import com.hp.saphyrareborn.MokelsBossfightSaphyraMod;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModMobEffects;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class ManicEffectStartedProcedure {
    
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) {
            return;
        }
        if (new Object(){

            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayer) {
                    ServerPlayer _serverPlayer = (ServerPlayer)_ent;
                    return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL;
                }
                if (_ent.level().isClientSide() && _ent instanceof Player) {
                    Player _player = (Player)_ent;
                    return Boolean.TRUE.equals(net.minecraftforge.fml.DistExecutor.unsafeCallWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT, () -> () -> com.hp.saphyrareborn.client.SaphyraClientBridge.isClientGameMode(_player, GameType.SURVIVAL)));
                }
                return false;
            }
        }.checkGamemode(entity)) {
            ServerLevel _level1;
            LivingEntity _entity1;
            if (entity instanceof Player) {
                com.hp.saphyrareborn.network.PlayerAnimationPayload.send(entity, "manic");
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.STAY_STILL.get(), 80, 1, false, false));
            }
            if (entity instanceof LivingEntity && !(_entity1 = (LivingEntity)entity).level().isClientSide()) {
                _entity1.addEffect(new MobEffectInstance(MokelsBossfightSaphyraModMobEffects.COOLDOWN.get(), 80, 1, false, false));
            }
            MokelsBossfightSaphyraMod.queueServerWork(30, () -> {
                if (world instanceof ServerLevel) {
                    ServerLevel _level2 = (ServerLevel)world;
                    ((ServerLevel)_level2).sendParticles((ParticleOptions)((SimpleParticleType)MokelsBossfightSaphyraModParticleTypes.GOLDEN_PARTICLE.get()), entity.getX(), 1.0 + entity.getY(), entity.getZ(), 100, 0.2, 0.2, 0.2, 0.25);
                }
            });
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, _level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @p times 20 20 20");
            }
            if (world instanceof ServerLevel) {
                _level1 = (ServerLevel)world;
                _level1.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(entity.getX(), entity.getY(), entity.getZ()), Vec2.ZERO, _level1, 4, "", (Component)Component.literal((String)""), _level1.getServer(), null).withSuppressedOutput(), "/title @p title {\"translate\":\"text.saphyra_reborn.title.manic\",\"fallback\":\"Manic\",\"color\":\"yellow\"}");
            }
        }
    }
}
