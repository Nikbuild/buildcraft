/*    */ package buildcraft.api.transport;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public enum EnumWirePart
/*    */ {
/*  9 */   EAST_UP_SOUTH(true, true, true),
/* 10 */   EAST_UP_NORTH(true, true, false),
/* 11 */   EAST_DOWN_SOUTH(true, false, true),
/* 12 */   EAST_DOWN_NORTH(true, false, false),
/* 13 */   WEST_UP_SOUTH(false, true, true),
/* 14 */   WEST_UP_NORTH(false, true, false),
/* 15 */   WEST_DOWN_SOUTH(false, false, true),
/* 16 */   WEST_DOWN_NORTH(false, false, false);
/*    */   static {
/* 18 */     VALUES = values();
/*    */   }
/*    */   
/*    */   public static final EnumWirePart[] VALUES;
/*    */   public final EnumFacing.AxisDirection x;
/*    */   public final EnumFacing.AxisDirection y;
/*    */   public final EnumFacing.AxisDirection z;
/*    */   public final AxisAlignedBB boundingBox;
/*    */   public final AxisAlignedBB boundingBoxPossible;
/*    */   
/*    */   EnumWirePart(boolean x, boolean y, boolean z) {
/* 29 */     this.x = x ? EnumFacing.AxisDirection.POSITIVE : EnumFacing.AxisDirection.NEGATIVE;
/* 30 */     this.y = y ? EnumFacing.AxisDirection.POSITIVE : EnumFacing.AxisDirection.NEGATIVE;
/* 31 */     this.z = z ? EnumFacing.AxisDirection.POSITIVE : EnumFacing.AxisDirection.NEGATIVE;
/* 32 */     double x1 = this.x.func_179524_a() * 0.3125D + 0.5D;
/* 33 */     double y1 = this.y.func_179524_a() * 0.3125D + 0.5D;
/* 34 */     double z1 = this.z.func_179524_a() * 0.3125D + 0.5D;
/* 35 */     double x2 = this.x.func_179524_a() * 0.25D + 0.5D;
/* 36 */     double y2 = this.y.func_179524_a() * 0.25D + 0.5D;
/* 37 */     double z2 = this.z.func_179524_a() * 0.25D + 0.5D;
/* 38 */     this.boundingBox = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
/*    */     
/* 40 */     Vec3d center = new Vec3d(0.5D, 0.5D, 0.5D);
/* 41 */     Vec3d edge = new Vec3d(x ? 0.75D : 0.25D, y ? 0.75D : 0.25D, z ? 0.75D : 0.25D);
/* 42 */     this.boundingBoxPossible = new AxisAlignedBB(center.field_72450_a, center.field_72448_b, center.field_72449_c, edge.field_72450_a, edge.field_72448_b, edge.field_72449_c);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumFacing.AxisDirection getDirection(EnumFacing.Axis axis) {
/* 47 */     switch (axis) {
/*    */       case X:
/* 49 */         return this.x;
/*    */       case Y:
/* 51 */         return this.y;
/*    */       case Z:
/* 53 */         return this.z;
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static EnumWirePart get(int x, int y, int z) {
/* 60 */     boolean bx = ((x % 2 + 2) % 2 == 1);
/* 61 */     boolean by = ((y % 2 + 2) % 2 == 1);
/* 62 */     boolean bz = ((z % 2 + 2) % 2 == 1);
/* 63 */     return get(bx, by, bz);
/*    */   }
/*    */   
/*    */   public static EnumWirePart get(boolean x, boolean y, boolean z) {
/* 67 */     if (x) {
/* 68 */       if (y) {
/* 69 */         return z ? EAST_UP_SOUTH : EAST_UP_NORTH;
/*    */       }
/* 71 */       return z ? EAST_DOWN_SOUTH : EAST_DOWN_NORTH;
/*    */     } 
/*    */     
/* 74 */     if (y) {
/* 75 */       return z ? WEST_UP_SOUTH : WEST_UP_NORTH;
/*    */     }
/* 77 */     return z ? WEST_DOWN_SOUTH : WEST_DOWN_NORTH;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\EnumWirePart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */