package com.rm.ekapi.casetwo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingListControllerTest {

    @MockBean
    private ShoppingListService shoppingListService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetAllItems() throws Exception {
        List<ShoppingItem> mockItems = Arrays.asList(
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );
        when(shoppingListService.getAllItems()).thenReturn(mockItems);

        mockMvc.perform(get("/api/shoppingitems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(mockItems.size()));
    }

    @Test
    public void testGetItem() throws Exception {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        when(shoppingListService.getItem("Apple")).thenReturn(Optional.of(mockItem));

        mockMvc.perform(get("/api/shoppingitems/Apple"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectName").value("Apple"));
    }

    @Test
    public void testCreateItem() throws Exception {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", null, LocalDateTime.now().plusDays(1));
        when(shoppingListService.createItem(any())).thenReturn(mockItem);

        mockMvc.perform(post("/api/shoppingitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"objectName\":\"Apple\",\"quantity\":10,\"creatorName\":\"User1\",\"dueDate\":\""+LocalDateTime.now().plusDays(1)+"\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectName").value("Apple"));
    }

    @Test
    public void testUpdateItem() throws Exception {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", null, LocalDateTime.now().plusDays(1));
        when(shoppingListService.updateItem(anyString(), any())).thenReturn(Optional.of(mockItem));

        mockMvc.perform(put("/api/shoppingitems/Apple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"objectName\":\"Apple\",\"quantity\":10,\"creatorName\":\"User1\",\"dueDate\":\""+LocalDateTime.now().plusDays(1)+"\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectName").value("Apple"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        doNothing().when(shoppingListService).deleteItem("Apple");

        mockMvc.perform(delete("/api/shoppingitems/Apple"))
                .andExpect(status().isOk());
    }

}
