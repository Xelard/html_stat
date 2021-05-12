package model.entity;

import java.io.Serializable;
import java.util.Objects;

public class WebSource implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    public WebSource() {
    }

    public WebSource(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WebSource source = (WebSource) o;
        return Objects.equals(id, source.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WebSources {" +
                "id =" + id +
                ", name ='" + name + '\'' +
                '}';
    }
}
