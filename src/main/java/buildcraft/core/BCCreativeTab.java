/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.lib.utils.Utils;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BCCreativeTab
/*    */   extends CreativeTabs
/*    */ {
/* 22 */   private static final Map<String, BCCreativeTab> tabs = new HashMap<String, BCCreativeTab>();
/*    */   
/*    */   private ItemStack icon;
/*    */   
/*    */   public BCCreativeTab(String name) {
/* 27 */     super("buildcraft." + name);
/*    */     
/* 29 */     tabs.put(name, this);
/*    */   }
/*    */   
/*    */   public static boolean isPresent(String name) {
/* 33 */     return tabs.containsKey(name);
/*    */   }
/*    */   
/*    */   public static BCCreativeTab get(String name) {
/* 37 */     BCCreativeTab tab = tabs.get(name);
/* 38 */     if (tab == null) {
/* 39 */       tab = new BCCreativeTab(name);
/* 40 */       tabs.put(name, tab);
/*    */     } 
/* 42 */     return tab;
/*    */   }
/*    */   
/*    */   public static BCCreativeTab getIfPresent(String name) {
/* 46 */     return tabs.get(name);
/*    */   }
/*    */   
/*    */   public void setIcon(ItemStack icon) {
/* 50 */     if (!Utils.isRegistered(icon)) {
/*    */ 
/*    */       
/* 53 */       icon = new ItemStack(BuildCraftCore.wrenchItem, 1);
/* 54 */       if (!Utils.isRegistered(icon)) {
/* 55 */         icon = new ItemStack(Blocks.field_150336_V, 1);
/*    */       }
/*    */     } 
/* 58 */     this.icon = icon;
/*    */   }
/*    */   
/*    */   private ItemStack getItem() {
/* 62 */     if (this.icon == null)
/*    */     {
/* 64 */       setIcon(null);
/*    */     }
/* 66 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_151244_d() {
/* 71 */     return getItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public Item func_78016_d() {
/* 76 */     return getItem().func_77973_b();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BCCreativeTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */