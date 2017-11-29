package blackjack;

import java.util.List;

public class DataList {
    private List<Player> players;
    public DataList(List<Player> p){
        for(Player play: p){
            players.add(play);
        }
    }
    public List<Player> getPlayers(){
        return players;
        //test
        
    }
}
