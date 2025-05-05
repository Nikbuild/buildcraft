/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.core.Box;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class BlockScanner
/*     */   implements Iterable<BlockIndex>
/*     */ {
/*  21 */   Box box = new Box();
/*     */   
/*     */   World world;
/*     */   
/*     */   int x;
/*  26 */   int blocksDone = 0; int y;
/*     */   int z;
/*     */   int iterationsPerCycle;
/*     */   
/*  30 */   class BlockIt implements Iterator<BlockIndex> { int it = 0;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/*  34 */       return (BlockScanner.this.z <= BlockScanner.this.box.zMax && this.it <= BlockScanner.this.iterationsPerCycle);
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockIndex next() {
/*  39 */       BlockIndex index = new BlockIndex(BlockScanner.this.x, BlockScanner.this.y, BlockScanner.this.z);
/*  40 */       this.it++;
/*  41 */       BlockScanner.this.blocksDone++;
/*     */       
/*  43 */       if (BlockScanner.this.x < BlockScanner.this.box.xMax) {
/*  44 */         BlockScanner.this.x++;
/*     */       } else {
/*  46 */         BlockScanner.this.x = BlockScanner.this.box.xMin;
/*     */         
/*  48 */         if (BlockScanner.this.y < BlockScanner.this.box.yMax) {
/*  49 */           BlockScanner.this.y++;
/*     */         } else {
/*  51 */           BlockScanner.this.y = BlockScanner.this.box.yMin;
/*     */           
/*  53 */           BlockScanner.this.z++;
/*     */         } 
/*     */       } 
/*     */       
/*  57 */       return index;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {} }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockScanner(Box box, World world, int iterationsPreCycle) {
/*  67 */     this.box = box;
/*  68 */     this.world = world;
/*  69 */     this.iterationsPerCycle = iterationsPreCycle;
/*     */     
/*  71 */     this.x = box.xMin;
/*  72 */     this.y = box.yMin;
/*  73 */     this.z = box.zMin;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockScanner() {}
/*     */ 
/*     */   
/*     */   public Iterator<BlockIndex> iterator() {
/*  81 */     return new BlockIt();
/*     */   }
/*     */   
/*     */   public int totalBlocks() {
/*  85 */     return this.box.sizeX() * this.box.sizeY() * this.box.sizeZ();
/*     */   }
/*     */   
/*     */   public int blocksLeft() {
/*  89 */     return totalBlocks() - this.blocksDone;
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/*  93 */     nbt.func_74768_a("x", this.x);
/*  94 */     nbt.func_74768_a("y", this.y);
/*  95 */     nbt.func_74768_a("z", this.z);
/*  96 */     nbt.func_74768_a("blocksDone", this.blocksDone);
/*  97 */     nbt.func_74768_a("iterationsPerCycle", this.iterationsPerCycle);
/*  98 */     NBTTagCompound boxNBT = new NBTTagCompound();
/*  99 */     this.box.writeToNBT(boxNBT);
/* 100 */     nbt.func_74782_a("box", (NBTBase)boxNBT);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 104 */     this.x = nbt.func_74762_e("x");
/* 105 */     this.y = nbt.func_74762_e("y");
/* 106 */     this.z = nbt.func_74762_e("z");
/* 107 */     this.blocksDone = nbt.func_74762_e("blocksDone");
/* 108 */     this.iterationsPerCycle = nbt.func_74762_e("iterationsPerCycle");
/* 109 */     this.box.initialize(nbt.func_74775_l("box"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\BlockScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */