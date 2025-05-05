/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.items.IList;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.core.list.ListHandlerNew;
/*     */ import buildcraft.core.list.ListHandlerOld;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemList
/*     */   extends ItemBuildCraft
/*     */   implements IList
/*     */ {
/*     */   public ItemList() {
/*  36 */     func_77627_a(true);
/*  37 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_77650_f(ItemStack stack) {
/*  42 */     this.field_77791_bV = this.icons[NBTUtils.getItemData(stack).func_74764_b("written") ? 1 : 0];
/*  43 */     return this.field_77791_bV;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getIconNames() {
/*  48 */     return new String[] { "list/clean", "list/used" };
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/*  53 */     if (!world.field_72995_K) {
/*  54 */       player.openGui(BuildCraftCore.instance, (stack.func_77960_j() == 1) ? 19 : 17, world, 0, 0, 0);
/*     */     }
/*     */     
/*  57 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
/*  62 */     if (stack.func_77960_j() == 0) {
/*  63 */       list.add(EnumChatFormatting.DARK_RED + StatCollector.func_74838_a("tip.deprecated"));
/*     */     }
/*     */     
/*  66 */     NBTTagCompound nbt = NBTUtils.getItemData(stack);
/*  67 */     if (nbt.func_74764_b("label")) {
/*  68 */       list.add(nbt.func_74779_i("label"));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void saveLabel(ItemStack stack, String text) {
/*  73 */     NBTTagCompound nbt = NBTUtils.getItemData(stack);
/*     */     
/*  75 */     nbt.func_74778_a("label", text);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setName(ItemStack stack, String name) {
/*  80 */     saveLabel(stack, name);
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName(ItemStack stack) {
/*  86 */     return getLabel(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel(ItemStack stack) {
/*  91 */     return NBTUtils.getItemData(stack).func_74779_i("label");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(ItemStack stackList, ItemStack item) {
/*  96 */     if (stackList.func_77960_j() == 1) {
/*  97 */       return ListHandlerNew.matches(stackList, item);
/*     */     }
/*  99 */     return ListHandlerOld.matches(stackList, item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> itemList) {
/* 106 */     itemList.add(new ItemStack((Item)this, 1, 0));
/* 107 */     itemList.add(new ItemStack((Item)this, 1, 1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\ItemList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */