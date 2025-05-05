/*     */ package buildcraft.api.blueprints;
/*     */ 
/*     */ import buildcraft.api.core.JavaTools;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchematicTile
/*     */   extends SchematicBlock
/*     */ {
/*  29 */   public NBTTagCompound tileNBT = new NBTTagCompound();
/*     */ 
/*     */ 
/*     */   
/*     */   public void idsToBlueprint(MappingRegistry registry) {
/*  34 */     registry.scanAndTranslateStacksToRegistry(this.tileNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void idsToWorld(MappingRegistry registry) {
/*     */     try {
/*  40 */       registry.scanAndTranslateStacksToWorld(this.tileNBT);
/*  41 */     } catch (MappingNotFoundException e) {
/*  42 */       this.tileNBT = new NBTTagCompound();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNBTLoaded() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/*  55 */     super.placeInWorld(context, x, y, z, stacks);
/*     */     
/*  57 */     if (this.block.hasTileEntity(this.meta)) {
/*  58 */       this.tileNBT.func_74768_a("x", x);
/*  59 */       this.tileNBT.func_74768_a("y", y);
/*  60 */       this.tileNBT.func_74768_a("z", z);
/*     */       
/*  62 */       context.world().func_147455_a(x, y, z, TileEntity.func_145827_c(this.tileNBT));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/*  68 */     super.initializeFromObjectAt(context, x, y, z);
/*     */     
/*  70 */     if (this.block.hasTileEntity(this.meta)) {
/*  71 */       TileEntity tile = context.world().func_147438_o(x, y, z);
/*     */       
/*  73 */       if (tile != null) {
/*  74 */         tile.func_145841_b(this.tileNBT);
/*     */       }
/*     */       
/*  77 */       this.tileNBT = (NBTTagCompound)this.tileNBT.func_74737_b();
/*  78 */       onNBTLoaded();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {
/*  84 */     super.storeRequirements(context, x, y, z);
/*     */     
/*  86 */     if (this.block.hasTileEntity(this.meta)) {
/*  87 */       TileEntity tile = context.world().func_147438_o(x, y, z);
/*     */       
/*  89 */       if (tile instanceof IInventory) {
/*  90 */         IInventory inv = (IInventory)tile;
/*     */         
/*  92 */         ArrayList<ItemStack> rqs = new ArrayList<ItemStack>();
/*     */         
/*  94 */         for (int i = 0; i < inv.func_70302_i_(); i++) {
/*  95 */           if (inv.func_70301_a(i) != null) {
/*  96 */             rqs.add(inv.func_70301_a(i));
/*     */           }
/*     */         } 
/*     */         
/* 100 */         this.storedRequirements = (ItemStack[])JavaTools.concat((Object[])this.storedRequirements, rqs
/* 101 */             .toArray((Object[])new ItemStack[rqs.size()]));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 108 */     super.writeSchematicToNBT(nbt, registry);
/*     */     
/* 110 */     nbt.func_74782_a("blockCpt", (NBTBase)this.tileNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 115 */     super.readSchematicFromNBT(nbt, registry);
/*     */     
/* 117 */     this.tileNBT = nbt.func_74775_l("blockCpt");
/* 118 */     onNBTLoaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public int buildTime() {
/* 123 */     return 5;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\blueprints\SchematicTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */