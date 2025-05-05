/*    */ package buildcraft.api.enums;
/*    */ 
/*    */ import buildcraft.api.properties.BuildCraftProperties;
/*    */ import java.util.Locale;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumSpring
/*    */   implements IStringSerializable
/*    */ {
/* 14 */   WATER(5, -1, Blocks.field_150355_j.func_176223_P()),
/* 15 */   OIL(6000, 8, null);
/*    */   static {
/* 17 */     VALUES = values();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canGen = true;
/*    */   
/* 24 */   private final String lowerCaseName = name().toLowerCase(Locale.ROOT); public static final EnumSpring[] VALUES; public final int tickRate;
/*    */   
/*    */   EnumSpring(int tickRate, int chance, IBlockState liquidBlock) {
/* 27 */     this.tickRate = tickRate;
/* 28 */     this.chance = chance;
/* 29 */     this.liquidBlock = liquidBlock;
/*    */   }
/*    */   public final int chance; public IBlockState liquidBlock; public Supplier<TileEntity> tileConstructor;
/*    */   public static EnumSpring fromState(IBlockState state) {
/* 33 */     return (EnumSpring)state.func_177229_b(BuildCraftProperties.SPRING_TYPE);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_176610_l() {
/* 38 */     return this.lowerCaseName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumSpring.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */