/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.core.lib.utils.XorShift128Random;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ 
/*     */ public class BlockSpring
/*     */   extends Block
/*     */ {
/*  30 */   public static final XorShift128Random rand = new XorShift128Random();
/*     */   
/*     */   public enum EnumSpring {
/*  33 */     WATER(5, -1, Blocks.field_150355_j),
/*  34 */     OIL(6000, 8, null);
/*  35 */     static {  } public static final EnumSpring[] VALUES = values(); public boolean canGen = true;
/*     */     public final int tickRate;
/*     */     public final int chance;
/*     */     public Block liquidBlock;
/*     */     
/*     */     EnumSpring(int tickRate, int chance, Block liquidBlock) {
/*  41 */       this.tickRate = tickRate;
/*  42 */       this.chance = chance;
/*  43 */       this.liquidBlock = liquidBlock;
/*     */     }
/*     */     
/*     */     public static EnumSpring fromMeta(int meta) {
/*  47 */       if (meta < 0 || meta >= VALUES.length) {
/*  48 */         return WATER;
/*     */       }
/*  50 */       return VALUES[meta];
/*     */     }
/*     */   }
/*     */   
/*     */   public BlockSpring() {
/*  55 */     super(Material.field_151576_e);
/*  56 */     func_149722_s();
/*  57 */     func_149752_b(6000000.0F);
/*  58 */     func_149672_a(field_149769_e);
/*     */     
/*  60 */     func_149649_H();
/*  61 */     func_149675_a(true);
/*  62 */     func_149647_a(BCCreativeTab.get("main"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149666_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/*  67 */     for (EnumSpring type : EnumSpring.VALUES) {
/*  68 */       list.add(new ItemStack(this, 1, type.ordinal()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149692_a(int meta) {
/*  74 */     return meta;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149674_a(World world, int x, int y, int z, Random random) {
/*  79 */     assertSpring(world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149726_b(World world, int x, int y, int z) {
/*  88 */     super.func_149726_b(world, x, y, z);
/*  89 */     int meta = world.func_72805_g(x, y, z);
/*  90 */     world.func_147464_a(x, y, z, this, (EnumSpring.fromMeta(meta)).tickRate);
/*     */   }
/*     */   
/*     */   private void assertSpring(World world, int x, int y, int z) {
/*  94 */     int meta = world.func_72805_g(x, y, z);
/*  95 */     EnumSpring spring = EnumSpring.fromMeta(meta);
/*  96 */     world.func_147464_a(x, y, z, this, spring.tickRate);
/*  97 */     if (!spring.canGen || spring.liquidBlock == null) {
/*     */       return;
/*     */     }
/* 100 */     if (!world.func_147437_c(x, y + 1, z)) {
/*     */       return;
/*     */     }
/* 103 */     if (spring.chance != -1 && rand.nextInt(spring.chance) != 0) {
/*     */       return;
/*     */     }
/* 106 */     world.func_147449_b(x, y + 1, z, spring.liquidBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149698_L() {
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister par1IconRegister) {
/* 118 */     this.field_149761_L = par1IconRegister.func_94245_a("bedrock");
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\BlockSpring.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */