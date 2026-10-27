package committee.nova.mods.avaritia.mixin.client;

import committee.nova.mods.avaritia.client.render.RenderTypeDrawTracker;
import committee.nova.mods.avaritia.client.shader.AvaritiaShaderUniforms;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 26.2: {@code RenderSystem#flipFrame} removed. End-of-frame cleanup hooks
 * {@code ensurePipelineModifiersEmpty} (called after each frame render).
 */
@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Inject(method = "bindDefaultUniforms", at = @At("TAIL"))
    private static void avaritia$bindUniforms(RenderPass renderPass, CallbackInfo ci) {
        AvaritiaShaderUniforms.bindIfAvaritia(renderPass);
    }

    @Inject(method = "ensurePipelineModifiersEmpty", at = @At("TAIL"))
    private static void avaritia$endFrame(CallbackInfo ci) {
        AvaritiaShaderUniforms.endFrame();
        RenderTypeDrawTracker.clear();
    }
}
