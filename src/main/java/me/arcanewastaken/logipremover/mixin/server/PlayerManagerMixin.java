package me.arcanewastaken.logipremover.mixin.server;

import me.arcanewastaken.logipremover.config.LogIPRemoverConfig;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin
{
    @Redirect(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;[Ljava/lang/Object;)V"))
    public void removePlayerPublicIPAddressFromJoinServerLogMessage(Logger logger, String s, Object[] objects)
    {
        if (LogIPRemoverConfig.getConfigInstance().shouldHidePlayerIPs())
        {
            logger.info("{}[IP address hidden by Log IP Remover] logged in with entity id {} at ({}, {}, {})", objects[0], objects[2], objects[3], objects[4],
                    objects[5]);
        }
        else
        {
            logger.info("{}[{}] logged in with entity id {} at ({}, {}, {})", objects[0], objects[1], objects[2], objects[3], objects[4], objects[5]);
        }
    }
}
