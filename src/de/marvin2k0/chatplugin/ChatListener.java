package de.marvin2k0.chatplugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatListener implements Listener
{
    private ChatPlugin cb = ChatPlugin.getChatPlugin();
    private List<UUID> spam = new ArrayList<UUID>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        if (cb.isMuted(e.getPlayer()))
        {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§cDu bist stummgeschaltet!");

            return;
        }

        if (!cb.isChatEnabled() && !e.getPlayer().hasPermission("chat.forcechat"))
        {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§cDer Chat ist momentan deaktiviert!");

            return;
        }

        if (spam.contains(e.getPlayer().getUniqueId()) && !e.getPlayer().hasPermission("chat.spam"))
        {
            e.setCancelled(true);

            e.getPlayer().sendMessage("§cBitte spamme nicht!");

            return;
        }
        else
        {
            spam.add(e.getPlayer().getUniqueId());
        }

        if (e.getPlayer().hasPermission("chat.color"))
        {
            e.setMessage(e.getMessage().replace('&', '§'));
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(cb, new Runnable()
        {
            @Override
            public void run()
            {
                spam.remove(e.getPlayer().getUniqueId());
            }
        }, (long) cb.getChatCooldown());
    }
}
