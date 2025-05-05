/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
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
/*     */ public class ItemConstructionMarker
/*     */   extends ItemBlock
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon iconBase;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon iconRecording;
/*     */   
/*     */   public ItemConstructionMarker(Block block) {
/*  36 */     super(block);
/*     */   }
/*     */   
/*     */   public static boolean linkStarted(ItemStack marker) {
/*  40 */     return NBTUtils.getItemData(marker).func_74764_b("x");
/*     */   }
/*     */   
/*     */   public static void link(ItemStack marker, World world, int x, int y, int z) {
/*  44 */     NBTTagCompound nbt = NBTUtils.getItemData(marker);
/*     */     
/*  46 */     if (nbt.func_74764_b("x")) {
/*  47 */       int ox = nbt.func_74762_e("x");
/*  48 */       int oy = nbt.func_74762_e("y");
/*  49 */       int oz = nbt.func_74762_e("z");
/*     */       
/*  51 */       TileEntity tile1 = world.func_147438_o(ox, oy, oz);
/*     */       
/*  53 */       if (!(new Position(ox, oy, oz)).isClose(new Position(x, y, z), 64.0F)) {
/*     */         return;
/*     */       }
/*     */       
/*  57 */       if (tile1 != null && tile1 instanceof TileArchitect) {
/*  58 */         TileArchitect architect = (TileArchitect)tile1;
/*  59 */         TileEntity tile2 = world.func_147438_o(x, y, z);
/*     */         
/*  61 */         if (tile1 != tile2 && tile2 != null && (
/*  62 */           tile2 instanceof TileArchitect || tile2 instanceof TileConstructionMarker || tile2 instanceof TileBuilder)) {
/*     */ 
/*     */           
/*  65 */           architect.addSubBlueprint(tile2);
/*     */           
/*  67 */           nbt.func_82580_o("x");
/*  68 */           nbt.func_82580_o("y");
/*  69 */           nbt.func_82580_o("z");
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     nbt.func_74768_a("x", x);
/*  78 */     nbt.func_74768_a("y", y);
/*  79 */     nbt.func_74768_a("z", z);
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_77650_f(ItemStack marker) {
/*  84 */     NBTTagCompound nbt = NBTUtils.getItemData(marker);
/*     */     
/*  86 */     if (nbt.func_74764_b("x")) {
/*  87 */       this.field_77791_bV = this.iconRecording;
/*     */     } else {
/*  89 */       this.field_77791_bV = this.iconBase;
/*     */     } 
/*     */     
/*  92 */     return this.field_77791_bV;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {
/*  98 */     super.func_94581_a(par1IconRegister);
/*     */     
/* 100 */     this.iconBase = par1IconRegister.func_94245_a("buildcraftbuilders:constructionMarkerBlock/default");
/* 101 */     this.iconRecording = par1IconRegister.func_94245_a("buildcraftbuilders:constructionMarkerBlock/recording");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack marker, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
/* 108 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 109 */     NBTTagCompound nbt = NBTUtils.getItemData(marker);
/*     */     
/* 111 */     if (nbt.func_74764_b("x") && !(tile instanceof TileBuilder) && !(tile instanceof TileArchitect) && !(tile instanceof TileConstructionMarker)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       nbt.func_82580_o("x");
/* 117 */       nbt.func_82580_o("y");
/* 118 */       nbt.func_82580_o("z");
/*     */       
/* 120 */       return true;
/*     */     } 
/* 122 */     return super.func_77648_a(marker, player, world, x, y, z, side, par8, par9, par10);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\ItemConstructionMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */