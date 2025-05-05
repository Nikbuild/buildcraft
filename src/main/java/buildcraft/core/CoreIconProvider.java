/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
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
/*    */ 
/*    */ public class CoreIconProvider
/*    */   implements IIconProvider
/*    */ {
/* 21 */   public static int ENERGY = 0;
/*    */   
/* 23 */   public static int MAX = 1;
/*    */   
/*    */   private IIcon[] icons;
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon(int iconIndex) {
/* 30 */     return this.icons[iconIndex];
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 36 */     this.icons = new IIcon[MAX];
/*    */     
/* 38 */     this.icons[ENERGY] = iconRegister.func_94245_a("buildcraftcore:icons/energy");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\CoreIconProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */