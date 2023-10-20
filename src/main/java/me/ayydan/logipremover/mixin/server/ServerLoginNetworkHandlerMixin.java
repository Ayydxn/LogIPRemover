package me.ayydan.logipremover.mixin.server;

import com.mojang.authlib.GameProfile;
import me.ayydan.logipremover.config.LogIPRemoverConfig;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLoginNetworkHandler.class)
public class ServerLoginNetworkHandlerMixin
{
    @Shadow
    @Nullable
    private GameProfile profile;

    @Shadow @Final
    ClientConnection connection;

    @Inject(method = "getConnectionInfo", at = @At("RETURN"), cancellable = true)
    public void getModifiedConnectionInfo(CallbackInfoReturnable<String> cir)
    {
        boolean shouldIPAddresses = LogIPRemoverConfig.getConfigInstance().shouldHidePlayerIPs();

        if (this.profile != null)
        {
            if (shouldIPAddresses)
            {
                cir.setReturnValue(String.format("%s (IP address hidden by Log IP Remover)", this.profile));
            }
            else
            {
                cir.setReturnValue(String.format("%s (%s)", this.profile, this.connection.getAddress()));
            }
        }

        cir.setReturnValue(shouldIPAddresses ? "(IP address hidden by Log IP Remover)" : String.valueOf(this.connection.getAddress()));
    }
}
