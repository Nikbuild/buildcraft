/*     */ package buildcraft.transport.stripes;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.transport.IStripesActivator;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.TileGenericPipe;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import buildcraft.transport.utils.TransportUtils;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.TickEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class PipeExtensionListener {
/*     */   private class PipeExtensionRequest {
/*     */     public ItemStack stack;
/*     */     public int x;
/*     */     public int y;
/*     */     public int z;
/*     */     public ForgeDirection o;
/*     */     public IStripesActivator h;
/*     */     
/*     */     private PipeExtensionRequest() {}
/*     */   }
/*  37 */   private final Map<World, HashSet<PipeExtensionRequest>> requests = new HashMap<World, HashSet<PipeExtensionRequest>>();
/*     */   
/*     */   public void requestPipeExtension(ItemStack stack, World world, int x, int y, int z, ForgeDirection o, IStripesActivator h) {
/*  40 */     if (world.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  44 */     if (!this.requests.containsKey(world)) {
/*  45 */       this.requests.put(world, new HashSet<PipeExtensionRequest>());
/*     */     }
/*  47 */     PipeExtensionRequest r = new PipeExtensionRequest();
/*  48 */     r.stack = stack;
/*  49 */     r.x = x;
/*  50 */     r.y = y;
/*  51 */     r.z = z;
/*  52 */     r.o = o;
/*  53 */     r.h = h;
/*  54 */     ((HashSet<PipeExtensionRequest>)this.requests.get(world)).add(r);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void tick(TickEvent.WorldTickEvent event) {
/*  59 */     if (event.phase == TickEvent.Phase.END && this.requests.containsKey(event.world)) {
/*  60 */       HashSet<PipeExtensionRequest> rSet = this.requests.get(event.world);
/*  61 */       World w = event.world;
/*  62 */       for (PipeExtensionRequest r : rSet) {
/*  63 */         Position target = new Position(r.x, r.y, r.z);
/*  64 */         target.orientation = r.o;
/*     */         
/*  66 */         boolean retract = (r.stack.func_77973_b() == BuildCraftTransport.pipeItemsVoid);
/*  67 */         ArrayList<ItemStack> removedPipeStacks = null;
/*     */         
/*  69 */         if (retract) {
/*  70 */           target.moveBackwards(1.0D);
/*  71 */           if (w.func_147439_a((int)target.x, (int)target.y, (int)target.z) != BuildCraftTransport.genericPipeBlock) {
/*  72 */             r.h.sendItem(r.stack, r.o.getOpposite());
/*     */             
/*     */             continue;
/*     */           } 
/*  76 */           target.moveBackwards(1.0D);
/*  77 */           if (w.func_147439_a((int)target.x, (int)target.y, (int)target.z) != BuildCraftTransport.genericPipeBlock) {
/*  78 */             r.h.sendItem(r.stack, r.o.getOpposite());
/*     */             
/*     */             continue;
/*     */           } 
/*  82 */           target.moveForwards(1.0D);
/*     */         } else {
/*  84 */           target.moveForwards(1.0D);
/*  85 */           if (!w.func_147437_c((int)target.x, (int)target.y, (int)target.z)) {
/*  86 */             r.h.sendItem(r.stack, r.o.getOpposite());
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */         
/*  92 */         Block oldBlock = w.func_147439_a(r.x, r.y, r.z);
/*  93 */         int oldMeta = w.func_72805_g(r.x, r.y, r.z);
/*  94 */         NBTTagCompound nbt = new NBTTagCompound();
/*  95 */         w.func_147438_o(r.x, r.y, r.z).func_145841_b(nbt);
/*  96 */         w.func_147468_f(r.x, r.y, r.z);
/*     */         
/*  98 */         boolean failedPlacement = false;
/*     */ 
/*     */         
/* 101 */         if (retract) {
/* 102 */           removedPipeStacks = w.func_147439_a((int)target.x, (int)target.y, (int)target.z).getDrops(w, (int)target.x, (int)target.y, (int)target.z, w
/* 103 */               .func_72805_g((int)target.x, (int)target.y, (int)target.z), 0);
/*     */           
/* 105 */           w.func_147468_f((int)target.x, (int)target.y, (int)target.z);
/*     */         }
/* 107 */         else if (!r.stack.func_77973_b().func_77648_a(r.stack, CoreProxy.proxy
/* 108 */             .getBuildCraftPlayer((WorldServer)w, r.x, r.y, r.z).get(), w, r.x, r.y, r.z, 1, 0.0F, 0.0F, 0.0F)) {
/*     */           
/* 110 */           failedPlacement = true;
/* 111 */           target.moveBackwards(1.0D);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 117 */         nbt.func_74768_a("x", (int)target.x);
/* 118 */         nbt.func_74768_a("y", (int)target.y);
/* 119 */         nbt.func_74768_a("z", (int)target.z);
/*     */         
/* 121 */         TileGenericPipe pipeTile = (TileGenericPipe)TileEntity.func_145827_c(nbt);
/*     */         
/* 123 */         w.func_147465_d((int)target.x, (int)target.y, (int)target.z, oldBlock, oldMeta, 3);
/* 124 */         w.func_147455_a((int)target.x, (int)target.y, (int)target.z, (TileEntity)pipeTile);
/*     */         
/* 126 */         pipeTile.func_145834_a(w);
/* 127 */         pipeTile.func_145829_t();
/* 128 */         pipeTile.func_145845_h();
/*     */ 
/*     */         
/* 131 */         PipeTransportItems items = (PipeTransportItems)pipeTile.pipe.transport;
/* 132 */         if (!retract && !failedPlacement) {
/* 133 */           r.stack.field_77994_a--;
/*     */         }
/*     */         
/* 136 */         if (r.stack.field_77994_a > 0) {
/* 137 */           sendItem(items, r.stack, r.o.getOpposite());
/*     */         }
/* 139 */         if (removedPipeStacks != null) {
/* 140 */           for (ItemStack s : removedPipeStacks) {
/* 141 */             sendItem(items, s, r.o.getOpposite());
/*     */           }
/*     */         }
/*     */         
/* 145 */         if (!retract && !failedPlacement) {
/* 146 */           TileGenericPipe newPipeTile = (TileGenericPipe)w.func_147438_o(r.x, r.y, r.z);
/* 147 */           newPipeTile.func_145845_h();
/* 148 */           pipeTile.scheduleNeighborChange();
/* 149 */           if (pipeTile.getPipe() != null) {
/* 150 */             ((Pipe)pipeTile.getPipe()).scheduleWireUpdate();
/*     */           }
/*     */         } 
/*     */       } 
/* 154 */       rSet.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendItem(PipeTransportItems transport, ItemStack itemStack, ForgeDirection direction) {
/* 159 */     TravelingItem newItem = TravelingItem.make(transport.container.field_145851_c + 0.5D, (transport.container.field_145848_d + 
/*     */         
/* 161 */         TransportUtils.getPipeFloorOf(itemStack)), transport.container.field_145849_e + 0.5D, itemStack);
/*     */     
/* 163 */     transport.injectItem(newItem, direction);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\stripes\PipeExtensionListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */