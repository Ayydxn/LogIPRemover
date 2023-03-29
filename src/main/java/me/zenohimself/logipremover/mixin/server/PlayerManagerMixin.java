package me.zenohimself.logipremover.mixin.server;

import me.zenohimself.logipremover.config.LogIPRemoverConfig;
import net.minecraft.server.PlayerManager;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin
{
    @Redirect(at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;[Ljava/lang/Object;)V"), method = "onPlayerConnect", remap = false)
    public void removePlayerPublicIPAddressFromJoinServerLogMessage(Logger instance, String s, Object[] objects)
    {
        if (LogIPRemoverConfig.getConfigInstance().shouldHidePlayerIPs())
        {
            instance.info("{}[This player's public IP is hidden.] logged in with entity id {} at ({}, {}, {})", objects[0], objects[2], objects[3], objects[4], objects[5]);
        }
        else
        {
            instance.info("{}[{}] logged in with entity id {} at ({}, {}, {})", objects[0], objects[1], objects[2], objects[3], objects[4], objects[5]);
        }
    }
}
