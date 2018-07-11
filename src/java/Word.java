import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Entity
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String word;
    private String noAccent;
    private String meaning;
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication1PU");
    public static EntityManager em = emf.createEntityManager();

    public Word() {
    }

    public String getWord() {
        return this.word;
    }

    public String getMeaning() {
        return this.meaning;
    }

    public Word(String word, String meaning) {
        this.word = word;
        this.noAccent = VNCharacterUtils.removeAccent(word);
        this.meaning = meaning;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Word)) {
            return false;
        }
        Word other = (Word) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Word saveToDB() {

        this.em.getTransaction().begin();
        try {
            this.em.persist(this); //save Object to DB
            this.em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static List<Word> getMeaning(String word) {
        Query query = em.createQuery("SELECT p FROM Word p where CAST(p.word AS binary) = CAST(?1 AS binary) or CAST(p.noAccent AS binary) = CAST(?1 AS binary)").setParameter(1, word);
        List<Word> results = query.getResultList();
        return results;
    }

}
