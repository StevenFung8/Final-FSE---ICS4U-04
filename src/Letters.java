import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Letters.java
//Dylan Tan and Steven Fung
//This is the class that manages all the letters in the grid
class Letters {
    private static Hashtable<String, Image> letterPictures = new Hashtable<>(); //pictures of all the levels
    private static Hashtable<String, Image> smallerLetterPictures = new Hashtable<>(); //smaller pictures for the selected word
    private static Hashtable<String,String> words = new Hashtable(); //make a dictionary
    private static ArrayList<String> scrambleCombo = new ArrayList<String>(); //all the permutations of the word
    public Letters() throws IOException{
        try{ //first load all the pictures for every letter
            String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
            for (int i = 0; i<26; i++){
                letterPictures.put(letters[i], ImageIO.read(new File("Pictures/Big Letters/" + letters[i] + ".png")));
                smallerLetterPictures.put(letters[i], ImageIO.read(new File("Pictures/smallerLetters/s" + letters[i] + ".png")));
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        loadDictionary(); //fill up the dictionary

    }

    public ArrayList<String> randomXletters(int num){ //generates a x amount of letters
        ArrayList<String> chosenLetters = new ArrayList<String>();
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        boolean wordsPresent = false;
        String scramble = "";
        while(!wordsPresent) {
            String [] keys = words.keySet().toArray(new String[words.size()]); //creates a random hashCode, therefore grabbing a random word
            String key = keys[randint(0,keys.length)];
            //we want to generate letters so that there hopefull be words in there, instead of checking for words every time
            if(scramble.length() + key.length() < num){  //if the generated word isn't too big
                scramble = scramble + key;
            }
            if (scramble.length() >num-4 && scramble.length() <num){ //if there is room fill up the leftover slots with random letters
                for (int i = 0; i< num-scramble.length(); i++){
                    scramble = scramble + letters[randint(0,25)];
                }
            }
            if(scramble.length() == num){ //when you finally generate the letters, stop making them
                wordsPresent = true;
            }
        }
        for(int i = 0; i<scramble.length();i++){ //make these letters the ones you want to add to the grid
            chosenLetters.add(String.valueOf(scramble.charAt(i)));
        }
        Collections.shuffle(chosenLetters);
        return chosenLetters;
    }
    public static Image getImage(String size,String a){ //returns the big or small letter pictures
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
    public static void loadDictionary() throws IOException{ //reads a dictionary text file and puts them all in a hashtable
        Scanner inFile = new Scanner(new BufferedReader(new FileReader("Text Files/english3.txt"))); //got this text
        //file from http://www.gwicks.net/dictionaries.htm
        while (inFile.hasNextLine()){
            String currentWord = inFile.nextLine().toLowerCase();
            words.put(currentWord, currentWord);
        }
        inFile.close();
    }
    public boolean checkWord(String input){ //checks to see if the word is in the dictionary
        if (words.contains(input)){
            return true;
        }
        else{
            return false;
        }
    }
}
