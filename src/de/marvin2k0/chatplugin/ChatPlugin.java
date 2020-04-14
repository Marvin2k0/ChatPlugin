package de.marvin2k0.chatplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ChatPlugin extends JavaPlugin
{
    private static ChatPlugin chatplugin;
    private List<String> muted;
    private boolean isEnabled;

    public static ChatPlugin getChatPlugin()
    {
        return chatplugin;
    }

    public void onEnable()
    {
        chatplugin = this;

        this.getConfig().options().copyDefaults(true);
        this.getConfig().addDefault("prefix", "&c[Prefix]&f");
        this.getConfig().addDefault("chatAllow", true);
        this.getConfig().addDefault("chatCooldown", 20);
        this.saveConfig();

        this.muted = getConfig().getStringList("muted");
        this.isEnabled = this.getConfig().getBoolean("chatAllow");

        this.getCommand("chat").setExecutor(new ChatCommand());
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    public String getPrefix()
    {
        return this.get("prefix");
    }

    public boolean isChatEnabled()
    {
        return this.isEnabled;
    }

    public boolean mute(Player player)
    {
        if (!isMuted(player))
        {
            List<String> temp = this.getConfig().getStringList("muted");
            temp.add(player.getUniqueId().toString());

            this.muted = temp;
            this.getConfig().set("muted", muted);
            this.saveConfig();

            return true;
        }
        else
        {
            List<String> temp = this.getConfig().getStringList("muted");
            temp.remove(player.getUniqueId().toString());

            this.muted = temp;
            this.getConfig().set("muted", muted);
            this.saveConfig();

            return false;
        }
    }

    public boolean isMuted(Player player)
    {
        return this.muted.contains(player.getUniqueId().toString());
    }

    public void setChatEnabled(boolean flag)
    {
        this.isEnabled = flag;

        this.getConfig().set("chatAllow", flag);
        this.saveConfig();
    }

    public float getChatCooldown()
    {
        return this.getConfig().getLong("chatCooldown");
    }

    public String get(String path)
    {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(path));
    }
}
