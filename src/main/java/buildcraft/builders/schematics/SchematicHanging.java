/*     */ package buildcraft.builders.schematics;
/*     */ 
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.SchematicEntity;
/*     */ import buildcraft.api.blueprints.Translation;
/*     */ import buildcraft.api.core.Position;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
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
/*     */ public class SchematicHanging
/*     */   extends SchematicEntity
/*     */ {
/*     */   private Item baseItem;
/*     */   
/*     */   public SchematicHanging(Item baseItem) {
/*  28 */     this.baseItem = baseItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToBlueprint(Translation transform) {
/*  33 */     super.translateToBlueprint(transform);
/*     */     
/*  35 */     Position pos = new Position(this.entityNBT.func_74762_e("TileX"), this.entityNBT.func_74762_e("TileY"), this.entityNBT.func_74762_e("TileZ"));
/*  36 */     pos = transform.translate(pos);
/*  37 */     this.entityNBT.func_74768_a("TileX", (int)pos.x);
/*  38 */     this.entityNBT.func_74768_a("TileY", (int)pos.y);
/*  39 */     this.entityNBT.func_74768_a("TileZ", (int)pos.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void translateToWorld(Translation transform) {
/*  44 */     super.translateToWorld(transform);
/*     */     
/*  46 */     Position pos = new Position(this.entityNBT.func_74762_e("TileX"), this.entityNBT.func_74762_e("TileY"), this.entityNBT.func_74762_e("TileZ"));
/*  47 */     pos = transform.translate(pos);
/*  48 */     this.entityNBT.func_74768_a("TileX", (int)pos.x);
/*  49 */     this.entityNBT.func_74768_a("TileY", (int)pos.y);
/*  50 */     this.entityNBT.func_74768_a("TileZ", (int)pos.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotateLeft(IBuilderContext context) {
/*  55 */     super.rotateLeft(context);
/*     */     
/*  57 */     Position pos = new Position(this.entityNBT.func_74762_e("TileX"), this.entityNBT.func_74762_e("TileY"), this.entityNBT.func_74762_e("TileZ"));
/*  58 */     pos = context.rotatePositionLeft(pos);
/*  59 */     this.entityNBT.func_74768_a("TileX", (int)pos.x);
/*  60 */     this.entityNBT.func_74768_a("TileY", (int)pos.y);
/*  61 */     this.entityNBT.func_74768_a("TileZ", (int)pos.z);
/*     */     
/*  63 */     int direction = this.entityNBT.func_74771_c("Direction");
/*  64 */     direction = (direction < 3) ? (direction + 1) : 0;
/*  65 */     this.entityNBT.func_74768_a("Direction", direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromWorld(IBuilderContext context, Entity entity) {
/*  70 */     super.readFromWorld(context, entity);
/*     */     
/*  72 */     if (this.baseItem == Items.field_151160_bD) {
/*  73 */       NBTTagCompound tag = this.entityNBT.func_74775_l("Item");
/*  74 */       ItemStack stack = ItemStack.func_77949_a(tag);
/*     */       
/*  76 */       if (stack != null) {
/*  77 */         this.storedRequirements = new ItemStack[2];
/*  78 */         this.storedRequirements[0] = new ItemStack(this.baseItem);
/*  79 */         this.storedRequirements[1] = stack;
/*     */       } else {
/*  81 */         this.storedRequirements = new ItemStack[1];
/*  82 */         this.storedRequirements[0] = new ItemStack(this.baseItem);
/*     */       } 
/*     */     } else {
/*  85 */       this.storedRequirements = new ItemStack[1];
/*  86 */       this.storedRequirements[0] = new ItemStack(this.baseItem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuilt(IBuilderContext context) {
/*  92 */     Position newPosition = new Position(this.entityNBT.func_74762_e("TileX"), this.entityNBT.func_74762_e("TileY"), this.entityNBT.func_74762_e("TileZ"));
/*     */     
/*  94 */     int dir = this.entityNBT.func_74762_e("Direction");
/*     */     
/*  96 */     for (Object o : (context.world()).field_72996_f) {
/*  97 */       Entity e = (Entity)o;
/*     */       
/*  99 */       if (e instanceof EntityHanging) {
/* 100 */         EntityHanging h = (EntityHanging)e;
/* 101 */         Position existingPositon = new Position(h.field_146063_b, h.field_146064_c, h.field_146062_d);
/*     */         
/* 103 */         if (existingPositon.isClose(newPosition, 0.1F) && dir == ((EntityHanging)e).field_82332_a) {
/* 104 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicHanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */