/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.transport.render.FacadeItemRenderer;
/*    */ import buildcraft.transport.render.GateItemRenderer;
/*    */ import buildcraft.transport.render.PipeItemRenderer;
/*    */ import buildcraft.transport.render.PipeRendererTESR;
/*    */ import buildcraft.transport.render.PipeRendererWorld;
/*    */ import buildcraft.transport.render.PipeTransportFluidsRenderer;
/*    */ import buildcraft.transport.render.PipeTransportItemsRenderer;
/*    */ import buildcraft.transport.render.PipeTransportPowerRenderer;
/*    */ import buildcraft.transport.render.PipeTransportRenderer;
/*    */ import buildcraft.transport.render.PlugItemRenderer;
/*    */ import buildcraft.transport.render.TileEntityPickupFX;
/*    */ import cpw.mods.fml.client.FMLClientHandler;
/*    */ import cpw.mods.fml.client.registry.ClientRegistry;
/*    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*    */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import net.minecraftforge.client.MinecraftForgeClient;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransportProxyClient
/*    */   extends TransportProxy
/*    */ {
/* 36 */   public static final PipeItemRenderer pipeItemRenderer = new PipeItemRenderer();
/* 37 */   public static final PipeRendererWorld pipeWorldRenderer = new PipeRendererWorld();
/* 38 */   public static final FacadeItemRenderer facadeItemRenderer = new FacadeItemRenderer();
/* 39 */   public static final PlugItemRenderer plugItemRenderer = new PlugItemRenderer();
/* 40 */   public static final GateItemRenderer gateItemRenderer = new GateItemRenderer();
/*    */ 
/*    */   
/*    */   public void registerTileEntities() {
/* 44 */     super.registerTileEntities();
/* 45 */     ClientRegistry.bindTileEntitySpecialRenderer(TileGenericPipe.class, (TileEntitySpecialRenderer)PipeRendererTESR.INSTANCE);
/*    */   }
/*    */ 
/*    */   
/*    */   public void obsidianPipePickup(World world, EntityItem item, TileEntity tile) {
/* 50 */     (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((EntityFX)new TileEntityPickupFX(world, item, tile));
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearDisplayList(int displayList) {
/* 55 */     GLAllocation.func_74523_b(displayList);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerRenderers() {
/* 60 */     for (Item itemPipe : BlockGenericPipe.pipes.keySet()) {
/* 61 */       MinecraftForgeClient.registerItemRenderer(itemPipe, (IItemRenderer)pipeItemRenderer);
/*    */     }
/*    */     
/* 64 */     MinecraftForgeClient.registerItemRenderer((Item)BuildCraftTransport.facadeItem, (IItemRenderer)facadeItemRenderer);
/* 65 */     MinecraftForgeClient.registerItemRenderer(BuildCraftTransport.plugItem, (IItemRenderer)plugItemRenderer);
/* 66 */     MinecraftForgeClient.registerItemRenderer(BuildCraftTransport.pipeGate, (IItemRenderer)gateItemRenderer);
/*    */     
/* 68 */     PipeTransportRenderer.RENDERER_MAP.put(PipeTransportItems.class, new PipeTransportItemsRenderer());
/* 69 */     PipeTransportRenderer.RENDERER_MAP.put(PipeTransportFluids.class, new PipeTransportFluidsRenderer());
/* 70 */     PipeTransportRenderer.RENDERER_MAP.put(PipeTransportPower.class, new PipeTransportPowerRenderer());
/*    */     
/* 72 */     TransportProxy.pipeModel = RenderingRegistry.getNextAvailableRenderId();
/*    */     
/* 74 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)pipeWorldRenderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setIconProviderFromPipe(ItemPipe item, Pipe<?> dummyPipe) {
/* 79 */     item.setPipesIcons(dummyPipe.getIconProvider());
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\TransportProxyClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */