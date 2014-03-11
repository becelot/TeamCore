package com.becelot.spacerace.team;

public enum TeamColor {
	TC_GRAY("§7"),
	TC_RED("§4"),
	TC_YELLOW("§6"),
	TC_GREEN("§2"),
	TC_BLUE("§1"),
	TC_CYAN("§b"),
	TC_PURPLE("§5");
	
	private final String chatPrefix;
	
	private TeamColor(String c) {
		this.chatPrefix = c;
	}
	
	public String getPrefix() {
		return chatPrefix;
	}
	
	public static TeamColor fromNum(int i) {
		switch(i) {
			case 1: return TC_RED;
			case 2: return TC_YELLOW;
			case 3: return TC_GREEN;
			case 4: return TC_BLUE;
			case 5: return TC_CYAN;
			case 6: return TC_PURPLE;
			default: return TC_GRAY;
		}
	}
}
