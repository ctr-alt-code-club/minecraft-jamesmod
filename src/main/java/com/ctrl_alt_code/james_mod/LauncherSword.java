package com.ctrl_alt_code.james_mod;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;

public class LauncherSword extends SwordItem {
    
    public LauncherSword(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Call the parent method to apply normal damage
        boolean result = super.hurtEnemy(stack, target, attacker);
        
        // Launch the target into the air
        // Get the target's current velocity
        Vec3 currentVelocity = target.getDeltaMovement();
        
        // Calculate launch power based on entity size (weight)
        // Get the entity's bounding box dimensions
        float width = target.getBbWidth();
        float height = target.getBbHeight();
        
        // Calculate a "weight" factor based on volume (width * width * height)
        // Larger entities have more volume and are "heavier"
        float volume = width * width * height;
        
        // Base launch power
        double baseLaunchPower = 2.0;
        
        // Calculate final launch power inversely proportional to volume
        // Smaller entities (low volume) get launched higher
        // Larger entities (high volume) get launched less
        // We use a divisor to prevent extremely small values
        double launchPower = baseLaunchPower / (1.0 + volume);
        
        // Ensure minimum launch power so even large entities get some lift
        launchPower = Math.max(launchPower, 0.3);
        
        // Set the new velocity with upward force
        target.setDeltaMovement(currentVelocity.x, launchPower, currentVelocity.z);
        
        // Make sure the velocity change is applied
        target.hasImpulse = true;
        
        return result;
    }
}
