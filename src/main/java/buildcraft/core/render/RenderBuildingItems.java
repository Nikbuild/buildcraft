/*     */ package buildcraft.core.render;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.StackAtPosition;
/*     */ import buildcraft.core.builders.BuildingItem;
/*     */ import buildcraft.core.builders.IBuildingItemsProvider;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class RenderBuildingItems
/*     */ {
/*  27 */   private final EntityItem dummyEntityItem = new EntityItem(null);
/*     */   
/*     */   private final RenderItem customRenderItem;
/*     */   
/*     */   private Item buildToolItem;
/*     */   private int buildToolGlList;
/*     */   
/*     */   public RenderBuildingItems() {
/*  35 */     this.customRenderItem = new RenderItem()
/*     */       {
/*     */         public boolean shouldBob() {
/*  38 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean shouldSpreadItems() {
/*  43 */           return false;
/*     */         }
/*     */       };
/*  46 */     this.customRenderItem.func_76976_a(RenderManager.field_78727_a);
/*     */   }
/*     */   
/*     */   public void render(TileEntity tile, double x, double y, double z) {
/*  50 */     IBuildingItemsProvider provider = (IBuildingItemsProvider)tile;
/*  51 */     GL11.glPushMatrix();
/*     */     
/*  53 */     GL11.glTranslated(x, y, z);
/*  54 */     GL11.glTranslated(-tile.field_145851_c, -tile.field_145848_d, -tile.field_145849_e);
/*     */     
/*  56 */     if (provider.getBuilders() != null) {
/*  57 */       for (BuildingItem i : provider.getBuilders()) {
/*  58 */         doRenderItem(i, 1.0F);
/*     */       }
/*     */     }
/*     */     
/*  62 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void renderToList(ItemStack stack, int list) {
/*  66 */     GL11.glNewList(list, 4865);
/*     */     
/*  68 */     float renderScale = 0.7F;
/*  69 */     GL11.glScalef(renderScale, renderScale, renderScale);
/*  70 */     this.dummyEntityItem.func_92058_a(stack);
/*  71 */     this.customRenderItem.func_76986_a(this.dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/*  72 */     GL11.glEndList();
/*     */   }
/*     */   
/*     */   private void doRenderItem(BuildingItem i, float light) {
/*  76 */     if (this.buildToolItem == null) {
/*  77 */       this.buildToolItem = Item.func_150898_a((Block)BuildCraftCore.buildToolBlock);
/*  78 */       this.buildToolGlList = GL11.glGenLists(1);
/*  79 */       renderToList(new ItemStack(this.buildToolItem), this.buildToolGlList);
/*     */     } 
/*  81 */     if (i == null) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     i.displayUpdate();
/*     */     
/*  87 */     for (StackAtPosition s : i.getStacks()) {
/*  88 */       if (s.display) {
/*  89 */         GL11.glPushMatrix();
/*  90 */         GL11.glTranslatef((float)s.pos.x, (float)s.pos.y + 0.25F, (float)s.pos.z);
/*     */ 
/*     */         
/*  93 */         if (s.stack.func_77973_b() == this.buildToolItem) {
/*  94 */           GL11.glCallList(this.buildToolGlList);
/*  95 */         } else if (!s.generatedListId) {
/*  96 */           s.generatedListId = true;
/*  97 */           s.glListId = GL11.glGenLists(1);
/*  98 */           renderToList(s.stack, s.glListId);
/*     */         } else {
/* 100 */           GL11.glCallList(s.glListId);
/*     */         } 
/*     */         
/* 103 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\render\RenderBuildingItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */