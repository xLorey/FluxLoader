package io.xlorey.fluxloader.client.core;

import io.xlorey.fluxloader.client.states.FluxLogoState;
import io.xlorey.fluxloader.utils.Logger;
import zombie.GameWindow;
import zombie.gameStates.GameState;
import zombie.gameStates.TISLogoState;

import java.util.List;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Game state manager
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class StateManager {
    /**
     * Initializing and adding the FluxLoader logo to display when loading the game
     */
    private static void initLogoState() {
        List<GameState> states = GameWindow.states.States;
        GameState tisLogoState = states.get(0);

        if (tisLogoState instanceof TISLogoState) {
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