package com.client.client.cache.definitions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.client.client.*;

public final class MobDefinition {

	public int frontLight = 68;
	public int backLight = 820;
	public int rightLight = 0;
	public int middleLight = -1; // Cannot be 0
	public int leftLight = 0;
	public static void NPCDump() {
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.home")+"/Desktop/Dumps/NPC Dump.txt");
			for(int id = 0; id < NPCAMOUNT; id++) {
				MobDefinition ed = MobDefinition.forID(id);
				fw.write("case "+id+":");
				fw.write(System.getProperty("line.separator"));
				fw.write("entityDef.name = \""+ed.name+"\";");
				fw.write(System.getProperty("line.separator"));
				fw.write("entityDef.description = \""+ed.description+"\";");
				fw.write(System.getProperty("line.separator"));
				fw.write("entityDef.combatLevel = "+ed.combatLevel+";");
				fw.write(System.getProperty("line.separator"));
				fw.write("entityDef.walkAnim = "+ed.walkAnim+";");
				fw.write(System.getProperty("line.separator"));
				fw.write("entityDef.standAnim = "+ed.standAnim+";");
				fw.write(System.getProperty("line.separator"));
				if(ed.actions != null) {
					fw.write("entityDef.actions = new String["+ed.actions.length+"];");
					fw.write(System.getProperty("line.separator"));
					for(int range = 0; range < ed.actions.length; range++) {
						if(ed.actions[range] != null) {
							fw.write("entityDef.actions["+range+"] = \""+ed.actions[range]+"\";");
							fw.write(System.getProperty("line.separator"));
						}
					}
				}
				if(ed.models != null) { 
					fw.write("entityDef.models = new int["+ed.models.length+"];");
					fw.write(System.getProperty("line.separator"));
					for(int range = 0; range < ed.models.length; range++) {
						fw.write("entityDef.models["+range+"] = "+ed.models[range]+";");
						fw.write(System.getProperty("line.separator"));
					}
				}
				if(ed.destColours != null) {
					fw.write("entityDef.originalModelColors = new int["+ed.destColours.length+"];");
					fw.write(System.getProperty("line.separator"));
					for(int range = 0; range < ed.destColours.length; range++) {
						fw.write("entityDef.originalModelColors["+range+"] = "+ed.destColours[range]+";");
						fw.write(System.getProperty("line.separator"));
					}
				}
				if(ed.originalColours != null) {
					fw.write("entityDef.modifiedModelColors = new int["+ed.originalColours.length+"];");
					fw.write(System.getProperty("line.separator"));
					for(int range = 0; range < ed.originalColours.length; range++) {
						fw.write("entityDef.modifiedModelColors["+range+"] = "+ed.originalColours[range]+";");
						fw.write(System.getProperty("line.separator"));
					}
				}

				fw.write("break;"); 
				fw.write(System.getProperty("line.separator"));
				fw.write(System.getProperty("line.separator"));
			}
			fw.close();
			System.out.println("NPC Dump Finished.");
			System.out.println("Total NPCs: "+NPCAMOUNT);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static MobDefinition forID(int i) {
		for (int j = 0; j < 20; j++)
			if (cache[j].type == (long) i)
				return cache[j];
		cacheIndex = (cacheIndex + 1) % 20;
		MobDefinition npc = cache[cacheIndex] = new MobDefinition();
		if (i >= streamIndices.length)
			return null;
		stream.currentOffset = streamIndices[i];
		npc.type = i;
		npc.readValues(stream);
		if (npc.name != null && npc.name.toLowerCase().contains("bank")) {
			if (npc.actions != null) {
				for (int l = 0; l < npc.actions.length; l++) {
					if (npc.actions[l] != null && npc.actions[l].equalsIgnoreCase("Collect"))
						npc.actions[l] = null;
				}
			}
		}

		npc.id = i;
		switch (i) {

		case 9772:
			npc.standAnim = 13696;
			break;
		case 9855:
		    npc.standAnim = 13467;
			npc.walkAnim = 13467;
			break;
			
		case 6307:
			npc.name = "Iktomi The Trickster";
			npc.description = "One of the guiders of the mighty God Zamorak!";
			npc.sizeXZ = 800;
			npc.sizeY = 800;
			npc.walkAnim = 5325;
			npc.standAnim = 5326;
			npc.combatLevel = 420;
			npc.squaresNeeded = 3;
			npc.models = new int[] { 20309, 20312 };
			npc.actions = new String[5];
			npc.actions = new String[] {null, "Attack",  null, null, null};
			npc.originalColours = new int[6];
			npc.originalColours[0] = 960;
			npc.originalColours[1] = 33728;
			npc.originalColours[2] = 22443;
			npc.originalColours[3] = 11200;
			npc.originalColours[4] = 56256;
			npc.originalColours[5] = 50099;
			npc.destColours = new int[6];
			npc.destColours[0] = 6; // 2ndary spider top arms
			npc.destColours[1] = 665; // spider top arms
			npc.destColours[2] = 660; // spuder under legs
			npc.destColours[3] = 22425; // back of spiders teeth
			npc.destColours[4] = 6; // spiders trim
			npc.destColours[5] = 22425; //spiders joints (cant really notice)
			break;
		case 6308:
			npc.name = "Pet Iktomi The Trickster";
			npc.description = "One of the guiders of the mighty God Zamorak!";
			npc.walkAnim = 5325;
			npc.standAnim = 5326;
			npc.combatLevel = 420;
			npc.squaresNeeded = 3;
			npc.models = new int[] { 20309, 20312 };
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.originalColours = new int[6];
			npc.originalColours[0] = 960;
			npc.originalColours[1] = 33728;
			npc.originalColours[2] = 22443;
			npc.originalColours[3] = 11200;
			npc.originalColours[4] = 56256;
			npc.originalColours[5] = 50099;
			npc.destColours = new int[6];
			npc.destColours[0] = 6; // 2ndary spider top arms
			npc.destColours[1] = 665; // spider top arms
			npc.destColours[2] = 660; // spider under legs
			npc.destColours[3] = 22425; // back of spiders teeth
			npc.destColours[4] = 6; // spiders trim
			npc.destColours[5] = 22425; //spiders joints (cant really notice)
			break;
		case 6309:
			npc.name = "Le'fosh The Brutal";
			npc.description = "Iktomi's brethren!";
			npc.sizeXZ = 350;
			npc.sizeY = 350;
			npc.walkAnim = 5325;
			npc.standAnim = 5326;
			npc.combatLevel = 164;
			npc.squaresNeeded = 3;
			npc.models = new int[] { 20309, 20312 };
			npc.actions = new String[5];
			npc.actions = new String[] {null, "Attack",  null, null, null};
			npc.originalColours = new int[6];
			npc.originalColours[0] = 960;
			npc.originalColours[1] = 33728;
			npc.originalColours[2] = 22443;
			npc.originalColours[3] = 11200;
			npc.originalColours[4] = 56256;
			npc.originalColours[5] = 50099;
			npc.destColours = new int[6];
			npc.destColours[0] = 6; // 2ndary spider top arms
			npc.destColours[1] = 54169; // spider top arms
			npc.destColours[2] = 54164; // spuder under legs
			npc.destColours[3] = 22425; // back of spiders teeth
			npc.destColours[4] = 6; // spiders trim
			npc.destColours[5] = 22425; //spiders joints (cant really notice)
			break;
		case 1220:
			/*npc.name = "Vampire";
			npc.walkAnim = 819;
			npc.standAnim = 808;
			npc.combatLevel = 72;
			npc.models = new int[5];
			npc.models[0] = 4089;
			npc.models[1] = 4090;
			npc.models[2] = 4088;
			npc.models[3] = 275;*/
			npc.models[4] = 181;
			break;
			
		case 6310:
			npc.name = "Pet Le'fosh The Brutal";
			npc.description = "Iktomi's brethren!";
			npc.walkAnim = 5325;
			npc.standAnim = 5326;
			npc.combatLevel = 164;
			npc.squaresNeeded = 3;
			npc.models = new int[] { 20309, 20312 };
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.originalColours = new int[6];
			npc.originalColours[0] = 960;
			npc.originalColours[1] = 33728;
			npc.originalColours[2] = 22443;
			npc.originalColours[3] = 11200;
			npc.originalColours[4] = 56256;
			npc.originalColours[5] = 50099;
			npc.destColours = new int[6];
			npc.destColours[0] = 6; // 2ndary spider top arms
			npc.destColours[1] = 54169; // spider top arms
			npc.destColours[2] = 54164; // spuder under legs
			npc.destColours[3] = 22425; // back of spiders teeth
			npc.destColours[4] = 6; // spiders trim
			npc.destColours[5] = 22425; //spiders joints (cant really notice)
			break;
		case 6311:
			npc.name = "Zamorakian warbeast";
			npc.description = "A warbeast of Zamorak!";
			npc.combatLevel = 173;
			npc.walkAnim = 1197;
			npc.standAnim = 1198;
			npc.models = new int[] { 3879 };
			npc.actions = new String[5];
			npc.actions = new String[] {null, "Attack",  null, null, null};
			npc.originalColours = new int[8];
			npc.originalColours[0] = 9129; //body
			npc.originalColours[1] = 10155; //hair
			npc.originalColours[2] = 127; //claws
			npc.originalColours[3] = 10126; //end of tail
			npc.originalColours[4] = 11177; //nothing
			npc.originalColours[5] = 4818; //ears
			npc.originalColours[6] = 12; //nose
			npc.originalColours[7] = 0; //eyes
			npc.destColours = new int[8];
			npc.destColours[0] = 660;
			npc.destColours[1] = 3;
			npc.destColours[2] = 3;
			npc.destColours[3] = 3;
			npc.destColours[4] = 3;
			npc.destColours[5] = 3;
			npc.destColours[6] = 995;
			npc.destColours[7] = 995;
			break;
		case 6312:
			npc.name = "Pet Zamorakian warbeast";
			npc.description = "A warbeast of Zamorak!";
			npc.sizeXZ = 70;
			npc.sizeY = 70;
			npc.combatLevel = 173;
			npc.walkAnim = 1197;
			npc.standAnim = 1198;
			npc.models = new int[] { 3879 };
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.originalColours = new int[8];
			npc.originalColours[0] = 9129; //body
			npc.originalColours[1] = 10155; //hair
			npc.originalColours[2] = 127; //claws
			npc.originalColours[3] = 10126; //end of tail
			npc.originalColours[4] = 11177; //nothing
			npc.originalColours[5] = 4818; //ears
			npc.originalColours[6] = 12; //nose
			npc.originalColours[7] = 0; //eyes
			npc.destColours = new int[8];
			npc.destColours[0] = 660;
			npc.destColours[1] = 3;
			npc.destColours[2] = 3;
			npc.destColours[3] = 3;
			npc.destColours[4] = 3;
			npc.destColours[5] = 3;
			npc.destColours[6] = 995;
			npc.destColours[7] = 995;
			break;
		case 6313:
			npc.name = "Abyzou Heartwrencher";
			npc.sizeXZ = 160;
			npc.sizeY = 160;
			npc.combatLevel = 718;
			npc.walkAnim = 63;
			npc.standAnim = 66;
			npc.models = new int[] { 17375, 17391, 17384, 17399 };
			npc.actions = new String[5];
			npc.actions = new String[] {null, "Attack",  null, null, null};
			npc.originalColours = new int[9];
			npc.originalColours[0] = 5219; //teeth
			npc.originalColours[1] = 910; //inside of mouth. Ankles. End of tail
			npc.originalColours[2] = 1814; //around face. Start of tail
			npc.originalColours[3] = 1938; //around face and back of head. Top part of legs and end bit of tail.
			npc.originalColours[4] = 1690; //top of forehead
			npc.originalColours[5] = 921; //eyebrows
			npc.originalColours[6] = 0; //horns. Feet
			npc.originalColours[7] = 962; //eye colour
			npc.originalColours[8] = 912; // knees, dick, end of tail
			npc.destColours = new int[9];
			npc.destColours[0] = 5584;
			npc.destColours[1] = 5584;
			npc.destColours[2] = 43;
			npc.destColours[3] = 6550;
			npc.destColours[4] = 6550;
			npc.destColours[5] = 43;
			npc.destColours[6] = 5584;
			npc.destColours[7] = 9152;
			npc.destColours[8] = 42;
			//npc.anInt55:-1, npc.anInt57:-1, npc.anInt58:-1,  npc.anInt59:-1, npc.anInt75:-1, entity.anInt79:16, npc.anInt83:-1, npc.anInt85:0, npc.anInt86:128, npc.anInt91:128, entity.anInt92:0, entity.anInt56:13 
			break;
		case 6314:
			npc.name = "Pet Abyzou Heartwrencher";
			npc.combatLevel = 718;
			npc.walkAnim = 63;
			npc.standAnim = 66;
			npc.models = new int[] { 17375, 17391, 17384, 17399 };
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.originalColours = new int[9];
			npc.originalColours[0] = 5219; //teeth
			npc.originalColours[1] = 910; //inside of mouth. Ankles. End of tail
			npc.originalColours[2] = 1814; //around face. Start of tail
			npc.originalColours[3] = 1938; //around face and back of head. Top part of legs and end bit of tail.
			npc.originalColours[4] = 1690; //top of forehead
			npc.originalColours[5] = 921; //eyebrows
			npc.originalColours[6] = 0; //horns. Feet
			npc.originalColours[7] = 962; //eye colour
			npc.originalColours[8] = 912; // knees, dick, end of tail
			npc.destColours = new int[9];
			npc.destColours[0] = 5584;
			npc.destColours[1] = 5584;
			npc.destColours[2] = 43;
			npc.destColours[3] = 6550;
			npc.destColours[4] = 6550;
			npc.destColours[5] = 43;
			npc.destColours[6] = 5584;
			npc.destColours[7] = 9152;
			npc.destColours[8] = 42;
			//npc.anInt55:-1, npc.anInt57:-1, npc.anInt58:-1,  npc.anInt59:-1, npc.anInt75:-1, entity.anInt79:16, npc.anInt83:-1, npc.anInt85:0, npc.anInt86:128, npc.anInt91:128, entity.anInt92:0, entity.anInt56:13 
			break;
			/*
			 * Rick & Morty
			 */
			
		case 6351:
			npc.name = "Rick";
			npc.description = "Rise above, focus on science.";
			npc.combatLevel = 256;
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.models = new int[1];
			npc.models[0] = 95023; //HEAD
			npc.actions = new String[5];
			npc.actions[0] = "Attack";
			break;
		case 6353:
			npc.name = "Pet Morty";
			npc.combatLevel = 0;
			npc.description = "Get your shit together.";
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.models = new int[1];
			npc.models[0] = 95024; //HEAD
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			break;
		case 6354:
			npc.name = "Pet Chilli";
			npc.description = "Wow, you look hot";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 95025;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.squaresNeeded = 2;
			npc.standAnim = 808;
			npc.walkAnim = 819;		
			break;
		case 6355:
			npc.name = "Pet Mayonaise";
			npc.description = "Mmmmmm, yummy";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 95026;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.squaresNeeded = 2;
			npc.standAnim = 808;
			npc.walkAnim = 819;		
			break;
			/*
			 * End Of Rick & Morty
			 */
		case 6356:
			npc.name = "Pet Striped Leopard";
			npc.walkAnim = 5226;
			npc.standAnim = 5225;
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 95044;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			//npc.anInt55:-1, npc.anInt57:-1, npc.anInt58:-1,  npc.anInt59:-1, npc.anInt75:-1, entity.anInt79:32, npc.anInt83:-1, npc.anInt85:0, npc.anInt86:128, npc.anInt91:128, entity.anInt92:0, entity.anInt56:4 
			break;

		case 7700:
			npc.models = new int[] { 33012 };
			npc.name = "JalTok-Jad";
			npc.standAnim = 7589;
			npc.walkAnim = 7588;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.destColours = null;
			npc.originalColours = null;
			npc.combatLevel = 900;
			npc.squaresNeeded = 5;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			break;

		case 7702:
			npc.models = new int[] { 33014 };
			npc.name = "Jal-Xil";
			npc.standAnim = 7602;
			npc.walkAnim = 7603;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.destColours = null;
			npc.originalColours = null;
			npc.combatLevel = 370;
			npc.squaresNeeded = 3;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			break;

		case 7703:
			npc.models = new int[] { 33000 };
			npc.name = "Jal-Zek";
			npc.standAnim = 7609;
			npc.walkAnim = 7608;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.destColours = null;
			npc.originalColours = null;
			npc.combatLevel = 490;
			npc.squaresNeeded = 4;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			break;

		case 7750:
			npc.models = new int[] { 33099 };
			npc.name = "Jal-MejJak";
			npc.standAnim = 2867;
			npc.walkAnim = 2863;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.destColours = null;
			npc.originalColours = null;
			npc.combatLevel = 250;
			npc.squaresNeeded = 1;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			break;

		case 7706:
			npc.models = new int[] { 33011 };
			npc.name = "TzKal-Zuk";
			npc.standAnim = 7564;
			npc.walkAnim = 7564;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.destColours = null;
			npc.originalColours = null;
			npc.combatLevel = 1400;
			npc.squaresNeeded = 7;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			break;
		case 7893:
			npc.models = new int[] { 33036 };
			npc.name = "@cya@Ancestral Glyph";
			npc.standAnim = 7567;
			npc.walkAnim = 7567;
			npc.actions = new String[] { null, null, null, null, null };
			npc.destColours = null;
			npc.originalColours = null;
			npc.combatLevel = 0;
			npc.squaresNeeded = 3;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			npc.drawMinimapDot = false;
			break;

		case 13:
			npc.originalColours = new int[] { 8741, 25238, 4626, 28318, 908 };
			npc.name = "Piles";
			npc.destColours = new int[] { 33, 24, 0, 4, 4 };
			npc.models = new int[] { 215, 247, 7611, 163, 176, 254, 181, 8954 };
			npc.npcHeadModels = new int[] { 53, 79 };
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.turn90CWAnimIndex = 821;
			npc.sizeXZ = 120;
			npc.turn180AnimIndex = 820;
			npc.actions = new String[] { "Talk-to", null, null, null, null };
			npc.combatLevel = 0;
			npc.turn90CCWAnimIndex = 822;
			npc.sizeY = 136;
			npc.hasRenderPriority = true;
			break;

		case 131:
			npc.combatLevel = 25;
			break;
		case 5453:
			npc.combatLevel = 25;
			break;
		case 1265:
			npc.drawMinimapDot = true;
			break;

		case 8327:
			npc.combatLevel = 110;
			npc.headIcon = -1;
			break;

		case 7553:
			npc.name = "General Khazard";
			npc.description = "It's a General Khazard.";
			npc.combatLevel = 112;
			npc.walkAnim = 11662;
			npc.standAnim = 11667;
			npc.actions = new String[5];
			npc.actions[0] = "Talk-to";
			npc.actions[1] = "Attack";
			npc.models = new int[1];
			npc.models[0] = 46712;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			npc.squaresNeeded = 3;
			break;

		case 9911:
			npc.name = "HarLakkRiftsplitter";
			npc.combatLevel = 582;
			npc.walkAnim = 63;
			npc.standAnim = 66;
			// npc.standAnim = 6943;
			// npc.walkAnim = 6942;
			break;
		/* runelive npc's */

		case 130:
			npc.name = "Ganodermic beast";
			npc.combatLevel = 280;
			npc.cacheIndex = 3;
			// npc.drawYellowDotOnMap = true;
			npc.sizeXZ = 128;
			npc.sizeY = 128;
			npc.models = new int[] { 13888 };
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.degreesToTurn = 32;
			npc.standAnim = 4553;
			npc.walkAnim = 4555;
			break;
		case 502:
			npc.name = "American Torva";
			npc.description = "Drops: 1:35 american set, 1:90 mbox, 1:3 mkey, 1:2 coins, 1:1 bones.";
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 65394;
			npc.models[1] = 55054;
			npc.models[2] = 55052;
			npc.models[3] = 55056;
			npc.models[4] = 55058;
			npc.models[5] = 65533;
			npc.models[6] = 65530;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.combatLevel = 75;
			npc.squaresNeeded = 1;
			break;
		case 503:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 15040;
			npc.models[1] = 15035;
			npc.models[2] = 15029;
			npc.models[3] = 15037;
			npc.models[4] = 15039;
			npc.models[5] = 15032;
			npc.models[6] = 15030;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Oreo Torva";
			npc.combatLevel = 100;
			npc.description = "Oreo themed torva.";
			break;
		case 504:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 15026;
			npc.models[1] = 15021;
			npc.models[2] = 15015;
			npc.models[3] = 15023;
			npc.models[4] = 15025;
			npc.models[5] = 15018;
			npc.models[6] = 15016;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Sky Torva";
			npc.combatLevel = 125;
			npc.description = "Sky themed torva.";
			break;
		case 505:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 65528;
			npc.models[1] = 65523;
			npc.models[2] = 65517;
			npc.models[3] = 65525;
			npc.models[4] = 65527;
			npc.models[5] = 65520;
			npc.models[6] = 65518;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Darth Maul Torva";
			npc.combatLevel = 150;
			npc.description = "Darth Maul torva.";
			break;
		case 506:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 119; // cape
			npc.models[1] = 115; // helm
			npc.models[2] = 117; // plate
			npc.models[3] = 113; // legs
			npc.models[4] = 111; // weapon
			npc.models[5] = 121; // gloves
			npc.models[6] = 123; // boots
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Cash Torva";
			npc.combatLevel = 175;
			npc.description = "Cash themed torva.";
			break;
		case 507:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 55073;
			npc.models[1] = 55047;
			npc.models[2] = 55046;
			npc.models[3] = 55050;
			npc.models[4] = 55072;
			npc.models[5] = 65512;
			npc.models[6] = 65514;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Silver Torva";
			npc.combatLevel = 200;
			npc.description = "Silver themed torva.";
			break;
		case 508:
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[1];
			npc.models[0] = 58467;
			npc.name = "MewTwo";
			npc.combatLevel = 50;
			npc.description = "It's MewTwo.";
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.sizeXZ = 50;
			npc.sizeY = 75;
			npc.squaresNeeded = 1;
			break;
		case 509:
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[1];
			npc.models[0] = 46031;
			npc.name = "Charmeleon";
			npc.combatLevel = 50;
			npc.description = "It's Charmeleon.";
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.squaresNeeded = 2;
			npc.sizeXZ = 50;
			npc.sizeY = 75;
			break;
		case 510:
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[1];
			npc.models[0] = 65207;
			npc.name = "Luigi";
			npc.combatLevel = 5;
			npc.description = "It's Luigi.";
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.squaresNeeded = 1;
			break;
		case 511:
			npc.squaresNeeded = 1;
			npc.actions = new String[5];
			npc.actions[2] = "Trade";
			npc.models = new int[5];
			npc.models[0] = 65212; // helm
			npc.models[1] = 65213; // plate
			npc.models[2] = 65214; // legs
			npc.models[3] = 65215; // gloves
			npc.models[4] = 65216; // boots
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "RuthlessPS Point Store";
			npc.description = "RuthlessPS Point Store";
			break;
		case 515:
			npc.squaresNeeded = 4;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 65408;
			npc.models[1] = 65415;
			npc.models[2] = 65413;
			npc.models[3] = 65417;
			npc.models[4] = 65419;
			npc.models[5] = 65411;
			npc.models[6] = 65410;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Camouflage Torva Boss";
			npc.combatLevel = 225;
			npc.description = "Camouflage themed torva boss.";
			npc.sizeXZ = 235;
			npc.sizeY = 235;
			break;
		case 517:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 3155;
			npc.models[1] = 2684;
			npc.models[2] = 2575;
			npc.models[3] = 2547;
			npc.models[4] = 24007;
			npc.models[5] = 2714;
			npc.models[6] = 14343;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Winter Torva Boss";
			npc.combatLevel = 250;
			npc.description = "Winter camo themed torva boss.";
			npc.sizeXZ = 235;
			npc.sizeY = 235;
			break;
		case 518:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 265;
			npc.models[1] = 2385;
			npc.models[2] = 17297;
			npc.models[3] = 202;
			npc.models[4] = 1314;
			npc.models[5] = 2521;
			npc.models[6] = 2483;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Bloodshot Torva Boss";
			npc.combatLevel = 300;
			npc.description = "Bloodshot camo themed torva boss.";
			npc.sizeXZ = 235;
			npc.sizeY = 235;
			break;
		case 998:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 65408;
			npc.models[1] = 65415;
			npc.models[2] = 65413;
			npc.models[3] = 65417;
			npc.models[4] = 65419;
			npc.models[5] = 65411;
			npc.models[6] = 65410;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Camouflage Mini Boss";
			npc.combatLevel = 225;
			npc.description = "Camouflage themed mini boss.";
			break;
		case 999:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 3155;
			npc.models[1] = 2684;
			npc.models[2] = 2575;
			npc.models[3] = 2547;
			npc.models[4] = 24007;
			npc.models[5] = 2714;
			npc.models[6] = 14343;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Winter Mini Boss";
			npc.combatLevel = 250;
			npc.description = "Winter camo themed mini boss.";
			break;
		case 1000:
			npc.squaresNeeded = 1;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.models = new int[7];
			npc.models[0] = 265;
			npc.models[1] = 2385;
			npc.models[2] = 17297;
			npc.models[3] = 202;
			npc.models[4] = 1314;
			npc.models[5] = 2521;
			npc.models[6] = 2483;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.name = "Bloodshot Mini Boss";
			npc.combatLevel = 300;
			npc.description = "Bloodshot camo themed mini boss.";
			break;
		case 604:
			npc.name = "Zython Pet";
			npc.description = "Zython pet.";
			npc.actions = new String[] { "Pick Up", null, null, null, null };
			npc.models = new int[1];
			npc.models[0] = 2767;
			npc.standAnim = 6374;
			npc.walkAnim = 6374;
			npc.combatLevel = 15;
			npc.squaresNeeded = 3;
			npc.sizeXZ = 50;
			npc.sizeY = 75;
			break;

		case 519:
			npc.models = new int[1];
			npc.models[0] = 28233;
			npc.name = "Squirtle";
			npc.description = "It's squirtle.";
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.combatLevel = 85;
			npc.standAnim = 11973;
			npc.walkAnim = 11975;
			npc.squaresNeeded = 1;
			npc.sizeXZ = 50;
			npc.sizeY = 75;
			break;

		// new npc's.

		case 132:
			npc.name = "Blitz";
			npc.description = "A master attacker of CelestialX.";
			npc.combatLevel = 913;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[9];
			npc.models[0] = 14395; // Hat
			npc.models[1] = 62746; // Platebody
			npc.models[2] = 62743; // Platelegs
			npc.models[3] = 62582; // Cape
			npc.models[4] = 13307; // Gloves
			npc.models[5] = 53327; // Boots
			npc.models[6] = 9642; // Amulet
			npc.models[7] = 2295; // Weapon
			npc.models[8] = 26423; // Shield
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
			npc.sizeXZ = 200;
			npc.sizeY = 200;
			npc.squaresNeeded = 2;
			break;
		case 133:
			npc.name = "Cobra";
			npc.description = "A master mager of CelestialX.";
			npc.combatLevel = 903;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[10];
			npc.models[0] = 3188; // Hat
			npc.models[1] = 58366; // Platebody
			npc.models[2] = 58333; // Platelegs
			npc.models[3] = 65297; // Cape
			npc.models[4] = 179; // Gloves
			npc.models[5] = 27738; // Boots
			npc.models[6] = 9642; // Amulet
			npc.models[7] = 56022; // Weapon
			npc.models[8] = 40942; // Shield
			npc.models[9] = 58316;
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
			npc.sizeXZ = 200;
			npc.sizeY = 200;
			npc.squaresNeeded = 2;
			npc.destColours = new int[] { 226770, 34503, 34503, 34503, 34503 };
			npc.originalColours = new int[] { 926, 65214, 65200, 65186, 62995 };
			break;
		case 135:
			npc.name = "Fear";
			npc.description = "A master ranger of CelestialX.";
			npc.combatLevel = 844;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[9];
			npc.models[0] = 26632; // Hat
			npc.models[1] = 20157; // Platebody
			npc.models[2] = 20139; // Platelegs
			npc.models[3] = 65297; // Cape
			npc.models[4] = 20129; // Gloves
			npc.models[5] = 27738; // Boots
			npc.models[6] = 9642; // Amulet
			npc.models[7] = 58380; // Weapon
			npc.models[8] = 20121;
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
			npc.sizeXZ = 200;
			npc.sizeY = 200;
			npc.destColours = ItemDefinition.forID(10372).newModelColor;
			npc.originalColours = ItemDefinition.forID(10372).editedModelColor;
			npc.squaresNeeded = 2;
			break;
		case 1472:
			npc.name = "Death";
			npc.description = "A master Attacker of CelestialX.";
			npc.combatLevel = 941;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[9];
			npc.models[0] = 55770; // Hat
			npc.models[1] = 55851; // Platebody
			npc.models[2] = 55815; // Platelegs
			npc.models[3] = 65297; // Cape
			npc.models[4] = 55728; // Gloves
			npc.models[5] = 55673; // Boots
			npc.models[6] = 9642; // Amulet
			npc.models[7] = 56046; // Weapon
			npc.models[8] = 38941; // Shield
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.npcHeadModels = MobDefinition.forID(517).npcHeadModels;
			npc.sizeXZ = 200;
			npc.sizeY = 200;
			npc.squaresNeeded = 2;
			npc.destColours = new int[] { 127, 127, 127, 127 };
			npc.originalColours = new int[] { 65214, 65200, 65186, 62995 };
			break;

		case 3334:
			npc.name = "WildyWyrm";
			npc.models = new int[] { 63604 };
			// npc.boundDim = 1;
			npc.standAnim = 12790;
			npc.walkAnim = 12790;
			npc.combatLevel = 382;
			npc.actions = new String[5];
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.sizeXZ = 225;
			npc.sizeY = 200;
			// npc.sizeXZ = 35;
			// npc.sizeY = 75;
			break;
		case 1:
			npc.name = "Poison";
			npc.actions = new String[] { null, null, null, null, null };
			npc.sizeXZ = 1;
			npc.sizeY = 1;
			npc.drawMinimapDot = false;
			break;
		case 0:
			npc.name = " ";
			npc.actions = new String[] { null, null, null, null, null };
			npc.sizeXZ = 1;
			npc.sizeY = 1;
			npc.drawMinimapDot = false;
			break;
		case 6723:
			npc.name = "Rock Golem";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 29755;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.walkAnim = 7181;
			npc.standAnim = 7180;
			npc.description = "Its a Rock Golem.";
			npc.squaresNeeded = 1;
			npc.sizeXZ = npc.sizeY = 110;
			break;
		case 6724:
			npc.name = "Heron";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 29756;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.walkAnim = 6774;
			npc.standAnim = 6772;
			npc.description = "Its a Heron.";
			npc.squaresNeeded = 1;
			break;

		case 568:
			npc.name = "Note Trader";
			npc.actions = new String[] { "Trade", null, null, null, null };
			break;

		case 6726:
			npc.name = "Beaver";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 29754;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.walkAnim = 7178;
			npc.standAnim = 7177;
			npc.description = "Its a Beaver.";
			npc.squaresNeeded = 1;
			break;

		case 6640:
			npc.name = "Kraken";
			npc.models = new int[] { 28231 };
			// npc.boundDim = 1;
			npc.standAnim = 3989;
			npc.walkAnim = 3989;
			npc.sizeXZ = 25;
			npc.sizeY = 25;
			npc.actions = new String[5];
			npc.drawMinimapDot = false;
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 0;
			npc.squaresNeeded = 1;
			break;

		case 963:
			npc.name = "Hellpupy";
			npc.models = new int[] { 29240 };
			// npc.boundDim = 1;
			npc.standAnim = 6561;
			npc.walkAnim = 6560;
			npc.originalColours = new int[] { 29270 };
			npc.actions = new String[5];
			npc.drawMinimapDot = false;
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 0;
			npc.squaresNeeded = 1;
			break;

		case 5781:
			npc.name = "Baby mole";
			npc.models = new int[] { 12073 };
			// npc.boundDim = 1;
			npc.standAnim = 3309;
			npc.walkAnim = 3313;
			npc.actions = new String[5];
			npc.drawMinimapDot = false;
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 0;
			npc.squaresNeeded = 1;
			npc.sizeXZ = 80;
			npc.sizeY = 80;
			break;

		case 6727:
			npc.name = "Tangleroot";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 32202;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.walkAnim = 7313;
			npc.standAnim = 7312;
			npc.description = "Its a Tangleroot.";
			npc.squaresNeeded = 1;
			break;
		case 6728:
			npc.name = "Rocky";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 32203;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.walkAnim = 7316;
			npc.standAnim = 7315;
			npc.description = "Its a Rocky.";
			npc.squaresNeeded = 1;
			break;
		case 6729:
			npc.name = "Giant squirrel";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 32206;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.walkAnim = 7310;
			npc.standAnim = 7309;
			npc.description = "Its a Giant squirrel.";
			npc.squaresNeeded = 1;
			break;
		case 6730:
			npc.name = "Rift guardian";
			npc.combatLevel = 0;
			npc.models = new int[1];
			npc.models[0] = 32204;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.walkAnim = 7306;
			npc.standAnim = 7307;
			npc.description = "Its a Rift guardian.";
			npc.squaresNeeded = 1;
			break;
		case 6731:
			npc.models = new int[1];
			npc.models[0] = 32697;
			npc.name = "Olmlet";
			npc.description = "Its a Olmlet.";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.squaresNeeded = 1;
			npc.standAnim = 7396;
			npc.walkAnim = 7395;
			npc.sizeXZ = npc.sizeY = 65;
			break;

		case 2000:
			npc.models = new int[2];
			npc.models[0] = 28294;
			npc.models[1] = 28295;
			npc.name = "Venenatis";
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.sizeXZ = 200;
			npc.sizeY = 200;
			MobDefinition ven = forID(60);
			npc.standAnim = ven.standAnim;
			npc.walkAnim = ven.walkAnim;
			npc.combatLevel = 464;
			npc.squaresNeeded = 3;
			break;
			
		case 2042://regular
			npc.name = "Zulrah";
			npc.actions = new String[] {null, "Attack", null, null, null};
			npc.models = new int[1];
			npc.models[0] = 14407;
			npc.standAnim = 5070;
			npc.walkAnim = 5070;
			npc.combatLevel = 725;
			npc.sizeXZ = 100;
			npc.sizeY = 100;
			break;
		case 2043://melee
			npc.name = "Zulrah";
			npc.actions = new String[] {null, "Attack", null, null, null};
			npc.models = new int[1];
			npc.models[0] = 14408;
			npc.standAnim = 5070;
			npc.walkAnim = 5070;
			npc.combatLevel = 725;
			npc.sizeXZ = 100;
			npc.sizeY = 100;
			break;
		case 2044://mage
			npc.name = "Zulrah";
			npc.actions = new String[] {null, "Attack", null, null, null};
			npc.models = new int[1];
			npc.models[0] = 14409;
			npc.standAnim = 5070;
			npc.walkAnim = 5070;
			npc.combatLevel = 725;
			npc.sizeXZ = 100;
			npc.sizeY = 100;
			break;
		case 2001:
			npc.models = new int[1];
			npc.models[0] = 28293;
			npc.name = "Scorpia";
			npc.actions = new String[] { null, "Attack", null, null, null };
			MobDefinition scor = forID(107);
			npc.standAnim = scor.standAnim;
			npc.walkAnim = scor.walkAnim;
			npc.combatLevel = 464;
			npc.squaresNeeded = 3;
			break;
		case 7286:
			npc.name = "Skotizo";
			npc.description = "Badass from the depths of hell";
			npc.combatLevel = 321;
			MobDefinition skotizo = forID(4698);
			npc.standAnim = skotizo.standAnim;
			npc.walkAnim = skotizo.walkAnim;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[1];
			npc.models[0] = 31653;
			npc.sizeXZ = 80; // resize if you wish hes a bit small cause
								// personal preference
			npc.sizeY = 80; // resize
			npc.squaresNeeded = 3;
			break;
		case 6766:
			npc.name = "Lizardman shaman";
			npc.description = "It's a lizardman.";
			npc.combatLevel = 150;
			npc.walkAnim = 7195;
			npc.standAnim = 7191;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[1];
			npc.models[0] = 4039;
			npc.sizeXZ = 108;
			npc.sizeY = 108;
			npc.squaresNeeded = 3;
			break;
		case 5886:
			npc.name = "Abyssal Sire";
			npc.description = "It's an abyssal sire.";
			npc.combatLevel = 350;
			npc.walkAnim = 4534;
			npc.standAnim = 4533;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[1];
			npc.models[0] = 29477;
			npc.sizeXZ = 108;
			npc.sizeY = 108;
			npc.squaresNeeded = 6;
			break;
		case 499:
			npc.name = "Thermonuclear smoke devil";
			npc.description = "It looks like its glowing";
			npc.combatLevel = 301;
			npc.walkAnim = 1828;
			npc.standAnim = 1829;
			npc.actions = new String[5];
			npc.actions[1] = "Attack";
			npc.models = new int[1];
			npc.models[0] = 28442;
			npc.sizeXZ = 240;
			npc.sizeY = 240;
			npc.squaresNeeded = 4;
			break;
		case 1999:
			npc.models = new int[2];
			npc.name = "Cerberus";
			npc.models[1] = 29270;
			npc.combatLevel = 318;
			npc.standAnim = 4484;
			npc.walkAnim = 4488;
			npc.actions = new String[5];
			npc.originalColours = new int[] { 29270 };
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.sizeXZ = 120;
			npc.sizeY = 120;
			break;
		case 2009:
			npc.name = "Callisto";
			npc.models = new int[] { 28298 };
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.combatLevel = 470;
			MobDefinition callisto = forID(105);
			npc.standAnim = callisto.standAnim;
			npc.walkAnim = callisto.walkAnim;
			npc.actions = callisto.actions;
			npc.sizeXZ = npc.sizeY = 110;
			npc.squaresNeeded = 4;
			break;
		case 2006:
			npc.models = new int[1];
			npc.models[0] = 28300;
			npc.name = "Vet'ion";
			npc.actions = new String[] { null, "Attack", null, null, null };
			MobDefinition vet = forID(90);
			npc.standAnim = vet.standAnim;
			npc.walkAnim = vet.walkAnim;
			npc.combatLevel = 464;
			break;

		case 3847:
			npc.name = "Kraken";
			npc.combatLevel = 291;
			npc.models = new int[] { 28231 };
			npc.standAnim = 3989;
			npc.walkAnim = forID(3847).walkAnim;
			npc.sizeXZ = npc.sizeY = 130;
			npc.lightning = 30;
			npc.shadow = 150;
			break;

		case 2004:
			npc.models = new int[1];
			npc.models[0] = 28231;
			npc.name = "Cave kraken";
			npc.actions = new String[] { null, "Attack", null, null, null };
			MobDefinition cave = forID(3847);
			npc.models = new int[1];
			npc.models[0] = 28233;
			npc.combatLevel = 127;
			npc.standAnim = 3989;
			npc.walkAnim = cave.walkAnim;
			npc.sizeXZ = npc.sizeY = 42;
			break;
		case 457:
			npc.name = "Ghost";
			npc.actions = new String[] { "Talk-to", null, "Teleport", null, null };
			break;
		case 922:
			npc.actions = new String[] { "Talk-to", null, null, null, null };
			break;
		case 241:
			npc.name = "Boss Point Shop";
			break;
		case 543:
			npc.name = "Decanter";
			break;
		case 4902:
			npc.name = "Expert Miner";
			npc.actions = new String[] { "Talk-To", null, "Trade", null, null };
			break;
		case 5417:
			npc.combatLevel = 210;
			break;
		case 5418:
			npc.combatLevel = 90;
			break;
		case 6715:
			npc.combatLevel = 91;
			break;
		case 6716:
			npc.combatLevel = 128;
			break;
		case 6701:
			npc.combatLevel = 173;
			break;
		case 6725:
			npc.combatLevel = 224;
			break;
		case 6691:
			npc.squaresNeeded = 2;
			npc.combatLevel = 301;
			break;
		case 1552:
			npc.name = "Donator Shop 1";
			break;
		case 741:
			npc.name = "Donator Shop 2";
			break;
		case 367:
			npc.name = "Item Gambler";
			break;

		case 725:
			npc.models = new int[] { 235, 252, 299, 60010, 10218, 263, 185 };
			npc.name = "Trivia Point Shop";
			break;
		case 8275:
			npc.models = new int[] { 231, 241, 246, 309, 60010, 10218, 254, 181, 3780, 3189 };
			break;
		case 8710:
		case 8707:
		case 8706:
		case 8705:
			npc.name = "Musician";
			npc.actions = new String[] { "Listen-to", null, null, null, null };
			break;

		case 947:
			npc.name = "Player Owned Shop Manager";
			npc.actions = new String[] { "Talk-to", null, "View Shops", "My Shop", "Claim Earnings" };
			break;
		case 9939:
			npc.combatLevel = 607;
			break;
		case 149:
			npc.name = "Whirlpool";
			npc.models = new int[] { 26699 };
			npc.actions = new String[] { null, "Disturb", null, null, null };
			npc.standAnim = 6737;
			npc.walkAnim = 6737;
			npc.squaresNeeded = 4;
			npc.combatLevel = 0;
			npc.sizeY = 130;
			npc.sizeXZ = 130;
			npc.lightning = 30;
			npc.shadow = 150;
			break;
		case 148:
			npc.name = "Enormous Tentacle";
			npc.models = new int[] { 13201, };
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.standAnim = 3617;
			npc.walkAnim = 3617;
			npc.combatLevel = 0;
			npc.sizeY = 200;
			npc.sizeXZ = 200;
			break;
		case 150:// small
			npc.name = "Whirlpool";
			npc.models = new int[] { 26699 };
			npc.actions = new String[] { null, "Disturb", null, null, null };
			npc.standAnim = 6737;
			npc.walkAnim = 6737;
			npc.combatLevel = 0;
			npc.sizeY = 55;
			npc.sizeXZ = 55;
			npc.lightning = 30;
			npc.shadow = 150;
			break;
		case 688:
			npc.name = "Archer";
			break;
		case 4540:
			npc.combatLevel = 299;
			break;
		case 3101:
			npc.sizeY = npc.sizeXZ = 80;
			npc.squaresNeeded = 1;
			npc.actions = new String[] { "Talk-to", null, "Start", "Rewards", null };
			break;
		case 6222:
			npc.name = "Kree'arra";
			npc.squaresNeeded = 5;
			npc.standAnim = 6972;
			npc.walkAnim = 6973;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.sizeY = npc.sizeXZ = 110;
			break;
		case 6203:
			npc.models = new int[] { 27768, 27773, 27764, 27765, 27770 };
			npc.name = "K'ril Tsutsaroth";
			npc.squaresNeeded = 5;
			npc.standAnim = 6943;
			npc.walkAnim = 6942;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.sizeY = npc.sizeXZ = 110;
			break;
		case 1610:
		case 491:
		case 10216:
			npc.actions = new String[] { null, "Attack", null, null, null };
			break;
		case 7969:
			npc.actions = new String[] { "Talk-to", null, "Trade", null, null };
			break;
		case 1382:
			npc.name = "Glacor";
			npc.models = new int[] { 58940 };
			npc.squaresNeeded = 3;
			// npc.anInt86 = 475;
			npc.sizeXZ = npc.sizeY = 180;
			npc.standAnim = 10869;
			npc.walkAnim = 10867;
			npc.actions = new String[] { null, "Attack", null, null, null };
			npc.combatLevel = 123;
			npc.drawMinimapDot = true;
			npc.combatLevel = 188;
			break;
		/*
		 * case 1383: npc.name = "Unstable glacyte"; npc.models = new int[]{58942};
		 * npc.standAnim = 10867; npc.walkAnim = 10901; npc.actions = new String[]{null,
		 * "Attack", null, null, null}; npc.combatLevel = 101; npc.drawMinimapDot =
		 * false; break; case 1384: npc.name = "Sapping glacyte"; npc.models = new
		 * int[]{58939}; npc.standAnim = 10867; npc.walkAnim = 10901; npc.actions = new
		 * String[]{null, "Attack", null, null, null}; npc.combatLevel = 101;
		 * npc.drawMinimapDot = true; break; case 1385: npc.name = "Enduring glacyte";
		 * npc.models = new int[]{58937}; npc.standAnim = 10867; npc.walkAnim = 10901;
		 * npc.actions = new String[]{null, "Attack", null, null, null}; npc.combatLevel
		 * = 101; npc.drawMinimapDot = true; break;
		 */
		case 4249:
			npc.name = "Gambler";
			break;

		case 710:
			npc.name = "Donator Shop 3";
			break;
		case 6970:
			npc.actions = new String[] { "Trade", null, "Exchange Shards", null, null };
			break;
		case 4657:
			npc.actions = new String[] { "Talk-to", null, "Claim Items", "Check Total", "Teleport" };
			break;
		case 605:
			npc.actions = new String[] { "Talk-to", null, "Vote Rewards", "Loyalty Titles", null };
			break;
		case 8591:
			npc.actions = new String[] { "Talk-to", null, "Trade", null, null };
			break;
		case 316:
		case 315:
		case 309:
		case 310:
		case 314:
		case 312:
		case 313:
			npc.sizeXZ = 30;
			break;
		case 318:
			npc.sizeXZ = 30;
			npc.actions = new String[] { "Net", null, "Lure", null, null };
			break;
		case 805:
			npc.actions = new String[] { "Trade", null, "Tan hide", null, null };
			break;
		case 461:
		case 844:
		case 650:
		case 5112:
		case 3789:
		case 802:
		case 520:
		case 521:
		case 11226:
			npc.actions = new String[] { "Trade", null, null, null, null };
			break;
		case 8022:
		case 8028:
			String color = i == 8022 ? "Yellow" : "Green";
			npc.name = "" + color + " energy source";
			npc.actions = new String[] { "Siphon", null, null, null, null };
			break;
		case 8444:
			npc.actions = new String[5];
			npc.actions[0] = "Trade";
			break;
		case 2579:
			npc.name = "Max";
			npc.description = "He's mastered the many skills of CelestialX.";
			npc.combatLevel = 138;
			npc.actions = new String[5];
			npc.actions[0] = "Talk-to";
			npc.actions[2] = "Trade";
			npc.standAnim = 808;
			npc.walkAnim = 819;
			npc.models = new int[] { 65291, 65322, 506, 529, 252, 9642, 62746, 13307, 62743, 53327 };
			npc.npcHeadModels = new int[] { 39332, 39235 };
			break;
		case 6830:
		case 6841:
		case 6796:
		case 7331:
		case 6831:
		case 7361:
		case 6847:
		case 6872:
		case 7353:
		case 6835:
		case 6845:
		case 6808:
		case 7370:
		case 7333:
		case 7351:
		case 7367:
		case 6853:
		case 6855:
		case 6857:
		case 6859:
		case 6861:
		case 6863:
		case 9481:
		case 6827:
		case 6889:
		case 6813:
		case 6817:
		case 7372:
		case 6839:
		case 8575:
		case 7345:
		case 6799:
		case 7335:
		case 7347:
		case 6800:
		case 9488:
		case 6804:
		case 6822:
		case 6849:
		case 7355:
		case 7357:
		case 7359:
		case 7341:
		case 7329:
		case 7339:
		case 7349:
		case 7375:
		case 7343:
		case 6820:
		case 6865:
		case 6809:
		case 7363:
		case 7337:
		case 7365:
		case 6991:
		case 6992:
		case 6869:
		case 6818:
		case 6843:
		case 6823:
		case 7377:
		case 6887:
		case 6885:
		case 6883:
		case 6881:
		case 6879:
		case 6877:
		case 6875:
		case 6833:
		case 6851:
		case 5079:
		case 5080:
		case 6824:
			npc.actions = new String[] { null, null, null, null, null };
			break;
		case 6806: // thorny snail
		case 6807:
		case 6994: // spirit kalphite
		case 6995:
		case 6867: // bull ant
		case 6868:
		case 6794: // spirit terrorbird
		case 6795:
		case 6815: // war tortoise
		case 6816:
		case 6874:// pack yak
		case 6873: // pack yak
		case 3594: // yak
		case 3590: // war tortoise
		case 3596: // terrorbird
			npc.actions = new String[] { "Store", null, null, null, null };
			break;
		case 548:
			npc.actions = new String[] { "Trade", null, null, null, null };
			break;
		case 3299:
		case 437:
		case 2313:
			npc.actions = new String[] { "Trade", null, null, null, null };
			break;

		case 1267:
		case 8459:
			npc.drawMinimapDot = true;
			break;
		case 961:
			npc.actions = new String[] { null, null, "Buy Consumables", "Restore Stats", null };
			npc.name = "Healer";
			break;
		case 705:
			npc.actions = new String[] { null, null, "Buy Armour", "Buy Weapons", "Buy Jewelries" };
			npc.name = "Warrior";
			break;
		case 1861:
			npc.actions = new String[] { null, null, "Buy Equipment", "Buy Ammunition", null };
			npc.name = "Archer";
			break;
		case 946:
			npc.actions = new String[] { null, null, "Buy Equipment", "Buy Runes", null };
			npc.name = "Mage";
			break;
		case 2253:
			npc.actions = new String[] { null, null, "Buy Skillcapes", "Buy Skillcapes (t)", "Buy Hoods" };
			break;
		case 2292:
			npc.actions = new String[] { "Trade", null, null, null, null };
			npc.name = "Merchant";
			break;
		case 2676:
			npc.actions = new String[] { "Makeover", null, null, null, null };
			break;
		case 494:
		case 1360:
			npc.actions = new String[] { "Talk-to", null, null, null, null };
			break;
		case 1685:
			npc.name = "Pure";
			npc.actions = new String[] { "Trade", null, null, null, null };
			break;
		case 3030:
			npc.name = "King black dragon";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 17414, 17415, 17429, 17422 };
			npc.combatLevel = 276;
			npc.standAnim = 90;
			npc.walkAnim = 4635;
			npc.sizeY = 40;
			npc.sizeXZ = 40;
			npc.squaresNeeded = 3;
			break;

		case 3031:
			npc.name = "General graardor";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 27785, 27789 };
			npc.combatLevel = 624;
			npc.standAnim = 7059;
			npc.walkAnim = 7058;
			npc.sizeY = 29;
			npc.sizeXZ = 33;
			break;

		case 3032:
			npc.name = "TzTok-Jad";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 34131 };
			npc.combatLevel = 702;
			npc.standAnim = 9274;
			npc.walkAnim = 9273;
			npc.sizeY = 25;
			npc.sizeXZ = 27;
			npc.squaresNeeded = 1;
			break;

		case 3033:
			npc.name = "Chaos elemental";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 11216 };
			npc.combatLevel = 305;
			npc.standAnim = 3144;
			npc.walkAnim = 3145;
			npc.sizeY = 49;
			npc.sizeXZ = 45;
			break;

		case 3034:
			npc.name = "Corporeal beast";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 40955 };
			npc.combatLevel = 785;
			npc.standAnim = 10056;
			npc.walkAnim = 10055;
			npc.sizeY = 24;
			npc.sizeXZ = 25;
			npc.squaresNeeded = 1;
			break;

		case 3035:
			npc.name = "Kree'arra";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 28003, 28004 };
			npc.combatLevel = 580;
			npc.standAnim = 6972;
			npc.walkAnim = 6973;
			npc.sizeY = 23;
			npc.sizeXZ = 23;
			npc.squaresNeeded = 1;
			break;

		case 3036:
			npc.name = "K'ril tsutsaroth";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 27768, 27773, 27764, 27765, 27770 };
			npc.combatLevel = 650;
			npc.standAnim = 6943;
			npc.walkAnim = 6942;
			npc.sizeY = 24;
			npc.sizeXZ = 24;
			npc.squaresNeeded = 1;
			break;
		case 3037:
			npc.name = "Commander zilyana";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 28057, 28071, 28078, 28056 };
			npc.combatLevel = 596;
			npc.standAnim = 6963;
			npc.walkAnim = 6962;
			npc.sizeY = 60;
			npc.sizeXZ = 60;
			npc.squaresNeeded = 1;
			break;
		case 3038:
			npc.name = "Dagannoth supreme";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 9941, 9943 };
			npc.combatLevel = 303;
			npc.standAnim = 2850;
			npc.walkAnim = 2849;
			npc.sizeY = 60;
			npc.sizeXZ = 60;
			npc.squaresNeeded = 1;
			break;

		case 3039:
			npc.name = "Dagannoth prime"; // 9940, 9943, 9942
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 9940, 9943, 9942 };
			npc.originalColours = new int[] { 11930, 27144, 16536, 16540 };
			npc.destColours = new int[] { 5931, 1688, 21530, 21534 };
			npc.combatLevel = 303;
			npc.standAnim = 2850;
			npc.walkAnim = 2849;
			npc.sizeY = 60;
			npc.sizeXZ = 60;
			npc.squaresNeeded = 1;
			break;

		case 3040:
			npc.name = "Dagannoth rex";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[] { 9941 };
			npc.originalColours = new int[] { 16536, 16540, 27144, 2477 };
			npc.destColours = new int[] { 7322, 7326, 10403, 2595 };
			npc.combatLevel = 303;
			npc.standAnim = 2850;
			npc.walkAnim = 2849;
			npc.sizeY = 60;
			npc.sizeXZ = 60;
			npc.squaresNeeded = 1;
			break;
		case 3047:
			npc.name = "Frost dragon";
			npc.combatLevel = 166;
			npc.standAnim = 13156;
			npc.walkAnim = 13157;
			npc.turn180AnimIndex = -1;
			npc.turn90CCWAnimIndex = -1;
			npc.turn90CWAnimIndex = -1;
			// npc.type = 51;
			npc.degreesToTurn = 32;
			npc.models = new int[] { 56767, 55294 };
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.sizeY = 45;
			npc.sizeXZ = 45;
			npc.squaresNeeded = 1;
			break;

		case 3048:
			npc.models = new int[] { 44733 };
			npc.name = "Tormented demon";
			npc.combatLevel = 450;
			npc.standAnim = 10921;
			npc.walkAnim = 10920;
			npc.turn180AnimIndex = -1;
			npc.turn90CCWAnimIndex = -1;
			npc.turn90CWAnimIndex = -1;
			// npc.type = 8349;
			npc.degreesToTurn = 32;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.sizeY = 40;
			npc.sizeXZ = 40;
			npc.squaresNeeded = 1;
			break;
		case 3050:
			npc.models = new int[] { 24602, 24605, 24606 };
			npc.name = "Kalphite queen";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 333;
			npc.standAnim = 6236;
			npc.walkAnim = 6236;
			npc.sizeY = 40;
			npc.sizeXZ = 40;
			npc.squaresNeeded = 1;
			break;
		case 3051:
			npc.models = new int[] { 46141 };
			npc.name = "Slash bash";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 111;
			npc.standAnim = 11460;
			npc.walkAnim = 11461;
			npc.sizeY = 45;
			npc.sizeXZ = 45;
			npc.squaresNeeded = 1;
			break;
		case 3052:
			npc.models = new int[] { 45412 };
			npc.name = "Phoenix";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 235;
			npc.standAnim = 11074;
			npc.walkAnim = 11075;
			npc.sizeY = 45;
			npc.sizeXZ = 45;
			npc.squaresNeeded = 1;
			break;
		case 3053:
			npc.models = new int[] { 46058, 46057 };
			npc.name = "Bandos avatar";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 299;
			npc.standAnim = 11242;
			npc.walkAnim = 11255;
			npc.sizeY = 45;
			npc.sizeXZ = 45;
			npc.squaresNeeded = 1;
			break;
		case 3054:
			npc.models = new int[] { 62717 };
			npc.name = "Nex";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 565;
			npc.standAnim = 6320;
			npc.walkAnim = 6319;
			npc.sizeY = 55;
			npc.sizeXZ = 55;
			npc.squaresNeeded = 1;
			break;
		case 3055:
			npc.models = new int[] { 51852, 51853 };
			npc.name = "Jungle strykewyrm";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 110;
			npc.standAnim = 12790;
			npc.walkAnim = 12790;
			npc.sizeY = 39;
			npc.sizeXZ = 35;
			npc.squaresNeeded = 1;
			break;
		case 3056:
			npc.models = new int[] { 51848, 51850 };
			npc.name = "Desert strykewyrm";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 130;
			npc.standAnim = 12790;
			npc.walkAnim = 12790;
			npc.sizeY = 39;
			npc.sizeXZ = 35;
			npc.squaresNeeded = 1;
			break;
		case 3057:
			npc.models = new int[] { 51847, 51849 };
			npc.name = "Ice strykewyrm";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 210;
			npc.standAnim = 12790;
			npc.walkAnim = 12790;
			npc.sizeY = 39;
			npc.sizeXZ = 35;
			npc.squaresNeeded = 1;
			break;
		case 3058:
			npc.models = new int[] { 49142, 49144 };
			npc.name = "Green dragon";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 79;
			npc.standAnim = 12248;
			npc.walkAnim = 12246;
			npc.sizeY = 40;
			npc.sizeXZ = 40;
			npc.squaresNeeded = 1;
			break;
		case 3059:
			npc.models = new int[] { 57937 };
			npc.name = "Baby blue dragon";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 48;
			npc.standAnim = 14267;
			npc.walkAnim = 14268;
			npc.sizeY = 70;
			npc.sizeXZ = 70;
			npc.squaresNeeded = 1;
			break;
		case 3060:
			npc.models = new int[] { 49137, 49144 };
			npc.name = "Blue dragon";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 111;
			npc.standAnim = 12248;
			npc.walkAnim = 12246;
			npc.sizeY = 45;
			npc.sizeXZ = 45;
			npc.squaresNeeded = 1;
			break;
		case 3061:
			npc.models = new int[] { 14294, 49144 };
			npc.name = "Black dragon";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.combatLevel = 227;
			npc.standAnim = 12248;
			npc.walkAnim = 12246;
			npc.sizeY = 45;
			npc.sizeXZ = 45;
			npc.squaresNeeded = 1;
			break;
		case 3062:
			npc.models = new int[2];
			npc.models[0] = 28294;
			npc.models[1] = 28295;
			npc.name = "Venenatis";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.sizeXZ = 45;
			npc.sizeY = 45;
			MobDefinition ven2 = forID(60);
			npc.standAnim = ven2.standAnim;
			npc.walkAnim = ven2.walkAnim;
			npc.combatLevel = 464;
			npc.squaresNeeded = 2;
			break;
		case 3063:
			npc.models = new int[1];
			npc.models[0] = 28293;
			npc.name = "Scorpia";
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			MobDefinition scor2 = forID(107);
			npc.standAnim = scor2.standAnim;
			npc.walkAnim = scor2.walkAnim;
			npc.sizeXZ = 55;
			npc.sizeY = 55;
			npc.combatLevel = 464;
			npc.squaresNeeded = 1;
			break;
		case 3064:
			npc.name = "Skotizo";
			npc.combatLevel = 321;
			MobDefinition skotizo2 = forID(4698);
			npc.standAnim = skotizo2.standAnim;
			npc.walkAnim = skotizo2.walkAnim;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[1];
			npc.models[0] = 31653;
			npc.sizeXZ = 22;
			npc.sizeY = 22;
			npc.squaresNeeded = 1;
			break;
		case 3065:

			npc.name = "Lizardman Shaman";
			npc.description = "It's a lizardman.";
			npc.combatLevel = 150;
			npc.walkAnim = 7195;
			npc.standAnim = 7191;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[1];
			npc.models[0] = 4039;
			npc.sizeXZ = 38;
			npc.sizeY = 38;
			npc.squaresNeeded = 1;
			break;

		case 3066:
			npc.name = "WildyWyrm";
			npc.models = new int[] { 63604 };
			// npc.boundDim = 1;
			npc.standAnim = 12790;
			npc.walkAnim = 12790;
			npc.combatLevel = 382;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.sizeXZ = 30;
			npc.sizeY = 40;
			npc.squaresNeeded = 1;
			// npc.sizeXZ = 35;
			// npc.sizeY = 75;
			break;
		case 3067:
			npc.name = "Bork";
			npc.models = new int[] { 40590 };
			// npc.boundDim = 1;
			npc.standAnim = 8753;
			npc.walkAnim = 8752;
			npc.combatLevel = 267;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.sizeXZ = 40;
			npc.sizeY = 40;
			npc.squaresNeeded = 1;
			// npc.sizeXZ = 35;
			// npc.sizeY = 75;
			break;

		case 3068:
			npc.name = "Barrelchest";
			npc.models = new int[] { 22790 };
			// npc.boundDim = 1;
			npc.standAnim = 5893;
			npc.walkAnim = 5892;
			npc.combatLevel = 267;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.sizeXZ = 40;
			npc.sizeY = 40;
			npc.squaresNeeded = 1;
			break;
		case 3069:
			npc.name = "Rock Crab";
			npc.models = new int[2];
			npc.models[0] = 4399;
			npc.models[1] = 4400;
			// npc.boundDim = 1;
			npc.standAnim = 1310;
			npc.walkAnim = 1311;
			npc.combatLevel = 13;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.sizeXZ = 80;
			npc.sizeY = 80;
			npc.squaresNeeded = 1;
			break;
		case 3070:
			npc.name = "Abyssal Sire";
			npc.description = "It's an abyssal sire.";
			npc.combatLevel = 350;
			npc.walkAnim = 4534;
			npc.standAnim = 4533;
			npc.actions = new String[5];
			npc.actions[0] = "Pick-up";
			npc.models = new int[1];
			npc.models[0] = 29477;
			npc.sizeXZ = 28;
			npc.sizeY = 28;
			npc.squaresNeeded = 1;
			break;

		case 3071:
			npc.name = "Vorago";
			npc.description = "It's an Vorago";
			npc.combatLevel = 1;
			npc.models = new int[1];
			npc.models[0] = 29478;
			npc.sizeXZ = 28;
			npc.sizeY = 28;
			npc.squaresNeeded = 1;
			break;
		/*
		 * case 1265: System.out.println("Models: " + npc.models[1]);
		 * System.out.println("Stand animation: " +npc.standAnim);
		 * System.out.println("Walk animation: " + npc.walkAnim);
		 * 
		 * break;
		 */
		}
	       if (npc.combatLevel > 0 && npc.actions[0] != "Pick-up") {
	        	
	    	   npc.actions[4] = "@cya@Drop Table";
	        }
		return npc;
	}

	public Model getHeadModel() {
		if (childrenIDs != null) {
			MobDefinition altered = getAlteredNPCDef();
			if (altered == null)
				return null;
			else
				return altered.getHeadModel();
		}
		if (npcHeadModels == null)
			return null;
		boolean everyFetched = false;
		for (int i = 0; i < npcHeadModels.length; i++)
			if (!Model.modelIsFetched(npcHeadModels[i]))
				everyFetched = true;
		if (everyFetched)
			return null;
		Model parts[] = new Model[npcHeadModels.length];
		for (int j = 0; j < npcHeadModels.length; j++)
			parts[j] = Model.fetchModel(npcHeadModels[j]);
		Model completeModel;
		if (parts.length == 1)
			completeModel = parts[0];
		else
			completeModel = new Model(parts.length, parts);
		if (originalColours != null) {
			for (int k = 0; k < originalColours.length; k++)
				completeModel.recolour(originalColours[k], destColours[k]);
		}
		return completeModel;
	}

	public MobDefinition getAlteredNPCDef() {
		try {
			int j = -1;
			if (varbitId != -1) {
				VarBit varBit = VarBit.cache[varbitId];
				int k = varBit.configId;
				int l = varBit.leastSignificantBit;
				int i1 = varBit.mostSignificantBit;
				int j1 = Client.anIntArray1232[i1 - l];
				j = clientInstance.variousSettings[k] >> l & j1;
			} else if (varSettingsId != -1) {
				j = clientInstance.variousSettings[varSettingsId];
			}
			if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
				return null;
			} else {
				return forID(childrenIDs[j]);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static int NPCAMOUNT = 11599;

	public static void unpackConfig(CacheArchive streamLoader) {
		stream = new Stream(streamLoader.getDataForName("npc.dat"));
		Stream stream2 = new Stream(streamLoader.getDataForName("npc.idx"));
		int totalNPCs = stream2.readUnsignedWord();
		streamIndices = new int[totalNPCs];
		int i = 2;
		for (int j = 0; j < totalNPCs; j++) {
			streamIndices[j] = i;
			i += stream2.readUnsignedWord();
		}
		cache = new MobDefinition[20];
		for (int k = 0; k < 20; k++)
			cache[k] = new MobDefinition();
		// NPCDefThing2.initialize();
	}

	public static void nullLoader() {
		modelCache = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public Model getAnimatedModel(int j, int k, int ai[]) {
		if (childrenIDs != null) {
			MobDefinition npc = getAlteredNPCDef();
			if (npc == null)
				return null;
			else
				return npc.getAnimatedModel(j, k, ai);
		}
		Model completedModel = (Model) modelCache.get(type);
		if (completedModel == null) {
			boolean everyModelFetched = false;
			for (int ptr = 0; ptr < models.length; ptr++)
				if (!Model.modelIsFetched(models[ptr]))
					everyModelFetched = true;

			if (everyModelFetched)
				return null;
			Model parts[] = new Model[models.length];
			for (int j1 = 0; j1 < models.length; j1++)
				parts[j1] = Model.fetchModel(models[j1]);
			if (parts.length == 1)
				completedModel = parts[0];
			else
				completedModel = new Model(parts.length, parts);
			if (originalColours != null) {
				for (int k1 = 0; k1 < originalColours.length; k1++)
					completedModel.recolour(originalColours[k1], destColours[k1]);
			}
			completedModel.createBones();
			completedModel.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
			modelCache.put(completedModel, type);
		}
		Model animatedModel = Model.entityModelDesc;
		animatedModel.method464(completedModel, FrameReader.isNullFrame(k) & FrameReader.isNullFrame(j));
		if (k != -1 && j != -1)
			animatedModel.method471(ai, j, k);
		else if (k != -1)
			animatedModel.applyTransform(k);
		if (sizeXZ != 128 || sizeY != 128)
			animatedModel.scaleT(sizeXZ, sizeXZ, sizeY);
		animatedModel.calculateDiagonals();
		animatedModel.triangleSkin = null;
		animatedModel.vertexSkin = null;
		if (squaresNeeded == 1)
			animatedModel.rendersWithinOneTile = true;
		return animatedModel;
	}

	public Model method164(int j, int frame, int ai[], int nextFrame, int idk, int idk2) {
		if (childrenIDs != null) {
			MobDefinition npc = getAlteredNPCDef();
			if (npc == null)
				return null;
			else
				return npc.method164(j, frame, ai, nextFrame, idk, idk2);
		}
		Model completedModel = (Model) modelCache.get(type);
		if (completedModel == null) {
			boolean everyModelFetched = false;
			for (int ptr = 0; ptr < models.length; ptr++)
				if (!Model.modelIsFetched(models[ptr]))
					everyModelFetched = true;

			if (everyModelFetched)
				return null;
			Model parts[] = new Model[models.length];
			for (int j1 = 0; j1 < models.length; j1++)
				parts[j1] = Model.fetchModel(models[j1]);
			if (parts.length == 1)
				completedModel = parts[0];
			else
				completedModel = new Model(parts.length, parts);
			if (originalColours != null) {
				for (int k1 = 0; k1 < originalColours.length; k1++)
					completedModel.recolour(originalColours[k1], destColours[k1]);
			}
			completedModel.createBones();
			completedModel.light(frontLight, backLight, rightLight, middleLight, leftLight, true);
			modelCache.put(completedModel, type);
		}
		Model animatedModel = Model.entityModelDesc;
		animatedModel.method464(completedModel, FrameReader.isNullFrame(frame) & FrameReader.isNullFrame(j));

		if (frame != -1 && j != -1)
			animatedModel.method471(ai, j, frame);
		else if (frame != -1 && nextFrame != -1)
			animatedModel.applyTransform(frame, nextFrame, idk, idk2);
		else if (frame != -1)
			animatedModel.applyTransform(frame);
		if (sizeXZ != 128 || sizeY != 128)
			animatedModel.scaleT(sizeXZ, sizeXZ, sizeY);
		animatedModel.calculateDiagonals();
		animatedModel.triangleSkin = null;
		animatedModel.vertexSkin = null;
		if (squaresNeeded == 1)
			animatedModel.rendersWithinOneTile = true;
		return animatedModel;
	}

	public void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0)
				return;
			if (i == 1) {
				int j = stream.readUnsignedByte();
				models = new int[j];
				for (int j1 = 0; j1 < j; j1++)
					models[j1] = stream.readUnsignedWord();
			} else if (i == 2)
				name = stream.readNewString();
			else if (i == 3) {
				description = stream.readNewString();
			} else if (i == 12)
				squaresNeeded = stream.readSignedByte();
			else if (i == 13)
				standAnim = stream.readUnsignedWord();
			else if (i == 14) {
				walkAnim = stream.readUnsignedWord();
				runAnim = walkAnim;
			} else if (i == 17) {
				walkAnim = stream.readUnsignedWord();
				turn180AnimIndex = stream.readUnsignedWord();
				turn90CWAnimIndex = stream.readUnsignedWord();
				turn90CCWAnimIndex = stream.readUnsignedWord();
				if (walkAnim == 65535)
					walkAnim = -1;
				if (turn180AnimIndex == 65535)
					turn180AnimIndex = -1;
				if (turn90CWAnimIndex == 65535)
					turn90CWAnimIndex = -1;
				if (turn90CCWAnimIndex == 65535)
					turn90CCWAnimIndex = -1;
			} else if (i >= 30 && i < 40) {
				if (actions == null)
					actions = new String[5];
				actions[i - 30] = stream.readNewString();
				if (actions[i - 30].equalsIgnoreCase("hidden"))
					actions[i - 30] = null;
			} else if (i == 40) {
				int k = stream.readUnsignedByte();
				destColours = new int[k];
				originalColours = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					originalColours[k1] = stream.readUnsignedWord();
					destColours[k1] = stream.readUnsignedWord();
				}
			} else if (i == 60) {
				int l = stream.readUnsignedByte();
				npcHeadModels = new int[l];
				for (int l1 = 0; l1 < l; l1++)
					npcHeadModels[l1] = stream.readUnsignedWord();
			} else if (i == 90)
				stream.readUnsignedWord();
			else if (i == 91)
				stream.readUnsignedWord();
			else if (i == 92)
				stream.readUnsignedWord();
			else if (i == 93)
				drawMinimapDot = false;
			else if (i == 95)
				combatLevel = stream.readUnsignedWord();
			else if (i == 97)
				sizeXZ = stream.readUnsignedWord();
			else if (i == 98)
				sizeY = stream.readUnsignedWord();
			else if (i == 99)
				hasRenderPriority = true;
			else if (i == 100)
				lightning = stream.readSignedByte();
			else if (i == 101)
				shadow = stream.readSignedByte() * 5;
			else if (i == 102)
				headIcon = stream.readUnsignedWord();
			else if (i == 103)
				degreesToTurn = stream.readUnsignedWord();
			else if (i == 106) {
				varbitId = stream.readUnsignedWord();
				if (varbitId == 65535)
					varbitId = -1;
				varSettingsId = stream.readUnsignedWord();
				if (varSettingsId == 65535)
					varSettingsId = -1;
				int i1 = stream.readUnsignedByte();
				childrenIDs = new int[i1 + 1];
				for (int i2 = 0; i2 <= i1; i2++) {
					childrenIDs[i2] = stream.readUnsignedWord();
					if (childrenIDs[i2] == 65535)
						childrenIDs[i2] = -1;
				}
			} else if (i == 107)
				clickable = false;
		} while (true);
	}

	public MobDefinition() {
		turn90CCWAnimIndex = -1;
		varbitId = -1;
		turn180AnimIndex = -1;
		varSettingsId = -1;
		combatLevel = -1;
		walkAnim = -1;
		squaresNeeded = 1;
		headIcon = -1;
		standAnim = -1;
		type = -1L;
		degreesToTurn = 32;
		turn90CWAnimIndex = -1;
		clickable = true;
		sizeY = 128;
		drawMinimapDot = true;
		sizeXZ = 128;
		hasRenderPriority = false;
	}

	public int turn90CCWAnimIndex;
	public static int cacheIndex;
	public int varbitId;
	public int turn180AnimIndex;
	public int varSettingsId;
	public static Stream stream;
	public int combatLevel;
	public String name;
	public String actions[];
	public int walkAnim;
	public int runAnim;
	public byte squaresNeeded;
	public int[] destColours;
	public static int[] streamIndices;
	public int[] npcHeadModels;
	public int headIcon;
	public int[] originalColours;
	public int standAnim;
	public long type;
	public int degreesToTurn;
	public static MobDefinition[] cache;
	public static Client clientInstance;
	public int turn90CWAnimIndex;
	public boolean clickable;
	public int lightning;
	public int sizeY;
	public boolean drawMinimapDot;
	public int childrenIDs[];
	public String description;
	public int sizeXZ;
	public int shadow;
	public boolean hasRenderPriority;
	public int[] models;
	public static MemCache modelCache = new MemCache(30);
	public int id;
}