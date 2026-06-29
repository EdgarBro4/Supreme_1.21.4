/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.client.model;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.client.renderer.block.model.BakedQuad;

import java.util.Arrays;
import java.util.List;

/**
 * Transformer for {@link BakedQuad baked quads}.
 *
 * @see QuadTransformers
 */
public interface IQuadTransformer {
    int STRIDE = DefaultVertexFormat.f_85811_.m_86020_() / 4;
    int POSITION = findOffset(VertexFormatElement.f_336661_);
    int COLOR = findOffset(VertexFormatElement.f_336914_);
    int UV0 = findOffset(VertexFormatElement.f_336642_);
    int UV1 = findOffset(VertexFormatElement.f_337543_);
    int UV2 = findOffset(VertexFormatElement.f_337050_);
    int NORMAL = findOffset(VertexFormatElement.f_336839_);

    void processInPlace(BakedQuad quad);

    default void processInPlace(List<BakedQuad> quads) {
        for (BakedQuad quad : quads)
            processInPlace(quad);
    }

    default BakedQuad process(BakedQuad quad) {
        var copy = copy(quad);
        processInPlace(copy);
        return copy;
    }

    default List<BakedQuad> process(List<BakedQuad> inputs) {
        return inputs.stream().map(IQuadTransformer::copy).peek(this::processInPlace).toList();
    }

    default IQuadTransformer andThen(IQuadTransformer other) {
        return quad -> {
            processInPlace(quad);
            other.processInPlace(quad);
        };
    }

    private static BakedQuad copy(BakedQuad quad) {
        var vertices = quad.m_111303_();
        return new BakedQuad(Arrays.copyOf(vertices, vertices.length), quad.m_111305_(), quad.m_111306_(), quad.m_173410_(), quad.m_111307_(), quad.m_353735_(), quad.hasAmbientOcclusion());
    }

    private static int findOffset(VertexFormatElement element) {
        // Divide by 4 because we want the int offset
        var index = DefaultVertexFormat.f_85811_.m_338798_(element);
        return index < 0 ? -1 : index / 4;
    }
}
