package buildcraft.core.lib.commands;

import java.util.List;
import java.util.SortedSet;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public interface IModCommand extends ICommand {
  String getFullCommandString();
  
  List<String> func_71514_a();
  
  int getMinimumPermissionLevel();
  
  SortedSet<SubCommand> getChildren();
  
  void printHelp(ICommandSender paramICommandSender);
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\commands\IModCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */