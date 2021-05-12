package model.entity;

import java.io.Serializable;
import java.util.Objects;

public class Word implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String word;
    private int qty;

    private WebSource source;

    public Word() {}

    public Word(String word, int qty, WebSource source) {
        this.word = word;
        this.qty = qty;
        this.source = source;
    }

    public long getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }

    public String getWord() { return word; }
    public void setWord(String word) {
        this.word = word;
    }

    public int getQtyWords() {
        return qty;
    }
    public void setQtyWords(int qty) {
        this.qty = qty;
    }

    public WebSource getWebSource() { return source; }
    public void setWebSource(WebSource source) { this.source = source; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;
        return Objects.equals(id, word.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UniqueWords{" +
                "id =" + id +
                ", word ='" + word + '\'' +
                ", qty =" + qty +
                ", source ='" + source + '\'' +
                '}';
    }
}
