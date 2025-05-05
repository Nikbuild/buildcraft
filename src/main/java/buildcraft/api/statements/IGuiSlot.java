/*    */ package buildcraft.api.statements;
/*    */ 
/*    */ import buildcraft.api.core.render.ISprite;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IGuiSlot
/*    */ {
/*    */   String getUniqueTag();
/*    */   
/*    */   String getDescription();
/*    */   
/*    */   default List<String> getTooltip() {
/* 25 */     String desc = getDescription();
/* 26 */     if (desc == null) {
/* 27 */       return (List<String>)ImmutableList.of();
/*    */     }
/* 29 */     return (List<String>)ImmutableList.of(desc);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   @Nullable
/*    */   ISprite getSprite();
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\IGuiSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */