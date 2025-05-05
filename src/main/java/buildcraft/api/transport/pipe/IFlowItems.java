/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.core.IStackFilter;
/*    */ import buildcraft.api.transport.IInjectable;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFlowItems
/*    */   extends IInjectable
/*    */ {
/*    */   @Deprecated
/*    */   default int tryExtractItems(int count, EnumFacing from, @Nullable EnumDyeColor colour, IStackFilter filter) {
/* 18 */     return tryExtractItems(count, from, colour, filter, false);
/*    */   }
/*    */   
/*    */   int tryExtractItems(int paramInt, EnumFacing paramEnumFacing, @Nullable EnumDyeColor paramEnumDyeColor, IStackFilter paramIStackFilter, boolean paramBoolean);
/*    */   
/*    */   void insertItemsForce(@Nonnull ItemStack paramItemStack, EnumFacing paramEnumFacing, @Nullable EnumDyeColor paramEnumDyeColor, double paramDouble);
/*    */   
/*    */   void sendPhantomItem(@Nonnull ItemStack paramItemStack, @Nullable EnumFacing paramEnumFacing1, @Nullable EnumFacing paramEnumFacing2, @Nullable EnumDyeColor paramEnumDyeColor);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\IFlowItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */