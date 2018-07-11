
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "wordController", eager = true)
@SessionScoped
public class WordController implements Serializable {

    private static final long serialVersionUID = 1L;
    private String word;
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        List<Word> results = Word.getMeaning(word);
        if (results.size() > 0) {
            //meaning exist
            StringBuffer sb = new StringBuffer();
            sb.append("Word you need: ");
            for (Word word : results) {
                sb.append(word.getWord());
                sb.append(": ");
                sb.append(word.getMeaning());
                sb.append("| ");    
            }
            return sb.toString();
        } else {
            //meaning not exist
            StringBuffer sb = new StringBuffer();
            sb.append("Word you need: Doesn't exist!");
            return sb.toString();
        }
    }
}
