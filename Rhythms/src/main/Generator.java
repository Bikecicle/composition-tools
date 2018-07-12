package main;

import java.util.Scanner;

public class Generator {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Voice count: ");
		int voiceCount = in.nextInt();
		System.out.println("Length (measures): ");
		int length = in.nextInt();
		System.out.println("Quantization (measure division): ");
		int quant = in.nextInt();
		System.out.println("Tempo (bpm): ");
		float tempo = in.nextFloat();
		
		Session session = new Session(voiceCount, length, quant, tempo);
		
		
		
		in.close();
	}

}
