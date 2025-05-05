/*    */ package buildcraft.silicon;
/*    */ 
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import buildcraft.silicon.render.PackageFontRenderer;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemPackage
/*    */   extends ItemBuildCraft
/*    */ {
/*    */   public static final class DispenseBehaviour
/*    */     extends BehaviorDefaultDispenseItem {
/*    */     public ItemStack func_82487_b(IBlockSource source, ItemStack stack) {
/* 28 */       if (stack != null && stack.func_77973_b() instanceof ItemPackage) {
/* 29 */         World world = source.func_82618_k();
/* 30 */         EnumFacing enumfacing = BlockDispenser.func_149937_b(source.func_82620_h());
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 35 */         EntityPackage entityPackage = new EntityPackage(source.func_82618_k(), source.func_82615_a() + enumfacing.func_82601_c(), source.func_82617_b() + enumfacing.func_96559_d(), source.func_82616_c() + enumfacing.func_82599_e(), stack.func_77946_l());
/* 36 */         entityPackage.func_70186_c(enumfacing.func_82601_c(), (enumfacing.func_96559_d() + 0.1F), enumfacing.func_82599_e(), 1.1F, 6.0F);
/* 37 */         world.func_72838_d((Entity)entityPackage);
/* 38 */         stack.func_77979_a(1);
/*    */       } 
/* 40 */       return stack;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemPackage() {
/* 46 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs tab, List list) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public static void update(ItemStack stack) {}
/*    */ 
/*    */   
/*    */   public static ItemStack getStack(ItemStack stack, int slot) {
/* 60 */     NBTTagCompound tag = NBTUtils.getItemData(stack);
/* 61 */     if (tag.func_74764_b("item" + slot)) {
/* 62 */       return ItemStack.func_77949_a(tag.func_74775_l("item" + slot));
/*    */     }
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 70 */     world.func_72956_a((Entity)player, "random.bow", 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 72 */     if (!world.field_72995_K) {
/* 73 */       world.func_72838_d((Entity)new EntityPackage(world, player, stack.func_77946_l()));
/*    */     }
/*    */     
/* 76 */     if (!player.field_71075_bZ.field_75098_d) {
/* 77 */       stack.field_77994_a--;
/*    */     }
/*    */     
/* 80 */     return stack;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public FontRenderer getFontRenderer(ItemStack stack) {
/* 86 */     return (FontRenderer)new PackageFontRenderer(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> strings, boolean adv) {
/* 92 */     NBTTagCompound tag = NBTUtils.getItemData(stack);
/* 93 */     if (!tag.func_82582_d()) {
/* 94 */       strings.add("{{BC_PACKAGE_SPECIAL:0}}");
/* 95 */       strings.add("{{BC_PACKAGE_SPECIAL:1}}");
/* 96 */       strings.add("{{BC_PACKAGE_SPECIAL:2}}");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\ItemPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */