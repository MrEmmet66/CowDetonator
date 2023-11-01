package org.mbf.cowdetonator;

import org.bukkit.plugin.java.JavaPlugin;

public final class CowDetonator extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getCommand("cowbomb").setExecutor(new CowCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CowDetonator getInstance(){
        return getPlugin(CowDetonator.class);
    }
}
