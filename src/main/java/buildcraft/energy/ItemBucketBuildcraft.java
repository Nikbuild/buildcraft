/*    */ package buildcraft.energy;
/*    */ 
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBucket;
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
/*    */ public class ItemBucketBuildcraft
/*    */   extends ItemBucket
/*    */ {
/*    */   private String iconName;
/*    */   
/*    */   public ItemBucketBuildcraft(Block block) {
/* 28 */     this(block, (CreativeTabs)BCCreativeTab.get("main"));
/*    */   }
/*    */   
/*    */   public ItemBucketBuildcraft(Block block, CreativeTabs creativeTab) {
/* 32 */     super(block);
/* 33 */     func_77642_a(Items.field_151133_ar);
/* 34 */     func_77637_a(creativeTab);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item func_77655_b(String par1Str) {
/* 39 */     this.iconName = par1Str;
/* 40 */     return super.func_77655_b(par1Str);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {
/* 46 */     this.field_77791_bV = par1IconRegister.func_94245_a("buildcraftenergy:" + this.iconName);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\ItemBucketBuildcraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */