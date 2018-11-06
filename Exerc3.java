/**************************************************************************
 * (C) Copyright 2018 by Anderson Oliveira. All Rights Reserved.          *
 *                                                                        *
 *     Problem 3:                                                         *
 *                                                                        *
 * Description: Let 'p' be a prime number, this program computes          * 
 * the inverse of 10 numbers distributed into the Galois field of         *
 * order 'p' using two methods: a brute force computing and an            *
 * optimized method that decomposes large values into exponentials        *
 * of 2. We compare the processing time of the two methods;               *
 *                                                                        *
 * Input:                                                                 *
 *         p;                                                             *
 *                                                                        *
 *************************************************************************/

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner; // program uses class Scanner

public class Exerc3 
{
   // main method begins execution of Java application
   public static void main( String args[] )
   {
	   
	  int p, k, a, tmp1, tmp2;
	  long time1, time2;
	  // We use the following variable to choice ten well -istributed numbers inside the finite field:
	  double dist[] = {0.09090, 0.18188, 0.27271, 0.36358, 0.45446, 0.54532, 0.63619, 0.72706, 0.834786, 0.9089 };
	  //////////////////////////////////////////////////////// 
      // create Scanner to obtain input from command window //
      //////////////////////////////////////////////////////// 	  
      Scanner input = new Scanner( System.in );     
      System.out.println( " Field order: (Ex.: 1020379)" ); // prompt 
      System.out.print( "p= " ); // prompt 
      p = input.nextInt(); // read the Galois field order 
      input.close(); // close the scanner;       
	  
      // The Loop will carry out until we reach P1:
      for(k=1; k<=10; k++)
      {    	  
    	  a = (int)(p*dist[k-1]); // This line chooses an integer number into between 0 and p
    	  final long startTime = System.nanoTime();
    	  tmp1 = InverseFiniteField(a,p);
    	  final long duration1 = System.nanoTime();
    	  tmp2 = BruteForceCompute(a,p);
    	  final long duration2 = System.nanoTime();
    	  time1=duration1-startTime;
    	  time2=duration2-duration1;    	  
    	  System.out.printf( "\n*************************\n %3d: %d (inverse [Meth1] = %d, inverse [Meth2] = %d)",k,a,tmp1,tmp2); // prompt  
    	  System.out.printf( "\n Optimized version: %10.4f micro seconds.",(double)(time1*0.001)); // prompt  
    	  System.out.printf( "\n Brute Force version: %10.4f micro seconds.",(double)(time2*0.001)); // prompt
    	  System.out.printf( "\n Brute Force version is ~ %d times slower than the optimized version.\n",(int)(((double)time2/(double)time1))); // prompt  	 
  	  
      }	  
	  return;
   } // end of method main  
 
   
   /*************************************************
   * This method computes the inverse of 'a' into   *
   * a finite Galois field of order p.              *
   * It is as the used method used in problem 2.    *
   *                                                *
   *  Input:                                        *
   *     a -> number to compute the inverse;        *
   *     p -> Field order;                          *
   *                                                *
   **************************************************/
   public static int InverseFiniteField( int a, int p )
   {
	   int inv=0, k, tmp2, n=1;
	   long tmp;
	   List<Integer> expVect = new ArrayList<Integer>();
	   List<Integer> expTwo = new ArrayList<Integer>();
	   
	   tmp = a % p; // a^1
	   expVect.add(a);
	   expTwo.add(1);
	   for(k=2; k< p-2; k=k*2 )
	   {
		   tmp = (tmp*tmp) % p;
		   tmp = tmp<0?p+tmp:tmp;	
		   tmp2 = (int)tmp;
		   expVect.add(tmp2);
		   expTwo.add(k);
		   n++;
	   }
	   
	   tmp=1; tmp2 = p-2;	   
	   for(k = n-1; k>=0; k--)
	   {
		   if(expTwo.get(k)<=tmp2)
		   {
			  tmp = (tmp * expVect.get(k)) % p;
			  tmp = tmp<0?p+tmp:tmp;
			  tmp2 = tmp2 - expTwo.get(k);			  
		   }		   
	   }
	   tmp2 = (int)(tmp % p);
	   inv = tmp2<0?p+tmp2:tmp2;
	   return inv;
   } // end of method InverseFiniteField


	/*************************************************
	* This method computes the inverse of 'a' into   *
	* a finite Galois field of order p.              *
	*                                                *
	*  Input:                                        *
	*     a -> number to compute the inverse;        *
	*     p -> Field order;                          *
	*                                                *
	**************************************************/
	public static int BruteForceCompute( int a, int p )
	{
		   int k, tmp, tmp2;
		   long  tmp3;
		   
		   tmp = a % p; // a^1
		   tmp2=tmp;
		   for(k=1; k< p-2; k++ )
		   {
			   tmp3 = ((long)tmp2*(long)tmp);
			   tmp2 = (int) (tmp3 % p);
		   }
		   return tmp2;
	} // end of method InverseFiniteField


} // end class Exerc2