/*     */ package buildcraft.api.core;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
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
/*     */ public class BlockIndex
/*     */   implements Comparable<BlockIndex>
/*     */ {
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public BlockIndex() {}
/*     */   
/*     */   public BlockIndex(int x, int y, int z) {
/*  33 */     this.x = x;
/*  34 */     this.y = y;
/*  35 */     this.z = z;
/*     */   }
/*     */   
/*     */   public BlockIndex(NBTTagCompound c) {
/*  39 */     this.x = c.func_74762_e("i");
/*  40 */     this.y = c.func_74762_e("j");
/*  41 */     this.z = c.func_74762_e("k");
/*     */   }
/*     */   
/*     */   public BlockIndex(Entity entity) {
/*  45 */     this.x = (int)Math.floor(entity.field_70165_t);
/*  46 */     this.y = (int)Math.floor(entity.field_70163_u);
/*  47 */     this.z = (int)Math.floor(entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public BlockIndex(TileEntity entity) {
/*  51 */     this(entity.field_145851_c, entity.field_145848_d, entity.field_145849_e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(BlockIndex o) {
/*  59 */     if (o.x < this.x)
/*  60 */       return 1; 
/*  61 */     if (o.x > this.x)
/*  62 */       return -1; 
/*  63 */     if (o.z < this.z)
/*  64 */       return 1; 
/*  65 */     if (o.z > this.z)
/*  66 */       return -1; 
/*  67 */     if (o.y < this.y)
/*  68 */       return 1; 
/*  69 */     if (o.y > this.y) {
/*  70 */       return -1;
/*     */     }
/*  72 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(NBTTagCompound c) {
/*  77 */     c.func_74768_a("i", this.x);
/*  78 */     c.func_74768_a("j", this.y);
/*  79 */     c.func_74768_a("k", this.z);
/*     */   }
/*     */   
/*     */   public Block getBlock(World world) {
/*  83 */     return world.func_147439_a(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  88 */     return "{" + this.x + ", " + this.y + ", " + this.z + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  93 */     if (obj instanceof BlockIndex) {
/*  94 */       BlockIndex b = (BlockIndex)obj;
/*     */       
/*  96 */       return (b.x == this.x && b.y == this.y && b.z == this.z);
/*     */     } 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return (this.x * 37 + this.y) * 37 + this.z;
/*     */   }
/*     */   
/*     */   public boolean nextTo(BlockIndex blockIndex) {
/* 108 */     return ((Math.abs(blockIndex.x - this.x) <= 1 && blockIndex.y == this.y && blockIndex.z == this.z) || (blockIndex.x == this.x && 
/* 109 */       Math.abs(blockIndex.y - this.y) <= 1 && blockIndex.z == this.z) || (blockIndex.x == this.x && blockIndex.y == this.y && 
/* 110 */       Math.abs(blockIndex.z - this.z) <= 1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\core\BlockIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */