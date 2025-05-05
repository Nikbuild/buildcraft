/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.api.transport.IPipeTile;
/*     */ import buildcraft.api.transport.pluggable.PipePluggable;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.transport.gates.GateDefinition;
/*     */ import buildcraft.transport.gates.GatePluggable;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemGateCopier
/*     */   extends ItemBuildCraft
/*     */ {
/*     */   public ItemGateCopier() {
/*  28 */     func_77625_d(1);
/*  29 */     func_77655_b("gateCopier");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77650_f(ItemStack i) {
/*  35 */     NBTTagCompound cpt = NBTUtils.getItemData(i);
/*  36 */     this.field_77791_bV = cpt.func_74764_b("logic") ? this.icons[1] : this.icons[0];
/*  37 */     return this.field_77791_bV;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/*  42 */     if (world.field_72995_K) {
/*  43 */       return true;
/*     */     }
/*     */     
/*  46 */     boolean isCopying = !player.func_70093_af();
/*  47 */     TileEntity tile = world.func_147438_o(x, y, z);
/*  48 */     NBTTagCompound data = NBTUtils.getItemData(stack);
/*  49 */     PipePluggable pluggable = null;
/*  50 */     Gate gate = null;
/*     */     
/*  52 */     if (tile == null || !(tile instanceof IPipeTile)) {
/*  53 */       isCopying = true;
/*     */     } else {
/*  55 */       Block block = world.func_147439_a(x, y, z);
/*     */       
/*  57 */       if (tile instanceof TileGenericPipe && block instanceof BlockGenericPipe) {
/*  58 */         BlockGenericPipe.RaytraceResult rayTraceResult = ((BlockGenericPipe)block).doRayTrace(world, x, y, z, player);
/*     */         
/*  60 */         if (rayTraceResult != null && rayTraceResult.boundingBox != null && rayTraceResult.hitPart == BlockGenericPipe.Part.Pluggable) {
/*  61 */           pluggable = ((TileGenericPipe)tile).getPipePluggable(rayTraceResult.sideHit);
/*     */         }
/*     */       } else {
/*  64 */         pluggable = ((IPipeTile)tile).getPipePluggable(ForgeDirection.getOrientation(side));
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     if (pluggable instanceof GatePluggable) {
/*  69 */       gate = ((GatePluggable)pluggable).realGate;
/*     */     }
/*     */     
/*  72 */     if (isCopying) {
/*  73 */       if (gate == null) {
/*  74 */         stack.func_77982_d(new NBTTagCompound());
/*  75 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.clear", new Object[0]));
/*  76 */         return true;
/*     */       } 
/*     */       
/*  79 */       data = new NBTTagCompound();
/*  80 */       stack.func_77982_d(data);
/*     */       
/*  82 */       gate.writeStatementsToNBT(data);
/*  83 */       data.func_74774_a("material", (byte)gate.material.ordinal());
/*  84 */       data.func_74774_a("logic", (byte)gate.logic.ordinal());
/*  85 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.gateCopied", new Object[0]));
/*     */     } else {
/*  87 */       if (!data.func_74764_b("logic")) {
/*  88 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.noInformation", new Object[0]));
/*  89 */         return true;
/*  90 */       }  if (gate == null) {
/*  91 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.noGate", new Object[0]));
/*  92 */         return true;
/*     */       } 
/*     */       
/*  95 */       GateDefinition.GateMaterial dataMaterial = GateDefinition.GateMaterial.fromOrdinal(data.func_74771_c("material"));
/*  96 */       GateDefinition.GateMaterial gateMaterial = gate.material;
/*     */       
/*  98 */       if (gateMaterial.numSlots < dataMaterial.numSlots) {
/*  99 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.warning.slots", new Object[0]));
/*     */       }
/* 101 */       if (gateMaterial.numActionParameters < dataMaterial.numActionParameters) {
/* 102 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.warning.actionParameters", new Object[0]));
/*     */       }
/* 104 */       if (gateMaterial.numTriggerParameters < dataMaterial.numTriggerParameters) {
/* 105 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.warning.triggerParameters", new Object[0]));
/*     */       }
/* 107 */       if (data.func_74771_c("logic") != gate.logic.ordinal()) {
/* 108 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.warning.logic", new Object[0]));
/*     */       }
/*     */       
/* 111 */       gate.readStatementsFromNBT(data);
/* 112 */       if (!gate.verifyGateStatements()) {
/* 113 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.warning.load", new Object[0]));
/*     */       }
/*     */       
/* 116 */       if (tile instanceof TileGenericPipe) {
/* 117 */         ((TileGenericPipe)tile).sendUpdateToClient();
/*     */       }
/* 119 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.gateCopier.gatePasted", new Object[0]));
/* 120 */       return true;
/*     */     } 
/*     */     
/* 123 */     return true;
/*     */   }
/*     */   
/*     */   public String[] getIconNames() {
/* 127 */     return new String[] { "gateCopier/empty", "gateCopier/full" };
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\ItemGateCopier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */