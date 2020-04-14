package de.marvin2k0.chatplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor
{
    private ChatPlugin cb = ChatPlugin.getChatPlugin();
    private String prefix = cb.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (!sender.hasPermission("chat.staff"))
        {
            sender.sendMessage(prefix + "§cDazu hast du keine Berechtigung!");

            return true;
        }

        if (args.length == 0)
        {
            sender.sendMessage("§b==========" + prefix + "§b==========");
            sender.sendMessage("§a/chat toggle §f- §aaktiviert/deaktiviert den Chat");
            sender.sendMessage("§a/chat clear §f- §aLeert den Chat");
            sender.sendMessage("§a/chat mute <spieler> §f- §aMutet einen Spieler");
            sender.sendMessage("§b==========" + prefix + "§b==========");

            return true;
        }

        if (args[0].equalsIgnoreCase("toggle"))
        {
            cb.setChatEnabled(!cb.isChatEnabled());

            Bukkit.broadcastMessage("§eDer chat wurde " + (cb.isChatEnabled() ? "§aaktiviert!" : "§cdeaktiviert!"));

            return true;
        }

        if (args[0].equalsIgnoreCase("clear"))
        {
            for (int i = 0; i < 100; i++)
            {
                Bukkit.getServer().broadcastMessage("");
            }

            Bukkit.getServer().broadcastMessage("§cDer Chat wurde geleert!");
        }

        if (args[0].equalsIgnoreCase("mute"))
        {
            if (args.length != 2)
            {
                sender.sendMessage(prefix + " §cBitte benutze /chat mute <spieler>");

                return true;
            }
            if (Bukkit.getPlayer(args[1]) == null)
            {
                sender.sendMessage(prefix + " §e" + args[1] + " §cexistiert nicht!");

                return true;
            }

            Player player = Bukkit.getPlayer(args[1]);

            if (cb.mute(player))
            {
                sender.sendMessage(prefix + " §cDu hast §e" + player.getName() + " §cstummgeschaltet!");
            }
            else
            {
                sender.sendMessage(prefix + " §cDu hast §e" + player.getName() + " §cfreigegeben!");
            }

            return true;
        }

        return false;
    }
}
