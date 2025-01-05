package com.challenge.manager.service;

import com.challenge.manager.model.Team;
import com.challenge.manager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id){
        return teamRepository.findById(id);
    }

    public Team createTeam(Team team){
        return teamRepository.save(team);
    }

    public Optional<Team> updateTeam(Team updatedTeam){
        return teamRepository.findById(updatedTeam.getId()).map(existingTeam -> {
           existingTeam.setName(updatedTeam.getName());
           existingTeam.setBalance(updatedTeam.getBalance());
           existingTeam.setCommissionRate(updatedTeam.getCommissionRate());
           return teamRepository.save(existingTeam);
        });
    }

    public boolean deleteTeam(Long id){
        if(teamRepository.existsById(id)){
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
