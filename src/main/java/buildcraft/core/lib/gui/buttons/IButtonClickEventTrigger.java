package buildcraft.core.lib.gui.buttons;

public interface IButtonClickEventTrigger {
  void registerListener(IButtonClickEventListener paramIButtonClickEventListener);
  
  void removeListener(IButtonClickEventListener paramIButtonClickEventListener);
  
  void notifyAllListeners();
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\gui\buttons\IButtonClickEventTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */