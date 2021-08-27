package ru.pinkgoosik.visuality.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class FeatherParticle extends AbstractSlowingParticle {

    private FeatherParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
        super(world, x, y, z, velX, velY, velZ);
        this.scale(0.7F + (float)world.random.nextInt(6) / 10);
        this.angle = prevAngle = random.nextFloat() * (float)(2 * Math.PI);

        this.velocityY = -0.25D;
        this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 7;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if(age == 1){
            this.velocityX = velocityX + (Math.random() * 2.0D - 1.0D) * 0.2D;
            this.velocityY = 0.3D + (double)random.nextInt(11) / 100;
            this.velocityZ = velocityZ + (Math.random() * 2.0D - 1.0D) * 0.2D;
        }else if(age <= 10){
            this.velocityY = velocityY - 0.05D;
        }
        if(this.onGround){
            this.setVelocity(0D, 0D, 0D);
            this.setPos(prevPosX, prevPosY + 0.1D, prevPosZ);
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
            FeatherParticle particle = new FeatherParticle(world, x, y, z, velX, velY, velZ);
            particle.setSprite(spriteProvider);
            return particle;
        }
    }
}
