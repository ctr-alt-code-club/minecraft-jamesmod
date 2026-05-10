package com.ctrl_alt_code.james_mod;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * PoisonFrog - A hostile jumping mob that applies poison on attack
 * Similar to a frog but with aggressive behavior and poison attacks
 */
public class PoisonFrog extends Monster {
    
    public PoisonFrog(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5; // Experience points dropped when killed
    }

    @Override
    protected void registerGoals() {
        // Priority 0: Float in water (highest priority)
        this.goalSelector.addGoal(0, new FloatGoal(this));
        
        // Priority 1: Leap at target (frog-like jumping behavior)
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4F));
        
        // Priority 2: Melee attack
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        
        // Priority 3: Random strolling
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        
        // Priority 4: Look at player
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        
        // Priority 5: Random looking around
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        
        // Target goals
        // Priority 1: Retaliate when hurt
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        
        // Priority 2: Target nearest player
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    /**
     * Define the attributes for the PoisonFrog
     * Sets health, movement speed, attack damage, etc.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)        // 8 hearts (same as zombie)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)    // Slightly faster than zombie (0.23)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)      // 1.5 hearts damage
                .add(Attributes.FOLLOW_RANGE, 32.0D)      // How far it can detect targets
                .add(Attributes.ARMOR, 2.0D);             // Small amount of armor
    }

    /**
     * Called when the mob successfully attacks a target
     * Applies poison effect to the target
     */
    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean success = super.doHurtTarget(target);
        
        if (success && target instanceof LivingEntity livingTarget) {
            // Apply poison effect for 5 seconds (100 ticks) at level 1 (level 0 in code)
            livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
        }
        
        return success;
    }

    /**
     * Returns the ambient sound the mob makes
     * TODO: Add custom sound later
     */
    @Override
    protected net.minecraft.sounds.SoundEvent getAmbientSound() {
        // Using frog sound as placeholder - you can create custom sounds later
        return net.minecraft.sounds.SoundEvents.FROG_AMBIENT;
    }

    /**
     * Returns the sound played when the mob is hurt
     */
    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource damageSource) {
        return net.minecraft.sounds.SoundEvents.FROG_HURT;
    }

    /**
     * Returns the sound played when the mob dies
     */
    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() {
        return net.minecraft.sounds.SoundEvents.FROG_DEATH;
    }

    /**
     * Returns the volume of the mob's sounds
     */
    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }
}
