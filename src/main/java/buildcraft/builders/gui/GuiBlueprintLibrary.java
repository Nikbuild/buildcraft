/*     */ package buildcraft.builders.gui;
/*     */ 
/*     */ import buildcraft.api.library.LibraryAPI;
/*     */ import buildcraft.builders.TileBlueprintLibrary;
/*     */ import buildcraft.core.blueprints.LibraryId;
/*     */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class GuiBlueprintLibrary
/*     */   extends GuiBuildCraft
/*     */ {
/*  25 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftbuilders:textures/gui/library_rw.png");
/*     */   private GuiButton deleteButton;
/*     */   private TileBlueprintLibrary library;
/*     */   
/*     */   public GuiBlueprintLibrary(EntityPlayer player, TileBlueprintLibrary library) {
/*  30 */     super(new ContainerBlueprintLibrary(player, library), (IInventory)library, TEXTURE);
/*  31 */     this.field_146999_f = 244;
/*  32 */     this.field_147000_g = 220;
/*     */     
/*  34 */     this.library = library;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  40 */     super.func_73866_w_();
/*     */     
/*  42 */     this.deleteButton = new GuiButton(2, this.field_147003_i + 174, this.field_147009_r + 109, 25, 20, StringUtils.localize("gui.del"));
/*  43 */     this.field_146292_n.add(this.deleteButton);
/*     */     
/*  45 */     this.library.refresh();
/*     */     
/*  47 */     checkDelete();
/*     */   }
/*     */   
/*     */   private ContainerBlueprintLibrary getLibraryContainer() {
/*  51 */     return (ContainerBlueprintLibrary)getContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/*  56 */     String title = StringUtils.localize("tile.libraryBlock.name");
/*  57 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/*     */     
/*  59 */     int off = (getLibraryContainer()).scrollbarWidget.getPosition();
/*  60 */     for (int i = off; i < off + 12 && 
/*  61 */       i < this.library.entries.size(); i++) {
/*     */ 
/*     */       
/*  64 */       LibraryId bpt = this.library.entries.get(i);
/*  65 */       String name = bpt.name;
/*     */       
/*  67 */       if (name.length() > 32) {
/*  68 */         name = name.substring(0, 32);
/*     */       }
/*     */       
/*  71 */       if (i == this.library.selected) {
/*  72 */         int l1 = 8;
/*  73 */         int i2 = 22;
/*     */         
/*  75 */         func_73733_a(l1, i2 + 9 * (i - off), l1 + 146, i2 + 9 * (i - off + 1), -2130706433, -2130706433);
/*     */       } 
/*     */       
/*  78 */       while (this.field_146289_q.func_78256_a(name) > 151) {
/*  79 */         name = name.substring(0, name.length() - 1);
/*     */       }
/*     */       
/*  82 */       this.field_146289_q.func_78276_b(name, 9, 23 + 9 * (i - off), LibraryAPI.getHandlerFor(bpt.extension).getTextColor());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/*  88 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  89 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/*     */     
/*  91 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/*  93 */     (getLibraryContainer()).scrollbarWidget.hidden = (this.library.entries.size() <= 12);
/*  94 */     (getLibraryContainer()).scrollbarWidget.setLength(Math.max(0, this.library.entries.size() - 12));
/*     */     
/*  96 */     drawWidgets(x, y);
/*     */     
/*  98 */     int inP = this.library.progressIn * 22 / 100;
/*  99 */     int outP = this.library.progressOut * 22 / 100;
/*     */     
/* 101 */     func_73729_b(this.field_147003_i + 194 + 22 - inP, this.field_147009_r + 57, 256 - inP, 240, inP, 16);
/* 102 */     func_73729_b(this.field_147003_i + 194, this.field_147009_r + 79, 234, 224, outP, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/* 107 */     if (this.deleteButton != null && button == this.deleteButton) {
/* 108 */       this.library.deleteSelectedBpt();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int i, int j, int k) {
/* 114 */     super.func_73864_a(i, j, k);
/*     */     
/* 116 */     int x = i - this.field_147003_i;
/* 117 */     int y = j - this.field_147009_r;
/*     */     
/* 119 */     if (x >= 8 && x <= 161) {
/* 120 */       int ySlot = (y - 22) / 9 + (getLibraryContainer()).scrollbarWidget.getPosition();
/*     */       
/* 122 */       if (ySlot > -1 && ySlot < this.library.entries.size()) {
/* 123 */         this.library.selectBlueprint(ySlot);
/*     */       }
/*     */     } 
/*     */     
/* 127 */     checkDelete();
/*     */   }
/*     */   
/*     */   protected void checkDelete() {
/* 131 */     if (this.library.selected != -1) {
/* 132 */       this.deleteButton.field_146124_l = true;
/*     */     } else {
/* 134 */       this.deleteButton.field_146124_l = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\gui\GuiBlueprintLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */