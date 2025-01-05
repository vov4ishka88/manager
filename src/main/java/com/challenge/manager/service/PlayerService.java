package com.challenge.manager.service;

import com.challenge.manager.model.Player;
import com.challenge.manager.model.Team;
import com.challenge.manager.repository.PlayerRepository;
import com.challenge.manager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

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

    @Transactional
    public void transferPlayer(Long playerId, Long fromTeamId, Long toTeamId){

        Player player =  playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        Team fromTeam = teamRepository.findById(fromTeamId)
                .orElseThrow(() -> new IllegalArgumentException("From team not found"));

        Team toTeam = teamRepository.findById(toTeamId)
                .orElseThrow(() -> new IllegalArgumentException("To team not found"));

        if(!player.getTeam().getId().equals(fromTeamId)){
            throw new IllegalArgumentException("Player isn't part of the From team");
        }

        BigDecimal transferFee = calculateTransferFee(player, fromTeam.getCommissionRate());

        if(toTeam.getBalance().compareTo(transferFee) < 0){
            throw new IllegalArgumentException("To team does not have enough balance for the transfer");
        }

        toTeam.setBalance(toTeam.getBalance().subtract(transferFee));
        fromTeam.setBalance(fromTeam.getBalance().add(transferFee));

        player.setTeam(toTeam);

        teamRepository.save(fromTeam);
        teamRepository.save(toTeam);
        playerRepository.save(player);
    }

    private BigDecimal calculateTransferFee(Player player, Float commissionRate){
        BigDecimal baseFee = BigDecimal.valueOf(player.getExperienceMonths() * 100000)
                .divide(BigDecimal.valueOf(player.getAge()));

        BigDecimal commission = baseFee.multiply(BigDecimal.valueOf(commissionRate / 100));
        return baseFee.add(commission);
    }
}
