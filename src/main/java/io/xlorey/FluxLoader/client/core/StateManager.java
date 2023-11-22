package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.client.gameStates.FluxLogoState;
import io.xlorey.FluxLoader.utils.Logger;
import zombie.GameWindow;
import zombie.gameStates.GameState;
import zombie.gameStates.TISLogoState;

import java.util.List;

/**
 * Game state manager
 */
public class StateManager {
    /**
     * Initializing and adding the FluxLoader logo to display when loading the game
     */
    private static void initLogoState() {
        List<GameState> states = GameWindow.states.States;
        GameState tisLogoState = states.get(0);

        if (tisLogoState instanceof TISLogoState)
        {
            GameWindow.states.States.add(1, new FluxLogoState());
            GameWindow.states.LoopToState = 2;
        }
        else {
            Logger.print(String.format(
                    "Unexpected GameState found at index 0 (%s) expected %s",
                    tisLogoState.getClass().getName(), TISLogoState.class.getName()));
        }
    }

    /**
     * Initializing
     */
    public static void init() {
        Logger.print("Initializing the state manager...");

        initLogoState();
    }
}
