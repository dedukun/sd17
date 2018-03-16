/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afternoonatrace_conc.Entities;

/**
 * Enumerate representing all existing states
 */
public enum States {
    //Broker
    OPENING_THE_EVENT,
    ANNOUNCING_NEXT_RACE,
    WAITING_FOR_BETS,
    SUPERVISING_THE_RACE,
    SETTLING_ACCOUNTS,
    PLAYING_HOST_AT_THE_BAR,
    //Horse/Jockey
    AT_THE_STABLE,
    AT_THE_PADDOCK,
    AT_THE_START_LINE,
    RUNNING,
    AT_THE_FINNISH_LINE,
    //Spectator
    WAITING_FOR_A_RACE_TO_START,
    APPRAISING_THE_HORSES,
    PLACING_A_BET,
    WATCHING_A_RACE,
    COLLECTING_THE_GAINS,
    CELEBRATING
}
