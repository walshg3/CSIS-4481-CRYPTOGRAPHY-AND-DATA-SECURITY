package aes;
/**
* Start by implementing the AES related methods in any of the 3 provided Java files.  And then return here to test.
* @author Sanghoon Park and Greg Walsh
*/

public class TestAESOps {
	
	public static void main(String[] args) {
		
		// Write some code to test your various methods.
		// You might consider using homework problems as a source of test cases since you can compare
		// output to what you worked through by hand.
		
		// In Java, if you want to output an integer value in hexadecimal, you can use printf as follows:
		// int value = SOME INTEGER VALUE;
		// System.out.printf("%02x", value);
		// The % starts specifying formatting.  The 02 means use 2 places and pad 1 digit numbers with a leading 0.
		// The x means use hexadecimal.  printf doesn't put a new line character.  If you want to start a new line after,
		// System.out.printf("%02x\n", value);  
		// If you want to output multiple values with a single printf (assume they're called value1, value2, etc)
		// then in the String that is the first parameter, you'd have formatting requirements for each as follows:
		// System.out.printf("%02x %02x %02x %02x\n", value1, value2, value3, value4);
		
		//This example is the one from the textbook on page 177
		//KEY: type down in column to column eg. (0,0) , (1,0), (2,0), etc.
		int[] key = {0x0f, 0x15, 0x71, 0xc9, 0x47, 0xd9, 0xe8, 0x59, 0x0c, 0xb7, 0xad, 0xd6, 0xaf, 0x7f, 0x67, 0x98};
		
		//PLAINTEXT: type row to row eg. (0,0), (0,1), (0,2), etc.
		int[][] plaintext = { {0x01, 0x89, 0xfe, 0x76}, {0x23, 0xab, 0xdc, 0x54}, {0x45, 0xcd, 0xba, 0x32}, {0x67, 0xef, 0x98, 0x10} };
		
		//This takes an array key and creates a matrix filling column by column.
		int[][] keyMatrix = AESOperationsSlightlyEasier.generateKeyMatrixFromKey(key);
		
		//This initializes an empty 4x4 matrix 
		int[][] ciphertext = new int[4][4];
		
		//This creates the first add round key
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				ciphertext[i][j] = plaintext[i][j] ^ keyMatrix[i][j];
			}
		}
		
		//This makes the key expansion matrix w4 - w43
		int[][] keyExpansion = AESOperationsSlightlyEasier.keyExpansion(keyMatrix);
		
		//This does the first 10 rounds (1 - 9 rounds) of byte substitution, shiftrows, mixColumns, and add round key
		for(int i = 1; i < 10; i++)
		{
			AESOperationsSlightlyEasier.byteSubstitution(ciphertext);
			AESOperationsSlightlyEasier.shiftRows(ciphertext);
			AESOperationsSlightlyEasier.mixColumns(ciphertext);
			
			for(int j = 0; j < 4; j++)
			{
				for(int k = 0; k < 4; k++)
				{
					ciphertext[k][j] = keyExpansion[k][4 * i + j] ^ ciphertext[k][j];
				}
			}
		}
		
		//This is the last round, where it does byte substituion, shiftrows, and the last add round key
		AESOperationsSlightlyEasier.byteSubstitution(ciphertext);
		AESOperationsSlightlyEasier.shiftRows(ciphertext);
		
		for(int j = 0; j < 4; j++)
		{
			for(int k = 0; k < 4; k++)
			{
				ciphertext[k][j] = keyExpansion[k][40 + j] ^ ciphertext[k][j];
			}
		}
		//This prints out the ciphertext in the form of a matrix (in decimal)
		System.out.println("AES Final Matrix: ");
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				System.out.print(ciphertext[i][j] + "\t");
			}
			System.out.print("\n");
		}
	}
}