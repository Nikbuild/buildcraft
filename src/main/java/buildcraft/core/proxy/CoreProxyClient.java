/*     */ package buildcraft.core.proxy;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.LaserKind;
/*     */ import buildcraft.core.RenderPathMarker;
/*     */ import buildcraft.core.TilePathMarker;
/*     */ import buildcraft.core.lib.EntityBlock;
/*     */ import buildcraft.core.lib.engines.RenderEngine;
/*     */ import buildcraft.core.lib.engines.TileEngineBase;
/*     */ import buildcraft.core.lib.render.RenderBlockComplex;
/*     */ import buildcraft.core.lib.render.RenderEntityBlock;
/*     */ import buildcraft.core.render.RenderingEntityBlocks;
/*     */ import buildcraft.core.render.RenderingMarkers;
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.client.registry.ClientRegistry;
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoreProxyClient
/*     */   extends CoreProxy
/*     */ {
/*     */   public Object getClient() {
/*  45 */     return FMLClientHandler.instance().getClient();
/*     */   }
/*     */ 
/*     */   
/*     */   public World getClientWorld() {
/*  50 */     return (World)(FMLClientHandler.instance().getClient()).field_71441_e;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity entity) {
/*  56 */     super.removeEntity(entity);
/*     */     
/*  58 */     if (entity.field_70170_p.field_72995_K) {
/*  59 */       ((WorldClient)entity.field_70170_p).func_73028_b(entity.func_145782_y());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemDisplayName(ItemStack stack) {
/*  66 */     if (stack.func_77973_b() == null) {
/*  67 */       return "";
/*     */     }
/*     */     
/*  70 */     return stack.func_82833_r();
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeRendering() {
/*  75 */     BuildCraftCore.blockByEntityModel = RenderingRegistry.getNextAvailableRenderId();
/*  76 */     BuildCraftCore.markerModel = RenderingRegistry.getNextAvailableRenderId();
/*  77 */     BuildCraftCore.complexBlockModel = RenderingRegistry.getNextAvailableRenderId();
/*     */     
/*  79 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderingEntityBlocks());
/*  80 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderingMarkers());
/*  81 */     RenderingRegistry.registerBlockHandler(BuildCraftCore.complexBlockModel, (ISimpleBlockRenderingHandler)new RenderBlockComplex());
/*  82 */     ClientRegistry.bindTileEntitySpecialRenderer(TilePathMarker.class, (TileEntitySpecialRenderer)new RenderPathMarker());
/*     */     
/*  84 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEngineBase.class, (TileEntitySpecialRenderer)new RenderEngine());
/*  85 */     for (int i = 0; i < 16; i++) {
/*  86 */       if (BuildCraftCore.engineBlock.hasEngine(i)) {
/*  87 */         TileEngineBase engineTile = (TileEngineBase)BuildCraftCore.engineBlock.createTileEntity(null, i);
/*  88 */         engineTile.field_145854_h = (Block)BuildCraftCore.engineBlock;
/*  89 */         engineTile.field_145847_g = i;
/*  90 */         RenderingEntityBlocks.blockByEntityRenders.put(new RenderingEntityBlocks.EntityRenderIndex((Block)BuildCraftCore.engineBlock, i), new RenderEngine(engineTile));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeEntityRendering() {
/*  97 */     RenderingRegistry.registerEntityRenderingHandler(EntityBlock.class, (Render)RenderEntityBlock.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String playerName() {
/* 103 */     return (FMLClientHandler.instance().getClient()).field_71439_g.getDisplayName();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityBlock newEntityBlock(World world, double i, double j, double k, double iSize, double jSize, double kSize, LaserKind laserKind) {
/* 108 */     EntityBlock eb = super.newEntityBlock(world, i, j, k, iSize, jSize, kSize, laserKind);
/* 109 */     switch (laserKind) {
/*     */       case Blue:
/* 111 */         eb.setTexture(BuildCraftCore.blueLaserTexture);
/*     */         break;
/*     */       
/*     */       case Red:
/* 115 */         eb.setTexture(BuildCraftCore.redLaserTexture);
/*     */         break;
/*     */       
/*     */       case Stripes:
/* 119 */         eb.setTexture(BuildCraftCore.stripesLaserTexture);
/*     */         break;
/*     */     } 
/* 122 */     return eb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayer getPlayerFromNetHandler(INetHandler handler) {
/* 131 */     if (handler instanceof NetHandlerPlayServer) {
/* 132 */       return (EntityPlayer)((NetHandlerPlayServer)handler).field_147369_b;
/*     */     }
/* 134 */     return (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity getServerTile(TileEntity source) {
/* 140 */     if (BuildCraftCore.useServerDataOnClient && Minecraft.func_71410_x().func_71356_B() && (source.func_145831_w()).field_72995_K) {
/* 141 */       WorldServer w = DimensionManager.getWorld((source.func_145831_w()).field_73011_w.field_76574_g);
/* 142 */       if (w != null && w.func_72863_F() != null) {
/* 143 */         Chunk c = w.func_72938_d(source.field_145851_c, source.field_145849_e);
/* 144 */         if (c != null) {
/* 145 */           TileEntity t = c.getTileEntityUnsafe(source.field_145851_c & 0xF, source.field_145848_d, source.field_145849_e & 0xF);
/* 146 */           if (t != null && t.getClass().equals(source.getClass())) {
/* 147 */             return t;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 152 */     return source;
/*     */   }
/*     */   
/*     */   public EntityPlayer getClientPlayer() {
/* 156 */     return (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\proxy\CoreProxyClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */