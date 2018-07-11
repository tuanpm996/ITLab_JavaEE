import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

@ManagedBean
public class Fileupload implements Serializable {

    private Part uploadedFile;
    private String folder = "/home/michu/NetBeansProjects/WebApplication1/src/java/Dictionary/";
    
    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void saveFile() {

        try (InputStream input = uploadedFile.getInputStream()) {
            String fileName = uploadedFile.getSubmittedFileName();
            File file = new File(folder, fileName);
            Files.copy(input, file.toPath());
            String filePath = "/home/michu/NetBeansProjects/WebApplication1/src/java/Dictionary/" + fileName;
            insertToDictionary(filePath);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertToDictionary(String filePath) throws IOException {
        FileReader in = new FileReader(filePath);

        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(":")) {
                String[] words = line.split(":");
                Word word = new Word(words[0].trim(), words[1].trim());
                word.saveToDB();
            } else {
                if (line.contains(",")) {
                    int index = line.indexOf(',');
                    String first = line.substring(0, index).trim();
                    String second = line.substring(index + 1).trim();
                    Word word = new Word(first, second);
                    word.saveToDB();
                }
            }
        }
        br.close();
    }

}
