/*    */ package buildcraft.core.render;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.lib.render.IInventoryRenderer;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderingEntityBlocks
/*    */   extends BCSimpleBlockRenderingHandler
/*    */ {
/* 24 */   public static HashMap<EntityRenderIndex, IInventoryRenderer> blockByEntityRenders = new HashMap<EntityRenderIndex, IInventoryRenderer>();
/*    */   
/*    */   public static class EntityRenderIndex {
/*    */     Block block;
/*    */     int damage;
/*    */     
/*    */     public EntityRenderIndex(Block block, int damage) {
/* 31 */       this.block = block;
/* 32 */       this.damage = damage;
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 37 */       return this.block.hashCode() + this.damage;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 42 */       if (!(o instanceof EntityRenderIndex)) {
/* 43 */         return false;
/*    */       }
/*    */       
/* 46 */       EntityRenderIndex i = (EntityRenderIndex)o;
/*    */       
/* 48 */       return (i.block == this.block && i.damage == this.damage);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
/* 54 */     if (block.func_149645_b() == BuildCraftCore.blockByEntityModel) {
/* 55 */       EntityRenderIndex index = new EntityRenderIndex(block, metadata);
/* 56 */       if (blockByEntityRenders.containsKey(index)) {
/* 57 */         ((IInventoryRenderer)blockByEntityRenders.get(index)).inventoryRender(-0.5D, -0.5D, -0.5D, 0.0F, 0.0F);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
/* 64 */     if (block.func_149645_b() == BuildCraftCore.blockByEntityModel);
/*    */ 
/*    */ 
/*    */     
/* 68 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender3DInInventory(int modelId) {
/* 73 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderId() {
/* 78 */     return BuildCraftCore.blockByEntityModel;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderingEntityBlocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */