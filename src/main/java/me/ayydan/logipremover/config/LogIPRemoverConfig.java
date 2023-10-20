package me.ayydan.logipremover.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class LogIPRemoverConfig
{
    private static Config configurationInstance;

    private static final File configurationFile = new File("./config/log-ip-remover/log-ip-remover-config.json");

    public static void initializeConfig()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (configurationFile.exists())
        {
            try
            {
                BufferedReader configFileReader = new BufferedReader(new FileReader(configurationFile));
                configurationInstance = gson.fromJson(configFileReader, Config.class);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                // Create the configuration file
                if (!configurationFile.getParentFile().mkdirs())
                    throw new RuntimeException("Failed to create Log IP Remover configuration file!");

                if (!configurationFile.createNewFile())
                    throw new RuntimeException("Failed to create Log IP Remover configuration file!");

                configurationInstance = new Config(true); // Create the config with default settings
                String configJsonString = gson.toJson(configurationInstance);

                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(configurationFile));
                fileWriter.write(configJsonString);
                fileWriter.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static Config getConfigInstance()
    {
        return configurationInstance;
    }

    public static File getConfigurationFile()
    {
        return configurationFile;
    }

    public record Config(boolean shouldHidePlayerIPs) {}
}
