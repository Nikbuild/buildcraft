/*    */ package buildcraft.api.filler;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ 
/*    */ public interface IFilledTemplate
/*    */ {
/*    */   BlockPos getSize();
/*    */   
/*    */   default BlockPos getMax() {
/* 12 */     return getSize().func_177973_b((Vec3i)new BlockPos(1, 1, 1));
/*    */   }
/*    */   
/*    */   boolean get(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   void set(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
/*    */   
/*    */   default void setLineX(int fromX, int toX, int y, int z, boolean value) {
/* 20 */     for (int x = fromX; x <= toX; x++) {
/* 21 */       set(x, y, z, value);
/*    */     }
/*    */   }
/*    */   
/*    */   default void setLineY(int x, int fromY, int toY, int z, boolean value) {
/* 26 */     for (int y = fromY; y <= toY; y++) {
/* 27 */       set(x, y, z, value);
/*    */     }
/*    */   }
/*    */   
/*    */   default void setLineZ(int x, int y, int fromZ, int toZ, boolean value) {
/* 32 */     for (int z = fromZ; z <= toZ; z++) {
/* 33 */       set(x, y, z, value);
/*    */     }
/*    */   }
/*    */   
/*    */   default void setAreaYZ(int x, int fromY, int toY, int fromZ, int toZ, boolean value) {
/* 38 */     for (int y = fromY; y <= toY; y++) {
/* 39 */       for (int z = fromZ; z <= toZ; z++) {
/* 40 */         set(x, y, z, value);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   default void setAreaXZ(int fromX, int toX, int y, int fromZ, int toZ, boolean value) {
/* 46 */     for (int x = fromX; x <= toX; x++) {
/* 47 */       for (int z = fromZ; z <= toZ; z++) {
/* 48 */         set(x, y, z, value);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   default void setAreaXY(int fromX, int toX, int fromY, int toY, int z, boolean value) {
/* 54 */     for (int y = fromY; y <= toY; y++) {
/* 55 */       for (int x = fromX; x <= toX; x++) {
/* 56 */         set(x, y, z, value);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   default void setPlaneYZ(int x, boolean value) {
/* 62 */     setAreaYZ(x, 0, getMax().func_177956_o(), 0, getMax().func_177952_p(), value);
/*    */   }
/*    */   
/*    */   default void setPlaneXZ(int y, boolean value) {
/* 66 */     setAreaXZ(0, getMax().func_177958_n(), y, 0, getMax().func_177952_p(), value);
/*    */   }
/*    */   
/*    */   default void setPlaneXY(int z, boolean value) {
/* 70 */     setAreaXY(0, getMax().func_177958_n(), 0, getMax().func_177956_o(), z, value);
/*    */   }
/*    */   
/*    */   default void setAll(boolean value) {
/* 74 */     for (int z = 0; z < getSize().func_177952_p(); z++) {
/* 75 */       for (int y = 0; y < getSize().func_177956_o(); y++) {
/* 76 */         for (int x = 0; x < getSize().func_177958_n(); x++)
/* 77 */           set(x, y, z, value); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\filler\IFilledTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */