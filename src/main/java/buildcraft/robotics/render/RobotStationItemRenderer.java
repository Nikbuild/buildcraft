/*    */ package buildcraft.robotics.render;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.core.lib.render.FakeBlock;
/*    */ import buildcraft.core.lib.render.RenderUtils;
/*    */ import buildcraft.transport.PipeIconProvider;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.RenderBlocks;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RobotStationItemRenderer
/*    */   implements IItemRenderer
/*    */ {
/*    */   private void renderPlugItem(RenderBlocks render, ItemStack item, float translateX, float translateY, float translateZ) {
/* 27 */     FakeBlock block = FakeBlock.INSTANCE;
/* 28 */     Tessellator tessellator = Tessellator.field_78398_a;
/* 29 */     IIcon textureID = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.PipeRobotStation.ordinal());
/*    */     
/* 31 */     GL11.glTranslatef(translateX, translateY, translateZ + 0.25F);
/*    */     
/* 33 */     block.func_149676_a(0.25F, 0.1875F, 0.25F, 0.75F, 0.25F, 0.75F);
/* 34 */     render.func_147775_a((Block)block);
/* 35 */     RenderUtils.drawBlockItem(render, tessellator, (Block)block, textureID);
/*    */     
/* 37 */     block.func_149676_a(0.4325F, 0.25F, 0.4325F, 0.5675F, 0.4375F, 0.5675F);
/* 38 */     render.func_147775_a((Block)block);
/* 39 */     RenderUtils.drawBlockItem(render, tessellator, (Block)block, textureID);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 44 */     switch (type) {
/*    */       case ENTITY:
/* 46 */         return true;
/*    */       case EQUIPPED:
/* 48 */         return true;
/*    */       case EQUIPPED_FIRST_PERSON:
/* 50 */         return true;
/*    */       case INVENTORY:
/* 52 */         return true;
/*    */     } 
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 60 */     return (helper != IItemRenderer.ItemRendererHelper.BLOCK_3D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 65 */     switch (type) {
/*    */       case ENTITY:
/* 67 */         GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 68 */         renderPlugItem((RenderBlocks)data[0], item, -0.6F, 0.0F, -0.6F);
/*    */         break;
/*    */       case EQUIPPED:
/*    */       case EQUIPPED_FIRST_PERSON:
/* 72 */         GL11.glRotatef(70.0F, 0.0F, 0.0F, 1.0F);
/* 73 */         GL11.glRotatef(-55.0F, 1.0F, 0.0F, 0.0F);
/* 74 */         GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 75 */         GL11.glTranslatef(0.0F, -0.6F, -0.4F);
/* 76 */         renderPlugItem((RenderBlocks)data[0], item, 0.0F, 0.0F, 0.0F);
/*    */         break;
/*    */       case INVENTORY:
/* 79 */         GL11.glScalef(1.1F, 1.1F, 1.1F);
/* 80 */         renderPlugItem((RenderBlocks)data[0], item, -0.3F, -0.35F, -0.7F);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\render\RobotStationItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */