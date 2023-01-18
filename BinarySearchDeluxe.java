import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("Null argument");
        }
        // range of search is a[low] to a[high]
        int lo = 0;
        int hi = a.length - 1;
        int mid;

        // result default -1
        int index = -1;

        // create reference equal key

        // adapted from elementary sort slide 39: binary search
        while (lo <= hi) {
            // set mid = midpoint between lo and hi
            mid = (lo + hi) >>> 1;

            int compared = comparator.compare(key, a[mid]);
            // if key found, continue search to its left (find earlier entries)
            // search will continue searching lower until it finds the lowest index key
            if (compared == 0) {
                index = mid;
                hi = mid - 1;
            }
            // search only right side if key > key at mid
            else if (compared > 0) {
                lo = mid + 1;
            }
            // search only left side if key < key at mid
            else {
                hi = mid - 1;
            }
        }

        // returns the first index of key (-1 if key not in array)
        return index;
    }


    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("Null argument");
        }
        // range of search is a[low] to a[high]
        int lo = 0;
        int hi = a.length - 1;
        int mid;

        // result default -1
        int index = -1;

        // adapted from elementary sort slide 39: binary search
        while (lo <= hi) {
            // set mid = midpoint between lo and hi
            mid = (lo + hi) >>> 1;

            // if key found, continue search to its right (find later entries)
            // search continues searching lower until it finds the highest index key
            int compared = comparator.compare(key, a[mid]);
            if (compared == 0) {
                index = mid;
                lo = mid + 1;
            }
            // search only right side if key > key at mid
            else if (compared > 0) {
                lo = mid + 1;
            }
            // search only left side if key<key at mid
            else {
                hi = mid - 1;
            }

        }

        // returns the last index of key (-1 if key not in array)
        return index;

    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] a = { "F", "F", "K", "N", "T" };
        String tester = "F";

        // adapted from COS 226 Autocomplete Assignment BinarySearchDeluxe FAQ
        int firstIndex =
                BinarySearchDeluxe.firstIndexOf(a, "F", String.CASE_INSENSITIVE_ORDER);
        StdOut.println("index of " + tester + "=" + firstIndex); // 2

        int lastIndex =
                BinarySearchDeluxe.lastIndexOf(a, "F", String.CASE_INSENSITIVE_ORDER);
        StdOut.println("index of " + tester + "=" + lastIndex); // 2


        // test using terms
        Term test1 = new Term("Dog", 4);
        Term test2 = new Term("Cat", 9);
        Term test3 = new Term("DogCatcher", 2000);

        Term[] terms = new Term[] { test1, test2, test3 };
        Term prefix = new Term("Do", 4);

        Comparator<Term> prefixComp = Term.byPrefixOrder(2);
        Arrays.sort(terms, prefixComp);

        int first = BinarySearchDeluxe.firstIndexOf(terms, prefix, prefixComp);
        StdOut.println(first); // 1
        int last = BinarySearchDeluxe.lastIndexOf(terms, prefix, prefixComp);
        StdOut.println(last); // 2

    }
}
