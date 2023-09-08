package com.example.demo.request;

public class LogicalPrograms {
	
	public static void main(String[] args) {
		String s = "HellOSateeshHowAreYours";
		int splitNo = 4;
		System.out.println(splitString(s,splitNo));
	}
	
	public static String splitString(String s, int splitNo) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(int i = 0; i <= s.length()-1; i++) {
			if(count == splitNo) {
				sb.append("\n");
				sb.append(s.charAt(i));
				count = 1;
			}else {
				sb.append(s.charAt(i));
				count++;
			}
		}	
		return sb.toString();	
	}
	
	public static String swapCase(String s) {
		String[] split = s.split("");
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String vowels = "AEIOUaeiou";
		StringBuilder updatedString = new StringBuilder();
		for(int i = 0; i < split.length; i++ ) {
			if(!vowels.contains(split[i]) && upper.contains(split[i])) {
				updatedString.append(split[i].toLowerCase());
			}else if(!vowels.contains(split[i])) {
				updatedString.append(split[i].toUpperCase());
			}else {
				updatedString.append(split[i]);
			}
		}
		return updatedString.toString();
	}

}
