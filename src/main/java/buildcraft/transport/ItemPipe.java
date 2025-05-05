/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.api.transport.IItemPipe;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.ColorUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import org.apache.logging.log4j.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemPipe
/*     */   extends ItemBuildCraft
/*     */   implements IItemPipe
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IIconProvider iconProvider;
/*     */   private int pipeIconIndex;
/*     */   
/*     */   protected ItemPipe(BCCreativeTab creativeTab) {
/*  45 */     super((CreativeTabs)creativeTab);
/*  46 */     func_77656_e(0);
/*  47 */     func_77627_a(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int sideI, float par8, float par9, float par10) {
/*  53 */     int side = sideI;
/*  54 */     BlockGenericPipe blockGenericPipe = BuildCraftTransport.genericPipeBlock;
/*     */     
/*  56 */     int i = x;
/*  57 */     int j = y;
/*  58 */     int k = z;
/*     */     
/*  60 */     Block worldBlock = world.func_147439_a(i, j, k);
/*     */     
/*  62 */     if (worldBlock == Blocks.field_150433_aE) {
/*  63 */       side = 1;
/*  64 */     } else if (worldBlock != Blocks.field_150395_bd && worldBlock != Blocks.field_150329_H && worldBlock != Blocks.field_150330_I && (worldBlock == null || 
/*  65 */       !worldBlock.isReplaceable((IBlockAccess)world, i, j, k))) {
/*  66 */       if (side == 0) {
/*  67 */         j--;
/*     */       }
/*  69 */       if (side == 1) {
/*  70 */         j++;
/*     */       }
/*  72 */       if (side == 2) {
/*  73 */         k--;
/*     */       }
/*  75 */       if (side == 3) {
/*  76 */         k++;
/*     */       }
/*  78 */       if (side == 4) {
/*  79 */         i--;
/*     */       }
/*  81 */       if (side == 5) {
/*  82 */         i++;
/*     */       }
/*     */     } 
/*     */     
/*  86 */     if (itemstack.field_77994_a == 0) {
/*  87 */       return false;
/*     */     }
/*     */     
/*  90 */     if (world.func_147472_a((Block)blockGenericPipe, i, j, k, false, side, (Entity)entityplayer, itemstack)) {
/*  91 */       Pipe<?> pipe = BlockGenericPipe.createPipe((Item)this);
/*     */       
/*  93 */       if (pipe == null) {
/*  94 */         BCLog.logger.log(Level.WARN, "Pipe failed to create during placement at {0},{1},{2}", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) });
/*  95 */         return false;
/*     */       } 
/*     */       
/*  98 */       if (BlockGenericPipe.placePipe(pipe, world, i, j, k, (Block)blockGenericPipe, 0, entityplayer, ForgeDirection.getOrientation(sideI))) {
/*  99 */         blockGenericPipe.func_149689_a(world, i, j, k, (EntityLivingBase)entityplayer, itemstack);
/*     */         
/* 101 */         if (!world.field_72995_K) {
/* 102 */           TileEntity tile = world.func_147438_o(i, j, k);
/* 103 */           ((TileGenericPipe)tile).initializeFromItemMetadata(itemstack.func_77960_j());
/*     */         } 
/*     */         
/* 106 */         world.func_72908_a((i + 0.5F), (j + 0.5F), (k + 0.5F), ((Block)blockGenericPipe).field_149762_H
/* 107 */             .func_150496_b(), (((Block)blockGenericPipe).field_149762_H
/* 108 */             .func_150497_c() + 1.0F) / 2.0F, ((Block)blockGenericPipe).field_149762_H
/* 109 */             .func_150494_d() * 0.8F);
/*     */         
/* 111 */         itemstack.field_77994_a--;
/*     */         
/* 113 */         return true;
/*     */       } 
/* 115 */       return false;
/*     */     } 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPipesIcons(IIconProvider iconProvider) {
/* 124 */     this.iconProvider = iconProvider;
/*     */   }
/*     */   
/*     */   public void setPipeIconIndex(int index) {
/* 128 */     this.pipeIconIndex = index;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int par1) {
/* 134 */     if (this.iconProvider != null) {
/* 135 */       return this.iconProvider.getIcon(this.pipeIconIndex);
/*     */     }
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_94901_k() {
/* 150 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
/* 156 */     super.func_77624_a(stack, player, list, advanced);
/* 157 */     if (stack.func_77960_j() >= 1) {
/* 158 */       int color = stack.func_77960_j() - 1 & 0xF;
/* 159 */       list.add(ColorUtils.getFormattingTooltip(color) + EnumChatFormatting.ITALIC + StringUtils.localize("color." + ColorUtils.getName(color)));
/*     */     } 
/* 161 */     Class<? extends Pipe<?>> pipe = BlockGenericPipe.pipes.get(this);
/* 162 */     List<String> toolTip = PipeToolTipManager.getToolTip(pipe, advanced);
/* 163 */     list.addAll(toolTip);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\ItemPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */