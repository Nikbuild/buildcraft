/*    */ package buildcraft.factory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.IBlockAccess;
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
/*    */ 
/*    */ 
/*    */ public class BlockPlainPipe
/*    */   extends Block
/*    */ {
/*    */   public BlockPlainPipe() {
/* 30 */     super(Material.field_151592_s);
/*    */     
/* 32 */     this.field_149759_B = 0.25D;
/* 33 */     this.field_149760_C = 0.0D;
/* 34 */     this.field_149754_D = 0.25D;
/*    */     
/* 36 */     this.field_149755_E = 0.75D;
/* 37 */     this.field_149756_F = 1.0D;
/* 38 */     this.field_149757_G = 0.75D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149662_c() {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149721_r() {
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_149666_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/* 59 */     list.add(new ItemStack(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public Item func_149650_a(int i, Random random, int j) {
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/* 69 */     return new ArrayList<ItemStack>();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_149651_a(IIconRegister register) {
/* 79 */     this.field_149761_L = register.func_94245_a("buildcraftfactory:plainPipeBlock/default");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockPlainPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */