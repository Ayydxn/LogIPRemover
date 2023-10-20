package me.ayydan.logipremover;

import me.ayydan.logipremover.config.LogIPRemoverConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

@Environment(EnvType.SERVER)
public class LogIPRemoverMod implements DedicatedServerModInitializer
{
    private static final Logger LOGGER = (Logger) LogManager.getLogger("Log IP Remover");

    @Override
    public void onInitializeServer()
    {
        LogIPRemoverConfig.initializeConfig();

        LOGGER.info("Initialized Lop IP Remover!");
    }
}
