package de.fhd.medien.mait.sfa;


/**
 * 
 * @author Abdul
 *
 */
public class Spielfeld {
	
	private int aufgedeckteFelder;
	private int[][] feld, result; 
	public final int box_n=3;
	
	/**
	 * Es wird ein Wert für aufgedeckteFelder per Zufall generiert, der abhängig
	 *  von der Schwierigkeit ist. Desweiteren wird das Sudospielfeld erzeugt und
	 *   mit nullen gefüllt.
	 * @param schwierigkeit, 1=leicht, 2=normal, 3=schwer
	 */
	Spielfeld(int schwierigkeit){
		MyRandom zufall = new MyRandom();
		
		switch (schwierigkeit) {
		case 1: // leicht 41-50 aufgedeckte Felder
				aufgedeckteFelder = zufall.nextInt(41, 50);
			break;
		case 2: // normal 31-40 aufgedeckte Felder
			aufgedeckteFelder = zufall.nextInt(31, 40);
			break;
			
		case 3 : // schwer 20-30 aufgedeckte Felder
			aufgedeckteFelder = zufall.nextInt(20, 30);
			break;

		default:
			aufgedeckteFelder = 0;
			break;
		}
		
		// Spielfeld erzeugen
		feld = new int[9][9];
		result = new int[9][9];
		
		// Spielfeld mit Nullen füllen
		for (int i=0;i<feld.length;i++)
			for (int j = 0;j<feld[i].length;j++){
				feld[i][j]=0;
				result[i][j]=0;
			}
				
					
	}// end of constructor
	
	/**
	 * Das Sudoku-Spielfeld wird durchlaufen und mit einer Zufallszahl (1-9)
	 * gefüllt, die vorher überprüft, ob eine Regel verletz wird.
	 * 
	 */
	public void fuelleSpielfeld(){
		MyRandom zufall = new MyRandom();
		int random = zufall.nextInt(1, 9);
		
		for (int i=0;i<feld.length;i++)
			for (int j=0;j<feld.length;j++){
				if (check(i, j, random )){
					random = zufall.nextInt(1, 9);
				}else {
					feld[i][j] = random;
				}
						
								
			} // innere schleife
				
		
	} // end of void fuelleSpielfeld(int[][] feld)
	
	
	/**
	 * Sudoku-Spielfeld wird Feld für Feld durchlaufen und mit einer Zufallszahl
	 * gefüllt, die per Regeln überprüft, ob sie da rein passt.
	 * 
	 * @param x, entspricht der Zeile
	 * @param y, entspricht der Spalte
	 * @return true-> gefüllt, false->unvollständig
	 */
	public void fuelleSpielfeld1(){
		MyRandom zufall = new MyRandom();
		int random = zufall.nextInt(1, 9);
		
		
		
		for (int i=0;i<feld.length;i++)
			for (int j=0;j<feld.length;j++){
				
				
				if (check(i, j, random )){
					
					/*while( check(i, j, random ) ){
						random = zufall.nextInt(1, 9);
						if (check(i, j, random )==false )
							feld[i][j]=random;
					}*/
					random = zufall.nextInt(1, 9);
				}else {
					feld[i][j] = random;
				}
						
								
			} // innere schleife
				
		
	} // end of void fuelleSpielfeld(int i, int j)
	
	/**
	 * 
	 * @param anzahlFelder
	 * @return
	 */
	public int[][] generateSudoku(int anzahlFelder){
		int[][] test;
		
		
		
		
		return null;//result;
	} // end of int[][] generateSudoku(int anzahlFelder)
	
	
	/**
	 * Prueft ob dieses Feld gesetzt werden kann (ruft dabei 3 Subfunktionen auf)
	 * @return-> Gibt true zurueck, wenn der Wert bereits existiert
	 */
	public boolean check(int i, int j, int value) {
	
 
		if(checkH(j, value))
			return true;
 
		if(checkV(i, value))
			return true;		
 
		if(checkBox(i, j, value))
			return true;
 
		return false;
	} // end of boolean check(int i, int j, int value)
 
	/**
	 * Prueft ob der Wert bereits in der (horizontalen) SPALTE existiert
	 * @return-> Gibt true zurueck, wenn der Wert bereits existiert
	 */
	public boolean checkH(int j, int value) {
		for(int a=0; a<feld.length; a++)
		    if(feld[a][j] == value)
		    	return true;
 
		return false;
	} // end of boolean checkH(int j, int value)
 
	/**
	 * Prueft ob der Wert bereits in der (vertikalen) REIHE existiert
	 * @return-> Gibt true zurueck, wenn der Wert bereits existiert
	 */
	public boolean checkV(int i, int value) {
		for(int a=0; a<feld.length; a++)
		    if(feld[i][a] == value)
		    	return true;
 
		return false;
	} // public boolean checkV(int i, int value)
 
	/**
	 * Prueft ob der Wert bereits in der BOX existiert
	 * @return-> Gibt true zurueck, wenn der Wert bereits existiert
	 */
	public boolean checkBox(int i, int j, int value) {
		// oberes, linkes Eck der Box herausfinden (2|8 zu 0|6)
		int i_start = (int)(i/box_n) * box_n;
		int j_start = (int)(j/box_n) * box_n;
 
		for(int a=i_start; a<i_start+box_n; a++)
		    for(int b=j_start; b<j_start+box_n; b++)
		    	if(feld[a][b] == value)
		    		return true;
 
		return false;
	} // end of boolean checkBox(int i, int j, int value)
	
	/**
	 * Sudoku-Spielfeld wird ausgegeben 
	 */
	public void ausgabe(){
		for (int i=0;i<feld.length;i++){
			for (int j = 0;j<feld[i].length;j++) {
				System.out.print(feld[i][j] + " ");
			}
			System.out.println();
		}
	} // end of ausgabe()
	
	
	
} // end of class 
