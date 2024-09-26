package com.example.franchiseservice.controller;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.franchise.FranchiseRequestDTO;
import com.example.franchiseservice.dto.franchise.FranchiseResponseDTO;
import com.example.franchiseservice.service.FranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranchiseController.class)
public class FranchiseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FranchiseService franchiseService;

    private FranchiseRequestDTO franchiseRequestDTO;
    private FranchiseResponseDTO franchiseResponseDTO;
    private UpdateNameDTO updateNameDTO;

    @BeforeEach
    void setUp() {
        franchiseRequestDTO = new FranchiseRequestDTO();
        franchiseRequestDTO.setName("New Franchise");

        franchiseResponseDTO = new FranchiseResponseDTO();
        franchiseResponseDTO.setId(1L);
        franchiseResponseDTO.setName("New Franchise");

        updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Franchise");
    }

    @Test
    void testAddFranchise() {
        when(franchiseService.createFranchise(any(FranchiseRequestDTO.class)))
                .thenReturn(Mono.just(franchiseResponseDTO));

        webTestClient.post()
                .uri("/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(franchiseRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FranchiseResponseDTO.class)
                .value(response -> {
                    assert response.getName().equals("New Franchise");
                    assert response.getId().equals(1L);
                });

        Mockito.verify(franchiseService).createFranchise(any(FranchiseRequestDTO.class));
    }

    @Test
    void testUpdateFranchiseName() {
        // Set the expected name in the response DTO to match the update
        franchiseResponseDTO.setName("Updated Franchise");

        when(franchiseService.updateFranchiseName(anyLong(), any(UpdateNameDTO.class)))
                .thenReturn(Mono.just(franchiseResponseDTO));

        webTestClient.put()
                .uri("/franchises/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateNameDTO)  // Ensure this contains the correct updated name
                .exchange()
                .expectStatus().isOk()
                .expectBody(FranchiseResponseDTO.class)
                .value(response -> {
                    // Now the expected name should match the actual response name
                    assert response.getName().equals("Updated Franchise");
                });

        Mockito.verify(franchiseService).updateFranchiseName(anyLong(), any(UpdateNameDTO.class));
    }

}