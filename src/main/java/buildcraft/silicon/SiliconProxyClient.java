/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import buildcraft.BuildCraftSilicon;
/*    */ import buildcraft.silicon.render.RenderLaserBlock;
/*    */ import buildcraft.silicon.render.RenderLaserTable;
/*    */ import buildcraft.silicon.render.RenderLaserTile;
/*    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderSnowball;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SiliconProxyClient
/*    */   extends SiliconProxy
/*    */ {
/*    */   public void registerRenderers() {
/* 24 */     SiliconProxy.laserBlockModel = RenderingRegistry.getNextAvailableRenderId();
/* 25 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderLaserBlock());
/*    */     
/* 27 */     SiliconProxy.laserTableModel = RenderingRegistry.getNextAvailableRenderId();
/* 28 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderLaserTable());
/*    */     
/* 30 */     ClientRegistry.bindTileEntitySpecialRenderer(TileLaser.class, (TileEntitySpecialRenderer)new RenderLaserTile());
/* 31 */     RenderingRegistry.registerEntityRenderingHandler(EntityPackage.class, (Render)new RenderSnowball((Item)BuildCraftSilicon.packageItem));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\SiliconProxyClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */