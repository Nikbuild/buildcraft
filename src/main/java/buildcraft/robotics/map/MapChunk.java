/*    */ package buildcraft.robotics.map;
/*    */ 
/*    */ import buildcraft.api.core.BCLog;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ public class MapChunk
/*    */ {
/*    */   private static final int VERSION = 1;
/*    */   private int x;
/*    */   private int z;
/*    */   private byte[] data;
/*    */   
/*    */   public MapChunk(int x, int z) {
/* 17 */     this.x = x;
/* 18 */     this.z = z;
/* 19 */     this.data = new byte[256];
/*    */   }
/*    */   
/*    */   public MapChunk(NBTTagCompound compound) {
/* 23 */     readFromNBT(compound);
/*    */   }
/*    */   
/*    */   public int getX() {
/* 27 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 31 */     return this.z;
/*    */   }
/*    */   
/*    */   public int getColor(int x, int z) {
/* 35 */     return this.data[(z & 0xF) << 4 | x & 0xF];
/*    */   }
/*    */   
/*    */   public void update(Chunk chunk) {
/* 39 */     for (int bz = 0; bz < 16; bz++) {
/* 40 */       for (int bx = 0; bx < 16; bx++) {
/* 41 */         int y = chunk.func_76611_b(bx, bz);
/* 42 */         int color = MapColor.field_151660_b.field_76290_q;
/*    */         
/* 44 */         if (y < 0) {
/* 45 */           y = 255;
/*    */         }
/*    */ 
/*    */ 
/*    */         
/* 50 */         while (y >= 0) {
/* 51 */           Block b = chunk.func_150810_a(bx, y, bz);
/* 52 */           color = (b.func_149728_f(0) != null) ? (b.func_149728_f(0)).field_76290_q : MapColor.field_151660_b.field_76290_q;
/* 53 */           if (color != MapColor.field_151660_b.field_76290_q) {
/*    */             break;
/*    */           }
/* 56 */           y--;
/*    */         } 
/*    */         
/* 59 */         this.data[bz << 4 | bx] = (byte)color;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 65 */     int version = compound.func_74765_d("version");
/* 66 */     if (version > 1) {
/* 67 */       BCLog.logger.error("Unsupported MapChunk version: " + version);
/*    */       return;
/*    */     } 
/* 70 */     this.x = compound.func_74762_e("x");
/* 71 */     this.z = compound.func_74762_e("z");
/* 72 */     this.data = compound.func_74770_j("data");
/* 73 */     if (this.data.length != 256) {
/* 74 */       BCLog.logger.error("Invalid MapChunk data length: " + this.data.length);
/* 75 */       this.data = new byte[256];
/*    */     } 
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 80 */     compound.func_74777_a("version", (short)1);
/* 81 */     compound.func_74768_a("x", this.x);
/* 82 */     compound.func_74768_a("z", this.z);
/* 83 */     compound.func_74773_a("data", this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 88 */     return 31 * this.x + this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\map\MapChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */