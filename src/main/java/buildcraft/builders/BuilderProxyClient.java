/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.BuildCraftBuilders;
/*    */ import buildcraft.builders.render.RenderArchitect;
/*    */ import buildcraft.builders.render.RenderBuilderTile;
/*    */ import buildcraft.builders.render.RenderConstructionMarker;
/*    */ import buildcraft.builders.render.RenderFiller;
/*    */ import buildcraft.builders.render.RenderFrame;
/*    */ import buildcraft.core.lib.EntityBlock;
/*    */ import buildcraft.core.lib.render.RenderMultiTESR;
/*    */ import buildcraft.core.lib.render.RenderVoid;
/*    */ import buildcraft.core.render.RenderBuilder;
/*    */ import buildcraft.core.render.RenderLEDTile;
/*    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.entity.Render;
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
/*    */ public class BuilderProxyClient
/*    */   extends BuilderProxy
/*    */ {
/*    */   public static IIcon drillTexture;
/*    */   public static IIcon drillSideTexture;
/*    */   public static IIcon drillHeadTexture;
/*    */   
/*    */   public void registerClientHook() {}
/*    */   
/*    */   public void registerBlockRenderers() {
/* 41 */     super.registerBlockRenderers();
/*    */     
/* 43 */     ClientRegistry.bindTileEntitySpecialRenderer(TileBuilder.class, (TileEntitySpecialRenderer)new RenderBuilderTile());
/* 44 */     ClientRegistry.bindTileEntitySpecialRenderer(TileConstructionMarker.class, (TileEntitySpecialRenderer)new RenderConstructionMarker());
/*    */     
/* 46 */     ClientRegistry.bindTileEntitySpecialRenderer(TileFiller.class, (TileEntitySpecialRenderer)new RenderMultiTESR(new TileEntitySpecialRenderer[] { (TileEntitySpecialRenderer)new RenderLEDTile((Block)BuildCraftBuilders.fillerBlock), (TileEntitySpecialRenderer)new RenderFiller() }));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     ClientRegistry.bindTileEntitySpecialRenderer(TileQuarry.class, (TileEntitySpecialRenderer)new RenderMultiTESR(new TileEntitySpecialRenderer[] { (TileEntitySpecialRenderer)new RenderLEDTile((Block)BuildCraftBuilders.quarryBlock), (TileEntitySpecialRenderer)new RenderBuilder() }));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     ClientRegistry.bindTileEntitySpecialRenderer(TileArchitect.class, (TileEntitySpecialRenderer)new RenderMultiTESR(new TileEntitySpecialRenderer[] { (TileEntitySpecialRenderer)new RenderLEDTile((Block)BuildCraftBuilders.architectBlock), (TileEntitySpecialRenderer)new RenderArchitect() }));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     RenderingRegistry.registerEntityRenderingHandler(EntityMechanicalArm.class, (Render)new RenderVoid());
/*    */     
/* 63 */     frameRenderId = RenderingRegistry.getNextAvailableRenderId();
/* 64 */     RenderingRegistry.registerBlockHandler(frameRenderId, (ISimpleBlockRenderingHandler)new RenderFrame());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityBlock newDrill(World w, double i, double j, double k, double l, double d, double e, boolean xz) {
/* 70 */     EntityBlock eb = super.newDrill(w, i, j, k, l, d, e, xz);
/* 71 */     if (xz) {
/* 72 */       eb.texture = new IIcon[6];
/* 73 */       for (int a = 0; a < 6; a++) {
/* 74 */         eb.texture[a] = (a >= 2) ? drillSideTexture : drillTexture;
/*    */       }
/*    */     } else {
/* 77 */       eb.setTexture(drillTexture);
/*    */     } 
/* 79 */     return eb;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityBlock newDrillHead(World w, double i, double j, double k, double l, double d, double e) {
/* 84 */     EntityBlock eb = super.newDrillHead(w, i, j, k, l, d, e);
/* 85 */     eb.setTexture(drillHeadTexture);
/* 86 */     return eb;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BuilderProxyClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */