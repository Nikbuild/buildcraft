package buildcraft.robotics;

import cpw.mods.fml.common.SidedProxy;

public class RoboticsProxy {
  @SidedProxy(clientSide = "buildcraft.robotics.RoboticsProxyClient", serverSide = "buildcraft.robotics.RoboticsProxy")
  public static RoboticsProxy proxy;
  
  public void registerRenderers() {}
}


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RoboticsProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */