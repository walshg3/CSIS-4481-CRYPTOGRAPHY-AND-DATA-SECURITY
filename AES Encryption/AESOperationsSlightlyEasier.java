package aes;
import java.lang.Math;
/**
 * 
 * Implement all of the methods in this class.
 * In this version, although all values are bytes, arrays of ints are used instead of bytes.
 *
 * Why is this slightly easier?  The version using byte values may involve casting that you'll need to think through.
 * Also, the mixColumns step will be easier to detect whether you need to modulo reduce the polynomials with int
 * values (i.e., if a result requires more than 8 bits, modulo reduce with the appropriate XOR).
 *
 * @author Sanghoon Park and Greg Walsh
 */

public class AESOperationsSlightlyEasier 
{
	
	static int[] Sbox = {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76, 
	 					 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
	 					 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
	 					 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
	 					 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
	 					 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
	 					 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
	 					 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
	 					 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
	 					 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
	 					 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
	 					 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
	 					 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
	 					 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
	 					 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
	 					 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}; 
	
	/**
	* Computes the byte substitution step of an AES round.
	*
	* @param state The current state matrix. This method modifies this matrix by replacing each byte
	* with the corresponding output from the AES s-box.
	*/
	public static void byteSubstitution(int[][] state) 
	{
		// HINT 1:  I'd recommend simply hardcoding the S-box as a 1 dimensional array of bytes as a field within this class.
		// If you use this hint, then you'd convert the 2D S-box to a simple array, by starting with the first row, followed by the 2nd, etc.
		// Just Google AES S-box to find it, such as on the wikipedia page.  The input byte is then simply used as an index into this array.
		//
		// HINT 2: You'll probably find the S-box elements specified in hexadecimal.  You don't need to convert from hexadecimal to decimal
		// in using HINT 1.  If you weren't already aware, in Java you can specify integer values with hex, by using 0x.  For example, 0x2a
		// is how you'd specify the decimal value 42 in hexadecimal.
		//
		// HINT 3: Reminder, when you initialize an array in Java, you can do so by specifying a list of elements within { }.
		// For example, consider the following:
		//   byte[] someBytes = { 0x2a, 0x33, 0x41 };
		// In this example, someBytes is initialized to an array of length 3, with the specific values specified in hexadecimal.
		// If you follow HINT 1, you'd have something like this as a field outside this method for the entire S-box.
		//There are prints statements that will print the new matrix after doing the byte substitution
		for(int i = 0; i < state.length; i++)
		{
			for(int j = 0; j < state.length; j++)
			{
				state[i][j] = Sbox[state[i][j]];
			}
		}
		
	}
	
	
	/**
	* Computes the shift rows step of an AES round.
	*
	* @param state The current state matrix. This method modifies this matrix to contain the result of the shift rows step.
	*/
	public static void shiftRows(int[][] state) 
	{
		//This method temp matrix with 2 store shifted values from state
		int[][] temp = new int[state.length][state.length];
		
		for(int i = 0; i < state.length; i++)
		{		
			for(int j = 0; j < state.length; j++)
			{
				temp[i][j] = state[i][(j + i) % state.length];
			}
		}
		
		//sets all values from temp back into state.
		for(int x = 0; x < temp.length; x++)
		{
			for(int y = 0; y < temp.length; y++)
			{
				state[x][y] = temp[x][y];
			}
		}
	}
	
	
	/**
	* Computes the mix columns step of an AES round.
	*
	* @param state The current state matrix. This method modifies this matrix to contain the result of the mix columns step.
	*/
	public static void mixColumns(int[][] state) 
	{
		// HINT 1: See the notes or textbook for the matrix that you need to multiply by state.
		
		// HINT 2: This step is similar to a matrix multiplication, because, well it is.  However, the
		// byte values actually represent polynomials with coefficients computed mod 2, and with the result mod
		// AES's prime polynomial.  You might start by implementing a normal matrix multiplication (you'll likely
		// need a temporary 2D array for the result, but before this method finishes make sure you copy the result back into state).
		// After you implement a normal matrix multiplication, you'll need to change it according to hints 3, 4, and 5.
		
		// HINT 3: Since the elements you're adding are not actually integer values, but rather the integer values
		// are encoding the coefficients of a polynomial (mod 2), then addition should actually be done with en XOR.
		// Java's XOR operator is ^ so if you followed hint 2, then wherever you are adding, you actually want to XOR.
		
		// HINT 4: For the same reason as in HINT 2, multiplication is not actually a simple multiplication.
		//  HINT 4a: The one matrix has nothing but 1, 2, and 3 values.  If you multiply any value by 1, the result is the value (this is
		//        no different than if the numbers were integers).
		//  HINT 4b: If you need to multiply by 2, well the 2 is actually the polynomial: X.  You can multiply by 2 in one of
		//        two ways.  You can either left shift 1 position.  For example, if you what to compute state[i][j] left shifted
		//        one position, you would do this: state[i][j] << 1
		//        You can actually also just multiply by 2 (multiplying by 2 is equivalent to shifting the bit values one place to the left).
		//  HINT 4c: Wherever you are multiplying by 3, you definitely cannot actually multiply by 3.  You will get the wrong answer.
		//        The value 3 represents the polynomial: X + 1 (since 3 in binary is 11).  If you have to compute 3 * state[i][j], then
		//        this really means (X + 1) * state[i][j], which is equivalent to X * state[i][j] + 1 * state[i][j], equivalent to
		//        X * state[i][j] + state[i][j].  But from hint 4b, the multiplication by X is a left shift, and from hint 3, the addition 
		//        should be an XOR.
		
		// HINT 5: If any results are polynomials with degree 8 or higher, then you need to modulo reduce by AES's prime polynomial.
		//       In general computing f(x) mod p(x) may involve multiple rounds of shifting and XOR.  However, the multiplications you
		//       did earlier, represented by 2 and 3 (i.e., X and X + 1) will produce polynomials with degree no greater than 8.
		//       Why?  Well, since each element of the state is a byte, then with 8 bits, each position representing a power of X, the
		//       left most bit is the X to the power 7 term.  If that bit is a 1 and if you multiply by X or by X + 1, then you will end
		//       up with an X to the power 8 term (but the exponent won't be any higher than that).  So, at most a single XOR will be
		//       needed (and no shifting).  So, whenever you shift left, you'll need to first detect whether the left most bit is a 1, and
		//       if so, after shifting, you'll need to XOR with the value that represents AES's prime polynomial.
		//       CAUTION: You'll need to detect whether or not the left shift will produce an X to the 8 term before left-shifting.
		//       Since the state matrix has byte values, the left most bit is lost by the left shift (you only have 8 bits), 
		//       so you need to know what it was before the left shift.
		
		int mixColumnMatrix[][] = { {2, 3, 1, 1}, {1, 2, 3, 1}, {1, 1, 2, 3}, {3, 1, 1, 2} };
		int tempMatrix[][] = new int[4][4];
		
		for(int i = 0; i < state.length; i++)
		{	
			for(int j = 0; j < state.length; j++)
			{
				int tempInt = 0;
				tempMatrix[i][j] = 0;
				
				for(int k = 0; k < state.length; k++)
				{			
					//This checks if the value in mixColumnMatrix is 2. If so, it left shifts state and sets it to tempInt
					if(mixColumnMatrix[i][k] == 2)
					{
						tempInt = state[k][j] << 1;
					}
					//This checks if the value in mixColumnMatrix is 3. If so, it left shifts state then XORs it with state and sets it to tempInt
					else if(mixColumnMatrix[i][k] == 3)
					{
						tempInt = (state[k][j] << 1) ^ state[k][j];
					}
					//This checks if the value in mixColumnMatrix is 1. If so, it sets tempInt to state
					else
					{
						tempInt = state[k][j];
					}
					//This checks if tempInt > 256 (or 100 in hex), if so it XORs with 283 (or 11B in hex).
					if(tempInt >= 0x100)
					{
						tempInt = tempInt ^ 0x11b;
					}
					//This XORs what is in the tempMatrix with the current tempInt
					tempMatrix[i][j] = tempMatrix[i][j] ^ tempInt;
				}
				//Last check to see if tempMatrix > 256 (or 100 in hex). If so it XORs with 283 (or 11B in hex).
				if(tempMatrix[i][j] >= 0x100)
				{
					tempMatrix[i][j] = tempMatrix[i][j] ^ 0x11b;
				}
			}
		}
		
		for(int i = 0; i < state.length; i++)
		{
			for(int j = 0; j < state.length; j++)
			{
				state[i][j] = tempMatrix[i][j];
			}
		}
		
	}
	
	
	/**
	* Returns a 2D array from the key.
	* @param key The key
	* @return 2D array with the key, 4 rows and 4 columns
	*/
	public static int[][] generateKeyMatrixFromKey(int[] key) 
	{
		// HINT: the bytes of the key or filled into the key matrix down the columns (not across rows).
		
		int[][] keyMatrix = new int[4][4];
		int keyIndex = 0;
		
		//Fill 2d keyMatrix down columns eg. (0,0), (1,0), (2,0), etc.
		for(int i = 0; i < keyMatrix.length; i++)
		{
			for(int j = 0; j < keyMatrix.length; j++)
			{
				keyMatrix[j][i] = key[keyIndex];
				keyIndex++;
			}
		}
		return keyMatrix;
	}
	
	
	/**
	* Computes the expanded key matrix for AES.
	* @param keyMatrix The first 4 columns, i.e., the initial key matrix.
	* @return Returns a new matrix that corresponds to the expanded key matrix of AES.
	*/
	public static int[][] keyExpansion(int[][] keyMatrix) 
	{
		// HINT: The expanded key matrix has 4 rows and 44 columns, so start by constructing a 2D array for the result that
		// is of the correct dimensions.  Then initialize the first 4 columns with the keyMatrix.  Then iterate over the columns
		// computing the rest per the AES key expansion rules.  Finally return the new 2D array.
		
		int[][] keyExpansionMatrix = new int[4][44];
		int[] RArray = new int[10];
		
		//Fills first 4 rows with key matrix
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				keyExpansionMatrix[i][j] = keyMatrix[i][j];	
			}
		}
		
		for(int i = 4; i < 44; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(i % 4 == 0)
				{
					int tempInt = keyExpansionMatrix[0][i - 1];
					//This rotates the column up 1
					if(j < 3)
					{
						keyExpansionMatrix[j][i] = keyExpansionMatrix[j + 1][i - 1];
					}
					else
					{
						keyExpansionMatrix[j][i] = tempInt;
					}
					//Sbox substitution
					keyExpansionMatrix[j][i] = Sbox[keyExpansionMatrix[j][i]];
					//Checks if r(i) (in the form of an array)
					if(j == 0)
					{
						//if greater than 283(11b in hex), left shift
						RArray[(i - 4) / 4] =  (int) (Math.pow(0x02, ((i - 4) / 4)));
						if(RArray[(i - 4) / 4] > 0x11b)
						{
							RArray[(i - 4) / 4] = RArray[((i - 4) / 4) - 1] << 1;
						}
						//if greater than or equal to 256(100 in hex) XOR with 283(11b in hex).
						if(RArray[(i - 4) / 4] >= 0x100)
						{
							RArray[(i - 4) / 4] = RArray[((i - 4) / 4)] ^ 0x11b;
						}
						keyExpansionMatrix[j][i] = keyExpansionMatrix[j][i] ^ RArray[(i - 4) / 4];
					}
					//Calculates wi divisible by 4
					keyExpansionMatrix[j][i] = keyExpansionMatrix[j][i - 4] ^ keyExpansionMatrix[j][i];
				}
				//Calculates wi not divisible by 4
				else
				{
					keyExpansionMatrix[j][i] = keyExpansionMatrix[j][i-4] ^ keyExpansionMatrix[j][i-1];
				}
			}
		}
		//This print statement is for printing the results of key expansion with the w column above
		/*
		for(int i = 0; i < 44; i++)
		{
			System.out.print("w" + i + "\t");
		}
		System.out.print("\n");
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 44; j++)
			{
				System.out.print(keyExpansionMatrix[i][j] + "\t");
			}
			System.out.print("\n");
		}
		*/
		return keyExpansionMatrix;		
	}
}