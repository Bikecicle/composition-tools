package filters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import visual.ViewTable;

public class Filter {
	public static void main(String[] args) {
		String input = "data/tables/test.tbl";
		String output = "data/tables/testF.tbl";
		int[][] table = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(input));
			table = (int[][]) in.readObject();
			in.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("Table loaded");

		table = edgeDetection(table, 32);
		/*
		 * for (int i = 0; i < 8; i++) { table = blur(table); } table =
		 * pulseLimiter(table);
		 */
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(output));
			out.writeObject(table);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Table saved");
		ViewTable.main(new String[0]);
	}

	public static int[][] edgeDetection(int[][] table, int threshold) {
		int steps = table.length;
		int res = table[0].length;
		int[][] newTable = new int[steps][res];
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (i < steps - 1 && Math.abs(table[i][j] - table[i + 1][j]) >= threshold) {
					newTable[i][j] = 1;
				} else if (i > 0 && Math.abs(table[i][j] - table[i - 1][j]) >= threshold) {
					newTable[i][j] = 1;
				} else if (j < res - 1 && Math.abs(table[i][j] - table[i][j + 1]) >= threshold) {
					newTable[i][j] = 1;
				} else if (j > 0 && Math.abs(table[i][j] - table[i][j - 1]) >= threshold) {
					newTable[i][j] = 1;
				} else {
					newTable[i][j] = 0;
				}
			}
		}
		return newTable;
	}

	public static int[][] blur(int[][] table) {
		int steps = table.length;
		int res = table[0].length;
		int[][] newTable = new int[steps][res];
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (table[i][j] == 1) {
					newTable[i][j] = 1;
					if (i > 0)
						newTable[i - 1][j] = 1;
					if (i < steps - 1)
						newTable[i + 1][j] = 1;
					if (j > 0)
						newTable[i][j - 1] = 1;
					if (j < res - 1)
						newTable[i][j + 1] = 1;
				}
			}
		}
		return newTable;
	}

	public static int[][] pulseLimiter(int[][] table) {
		int steps = table.length;
		int res = table[0].length;
		int[][] newTable = new int[steps][res];
		int chain = 0;
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (table[i][j] == 1) {
					chain++;
					if (j == res - 1) {
						newTable[i][j - chain / 2] = 1;
						chain = 0;
					}
				} else if (chain > 0) {
					newTable[i][j - chain / 2] = 1;
					chain = 0;
				}
			}
		}
		for (int j = 0; j < res; j++) {
			for (int i = 0; i < steps; i++) {
				if (table[i][j] == 1) {
					chain++;
					if (i == steps - 1) {
						newTable[i - chain / 2][j] = 1;
						chain = 0;
					}
				} else if (chain > 0) {
					newTable[i - chain / 2][j] = 1;
					chain = 0;
				}
			}
		}
		return newTable;
	}

	public static int[][] denoise(int[][] table) {
		int steps = table.length;
		int res = table[0].length;
		int[][] newTable = new int[steps][res];
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (table[i][j] == 1) {
					if ((i == 0 || table[i - 1][j] == 0) && (i == steps - 1 || table[i + 1][j] == 0)
							&& (j == 0 || table[i][j - 1] == 0) && (j == res - 1 || table[i][j + 1] == 0)) {
						newTable[i][j] = 0;
					} else {
						newTable[i][j] = 1;
					}

				}
			}
		}
		return newTable;
	}

	public static int[][] trianglePeriod(int[][] table, int period) {
		int steps = table.length;
		int res = table[0].length;
		int[][] newTable = new int[steps][res];
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				newTable[i][j] = period / 2 - Math.abs((table[i][j] % period) - period / 2);
			}
		}
		return newTable;
	}
}
