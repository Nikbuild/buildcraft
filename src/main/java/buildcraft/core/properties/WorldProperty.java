/*    */ package buildcraft.core.properties;
/*    */ 
/*    */ import buildcraft.api.core.IWorldProperty;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldProperty
/*    */   implements IWorldProperty
/*    */ {
/* 21 */   public HashMap<Integer, DimensionProperty> properties = new HashMap<Integer, DimensionProperty>();
/*    */ 
/*    */   
/*    */   public synchronized boolean get(World world, int x, int y, int z) {
/* 25 */     return getDimension(world).get(x, y, z);
/*    */   }
/*    */   
/*    */   private DimensionProperty getDimension(World world) {
/* 29 */     int id = world.field_73011_w.field_76574_g * 2;
/*    */     
/* 31 */     if (world.field_72995_K) {
/* 32 */       id++;
/*    */     }
/*    */     
/* 35 */     DimensionProperty result = this.properties.get(Integer.valueOf(id));
/*    */     
/* 37 */     if (result == null) {
/* 38 */       result = new DimensionProperty(world, this);
/* 39 */       this.properties.put(Integer.valueOf(id), result);
/*    */     } 
/*    */     
/* 42 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 47 */     for (DimensionProperty p : this.properties.values()) {
/* 48 */       if (p != null) {
/* 49 */         p.clear();
/*    */       }
/*    */     } 
/*    */     
/* 53 */     this.properties.clear();
/*    */   }
/*    */   
/*    */   protected abstract boolean get(IBlockAccess paramIBlockAccess, Block paramBlock, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */