package Project02;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

	class Node {
//	static class Node implements Comparable{
		Node left, right;
		char key;
		int frequency;
	 
		Node(char k, int v )		
		{
			this.key = k;
			this.frequency = v;
			this.left = null;
			this.right = null;
		}

		Node(char k, int v, Node left, Node right )		
		{
			this.key = k;
			this.frequency = v;
			this.left = left;
			this.right = right;
		}

		public String toString() {
			return (key + ":" + frequency) ;
		}
	
	}

	
/* Execution includes insert, delete, search, printing function  */
	
	private Node root;
	String decodeStr = "";
	 
    public HuffmanTree()		// constructor
    {
        root = null;
    }
   
  /*
   * First construct a frequency table for the message,
   * then create a Huffman tree for this message
   */
    public void buildTree(String str) {
    	//Construct a frequency table for the  message
    	HashMap<Character, Integer> freqs = new HashMap<Character, Integer>();
    	char key;
    	for (int i=0; i<str.length(); i++) {
    		key = str.charAt(i);
    		if ( freqs.containsKey(key))
    			freqs.put(key, freqs.get(key)+1);	//key exists, increase the value 
    		else
    			freqs.put(key, 1);	// add key, value = 1
    	}
    	
    	/* print the frequency table */
    	System.out.println("\nFrequency table: ");
    	for(Character key1: freqs.keySet())
    		System.out.println(key1 + " " + freqs.get(key1));
    	
    	/* minimum heap (priority queue) */ 
       	PriorityQueue<Node> queues = new PriorityQueue<>((l, r) -> l.frequency - r.frequency);
        for(Character key2: freqs.keySet())				// 1. Insert node into last position 
       		queues.add(new Node(key2, freqs.get(key2)));// 2. Bubble upward
 
        /* Create a Huffman Tree for the message */
       	while (queues.size() > 1) {
       		Node left = queues.poll();
       		Node right = queues.poll();
       		int sum = left.frequency + right.frequency;
       		queues.add(new Node('#', sum, left, right));
       	}
       	root = queues.peek();
    }
    /*
     * Create a code table
     */
    public Map<Character, String> encode() {
    	
    	HashMap<Character, String> huffmanCode = new HashMap<Character, String>();
    	Map<Character, String> tCode = encode(root, "", huffmanCode);
    	return tCode;
    }
    
    public HashMap<Character, String> encode(Node node, String strCode, HashMap<Character, String> huffmanCode) {
    	if (node == null)
    		return huffmanCode;
    	
    	if(node.left == null && node.right == null)
    		huffmanCode.put(node.key, strCode);
    	encode(node.left, strCode + "0", huffmanCode);
    	encode(node.right, strCode + "1", huffmanCode);
    	return huffmanCode;
    }
    /*
     * Decode the message from binary
     */
    public void decode(StringBuilder sb) {
    	int i=-1;
    	while( i<sb.length() - 1) {
    		i = decode(root, sb, i);
    	}
    }
    
    public int decode(Node node, StringBuilder sb, int index) {
    	if (node == null)
    		return index;
    	if (node.left == null && node.right == null) {
    		decodeStr = decodeStr + node.key;
    		return index;
    	}
    	index ++;
    	if (sb.charAt(index) == '0')	// go left if the code is '0'
    		index = decode(node.left, sb, index);
    	else							// go right if the code is '1'
    		index = decode(node.right, sb, index);
    	return index;
    	
    	
    }
  
    /* Encode the message into binary */
    public StringBuilder encodeString(Map<Character, String> huffmanCode, String str) {
    	StringBuilder sb = new StringBuilder();
    	for (int i=0; i<str.length(); i++) {
    		sb.append(huffmanCode.get(str.charAt(i)));
    	}
    	return sb;
    }
    /*
	 * print the code table, binary of encode the message, 
	 * and the message of decode to the console
	 */
    public void printCode(Map<Character, String> huffmanCode, StringBuilder sb) {
    	
    	System.out.println("\n\nHuffman Codes: ");
    	for (Map.Entry<Character, String> entry : huffmanCode.entrySet())
    		System.out.println(entry.getKey() + " " + entry.getValue());
    	
    	System.out.println("\nthe code of encode the message into binary: ");
    	System.out.print(sb + "\n");
    	System.out.println("\nDecode message: " + decodeStr);
    }
}
