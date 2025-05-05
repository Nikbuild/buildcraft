/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.recipes.BuildcraftRecipeRegistry;
/*     */ import buildcraft.api.recipes.IProgrammingRecipe;
/*     */ import buildcraft.core.lib.network.Packet;
/*     */ import buildcraft.core.lib.network.command.CommandWriter;
/*     */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*     */ import buildcraft.core.lib.network.command.PacketCommand;
/*     */ import buildcraft.core.lib.utils.NetworkUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class TileProgrammingTable
/*     */   extends TileLaserTableBase
/*     */   implements IInventory, ISidedInventory, ICommandReceiver
/*     */ {
/*     */   public static final int WIDTH = 6;
/*     */   public static final int HEIGHT = 4;
/*  36 */   public String currentRecipeId = "";
/*     */   public IProgrammingRecipe currentRecipe;
/*     */   public List<ItemStack> options;
/*     */   public int optionId;
/*     */   private boolean queuedNetworkUpdate = false;
/*     */   
/*     */   private void queueNetworkUpdate() {
/*  43 */     this.queuedNetworkUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  48 */     return !FMLCommonHandler.instance().getEffectiveSide().isClient();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  53 */     super.func_145845_h();
/*     */     
/*  55 */     if (this.queuedNetworkUpdate) {
/*  56 */       sendNetworkUpdate();
/*  57 */       this.queuedNetworkUpdate = false;
/*     */     } 
/*     */     
/*  60 */     if (this.currentRecipe == null) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     if (func_70301_a(0) == null) {
/*  65 */       this.currentRecipe = null;
/*     */       
/*     */       return;
/*     */     } 
/*  69 */     if (this.optionId >= 0 && getEnergy() >= this.currentRecipe.getEnergyCost((ItemStack)this.options.get(this.optionId))) {
/*  70 */       if (this.currentRecipe.canCraft(func_70301_a(0))) {
/*  71 */         ItemStack remaining = this.currentRecipe.craft(func_70301_a(0), this.options.get(this.optionId));
/*  72 */         if (remaining != null && remaining.field_77994_a > 0) {
/*  73 */           setEnergy(0);
/*  74 */           func_70298_a(0, remaining.field_77994_a);
/*  75 */           outputStack(remaining, this, 1, false);
/*     */         } 
/*     */       } 
/*  78 */       findRecipe();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  85 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70299_a(int slot, ItemStack stack) {
/*  90 */     super.func_70299_a(slot, stack);
/*     */     
/*  92 */     if (slot == 0) {
/*  93 */       findRecipe();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  99 */     return StringUtils.localize("tile.programmingTableBlock.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/* 104 */     super.readData(stream);
/* 105 */     this.currentRecipeId = NetworkUtils.readUTF(stream);
/* 106 */     this.optionId = stream.readByte();
/* 107 */     updateRecipe();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/* 112 */     super.writeData(stream);
/* 113 */     NetworkUtils.writeUTF(stream, this.currentRecipeId);
/* 114 */     stream.writeByte(this.optionId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 119 */     super.func_145839_a(nbt);
/*     */     
/* 121 */     if (nbt.func_74764_b("recipeId") && nbt.func_74764_b("optionId")) {
/* 122 */       this.currentRecipeId = nbt.func_74779_i("recipeId");
/* 123 */       this.optionId = nbt.func_74762_e("optionId");
/*     */     } else {
/* 125 */       this.currentRecipeId = null;
/*     */     } 
/* 127 */     updateRecipe();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbt) {
/* 132 */     super.func_145841_b(nbt);
/*     */     
/* 134 */     if (this.currentRecipeId != null) {
/* 135 */       nbt.func_74778_a("recipeId", this.currentRecipeId);
/* 136 */       nbt.func_74774_a("optionId", (byte)this.optionId);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequiredEnergy() {
/* 142 */     if (hasWork()) {
/* 143 */       return this.currentRecipe.getEnergyCost(this.options.get(this.optionId));
/*     */     }
/* 145 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void findRecipe() {
/* 150 */     String oldId = this.currentRecipeId;
/* 151 */     this.currentRecipeId = null;
/*     */     
/* 153 */     if (func_70301_a(0) != null) {
/* 154 */       for (IProgrammingRecipe recipe : BuildcraftRecipeRegistry.programmingTable.getRecipes()) {
/* 155 */         if (recipe.canCraft(func_70301_a(0))) {
/* 156 */           this.currentRecipeId = recipe.getId();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 162 */     if ((oldId != null && this.currentRecipeId != null && !oldId.equals(this.currentRecipeId)) || (oldId == null && this.currentRecipeId != null) || (oldId != null && this.currentRecipeId == null)) {
/*     */ 
/*     */       
/* 165 */       this.optionId = -1;
/* 166 */       updateRecipe();
/* 167 */       queueNetworkUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateRecipe() {
/* 172 */     this.currentRecipe = BuildcraftRecipeRegistry.programmingTable.getRecipe(this.currentRecipeId);
/* 173 */     if (this.currentRecipe != null) {
/* 174 */       this.options = this.currentRecipe.getOptions(6, 4);
/*     */     } else {
/* 176 */       this.options = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rpcSelectOption(final int pos) {
/* 181 */     BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "select", new CommandWriter() {
/*     */             public void write(ByteBuf data) {
/* 183 */               data.writeByte(pos);
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/* 190 */     if (side.isServer() && "select".equals(command)) {
/* 191 */       this.optionId = stream.readByte();
/* 192 */       if (this.optionId >= this.options.size()) {
/* 193 */         this.optionId = -1;
/* 194 */       } else if (this.optionId < -1) {
/* 195 */         this.optionId = -1;
/*     */       } 
/*     */       
/* 198 */       queueNetworkUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWork() {
/* 204 */     return (this.currentRecipe != null && this.optionId >= 0 && func_70301_a(1) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraft() {
/* 209 */     return hasWork();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int slot, ItemStack stack) {
/* 214 */     return (slot == 0 || stack == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int side) {
/* 224 */     return new int[] { 0, 1 };
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int slot, ItemStack stack, int side) {
/* 229 */     return (slot == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int slot, ItemStack stack, int side) {
/* 234 */     return (slot == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\TileProgrammingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */