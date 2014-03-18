package com.becelot.teamcore.setup.state;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

import com.becelot.teamcore.SpaceConfig;
import com.becelot.teamcore.player.PlayerEvent;
import com.becelot.teamcore.setup.FSMTeamBuilder;
import com.becelot.teamcore.setup.FSMTeamBuilderState;
import com.becelot.teamcore.util.Chat;
import com.becelot.teamcore.util.SetupStructureBuilder;

public class TeamLimitState extends FSMTeamBuilderState {
	
	private ChatMessageComponent invalidArgument = Chat.fromRegistry("command.teamlimit.invalidargument");
	private ChatMessageComponent invalidArgumentCount = Chat.fromRegistry("command.teamlimit.invalidargumentcount");


	public TeamLimitState() {
		super();
		this.requiredCommandNames.add("teamlimits");
	}

	@Override
	public void interact(EntityPlayer entityPlayer, int team, int x, int y,
			int z) {
		// Nothing here

	}

	@Override
	public void commandSend(String command, ICommandSender icommandsender, String[] astring) {
		if (astring.length <= 2) {
			//Confirm selection
			if (astring.length == 1) {
				if (astring[0].toLowerCase().equals("ok")) {
					Chat.sendToAllPlayersFromRegistryFormatted("command.teamlimit.successfull.server", 
							SpaceConfig.teamCount, SpaceConfig.minMemberCount, SpaceConfig.maxMemberCount);

					SetupStructureBuilder.buildMidCage(MinecraftServer.getServer().worldServerForDimension(SpaceConfig.dimensionId), SpaceConfig.unbreakableGlassId);
					SetupStructureBuilder.buildTeamSelection(MinecraftServer.getServer().worldServerForDimension(SpaceConfig.dimensionId));
					this.switchState(fsmRegisterTeam);
					PlayerEvent.registerTeamInteractionListener(FSMTeamBuilder.instance);
					return;
				}
			}

			//No valid arguements given
			icommandsender.sendChatToPlayer(invalidArgumentCount);
			return;
		}

		//Parse arguments and save them
		try {
			int teamCount = Integer.parseInt(astring[0]);
			int minMemberCount = Integer.parseInt(astring[1]);
			int maxMemberCount = Integer.parseInt(astring[2]);

			if (minMemberCount > maxMemberCount) throw new Exception();

			SpaceConfig.teamCount = teamCount;
			SpaceConfig.minMemberCount = minMemberCount;
			SpaceConfig.maxMemberCount = maxMemberCount;
		} catch (Exception e) {
			icommandsender.sendChatToPlayer(invalidArgument);
			return;
		}

		//Send confirmation to GameMod
		Chat.sendToPlayerFormatted((EntityPlayer)icommandsender, "command.teamlimit.successfull", 
				SpaceConfig.teamCount, SpaceConfig.minMemberCount, SpaceConfig.maxMemberCount);


	}

}
