package committee.nova.mods.avaritia.mixin.client;

import committee.nova.mods.avaritia.client.render.RenderTypeDrawTracker;
import net.minecraft.client.renderer.rendertype.PreparedRenderType;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 26.2: {@code RenderType#draw(MeshData)} removed. Track PreparedRenderType -> RenderType
 * at prepare() so draw-time mixins can restore the source RenderType.
 */
@Mixin(RenderType.class)
public abstract class RenderTypeMixin {

    @Inject(method = "prepare", at = @At("RETURN"))
    private void avaritia$trackPreparedRenderType(CallbackInfoReturnable<PreparedRenderType> cir) {
        RenderTypeDrawTracker.register(cir.getReturnValue(), (RenderType) (Object) this);
    }
}
