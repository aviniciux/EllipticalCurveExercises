/**************************************************************************
 * (C) Copyright 2018 by Anderson Oliveira. All Rights Reserved.          *
 *                                                                        *
 * Description: Let be an elliptical curve given by the function          *
 * y^2 = x^3 + Ax + B into a finite Galois field of order p,              *
 * and a point P1 = (x1 ,y1) that belongs to the curve, this              * 
 * program returns the sequence of pairs (xN,yN) resulting from           *
 * the successive sums of P1 + P1 ... + P1 until the resulting            *
 * return to the primitive point P1.                                      *
 *                                                                        *
 * Inputs:                                                                *
 *         A;                                                             *
 *         B;                                                             *
 *         p;                                                             *
 *         x1;                                                            *
 *         y1;                                                            *
 *                                                                        *
 *************************************************************************/

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner; // program uses class Scanner

public class Exerc2 
{
   // main method begins execution of Java application
   public static void main( String args[] )
   {
	   
	  int A, B, p, x1, y1, x2, y2, x3=0, y3 = 0, m, b, count=1, tmp;
      //////////////////////////////////////////////////////// 
      // create Scanner to obtain input from command window //
      //////////////////////////////////////////////////////// 
      Scanner input = new Scanner( System.in );      
      System.out.println( " Elliptical curve: y^2 = x^3 + Ax + B " ); // prompt 
      System.out.print( " Enter values for: A B p x1 x2 \n Ex.: 22 15 47 3 22 \n : " ); // prompt
      A = input.nextInt(); // read the value for A
      B = input.nextInt(); // read the value for B
      p = input.nextInt(); // read the Galois field order
      x1 = input.nextInt(); // read x-axis
      y1 = input.nextInt(); // read y-axis
      input.close(); // close the scanner;      
      ///////////////////////////////////////////////////////
      int stopX = x1, stopY=y1;      
      // Verify if the primitive point (x1,y1) belongs to the curve:      
	  if(VerifyPointOnCurve( A, B, p, x1, y1 )==0)
	  {
		  System.out.printf( "\n The point (%d,%d) does not belongs to the curve",x1,y1); // prompt
		  return;
	  }  
	  // The first sum will be with P1 + P1, so we set P2 = P1:
	  x2 = x1; y2 = y1;
	  System.out.printf( "\n %3d: (%3d,%3d)",count++,x2,y2); // prompt
	  
      // The Loop will carry out until we reach P1:
      while(x3!=stopX || y3!=stopY)
      {    	  
    	  m = AngularCoeff( x1, y1, x2, y2, A, p );
    	  tmp = ((y1 - m*x1) % p);
    	  b = tmp<0?p+tmp:tmp;
    	  //System.out.printf( "\n (m,b)=(%d,%d) ",m,b);
    	  tmp = (( (m*m) -x1 -x2  ) % p);
    	  x3 = tmp<0?p+tmp:tmp;
    	  tmp = (( -(m*m*m) + (m*(x1+x2)) -b ) % p);
    	  y3 = tmp<0?p+tmp:tmp;
          // The loop ends when the point P3 is not on the curve:
    	  // In this case, P3 is the infinite point P0. Then, since
    	  // P1 + P2 = P0 when x1==x2 and y1==-y2 for coordinates
    	  // P1(x1,y1) and P2(x2,y2) we set P1 as (x1,-y1) and sum it
    	  // with the point P0
	      // we set P3 as P1.
    	  if(VerifyPointOnCurve( A, B, p, x3, y3 )==0) 
    	  {
    		  System.out.printf( "\n %3d: Point at the infinite: (%3d,%3d)",count++,x3,y3); // prompt    		   
    		  y1=(-y1)<0?p-y1:-y1;
    	  }
    	  else
    	  {
    		  System.out.printf( "\n %3d: (%3d,%3d)",count++,x3,y3); // prompt   
    	  }    	  
    	  x2=x3;y2=y3;    	  
      }	  
	  return;
   } // end of method main
   
   /*****************************************************
   *  This method computes the angle of inclination 'm' *
   *  which pass through the points P1 and P2 and       *
   *  belongs to the curve to y^2 = x^3 + Ax + B.       *                   
   *                                                    *
   *   Input:                                           *
   *     P1 = (x1,y1);                                  *
   *     P2 = (x2,y2);                                  *
   *     A -> as in Eq. above;                          *       
   *     p -> Field order;                              *
   *                                                    *                             
   *****************************************************/   
   public static int AngularCoeff( int x1, int y1, int x2, int y2, int A, int p )
   {
	   int mm = 0, numerator, denumerator;
	   
	   if(x1==x2 && y1==y2) {
		   numerator = ((3*(x1*x1)) + A ) % p;
		   denumerator = (2*y1) % p;
	   }
	   else {
		   numerator = (y2-y1) % p;
		   denumerator = (x2-x1) % p;
	   }
		   
	   mm = (numerator * InverseFiniteField(denumerator,p) ) % p;
	   
	   return mm;
   } // end of method VerifyPointOnCurve
   
   /**************************************************
   * This method verify if a point (x, y) is on the  *
   * elliptical curve y^2 = x^3 + Ax + B.            *
   *                                                 *
   *  Input:                                         *
   *     A -> as in Eq. above;                       *
   *     B -> as in Eq. above;                       *
   *     p -> Field order;                           *
   *     P1 = (x,y) -> point to verify;              *                                    
   *                                                 *                             
   ****************************************************/ 
   public static int VerifyPointOnCurve( int A, int B, int p, int x, int y )
   {
	   int IsTrue = 0, test1, test2;
	   
	  test1 =  ( (int)Math.pow(x, 3)  + A*x + B) % p;
	  test2 =  ( (int)Math.pow(y, 2)) % p ;
	  
	  if(test1 == test2)
		  IsTrue = 1;
	   
	   return IsTrue;
   } // end of method VerifyPointOnCurve
   
   /*************************************************
   * This method computes the inverse of 'a' into   *
   * a finite Galois field of order p.              *
   *                                                *
   *  Input:                                        *
   *     a -> number to compute the inverse;        *
   *     p -> Field order;                          *
   *                                                *
   **************************************************/
   public static int InverseFiniteField( int a, int p )
   {
	   int inv=0, k, tmp, tmp2, n=1;
	   List<Integer> expVect = new ArrayList<Integer>();
	   List<Integer> expTwo = new ArrayList<Integer>();
	   
	   tmp = a % p; // a^1
	   expVect.add(tmp);
	   expTwo.add(1);
	   for(k=2; k< p-2; k=k*2 )
	   {
		   tmp = (tmp*tmp) % p;
		   expVect.add(tmp);
		   expTwo.add(k);
		   n++;
	   }
	   
	   tmp=1; tmp2 = p-2;	   
	   for(k = n-1; k>=0; k--)
	   {
		   if(expTwo.get(k)<=tmp2)
		   {
			  tmp = tmp * expVect.get(k);			  
			  tmp2 = tmp2 - expTwo.get(k);			  
		   }		   
	   }
	   tmp2 = (tmp % p);
	   inv = tmp2<0?p+tmp2:tmp2;
	   return inv;
   } // end of method InverseFiniteField
   

} // end class Exerc2