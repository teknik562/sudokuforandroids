package de.fhd.medien.mait.sfa;


import java.util.Random;

/**
 * Erzeugt eine Zufallszahl.
 *
 * @author Abdul
 */
public class MyRandom extends Random
{
  
  /**
   * Erstellt eine neue Zufallszahl. Das Ergebnis basiert auf einem ZeitWert des System:
   * public Random() { this(System.currentTimeMillis()); }
   */
  public MyRandom()
  {
    super();
  }

  /**
   * liefert einen ganzzahligen Wert zwischen min (inklusive) und max (exklusive)
   *
   * @param min minimaler Zufallswert
   * @param max maximaler Zufallswert
   * @return Zufallszahl
   */
  public int nextInt(int min, int max)
  {
    return this.nextInt(max - min + 1) + min;
  }
}