public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> convertedWord = new LinkedListDeque<>();
        for(int i = 0; i < word.length(); i++) {
            convertedWord.addLast(word.charAt(i));
        }
        return convertedWord;
    }
    public boolean isPalindrome(String word) {
        if(word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            return reverseString(word).equals(word);
        }
    }

    //reverse the string

    private static String reverseString(String s) {
        if(s.length() == 1) {
            return s;
        } else {
            String reverse = reverseString(s.substring(1)) + s.charAt(0);
            return reverse;
        }

    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        boolean equals = false;
        if(word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            for(int i = 0, j = word.length()-1; i < word.length()/2; i++,j--) {
                if(!cc.equalChars(word.charAt(i), word.charAt(j))) {
                    equals = false;
                } else {
                    equals = true;
                }
            }
        }
        return equals;
    }
}