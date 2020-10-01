
// The Candidate class represents a candidate for the AutocompleteProvider word
// suggestion algorithm. Each instance contains a word and a confidence level.
public class Candidate implements Comparable<Candidate> {
	
	private String word;
	private int confidence;
	
	// the constructor takes in a word and a confidence level
	public Candidate(String word, int confidence) {
		this.word = word;
		this.confidence = confidence;
	}
	
	public String getWord() {
		return this.word;
	}
	
    public Integer getConfidence() {
    	return this.confidence;
    }
    
    @Override
    public String toString() {
    	return "\"" + this.word + "\" (" + this.confidence + ")";
    }

	@Override
	public int compareTo(Candidate o) {
		if (this.confidence < o.confidence)
			return -1;
		if (this.confidence > o.confidence)
			return 1;
		return 0;
	}
}
