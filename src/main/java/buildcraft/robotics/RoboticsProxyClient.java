/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.robotics.render.RenderRobot;
/*    */ import buildcraft.robotics.render.RenderZonePlan;
/*    */ import buildcraft.robotics.render.RobotStationItemRenderer;
/*    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import cpw.mods.fml.common.Loader;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import net.minecraftforge.client.MinecraftForgeClient;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoboticsProxyClient
/*    */   extends RoboticsProxy
/*    */ {
/*    */   public void registerRenderers() {
/* 23 */     RenderingRegistry.registerEntityRenderingHandler(EntityRobot.class, (Render)new RenderRobot());
/* 24 */     MinecraftForgeClient.registerItemRenderer(BuildCraftRobotics.robotItem, (IItemRenderer)new RenderRobot());
/* 25 */     ClientRegistry.bindTileEntitySpecialRenderer(TileZonePlan.class, (TileEntitySpecialRenderer)new RenderZonePlan());
/*    */ 
/*    */     
/* 28 */     if (Loader.isModLoaded("BuildCraft|Transport")) {
/* 29 */       loadBCTransport();
/*    */     }
/*    */   }
/*    */   
/*    */   private void loadBCTransport() {
/* 34 */     MinecraftForgeClient.registerItemRenderer(BuildCraftRobotics.robotStationItem, (IItemRenderer)new RobotStationItemRenderer());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\RoboticsProxyClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */