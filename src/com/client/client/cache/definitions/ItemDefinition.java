package com.client.client.cache.definitions;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.client.Configuration;
import com.client.client.*;

public final class ItemDefinition {

	private static int[] prices;
	private static List<Integer> untradeableItems = new ArrayList<Integer>();
	public static String itemModels(int itemID) {
		int inv = forID(itemID).modelID;
		int male = forID(itemID).maleEquip1;
		int female = forID(itemID).femaleEquip1;
		String name = forID(itemID).name;
		return "<col=225>"+name+"</col> (<col=800000000>"+itemID+"</col>) - [inv: <col=800000000>"+inv+"</col>] - [male: <col=800000000>"+male+"</col>] - [female: <col=800000000>"+female+"</col>]";
	}
	public static void nullLoader() {
		modelCache = null;
		spriteCache = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public boolean dialogueModelFetched(int j) {
		int k = maleDialogue;
		int l = maleDialogueModel;
		if (j == 1) {
			k = femaleDialogue;
			l = femaleDialogueModel;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.modelIsFetched(k)) {
			flag = false;
		}
		if (l != -1 && !Model.modelIsFetched(l)) {
			flag = false;
		}
		return flag;
	}

	public Model getDialogueModel(int gender) {
		int k = maleDialogue;
		int l = maleDialogueModel;
		if (gender == 1) {
			k = femaleDialogue;
			l = femaleDialogueModel;
		}
		if (k == -1) {
			return null;
		}
		Model model = Model.fetchModel(k);
		if (l != -1) {
			Model model_1 = Model.fetchModel(l);
			Model models[] = { model, model_1 };
			model = new Model(2, models);
		}
		if (editedModelColor != null) {
			for (int i1 = 0; i1 < editedModelColor.length; i1++) {
				model.recolour(editedModelColor[i1], newModelColor[i1]);
			}
		}
		return model;
	}

	public boolean equipModelFetched(int gender) {
		int fistModel = maleEquip1;
		int secondModel = maleEquip2;
		int thirdModel = maleEquip3;
		if (gender == 1) {
			fistModel = femaleEquip1;
			secondModel = femaleEquip2;
			thirdModel = femaleEquip3;
		}
		if (fistModel == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.modelIsFetched(fistModel)) {
			flag = false;
		}
		if (secondModel != -1 && !Model.modelIsFetched(secondModel)) {
			flag = false;
		}
		if (thirdModel != -1 && !Model.modelIsFetched(thirdModel)) {
			flag = false;
		}
		return flag;
	}

	public Model getEquipModel(int gender) {
		int j = maleEquip1;
		int k = maleEquip2;
		int l = maleEquip3;
		if (gender == 1) {
			j = femaleEquip1;
			k = femaleEquip2;
			l = femaleEquip3;
		}
		if (j == -1) {
			return null;
		}
		Model model = Model.fetchModel(j);
		if (k != -1) {
			if (l != -1) {
				Model model_1 = Model.fetchModel(k);
				Model model_3 = Model.fetchModel(l);
				Model model_1s[] = { model, model_1, model_3 };
				model = new Model(3, model_1s);
			} else {
				Model model_2 = Model.fetchModel(k);
				Model models[] = { model, model_2 };
				model = new Model(2, models);
			}
		}
		if (j == 32678) {
			model.translate(-15, 10, 10);
		}
		if (gender == 1) {
			if (id == 11283 || id == 11284 || id == 11285) {
				model.translate(-3, 0, 0);
			}
			for (String itemActions : actions) {
				if (itemActions != null) {
					if (itemActions.equalsIgnoreCase("Wield")) {
						model.translate(3, 12, 5);
					}
				}
			}
		}
		if (gender == 0 && (maleZOffset != 0 || maleXOffset != 0 || maleYOffset != 0)) {
			model.translate(maleXOffset, maleYOffset, maleZOffset);
		}

		if (gender == 1 && (femaleYOffset != 0)) {
			model.translate(0, femaleYOffset, 0);
		}

		// Infernal capes
		// Reposition the capes on the player
		if (id == 19112) {
			model.translate(0, 0, 7);
		} else if (id == 19113) {
			model.translate(0, 0, 3);
		}

		if (editedModelColor != null && newModelColor != null) {
			for (int i1 = 0; i1 < editedModelColor.length; i1++) {
				model.recolour(editedModelColor[i1], newModelColor[i1]);
			}
		}
		return model;
	}

	public void setDefaults() {
		untradeable = false;
		modelID = 0;
		name = null;
		description = null;
		editedModelColor = null;
		newModelColor = null;
		modelZoom = 2000;
		rotationY = 0;
		rotationX = 0;
		rotationZ = 0;
		modelOffsetX = 0;
		modelOffsetY = 0;
		stackable = false;
		value = 0;
		membersObject = false;
		groundActions = null;
		actions = null;
		maleEquip1 = -1;
		maleEquip2 = -1;
		maleYOffset = 0;
		maleXOffset = 0;
		femaleEquip1 = -1;
		femaleEquip2 = -1;
		femaleYOffset = 0;
		maleEquip3 = -1;
		femaleEquip3 = -1;
		maleDialogue = -1;
		maleDialogueModel = -1;
		femaleDialogue = -1;
		femaleDialogueModel = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		sizeX = 128;
		sizeY = 128;
		sizeZ = 128;
		shadow = 0;
		lightness = 0;
		team = 0;
		lendID = -1;
		lentItemID = -1;
	}

	public static void unpackConfig(CacheArchive streamLoader) {
		stream = new Stream(streamLoader.getDataForName("obj.dat"));
		Stream stream = new Stream(streamLoader.getDataForName("obj.idx"));
		totalItems = stream.readUnsignedWord();
		streamIndices = new int[totalItems + 5000];
		int i = 2;
		for (int j = 0; j < totalItems; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}
		cache = new ItemDefinition[10];
		for (int k = 0; k < 10; k++) {
			cache[k] = new ItemDefinition();
		}
		setSettings();
	}

	public static ItemDefinition forID(int i) {
		for (int j = 0; j < 10; j++) {
			if (cache[j].id == i) {
				if (Configuration.DISCO_ITEMS) {
					if (i == 5572 || i == 5573 || i == 640 || i == 650 || i == 630 || i == 1121 || i == 544 || i == 542
						|| i == 1165 || i == 1125 || i == 1007 || i == 7332 || i == 1283 || i == 4125
							) {
						ItemDefinition.cache[j].newModelColor[0] = RandomColor.currentColour;
					}
				}
				return cache[j];
			}
		}
		cacheIndex = (cacheIndex + 1) % 10;
		ItemDefinition itemDef = cache[cacheIndex];
		if (i >= streamIndices.length) {
			itemDef.id = 1;
			itemDef.setDefaults();
			return itemDef;
		}
		stream.currentOffset = streamIndices[i];
		itemDef.id = i;
		itemDef.setDefaults();
		itemDef.readValues(stream);
		if (itemDef.certTemplateID != -1) {
			itemDef.toNote();
		}
		if (itemDef.lentItemID != -1) {
			itemDef.toLend();
		}
		if (itemDef.id == i && itemDef.editedModelColor == null) {
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 0;
			itemDef.newModelColor[0] = 1;
		}
		if (untradeableItems.contains(itemDef.id)) {
			itemDef.untradeable = true;
		}
		itemDef.shadow += 15;
		itemDef.value = prices[itemDef.id];
		
		itemDef.actions[4] = "Drop";
		

		switch (i) {
		
		//CUSTOM ITEMS


		case 11592:
			itemDef.modelID = 65504;
			itemDef.name = "Malevolent 2H Sword";
			itemDef.description = "It's a Malevolent 2H Sword.";
			itemDef.modelZoom = 1957;
			itemDef.modelOffsetY = -8;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 498;
			itemDef.rotationY = 444;
			itemDef.maleEquip1 = 65204; // done
			itemDef.femaleEquip1 = 65204;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11593:
			itemDef.modelID = 65201;
			itemDef.name = "Malevolent Guard";
			itemDef.description = "It's a Malevolent Guard.";
			itemDef.modelZoom = 1560;
			itemDef.modelOffsetY = -14;
			itemDef.modelOffsetX = -6;
			itemDef.rotationX = 1104;
			itemDef.rotationY = 344;
			itemDef.maleEquip1 = 65505; // done
			itemDef.femaleEquip1 = 65505;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11594:
			itemDef.modelID = 65492;
			itemDef.name = "Malevolent Sword";
			itemDef.description = "It's a Malevolent Sword.";
			itemDef.modelZoom = 1645;
			itemDef.modelOffsetX = 108;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1535;
			itemDef.maleEquip1 = 65491;
			itemDef.femaleEquip1 = 65491;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11595:
			itemDef.modelID = 65493;
			itemDef.name = "Malevolent Maul";
			itemDef.description = "It's a Malevolent Maul.";
			itemDef.modelZoom = 1957;
			itemDef.modelOffsetY = -8;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 498;
			itemDef.rotationY = 444;
			itemDef.maleEquip1 = 65494;
			itemDef.femaleEquip1 = 65494;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11596:
			itemDef.name = "Malevolent Gloves";
			itemDef.description = "It's a pair of Malevolent gloves.";
			itemDef.modelZoom = 930;
			itemDef.modelOffsetY = -8;
			itemDef.modelOffsetX = 8;
			itemDef.rotationX = 828;
			itemDef.rotationY = 420;
			itemDef.modelID = 65202; // 65495;
			itemDef.maleEquip1 = 65496;
			itemDef.femaleEquip1 = 65496;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11597:
			itemDef.name = "Malevolent Boots";
			itemDef.description = "It's a pair of Malevolent boots.";
			itemDef.modelID = 65497;
			itemDef.modelZoom = 770;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 156;
			itemDef.rotationY = 164;
			itemDef.maleEquip1 = 65497;
			itemDef.femaleEquip1 = 65497;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.description = "Some boots.";
			break;
		case 11598:
			itemDef.modelID = 65498;
			itemDef.name = "Malevolent Wings";
			itemDef.description = "It's a pair of Malevolent wings.";
			itemDef.modelZoom = 2130;
			itemDef.modelOffsetY = 12;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 992;
			itemDef.rotationY = 364;
			itemDef.maleEquip1 = 65499;
			itemDef.femaleEquip1 = 65499;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11599:
			itemDef.modelID = 65500;
			itemDef.name = "Sirenic Crossbow";
			itemDef.description = "It's a Sirenic Crossbow.";
			itemDef.modelZoom = 1200;
			itemDef.rotationX = 269;
			itemDef.rotationY = 323;
			itemDef.maleEquip1 = 65501;
			itemDef.femaleEquip1 = 65501;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11600:
			itemDef.modelID = 65502; // done
			itemDef.name = "Sirenic Buckler";
			itemDef.description = "It's a Sirenic Buckler.";
			itemDef.modelZoom = 625;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.maleEquip1 = 65470;
			itemDef.femaleEquip1 = 65470;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11601:
			itemDef.name = "Sirenic Gloves";
			itemDef.description = "It's a pair of Sirenic gloves.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.modelID = 65471;
			itemDef.maleEquip1 = 65472;
			itemDef.femaleEquip1 = 65472;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11602:
			itemDef.name = "Sirenic Boots";
			itemDef.description = "It's a pair of Sirenic boots.";
			itemDef.modelID = 65473;
			itemDef.modelZoom = 770;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 156;
			itemDef.rotationY = 164;
			itemDef.maleEquip1 = 65474;
			itemDef.femaleEquip1 = 65474;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.description = "Some boots.";
			break;
		case 11603:
			itemDef.modelID = 65475; // done
			itemDef.name = "Sirenic Tendrils"; // wings
			itemDef.description = "It's a pair of Sirenic Tendrils.";
			itemDef.modelZoom = 1700;
			itemDef.rotationY = 500;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 65456;
			itemDef.femaleEquip1 = 65456;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11604:
			itemDef.modelID = 65439;
			itemDef.name = "Seismic Defender";
			itemDef.description = "It's a Seismic Defender.";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.modelOffsetX = -2;
			itemDef.modelOffsetY = 3;
			itemDef.maleEquip1 = 65421;
			itemDef.femaleEquip1 = 65421;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11605:
			itemDef.modelID = 65423;
			itemDef.name = "Seismic Staff";
			itemDef.description = "It's a Seismic staff.";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.modelOffsetX = -2;
			itemDef.modelOffsetY = 3;
			itemDef.maleEquip1 = 65422;
			itemDef.femaleEquip1 = 65422;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11606:
			itemDef.name = "Tectonic Gloves";
			itemDef.description = "It's a pair of Tectonic gloves.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.modelID = 65424;
			itemDef.maleEquip1 = 65425;
			itemDef.femaleEquip1 = 65425;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11607:
			itemDef.name = "Tectonic Boots";
			itemDef.description = "It's a pair of Tectonic boots.";
			itemDef.modelID = 65426;
			itemDef.modelZoom = 770;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 156;
			itemDef.rotationY = 164;
			itemDef.maleEquip1 = 65427;
			itemDef.femaleEquip1 = 65427;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.description = "Some boots.";
			break;
		case 11608:
			itemDef.modelID = 65428; // done
			itemDef.name = "Tectonic Crystals"; // wings
			itemDef.description = "It's a pair of Tectonic Crystals.";
			itemDef.modelZoom = 1700;
			itemDef.rotationY = 500;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 65200;
			itemDef.femaleEquip1 = 65200;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11526:
			itemDef.modelID = 2573;
			itemDef.name = "@or2@Lava @bla@whip";
			itemDef.description = "Lava whip";
			itemDef.modelZoom = 840;
			itemDef.modelOffsetY = 56;
			itemDef.modelOffsetX = -2;
			itemDef.rotationY = 280;
			itemDef.maleEquip1 = 2778;
			itemDef.femaleEquip1 = 2778;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 11527:
			itemDef.modelID = 2531;
			itemDef.name = "@gre@Lime @bla@whip";
			itemDef.description = "lime whip";
			itemDef.modelZoom = 840;
			itemDef.modelOffsetY = 56;
			itemDef.modelOffsetX = -2;
			itemDef.rotationY = 280;
			itemDef.maleEquip1 = 2418;
			itemDef.femaleEquip1 = 2418;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		/*case 8845:
			itemDef.modelID = 15335;
			itemDef.name = "Ice defender";
			itemDef.description = "Ice defender";
			itemDef.modelZoom = 490;
			itemDef.modelOffsetY = -30;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 1882;
			itemDef.rotationY = 221;
			itemDef.maleEquip1 = 15413;
			itemDef.femaleEquip1 = 15413;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558 };
			itemDef.editedModelColor = new int[] { 28, 74 };
			break;
		case 1067:
			itemDef.modelID = 2582;
			itemDef.name = "Ice Platelegs";
			itemDef.description = "platelegs";
			itemDef.modelZoom = 1740;
			itemDef.modelOffsetY = -8;
			itemDef.rotationY = 444;
			itemDef.maleEquip1 = 268;
			itemDef.femaleEquip1 = 432;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 123, 32558, 123 };
			itemDef.editedModelColor = new int[] { 61, 41, 57 };
			break;
		case 1115:
			itemDef.modelID = 2605;
			itemDef.name = "Ice Platebody";
			itemDef.description = "platebody";
			itemDef.modelZoom = 1250;
			itemDef.modelOffsetX = -1;
			itemDef.rotationY = 488;
			itemDef.maleEquip1 = 306;
			itemDef.femaleEquip1 = 468;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 123, 32558, 123 };
			itemDef.editedModelColor = new int[] { 24, 61, 41 };
			break;
		case 1153:
			itemDef.modelID = 2813;
			itemDef.name = "Ice Full Helm";
			itemDef.description = "full helm";
			itemDef.modelZoom = 800;
			itemDef.modelOffsetY = 6;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 152;
			itemDef.rotationY = 160;
			itemDef.maleEquip1 = 218;
			itemDef.femaleEquip1 = 394;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 123 };
			itemDef.editedModelColor = new int[] { 61, 926 };
			break;
		case 4121:
			itemDef.modelID = 5037;
			itemDef.name = "Ice Boots";
			itemDef.description = "boots";
			itemDef.modelZoom = 770;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 156;
			itemDef.rotationY = 164;
			itemDef.maleEquip1 = 4954;
			itemDef.femaleEquip1 = 5031;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558, 123 };
			itemDef.editedModelColor = new int[] { 5648, 5400, 61 };
			break;
		case 1191:
			itemDef.modelID = 2339;
			itemDef.name = "Ice Kiteshield";
			itemDef.description = "kiteshield";
			itemDef.modelZoom = 1560;
			itemDef.modelOffsetY = -14;
			itemDef.modelOffsetX = -6;
			itemDef.rotationX = 1104;
			itemDef.rotationY = 344;
			itemDef.maleEquip1 = 486;
			itemDef.femaleEquip1 = 486;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 123, 32558, 32558 };
			itemDef.editedModelColor = new int[] { 7054, 61, 57 };
			break;
		case 1203:
			itemDef.modelID = 2672;
			itemDef.name = "Ice Dagger";
			itemDef.description = " dagger";
			itemDef.modelZoom = 760;
			itemDef.modelOffsetY = -4;
			itemDef.rotationX = 1276;
			itemDef.rotationY = 472;
			itemDef.maleEquip1 = 533;
			itemDef.femaleEquip1 = 533;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558, 32558, 32558 };
			itemDef.editedModelColor = new int[] { 61, 37, 74, 103 };
			break;
		case 1267:
			itemDef.modelID = 2529;
			itemDef.name = "Ice Pickaxe";
			itemDef.description = " pickaxe";
			itemDef.modelZoom = 1382;
			itemDef.modelOffsetY = 1;
			itemDef.modelOffsetX = -6;
			itemDef.rotationX = 997;
			itemDef.rotationY = 660;
			itemDef.maleEquip1 = 509;
			itemDef.femaleEquip1 = 509;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558, 32558, 32558, 32558, 32558, 32558, 32558, 32558 };
			itemDef.editedModelColor = new int[] { 61, 51, 76, 74, 60, 121, 99, 42, 41 };
			break;
		case 1349:
			itemDef.modelID = 2544;
			itemDef.name = "Ice Hatchet";
			itemDef.description = " hatchet";
			itemDef.modelZoom = 1060;
			itemDef.modelOffsetX = -15;
			itemDef.rotationX = 1196;
			itemDef.rotationY = 520;
			itemDef.maleEquip1 = 510;
			itemDef.femaleEquip1 = 510;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 61 };
			break;
		case 440:
			itemDef.modelID = 2748;
			itemDef.name = "Ice ore";
			itemDef.description = "ore";
			itemDef.modelZoom = 1400;
			itemDef.modelOffsetY = 15;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 1576;
			itemDef.rotationY = 368;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558 };
			itemDef.editedModelColor = new int[] { 7062, 45 };
			break;
		case 2351:
			itemDef.modelID = 2408;
			itemDef.name = "Ice bar";
			itemDef.description = "Bar";
			itemDef.modelZoom = 820;
			itemDef.modelOffsetY = -8;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 1180;
			itemDef.rotationY = 196;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 7062 };
			break;
		case 9177:
			itemDef.modelID = 16876;
			itemDef.name = "Ice crossbow";
			itemDef.description = " crossbow";
			itemDef.modelZoom = 850;
			itemDef.modelOffsetY = 1;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 269;
			itemDef.rotationY = 323;
			itemDef.maleEquip1 = 16846;
			itemDef.femaleEquip1 = 16846;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 123, 123, 123, 32558, 32558, 32558 };
			itemDef.editedModelColor = new int[] { 6447, 6443, 6439, 5656, 5652, 5904 };
			break;
		case 3192:
			itemDef.modelID = 2791;
			itemDef.name = "Ice halberd";
			itemDef.description = "Ice halberd";
			itemDef.modelZoom = 2130;
			itemDef.rotationX = 48;
			itemDef.rotationY = 604;
			itemDef.maleEquip1 = 555;
			itemDef.femaleEquip1 = 555;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 61 };
			break;
		case 3096:
			itemDef.modelID = 3781;
			itemDef.name = "Ice claws";
			itemDef.description = "claws";
			itemDef.modelZoom = 630;
			itemDef.modelOffsetY = -13;
			itemDef.modelOffsetX = -7;
			itemDef.rotationX = 1340;
			itemDef.rotationY = 268;
			itemDef.maleEquip1 = 3780;
			itemDef.femaleEquip1 = 42331;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558, 32558, 32558 };
			itemDef.editedModelColor = new int[] { 24, 33, 41, 49 };
			break;
		case 1420:
			itemDef.modelID = 2464;
			itemDef.name = "Ice mace";
			itemDef.description = "mace";
			itemDef.modelZoom = 1030;
			itemDef.modelOffsetY = -1;
			itemDef.rotationX = 1188;
			itemDef.rotationY = 392;
			itemDef.maleEquip1 = 502;
			itemDef.femaleEquip1 = 502;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 61 };
			break;
		case 1335:
			itemDef.modelID = 2731;
			itemDef.name = "Ice warhammer";
			itemDef.description = "warhammer";
			itemDef.modelZoom = 1050;
			itemDef.modelOffsetY = 3;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 456;
			itemDef.rotationY = 344;
			itemDef.maleEquip1 = 542;
			itemDef.femaleEquip1 = 542;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 61 };
			break;
		case 1309:
			itemDef.modelID = 2754;
			itemDef.name = "Ice 2h sword";
			itemDef.description = "2h sword";
			itemDef.modelZoom = 1711;
			itemDef.rotationX = 1513;
			itemDef.rotationY = 354;
			itemDef.maleEquip1 = 546;
			itemDef.femaleEquip1 = 546;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558, 32558, 32558, 32558, 32558 };
			itemDef.editedModelColor = new int[] { 75, 41, 127, 82, 48, 57 };
			break;
		case 1175:
			itemDef.modelID = 2720;
			itemDef.name = "Ice sq shield";
			itemDef.description = "sq shield";
			itemDef.modelZoom = 1410;
			itemDef.modelOffsetY = 174;
			itemDef.modelOffsetX = 2;
			itemDef.rotationX = 60;
			itemDef.rotationY = 268;
			itemDef.maleEquip1 = 541;
			itemDef.femaleEquip1 = 541;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 57 };
			break;
		case 807:
			itemDef.modelID = 2379;
			itemDef.name = "Ice dart";
			itemDef.description = "dart";
			itemDef.modelZoom = 720;
			itemDef.modelOffsetY = 11;
			itemDef.modelOffsetX = 8;
			itemDef.rotationX = 336;
			itemDef.rotationY = 396;
			itemDef.maleEquip1 = 492;
			itemDef.femaleEquip1 = 492;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 82 };
			itemDef.stackable = true;
			break;
		case 826:
			itemDef.modelID = 2763;
			itemDef.name = "Ice javelin";
			itemDef.description = "javelin";
			itemDef.modelZoom = 1470;
			itemDef.modelOffsetY = 63;
			itemDef.modelOffsetX = -2;
			itemDef.rotationX = 1964;
			itemDef.rotationY = 268;
			itemDef.maleEquip1 = 547;
			itemDef.femaleEquip1 = 547;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558 };
			itemDef.editedModelColor = new int[] { 61, 22451 };
			itemDef.stackable = true;
			break;
		case 863:
			itemDef.modelID = 2557;
			itemDef.name = "Ice knife";
			itemDef.description = "knife";
			itemDef.modelZoom = 1080;
			itemDef.modelOffsetX = 1860;
			itemDef.modelOffsetY = -26;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 112;
			itemDef.rotationY = 204;
			itemDef.maleEquip1 = 548;
			itemDef.femaleEquip1 = 548;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558 };
			itemDef.editedModelColor = new int[] { 61, 57 };
			itemDef.stackable = true;
			break;
		case 1239:
			itemDef.modelID = 2719;
			itemDef.name = "Ice spear";
			itemDef.description = "spear";
			itemDef.modelZoom = 1490;
			itemDef.modelOffsetX = 144;
			itemDef.modelOffsetY = -4;
			itemDef.modelOffsetX = 7;
			itemDef.rotationX = 112;
			itemDef.rotationY = 1960;
			itemDef.maleEquip1 = 540;
			itemDef.femaleEquip1 = 540;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558 };
			itemDef.editedModelColor = new int[] { 33, 28 };
			break;
		case 801:
			itemDef.modelID = 2752;
			itemDef.name = "Ice thrownaxe";
			itemDef.description = "thrownaxe";
			itemDef.modelZoom = 750;
			itemDef.modelOffsetY = 6;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 208;
			itemDef.rotationY = 388;
			itemDef.maleEquip1 = 545;
			itemDef.femaleEquip1 = 545;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558 };
			itemDef.editedModelColor = new int[] { 61, 57 };
			itemDef.stackable = true;
			break;
		case 1363:
			itemDef.modelID = 2778;
			itemDef.name = "Ice battleaxe";
			itemDef.description = "battleaxe";
			itemDef.modelZoom = 1180;
			itemDef.modelOffsetY = -2;
			itemDef.modelOffsetX = -7;
			itemDef.rotationX = 1128;
			itemDef.rotationY = 420;
			itemDef.maleEquip1 = 550;
			itemDef.femaleEquip1 = 550;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558 };
			itemDef.editedModelColor = new int[] { 61 };
			break;
		case 1279:
			itemDef.modelID = 2604;
			itemDef.name = "Ice sword";
			itemDef.description = "sword";
			itemDef.modelZoom = 1513;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1562;
			itemDef.maleEquip1 = 519;
			itemDef.femaleEquip1 = 519;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 32558, 32558, 32558, 32558, 32558, 32558 };
			itemDef.editedModelColor = new int[] { 127, 82, 75, 41, 48, 57 };
			break;*/
		case 695:
			itemDef.modelID = 2522;
			itemDef.name = "Staff of gods";
			itemDef.description = "godly staff";
			itemDef.modelZoom = 1874;
			itemDef.modelOffsetY = 2;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 1499;
			itemDef.rotationY = 292;
			itemDef.maleEquip1 = 2493;
			itemDef.femaleEquip1 = 2493;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 625:
			itemDef.modelID = 2536;
			itemDef.name = "Winter Camo Whip";
			itemDef.description = "It's a whip.";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.modelOffsetX = -2;
			itemDef.modelOffsetY = 3;
			itemDef.maleEquip1 = 24007;
			itemDef.femaleEquip1 = 24007;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 624:
			itemDef.modelID = 2560;
			itemDef.name = "Winter Camo Torva Platelegs";
			itemDef.description = "It's a set of torva platelegs.";
			itemDef.modelZoom = 1740;
			itemDef.rotationY = 474;
			itemDef.rotationX = 2045;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 2547;
			itemDef.femaleEquip1 = 2547;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 623:
			itemDef.modelID = 14342;
			itemDef.name = "Winter Camo Torva Fullhelm";
			itemDef.description = "It's a torva fullhelm.";
			itemDef.modelZoom = 676;
			itemDef.rotationY = 0;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -14;
			itemDef.maleEquip1 = 2684;
			itemDef.femaleEquip1 = 2684;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 621:
			itemDef.modelID = 2336;
			itemDef.name = "Winter Camo Torva Platebody";
			itemDef.description = "It's a torva platebody.";
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -8;
			itemDef.modelZoom = 1513;
			itemDef.rotationY = 566;
			itemDef.rotationX = 0;
			itemDef.maleEquip1 = 2575;
			itemDef.femaleEquip1 = 2575;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 620:
			itemDef.name = "Winter Camo Gloves";
			itemDef.description = "It's a pair of gloves.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.modelID = 2623;
			itemDef.maleEquip1 = 2714;
			itemDef.femaleEquip1 = 2714;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 619:
			itemDef.name = "Winter Camo Boots";
			itemDef.description = "It's a pair of boots.";
			itemDef.modelID = 14343;
			itemDef.modelZoom = 855;
			itemDef.rotationY = 215;
			itemDef.rotationX = 94;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = -32;
			itemDef.maleEquip1 = 14343;
			itemDef.femaleEquip1 = 14343;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.description = "Some boots.";
			break;
		case 618:
			itemDef.modelID = 2600;
			itemDef.name = "Winter Camo Wings";
			itemDef.description = "It's a pair of wings.";
			itemDef.modelZoom = 1700;
			itemDef.rotationY = 500;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 3155;
			itemDef.femaleEquip1 = 3155;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 665:
			itemDef.modelID = 2774;
			itemDef.name = "Bloodshot Camo Whip";
			itemDef.description = "It's a whip.";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.modelOffsetX = -2;
			itemDef.modelOffsetY = 3;
			itemDef.maleEquip1 = 1314;
			itemDef.femaleEquip1 = 1314;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.stackable = false;
			break;
		case 666:
			itemDef.modelID = 17291;
			itemDef.name = "Bloodshot Camo Torva Platelegs";
			itemDef.description = "It's a set of torva platelegs.";
			itemDef.modelZoom = 1740;
			itemDef.rotationY = 474;
			itemDef.rotationX = 2045;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 202;
			itemDef.femaleEquip1 = 202;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 669:
			itemDef.modelID = 17298;
			itemDef.name = "Bloodshot Camo Torva Fullhelm";
			itemDef.description = "It's a torva fullhelm.";
			itemDef.modelZoom = 676;
			itemDef.rotationY = 0;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -14;
			itemDef.maleEquip1 = 2385;
			itemDef.femaleEquip1 = 2385;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 670:
			itemDef.modelID = 17290;
			itemDef.name = "Bloodshot Camo Torva Platebody";
			itemDef.description = "It's a torva platebody.";
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -8;
			itemDef.modelZoom = 1513;
			itemDef.rotationY = 566;
			itemDef.rotationX = 0;
			itemDef.maleEquip1 = 17297;
			itemDef.femaleEquip1 = 17297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 671:
			itemDef.name = "Bloodshot Camo Gloves";
			itemDef.description = "It's a pair of gloves.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.modelID = 2786;
			itemDef.maleEquip1 = 2521;
			itemDef.femaleEquip1 = 2521;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 672:
			itemDef.name = "Bloodshot Camo Boots";
			itemDef.description = "It's a pair of boots.";
			itemDef.modelID = 2483;
			itemDef.modelZoom = 855;
			itemDef.rotationY = 215;
			itemDef.rotationX = 94;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = -32;
			itemDef.maleEquip1 = 2483;
			itemDef.femaleEquip1 = 2483;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.description = "Some boots.";
			break;
		case 673:
			itemDef.modelID = 377;
			itemDef.name = "Bloodshot Camo Wings";
			itemDef.description = "It's a pair of wings.";
			itemDef.modelZoom = 1700;
			itemDef.rotationY = 500;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 265;
			itemDef.femaleEquip1 = 265;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 3812:
			itemDef.modelID = 2438;
			itemDef.name = "Gold h'ween mask";
			itemDef.description = "Aaaarrrghhh ... I'm a monster.";
			itemDef.modelZoom = 730;
			itemDef.modelOffsetY = -10;
			itemDef.rotationY = 516;
			itemDef.maleEquip1 = 3188;
			itemDef.femaleEquip1 = 3192;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 7849 };
			itemDef.editedModelColor = new int[] { 1821 };
			break;
		case 3080:
			itemDef.modelID = 65381;
			itemDef.name = "Dark mace";
			itemDef.description = "Dark mace";
			itemDef.modelZoom = 1750;
			itemDef.modelOffsetY = 0;
			itemDef.rotationX = 0;
			itemDef.rotationY = 500;
			itemDef.maleEquip1 = 65380;
			itemDef.femaleEquip1 = 65380;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3305:
			itemDef.modelID = 65300;
		//	itemDef.name = "Bandos war spear";
			itemDef.name = "Godly war spear";

			itemDef.description = "Bandos war spear";
			itemDef.modelZoom = 2750;
			itemDef.modelOffsetX = 144;
			itemDef.modelOffsetY = 1;
			itemDef.modelOffsetX = 7;
			itemDef.rotationX = 112;
			itemDef.rotationY = 1960;
			itemDef.maleEquip1 = 65299;
			itemDef.femaleEquip1 = 65299;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3308:
			itemDef.modelID = 65318;
			itemDef.name = "Godly crossbow";
		//itemDef.name = "Bandos crossbow";

			itemDef.description = "crossbow";
			itemDef.modelZoom = 1250;
			itemDef.modelOffsetX = 20;
			itemDef.rotationX = 269;
			itemDef.rotationY = 323;
			itemDef.maleEquip1 = 65317;
			itemDef.femaleEquip1 = 65317;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 6806, 7054, 5002, 928, 928, 928 };
			itemDef.editedModelColor = new int[] { 6447, 6443, 6439, 5656, 5652, 5904 };
			break;
		case 3647:
			itemDef.modelID = 65445;
			itemDef.name = "Drygore Full Helmet";
			itemDef.description = "It's a drygore full helmet.";
			itemDef.modelZoom = 725;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65446;
			itemDef.femaleEquip1 = 65446;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3648:
			itemDef.modelID = 65443;
			itemDef.name = "Drygore Platebody";
			itemDef.description = "It's a drygore platebody.";
			itemDef.modelZoom = 1250;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65444;
			itemDef.femaleEquip1 = 65444;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3649:
			itemDef.modelID = 65441;
			itemDef.name = "Drygore Platelegs";
			itemDef.description = "It's a pair of drygore platelegs.";
			itemDef.modelZoom = 1000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65442;
			itemDef.femaleEquip1 = 65442;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3650:
			itemDef.modelID = 65440;
			itemDef.name = "Drygore Boots";
			itemDef.description = "It's a pair of drygore boots.";
			itemDef.modelZoom = 855;
			itemDef.rotationY = 215;
			itemDef.rotationX = 94;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = -32;
			itemDef.maleEquip1 = 65440;
			itemDef.femaleEquip1 = 65440;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3651:
			itemDef.modelID = 65437;
			itemDef.name = "Drygore Gauntlets";
			itemDef.description = "It's a pair of Drygore Gauntlets.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.maleEquip1 = 65438;
			itemDef.femaleEquip1 = 65438;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3652:
			itemDef.modelID = 65435;
			itemDef.name = "Drygore Wings";
			itemDef.description = "It's a pair of drygore wings.";
			itemDef.modelZoom = 1519;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65436;
			itemDef.femaleEquip1 = 65436;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3653:
			itemDef.modelID = 65433;
			itemDef.name = "Drygore Godsword";
			itemDef.description = "It's a drygore godsword.";
			itemDef.modelZoom = 1711;
			itemDef.rotationX = 1513;
			itemDef.rotationY = 354;
			itemDef.maleEquip1 = 65434;
			itemDef.femaleEquip1 = 65434;
			itemDef.actions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3654:
			itemDef.modelID = 65462;
			itemDef.name = "Purgatory Full Helmet";
			itemDef.description = "It's a purgatory full helmet.";
			itemDef.modelZoom = 1000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65461;
			itemDef.femaleEquip1 = 65461;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3655:
			itemDef.modelID = 65460;
			itemDef.name = "Purgatory Platebody";
			itemDef.description = "It's a purgatory platebody.";
			itemDef.modelZoom = 1400;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65459;
			itemDef.femaleEquip1 = 65459;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3656:
			itemDef.modelID = 65458;
			itemDef.name = "Purgatory Platelegs";
			itemDef.description = "It's a pair of purgatory platelegs.";
			itemDef.modelZoom = 1500;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65457;
			itemDef.femaleEquip1 = 65457;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3657:
			itemDef.modelID = 65455;
			itemDef.name = "Purgatory Boots";
			itemDef.description = "It's a pair of purgatory boots.";
			itemDef.modelZoom = 855;
			itemDef.rotationY = 215;
			itemDef.rotationX = 94;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = -32;
			itemDef.maleEquip1 = 65455;
			itemDef.femaleEquip1 = 65455;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3658:
			itemDef.modelID = 65454;
			itemDef.name = "Purgatory Gauntlets";
			itemDef.description = "It's a pair of purgatory gauntlets.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.maleEquip1 = 65453;
			itemDef.femaleEquip1 = 65453;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3659:
			itemDef.modelID = 65452;
			itemDef.name = "Purgatory Wings";
			itemDef.description = "It's a pair of purgatory wings.";
			itemDef.modelZoom = 1519;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65451;
			itemDef.femaleEquip1 = 65451;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3660:
			itemDef.modelID = 65450;
			itemDef.name = "Purgatory Longsword";
			itemDef.description = "It's a purgatory longsword.";
			itemDef.modelZoom = 1519;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65449;
			itemDef.femaleEquip1 = 65449;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3661:
			itemDef.modelID = 65448;
			itemDef.name = "Purgatory Offhand";
			itemDef.description = "It's a purgatory offhand.";
			itemDef.modelZoom = 1519;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65447;
			itemDef.femaleEquip1 = 65447;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 8675:
			itemDef.modelID = 65431;
			itemDef.name = "Cleaver Sword";
			itemDef.description = "Cleaver sword";
			itemDef.modelZoom = 550;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1562;
			itemDef.maleEquip1 = 65432;
			itemDef.femaleEquip1 = 65432;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 8677:
			itemDef.modelID = 65430;
			itemDef.name = "Sabre Sword";
			itemDef.description = "Sabre sword";
			itemDef.modelZoom = 550;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1562;
			itemDef.maleEquip1 = 65429;
			itemDef.femaleEquip1 = 65429;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3619:
			itemDef.modelID = 65420;
			itemDef.name = "Camouflage Whip";
			itemDef.description = "It's a Camouflage whip.";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.modelOffsetX = -2;
			itemDef.modelOffsetY = 3;
			itemDef.maleEquip1 = 65419;
			itemDef.femaleEquip1 = 65419;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 3641:
			itemDef.modelID = 65377;
			itemDef.name = "Blue Dragon chainbody";
			itemDef.description = "Blue Dragon chainbody";
			itemDef.modelZoom = 1100;
			itemDef.modelOffsetY = 2;
			itemDef.rotationY = 568;
			itemDef.maleEquip1 = 65376;
			itemDef.femaleEquip1 = 65376;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3642:
			itemDef.modelID = 65379;
			itemDef.name = "Blue Dragon Full Helm";
			itemDef.description = "Blue Dragon Full Helm";
			itemDef.modelZoom = 789;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 123;
			itemDef.rotationY = 135;
			itemDef.maleEquip1 = 65378;
			itemDef.femaleEquip1 = 65378;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 1 };
			itemDef.editedModelColor = new int[] { 0 };
			break;
		case 667:
			itemDef.modelID = 2503;
			itemDef.name = "Ragefire Beta Sword";
			itemDef.description = "Ragefire Beta Sword";
			itemDef.modelZoom = 1530;
			itemDef.modelOffsetY = -49;
			itemDef.modelOffsetX = 6;
			itemDef.rotationX = 408;
			itemDef.rotationY = 224;
			itemDef.maleEquip1 = 522;
			itemDef.femaleEquip1 = 522;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 5042, 63, 5039, 5038, 12, 5036 };
			itemDef.editedModelColor = new int[] { 43990, 127, 43228, 43107, 39818, 43321 };
			break;
		
		case 13506:
			itemDef.modelID = 2840;
			itemDef.name = "Blue Dragon sq shield";
			itemDef.description = "Dragon sq shield";
			itemDef.modelZoom = 1730;
			itemDef.modelOffsetY = 10;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 120;
			itemDef.rotationY = 352;
			itemDef.maleEquip1 = 565;
			itemDef.femaleEquip1 = 565;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 1, 60, 43803, 60 };
			itemDef.editedModelColor = new int[] { 0, 37, 924, 7054 };
			break;
		case 1231:
			itemDef.modelID = 2718;
			itemDef.name = "Blue Dragon dagger (P)";
			itemDef.description = "Dragon dagger";
			itemDef.modelZoom = 760;
			itemDef.modelOffsetY = -4;
			itemDef.rotationX = 1276;
			itemDef.rotationY = 472;
			itemDef.maleEquip1 = 539;
			itemDef.femaleEquip1 = 539;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43803 };
			itemDef.editedModelColor = new int[] { 924 };
			break;
		case 1249:
			itemDef.modelID = 2482;
			itemDef.name = "Blue Dragon spear";
			itemDef.description = "Dragon spear";
			itemDef.modelZoom = 1490;
			itemDef.modelOffsetX = 144;
			itemDef.modelOffsetY = -4;
			itemDef.modelOffsetX = 7;
			itemDef.rotationX = 112;
			itemDef.rotationY = 1960;
			itemDef.maleEquip1 = 505;
			itemDef.femaleEquip1 = 505;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43803 };
			itemDef.editedModelColor = new int[] { 924 };
			break;
		case 1263:
			itemDef.modelID = 2801;
			itemDef.name = "Blue Dragon battleaxe";
			itemDef.description = "Dragon battleaxe";
			itemDef.modelZoom = 1360;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 2020;
			itemDef.rotationY = 420;
			itemDef.maleEquip1 = 559;
			itemDef.femaleEquip1 = 559;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43803 };
			itemDef.editedModelColor = new int[] { 924 };
			break;
		case 13479:
			itemDef.modelID = 2391;
			itemDef.name = "Blue Dragon mace";
			itemDef.description = "Dragon mace";
			itemDef.modelZoom = 1440;
			itemDef.modelOffsetY = 13;
			itemDef.modelOffsetX = -13;
			itemDef.rotationX = 1388;
			itemDef.rotationY = 348;
			itemDef.maleEquip1 = 493;
			itemDef.femaleEquip1 = 493;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43803 };
			itemDef.editedModelColor = new int[] { 924 };
			break;
		case 13478:
			itemDef.modelID = 3886;
			itemDef.name = "Blue Dragon halberd";
			itemDef.description = "Dragon halberd";
			itemDef.modelZoom = 2160;
			itemDef.modelOffsetY = 12;
			itemDef.modelOffsetX = -1;
			itemDef.rotationY = 364;
			itemDef.maleEquip1 = 3885;
			itemDef.femaleEquip1 = 3885;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43803, 43797, 43793 };
			itemDef.editedModelColor = new int[] { 924, 918, 914 };
			break;
		case 13470:
			itemDef.modelID = 9959;
			itemDef.name = "Blue Dragon hatchet";
			itemDef.description = "Dragon hatchet";
			itemDef.modelZoom = 1450;
			itemDef.modelOffsetY = 6;
			itemDef.modelOffsetX = -6;
			itemDef.rotationX = 1288;
			itemDef.rotationY = 520;
			itemDef.maleEquip1 = 9957;
			itemDef.femaleEquip1 = 9957;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 1, 49, 43803, 43450 };
			itemDef.editedModelColor = new int[] { 0, 37, 924, 1467 };
			break;
		case 13462:
			itemDef.modelID = 28138;
			itemDef.name = "Blue Dragon boots";
			itemDef.description = "Dragon boots";
			itemDef.modelZoom = 595;
			itemDef.modelOffsetY = -1;
			itemDef.rotationY = 96;
			itemDef.maleEquip1 = 27738;
			itemDef.femaleEquip1 = 27754;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43805, 43791 };
			itemDef.editedModelColor = new int[] { 926, 912 };
			break;
		case 4180:
			itemDef.modelID = 5026;
			itemDef.name = "Blue Dragon platelegs";
			itemDef.description = "Dragon platelegs";
			itemDef.modelZoom = 1740;
			itemDef.modelOffsetY = -8;
			itemDef.rotationY = 444;
			itemDef.maleEquip1 = 5024;
			itemDef.femaleEquip1 = 5025;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43787, 43805, 43791 };
			itemDef.editedModelColor = new int[] { 908, 926, 912 };
			break;
		case 3072:
			itemDef.modelID = 10435;
			itemDef.name = "Lime Bobble hat";
			itemDef.description = "Bobble hat";
			itemDef.modelZoom = 512;
			itemDef.modelOffsetY = -1;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 270;
			itemDef.rotationY = 99;
			itemDef.maleEquip1 = 10532;
			itemDef.femaleEquip1 = 10543;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 22463 };
			itemDef.editedModelColor = new int[] { 7104 };
			break;
		case 3073:
			itemDef.modelID = 10436;
			itemDef.name = "Lime Bobble scarf";
			itemDef.description = "Bobble scarf";
			itemDef.modelZoom = 659;
			itemDef.modelOffsetY = -1;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 207;
			itemDef.rotationY = 511;
			itemDef.maleEquip1 = 10537;
			itemDef.femaleEquip1 = 10547;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 22463 };
			itemDef.editedModelColor = new int[] { 7104 };
			break;
	/*	case 14484:
			itemDef.modelID = 44590;
			itemDef.name = "Blue Dragon claws";
			itemDef.description = "Blue Dragon claws";
			itemDef.modelZoom = 789;
			itemDef.modelOffsetY = -23;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 60;
			itemDef.rotationY = 240;
			itemDef.maleEquip1 = 43660;
			itemDef.femaleEquip1 = 43651;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 43793, 43813, 43808, 43801, 43797 };
			itemDef.editedModelColor = new int[] { 914, 934, 929, 922, 918 };
			break;
*/
		case 910:
			itemDef.modelID = 40920;
			itemDef.name = "Mystical Spirit Shield";
			itemDef.description = "It's a mystical spirit shield";
			itemDef.newModelColor = new int[] { 32703, 33727, 34751, 35775, 36799, 37823, 38847, 39871, 43967, 40895,
					41919, 42943 };
			itemDef.editedModelColor = new int[] { 44635, 44612, 44606, 44615, 44641, 44564, 44575, 44618, 105, 44603,
					44570, 4500 };
			itemDef.modelZoom = 1616;
			itemDef.rotationY = 396;
			itemDef.rotationX = 1050;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 4;
			itemDef.maleEquip1 = 40940;
			itemDef.femaleEquip1 = 40940;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 911:
			itemDef.modelID = 40920;
			itemDef.name = "Deathly Spirit Shield";
			itemDef.description = "It's a deathly spirit shield";
			itemDef.editedModelColor = new int[13];
			itemDef.newModelColor = new int[13];
			itemDef.editedModelColor[0] = 44635;
			itemDef.newModelColor[0] = 959;
			itemDef.editedModelColor[1] = 44612;
			itemDef.newModelColor[1] = 1983;
			itemDef.editedModelColor[2] = 44606;
			itemDef.newModelColor[2] = 3007;
			itemDef.editedModelColor[3] = 44615;
			itemDef.newModelColor[3] = 4031;
			itemDef.editedModelColor[4] = 44641;
			itemDef.newModelColor[4] = 5055;
			itemDef.editedModelColor[5] = 44564;
			itemDef.newModelColor[5] = 6079;
			itemDef.editedModelColor[6] = 44575;
			itemDef.newModelColor[6] = 7103;
			itemDef.editedModelColor[7] = 44618;
			itemDef.newModelColor[7] = 8127;
			itemDef.editedModelColor[8] = 105;
			itemDef.newModelColor[8] = 0;
			itemDef.editedModelColor[9] = 44603;
			itemDef.newModelColor[9] = 9151;
			itemDef.editedModelColor[10] = 44570;
			itemDef.newModelColor[10] = 11199;
			itemDef.editedModelColor[11] = 4500;
			itemDef.newModelColor[11] = 12223;
			itemDef.modelZoom = 1616;
			itemDef.rotationY = 396;
			itemDef.rotationX = 1050;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 4;
			itemDef.maleEquip1 = 40940;
			itemDef.femaleEquip1 = 40940;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 912:
			itemDef.modelID = 40920;
			itemDef.name = "Lime Spirit Shield";
			itemDef.description = "It's a lime spirit shield";
			itemDef.editedModelColor = new int[13];
			itemDef.newModelColor = new int[13];
			itemDef.editedModelColor[0] = 44635;
			itemDef.newModelColor[0] = 22463;
			itemDef.editedModelColor[1] = 44612;
			itemDef.newModelColor[1] = 22463;
			itemDef.editedModelColor[2] = 44606;
			itemDef.newModelColor[2] = 22463;
			itemDef.editedModelColor[3] = 44615;
			itemDef.newModelColor[3] = 22463;
			itemDef.editedModelColor[4] = 44641;
			itemDef.newModelColor[4] = 22463;
			itemDef.editedModelColor[5] = 44564;
			itemDef.newModelColor[5] = 22463;
			itemDef.editedModelColor[6] = 44575;
			itemDef.newModelColor[6] = 22463;
			itemDef.editedModelColor[7] = 44618;
			itemDef.newModelColor[7] = 22463;
			itemDef.editedModelColor[8] = 105;
			itemDef.newModelColor[8] = 127;
			itemDef.editedModelColor[9] = 44603;
			itemDef.newModelColor[9] = 22463;
			itemDef.editedModelColor[10] = 44570;
			itemDef.newModelColor[10] = 22463;
			itemDef.editedModelColor[11] = 4500;
			itemDef.newModelColor[11] = 22463;
			itemDef.modelZoom = 1616;
			itemDef.rotationY = 396;
			itemDef.rotationX = 1050;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 4;
			itemDef.maleEquip1 = 40940;
			itemDef.femaleEquip1 = 40940;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 3091:
			itemDef.modelID = 40920;
			itemDef.name = "Pink Spirit Shield";
			itemDef.description = "It's a pink spirit shield";
			itemDef.editedModelColor = new int[13];
			itemDef.newModelColor = new int[13];
			itemDef.editedModelColor[0] = 44635;
			itemDef.newModelColor[0] = 54207;
			itemDef.editedModelColor[1] = 44612;
			itemDef.newModelColor[1] = 54207;
			itemDef.editedModelColor[2] = 44606;
			itemDef.newModelColor[2] = 54207;
			itemDef.editedModelColor[3] = 44615;
			itemDef.newModelColor[3] = 54207;
			itemDef.editedModelColor[4] = 44641;
			itemDef.newModelColor[4] = 54207;
			itemDef.editedModelColor[5] = 44564;
			itemDef.newModelColor[5] = 54207;
			itemDef.editedModelColor[6] = 44575;
			itemDef.newModelColor[6] = 54207;
			itemDef.editedModelColor[7] = 44618;
			itemDef.newModelColor[7] = 54207;
			itemDef.editedModelColor[8] = 105;
			itemDef.newModelColor[8] = 127;
			itemDef.editedModelColor[9] = 44603;
			itemDef.newModelColor[9] = 54207;
			itemDef.editedModelColor[10] = 44570;
			itemDef.newModelColor[10] = 54207;
			itemDef.editedModelColor[11] = 4500;
			itemDef.newModelColor[11] = 54207;
			itemDef.modelZoom = 1616;
			itemDef.rotationY = 396;
			itemDef.rotationX = 1050;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 4;
			itemDef.maleEquip1 = 40940;
			itemDef.femaleEquip1 = 40940;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 914:
			itemDef.modelID = 40920;
			itemDef.name = "Green Spirit Shield";
			itemDef.description = "It's a green spirit shield";
			itemDef.editedModelColor = new int[13];
			itemDef.newModelColor = new int[13];
			itemDef.editedModelColor[0] = 44635;
			itemDef.newModelColor[0] = 22425;
			itemDef.editedModelColor[1] = 44612;
			itemDef.newModelColor[1] = 22425;
			itemDef.editedModelColor[2] = 44606;
			itemDef.newModelColor[2] = 22425;
			itemDef.editedModelColor[3] = 44615;
			itemDef.newModelColor[3] = 22425;
			itemDef.editedModelColor[4] = 44641;
			itemDef.newModelColor[4] = 22425;
			itemDef.editedModelColor[5] = 44564;
			itemDef.newModelColor[5] = 22425;
			itemDef.editedModelColor[6] = 44575;
			itemDef.newModelColor[6] = 22425;
			itemDef.editedModelColor[7] = 44618;
			itemDef.newModelColor[7] = 22425;
			itemDef.editedModelColor[8] = 105;
			itemDef.newModelColor[8] = 127;
			itemDef.editedModelColor[9] = 44603;
			itemDef.newModelColor[9] = 22425;
			itemDef.editedModelColor[10] = 44570;
			itemDef.newModelColor[10] = 22425;
			itemDef.editedModelColor[11] = 4500;
			itemDef.newModelColor[11] = 22425;
			itemDef.modelZoom = 1616;
			itemDef.rotationY = 396;
			itemDef.rotationX = 1050;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 4;
			itemDef.maleEquip1 = 40940;
			itemDef.femaleEquip1 = 40940;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 3089:
			itemDef.modelID = 40920;
			itemDef.name = "Purple Spirit Shield";
			itemDef.description = "It's a purple spirit shield";
			itemDef.editedModelColor = new int[13];
			itemDef.newModelColor = new int[13];
			itemDef.editedModelColor[0] = 44635;
			itemDef.newModelColor[0] = 52127;
			itemDef.editedModelColor[1] = 44612;
			itemDef.newModelColor[1] = 52127;
			itemDef.editedModelColor[2] = 44606;
			itemDef.newModelColor[2] = 52127;
			itemDef.editedModelColor[3] = 44615;
			itemDef.newModelColor[3] = 52127;
			itemDef.editedModelColor[4] = 44641;
			itemDef.newModelColor[4] = 52127;
			itemDef.editedModelColor[5] = 44564;
			itemDef.newModelColor[5] = 52127;
			itemDef.editedModelColor[6] = 44575;
			itemDef.newModelColor[6] = 52127;
			itemDef.editedModelColor[7] = 44618;
			itemDef.newModelColor[7] = 52127;
			itemDef.editedModelColor[8] = 105;
			itemDef.newModelColor[8] = 127;
			itemDef.editedModelColor[9] = 44603;
			itemDef.newModelColor[9] = 52127;
			itemDef.editedModelColor[10] = 44570;
			itemDef.newModelColor[10] = 52127;
			itemDef.editedModelColor[11] = 4500;
			itemDef.newModelColor[11] = 52127;
			itemDef.modelZoom = 1616;
			itemDef.rotationY = 396;
			itemDef.rotationX = 1050;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 4;
			itemDef.maleEquip1 = 40940;
			itemDef.femaleEquip1 = 40940;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 896:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 32703;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Cyan santa hat";
			itemDef.description = "It's a cyan santa hat.";
			break;
		case 924:
			itemDef.modelID = 65490;
			itemDef.name = "Rainbow santa hat";
			itemDef.description = "Santa hat";
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65489;
			itemDef.femaleEquip1 = 65488;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 1 };
			itemDef.editedModelColor = new int[] { 0 };
			break;
		case 2380:
			itemDef.modelID = 65487;
			itemDef.name = "Striped santa hat";
			itemDef.description = "Santa hat";
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65486;
			itemDef.femaleEquip1 = 65485;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 1 };
			itemDef.editedModelColor = new int[] { 0 };
			break;
		case 909:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 5055;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Orange santa hat";
			itemDef.description = "It's a orange santa hat.";
			break;
		case 898:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 43967;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Blue santa hat";
			itemDef.description = "It's a blue santa hat.";
			break;
		case 899:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 54207;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Pink santa hat";
			itemDef.description = "It's a pink santa hat.";
			break;
		case 900:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 11199;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Yellow santa hat";
			itemDef.description = "It's a yellow santa hat.";
			break;
		case 901:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 22425;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Green santa hat";
			itemDef.description = "It's a green santa hat.";
			break;
		case 902:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 22463;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Lime santa hat";
			itemDef.description = "It's a lime santa hat.";
			break;
		case 903:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 52127;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Purple santa hat";
			itemDef.description = "It's a purple santa hat.";
			break;
		case 2548:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 52127;
			itemDef.modelID = 65465;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 65466;
			itemDef.femaleEquip1 = 65466;
			itemDef.name = "Camo partyhat";
			itemDef.description = "It's a camo partyhat.";
			break;
		case 2547:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 52127;
			itemDef.modelID = 65463;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65464;
			itemDef.femaleEquip1 = 65464;
			itemDef.name = "Camo santa hat";
			itemDef.description = "It's a camo santa hat.";
			break;
		case 904:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 52127;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.name = "Purple partyhat";
			itemDef.description = "It's a purple partyhat.";
			break;
		case 3088:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 5055;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.name = "Orange partyhat";
			itemDef.description = "It's a orange partyhat.";
			break;
		case 906:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 22463;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.name = "Lime partyhat";
			itemDef.description = "It's a lime partyhat.";
			break;
		case 907:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 54207;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.name = "Pink partyhat";
			itemDef.description = "It's a pink partyhat.";
			break;
		case 908:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 32703;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.name = "Cyan partyhat";
			itemDef.description = "It's a cyan partyhat.";
			break;
		case 926:
			itemDef.modelID = 65481;
			itemDef.name = "Rainbow partyhat";
			itemDef.description = "Rainbow partyhat";
			itemDef.modelZoom = 440;
			itemDef.modelOffsetY = 1;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 1845;
			itemDef.rotationY = 121;
			itemDef.maleEquip1 = 65480;
			itemDef.femaleEquip1 = 65479;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 1 };
			itemDef.editedModelColor = new int[] { 0 };
			break;
		case 3878:
			itemDef.modelID = 65484;
			itemDef.name = "Striped partyhat";
			itemDef.description = "Striped partyhat";
			itemDef.modelZoom = 440;
			itemDef.modelOffsetY = 1;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 1845;
			itemDef.rotationY = 121;
			itemDef.maleEquip1 = 65483;
			itemDef.femaleEquip1 = 65482;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 1 };
			itemDef.editedModelColor = new int[] { 0 };
			break;
		
		case 82:
			itemDef.modelID = 65507;
			itemDef.name = "@blu@Water Whip";
			itemDef.description = "It's a water whip.";
			itemDef.modelZoom = 987;
			itemDef.rotationY = 440;
			itemDef.rotationX = 630;
			itemDef.modelOffsetX = 8;
			itemDef.modelOffsetY = -1;
			itemDef.maleEquip1 = 65506;
			itemDef.femaleEquip1 = 65506;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 894:
			itemDef.modelID = 65509;
			itemDef.name = "@whi@Air Whip";
			itemDef.description = "It's a air whip.";
			itemDef.modelZoom = 1468;
			itemDef.rotationY = 504;
			itemDef.rotationX = 200;
			itemDef.modelOffsetX = 8;
			itemDef.modelOffsetY = -1;
			itemDef.maleEquip1 = 65510;
			itemDef.femaleEquip1 = 65510;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 895:
			itemDef.modelID = 65508;
			itemDef.name = "@gre@ Earth Whip";
			itemDef.description = "It's a earth whip.";
			itemDef.modelZoom = 840;
			itemDef.rotationY = 280;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 8;
			itemDef.modelOffsetY = -1;
			itemDef.maleEquip1 = 65503;
			itemDef.femaleEquip1 = 65503;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11609:
			itemDef.modelID = 65211;
			itemDef.name = "Bullets";
			itemDef.description = "Bullets";
			itemDef.modelZoom = 720;
			itemDef.modelOffsetY = 5;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 117;
			itemDef.rotationY = 477;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.stackable = true;
			break;
		case 20692:
			itemDef.modelID = 3683;
			itemDef.name = "Bullet box";
			itemDef.description = "It's a bullet box with 1k bullets in it!";
			itemDef.modelZoom = 3840;
			itemDef.modelOffsetY = -126;
			itemDef.modelOffsetX = 15;
			itemDef.rotationX = 1684;
			itemDef.rotationY = 108;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { "Open", null, null, null, "Drop" };
			break;
		case 6201:
			itemDef.modelID = 2426;
			itemDef.name = "Mega Mystery Box";
			itemDef.description = "Mega Mystery box";
			itemDef.modelZoom = 1180;
			itemDef.modelOffsetY = -14;
			itemDef.rotationX = 172;
			itemDef.rotationY = 160;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { "Open", null, null, null, null };
			itemDef.newModelColor = new int[] { 12, 6, 63 };
			itemDef.editedModelColor = new int[] { 22410, 926, 2999 };
			itemDef.stackable = true;
			break;
		case 20690:
			itemDef.modelID = 65526;
			itemDef.name = "Darth Maul Whip";
			itemDef.description = "It's a darth maul whip.";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.modelOffsetX = -2;
			itemDef.modelOffsetY = 3;
			itemDef.maleEquip1 = 65527;
			itemDef.femaleEquip1 = 65527;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14581:
			itemDef.modelID = 65524;
			itemDef.name = "Darth Maul Torva Platelegs";
			itemDef.description = "It's a set of darth maul torva platelegs.";
			itemDef.modelZoom = 1740;
			itemDef.rotationY = 474;
			itemDef.rotationX = 2045;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 65525;
			itemDef.femaleEquip1 = 65525;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14582:
			itemDef.modelID = 65522;
			itemDef.name = "Darth Maul Torva Fullhelm";
			itemDef.description = "It's a darth maul torva fullhelm.";
			itemDef.modelZoom = 676;
			itemDef.rotationY = 0;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -14;
			itemDef.maleEquip1 = 65523;
			itemDef.femaleEquip1 = 65523;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14583:
			itemDef.modelID = 65516;
			itemDef.name = "Darth Maul Torva Platebody";
			itemDef.description = "It's a darth maul torva platebody.";
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -8;
			itemDef.modelZoom = 1513;
			itemDef.rotationY = 566;
			itemDef.rotationX = 0;
			itemDef.maleEquip1 = 65517;
			itemDef.femaleEquip1 = 65517;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14584:
			itemDef.name = "Darth Maul Gloves";
			itemDef.description = "It's a pair of darth maul gloves.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.modelID = 65521;
			itemDef.maleEquip1 = 65520;
			itemDef.femaleEquip1 = 65520;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14585:
			itemDef.name = "Darth Maul Boots";
			itemDef.description = "It's a pair of darth maul boots.";
			itemDef.modelID = 65519;
			itemDef.modelZoom = 855;
			itemDef.rotationY = 215;
			itemDef.rotationX = 94;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = -32;
			itemDef.maleEquip1 = 65518;
			itemDef.femaleEquip1 = 65518;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.description = "Some boots.";
			break;
		case 14586:
			itemDef.modelID = 65529;
			itemDef.name = "Darth Maul Wings";
			itemDef.description = "It's a pair of darth maul wings.";
			itemDef.modelZoom = 1700;
			itemDef.rotationY = 500;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 65528;
			itemDef.femaleEquip1 = 65528;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 3090:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 7849;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationY = 72;
			itemDef.rotationX = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Gold santa hat";
			itemDef.description = "It's a gold santa hat.";
			break;
		case 3811:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			;
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 7849;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.name = "Gold partyhat";
			itemDef.description = "It's a gold partyhat.";
			break;

		case 3287:
			itemDef.modelID = 65356;
			itemDef.name = "Dragon slayer sword";
			itemDef.description = "Dragon slayer sword";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65355;
			itemDef.femaleEquip1 = 65355;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3288:
			itemDef.modelID = 65354;
			itemDef.name = "Saradomin halberd";
			itemDef.description = "Saradomin halberd";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 25;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65353;
			itemDef.femaleEquip1 = 65353;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;

		case 3290:
			itemDef.modelID = 65350;
			itemDef.name = "Thunder sword";
			itemDef.description = "Thunder sword";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65349;
			itemDef.femaleEquip1 = 65349;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
	
		case 3292:
			itemDef.name = "SAP Glove";
			itemDef.description = "It's a sap glove.";
			itemDef.modelZoom = 592;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1710;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.modelID = 65346;
			itemDef.maleEquip1 = 65345;
			itemDef.femaleEquip1 = 65345;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 3293:
			itemDef.name = "Clawed Glove";
			itemDef.description = "It's a clawed glove.";
			itemDef.modelZoom = 750;
			itemDef.rotationY = 323;
			itemDef.rotationX = 1250;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 5;
			itemDef.modelID = 65344;
			itemDef.maleEquip1 = 65343;
			itemDef.femaleEquip1 = 65343;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 3294:
			itemDef.modelID = 65342;
			itemDef.name = "Saradomin Kiteshield";
			itemDef.description = "kiteshield";
			itemDef.modelZoom = 1750;
			itemDef.modelOffsetY = -14;
			itemDef.modelOffsetX = -6;
			itemDef.rotationX = 1000;
			itemDef.rotationY = 344;
			itemDef.maleEquip1 = 65341;
			itemDef.femaleEquip1 = 65341;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3295:
			itemDef.modelID = 65340;
			itemDef.name = "Saradomin Kiteshield (G)";
			itemDef.description = "kiteshield (G)";
			itemDef.modelZoom = 1750;
			itemDef.modelOffsetY = -14;
			itemDef.modelOffsetX = -6;
			itemDef.rotationX = 1000;
			itemDef.rotationY = 344;
			itemDef.maleEquip1 = 65339;
			itemDef.femaleEquip1 = 65339;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 3296:
			itemDef.modelID = 65338;
			itemDef.name = "Saradomin longsword";
			itemDef.description = "Saradomin longsword";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65337;
			itemDef.femaleEquip1 = 65337;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3297:
			itemDef.modelID = 65336;
			itemDef.name = "Saradomin longsword (G)";
			itemDef.description = "Saradomin longsword (G)";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65335;
			itemDef.femaleEquip1 = 65335;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 3298:
			itemDef.modelID = 65334;
			itemDef.name = "Zamorak Wings";
			itemDef.description = "It's a pair of zamorak wings.";
			itemDef.modelZoom = 1700;
			itemDef.rotationY = 500;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 65333;
			itemDef.femaleEquip1 = 65333;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 3299:
			itemDef.name = "Dragon Rider Cape";
			itemDef.value = 60000;
			itemDef.maleEquip1 = 65331;
			itemDef.femaleEquip1 = 65331;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.modelOffsetX = -4;
			itemDef.modelID = 65332;
			itemDef.description = "A cape made of ancient, enchanted rocks.";
			itemDef.modelZoom = 2086;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.rotationZ = 0;
			itemDef.rotationY = 533;
			itemDef.rotationX = 333;
			break;
		case 6756:
			itemDef.modelID = 65330;
			itemDef.name = "Blue Dragon flail";
			itemDef.description = "Flail";
			itemDef.modelZoom = 1440;
			itemDef.modelOffsetY = 32;
			itemDef.rotationX = 352;
			itemDef.rotationY = 272;
			itemDef.maleEquip1 = 65329;
			itemDef.femaleEquip1 = 65329;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		
		case 11292:
			itemDef.modelID = 12277;
			itemDef.name = "Gold boxing gloves";
			itemDef.description = "Gold boxing gloves";
			itemDef.modelZoom = 789;
			itemDef.rotationZ = 69;
			itemDef.modelOffsetY = 15;
			itemDef.modelOffsetX = 4;
			itemDef.rotationX = 1674;
			itemDef.rotationY = 346;
			itemDef.maleEquip1 = 13317;
			itemDef.femaleEquip1 = 13329;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.newModelColor = new int[] { 30 };
			itemDef.editedModelColor = new int[] { 947 };
			break;
		case 3869:
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 65323;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 40090;
			itemDef.femaleEquip1 = 65328;
			itemDef.name = "Lava partyhat";
			itemDef.description = "It's a lava partyhat.";
			itemDef.newModelColor = new int[] { 59 };
			itemDef.editedModelColor = new int[] { 40 };
			break;

		case 84:
			itemDef.modelID = 47349;
			itemDef.name = "Magma shield";
			itemDef.description = "kiteshield";
			itemDef.modelZoom = 1450;
			itemDef.modelOffsetY = -14;
			itemDef.modelOffsetX = -6;
			itemDef.rotationX = 1825;
			itemDef.rotationY = 344;
			itemDef.maleEquip1 = 17120;
			itemDef.femaleEquip1 = 17120;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 4204:
			itemDef.modelID = 2838;
			itemDef.name = "Magma battleaxe";
			itemDef.description = "Magma battleaxe";
			itemDef.modelZoom = 1660;
			itemDef.modelOffsetY = -3;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 2020;
			itemDef.rotationY = 420;
			itemDef.maleEquip1 = 3902;
			itemDef.femaleEquip1 = 3902;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;

		case 758:
			itemDef.modelID = 65266;
			itemDef.name = "Star Katana";
			itemDef.description = "Star Katana";
			itemDef.modelZoom = 1845;
			itemDef.rotationZ = 108;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1535;
			itemDef.maleEquip1 = 65267;
			itemDef.femaleEquip1 = 65267;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 788:
			itemDef.modelID = 65273;
			itemDef.name = "Newt Blade";
			itemDef.description = "Newt Blade";
			itemDef.modelZoom = 1645;
			itemDef.rotationZ = 108;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1535;
			itemDef.maleEquip1 = 65272;
			itemDef.femaleEquip1 = 65272;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 983:
			itemDef.modelID = 65275;
			itemDef.name = "Tiger Katana";
			itemDef.description = "Tiger Katana";
			itemDef.modelZoom = 1845;
			itemDef.rotationZ = 108;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1535;
			itemDef.maleEquip1 = 65274;
			itemDef.femaleEquip1 = 65274;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;

		case 5:
			itemDef.modelID = 65279;
			itemDef.name = "Skairu Staff";
			itemDef.description = "Skairu Staff";
			itemDef.modelZoom = 1590;
			itemDef.rotationZ = 144;
			itemDef.modelOffsetY = -4;
			itemDef.modelOffsetX = 7;
			itemDef.rotationX = 112;
			itemDef.rotationY = 1960;
			itemDef.maleEquip1 = 65278;
			itemDef.femaleEquip1 = 65278;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;

		case 1544:
			itemDef.modelID = 65287;
			itemDef.name = "Wrathful Blade";
			itemDef.description = "Wrathful Blade";
			itemDef.modelZoom = 1645;
			itemDef.rotationZ = 108;
			itemDef.rotationX = 1778;
			itemDef.rotationY = 1535;
			itemDef.maleEquip1 = 65286;
			itemDef.femaleEquip1 = 65286;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;

		case 21077:
			itemDef.modelID = 42632; // done
			itemDef.name = "Dual Rapier";
			itemDef.description = "Dual Rapier";
			itemDef.modelZoom = 850;
			itemDef.modelOffsetY = -9;
			itemDef.modelOffsetX = 5;
			itemDef.rotationX = 1212;
			itemDef.rotationY = 272;
			itemDef.maleEquip1 = 42590;
			itemDef.femaleEquip1 = 42590;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;
		case 11586:
			itemDef.name = "White partyhat & specs";
			itemDef.modelZoom = 575;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42323; // done
			itemDef.maleEquip1 = 42322; // done
			itemDef.femaleEquip1 = 42322; // done
			break;
		case 11587:
			itemDef.name = "Orange partyhat & specs";
			itemDef.modelZoom = 575;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42323; // done
			itemDef.maleEquip1 = 42322; // done
			itemDef.femaleEquip1 = 42322; // done
			itemDef.newModelColor = new int[] { 5055, 5055 };
			itemDef.editedModelColor = new int[] { 13549, 15472 };
			break;
		case 11588:
			itemDef.name = "Cyan partyhat & specs";
			itemDef.modelZoom = 575;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42323; // done
			itemDef.maleEquip1 = 42322; // done
			itemDef.femaleEquip1 = 42322; // done
			itemDef.editedModelColor = new int[] { 13549, 15472 };
			itemDef.newModelColor = new int[] { 32703, 32703 };
			break;
		case 11589:
			itemDef.name = "Purple partyhat & specs";
			itemDef.modelZoom = 575;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42323; // done
			itemDef.maleEquip1 = 42322; // done
			itemDef.femaleEquip1 = 42322; // done
			itemDef.editedModelColor = new int[] { 13549, 15472 };
			itemDef.newModelColor = new int[] { 52127, 52127 };
			break;
		case 11590:
			itemDef.name = "Lime partyhat & specs";
			itemDef.modelZoom = 575;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42323; // done
			itemDef.maleEquip1 = 42322; // done
			itemDef.femaleEquip1 = 42322; // done
			itemDef.editedModelColor = new int[] { 13549, 15472 };
			itemDef.newModelColor = new int[] { 22463, 22463 };
			break;
		case 11591:
			itemDef.name = "Pink partyhat & specs";
			itemDef.modelZoom = 575;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.modelID = 42323; // done
			itemDef.maleEquip1 = 42322; // done
			itemDef.femaleEquip1 = 42322; // done
			itemDef.editedModelColor = new int[] { 13549, 15472 };
			itemDef.newModelColor = new int[] { 54207, 54207 };
			break;
			
		}
		itemDef = hardcodedItems(itemDef, i);
	//	if (GameClient.ironman > 0)
			if (itemDef.actions != null && itemDef.actions.length > 0)
				for (int a = 0; a < itemDef.actions.length; a++)
					if (itemDef.actions[a] != null && !itemDef.actions[a].isEmpty())
						if (itemDef.actions[a].equalsIgnoreCase("drop"))
							itemDef.actions[a] = "Drop";

		return itemDef;
	}

	public static ItemDefinition hardcodedItems(ItemDefinition itemDef, int id) {
		switch (id) {
		case 3287:
			itemDef.modelID = 65356;
			itemDef.name = "Dragon slayer sword";
			itemDef.description = "Dragon slayer sword";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 595;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 65355;
			itemDef.femaleEquip1 = 65355;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			break;

	



		// END OF CUSTOMS --------------------
		
		
		case 6685:
		case 6687:
		case 6689:
		case 6691:
			itemDef.shadow = 45;
			break;
		case 21009:
			itemDef.editedModelColor = new int[] { 61 };
			itemDef.newModelColor = new int[] { -29403 };
			itemDef.femaleEquip1 = 32676;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 32791;
			itemDef.maleEquip1 = 32676;
			itemDef.name = "Dragon sword";
			itemDef.rotationY = 691;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 478;
			itemDef.modelOffsetY = 1;
			itemDef.modelZoom = 1168;
			break;

		case 21275:
			itemDef.setDefaults();
			itemDef.name = "Dark claw";
			itemDef.editedModelColor = new int[] { 5656, 5652, 8536, 10343 };
			itemDef.newModelColor = new int[] { 33, 12, -15590, -15936 };
			itemDef.modelID = 19397;
			itemDef.modelZoom = 2086;
			itemDef.rotationX = 8;
			itemDef.rotationY = 420;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = 6;
			itemDef.actions = new String[] { null, null, null, null, null };
			break;

		case 19112:
			itemDef.setDefaults();
			itemDef.name = "Infernal cape";
			itemDef.modelID = 33144;
			itemDef.femaleEquip1 = 33111;
			itemDef.maleEquip1 = 33103;
			itemDef.modelZoom = 2086;
			itemDef.rotationX = 2031;
			itemDef.rotationY = 567;
			itemDef.modelOffsetX = -4;
			itemDef.modelOffsetY = 0;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			break;
		case 19113:
			itemDef.name = "Infernal max cape";
			itemDef.modelID = 33145;
			itemDef.femaleEquip1 = 33114;
			itemDef.maleEquip1 = 33102;
			itemDef.modelZoom = 2232;
			itemDef.rotationX = 27;
			itemDef.rotationY = 687;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -5;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			break;
		case 12695:
			itemDef.lightness = 15;
			itemDef.editedModelColor = new int[] { 61 };
			itemDef.newModelColor = new int[] { 21396 };
			itemDef.shadow = 15;
			itemDef.actions = new String[] { "Drink", null, null, "Empty", null };
			itemDef.modelID = 2789;
			itemDef.membersObject = true;
			itemDef.name = "Super combat potion (4)";
			itemDef.certID = 12696;
			itemDef.rotationY = 84;
			itemDef.rotationX = 1996;
			itemDef.modelOffsetY = -1;
			itemDef.modelZoom = 550;
			break;

		case 12696:
			itemDef.setDefaults();
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.name = "Super Combat Potion (4)";
			itemDef.modelID = 2429;
			itemDef.certID = 12695;
			itemDef.certTemplateID = 799;
			break;

		case 12697:
			itemDef.lightness = 15;
			itemDef.editedModelColor = new int[] { 61 };
			itemDef.newModelColor = new int[] { 21396 };
			itemDef.shadow = 15;
			itemDef.actions = new String[] { "Drink", null, null, "Empty", null };
			itemDef.modelID = 2697;
			itemDef.membersObject = true;
			itemDef.name = "Super combat potion (3)";
			itemDef.certID = 12698;
			itemDef.rotationY = 84;
			itemDef.rotationX = 1996;
			itemDef.modelOffsetY = -1;
			itemDef.modelZoom = 550;
			break;

		case 12698:
			itemDef.setDefaults();
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.name = "Super Combat Potion (3)";
			itemDef.certID = 12697;
			itemDef.certTemplateID = 799;
			break;

		case 12699:
			itemDef.lightness = 15;
			itemDef.editedModelColor = new int[] { 61 };
			itemDef.newModelColor = new int[] { 21396 };
			itemDef.shadow = 15;
			itemDef.actions = new String[] { "Drink", null, null, "Empty", null };
			itemDef.modelID = 2384;
			itemDef.membersObject = true;
			itemDef.name = "Super combat potion (2)";
			itemDef.certID = 12700;
			itemDef.rotationY = 84;
			itemDef.rotationX = 1996;
			itemDef.modelOffsetY = -1;
			itemDef.modelZoom = 550;
			break;

		case 12700:
			itemDef.setDefaults();
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.name = "Super Combat Potion (2)";
			itemDef.certID = 12699;
			itemDef.certTemplateID = 799;
			break;

		case 12701:
			itemDef.lightness = 15;
			itemDef.editedModelColor = new int[] { 61 };
			itemDef.newModelColor = new int[] { 21396 };
			itemDef.shadow = 15;
			itemDef.actions = new String[] { "Drink", null, null, "Empty", null };
			itemDef.modelID = 2621;
			itemDef.membersObject = true;
			itemDef.name = "Super combat potion (1)";
			itemDef.certID = 12702;
			itemDef.rotationY = 84;
			itemDef.rotationX = 1996;
			itemDef.modelOffsetY = -1;
			itemDef.modelZoom = 550;
			break;

		case 12702:
			itemDef.setDefaults();
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.name = "Super Combat Potion(1)";
			itemDef.certID = 12701;
			itemDef.certTemplateID = 799;
			break;

		case 8421:
			itemDef.setDefaults();
			itemDef.name = "Dragonbone spirit shield";
			itemDef.modelZoom = 1616; // Model Zoom
			itemDef.maleEquip1 = 38941; // Male Equip 1
			itemDef.femaleEquip1 = 38941; // Male Equip 2
			itemDef.modelID = 38942; // Model ID
			itemDef.rotationY = 396; // Model Rotation 1
			itemDef.rotationX = 1050; // Model Rotation 2
			itemDef.modelOffsetX = -3; // Model Offset 1
			itemDef.modelOffsetY = 16; // Model Offset 2
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 5573:
			ItemDefinition def = forID(1019);
			itemDef.name = "Disco cape";
			itemDef.description = def.description;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 7700;
			itemDef.newModelColor[0] = RandomColor.currentColour;
			itemDef.modelID = def.modelID;
			itemDef.modelZoom = def.modelZoom;
			itemDef.rotationY = def.rotationY;
			itemDef.rotationX = def.rotationX;
			itemDef.modelOffsetX = def.modelOffsetX;
			itemDef.modelOffsetY = def.modelOffsetY;
			itemDef.maleEquip1 = def.maleEquip1;
			itemDef.femaleEquip1 = def.femaleEquip1;
			// System.out.print("model "+def.maleEquip1);
			break;

		/**
		 * OSRS Bounty Hunter
		 * 
		 */

		case 18201:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			itemDef.stackAmounts = new int[] { 2, 3, 4, 5, 25, 100, 250, 1000, 10000, 0 };
			itemDef.stackIDs = new int[] { 3913, 3915, 3917, 3919, 3921, 3923, 3925, 3927, 3929, 0 };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2484;
			itemDef.modelZoom = 710;
			itemDef.rotationY = 184;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 3;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3913:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2485;
			itemDef.modelZoom = 710;
			itemDef.rotationY = 184;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 3;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3915:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2486;
			itemDef.modelZoom = 710;
			itemDef.rotationY = 184;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 3;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3917:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2487;
			itemDef.modelZoom = 710;
			itemDef.rotationY = 184;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 3;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3919:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2488;
			itemDef.modelZoom = 710;
			itemDef.rotationY = 184;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 3;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3921:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2667;
			itemDef.modelZoom = 710;
			itemDef.rotationY = 184;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 3;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3923:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2825;
			itemDef.modelZoom = 710;
			itemDef.rotationY = 184;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 3;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3925:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2423;
			itemDef.modelZoom = 650;
			itemDef.rotationY = 160;
			itemDef.rotationX = 2012;
			itemDef.modelOffsetX = 2;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;
		case 3927:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2710;
			itemDef.modelZoom = 980;
			itemDef.rotationY = 172;
			itemDef.rotationX = 64;
			itemDef.modelOffsetX = 11;
			itemDef.rotationZ = 13;
			itemDef.stackable = true;
			break;
		case 3929:
			itemDef.name = "Blood money";
			itemDef.actions = new String[] { "Use", null, null, null, "Drop" };
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 8128;
			itemDef.newModelColor[0] = 947;
			itemDef.modelID = 2775;
			itemDef.modelZoom = 1000;
			itemDef.rotationY = 168;
			itemDef.rotationX = 80;
			itemDef.modelOffsetX = 11;
			itemDef.rotationZ = 0;
			itemDef.stackable = true;
			break;

		case 12746:
			itemDef.modelID = 3677;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem";
			itemDef.modelZoom = 1221;
			itemDef.rotationX = 2047;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;

		case 12748:
			itemDef.modelID = 3680;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 2)";
			itemDef.modelZoom = 1032;
			itemDef.rotationX = 1912;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;

		case 12749:
			itemDef.modelID = 5446;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 3)";
			itemDef.modelZoom = 968;
			itemDef.rotationX = 1926;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -4;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 12750:
			itemDef.modelID = 3676;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 4)";
			itemDef.modelZoom = 1032;
			itemDef.rotationX = 202;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -4;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 12751:
			itemDef.modelID = 5448;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 5)";
			itemDef.modelZoom = 1221;
			itemDef.rotationX = 54;
			itemDef.rotationY = 13;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -1;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 12752:
			itemDef.modelID = 5447;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 6)";
			itemDef.modelZoom = 1221;
			itemDef.rotationX = 202;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = -1;
			itemDef.modelOffsetY = -1;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 12753:
			itemDef.modelID = 3678;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 7)";
			itemDef.modelZoom = 1221;
			itemDef.rotationX = 0;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = -4;
			itemDef.modelOffsetY = -5;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 12754:
			itemDef.modelID = 5444;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 8)";
			itemDef.modelZoom = 1411;
			itemDef.rotationX = 0;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = -5;
			itemDef.modelOffsetY = -4;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 12755:
			itemDef.modelID = 3679;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 9)";
			itemDef.modelZoom = 1284;
			itemDef.rotationX = 0;
			itemDef.rotationY = 135;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -1;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 12756:
			itemDef.modelID = 5445;
			itemDef.maleEquip1 = 0;
			itemDef.femaleEquip1 = 0;
			itemDef.name = "Mysterious emblem (tier 10)";
			itemDef.modelZoom = 1600;
			itemDef.rotationX = 108;
			itemDef.rotationY = 121;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -9;
			itemDef.editedModelColor = null;
			itemDef.newModelColor = null;
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;

		/**
		 * Treasure Island Keys
		 */
		case 18689:
			itemDef.name = "Key of blitz";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.description = "I wonder what this does..?";
			break;
		case 14678:
			itemDef.name = "Key of cobra";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.description = "I wonder what this does..?";
			break;
		case 13158:
			itemDef.name = "Key of fear";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.description = "I wonder what this does..?";
			break;
		case 13758:
			itemDef.name = "Key of death";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.description = "I wonder what this does..?";
			break;

		case 640:
			itemDef.name = "Disco Robe Top";
			itemDef.newModelColor[0] = RandomColor.currentColour;
			break;
		case 630:
			itemDef.name = "Disco Boots";
			itemDef.newModelColor[0] = RandomColor.currentColour;
			break;
		case 650:
			itemDef.name = "Disco Robe Bottom";
			itemDef.newModelColor[0] = RandomColor.currentColour;
			break;
		case 5572:
			itemDef.name = "Disco partyhat";
			itemDef.description = "A nice hat from a cracker.";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = RandomColor.currentColour;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationY = 121;
			itemDef.rotationX = 1845;
			itemDef.modelOffsetY = 1;
			itemDef.modelOffsetX = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			break;

		/** OSRS && NEW ITEMS **/
		case 13247:
			itemDef.modelID = 29240;
			itemDef.name = "Hellpuppy";
			itemDef.description = "It's a Hellpuppy.";
			itemDef.modelZoom = 1616;
			itemDef.rotationY = 3;
			itemDef.rotationX = 213;
			// itemDef.modelOffsetX = 5;
			// itemDef.modelOffsetY = 38;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;

		case 12646:
			itemDef.modelID = 12073;
			itemDef.name = "Baby mole";
			itemDef.description = "It's a Baby mole.";
			itemDef.modelZoom = 2256;
			itemDef.rotationY = 369;
			itemDef.rotationX = 1874;
			// itemDef.modelOffsetX = 5;
			// itemDef.modelOffsetY = 38;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;

		case 12655:
			itemDef.modelID = 28869;
			itemDef.name = "Pet kraken";
			itemDef.description = "It's a Pet kraken.";
			itemDef.modelZoom = 1744;
			itemDef.rotationY = 330;
			itemDef.rotationX = 1940;
			// itemDef.modelOffsetX = 5;
			// itemDef.modelOffsetY = 38;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;

		case 15444:// i changed it for the anim i think
			itemDef.modelID = 10247;
			itemDef.name = "Abyssal vine whip";
			itemDef.description = "Abyssal vine whip";
			itemDef.modelZoom = 848;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1808;
			itemDef.modelOffsetX = 5;
			itemDef.modelOffsetY = 38;
			itemDef.maleEquip1 = 10253;
			itemDef.femaleEquip1 = 10253;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			break;
		case 20010:
			itemDef.name = "Trickster robe";
			itemDef.description = "Its a Trickster robe";
			itemDef.maleEquip1 = 44786;
			itemDef.femaleEquip1 = 44786;
			itemDef.modelID = 45329;
			itemDef.rotationY = 593;
			itemDef.rotationX = 2041;
			itemDef.modelZoom = 1420;
			itemDef.modelOffsetY = 0;
			itemDef.modelOffsetX = 0;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;
		case 20011:
			itemDef.name = "Trickster robe legs";
			itemDef.description = "Its a Trickster robe";
			itemDef.maleEquip1 = 44770;
			itemDef.femaleEquip1 = 44770;
			itemDef.modelID = 45335;
			itemDef.rotationY = 567;
			itemDef.rotationX = 1023;
			itemDef.modelZoom = 2105;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;
		case 20012:
			itemDef.name = "Trickster helm";
			itemDef.description = "Its a Trickster helm";
			itemDef.maleEquip1 = 44764;
			itemDef.femaleEquip1 = 44764;
			itemDef.modelID = 45328;
			itemDef.rotationY = 5;
			itemDef.rotationX = 1889;
			itemDef.modelZoom = 738;
			itemDef.modelOffsetY = 0;
			itemDef.modelOffsetX = 0;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;
		case 20013:
			itemDef.modelID = 44633;
			itemDef.name = "Vanguard helm";
			itemDef.modelZoom = 855;
			itemDef.rotationY = 1966;
			itemDef.rotationX = 5;
			itemDef.modelOffsetY = 4;
			itemDef.modelOffsetX = -1;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44769;
			itemDef.femaleEquip1 = 44769;
			break;
		case 20014:
			itemDef.modelID = 44627;
			itemDef.name = "Vanguard body";
			itemDef.modelZoom = 1513;
			itemDef.rotationX = 2041;
			itemDef.rotationY = 593;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -11;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44812;
			itemDef.femaleEquip1 = 44812;
			break;

		case 14062:
			itemDef.modelID = 50011;
			itemDef.name = "Vanguard legs";
			itemDef.modelZoom = 1711;
			itemDef.rotationX = 0;
			itemDef.rotationY = 360;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -11;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44771;
			itemDef.femaleEquip1 = 44771;
			break;
		case 20016:
			itemDef.modelID = 44704;
			itemDef.name = "Battle-mage helm";
			itemDef.modelZoom = 658;
			itemDef.rotationX = 1898;
			itemDef.rotationY = 2;
			itemDef.modelOffsetX = 12;
			itemDef.modelOffsetY = 3;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44767;
			itemDef.femaleEquip1 = 44767;
			break;
		case 20017:
			itemDef.modelID = 44631;
			itemDef.name = "Battle-mage robe";
			itemDef.modelZoom = 1382;
			itemDef.rotationX = 3;
			itemDef.rotationY = 488;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = 0;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44818;
			itemDef.femaleEquip1 = 44818;
			break;
		case 20018:
			itemDef.modelID = 44672;
			itemDef.name = "Battle-mage robe legs";
			itemDef.modelZoom = 1842;
			itemDef.rotationX = 1024;
			itemDef.rotationY = 498;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = -1;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44775;
			itemDef.femaleEquip1 = 44775;
			break;
		case 20019:
			itemDef.modelID = 45316;
			itemDef.name = "Trickster boots";
			itemDef.modelZoom = 848;
			itemDef.rotationY = 141;
			itemDef.rotationX = 141;
			itemDef.modelOffsetX = -9;
			itemDef.modelOffsetY = 0;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44757;
			itemDef.femaleEquip1 = 44757;
			break;
		case 20020:
			itemDef.modelID = 45317;
			itemDef.name = "Trickster gloves";
			itemDef.modelZoom = 830;
			itemDef.rotationX = 150;
			itemDef.rotationY = 536;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = 3;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44761;
			itemDef.femaleEquip1 = 44761;
			break;
		case 20021:
			itemDef.modelID = 44662;
			itemDef.name = "Battle-mage boots";
			itemDef.modelZoom = 987;
			itemDef.rotationX = 1988;
			itemDef.rotationY = 188;
			itemDef.modelOffsetX = -8;
			itemDef.modelOffsetY = 5;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44755;
			itemDef.femaleEquip1 = 44755;
			break;
		case 20022:
			itemDef.modelID = 44573;
			itemDef.name = "Battle-mage gloves";
			itemDef.modelZoom = 1053;
			itemDef.rotationX = 0;
			itemDef.rotationY = 536;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 0;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44762;
			itemDef.femaleEquip1 = 44762;
			break;
		case 11554:
			itemDef.name = "Abyssal tentacle";
			itemDef.modelZoom = 840;
			itemDef.rotationY = 280;
			itemDef.rotationX = 121;
			itemDef.modelOffsetY = 56;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 28439;
			itemDef.maleEquip1 = 45006;
			itemDef.femaleEquip1 = 43500;
			break;
		case 11926:
			itemDef.name = "Odium ward";
			itemDef.modelZoom = 1200;
			itemDef.rotationY = 568;
			itemDef.rotationX = 1836;
			itemDef.rotationZ = 2;
			itemDef.modelOffsetY = 3;
			itemDef.newModelColor = new int[] { 15252 };
			itemDef.editedModelColor = new int[] { 908 };
			itemDef.modelID = 9354;
			itemDef.actions[1] = "Wield";
			itemDef.actions[4] = "Drop";
			itemDef.maleEquip1 = 9347;
			itemDef.femaleEquip1 = 9347;
			break;
		case 11290:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 689484;
			itemDef.modelID = 2438;
			itemDef.modelZoom = 730;
			itemDef.rotationY = 516;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -10;
			itemDef.maleEquip1 = 3188;
			itemDef.femaleEquip1 = 3192;
			itemDef.name = "Sky Blue h'ween Mask";
			itemDef.description = "Aaaarrrghhh... I'm a monster.";
			break;
		case 11291:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 6073;
			itemDef.modelID = 2438;
			itemDef.modelZoom = 730;
			itemDef.rotationY = 516;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -10;
			itemDef.maleEquip1 = 3188;
			itemDef.femaleEquip1 = 3192;
			itemDef.name = "Orange h'ween Mask";
			itemDef.description = "Aaaarrrghhh... I'm a monster.";
			break;
		
		case 11293:
			itemDef.name = "Yellow Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare yellow santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 11191;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11294:
			itemDef.name = "Sky Blye Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare sky blue santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 689484;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 12426:
			itemDef.modelID = 28633;
			itemDef.name = "3rd age longsword";
			itemDef.description = "3rd age longsword";
			itemDef.modelZoom = 1726;
			itemDef.rotationY = 1576;
			itemDef.rotationX = 242;
			itemDef.modelOffsetX = 5;
			itemDef.modelOffsetY = 4;
			itemDef.maleEquip1 = 28618;
			itemDef.femaleEquip1 = 28618;
			itemDef.femaleYOffset = 4;
			itemDef.maleYOffset = 4;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { null, "Wear", null, null, null, null };
			break;

		case 12437:
			itemDef.modelID = 28648;
			itemDef.name = "3rd age cloak";
			itemDef.description = "3rd age cloak";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 282;
			itemDef.rotationX = 962;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 28483;
			itemDef.femaleEquip1 = 28560;
			itemDef.maleXOffset = -3;
			itemDef.maleYOffset = -3;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { null, "Wear", null, null, null, null };
			break;
		case 11295:
			itemDef.name = "White Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare white santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 9583;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11289:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 9583;
			itemDef.modelID = 2438;
			itemDef.modelZoom = 730;
			itemDef.rotationY = 516;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -10;
			itemDef.maleEquip1 = 3188;
			itemDef.femaleEquip1 = 3192;
			itemDef.name = "White h'ween Mask";
			itemDef.description = "Aaaarrrghhh... I'm a monster.";
			break;
		case 11288:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 196608;
			itemDef.modelID = 2438;
			itemDef.modelZoom = 730;
			itemDef.rotationY = 516;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -10;
			itemDef.maleEquip1 = 3188;
			itemDef.femaleEquip1 = 3192;
			itemDef.name = "Black h'ween Mask";
			itemDef.description = "Aaaarrrghhh... I'm a monster.";
			break;
		case 11924:
			itemDef.name = "Malediction ward";
			itemDef.modelZoom = 1200;
			itemDef.rotationY = 568;
			itemDef.rotationX = 1836;
			itemDef.rotationZ = 2;
			itemDef.modelOffsetY = 3;
			itemDef.newModelColor = new int[] { -21608 };
			itemDef.editedModelColor = new int[] { 908 };
			itemDef.modelID = 9354;
			itemDef.actions[1] = "Wield";
			itemDef.actions[4] = "Drop";
			itemDef.maleEquip1 = 9347;
			itemDef.femaleEquip1 = 9347;
			break;
		case 12282:
			itemDef.femaleEquip1 = 14398;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 19220;
			itemDef.maleEquip1 = 14395;
			itemDef.name = "Serpentine helm";
			itemDef.rotationY = 215;
			itemDef.rotationX = 1724;
			itemDef.modelOffsetY = -17;
			itemDef.modelZoom = 700;
			itemDef.maleYOffset = -5;
			break;

		case 12279:
			itemDef.femaleEquip1 = 14426;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 29205;
			itemDef.maleEquip1 = 14424;
			itemDef.name = "Magma helm";
			itemDef.rotationY = 215;
			itemDef.rotationX = 1724;
			itemDef.modelOffsetY = -17;
			itemDef.modelZoom = 700;
			itemDef.maleYOffset = -5;
			break;
		case 12278:
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 29213;
			itemDef.maleEquip1 = 14421;
			itemDef.name = "Tanzanite helm";
			itemDef.rotationY = 215;
			itemDef.rotationX = 1724;
			itemDef.modelOffsetY = -17;
			itemDef.modelZoom = 700;
			itemDef.maleYOffset = -5;
			break;
		case -1:
			itemDef.name = "Pet King black dragon";
			ItemDefinition itemDef2 = ItemDefinition.forID(12458);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			break;
		case 13239:
			itemDef.cost = 75000;
			itemDef.femaleEquip1 = 29255;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 29397;
			itemDef.maleEquip1 = 29250;
			itemDef.members = true;
			itemDef.name = "Primordial boots";
			itemDef.notedID = 13240;
			itemDef.stockMarket = true;
			itemDef.rotationY = 147;
			itemDef.modelOffsetX = 5;
			itemDef.rotationX = 279;
			itemDef.modelOffsetY = -5;
			itemDef.modelZoom = 976;
			itemDef.anInt1879 = 14027;
			break;
		case 12708:
			itemDef.cost = 75000;
			itemDef.femaleEquip1 = 29253;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 29396;
			itemDef.maleEquip1 = 29252;
			itemDef.members = true;
			itemDef.name = "Pegasian boots";
			itemDef.notedID = 13238;
			itemDef.stockMarket = true;
			itemDef.rotationY = 147;
			itemDef.modelOffsetX = 5;
			itemDef.rotationX = 279;
			itemDef.modelOffsetY = -5;
			itemDef.modelZoom = 976;
			itemDef.anInt1879 = 14026;
			break;
		case 13235:
			itemDef.cost = 75000;
			itemDef.femaleEquip1 = 29254;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 29394;
			itemDef.maleEquip1 = 29249;
			itemDef.members = true;
			itemDef.name = "Eternal boots";
			itemDef.notedID = 13236;
			itemDef.stockMarket = true;
			itemDef.rotationY = 147;
			itemDef.modelOffsetX = 5;
			itemDef.rotationX = 279;
			itemDef.modelOffsetY = -5;
			itemDef.modelZoom = 976;
			itemDef.anInt1879 = 14025;
			break;
		case 20059:
			itemDef.name = "Drygore rapier";
			itemDef.modelZoom = 1145;
			itemDef.rotationX = 2047;
			itemDef.rotationY = 254;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = -45;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", "Check-charges", null, "Drop" };
			itemDef.modelID = 14000;
			itemDef.maleEquip1 = 14001;
			itemDef.femaleEquip1 = 14001;
			break;
		case 20057:
			itemDef.name = "Drygore longsword";
			itemDef.modelZoom = 1645;
			itemDef.rotationX = 377;
			itemDef.rotationY = 444;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 0;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", "Check-charges", null, "Drop" };
			itemDef.modelID = 14022;
			itemDef.maleEquip1 = 14023;
			itemDef.femaleEquip1 = 14023;
			break;
		case 20058:
			itemDef.name = "Drygore mace";
			itemDef.modelZoom = 1118;
			itemDef.rotationX = 28;
			itemDef.rotationY = 235;
			itemDef.modelOffsetX = -1;
			itemDef.modelOffsetY = -47;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", "Check-charges", null, "Drop" };
			itemDef.modelID = 14024;
			itemDef.maleEquip1 = 14025;
			itemDef.femaleEquip1 = 14025;
			break;
		/** END OF OSRS ITEMS **/
		case 19670:
			itemDef.name = "Vote scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
			itemDef.actions[2] = "Claim-All";
			break;
		case 6821:
			itemDef.name = "Magic Coin Orb";
			break;
		case 10034:
		case 10033:
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 13727:
			itemDef.actions = new String[] { null, null, null, null, "Drop" };
			break;
		case 6500:
			itemDef.setDefaults();
			itemDef.imitate(forID(9952));
			itemDef.name = "Charming imp";
			itemDef.stackable = false;
			// itemDef.rotationX = 85;
			// itemDef.rotationY = 1867;
			itemDef.actions = new String[] { null, null, "Check", "Config", "Drop" };
			break;
		case 13045:
			itemDef.name = "Abyssal bludgeon";
			itemDef.modelZoom = 2611;
			itemDef.rotationY = 1508;
			itemDef.rotationX = 552;
			// itemDef.rotationZ = 552;
			itemDef.modelOffsetY = 3;
			itemDef.modelOffsetX = -17;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
			itemDef.modelID = 29597;
			itemDef.maleEquip1 = 29424;
			itemDef.femaleEquip1 = 29424;
			break;
		case 13047:
			itemDef.name = "Abyssal dagger";
			itemDef.modelZoom = 1347;
			itemDef.rotationY = 1589;
			itemDef.rotationX = 781;
			itemDef.modelOffsetY = 3;
			itemDef.modelOffsetX = -5;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
			itemDef.modelID = 29598;
			itemDef.maleEquip1 = 29425;
			itemDef.femaleEquip1 = 29425;
			break;
		case 13049:
			itemDef.femaleEquip1 = 29426;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 29596;
			itemDef.maleEquip1 = 29426;
			itemDef.name = "Abyssal dagger (p++)";
			itemDef.rotationY = 1589;
			itemDef.modelOffsetX = -5;
			itemDef.rotationX = 781;
			itemDef.modelOffsetY = 3;
			itemDef.modelZoom = 1347;
			break;
		case 500:
			itemDef.modelID = 2635;
			itemDef.name = "Black Party Hat";
			itemDef.description = "Black Party Hat";
			itemDef.modelZoom = 440;
			itemDef.rotationX = 1845;
			itemDef.rotationY = 121;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 1;
			itemDef.stackable = false;
			itemDef.value = 1;
			itemDef.membersObject = true;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor = new int[] { 926 };
			break;
		case 11551:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor[0] = 6020;
			itemDef.editedModelColor[0] = 933;
			itemDef.modelID = 2537;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 72;
			itemDef.rotationY = 136;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.name = "Black santa hat";
			itemDef.description = "It's a Black santa hat.";
			break;
		case 13655:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.name = "Red Dragon Kiteshield";
			itemDef.description = "A rare, protective kiteshield.";
			itemDef.modelID = 13701;
			itemDef.modelZoom = 1560;
			itemDef.rotationY = 344;
			itemDef.rotationX = 1104;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = -14;
			itemDef.maleEquip1 = 13700;
			itemDef.femaleEquip1 = 13700;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			// itemDef.anInt188 = -1;
			// itemDef.anInt164 = -1;
			itemDef.maleDialogue = -1;
			itemDef.femaleDialogue = -1;
			break;
		case 12284:
			itemDef.name = "Toxic staff of the dead";
			itemDef.modelID = 19224;
			itemDef.modelZoom = 2150;
			itemDef.rotationX = 1010;
			itemDef.rotationY = 512;
			itemDef.femaleEquip1 = 14402;
			itemDef.maleEquip1 = 49001;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.actions[2] = "Check";
			itemDef.actions[4] = "Uncharge";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.editedModelColor = new int[1];
			itemDef.editedModelColor[0] = 17467;
			itemDef.newModelColor = new int[1];
			itemDef.newModelColor[0] = 21947;
			break;
		case 20726:
			itemDef.femaleEquip1 = 33273;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", "Inspect", "Empty", null };
			itemDef.modelID = 33263;
			itemDef.maleEquip1 = 33273;
			itemDef.name = "Ancient wyvern shield";
			itemDef.rotationY = 516;
			itemDef.rotationX = 1281;
			itemDef.modelZoom = 1936;
			itemDef.modelOffsetY = -5;

			break;
		case 20724:
			itemDef.actions = new String[] { "Invigorate", null, null, null, null };
			itemDef.modelID = 32298;
			itemDef.name = "Imbued heart";
			itemDef.rotationY = 96;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 1690;
			itemDef.modelOffsetY = -1;
			itemDef.modelZoom = 1168;
			break;
		case 12899:
			itemDef.femaleEquip1 = 14400;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", "Check", "Uncharge", null };
			itemDef.modelID = 19223;
			itemDef.maleEquip1 = 14400;
			itemDef.maleEquip2 = 14400;
			itemDef.name = "Trident of the swamp";
			itemDef.rotationY = 1549;
			itemDef.rotationX = 1818;
			itemDef.modelOffsetY = 9;
			itemDef.rotationZ = 377;
			itemDef.modelZoom = 2421;
			itemDef.maleYOffset = 5;
			itemDef.maleXOffset = -3;
			break;
		case 12285:
			itemDef.editedModelColor = new int[] { 17467, -22419, 7073, 61 };
			itemDef.newModelColor = new int[] { 18626, 18626, 18626, 18626 };
			itemDef.femaleEquip1 = 5232;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 2810;
			itemDef.maleEquip1 = 5232;
			itemDef.name = "Staff of the dead";
			itemDef.rotationY = 138;
			itemDef.modelOffsetX = -5;
			itemDef.rotationX = 1300;
			itemDef.modelOffsetY = 2;
			itemDef.modelZoom = 1490;
			break;

		/** clue scrolls **/
		case 2714:
			itemDef.name = "Casket";
			break;
		case 6183:
			itemDef.name = "@red@Donation Box";
			break;
		case 12632:
			itemDef.name = "100m Note";
			itemDef.actions = new String[] { "Claim", null, null, null, "Drop" };
			break;
		case 4202:
			itemDef.name = "Ring of Coins";
			break;
		case 2568:
			itemDef.name = "Ring of Wealthier";
			break;

		case 2677:
		case 2678:
		case 2679:
		case 2680:
		case 2681:
		case 2682:
		case 2683:
		case 2684:
		case 2685:
		case 2686:
		case 2687:
		case 2688:
		case 2689:
		case 2690:
		case 2691:
			itemDef.name = "Clue Scroll";
			break;

		case 13051:
			itemDef.name = "Armadyl crossbow";
			itemDef.modelZoom = 1325;
			itemDef.rotationY = 240;
			itemDef.rotationX = 110;
			itemDef.modelOffsetX = -6;
			itemDef.modelOffsetY = -40;
			itemDef.newModelColor = new int[] { 115, 107, 10167, 10171 };
			itemDef.editedModelColor = new int[] { 5409, 5404, 6449, 7390 };
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.modelID = 19967;
			itemDef.maleEquip1 = 19839;
			itemDef.femaleEquip1 = 19839;
			break;
		case 12927:
			itemDef.name = "Magma blowpipe";
			itemDef.modelZoom = 1158;
			itemDef.rotationY = 768;
			itemDef.rotationX = 189;
			itemDef.modelOffsetX = -7;
			itemDef.modelOffsetY = 4;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", "Check", "Unload", "Drop" };
			itemDef.newModelColor = new int[] { 8134, 5058, 926, 957, 3008, 1321, 86, 41, 49, 7110, 3008, 1317 };
			itemDef.editedModelColor = new int[] { 48045, 49069, 48055, 49083, 50114, 33668, 29656, 29603, 33674, 33690,
					33680, 33692 };
			itemDef.modelID = 19219;
			itemDef.maleEquip1 = 14403;
			itemDef.femaleEquip1 = 14403;
			break;
		case 12926:
			itemDef.femaleEquip1 = 14403;
			itemDef.maleYOffset = 7;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 19219;
			itemDef.maleEquip1 = 14403;
			itemDef.name = "Toxic blowpipe";
			itemDef.rotationY = 768;
			itemDef.modelOffsetX = -7;
			itemDef.rotationX = 189;
			itemDef.modelOffsetY = 4;
			itemDef.modelZoom = 1158;
			break;
		case 13058:
			itemDef.femaleEquip1 = 28230;
			itemDef.maleYOffset = 6;
			itemDef.actions = new String[] { null, "Wield", "Check", "Uncharge", null };
			itemDef.modelID = 28232;
			itemDef.maleEquip1 = 28230;
			itemDef.name = "Trident of the seas";
			itemDef.rotationY = 1505;
			itemDef.rotationX = 1850;
			itemDef.rotationZ = 290;
			itemDef.modelZoom = 2350;
			break;

		case 12601:
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 28824;
			itemDef.name = "Ring of the gods";
			itemDef.rotationY = 393;
			itemDef.modelOffsetX = -9;
			itemDef.rotationX = 1589;
			itemDef.modelOffsetY = -12;
			itemDef.modelZoom = 900;
			break;
		case 12602:
			itemDef.actions = new String[] { null, "Wear", "Uncharge", null, null };
			itemDef.modelID = 29222;
			itemDef.name = "Ring of the gods (i)";
			itemDef.rotationY = 393;
			itemDef.modelOffsetX = -9;
			itemDef.rotationX = 1589;
			itemDef.modelOffsetY = -12;
			itemDef.modelZoom = 900;
			break;
		case 12604:
			itemDef.actions = new String[] { null, "Wear", "Uncharge", null, null };
			itemDef.modelID = 23907;
			itemDef.name = "Tyrannical ring (i)";
			itemDef.rotationY = 285;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 1163;
			itemDef.modelZoom = 592;
			break;
		case 12603:
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 28823;
			itemDef.name = "Tyrannical ring";
			itemDef.rotationY = 285;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 1163;
			itemDef.modelZoom = 592;
			break;
		case 12605:
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 28825;
			itemDef.name = "Treasonous ring";
			itemDef.rotationY = 342;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 252;
			itemDef.modelOffsetY = -12;
			itemDef.modelZoom = 750;
			break;
		case 12606:
			itemDef.actions = new String[] { null, "Wear", "Uncharge", null, null };
			itemDef.modelID = 21853;
			itemDef.name = "Treasonous ring (i)";
			itemDef.rotationY = 342;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 252;
			itemDef.modelOffsetY = -12;
			itemDef.modelZoom = 750;
			break;
		case 20555:
			itemDef.name = "Dragon warhammer";
			itemDef.modelID = 4041;
			itemDef.rotationY = 1510;
			itemDef.rotationX = 1785;
			itemDef.modelZoom = 1600;
			itemDef.rotationZ = 821;
			itemDef.modelOffsetX = 9;
			itemDef.modelOffsetY = -4;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.maleEquip1 = 4037;
			itemDef.femaleEquip1 = 4038;
			itemDef.maleYOffset = 7;
			break;
		case 11613:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 13701;
			itemDef.modelZoom = 1560;
			itemDef.rotationY = 344;
			itemDef.rotationX = 1104;
			itemDef.modelOffsetY = -14;
			itemDef.rotationZ = 0;
			itemDef.maleEquip1 = 13700;
			itemDef.femaleEquip1 = 13700;
			itemDef.name = "Dragon Kiteshield";
			itemDef.description = "A Dragon Kiteshield!";
			break;

		case 11614:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 3288;
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 500;
			itemDef.rotationX = 0;
			itemDef.modelOffsetY = -6;
			itemDef.rotationZ = 1;
			itemDef.maleEquip1 = 3287;
			itemDef.femaleEquip1 = 3287;
			itemDef.name = "Death Cape";
			break;
		case 11995:
			itemDef.name = "Pet Chaos elemental";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1284;
			itemDef.rotationX = 175;
			itemDef.rotationY = 0;
			itemDef.modelID = 40852;
			itemDef.modelOffsetX = -66;
			itemDef.modelOffsetY = 75;
			itemDef.rotationZ = 1939;
			break;
		case 11996:
			itemDef.name = "Pet King black dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 2000;
			itemDef.rotationX = 0;
			itemDef.rotationY = 0;
			itemDef.modelID = 40858;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11997:
			itemDef.name = "Pet General graardor";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1872;
			itemDef.rotationX = 0;
			itemDef.rotationY = 0;
			itemDef.modelID = 40853;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11978:
			itemDef.name = "Pet TzTok-Jad";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 10000;
			itemDef.rotationX = 553;
			itemDef.rotationY = 0;
			itemDef.modelID = 40854;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = -30;
			itemDef.rotationZ = 0;
			break;
		case 12001:
			itemDef.name = "Pet Corporeal beast";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 10000;
			itemDef.rotationX = 553;
			itemDef.rotationY = 0;
			itemDef.modelID = 40955;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = -30;
			itemDef.rotationZ = 0;
			break;
		case 12002:
			itemDef.name = "Pet Kree'arra";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 976;
			itemDef.rotationX = 1892;
			itemDef.rotationY = 2042;
			itemDef.modelID = 40855;
			itemDef.modelOffsetX = -20;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 12003:
			itemDef.name = "Pet K'ril tsutsaroth";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1168;
			itemDef.rotationX = 0;
			itemDef.rotationY = 2012;
			itemDef.modelID = 40856;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 12004:
			itemDef.name = "Pet Commander zilyana";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1000;
			itemDef.rotationX = 1931;
			itemDef.rotationY = 9;
			itemDef.modelID = 40857;
			itemDef.modelOffsetX = 5;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 12005:
			itemDef.name = "Pet Dagannoth supreme";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 4560;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 9941;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 12006:
			itemDef.name = "Pet Dagannoth prime";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 4560;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 9941;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			itemDef.newModelColor = new int[] { 5931, 1688, 21530, 21534 };
			itemDef.editedModelColor = new int[] { 11930, 27144, 16536, 16540 };
			break;
		case 11990:
			itemDef.name = "Pet Dagannoth rex";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 4560;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 9941;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			itemDef.newModelColor = new int[] { 7322, 7326, 10403, 2595 };
			itemDef.editedModelColor = new int[] { 16536, 16540, 27144, 2477 };
			break;
		case 11991:
			itemDef.name = "Pet Frost dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 5060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 56767;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11992:
			itemDef.name = "Pet Tormented demon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 5060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 44733;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11993:
			itemDef.name = "Pet Kalphite queen";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 7060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 24597;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11994:
			itemDef.name = "Pet Slash bash";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 7060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 46141;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11989:
			itemDef.name = "Pet Phoenix";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 7060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 45412;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11988:
			itemDef.name = "Pet Bandos avatar";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 6060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 46058;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11987:
			itemDef.name = "Pet Nex";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 5000;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 62717;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11986:
			itemDef.name = "Pet Jungle strykewyrm";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 7060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 51852;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11985:
			itemDef.name = "Pet Desert strykewyrm";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 7060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 51848;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11984:
			itemDef.name = "Pet Ice strykewyrm";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 7060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 51847;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11983:
			itemDef.name = "Pet Green dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 5060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 49142;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11982:
			itemDef.name = "Pet Baby blue dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 3060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 57937;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11981:
			itemDef.name = "Pet Blue dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 5060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 49137;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11979:
			itemDef.name = "Pet Black dragon";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 5060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 14294;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;

		case 11967:
			itemDef.name = "Pet Skotizo";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 13000;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 31653;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11969:
			itemDef.name = "Pet Lizardman Shaman";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 8060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 4039;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11970:
			itemDef.name = "Pet WildyWyrm";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 6060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 63604;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11971:
			itemDef.name = "Pet Bork";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 6560;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 40590;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11972:
			itemDef.name = "Pet Barrelchest";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 5560;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 22790;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11973:
			itemDef.name = "Pet Abyssal Sire";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 12060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 29477;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11974:
			itemDef.name = "Pet Rock Crab";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 2060;
			itemDef.rotationX = 1868;
			itemDef.rotationY = 2042;
			itemDef.modelID = 4400;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.rotationZ = 0;
			break;
		case 11975:
			itemDef.name = "Pet Scorpia";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 3347;
			itemDef.rotationX = 189;
			itemDef.rotationY = 121;
			itemDef.modelID = 28293;
			itemDef.modelOffsetX = 12;
			itemDef.modelOffsetY = -100;
			itemDef.rotationZ = 0;
			break;

		case 11976:
			itemDef.name = "Pet Venenatis";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 4080;
			itemDef.rotationX = 67;
			itemDef.rotationY = 67;
			itemDef.modelID = 28294;
			itemDef.modelOffsetX = 9;
			itemDef.modelOffsetY = -4;
			itemDef.rotationZ = 0;
			break;
		case 14667:
			itemDef.name = "Zombie fragment";
			itemDef.modelID = ItemDefinition.forID(14639).modelID;
			break;
		case 15182:
			itemDef.actions[0] = "Bury";
			break;
		case 15084:
			itemDef.actions[0] = "Roll";
			itemDef.name = "Dice (up to 100)";
			itemDef2 = ItemDefinition.forID(15098);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;
			break;
		case 2996:
			itemDef.name = "Agility ticket";
			break;
		case 5510:
		case 5512:
		case 5509:
			itemDef.actions = new String[] { "Fill", null, "Empty", "Check", null, null };
			break;
		case 11998:
			itemDef.name = "Scimitar";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			break;
		case 11999:
			itemDef.name = "Scimitar";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 700;
			itemDef.rotationX = 0;
			itemDef.rotationY = 350;
			itemDef.modelID = 2429;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 11998;
			itemDef.certTemplateID = 799;
			break;
		case 10025:
			itemDef.name = "Charm Box";
			itemDef.actions = new String[] { "Open", null, null, null, null };
			break;
		case 1389:
			itemDef.name = "Staff";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			break;
		case 1390:
			itemDef.name = "Staff";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			break;
		case 17401:
			itemDef.name = "Damaged Hammer";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			break;
		case 17402:
			itemDef.name = "Damaged Hammer";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.modelID = 2429;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 17401;
			itemDef.certTemplateID = 799;
			break;
		case 15009:
			itemDef.name = "Gold Ring";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			break;
		case 20787:
			itemDef.modelID = 35118;
			itemDef.name = "Golden mining gloves";
			itemDef.modelZoom = 1049;
			itemDef.rotationX = 377;
			itemDef.rotationY = 970;
			itemDef.modelOffsetX = -1;
			itemDef.modelOffsetY = -1;
			itemDef.maleEquip1 = 35119;
			itemDef.femaleEquip1 = 35120;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 20788:
			itemDef.modelID = 35121;
			itemDef.name = "Golden mining boots";
			itemDef.modelZoom = 848;
			itemDef.rotationX = 177;
			itemDef.rotationY = 195;
			itemDef.modelOffsetX = 7;
			itemDef.modelOffsetY = -20;
			itemDef.maleEquip1 = 35122;
			itemDef.femaleEquip1 = 35123;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 20789:
			itemDef.modelID = 35124;
			itemDef.name = "Golden mining helmet";
			itemDef.modelZoom = 976;
			itemDef.rotationX = 132;
			itemDef.rotationY = 165;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 35125;
			itemDef.femaleEquip1 = 35126;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 20790:
			itemDef.modelID = 35127;
			itemDef.name = "Golden mining trousers";
			itemDef.modelZoom = 1616;
			itemDef.rotationX = 276;
			itemDef.rotationY = 1829;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 11;
			itemDef.maleEquip1 = 35128;
			itemDef.femaleEquip1 = 35129;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 11350:
			ItemDefinition itemDef21 = forID(11732);
			itemDef.modelID = itemDef21.modelID;
			itemDef.maleEquip1 = itemDef21.maleEquip1;
			itemDef.femaleEquip1 = itemDef21.femaleEquip1;
			itemDef.modelOffsetX = itemDef21.modelOffsetX;
			itemDef.rotationZ = itemDef21.rotationZ;
			itemDef.modelOffsetY = itemDef21.modelOffsetY;
			itemDef.rotationY = itemDef21.rotationY;
			itemDef.rotationX = itemDef21.rotationX;
			itemDef.modelZoom = itemDef21.modelZoom;
			itemDef.name = "Green Dragon boots ";
			itemDef.actions = itemDef21.actions;

			itemDef.editedModelColor = new int[] { 926 };
			itemDef.newModelColor = new int[] { 419770 };

			itemDef.stackable = false;

			break;
		case 11352:
			ItemDefinition itemDef2111 = forID(11732);
			itemDef.modelID = itemDef2111.modelID;
			itemDef.maleEquip1 = itemDef2111.maleEquip1;
			itemDef.femaleEquip1 = itemDef2111.femaleEquip1;
			itemDef.modelOffsetX = itemDef2111.modelOffsetX;
			itemDef.rotationZ = itemDef2111.rotationZ;
			itemDef.modelOffsetY = itemDef2111.modelOffsetY;
			itemDef.rotationY = itemDef2111.rotationY;
			itemDef.rotationX = itemDef2111.rotationX;
			itemDef.modelZoom = itemDef2111.modelZoom;
			itemDef.name = "Blue Dragon boots ";
			itemDef.actions = itemDef2111.actions;

			itemDef.editedModelColor = new int[] { 926 };
			itemDef.newModelColor = new int[] { 302770 };
			itemDef.stackable = false;

			break;
		case 11351:
			ItemDefinition itemDef211 = forID(11732);
			itemDef.modelID = itemDef211.modelID;
			itemDef.maleEquip1 = itemDef211.maleEquip1;
			itemDef.femaleEquip1 = itemDef211.femaleEquip1;
			itemDef.modelOffsetX = itemDef211.modelOffsetX;
			itemDef.rotationZ = itemDef211.rotationZ;
			itemDef.modelOffsetY = itemDef211.modelOffsetY;
			itemDef.rotationY = itemDef211.rotationY;
			itemDef.rotationX = itemDef211.rotationX;
			itemDef.modelZoom = itemDef211.modelZoom;
			itemDef.name = "Lava Dragon boots ";
			itemDef.actions = itemDef211.actions;

			itemDef.editedModelColor = new int[] { 926 };
			itemDef.newModelColor = new int[] { 461770 };
			itemDef.stackable = false;

			break;

		case 11353:
			ItemDefinition itemDef211111 = forID(11732);
			itemDef.modelID = itemDef211111.modelID;
			itemDef.maleEquip1 = itemDef211111.maleEquip1;
			itemDef.femaleEquip1 = itemDef211111.femaleEquip1;
			itemDef.modelOffsetX = itemDef211111.modelOffsetX;
			itemDef.rotationZ = itemDef211111.rotationZ;
			itemDef.modelOffsetY = itemDef211111.modelOffsetY;
			itemDef.rotationY = itemDef211111.rotationY;
			itemDef.rotationX = itemDef211111.rotationX;
			itemDef.modelZoom = itemDef211111.modelZoom;
			itemDef.name = "Pink Dragon boots ";
			itemDef.actions = itemDef211111.actions;
			itemDef.editedModelColor = new int[] { 926 };
			itemDef.newModelColor = new int[] { 123770 };
			itemDef.stackable = false;
			break;
		case 12711:
			itemDef.femaleEquip1 = 31237;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 31522;
			itemDef.maleEquip1 = 31237;
			itemDef.name = "Light ballista";
			itemDef.rotationY = 189;
			itemDef.modelOffsetX = 8;
			itemDef.rotationX = 148;
			itemDef.modelOffsetY = -18;
			itemDef.modelZoom = 1250;
			break;
		case 12712:
			itemDef.name = "Heavy ballista";
			itemDef.description = "It's a Heavy ballista.";
			itemDef.modelZoom = 1284;
			itemDef.rotationY = 189;
			itemDef.rotationX = 148;
			itemDef.modelOffsetX = 8;
			itemDef.modelOffsetY = -18;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.modelID = 31523;
			itemDef.maleEquip1 = 31236;
			itemDef.femaleEquip1 = 31236;
			break;

		case 19592:
			itemDef.cost = 50000;
			itemDef.modelID = 31501;
			itemDef.members = true;
			itemDef.name = "Ballista limbs";
			itemDef.notedID = 19593;
			itemDef.stockMarket = true;
			itemDef.rotationY = 512;
			itemDef.modelOffsetX = 4;
			itemDef.rotationZ = 337;
			itemDef.modelZoom = 779;
			itemDef.anInt1879 = 19594;
			break;

		case 19595:
			itemDef.cost = 50000;
			itemDef.modelID = 31527;
			itemDef.members = true;
			itemDef.name = "Incomplete light ballista";
			itemDef.notedID = 19596;
			itemDef.stockMarket = true;
			itemDef.rotationY = 144;
			itemDef.modelOffsetX = 24;
			itemDef.rotationX = 666;
			itemDef.modelOffsetY = 1;
			itemDef.modelZoom = 968;
			itemDef.anInt1879 = 19597;
			break;

		case 19598:
			itemDef.cost = 50000;
			itemDef.modelID = 31505;
			itemDef.members = true;
			itemDef.name = "Incomplete heavy ballista";
			itemDef.notedID = 19599;
			itemDef.stockMarket = true;
			itemDef.rotationY = 81;
			itemDef.modelOffsetX = 9;
			itemDef.rotationX = 699;
			itemDef.modelOffsetY = 12;
			itemDef.modelZoom = 968;
			itemDef.anInt1879 = 19600;
			break;

		case 19601:
			itemDef.cost = 50000;
			itemDef.modelID = 31518;
			itemDef.members = true;
			itemDef.name = "Ballista spring";
			itemDef.notedID = 19602;
			itemDef.stockMarket = true;
			itemDef.rotationY = 45;
			itemDef.modelOffsetX = 5;
			itemDef.rotationX = 1124;
			itemDef.modelOffsetY = -11;
			itemDef.modelZoom = 779;
			itemDef.anInt1879 = 19603;
			break;

		case 19604:
			itemDef.cost = 50000;
			itemDef.modelID = 31522;
			itemDef.members = true;
			itemDef.name = "Unstrung light ballista";
			itemDef.notedID = 19605;
			itemDef.stockMarket = true;
			itemDef.rotationY = 72;
			itemDef.modelOffsetX = 11;
			itemDef.rotationX = 151;
			itemDef.modelOffsetY = -12;
			itemDef.modelZoom = 1250;
			itemDef.anInt1879 = 19606;
			break;

		case 19607:
			itemDef.cost = 50000;
			itemDef.modelID = 31523;
			itemDef.members = true;
			itemDef.name = "Unstrung heavy ballista";
			itemDef.notedID = 19608;
			itemDef.stockMarket = true;
			itemDef.rotationY = 72;
			itemDef.modelOffsetX = 11;
			itemDef.rotationX = 151;
			itemDef.modelOffsetY = -12;
			itemDef.modelZoom = 1250;
			itemDef.anInt1879 = 19609;
			break;

		case 19610:
			itemDef.cost = 50000;
			itemDef.modelID = 31507;
			itemDef.members = true;
			itemDef.name = "Monkey tail";
			itemDef.notedID = 19611;
			itemDef.stockMarket = true;
			itemDef.rotationY = 99;
			itemDef.modelOffsetX = -5;
			itemDef.rotationX = 84;
			itemDef.modelZoom = 400;
			itemDef.anInt1879 = 19612;
			break;
		case 12713:
			itemDef.editedModelColor = new int[] { 22451 };
			itemDef.newModelColor = new int[] { 10456 };
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 31511;
			itemDef.name = "Dragon javelin";
			itemDef.rotationY = 268;
			itemDef.modelOffsetX = -2;
			itemDef.rotationX = 1964;
			itemDef.modelOffsetY = 63;
			itemDef.modelZoom = 1470;
			break;
		// Raid items
		case 21034:
			itemDef.actions = new String[] { "Read", null, null, null, null };
			itemDef.modelID = 32770;
			itemDef.name = "Dexterous prayer scroll";
			itemDef.rotationY = 364;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 1454;
			itemDef.modelZoom = 1032;
			break;
		case 21035:
			itemDef.editedModelColor = new int[] { 5563 };
			itemDef.newModelColor = new int[] { 5351 };
			itemDef.actions = new String[] { "Read", null, null, null, null };
			itemDef.modelID = 32770;
			itemDef.name = "Arcane prayer scroll";
			itemDef.rotationY = 364;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 1454;
			itemDef.modelZoom = 1032;
			break;
		case 13227:
			itemDef.setDefaults();
			itemDef.name = "Eternal crystal";
			itemDef.editedModelColor = new int[] { 5656, 5652, 8536, 10343 };
			itemDef.newModelColor = new int[] { 33, 12, -15590, -15936 };
			itemDef.modelID = 29264;
			itemDef.modelZoom = 740;
			itemDef.rotationX = 225;
			itemDef.rotationY = 429;
			itemDef.modelOffsetX = 5;
			itemDef.modelOffsetY = 5;
			itemDef.actions = new String[] { null, null, null, null, null };
			break;
		case 13231:
			itemDef.modelID = 29263;
			itemDef.name = "Primordial crystal";
			itemDef.notedID = 13232;
			itemDef.stockMarket = true;
			itemDef.rotationY = 429;
			itemDef.modelOffsetX = 5;
			itemDef.rotationX = 225;
			itemDef.modelOffsetY = 5;
			itemDef.modelZoom = 740;
			itemDef.anInt1879 = 14023;
			itemDef.actions = new String[] { null, null, null, null, null };

			break;

		case 13229:
			itemDef.modelID = 29261;
			itemDef.name = "Pegasian crystal";
			itemDef.notedID = 13230;
			itemDef.stockMarket = true;
			itemDef.rotationY = 429;
			itemDef.modelOffsetX = 5;
			itemDef.rotationX = 225;
			itemDef.modelOffsetY = 5;
			itemDef.modelZoom = 740;
			itemDef.anInt1879 = 14022;
			itemDef.actions = new String[] { null, null, null, null, null };

			break;

		case 12849:
			itemDef.cost = 5000;
			itemDef.modelID = 28994;
			itemDef.members = true;
			itemDef.name = "Granite clamp";
			itemDef.notedID = 12850;
			itemDef.stockMarket = true;
			itemDef.modelOffsetX = -22;
			itemDef.modelOffsetY = -7;
			itemDef.rotationZ = 1818;
			itemDef.rotationX = 0;
			itemDef.rotationY = 0;
			itemDef.actions = new String[] { null, null, null, null, null };
			itemDef.modelZoom = 1280;
			itemDef.anInt1879 = 15098;
			break;
		case 12851:
			itemDef.cost = 10000;
			itemDef.actions = new String[] { "Open", null, null, "Empty", "Drop" };
			itemDef.modelID = 5453;
			itemDef.members = true;
			itemDef.name = "Rune pouch";
			itemDef.rotationY = 512;
			itemDef.modelOffsetX = -4;
			itemDef.rotationX = 475;
			itemDef.rotationZ = 59;
			itemDef.modelZoom = 350;
			itemDef.anInt1879 = 15092;
			break;
		case 12853:
			itemDef.editedModelColor = new int[] { 6430, 7467, 6798, 7223 };
			itemDef.newModelColor = new int[] { -32729, -32738, -32750, -6867 };
			itemDef.cost = 10;
			itemDef.actions = new String[] { "Check", null, "Deposit", null, "Drop" };
			itemDef.modelID = 7508;
			itemDef.members = true;
			itemDef.name = "Looting bag";
			itemDef.rotationY = 67;
			itemDef.rotationX = 471;
			itemDef.modelZoom = 740;
			itemDef.anInt1879 = 18274;
			break;
		case 12782:
			itemDef.editedModelColor = new int[] { 6464, 6587, 6604, 6608, 6740 };
			itemDef.newModelColor = new int[] { 6699, 4399, 5437, 5442, 5446 };
			itemDef.cost = 5000;
			itemDef.modelID = 3374;
			itemDef.members = true;
			itemDef.name = "Ring of wealth scroll";
			itemDef.notedID = 12784;
			itemDef.stockMarket = true;
			itemDef.rotationY = 458;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 1104;
			itemDef.rotationZ = 1912;
			itemDef.modelZoom = 842;
			itemDef.anInt1879 = 15088;
			itemDef.actions = new String[] { null, null, null, null, null };
			break;

		case 20005:
			itemDef.lightness = 15;
			itemDef.shadow = 35;
			itemDef.cost = 45000;
			itemDef.femaleEquip1 = 8963;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, "Revert" };
			itemDef.modelID = 5454;
			itemDef.maleEquip1 = 8963;
			itemDef.members = true;
			itemDef.name = "Malediction ward";
			itemDef.rotationY = 512;
			itemDef.rotationX = 1872;
			itemDef.modelOffsetY = 3;
			itemDef.modelZoom = 1916;
			itemDef.anInt1879 = 18262;
			break;

		case 20006:
			itemDef.lightness = 15;
			itemDef.shadow = 35;
			itemDef.cost = 45000;
			itemDef.femaleEquip1 = 8960;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, "Revert" };
			itemDef.modelID = 8958;
			itemDef.maleEquip1 = 8960;
			itemDef.members = true;
			itemDef.name = "Odium ward";
			itemDef.rotationY = 512;
			itemDef.rotationX = 1751;
			itemDef.modelOffsetY = 7;
			itemDef.modelZoom = 1474;
			itemDef.anInt1879 = 18264;
			break;
		case 12786:
			itemDef.editedModelColor = new int[] { 6464, 6587, 6604, 6608, 6740 };
			itemDef.newModelColor = new int[] { 6699, 4399, 5437, 5442, 5446 };
			itemDef.cost = 5000;
			itemDef.modelID = 3374;
			itemDef.members = true;
			itemDef.name = "Magic shortbow scroll";
			itemDef.notedID = 12787;
			itemDef.stockMarket = true;
			itemDef.rotationY = 404;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 1064;
			itemDef.rotationZ = 1953;
			itemDef.modelZoom = 842;
			itemDef.anInt1879 = 15090;
			itemDef.actions = new String[] { null, null, null, null, null };

			break;

		case 21791:
			itemDef.editedModelColor = new int[] { 926, 7700, 11200, 6032, -23870, -23753 };
			itemDef.newModelColor = new int[] { 10714, 127, -21593, 10714, -22611, -22623 };
			itemDef.femaleEquip1 = 34151;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 34261;
			itemDef.maleEquip1 = 34149;
			itemDef.name = "Imbued saradomin cape";
			itemDef.rotationY = 424;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 528;
			itemDef.modelOffsetY = -2;
			itemDef.modelZoom = 2140;
			itemDef.anInt1879 = 21792;
			itemDef.maleYOffset = -7;
			itemDef.maleZOffset = 7;

			break;

		case 21793:
			itemDef.editedModelColor = new int[] { 24474, 25505 };
			itemDef.newModelColor = new int[] { 24468, 25496 };
			itemDef.femaleEquip1 = 34150;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 60167;
			itemDef.maleEquip1 = 60146;
			itemDef.name = "Imbued guthix cape";
			itemDef.rotationY = 424;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 528;
			itemDef.modelOffsetY = -2;
			itemDef.modelZoom = 2140;
			itemDef.anInt1879 = 21792;
			itemDef.maleYOffset = -7;
			itemDef.maleZOffset = 7;

			break;
		case 21795:
			itemDef.editedModelColor = new int[] { 33, 24 };
			itemDef.newModelColor = new int[] { 16, 12 };
			itemDef.femaleEquip1 = 34152;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 60166;
			itemDef.maleEquip1 = 60148;
			itemDef.name = "Imbued zamorak cape";
			itemDef.rotationY = 424;
			itemDef.modelOffsetX = -1;
			itemDef.rotationX = 528;
			itemDef.modelOffsetY = -2;
			itemDef.modelZoom = 2140;
			itemDef.anInt1879 = 21792;
			itemDef.maleYOffset = -7;
			itemDef.maleZOffset = 7;
			break;

		case 12788:
			itemDef.editedModelColor = new int[] { 6674 };
			itemDef.newModelColor = new int[] { 31516 };
			itemDef.cost = 1600;
			itemDef.femaleEquip1 = 512;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 2562;
			itemDef.maleEquip1 = 512;
			itemDef.members = true;
			itemDef.name = "Magic shortbow (i)";
			itemDef.rotationY = 508;
			itemDef.modelOffsetX = 7;
			itemDef.rotationX = 124;
			itemDef.modelOffsetY = 2;
			itemDef.modelZoom = 1200;
			itemDef.anInt1879 = 15091;
			break;
		case 20998:
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.modelID = 32799;
			itemDef.name = "Twisted bow";
			itemDef.modelZoom = 2000;
			itemDef.rotationY = 720;
			itemDef.rotationX = 1500;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = 1;
			itemDef.femaleEquip1 = 32674;
			itemDef.maleEquip1 = 32674;
			itemDef.description = "A mystical bow carved from the twisted remains of the Great Olm.";
			break;
		case 12714:
			itemDef.cost = 500010;
			itemDef.femaleEquip1 = 32678;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 32792;
			itemDef.maleEquip1 = 32678;
			itemDef.members = true;
			itemDef.name = "Elder maul";
			itemDef.notedID = 21004;
			itemDef.stockMarket = true;
			itemDef.rotationY = 237;
			itemDef.modelOffsetX = -3;
			itemDef.rotationX = 429;
			itemDef.modelOffsetY = -58;
			itemDef.modelZoom = 1744;
			break;
		case 12715:
			itemDef.cost = 560000;
			itemDef.femaleEquip1 = 32671;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 32801;
			itemDef.maleEquip1 = 32671;
			itemDef.members = true;
			itemDef.name = "Dinh's bulwark";
			itemDef.notedID = 21016;
			itemDef.stockMarket = true;
			itemDef.rotationY = 402;
			itemDef.rotationX = 1409;
			itemDef.modelOffsetY = 4;
			itemDef.modelZoom = 2276;
			itemDef.anInt1879 = 21017;
			break;
		case 12716:
			itemDef.name = "Dragon sword";
			itemDef.modelZoom = 1200;
			itemDef.rotationY = 691;
			itemDef.rotationX = 478;
			itemDef.modelOffsetY = 7;
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.modelID = 32791;
			itemDef.maleEquip1 = 32676;
			itemDef.femaleEquip1 = 32676;
			itemDef.maleYOffset = 7;
			itemDef.maleXOffset = 7;
			break;
		case 12717:
			itemDef.name = "Dragon harpoon";
			itemDef.modelZoom = 1347;
			itemDef.rotationY = 600;
			itemDef.rotationX = 54;
			itemDef.modelOffsetX = 7;
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.modelID = 32796;
			itemDef.maleEquip1 = 32670;
			itemDef.femaleEquip1 = 32670;
			itemDef.maleYOffset = 7;
			itemDef.maleXOffset = 7;
			itemDef.modelOffsetY = 6;
			itemDef.modelOffsetX = 0;
			break;
		case 12718:
			itemDef.name = "Dragon thrownaxe";
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 32788;
			itemDef.modelZoom = 912;
			itemDef.rotationY = 525;
			itemDef.rotationX = 1799;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 2;
			itemDef.femaleEquip1 = 32672;
			itemDef.maleEquip1 = 32672; // male equip
			itemDef.maleYOffset = 7;
			itemDef.maleXOffset = 7;
			break;
		case 12719:
			itemDef.cost = 150000;
			itemDef.femaleEquip1 = 32669;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 32789;
			itemDef.maleEquip1 = 32669;
			itemDef.members = true;
			itemDef.name = "Kodai wand";
			itemDef.notedID = 21007;
			itemDef.stockMarket = true;
			itemDef.rotationY = 140;
			itemDef.modelOffsetX = 2;
			itemDef.rotationX = 1416;
			itemDef.modelOffsetY = -4;
			itemDef.modelZoom = 668;
			itemDef.anInt1879 = 21008;
			itemDef.maleYOffset = 4;
			break;
		case 12720:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 32794;
			itemDef.name = "Ancestral hat";
			itemDef.modelZoom = 1236;
			itemDef.rotationY = 118;
			itemDef.rotationX = 10;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -12;
			itemDef.femaleEquip1 = 32663;
			itemDef.maleEquip1 = 32655;
			itemDef.description = "The hat of a powerful sorceress from a bygone era.";
			break;
		case 12721:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 32790;
			itemDef.name = "Ancestral robe top";
			itemDef.modelZoom = 1358;
			itemDef.rotationY = 514;
			itemDef.rotationX = 2041;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -3;
			itemDef.femaleEquip1 = 32664;
			itemDef.maleEquip1 = 32657;
			itemDef.maleEquip2 = 32658; // male arms
			itemDef.femaleEquip2 = 32665; // female arms
			itemDef.description = "The robe top of a powerful sorceress from a bygone era.";
			break;
		case 12722:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 32787;
			itemDef.name = "Ancestral robe bottom";
			itemDef.modelZoom = 1690;
			itemDef.rotationY = 435;
			itemDef.rotationX = 9;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = 7;
			itemDef.femaleEquip1 = 32653;
			itemDef.maleEquip1 = 32662;
			itemDef.description = "The robe bottom of a powerful sorceress from a bygone era.";
			break;

		case 12723:
			itemDef.name = "Ganodermic visor";
			itemDef.description = "It's an Ganodermic visor";
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.modelID = 10935;
			itemDef.maleEquip1 = 10373;
			itemDef.femaleEquip1 = 10523;
			itemDef.modelZoom = 1118;
			itemDef.rotationY = 215;
			itemDef.rotationX = 175;
			itemDef.modelOffsetY = 1;
			itemDef.modelOffsetX = -30;
			break;
		case 12724:
			itemDef.name = "Ganodermic poncho";
			itemDef.description = "It's an Ganodermic poncho";
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.groundActions = new String[] { null, null, "take", null, null };
			itemDef.modelID = 60919;
			itemDef.maleEquip1 = 60490;
			itemDef.femaleEquip1 = 60664;
			itemDef.modelZoom = 1513;
			itemDef.rotationY = 485;
			itemDef.rotationX = 13;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -3;
			break;
		case 12725:
			itemDef.name = "Ganodermic leggings";
			itemDef.description = "It's an Ganodermic leggings";
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.modelID = 10942;
			itemDef.maleEquip1 = 60486;
			itemDef.femaleEquip1 = 60578;
			itemDef.modelZoom = 1513;
			itemDef.rotationY = 498;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 8;
			itemDef.modelOffsetY = -18;
			break;
		case 12726:
			itemDef.name = "Polypore staff";
			itemDef.description = "It's an Polypore staff";
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.groundActions = new String[] { null, null, "take", null, null };
			itemDef.modelID = 63426;
			itemDef.maleEquip1 = 63417;
			itemDef.femaleEquip1 = 63417;
			itemDef.modelZoom = 3750;
			itemDef.rotationY = 1454;
			itemDef.rotationX = 997;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 8;
			break;
		case 12727:
			itemDef.femaleEquip1 = 28445;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 28438;
			itemDef.maleEquip1 = 28445;
			itemDef.membersObject = true;
			itemDef.name = "Occult necklace";
			itemDef.rotationY = 431;
			itemDef.modelOffsetX = 3;
			itemDef.rotationX = 81;
			itemDef.modelOffsetY = 21;
			// itemDef.zan2d = 27;
			itemDef.modelZoom = 589;
			break;
		case 12728:
			itemDef.femaleEquip1 = 31233;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 31524;
			itemDef.maleEquip1 = 31227;
			itemDef.membersObject = true;
			itemDef.name = "Amulet of torture";
			itemDef.rotationY = 424;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 68;
			itemDef.modelOffsetY = 16;
			itemDef.modelZoom = 620;
			itemDef.maleYOffset = -6;
			itemDef.maleZOffset = -1;
			break;
		case 12738:
			itemDef.femaleEquip1 = 31232;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 31510;
			itemDef.maleEquip1 = 31228;
			itemDef.name = "Necklace of anguish";
			itemDef.rotationY = 332;
			itemDef.rotationX = 2020;
			itemDef.modelOffsetY = -16;
			itemDef.modelZoom = 1020;
			break;
		case 12729:
			itemDef.lightness = 15;
			itemDef.editedModelColor = new int[] { 908 };
			itemDef.newModelColor = new int[] { -21608 };
			itemDef.shadow = 35;
			itemDef.femaleEquip1 = 9347;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 9354;
			itemDef.maleEquip1 = 9347;
			itemDef.name = "Malediction ward";
			itemDef.rotationY = 568;
			itemDef.rotationX = 1836;
			itemDef.modelOffsetY = 3;
			itemDef.modelZoom = 1200;
			itemDef.modelOffsetX = -1;
			break;
		case 12730:
			itemDef.lightness = 15;
			itemDef.editedModelColor = new int[] { 908 };
			itemDef.newModelColor = new int[] { 15252 };
			itemDef.shadow = 35;
			itemDef.femaleEquip1 = 9347;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 9354;
			itemDef.maleEquip1 = 9347;
			itemDef.name = "Odium ward";
			itemDef.rotationY = 568;
			itemDef.rotationX = 1836;
			itemDef.modelOffsetX = -1;
			itemDef.modelOffsetY = 3;
			itemDef.modelZoom = 1200;
			break;
		case 21000:
			itemDef.femaleEquip1 = 32667;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wear", null, null, null };
			itemDef.modelID = 32793;
			itemDef.maleEquip1 = 32667;
			itemDef.name = "Twisted buckler";
			itemDef.rotationY = 494;
			itemDef.modelOffsetX = 1;
			itemDef.rotationX = 525;
			itemDef.modelOffsetY = 1;
			itemDef.modelZoom = 792;
			break;

		case 12731:
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelID = 32798;
			itemDef.membersObject = true;
			itemDef.name = "Pet Olmlet";
			itemDef.rotationY = 67;
			itemDef.modelOffsetX = -16;
			itemDef.rotationX = 1778;
			itemDef.modelOffsetY = -1;
			itemDef.modelZoom = 968;
			break;
		case 12732:
			itemDef.femaleEquip1 = 32668;
			itemDef.maleXOffset = 6;
			itemDef.actions = new String[] { null, "Wield", null, null, null };
			itemDef.modelID = 32797;
			itemDef.maleEquip1 = 32668;
			itemDef.name = "Dragon hunter crossbow";
			itemDef.rotationY = 432;
			itemDef.rotationX = 258;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = 9;
			itemDef.modelZoom = 926;
			break;
		case 8465:
			itemDef.name = "Red slayer helmet";
			itemDef.description = "You really don't want to wear it inside-out.";
			itemDef.modelID = 20861;
			itemDef.maleEquip1 = 20862;
			itemDef.femaleEquip1 = 20862;
			itemDef.modelZoom = 750;
			itemDef.actions = new String[] { null, "Wear", "Revert", null, "Drop" };
			itemDef.groundActions = new String[] { null, null, "Take", null, null, };
			itemDef.rotationX = 1743;
			itemDef.rotationY = 69;
			itemDef.modelOffsetX = -4;
			itemDef.modelOffsetY = -3;

			break;

		case 8467:
			itemDef.name = "Green slayer helmet";
			itemDef.description = "You really don't want to wear it inside-out.";
			itemDef.modelID = 20859;
			itemDef.maleEquip1 = 20860;
			itemDef.femaleEquip1 = 20860;
			itemDef.modelZoom = 750;
			itemDef.actions = new String[] { null, "Wear", "Revert", null, "Drop" };
			itemDef.groundActions = new String[] { null, null, "Take", null, null, };
			itemDef.rotationX = 1743;
			itemDef.rotationY = 69;
			itemDef.modelOffsetX = -4;
			itemDef.modelOffsetY = -3;

			break;
		case 8469:
			itemDef.name = "Black slayer helmet";
			itemDef.description = "You really don't want to wear it inside-out.";
			itemDef.modelID = 20863;
			itemDef.maleEquip1 = 20864;
			itemDef.femaleEquip1 = 20864;
			itemDef.modelZoom = 750;
			itemDef.actions = new String[] { null, "Wear", "Revert", null, "Drop" };
			itemDef.groundActions = new String[] { null, null, "Take", null, null, };
			itemDef.rotationX = 1743;
			itemDef.rotationY = 69;
			itemDef.modelOffsetX = -4;
			itemDef.modelOffsetY = -3;

			break;
		case 11354:
			ItemDefinition itemDef2111111 = forID(11732);
			itemDef.modelID = itemDef2111111.modelID;
			itemDef.maleEquip1 = itemDef2111111.maleEquip1;
			itemDef.femaleEquip1 = itemDef2111111.femaleEquip1;
			itemDef.modelOffsetX = itemDef2111111.modelOffsetX;
			itemDef.rotationZ = itemDef2111111.rotationZ;
			itemDef.modelOffsetY = itemDef2111111.modelOffsetY;
			itemDef.rotationY = itemDef2111111.rotationY;
			itemDef.rotationX = itemDef2111111.rotationX;
			itemDef.modelZoom = itemDef2111111.modelZoom;
			itemDef.name = "Yellow Dragon boots ";
			itemDef.actions = itemDef2111111.actions;
			itemDef.editedModelColor = new int[] { 926 };
			itemDef.newModelColor = new int[] { 76770 };
			itemDef.stackable = false;
			break;
		case 11355:
			ItemDefinition itemDef21111111 = forID(11732);
			itemDef.modelID = itemDef21111111.modelID;
			itemDef.maleEquip1 = itemDef21111111.maleEquip1;
			itemDef.femaleEquip1 = itemDef21111111.femaleEquip1;
			itemDef.modelOffsetX = itemDef21111111.modelOffsetX;
			itemDef.rotationZ = itemDef21111111.rotationZ;
			itemDef.modelOffsetY = itemDef21111111.modelOffsetY;
			itemDef.rotationY = itemDef21111111.rotationY;
			itemDef.rotationX = itemDef21111111.rotationX;
			itemDef.modelZoom = itemDef21111111.modelZoom;
			// itemDef.name = "White Dragon boots ";
			itemDef.actions = itemDef21111111.actions;
			itemDef.editedModelColor = new int[] { 926 };
			itemDef.newModelColor = new int[] { 266770 };
			itemDef.stackable = false;
			break;
		case 11356:
			ItemDefinition itemDef211111111 = forID(11732);
			itemDef.modelID = itemDef211111111.modelID;
			itemDef.maleEquip1 = itemDef211111111.maleEquip1;
			itemDef.femaleEquip1 = itemDef211111111.femaleEquip1;
			itemDef.modelOffsetX = itemDef211111111.modelOffsetX;
			itemDef.rotationZ = itemDef211111111.rotationZ;
			itemDef.modelOffsetY = itemDef211111111.modelOffsetY;
			itemDef.rotationY = itemDef211111111.rotationY;
			itemDef.rotationX = itemDef211111111.rotationX;
			itemDef.modelZoom = itemDef211111111.modelZoom;
			itemDef.name = "White Dragon boots ";
			itemDef.actions = itemDef211111111.actions;
			itemDef.editedModelColor = new int[] { 926 };
			itemDef.newModelColor = new int[] { 100 };
			itemDef.stackable = false;
		case 13994:
			itemDef.modelID = 44699;
			itemDef.name = "Vanguard gloves";
			itemDef.modelZoom = 830;
			itemDef.rotationX = 536;
			itemDef.rotationY = 0;
			itemDef.modelOffsetX = 9;
			itemDef.modelOffsetY = 3;
			itemDef.stackable = false;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44758;
			itemDef.femaleEquip1 = 44758;
			break;

		case 13995:
			itemDef.modelID = 44700;
			itemDef.name = "Vanguard boots";
			itemDef.modelZoom = 848;
			itemDef.rotationY = 141;
			itemDef.rotationX = 141;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = 0;
			itemDef.stackable = false;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.maleEquip1 = 44752;
			itemDef.femaleEquip1 = 44752;
			break;
		// 120 Capes
		case 14911:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65495;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65496;
			itemDef.femaleEquip1 = 65496;
			itemDef.stackable = false;
			itemDef.name = "Master agility cape";
			itemDef.description = "Master agility cape";
			break;
		case 14912:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65497;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65498;
			itemDef.femaleEquip1 = 65498;
			itemDef.stackable = false;
			itemDef.name = "Master attack cape";
			itemDef.description = "Master attack cape";
			break;
		case 14913:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65499;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65500;
			itemDef.femaleEquip1 = 65500;
			itemDef.stackable = false;
			itemDef.name = "Master const. cape";
			itemDef.description = "Master const. cape";
			break;
		case 14914:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65501;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65502;
			itemDef.femaleEquip1 = 65502;
			itemDef.stackable = false;
			itemDef.name = "Master cooking cape";
			itemDef.description = "Master cooking cape";
			break;
		case 14915:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65503;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65504;
			itemDef.femaleEquip1 = 65504;
			itemDef.stackable = false;
			itemDef.name = "Master crafting cape";
			itemDef.description = "Master crafting cape";
			break;
		case 14916:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65505;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65506;
			itemDef.femaleEquip1 = 65506;
			itemDef.stackable = false;
			itemDef.name = "Master defence cape";
			itemDef.description = "Master defence cape";
			break;
		case 14917:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65507;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65508;
			itemDef.femaleEquip1 = 65508;
			itemDef.stackable = false;
			itemDef.name = "Master farming cape";
			itemDef.description = "Master farming cape";
			break;
		case 14918:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65509;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65510;
			itemDef.femaleEquip1 = 65510;
			itemDef.stackable = false;
			itemDef.name = "Master firemaking cape";
			itemDef.description = "Master firemaking cape";
			break;
		case 14919:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65511;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65512;
			itemDef.femaleEquip1 = 65512;
			itemDef.stackable = false;
			itemDef.name = "Master fishing cape";
			itemDef.description = "Master fishing cape";
			break;
		case 14920:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65513;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65514;
			itemDef.femaleEquip1 = 65514;
			itemDef.stackable = false;
			itemDef.name = "Master fletching cape";
			itemDef.description = "Master fletching cape";
			break;
		case 14921:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65515;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65516;
			itemDef.femaleEquip1 = 65516;
			itemDef.stackable = false;
			itemDef.name = "Master herblore cape";
			itemDef.description = "Master herblore cape";
			break;
		case 14922:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65517;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65518;
			itemDef.femaleEquip1 = 65518;
			itemDef.stackable = false;
			itemDef.name = "Master hitpoints cape";
			itemDef.description = "Master hitpoints cape";
			break;
		case 14923:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65519;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65520;
			itemDef.femaleEquip1 = 65520;
			itemDef.stackable = false;
			itemDef.name = "Master hunter cape";
			itemDef.description = "Master hunter cape";
			break;
		case 14924:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65521;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65522;
			itemDef.femaleEquip1 = 65522;
			itemDef.stackable = false;
			itemDef.name = "Master magic cape";
			itemDef.description = "Master magic cape";
			break;
		case 14925:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65523;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65524;
			itemDef.femaleEquip1 = 65524;
			itemDef.stackable = false;
			itemDef.name = "Master mining cape";
			itemDef.description = "Master mining cape";
			break;
		case 14926:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65525;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65526;
			itemDef.femaleEquip1 = 65526;
			itemDef.stackable = false;
			itemDef.name = "Master prayer cape";
			itemDef.description = "Master prayer cape";
			break;
		case 14927:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65527;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65528;
			itemDef.femaleEquip1 = 65528;
			itemDef.stackable = false;
			itemDef.name = "Master ranged cape";
			itemDef.description = "Master ranged cape";
			break;
		case 14928:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65529;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65530;
			itemDef.femaleEquip1 = 65530;
			itemDef.stackable = false;
			itemDef.name = "Master runecrafting cape";
			itemDef.description = "Master runecrafting cape";
			break;
		case 14929:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65531;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65532;
			itemDef.femaleEquip1 = 65532;
			itemDef.stackable = false;
			itemDef.name = "Master slayer cape";
			itemDef.description = "Master slayer cape";
			break;
		case 14930:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65533;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 65534;
			itemDef.femaleEquip1 = 65534;
			itemDef.stackable = false;
			itemDef.name = "Master smithing cape";
			itemDef.description = "Master smithing cape";
			break;
		case 14931:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 65535;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 64536;
			itemDef.femaleEquip1 = 64536;
			itemDef.stackable = false;
			itemDef.name = "Master strength cape";
			itemDef.description = "Master strength cape";
			break;
		case 14932:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 64537;
			itemDef.modelZoom = 2203;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 64538;
			itemDef.femaleEquip1 = 64538;
			itemDef.stackable = false;
			itemDef.name = "Master summoning cape";
			itemDef.description = "Master summoning cape";
			break;
		case 14933:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 64539;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 64540;
			itemDef.femaleEquip1 = 64540;
			itemDef.stackable = false;
			itemDef.name = "Master thieving cape";
			itemDef.description = "Master thieving cape";
			break;
		case 14934:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.modelID = 64541;
			itemDef.modelZoom = 2200;
			itemDef.rotationY = 504;
			itemDef.rotationX = 1000;
			itemDef.modelOffsetX = 30;
			itemDef.modelOffsetY = 35;
			itemDef.maleEquip1 = 64542;
			itemDef.femaleEquip1 = 64542;
			itemDef.stackable = false;
			itemDef.name = "Master woodcutting cape";
			itemDef.description = "Master woodcutting cape";
			break;
		// END pf 120 capes
		case 15010:
			itemDef.modelID = 2429;
			itemDef.name = "Gold Ring";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 15009;
			itemDef.certTemplateID = 799;
			break;
		// start osrs pets
		case 13321:
			itemDef.name = "Rock Golem";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 2640;
			itemDef.rotationX = 1829;
			itemDef.rotationY = 42;
			itemDef.modelID = 29755;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetX = 0;
			break;
		case 13320:
			itemDef.name = "Heron";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 2256;
			itemDef.rotationX = 1757;
			itemDef.rotationY = 381;
			itemDef.modelID = 29756;
			itemDef.rotationZ = 10;
			itemDef.modelOffsetX = 25;
			break;
		case 13322:
			itemDef.name = "Beaver";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1872;
			itemDef.rotationX = 333;
			itemDef.rotationY = 195;
			itemDef.modelID = 29754;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetX = 0;
			break;
		case 13323:
			itemDef.name = "Tangleroot";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1872;
			itemDef.rotationX = 333;
			itemDef.rotationY = 195;
			itemDef.modelID = 32202;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetX = 0;
			break;
		case 13324:
			itemDef.name = "Rocky";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1872;
			itemDef.rotationX = 333;
			itemDef.rotationY = 195;
			itemDef.modelID = 32203;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetX = 0;
			break;
		case 13325:
			itemDef.name = "Giant squirrel";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1872;
			itemDef.rotationX = 333;
			itemDef.rotationY = 195;
			itemDef.modelID = 32205;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetX = 0;
			break;
		case 13326:
			itemDef.name = "Rift Guardian";
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, null, "Summon", null, "Drop" };
			itemDef.modelZoom = 1872;
			itemDef.rotationX = 333;
			itemDef.rotationY = 195;
			itemDef.modelID = 32204;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetX = 0;
			break;
		// end

		case 11884:
			itemDef.actions = new String[] { "Open", null, null, null, null, null };
			break;

		/**
		 * Flasks
		 */
		case 14136:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super antipoison flask (4)";
			itemDef.description = "4 doses of super antipoison.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62404 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14134:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super antipoison flask (3)";
			itemDef.stackable = false;
			itemDef.description = "3 doses of super antipoison.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62404 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14132:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super antipoison flask (2)";
			itemDef.stackable = false;
			itemDef.description = "2 doses of super antipoison.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62404 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14130:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super antipoison flask (1)";
			itemDef.stackable = false;
			itemDef.description = "1 dose of super antipoison.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetX = -1;
			itemDef.newModelColor = new int[] { 62404 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14428:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (6)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14427;
			itemDef.certTemplateID = 799;
			break;
		case 14427:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Saradomin brew flask (6)";
			itemDef.stackable = false;
			itemDef.description = "6 doses of saradomin brew.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetX = -1;
			itemDef.newModelColor = new int[] { 10939 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			itemDef.lightness = 200;
			itemDef.shadow = 40;
			break;
		case 14426:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (5)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14425;
			itemDef.certTemplateID = 799;
			break;
		case 14425:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Saradomin brew flask (5)";
			itemDef.stackable = false;
			itemDef.description = "5 doses of saradomin brew.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetX = -1;
			itemDef.newModelColor = new int[] { 10939 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			itemDef.lightness = 200;
			itemDef.shadow = 40;
			break;
		case 14424:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (4)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14423;
			itemDef.certTemplateID = 799;
			break;
		case 14423:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Saradomin brew flask (4)";
			itemDef.stackable = false;
			itemDef.description = "4 doses of saradomin brew.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetX = -1;
			itemDef.newModelColor = new int[] { 10939 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			itemDef.lightness = 200;
			itemDef.shadow = 40;
			break;
		case 14422:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (3)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14421;
			itemDef.certTemplateID = 799;
			break;
		case 14421:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Saradomin brew flask (3)";
			itemDef.stackable = false;
			itemDef.description = "3 doses of saradomin brew.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetX = -1;
			itemDef.newModelColor = new int[] { 10939 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			itemDef.lightness = 200;
			itemDef.shadow = 40;
			break;
		case 14420:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (2)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14419;
			itemDef.certTemplateID = 799;
			break;
		case 14419:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Saradomin brew flask (2)";
			itemDef.stackable = false;
			itemDef.description = "2 doses of saradomin brew.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetX = -1;
			itemDef.newModelColor = new int[] { 10939 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			itemDef.lightness = 200;
			itemDef.shadow = 40;
			break;
		case 14418:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Saradomin brew flask (1)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14417;
			itemDef.certTemplateID = 799;
			break;
		case 14417:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Saradomin brew flask (1)";
			itemDef.stackable = false;
			itemDef.description = "1 dose of saradomin brew.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetX = -1;
			itemDef.newModelColor = new int[] { 10939 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			itemDef.lightness = 200;
			itemDef.shadow = 40;
			break;
		case 14416:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (6)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14415;
			itemDef.certTemplateID = 799;
			break;
		case 14415:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super restore flask (6)";
			itemDef.stackable = false;
			itemDef.description = "6 doses of super restore potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62135 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14414:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (5)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14413;
			itemDef.certTemplateID = 799;
			break;
		case 14413:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super restore flask (5)";
			itemDef.stackable = false;
			itemDef.description = "5 doses of super restore potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62135 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14412:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (4)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14411;
			itemDef.certTemplateID = 799;
			break;
		case 14411:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super restore flask (4)";
			itemDef.stackable = false;
			itemDef.description = "4 doses of super restore potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62135 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14410:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (3)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14409;
			itemDef.certTemplateID = 799;
			break;
		case 14409:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super restore flask (3)";
			itemDef.stackable = false;
			itemDef.description = "3 doses of super restore potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62135 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14408:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (2)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14407;
			itemDef.certTemplateID = 799;
			break;
		case 14407:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super restore flask (2)";
			itemDef.stackable = false;
			itemDef.description = "2 doses of super restore potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62135 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14406:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super restore flask (1)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14405;
			itemDef.certTemplateID = 799;
			break;
		case 14405:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super restore flask (1)";
			itemDef.stackable = false;
			itemDef.description = "1 dose of super restore potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62135 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14404:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (6)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14403;
			itemDef.certTemplateID = 799;
			break;
		case 14403:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Magic flask (6)";
			itemDef.description = "6 doses of magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 2524 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 3046:
		case 3044:
		case 3042:
		case 3040:
			itemDef.newModelColor = new int[] { 2524 };
			itemDef.editedModelColor = new int[] { 61 };
			break;
		case 14402:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (5)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14401;
			itemDef.certTemplateID = 799;
			break;
		case 14401:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Magic flask (5)";
			itemDef.stackable = false;
			itemDef.description = "5 doses of magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 2524 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14400:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (4)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14399;
			itemDef.certTemplateID = 799;
			break;
		case 14399:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Magic flask (4)";
			itemDef.stackable = false;
			itemDef.description = "4 doses of magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 2524 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14398:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (3)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14397;
			itemDef.certTemplateID = 799;
			break;
		case 14397:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Magic flask (3)";
			itemDef.stackable = false;
			itemDef.description = "3 doses of magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 2524 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14396:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (2)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14395;
			itemDef.certTemplateID = 799;
			break;
		case 14395:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Magic flask (2)";
			itemDef.description = "2 doses of magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 2524 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14394:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Magic flask (1)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14393;
			itemDef.certTemplateID = 799;
			break;
		case 14393:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Magic flask (1)";
			itemDef.stackable = false;
			itemDef.description = "1 dose of magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 2524 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14385:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Recover special flask (6)";
			itemDef.stackable = false;
			itemDef.description = "6 doses of recover special.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 38222 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14383:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Recover special flask (5)";
			itemDef.stackable = false;
			itemDef.description = "5 doses of recover special.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 38222 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14381:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Recover special flask (4)";
			itemDef.description = "4 doses of recover special.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 38222 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14379:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Recover special flask (3)";
			itemDef.description = "3 doses of recover special.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 38222 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14377:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Recover special flask (2)";
			itemDef.description = "2 doses of recover special.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 38222 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14375:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Recover special flask (1)";
			itemDef.description = "1 dose of recover special.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 38222 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14373:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme attack flask (6)";
			itemDef.description = "6 doses of extreme attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33112 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14371:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme attack flask (5)";
			itemDef.description = "5 doses of extreme attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33112 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14369:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme attack flask (4)";
			itemDef.description = "4 doses of extreme attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33112 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14367:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme attack flask (3)";
			itemDef.description = "3 doses of extreme attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33112 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14365:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme attack flask (2)";
			itemDef.description = "2 doses of extreme attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33112 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14363:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme attack flask (1)";
			itemDef.description = "1 dose of extreme attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33112 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14361:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme strength flask (6)";
			itemDef.description = "6 doses of extreme strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 127 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14359:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme strength flask (5)";
			itemDef.description = "5 doses of extreme strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 127 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14357:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme strength flask (4)";
			itemDef.description = "4 doses of extreme strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 127 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14355:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme strength flask (3)";
			itemDef.description = "3 doses of extreme strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 127 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14353:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme strength flask (2)";
			itemDef.description = "2 doses of extreme strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 127 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14351:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme strength flask (1)";
			itemDef.description = "1 dose of extreme strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 127 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14349:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme defence flask (6)";
			itemDef.description = "6 doses of extreme defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 10198 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14347:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme defence flask (5)";
			itemDef.description = "5 doses of extreme defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 10198 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14345:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme defence flask (4)";
			itemDef.description = "4 doses of extreme defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 10198 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14343:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme defence flask (3)";
			itemDef.stackable = false;
			itemDef.description = "3 doses of extreme defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 10198 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14341:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme defence flask (2)";
			itemDef.description = "2 doses of extreme defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 10198 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14339:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme defence flask (1)";
			itemDef.description = "1 dose of extreme defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 10198 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14337:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme magic flask (6)";
			itemDef.description = "6 doses of extreme magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33490 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14335:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme magic flask (5)";
			itemDef.description = "5 doses of extreme magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33490 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14333:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme magic flask (4)";
			itemDef.description = "4 doses of extreme magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33490 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14331:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme magic flask (3)";
			itemDef.stackable = false;
			itemDef.description = "3 doses of extreme magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33490 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14329:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Extreme magic flask (2)";
			itemDef.stackable = false;
			itemDef.description = "2 doses of extreme magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33490 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14327:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme magic flask (1)";
			itemDef.stackable = false;
			itemDef.description = "1 dose of extreme magic potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 33490 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14325:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme ranging flask (6)";
			itemDef.stackable = false;
			itemDef.description = "6 doses of extreme ranging potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 13111 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14323:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme ranging flask (5)";
			itemDef.description = "5 doses of extreme ranging potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 13111 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14321:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme ranging flask (4)";
			itemDef.description = "4 doses of extreme ranging potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 13111 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14319:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme ranging flask (3)";
			itemDef.description = " 3 doses of extreme ranging potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 13111 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14317:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme ranging flask (2)";
			itemDef.description = "2 doses of extreme ranging potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 13111 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14315:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Extreme ranging flask (1)";
			itemDef.description = "1 dose of extreme ranging potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 13111 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14313:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super prayer flask (6)";
			itemDef.description = "6 doses of super prayer potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 3016 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14311:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super prayer flask (5)";
			itemDef.description = "5 doses of super prayer potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 3016 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14309:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super prayer flask (4)";
			itemDef.description = "4 doses of super prayer potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 3016 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14307:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super prayer flask (3)";
			itemDef.description = "3 doses of super prayer potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 3016 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14305:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super prayer flask (2)";
			itemDef.description = "2 doses of super prayer potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 3016 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14303:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Super prayer flask (1)";
			itemDef.description = "1 dose of super prayer potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 3016 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14301:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Overload flask (6)";
			itemDef.description = "6 doses of overload potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 0 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14299:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Overload flask (5)";
			itemDef.description = "5 doses of overload potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 0 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14297:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Overload flask (4)";
			itemDef.description = "4 doses of overload potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 0 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14295:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Overload flask (3)";
			itemDef.description = "3 doses of overload potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 0 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14293:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Overload flask (2)";
			itemDef.description = "2 doses of overload potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 0 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14291:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Overload flask (1)";
			itemDef.description = "1 dose of overload potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 0 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.groundActions[2] = "Take";

			itemDef.modelID = 61812;
			break;
		case 14289:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Prayer renewal flask (6)";
			itemDef.description = "6 doses of prayer renewal.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 926 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14287:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Prayer renewal flask (5)";
			itemDef.description = "5 doses of prayer renewal.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 926 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 15123:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Prayer renewal flask (4)";
			itemDef.description = "4 doses of prayer renewal potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 926 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 15121:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Prayer renewal flask (3)";
			itemDef.description = "3 doses of prayer renewal potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 926 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 15119:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Prayer renewal flask (2)";
			itemDef.description = "2 doses of prayer renewal potion.";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 926 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 7340:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.name = "Prayer renewal flask (1)";
			itemDef.description = "1 dose of prayer renewal potion";
			itemDef.stackable = false;
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 926 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14196:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Prayer flask (4)";
			itemDef.description = "4 doses of prayer potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 28488 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14194:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Prayer flask (3)";
			itemDef.description = "3 doses of prayer potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 28488 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14192:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Prayer flask (2)";
			itemDef.description = "2 doses of prayer potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 28488 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14190:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Prayer flask (1)";
			itemDef.description = "1 dose of prayer potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 28488 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14189:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (6)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14188;
			itemDef.certTemplateID = 799;
			break;
		case 14188:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super attack flask (6)";
			itemDef.description = "6 doses of super attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 43848 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14187:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (5)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14186;
			itemDef.certTemplateID = 799;
			break;
		case 14186:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super attack flask (5)";
			itemDef.description = "5 doses of super attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 43848 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14185:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (4)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14184;
			itemDef.certTemplateID = 799;
			break;
		case 14184:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super attack flask (4)";
			itemDef.description = "4 doses of super attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 43848 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14183:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (3)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14182;
			itemDef.certTemplateID = 799;
			break;
		case 14182:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super attack flask (3)";
			itemDef.description = "3 doses of super attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 43848 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14181:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (2)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14180;
			itemDef.certTemplateID = 799;
			break;
		case 14180:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super attack flask (2)";
			itemDef.description = "2 doses of super attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 43848 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14179:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super attack flask (1)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14178;
			itemDef.certTemplateID = 799;
			break;
		case 14178:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super attack flask (1)";
			itemDef.description = "1 dose of super attack potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 43848 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14177:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (6)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14176;
			itemDef.certTemplateID = 799;
			break;
		case 14176:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super strength flask (6)";
			itemDef.description = "6 doses of super strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 119 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14175:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (5)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14174;
			itemDef.certTemplateID = 799;
			break;
		case 14174:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super strength flask (5)";
			itemDef.description = "5 doses of super strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 119 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14173:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (4)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14172;
			itemDef.certTemplateID = 799;
			break;
		case 14172:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super strength flask (4)";
			itemDef.description = "4 doses of super strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 119 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14171:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (3)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14170;
			itemDef.certTemplateID = 799;
			break;
		case 14170:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super strength flask (3)";
			itemDef.description = "3 doses of super strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 119 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14169:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (2)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14168;
			itemDef.certTemplateID = 799;
			break;
		case 14168:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super strength flask (2)";
			itemDef.description = "2 doses of super strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 119 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14167:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Super strength flask (1)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14166;
			itemDef.certTemplateID = 799;
			break;
		case 14166:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super strength flask (1)";
			itemDef.description = "1 dose of super strength potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 119 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14164:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super defence flask (6)";
			itemDef.description = "6 doses of super defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 8008 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14162:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super defence flask (5)";
			itemDef.description = "5 doses of super defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 8008 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14160:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super defence flask (4)";
			itemDef.description = "4 doses of super defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 8008 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61764;
			break;
		case 14158:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super defence flask (3)";
			itemDef.description = "3 doses of super defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 8008 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14156:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super defence flask (2)";
			itemDef.description = "2 doses of super defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 8008 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14154:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super defence flask (1)";
			itemDef.description = "1 dose of super defence potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 8008 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14153:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (6)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14152;
			itemDef.certTemplateID = 799;
			break;
		case 14152:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Ranging flask (6)";
			itemDef.description = "6 doses of ranging potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 36680 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14151:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (5)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14150;
			itemDef.certTemplateID = 799;
			break;
		case 14150:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Ranging flask (5)";
			itemDef.description = "5 doses of ranging potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 36680 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 14149:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (4)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14148;
			itemDef.certTemplateID = 799;
			break;
		case 14148:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Ranging flask (4)";
			itemDef.description = "4 doses of ranging potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 36680 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.groundActions[2] = "Take";
			itemDef.modelID = 61764;
			break;
		case 14147:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (3)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14146;
			itemDef.certTemplateID = 799;
			break;
		case 14146:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Ranging flask (3)";
			itemDef.description = "3 doses of ranging potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 36680 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61727;
			break;
		case 14145:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (2)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14144;
			itemDef.certTemplateID = 799;
			break;
		case 14144:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Ranging flask (2)";
			itemDef.description = "2 doses of ranging potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 36680 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61731;
			break;
		case 14143:
			itemDef.setDefaults();
			itemDef.modelID = 2429;
			itemDef.name = "Ranging flask (1)";
			itemDef.actions = new String[] { null, null, null, null, null, null };
			itemDef.modelZoom = 760;
			itemDef.rotationX = 28;
			itemDef.rotationY = 552;
			itemDef.rotationZ = itemDef.modelOffsetX = 0;
			itemDef.stackable = true;
			itemDef.certID = 14142;
			itemDef.certTemplateID = 799;
			break;
		case 14142:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Ranging flask (1)";
			itemDef.description = "1 dose of ranging potion.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 36680 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61812;
			break;
		case 14140:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super antipoison flask (6)";
			itemDef.description = "6 doses of super antipoison.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62404 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61732;
			break;
		case 14138:
			itemDef.setDefaults();
			itemDef.groundActions = new String[5];
			itemDef.stackable = false;
			itemDef.name = "Super antipoison flask (5)";
			itemDef.description = "5 doses of super antipoison.";
			itemDef.modelZoom = 804;
			itemDef.rotationX = 131;
			itemDef.rotationY = 198;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -1;
			itemDef.newModelColor = new int[] { 62404 };
			itemDef.editedModelColor = new int[] { 33715 };
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[] { "Drink", null, null, null, null, "Drop" };
			itemDef.modelID = 61729;
			break;
		case 4706:
			itemDef.modelID = 62692;
			itemDef.name = "Zaryte bow";
			itemDef.modelZoom = 1703;
			itemDef.rotationY = 221;
			itemDef.rotationX = 404;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -13;
			itemDef.maleEquip1 = 62750;
			itemDef.femaleEquip1 = 62750;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.actions[4] = "Drop";
			break;
		case 15262:
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Open";
			itemDef.actions[2] = "Open-All";
			break;
		case 6570:
			itemDef.actions[2] = "Upgrade";
			break;
		case 4155:
			itemDef.name = "Slayer gem";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Open";
			break;
		case 13663:
			itemDef.name = "Stat reset cert.";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Open";
			break;
		case 13653:
			itemDef.name = "Energy fragment";
			break;
		case 292:
			itemDef.name = "Ingredients book";
			break;
		case 15707:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[0] = "Create Party";
			break;
		/*
		 * case 14044: itemDef.actions = new String[5]; itemDef.actions[1] =
		 * "Wear"; itemDef.editedModelColor = new int[1]; itemDef.newModelColor
		 * = new int[1]; itemDef.editedModelColor[0] = 926;
		 * itemDef.newModelColor[0] = 0; itemDef.modelID = 2635;
		 * itemDef.modelZoom = 440; itemDef.rotationX = 76; itemDef.rotationY =
		 * 1850;
		 * 
		 * itemDef.rotationZ = 1; itemDef.modelOffsetY = 1; itemDef.maleEquip1 =
		 * 187; itemDef.femaleEquip1 = 363; itemDef.anInt175 = 29;
		 * itemDef.stackable = false; itemDef.anInt197 = 87; itemDef.name =
		 * "Black Party Hat"; itemDef.description = "A Party Hat."; break;
		 */
		case 14044:
			itemDef.name = "Black Party Hat";
			itemDef.modelID = 2635;
			itemDef.description = "A rare black party hat";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 0;
			itemDef.modelZoom = 440;
			itemDef.rotationX = 1852;
			itemDef.rotationY = 76;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14045:
			itemDef.name = "Lime Party Hat";
			itemDef.modelID = 2635;
			itemDef.description = "A rare lime party hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 17350;
			itemDef.modelZoom = 440;
			itemDef.rotationX = 1852;
			itemDef.rotationY = 76;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14046:
			itemDef.name = "Pink Party Hat";
			itemDef.modelID = 2635;
			itemDef.description = "A rare pink party hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 57300;
			itemDef.modelZoom = 440;
			itemDef.rotationX = 1852;
			itemDef.rotationY = 76;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14047:
			itemDef.name = "Sky Blue Party Hat";
			itemDef.modelID = 2635;
			itemDef.description = "A rare sky blue party hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 689484;
			itemDef.modelZoom = 440;
			itemDef.rotationX = 1852;
			itemDef.rotationY = 76;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14048:
			itemDef.name = "Lava Party Hat";
			itemDef.modelID = 2635;
			itemDef.description = "A rare lava party hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 6073;
			itemDef.modelZoom = 440;
			itemDef.rotationX = 1852;
			itemDef.rotationY = 76;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;
			itemDef.femaleEquip1 = 363;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14049:
			itemDef.name = "Pink Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare pink santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 57300;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14050:
			itemDef.name = "Black Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare black santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 0;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14051:
			itemDef.name = "Lime Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare lime santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 17350;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14052:
			itemDef.name = "Lava Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare lava santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 6073;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14053:
			itemDef.name = "Lava Santa Hat";
			itemDef.modelID = 2537;
			itemDef.description = "A rare lava santa hat!";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 933;
			itemDef.newModelColor[0] = 6073;
			itemDef.modelZoom = 540;
			itemDef.rotationX = 136;
			itemDef.rotationY = 72;
			itemDef.rotationZ = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 189;
			itemDef.femaleEquip1 = 366;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 15152:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 926;
			itemDef.newModelColor[0] = 17350;
			itemDef.modelID = 2635;
			itemDef.modelZoom = 440;
			itemDef.rotationX = 76;
			itemDef.rotationY = 1850;

			itemDef.rotationZ = 1;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 187;

			itemDef.femaleEquip1 = 363;

			itemDef.name = "Lime Party Hat";
			itemDef.description = "A Party Hat.";
		case 14501:
			itemDef.modelID = 44574;
			itemDef.maleEquip1 = 43693;
			itemDef.femaleEquip1 = 43693;
			break;
		case 19111:
			itemDef.name = "TokHaar-Kal";
			itemDef.value = 60000;
			itemDef.maleEquip1 = 62575;
			itemDef.femaleEquip1 = 62582;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.modelOffsetX = -4;
			itemDef.modelID = 62592;
			itemDef.stackable = false;
			itemDef.description = "A cape made of ancient, enchanted rocks.";
			itemDef.modelZoom = 1616;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelOffsetX = 0;
			itemDef.rotationY = 339;
			itemDef.rotationX = 192;
			break;
		case 13262:
			itemDef2 = ItemDefinition.forID(20072);
			itemDef.modelID = itemDef2.modelID;
			itemDef.maleEquip1 = itemDef2.maleEquip1;
			itemDef.femaleEquip1 = itemDef2.femaleEquip1;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.rotationY = itemDef2.rotationY;
			itemDef.rotationX = itemDef2.rotationX;
			itemDef.modelZoom = itemDef2.modelZoom;
			itemDef.name = itemDef2.name;
			itemDef.actions = itemDef2.actions;
			break;
		case 10942:
			itemDef.name = "$10 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
			itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;
			break;
		case 10934:
			itemDef.name = "$25 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
			itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;
			break;
		case 10935:
			itemDef.name = "$50 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
			itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;
			break;
		case 10943:
			itemDef.name = "$100 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
			itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;
			break;
		case 6798:
			itemDef.name = "$1 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6799:
			itemDef.name = "$5 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6800:
			itemDef.name = "$10 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6801:
			itemDef.name = "$25 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6802:
			itemDef.name = "$50 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6808:
			itemDef.name = "$10 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6803:
			itemDef.name = "$100 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6804:
			itemDef.name = "$150 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 6805:
			itemDef.name = "$250 Scroll";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[0] = "Claim";
		/*	itemDef2 = ItemDefinition.forID(761);
			itemDef.modelID = itemDef2.modelID;
			itemDef.modelOffsetX = itemDef2.modelOffsetX;
			itemDef.rotationZ = itemDef2.rotationZ;
			itemDef.modelOffsetY = itemDef2.modelOffsetY;
			itemDef.modelZoom = itemDef2.modelZoom;*/
			break;
		case 995:
			itemDef.name = "Coins";
			itemDef.actions = new String[5];
			itemDef.actions[4] = "Drop";
			itemDef.actions[3] = "Add-to-pouch";
			break;
		case 17291:
			itemDef.name = "Blood necklace";
			itemDef.actions = new String[] { null, "Wear", null, null, null, null };
			break;
		case 20084:
			itemDef.name = "Golden Maul";
			break;
		case 6199:
			itemDef.name = "Mystery Box";
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Open";
			break;
		case 15501:
			itemDef.name = "Legendary Mystery Box";
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Open";
			break;
		case 6568: // To replace Transparent black with opaque black.
			itemDef.editedModelColor = new int[1];
			itemDef.newModelColor = new int[1];
			itemDef.editedModelColor[0] = 0;
			itemDef.newModelColor[0] = 2059;
			break;
	

		case 14017:
			itemDef.name = "Brackish blade";
			itemDef.modelZoom = 1488;
			itemDef.rotationY = 276;
			itemDef.rotationX = 1580;
			itemDef.modelOffsetY = 1;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wield", null, null, "Drop" };
			itemDef.modelID = 64593;
			itemDef.maleEquip1 = 64704;
			itemDef.femaleEquip2 = 64704;
			break;

		case 15220:
			itemDef.name = "Berserker ring (i)";
			itemDef.modelZoom = 600;
			itemDef.rotationY = 324;
			itemDef.rotationX = 1916;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -15;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 7735; // if it doesn't work try 7735
			itemDef.maleEquip1 = -1;
			// itemDefinition.maleArm = -1;
			itemDef.femaleEquip1 = -1;
			// itemDefinition.femaleArm = -1;
			break;

		case 14019:
			itemDef.modelID = 65262;
			itemDef.name = "Max Cape";
			itemDef.description = "A cape worn by those who've achieved 99 in all skills.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 24;
			itemDef.rotationY = 279;
			itemDef.rotationX = 948;
			itemDef.maleEquip1 = 65300;
			itemDef.femaleEquip1 = 65322;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Customize";
			itemDef.editedModelColor = new int[4];
			itemDef.newModelColor = new int[4];
			itemDef.editedModelColor[0] = 65214; // red
			itemDef.editedModelColor[1] = 65200; // darker red
			itemDef.editedModelColor[2] = 65186; // dark red
			itemDef.editedModelColor[3] = 62995; // darker red
			itemDef.newModelColor[0] = 65214;// cape
			itemDef.newModelColor[1] = 65200;// cape
			itemDef.newModelColor[2] = 65186;// outline
			itemDef.newModelColor[3] = 62995;// cape
			break;
		case 14020:
			itemDef.name = "Veteran hood";
			itemDef.description = "A hood worn by Chivalry's veterans.";
			itemDef.modelZoom = 760;
			itemDef.rotationY = 11;
			itemDef.rotationX = 81;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -3;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.actions = new String[] { null, "Wear", null, null, "Drop" };
			itemDef.modelID = 65271;
			itemDef.maleEquip1 = 65289;
			itemDef.femaleEquip1 = 65314;
			break;
		case 14021:
			itemDef.modelID = 65261;
			itemDef.name = "Veteran Cape";
			itemDef.description = "A cape worn by Chivalry's veterans.";
			itemDef.modelZoom = 760;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 24;
			itemDef.rotationY = 279;
			itemDef.rotationX = 948;
			itemDef.maleEquip1 = 65305;
			itemDef.femaleEquip1 = 65318;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			break;
		case 14022:
			itemDef.modelID = 65270;
			itemDef.name = "Completionist Cape";
			itemDef.description = "We'd pat you on the back, but this cape would get in the way.";
			itemDef.modelZoom = 1385;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 24;
			itemDef.rotationY = 279;
			itemDef.rotationX = 948;
			itemDef.maleEquip1 = 65297;
			itemDef.femaleEquip1 = 65297;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Customize";
			itemDef.editedModelColor = new int[4];
			itemDef.newModelColor = new int[4];
			itemDef.editedModelColor[0] = 65214; // red
			itemDef.editedModelColor[1] = 65200; // darker red
			itemDef.editedModelColor[2] = 65186; // dark red
			itemDef.editedModelColor[3] = 62995; // darker red
			itemDef.newModelColor[0] = 65214;// cape
			itemDef.newModelColor[1] = 65200;// cape
			itemDef.newModelColor[2] = 65186;// outline
			itemDef.newModelColor[3] = 62995;// cape
			break;
		case 9666:
		case 11814:
		case 11816:
		case 11818:
		case 11820:
		case 11822:
		case 11824:
		case 11826:
		case 11828:
		case 11830:
		case 11832:
		case 11834:
		case 11836:
		case 11838:
		case 11840:
		case 11842:
		case 11844:
		case 11846:
		case 11848:
		case 11850:
		case 11852:
		case 11854:
		case 11856:
		case 11858:
		case 11860:
		case 11862:
		case 11864:
		case 11866:
		case 11868:
		case 11870:
		case 11874:
		case 11876:
		case 11878:
		case 11882:
		case 11886:
		case 11890:
		case 11894:
		case 11898:
		case 11902:
		case 11904:
		case 11906:
		case 11928:
		case 11930:
		case 11938:
		case 11942:
		case 11944:
		case 11946:
		case 14525:
		case 14527:
		case 14529:
		case 14531:
		case 19588:
			// case 19592:
		case 19596:
		case 11908:
		case 11910:
		case 11912:
		case 11914:
		case 11916:
		case 11618:
		case 11920:
		case 11922:
		case 11960:
		case 11962:

		case 19586:
		case 19584:
		case 19590:
		case 19594:
			// case 19598:
			itemDef.actions = new String[5];
			itemDef.actions[0] = "Open";
			break;

		case 14004:
			itemDef.name = "Staff of light";
			itemDef.modelID = 51845;
			itemDef.editedModelColor = new int[11];
			itemDef.newModelColor = new int[11];
			itemDef.editedModelColor[0] = 7860;
			itemDef.newModelColor[0] = 38310;
			itemDef.editedModelColor[1] = 7876;
			itemDef.newModelColor[1] = 38310;
			itemDef.editedModelColor[2] = 7892;
			itemDef.newModelColor[2] = 38310;
			itemDef.editedModelColor[3] = 7884;
			itemDef.newModelColor[3] = 38310;
			itemDef.editedModelColor[4] = 7868;
			itemDef.newModelColor[4] = 38310;
			itemDef.editedModelColor[5] = 7864;
			itemDef.newModelColor[5] = 38310;
			itemDef.editedModelColor[6] = 7880;
			itemDef.newModelColor[6] = 38310;
			itemDef.editedModelColor[7] = 7848;
			itemDef.newModelColor[7] = 38310;
			itemDef.editedModelColor[8] = 7888;
			itemDef.newModelColor[8] = 38310;
			itemDef.editedModelColor[9] = 7872;
			itemDef.newModelColor[9] = 38310;
			itemDef.editedModelColor[10] = 7856;
			itemDef.newModelColor[10] = 38310;
			itemDef.modelZoom = 2256;
			itemDef.rotationX = 456;
			itemDef.rotationY = 513;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.maleEquip1 = 51795;
			itemDef.femaleEquip1 = 51795;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 14005:
			itemDef.name = "Staff of light";
			itemDef.modelID = 51845;
			itemDef.editedModelColor = new int[11];
			itemDef.newModelColor = new int[11];
			itemDef.editedModelColor[0] = 7860;
			itemDef.newModelColor[0] = 432;
			itemDef.editedModelColor[1] = 7876;
			itemDef.newModelColor[1] = 432;
			itemDef.editedModelColor[2] = 7892;
			itemDef.newModelColor[2] = 432;
			itemDef.editedModelColor[3] = 7884;
			itemDef.newModelColor[3] = 432;
			itemDef.editedModelColor[4] = 7868;
			itemDef.newModelColor[4] = 432;
			itemDef.editedModelColor[5] = 7864;
			itemDef.newModelColor[5] = 432;
			itemDef.editedModelColor[6] = 7880;
			itemDef.newModelColor[6] = 432;
			itemDef.editedModelColor[7] = 7848;
			itemDef.newModelColor[7] = 432;
			itemDef.editedModelColor[8] = 7888;
			itemDef.newModelColor[8] = 432;
			itemDef.editedModelColor[9] = 7872;
			itemDef.newModelColor[9] = 432;
			itemDef.editedModelColor[10] = 7856;
			itemDef.newModelColor[10] = 432;
			itemDef.modelZoom = 2256;
			itemDef.rotationX = 456;
			itemDef.rotationY = 513;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.maleEquip1 = 51795;
			itemDef.femaleEquip1 = 51795;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 14006:
			itemDef.name = "Staff of light";
			itemDef.modelID = 51845;
			itemDef.editedModelColor = new int[11];
			itemDef.newModelColor = new int[11];
			itemDef.editedModelColor[0] = 7860;
			itemDef.newModelColor[0] = 24006;
			itemDef.editedModelColor[1] = 7876;
			itemDef.newModelColor[1] = 24006;
			itemDef.editedModelColor[2] = 7892;
			itemDef.newModelColor[2] = 24006;
			itemDef.editedModelColor[3] = 7884;
			itemDef.newModelColor[3] = 24006;
			itemDef.editedModelColor[4] = 7868;
			itemDef.newModelColor[4] = 24006;
			itemDef.editedModelColor[5] = 7864;
			itemDef.newModelColor[5] = 24006;
			itemDef.editedModelColor[6] = 7880;
			itemDef.newModelColor[6] = 24006;
			itemDef.editedModelColor[7] = 7848;
			itemDef.newModelColor[7] = 24006;
			itemDef.editedModelColor[8] = 7888;
			itemDef.newModelColor[8] = 24006;
			itemDef.editedModelColor[9] = 7872;
			itemDef.newModelColor[9] = 24006;
			itemDef.editedModelColor[10] = 7856;
			itemDef.newModelColor[10] = 24006;
			itemDef.modelZoom = 2256;
			itemDef.rotationX = 456;
			itemDef.rotationY = 513;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.maleEquip1 = 51795;
			itemDef.femaleEquip1 = 51795;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14007:
			itemDef.name = "Staff of light";
			itemDef.modelID = 51845;
			itemDef.editedModelColor = new int[11];
			itemDef.newModelColor = new int[11];
			itemDef.editedModelColor[0] = 7860;
			itemDef.newModelColor[0] = 14285;
			itemDef.editedModelColor[1] = 7876;
			itemDef.newModelColor[1] = 14285;
			itemDef.editedModelColor[2] = 7892;
			itemDef.newModelColor[2] = 14285;
			itemDef.editedModelColor[3] = 7884;
			itemDef.newModelColor[3] = 14285;
			itemDef.editedModelColor[4] = 7868;
			itemDef.newModelColor[4] = 14285;
			itemDef.editedModelColor[5] = 7864;
			itemDef.newModelColor[5] = 14285;
			itemDef.editedModelColor[6] = 7880;
			itemDef.newModelColor[6] = 14285;
			itemDef.editedModelColor[7] = 7848;
			itemDef.newModelColor[7] = 14285;
			itemDef.editedModelColor[8] = 7888;
			itemDef.newModelColor[8] = 14285;
			itemDef.editedModelColor[9] = 7872;
			itemDef.newModelColor[9] = 14285;
			itemDef.editedModelColor[10] = 7856;
			itemDef.newModelColor[10] = 14285;
			itemDef.modelZoom = 2256;
			itemDef.rotationX = 456;
			itemDef.rotationY = 513;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetX = 0;
			itemDef.maleEquip1 = 51795;
			itemDef.femaleEquip1 = 51795;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;
		case 14003:
			itemDef.name = "Robin hood hat";
			itemDef.modelID = 3021;
			itemDef.editedModelColor = new int[3];
			itemDef.newModelColor = new int[3];
			itemDef.editedModelColor[0] = 15009;
			itemDef.newModelColor[0] = 30847;
			itemDef.editedModelColor[1] = 17294;
			itemDef.newModelColor[1] = 32895;
			itemDef.editedModelColor[2] = 15252;
			itemDef.newModelColor[2] = 30847;
			itemDef.modelZoom = 650;
			itemDef.rotationY = 2044;
			itemDef.rotationX = 256;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 3378;
			itemDef.femaleEquip1 = 3382;
			itemDef.maleDialogue = 3378;
			itemDef.femaleDialogue = 3382;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 14001:
			itemDef.name = "Robin hood hat";
			itemDef.modelID = 3021;
			itemDef.editedModelColor = new int[3];
			itemDef.newModelColor = new int[3];
			itemDef.editedModelColor[0] = 15009;
			itemDef.newModelColor[0] = 10015;
			itemDef.editedModelColor[1] = 17294;
			itemDef.newModelColor[1] = 7730;
			itemDef.editedModelColor[2] = 15252;
			itemDef.newModelColor[2] = 7973;
			itemDef.modelZoom = 650;
			itemDef.rotationY = 2044;
			itemDef.rotationX = 256;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 3378;
			itemDef.femaleEquip1 = 3382;
			itemDef.maleDialogue = 3378;
			itemDef.femaleDialogue = 3382;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 14002:
			itemDef.name = "Robin hood hat";
			itemDef.modelID = 3021;
			itemDef.editedModelColor = new int[3];
			itemDef.newModelColor = new int[3];
			itemDef.editedModelColor[0] = 15009;
			itemDef.newModelColor[0] = 35489;
			itemDef.editedModelColor[1] = 17294;
			itemDef.newModelColor[1] = 37774;
			itemDef.editedModelColor[2] = 15252;
			itemDef.newModelColor[2] = 35732;
			itemDef.modelZoom = 650;
			itemDef.rotationY = 2044;
			itemDef.rotationX = 256;
			itemDef.modelOffsetX = -3;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 3378;
			itemDef.femaleEquip1 = 3382;
			itemDef.maleDialogue = 3378;
			itemDef.femaleDialogue = 3382;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			break;

		case 14000:
			itemDef.name = "Robin hood hat";
			itemDef.modelID = 3021;
			itemDef.editedModelColor = new int[3];
			itemDef.newModelColor = new int[3];
			itemDef.editedModelColor[0] = 15009;
			itemDef.newModelColor[0] = 3745;
			itemDef.editedModelColor[1] = 17294;
			itemDef.newModelColor[1] = 3982;
			itemDef.editedModelColor[2] = 15252;
			itemDef.newModelColor[2] = 3988;
			itemDef.modelZoom = 650;
			itemDef.rotationY = 2044;
			itemDef.rotationX = 256;
			itemDef.rotationZ = 1;
			itemDef.modelOffsetY = -5;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.maleEquip1 = 3378;
			itemDef.femaleEquip1 = 3382;
			itemDef.maleDialogue = 3378;
			itemDef.femaleDialogue = 3382;
			break;

		/*
		 * case 19111: itemDef.name = "TokHaar-Kal"; // itemDef.femaleOffset =
		 * 0; itemDef.value = 60000; itemDef.maleEquip1 = 62575;
		 * itemDef.femaleEquip1 = 62582; itemDef.groundActions = new String[5];
		 * itemDef.groundActions[2] = "Take"; itemDef.modelOffsetX = -4;
		 * itemDef.modelID = 62592; itemDef.stackable = false;
		 * itemDef.description = "A cape made of ancient, enchanted obsidian.";
		 * itemDef.modelZoom = 2086; itemDef.actions = new String[5];
		 * itemDef.actions[1] = "Wear"; itemDef.actions[4] = "Drop";
		 * itemDef.modelOffsetY = 0; itemDef.rotationY = 533; itemDef.rotationX
		 * = 333; break;
		 */
		case 20000:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 53835;
			itemDef.name = "Steadfast boots";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 165;
			itemDef.rotationX = 99;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -7;
			itemDef.maleEquip1 = 53327;
			itemDef.femaleEquip1 = 53643;
			itemDef.description = "A pair of Steadfast boots.";
			break;

		case 20001:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.modelID = 53828;
			itemDef.name = "Glaiven boots";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 165;
			itemDef.rotationX = 99;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -7;
			itemDef.femaleEquip1 = 53309;
			itemDef.maleEquip1 = 53309;
			itemDef.description = "A pair of Glaiven boots.";
			break;

		case 20002:
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[4] = "Drop";
			itemDef.description = "A pair of Ragefire boots.";
			itemDef.modelID = 53897;
			itemDef.name = "Ragefire boots";
			itemDef.modelZoom = 900;
			itemDef.rotationY = 165;
			itemDef.rotationX = 99;
			itemDef.modelOffsetX = 3;
			itemDef.modelOffsetY = -7;
			itemDef.maleEquip1 = 53330;
			itemDef.femaleEquip1 = 53651;
			break;

		case 14018:
			itemDef.modelID = 5324;
			itemDef.name = "Ornate katana";
			itemDef.modelZoom = 1520;
			itemDef.rotationY = 1570;
			itemDef.rotationX = 157;
			itemDef.modelOffsetX = 5;
			itemDef.modelOffsetY = 1;
			itemDef.value = 50000;
			itemDef.membersObject = true;
			itemDef.maleEquip1 = 5324;
			itemDef.femaleEquip1 = 5324;
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wield";
			itemDef.actions[4] = "Drop";
			break;
		case 14008:
			itemDef.modelID = 62714;
			itemDef.name = "Torva full helm";
			itemDef.description = "Torva full helm";
			itemDef.modelZoom = 672;
			itemDef.rotationY = 85;
			itemDef.rotationX = 1867;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -3;
			itemDef.maleEquip1 = 62738;
			itemDef.femaleEquip1 = 62754;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			itemDef.maleDialogue = 62729;
			itemDef.femaleDialogue = 62729;
			break;
		case 14009:
			itemDef.modelID = 62699;
			itemDef.name = "Torva platebody";
			itemDef.description = "Torva platebody";
			itemDef.modelZoom = 1506;
			itemDef.rotationY = 473;
			itemDef.rotationX = 2042;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 62746;
			itemDef.femaleEquip1 = 62762;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 14010:
			itemDef.modelID = 62701;
			itemDef.name = "Torva platelegs";
			itemDef.description = "Torva platelegs";
			itemDef.modelZoom = 1740;
			itemDef.rotationY = 474;
			itemDef.rotationX = 2045;
			itemDef.modelOffsetX = 0;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 62743;
			itemDef.femaleEquip1 = 62760;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 14011:
			itemDef.modelID = 62693;
			itemDef.name = "Pernix cowl";
			itemDef.description = "Pernix cowl";
			itemDef.modelZoom = 800;
			itemDef.rotationY = 532;
			itemDef.rotationX = 14;
			itemDef.modelOffsetX = -1;
			itemDef.modelOffsetY = 1;
			itemDef.maleEquip1 = 62739;
			itemDef.femaleEquip1 = 62756;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			itemDef.maleDialogue = 62731;
			itemDef.femaleDialogue = 62727;
			itemDef.editedModelColor = new int[2];
			itemDef.newModelColor = new int[2];
			itemDef.editedModelColor[0] = 4550;
			itemDef.newModelColor[0] = 0;
			itemDef.editedModelColor[1] = 4540;
			itemDef.newModelColor[1] = 0;
			break;

		case 14012:
			itemDef.modelID = 62709;
			itemDef.name = "Pernix body";
			itemDef.description = "Pernix body";
			itemDef.modelZoom = 1378;
			itemDef.rotationY = 485;
			itemDef.rotationX = 2042;
			itemDef.modelOffsetX = -1;
			itemDef.modelOffsetY = 7;
			itemDef.maleEquip1 = 62744;
			itemDef.femaleEquip1 = 62765;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 14013:
			itemDef.modelID = 62695;
			itemDef.name = "Pernix chaps";
			itemDef.description = "Pernix chaps";
			itemDef.modelZoom = 1740;
			itemDef.rotationY = 504;
			itemDef.rotationX = 0;
			itemDef.modelOffsetX = 4;
			itemDef.modelOffsetY = 3;
			itemDef.maleEquip1 = 62741;
			itemDef.femaleEquip1 = 62757;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;
		case 14014:
			itemDef.modelID = 62710;
			itemDef.name = "Virtus mask";
			itemDef.description = "Virtus mask";
			itemDef.modelZoom = 928;
			itemDef.rotationY = 406;
			itemDef.rotationX = 2041;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = -5;
			itemDef.maleEquip1 = 62736;
			itemDef.femaleEquip1 = 62755;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			itemDef.maleDialogue = 62728;
			itemDef.femaleDialogue = 62728;
			break;

		case 14015:
			itemDef.modelID = 62704;
			itemDef.name = "Virtus robe top";
			itemDef.description = "Virtus robe top";
			itemDef.modelZoom = 1122;
			itemDef.rotationY = 488;
			itemDef.rotationX = 3;
			itemDef.modelOffsetX = 1;
			itemDef.modelOffsetY = 0;
			itemDef.maleEquip1 = 62748;
			itemDef.femaleEquip1 = 62764;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;

		case 14016:
			itemDef.modelID = 62700;
			itemDef.name = "Virtus robe legs";
			itemDef.description = "Virtus robe legs";
			itemDef.modelZoom = 1740;
			itemDef.rotationY = 498;
			itemDef.rotationX = 2045;
			itemDef.modelOffsetX = -1;
			itemDef.modelOffsetY = 4;
			itemDef.maleEquip1 = 62742;
			itemDef.femaleEquip1 = 62758;
			itemDef.groundActions = new String[5];
			itemDef.groundActions[2] = "Take";
			itemDef.actions = new String[5];
			itemDef.actions[1] = "Wear";
			itemDef.actions[2] = "Check-charges";
			itemDef.actions[4] = "Drop";
			break;
		case 9924:
		case 9923:
		case 9925:
		case 9921:
		case 9922:
			itemDef.editedModelColor = new int[] { 1 };
			break;
		}
		return itemDef;
	}

	public void imitate(ItemDefinition other) {
		name = other.name;
		description = other.description;
		editedModelColor = other.editedModelColor;
		newModelColor = other.newModelColor;
		sizeX = other.sizeX;
		sizeY = other.sizeY;
		sizeZ = other.sizeZ;
		rotationY = other.rotationY;
		rotationX = other.rotationX;
		modelOffsetX = other.modelOffsetX;
		modelOffsetY = other.modelOffsetY;
		rotationZ = other.rotationZ;
		modelZoom = other.modelZoom;
		modelID = other.modelID;
		actions = other.actions;
		maleEquip1 = other.maleEquip1;
		maleEquip2 = other.maleEquip2;
		maleEquip3 = other.maleEquip3;
		femaleEquip1 = other.femaleEquip1;
		femaleEquip2 = other.femaleEquip2;
		femaleEquip3 = other.femaleEquip3;
		maleDialogue = other.maleDialogue;
		maleDialogueModel = other.maleDialogueModel;
		femaleDialogue = other.femaleDialogue;
		femaleDialogue = other.femaleDialogueModel;
	}

	private void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				modelID = stream.readUnsignedWord();
			} else if (i == 2) {
				name = stream.readString();
			} else if (i == 3) {
				description = stream.readString();
			} else if (i == 4) {
				modelZoom = stream.readUnsignedWord();
			} else if (i == 5) {
				rotationY = stream.readUnsignedWord();
			} else if (i == 6) {
				rotationX = stream.readUnsignedWord();
			} else if (i == 7) {
				modelOffsetX = stream.readUnsignedWord();
				if (modelOffsetX > 32767) {
					modelOffsetX -= 0x10000;
				}
			} else if (i == 8) {
				modelOffsetY = stream.readUnsignedWord();
				if (modelOffsetY > 32767) {
					modelOffsetY -= 0x10000;
				}
			} else if (i == 10) {
				stream.readUnsignedWord();
			} else if (i == 11) {
				stackable = true;
			} else if (i == 12) {
				value = stream.readUnsignedWord();
			} else if (i == 16) {
				membersObject = true;
			} else if (i == 23) {
				maleEquip1 = stream.readUnsignedWord();
				maleYOffset = stream.readSignedByte();
			} else if (i == 24) {
				maleEquip2 = stream.readUnsignedWord();
			} else if (i == 25) {
				femaleEquip1 = stream.readUnsignedWord();
				femaleYOffset = stream.readSignedByte();
			} else if (i == 26) {
				femaleEquip2 = stream.readUnsignedWord();
			} else if (i >= 30 && i < 35) {
				if (groundActions == null) {
					groundActions = new String[5];
				}
				groundActions[i - 30] = stream.readString();
				if (groundActions[i - 30].equalsIgnoreCase("hidden")) {
					groundActions[i - 30] = null;
				}
			} else if (i >= 35 && i < 40) {
				if (actions == null) {
					actions = new String[5];
				}
				actions[i - 35] = stream.readString();
				if (actions[i - 35].equalsIgnoreCase("null")) {
					actions[i - 35] = null;
				}
			} else if (i == 40) {
				int j = stream.readUnsignedByte();
				editedModelColor = new int[j];
				newModelColor = new int[j];
				for (int k = 0; k < j; k++) {
					editedModelColor[k] = stream.readUnsignedWord();
					newModelColor[k] = stream.readUnsignedWord();
				}
			} else if (i == 78) {
				maleEquip3 = stream.readUnsignedWord();
			} else if (i == 79) {
				femaleEquip3 = stream.readUnsignedWord();
			} else if (i == 90) {
				maleDialogue = stream.readUnsignedWord();
			} else if (i == 91) {
				femaleDialogue = stream.readUnsignedWord();
			} else if (i == 92) {
				maleDialogueModel = stream.readUnsignedWord();
			} else if (i == 93) {
				femaleDialogueModel = stream.readUnsignedWord();
			} else if (i == 95) {
				rotationZ = stream.readUnsignedWord();
			} else if (i == 97) {
				certID = stream.readUnsignedWord();
			} else if (i == 98) {
				certTemplateID = stream.readUnsignedWord();
			} else if (i >= 100 && i < 110) {
				if (stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}
				stackIDs[i - 100] = stream.readUnsignedWord();
				stackAmounts[i - 100] = stream.readUnsignedWord();
			} else if (i == 110) {
				sizeX = stream.readUnsignedWord();
			} else if (i == 111) {
				sizeY = stream.readUnsignedWord();
			} else if (i == 112) {
				sizeZ = stream.readUnsignedWord();
			} else if (i == 113) {
				shadow = stream.readSignedByte();
			} else if (i == 114) {
				lightness = stream.readSignedByte() * 5;
			} else if (i == 115) {
				team = stream.readUnsignedByte();
			} else if (i == 116) {
				lendID = stream.readUnsignedWord();
			} else if (i == 117) {
				lentItemID = stream.readUnsignedWord();
			}
		} while (true);
	}

	public static void setSettings() {
		try {
			prices = new int[22694];
			int index = 0;
			for (String line : Files.readAllLines(Paths.get(signlink.findcachedir() + "data/data.txt"),
					Charset.defaultCharset())) {
				prices[index] = Integer.parseInt(line);
				index++;
			}
			for (int i : UNTRADEABLE_ITEMS) {
				untradeableItems.add(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void toNote() {
		ItemDefinition itemDef = forID(certTemplateID);
		modelID = itemDef.modelID;
		modelZoom = itemDef.modelZoom;
		rotationY = itemDef.rotationY;
		rotationX = itemDef.rotationX;
		rotationZ = itemDef.rotationZ;
		modelOffsetX = itemDef.modelOffsetX;
		modelOffsetY = itemDef.modelOffsetY;
		editedModelColor = itemDef.editedModelColor;
		newModelColor = itemDef.newModelColor;
		ItemDefinition itemDef_1 = forID(certID);
		name = itemDef_1.name;
		membersObject = itemDef_1.membersObject;
		value = itemDef_1.value;
		String s = "a";
		char c = itemDef_1.name.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}
		description = ("Swap this note at any bank for " + s + " " + itemDef_1.name + ".");
		stackable = true;
	}

	private void toLend() {
		ItemDefinition itemDef = forID(lentItemID);
		actions = new String[5];
		modelID = itemDef.modelID;
		modelOffsetX = itemDef.modelOffsetX;
		rotationX = itemDef.rotationX;
		modelOffsetY = itemDef.modelOffsetY;
		modelZoom = itemDef.modelZoom;
		rotationY = itemDef.rotationY;
		rotationZ = itemDef.rotationZ;
		value = 0;
		ItemDefinition itemDef_1 = forID(lendID);
		maleDialogueModel = itemDef_1.maleDialogueModel;
		editedModelColor = itemDef_1.editedModelColor;
		maleEquip3 = itemDef_1.maleEquip3;
		maleEquip2 = itemDef_1.maleEquip2;
		femaleDialogueModel = itemDef_1.femaleDialogueModel;
		maleDialogue = itemDef_1.maleDialogue;
		groundActions = itemDef_1.groundActions;
		maleEquip1 = itemDef_1.maleEquip1;
		name = itemDef_1.name;
		femaleEquip1 = itemDef_1.femaleEquip1;
		membersObject = itemDef_1.membersObject;
		femaleDialogue = itemDef_1.femaleDialogue;
		femaleEquip2 = itemDef_1.femaleEquip2;
		femaleEquip3 = itemDef_1.femaleEquip3;
		newModelColor = itemDef_1.newModelColor;
		team = itemDef_1.team;
		if (itemDef_1.actions != null) {
			for (int i_33_ = 0; i_33_ < 4; i_33_++) {
				actions[i_33_] = itemDef_1.actions[i_33_];
			}
		}
		actions[4] = "Discard";
	}

	public static Sprite getSprite(int i, int j, int k, int zoom) {
		if (k == 0 && zoom != -1) {
			Sprite sprite = (Sprite) spriteCache.get(i);
			if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}
			if (sprite != null) {
				return sprite;
			}
		}
		ItemDefinition itemDef = forID(i);
		if (itemDef.stackIDs == null) {
			j = -1;
		}
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0) {
					i1 = itemDef.stackIDs[j1];
				}
			}

			if (i1 != -1) {
				itemDef = forID(i1);
			}
		}
		Model model = itemDef.getItemModelFinalised(1);
		if (model == null) {
			return null;
		}
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if (sprite == null) {
				return null;
			}
		}
		if (itemDef.lendID != -1) {
			sprite = getSprite(itemDef.lendID, 50, 0);
			if (sprite == null) {
				return null;
			}
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Rasterizer.textureInt1;
		int l1 = Rasterizer.textureInt2;
		int ai[] = Rasterizer.anIntArray1472;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Rasterizer.aBoolean1464 = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Rasterizer.setDefaultBounds();
		int k3 = itemDef.modelZoom;
		if (zoom != -1 && zoom != 0) {
			k3 = (itemDef.modelZoom * 100) / zoom;
		}
		if (k == -1) {
			k3 = (int) ((double) k3 * 1.5D);
		}
		if (k > 0) {
			k3 = (int) ((double) k3 * 1.04D);
		}
		int l3 = Rasterizer.anIntArray1470[itemDef.rotationY] * k3 >> 16;
		int i4 = Rasterizer.anIntArray1471[itemDef.rotationY] * k3 >> 16;
		model.renderSingle(itemDef.rotationX, itemDef.rotationZ, itemDef.rotationY, itemDef.modelOffsetX,
				l3 + model.modelHeight / 2 + itemDef.modelOffsetY, i4 + itemDef.modelOffsetY);
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--) {
				if (sprite2.myPixels[i5 + j4 * 32] != 0) {
					continue;
				}
				if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1) {
					sprite2.myPixels[i5 + j4 * 32] = 1;
					continue;
				}
				if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1) {
					sprite2.myPixels[i5 + j4 * 32] = 1;
					continue;
				}
				if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1) {
					sprite2.myPixels[i5 + j4 * 32] = 1;
					continue;
				}
				if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1) {
					sprite2.myPixels[i5 + j4 * 32] = 1;
				}
			}

		}

		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--) {
					if (sprite2.myPixels[j5 + k4 * 32] != 0) {
						continue;
					}
					if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1) {
						sprite2.myPixels[j5 + k4 * 32] = k;
						continue;
					}
					if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1) {
						sprite2.myPixels[j5 + k4 * 32] = k;
						continue;
					}
					if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1) {
						sprite2.myPixels[j5 + k4 * 32] = k;
						continue;
					}
					if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1) {
						sprite2.myPixels[j5 + k4 * 32] = k;
					}
				}

			}

		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--) {
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0) {
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
					}
				}

			}

		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		if (itemDef.lendID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		if (k == 0 && i != 5572 && i != 5573 && i != 640 && i != 650 && i != 630 && i != 1121  && i != 544  && i != 542 
				&& i != 1165 && i != 1125 && i != 1283 && i != 1077 && i != 7332 && i != 4215
				
				)
		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Rasterizer.textureInt1 = k1;
		Rasterizer.textureInt2 = l1;
		Rasterizer.anIntArray1472 = ai;
		Rasterizer.aBoolean1464 = true;
		sprite2.maxWidth = itemDef.stackable ? 33 : 32;
		sprite2.maxHeight = j;
		return sprite2;
	}

	public static Sprite getSprite(int i, int j, int k) {
		if (k == 0) {
			Sprite sprite = (Sprite) spriteCache.get(i);
			if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}
			if (sprite != null) {
				return sprite;
			}
		}
		ItemDefinition itemDef = forID(i);
		if (itemDef.stackIDs == null) {
			j = -1;
		}
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0) {
					i1 = itemDef.stackIDs[j1];
				}
			}
			if (i1 != -1) {
				itemDef = forID(i1);
			}
		}
		Model model = itemDef.getItemModelFinalised(1);
		if (model == null) {
			return null;
		}
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if (sprite == null) {
				return null;
			}
		}
		if (itemDef.lentItemID != -1) {
			sprite = getSprite(itemDef.lendID, 50, 0);
			if (sprite == null) {
				return null;
			}
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Rasterizer.textureInt1;
		int l1 = Rasterizer.textureInt2;
		int ai[] = Rasterizer.anIntArray1472;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Rasterizer.aBoolean1464 = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Rasterizer.setDefaultBounds();
		int k3 = itemDef.modelZoom;
		if (k == -1) {
			k3 = (int) ((double) k3 * 1.5D);
		}
		if (k > 0) {
			k3 = (int) ((double) k3 * 1.04D);
		}
		int l3 = Rasterizer.anIntArray1470[itemDef.rotationY] * k3 >> 16;
		int i4 = Rasterizer.anIntArray1471[itemDef.rotationY] * k3 >> 16;
		model.renderSingle(itemDef.rotationX, itemDef.rotationZ, itemDef.rotationY, itemDef.modelOffsetX,
				l3 + model.modelHeight / 2 + itemDef.modelOffsetY, i4 + itemDef.modelOffsetY);
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--) {
				if (sprite2.myPixels[i5 + j4 * 32] == 0) {
					if (i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					} else if (j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1) {
						sprite2.myPixels[i5 + j4 * 32] = 1;
					}
				}
			}
		}
		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--) {
					if (sprite2.myPixels[j5 + k4 * 32] == 0) {
						if (j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						} else if (k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1) {
							sprite2.myPixels[j5 + k4 * 32] = k;
						}
					}
				}
			}
		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--) {
					if (sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0) {
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
					}
				}
			}
		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		if (itemDef.lentItemID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}
		if (k == 0 && i != 5572 && i != 5573 && i != 640 && i != 650 && i != 630 && i != 1121  && i != 544  && i != 542
				&& i != 1165 && i != 1125 && i != 1283 && i != 1077 && i != 7332 && i != 4215) {
			spriteCache.put(sprite2, i);
		}
		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Rasterizer.textureInt1 = k1;
		Rasterizer.textureInt2 = l1;
		Rasterizer.anIntArray1472 = ai;
		Rasterizer.aBoolean1464 = true;
		if (itemDef.stackable) {
			sprite2.maxWidth = 33;
		} else {
			sprite2.maxWidth = 32;
		}
		sprite2.maxHeight = j;
		return sprite2;
	}

	public Model getItemModelFinalised(int amount) {
		if (stackIDs != null && amount > 1) {
			int stackId = -1;
			for (int k = 0; k < 10; k++) {
				if (amount >= stackAmounts[k] && stackAmounts[k] != 0) {
					stackId = stackIDs[k];
				}
			}
			if (stackId != -1) {
				return forID(stackId).getItemModelFinalised(1);
			}
		}
		Model model = (Model) modelCache.get(id);
		if (model != null) {
			return model;
		}
		model = Model.fetchModel(modelID);
		if (model == null) {
			return null;
		}
		if (sizeX != 128 || sizeY != 128 || sizeZ != 128) {
			model.scaleT(sizeX, sizeZ, sizeY);
		}
		if (editedModelColor != null) {
			for (int l = 0; l < editedModelColor.length; l++) {
				model.recolour(editedModelColor[l], newModelColor[l]);
			}
		}
		model.light(64 + shadow, 768 + lightness, -50, -10, -50, true);
		model.rendersWithinOneTile = true;
		if (id != 5572 && id != 5573 && id != 640 && id != 650 && id != 630 && id != 1121 && id != 544 && id != 542
				&& id != 1165 && id != 1125 && id != 1283 && id != 1077 && id != 7332 && id != 4215)
		{
			modelCache.put(model, id);
		}
		return model;
	}

	public Model getItemModel(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if (i >= stackAmounts[k] && stackAmounts[k] != 0) {
					j = stackIDs[k];
				}
			}
			if (j != -1) {
				return forID(j).getItemModel(1);
			}
		}
		Model model = Model.fetchModel(modelID);
		if (model == null) {
			return null;
		}
		if (editedModelColor != null) {
			for (int l = 0; l < editedModelColor.length; l++) {
				model.recolour(editedModelColor[l], newModelColor[l]);
			}
		}
		return model;
	}

	public static final int[] UNTRADEABLE_ITEMS = { 13661, 13262, 6529, 6950, 1464, 2996, 2677, 2678, 2679, 2680, 2682,
			2683, 2684, 2685, 2686, 2687, 2688, 2689, 2690, 6570, 12158, 12159, 12160, 12163, 12161, 12162, 19143,
			19149, 19146, 19157, 19162, 19152, 4155, 8850, 10551, 8839, 8840, 8842, 11663, 11664, 11665, 3842, 3844,
			3840, 8844, 8845, 8846, 8847, 8848, 8849, 8850, 10551, 7462, 7461, 7460, 7459, 7458, 7457, 7456, 7455, 7454,
			7453, 8839, 8840, 8842, 11663, 11664, 11665, 10499, 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808,
			9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766, 9749, 9755, 9752, 9770, 9758, 9761,
			9764, 9803, 9809, 9785, 9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779, 9788, 9812, 9767, 9747, 9753, 9750,
			9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765,
			9948, 9949, 9950, 12169, 12170, 12171, 20671, 14641, 14642, 6188, 10954, 10956, 10958, 3057, 3058, 3059,
			3060, 3061, 7594, 7592, 7593, 7595, 7596, 14076, 14077, 14081, 10840, 10836, 6858, 6859, 10837, 10838,
			10839, 9925, 9924, 9923, 9922, 9921, 4084, 4565, 20046, 20044, 20045, 1050, 14595, 14603, 14602, 14605,
			11789, 19708, 19706, 19707, 4860, 4866, 4872, 4878, 4884, 4896, 4890, 4896, 4902, 4932, 4938, 4944, 4950,
			4908, 4914, 4920, 4926, 4956, 4926, 4968, 4994, 4980, 4986, 4992, 4998, 18778, 18779, 18780, 18781, 13450,
			13444, 13405, 15502, 10548, 10549, 10550, 10551, 10555, 10552, 10553, 2412, 2413, 2414, 20747, 18365, 18373,
			18371, 15246, 12964, 12971, 12978, 14017, 757, 8851, 13855, 13848, 13857, 13856, 13854, 13853, 13852, 13851,
			13850, 5509, 13653, 14021, 14020, 19111, 14019, 14022, 19785, 19786, 18782, 18351, 18349, 18353, 18357,
			18355, 18359, 18335, 19785, 19786, 18351, 18349, 18353, 18357, 18355, 18359, 18361, 18335, 11998, 11999,
			12000, 12001, 12002, 12003, 12004, 12005, 12006, 15103, 15104, 15106, 15105, 21359, 19906, 12902, 12903,
			12904, 20659, 12856, 12855, 12854, 18510, 18363, };

	public ItemDefinition() {
		id = -1;
	}

	public byte femaleYOffset;
	public int value;
	public int[] editedModelColor;
	public int id;
	public static MemCache spriteCache = new MemCache(100);
	public static MemCache modelCache = new MemCache(50);
	public int[] newModelColor;
	public boolean membersObject;
	public int femaleEquip3;
	public int certTemplateID;
	public int femaleEquip2;
	public int maleEquip1;
	public int maleDialogueModel;
	public int sizeX;
	public String groundActions[];
	public int modelOffsetX;
	public String name;
	public static ItemDefinition[] cache;
	public int femaleDialogueModel;
	public int modelID;
	public int maleDialogue;
	public boolean stackable;
	public String description;
	public int certID;
	public static int cacheIndex;
	public int modelZoom;
	public static Stream stream;
	public int lightness;
	public int maleEquip3;
	public int maleEquip2;
	public String actions[];
	public int rotationY;
	public int sizeZ;
	public int sizeY;
	public int[] stackIDs;
	public int modelOffsetY;
	public static int[] streamIndices;
	public int shadow;
	public int femaleDialogue;
	public int rotationX;
	public int femaleEquip1;
	public int[] stackAmounts;
	public int team;
	public static int totalItems;
	public int rotationZ;
	public byte maleYOffset;
	public byte maleXOffset;
	public byte maleZOffset;
	public int lendID;
	public int lentItemID;
	public boolean untradeable;

	public boolean members;

	public boolean stockMarket;
	public int cost;

	public int notedID;
	public int anInt1879;
}
