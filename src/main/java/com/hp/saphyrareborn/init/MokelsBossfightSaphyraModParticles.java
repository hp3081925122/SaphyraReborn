package com.hp.saphyrareborn.init;

import com.hp.saphyrareborn.client.particle.DisgustOmenParticleParticle;
import com.hp.saphyrareborn.client.particle.GhostlyFlameParticleParticle;
import com.hp.saphyrareborn.client.particle.GoldenParticleParticle;
import com.hp.saphyrareborn.client.particle.LongGhostlyFlameParticleParticle;
import com.hp.saphyrareborn.client.particle.PukeParticleParticle;
import com.hp.saphyrareborn.client.particle.PurpleFlameParticleParticle;
import com.hp.saphyrareborn.client.particle.RedfireParticleParticle;
import com.hp.saphyrareborn.client.particle.RedflameParticleParticle;
import com.hp.saphyrareborn.init.MokelsBossfightSaphyraModParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT})
public class MokelsBossfightSaphyraModParticles {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.GHOSTLY_FLAME_PARTICLE.get(), GhostlyFlameParticleParticle::provider);
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.PURPLE_FLAME_PARTICLE.get(), PurpleFlameParticleParticle::provider);
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.REDFLAME_PARTICLE.get(), RedflameParticleParticle::provider);
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.REDFIRE_PARTICLE.get(), RedfireParticleParticle::provider);
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.PUKE_PARTICLE.get(), PukeParticleParticle::provider);
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.DISGUST_OMEN_PARTICLE.get(), DisgustOmenParticleParticle::provider);
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.GOLDEN_PARTICLE.get(), GoldenParticleParticle::provider);
        event.registerSpriteSet((ParticleType)MokelsBossfightSaphyraModParticleTypes.LONG_GHOSTLY_FLAME_PARTICLE.get(), LongGhostlyFlameParticleParticle::provider);
    }
}
