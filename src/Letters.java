import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
class Letters {
    private static Hashtable<String, Image> letterPictures = new Hashtable<>();
    private static Hashtable<String, Image> smallerLetterPictures = new Hashtable<>();
    private static Hashtable<String,String> words = new Hashtable();
    private static ArrayList<String> scrambleCombo = new ArrayList<String>();
    public Letters() throws IOException{
        //System.out.println("no");
        try{
            String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
            for (int i = 0; i<26; i++){
                letterPictures.put(letters[i], ImageIO.read(new File("Pictures/" + letters[i] + ".png")));
                smallerLetterPictures.put(letters[i], ImageIO.read(new File("Pictures/smallerLetters/s" + letters[i] + ".png")));
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        loadDictionary();

    }

    public Image getLetterImage(String letter){
        return letterPictures.get(letter);
    }
    public ArrayList<String> randomXletters(int num){
        ArrayList<String> chosenLetters = new ArrayList<String>();
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        boolean wordsPresent = false;
        String scramble = "";
        while(!wordsPresent) {
            String [] keys = words.keySet().toArray(new String[words.size()]);
            String key = keys[randint(0,keys.length)];
            if(scramble.length() + key.length() < num){
                scramble = scramble + key;
            }
            if (scramble.length() >num-4 && scramble.length() <num){
                for (int i = 0; i< num-scramble.length(); i++){
                    scramble = scramble + letters[randint(0,25)];
                }
            }
            if(scramble.length() == num){
                wordsPresent = true;
            }
        }
        for(int i = 0; i<scramble.length();i++){
            chosenLetters.add(String.valueOf(scramble.charAt(i)));
        }
        Collections.shuffle(chosenLetters);
        return chosenLetters;
    }
    public static Image getImage(String size,String a){
        if (size.equals("SMALL")) {
            return smallerLetterPictures.get(a);
        }
        if (size.equals("NORMAL")){
            return letterPictures.get(a);
        }
        return null;
    }

    public static int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
    public static void permutations(String word){
        permutations("",word);
    }
    public static void permutations(String wordLeft, String word){
        if (word.equals("")){
            scrambleCombo.add(word);
        }
        else{
            for (int i = 0; i< word.length(); i++){
                String next = word.substring(0,i) + word.substring(i+1);
                permutations(wordLeft+word.charAt(i),next);
            }
        }
    }
    public static void loadDictionary() throws IOException{
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/english3.txt"))); //got this text
        //file from http://www.gwicks.net/dictionaries.htm
        while (inFile.hasNextLine()){
            String currentWord = inFile.nextLine().toLowerCase();
            words.put(currentWord, currentWord);
        }
    }
    public boolean checkWord(String input){
        if (words.contains(input)){
            return true;
        }
        else{
            return false;
        }
    }
}
