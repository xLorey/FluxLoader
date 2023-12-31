package zombie.commands.serverCommands;

import io.xlorey.FluxLoader.annotations.Modified;
import io.xlorey.FluxLoader.shared.EventManager;
import zombie.commands.CommandBase;
import zombie.commands.CommandHelp;
import zombie.commands.CommandName;
import zombie.commands.RequiredRight;
import zombie.core.logger.LoggerManager;
import zombie.core.raknet.UdpConnection;
import zombie.debug.DebugLog;
import zombie.network.ServerMap;

@CommandName(
        name = "quit"
)
@CommandHelp(
        helpText = "UI_ServerOptionDesc_Quit"
)
@RequiredRight(
        requiredRights = 32
)
public class QuitCommand extends CommandBase {
    public QuitCommand(String var1, String var2, String var3, UdpConnection var4) {
        super(var1, var2, var3, var4);
    }

    @Modified
    protected String Command() {
        EventManager.invokeEvent("onServerShutdown");
        DebugLog.Multiplayer.debugln(this.description);
        ServerMap.instance.QueueSaveAll();
        ServerMap.instance.QueueQuit();
        LoggerManager.getLogger("admin").write(this.getExecutorUsername() + " closed server");
        return "Quit";
    }
}
