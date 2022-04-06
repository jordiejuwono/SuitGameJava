package com.example.suitgamejavaversion.utils;

public class GetWinner {

    public final static String DRAW = "Draw";
    public final static String PLAYER_ONE_WIN = "Player One Win";
    public final static String COMPUTER_WIN = "Com Win";

    public static String getWinner(Hands playerHand, Hands computerHands) {
        if (playerHand == computerHands){
            return DRAW;
        } else if (playerHand == Hands.PAPER && computerHands == Hands.ROCK){
            return PLAYER_ONE_WIN;
        } else if (playerHand == Hands.ROCK && computerHands == Hands.SCISSORS){
            return PLAYER_ONE_WIN;
        } else if (playerHand == Hands.SCISSORS && computerHands == Hands.PAPER){
            return PLAYER_ONE_WIN;
        } else {
            return COMPUTER_WIN;
        }
    }

}
