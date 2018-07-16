package main;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import csnd6.Csound;
import csnd6.CsoundPerformanceThread;
import csnd6.csnd6;
import orc.Orchestra;

public class Generator {
	
	public static void main(String[] args) {
		Orchestra orc = Rhythm.getOrchestra();
		System.out.println(orc);
		List<HashMap<Integer, String>> paramMap = orc.mapParams();
		for (int i : paramMap.get(0).keySet()) {
			System.out.println(i + ": " + paramMap.get(0).get(i));
		}
		/**
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
		
		while (true) {
			List<Rhythm> batch = session.createBatch();
			for (Rhythm rhythm : batch) {
				
			}
			break;
		}
		
		in.close();*/
	}
}
