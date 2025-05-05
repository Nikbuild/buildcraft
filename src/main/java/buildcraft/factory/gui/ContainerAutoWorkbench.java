/*     */ package buildcraft.factory.gui;
/*     */ 
/*     */ import buildcraft.core.lib.gui.BuildCraftContainer;
/*     */ import buildcraft.core.lib.gui.slots.SlotOutput;
/*     */ import buildcraft.core.lib.gui.slots.SlotUntouchable;
/*     */ import buildcraft.factory.TileAutoWorkbench;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerAutoWorkbench
/*     */   extends BuildCraftContainer
/*     */ {
/*     */   public IInventory craftResult;
/*     */   private final TileAutoWorkbench tile;
/*     */   private int lastProgress;
/*     */   private ItemStack prevOutput;
/*     */   
/*     */   public ContainerAutoWorkbench(InventoryPlayer inventoryplayer, TileAutoWorkbench t) {
/*  33 */     super(t.func_70302_i_());
/*     */     
/*  35 */     this.craftResult = (IInventory)new InventoryCraftResult();
/*  36 */     this.tile = t;
/*  37 */     func_75146_a((Slot)new SlotUntouchable(this.craftResult, 0, 93, 27));
/*  38 */     func_75146_a((Slot)new SlotOutput((IInventory)this.tile, 9, 124, 35));
/*  39 */     for (int j = 0; j < 3; j++) {
/*  40 */       for (int k = 0; k < 3; k++) {
/*  41 */         func_75146_a((Slot)new SlotWorkbench((IInventory)this.tile, 10 + k + j * 3, 30 + k * 18, 17 + j * 18));
/*     */       }
/*     */     } 
/*     */     
/*  45 */     for (int i = 0; i < 9; i++) {
/*  46 */       func_75146_a(new Slot((IInventory)this.tile, i, 8 + i * 18, 84));
/*     */     }
/*     */     
/*  49 */     for (int y = 0; y < 3; y++) {
/*  50 */       for (int k = 0; k < 9; k++) {
/*  51 */         func_75146_a(new Slot((IInventory)inventoryplayer, k + y * 9 + 9, 8 + k * 18, 115 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  55 */     for (int x = 0; x < 9; x++) {
/*  56 */       func_75146_a(new Slot((IInventory)inventoryplayer, x, 8 + x * 18, 173));
/*     */     }
/*     */     
/*  59 */     func_75130_a((IInventory)this.tile);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75132_a(ICrafting icrafting) {
/*  64 */     super.func_75132_a(icrafting);
/*  65 */     icrafting.func_71112_a((Container)this, 0, this.tile.progress);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/*  70 */     super.func_75142_b();
/*  71 */     for (Object crafter : this.field_75149_d) {
/*  72 */       ICrafting icrafting = (ICrafting)crafter;
/*     */       
/*  74 */       if (this.lastProgress != this.tile.progress) {
/*  75 */         icrafting.func_71112_a((Container)this, 0, this.tile.progress);
/*     */       }
/*     */     } 
/*     */     
/*  79 */     ItemStack output = this.craftResult.func_70301_a(0);
/*  80 */     if (output != this.prevOutput) {
/*  81 */       this.prevOutput = output;
/*  82 */       func_75130_a((IInventory)this.tile.craftMatrix);
/*     */     } 
/*     */     
/*  85 */     this.lastProgress = this.tile.progress;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_75137_b(int id, int data) {
/*  90 */     switch (id) {
/*     */       case 0:
/*  92 */         this.tile.progress = data;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void func_75130_a(IInventory inv) {
/*  99 */     super.func_75130_a(inv);
/* 100 */     this.tile.craftMatrix.rebuildCache();
/* 101 */     ItemStack output = this.tile.craftMatrix.getRecipeOutput();
/* 102 */     this.craftResult.func_70299_a(0, output);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_75144_a(int i, int j, int modifier, EntityPlayer entityplayer) {
/* 107 */     ItemStack stack = super.func_75144_a(i, j, modifier, entityplayer);
/* 108 */     func_75130_a((IInventory)this.tile.craftMatrix);
/* 109 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 114 */     return this.tile.func_70300_a(entityplayer);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\gui\ContainerAutoWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */