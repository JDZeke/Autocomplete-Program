import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutocompleteProvider {

	// This tree stores the data used to suggest words based on string fragments
	private CharTreeNode wordFrequencies;

	// This class is used to create a tree where each node represents a letter
	// of the alphabet (up to 26 children per node). Each path from the root of
	// the tree to a node represents a list of characters (i.e. a string). Thus,
	// every node in the tree represents a string and stores the frequency of
	// how often that string is used in the training passages. References to
	// each node's children are stored in an sorted ArrayList so that it can be
	// binary searched when traversing down the tree for better time efficiency.
	private class CharTreeNode {
		// the character this node represents
		char c;
		// the number of times this string has appeared in the training passages
		int frequency;
		// references to this node's children
		ArrayList<CharTreeNode> children;

		private CharTreeNode(char c) {
			this.c = c;
			this.frequency = 0;
			// the size is initialized to 1 to save space because as the tree
			// grows, the vast majority of combinations of strings aren't real
			// words, and consequently, most nodes will not have many children
			this.children = new ArrayList<>(1);
		}
	}

	// Class constructor
	public AutocompleteProvider() {
		// 'R' is for root and no other node should have a capital letter
		wordFrequencies = new CharTreeNode('R');
	}

	// returns list of candidates ordered by confidence
	public List<Candidate> getWords(String fragment) {
		List<Candidate> temp = getWordsHelper(this.wordFrequencies, fragment, "");
		Collections.sort(temp, Collections.reverseOrder());
		return temp;
	}

	// helper method for getting the word candidates based off of a string
	// fragment. This method recursively calls itself until it has traversed
	// the tree up to the node that represents the string fragment. Then it
	// does a depth first search to find all existing branches below the
	// node. For each branch with a frequency > 0, it is added to an ArrayList
	// in the recursive step.
	private List<Candidate> getWordsHelper(CharTreeNode node, String fragment, String word) {
		// Part 1 of the algorithm (traverse down to the word in the tree)
		if (fragment.length() > 0) {
			char c = fragment.charAt(0);
			if (fragment.length() == 1)
				return getWordsHelper(getChild(node, c), fragment.substring(1), word);
			return getWordsHelper(getChild(node, c), fragment.substring(1), word + c);
		}

		// Part 2 of the algorithm (recursively traverse down and add all
		// strings with frequency > 0 to an ArrayList)

		char c = node.c;
		ArrayList<Candidate> candidates = new ArrayList<>();

		if (node.frequency > 0)
			candidates.add(new Candidate(word + c, node.frequency));

		for (CharTreeNode n : node.children)
			candidates.addAll(getWordsHelper(n, "", word + c));

		return candidates;
	}

	// trains the algorithm with the provided passage
	public void train(String passage) {
		// convert the passage into words separated by one or more whitespace character
		// any non-alphabet characters are removed and everything is made lower case
		String[] words = passage.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

		// train each word
		for (String word : words)
			trainWord(this.wordFrequencies, word);
	}

	// trains the algorithm with the provided word
	private void trainWord(CharTreeNode node, String w) {
		if (w.length() > 1)
			trainWord(getChild(node, w.charAt(0)), w.substring(1));
		else
			getChild(node, w.charAt(0)).frequency++;
	}

	// finds the CharTreeNode with character c in the children of the parameter
	// node. If no child exists with character c, one will be created and returned
	// binary search is used to find the node with character c
	private CharTreeNode getChild(CharTreeNode node, char c) {
		// initialize the left, right, and middle indices to be used during search
		int left = 0, right = node.children.size() - 1, mid;

		while (left <= right) {
			mid = ((right - left) / 2) + left;

			// if should be lie to the left of mid
			if (c < node.children.get(mid).c)
				right = mid - 1;
			// if should be lie to the right of mid
			else if (c > node.children.get(mid).c)
				left = mid + 1;
			// if c equals the character at index mid
			else
				return node.children.get(mid);
		}

		// if this spot is reached, the node with char c must not exist
		// in this case, we must create it and insert it in the right order
		CharTreeNode newNode = new CharTreeNode(c);

		// if newNode needs to go at the end of the ArrayList, we can just add it
		// if not, we can add it to the index left - 1 and the ArrayList add method
		// handles shifting all subsequent elements to the right
		if (left >= node.children.size())
			node.children.add(newNode);
		else
			node.children.add(left, newNode);

		return newNode;
	}
}
