/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.api.robots.DockingStation;
/*    */ import buildcraft.api.robots.IRobotRegistry;
/*    */ import buildcraft.api.robots.IRobotRegistryProvider;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ 
/*    */ public class RobotRegistryProvider
/*    */   implements IRobotRegistryProvider
/*    */ {
/* 13 */   private static HashMap<Integer, RobotRegistry> registries = new HashMap<Integer, RobotRegistry>();
/*    */ 
/*    */   
/*    */   public synchronized RobotRegistry getRegistry(World world) {
/* 17 */     if (!registries.containsKey(Integer.valueOf(world.field_73011_w.field_76574_g)) || ((RobotRegistry)registries
/* 18 */       .get(Integer.valueOf(world.field_73011_w.field_76574_g))).world != world) {
/*    */       
/* 20 */       RobotRegistry newRegistry = (RobotRegistry)world.perWorldStorage.func_75742_a(RobotRegistry.class, "robotRegistry");
/*    */       
/* 22 */       if (newRegistry == null) {
/* 23 */         newRegistry = new RobotRegistry("robotRegistry");
/* 24 */         world.perWorldStorage.func_75745_a("robotRegistry", newRegistry);
/*    */       } 
/*    */       
/* 27 */       newRegistry.world = world;
/*    */       
/* 29 */       for (DockingStation d : newRegistry.stations.values()) {
/* 30 */         d.world = world;
/*    */       }
/*    */       
/* 33 */       MinecraftForge.EVENT_BUS.register(newRegistry);
/*    */       
/* 35 */       registries.put(Integer.valueOf(world.field_73011_w.field_76574_g), newRegistry);
/*    */       
/* 37 */       return newRegistry;
/*    */     } 
/*    */     
/* 40 */     return registries.get(Integer.valueOf(world.field_73011_w.field_76574_g));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RobotRegistryProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */