/*     */ package buildcraft.transport.pipes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.IIconProvider;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.transport.IDiamondPipe;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransport;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.pipes.events.PipeEventItem;
/*     */ import buildcraft.transport.pipes.events.PipeEventPriority;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipeItemsDiamond
/*     */   extends Pipe<PipeTransportItems>
/*     */   implements IDiamondPipe
/*     */ {
/*     */   private class SimpleFilterInventory
/*     */     extends SimpleInventory
/*     */   {
/*  42 */     protected int[] filterCounts = new int[6];
/*     */     
/*     */     public SimpleFilterInventory(int size, String invName, int invStackLimit) {
/*  45 */       super(size, invName, invStackLimit);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70296_d() {
/*  50 */       super.func_70296_d();
/*     */       
/*  52 */       for (int i = 0; i < 6; i++) {
/*  53 */         this.filterCounts[i] = 0;
/*  54 */         for (int j = 0; j < 9; j++) {
/*  55 */           if (func_70301_a(j + i * 9) != null) {
/*  56 */             this.filterCounts[i] = this.filterCounts[i] + 1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*  63 */   private SimpleFilterInventory filters = new SimpleFilterInventory(54, "Filters", 1);
/*     */   private long usedFilters;
/*     */   
/*     */   public PipeItemsDiamond(Item item) {
/*  67 */     super((PipeTransport)new PipeTransportItems(), item);
/*     */   }
/*     */ 
/*     */   
/*     */   public IInventory getFilters() {
/*  72 */     return (IInventory)this.filters;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIconProvider getIconProvider() {
/*  78 */     return BuildCraftTransport.instance.pipeIconProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconIndex(ForgeDirection direction) {
/*  83 */     switch (direction) {
/*     */       case UNKNOWN:
/*  85 */         return PipeIconProvider.TYPE.PipeItemsDiamond_Center.ordinal();
/*     */       case DOWN:
/*  87 */         return PipeIconProvider.TYPE.PipeItemsDiamond_Down.ordinal();
/*     */       case UP:
/*  89 */         return PipeIconProvider.TYPE.PipeItemsDiamond_Up.ordinal();
/*     */       case NORTH:
/*  91 */         return PipeIconProvider.TYPE.PipeItemsDiamond_North.ordinal();
/*     */       case SOUTH:
/*  93 */         return PipeIconProvider.TYPE.PipeItemsDiamond_South.ordinal();
/*     */       case WEST:
/*  95 */         return PipeIconProvider.TYPE.PipeItemsDiamond_West.ordinal();
/*     */       case EAST:
/*  97 */         return PipeIconProvider.TYPE.PipeItemsDiamond_East.ordinal();
/*     */     } 
/*  99 */     throw new IllegalArgumentException("direction out of bounds");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconIndexForItem() {
/* 105 */     return PipeIconProvider.TYPE.PipeItemsDiamond_Item.ordinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blockActivated(EntityPlayer entityplayer, ForgeDirection direction) {
/* 110 */     if (entityplayer.func_71045_bC() != null && 
/* 111 */       Block.func_149634_a(entityplayer.func_71045_bC().func_77973_b()) instanceof buildcraft.transport.BlockGenericPipe) {
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 116 */     if (!(this.container.func_145831_w()).field_72995_K) {
/* 117 */       entityplayer.openGui(BuildCraftTransport.instance, 50, this.container.func_145831_w(), this.container.field_145851_c, this.container.field_145848_d, this.container.field_145849_e);
/*     */     }
/*     */     
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   private boolean findDest(PipeEventItem.FindDest event) {
/* 124 */     for (ForgeDirection dir : event.destinations) {
/* 125 */       if (this.filters.filterCounts[dir.ordinal()] > 0)
/* 126 */         for (int slot = 0; slot < 9; slot++) {
/* 127 */           int v = dir.ordinal() * 9 + slot;
/* 128 */           if ((this.usedFilters & (1 << v)) == 0L) {
/*     */ 
/*     */ 
/*     */             
/* 132 */             ItemStack filter = getFilters().func_70301_a(v);
/*     */             
/* 134 */             if (StackHelper.isMatchingItemOrList(filter, event.item.getItemStack())) {
/* 135 */               this.usedFilters |= (1 << v);
/* 136 */               event.destinations.clear();
/* 137 */               event.destinations.add(dir);
/* 138 */               event.shuffle = false;
/* 139 */               return true;
/*     */             } 
/*     */           } 
/*     */         }  
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   private void clearDest(PipeEventItem.FindDest event) {
/* 148 */     for (ForgeDirection dir : event.destinations) {
/* 149 */       if (this.filters.filterCounts[dir.ordinal()] > 0) {
/* 150 */         for (int slot = 0; slot < 9; slot++) {
/* 151 */           int v = dir.ordinal() * 9 + slot;
/* 152 */           if ((this.usedFilters & (1 << v)) != 0L) {
/*     */ 
/*     */ 
/*     */             
/* 156 */             ItemStack filter = getFilters().func_70301_a(v);
/*     */             
/* 158 */             if (StackHelper.isMatchingItemOrList(filter, event.item.getItemStack())) {
/* 159 */               this.usedFilters ^= (1 << v);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PipeEventPriority(priority = -4194304)
/*     */   public void eventHandler(PipeEventItem.FindDest event) {
/* 172 */     if (findDest(event)) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     if (this.usedFilters != 0L) {
/* 177 */       clearDest(event);
/* 178 */       if (findDest(event)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     Iterator<ForgeDirection> i = event.destinations.iterator();
/* 184 */     while (i.hasNext()) {
/* 185 */       if (this.filters.filterCounts[((ForgeDirection)i.next()).ordinal()] > 0) {
/* 186 */         i.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 194 */     super.readFromNBT(nbt);
/* 195 */     this.filters.readFromNBT(nbt);
/* 196 */     if (nbt.func_74764_b("usedFilters")) {
/* 197 */       this.usedFilters = nbt.func_74763_f("usedFilters");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 203 */     super.writeToNBT(nbt);
/* 204 */     this.filters.writeToNBT(nbt);
/* 205 */     nbt.func_74772_a("usedFilters", this.usedFilters);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf data) {
/* 211 */     NBTTagCompound nbt = new NBTTagCompound();
/* 212 */     this.filters.writeToNBT(nbt);
/* 213 */     nbt.func_74772_a("usedFilters", this.usedFilters);
/* 214 */     NetworkUtils.writeNBT(data, nbt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf data) {
/* 219 */     NBTTagCompound nbt = NetworkUtils.readNBT(data);
/* 220 */     this.filters.readFromNBT(nbt);
/* 221 */     if (nbt.func_74764_b("usedFilters"))
/* 222 */       this.usedFilters = nbt.func_74763_f("usedFilters"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pipes\PipeItemsDiamond.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */