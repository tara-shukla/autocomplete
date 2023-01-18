import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query; // query of the term
    private long weight; // weight of the term

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new WeightComparator();
    }

    // compares substring prefixes of term1 and term2 queries
    private static class WeightComparator implements Comparator<Term> {

        public int compare(Term term1, Term term2) {
            // compare the weights and cast long to int
            int compared = (int) (term2.weight - term1.weight);
            return compared;
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        return new PrefixComparator(r);
    }

    // compares substring prefixes of term1 and term2 queries
    private static class PrefixComparator implements Comparator<Term> {
        private int r; // integer parameter for prefix length

        // initializes r (substring length) with argument
        private PrefixComparator(int r) {
            if (r < 0) {
                throw new IllegalArgumentException("Invalid r length");
            }
            this.r = r;
        }

        public int compare(Term term1, Term term2) {

            // creating vars for lengths of terms' queries
            int t1Length = term1.query.length();
            int t2Length = term2.query.length();

            // find min length between r and each queries length
            int minLength = Math.min(t1Length, t2Length);
            minLength = Math.min(minLength, r);

            for (int i = 0; i < minLength; i++) {
                if (term1.query.charAt(i) != term2.query.charAt(i)) {
                    return (int) (term1.query.charAt(i) - term2.query.charAt(i));
                }
            }

            if (r == minLength) return 0;
            return t1Length - t2Length;
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return (this.query.compareTo(that.query));
    }


    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (weight + "\t" + query);
    }

    // unit testing
    public static void main(String[] args) {
        // create terms
        Term test1 = new Term("ZA", 8);
        Term test2 = new Term("CAAAAAAT", 2);

        StdOut.print("lexicographic comparison: " + test1.compareTo(test2));
        StdOut.println();

        Term[] terms = new Term[] { test1, test2 };

        Arrays.sort(terms, Term.byReverseWeightOrder());
        StdOut.println("reverse weight order");
        for (Term a : terms) {
            StdOut.println(a);
        }


        Arrays.sort(terms, Term.byPrefixOrder(3));
        StdOut.println("prefix order:");
        for (Term a : terms) {
            StdOut.println(a);
        }
    }
}
