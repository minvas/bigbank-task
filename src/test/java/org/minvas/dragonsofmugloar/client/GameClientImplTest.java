package org.minvas.dragonsofmugloar.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.client.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(GameClientImpl.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GameClientImplTest {
    private static final String GAME_ID = "aSdwA";
    private static final String MESSAGE_ID = "uSVase";
    private static final String ITEM_ID = "hpot";

    @Autowired
    private GameClientImpl gameClient;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.client.base-url}")
    private String baseUrl;

    @Test
    public void Start_game_returns_game_DTO() throws JsonProcessingException {
        String responseJson = objectMapper.writeValueAsString(new Game("aSdwA", 1, 2, 3, 4, 5, 7));

        String uri = UriComponentsBuilder.fromUriString(baseUrl).path("/game/start").toUriString();
        this.server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        Game game = this.gameClient.startGame();

        assertEquals("aSdwA", game.gameId());
        assertEquals(1, game.lives());
        assertEquals(2, game.gold());
        assertEquals(3, game.level());
        assertEquals(4, game.score());
        assertEquals(5, game.highScore());
        assertEquals(7, game.turn());
    }

    @Test
    public void Fetch_messages_returns_a_list_of_message_DTOs() throws JsonProcessingException {
        Message message = new Message("lWasc", "Test message", 15, 7, "Gamble");
        String responseJson = objectMapper.writeValueAsString(List.of(message));

        String uri = UriComponentsBuilder.fromUriString(baseUrl).path("/{gameId}/messages").build(GAME_ID).toString();
        this.server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        List<Message> messages = this.gameClient.fetchMessages(GAME_ID);

        assertEquals(1, messages.size());
        assertEquals("lWasc", messages.getFirst().adId());
        assertEquals("Test message", messages.getFirst().message());
        assertEquals(15, messages.getFirst().reward());
        assertEquals(7, messages.getFirst().expiresIn());
        assertEquals("Gamble", messages.getFirst().probability());
    }

    @Test
    public void Solve_message_returns_task_result_DTO() throws JsonProcessingException {
        TaskResult responseDto = new TaskResult(true, 2, 10, 150, 8000, 5, "Task completed successfully");
        String responseJson = objectMapper.writeValueAsString(responseDto);

        String uri = UriComponentsBuilder.fromUriString(baseUrl).path("/{gameId}/solve/{adId}").build(GAME_ID, MESSAGE_ID).toString();
        this.server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        TaskResult taskResult = this.gameClient.solveMessage(GAME_ID, MESSAGE_ID);

        assertTrue(taskResult.success());
        assertEquals(2, taskResult.lives());
        assertEquals(10, taskResult.gold());
        assertEquals(150, taskResult.score());
        assertEquals(8000, taskResult.highScore());
        assertEquals(5, taskResult.turn());
        assertEquals("Task completed successfully", taskResult.message());
    }

    @Test
    public void Fetch_shop_items_returns_a_list_of_shop_item_DTOs() throws JsonProcessingException {
        ShopItem shopItem = new ShopItem("hpot", "Health potion", 50);
        String responseJson = objectMapper.writeValueAsString(List.of(shopItem));

        String uri = UriComponentsBuilder.fromUriString(baseUrl).path("/{gameId}/shop").build(GAME_ID).toString();
        this.server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        List<ShopItem> shopItems = this.gameClient.fetchShopItems(GAME_ID);

        assertEquals(1, shopItems.size());
        assertEquals("hpot", shopItems.getFirst().id());
        assertEquals("Health potion", shopItems.getFirst().name());
        assertEquals(50, shopItems.getFirst().cost());
    }

    @Test
    public void Buy_items_returns_a_purchase_result_DTO() throws JsonProcessingException {
        PurchaseResult responseDto = new PurchaseResult(true, 2, 10, 7, 10);
        String responseJson = objectMapper.writeValueAsString(responseDto);

        String uri = UriComponentsBuilder.fromUriString(baseUrl).path("/{gameId}/shop/buy/{itemId}").build(GAME_ID, ITEM_ID).toString();
        this.server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseJson, MediaType.APPLICATION_JSON));

        PurchaseResult purchaseResult = this.gameClient.buyItem(GAME_ID, ITEM_ID);

        assertTrue(purchaseResult.shoppingSuccess());
        assertEquals(2, purchaseResult.gold());
        assertEquals(10, purchaseResult.lives());
        assertEquals(7, purchaseResult.level());
        assertEquals(10, purchaseResult.turn());
    }
}