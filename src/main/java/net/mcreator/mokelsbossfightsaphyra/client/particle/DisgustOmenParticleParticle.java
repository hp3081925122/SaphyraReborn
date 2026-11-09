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

public class DisgustOmenParticleParticle
extends SingleQuadParticle {
    private final SpriteSet spriteSet;

    public static DisgustOmenParticleParticleProvider provider(SpriteSet spriteSet) {
        return new DisgustOmenParticleParticleProvider(spriteSet);
    }

    protected DisgustOmenParticleParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z, spriteSet.first());
        this.spriteSet = spriteSet;
        this.setSize(0.8f, 0.8f);
        this.quadSize *= 4.0f;
        this.lifetime = Math.max(1, 40 + (this.random.nextInt(80) - 40));
        this.gravity = 0.0f;
        this.hasPhysics = false;
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

    public static class DisgustOmenParticleParticleProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public DisgustOmenParticleParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, net.minecraft.util.RandomSource random) {
            return new DisgustOmenParticleParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}




















