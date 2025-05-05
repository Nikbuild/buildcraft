/*     */ package buildcraft.builders.schematics;
/*     */ 
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.MappingRegistry;
/*     */ import buildcraft.core.builders.schematics.SchematicBlockFloored;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class SchematicDoor
/*     */   extends SchematicBlockFloored
/*     */ {
/*     */   final ItemStack stack;
/*  24 */   int upperMeta = 0;
/*     */   
/*     */   public SchematicDoor(ItemStack stack) {
/*  27 */     this.stack = stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/*  32 */     if ((this.meta & 0x8) == 0) {
/*  33 */       requirements.add(this.stack.func_77946_l());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotateLeft(IBuilderContext context) {
/*  44 */     this.meta = rotateMeta(this.meta);
/*     */   }
/*     */   
/*     */   private int rotateMeta(int meta) {
/*  48 */     int orientation = meta & 0x3;
/*  49 */     int others = meta - orientation;
/*     */     
/*  51 */     switch (orientation) {
/*     */       case 0:
/*  53 */         return 1 + others;
/*     */       case 1:
/*  55 */         return 2 + others;
/*     */       case 2:
/*  57 */         return 3 + others;
/*     */       case 3:
/*  59 */         return 0 + others;
/*     */     } 
/*     */     
/*  62 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doNotBuild() {
/*  67 */     return ((this.meta & 0x8) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/*  72 */     return (this.block == context.world().func_147439_a(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/*  77 */     context.world().func_147465_d(x, y, z, this.block, this.meta, 3);
/*  78 */     context.world().func_147465_d(x, y + 1, z, this.block, this.upperMeta, 3);
/*     */     
/*  80 */     context.world().func_72921_c(x, y + 1, z, this.upperMeta, 3);
/*  81 */     context.world().func_72921_c(x, y, z, this.meta, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/*  86 */     super.initializeFromObjectAt(context, x, y, z);
/*     */     
/*  88 */     if ((this.meta & 0x8) == 0) {
/*  89 */       this.upperMeta = context.world().func_72805_g(x, y + 1, z);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/*  95 */     super.writeSchematicToNBT(nbt, registry);
/*     */     
/*  97 */     nbt.func_74774_a("upperMeta", (byte)this.upperMeta);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 102 */     super.readSchematicFromNBT(nbt, registry);
/*     */     
/* 104 */     this.upperMeta = nbt.func_74771_c("upperMeta");
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */