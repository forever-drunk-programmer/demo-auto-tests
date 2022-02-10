package demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.model.TodoDTO;
import okhttp3.Response;
import org.assertj.core.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationClient extends AbstractApplicationClient {
    private final static int OK_CODE = 200;
    private final static int CREATED_CODE = 201;
    private final static int UPDATED_CODE = 200;
    private final static int DELETED_CODE = 204;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TodosRepository cache;

    public ApplicationClient(String baseUrl) {
        super(baseUrl);
        this.cache = new TodosRepository();
        List<TodoDTO> todos = getTodos();
        todos.forEach(it -> getCache().addNewToCache(it));
    }

    public Response createNewRaw(TodoDTO dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);

            return createNewRaw(json);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public TodoDTO createNew(TodoDTO dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            Response response = super.createNewRaw(json);

            Assertions.assertThat(response.code())
                    .isEqualTo(CREATED_CODE);

            getCache().addNewToCache(dto);
            return dto;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<TodoDTO> getTodos(Integer offset, Integer limit) {
        try {
            Response response = super.getTodosRaw(offset, limit);

            Assertions.assertThat(response.code())
                    .isEqualTo(OK_CODE);

            TodoDTO[] todos = objectMapper.readValue(response.body().string(), TodoDTO[].class);
            return Arrays.stream(todos).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public TodoDTO updateTodos(TodoDTO entity) {
        try {
            String json = objectMapper.writeValueAsString(entity);
            Response response = super.updateTodos(entity.getId(), json);

            Assertions.assertThat(response.code())
                    .isEqualTo(UPDATED_CODE);

            getCache().addNewToCache(entity);
            return entity;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteTodos(Long id) {
        try {
            Response response = super.deleteTodosRaw(id);

            Assertions.assertThat(response.code())
                    .isEqualTo(DELETED_CODE);

            getCache().deleteTodo(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<TodoDTO> getTodos() {
        return getTodos(null, null);
    }

    public List<TodoDTO> getLimitedTodos(Integer limit) {
        return getTodos(null, limit);
    }

    public List<TodoDTO> getOffsetTodos(Integer offset) {
        return getTodos(offset, null);
    }

    public TodosRepository getCache() {
        return cache;
    }
}
