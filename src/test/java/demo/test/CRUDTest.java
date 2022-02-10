package demo.test;

import demo.model.TodoDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CRUDTest extends AbstractCleanTest {

    @Test
    public void createTodosTest() {
        TodoDTO todoDTO = client.getCache().prepareNewTodo("CRUDTest - create");
        client.createNew(todoDTO);
    }

    @Test
    public void createTodosAndCheckSizeTest() {
        TodoDTO todoDTO = client.getCache().prepareNewTodo("CRUDTest - create");
        client.createNew(todoDTO);
        List<TodoDTO> actual = client.getTodos();
        Assertions.assertThat(actual)
                .hasSize(1);
    }

    @Test
    public void getTodosTest() {
        TodoDTO todoDTO = client.getCache().prepareNewTodo("CRUDTest - create");
        client.createNew(todoDTO);

        List<TodoDTO> actual = client.getTodos();
        Assertions.assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(client.getCache().getAllTodos());
    }


    @Test
    public void updateTodosTest() {
        TodoDTO newTodo = client.getCache().prepareNewTodo();
        client.createNew(newTodo);

        TodoDTO expected = new TodoDTO(newTodo.getId(), "updateTest" + LocalDateTime.now(), false);
        client.updateTodos(expected);

        TodoDTO actual = client.getTodos().stream()
                .filter(it -> Objects.equals(it.getId(), expected.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found Todo"));

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    public void deleteTodosTest() {
        TodoDTO notExpected = client.getCache().prepareNewTodo();
        TodoDTO expected = client.getCache().prepareNewTodo();
        client.createNew(notExpected);
        client.createNew(expected);

        client.deleteTodos(notExpected.getId());

        List<TodoDTO> todos = client.getTodos();

        Assertions.assertThat(todos)
                .doesNotContain(notExpected)
                .contains(expected);
    }
}
