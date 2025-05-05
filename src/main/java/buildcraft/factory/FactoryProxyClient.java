/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.core.lib.EntityBlock;
/*    */ import buildcraft.core.render.RenderLEDTile;
/*    */ import buildcraft.core.render.RenderingEntityBlocks;
/*    */ import buildcraft.factory.render.RenderHopper;
/*    */ import buildcraft.factory.render.RenderRefinery;
/*    */ import buildcraft.factory.render.RenderTank;
/*    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FactoryProxyClient
/*    */   extends FactoryProxy
/*    */ {
/*    */   public static IIcon pumpTexture;
/*    */   
/*    */   public void initializeTileEntities() {
/* 30 */     super.initializeTileEntities();
/*    */     
/* 32 */     if (BuildCraftFactory.tankBlock != null) {
/* 33 */       ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, (TileEntitySpecialRenderer)new RenderTank());
/*    */     }
/*    */     
/* 36 */     if (BuildCraftFactory.refineryBlock != null) {
/* 37 */       ClientRegistry.bindTileEntitySpecialRenderer(TileRefinery.class, (TileEntitySpecialRenderer)new RenderRefinery());
/* 38 */       RenderingEntityBlocks.blockByEntityRenders.put(new RenderingEntityBlocks.EntityRenderIndex((Block)BuildCraftFactory.refineryBlock, 0), new RenderRefinery());
/*    */     } 
/*    */     
/* 41 */     if (BuildCraftFactory.hopperBlock != null) {
/* 42 */       ClientRegistry.bindTileEntitySpecialRenderer(TileHopper.class, (TileEntitySpecialRenderer)new RenderHopper());
/* 43 */       RenderingEntityBlocks.blockByEntityRenders.put(new RenderingEntityBlocks.EntityRenderIndex((Block)BuildCraftFactory.hopperBlock, 0), new RenderHopper());
/*    */     } 
/*    */     
/* 46 */     ClientRegistry.bindTileEntitySpecialRenderer(TileMiningWell.class, (TileEntitySpecialRenderer)new RenderLEDTile((Block)BuildCraftFactory.miningWellBlock));
/* 47 */     ClientRegistry.bindTileEntitySpecialRenderer(TilePump.class, (TileEntitySpecialRenderer)new RenderLEDTile((Block)BuildCraftFactory.pumpBlock));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initializeEntityRenders() {}
/*    */ 
/*    */   
/*    */   public EntityBlock newPumpTube(World w) {
/* 56 */     EntityBlock eb = super.newPumpTube(w);
/* 57 */     eb.setTexture(pumpTexture);
/* 58 */     return eb;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\FactoryProxyClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */