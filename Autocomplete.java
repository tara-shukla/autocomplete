import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    private int firstIndex; // index of the first prefix match
    private int lastIndex; // index of the last prefix match
    private Term[] terms; // array to contain search terms

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("Terms cannot be null.");
        }

        int n = terms.length;

        // create defensive copy of terms[]
        this.terms = new Term[n];
        for (int i = 0; i < n; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException("Null entry");
            }
            this.terms[i] = terms[i];
        }

        // Sort terms by lexicographic order
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix can't be null.");
        }

        // create an array to contain all the matching terms found
        Term[] matchingTerms = new Term[numberOfMatches(prefix)];

        if (firstIndex != -1) {
            // create array to contain matching terms
            int counter = 0;
            for (int i = firstIndex; i <= lastIndex; i++) {
                matchingTerms[counter] = terms[i];
                counter++;
            }

            // sort the matching terms by descending weight order
            Comparator<Term> weightComp = Term.byReverseWeightOrder();
            Arrays.sort(matchingTerms, weightComp);

        }

        return matchingTerms; // array returns empty if no matches are found
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix can't be null.");
        }
        int prefixLength = prefix.length();

        // create new term with query = prefix, weight = 0
        Term prefixTerm = new Term(prefix, 0);

        // create comparator that sorts by prefix order and sort array
        Comparator<Term> prefixComp = Term.byPrefixOrder(prefixLength);

        // find first and last index of element with that prefix
        firstIndex = BinarySearchDeluxe.firstIndexOf(terms, prefixTerm, prefixComp);
        lastIndex = BinarySearchDeluxe.lastIndexOf(terms, prefixTerm, prefixComp);

        // total number of terms containing the given prefix
        int totalMatch;

        if (firstIndex == -1) {
            totalMatch = 0; // set to 0 if no matches found
        }
        else {
            totalMatch = (lastIndex - firstIndex) + 1;
        }

        return totalMatch;
    }


    // unit testing adapted from sample client in COS 226 Autocomplete Assignment
    public static void main(String[] args) {

        // read terms from file given in command line
        String filename = args[0];
        In in = new In(filename);

        // read length of file (# of terms) from file
        int n = in.readInt();

        // create and fill terms array
        Term[] test = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();        // read the next query
            test[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(test);

        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }

    }
}
