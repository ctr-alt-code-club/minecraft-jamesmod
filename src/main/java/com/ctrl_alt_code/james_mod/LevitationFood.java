package com.ctrl_alt_code.james_mod;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LevitationFood extends Item {
    
    public LevitationFood(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // Call parent method to handle normal food consumption
        ItemStack result = super.finishUsingItem(stack, level, entity);
        
        // Only execute on server side to prevent desync
        if (!level.isClientSide && entity instanceof Player player) {
            // Restore full health
            player.setHealth(player.getMaxHealth());
            
            // Apply levitation effect for 10 seconds (200 ticks)
            // Levitation level 10 provides a strong upward force
            player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 10, false, true, true));
            
            // Optional: Add a small initial upward velocity boost for immediate effect
            Vec3 currentVelocity = player.getDeltaMovement();
            player.setDeltaMovement(currentVelocity.x, 1.5, currentVelocity.z);
            player.hasImpulse = true;
        }
        
        return result;
    }
}
