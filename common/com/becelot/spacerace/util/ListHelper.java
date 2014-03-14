package com.becelot.spacerace.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.StatCollector;

public class ListHelper {
	public static Map<Integer, String> countdownNotificationsList(String registry) {
		String thresholds = StatCollector.translateToLocal(registry + ".thresholds");
		String[] threshold = thresholds.split(",");
		
		Map<Integer, String> notifications = new HashMap<>();
		try {
			for (String s : threshold) {
				int second = Integer.parseInt(s);
				String notify = StatCollector.translateToLocal(registry + ".time." + s);
				notifications.put(second, notify);
			}
		} catch (Exception e) {
			Chat.sendToGameMod("A conversion error occured in the countdown timer!");
			e.printStackTrace();
		}
		
		return notifications;
	}
}
