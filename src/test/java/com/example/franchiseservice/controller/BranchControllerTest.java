package com.example.franchiseservice.controller;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.branch.BranchRequestDTO;
import com.example.franchiseservice.dto.branch.BranchResponseDTO;
import com.example.franchiseservice.service.BranchService;
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

@WebFluxTest(controllers = BranchController.class)
public class BranchControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BranchService branchService;

    private BranchRequestDTO branchRequestDTO;
    private BranchResponseDTO branchResponseDTO;
    private UpdateNameDTO updateNameDTO;

    @BeforeEach
    void setUp() {
        branchRequestDTO = new BranchRequestDTO();
        branchRequestDTO.setName("New Branch");

        branchResponseDTO = new BranchResponseDTO();
        branchResponseDTO.setId(1L);
        branchResponseDTO.setName("New Branch");

        updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Branch");
    }

    @Test
    void testAddBranchToFranchise() {
        when(branchService.addBranchToFranchise(anyLong(), any(BranchRequestDTO.class)))
                .thenReturn(Mono.just(branchResponseDTO));

        webTestClient.post()
                .uri("/branches/franchise/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(branchRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BranchResponseDTO.class)
                .value(response -> {
                    assert response.getName().equals("New Branch");
                    assert response.getId().equals(1L);
                });

        Mockito.verify(branchService).addBranchToFranchise(anyLong(), any(BranchRequestDTO.class));
    }

    @Test
    void testUpdateBranchName() {
        when(branchService.updateBranchName(anyLong(), any(UpdateNameDTO.class)))
                .thenReturn(Mono.just(branchResponseDTO));

        webTestClient.put()
                .uri("/branches/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateNameDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BranchResponseDTO.class)
                .value(response -> {
                    assert response.getName().equals("New Branch");
                });

        Mockito.verify(branchService).updateBranchName(anyLong(), any(UpdateNameDTO.class));
    }
}
