package demo.test.put;

import demo.model.TodoDTO;
import demo.test.AbstractCleanTest;
import okhttp3.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

public class UpdateTest extends AbstractCleanTest {
    @Test
    public void changeCompletedState() {
        TodoDTO todoDTO = client.getCache().prepareNewTodo(false);
        client.createNew(todoDTO);
        TodoDTO expected = new TodoDTO(todoDTO.getId(), todoDTO.getText(), true);
        client.updateTodos(expected);
        TodoDTO actual = client.getTodos().stream().filter(it -> Objects.equals(it.getId(), expected.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found expected TODOS"));

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    public void canBeEmptyText() throws IOException {
        Response response = client.createNewRaw("{\"id\":1,\"text\":\"\",\"completed\":false}");
        Assertions.assertThat(response.code())
                .isEqualTo(201);
    }

    @Test
    public void completedTrueAndFalse() throws IOException {
        Response response1 = client.createNewRaw("{\"id\":1,\"text\":\"\",\"completed\":false}");
        Response response2 = client.createNewRaw("{\"id\":2,\"text\":\"\",\"completed\":true}");
        Assertions.assertThat(response1.code())
                .isEqualTo(201);
        Assertions.assertThat(response2.code())
                .isEqualTo(201);
    }
}
