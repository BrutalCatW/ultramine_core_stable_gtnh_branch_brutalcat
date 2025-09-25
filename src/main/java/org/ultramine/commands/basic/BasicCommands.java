package org.ultramine.commands.basic;

import java.util.Map;

import static net.minecraft.util.EnumChatFormatting.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.storage.WorldInfo;

import org.ultramine.commands.Command;
import org.ultramine.commands.CommandContext;
import org.ultramine.server.ConfigurationHandler;
import org.ultramine.server.Teleporter;
import org.ultramine.server.data.player.PlayerData;
import org.ultramine.server.util.BasicTypeFormatter;
import org.ultramine.server.util.InventoryUtil;
import org.ultramine.server.util.WarpLocation;

public class BasicCommands
{
	@Command(
			name = "dropall",
			group = "basic",
			permissions = {"command.basic.dropall"},
			syntax = {"", "<player>"}
	)
	public static void dropall(CommandContext ctx)
	{
		ctx.checkPermissionIfArg("player", "command.admin.dropall.other", "command.dropall.noperm.other");
		EntityPlayerMP player = ctx.contains("player") ? ctx.get("player").asPlayer() : ctx.getSenderAsPlayer();
		player.inventory.dropAllItems();
	}
	
	@Command(
			name = "item",
			group = "basic",
			aliases = {"i"},
			permissions = {"command.basic.item"},
			syntax = {
					"<item>",
					"<item> <int%size>",
					"<player> <item>..."
			}
	)
	public static void item(CommandContext ctx)
	{
		ItemStack is = ctx.get("item").asItemStack();
		EntityPlayerMP player = ctx.contains("player") ? ctx.get("player").asPlayer() : ctx.getSenderAsPlayer();
		if(ctx.contains("size"))
			is.stackSize = ctx.get("size").asInt();
		InventoryUtil.addItem(player.inventory, is);
	}
	
	@Command(
			name = "dupe",
			group = "basic",
			permissions = {"command.basic.dupe"},
			syntax = {"", "<%count>"}
	)
	public static void dupe(CommandContext ctx)
	{
		ItemStack is = ctx.getSenderAsPlayer().inventory.getCurrentItem();
		ctx.check(is != null, "command.dupe.fail");
		is = is.copy();
		if(ctx.contains("count"))
			is.stackSize *= ctx.get("count").asInt();
		InventoryUtil.addItem(ctx.getSenderAsPlayer().inventory, is);
	}
	
	@Command(
			name = "gm",
			group = "basic",
			permissions = {"command.basic.gm"},
			syntax = {""}
	)
	public static void gm(CommandContext ctx)
	{
		EntityPlayerMP player = ctx.getSenderAsPlayer();
		
		GameType type = player.theItemInWorldManager.getGameType();
		GameType newtype = GameType.SURVIVAL;
		if(type == GameType.SURVIVAL)
			newtype = GameType.CREATIVE;
		
		player.setGameType(newtype);
	}
}
