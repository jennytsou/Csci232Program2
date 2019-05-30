package Project02;

import java.io.*;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
 * CSCI232: Program 2
 * Yueh-Chen Tsou
 * 5/25/2019
 * The program read a message from input.txt and use Huffman coding compression
 * algorithm which can reduce the amount of space. 
 * .Accept a text message from an input file (input.txt).
 * .Construct a frequency table for the message.
 * .Create a Huffman tree for this message.
 * .Create a code table.
 * .Encode the message into binary.
 * .Decode the message from binary back to the message and write into "output.txt". 
 */

public class HuffmanDemo {
	
	public static void main(String[] args) {
		
		String myString="";
		try {
			Scanner fileInput = new Scanner(new File("input.txt"));
		
			while (fileInput.hasNextLine()) {	
					myString = fileInput.nextLine();		
				}
			fileInput.close();
		} catch (FileNotFoundException exc) {
			System.out.println("There was a problem opening the input file");
		}
		System.out.println("Original message: " + myString);
		
		HuffmanTree tree = new HuffmanTree();
		/* 
		 * construct a frequency table for the message
		 * and create a Huffman tree for this message
		 */
		tree.buildTree(myString);
		/*
		 * Create a code table
		 */
		Map<Character, String> huffmanCode = tree.encode();
		/*
		 * Encode the message into binary
		 */
		StringBuilder sb = tree.encodeString(huffmanCode, myString);
		System.out.println("\nEncoded message: " + myString);
		/*
		 * Decode the message from binary back to the message
		 */
		tree.decode(sb);
		/*
		 * print the code table, binary of encode the message, 
		 * and the message of decode to the console
		 */
		tree.printCode(huffmanCode, sb);
		/*
		 * write the message of decode into "output.txt"
		 */
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
			writer.write(tree.decodeStr);
			writer.close();
		} catch (IOException exc) {
			System.out.println("There was a problem opening the input file");
		}
	}

}	
		