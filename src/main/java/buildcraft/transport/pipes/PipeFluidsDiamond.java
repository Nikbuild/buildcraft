/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.utils.FluidUtils;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.transport.IDiamondPipe;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportFluids;
/*     */ import buildcraft.transport.pipes.events.PipeEventFluid;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.fluids.Fluid;
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
/*     */ public class PipeFluidsDiamond
/*     */   extends Pipe<PipeTransportFluids>
/*     */   implements IDiamondPipe
/*     */ {
/*     */   private class FilterInventory
/*     */     extends SimpleInventory
/*     */   {
/*  44 */     public boolean[] filteredDirections = new boolean[6];
/*  45 */     public Fluid[] fluids = new Fluid[54];
/*     */     
/*     */     public FilterInventory(int size, String invName, int invStackLimit) {
/*  48 */       super(size, invName, invStackLimit);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_94041_b(int slot, ItemStack stack) {
/*  53 */       return (stack == null || FluidUtils.isFluidContainer(stack));
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70296_d() {
/*     */       int i;
/*  59 */       for (i = 0; i < 6; i++) {
/*  60 */         this.filteredDirections[i] = false;
/*     */       }
/*     */       
/*  63 */       for (i = 0; i < 54; i++) {
/*  64 */         this.fluids[i] = FluidUtils.getFluidFromItemStack(func_70301_a(i));
/*  65 */         if (this.fluids[i] != null) {
/*  66 */           this.filteredDirections[i / 9] = true;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*  72 */   private FilterInventory filters = new FilterInventory(54, "Filters", 1);
/*     */   
/*     */   public PipeFluidsDiamond(Item item) {
/*  75 */     super((PipeTransport)new PipeTransportFluids(), item);
/*     */     
/*  77 */     ((PipeTransportFluids)this.transport).initFromPipe(getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public IInventory getFilters() {
/*  82 */     return (IInventory)this.filters;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  88 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  93 */     switch (direction) {
/*     */       case UNKNOWN:
/*  95 */         return PipeIconProvider.TYPE.PipeFluidsDiamond_Center.ordinal();
/*     */       case DOWN:
/*  97 */         return PipeIconProvider.TYPE.PipeFluidsDiamond_Down.ordinal();
/*     */       case UP:
/*  99 */         return PipeIconProvider.TYPE.PipeFluidsDiamond_Up.ordinal();
/*     */       case NORTH:
/* 101 */         return PipeIconProvider.TYPE.PipeFluidsDiamond_North.ordinal();
/*     */       case SOUTH:
/* 103 */         return PipeIconProvider.TYPE.PipeFluidsDiamond_South.ordinal();
/*     */       case WEST:
/* 105 */         return PipeIconProvider.TYPE.PipeFluidsDiamond_West.ordinal();
/*     */       case EAST:
/* 107 */         return PipeIconProvider.TYPE.PipeFluidsDiamond_East.ordinal();
/*     */     } 
/* 109 */     throw new IllegalArgumentException("direction out of bounds");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconIndexForItem() {
/* 115 */     return PipeIconProvider.TYPE.PipeFluidsDiamond_Item.ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection direction) {
/* 120 */     if (entityplayer.func_71045_bC() != null && 
/* 121 */       Block.func_149634_a(entityplayer.func_71045_bC().func_77973_b()) instanceof buildcraft.transport.BlockGenericPipe) {
/* 122 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 126 */     if (!(this.container.func_145831_w()).field_72995_K) {
/* 127 */       entityplayer.openGui(BuildCraftTransport.instance, 50, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */     }
/*     */     
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public void eventHandler(PipeEventFluid.FindDest event) {
/* 134 */     Fluid fluidInTank = event.fluidStack.getFluid();
/* 135 */     Set<ForgeDirection> originalDestinations = new HashSet<ForgeDirection>();
/* 136 */     originalDestinations.addAll(event.destinations.elementSet());
/* 137 */     boolean isFiltered = false;
/* 138 */     int[] filterCount = new int[6];
/*     */     
/* 140 */     for (ForgeDirection dir : originalDestinations) {
/* 141 */       if (this.container.isPipeConnected(dir) && this.filters.filteredDirections[dir.ordinal()]) {
/* 142 */         for (int slot = dir.ordinal() * 9; slot < dir.ordinal() * 9 + 9; slot++) {
/* 143 */           if (this.filters.fluids[slot] != null && this.filters.fluids[slot].getID() == fluidInTank.getID()) {
/* 144 */             filterCount[dir.ordinal()] = filterCount[dir.ordinal()] + 1;
/* 145 */             isFiltered = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 151 */     event.destinations.clear();
/*     */     
/* 153 */     if (!isFiltered) {
/* 154 */       for (ForgeDirection to : originalDestinations) {
/* 155 */         if (!this.filters.filteredDirections[to.ordinal()]) {
/* 156 */           event.destinations.add(to);
/*     */         }
/*     */       } 
/*     */     } else {
/* 160 */       for (ForgeDirection to : originalDestinations) {
/* 161 */         if (filterCount[to.ordinal()] > 0) {
/* 162 */           event.destinations.add(to, filterCount[to.ordinal()]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 171 */     super.readFromNBT(nbt);
/* 172 */     this.filters.readFromNBT(nbt);
/* 173 */     this.filters.func_70296_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 178 */     super.writeToNBT(nbt);
/* 179 */     this.filters.writeToNBT(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 185 */     NBTTagCompound nbt = new NBTTagCompound();
/* 186 */     this.filters.writeToNBT(nbt);
/* 187 */     NetworkUtils.writeNBT(data, nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 192 */     NBTTagCompound nbt = NetworkUtils.readNBT(data);
/* 193 */     this.filters.readFromNBT(nbt);
/* 194 */     this.filters.func_70296_d();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeFluidsDiamond.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */