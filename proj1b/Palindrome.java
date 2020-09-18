public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> temp = new ArrayDeque<>();
        for (int index = 0; index < word.length(); index += 1) {
            temp.addLast(word.charAt(index));
        }
        return temp;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        } else if (word.length() == 0 || word.length() == 1) {
            return true;
        } else if (word.charAt(0) == word.charAt(word.length() - 1)){
            return isPalindrome(word.substring(1, word.length() - 1));
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        } else if (word.length() == 0 || word.length() == 1) {
            return true;
        } else if (cc.equalChars(word.charAt(0), word.charAt(word.length() - 1))){
            return isPalindrome(word.substring(1, word.length() - 1));
        }
        return false;
    }
}
