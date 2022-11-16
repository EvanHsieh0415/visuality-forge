package com.github.channelingmc.visuality.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.github.channelingmc.visuality.config.VisualityConfig;
import com.github.channelingmc.visuality.registry.VisualityParticles;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Mixin(AmethystClusterBlock.class)
public abstract class AmethystClusterBlockMixin extends AmethystBlock implements SimpleWaterloggedBlock {
    
    private int visuality$height;

    protected AmethystClusterBlockMixin(BlockBehaviour.Properties props) {
        super(props);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(int i, int j, Properties props, CallbackInfo ci) {
        this.visuality$height = i;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if(VisualityConfig.SPARKLE_ENABLED.get()) {
            if(visuality$height > 5 && random.nextFloat() > 0.5) {
                double x = pos.getX() + random.nextDouble();
                double y = pos.getY() + random.nextDouble();
                double z = pos.getZ() + random.nextDouble();
                level.addParticle(VisualityParticles.SPARKLE.get(), x, y, z, 0, 0, 0);
            }
        }
    }

}
