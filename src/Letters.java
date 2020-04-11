import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
class Letters {
    private static Hashtable<String, Image> letterPictures = new Hashtable<>();
    private static Hashtable<String,String> words = new Hashtable();
    private static ArrayList<String> scrambleCombo = new ArrayList<String>();
    public Letters() throws IOException{
        //System.out.println("no");
        try{
            String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
            for (int i = 0; i<26; i++){
                letterPictures.put(letters[i], ImageIO.read(new File("Pictures/" + letters[i] + ".png")));
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
    public ArrayList<String> random16letters(){
        ArrayList<String> chosenLetters = new ArrayList<String>();
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        boolean wordsPresent = false;
        String scramble = "";
        while(!wordsPresent) {
            String [] keys = words.keySet().toArray(new String[words.size()]);
            String key = keys[randint(0,keys.length)];
            if(scramble.length() + key.length() < 16){
                scramble = scramble + key;
            }
            if (scramble.length() >11 && scramble.length() <16){
                for (int i = 0; i< 16-scramble.length(); i++){
                    scramble = scramble + letters[randint(0,25)];
                }
            }
            if(scramble.length() == 16){
                wordsPresent = true;
            }
        }
        for(int i = 0; i<scramble.length();i++){
            chosenLetters.add(String.valueOf(scramble.charAt(i)));
        }
        Collections.shuffle(chosenLetters);
        return chosenLetters;
    }
    public static Image getImage(String a){
        return letterPictures.get(a);
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
}
