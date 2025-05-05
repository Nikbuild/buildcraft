/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.transport.gates.ItemGate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GateItemRenderer
/*     */   implements IItemRenderer
/*     */ {
/*  30 */   RenderItem renderItem = new RenderItem();
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack stack, IItemRenderer.ItemRenderType type) {
/*  34 */     return (type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.ENTITY || type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack stack, IItemRenderer.ItemRendererHelper helper) {
/*  42 */     return (helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack stack, Object... data) {
/*  47 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  48 */       render(IItemRenderer.ItemRenderType.INVENTORY, stack);
/*  49 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/*  50 */       if (RenderManager.field_78727_a.field_78733_k.field_74347_j) {
/*  51 */         renderAsEntity(stack, (EntityItem)data[1]);
/*     */       } else {
/*  53 */         renderAsEntityFlat(stack);
/*     */       } 
/*  55 */     } else if (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/*  56 */       renderIn3D(stack);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderIn3D(ItemStack stack) {
/*  61 */     GL11.glPushMatrix();
/*     */     
/*  63 */     renderLayerIn3D(ItemGate.getLogic(stack).getIconItem());
/*  64 */     GL11.glScalef(1.0F, 1.0F, 1.05F);
/*  65 */     renderLayerIn3D(ItemGate.getMaterial(stack).getIconItem());
/*     */     
/*  67 */     for (IGateExpansion expansion : ItemGate.getInstalledExpansions(stack)) {
/*  68 */       renderLayerIn3D(expansion.getOverlayItem());
/*     */     }
/*     */     
/*  71 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void renderLayerIn3D(IIcon icon) {
/*  75 */     if (icon == null) {
/*     */       return;
/*     */     }
/*  78 */     GL11.glPushMatrix();
/*  79 */     Tessellator tessellator = Tessellator.field_78398_a;
/*     */     
/*  81 */     float uv1 = icon.func_94209_e();
/*  82 */     float uv2 = icon.func_94212_f();
/*  83 */     float uv3 = icon.func_94206_g();
/*  84 */     float uv4 = icon.func_94210_h();
/*     */     
/*  86 */     ItemRenderer.func_78439_a(tessellator, uv2, uv3, uv1, uv4, icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
/*  87 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void renderAsEntity(ItemStack stack, EntityItem entity) {
/*  91 */     GL11.glPushMatrix();
/*  92 */     byte iterations = 1;
/*  93 */     if (stack.field_77994_a > 1) {
/*  94 */       iterations = 2;
/*     */     }
/*  96 */     if (stack.field_77994_a > 15) {
/*  97 */       iterations = 3;
/*     */     }
/*  99 */     if (stack.field_77994_a > 31) {
/* 100 */       iterations = 4;
/*     */     }
/*     */     
/* 103 */     Random rand = new Random(187L);
/*     */     
/* 105 */     float offsetZ = 0.084375F;
/*     */     
/* 107 */     GL11.glRotatef(((entity.field_70292_b + 1.0F) / 20.0F + entity.field_70290_d) * 57.295776F, 0.0F, 1.0F, 0.0F);
/* 108 */     GL11.glTranslatef(-0.5F, -0.25F, -(offsetZ * iterations / 2.0F));
/*     */     
/* 110 */     for (int count = 0; count < iterations; count++) {
/* 111 */       if (count > 0) {
/* 112 */         float offsetX = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
/* 113 */         float offsetY = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
/* 114 */         GL11.glTranslatef(offsetX, offsetY, offsetZ);
/*     */       } else {
/* 116 */         GL11.glTranslatef(0.0F, 0.0F, offsetZ);
/*     */       } 
/*     */       
/* 119 */       renderIn3D(stack);
/*     */     } 
/* 121 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void renderAsEntityFlat(ItemStack stack) {
/* 125 */     GL11.glPushMatrix();
/* 126 */     byte iterations = 1;
/* 127 */     if (stack.field_77994_a > 1) {
/* 128 */       iterations = 2;
/*     */     }
/* 130 */     if (stack.field_77994_a > 15) {
/* 131 */       iterations = 3;
/*     */     }
/* 133 */     if (stack.field_77994_a > 31) {
/* 134 */       iterations = 4;
/*     */     }
/*     */     
/* 137 */     Random rand = new Random(187L);
/*     */     
/* 139 */     for (int ii = 0; ii < iterations; ii++) {
/* 140 */       GL11.glPushMatrix();
/* 141 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 142 */       GL11.glRotatef(180.0F - RenderManager.field_78727_a.field_78735_i, 0.0F, 1.0F, 0.0F);
/*     */       
/* 144 */       if (ii > 0) {
/* 145 */         float var12 = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
/* 146 */         float var13 = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
/* 147 */         float var14 = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
/* 148 */         GL11.glTranslatef(var12, var13, var14);
/*     */       } 
/*     */       
/* 151 */       GL11.glTranslatef(0.5F, 0.8F, 0.0F);
/* 152 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 153 */       GL11.glScalef(0.0625F, 0.0625F, 1.0F);
/*     */       
/* 155 */       render(IItemRenderer.ItemRenderType.ENTITY, stack);
/* 156 */       GL11.glPopMatrix();
/*     */     } 
/* 158 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void render(IItemRenderer.ItemRenderType type, ItemStack stack) {
/* 162 */     GL11.glPushMatrix();
/* 163 */     GL11.glDisable(2896);
/* 164 */     GL11.glEnable(3008);
/* 165 */     IIcon icon = ItemGate.getLogic(stack).getIconItem();
/* 166 */     this.renderItem.func_94149_a(0, 0, icon, 16, 16);
/*     */     
/* 168 */     if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 169 */       GL11.glTranslatef(0.0F, 0.0F, -0.01F);
/*     */     }
/*     */     
/* 172 */     icon = ItemGate.getMaterial(stack).getIconItem();
/* 173 */     if (icon != null) {
/* 174 */       this.renderItem.func_94149_a(0, 0, icon, 16, 16);
/*     */     }
/*     */     
/* 177 */     for (IGateExpansion expansion : ItemGate.getInstalledExpansions(stack)) {
/* 178 */       icon = expansion.getOverlayItem();
/* 179 */       if (icon != null) {
/* 180 */         this.renderItem.func_94149_a(0, 0, icon, 16, 16);
/*     */       }
/*     */     } 
/* 183 */     GL11.glEnable(2896);
/* 184 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\GateItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */