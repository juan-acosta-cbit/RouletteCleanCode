package com.mezubo.refector;

public class Printer {
	
	final static int M = 1000;
	final static int RR = 50;
	final static int CC = 4;
	
	public static void main(String[] args) {
		
		int p[] = new int[M + 1];
		int pageNumber = 1;
		int j = 1;		
		boolean jPrime;
		int ord = 2;
		int square = 9;
		int mult[] = new int[31];
		p[1] = 2;
		for (int k = 1; k < M; k++) {
			do {
				j += 2;
				if (j == square) {
					ord++;
					square = p[ord] * p[ord];
					mult[ord - 1] = j;
				}
				jPrime = true;
				for(int n = 2; n < ord && jPrime; n++) {
					while (mult[n] < j) { mult[n] += p[n] * 2; }
					jPrime = mult[n] == j ? false : jPrime;	
				}
			} while (!jPrime);
			p[k] = j;
		}
		for(int pageOffSet = 1; pageOffSet <= M; pageOffSet += RR * CC) {
			System.out.println("The First "+ Integer.toString(M) + " Prime Numbers === Page " + Integer.toString(pageNumber++));
			for (int rowOffSet = pageOffSet; rowOffSet <= pageOffSet + RR - 1; rowOffSet++) {
				for ( int c = 0; c <= CC - 1; c++)
					if (rowOffSet + c * RR <= M)
						System.out.printf("%10d", p[rowOffSet + c * RR]);
				System.out.println();
			}
			System.out.println("\f");
		}
	}
}
