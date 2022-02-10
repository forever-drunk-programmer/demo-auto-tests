package demo.test.get;

import demo.test.AbstractCleanTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import demo.model.TodoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GetTest extends AbstractCleanTest {
    @Test
    public void getEmptyEntitiesList() {
        List<TodoDTO> todos = client.getTodos();
        Assertions.assertThat(todos).isEmpty();
    }

    @Test
    public void getTodosOffsetTest() {
        if (client.getCache().getAllIds().size() < 2) {
            TodoDTO newTodo = client.getCache().prepareNewTodo();
            client.createNew(newTodo);
        }

        Set<Long> cachedTodosIds = client.getCache().getAllIds();

        List<TodoDTO> actual = client.getOffsetTodos(1);
        Assertions.assertThat(actual)
                .hasSize(cachedTodosIds.size() - 1);
    }

    @Test
    public void getTodosLimitTest() {
        if (client.getCache().getAllIds().size() < 2) {
            TodoDTO newTodo = client.getCache().prepareNewTodo();
            client.createNew(newTodo);
        }

        List<TodoDTO> actual = client.getLimitedTodos(1);
        Assertions.assertThat(actual)
                .hasSize(1);
    }

    @Test
    public void getTodosOffsetAndLimitTest() {
        while (client.getCache().getAllIds().size() < 3) {
            TodoDTO newTodo = client.getCache().prepareNewTodo();
            client.createNew(newTodo);
        }

        List<TodoDTO> actual = client.getTodos(1, 1);
        Assertions.assertThat(actual)
                .hasSize(1);
    }

    @Test
    public void getTodosOffsetAndLimitCheckByCountTest() {
        while (client.getCache().getAllIds().size() < 5) {
            TodoDTO newTodo = client.getCache().prepareNewTodo();
            client.createNew(newTodo);
        }

        List<TodoDTO> actual = client.getTodos(2, 2);
        Assertions.assertThat(actual)
                .hasSize(2);
    }

    @Test
    public void getTodosZeroOffsetAndAllIsLimitTest() {
        while (client.getCache().getAllIds().size() < 3) {
            TodoDTO newTodo = client.getCache().prepareNewTodo();
            client.createNew(newTodo);
        }

        List<TodoDTO> actual = client.getTodos(0, 3);
        Assertions.assertThat(actual)
                .hasSize(3);
    }

    @Test
    public void getOrderTest() {
        TodoDTO todo1 = new TodoDTO(1, "Test1", false);
        TodoDTO todo2 = new TodoDTO(2, "Test2", false);
        TodoDTO todo3 = new TodoDTO(3, "Test3", false);
        TodoDTO todo4 = new TodoDTO(4, "Test4", false);

        List<TodoDTO> expectedOrder = new ArrayList<>();
        expectedOrder.add(todo3);
        expectedOrder.add(todo1);
        expectedOrder.add(todo2);
        expectedOrder.add(todo4);
        expectedOrder.forEach(it -> client.createNew(it));

        List<TodoDTO> actual = client.getTodos();

        Assertions.assertThat(actual)
                .containsExactlyElementsOf(expectedOrder);
    }

    @Test
    public void getCompletedEntity() {
        TodoDTO expected = new TodoDTO(1, "test", true);
        client.createNew(expected);
        List<TodoDTO> actual = client.getTodos();
        Assertions.assertThat(actual)
                .containsExactly(expected);
    }
}
