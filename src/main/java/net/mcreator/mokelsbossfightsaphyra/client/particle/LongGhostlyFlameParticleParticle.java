/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.particle.SingleQuadParticle
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.neoforged.api.distmarker.Dist
 */
package com.hp.saphyrareborn.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class LongGhostlyFlameParticleParticle
extends SingleQuadParticle {
    private final SpriteSet spriteSet;

    public static LongGhostlyFlameParticleParticleProvider provider(SpriteSet spriteSet) {
        return new LongGhostlyFlameParticleParticleProvider(spriteSet);
    }

    protected LongGhostlyFlameParticleParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet.first());
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 0.6f;
        this.lifetime = Math.max(1, 30 + (this.random.nextInt(40) - 20));
        this.gravity = 0.0f;
        this.hasPhysics = true;
        this.xd = vx * 1.0;
        this.yd = vy * 1.0;
        this.zd = vz * 1.0;
    }

    protected int getLightCoords(float partialTick) {
        return 0xF000F0;
    }

    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public void tick() {
        super.tick();
    }

    public static class LongGhostlyFlameParticleParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public LongGhostlyFlameParticleParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, net.minecraft.util.RandomSource random) {
            return new LongGhostlyFlameParticleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}




















