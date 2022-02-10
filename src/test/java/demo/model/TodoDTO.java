package demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TodoDTO {
    private final Long id;
    private final String text;
    private final Boolean completed;

    public TodoDTO(Integer id, String text, Boolean completed) {
        this(id.longValue(), text, completed);
    }

    public TodoDTO(@JsonProperty("id") Long id,
                   @JsonProperty("text") String text,
                   @JsonProperty("completed") Boolean completed) {
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean getCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoDTO todoDTO = (TodoDTO) o;

        return Objects.equals(id, todoDTO.id)
                && Objects.equals(text, todoDTO.text)
                && Objects.equals(completed, todoDTO.completed);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (completed != null ? completed.hashCode() : 0);
        return result;
    }
}
