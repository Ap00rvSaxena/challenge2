import javax.management.ObjectName;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class challenge2 {
    static String fileSeperator = File.separator;
	
	//Main method intializes and runs all method to achieve Challenge2
    public static void main(String[] args){
        List<String> lines = getLines("loomings.txt");
        System.out.println("Total number of non-blank lines: " + lines.size()+"\n");
        generateFiles(lines);
        List<String[]> duplicate = new ArrayList<>();
        List<String> hashDigest = generateHashDigest(lines, duplicate);
        listFiles();
        printDuplicateContent(duplicate);
        cleanTheFile(lines, duplicate, "loomings-clean.txt");
    }
	
	//Generate looming-clean.txt file from loomings.txt, which contains all the non-blank and non repeating lines
    private static void cleanTheFile(List<String> lines, List<String[]> duplicate, String fileName) {
        try{
            Set<Integer> set = new HashSet<>();
            for (int i=0; i<duplicate.size(); i++){
                set.add(Integer.valueOf(duplicate.get(i)[0])-1);
            }
            File file = new File(fileName);
            if (file.exists()){
                file.delete();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            for (int i=0; i<lines.size(); i++){
                if (set.contains(i))
                    continue;
                writer.write(lines.get(i) + (i==lines.size()-1 ? "" : "\n"));
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//List all the files in the current directory and prints them out in the sort order fashion, low to high total size in bytes
    private static void listFiles() {
        try{
            PriorityQueue<String[]> pq = new PriorityQueue<>(new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    String fileName1 = o1[0];
                    String fileName2 = o2[0];
                    long file1Size = Long.valueOf(o1[1]);
                    long file2Size = Long.valueOf(o2[1]);

                    if (file1Size == file2Size){
                        return fileName1.compareTo(fileName2);
                    }
                    return (int)(file1Size-file2Size);
                }
            });
            File dir = new File(".");
            File[] filesList = dir.listFiles();
            for (File file : filesList) {
                if (file.isFile()) {
                    long totalBytes = file.length();
                    pq.add(new String[]{file.getName(), String.valueOf(totalBytes)});
                }
            }

            System.out.println("Filename" + " \t" +"Size");
            while(!pq.isEmpty()){
                String[] pop = pq.remove();
                String fileName = pop[0];
                long totalBytes = Long.valueOf(pop[1]);
                System.out.println(fileName + " \t" + String.format("%,d bytes", totalBytes));
            }
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	
	// Prints out the hash digest files names that contains same hash digest and all the original line which contributes to this digest 
    private static void printDuplicateContent(List<String[]> duplicate) {
        System.out.println("First Filename: \tSecond Filename: \t Original Text:");
        for (String[] s : duplicate){
            // 0-Second Occurance Index, 1-Duplicate FileName1, 2-Duplicate FileName2, 3-Hash Digest, 4-Original Line
            String fileName1 = s[1];
            String fileName2 = s[2];
            String originalText = s[4];
            System.out.println(fileName1 + " \t" + fileName2 + " \t" + originalText);
        }
        System.out.println();
    }
	
	// Uses SHA-256 to generate hash digest of 64 bytes  
    private static List<String> generateHashDigest(List<String> file, List<String[]> duplicate) {
        List<String> hashDigest  = new ArrayList<>();
        HashMap<String, String> set = new HashMap<>();
        try{
            for (int i=0; i<file.size(); i++){
                String content = file.get(i);
                String fileName = "File-"+(i+1)+".hash";
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedhash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
                String digestMsg = bytes2Hex(encodedhash);
                hashDigest.add(digestMsg);
                //If already have seen same digest then duplicate
                if (set.containsKey(digestMsg)){
                    // 0-Second Occurance Index, 1-Duplicate FileName1, 2-Duplicate FileName2, 3-Hash Digest, 4-Original Line
                    duplicate.add(new String[]{fileName.substring(5, fileName.length()-5), set.get(digestMsg), fileName, digestMsg, content});
                }
                else{
                    set.put(digestMsg, fileName);
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(digestMsg);
                writer.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(hashDigest);
        return hashDigest;
    }
	
	// Generate Files like File-#, where # denotes the line in loomings.txt file 
    private static void generateFiles(List<String> file) {
        try{
            for (int i=0; i<file.size(); i++){
                String content = file.get(i);
                String fileName = "File-"+(i+1);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(content);
                writer.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	//Used as a helper method to convert the byte code data generated vis SHA-256 and converts it into more readable HexCode
    private static String bytes2Hex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
	
	//Reads all the non-blank lines from loomings.txt files
    private static List<String> getLines(String filePath) {
        List<String> file = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("data" +fileSeperator + filePath));
            String line = reader.readLine();
            while (line != null) {
                if (line.length() > 0)
                    file.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
