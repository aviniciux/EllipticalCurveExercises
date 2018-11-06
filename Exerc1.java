/**************************************************************************
 * (C) Copyright 2018 by Anderson Oliveira. All Rights Reserved.          *
 *                                                                        *
 * Description: Let be an elliptical curve given by the function          *
 * y^2 = x^3 + Ax + B into a finite Galois field of order p,              *
 * this program returns all the pairs (x,y) beyond the zero element       *
 * which belongs to the curve.                                            *
 *                                                                        *
 * Inputs:                                                                *
 *         A;                                                             *
 *         B;                                                             *
 *         p;                                                             *
 *                                                                        *
 *************************************************************************/

import java.util.Scanner; // program uses class Scanner

public class Exerc1 
{
   // main method begins execution of Java application
   public static void main( String[] args )
   {
	   
	   int A, B, p, x, y, test1, test2, nCount = 0; // first number to add
      // create Scanner to obtain input from command window
      Scanner input = new Scanner( System.in );  
      System.out.println( " Elliptical curve: y^2 = x^3 + Ax + B " ); // prompt 
      System.out.println( " Enter values for: A B p \n Ex.: 22 15 47 \n : " ); // prompt
      A = input.nextInt(); // read the value for A
      B = input.nextInt(); // read the value for B
      p = input.nextInt(); // read the Galois field order
      input.close(); // close the scanner;

      for(x=0; x<p; x++)
      {
          for(y=0; y<p; y++)
          {    		 
			  test1 =  ( (int)Math.pow(x, 3)  + A*x + B) % p;
			  test2 =  ( (int)Math.pow(y, 2)) % p ;
			  
			  if(test1 == test2) 
			  {
				  nCount++;
				  System.out.printf( "\n %3d (%3d,%3d) = (%3d,%3d)  ",nCount,x,y,test1,test2 ); // prompt
			  }
          }	
      }

   } // end method main

} // end class Exerc1
