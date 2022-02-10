package demo.test.get;

import demo.model.TodoDTO;
import demo.test.AbstractCleanTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetNegativeTest extends AbstractCleanTest {
    @Test
    public void limitMoreThanObjects() {
        client.createNew(client.getCache().prepareNewTodo());

        List<TodoDTO> actual = client.getLimitedTodos(2);

        Assertions.assertThat(actual)
                .hasSize(1);
    }

    @Test
    public void offsetMoreThanElements() {
        client.createNew(client.getCache().prepareNewTodo());

        List<TodoDTO> actual = client.getOffsetTodos(1);

        Assertions.assertThat(actual)
                .isEmpty();
    }

    @Test
    public void zeroLimit() {
        client.createNew(client.getCache().prepareNewTodo());

        List<TodoDTO> actual = client.getLimitedTodos(0);

        Assertions.assertThat(actual)
                .isEmpty();
    }
}
