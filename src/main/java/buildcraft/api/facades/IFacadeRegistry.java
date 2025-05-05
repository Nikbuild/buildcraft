/*    */ package buildcraft.api.facades;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFacadeRegistry
/*    */ {
/*    */   Collection<? extends IFacadeState> getValidFacades();
/*    */   
/*    */   IFacadePhasedState createPhasedState(IFacadeState paramIFacadeState, boolean paramBoolean, @Nullable EnumDyeColor paramEnumDyeColor);
/*    */   
/*    */   IFacade createPhasedFacade(IFacadePhasedState[] paramArrayOfIFacadePhasedState);
/*    */   
/*    */   default IFacade createBasicFacade(IFacadeState state, boolean isHollow) {
/* 18 */     return createPhasedFacade(new IFacadePhasedState[] { createPhasedState(state, isHollow, null) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\facades\IFacadeRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */