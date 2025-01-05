package com.challenge.manager.controller;

import com.challenge.manager.model.Player;
import com.challenge.manager.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping()
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id){
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player){
        Player createdPlayer = playerService.createPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @PutMapping()
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player){
         return playerService.updatePlayer(player)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id){
        if(playerService.deletePlayer(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{playerId}/transfer")
    public ResponseEntity<String> transferPlayer(
            @PathVariable Long playerId,
            @RequestParam Long fromTeamId,
            @RequestParam Long toTeamId){
        try {
            playerService.transferPlayer(playerId, fromTeamId, toTeamId);
            return ResponseEntity.ok("Player transferred successfully");
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO: consider if including team object for the player creation isn't too much
    //TODO: consider to add Global Exception Handling
    //TODO: verify if Global Exception Handling handles @Valid exception
}
