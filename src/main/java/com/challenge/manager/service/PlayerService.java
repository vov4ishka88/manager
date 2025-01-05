package com.challenge.manager.service;

import com.challenge.manager.model.Player;
import com.challenge.manager.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> updatePlayer(Player player) {
        return playerRepository.findById(player.getId())
                .map(existingPlayer -> {
                    existingPlayer.setName(player.getName());
                    existingPlayer.setAge(player.getAge());
                    existingPlayer.setExperienceMonths(player.getExperienceMonths());
                    return playerRepository.save(existingPlayer);
                });
    }

    public boolean deletePlayer(Long id) {
        if(playerRepository.existsById(id)){
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
