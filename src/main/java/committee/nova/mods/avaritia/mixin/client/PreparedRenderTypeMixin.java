package committee.nova.mods.avaritia.mixin.client;

import committee.nova.mods.avaritia.client.render.RenderTypeDrawTracker;
import committee.nova.mods.avaritia.client.shader.AvaritiaShaderUniforms;
import com.mojang.blaze3d.IndexType;
import com.mojang.blaze3d.buffers.GpuBuffer;
import net.minecraft.client.renderer.rendertype.PreparedRenderType;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 26.2 replacement for {@code RenderType#draw} active-type tracking.
 * Inject on the full {@code drawFromBuffer} overload (the ExecuteInfo overload delegates here).
 */
@Mixin(PreparedRenderType.class)
public abstract class PreparedRenderTypeMixin {

    @Inject(
            method = "drawFromBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/IndexType;III)V",
            at = @At("HEAD")
    )
    private void avaritia$setActiveRenderType(GpuBuffer vertexBuffer, GpuBuffer indexBuffer, IndexType indexType, int baseVertex, int firstIndex, int indexCount, CallbackInfo ci) {
        RenderType renderType = RenderTypeDrawTracker.get((PreparedRenderType) (Object) this);
        if (renderType != null) {
            AvaritiaShaderUniforms.setActiveRenderType(renderType);
        }
    }

    @Inject(
            method = "drawFromBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/IndexType;III)V",
            at = @At("TAIL")
    )
    private void avaritia$clearActiveRenderType(GpuBuffer vertexBuffer, GpuBuffer indexBuffer, IndexType indexType, int baseVertex, int firstIndex, int indexCount, CallbackInfo ci) {
        RenderType renderType = RenderTypeDrawTracker.get((PreparedRenderType) (Object) this);
        if (renderType != null) {
            AvaritiaShaderUniforms.clearActiveRenderType(renderType);
        }
    }
}
