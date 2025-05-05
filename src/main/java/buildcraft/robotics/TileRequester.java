/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.robots.IRequestProvider;
/*     */ import buildcraft.core.lib.block.TileBuildCraft;
/*     */ import buildcraft.core.lib.inventory.SimpleInventory;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileRequester
/*     */   extends TileBuildCraft
/*     */   implements IInventory, IRequestProvider, ICommandReceiver
/*     */ {
/*     */   public static final int NB_ITEMS = 20;
/*  33 */   private SimpleInventory inv = new SimpleInventory(20, "items", 64);
/*  34 */   private SimpleInventory requests = new SimpleInventory(20, "requests", 64);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequest(final int index, final ItemStack stack) {
/*  41 */     if (this.field_145850_b.field_72995_K) {
/*  42 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "setRequest", new CommandWriter() {
/*     */               public void write(ByteBuf data) {
/*  44 */                 data.writeByte(index);
/*  45 */                 NetworkUtils.writeStack(data, stack);
/*     */               }
/*     */             }));
/*     */     } else {
/*  49 */       this.requests.func_70299_a(index, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/*  55 */     if (side.isServer() && "setRequest".equals(command)) {
/*  56 */       setRequest(stream.readUnsignedByte(), NetworkUtils.readStack(stream));
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getRequestTemplate(int index) {
/*  61 */     return this.requests.func_70301_a(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  66 */     return this.inv.func_70302_i_();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int slotId) {
/*  71 */     return this.inv.func_70301_a(slotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int slotId, int count) {
/*  76 */     return this.inv.func_70298_a(slotId, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int slotId) {
/*  81 */     return this.inv.func_70304_b(slotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slotId, ItemStack itemStack) {
/*  86 */     this.inv.func_70299_a(slotId, itemStack);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  91 */     return this.inv.func_145825_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/*  96 */     return this.inv.func_145818_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 101 */     return this.inv.func_70297_j_();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer entityPlayer) {
/* 106 */     return this.inv.func_70300_a(entityPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70295_k_() {
/* 111 */     this.inv.func_70295_k_();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70305_f() {
/* 116 */     this.inv.func_70305_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int i, ItemStack itemStack) {
/* 121 */     if (this.requests.func_70301_a(i) == null)
/* 122 */       return false; 
/* 123 */     if (!StackHelper.isMatchingItemOrList(this.requests.func_70301_a(i), itemStack)) {
/* 124 */       return false;
/*     */     }
/* 126 */     return this.inv.func_94041_b(i, itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 132 */     super.func_145841_b(nbt);
/*     */     
/* 134 */     NBTTagCompound invNBT = new NBTTagCompound();
/* 135 */     this.inv.writeToNBT(invNBT);
/* 136 */     nbt.func_74782_a("inv", (NBTBase)invNBT);
/*     */     
/* 138 */     NBTTagCompound reqNBT = new NBTTagCompound();
/* 139 */     this.requests.writeToNBT(reqNBT);
/* 140 */     nbt.func_74782_a("req", (NBTBase)reqNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 145 */     super.func_145839_a(nbt);
/*     */     
/* 147 */     this.inv.readFromNBT(nbt.func_74775_l("inv"));
/* 148 */     this.requests.readFromNBT(nbt.func_74775_l("req"));
/*     */   }
/*     */   
/*     */   public boolean isFulfilled(int i) {
/* 152 */     if (this.requests.func_70301_a(i) == null)
/* 153 */       return true; 
/* 154 */     if (this.inv.func_70301_a(i) == null) {
/* 155 */       return false;
/*     */     }
/* 157 */     return (StackHelper.isMatchingItemOrList(this.requests.func_70301_a(i), this.inv.func_70301_a(i)) && 
/* 158 */       (this.inv.func_70301_a(i)).field_77994_a >= (this.requests.func_70301_a(i)).field_77994_a);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequestsCount() {
/* 164 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRequest(int i) {
/* 169 */     if (this.requests.func_70301_a(i) == null)
/* 170 */       return null; 
/* 171 */     if (isFulfilled(i)) {
/* 172 */       return null;
/*     */     }
/* 174 */     ItemStack request = this.requests.func_70301_a(i).func_77946_l();
/*     */     
/* 176 */     ItemStack existingStack = this.inv.func_70301_a(i);
/* 177 */     if (existingStack == null) {
/* 178 */       return request;
/*     */     }
/*     */     
/* 181 */     if (!StackHelper.isMatchingItemOrList(request, existingStack)) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     request.field_77994_a -= existingStack.field_77994_a;
/* 186 */     if (request.field_77994_a <= 0) {
/* 187 */       return null;
/*     */     }
/*     */     
/* 190 */     return request;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack offerItem(int i, ItemStack stack) {
/* 196 */     ItemStack existingStack = this.inv.func_70301_a(i);
/*     */     
/* 198 */     if (this.requests.func_70301_a(i) == null)
/* 199 */       return stack; 
/* 200 */     if (existingStack == null) {
/* 201 */       if (!StackHelper.isMatchingItemOrList(stack, this.requests.func_70301_a(i))) {
/* 202 */         return stack;
/*     */       }
/*     */       
/* 205 */       int maxQty = (this.requests.func_70301_a(i)).field_77994_a;
/*     */       
/* 207 */       if (stack.field_77994_a <= maxQty) {
/* 208 */         this.inv.func_70299_a(i, stack);
/*     */         
/* 210 */         return null;
/*     */       } 
/* 212 */       ItemStack newStack = stack.func_77946_l();
/* 213 */       newStack.field_77994_a = maxQty;
/* 214 */       stack.field_77994_a -= maxQty;
/*     */       
/* 216 */       this.inv.func_70299_a(i, newStack);
/*     */       
/* 218 */       return stack;
/*     */     } 
/* 220 */     if (!StackHelper.isMatchingItemOrList(stack, existingStack))
/* 221 */       return stack; 
/* 222 */     if (StackHelper.isMatchingItemOrList(stack, this.requests.func_70301_a(i))) {
/* 223 */       int maxQty = (this.requests.func_70301_a(i)).field_77994_a;
/*     */       
/* 225 */       if (existingStack.field_77994_a + stack.field_77994_a <= maxQty) {
/* 226 */         existingStack.field_77994_a += stack.field_77994_a;
/* 227 */         return null;
/*     */       } 
/* 229 */       stack.field_77994_a -= maxQty - existingStack.field_77994_a;
/* 230 */       existingStack.field_77994_a = maxQty;
/* 231 */       return stack;
/*     */     } 
/*     */     
/* 234 */     return stack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\TileRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */