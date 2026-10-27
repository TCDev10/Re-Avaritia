package committee.nova.mods.avaritia.client.render;

import net.minecraft.client.renderer.rendertype.PreparedRenderType;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * On Minecraft 26.2+, {@code RenderType#draw} was removed. Drawing only holds a
 * {@link PreparedRenderType}, so we register the mapping at {@code RenderType#prepare}
 * and look it up while drawing.
 */
public final class RenderTypeDrawTracker {
    private static final Map<PreparedRenderType, RenderType> RENDER_TYPES = new IdentityHashMap<>();

    private RenderTypeDrawTracker() {
    }

    public static void register(PreparedRenderType preparedRenderType, RenderType renderType) {
        RENDER_TYPES.put(preparedRenderType, renderType);
    }

    public static @Nullable RenderType get(PreparedRenderType preparedRenderType) {
        return RENDER_TYPES.get(preparedRenderType);
    }

    public static void clear() {
        RENDER_TYPES.clear();
    }
}
