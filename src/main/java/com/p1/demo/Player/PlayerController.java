package com.p1.demo.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    PlayerController(PlayerService playerService){
        this.playerService=playerService;
    }

    @GetMapping
    public List<Player> getPlayers(
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String nation){

        if(team!=null && position!=null){
            playerService.getPlayerByTeamPos(team, position);
        } else if (team!=null) {
            playerService.getPlayerfromTeam(team);
        }else if(position!=null){
            playerService.getPlayerByPos(position);
        } else if (name!=null) {
            playerService.getPlayerByName(name);
        } else if (nation!=null) {
            playerService.getPlayerByNation(nation);
        }
        return playerService.getPlayers();
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(Player player){
        Player createPlayer=playerService.addPlayer(player);
        return new ResponseEntity<>(createPlayer, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(Player player){
        Player updatedPlayer=playerService.updatePlayer(player);
        if(updatedPlayer==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    @DeleteMapping("/{deletename}")
    public ResponseEntity<String> deletePlayer(@PathVariable String name){
        playerService.deletePlayer(name);
        return new ResponseEntity<>("Player Deleted", HttpStatus.OK);
    }
}
