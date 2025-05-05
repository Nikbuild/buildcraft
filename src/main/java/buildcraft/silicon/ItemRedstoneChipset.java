/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import buildcraft.BuildCraftSilicon;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import cpw.mods.fml.common.registry.GameRegistry;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraftforge.oredict.OreDictionary;
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
/*    */ public class ItemRedstoneChipset
/*    */   extends ItemBuildCraft
/*    */ {
/*    */   public enum Chipset
/*    */   {
/* 32 */     RED,
/* 33 */     IRON,
/* 34 */     GOLD,
/* 35 */     DIAMOND,
/* 36 */     PULSATING,
/* 37 */     QUARTZ,
/* 38 */     COMP,
/* 39 */     EMERALD;
/* 40 */     public static final Chipset[] VALUES = values();
/*    */     private IIcon icon;
/*    */     
/*    */     public String getChipsetName() {
/* 44 */       return "redstone_" + name().toLowerCase(Locale.ENGLISH) + "_chipset";
/*    */     } static {
/*    */     
/*    */     } public ItemStack getStack() {
/* 48 */       return getStack(1);
/*    */     }
/*    */     
/*    */     public ItemStack getStack(int qty) {
/* 52 */       return new ItemStack((Item)BuildCraftSilicon.redstoneChipset, qty, ordinal());
/*    */     }
/*    */     
/*    */     public static Chipset fromOrdinal(int ordinal) {
/* 56 */       if (ordinal < 0 || ordinal >= VALUES.length) {
/* 57 */         return RED;
/*    */       }
/* 59 */       return VALUES[ordinal];
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemRedstoneChipset() {
/* 65 */     func_77627_a(true);
/* 66 */     func_77656_e(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public IIcon func_77617_a(int damage) {
/* 71 */     return (Chipset.fromOrdinal(damage)).icon;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack stack) {
/* 76 */     return "item." + Chipset.fromOrdinal(stack.func_77960_j()).getChipsetName();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> itemList) {
/* 83 */     for (Chipset chipset : Chipset.VALUES) {
/* 84 */       itemList.add(chipset.getStack());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {
/* 91 */     for (Chipset chipset : Chipset.VALUES) {
/* 92 */       chipset.icon = par1IconRegister.func_94245_a("buildcraftsilicon:chipset/" + chipset.getChipsetName());
/*    */     }
/*    */   }
/*    */   
/*    */   public void registerItemStacks() {
/* 97 */     for (Chipset chipset : Chipset.VALUES) {
/* 98 */       GameRegistry.registerCustomItemStack(chipset.getChipsetName(), chipset.getStack());
/* 99 */       OreDictionary.registerOre("chipset" + chipset.name().toUpperCase().substring(0, 1) + chipset.name().toLowerCase().substring(1), chipset.getStack());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\ItemRedstoneChipset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */