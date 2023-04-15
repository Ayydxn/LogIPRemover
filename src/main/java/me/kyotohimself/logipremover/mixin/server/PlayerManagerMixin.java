package me.kyotohimself.logipremover.mixin.server;

import me.kyotohimself.logipremover.config.LogIPRemoverConfig;
import net.minecraft.server.PlayerManager;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin
{
    @Redirect(at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;[Ljava/lang/Object;)V"), method = "onPlayerConnect")
    public void removePlayerPublicIPAddressFromJoinServerLogMessage(Logger instance, String s, Object[] objects)
    {
        if (LogIPRemoverConfig.getConfigInstance().shouldHidePlayerIPs())
        {
            instance.info("{}[IP address hidden by Log IP Remover] logged in with entity id {} at ({}, {}, {})", objects[0], objects[2], objects[3], objects[4], objects[5]);
        }
        else
        {
            instance.info("{}[{}] logged in with entity id {} at ({}, {}, {})", objects[0], objects[1], objects[2], objects[3], objects[4], objects[5]);
        }
    }
}
