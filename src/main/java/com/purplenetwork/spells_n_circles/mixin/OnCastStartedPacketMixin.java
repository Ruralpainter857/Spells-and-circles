package com.purplenetwork.spells_n_circles.mixin;

import com.purplenetwork.spells_n_circles.render.RenderManager;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.network.casting.OnCastStartedPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Mixin(OnCastStartedPacket.class)
public abstract class OnCastStartedPacketMixin {

    @Shadow
    @Final
    private UUID castingEntityId;

    @Shadow
    @Final
    private int spellLevel;

    @Shadow
    @Final
    private String spellId;

    @Inject(method = "handle", at = @At("TAIL"))
    private static void handle(OnCastStartedPacket packet, IPayloadContext context, CallbackInfo ci) {
        UUID castingEntityUUID = ((OnCastStartedPacketMixin) (Object) packet).castingEntityId;
        assert Minecraft.getInstance().level != null;
        Optional<Entity> castingEntity = StreamSupport.stream(Minecraft.getInstance().level.entitiesForRendering().spliterator(), false)
                .filter(entity -> entity.getUUID().equals(castingEntityUUID)).findFirst();
        int spellLevel = ((OnCastStartedPacketMixin) (Object) packet).spellLevel;
        String spellId = ((OnCastStartedPacketMixin) (Object) packet).spellId; // dw it doesn't produce any error
        AbstractSpell spell = SpellRegistry.getSpell(spellId);
        if (!(castingEntity.get() instanceof LivingEntity castingLivingEntity)) return;
        RenderManager.buildAndAddCircle(spell.getCastTime(spellLevel) + 35,
                spell.getTargetingColor(), castingLivingEntity, spellLevel);
    }
}
