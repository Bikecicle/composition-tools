package main;

import java.util.Scanner;

public class Generator {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Voice count: ");
		int voiceCount = in.nextInt();
		Session session = new Session(voiceCount);
		
		
		
		in.close();
	}

}
