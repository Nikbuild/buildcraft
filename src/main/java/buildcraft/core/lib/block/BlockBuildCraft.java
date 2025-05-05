/*     */ package buildcraft.core.lib.block;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.events.BlockInteractionEvent;
/*     */ import buildcraft.api.tiles.IHasWork;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.utils.ResourceUtils;
/*     */ import buildcraft.core.lib.utils.Utils;
/*     */ import buildcraft.core.lib.utils.XorShift128Random;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockContainer;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockBuildCraft
/*     */   extends BlockContainer
/*     */ {
/*     */   protected static boolean keepInventory = false;
/*  43 */   private static final int[][] SIDE_TEXTURING_LOCATIONS = new int[][] { { 2, 3, 5, 4 }, { 3, 2, 4, 5 }, { 4, 5, 2, 3 }, { 5, 4, 3, 2 } };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon[][] icons;
/*     */ 
/*     */ 
/*     */   
/*  53 */   protected final XorShift128Random rand = new XorShift128Random();
/*     */   
/*     */   protected int renderPass;
/*  56 */   protected int maxPasses = 1;
/*     */   
/*     */   private boolean rotatable = false;
/*     */   private boolean alphaPass = false;
/*     */   
/*     */   protected BlockBuildCraft(Material material) {
/*  62 */     this(material, (CreativeTabs)BCCreativeTab.get("main"));
/*     */   }
/*     */   
/*     */   protected BlockBuildCraft(Material material, CreativeTabs creativeTab) {
/*  66 */     super(material);
/*  67 */     func_149647_a(creativeTab);
/*  68 */     func_149711_c(5.0F);
/*     */   }
/*     */   
/*     */   public boolean hasAlphaPass() {
/*  72 */     return this.alphaPass;
/*     */   }
/*     */   
/*     */   public boolean isRotatable() {
/*  76 */     return this.rotatable;
/*     */   }
/*     */   
/*     */   public void setRotatable(boolean rotatable) {
/*  80 */     this.rotatable = rotatable;
/*     */   }
/*     */   
/*     */   public void setAlphaPass(boolean alphaPass) {
/*  84 */     this.alphaPass = alphaPass;
/*     */   }
/*     */   
/*     */   public void setPassCount(int maxPasses) {
/*  88 */     this.maxPasses = maxPasses;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
/*  93 */     super.func_149689_a(world, x, y, z, entity, stack);
/*  94 */     TileEntity tile = world.func_147438_o(x, y, z);
/*     */     
/*  96 */     if (isRotatable()) {
/*  97 */       ForgeDirection orientation = Utils.get2dOrientation(entity);
/*  98 */       world.func_72921_c(x, y, z, world.func_72805_g(x, y, z) & 0x8 | orientation.getOpposite().ordinal(), 1);
/*     */     } 
/*     */     
/* 101 */     if (tile instanceof TileBuildCraft) {
/* 102 */       ((TileBuildCraft)tile).onBlockPlacedBy(entity, stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 109 */     BlockInteractionEvent event = new BlockInteractionEvent(entityplayer, (Block)this);
/* 110 */     FMLCommonHandler.instance().bus().post((Event)event);
/* 111 */     if (event.isCanceled()) {
/* 112 */       return true;
/*     */     }
/*     */     
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int x, int y, int z, Block block, int par6) {
/* 120 */     Utils.preDestroyBlock(world, x, y, z);
/* 121 */     super.func_149749_a(world, x, y, z, block, par6);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 126 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 127 */     if (tile instanceof IHasWork && ((IHasWork)tile).hasWork()) {
/* 128 */       return super.getLightValue(world, x, y, z) + 8;
/*     */     }
/* 130 */     return super.getLightValue(world, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
/* 137 */     if (isRotatable()) {
/*     */       
/* 139 */       int meta = world.func_72805_g(x, y, z);
/*     */       
/* 141 */       switch (ForgeDirection.getOrientation(meta))
/*     */       { case WEST:
/* 143 */           world.func_72921_c(x, y, z, ForgeDirection.SOUTH.ordinal(), 3);
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
/*     */           
/* 156 */           world.func_147471_g(x, y, z);
/* 157 */           return true;case EAST: world.func_72921_c(x, y, z, ForgeDirection.NORTH.ordinal(), 3); world.func_147471_g(x, y, z); return true;case NORTH: world.func_72921_c(x, y, z, ForgeDirection.WEST.ordinal(), 3); world.func_147471_g(x, y, z); return true; }  world.func_72921_c(x, y, z, ForgeDirection.EAST.ordinal(), 3); world.func_147471_g(x, y, z); return true;
/*     */     } 
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIconAbsolute(IBlockAccess access, int x, int y, int z, int side, int metadata) {
/* 165 */     return getIconAbsolute(side, metadata);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIconAbsolute(int side, int metadata) {
/* 170 */     if (metadata < 0 || metadata >= this.icons.length || this.icons[metadata] == null) {
/* 171 */       return this.icons[0][side];
/*     */     }
/* 173 */     return this.icons[metadata][side];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess access, int x, int y, int z, int side) {
/*     */     IIcon icon;
/* 181 */     int metadata = access.func_72805_g(x, y, z);
/* 182 */     if (isRotatable()) {
/* 183 */       if (side < 2) {
/* 184 */         icon = getIconAbsolute(access, x, y, z, side, metadata & 0x8);
/*     */       } else {
/* 186 */         int front = (metadata >= 2 && metadata <= 5) ? metadata : 3;
/* 187 */         icon = getIconAbsolute(access, x, y, z, SIDE_TEXTURING_LOCATIONS[(front - 2) % 4][(side - 2) % 4], metadata & 0x8);
/*     */       } 
/*     */     } else {
/* 190 */       icon = getIconAbsolute(access, x, y, z, side, metadata);
/*     */     } 
/* 192 */     return (icon != null) ? icon : BuildCraftCore.transparentTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int metadata) {
/* 198 */     if (isRotatable()) {
/* 199 */       if (side < 2) {
/* 200 */         return getIconAbsolute(side, metadata & 0x8);
/*     */       }
/*     */       
/* 203 */       int front = getFrontSide(metadata);
/* 204 */       return getIconAbsolute(SIDE_TEXTURING_LOCATIONS[(front - 2) % 4][(side - 2) % 4], metadata & 0x8);
/*     */     } 
/* 206 */     return getIconAbsolute(side, metadata);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected void registerIconsForMeta(int meta, String blockName, IIconRegister register) {
/* 212 */     this.icons[meta] = new IIcon[6];
/* 213 */     String name = ResourceUtils.getObjectPrefix(blockName);
/* 214 */     this.icons[meta][0] = ResourceUtils.getIconPriority(register, name, new String[] { "bottom", "topbottom", "default" });
/*     */ 
/*     */     
/* 217 */     this.icons[meta][1] = ResourceUtils.getIconPriority(register, name, new String[] { "top", "topbottom", "default" });
/*     */ 
/*     */     
/* 220 */     this.icons[meta][2] = ResourceUtils.getIconPriority(register, name, new String[] { "front", "frontback", "side", "default" });
/*     */ 
/*     */     
/* 223 */     this.icons[meta][3] = ResourceUtils.getIconPriority(register, name, new String[] { "back", "frontback", "side", "default" });
/*     */ 
/*     */     
/* 226 */     this.icons[meta][4] = ResourceUtils.getIconPriority(register, name, new String[] { "left", "leftright", "side", "default" });
/*     */ 
/*     */     
/* 229 */     this.icons[meta][5] = ResourceUtils.getIconPriority(register, name, new String[] { "right", "leftright", "side", "default" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String[] getIconBlockNames() {
/* 236 */     return new String[] { Block.field_149771_c.func_148750_c(this) };
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister register) {
/* 242 */     this.icons = new IIcon[16][];
/* 243 */     String[] iconBlockNames = getIconBlockNames();
/* 244 */     for (int i = 0; i < iconBlockNames.length; i++) {
/* 245 */       registerIconsForMeta(i, iconBlockNames[i], register);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canRenderInPassBC(int pass) {
/* 250 */     if (pass >= this.maxPasses) {
/* 251 */       this.renderPass = 0;
/* 252 */       return false;
/*     */     } 
/* 254 */     this.renderPass = pass;
/* 255 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRenderInPass(int pass) {
/* 261 */     if (this.alphaPass) {
/* 262 */       this.renderPass = pass;
/*     */     }
/* 264 */     return (pass == 0 || this.alphaPass);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_149701_w() {
/* 269 */     return hasAlphaPass() ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/* 274 */     return (this.maxPasses > 1 || isRotatable()) ? BuildCraftCore.complexBlockModel : 0;
/*     */   }
/*     */   
/*     */   public int getCurrentRenderPass() {
/* 278 */     return this.renderPass;
/*     */   }
/*     */   
/*     */   public int getFrontSide(int meta) {
/* 282 */     if (!isRotatable()) {
/* 283 */       return -1;
/*     */     }
/* 285 */     return (meta >= 2 && meta <= 5) ? meta : 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149740_M() {
/* 290 */     return this instanceof IComparatorInventory;
/*     */   }
/*     */   
/*     */   public int func_149736_g(World world, int x, int y, int z, int side) {
/* 294 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 295 */     if (tile instanceof IInventory) {
/* 296 */       int count = 0;
/* 297 */       int countNonEmpty = 0;
/* 298 */       float power = 0.0F;
/* 299 */       for (IInvSlot slot : InventoryIterator.getIterable((IInventory)tile, ForgeDirection.getOrientation(side))) {
/* 300 */         if (((IComparatorInventory)this).doesSlotCountComparator(tile, slot.getIndex(), slot.getStackInSlot())) {
/* 301 */           count++;
/* 302 */           if (slot.getStackInSlot() != null) {
/* 303 */             countNonEmpty++;
/* 304 */             power += (slot.getStackInSlot()).field_77994_a / Math.min(((IInventory)tile).func_70297_j_(), slot.getStackInSlot().func_77976_d());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 309 */       power /= count;
/* 310 */       return MathHelper.func_76141_d(power * 14.0F) + ((countNonEmpty > 0) ? 1 : 0);
/*     */     } 
/*     */     
/* 313 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\block\BlockBuildCraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */