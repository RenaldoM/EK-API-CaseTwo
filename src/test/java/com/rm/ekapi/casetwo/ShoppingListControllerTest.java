package com.rm.ekapi.casetwo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

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
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString()),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString())
        );
        when(shoppingListService.getAllItems()).thenReturn(mockItems);

        mockMvc.perform(get("/api/shoppingitems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(mockItems.size()));
    }

    @Test
    public void testGetItem() throws Exception {
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString());
        when(shoppingListService.getItem("Apple")).thenReturn(Optional.of(mockItem));

        mockMvc.perform(get("/api/shoppingitems/Apple"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectName").value("Apple"));
    }

    @Test
    public void testCreateItem() throws Exception {
        mockAllItems();
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", null, LocalDateTime.now().plusDays(1).toString());
        when(shoppingListService.createItem(any())).thenReturn(mockItem);

        mockMvc.perform(post("/api/shoppingitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"objectName\":\"Apple\",\"quantity\":10,\"creatorName\":\"User1\",\"dueDate\":\""+LocalDateTime.now().plusDays(1)+"\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectName").value("Apple"));
    }

    @Test
    public void testUpdateItem() throws Exception {
        mockAllItems();
        ShoppingItem mockItem = new ShoppingItem("Apple", 10, "User1", null, LocalDateTime.now().plusDays(1).toString());
        when(shoppingListService.updateItem(anyString(), any())).thenReturn(mockItem);

        mockMvc.perform(put("/api/shoppingitems/Apple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"objectName\":\"Apple\",\"quantity\":10,\"creatorName\":\"User1\",\"dueDate\":\""+LocalDateTime.now().plusDays(1)+"\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.objectName").value("Apple"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        mockAllItems();
        doNothing().when(shoppingListService).deleteItem("Apple");

        mockMvc.perform(delete("/api/shoppingitems/Apple"))
                .andExpect(status().is2xxSuccessful());
    }

    private void mockAllItems(){
        List<ShoppingItem> mockItems = Arrays.asList(
                new ShoppingItem("Apple", 10, "User1", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString()),
                new ShoppingItem("Banana", 5, "User2", LocalDateTime.now(), LocalDateTime.now().plusDays(1).toString())
        );
        when(shoppingListService.getAllItems()).thenReturn(mockItems);
    }

}
