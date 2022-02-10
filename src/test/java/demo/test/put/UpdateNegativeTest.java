package demo.test.put;

import demo.model.TodoDTO;
import demo.test.AbstractCleanTest;
import okhttp3.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class UpdateNegativeTest extends AbstractCleanTest {
    @Test
    public void canUpdateId() throws IOException {
        TodoDTO origin = client.getCache().prepareNewTodo(false);
        client.createNew(origin);

        TodoDTO expected = new TodoDTO(origin.getId() + 1, origin.getText(), origin.getCompleted());
        String newTodoJson = String.format("{\"id\":%s,\"text\":\"%s\",\"completed\":false}", origin.getId() + 1, origin.getText());
        Response response = client.updateTodos(origin.getId(), newTodoJson);
        Assertions.assertThat(response.code())
                .isEqualTo(200);

        List<TodoDTO> todos = client.getTodos();
        Assertions.assertThat(todos)
                .contains(expected)
                .doesNotContain(origin);
    }

    @Test
    public void updateOnSameData() {
        TodoDTO origin = client.getCache().prepareNewTodo(false);
        client.createNew(origin);
        client.updateTodos(origin);

        List<TodoDTO> todos = client.getTodos();
        Assertions.assertThat(todos)
                .contains(origin);
    }

    @Test
    public void idIsNullInRequest() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"id\":null,\"text\":\"Test\",\"completed\":false}");

        assertBadRequest(response);
    }

    @Test
    public void noIdInModelInRequest() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"text\":\"Test\",\"completed\":false}");

        assertBadRequest(response);
    }

    @Test
    public void textIsNullInRequest() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"id\":1,\"text\":null,\"completed\":false}");

        assertBadRequest(response);
    }

    @Test
    public void noTextInRequest() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"id\":1,\"completed\":false}");

        assertBadRequest(response);
    }

    @Test
    public void completedIsNullInRequest() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"id\":1,\"text\":\"test\",\"completed\":null}");

        assertBadRequest(response);
    }

    @Test
    public void noCompletedInRequest() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"id\":1,\"text\":\"test\"}");

        assertBadRequest(response);
    }

    @Test
    public void idIsOnlyNumExpected() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"id\":\"1\",\"text\":\"test\",\"completed\":false}");

        assertBadRequest(response);
    }

    @Test
    public void completedIsOnlyBool() throws IOException {
        TodoDTO origin = new TodoDTO(1, "Test", false);
        client.createNew(origin);
        Response response = client.updateTodos(origin.getId(), "{\"id\":1,\"text\":\"test\",\"completed\":\"false\"}");

        assertBadRequest(response);
    }
}
