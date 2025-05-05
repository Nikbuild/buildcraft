/*    */ package buildcraft.core.lib.items;
/*    */ 
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.utils.ResourceUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBuildCraft
/*    */   extends Item
/*    */ {
/*    */   public IIcon[] icons;
/*    */   private String iconName;
/*    */   private boolean passSneakClick = false;
/*    */   
/*    */   public ItemBuildCraft() {
/* 30 */     this((CreativeTabs)BCCreativeTab.get("main"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemBuildCraft(CreativeTabs creativeTab) {
/* 36 */     func_77637_a(creativeTab);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item func_77655_b(String par1Str) {
/* 41 */     this.iconName = par1Str;
/* 42 */     return super.func_77655_b(par1Str);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int meta) {
/* 48 */     if (this.field_77791_bV != null) {
/* 49 */       return this.field_77791_bV;
/*    */     }
/* 51 */     if (this.icons != null && this.icons.length > 0) {
/* 52 */       return this.icons[meta % this.icons.length];
/*    */     }
/* 54 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getIconNames() {
/* 59 */     return new String[] { this.iconName };
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {
/* 65 */     String[] names = getIconNames();
/* 66 */     String prefix = ResourceUtils.getObjectPrefix(Item.field_150901_e.func_148750_c(this));
/* 67 */     prefix = prefix.substring(0, prefix.indexOf(":") + 1);
/* 68 */     this.icons = new IIcon[names.length];
/*    */     
/* 70 */     for (int i = 0; i < names.length; i++) {
/* 71 */       this.icons[i] = par1IconRegister.func_94245_a(prefix + names[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   public Item setPassSneakClick(boolean passClick) {
/* 76 */     this.passSneakClick = passClick;
/* 77 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 82 */     return this.passSneakClick;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\items\ItemBuildCraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */