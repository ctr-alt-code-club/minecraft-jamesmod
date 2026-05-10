package com.ctrl_alt_code.james_mod;

import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for the PoisonFrog entity
 * Uses a slime model as a base (cube-like jumping creature)
 * You can create a custom model later using Blockbench
 */
public class PoisonFrogRenderer extends MobRenderer<PoisonFrog, SlimeModel<PoisonFrog>> {
    
    // Custom texture location for the poison frog
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            jamesmod.MODID, "textures/entity/poison_frog.png");

    public PoisonFrogRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(PoisonFrog entity) {
        return TEXTURE;
    }
}
