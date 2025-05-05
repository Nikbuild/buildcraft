/*    */ package buildcraft.transport.statements;
/*    */ 
/*    */ import buildcraft.api.core.EnumColor;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatementContainer;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.core.statements.BCStatement;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionExtractionPreset
/*    */   extends BCStatement
/*    */   implements IActionInternal
/*    */ {
/*    */   public final EnumColor color;
/*    */   
/*    */   public ActionExtractionPreset(EnumColor color) {
/* 27 */     super(new String[] { "buildcraft:extraction.preset." + color.getTag(), "buildcraft.extraction.preset." + color.getTag() });
/*    */     
/* 29 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 34 */     return String.format(StringUtils.localize("gate.action.extraction"), new Object[] { this.color.getName() });
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 39 */     this.icon = iconRegister.func_94245_a("buildcrafttransport:triggers/extraction_preset_" + this.color.name().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */   
/*    */   public void actionActivate(IStatementContainer source, IStatementParameter[] parameters) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\ActionExtractionPreset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */