/*    */ package buildcraft.transport.gates;
/*    */ 
/*    */ import buildcraft.api.gates.IGateExpansion;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GateExpansionBuildcraft
/*    */   implements IGateExpansion
/*    */ {
/*    */   private final String tag;
/*    */   private IIcon iconBlock;
/*    */   private IIcon iconItem;
/*    */   
/*    */   public GateExpansionBuildcraft(String tag) {
/* 24 */     this.tag = tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUniqueIdentifier() {
/* 29 */     return "buildcraft:" + this.tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 34 */     return StringUtils.localize("gate.expansion." + this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerBlockOverlay(IIconRegister iconRegister) {
/* 39 */     this.iconBlock = iconRegister.func_94245_a("buildcrafttransport:gates/gate_expansion_" + this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerItemOverlay(IIconRegister iconRegister) {
/* 44 */     this.iconItem = iconRegister.func_94245_a("buildcrafttransport:gates/gate_expansion_" + this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getOverlayBlock() {
/* 49 */     return this.iconBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon getOverlayItem() {
/* 54 */     return this.iconItem;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateExpansionBuildcraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */