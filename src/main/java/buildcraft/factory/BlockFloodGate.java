/*     */ package buildcraft.factory;
/*     */ 
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.core.lib.block.BlockBuildCraft;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
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
/*     */ public class BlockFloodGate
/*     */   extends BlockBuildCraft
/*     */ {
/*     */   private IIcon valve;
/*     */   private IIcon transparent;
/*     */   
/*     */   public BlockFloodGate() {
/*  32 */     super(Material.field_151573_f);
/*  33 */     setPassCount(2);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int metadata) {
/*  38 */     return (TileEntity)new TileFloodGate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float par7, float par8, float par9) {
/*  43 */     if (super.func_149727_a(world, i, j, k, entityplayer, side, par7, par8, par9)) {
/*  44 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  48 */     if (entityplayer.func_70093_af()) {
/*  49 */       return false;
/*     */     }
/*     */     
/*  52 */     TileEntity tile = world.func_147438_o(i, j, k);
/*     */     
/*  54 */     if (tile instanceof TileFloodGate) {
/*  55 */       TileFloodGate floodGate = (TileFloodGate)tile;
/*     */       
/*  57 */       Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/*  58 */       if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(entityplayer, i, j, k)) {
/*  59 */         if (side == 1) {
/*  60 */           floodGate.rebuildQueue();
/*     */         } else {
/*  62 */           floodGate.switchSide(ForgeDirection.getOrientation(side));
/*     */         } 
/*     */         
/*  65 */         ((IToolWrench)equipped).wrenchUsed(entityplayer, i, j, k);
/*  66 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int x, int y, int z, Block block) {
/*  75 */     super.func_149695_a(world, x, y, z, block);
/*  76 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  77 */     if (tile instanceof TileFloodGate) {
/*  78 */       ((TileFloodGate)tile).onNeighborBlockChange(block);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149651_a(IIconRegister register) {
/*  85 */     super.func_149651_a(register);
/*  86 */     this.valve = register.func_94245_a("buildcraftfactory:floodGateBlock/valve");
/*  87 */     this.transparent = register.func_94245_a("buildcraftcore:misc/transparent");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149673_e(IBlockAccess world, int x, int y, int z, int side) {
/*  93 */     if (this.renderPass == 1) {
/*  94 */       if (side != 1) {
/*  95 */         TileEntity tile = world.func_147438_o(x, y, z);
/*  96 */         if (tile instanceof TileFloodGate) {
/*  97 */           return ((TileFloodGate)tile).isSideBlocked(side) ? this.transparent : this.valve;
/*     */         }
/*     */       } 
/* 100 */       return this.transparent;
/*     */     } 
/* 102 */     return super.func_149673_e(world, x, y, z, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_149691_a(int side, int metadata) {
/* 109 */     if (this.renderPass == 1) {
/* 110 */       if (side == 1) {
/* 111 */         return null;
/*     */       }
/* 113 */       return this.valve;
/*     */     } 
/* 115 */     return super.func_149691_a(side, metadata);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockFloodGate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */