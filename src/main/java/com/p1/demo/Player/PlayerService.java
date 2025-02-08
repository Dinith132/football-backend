package com.p1.demo.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository=playerRepository;
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public List<Player> getPlayerfromTeam(String teamname){
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getTeam().equals(teamname)).collect(Collectors.toList());
    }

    public List<Player> getPlayerByName(String name){
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getName().toLowerCase().contains(name))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByPos(String pos){
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getPos().toLowerCase().contains(pos))
                .toList();
    }

    public List<Player> getPlayerByNation(String nation){
        return playerRepository.findAll()
                .stream()
                .filter(player -> player.getNation().toLowerCase().contains(nation))
                .toList();
    }

    public List<Player> getPlayerByTeamPos(String team, String pos){
        return playerRepository.findAll()
                .stream()
                .filter(player -> team.equals(player.getTeam()) && pos.equals(player.getPos()))
                .toList();
    }

    public Player addPlayer(Player player){
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player upPlayer){
        Optional<Player> exPlayer= playerRepository.findByName(upPlayer.getName());

        if(exPlayer.isPresent()){
            Player playerToUpdate=exPlayer.get();

            playerToUpdate.setName(upPlayer.getName());
            playerToUpdate.setTeam(upPlayer.getTeam());
            playerToUpdate.setPos(upPlayer.getPos());
            playerToUpdate.setNation(upPlayer.getNation());

            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return null;
    }

    public void deletePlayer(String name){
        playerRepository.deleteByName(name);

    }





}
