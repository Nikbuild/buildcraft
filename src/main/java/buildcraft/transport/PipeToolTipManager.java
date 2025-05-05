/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.gui.GuiScreen;
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
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public final class PipeToolTipManager
/*    */ {
/* 28 */   private static final Map<Class<? extends Pipe<?>>, String> toolTips = new HashMap<Class<? extends Pipe<?>>, String>();
/*    */   
/*    */   static {
/* 31 */     if (!BuildCraftCore.hidePowerNumbers && !BuildCraftTransport.usePipeLoss) {
/* 32 */       for (Map.Entry<Class<? extends Pipe<?>>, Integer> pipe : PipeTransportPower.powerCapacities.entrySet()) {
/* 33 */         addToolTip(pipe.getKey(), String.format("%d RF/t", new Object[] { pipe.getValue() }));
/*    */       } 
/*    */     }
/*    */     
/* 37 */     if (!BuildCraftCore.hideFluidNumbers) {
/* 38 */       for (Map.Entry<Class<? extends Pipe<?>>, Integer> pipe : PipeTransportFluids.fluidCapacities.entrySet()) {
/* 39 */         addToolTip(pipe.getKey(), String.format("%d mB/t", new Object[] { pipe.getValue() }));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void addTipToList(String tipTag, List<String> tips) {
/* 51 */     if (StringUtils.canLocalize(tipTag)) {
/* 52 */       String localized = StringUtils.localize(tipTag);
/* 53 */       if (localized != null) {
/* 54 */         List<String> lines = StringUtils.newLineSplitter.splitToList(localized);
/* 55 */         tips.addAll(lines);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void addToolTip(Class<? extends Pipe<?>> pipe, String toolTip) {
/* 61 */     toolTips.put(pipe, toolTip);
/*    */   }
/*    */   
/*    */   public static List<String> getToolTip(Class<? extends Pipe<?>> pipe, boolean advanced) {
/* 65 */     List<String> tips = new ArrayList<String>();
/* 66 */     addTipToList("tip." + pipe.getSimpleName(), tips);
/*    */     
/* 68 */     String tip = toolTips.get(pipe);
/* 69 */     if (tip != null) {
/* 70 */       tips.add(tip);
/*    */     }
/*    */     
/* 73 */     if (GuiScreen.func_146272_n()) {
/* 74 */       addTipToList("tip.shift." + pipe.getSimpleName(), tips);
/*    */     }
/* 76 */     return tips;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeToolTipManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */